package com.tmdigital.gestiondestock.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public @ResponseBody ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    };

    @Override
    public @ResponseBody ArticleDto get(Integer id) {
        return articleService.findById(id);
    };

    @Override
    public List<ArticleDto> getAll() {
        return articleService.findAll();
    };

    @Override
    public @ResponseBody void delete(Integer id) {
        articleService.delete(id);
    }
    
}
