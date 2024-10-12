package com.stockinformationapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockinformationapp.core.selenium.SeleniumBaseConfiguration;
import com.stockinformationapp.core.response.BaseResponse;
import com.stockinformationapp.core.response.ResponseCodeEnum;
import com.stockinformationapp.core.response.ResponseMessageEnum;
import com.stockinformationapp.entity.StockHistoryEntity;
import com.stockinformationapp.model.StockHistoryDto;
import com.stockinformationapp.repository.StockHistoryRepository;
import com.stockinformationapp.service.StockHistoryService;
import com.stockinformationapp.util.StockOperationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;

import static com.stockinformationapp.util.StockOperationUtil.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class StockHistoryServiceImpl extends SeleniumBaseConfiguration implements StockHistoryService {
    private static final String WEB_TABLE_ELEMENT_XPATH = "/html/body/div[2]/main/section/section/section/article/div[1]/div[3]/table/tbody/tr";
    private static final String URL = "https://finance.yahoo.com/quote/%s/history/?period1=%s&period2=%s";

    private final StockHistoryRepository stockHistoryRepository;
    private final ObjectMapper mapper;


    @Override
    public BaseResponse<StockHistoryEntity> create(StockHistoryDto stockHistoryDto) {
        log.debug("Stock History CREATE Process Started.");
        BaseResponse<StockHistoryEntity> baseResponse = new BaseResponse<>();

        try{
            StockHistoryEntity stockHistoryEntity = convertStockDtoToStockHistory(stockHistoryDto);
            StockHistoryEntity result = stockHistoryRepository.save(stockHistoryEntity);

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.addList(result);

            log.debug("Stock History CREATE Process Completed.");
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock History CREATE Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockHistoryEntity> update(StockHistoryDto stockHistoryDto) {
        log.debug(String.format("Stock History UPDATE Process Started. Stock Id: %s", stockHistoryDto.getId()));
        BaseResponse<StockHistoryEntity> baseResponse = new BaseResponse<>();

        try{
            StockHistoryEntity stockHistoryEntity = StockOperationUtil.convertStockDtoToStockHistory(stockHistoryDto);
            StockHistoryEntity result = stockHistoryRepository.save(stockHistoryEntity);

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.addList(result);

            log.debug(String.format("Stock History UPDATE Process Completed. Stock Id: %s", stockHistoryEntity.getId()));
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock History UPDATE Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockHistoryEntity> getById(String id) {
        log.debug(String.format("Stock History GET Process Started. Stock Id: %s", id));
        BaseResponse<StockHistoryEntity> baseResponse = new BaseResponse<>();

        try{
            Optional<StockHistoryEntity> stockHistoryEntity = stockHistoryRepository.findById(UUID.fromString(id));

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.addList(stockHistoryEntity.orElse(null));

            log.debug(String.format("Stock History GET Process Completed.Response: %s", mapper.writeValueAsString(stockHistoryEntity)));
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock History GET Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockHistoryEntity> getBySymbol(String symbol) {
        log.debug(String.format("Stock History GET Process Started With Stock Symbol. Stock Id: %s", symbol));
        BaseResponse<StockHistoryEntity> baseResponse = new BaseResponse<>();

        try{
            List<StockHistoryEntity> stockHistoryEntityList = stockHistoryRepository.findByStockName(symbol);

            baseResponse.setReturnCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.SUCCESS.getMessage());
            baseResponse.setReturnData(stockHistoryEntityList);

            log.debug(String.format("Stock History GET Process Completed With Stock Symbol. StockHistory List Size: %s", stockHistoryEntityList.size()));
        }catch (Exception e){
            baseResponse.setReturnCode(ResponseCodeEnum.UNSUCCESSFUL.getCode());
            baseResponse.setReturnMessage(ResponseMessageEnum.UNSUCCESSFUL.getMessage());
            baseResponse.setDetailMessage(e.getMessage());

            log.error(String.format("Stock History GET Process Fail. Exception Message %s", e.getMessage()));
        }

        return baseResponse;
    }

    @Override
    public BaseResponse<StockHistoryEntity> getBySymbolAndDateRange(String symbol, String beginDate, String endDate) {
        return null;
    }

    @Async
    @Override
    @Transactional
    public void startStockDataPolling(String symbol, int totalYear) {
        WebDriver driver = getChromeDriver();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentDatePartStart = LocalDateTime.of(now.getYear() - totalYear, now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime currentDatePartFinal = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 19, 33, 39);

        long startPartUnix = currentDatePartStart.toEpochSecond(ZoneOffset.UTC);
        long finalPartUnix = currentDatePartFinal.toEpochSecond(ZoneOffset.UTC);

        String url = String.format(URL, symbol, startPartUnix, finalPartUnix);

        setStockInfoByWeb(driver, url, symbol);
    }

    private void setStockInfoByWeb(WebDriver driver, String url, String symbol) {
        CopyOnWriteArrayList<StockHistoryEntity> stockHistoryEntityList = new CopyOnWriteArrayList<>();

        try {
            long startTime = System.currentTimeMillis();
            log.debug(String.format("\nStarted Async Data Save Process With Selenium. Stock Symbol: %s\nDriver Start Url: %s", symbol, url));

            driver.get(url);

            List<WebElement> rows = driver.findElements(By.xpath(WEB_TABLE_ELEMENT_XPATH));

            log.debug(String.format("\nCompleted Web Element Getting. Total Element Siz: %s\nCell Setting To Entity Loop Started.", rows.size()));

            multiThreadScanList(stockHistoryEntityList, rows, symbol);

            driver.quit();

            log.debug("\nCell Setting To Entity Loop Completed.\nSelenium Web Driver Closed.\nStarted DB Create Operation.");

            stockHistoryRepository.saveAll(stockHistoryEntityList);

            log.debug("Completed DB Create Operation.");

            long endTime = System.currentTimeMillis();
            log.debug(String.format("Process Spent Time: %s", (endTime-startTime)/60000.0));

        } catch (Exception e) {
            driver.quit();
            throw new RuntimeException(e);
        }
    }

    private void multiThreadScanList(CopyOnWriteArrayList<StockHistoryEntity> stockHistoryEntityList, List<WebElement> rows, String symbol) {
        int threadCount = Runtime.getRuntime().availableProcessors();
        log.debug("Local Available Processor Count: " + threadCount);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        int chunkSize = (int) Math.ceil((double) rows.size() / threadCount);

        for (int i = 0; i < threadCount; i++) {

            int startIndex = i * chunkSize;
            int endIndex = Math.min(startIndex + chunkSize, rows.size());

            final List<WebElement> subList = rows.subList(startIndex, endIndex);

            executor.submit(() -> {
                addStockHistoryByWebElements(subList, stockHistoryEntityList, symbol);
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

}
