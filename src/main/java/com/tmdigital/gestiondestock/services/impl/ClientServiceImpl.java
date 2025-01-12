package com.tmdigital.gestiondestock.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.ClientDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.OrderClient;
import com.tmdigital.gestiondestock.repository.ClientRepository;
import com.tmdigital.gestiondestock.repository.OrderClientRepository;
import com.tmdigital.gestiondestock.services.ClientService;
import com.tmdigital.gestiondestock.validator.ClientValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private OrderClientRepository orderClientRespRepository;

    public ClientServiceImpl(ClientRepository clientRepository, OrderClientRepository orderClientRespRepository) {
        this.clientRepository = clientRepository;
        this.orderClientRespRepository = orderClientRespRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);

        if(!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        return ClientDto.fromEntity(
            clientRepository.save(
                ClientDto.toEntity(dto)
            )
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return clientRepository.findById(id)
            .map(ClientDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException(
                "Aucun client n'a été trouvé avec l'identifiant : " + id,
                ErrorCodes.CLIENT_NOT_FOUND
            ));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll()
            .stream()
            .map(ClientDto::fromEntity)
            .toList();
    }

    @Override
    public List<ClientDto> findAllByCompany(Integer idCompany) {
        if (idCompany == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return clientRepository.findAllByIdCompany(idCompany)
            .stream()
            .map(ClientDto::fromEntity)
            .toList();
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        List<OrderClient> orderClients = orderClientRespRepository.findAllByClientId(id);

        if (!orderClients.isEmpty()) {
            log.error("Impossible de supprimer le client avec l'id = {}", id);
            throw new InvalidEntityException("Impossible de supprimer client", ErrorCodes.CLIENT_IS_ALREADY_USED);
        }

        clientRepository.deleteById(id);
    }

}
