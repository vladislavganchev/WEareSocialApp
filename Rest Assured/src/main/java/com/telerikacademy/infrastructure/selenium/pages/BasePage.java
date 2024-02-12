package com.telerikacademy.infrastructure.selenium.pages;

import com.telerikacademy.infrastructure.selenium.UserActions;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.telerikacademy.infrastructure.selenium.Utils.getConfigPropertyByKey;

public abstract class BasePage {

    protected String url;
    protected WebDriver driver;
    public UserActions actions;

    public BasePage(WebDriver driver, String urlKey) {
        String pageUrl = getConfigPropertyByKey(urlKey);
        this.driver = driver;
        this.url = pageUrl;
        actions = new UserActions();
    }

    public String getUrl() {
        return url;
    }

    public void navigateToPage() {
        this.driver.get(url);
    }

    public void assertPageNavigated() {
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains(url),
                "Landed URL is not as expected. Actual URL: " + currentUrl + ". Expected URL: " + url);
    }

    public abstract void waitForPageToLoad();
}
