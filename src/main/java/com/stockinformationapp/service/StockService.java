package com.stockinformationapp.service;

import com.stockinformationapp.core.response.BaseResponse;
import com.stockinformationapp.entity.StockEntity;

public interface StockService {
    BaseResponse<StockEntity> create(StockEntity stockEntity);
    BaseResponse<StockEntity> update(StockEntity stockEntity);
    BaseResponse<StockEntity> get(String id);
    BaseResponse<StockEntity> getAll();
}
