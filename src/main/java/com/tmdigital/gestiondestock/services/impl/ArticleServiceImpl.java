package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.NotFoundEntityException;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.services.ArticleService;
import com.tmdigital.gestiondestock.validator.ArticleValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository ArticleRepository;

    public ArticleServiceImpl(ArticleRepository ArticleRepository) {
        this.ArticleRepository = ArticleRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article n'est pas valide {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        return ArticleDto.fromEntity(
                ArticleRepository.save(
                    ArticleDto.toEntity(dto)
                )
            );
    };

    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            throw new NotFoundEntityException("L'id de l'article est nulle");
        }

        return Optional.of(ArticleDto.fromEntity(
                    ArticleRepository.findById(id).get()
                )).orElseThrow(
                        () -> new NotFoundEntityException("L'article avec l'id = " + id + ", n'existe pas.", ErrorCodes.ARTICLE_NOT_FOUND)
                    );
    };

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (codeArticle == null) {
            throw new NotFoundEntityException("Le code de l'article est nulle");
        }

        return Optional.of(ArticleDto.fromEntity(
                    ArticleRepository.findArticleByCode(codeArticle)
                )).orElseThrow(
                        () -> new NotFoundEntityException("L'article avec le code = " + codeArticle + ", n'existe pas.", ErrorCodes.ARTICLE_NOT_FOUND)
                    );
    };

    @Override
    public List<ArticleDto> findAll() {
        return ArticleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public List<ArticleDto> findAllArticleByCompany(Integer idCompany) {
        if (idCompany == null) {
            throw new NotFoundEntityException("L'id de l'entreprise est nulle");
        }
        return ArticleRepository.findAllByCompanyId(idCompany).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public List<ArticleDto> findAllArticleByCategory(Integer idCategory) {
        if (idCategory == null) {
            throw new NotFoundEntityException("L'id' de la catégorie de l'article est nulle");
        }
        return ArticleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public void delete(Integer id) {
        if (id == null) {
            throw new NotFoundEntityException("L'id de l'article est nulle");
        }
        ArticleRepository.deleteById(id);
    };
}
