package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.Client;
import com.tmdigital.gestiondestock.repository.ClientRepository;
import com.tmdigital.gestiondestock.repository.OrderClientRepository;
import com.tmdigital.gestiondestock.services.OrderClientService;
import com.tmdigital.gestiondestock.validator.OrderClientValidator;


@Service
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderClientRepository orderClientRepository;
    private final ClientRepository clientRepository;

    public OrderClientServiceImpl(OrderClientRepository orderClientRepository, ClientRepository clientRepository) {
        this.orderClientRepository = orderClientRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public OrderClientDto save(OrderClientDto dto) {
        List<String> errors = OrderClientValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("La commande n'est pas valide", ErrorCodes.ORDER_CLIENT_NOT_VALID, errors);
        }

        Optional<Client> company = clientRepository.findById(dto.getClient().getId());
        if (!company.isPresent()) {
            throw new InvalidEntityException("Le client n'existe pas", ErrorCodes.CLIENT_NOT_FOUND);
        }

        return OrderClientDto.fromEntity(orderClientRepository.save(OrderClientDto.toEntity(dto)));
    }

    @Override
    public OrderClientDto findById(Integer id) {
        if(id == null) {
            throw new InvalidEntityException("L'identifiant de la commande est invalide.");
        }

        return orderClientRepository.findById(id)
            .map(OrderClientDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucune commande n'a été trouvée avec l'identifiant " + id, ErrorCodes.ORDER_CLIENT_NOT_FOUND));
    }

    @Override
    public OrderClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            throw new InvalidEntityException("L'identifiant de la commande est invalide.");
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
            throw new InvalidEntityException("L'identifiant est invalide.");
        }

        return orderClientRepository.findAll().stream()
            .map(OrderClientDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("L'identifiant est invalide.");
        }

        orderClientRepository.deleteById(id);
    }
}
