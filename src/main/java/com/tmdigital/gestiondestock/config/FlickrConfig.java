package com.tmdigital.gestiondestock.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.FlickrApi.FlickrPerm;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// @Configuration
public class FlickrConfig {

  @Value("${flickr.apiKey}")
  private String apiKey;

  @Value("${flickr.apiSecret}")
  private String apiSecret;

    // @Bean
    public Flickr getFlickr() throws InterruptedException, ExecutionException, IOException, FlickrException {
    Flickr flickr = new
       Flickr(apiKey, apiSecret, new REST());

   OAuth10aService service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(FlickrApi.instance(FlickrPerm.WRITE));

   final OAuth1RequestToken request = service.getRequestToken();
   final String authUrl = service.getAuthorizationUrl(request);
   
   String authVerifier;
   System.out.println("Paste it here -->> ");
   System.out.println(authUrl);
   Scanner scanner = new Scanner(System.in);
   authVerifier = scanner.nextLine();

   OAuth1AccessToken accessToken = service.getAccessToken(request, authVerifier);

   System.out.println(accessToken.getToken());
   System.out.println(accessToken.getTokenSecret());

   Auth auth = flickr.getAuthInterface().checkToken(accessToken);

   System.out.println("---------------------------");
   System.out.println(auth.getToken());
   System.out.println(auth.getTokenSecret());
   scanner.close();

   return flickr;
 }
}
