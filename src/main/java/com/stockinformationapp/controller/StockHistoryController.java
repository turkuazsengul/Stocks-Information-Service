package com.stockinformationapp.controller;

import com.stockinformationapp.core.aop.ValidateRequest;
import com.stockinformationapp.core.response.BaseResponse;
import com.stockinformationapp.core.response.ResponseCodeEnum;
import com.stockinformationapp.core.response.ResponseMessageEnum;
import com.stockinformationapp.entity.StockHistoryEntity;
import com.stockinformationapp.model.StockHistoryDto;
import com.stockinformationapp.service.StockHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stockHistory")
public class StockHistoryController {
    private static final String MESSAGE_OF_STOCK_DATA_START = "Stock Historical Data Getting Process Started";

    private final StockHistoryService stockHistoryService;

    @PostMapping("/create")
    @ValidateRequest
    public ResponseEntity<BaseResponse<StockHistoryEntity>> create(@RequestBody StockHistoryDto stockHistoryDto){
        BaseResponse<StockHistoryEntity> baseResponse = stockHistoryService.create(stockHistoryDto);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PatchMapping("/update")
    @ValidateRequest
    public ResponseEntity<BaseResponse<StockHistoryEntity>> update(@RequestBody StockHistoryDto stockHistoryDto){
        BaseResponse<StockHistoryEntity> baseResponse = stockHistoryService.update(stockHistoryDto);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<BaseResponse<StockHistoryEntity>> get(@RequestParam String id){
        BaseResponse<StockHistoryEntity> baseResponse = stockHistoryService.getById(id);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/getBySymbol")
    public ResponseEntity<BaseResponse<StockHistoryEntity>> getBySymbol(@RequestParam String symbol){
        BaseResponse<StockHistoryEntity> baseResponse = stockHistoryService.getBySymbol(symbol);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/getBySymbolAndDateRange")
    public ResponseEntity<BaseResponse<StockHistoryEntity>> getBySymbolAndDateRange(@RequestParam String symbol,
                                                                                    @RequestParam String beginDate,
                                                                                    @RequestParam String endDate){
        BaseResponse<StockHistoryEntity> baseResponse = stockHistoryService.getBySymbolAndDateRange(symbol, beginDate, endDate);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/startStockDataPolling")
    public ResponseEntity<BaseResponse<String>> startStockDataPolling(@RequestParam String symbol, @RequestParam int totalYear){
        BaseResponse<String> baseResponse = new BaseResponse<>();

        stockHistoryService.startStockDataPolling(symbol, totalYear);

        baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
        baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
        baseResponse.addList(MESSAGE_OF_STOCK_DATA_START);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
