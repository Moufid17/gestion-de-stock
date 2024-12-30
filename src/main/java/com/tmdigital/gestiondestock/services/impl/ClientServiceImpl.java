package com.tmdigital.gestiondestock.services.impl;

import java.util.List;

import com.tmdigital.gestiondestock.dto.ClientDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.repository.ClientRepository;
import com.tmdigital.gestiondestock.services.ClientService;
import com.tmdigital.gestiondestock.validator.ClientValidator;

public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);

        if(!errors.isEmpty()) {
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        return ClientDto.fromEntity(
            this.clientRepository.save(
                ClientDto.toEntity(dto)
            )
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'a été fourni");
        }

        return this.clientRepository.findById(id)
            .map(ClientDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException(
                "Aucun client n'a été trouvé avec l'identifiant : " + id,
                ErrorCodes.CLIENT_NOT_FOUND
            ));
    }

    @Override
    public List<ClientDto> findAll() {
        return this.clientRepository.findAll()
            .stream()
            .map(ClientDto::fromEntity)
            .toList();
    }

    @Override
    public List<ClientDto> findAllByCompany(Integer idCompany) {
        if (idCompany == null) {
            throw new InvalidEntityException("Aucun identifiant de la société n'a été fourni");
        }

        return this.clientRepository.findAllByIdCompany(idCompany)
            .stream()
            .map(ClientDto::fromEntity)
            .toList();
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'a été fourni");
        }

        this.clientRepository.deleteById(id);
    }

}
