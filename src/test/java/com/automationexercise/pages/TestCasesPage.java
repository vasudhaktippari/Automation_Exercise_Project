package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class TestCasesPage {

    private WebDriver driver;

    // Locators
    private By testScenarioList = By.cssSelector(".panel-heading a"); // links to scenarios
    private By testStepsSection = By.cssSelector(".panel-body");      // steps of selected scenario
    private By feedbackLink = By.xpath("//a[contains(text(),'Contact us')]");
    private By subscriptionEmail = By.id("susbscribe_email");
    private By subscriptionBtn = By.id("subscribe");
    private By successMsg = By.cssSelector(".alert-success");

    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void openTestCasesPage() {
        driver.get("https://automationexercise.com/test_cases");
    }

    public boolean isTestCasesPageLoaded() {
        return driver.getTitle().contains("Test Cases");
    }

    public List<WebElement> getTestScenarios() {
        return driver.findElements(testScenarioList);
    }

    public void clickScenario(int index) {
        getTestScenarios().get(index).click();
    }

    public boolean areStepsDisplayed() {
        return driver.findElement(testStepsSection).isDisplayed();
    }

    public void clickFeedback() {
        driver.findElement(feedbackLink).click();
    }

    public void enterSubscriptionEmail(String email) {
        driver.findElement(subscriptionEmail).clear();
        driver.findElement(subscriptionEmail).sendKeys(email);
    }

    public void submitSubscription() {
        driver.findElement(subscriptionBtn).click();
    }

    public boolean isSubscriptionSuccess() {
        return driver.findElement(successMsg).isDisplayed();
    }
}
