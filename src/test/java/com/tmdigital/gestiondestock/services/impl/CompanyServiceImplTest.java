package com.tmdigital.gestiondestock.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.tmdigital.gestiondestock.dto.AddressDto;
import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.dto.UserDto;
import com.tmdigital.gestiondestock.model.Company;
import com.tmdigital.gestiondestock.model.User;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.repository.UserRepository;

import io.jsonwebtoken.lang.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private CompanyDto companyDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Given
        companyDto = CompanyDto.builder()
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
    }

    @Test
    void shouldSaveCompanySuccessfully() {
        
        // When
        when(companyRepository.save(any(Company.class))).thenReturn(CompanyDto.toEntity(companyDto));
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

    @Test
    void shouldThrowInvalidExceptionWhenSavingCompanyWithInvalidData() {
        // Given
        CompanyDto companyDto = CompanyDto.builder().build();

        // When
        // Then
        assertThrows(RuntimeException.class, () -> companyService.save(companyDto), "L'entité company n'est pas valide");
    }

    @Test
    void shouldFindByIdCompanySuccessfully() {
        // Given
        Company company = CompanyDto.toEntity(companyDto);
        //when
        when(companyRepository.findById(companyDto.getId())).thenReturn(Optional.of(company));
        CompanyDto foundCompany = companyService.findById(companyDto.getId());

        // Then
        assertNotNull(foundCompany);
        assertEquals(companyDto, foundCompany);
        assertEquals(companyDto.getId(), foundCompany.getId());
        assertEquals(companyDto.getDescription(), foundCompany.getDescription());
        assertEquals(companyDto.getMail(), foundCompany.getMail());
        assertEquals(companyDto.getNumTel(), foundCompany.getNumTel());
        assertEquals(companyDto.getTaxCode(), foundCompany.getTaxCode());
        assertEquals(companyDto.getWebsite(), foundCompany.getWebsite());
        assertEquals(companyDto.getAddress().getAddress1(), foundCompany.getAddress().getAddress1());
        assertEquals(companyDto.getAddress().getCity(), foundCompany.getAddress().getCity());
        assertEquals(companyDto.getAddress().getZip(), foundCompany.getAddress().getZip());
        assertEquals(companyDto.getAddress().getState(), foundCompany.getAddress().getState());
        assertEquals(companyDto.getAddress().getCountry(), foundCompany.getAddress().getCountry());
        verify(companyRepository, atMostOnce()).findById(companyDto.getId());
    }

    @Test
    void shouldReturnNullWhenFindingCompanyByIdWithNullId() {
        // Given
        // When
        // Then
        assertEquals(null, companyService.findById(null));
    }

    @Test
    void shouldThrowExceptionWhenFindingCompanyByIdWithUnexistingId() {
        // Given
        Integer unExistId = 0;

        // When
        when(companyRepository.findById(unExistId)).thenReturn(Optional.empty());
        // Then
        assertThrows(RuntimeException.class, () -> companyService.findById(unExistId), "Aucune entreprise n'a été trouvé avec l'id = " + unExistId);
    }

    @Test
    void shouldDeleteCompanySuccessfully() {
        // When
        when(userRepository.findAllByCompanyId(companyDto.getId())).thenReturn(Collections.emptyList());
        doNothing().when(companyRepository).deleteById(companyDto.getId());
        companyService.delete(companyDto.getId());

        // Then
        verify(userRepository, times(1)).findAllByCompanyId(companyDto.getId());
        verify(companyRepository, times(1)).deleteById(companyDto.getId());
    }

    @SuppressWarnings("null")
    @Test
    void shouldReturnVoidWhenDeleteCompanyWithNullId() {
        // Given
        // When
        // Then
        companyService.delete(null);
        verify(userRepository, times(0)).findAllByCompanyId(null);
        verify(companyRepository, times(0)).deleteById(null);
    }

    @Test
    void shouldThrowInvalidEntityExceptionWhenDeleteCompanyHavingUsers() {
        // Given
        Company company = CompanyDto.toEntity(companyDto);
        UserDto userDto = UserDto.builder()
            .id(1)
            .firstName("Test")
            .lastName("User")
            .email("")
            .password("password")
            .numTel("123456789")
            .address(AddressDto.builder()
                .address1("123 Rue")
                .city("Paris")
                .zip("75000")
                .state("Ile de France")
                .country("France")
                .build())
            .idCompany(company.getId())
        .build();
        User user = UserDto.toEntity(userDto, companyRepository);
        
        // When
        when(userRepository.findAllByCompanyId(companyDto.getId())).thenReturn(List.of(user));
        doNothing().when(companyRepository).deleteById(companyDto.getId());
        

        // Then
        assertThrows(RuntimeException.class, () -> companyService.delete(companyDto.getId()), "Impossible de supprimer l'entreprise avec l'id = " + companyDto.getId());
        verify(userRepository, times(1)).findAllByCompanyId(companyDto.getId());
        verify(companyRepository, times(0)).deleteById(companyDto.getId());
    }
}
