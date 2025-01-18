package com.tmdigital.gestiondestock.controller.api;

import com.flickr4java.flickr.FlickrException;
import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoApi {

  @PostMapping("/{id}/{title}/{context}")
  Object savePhoto(
        @PathVariable("context") String context, 
        @PathVariable("id") Integer id,
        @RequestPart("file") MultipartFile photo, 
        @PathVariable("title") String title
    ) throws IOException, FlickrException;

}
