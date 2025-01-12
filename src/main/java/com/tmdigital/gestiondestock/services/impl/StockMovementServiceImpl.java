package com.tmdigital.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.StockMovementDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.repository.StockMovementRepository;
import com.tmdigital.gestiondestock.services.StockMovementService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockMovementServiceImpl implements StockMovementService{

    private StockMovementRepository stockMovementRepository;

    public StockMovementServiceImpl(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public StockMovementDto findById(Integer id) {
        if (id == null) {
            log.error("Trying to find stockMovement by null id");
            return null;
        }

        return stockMovementRepository.findById(id)
                .map(StockMovementDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException(
                        "No stockMovement found with id = " + id, ErrorCodes.STOCK_MOVEMENT_NOT_FOUND)
                );
    }

    @Override
    public List<StockMovementDto> findAllByArticleId(Integer id) {
       return stockMovementRepository.findAllByArticleId(id).stream()
                .map(StockMovementDto::fromEntity)
                .collect(Collectors.toList());
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

    // [ ] TODO: Implement stockIn method
    public void stockIn(StockMovementDto stockMovement) {
        return;
    }

    // [ ] TODO: Implement stockOut method
    public void stockOut(StockMovementDto stockMovement) {
        if (stockMovement == null) {
            log.warn("Trying to stock out null stockMovement");
            throw new InvalidEntityException("Trying to stock out null stockMovement", ErrorCodes.STOCK_MOVEMENT_NOT_VALID);
        }

        if (stockMovement.getArticle() == null || stockMovement.getArticle().getId() == null) {
            log.warn("Trying to stock out stockMovement with no article");
            throw new InvalidEntityException("Trying to stock out stockMovement with no article", ErrorCodes.STOCK_MOVEMENT_NOT_VALID);
        }

        if (stockMovement.getQty() == null || stockMovement.getQty().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Trying to stock out stockMovement with invalid qty: {}", stockMovement.getQty());
            throw new InvalidEntityException("Trying to stock out stockMovement with invalid qty: " + stockMovement.getQty(), ErrorCodes.STOCK_MOVEMENT_NOT_VALID);
        }

        BigDecimal stockReel = stockMovementRepository.stockReel(stockMovement.getArticle().getId());

        if (stockReel == null || stockReel.compareTo(stockMovement.getQty()) < 0) {
            log.warn("Trying to stock out stockMovement with qty greater than stock reel: {} > {}", stockMovement.getQty(), stockReel);
            throw new InvalidEntityException("Trying to stock out stockMovement with qty greater than stock reel: " + stockMovement.getQty() + " > " + stockReel, ErrorCodes.STOCK_MOVEMENT_NOT_VALID);
        }

        stockMovementRepository.save(StockMovementDto.toEntity(stockMovement));

    }

}
