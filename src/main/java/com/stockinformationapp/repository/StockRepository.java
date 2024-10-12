package com.stockinformationapp.repository;

import com.stockinformationapp.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<StockEntity, UUID> {
}
