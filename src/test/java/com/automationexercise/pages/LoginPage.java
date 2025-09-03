package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Locators login verificaion
    By emailField    = By.xpath("//input[@data-qa='login-email']");
    By passwordField = By.xpath("//input[@data-qa='login-password']");
    By loginButton   = By.xpath("//button[@data-qa='login-button']");
    By loggedInUser  = By.xpath("//i[@class='fa fa-user']/..");  // success locator (shows username in header)
    By errorMessage  = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]"); // error locator
    
   // Locators signup section
    
    By signupHeader     = By.xpath("//h2[text()='New User Signup!']");
    By signupNameField  = By.xpath("//input[@data-qa='signup-name']");
    By signupEmailField = By.xpath("//input[@data-qa='signup-email']");
    By signupButton     = By.xpath("//button[@data-qa='signup-button']");
    
    

    // locator logout button
    By logoutbtn     = By.xpath("//a[@href='/logout']");
    
    // Header: "Login to your account"
    By loginHeader = By.xpath("//h2[normalize-space()='Login to your account']");
    
     // Header: "New User Signup!"
    By newUserSignupHeader = By.xpath("//h2[normalize-space()='New User Signup!']");

    // Page constants
    String LOGIN_URL   = "https://automationexercise.com/login";
    String LOGIN_TITLE = "Automation Exercise - Signup / Login";

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // === Navigation / Page checks ===
    public LoginPage open() {
        driver.get(LOGIN_URL);
        return this;
    }
    
    
    // Header: "Login to your account"

    public boolean isLoginHeaderVisible() {
        try {
            return driver.findElement(loginHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Header: "New User Signup!"

    
    public boolean isNewUserSignupHeaderVisible() {
        try {
            return driver.findElement(newUserSignupHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    
    
    
    public boolean isLoginPageDisplayed() {
        try {
            return driver.getTitle().equalsIgnoreCase(LOGIN_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    // === Actions ===
    public void login(String email, String password) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public void logout() {
        driver.findElement(logoutbtn).click();
    }

    // Backward-compatible alias (old typo)
    public void logut() {
        logout();
    }

    // === Result checks ===
    public boolean isLoginSuccessful() {
        try {
            return driver.findElement(loggedInUser).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to fetch error message (if login fails)
    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isLogoutSuccessful() {
        try {
            String actualTitle = driver.getTitle();
            String expectedTitle = LOGIN_TITLE;
            return actualTitle.equalsIgnoreCase(expectedTitle);
        } catch (Exception e) {
            return false;
        }
    }
    
 // === Checks for Sign Up section ===
    public boolean isSignUpSectionDisplayed() {
        try {
            return driver.findElement(signupHeader).isDisplayed()
                && driver.findElement(signupNameField).isDisplayed()
                && driver.findElement(signupEmailField).isDisplayed()
                && driver.findElement(signupButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
 // === Action: Sign Up ===
    public void signUp(String name, String email) {
        driver.findElement(signupNameField).clear();
        driver.findElement(signupNameField).sendKeys(name);
        driver.findElement(signupEmailField).clear();
        driver.findElement(signupEmailField).sendKeys(email);
        driver.findElement(signupButton).click();
    }

    // === Check: Did Sign Up page open? ===
    public boolean isAccountInfoPageDisplayed() {
        try {
            String expectedTitle = "Automation Exercise - Signup";
            return driver.getTitle().equalsIgnoreCase(expectedTitle);
        } catch (Exception e) {
            return false;
        }
    }
    
    

}
