package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.dto.OrderLineClientDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
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

        // check if the order is already delivered or canceled
        if (dto.isDelivered() || dto.isCancaled()) {
            log.error("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré.");
            throw new InvalidEntityException("Impossible de mettre à jour cette commande car elle a été annulé ou elle est déjà livré", ErrorCodes.ORDER_CLIENT_ALREADY_DELIVERED);
        }

        // Check if each order line are valid abd if the article exists
        List<String> orderLineErrors = dto.getOrderLineClients().stream()
            .map(orderLine -> {
                List<String> errorsOrderLine = OrderLineClientValidator.validate(orderLine);
                if (errorsOrderLine.isEmpty()) {
                    Optional<Article> article = articleRepository.findById(orderLine.getArticle().getId());
                    if (!article.isPresent()) {
                        return "L'article avec l'identifiant " + orderLine.getArticle().getId() + " n'existe pas";
                    } else {
                        return null;
                    }
                } else {
                    return "Impossible d'enregister une commande avec une ligne de commande non valide";
                }
            })
            .filter(error -> error != null)
            .distinct()
            .collect(Collectors.toList());

        if (!orderLineErrors.isEmpty()) {
            log.warn("Une ligne de commande n'est pas valide ou un L'article n'existe pas.");
            throw new InvalidEntityException("Une ligne de commande n'est pas valide ou un L'article n'existe pas.", ErrorCodes.ORDER_CLIENT_NOT_VALID, orderLineErrors);
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


}
