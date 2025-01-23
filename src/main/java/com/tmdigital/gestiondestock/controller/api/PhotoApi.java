package com.tmdigital.gestiondestock.controller.api;

import com.flickr4java.flickr.FlickrException;

import io.swagger.v3.oas.annotations.Parameter;

import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoApi {

  @PostMapping("/{id}/{title}/{context}")
  Object savePhoto(
    @Parameter(description = "file") @RequestPart("file") MultipartFile photo, 
    @Parameter(description = "Object identifier") @PathVariable("id") Integer id,
    @Parameter(description = "File name") @PathVariable("title") String title,
    @Parameter(description = "File context") @PathVariable("context") String context
  ) throws IOException, FlickrException;

}
