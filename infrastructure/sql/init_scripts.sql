CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Stock
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    Stock_Name   VARCHAR(255) NOT NULL,
    Created_Date DATE             DEFAULT CURRENT_DATE
);

CREATE TABLE StockHistoricMetric
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    Stock_Name  VARCHAR(255) NOT NULL,
    Date        DATE NOT NULL,
    Open_Price  DOUBLE PRECISION NOT NULL,
    High_Price  DOUBLE PRECISION NOT NULL,
    Low_Price   DOUBLE PRECISION NOT NULL,
    Close_Price DOUBLE PRECISION NOT NULL,
    Adj_Close   DOUBLE PRECISION NOT NULL,
    Volume      VARCHAR(255) NOT NULL
);
