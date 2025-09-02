package com.automationexercise.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;
import com.automationexercise.pages.AccountInformationPage;


public class LoginTest extends BaseTest {
	
    //  verifyLoginPageIsDisplayed  

    @Test(groups = {"smoke"},priority = 1)
    public void verifyLoginPageIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page did not load properly");
        getTest().pass("Login page loaded and displayed successfully");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "LoginPageDisplayed");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    // Verify Sign Up section is visible
    @Test(groups = {"smoke"},priority = 2)
    public void verifySignUpSectionIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isSignUpSectionDisplayed(), "Sign Up section not displayed on Login page");
        getTest().pass("Sign Up section is displayed with Name, Email fields and Sign Up button");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SignUpSectionDisplayed");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }
    
    
   

    
 
    
    //Verify a user sign/up 
    @Test(groups = {"smoke","functional"},priority = 3)
    public void verifyUserRegistration() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        // Verify Sign Up section is displayed
        Assert.assertTrue(loginPage.isSignUpSectionDisplayed(), "Sign Up section is not visible on Login page");

        // Use a unique email (timestamp to avoid "already exists" error)
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@example.com";
        String name = "TestUser";

        // Perform Sign Up
        loginPage.signUp(name, uniqueEmail);

        // Assert we land on Account Information page
        Assert.assertTrue(loginPage.isAccountInfoPageDisplayed(),
                "Account Information page was not displayed after sign up");

        getTest().pass("Sign Up started successfully with name: " + name + " and email: " + uniqueEmail);

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SignUp_" + name);
        getTest().addScreenCaptureFromPath(screenshotPath);
    }
    
 // Verify "ENTER ACCOUNT INFORMATION" header is visible
    @Test(groups = {"smoke"}, priority = 4)
    public void verifyEnterAccountInformationHeaderIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        // Use a unique email for registration
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@example.com";
        String name = "HeaderCheckUser";

        // Perform sign up step
        loginPage.signUp(name, uniqueEmail);

        // Land on Account Information page
        AccountInformationPage accountPage = new AccountInformationPage(getDriver());

        Assert.assertTrue(
            accountPage.isEnterAccountInformationVisible(),
            "'ENTER ACCOUNT INFORMATION' header was not displayed"
        );

        getTest().pass("'ENTER ACCOUNT INFORMATION' header is displayed successfully");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "EnterAccountInformationHeader");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

 // Verify "ADDRESS INFORMATION" header is visible
    @Test(groups = {"smoke"}, priority = 5)
    public void verifyAddressInformationHeaderIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        // Use a unique email to reach Account Information page
        String uniqueEmail = "addr_" + System.currentTimeMillis() + "@example.com";
        String name = "AddressCheckUser";

        // Perform sign up
        loginPage.signUp(name, uniqueEmail);

        // Land on Account Information page
        AccountInformationPage accountPage = new AccountInformationPage(getDriver());

        Assert.assertTrue(
            accountPage.isAddressInformationVisible(),
            "'ADDRESS INFORMATION' header was not displayed"
        );

        getTest().pass("'ADDRESS INFORMATION' header is displayed successfully");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "AddressInformationHeader");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    
    
    //verify user successfully registers
    @Test(
    	    dataProvider = "registrationData",
    	    groups = {"smoke","functional"},
    	    priority = 6
    	)
    	public void registerUserFromExcel(
    	        String name,
    	        String email,
    	        String password,
    	        String day,
    	        String month,
    	        String year,
    	        String newsletter,
    	        String offers,
    	        String firstName,
    	        String lastName,
    	        String company,
    	        String address1,
    	        String address2,
    	        String country,
    	        String state,
    	        String city,
    	        String zipcode,
    	        String mobile
    	) throws Exception {

    	    LoginPage loginPage = new LoginPage(getDriver()).open();

    	    // Pre-check: Sign Up section
    	    Assert.assertTrue(loginPage.isSignUpSectionDisplayed(),
    	            "Sign Up section is not visible on Login page");

    	    // Unique email if blank or placeholder
    	    if (email == null || email.trim().isEmpty() || email.contains("{{unique}}")) {
    	        email = (name == null || name.isEmpty() ? "user" : name.toLowerCase().replaceAll("\\s+",""))
    	                + "_" + System.currentTimeMillis() + "@example.com";
    	    }

    	    // Start sign up
    	    loginPage.signUp(name, email);

    	    // Land on Account Information page
    	    AccountInformationPage accountPage = new AccountInformationPage(getDriver());
    	    Assert.assertTrue(accountPage.isAccountInfoPageDisplayed(),
    	            "Account Information page was not displayed after sign up");

    	    // Parse booleans from sheet (accepts true/false/yes/no/y/n/1/0)
    	    boolean nl = parseBooleanFlexible(newsletter);
    	    boolean of = parseBooleanFlexible(offers);

    	    // Fill Account Information
    	    accountPage
    	            .selectTitleMr()                // or selectTitleMrs()—make it a column if needed
    	            .setPassword(password)
    	            .setDOB(day, month, year)       // Make sure month text matches dropdown (e.g., "May")
    	            .toggleNewsletter(nl)
    	            .toggleOffers(of)
    	            .setFirstName(firstName)
    	            .setLastName(lastName)
    	            .setCompany(company)
    	            .setAddress1(address1)
    	            .setAddress2(address2)
    	            .setCountry(country)            // e.g., "United States"
    	            .setState(state)
    	            .setCity(city)
    	            .setZipcode(zipcode)
    	            .setMobile(mobile)
    	            .submitCreateAccount();

    	    // Verify account created
    	    Assert.assertTrue(accountPage.isAccountCreatedMessageVisible(),
    	            "Account Created message was not displayed");

    	    getTest().pass("Account registered successfully with email: " + email);
    	    String createdShot = ScreenshotUtilities.captureScreen(getDriver(), "Registered_" + name);
    	    getTest().addScreenCaptureFromPath(createdShot);

    	    // Continue → verify logged in header
    	    accountPage.clickContinueAfterCreated();
    	    Assert.assertTrue(accountPage.isLoggedInHeaderVisible(),
    	            "Logged-in header not visible after account creation");

    	    getTest().pass("Newly registered user is logged in successfully");
    	}
    //verify login
    @Test(dataProvider = "logindata", groups = {"smoke", "functional"},priority = 7)
    public void verifyLogin(String username, String password) throws IOException {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        loginPage.login(username, password);

        if (loginPage.isLoginSuccessful()) {
            getTest().pass("Login successful for user: " + username);
            String successShot = ScreenshotUtilities.captureScreen(getDriver(), "LoginSuccess_" + username);
            getTest().addScreenCaptureFromPath(successShot);
        } else {
            String error = loginPage.getErrorMessage();
            getTest().fail("Login failed for user: " + username + " | Error: " + error);
            String failShot = ScreenshotUtilities.captureScreen(getDriver(), "LoginFail_" + username);
            getTest().addScreenCaptureFromPath(failShot);
            Assert.fail("Login failed for user: " + username + " | Error: " + error);
        }
    }
    
    //verify logout 
    @Test(
        dataProvider = "logindata",
        priority = 8,
        groups = {"smoke", "functional"},
        dependsOnMethods = "verifyLogin"
    )
    public void verifyLogout(String username, String password) throws IOException {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.login(username, password);

        Assert.assertTrue(
            loginPage.isLoginSuccessful(),
            "Precondition failed: Login was not successful for user: " + username
        );

        loginPage.logout();

        if (loginPage.isLogoutSuccessful()) {
            getTest().pass("Logout successful for user: " + username);
            String successShot = ScreenshotUtilities.captureScreen(getDriver(), "LogoutSuccess_" + username);
            getTest().addScreenCaptureFromPath(successShot);
        } else {
            getTest().fail("Logout failed for user: " + username);
            String failShot = ScreenshotUtilities.captureScreen(getDriver(), "LogoutFail_" + username);
            getTest().addScreenCaptureFromPath(failShot);
            Assert.fail("Logout verification failed for user: " + username);
        }
    }
    	/**
    	 * Helper to interpret flexible boolean strings from Excel.
    	 * Accepts: true/false, yes/no, y/n, 1/0 (case-insensitive).
    	 */
    	private boolean parseBooleanFlexible(String val) {
    	    if (val == null) return false;
    	    String v = val.trim().toLowerCase();
    	    return v.equals("true") || v.equals("yes") || v.equals("y") || v.equals("1");
    	}

    

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() throws IOException {
     // Reads rows from Sheet1 -> Object[][]
      return ExcelUtilities.getData("Sheet1");
    	}

    @DataProvider
    public Object[][] logindata() throws IOException {
        // Reads rows from DynamicData -> Object[][]

        return ExcelUtilities.getData("DynamicData");
    }
}
