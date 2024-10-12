package com.stockinformationapp.core.schedule;

import com.stockinformationapp.core.selenium.SeleniumBaseConfiguration;
import com.stockinformationapp.entity.StockEntity;
import com.stockinformationapp.entity.StockHistoryEntity;
import com.stockinformationapp.repository.StockHistoryRepository;
import com.stockinformationapp.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.stockinformationapp.util.StockOperationUtil.addStockHistoryByWebElements;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockHistoryScheduledTask extends SeleniumBaseConfiguration {
    private static final String WEB_TABLE_ELEMENT_XPATH = "/html/body/div[2]/main/section/section/section/article/div[1]/div[3]/table/tbody/tr";
    private static final String URL = "https://finance.yahoo.com/quote/%s/history/?period1=%s&period2=%s";

    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;

    @Scheduled(cron = "0 35 19 * * ?")
    public void runDailyTask() {
        log.info("Daily Scheduled Stock History Polling Task Started");

        WebDriver driver = getChromeDriver();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentDatePartStart = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime currentDatePartFinal = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 19, 33, 39);

        long startPartUnix = currentDatePartStart.toEpochSecond(ZoneOffset.UTC);
        long finalPartUnix = currentDatePartFinal.toEpochSecond(ZoneOffset.UTC);

        List<StockEntity> stockEntities = stockRepository.findAll();

        stockEntities.forEach(stockEntity -> {
            StockHistoryEntity stockHistoryEntity = getStockHistoryFromDailyPolling(stockEntity.getStockName(), startPartUnix, finalPartUnix, driver);
            stockHistoryRepository.save(stockHistoryEntity);
        });
    }

    private StockHistoryEntity getStockHistoryFromDailyPolling(String symbol, long startPartUnix, long finalPartUnix, WebDriver driver){
        List<StockHistoryEntity> stockHistoryEntityList = new ArrayList<>();
        String url = String.format(URL,symbol, startPartUnix, finalPartUnix);

        driver.get(url);
        List<WebElement> rows = driver.findElements(By.xpath(WEB_TABLE_ELEMENT_XPATH));

        addStockHistoryByWebElements(rows, stockHistoryEntityList, symbol);

        driver.quit();

        if (stockHistoryEntityList.size() != 1){
            throw new RuntimeException("Daily Stock Data Was Not Correct");
        }

        return stockHistoryEntityList.get(0);
    }

}
