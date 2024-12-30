package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.ClientDto;

public interface ClientService {

    public ClientDto save(ClientDto dto);

    public ClientDto findById(Integer id);

    public List<ClientDto> findAll();

    public List<ClientDto> findAllByCompany(Integer idCompany);

    public void delete(Integer id);
}
