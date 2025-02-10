package com.tmdigital.gestiondestock.services.Strategy;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.flickr4java.flickr.FlickrException;
import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.services.CompanyService;
import com.tmdigital.gestiondestock.services.FlickrService;

import lombok.extern.slf4j.Slf4j;

@Service("companyStrategy")
@Slf4j
public class SaveCompanyPhoto implements Strategy<CompanyDto> {

    private CompanyService companyService;
    private FlickrService flickrService;

    public SaveCompanyPhoto(CompanyService companyService, FlickrService flickrService) {
        this.companyService = companyService;
        this.flickrService = flickrService;
    }
    
    @Override
    public CompanyDto savePhoto(Integer idCompany, InputStream photo, String title) throws FlickrException {

        CompanyDto company = companyService.findById(idCompany);
        String idPhoto = "";
        try {
            idPhoto = flickrService.savePhoto(photo, title);
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de photo de l'entreprise. flickrService = ", e);
            e.printStackTrace();
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        log.info("Photo enregistrée avec succès ===> {}", idPhoto);
        company.setPhoto(idPhoto);
        return companyService.save(company);
    }

}
