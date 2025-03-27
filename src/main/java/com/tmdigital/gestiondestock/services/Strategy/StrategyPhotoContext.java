package com.tmdigital.gestiondestock.services.Strategy;

import java.io.InputStream;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import com.flickr4java.flickr.FlickrException;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;

import lombok.Setter;

@Service
public class StrategyPhotoContext {

    private Strategy<?> strategy;
    private BeanFactory beanFactory;

    @Setter
    private String beanContext;

    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String beanContext, Integer idObjet, InputStream photo, String titlePhoto) throws FlickrException {

        final String beanName = beanContext + "Strategy";

        switch (beanContext) {
            case "article":
                strategy = beanFactory.getBean(beanName,SaveArticlePhoto.class);
                break;
            case "company":
                strategy = beanFactory.getBean(beanName,SaveCompanyPhoto.class);
                break;
        
            default:
                throw new InvalidOperationException("Context inconnue pour la sauvegarde de photo", ErrorCodes.UNKNOWN_CONTEXT);
        }

        return strategy.savePhoto(idObjet, photo, titlePhoto);
    }
    
}
