package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class AccountInformationPage {
    WebDriver driver;

    // --- Page title check ---
    String ACCOUNT_INFO_TITLE = "Automation Exercise - Signup";
    
 // --- Locators: headers ---
    By enterAccountInfoHeader = By.xpath(
        "//*[self::h1 or self::h2 or self::b]" +
        "[contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
        "'ENTER ACCOUNT INFORMATION')]"
    );


    // --- Locators: Account Information (Step 2) ---
    By titleMrRadio        = By.id("id_gender1");
    By titleMrsRadio       = By.id("id_gender2");
    By passwordField       = By.id("password");

    By daysSelect          = By.id("days");
    By monthsSelect        = By.id("months");
    By yearsSelect         = By.id("years");

    By newsletterCheckbox  = By.id("newsletter");
    By offersCheckbox      = By.id("optin");

    // Address info
    By firstNameField      = By.id("first_name");
    By lastNameField       = By.id("last_name");
    By companyField        = By.id("company");
    By address1Field       = By.id("address1");
    By address2Field       = By.id("address2");
    By countrySelect       = By.id("country");
    By stateField          = By.id("state");
    By cityField           = By.id("city");
    By zipcodeField        = By.id("zipcode");
    By mobileNumberField   = By.id("mobile_number");

    // Actions
    By createAccountButton = By.xpath("//button[@data-qa='create-account']");

    // Success page
    By accountCreatedText  = By.xpath("//b[normalize-space()='Account Created!']");
    By continueButton      = By.xpath("//a[@data-qa='continue-button']");

    // Header verification after continue
    By loggedInAs         = By.xpath("//a[.//i[contains(@class,'fa-user')]]");

    public AccountInformationPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Checks ---
    public boolean isAccountInfoPageDisplayed() {
        try {
            return driver.getTitle().equalsIgnoreCase(ACCOUNT_INFO_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

 // --- Checks ---
    public boolean isEnterAccountInformationVisible() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions
                            .visibilityOfElementLocated(enterAccountInfoHeader));
            return driver.findElement(enterAccountInfoHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** (Optional) Assert text exactly, ignoring case/extra spaces */
    public boolean doesEnterAccountInformationHaveExactText() {
        try {
            String txt = driver.findElement(enterAccountInfoHeader).getText().trim();
            return "ENTER ACCOUNT INFORMATION".equalsIgnoreCase(txt);
        } catch (Exception e) {
            return false;
        }
    }

    
    
    // --- Fillers ---
    public AccountInformationPage selectTitleMr() {
        driver.findElement(titleMrRadio).click();
        return this;
    }

    public AccountInformationPage selectTitleMrs() {
        driver.findElement(titleMrsRadio).click();
        return this;
    }

    public AccountInformationPage setPassword(String pwd) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(pwd);
        return this;
    }

    public AccountInformationPage setDOB(String day, String month, String year) {
        new Select(driver.findElement(daysSelect)).selectByVisibleText(day);
        new Select(driver.findElement(monthsSelect)).selectByVisibleText(month);
        new Select(driver.findElement(yearsSelect)).selectByVisibleText(year);
        return this;
    }

    public AccountInformationPage toggleNewsletter(boolean enable) {
        if (driver.findElement(newsletterCheckbox).isSelected() != enable) {
            driver.findElement(newsletterCheckbox).click();
        }
        return this;
    }

    public AccountInformationPage toggleOffers(boolean enable) {
        if (driver.findElement(offersCheckbox).isSelected() != enable) {
            driver.findElement(offersCheckbox).click();
        }
        return this;
    }

    public AccountInformationPage setFirstName(String val) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(val);
        return this;
    }

    public AccountInformationPage setLastName(String val) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(val);
        return this;
    }

    public AccountInformationPage setCompany(String val) {
        driver.findElement(companyField).clear();
        driver.findElement(companyField).sendKeys(val);
        return this;
    }

    public AccountInformationPage setAddress1(String val) {
        driver.findElement(address1Field).clear();
        driver.findElement(address1Field).sendKeys(val);
        return this;
    }

    public AccountInformationPage setAddress2(String val) {
        driver.findElement(address2Field).clear();
        driver.findElement(address2Field).sendKeys(val);
        return this;
    }

    public AccountInformationPage setCountry(String visibleText) {
        new Select(driver.findElement(countrySelect)).selectByVisibleText(visibleText);
        return this;
    }

    public AccountInformationPage setState(String val) {
        driver.findElement(stateField).clear();
        driver.findElement(stateField).sendKeys(val);
        return this;
    }

    public AccountInformationPage setCity(String val) {
        driver.findElement(cityField).clear();
        driver.findElement(cityField).sendKeys(val);
        return this;
    }

    public AccountInformationPage setZipcode(String val) {
        driver.findElement(zipcodeField).clear();
        driver.findElement(zipcodeField).sendKeys(val);
        return this;
    }

    public AccountInformationPage setMobile(String val) {
        driver.findElement(mobileNumberField).clear();
        driver.findElement(mobileNumberField).sendKeys(val);
        return this;
    }

    public AccountInformationPage submitCreateAccount() {
        driver.findElement(createAccountButton).click();
        return this;
    }

    // --- Post-submit verifications ---
    public boolean isAccountCreatedMessageVisible() {
        try {
            return driver.findElement(accountCreatedText).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public AccountInformationPage clickContinueAfterCreated() {
        driver.findElement(continueButton).click();
        return this;
    }

    public boolean isLoggedInHeaderVisible() {
        try {
            return driver.findElement(loggedInAs).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
