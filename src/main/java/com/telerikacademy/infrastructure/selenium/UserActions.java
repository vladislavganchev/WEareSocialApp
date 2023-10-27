package com.telerikacademy.infrastructure.selenium;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.telerikacademy.infrastructure.selenium.Utils.*;
import static java.lang.String.format;

public class UserActions {
    final WebDriver driver;
    final TimeoutSettings timeoutSettings;
    final Actions driverActions;
    public WebDriver getDriver() {
        return driver;
    }

    public UserActions() {
        driver = getWebDriver();
        timeoutSettings = new TimeoutSettings();
        driverActions = new Actions(driver);
    }

    public static void loadBrowser(String baseUrlKey) {
        getWebDriver().get(getConfigPropertyByKey(baseUrlKey));
    }

    public static void quitDriver() {
        tearDownWebDriver();
    }

    public void clickElement(String key, Object... arguments) {
        String locator = getLocatorValueByKey(key, arguments);

        LOGGER.info("Clicking on element " + key);
        WebElement element = driver.findElement(By.xpath(locator));
        element.click();
    }

    public void typeValueInField(String value, String field, Object... fieldArguments) {
        String locator = getLocatorValueByKey(field, fieldArguments);
        WebElement element = driver.findElement(By.xpath(locator));
        element.sendKeys(value);
    }

    public void dragAndDropElement(String fromElementLocator, String toElementLocator) {
        String fromLocator = getLocatorValueByKey(fromElementLocator);
        WebElement fromElement = driver.findElement(By.xpath(fromLocator));

        String toLocator = getLocatorValueByKey(toElementLocator);
        WebElement toElement = driver.findElement(By.xpath(toLocator));

        Action dragAndDrop = driverActions.clickAndHold(fromElement)
                .moveToElement(toElement)
                .release(toElement)
                .build();
        dragAndDrop.perform();
    }

    //############# WAITS #########
    public void waitForElementVisible(String locatorKey, Object... arguments) {
        waitForElementVisibleUntilTimeout(locatorKey, arguments);
    }

    public void waitForElementClickable(String locatorKey, Object... arguments) {
        waitForElementToBeClickableUntilTimeout(locatorKey, arguments);
    }

    public void waitForPageToLoadCompletely() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSettings.jsScripts());
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(webDriver -> (js.executeScript("return document.readyState").equals("complete")));
    }

    //############# WAITS #########
    public void waitForElementPresent(String locator, Object... arguments) {
        waitForElementPresenceUntilTimeout(locator, arguments);
    }

    public void assertElementPresent(String locator) {
        Assertions.assertNotNull(driver.findElement(By.xpath(getUIMappingByKey(locator))),
                format("Element with %s doesn't present.", locator));
    }

    public void assertElementAttribute(String locator, String attributeName, String attributeValue) {
        String xpath = getLocatorValueByKey(locator);
        WebElement element = driver.findElement(By.xpath(xpath));
        String value = element.getAttribute(attributeName);
        Assertions.assertEquals(format("Element with locator %s doesn't match", attributeName), getLocatorValueByKey(attributeValue), value);
    }

    private String getLocatorValueByKey(String locator) {
        return format(getUIMappingByKey(locator));
    }

    private String getLocatorValueByKey(String locator, Object[] arguments) {
        return format(getUIMappingByKey(locator), arguments);
    }

    private void waitForElementVisibleUntilTimeout(String locator, Object... locatorArguments) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSettings.elementToBeVisible());
        String xpath = getLocatorValueByKey(locator, locatorArguments);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (Exception exception) {
            Assertions.fail("Element with locator: '" + xpath + "' was not found.");
        }
    }

    private void waitForElementToBeClickableUntilTimeout(String locator, Object... locatorArguments) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSettings.elementToBeClickable());
        String xpath = getLocatorValueByKey(locator, locatorArguments);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception exception) {
            Assertions.fail("Element with locator: '" + xpath + "' was not found.");
        }
    }

    private void waitForElementPresenceUntilTimeout(String locator, Object... locatorArguments) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSettings.elementToBePresent());
        String xpath = getLocatorValueByKey(locator, locatorArguments);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        } catch (Exception exception) {
            Assertions.fail("Element with locator: '" + xpath + "' was not found.");
        }
    }


    public void hoverElement(String key, Object... arguments) {
        // TODO: Implement the The Logger message
        var element = findElementByXpath(key);
        LOGGER.info("Hovering element with locator");
        driverActions.moveToElement(element).perform();

    }

    public void switchToIFrame(String iframe) {
        // TODO: Implement the The Logger message
        var iframeElement = findElementByXpath(iframe);
        driver.switchTo().frame(iframeElement);
    }

    public boolean isElementPresent(String locator, Object... arguments) {
        // TODO: Implement the method
        // 1. Get default timeout from properties
        // 2. Initialize Wait utility
        // 3. Try to wait for element present
        // 4. return true/false if the element is/not present
        return true;
    }

    public boolean isElementVisible(String locator, Object... arguments) {
        // TODO: Implement the method
        // 1. Get default timeout from properties
        // 2. Initialize Wait utility
        // 3. Try to wait for element visible
        // 4. return true/false if the element is/not visible
        return true;
    }

    public void waitFor(long timeOutMilliseconds) {
        try {
            Thread.sleep(timeOutMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //############# ASSERTS #########
    public void assertNavigatedUrl(String urlKey) {
        // TODO: Implement the method
        String expectedUrl = getConfigPropertyByKey(urlKey);
        String actualUrl = driver.getCurrentUrl();

        Assertions.assertEquals(expectedUrl, actualUrl, String.format("Expected url is: %s, but actual url is %s", expectedUrl, actualUrl));
    }

    public void pressKey(Keys key) {
        LOGGER.info("Sending Key:" + key.toString());
        driverActions.sendKeys(key);
    }

    public String getElementAttribute(String key, String attributeName) {
        WebElement element = findElementByXpath(key);

        var attributeValue = element.getAttribute(attributeName);

        return attributeValue;
    }

    private WebElement findElementByXpath(String locatorKey) {
        String value = getUIMappingByKey(locatorKey);

        return driver.findElement(By.xpath(value));
    }
}
