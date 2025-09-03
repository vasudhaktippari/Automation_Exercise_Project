package com.automationexercise.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.AccountInformationPage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;

public class LoginTest extends BaseTest {
	
	
	// Verify login with invalid credentials
	// Invalid login using data from Excel + screenshot + error in Extent
	@Test(
	    dataProvider = "invalidLoginData",
	    groups = {"negative","functional","Regression","Data-Driven"},
	    priority = 10
	)
	public void verifyLoginWithInvalidCredentialsFromExcel(
	        String email,
	        String password,
	        String expectedError // optional column; assert if provided
	) throws Exception {

	    getTest().info("Starting invalid login test | Email: " + email);

	    LoginPage loginPage = new LoginPage(getDriver()).open();

	    // Attempt invalid login
	    loginPage.login(email, password);

	    // Capture error text
	    String errorMessage = loginPage.getErrorMessage();
	    getTest().info("UI Error message: " + errorMessage);

	    // Screenshot (unique name per row)
	    String ssName = "InvalidLoginError_" + (email == null ? "null" : email.replaceAll("[^a-zA-Z0-9]", "_"))
	            + "_" + System.currentTimeMillis();
	    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), ssName);
	    getTest().addScreenCaptureFromPath(screenshotPath);

	    // Assertions
	    Assert.assertFalse(loginPage.isLoginSuccessful(),
	            "Login unexpectedly succeeded with invalid credentials for: " + email);
	    Assert.assertNotNull(errorMessage, "Expected an error message on invalid login, but none was shown!");

	    // If expected error is provided in Excel, validate it; otherwise validate default copy
	    if (expectedError != null && !expectedError.isBlank()) {
	        Assert.assertTrue(errorMessage.contains(expectedError),
	                "Error text mismatch.\nExpected to contain: " + expectedError + "\nActual: " + errorMessage);
	    } else {
	        Assert.assertTrue(errorMessage.contains("Your email or password is incorrect!"),
	                "Default error copy mismatch. Actual: " + errorMessage);
	    }

	    getTest().pass("Invalid login correctly failed and evidence captured for: " + email);
	}
	@Test(
		    dataProvider = "invalidLoginData",
		    groups = {"negative","functional","Regression","Data-Driven"},
		    priority = 11
		)
		public void verifyLoginWithValidEmailAndWrongPassword(
		        String email,
		        String wrongPassword,
		        String expectedError
		) throws Exception {

		    getTest().info("Attempting login with VALID email but WRONG password | Email: " + email);

		    LoginPage loginPage = new LoginPage(getDriver()).open();

		    // Try to login with valid email + wrong password
		    loginPage.login(email, wrongPassword);

		    // Capture error message
		    String errorMessage = loginPage.getErrorMessage();
		    getTest().info("UI Error Message: " + errorMessage);

		    // Screenshot (per dataset)
		    String ssName = "ValidEmailWrongPassword_" + email.replaceAll("[^a-zA-Z0-9]", "_")
		            + "_" + System.currentTimeMillis();
		    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), ssName);
		    getTest().addScreenCaptureFromPath(screenshotPath);

		    // Assertions
		    Assert.assertFalse(loginPage.isLoginSuccessful(),
		            "Login unexpectedly succeeded with valid email + wrong password!");
		    Assert.assertNotNull(errorMessage, "Expected error message but found none!");

		    if (expectedError != null && !expectedError.isBlank()) {
		        Assert.assertTrue(errorMessage.contains(expectedError),
		                "Error text mismatch.\nExpected: " + expectedError + "\nActual: " + errorMessage);
		    } else {
		        Assert.assertTrue(errorMessage.contains("Your email or password is incorrect!"),
		                "Default error text mismatch. Actual: " + errorMessage);
		    }

		    getTest().pass("Login failed as expected with valid email + wrong password. Error and screenshot captured.");
		}


	// LoginTest.java (add to your class)

	@Test(
	    dataProvider = "invalidEmailSignUpData",
	    groups = {"negative","ui","functional","Data-Driven"},
	    priority = 12
	)
	public void verifySignUpWithInvalidEmailFormat_FromExcel(
	        String name,
	        String invalidEmail,
	        String expectedValidation // optional (browser copy differs by locale)
	) throws Exception {

	    getTest().info("Sign Up with invalid email | Name: " + name + " | Email: " + invalidEmail);

	    LoginPage loginPage = new LoginPage(getDriver()).open();
	    Assert.assertTrue(loginPage.isSignUpSectionDisplayed(), "Sign Up section is not displayed");

	    // Try to submit invalid email
	    loginPage
	            .typeSignUpName(name == null || name.isBlank() ? "TestUser" : name)
	            .typeSignUpEmail(invalidEmail)
	            .clickSignUp();

	    // HTML5 validity + native validation text
	    boolean isValid = loginPage.isSignUpEmailValid();
	    String validationMsg = loginPage.getSignUpEmailValidationMessage();

	    getTest().info("Email validity (HTML5): " + isValid);
	    getTest().info("Validation message: " + validationMsg);

	    // Screenshot evidence
	    String ssName = "InvalidEmailSignup_" +
	            (invalidEmail == null ? "null" : invalidEmail.replaceAll("[^a-zA-Z0-9]", "_")) +
	            "_" + System.currentTimeMillis();
	    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), ssName);
	    getTest().addScreenCaptureFromPath(screenshotPath);

	    // Assertions
	    Assert.assertFalse(isValid, "Browser considered invalid email as valid: " + invalidEmail);
	    Assert.assertNotNull(validationMsg, "Expected a browser validation message, got null");
	    Assert.assertFalse(validationMsg.isBlank(), "Expected a non-empty validation message");

	    // Ensure we did NOT navigate forward
	    Assert.assertFalse(loginPage.isAccountInfoPageDisplayed(),
	            "Navigated to Account Info page despite invalid email: " + invalidEmail);

	    // If expected copy provided, do 'contains' (not equals) for cross-browser/locale tolerance
	    if (expectedValidation != null && !expectedValidation.isBlank()) {
	        Assert.assertTrue(
	            validationMsg.toLowerCase().contains(expectedValidation.toLowerCase()),
	            "Validation text mismatch.\nExpected contains: " + expectedValidation + "\nActual: " + validationMsg
	        );
	    }

	    getTest().pass("Invalid email format correctly blocked sign up. Message logged and screenshot attached.");
	}

	// ===== DataProvider for invalid email signup =====
	@DataProvider(name = "invalidEmailSignUpData")
	public Object[][] invalidEmailSignUpData() throws IOException {
	    // Excel sheet "InvalidEmailData" with columns:
	    // name | invalidEmail | expectedValidation (optional)
	    return ExcelUtilities.getData("InvalidEmailData");
	}



    // Verify Login page loads
    @Test(groups = {"smoke","ui"}, priority = 1)
    public void verifyLoginPageIsDisplayed() throws Exception {
        getTest().info("Opening Login page via POM");
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page did not load properly");
        getTest().pass("Login page loaded successfully");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "LoginPageDisplayed");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    // Header: "Login to your account"
    @Test(groups = {"smoke","ui"}, priority = 2)
    public void verifyLoginHeaderVisible() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isLoginHeaderVisible(), "'Login to your account' header is not visible");
        getTest().pass("'Login to your account' header is visible");
    }

    // Header: "New User Signup!"
    @Test(groups = {"smoke","ui"}, priority = 2)
    public void verifyNewUserSignupHeaderVisible() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isNewUserSignupHeaderVisible(),
                "'New User Signup!' header is not visible");
        getTest().pass("'New User Signup!' header is visible");
    }

    // Verify Sign Up section
    @Test(groups = {"smoke","ui"}, priority = 3)
    public void verifySignUpSectionIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isSignUpSectionDisplayed(), "Sign Up section not displayed");
        getTest().pass("Sign Up section is displayed");
    }

    // Verify Sign Up button clickable
    @Test(groups = {"smoke","ui"}, priority = 3)
    public void verifySignUpButtonVisibleAndClickable() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isSignUpButtonVisibleAndClickable(), "Sign Up button not clickable");
        getTest().pass("Sign Up button is clickable");
    }

    // Verify Login button clickable
    @Test(groups = {"smoke","ui"}, priority = 3)
    public void verifyLoginButtonVisibleAndClickable() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();

        Assert.assertTrue(loginPage.isLoginButtonVisibleAndClickable(), "Login button not clickable");
        getTest().pass("Login button is clickable");
    }

    // Verify user registration with random email
    @Test(groups = {"smoke","functional","Regression"}, priority = 4)
    public void verifyUserRegistration() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        Assert.assertTrue(loginPage.isSignUpSectionDisplayed(), "Sign Up section is missing");

        String uniqueEmail = "user_" + System.currentTimeMillis() + "@example.com";
        loginPage.signUp("TestUser", uniqueEmail);

        Assert.assertTrue(loginPage.isAccountInfoPageDisplayed(), "Account Info page not displayed");
        getTest().pass("Sign Up initiated successfully");
    }

    // Verify Account Info header
    @Test(groups = {"smoke","ui"}, priority = 5)
    public void verifyEnterAccountInformationHeaderIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.signUp("HeaderCheckUser", "user_" + System.currentTimeMillis() + "@example.com");

        AccountInformationPage accountPage = new AccountInformationPage(getDriver());
        Assert.assertTrue(accountPage.isEnterAccountInformationVisible(),
                "'ENTER ACCOUNT INFORMATION' header not visible");
        getTest().pass("'ENTER ACCOUNT INFORMATION' header displayed");
    }

    // Verify Address Info header
    @Test(groups = {"smoke","ui"}, priority = 6)
    public void verifyAddressInformationHeaderIsDisplayed() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.signUp("AddressCheckUser", "addr_" + System.currentTimeMillis() + "@example.com");

        AccountInformationPage accountPage = new AccountInformationPage(getDriver());
        Assert.assertTrue(accountPage.isAddressInformationVisible(),
                "'ADDRESS INFORMATION' header not visible");
        getTest().pass("'ADDRESS INFORMATION' header displayed");
    }

    // Data-driven registration
    @Test(dataProvider = "registrationData",
          groups = {"smoke","functional","Regression","Data-Driven"},
          priority = 7)
    public void registerUserFromExcel(
            String name, String email, String password,
            String day, String month, String year,
            String newsletter, String offers,
            String firstName, String lastName,
            String company, String address1, String address2,
            String country, String state, String city,
            String zipcode, String mobile
    ) throws Exception {

        LoginPage loginPage = new LoginPage(getDriver()).open();
        Assert.assertTrue(loginPage.isSignUpSectionDisplayed(), "Sign Up section not visible");

        if (email == null || email.isBlank() || email.contains("{{unique}}")) {
            email = (name == null || name.isEmpty() ? "user" : name.toLowerCase().replaceAll("\\s+",""))
                    + "_" + System.currentTimeMillis() + "@example.com";
        }
        loginPage.signUp(name, email);

        AccountInformationPage accountPage = new AccountInformationPage(getDriver());
        Assert.assertTrue(accountPage.isAccountInfoPageDisplayed(),
                "Account Info page not displayed");

        accountPage
                .selectTitleMr()
                .setPassword(password)
                .setDOB(day, month, year)
                .toggleNewsletter(parseBooleanFlexible(newsletter))
                .toggleOffers(parseBooleanFlexible(offers))
                .setFirstName(firstName)
                .setLastName(lastName)
                .setCompany(company)
                .setAddress1(address1)
                .setAddress2(address2)
                .setCountry(country)
                .setState(state)
                .setCity(city)
                .setZipcode(zipcode)
                .setMobile(mobile)
                .submitCreateAccount();

        Assert.assertTrue(accountPage.isAccountCreatedMessageVisible(),
                "Account Created message not displayed");
        getTest().pass("User registered successfully: " + email);

        accountPage.clickContinueAfterCreated();
        Assert.assertTrue(accountPage.isLoggedInHeaderVisible(),
                "Logged-in header not visible after registration");
    }

    // Verify login
    @Test(dataProvider = "logindata",
          groups = {"smoke","functional","Regression"},
          priority = 8)
    public void verifyLogin(String username, String password) throws IOException {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.login(username, password);

        if (loginPage.isLoginSuccessful()) {
            getTest().pass("Login successful for: " + username);
        } else {
            String error = loginPage.getErrorMessage();
            getTest().fail("Login failed for: " + username + " | " + error);
            Assert.fail("Login failed for: " + username + " | " + error);
        }
    }

    // Verify logout
    @Test(dataProvider = "logindata",
          groups = {"smoke","functional","Regression"},
          priority = 9,
          dependsOnMethods = "verifyLogin")
    public void verifyLogout(String username, String password) throws IOException {
        LoginPage loginPage = new LoginPage(getDriver()).open();
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Precondition: login failed");

        loginPage.logout();
        Assert.assertTrue(loginPage.isLogoutSuccessful(), "Logout not successful");
        getTest().pass("Logout successful for: " + username);
    }

    /** Helper: interpret boolean-like strings from Excel */
    private boolean parseBooleanFlexible(String val) {
        if (val == null) return false;
        String v = val.trim().toLowerCase();
        return v.equals("true") || v.equals("yes") || v.equals("y") || v.equals("1");
    }

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() throws IOException {
        // No Extent logging here → avoids NPE
        return ExcelUtilities.getData("Sheet1");
    }

    @DataProvider(name = "logindata")
    public Object[][] logindata() throws IOException {
        // No Extent logging here → avoids NPE
        return ExcelUtilities.getData("DynamicData");
    }
    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() throws IOException {
        // Excel sheet should exist with columns: email | password | expectedError (optional)
        // Example sheet name: "InvalidLoginData"
        return ExcelUtilities.getData("InvalidLoginData");
    }

}
