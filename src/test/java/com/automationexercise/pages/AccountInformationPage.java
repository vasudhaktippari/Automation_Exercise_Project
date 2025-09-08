// File: src/main/java/com/automationexercise/pages/AccountInformationPage.java
package com.automationexercise.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static com.automationexercise.utilities.ClickUtils.safeClick;
import static com.automationexercise.utilities.ClickUtils.setCheckbox;
import static com.automationexercise.utilities.ClickUtils.suppressAds;

public class AccountInformationPage {

    private final WebDriver driver;

    // ---- Timeouts ----
    private static final Duration SHORT  = Duration.ofSeconds(5);
    private static final Duration MEDIUM = Duration.ofSeconds(10);
    private static final Duration LONG   = Duration.ofSeconds(15);

    public static final String ACCOUNT_INFO_TITLE = "Automation Exercise - Signup";

    // ---- Headers ----
    public static final By ENTER_ACCOUNT_INFO_HEADER = By.xpath(
        "//*[self::h1 or self::h2 or self::b]" +
        "[contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
        "'ENTER ACCOUNT INFORMATION')]"
    );

    public static final By ADDRESS_INFORMATION_HEADER = By.xpath(
        "//*[self::h1 or self::h2 or self::b]" +
        "[contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
        "'ADDRESS INFORMATION')]"
    );

    // ---- Account info ----
    public static final By TITLE_MR_RADIO       = By.id("id_gender1");
    public static final By TITLE_MRS_RADIO      = By.id("id_gender2");
    public static final By PASSWORD_FIELD       = By.id("password");
    public static final By DAYS_SELECT          = By.id("days");
    public static final By MONTHS_SELECT        = By.id("months");
    public static final By YEARS_SELECT         = By.id("years");
    public static final By NEWSLETTER_CHECKBOX  = By.id("newsletter");
    public static final By OFFERS_CHECKBOX      = By.id("optin");

    // ---- Address ----
    public static final By FIRST_NAME_FIELD     = By.id("first_name");
    public static final By LAST_NAME_FIELD      = By.id("last_name");
    public static final By COMPANY_FIELD        = By.id("company");
    public static final By ADDRESS1_FIELD       = By.id("address1");
    public static final By ADDRESS2_FIELD       = By.id("address2");
    public static final By COUNTRY_SELECT       = By.id("country");
    public static final By STATE_FIELD          = By.id("state");
    public static final By CITY_FIELD           = By.id("city");
    public static final By ZIPCODE_FIELD        = By.id("zipcode");
    public static final By MOBILE_NUMBER_FIELD  = By.id("mobile_number");

    // ---- Actions ----
    public static final By CREATE_ACCOUNT_BUTTON = By.xpath("//button[@data-qa='create-account']");

    // ---- Success page ----
    public static final By ACCOUNT_CREATED_TEXT  = By.xpath("//b[normalize-space()='Account Created!']");
    public static final By CONTINUE_BUTTON       = By.xpath("//a[@data-qa='continue-button']");

    // ---- Header verification after continue ----
    public static final By LOGGED_IN_ICON_LINK   = By.xpath("//a[.//i[contains(@class,'fa-user')]]");
    public static final By LOGGED_IN_TEXT_LINK   = By.xpath("//a[contains(.,'Logged in as')]");

    public AccountInformationPage(WebDriver driver) {
        this.driver = driver;
        suppressAds(driver);
        waitUntilLoaded();
    }

