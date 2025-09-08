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

    // Locators for new UI tests
    By homeIcon = By.cssSelector("a[href='/'] i.fa-home");
    By logo = By.cssSelector("div.logo.pull-left img");
    By testCasesBtn = By.xpath("//a[text()='Test Cases']");
    By apiTestingBtn = By.xpath("//a[text()='API Testing']");
    By loginSignupIcon = By.cssSelector("i.fa-user");
    By productsIcon = By.xpath("//a[text()=' Products']");
    By videoTutorialsIcon = By.cssSelector("i.fa-video-camera");
    By cartIcon = By.cssSelector("i.fa-shopping-cart");
    By noteMessage = By.xpath("//div[text()='Below contact form is for testing purpose.']");


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

    

    public boolean isHomeIconVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(homeIcon));
            return driver.findElement(homeIcon).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean clickHomeIcon() {
        try {
            driver.findElement(homeIcon).click();
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean isLogoVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(logo));
            return driver.findElement(logo).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean clickLogo() {
        try {
            driver.findElement(logo).click();
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean isButtonVisibleAndClickable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isNoteMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(noteMessage));
            return driver.findElement(noteMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
