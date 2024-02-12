package com.telerikacademy.infrastructure.selenium;

import java.time.Duration;

public class TimeoutSettings {
    private Duration elementToBeVisible;
    private Duration elementToBePresent;
    private Duration elementToBeClickable;
    private Duration jsScripts;

    public TimeoutSettings() {
        setElementToBePresent();
        setElementToBeVisible();
        setElementToBeClickable();
        setJsScripts();
    }

    public Duration elementToBeVisible() {
        return elementToBeVisible;
    }
    public Duration jsScripts() {
        return jsScripts;
    }

    public Duration elementToBePresent() {
        return elementToBePresent;
    }

    public Duration elementToBeClickable(){
        return elementToBeClickable;
    }

    private void setElementToBePresent() {
        int timeout = Integer.parseInt(Utils.getConfigPropertyByKey("config.timeoutSettings.elementToBePresent"));
        elementToBePresent = Duration.ofSeconds(timeout);
    }

    private void setElementToBeVisible() {
        int timeout = Integer.parseInt(Utils.getConfigPropertyByKey("config.timeoutSettings.elementToBeVisible"));
        elementToBeVisible = Duration.ofSeconds(timeout);
    }

    private void setElementToBeClickable(){
        int timeout = Integer.parseInt(Utils.getConfigPropertyByKey("config.timeoutSettings.elementToBeClickable"));
        elementToBeClickable = Duration.ofSeconds(timeout);
    }

    private void setJsScripts(){
        int timeout = Integer.parseInt(Utils.getConfigPropertyByKey("config.timeoutSettings.jsScripts"));
        jsScripts = Duration.ofSeconds(timeout);
    }
}
