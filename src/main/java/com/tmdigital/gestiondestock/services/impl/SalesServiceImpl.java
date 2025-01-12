package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.SalesDto;
import com.tmdigital.gestiondestock.dto.SalesLineDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.Article;
import com.tmdigital.gestiondestock.model.Sales;
import com.tmdigital.gestiondestock.model.SalesLine;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.SalesLineRepository;
import com.tmdigital.gestiondestock.repository.SalesRepository;
import com.tmdigital.gestiondestock.services.SalesService;
import com.tmdigital.gestiondestock.validator.SalesLineValidator;
import com.tmdigital.gestiondestock.validator.SalesValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {

    private SalesRepository salesRepository;
    private SalesLineRepository salesLineRepository;
    private ArticleRepository articleRepository;

    public SalesServiceImpl(SalesRepository salesRepository, SalesLineRepository salesLineRepository, ArticleRepository articleRepository) {
        this.salesRepository = salesRepository;
        this.salesLineRepository = salesLineRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public SalesDto save(SalesDto dto) {
        List<String> errors = SalesValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande vente n'est pas valide", ErrorCodes.SALES_NOT_VALID, errors);
        }

        // Check if the order lines are valid
        if (dto.getSalesLines() ==  null) {
            log.warn("Impossible d'enregister une commande avec des lignes de commandes nulles.");
            throw new InvalidEntityException("Impossible d'enregister une commande avec des lignes de commandes nulles.", ErrorCodes.ORDER_CLIENT_NOT_VALID, errors);
        }

        // Check if each order line are valid abd if the article exists
        List<String> orderLineErrors = dto.getSalesLines().stream()
            .map(orderLine -> {
                List<String> errorsOrderLine = SalesLineValidator.validate(orderLine);
                if (errorsOrderLine.isEmpty()) {
                    Optional<Article> article = articleRepository.findById(orderLine.getArticle().getId());
                    if (!article.isPresent()) {
                        return "L'article avec l'identifiant " + orderLine.getArticle().getId() + " n'existe pas";
                    } else {
                        return null;
                    }
                } else {
                    return "Impossible d'enregister une commande avec une ligne de commande non valide";
                }
            })
            .filter(error -> error != null)
            .distinct()
            .collect(Collectors.toList());

        if (!orderLineErrors.isEmpty()) {
            log.warn("Une ligne de commande n'est pas valide ou un L'article n'existe pas.");
            throw new InvalidEntityException("Une ligne de commande n'est pas valide ou un L'article n'existe pas.", ErrorCodes.ORDER_CLIENT_NOT_VALID, orderLineErrors);
        }

        // Save the order
        Sales savedSales = salesRepository.save(SalesDto.toEntity(dto));

        dto.getSalesLines().forEach(orderLine -> {
            SalesLine salesLine = SalesLineDto.toEntity(orderLine);
            salesLine.setIdCompany(dto.getIdCompany());
            salesLineRepository.save(salesLine);

            // [ ] Mise à jour le Mvt de stock en sortie

        });
        
        return SalesDto.fromEntity(savedSales);
    }

    @Override
    public SalesDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesRepository.findById(id)
            .map(SalesDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucune commande de vente n'a été trouvée avec l'identifiant " + id));
    }

    @Override
    public SalesDto findByCode(String code) {
        if (code == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesRepository.findSalesByCode(code).map(SalesDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("Aucune commande de vente n'a été trouvée avec le code " + code));
    }

    @Override
    public List<SalesDto> findAllByCompany(Integer idCompany) {
        if (idCompany == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesRepository.findAllByIdCompany(idCompany).stream()
            .map(SalesDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<SalesDto> findAll() {
        return salesRepository.findAll().stream()
            .map(SalesDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        // check if the order has order lines
        if (!salesLineRepository.findAllBySalesId(id).isEmpty()) {
            log.error("Impossible de supprimer une commande avec des lignes de commandes");
            throw new InvalidEntityException("Impossible de supprimer une commande avec des lignes de commandes", ErrorCodes.SALES_IS_ALREADY_USED);
        }

        salesRepository.deleteById(id);
    }

}
