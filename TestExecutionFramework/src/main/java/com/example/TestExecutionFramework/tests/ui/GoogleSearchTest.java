package com.example.TestExecutionFramework.tests.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleSearchTest {

    public boolean runTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--disable-gpu"); // Optional: Reduce resource usage
        options.addArguments("--window-size=1920,1080"); // Set window size
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("https://www.duckduckgo.com");
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("Spring Boot Testing");
            searchBox.submit();
            String title = driver.getTitle();
            return title.contains("Spring Boot");
        } catch (Exception e) {
            return false;
        } finally {
            driver.quit();
        }
    }
}