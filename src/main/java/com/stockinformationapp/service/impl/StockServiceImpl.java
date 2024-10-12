package com.stockinformationapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockinformationapp.core.response.BaseResponse;
import com.stockinformationapp.core.response.ResponseCodeEnum;
import com.stockinformationapp.core.response.ResponseMessageEnum;
import com.stockinformationapp.entity.StockEntity;
import com.stockinformationapp.entity.StockHistoryEntity;
import com.stockinformationapp.repository.StockRepository;
import com.stockinformationapp.service.StockService;
import com.stockinformationapp.util.StockOperationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final ObjectMapper mapper;

    @Override
    public BaseResponse<StockEntity> create(StockEntity stockEntity) {
        log.debug("Stock CREATE Process Started.");
        BaseResponse<StockEntity> baseResponse = new BaseResponse<>();

        try{
            StockEntity result = stockRepository.save(stockEntity);

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.addList(result);

            log.debug("Stock CREATE Process Completed.");
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock CREATE Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockEntity> update(StockEntity stockEntity) {
        log.debug(String.format("Stock UPDATE Process Started. Stock Id: %s", stockEntity.getId()));
        BaseResponse<StockEntity> baseResponse = new BaseResponse<>();

        try{
            StockEntity result = stockRepository.save(stockEntity);

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.addList(result);

            log.debug(String.format("Stock UPDATE Process Completed. Stock Id: %s", result.getId()));
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock UPDATE Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockEntity> get(String id) {
        log.debug(String.format("Stock GET Process Started. Stock Id: %s", id));
        BaseResponse<StockEntity> baseResponse = new BaseResponse<>();

        try{
            Optional<StockEntity> stockHistoryEntity = stockRepository.findById(UUID.fromString(id));

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.addList(stockHistoryEntity.orElse(null));

            log.debug(String.format("Stock GET Process Completed.Response: %s", mapper.writeValueAsString(stockHistoryEntity)));
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock GET Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockEntity> getAll() {
        log.debug("Stock GET-All Process Started.");
        BaseResponse<StockEntity> baseResponse = new BaseResponse<>();

        try{
            List<StockEntity> stockHistoryEntity = stockRepository.findAll();

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.setTotalDataCount(stockHistoryEntity.size());
            baseResponse.setReturnData(stockHistoryEntity);

            log.debug("Stock GET-All Process Completed.");
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock GET-ALL Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }
}
