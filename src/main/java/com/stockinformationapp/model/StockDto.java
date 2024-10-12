package com.stockinformationapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class StockDto {
    private UUID id;
    private String stockName;
    private Date date;
}
