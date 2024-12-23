package com.tmdigital.gestiondestock.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmdigital.gestiondestock.controller.api.ArticleApi;
import com.tmdigital.gestiondestock.services.ArticleService;
import com.tmdigital.gestiondestock.dto.ArticleDto;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController implements ArticleApi {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public @ResponseBody ArticleDto save(@RequestBody ArticleDto dto) {
        return articleService.save(dto);
    };

    @Override
    public @ResponseBody ArticleDto get(@PathVariable("articleId") Integer articleId) {
        return articleService.findById(articleId);
    };

    @Override
    public List<ArticleDto> getAll() {
        return articleService.findAll();
    };

    @Override
    public @ResponseBody void delete(@PathVariable("articleId") Integer articleId) {
        articleService.delete(articleId);
    }
    
}
