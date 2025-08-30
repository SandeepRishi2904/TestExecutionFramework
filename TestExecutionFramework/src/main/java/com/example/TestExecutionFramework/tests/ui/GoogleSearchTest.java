package com.example.TestExecutionFramework.tests.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleSearchTest {

    public boolean runTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://duckduckgo.com/");

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("Spring Boot Testing");
            searchBox.submit();

            Thread.sleep(2000);

            String title = driver.getTitle();
            return title.contains("Spring Boot");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            driver.quit();
        }
    }
}
