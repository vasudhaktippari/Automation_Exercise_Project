package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class TestCasesPage {

    private WebDriver driver;

   
    private By testScenarioList = By.cssSelector(".panel-heading a"); // links to scenarios
    private By testStepsSection = By.cssSelector(".panel-body");      // steps of selected scenario
    private By feedbackLink = By.xpath("//a[contains(text(),'Contact us')]");
    private By subscriptionEmail = By.id("susbscribe_email");
    private By subscriptionBtn = By.id("subscribe");
    private By successMsg = By.cssSelector(".alert-success");

    
    private By productsLink = By.cssSelector("a[href='/products'] i");
    private By cartLink = By.cssSelector("a[href='/view_cart'] i");
    private By apiTestingLink = By.cssSelector("a[href='/api_list'] i");

    private By apiTestingHeading = By.xpath("/html/body/section/div/div[1]/div/h2/b");
    private By api1Link = By.xpath("/html/body/section/div/div[2]/div/div[1]/h4/a/u");
    private By api1Content = By.xpath("/html/body/section/div/div[2]/div/div[2]/ul/li[1]/a");

    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
    }

    // =================== Existing Actions ===================
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

    // =================== New Actions ===================

    // 🔹 Products Page
    public boolean isProductsLinkVisible() {
        return driver.findElement(productsLink).isDisplayed();
    }

    public void clickProductsLink() {
        driver.findElement(By.cssSelector("a[href='/products']")).click();
    }

    // 🔹 Cart Page
    public boolean isCartLinkVisible() {
        return driver.findElement(cartLink).isDisplayed();
    }

    public void clickCartLink() {
        driver.findElement(By.cssSelector("a[href='/view_cart']")).click();
    }

    // 🔹 API Testing Page
    public boolean isAPITestingLinkVisible() {
        return driver.findElement(apiTestingLink).isDisplayed();
    }

    public void clickAPITestingLink() {
        driver.findElement(By.cssSelector("a[href='/api_list']")).click();
    }

    public boolean isAPITestingHeadingVisible() {
        return driver.findElement(apiTestingHeading).isDisplayed();
    }

    public boolean isAPI1Visible() {
        return driver.findElement(api1Link).isDisplayed();
    }

    public void clickAPI1() {
        driver.findElement(api1Link).click();
    }

    public boolean isAPI1ContentVisible() {
        return driver.findElement(api1Content).isDisplayed();
    }
}
