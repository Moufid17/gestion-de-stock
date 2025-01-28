package com.tmdigital.gestiondestock.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;
import com.tmdigital.gestiondestock.controller.api.PhotoApi;
import com.tmdigital.gestiondestock.services.Strategy.StrategyPhotoContext;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController implements PhotoApi {

    private StrategyPhotoContext strategyPhotoContext;

    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(MultipartFile photo, Integer id, String title, String context) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }

}
