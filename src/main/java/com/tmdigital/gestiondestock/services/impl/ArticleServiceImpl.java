package com.tmdigital.gestiondestock.services.impl;

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
import com.tmdigital.gestiondestock.model.OrderLineClient;
import com.tmdigital.gestiondestock.model.OrderLineSupplier;
import com.tmdigital.gestiondestock.model.SalesLine;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
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

    public ArticleServiceImpl(ArticleRepository articleRepository, SalesLineRepository salesLineRepository, OrderLineClientRepository orderLineClientRepository, OrderLineSupplierRepository orderLineSupplierRepository) {
        this.articleRepository = articleRepository;
        this.salesLineRepository = salesLineRepository;
        this.orderLineClientRepository = orderLineClientRepository;
        this.orderLineSupplierRepository = orderLineSupplierRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
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

        return Optional.of(ArticleDto.fromEntity(
                    articleRepository.findById(id).get()
                )).orElseThrow(
                        () -> new NotFoundEntityException("L'article avec l'id = " + id + ", n'existe pas.", ErrorCodes.ARTICLE_NOT_FOUND)
                    );
    };

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (codeArticle == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return Optional.of(ArticleDto.fromEntity(
            articleRepository.findArticleByCode(codeArticle)
                )).orElseThrow(
                        () -> new NotFoundEntityException("L'article avec le code = " + codeArticle + ", n'existe pas.", ErrorCodes.ARTICLE_NOT_FOUND)
                    );
    };

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public List<ArticleDto> findAllArticleByCompany(Integer idCompany) {
        if (idCompany == null) {
            log.error("L'identifiant est nul");
            return null;
        }
        return articleRepository.findAllByCompany(idCompany).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };
    
    public List<ArticleDto> findAllArticleByCategory(Integer idCategory) {
        if (idCategory == null) {
            log.error("L'identifiant est nul");
            return null;
        }
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    };

    @Override
    public List<SalesLineDto> findAllSalesLineByArticleId(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesLineRepository.findAllByArticleId(idArticle).stream()
                .map(SalesLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderLineClientDto> findAllInOrderLineClientByArticleId(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderLineClientRepository.findAllByArticleId(idArticle).stream()
                .map(OrderLineClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderLineSupplierDto> findAllOrderLineSupplierByArticleId(Integer idArticle) {
        if (idArticle == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderLineSupplierRepository.findAllByArticleId(idArticle).stream()
                .map(OrderLineSupplierDto::fromEntity)
                .collect(Collectors.toList());
    };

    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
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

        if (!orderLineSuppliers.isEmpty()) {
            throw new InvalidEntityException("Impossible de supprimer l'article avec l'id = " + id + ", car il est lié à une ou plusieurs commande.", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        articleRepository.deleteById(id);
    }
}
