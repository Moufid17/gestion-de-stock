package com.tmdigital.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;
import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.dto.StockMovementDto;
import com.tmdigital.gestiondestock.dto.SupplierDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.model.Article;
import com.tmdigital.gestiondestock.model.OrderLineSupplier;
import com.tmdigital.gestiondestock.model.OrderStatus;
import com.tmdigital.gestiondestock.model.OrderSupplier;
import com.tmdigital.gestiondestock.model.Supplier;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.OrderLineSupplierRepository;
import com.tmdigital.gestiondestock.repository.OrderSupplierRepository;
import com.tmdigital.gestiondestock.repository.SupplierRepository;
import com.tmdigital.gestiondestock.services.OrderSupplierService;
import com.tmdigital.gestiondestock.services.StockMovementService;
import com.tmdigital.gestiondestock.validator.OrderLineSupplierValidator;
import com.tmdigital.gestiondestock.validator.OrderSupplierValidator;
import com.tmdigital.gestiondestock.model.StockMovementType;
import com.tmdigital.gestiondestock.model.MovementSource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderSupplierServiceImpl implements OrderSupplierService {

    private OrderSupplierRepository orderSupplierRepository;
    private SupplierRepository supplierRepository;
    private final ArticleRepository articleRepository;
    private final OrderLineSupplierRepository orderLineSupplierRepository;
    private final StockMovementService stockMovementService;

    public OrderSupplierServiceImpl(OrderSupplierRepository orderSupplierRepository, SupplierRepository supplierRepository, ArticleRepository articleRepository, OrderLineSupplierRepository orderLineSupplierRepository, StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
        this.orderSupplierRepository = orderSupplierRepository;
        this.supplierRepository = supplierRepository;
        this.articleRepository = articleRepository;
        this.orderLineSupplierRepository = orderLineSupplierRepository;
    }

    @Override
    public OrderSupplierDto save(OrderSupplierDto dto) {
        List<String> errors = OrderSupplierValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande n'est pas valide", ErrorCodes.ORDER_SUPPLIER_NOT_VALID, errors);
        }

        if (null == dto.getIdCompany()) {
            log.error("Impossible de créer une commande sans entreprise");
            throw new InvalidEntityException("Impossible de créer une commande sans entreprise", ErrorCodes.COMPANY_NOT_FOUND);
        }

        // check if the order is already delivered or canceled
        if (dto.getId() != null && (dto.isDelivered() || dto.isCancaled())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré.");
            throw new InvalidEntityException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_SUPPLIER_ALREADY_DELIVERED);
        }

        // Check if the order lines are valid
        if (null == dto.getOrderLineSupplier() || dto.getOrderLineSupplier().isEmpty()) {
            log.warn("Impossible d'enregister une commande avec des lignes de commandes nulles.");
            throw new InvalidEntityException("Impossible d'enregister une commande avec des lignes de commandes nulles.", ErrorCodes.ORDER_SUPPLIER_NOT_VALID, errors);
        }

        // Check if the supplier exists
        Optional<Supplier> supplier = supplierRepository.findById(dto.getSupplier().getId());
        if (!supplier.isPresent()) {
            log.warn("L'identifiant {}  n'est pas valide", dto.getSupplier().getId());
            throw new InvalidEntityException("Le fournisseur n'existe pas", ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        // Check if the order lines are valid
        Set<String> errorsOrderLine = new HashSet<>();
            
        dto.getOrderLineSupplier().forEach(orderLine -> {
            errorsOrderLine.addAll(OrderLineSupplierValidator.validate(orderLine));
        });

        if (!errorsOrderLine.isEmpty()) {
            log.warn("Une ligne de commande n'est pas valide ou un L'article n'existe pas.");
            throw new InvalidEntityException("Une ligne de commande n'est pas valide ou un L'article n'existe pas.", ErrorCodes.ORDER_SUPPLIER_NOT_VALID, new ArrayList<>(errorsOrderLine));
        }

        if (null == dto.getStatus()) {
            dto.setStatus(OrderStatus.IN_PROGRESS);
        }

        // Save the order
        OrderSupplier savedOrderSupplier = orderSupplierRepository.save(OrderSupplierDto.toEntity(dto));

        dto.getOrderLineSupplier().forEach(orderLine -> {
            ArticleDto articleDto = ArticleDto.fromEntity(articleRepository.findById(orderLine.getArticle().getId())
                .orElseThrow(() -> new InvalidEntityException("Aucun article n'a été trouvé avec l'identifiant " + orderLine.getArticle().getId(), ErrorCodes.ARTICLE_NOT_FOUND)));
            
            OrderLineSupplier orderLineSupplier = OrderLineSupplierDto.toEntity(orderLine);
            orderLineSupplier.setOrderSupplier(savedOrderSupplier);
            orderLineSupplier.setIdCompany(dto.getIdCompany());
            if (null == orderLine.getSellPriceInclTax()) {
                orderLineSupplier.setSellPriceInclTax(articleDto.getSellPriceInclTax());
            }
            OrderLineSupplierDto newOrderLineDto = OrderLineSupplierDto.fromEntity(orderLineSupplierRepository.save(orderLineSupplier));

            addStockMovement(newOrderLineDto, OrderSupplierDto.fromEntity(savedOrderSupplier));

        });
        
        return OrderSupplierDto.fromEntity(savedOrderSupplier);
    }

    @Override
    public OrderSupplierDto addSupplierOrderLine(Integer orderId, OrderLineSupplierDto dto) {

        List<String> errors = OrderLineSupplierValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La ligne de commande n'est pas valide", ErrorCodes.ORDER_LINE_SUPPLIER_NOT_VALID, errors);
        }

        ArticleDto articleDto = ArticleDto.fromEntity(articleRepository.findById(dto.getArticle().getId())
            .orElseThrow(() -> new InvalidEntityException("Aucun article n'a été trouvé avec l'identifiant " + dto.getArticle().getId(), ErrorCodes.ARTICLE_NOT_FOUND)));   

        List<OrderLineSupplierDto> orderLineSupplierList = findAllOrderLine(orderId);
        if (null == orderLineSupplierList  || orderLineSupplierList.isEmpty()) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND);
        }        
        
        // [ ] Use orderlinerepository to find artilce list
        orderLineSupplierList.stream()
            .filter(orderLine -> orderLine.getArticle().getId().equals(articleDto.getId()))
            .findAny()
            .ifPresent(orderLine -> {
                log.error("L'article avec l'identifiant {} est déjà dans la commande", dto.getArticle().getId());
                throw new InvalidOperationException("L'article avec l'identifiant " + dto.getArticle().getId() + " est déjà dans la commande", ErrorCodes.ARTICLE_ALREADY_IN_USE);
            });

        OrderSupplierDto orderSupplierDto = findById(orderId);

        OrderLineSupplier newOrderLineSupplier = OrderLineSupplierDto.toEntity(dto);
        newOrderLineSupplier.setOrderSupplier(OrderSupplierDto.toEntity(orderSupplierDto));
        newOrderLineSupplier.setIdCompany(orderSupplierDto.getIdCompany());
        newOrderLineSupplier.setSellPriceInclTax(articleDto.getSellPriceInclTax());
        OrderLineSupplierDto newOrderLineSupplierDto = OrderLineSupplierDto.fromEntity(orderLineSupplierRepository.save(newOrderLineSupplier));

        // Mise à jour le Mvt de stock en entrée
        addStockMovement(newOrderLineSupplierDto, orderSupplierDto);

        orderSupplierDto.getOrderLineSupplier().add(OrderLineSupplierDto.fromEntity(newOrderLineSupplier));
        return OrderSupplierDto.fromEntity(orderSupplierRepository.save(OrderSupplierDto.toEntity(orderSupplierDto)));
    }

    @Override
    public OrderSupplierDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidOperationException("Order id is required", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }

        return orderSupplierRepository.findById(id)
                .map(OrderSupplierDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("L'identifiant " + id + " n'est pas valide.", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND));
    }

    @Override
    public OrderSupplierDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findByCode(code)
                .map(OrderSupplierDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("Le code " + code + " n'est pas valide.", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND));
    }

    @Override
    public List<OrderLineSupplierDto> findAllOrderLine(Integer orderId) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }

        Optional<OrderSupplier> orderSupplier = orderSupplierRepository.findById(orderId);

        // check if the order exists
        if (!orderSupplier.isPresent()) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }

        if (OrderStatus.CANCELED.equals(orderSupplier.get().getStatus()) || OrderStatus.DELIVERED.equals(orderSupplier.get().getStatus())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", orderSupplier.get().getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_SUPPLIER_ALREADY_DELIVERED);
        }

        List<OrderLineSupplier> orderLineSupplierList = orderSupplier.get().getOrderLineSupplier();
        // check if the order has order lines
        if (orderLineSupplierList == null || orderLineSupplierList.isEmpty()) {
            log.info("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            return new ArrayList<>();
        }

        return orderLineSupplierList.stream()
            .map(OrderLineSupplierDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderSupplierDto> findAll() {
        return orderSupplierRepository.findAll().stream()
                .map(OrderSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSupplierDto> findAllByCompany(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findAllByIdCompany(id).stream()
                .map(OrderSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSupplierDto> findAllBySupplier(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findAllBySupplierId(id).stream()
                .map(OrderSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Integer orderId, OrderStatus newStatus) {
        if (orderId == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }

        if (newStatus == null || !StringUtils.hasLength(newStatus.name())) {
            log.error("Le nouveau status est nul");
            throw new InvalidOperationException("Le nouveau status est nul", ErrorCodes.ORDER_SUPPLIER_NOT_VALID);
        }

        OrderSupplierDto orderSupplierDto = findById(orderId);

        if (orderSupplierDto == null) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }

        if (OrderStatus.CANCELED.equals(orderSupplierDto.getStatus()) || OrderStatus.DELIVERED.equals(orderSupplierDto.getStatus())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", orderSupplierDto.getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_SUPPLIER_ALREADY_DELIVERED);
        }

        if (orderSupplierDto.getStatus().equals(newStatus)) {
            log.error("Le nouveau status est le même que l'ancien status");
            throw new InvalidOperationException("Le nouveau status est le même que l'ancien status", ErrorCodes.ORDER_SUPPLIER_NOT_VALID);
        }

        orderSupplierDto.setStatus(newStatus);

        OrderSupplierDto.fromEntity(orderSupplierRepository.save(OrderSupplierDto.toEntity(orderSupplierDto)));
    }

    @Override
    public void updateOrderLineQte(Integer orderId, Integer orderLineId, BigDecimal qte) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidEntityException("L'identifiant de la commande est nul", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }

        if (orderLineId == null) {
            log.error("L'identifiant de la ligne de commande est nul");
            throw new InvalidEntityException("L'identifiant de la ligne de commande est nul", ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND);
        }

        if (qte == null || BigDecimal.ZERO.compareTo(qte) == 0) {
            log.error("La quantité est nulle ou égale à zéro");
            throw new InvalidEntityException("La quantité est nulle ou égale à zéro", ErrorCodes.ORDER_LINE_SUPPLIER_NOT_VALID);
        }

        OrderSupplierDto orderSupplierDto = findById(orderId);

        if (OrderStatus.CANCELED.equals(orderSupplierDto.getStatus()) || OrderStatus.DELIVERED.equals(orderSupplierDto.getStatus())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", orderSupplierDto.getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_SUPPLIER_ALREADY_DELIVERED);
        }

        List<OrderLineSupplierDto> orderLineSupplierDtoList = orderSupplierDto.getOrderLineSupplier();

        if (orderLineSupplierDtoList == null || orderLineSupplierDtoList.isEmpty()) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidOperationException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND);
        }
        
        OrderLineSupplierDto orderLineSupplierDto = orderLineSupplierDtoList.stream()
            .filter(orderLine -> orderLine.getId().equals(orderLineId))
            .findFirst()
            .orElseThrow(() -> new InvalidEntityException("Aucune ligne de commande n'a été trouvée avec l'identifiant " + orderLineId, ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND));

        if (orderLineSupplierDto.getQty().compareTo(qte) == 0) {
            log.error("La nouvelle quantité est la même que l'ancienne quantité");
            throw new InvalidOperationException("La nouvelle quantité est la même que l'ancienne quantité", ErrorCodes.ORDER_LINE_SUPPLIER_NOT_VALID);
        }

        orderLineSupplierDto.setQty(qte);

        // orderLineSupplierDto do not have the orderSupplier attribut (due to JsonIgnore : so neither setter nor getter)
        // So we need to convert it to OrderLineSupplier entity to set "orderSupplierDto" before save it.
        // Do not try to use <OrderSupplierDto.toEntity(...)> inside the <OrderLineSupplierDto.toEntity(...)>, it will cause a dependency loop error. 
        OrderLineSupplier orderLineSupplier = OrderLineSupplierDto.toEntity(orderLineSupplierDto);
        orderLineSupplier.setOrderSupplier(OrderSupplierDto.toEntity(orderSupplierDto));

        orderLineSupplierRepository.save(orderLineSupplier);

        // Mise à jour le Mvt de stock en entrée
        updateStockMovement(orderLineSupplierDto, orderSupplierDto);
    }

    @Override
    public void updateSupplier(Integer orderId, Integer supplierId) {
        
        OrderSupplierDto orderSupplierDto = findById(orderId);

        checkStatus(orderSupplierDto);

        if (supplierId == null) {
            log.error("L'identifiant du client est nul");
            throw new InvalidOperationException("Supplier id is required", ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        Supplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new InvalidEntityException("Aucun fournisseur n'a été trouvé avec l'identifiant " + supplierId, ErrorCodes.SUPPLIER_NOT_FOUND));

        orderSupplierDto.setSupplier(SupplierDto.fromEntity(supplier));

        orderSupplierRepository.save(OrderSupplierDto.toEntity(orderSupplierDto));
    }

    @Override
    public void updateArticle(Integer orderId, Integer orderLineId, Integer newArticleId) {
        OrderSupplierDto orderSupplierDto = findById(orderId);

        if (orderLineId == null) {
            log.error("L'identifiant de la line de commande est nul");
            throw new InvalidOperationException("L'identifiant de la line de commande est nul", ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND);
        }

        if (newArticleId == null) {
            log.error("L'identifiant de l'article est nul");
            throw new InvalidOperationException("L'identifiant de l'article est nul", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<OrderLineSupplierDto> orderLineSupplierDtoList = orderSupplierDto.getOrderLineSupplier();
        
        if (null == orderLineSupplierDtoList) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidOperationException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND);
        }
        
        OrderLineSupplierDto orderLineSupplierDto = orderLineSupplierDtoList.stream()
            .filter(orderLine -> orderLine.getId().equals(orderLineId))
            .findFirst()
            .orElseThrow(() -> new InvalidEntityException("Aucune ligne de commande n'a été trouvée avec l'identifiant " + orderLineId, ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND));

        if (orderLineSupplierDto.getArticle().getId().equals(newArticleId)) {
            log.error("Le nouvel article {} est le même que l'ancien article {}", newArticleId, orderLineSupplierDto.getArticle().getId());
            throw new InvalidOperationException("Impossible de mettre à jour la commande", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        Optional<Article> newArticle = articleRepository.findById(newArticleId);

        if (!newArticle.isPresent()) {
            log.error("Aucun article n'a été trouvé avec l'identifiant {}", newArticleId);
            throw new InvalidEntityException("Aucun article n'a été trouvé avec l'identifiant " + newArticleId, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        orderLineSupplierDto.setArticle(ArticleDto.fromEntity(newArticle.get()));
        orderLineSupplierDto.setSellPriceInclTax(newArticle.get().getSellPriceInclTax());

        // Reset orderLineSupplierDto client with the old client (it's set in the OrderLineSupplierDto.toEntity method)
        OrderLineSupplier orderLineSupplier = OrderLineSupplierDto.toEntity(orderLineSupplierDto);
        orderLineSupplier.setOrderSupplier(OrderSupplierDto.toEntity(orderSupplierDto));

        orderLineSupplierRepository.save(orderLineSupplier);

        // Mise à jour le Mvt de stock en entrée
        StockMovementDto stockMovementDto = stockMovementService.findByOrderIdAndOrderlineId(orderSupplierDto.getId(), orderLineSupplier.getId());
        
        stockMovementDto.setArticle(orderLineSupplierDto.getArticle());
        stockMovementService.updateIn(stockMovementDto);
    }

    @Override
    public void delete(Integer orderId) {
        OrderSupplierDto orderSupplierDto = findById(orderId);
        
        // check if the order is already delivered or canceled
        checkStatus(orderSupplierDto);

        if (null != orderSupplierDto.getOrderLineSupplier()) {
            if (!orderSupplierDto.getOrderLineSupplier().isEmpty()) {
                log.error("Impossible de supprimer une commande avec des lignes de commandes");
                throw new InvalidEntityException("Impossible de supprimer une commande avec des lignes de commandes", ErrorCodes.ORDER_SUPPLIER_IS_ALREADY_USED);
            }
        }

        orderSupplierRepository.deleteById(orderId);
    }

    @Override
    public void deleteOrderLine(Integer orderId, Integer orderLineId) {
        OrderSupplierDto orderSupplierDto = findById(orderId);

        // check if the order is already delivered or canceled
        checkStatus(orderSupplierDto);

        if (orderLineId == null) {
            log.error("L'identifiant de la ligne de commande est nul");
            throw new InvalidOperationException("order line id is required", ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND);
        }

        OrderLineSupplier orderLineSupplier = orderLineSupplierRepository.findById(orderLineId)
            .orElseThrow(() -> new InvalidEntityException("Aucune ligne de commande n'a été trouvée avec l'identifiant " + orderLineId, ErrorCodes.ORDER_LINE_SUPPLIER_NOT_FOUND));

        StockMovementDto stockMovementDto = stockMovementService.findByOrderIdAndOrderlineId(orderId, orderLineSupplier.getId());
        stockMovementService.delete(stockMovementDto.getId());
        
        orderLineSupplierRepository.deleteById(orderLineId);
    }

    private void addStockMovement(OrderLineSupplierDto orderLineSupplierDto, OrderSupplierDto orderSupplierDto) {
        StockMovementDto stockMovementDto = StockMovementDto.builder()
            .article(orderLineSupplierDto.getArticle())
            .qty(orderLineSupplierDto.getQty())
            .dateMovement(Instant.now())
            .typeMvt(StockMovementType.INPUT)
            .sourceMvt(MovementSource.ORDER_SUPPLIER)
            .orderId(orderSupplierDto.getId())
            .orderlineId(orderLineSupplierDto.getId())
            .companyId(orderSupplierDto.getIdCompany())
            .build();

        stockMovementService.stockIn(stockMovementDto);
    }

    private void updateStockMovement(OrderLineSupplierDto orderLineSupplierDto, OrderSupplierDto orderSupplierDto) {
        StockMovementDto stockMovementDto = stockMovementService.findByOrderIdAndOrderlineId(orderSupplierDto.getId(), orderLineSupplierDto.getId());
        
        stockMovementDto.setQty(orderLineSupplierDto.getQty());
        stockMovementDto.setArticle(orderLineSupplierDto.getArticle());
        stockMovementService.updateIn(stockMovementDto);
    }

    private void checkStatus(OrderSupplierDto dto) {
        if (dto.isCancaled() || dto.isDelivered()) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", dto.getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_SUPPLIER_ALREADY_DELIVERED);
        }
    }
}
