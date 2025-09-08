package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.PaymentPage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;

public class PaymentPageTest extends BaseTest {

    private PaymentPage paymentPage;

    @DataProvider(name = "CartData")
    public Object[][] getCartData() throws Exception {
        return ExcelUtilities.getData("Sheet2");
    }

    @DataProvider(name = "SingleCartData")
    public Object[][] getSingleCartData() throws Exception {
        Object[][] allData = ExcelUtilities.getData("Sheet2");
        return new Object[][] { allData[0] }; // pick first row only
    }

    @DataProvider(name = "PaymentData")
    public Object[][] getPaymentData() throws IOException {
        return ExcelUtilities.getData("Payment");
    }

    /**
     * For each test method we:
     *  1) Open site
     *  2) Log in (using first row from Sheet2)
     *  3) Add a product to cart (first row product)
     *  4) Go to Cart -> Proceed to Checkout -> Place Order (land on /payment)
     */
    @BeforeMethod(alwaysRun = true)
    public void goToPaymentPage() throws Exception {
        paymentPage = new PaymentPage(getDriver());

        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email       = allData[0][0].toString().trim();
        String password    = allData[0][1].toString().trim();
        String productId   = allData[0][2].toString().trim();
        String productName = allData[0][3].toString().trim();

        // Open site
        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        // Login
        paymentPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        // Add product
        paymentPage.goToProductsPage();
        paymentPage.clickAddToCart(productId);
        getTest().info("Added product: " + productName);

        // View Cart
        paymentPage.clickViewCart();
        getTest().info("Navigated to Cart Page");

        // Verify product in cart
        Assert.assertTrue(
            paymentPage.isProductInCart(productName),
            "❌ Product not found in cart: " + productName
        );

        // Proceed to Checkout
        paymentPage.clickProceedToCheckout();
        Assert.assertTrue(
            paymentPage.isCheckoutPageDisplayed(),
            "Checkout page not displayed!"
        );

        String checkoutScreenshot = paymentPage.captueCartPageScreenshot("CheckoutPage");
        if (checkoutScreenshot != null) getTest().addScreenCaptureFromPath(checkoutScreenshot);

        // Place Order (land on /payment)
        paymentPage.clickPlaceOrderAndWait();
        getTest().info("Clicked Place Order");
    }

    // ---- Field validations on the payment page ----

    @Test(dataProvider = "PaymentData", priority = 1)
    public void testNameOnCardValidation(String name, String cardNo, String cvc, String month, String year) throws Exception {
        paymentPage.enterNameOnCard(name);
        String shot = ScreenshotUtilities.captureScreen(getDriver(), "NameCheck_" + name);
        if (shot != null) getTest().addScreenCaptureFromPath(shot);
        Assert.assertTrue(paymentPage.isNameAlphabetic(name), "❌ Invalid name: " + name);
    }

    @Test(dataProvider = "PaymentData", priority = 2)
    public void testCardNumberValidation(String name, String cardNo, String cvc, String month, String year) throws Exception {
        paymentPage.enterCardNumber(cardNo);
        String shot = ScreenshotUtilities.captureScreen(getDriver(), "CardNumberCheck_" + cardNo);
        if (shot != null) getTest().addScreenCaptureFromPath(shot);
        Assert.assertTrue(paymentPage.isCardNumberNumeric(cardNo), "❌ Invalid card: " + cardNo);
    }

    @Test(dataProvider = "PaymentData", priority = 3)
    public void testCvcNumericValidation(String name, String cardNo, String cvc, String month, String year) throws Exception {
        paymentPage.enterCVC(cvc);
        String shot = ScreenshotUtilities.captureScreen(getDriver(), "CVCCheck_" + cvc);
        if (shot != null) getTest().addScreenCaptureFromPath(shot);
        Assert.assertTrue(paymentPage.isCvcNumeric(cvc), "❌ Invalid CVC: " + cvc);
    }

    @Test(dataProvider = "PaymentData", priority = 4)
    public void testCvcThreeDigitValidation(String name, String cardNo, String cvc, String month, String year) {
        Assert.assertTrue(paymentPage.isCvcThreeDigits(cvc), "❌ CVC not 3 digits: " + cvc);
    }

    @Test(dataProvider = "PaymentData", priority = 5)
    public void testExpiryValidation(String name, String cardNo, String cvc, String month, String year) throws Exception {
        paymentPage.enterExpiry(month, year);
        String shot = ScreenshotUtilities.captureScreen(getDriver(), "ExpiryCheck_" + month + "_" + year);
        if (shot != null) getTest().addScreenCaptureFromPath(shot);
        Assert.assertTrue(paymentPage.isExpiryValid(month, year), "❌ Invalid expiry: " + month + "/" + year);
    }

    @Test(dataProvider = "PaymentData", priority = 6)
    public void testPlaceOrderAndDownloadInvoice(String name, String cardNo, String cvc, String month, String year) throws Exception {
        if (paymentPage.isNameAlphabetic(name) &&
            paymentPage.isCardNumberNumeric(cardNo) &&
            paymentPage.isCvcThreeDigits(cvc) &&
            paymentPage.isExpiryValid(month, year)) {

            paymentPage.clickPayAndConfirm();
            Assert.assertTrue(paymentPage.isOrderPlacedMessageDisplayed(), "❌ Order not placed!");
            String shot1 = ScreenshotUtilities.captureScreen(getDriver(), "OrderPlaced_" + name);
            if (shot1 != null) getTest().addScreenCaptureFromPath(shot1);

            Assert.assertTrue(paymentPage.isDownloadInvoiceButtonDisplayed(), "❌ Invoice button missing!");
            String shot2 = ScreenshotUtilities.captureScreen(getDriver(), "Invoice_" + name);
            if (shot2 != null) getTest().addScreenCaptureFromPath(shot2);

            paymentPage.clickDownloadInvoice();
        } else {
            getTest().info("Form inputs invalid; skipping payment submission for this data row.");
        }
    }
}
