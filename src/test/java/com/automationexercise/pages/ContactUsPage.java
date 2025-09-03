package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ContactUsPage {
    WebDriver driver;
    WebDriverWait wait;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By nameInput = By.name("name");
    By emailInput = By.name("email");
    By subjectInput = By.name("subject");
    By messageInput = By.name("message");
    By submitBtn = By.cssSelector("input[type='submit']");
    By successMessage = By.cssSelector("div.status.alert-success");
    By errorMessage = By.cssSelector("div.status.alert-danger");

    // Fill the form
    public void fillForm(String name, String email, String subject, String message) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);

        driver.findElement(subjectInput).clear();
        driver.findElement(subjectInput).sendKeys(subject);

        driver.findElement(messageInput).clear();
        driver.findElement(messageInput).sendKeys(message);
    }

    // Submit form
    public void submitForm() {
        driver.findElement(submitBtn).click();
    }

    // Check if success message is visible
    public boolean isSuccessMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Check if error message is visible
    public boolean isErrorMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessageText() {
        try {
            return driver.findElement(successMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getErrorMessageText() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
