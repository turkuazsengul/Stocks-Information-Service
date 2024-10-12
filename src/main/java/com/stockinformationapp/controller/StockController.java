package com.stockinformationapp.controller;

import com.stockinformationapp.core.aop.ValidateRequest;
import com.stockinformationapp.core.response.BaseResponse;
import com.stockinformationapp.entity.StockEntity;

import com.stockinformationapp.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
    private final StockService stockService;

    @PostMapping("/create")
    @ValidateRequest
    public ResponseEntity<BaseResponse<StockEntity>> create(@RequestBody StockEntity stockEntity){
        BaseResponse<StockEntity> baseResponse = stockService.create(stockEntity);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PatchMapping("/update")
    @ValidateRequest
    public ResponseEntity<BaseResponse<StockEntity>> update(@RequestBody StockEntity stockEntity){
        BaseResponse<StockEntity> baseResponse = stockService.update(stockEntity);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<BaseResponse<StockEntity>> get(@RequestParam String id){
        BaseResponse<StockEntity> baseResponse = stockService.get(id);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<StockEntity>> getAll(){
        BaseResponse<StockEntity> baseResponse = stockService.getAll();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
