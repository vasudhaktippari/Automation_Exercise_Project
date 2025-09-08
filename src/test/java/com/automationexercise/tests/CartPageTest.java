package com.automationexercise.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.utilities.ExcelUtilities;

public class CartPageTest extends BaseTest {

    // Excel Sheet2 (all rows)
    @DataProvider(name = "CartData")
    public Object[][] getCartData() throws Exception {
        return ExcelUtilities.getData("Sheet2");
    }

    // Just first row from Sheet2
    @DataProvider(name = "SingleCartData")
    public Object[][] getSingleCartData() throws Exception {
        Object[][] allData = ExcelUtilities.getData("Sheet2");
        return new Object[][] { allData[0] }; // pick first row
    }

    @Test(groups = {"ui"}, priority = 1)
    public void VeriryCartPageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");
        new CartPage(getDriver()); // initialize static driver in POM
        CartPage.ClcikCart();
        getTest().pass("Cart page is displayed");
    }

    @Test(groups = {"ui"}, priority = 2)
    public void VeriryHomePageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClickHome();
        getTest().pass("Home page is displayed");
    }

    @Test(groups = {"ui"}, priority = 3)
    public void VeriryProductPageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClcikPrduct();
        getTest().pass("Product page is displayed");
    }

    @Test(groups = {"ui"}, priority = 4)
    public void VeriryLoginPageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClickLogin();
        getTest().pass("Login page is displayed");
    }

    @Test(groups = {"ui"}, priority = 5)
    public void VeriryApiPageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClickApiList();
        getTest().pass("API List page is displayed");
    }

    @Test(groups = {"ui"}, priority = 6)
    public void VeriryTestCasePageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClickTestCase();
        getTest().pass("TestCase page is displayed");
    }

    @Test(groups = {"ui"}, priority = 7)
    public void VeriryContctUsPageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClickContactUs();
        getTest().pass("ContactUs page is displayed");
    }

    @Test(groups = {"ui"}, priority = 8)
    public void VeriryVedioTutorialPageDisplyed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
        new CartPage(getDriver());
        CartPage.ClickVedioTutorial();
        getTest().pass("Video tutorial header clicked");
    }

    @Test(groups = {"functional","ui"}, priority = 9)
    public void verifyLogoIsDisplayed() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not displayed on the Cart Page");
        getTest().pass("Logo is displayed successfully on Cart Page");

        String logoShot = homePage.captureLogoScreenshot("CartPageLogo");
        Assert.assertNotNull(logoShot, "Failed to capture logo screenshot");
        getTest().addScreenCaptureFromPath(logoShot);
    }

    @Test(groups = {"functional","ui"}, priority = 10)
    public void ClickOnLogo() throws Exception {
        getDriver().get("https://automationexercise.com/view_cart");
        CartPage cartPage = new CartPage(getDriver());

        Assert.assertTrue(cartPage.isLogoDisplayed(), "Logo is not displayed on the Cart Page");
        getTest().pass("Logo is displayed successfully on Cart Page");

        CartPage.ClickLogo();
        getTest().pass("Logo click redirected to homepage");
    }

    // Scroll verification
    @Test(dataProvider = "SingleCartData", groups = {"functional","ui"}, priority = 11)
    public void verifyScroll(Object... row) throws Exception {
        String productId   = row[2].toString().trim();
        String productName = row[3].toString().trim();

        CartPage cartPage = new CartPage(getDriver());

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId);
        getTest().info("Added first product: " + productName);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page");

        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product not found in cart: " + productName);

        cartPage.scrollToFooter();
        Assert.assertTrue(CartPage.isFooterDisplayed(), "Footer is not displayed after scrolling down!");
        String down = cartPage.captueCartPageScreenshot("Cart_Page_Scrolled_Down");
        Assert.assertNotNull(down, "Failed to capture scroll down ");
        getTest().addScreenCaptureFromPath(down);

        cartPage.scrollToHeader();
        Assert.assertTrue(CartPage.isHeaderDisplayed(), "Header is not displayed after scrolling up!");
        String up = cartPage.captueCartPageScreenshot("Cart_Page_Scrolled_Up");
        Assert.assertNotNull(up, "Failed to capture scroll up ");
        getTest().addScreenCaptureFromPath(up);
    }

    // Add first product without login
    @Test(dataProvider = "SingleCartData", groups = {"smoke"}, priority = 12)
    public void addFirstProduct(Object... row) {
        String productId   = row[2].toString().trim();
        String productName = row[3].toString().trim();

        CartPage cartPage = new CartPage(getDriver());

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId);
        getTest().info("Added first product: " + productName);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page");

        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product not found in cart: " + productName);

        getTest().pass("Verified product in cart: " + productName);
        String shot = cartPage.captueCartPageScreenshot("Cart_Page");
        Assert.assertNotNull(shot, "Failed to capture cart screenshot");
        getTest().addScreenCaptureFromPath(shot);
    }

    // Login and add more products (runs with a fresh driver; does its own login)
    @Test(dependsOnMethods = "addFirstProduct", groups = {"functional"}, priority = 13)
    public void loginAndAddSecondAndThirdProduct() throws Exception {
        Object[][] allData = ExcelUtilities.getData("Sheet2");

        String email       = allData[0][0].toString().trim();
        String password    = allData[0][1].toString().trim();

        CartPage cartPage = new CartPage(getDriver());

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opening home page");
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        String productId2   = allData[0][2].toString().trim();
        String productName2 = allData[0][3].toString().trim();

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId2);
        getTest().info("Added second product: " + productName2);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page after adding second product");

        Assert.assertTrue(cartPage.isProductInCart(productName2),
                "Second product not found in cart: " + productName2);

        String productId3   = allData[1][2].toString().trim();
        String productName3 = allData[1][3].toString().trim();

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId3);
        getTest().info("Added third product: " + productName3);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page after adding third product");

        Assert.assertTrue(cartPage.isProductInCart(productName3),
                "Third product not found in cart: " + productName3);

        String shot = cartPage.captueCartPageScreenshot("Cart_Page");
        Assert.assertNotNull(shot, "Failed to capture cart screenshot");
        getTest().addScreenCaptureFromPath(shot);
    }

    @Test(groups = {"functional"}, priority = 14)
    public void RemoveProductFromCart() throws Exception {
        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email    = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();

        CartPage cartPage = new CartPage(getDriver());

        getDriver().get("https://automationexercise.com/");
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        cartPage.ViewCart();
        cartPage.RemoveCartItem();
        String shot = cartPage.captueCartPageScreenshot("Cart_Page_After_Removing_Items");
        Assert.assertNotNull(shot, "Failed to capture cart screenshot");
        getTest().addScreenCaptureFromPath(shot);
    }

    @Test(groups = {"functional","ui"}, priority = 15)
    public void clickHere() throws Exception{
        CartPage cartPage = new CartPage(getDriver());

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");
        cartPage.ViewCart();
        String shot = cartPage.captueCartPageScreenshot("Clickhere_On_CartPage");
        Assert.assertNotNull(shot, "Failed to capture cart screenshot");
        getTest().addScreenCaptureFromPath(shot);
    }

    @Test(dataProvider = "SingleCartData", groups = {"functional","Regression","smoke"}, priority = 16)
    public void clikOnCkeckoutWithouLogin(Object... row) throws Exception {
        CartPage cartPage = new CartPage(getDriver());
        String productId   = row[2].toString().trim();
        String productName = row[3].toString().trim();

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId);
        getTest().info("Added first product: " + productName);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page");

        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product not found in cart: " + productName);
        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(), "CartPage not displayed");

        String shot = cartPage.captueCartPageScreenshot("Checkout_Page_No_Login");
        Assert.assertNotNull(shot, "Failed to capture Checkout page");
        getTest().addScreenCaptureFromPath(shot);
    }

    @Test(dataProvider = "SingleCartData", groups = {"functional","ui"}, priority = 17)
    public void clikOnCkeckoutDisplaysLogin(Object... row) throws Exception {
        CartPage cartPage = new CartPage(getDriver());
        String productId   = row[2].toString().trim();
        String productName = row[3].toString().trim();

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId);
        getTest().info("Added first product: " + productName);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page");
        getTest().info("CartPage is Displayed");

        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product not found in cart: " + productName);

        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(), "CartPage not displayed");
        String shot1 = cartPage.captueCartPageScreenshot("Checkout_Page");
        Assert.assertNotNull(shot1, "Failed to capture Checkout page");
        getTest().addScreenCaptureFromPath(shot1);

        cartPage.LoginToProceedToCheckout();
        String shot2 = cartPage.captueCartPageScreenshot("LoginPage_Displayed");
        Assert.assertNotNull(shot2, "Failed to capture Login page");
        getTest().addScreenCaptureFromPath(shot2);
        getTest().info("User is asked to register before CheckOut");
    }

    @Test(dataProvider = "SingleCartData", groups = {"functional","Regression","smoke","ui"}, priority = 18)
    public void ProceedToCheckOut(Object... row) throws Exception {
        CartPage cartPage = new CartPage(getDriver());

        String productId   = row[2].toString().trim();
        String productName = row[3].toString().trim();

        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email       = allData[0][0].toString().trim();
        String password    = allData[0][1].toString().trim();

        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId);
        getTest().info("Added product: " + productName);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page");

        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product not found in cart: " + productName);

        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(),
                "Checkout not reached / login prompt missing");
        String checkoutBeforeLogin = cartPage.captueCartPageScreenshot("CheckoutPrompt");
        getTest().addScreenCaptureFromPath(checkoutBeforeLogin);

        cartPage.LoginToProceedToCheckout();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(),
                "Checkout page not displayed after login");

        String finalCheckout = cartPage.captueCartPageScreenshot("CheckoutAfterLogin");
        getTest().addScreenCaptureFromPath(finalCheckout);
        getTest().info("Checkout page displayed successfully after login");
    }

    @Test(groups = {"functional","Regression"}, priority = 19)
    public void AddCommentAndPlaceOrder() throws Exception {
        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        CartPage cartPage = new CartPage(getDriver());
        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email       = allData[0][0].toString().trim();
        String password    = allData[0][1].toString().trim();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        cartPage.enterComment("Please deliver between 9AM-11AM");

        Assert.assertTrue(cartPage.isCommentEntered(), "Comment was not entered into the box!");

        String commentShot = cartPage.captueCartPageScreenshot("Comment_Added_In_CheckoutPage");
        getTest().addScreenCaptureFromPath(commentShot);

        cartPage.clickPlaceOrder();
        String placeOrderShot = cartPage.captueCartPageScreenshot("Place_Order_Clicked");
        getTest().addScreenCaptureFromPath(placeOrderShot);
    }

    @Test(groups = {"functional","Regression","ui"}, priority = 20)
    public void ClickOnArrowAndPlaceOrderBtn() throws Exception {
        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        CartPage cartPage = new CartPage(getDriver());
        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email       = allData[0][0].toString().trim();
        String password    = allData[0][1].toString().trim();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        cartPage.ClickUpArrow();
        getTest().info("Navigate to top of the page");

        cartPage.clickPlaceOrderAndWait();
        String shot = cartPage.captueCartPageScreenshot("Place_Order_Clicked");
        getTest().addScreenCaptureFromPath(shot);
    }

    @Test(groups = {"ui"}, priority = 21)
    public void VerifyAddressIsDisplayed() throws Exception {
        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");
        CartPage cartPage = new CartPage(getDriver());

        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email       = allData[0][0].toString().trim();
        String password    = allData[0][1].toString().trim();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isAddressDisplayed(), "Address section not visible on checkout!");
        String addr = cartPage.captueCartPageScreenshot("Address_Displayed_in_CheckoutPage");
        getTest().addScreenCaptureFromPath(addr);
        getTest().info("Checkout page displayed successfully and contains Address field");
    }
}

