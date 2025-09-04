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
    
    @Test(groups = {"ui","functional","Regression"})
    public void verifyNavigationFromHomePage() throws Exception {
        getTest().info("Opening Home Page: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage homePage = new HomePage(getDriver());

        // Navigate to Products
        getTest().info("Navigating to Products page");
        homePage.goToProducts();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/products"), "Products page URL mismatch");
        String productsShot = ScreenshotUtilities.captureScreen(getDriver(), "ProductsPage");
        getTest().addScreenCaptureFromPath(productsShot);
        getTest().info("Screenshot captured for Products page");

        // Navigate to Cart
        getDriver().navigate().back();
        getTest().info("Navigating to Cart page");
        homePage.goToCart();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/view_cart"), "Cart page URL mismatch");
        String cartShot = ScreenshotUtilities.captureScreen(getDriver(), "CartPage");
        getTest().addScreenCaptureFromPath(cartShot);
        getTest().info("Screenshot captured for Cart page");

        // Navigate to Login
        getDriver().navigate().back();
        getTest().info("Navigating to Login page");
        homePage.goToLogin();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/login"), "Login page URL mismatch");
        String loginShot = ScreenshotUtilities.captureScreen(getDriver(), "LoginPage");
        getTest().addScreenCaptureFromPath(loginShot);
        getTest().info("Screenshot captured for Login page");

        // Navigate to Test Cases
        getDriver().navigate().back();
        getTest().info("Navigating to Test Cases page");
        homePage.goToTestCases();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/test_cases"), "Test Cases page URL mismatch");
        String testCasesShot = ScreenshotUtilities.captureScreen(getDriver(), "TestCasesPage");
        getTest().addScreenCaptureFromPath(testCasesShot);
        getTest().info("Screenshot captured for Test Cases page");

        // Navigate to API Testing
        getDriver().navigate().back();
        getTest().info("Navigating to API Testing page");
        homePage.goToApiTesting();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/api_list"), "API Testing page URL mismatch");
        String apiShot = ScreenshotUtilities.captureScreen(getDriver(), "ApiTestingPage");
        getTest().addScreenCaptureFromPath(apiShot);
        getTest().info("Screenshot captured for API Testing page");

        // Navigate to Contact Us
        getDriver().navigate().back();
        getTest().info("Navigating to Contact Us page");
        homePage.goToContactUs();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/contact_us"), "Contact Us page URL mismatch");
        String contactShot = ScreenshotUtilities.captureScreen(getDriver(), "ContactUsPage");
        getTest().addScreenCaptureFromPath(contactShot);
        getTest().info("Screenshot captured for Contact Us page");

        getTest().pass("Navigation from Home Page verified successfully for all links with screenshots");
    }
    
    @Test(groups = {"ui","functional","Regression"})
    public void verifySliderNavigation() throws Exception {
        getTest().info("Opening Home Page: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage homePage = new HomePage(getDriver());
        homePage.waitForSliderVisible();

        // Initial index
        String startIdx = homePage.getActiveSlideIndex();
        getTest().info("Initial slide index: " + startIdx);

        // Next ( > )
        getTest().info("Click Next ( > )");
        homePage.clickNextSlider();
        homePage.waitForSlideIndexChange(startIdx);
        String nextIdx = homePage.getActiveSlideIndex();
        getTest().info("After Next, index: " + nextIdx);
        Assert.assertNotEquals(nextIdx, startIdx, "Next slide index did not change");
        String nextShot = ScreenshotUtilities.captureScreen(getDriver(), "SliderNext");
        getTest().addScreenCaptureFromPath(nextShot);

        // Previous ( < )
        getTest().info("Click Previous ( < )");
        homePage.clickPreviousSlider();
        homePage.waitForSlideIndexChange(nextIdx);
        String backIdx = homePage.getActiveSlideIndex();
        getTest().info("After Previous, index: " + backIdx);
        Assert.assertEquals(backIdx, startIdx, "Previous did not return to initial slide");
        String prevShot = ScreenshotUtilities.captureScreen(getDriver(), "SliderPrevious");
        getTest().addScreenCaptureFromPath(prevShot);

        getTest().pass("Slider arrows (< and >) change the active slide index correctly.");
    }

    @Test(groups = {"ui","functional","Regression"})
    public void verifyCarouselButtonsNavigation() throws Exception {
        getTest().info("Opening Home Page: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage home = new HomePage(getDriver());

        // -------- Test Cases --------
        getTest().info("Clicking 'Test Cases' button in carousel");
        home.clickCarouselTestCases();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/test_cases"),
                "Carousel 'Test Cases' did not navigate correctly");
        String tcShot = ScreenshotUtilities.captureScreen(getDriver(), "Carousel_TestCases");
        getTest().addScreenCaptureFromPath(tcShot);
        getTest().info("Going Back to Home Page");

        // Back to Home Page
        getDriver().navigate().back();

        // -------- APIs list --------
        getTest().info("Clicking 'APIs list for practice' button in carousel");
        home.clickCarouselApiList();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/api_list"),
                "Carousel 'APIs list for practice' did not navigate correctly");
        String apiShot = ScreenshotUtilities.captureScreen(getDriver(), "Carousel_ApiList");
        getTest().addScreenCaptureFromPath(apiShot);

        getTest().pass("Carousel buttons navigation verified successfully");
    }




}
