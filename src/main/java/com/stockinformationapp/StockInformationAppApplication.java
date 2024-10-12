package com.stockinformationapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class StockInformationAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockInformationAppApplication.class, args);
    }

}
