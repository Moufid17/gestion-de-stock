package com.tmdigital.gestiondestock.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.tmdigital.gestiondestock.services.FlickrService;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {
    
    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.oauthAccessToken}")
    private String oauthAccessToken;

    @Value("${flickr.oauthAccessTokenSecret}")
    private String oauthAccessTokenSecret;

    private Flickr flickr;

    @Override
    public String savePhoto(InputStream photo, String title) throws Exception {
        getFlickrInstance();
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

    private void getFlickrInstance() throws InterruptedException, ExecutionException, IOException, FlickrException {
        flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.WRITE); // Permissions: READ, WRITE, DELETE
        auth.setToken(oauthAccessToken);
        auth.setTokenSecret(oauthAccessTokenSecret);
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
    }

    
}
