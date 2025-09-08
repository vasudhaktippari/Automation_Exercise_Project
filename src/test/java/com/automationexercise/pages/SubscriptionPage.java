package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubscriptionPage {

    private WebDriver driver;

    private By subscriptionSection = By.id("newsletter");
    private By emailField = By.id("susbscribe_email");
    private By subscribeButton = By.id("subscribe");
    private By successMessage = By.cssSelector(".alert-success");

    public SubscriptionPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        WebElement field = driver.findElement(emailField);
        field.clear();
        field.sendKeys(email);
    }

    public void clickSubscribe() {
        driver.findElement(subscribeButton).click();
    }

    public boolean isSubscriptionSectionVisible() {
       
        try {
            WebElement emailInput = driver.findElement(By.id("susbscribe_email"));
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isEmailFieldEnabled() {
        return driver.findElement(emailField).isEnabled();
    }

    public boolean isSubscribeButtonEnabled() {
        return driver.findElement(subscribeButton).isEnabled();
    }

    public String getEmailFieldPlaceholder() {
        return driver.findElement(emailField).getAttribute("placeholder");
    }

    public String getSuccessMessage() {
        // wait until the message is visible
        WebElement msg = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(driver1 -> driver1.findElement(successMessage));
        return msg.getText().trim();
    }
}
