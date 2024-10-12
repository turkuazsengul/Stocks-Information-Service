package com.stockinformationapp.service;

import com.stockinformationapp.core.response.BaseResponse;
import com.stockinformationapp.entity.StockHistoryEntity;
import com.stockinformationapp.model.StockHistoryDto;

public interface StockHistoryService {
    BaseResponse<StockHistoryEntity> create(StockHistoryDto stockHistoryDto);
    BaseResponse<StockHistoryEntity> update(StockHistoryDto stockHistoryDto);
    BaseResponse<StockHistoryEntity> getById(String id);
    BaseResponse<StockHistoryEntity> getBySymbol(String symbol);
    BaseResponse<StockHistoryEntity> getBySymbolAndDateRange(String symbol, String beginDate, String endDate);
    void startStockDataPolling(String symbol, int totalYear);

}
