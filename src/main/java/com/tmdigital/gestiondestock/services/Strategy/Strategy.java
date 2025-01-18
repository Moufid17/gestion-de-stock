package com.tmdigital.gestiondestock.services.Strategy;

import java.io.InputStream;

import com.flickr4java.flickr.FlickrException;

public interface Strategy<T> {

    T savePhoto(Integer idObjet, InputStream photo, String titlePhoto) throws FlickrException;
}