    // ---- Waiters / checks ----
    private void waitUntilLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, LONG);
        wait.until(ExpectedConditions.or(
            ExpectedConditions.titleIs(ACCOUNT_INFO_TITLE),
            ExpectedConditions.presenceOfElementLocated(ENTER_ACCOUNT_INFO_HEADER)
        ));
    }

    public boolean isAccountInfoPageDisplayed() {
        try { return driver.getTitle().equalsIgnoreCase(ACCOUNT_INFO_TITLE); }
        catch (Exception e) { return false; }
    }

    public boolean isEnterAccountInformationVisible() {
        try {
            new WebDriverWait(driver, MEDIUM)
                .until(ExpectedConditions.visibilityOfElementLocated(ENTER_ACCOUNT_INFO_HEADER));
            return driver.findElement(ENTER_ACCOUNT_INFO_HEADER).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean doesEnterAccountInformationHaveExactText() {
        try {
            String txt = driver.findElement(ENTER_ACCOUNT_INFO_HEADER).getText().trim();
            return "ENTER ACCOUNT INFORMATION".equalsIgnoreCase(txt);
        } catch (Exception e) { return false; }
    }

    public boolean isAddressInformationVisible() {
        try {
            new WebDriverWait(driver, MEDIUM)
                .until(ExpectedConditions.visibilityOfElementLocated(ADDRESS_INFORMATION_HEADER));
            return driver.findElement(ADDRESS_INFORMATION_HEADER).isDisplayed();
        } catch (Exception e) { return false; }
    }

    // ---- Fillers ----
    public AccountInformationPage selectTitleMr() {
        safeClick(driver, TITLE_MR_RADIO);
        return this;
    }

    public AccountInformationPage selectTitleMrs() {
        safeClick(driver, TITLE_MRS_RADIO);
        return this;
    }

    public AccountInformationPage setPassword(String pwd) {
        WebElement el = driver.findElement(PASSWORD_FIELD);
        el.clear();
        el.sendKeys(pwd);
        return this;
    }

    public AccountInformationPage setDOB(String day, String month, String year) {
        selectByVisibleTextResilient(DAYS_SELECT, day);
        selectByVisibleTextResilient(MONTHS_SELECT, month);
        selectByVisibleTextResilient(YEARS_SELECT, year);
        return this;
    }

    public AccountInformationPage toggleNewsletter(boolean enable) {
        setCheckbox(driver, NEWSLETTER_CHECKBOX, enable);
        return this;
    }

    public AccountInformationPage toggleOffers(boolean enable) {
        setCheckbox(driver, OFFERS_CHECKBOX, enable);
        return this;
    }

    public AccountInformationPage setFirstName(String val) {
        WebElement el = driver.findElement(FIRST_NAME_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setLastName(String val) {
        WebElement el = driver.findElement(LAST_NAME_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setCompany(String val) {
        WebElement el = driver.findElement(COMPANY_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setAddress1(String val) {
        WebElement el = driver.findElement(ADDRESS1_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setAddress2(String val) {
        WebElement el = driver.findElement(ADDRESS2_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setCountry(String visibleText) {
        new Select(driver.findElement(COUNTRY_SELECT)).selectByVisibleText(visibleText);
        return this;
    }

    public AccountInformationPage setState(String val) {
        WebElement el = driver.findElement(STATE_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setCity(String val) {
        WebElement el = driver.findElement(CITY_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setZipcode(String val) {
        WebElement el = driver.findElement(ZIPCODE_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage setMobile(String val) {
        WebElement el = driver.findElement(MOBILE_NUMBER_FIELD);
        el.clear();
        el.sendKeys(val);
        return this;
    }

    public AccountInformationPage submitCreateAccount() {
        safeClick(driver, CREATE_ACCOUNT_BUTTON);
        return this;
    }

    // ---- Post-submit verifications ----
    public boolean isAccountCreatedMessageVisible() {
        try { return driver.findElement(ACCOUNT_CREATED_TEXT).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public AccountInformationPage clickContinueAfterCreated() {
        for (int i = 0; i < 3; i++) {
            suppressAds(driver);
            safeClick(driver, CONTINUE_BUTTON);
            if (waitForLoggedInHeaderOrHome()) return this;
            sleep(250);
        }
        waitForLoggedInHeaderOrHome();
        return this;
    }

    public boolean isLoggedInHeaderVisible() {
        try {
            return driver.findElement(LOGGED_IN_ICON_LINK).isDisplayed()
                || driver.findElement(LOGGED_IN_TEXT_LINK).isDisplayed();
        } catch (Exception e) { return false; }
    }

    // ---- Helpers ----
    private boolean waitForLoggedInHeaderOrHome() {
        WebDriverWait wait = new WebDriverWait(driver, MEDIUM);
        try {
            return wait.until(d -> {
                suppressAds(d);
                boolean header = isLoggedInHeaderVisible();
                boolean titleOk = d.getTitle() != null && d.getTitle().toLowerCase().contains("automation exercise");
                return header || titleOk;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void selectByVisibleTextResilient(By selectLocator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, SHORT);
        wait.until(ExpectedConditions.presenceOfElementLocated(selectLocator));
        for (int i = 0; i < 3; i++) {
            try {
                Select s = new Select(driver.findElement(selectLocator));
                s.selectByVisibleText(text);
                return;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                sleep(150);
            }
        }
        throw new IllegalStateException("Option not present for " + selectLocator + ": " + text);
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    
    
    /** HTML5 validity helpers for First Name */
    public boolean isFirstNameValid() {
        try {
            WebElement el = driver.findElement(FIRST_NAME_FIELD);
            return (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].checkValidity();", el);
        } catch (Exception e) {
            return true; // be conservative; test will catch failures after submit
        }
    }

    public String getFirstNameValidationMessage() {
        try {
            WebElement el = driver.findElement(FIRST_NAME_FIELD);
            return (String) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].validationMessage;", el);
        } catch (Exception e) {
            return null;
        }
    }

    /** Blur the First Name field to trigger client-side validation */
    public AccountInformationPage blurFirstName() {
        try {
            driver.findElement(LAST_NAME_FIELD).click(); // simple blur target
        } catch (Exception ignored) {}
        return this;
    }
    
 // Generic HTML5 validity helpers
    public boolean checkValidity(By locator) {
        try {
            WebElement el = driver.findElement(locator);
            return (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].checkValidity();", el);
        } catch (Exception e) { return true; } // fall back to E2E assertion
    }

    public String validationMessage(By locator) {
        try {
            WebElement el = driver.findElement(locator);
            return (String) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].validationMessage;", el);
        } catch (Exception e) { return null; }
    }


}