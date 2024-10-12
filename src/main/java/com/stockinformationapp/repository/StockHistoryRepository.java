package com.stockinformationapp.repository;

import com.stockinformationapp.entity.StockHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistoryEntity, UUID> {
    List<StockHistoryEntity> findByStockName(String symbol);
    @Query("SELECT e FROM StockHistoryEntity e WHERE e.stockName = :symbol AND e.date BETWEEN :startDate AND :endDate")
    List<StockHistoryEntity> findAllByDateBetween(@Param("symbol") String symbol, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
