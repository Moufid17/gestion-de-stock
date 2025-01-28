package com.tmdigital.gestiondestock.controller.api;

import com.flickr4java.flickr.FlickrException;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoApi {

  @PostMapping(value="/{id}/{title}/{context}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  Object savePhoto(
    @Parameter(description = "file", 
      content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))) 
      @RequestPart("file") MultipartFile photo, 
    @Parameter(description = "Object identifier") @PathVariable("id") Integer id,
    @Parameter(description = "File name") @PathVariable("title") String title,
    @Parameter(description = "File context") @PathVariable("context") String context
  ) throws IOException, FlickrException;

}
