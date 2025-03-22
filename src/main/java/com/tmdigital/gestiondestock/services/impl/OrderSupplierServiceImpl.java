package com.tmdigital.gestiondestock.services.impl;

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
import com.tmdigital.gestiondestock.validator.OrderLineClientValidator;
import com.tmdigital.gestiondestock.validator.OrderLineSupplierValidator;
import com.tmdigital.gestiondestock.validator.OrderSupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderSupplierServiceImpl implements OrderSupplierService {

    private OrderSupplierRepository orderSupplierRepository;
    private SupplierRepository supplierRepository;
    private final ArticleRepository articleRepository;
    private final OrderLineSupplierRepository orderLineSupplierRepository;

    public OrderSupplierServiceImpl(OrderSupplierRepository orderSupplierRepository, SupplierRepository supplierRepository, ArticleRepository articleRepository, OrderLineSupplierRepository orderLineSupplierRepository) {
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
            throw new InvalidEntityException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        // Check if the order lines are valid
        if (dto.getOrderLineSupplier() ==  null) {
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
            throw new InvalidEntityException("Une ligne de commande n'est pas valide ou un L'article n'existe pas.", ErrorCodes.ORDER_CLIENT_NOT_VALID, new ArrayList<>(errorsOrderLine));
        }

        if (dto.getStatus() == null) {
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
            orderLineSupplierRepository.save(orderLineSupplier);

            // [ ] Mise à jour le Mvt de stock en entrée

        });
        
        return OrderSupplierDto.fromEntity(savedOrderSupplier);
    }

    @Override
    public OrderSupplierDto addClientOrderLine(Integer orderId, OrderLineSupplierDto dto) {

        List<String> errors = OrderLineSupplierValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La ligne de commande n'est pas valide", ErrorCodes.ORDER_LINE_CLIENT_NOT_VALID, errors);
        }

        ArticleDto articleDto = ArticleDto.fromEntity(articleRepository.findById(dto.getArticle().getId())
            .orElseThrow(() -> new InvalidEntityException("Aucun article n'a été trouvé avec l'identifiant " + dto.getArticle().getId(), ErrorCodes.ARTICLE_NOT_FOUND)));   


        List<OrderLineSupplierDto> orderLineSupplierList = findAllOrderLine(orderId);
        if (orderLineSupplierList == null || orderLineSupplierList.isEmpty()) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }        
                
        orderLineSupplierList.stream()
            .filter(orderLine -> orderLine.getArticle().getId().equals(dto.getArticle().getId()))
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
        orderLineSupplierRepository.save(newOrderLineSupplier);

        orderSupplierDto.getOrderLineSupplier().add(OrderLineSupplierDto.fromEntity(newOrderLineSupplier));
        return OrderSupplierDto.fromEntity(orderSupplierRepository.save(OrderSupplierDto.toEntity(orderSupplierDto)));
    }

    @Override
    public OrderSupplierDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
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
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        Optional<OrderSupplier> orderSupplier = orderSupplierRepository.findById(orderId);

        // check if the order exists
        if (!orderSupplier.isPresent()) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
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
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        Optional<OrderSupplier> orderSupplier = orderSupplierRepository.findById(id);

        // check if the order exists
        if (!orderSupplier.isPresent()) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", id);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + id, ErrorCodes.ORDER_SUPPLIER_NOT_FOUND);
        }
        
        // check if the order is already delivered or canceled
        if (orderSupplier.get().getStatus().equals(OrderStatus.DELIVERED) || orderSupplier.get().getStatus().equals(OrderStatus.CANCELED)) {
            log.error("Impossible de supprimer une commande annulé ou déjà validée");
            throw new InvalidEntityException("Impossible de supprimer une commande annulé ou déjà validée");
        }

        if (!orderSupplier.get().getOrderLineSupplier().isEmpty()) {
            log.error("Impossible de supprimer une commande avec des lignes de commandes");
            throw new InvalidEntityException("Impossible de supprimer une commande avec des lignes de commandes", ErrorCodes.ORDER_SUPPLIER_IS_ALREADY_USED);
        }

        orderSupplierRepository.deleteById(id);
    }
}
