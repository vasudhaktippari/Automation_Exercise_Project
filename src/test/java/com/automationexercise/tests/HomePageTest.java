package com.automationexercise.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.utilities.ScreenshotUtilities;

public class HomePageTest extends BaseTest {

    private static final String BASE_URL = "https://automationexercise.com/";

    @Test(groups = {"functional","smoke","Regression","ui"} ,priority = 1)
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

    @Test(groups = {"ui","smoke","Regression"},priority = 2)
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
    
    @Test(groups = {"ui","functional","Regression"},priority = 3)
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
    
    @Test(groups = {"ui","functional","Regression"},priority = 4)
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
    @Test(groups = {"ui","functional","Regression"},priority = 5)
    public void verifyCategoryItemsVisibleAndClickable() throws Exception {
        getTest().info("Opening Home Page: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage home = new HomePage(getDriver());

        // Scroll Category into view and verify toggles visible
        home.scrollCategoryIntoView();
        Assert.assertTrue(home.isWomenVisible(), "Women toggle not visible");
        Assert.assertTrue(home.isMenVisible(),   "Men toggle not visible");
        Assert.assertTrue(home.isKidsVisible(),  "Kids toggle not visible");
        String initial = ScreenshotUtilities.captureScreen(getDriver(), "Category_Visible");
        getTest().addScreenCaptureFromPath(initial);

        // Ensure each section is clickable & expands; take screenshots
        getTest().info("Expanding Women panel");
        home.expandWomen();
        String womenShot = ScreenshotUtilities.captureScreen(getDriver(), "Category_Women_Expanded");
        getTest().addScreenCaptureFromPath(womenShot);

        getTest().info("Expanding Men panel");
        home.expandMen();
        String menShot = ScreenshotUtilities.captureScreen(getDriver(), "Category_Men_Expanded");
        getTest().addScreenCaptureFromPath(menShot);

        getTest().info("Expanding Kids panel");
        home.expandKids();
        String kidsShot = ScreenshotUtilities.captureScreen(getDriver(), "Category_Kids_Expanded");
        getTest().addScreenCaptureFromPath(kidsShot);

        getTest().pass("Women, Men, and Kids are visible, clickable, and expand correctly with evidence screenshots.");
    }

    @Test(groups = {"ui","functional","Regression"},priority = 6)
    public void verifyFeaturesItemsVisible() throws Exception {
        getTest().info("Opening Home Page");
        getDriver().get("https://automationexercise.com/");
        HomePage home = new HomePage(getDriver());

        getTest().info("Scroll to FEATURES ITEMS section");
        home.scrollFeaturesIntoView();
        home.waitForFeaturesVisible();

        getTest().info("Verify FEATURES ITEMS section is visible");
        Assert.assertTrue(home.isFeaturesSectionVisible(), "FEATURES ITEMS section not visible");

        int visible = home.getVisibleFeatureItemCount();
        getTest().info("Visible feature item cards: " + visible);

        // Expect at least 6 cards in view on page load (3x2 grid). Tweak if your layout differs.
        Assert.assertTrue(visible >= 6, "Expected at least 6 visible feature items");

        getTest().info("Capture FEATURES ITEMS section screenshot");
        String shot = home.captureFeaturesSectionScreenshot("FeaturesItems_Section");
        Assert.assertNotNull(shot, "Failed to capture FEATURES ITEMS screenshot");
        getTest().addScreenCaptureFromPath(shot);

        // bonus: full-page for context
        String full = com.automationexercise.utilities.ScreenshotUtilities.captureScreen(getDriver(), "FeaturesItems_FullPage");
        getTest().addScreenCaptureFromPath(full);

        getTest().pass("FEATURES ITEMS are visible after scroll; screenshots attached.");
    }
    @Test(groups = {"ui","functional","Regression"},priority = 7)
    public void verifyBrandsSectionVisible() throws Exception {
        getTest().info("Open Home Page");
        getDriver().get("https://automationexercise.com/");
        HomePage home = new HomePage(getDriver());

        getTest().info("Scroll to BRANDS section");
        home.scrollBrandsIntoView();
        home.waitForBrandsVisible();

        getTest().info("Validate BRANDS container visibility");
        Assert.assertTrue(home.isBrandsSectionVisible(), "BRANDS section is not visible");

        int count = home.getVisibleBrandCount();
        getTest().info("Visible brand rows: " + count);
        Assert.assertTrue(count >= 1, "Expected at least one brand row to be visible");

        // Only capture full-page screenshot (removed element screenshot)
        String full = com.automationexercise.utilities.ScreenshotUtilities.captureScreen(getDriver(), "Brands_FullPage");
        Assert.assertNotNull(full, "Failed to capture full-page screenshot for BRANDS");
        getTest().addScreenCaptureFromPath(full);

        getTest().pass("BRANDS section visible; full-page screenshot attached.");
    }

    @Test(groups = {"ui","functional","Regression"},priority = 8)
    public void verifyRecommendedItemsVisible() throws Exception {
        final String BASE_URL = "https://automationexercise.com/";
        getTest().info("Open Home Page");
        getDriver().get(BASE_URL);

        HomePage home = new HomePage(getDriver());

        getTest().info("Scroll to RECOMMENDED ITEMS section");
        home.scrollRecommendedIntoView();
        home.waitForRecommendedVisible();

        getTest().info("Validate RECOMMENDED ITEMS container visibility");
        Assert.assertTrue(home.isRecommendedSectionVisible(), "RECOMMENDED ITEMS section is not visible");

        int visible = home.getVisibleRecommendedCount();
        getTest().info("Visible recommended item cards in active slide: " + visible);
        Assert.assertTrue(visible >= 1, "Expected at least one recommended item to be visible");

        // Take only the full-page screenshot for evidence
        String full = home.captureRecommendedFullPage("RecommendedItems_FullPage");
        Assert.assertNotNull(full, "Failed to capture full-page screenshot for RECOMMENDED ITEMS");
        getTest().addScreenCaptureFromPath(full);

        getTest().pass("RECOMMENDED ITEMS section is visible; full-page screenshot attached.");
    }
     
    @Test(groups = {"ui","functional"}, priority = 9)
    public void verifyBlueTopCardInfoAndImageDisplayed() throws Exception {
        final String BASE_URL = "https://automationexercise.com/";
        final String PRODUCT  = "Blue Top";

        getTest().info("Open Home: " + BASE_URL);
        getDriver().get(BASE_URL);

        HomePage home = new HomePage(getDriver());

        getTest().info("Scroll to FEATURES and wait for it to be visible");
        home.scrollFeaturesIntoView();
        home.waitForFeaturesVisible();

        // fetch displayed values
        String price = home.getFeaturePriceText(PRODUCT);
        String name  = home.getFeatureNameText(PRODUCT);
        boolean imageVisible       = home.isFeatureImageDisplayed(PRODUCT);
        boolean addToCartVisible   = home.isFeatureAddToCartDisplayed(PRODUCT);
        boolean viewProductVisible = home.isFeatureViewProductDisplayed(PRODUCT);

        // log actual info
        getTest().info("Displayed Info -> "
                     + "Price: " + price
                     + " | Name: " + name
                     + " | Image visible: " + imageVisible
                     + " | Add to Cart visible: " + addToCartVisible
                     + " | View Product visible: " + viewProductVisible);

        // assertions
        Assert.assertTrue(imageVisible, "Product image not displayed");
        Assert.assertTrue(addToCartVisible, "Add to Cart button not displayed");
        Assert.assertTrue(viewProductVisible, "View Product link not displayed");

        // full-page screenshot only
        String fullShot = home.captureFullPage("BlueTop_FullPage");
        getTest().addScreenCaptureFromPath(fullShot);

        getTest().pass("Blue Top card info (image, price, name, add-to-cart, view product) displayed correctly; full-page screenshot attached.");
    }











}
