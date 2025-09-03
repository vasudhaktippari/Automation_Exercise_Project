package com.automationexercise.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.utilities.ScreenshotUtilities;

public class HomePageTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com/";

    @Test(groups = {"functional","smoke","Regression","ui"})
    public void verifyHomePageTitle() throws Exception {

        getTest().info("Opening Home Page: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage homePage = new HomePage(getDriver());

        getTest().info("Current URL after navigation: " + getDriver().getCurrentUrl());

        getTest().info("Fetching page title from HomePage");
        String expectedTitle = "Automation Exercise";
        String actualTitle   = homePage.getPageTitle();

        getTest().info("Expected Title: " + expectedTitle);
        getTest().info("Actual Title: " + actualTitle);

        getTest().info("Asserting page title equals expected");
        Assert.assertEquals(actualTitle, expectedTitle, "Home Page title did not match");

        getTest().pass("Home Page title verified successfully: " + actualTitle);

        getTest().info("Capturing full-page screenshot for evidence");
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "HomePageTitle");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    @Test(groups = {"ui","smoke","Regression"})
    public void verifyLogoIsDisplayed() throws Exception {

        getTest().info("Opening Home Page: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage homePage = new HomePage(getDriver());

        getTest().info("Verifying that the site logo is visible on the Home Page");
        boolean logoVisible = homePage.isLogoDisplayed();
        getTest().info("Logo visibility state: " + logoVisible);

        Assert.assertTrue(logoVisible, "Logo is not displayed on the Home Page");
        getTest().pass("Logo is displayed successfully on Home Page");

        getTest().info("Capturing logo-only screenshot via POM helper");
        String logoShot = homePage.captureLogoScreenshot("HomePageLogo");

        getTest().info("Validating logo screenshot path is not null");
        Assert.assertNotNull(logoShot, "Failed to capture logo screenshot");

        getTest().addScreenCaptureFromPath(logoShot);
        getTest().info("Logo screenshot attached to report");
    }
}
