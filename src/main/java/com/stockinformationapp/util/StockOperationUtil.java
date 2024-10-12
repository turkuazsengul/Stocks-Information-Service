package com.stockinformationapp.util;

import com.stockinformationapp.entity.StockHistoryEntity;
import com.stockinformationapp.model.StockHistoryDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public final class StockOperationUtil {
    public StockOperationUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Invalid Access Utility Class");
    }

    public static void addStockHistoryByWebElements(List<WebElement> webElementList, List<StockHistoryEntity> stockHistoryEntityList, String symbol){
        for (WebElement row : webElementList) {
            StockHistoryEntity stockHistoryEntity = new StockHistoryEntity();

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                if (cells.size() == 7) {
                    stockHistoryEntity.setStockName(symbol);
                    stockHistoryEntity.setDate(Date.from(getLocalDateFromString(cells.get(0).getText()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    stockHistoryEntity.setOpenPrice(Double.parseDouble(cells.get(1).getText()));
                    stockHistoryEntity.setHighPrice(Double.parseDouble(cells.get(2).getText()));
                    stockHistoryEntity.setLowPrice(Double.parseDouble(cells.get(3).getText()));
                    stockHistoryEntity.setClosePrice(Double.parseDouble(cells.get(4).getText()));
                    stockHistoryEntity.setAdjClose(Double.parseDouble(cells.get(5).getText()));
                    stockHistoryEntity.setVolume(cells.get(6).getText());

                    stockHistoryEntityList.add(stockHistoryEntity);
                }
            }
        }
    }

    public static LocalDate getLocalDateFromString(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        return LocalDate.parse(dateString, formatter);
    }

    public static Date getDateFromString(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static StockHistoryEntity convertStockDtoToStockHistory(StockHistoryDto stockHistoryDto){
        StockHistoryEntity stockHistoryEntity = new StockHistoryEntity();

        if (stockHistoryDto.getId() != null){
            stockHistoryEntity.setId(UUID.fromString(stockHistoryDto.getId()));
        }
        stockHistoryEntity.setStockName(stockHistoryDto.getStockName());
        stockHistoryEntity.setDate(getDateFromString(stockHistoryDto.getDate()));
        stockHistoryEntity.setOpenPrice(stockHistoryDto.getOpenPrice());
        stockHistoryEntity.setHighPrice(stockHistoryDto.getHighPrice());
        stockHistoryEntity.setLowPrice(stockHistoryDto.getLowPrice());
        stockHistoryEntity.setClosePrice(stockHistoryDto.getClosePrice());
        stockHistoryEntity.setAdjClose(stockHistoryDto.getAdjClose());
        stockHistoryEntity.setVolume(stockHistoryDto.getVolume());

        return stockHistoryEntity;
    }
}
