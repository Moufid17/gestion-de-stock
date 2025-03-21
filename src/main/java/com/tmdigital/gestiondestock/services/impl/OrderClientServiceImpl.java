package com.tmdigital.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.dto.ClientDto;
import com.tmdigital.gestiondestock.dto.OrderLineClientDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.model.Article;
import com.tmdigital.gestiondestock.model.Client;
import com.tmdigital.gestiondestock.model.OrderClient;
import com.tmdigital.gestiondestock.model.OrderLineClient;
import com.tmdigital.gestiondestock.model.OrderStatus;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.ClientRepository;
import com.tmdigital.gestiondestock.repository.OrderClientRepository;
import com.tmdigital.gestiondestock.repository.OrderLineClientRepository;
import com.tmdigital.gestiondestock.services.OrderClientService;
import com.tmdigital.gestiondestock.validator.OrderClientValidator;
import com.tmdigital.gestiondestock.validator.OrderLineClientValidator;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderClientRepository orderClientRepository;
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;
    private final OrderLineClientRepository orderLineClientRepository;

    public OrderClientServiceImpl(OrderClientRepository orderClientRepository, ClientRepository clientRepository, ArticleRepository articleRepository, OrderLineClientRepository orderLineClientRepository) {
        this.orderClientRepository = orderClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.orderLineClientRepository = orderLineClientRepository;
    }

    @Override
    public OrderClientDto save(OrderClientDto dto) {
        List<String> errors = OrderClientValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande n'est pas valide", ErrorCodes.ORDER_CLIENT_NOT_VALID, errors);
        }

        // check if the order is already delivered or canceled
        if (dto.getId() != null && (dto.isDelivered() || dto.isCancaled())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré.");
            throw new InvalidEntityException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        // Check if the order lines are valid
        if (dto.getOrderLineClients() ==  null) {
            log.warn("Impossible d'enregister une commande avec des lignes de commandes nulles.");
            throw new InvalidEntityException("Impossible d'enregister une commande avec des lignes de commandes nulles.", ErrorCodes.ORDER_CLIENT_NOT_VALID, errors);
        }

        // Check if the client exists
        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (!client.isPresent()) {
            log.warn("L'identifiant {}  n'est pas valide", dto.getClient().getId());
            throw new InvalidEntityException("Le client n'existe pas", ErrorCodes.CLIENT_NOT_FOUND);
        }

        // Check if each order line are valid
        Set<String> errorsOrderLine = new HashSet<>();
            
        dto.getOrderLineClients().forEach(orderLine -> {
            errorsOrderLine.addAll(OrderLineClientValidator.validate(orderLine));
        });

        if (!errorsOrderLine.isEmpty()) {
            log.warn("Une ligne de commande n'est pas valide ou un L'article n'existe pas.");
            throw new InvalidEntityException("Une ligne de commande n'est pas valide ou un L'article n'existe pas.", ErrorCodes.ORDER_CLIENT_NOT_VALID, new ArrayList<>(errorsOrderLine));
        }

        if (dto.getStatus() == null) {
            dto.setStatus(OrderStatus.IN_PROGRESS);
        }

        // Save the order
        OrderClient savedOrderClient = orderClientRepository.save(OrderClientDto.toEntity(dto));
        
        dto.getOrderLineClients().forEach(orderLine -> {
            OrderLineClient orderLineClient = OrderLineClientDto.toEntity(orderLine);
            orderLineClient.setOrderClient(savedOrderClient);
            orderLineClient.setIdCompany(dto.getIdCompany());
            orderLineClientRepository.save(orderLineClient);
            // [ ] Mise à jour le Mvt de stock en sortie

        });
        
        return OrderClientDto.fromEntity(savedOrderClient);
    }

    @Override
    public void updateOrderLineQte(Integer orderId, Integer orderLineId, BigDecimal qte) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidEntityException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (orderLineId == null) {
            log.error("L'identifiant de la ligne de commande est nul");
            throw new InvalidEntityException("L'identifiant de la ligne de commande est nul", ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }

        if (qte == null || BigDecimal.ZERO.compareTo(qte) == 0) {
            log.error("La quantité est nulle ou égale à zéro");
            throw new InvalidEntityException("La quantité est nulle ou égale à zéro", ErrorCodes.ORDER_LINE_CLIENT_NOT_VALID);
        }

        OrderClientDto orderClientDto = findById(orderId);

        if (orderClientDto == null) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (OrderStatus.CANCELED.equals(orderClientDto.getStatus()) || OrderStatus.DELIVERED.equals(orderClientDto.getStatus())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", orderClientDto.getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        List<OrderLineClientDto> orderLineClientDtoList = orderClientDto.getOrderLineClients();

        if (orderLineClientDtoList == null || orderLineClientDtoList.isEmpty()) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidOperationException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }
        
        OrderLineClientDto orderLineClientDto = orderLineClientDtoList.stream()
            .filter(orderLine -> orderLine.getId().equals(orderLineId))
            .findFirst()
            .orElseThrow(() -> new InvalidEntityException("Aucune ligne de commande n'a été trouvée avec l'identifiant " + orderLineId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND));

        if (orderLineClientDto.getQty().compareTo(qte) == 0) {
            log.error("La nouvelle quantité est la même que l'ancienne quantité");
            throw new InvalidOperationException("La nouvelle quantité est la même que l'ancienne quantité", ErrorCodes.ORDER_LINE_CLIENT_NOT_VALID);
        }

        orderLineClientDto.setQty(qte);

        // OrderLineClientDto do not have the orderClient attribut (due to JsonIgnore : so neither setter nor getter)
        // So we need to convert it to OrderLineClient entity to set "orderClientDto" before save it.
        // Do not try to use <OrderClientDto.toEntity(...)> inside the <OrderLineClientDto.toEntity(...)>, it will cause a dependency loop error. 
        OrderLineClient orderLineClient = OrderLineClientDto.toEntity(orderLineClientDto);
        orderLineClient.setOrderClient(OrderClientDto.toEntity(orderClientDto));

        orderLineClientRepository.save(orderLineClient);
    }

    @Override
    public void updateOrderStatus(Integer orderId, OrderStatus newStatus) {
        
        if (orderId == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (newStatus == null || !StringUtils.hasLength(newStatus.name())) {
            log.error("Le nouveau status est nul");
            throw new InvalidOperationException("Le nouveau status est nul", ErrorCodes.ORDER_CLIENT_NOT_VALID);
        }

        OrderClientDto orderClientDto = findById(orderId);

        if (orderClientDto == null) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (OrderStatus.CANCELED.equals(orderClientDto.getStatus()) || OrderStatus.DELIVERED.equals(orderClientDto.getStatus())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", orderClientDto.getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        if (orderClientDto.getStatus().equals(newStatus)) {
            log.error("Le nouveau status est le même que l'ancien status");
            throw new InvalidOperationException("Le nouveau status est le même que l'ancien status", ErrorCodes.ORDER_CLIENT_NOT_VALID);
        }

        orderClientDto.setStatus(newStatus);

        OrderClientDto.fromEntity(orderClientRepository.save(OrderClientDto.toEntity(orderClientDto)));
    }

    @Override
    public void updateClient(Integer orderId, Integer clientId) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (clientId == null) {
            log.error("L'identifiant du client est nul");
            throw new InvalidOperationException("L'identifiant du client est nul", ErrorCodes.CLIENT_NOT_FOUND);
        }

        OrderClientDto orderClientDto = findById(orderId);

        if (orderClientDto == null) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (OrderStatus.CANCELED.equals(orderClientDto.getStatus()) || OrderStatus.DELIVERED.equals(orderClientDto.getStatus())) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré : {}", orderClientDto.getStatus());
            throw new InvalidOperationException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new InvalidEntityException("Aucun client n'a été trouvé avec l'identifiant " + clientId, ErrorCodes.CLIENT_NOT_FOUND));

        orderClientDto.setClient(ClientDto.fromEntity(client));

        orderClientRepository.save(OrderClientDto.toEntity(orderClientDto));
    }

    @Override
    public void updateArticle(Integer orderId, Integer orderLineId, Integer newArticleId) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (orderLineId == null) {
            log.error("L'identifiant de la line de commande est nul");
            throw new InvalidOperationException("L'identifiant de la line de commande est nul", ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }

        if (newArticleId == null) {
            log.error("L'identifiant de l'article est nul");
            throw new InvalidOperationException("L'identifiant de l'article est nul", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        OrderClientDto orderClientDto = findById(orderId);

        if (orderClientDto == null) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        List<OrderLineClientDto> orderLineClientDtoList = orderClientDto.getOrderLineClients();
        
        if (orderLineClientDtoList == null || orderLineClientDtoList.isEmpty()) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidOperationException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }
        
        OrderLineClientDto orderLineClientDto = orderLineClientDtoList.stream()
            .filter(orderLine -> orderLine.getId().equals(orderLineId))
            .findFirst()
            .orElseThrow(() -> new InvalidEntityException("Aucune ligne de commande n'a été trouvée avec l'identifiant " + orderLineId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND));

        Optional<Article> article = articleRepository.findById(newArticleId);

        if (!article.isPresent()) {
            log.error("Aucun article n'a été trouvé avec l'identifiant {}", newArticleId);
            throw new InvalidEntityException("Aucun article n'a été trouvé avec l'identifiant " + newArticleId, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        if (orderLineClientDto.getArticle().getId().equals(newArticleId)) {
            log.error("Le nouvel article est le même que l'ancien article");
            throw new InvalidOperationException("Impossible de mettre à jour la commande", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        orderLineClientDto.setArticle(ArticleDto.fromEntity(article.get()));
        orderLineClientDto.setSellPriceInclTax(article.get().getSellPriceInclTax());

        // Reset orderLineClientDto client with the old client (it's set in the OrderLineClientDto.toEntity method)
        OrderLineClient orderLineClient = OrderLineClientDto.toEntity(orderLineClientDto);
        orderLineClient.setOrderClient(OrderClientDto.toEntity(orderClientDto));

        orderLineClientRepository.save(orderLineClient);
        
    }

    @Override
    public OrderClientDto findById(Integer id) {
        if(id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderClientRepository.findById(id)
            .map(OrderClientDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + id, ErrorCodes.ORDER_CLIENT_NOT_FOUND));
    }

    @Override
    public OrderClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderClientRepository.findByCode(code)
            .map(OrderClientDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + code, ErrorCodes.ORDER_CLIENT_NOT_FOUND));
    }

    @Override
    public List<OrderClientDto> findAll() {
        return orderClientRepository.findAll().stream()
            .map(OrderClientDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderLineClientDto> findAllOrderLine(Integer orderId) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        Optional<OrderClient> orderClient = orderClientRepository.findById(orderId);

        // check if the order exists
        if (!orderClient.isPresent()) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        List<OrderLineClient> orderLineClientsList = orderClient.get().getOrderLineClients();
        // check if the order has order lines
        if (orderLineClientsList == null || orderLineClientsList.isEmpty()) {
            log.info("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            return new ArrayList<>();
        }

        return orderLineClientsList.stream()
            .map(OrderLineClientDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderClientDto> findAllByCompany(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderClientRepository.findAll().stream()
            .map(OrderClientDto::fromEntity)
            .collect(Collectors.toList());
    }

	@Override
	public List<OrderClientDto> findAllByClient(Integer id) {
		if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderClientRepository.findAllByClientId(id).stream()
            .map(OrderClientDto::fromEntity)
            .collect(Collectors.toList());
	}

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        Optional<OrderClient> orderClient = orderClientRepository.findById(id);

        // check if the order exists
        if (!orderClient.isPresent()) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", id);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + id, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }
        
        // check if the order is already delivered or canceled
        if (orderClient.get().getStatus().equals(OrderStatus.DELIVERED) || orderClient.get().getStatus().equals(OrderStatus.CANCELED)) {
            log.error("Impossible de supprimer une commande annulé ou déjà livrée");
            throw new InvalidEntityException("Impossible de supprimer une commande annulé ou déjà livrée.");
        }

        // check if the order has order lines
        if (!orderClient.get().getOrderLineClients().isEmpty()) {
            log.error("Impossible de supprimer une commande avec des lignes de commandes");
            throw new InvalidEntityException("Impossible de supprimer une commande avec des lignes de commandes", ErrorCodes.ORDER_CLIENT_IS_ALREADY_USED);
        }

        orderClientRepository.deleteById(id);
    }

    @Override
    public void deleteOrderLine(Integer orderId, Integer orderLineId) {
        if (orderId == null) {
            log.error("L'identifiant de la commande est nul");
            throw new InvalidOperationException("L'identifiant de la commande est nul", ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }

        if (orderLineId == null) {
            log.error("L'identifiant de la ligne de commande est nul");
            throw new InvalidOperationException("L'identifiant de la ligne de commande est nul", ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }

        Optional<OrderClient> orderClient = orderClientRepository.findById(orderId);

        // check if the order exists
        if (!orderClient.isPresent()) {
            log.error("Aucune commande n'a été trouvée avec l'identifiant {}", orderId);
            throw new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + orderId, ErrorCodes.ORDER_CLIENT_NOT_FOUND);
        }
        
        // check if the order is already delivered or canceled
        if (OrderStatus.DELIVERED.equals(orderClient.get().getStatus()) || OrderStatus.CANCELED.equals(orderClient.get().getStatus())) {
            log.error("Impossible de supprimer une commande annulé ou déjà livrée");
            throw new InvalidOperationException("Impossible de supprimer une commande annulé ou déjà livrée.", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        List<OrderLineClient> orderLineClientsList = orderClient.get().getOrderLineClients();
        // check if the order has order lines
        if (orderLineClientsList == null || orderLineClientsList.isEmpty()) {
            log.error("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant {}", orderId);
            throw new InvalidOperationException("Aucune ligne de commande n'a été trouvée pour la commande d'identifiant " + orderId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }

        Optional<OrderLineClient> orderLineClient = orderLineClientsList.stream()
            .filter(orderLine -> orderLine.getId().equals(orderLineId))
            .findFirst();
        
        if (!orderLineClient.isPresent()) {
            log.error("Aucune ligne de commande n'a été trouvée avec l'identifiant {}", orderLineId);
            throw new InvalidEntityException("Aucune ligne de commande n'a été trouvée avec l'identifiant " + orderLineId, ErrorCodes.ORDER_LINE_CLIENT_NOT_FOUND);
        }

        orderLineClientRepository.deleteById(orderLineId);
    }

}
