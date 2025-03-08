package com.tmdigital.gestiondestock.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tmdigital.gestiondestock.dto.AddressDto;
import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.model.Company;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCompanySuccessfully() {
        // Given
        CompanyDto companyDto = CompanyDto.builder()
            .id(1)
            .name("Test Company")
            .description("Description")
            .mail("test@email.com")
            .numTel("123456789")
            .taxCode("123456789")
            .website("test.com")
            .address(AddressDto.builder()
                .address1("123 Rue")
                .city("Paris")
                .zip("75000")
                .state("Ile de France")
                .country("France")
                .build())
            .build();

        when(companyRepository.save(any(Company.class))).thenReturn(CompanyDto.toEntity(companyDto));

        // When
        CompanyDto savedCompany = companyService.save(companyDto);

        // Then
        assertNotNull(savedCompany);
        assertNotNull(savedCompany.getId());
        assertEquals(companyDto.getDescription(), savedCompany.getDescription());
        assertEquals(companyDto.getMail(), savedCompany.getMail());
        assertEquals(companyDto.getNumTel(), savedCompany.getNumTel());
        assertEquals(companyDto.getTaxCode(), savedCompany.getTaxCode());
        assertEquals(companyDto.getWebsite(), savedCompany.getWebsite());
        assertEquals(companyDto.getAddress().getAddress1(), savedCompany.getAddress().getAddress1());
        assertEquals(companyDto.getAddress().getCity(), savedCompany.getAddress().getCity());
        assertEquals(companyDto.getAddress().getZip(), savedCompany.getAddress().getZip());
        assertEquals(companyDto.getAddress().getState(), savedCompany.getAddress().getState());
        assertEquals(companyDto.getAddress().getCountry(), savedCompany.getAddress().getCountry());
        verify(companyRepository, times(1)).save(any(Company.class));
    }
}
