package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VideoTutorialsPage {

    private WebDriver driver;

    // Locators
    private By videoTutorialIcon = By.cssSelector("a[href='https://www.youtube.com/c/AutomationExercise'] i.fa-youtube-play");

    public VideoTutorialsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Check if the icon is visible
    public boolean isVideoIconVisible() {
        WebElement icon = driver.findElement(videoTutorialIcon);
        return icon.isDisplayed();
    }

    // Check if the icon is enabled (clickable)
    public boolean isVideoIconClickable() {
        WebElement icon = driver.findElement(videoTutorialIcon);
        return icon.isEnabled();
    }

    // Click the icon
    public void clickVideoIcon() {
        driver.findElement(videoTutorialIcon).click();
    }

    // Get current URL after click
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
