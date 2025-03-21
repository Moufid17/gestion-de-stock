package com.tmdigital.gestiondestock.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.dto.OrderLineClientDto;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;
import com.tmdigital.gestiondestock.dto.SalesLineDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.NotFoundEntityException;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.model.Article;
import com.tmdigital.gestiondestock.model.Category;
import com.tmdigital.gestiondestock.model.OrderLineClient;
import com.tmdigital.gestiondestock.model.OrderLineSupplier;
import com.tmdigital.gestiondestock.model.SalesLine;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.CategoryRepository;
import com.tmdigital.gestiondestock.repository.OrderLineClientRepository;
import com.tmdigital.gestiondestock.repository.OrderLineSupplierRepository;
import com.tmdigital.gestiondestock.repository.SalesLineRepository;
import com.tmdigital.gestiondestock.services.ArticleService;
import com.tmdigital.gestiondestock.validator.ArticleValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private SalesLineRepository salesLineRepository;
    private OrderLineClientRepository orderLineClientRepository;
    private OrderLineSupplierRepository orderLineSupplierRepository;
    private CategoryRepository categoryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, SalesLineRepository salesLineRepository, OrderLineClientRepository orderLineClientRepository, OrderLineSupplierRepository orderLineSupplierRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.salesLineRepository = salesLineRepository;
        this.orderLineClientRepository = orderLineClientRepository;
        this.orderLineSupplierRepository = orderLineSupplierRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        // CHECK IF THE CATEGORY EXISTS
        Optional<Category> category = categoryRepository.findById(dto.getCategory().getId());

        if (!category.isPresent()) {
            log.warn("La category avec l'ID {} n'existe pas.", dto.getCategory().getId());
            throw new InvalidEntityException("La category avec l'ID " + dto.getCategory().getId() + " n'existe pas.", ErrorCodes.CATEGORY_NOT_FOUND);
        }

        return ArticleDto.fromEntity(
            articleRepository.save(
                    ArticleDto.toEntity(dto)
                )
            );
    };

    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        Optional<Article> articleRetreived = articleRepository.findById(id);

        if (articleRetreived.isEmpty()) {
            log.error("L'article avec l'id = {} n'existe pas.", id);
            throw new NotFoundEntityException("L'article avec l'id = " + id + ", n'existe pas.", ErrorCodes.ARTICLE_NOT_FOUND);
        }
                
        return ArticleDto.fromEntity(articleRetreived.get());
    };

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (codeArticle == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        Optional<Article> articleRetreived = articleRepository.findArticleByCode(codeArticle);

        if (articleRetreived.isEmpty()) {
            log.error("L'article avec le code = {}, n'existe pas.", codeArticle);
            throw new NotFoundEntityException("L'article avec le code = " + codeArticle + ", n'existe pas.", ErrorCodes.ARTICLE_NOT_FOUND);
        }
                
        return ArticleDto.fromEntity(articleRetreived.get());
    };

    @Override
    public List<ArticleDto> findAll() {
        List<Article> allArticles = articleRepository.findAll();

        if (allArticles.isEmpty()) {
            return new ArrayList<>();
        }

        return allArticles.stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public List<ArticleDto> findAllArticleByCompany(Integer idCompany) {
        if (idCompany == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant est nul", ErrorCodes.ARTICLE_NOT_VALID);
        }

        List<Article> allArticlesByCompany = articleRepository.findAllByCompany(idCompany);

        if (allArticlesByCompany.isEmpty()) {
            return new ArrayList<>();
        }

        return allArticlesByCompany.stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public List<ArticleDto> findAllArticleByCategory(Integer idCategory) {
        if (idCategory == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant est nul", ErrorCodes.ARTICLE_NOT_VALID);
        }

        List<Article> allArticlesByCategory = articleRepository.findAllByCategoryId(idCategory);

        if (allArticlesByCategory.isEmpty()) {
            return new ArrayList<>();
        }

        return allArticlesByCategory.stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };

    @Override
    public List<SalesLineDto> findAllSalesLineByArticleId(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant est nul", ErrorCodes.ARTICLE_NOT_VALID);
        }

        List<SalesLine> allArticlesByArticleId = salesLineRepository.findAllByArticleId(idArticle);

        if (allArticlesByArticleId.isEmpty()) {
            return new ArrayList<>();
        }

        return allArticlesByArticleId.stream()
                .map(SalesLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderLineClientDto> findAllInOrderLineClientByArticleId(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant est nul", ErrorCodes.ARTICLE_NOT_VALID);
        }

        List<OrderLineClient> allArticlesInOrderLineClientByArticleId = orderLineClientRepository.findAllByArticleId(idArticle);

        if (allArticlesInOrderLineClientByArticleId.isEmpty()) {
            return new ArrayList<>();
        }

        return allArticlesInOrderLineClientByArticleId.stream()
                .map(OrderLineClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderLineSupplierDto> findAllOrderLineSupplierByArticleId(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant est nul", ErrorCodes.ARTICLE_NOT_VALID);
        }

        List<OrderLineSupplier> allArticlesOrderLineSupplierByArticleId = orderLineSupplierRepository.findAllByArticleId(idArticle);

        if (allArticlesOrderLineSupplierByArticleId.isEmpty()) {
            return new ArrayList<>();
        }

        return allArticlesOrderLineSupplierByArticleId.stream()
                .map(OrderLineSupplierDto::fromEntity)
                .collect(Collectors.toList());
    };

    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            throw new InvalidOperationException("L'identifiant est nul", ErrorCodes.ARTICLE_NOT_VALID);
        }

        List<SalesLine> salesLines = salesLineRepository.findAllByArticleId(id);

        if (!salesLines.isEmpty()) {
            throw new InvalidEntityException("Impossible de supprimer l'article avec l'id = " + id + ", car il est lié à une ou plusieurs commande.", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<OrderLineClient> orderLineClients = orderLineClientRepository.findAllByArticleId(id);

        if (!orderLineClients.isEmpty()) {
            throw new InvalidEntityException("Impossible de supprimer l'article avec l'id = " + id + ", car il est lié à une ou plusieurs commande.", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<OrderLineSupplier> orderLineSuppliers = orderLineSupplierRepository.findAllByArticleId(id);

        if (orderLineSuppliers.isEmpty()) {
            log.error("Impossible de supprimer l'article avec l'id = " + id + ", car il est lié à une ou plusieurs commande.");
            throw new InvalidEntityException("Impossible de supprimer l'article avec l'id = " + id + ", car il est lié à une ou plusieurs commande.", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        articleRepository.deleteById(id);
    }
}
