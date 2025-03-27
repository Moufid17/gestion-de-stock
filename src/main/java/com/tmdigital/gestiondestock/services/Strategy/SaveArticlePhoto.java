package com.tmdigital.gestiondestock.services.Strategy;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.flickr4java.flickr.FlickrException;
import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.services.ArticleService;
import com.tmdigital.gestiondestock.services.FlickrService;

@Service
@Qualifier("articleStrategy")
public class SaveArticlePhoto implements Strategy<ArticleDto> {

    private ArticleService articleService;
    private FlickrService flickrService;

    public SaveArticlePhoto(ArticleService articleService, FlickrService flickrService) {
        this.articleService = articleService;
        this.flickrService = flickrService;
    }
    
    @Override
    public ArticleDto savePhoto(Integer idArticle, InputStream photo, String title) throws FlickrException {

        ArticleDto article = articleService.findById(idArticle);
        String idPhoto = "";
        try {
            idPhoto = flickrService.savePhoto(photo, title);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        article.setPhoto(idPhoto);
        return articleService.save(article);
    }

}
