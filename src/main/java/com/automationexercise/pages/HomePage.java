package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.automationexercise.utilities.ScreenshotUtilities;

public class HomePage {
    WebDriver driver;

    // Locators (same visibility style as LoginPage - no 'private')
    By logo = By.xpath("//div[@class='logo pull-left']");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Fetch page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Simple check if Home Page is loaded (mirrors the simple checks in LoginPage)
    public boolean isHomePageDisplayed() {
        try {
            return driver.getTitle().equals("Automation Exercise");
        } catch (Exception e) {
            return false;
        }
    }

    // Verify logo visible
    public boolean isLogoDisplayed() {
        try {
            return driver.findElement(logo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Capture screenshot of Home Page
    public String captureHomePageScreenshot(String screenshotName) {
        try {
            return ScreenshotUtilities.captureScreen(driver, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Capture screenshot of only the logo
    public String captureLogoScreenshot(String screenshotName) {
        try {
            WebElement logoElement = driver.findElement(logo);
            return ScreenshotUtilities.captureElement(driver, logoElement, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
