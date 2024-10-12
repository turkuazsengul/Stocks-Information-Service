package com.stockinformationapp.core.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumBaseConfiguration {

    protected WebDriver getChromeDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\turku\\OneDrive\\Masaüstü\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }
}
