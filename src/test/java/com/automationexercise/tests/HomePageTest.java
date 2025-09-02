package com.automationexercise.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.utilities.ScreenshotUtilities;

public class HomePageTest extends BaseTest {

    @Test(groups = {"functional","smoke","Regression","ui"})
    public void verifyHomePageTitle() throws Exception {
        getDriver().get("https://automationexercise.com/");
        HomePage homePage = new HomePage(getDriver());

        String expectedTitle = "Automation Exercise";
        String actualTitle   = homePage.getPageTitle();

        getTest().info("Expected Title: " + expectedTitle);
        getTest().info("Actual Title: " + actualTitle);

        Assert.assertEquals(actualTitle, expectedTitle, "Home Page title did not match");
        getTest().pass("Home Page title verified successfully: " + actualTitle);

        // Full page screenshot
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "HomePageTitle");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    @Test(groups = {"ui","smoke","Regression"})
    public void verifyLogoIsDisplayed() throws Exception {
        getDriver().get("https://automationexercise.com/");
        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not displayed on the Home Page");
        getTest().pass("Logo is displayed successfully on Home Page");

        // Capture only the logo via POM helper
        String logoShot = homePage.captureLogoScreenshot("HomePageLogo");
        Assert.assertNotNull(logoShot, "Failed to capture logo screenshot");
        getTest().addScreenCaptureFromPath(logoShot);
    }
}
