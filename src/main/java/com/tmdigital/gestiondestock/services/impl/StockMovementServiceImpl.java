package com.tmdigital.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.StockMovementDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.model.Article;
import com.tmdigital.gestiondestock.model.StockMovementType;
import com.tmdigital.gestiondestock.model.StockMovement;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.StockMovementRepository;
import com.tmdigital.gestiondestock.services.StockMovementService;
import com.tmdigital.gestiondestock.validator.StockMovementValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockMovementServiceImpl implements StockMovementService{

    private StockMovementRepository stockMovementRepository;
    private ArticleRepository articlRepository;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository, ArticleRepository articlRepository) {
        this.articlRepository = articlRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public BigDecimal realStockArticle(Integer idArticle) {

        if (idArticle == null) {
            log.error("(realStockArticle) Article ID is null");
            throw new InvalidOperationException("Article ID is required", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        Optional<Article> article = articlRepository.findById(idArticle);
        if (!article.isPresent()) {
            log.error("Article with id {} was not found in the DB", idArticle);
            throw new InvalidOperationException("Article with id " + idArticle + " was not found in the DB", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<StockMovement> stockMovements = stockMovementRepository.findAllByArticleId(idArticle);
        
        if (stockMovements == null || stockMovements.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return stockMovementRepository.stockReel(idArticle);
    }

    @Override
    public List<StockMovementDto> stockMovementArticle(Integer id) {
        List<StockMovement> stockMovements = stockMovementRepository.findAllByArticleId(id);
        if (stockMovements == null || stockMovements.isEmpty()) {
            return new ArrayList<>();
        }

        return stockMovements.stream()
            .map(StockMovementDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void stockIn(StockMovementDto dto) {
        stockInMvt(dto, StockMovementType.INPUT);
    }
    
    @Override
    public void stockOut(StockMovementDto dto) {
        stockOutMvt(dto, StockMovementType.OUTPUT);
    }
    
    @Override
    public void updateIn(StockMovementDto dto) {
        stockInMvt(dto, StockMovementType.POS_UPDATE);
    }
    
    @Override
    public void updateOut(StockMovementDto dto) {
        stockOutMvt(dto, StockMovementType.NEG_UPDATE);
    }

    @Override
    public StockMovementDto findById(Integer id) {
        if (id == null) {
            log.error("Trying to find stockMovement by null id");
            throw new InvalidOperationException("The id is required", ErrorCodes.STOCK_MOVEMENT_NOT_FOUND);
        }
        Optional<StockMovement> stockMovement = stockMovementRepository.findById(id);

        if (!stockMovement.isPresent()) {
            throw   new InvalidEntityException("No stockMovement found with id = " + id, ErrorCodes.STOCK_MOVEMENT_NOT_FOUND);
        }

        return StockMovementDto.fromEntity(stockMovement.get());
    }

    @Override
    public StockMovementDto findByOrderIdAndOrderlineId(Integer orderId, Integer orderlineId) {
        Optional<StockMovement> stockMvt = stockMovementRepository.findByOrderIdAndOrderlineId(orderId, orderlineId);

        if (!stockMvt.isPresent()) {
            log.error("No stockMovement found with orderId = {} and orderlineId = {}", orderId, orderlineId);
            throw new InvalidEntityException("No stockMovement found with orderId = " + orderId + " and orderlineId = " + orderlineId, ErrorCodes.STOCK_MOVEMENT_NOT_FOUND);
        }
        
        return StockMovementDto.fromEntity(stockMvt.get());
    }  

    @Override
    public List<StockMovementDto> findAllByTypeMvt(String typeMvt) {
        return stockMovementRepository.findAllByTypeMvt(typeMvt).stream()
                .map(StockMovementDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockMovementDto> findAllBysourceMvt(String sourceMvt) {
        return stockMovementRepository.findAllBysourceMvt(sourceMvt).stream()
            .map(StockMovementDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<StockMovementDto> findAllByCompanyId(Integer id) {
        return stockMovementRepository.findAllByIdCompany(id).stream()
            .map(StockMovementDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<StockMovementDto> findAll() {
        return stockMovementRepository.findAll().stream()
            .map(StockMovementDto::fromEntity)
            .collect(Collectors.toList());
    } 

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Trying to delete stockMovement by null id");
            throw new InvalidOperationException("The id is required", ErrorCodes.STOCK_MOVEMENT_NOT_FOUND);
        }

        stockMovementRepository.deleteById(id);
    }

    @Override
    public void deleteAllByOrderId(Integer orderId) {
        if (null == orderId) {
            log.error("Trying to delete stockMovement by null orderId");
            throw new InvalidOperationException("The order id is required", ErrorCodes.STOCK_MOVEMENT_NOT_FOUND);
        }

        stockMovementRepository.deleteAllByOrderId(orderId);
    }

    private void stockInMvt(StockMovementDto dto, StockMovementType typeMvt) {
        List<String> errors = StockMovementValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("StockMovement is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.STOCK_MOVEMENT_NOT_VALID, errors);
        }

        Optional<Article> article = articlRepository.findById(dto.getArticle().getId());
        if (!article.isPresent()) {
            log.warn("Trying to create stockMovement for non existing article {}", dto.getArticle().getId());
            throw new InvalidOperationException("Article with id " + dto.getArticle().getId() + " was not found in the DB", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        dto.setQty(null == dto.getQty() ? BigDecimal.ZERO : 
            BigDecimal.valueOf(
                Math.abs(dto.getQty().intValue())
            )
        );

        if (null != typeMvt) dto.setTypeMvt(typeMvt);

        stockMovementRepository.save(StockMovementDto.toEntity(dto));
    }

    private void stockOutMvt(StockMovementDto dto, StockMovementType typeMvt) {
        List<String> errors = StockMovementValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("StockMovement is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.STOCK_MOVEMENT_NOT_VALID, errors);
        }

        Optional<Article> article = articlRepository.findById(dto.getArticle().getId());
        if (!article.isPresent()) {
            log.warn("Trying to create stockMovement for non existing article {}", dto.getArticle().getId());
            throw new InvalidOperationException("Article with id " + dto.getArticle().getId() + " was not found in the DB", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        dto.setQty(null == dto.getQty() ? BigDecimal.ZERO : 
            BigDecimal.valueOf(
                Math.abs(dto.getQty().intValue()) * -1
            )
        );

        dto.setTypeMvt(null == dto.getTypeMvt() ? typeMvt : dto.getTypeMvt());

        stockMovementRepository.save(StockMovementDto.toEntity(dto));
    }
}
