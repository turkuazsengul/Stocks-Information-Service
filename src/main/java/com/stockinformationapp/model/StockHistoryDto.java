package com.stockinformationapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockHistoryDto {
    private String id;
    private String stockName;
    private String date;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double closePrice;
    private double adjClose;
    private String volume;
}
