package com.stockinformationapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "StockHistoricMetric")
public class StockHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "Stock_Name")
    private String stockName;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Open_Price")
    private double openPrice;

    @Column(name = "High_Price")
    private double highPrice;

    @Column(name = "Low_Price")
    private double lowPrice;

    @Column(name = "Close_Price")
    private double closePrice;

    @Column(name = "Adj_Close")
    private double adjClose;

    @Column(name = "Volume")
    private String volume;
}
