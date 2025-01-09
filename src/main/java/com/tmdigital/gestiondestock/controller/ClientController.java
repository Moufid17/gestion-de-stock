package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.ClientApi;
import com.tmdigital.gestiondestock.dto.ClientDto;
import com.tmdigital.gestiondestock.services.impl.ClientServiceImpl;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController implements ClientApi {
    private ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public List<ClientDto> findAllByCompany(Integer id) {
        return clientService.findAllByCompany(id);
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }

}
