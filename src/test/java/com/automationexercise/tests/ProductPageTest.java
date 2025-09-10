package com.automationexercise.tests;

import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.ProductPage;
import com.automationexercise.base.BaseTest;
import com.automationexercise.utilities.ClickUtils;
import com.automationexercise.utilities.ScreenshotUtilities;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductPageTest extends BaseTest {

    
    @Test(groups = {"smoke"}, priority = 1)
    public void verifyProductPageLoads() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        Assert.assertTrue(productPage.isProductPageDisplayed(), "Product page did not load properly");

        getTest().pass("Product page loaded successfully");
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "ProductPage");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    //P
    @Test(groups = {"smoke", "functional"}, priority = 2)
    public void verifyAddProductToCart() throws Exception {
        getDriver().get("https://automationexercise.com/products");

        // Suppress ads and iframes before interacting with elements
        ClickUtils.suppressAds(getDriver());

        // Wait for any ads to completely disappear (increase timeout if needed)
        Thread.sleep(2000); // Optional: ensure ads have been hidden

        ProductPage productPage = new ProductPage(getDriver());

        try {
            productPage.addFirstProductToCart(); // Assuming this triggers the add-to-cart action
        } catch (ElementClickInterceptedException e) {
            // Fallback: Use JavaScript click if the element is still intercepted
            WebElement addToCartButton = getDriver().findElement(By.xpath("//a[contains(text(),'Add to cart')]"));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", addToCartButton);
        }

        productPage.goToCart();

        Assert.assertTrue(productPage.isProductInCart(""), "Product was not added to the cart");

        getTest().pass("Product added to cart successfully");
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "ProductAddedToCart");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    //P
    @Test(groups = {"functional"}, priority = 3)
    public void verifyProductPriceIsDisplayed() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        String price = productPage.getFirstProductPrice();
        Assert.assertNotNull(price, "Price is not displayed for the first product");

        getTest().pass("Price displayed for first product: " + price);
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "ProductPrice");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    //P
    @Test(groups = {"functional"}, priority = 4)
    public void verifyProductSearchDisplaysResults() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchProduct("Tshirt");
        Assert.assertTrue(productPage.areSearchResultsDisplayed(), "Search results were not displayed");

        getTest().pass("Search results displayed successfully for keyword 'Tshirt'");
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SearchResults_Tshirt");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }

    //P
    @Test(groups = {"functional"}, priority = 5)
    public void verifyFilterByBrand() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.selectBrand("Polo");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Products were not filtered by brand");

        getTest().pass("Products filtered successfully by brand 'Polo'");
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "BrandFilter_Polo");
        getTest().addScreenCaptureFromPath(screenshotPath);
    }
    

   
   
    
    //P
    @Test(groups = {"functional"}, priority = 7)
    public void verifyMultipleProductsCanBeAddedToCart() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.addMultipleProductsToCart(3);
        productPage.goToCart();

        Assert.assertFalse(productPage.isCartEmpty(), "Cart is empty after adding multiple products");

        getTest().pass("Multiple products added to cart successfully");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "MultipleProducts"));
    }

    
    

    //P
    @Test(groups = {"functional"}, priority = 9)
    public void verifyFilterByCategory() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.selectCategory("Women");
        Assert.assertTrue(productPage.isProductPageDisplayed(), "Products not filtered by category");

        getTest().pass("Products filtered successfully by category 'Women'");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "CategoryFilter_Women"));
    }

    //P
    @Test(groups = {"functional"}, priority = 10)
    public void verifyNewsletterSubscription() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.subscribeNewsletter("testemail@example.com");

        Assert.assertTrue(productPage.isSubscriptionSuccessDisplayed(), "Subscription success message not displayed");

        getTest().pass("Newsletter subscribed successfully");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "Subscription"));
    }

    //N
    @Test(groups = {"functional"}, priority = 11)
    public void verifySearchWithNoResults() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchProduct("INVALID_PRODUCT_123");

        boolean isMessageDisplayed = productPage.isNoSearchResultsMessageDisplayed();

        if (!isMessageDisplayed) {
            String path = ScreenshotUtilities.captureScreen(getDriver(), "SearchNoResults");
            getTest().fail("Expected 'No results found' message, but nothing displayed").addScreenCaptureFromPath(path);
            Assert.fail("Expected 'No results found' message, but nothing displayed");
        }

        getTest().pass("Proper message displayed when no search results found");
    }

    

    //P
    @Test(groups = {"functional"}, priority = 12)
    public void verifyProductDetailsPageIsDisplayed() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.clickFirstViewProduct();

        Assert.assertTrue(productPage.isProductDetailsDisplayed(),
                "Product details page did not display properly");

        getTest().pass("Product details page displayed successfully");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "ProductDetailsPage"));
    }

    //P
    @Test(groups = {"functional"}, priority = 13)
    public void verifyAddProductWithSpecificQuantity() {
        getDriver().get("https://automationexercise.com/product_details/1");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.addProductFromDetailPage(4);
        productPage.goToCart();

        Assert.assertTrue(productPage.isProductInCart("Blue Top"), "Product not added with correct quantity");
    }

   

    //N
    @Test(groups = {"negative"}, priority = 14)
    public void verifyUpdateCartQuantityNotAllowed() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.addFirstProductToCart();
        productPage.goToCart();

        Assert.assertFalse(productPage.isCartQuantityInputPresent(),
                "Unexpectedly found editable quantity field in cart!");

        getTest().pass("As expected, site does not allow updating cart quantity");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "CartQuantityUpdateNotAllowed"));
    }

    

    
    @Test(groups = {"functional"}, priority = 15)
    public void verifyViewProductDetails() {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        Assert.assertTrue(productPage.isProductPageDisplayed(),
                "Products page is not displayed");

        productPage.clickFirstProductDetailLink();

        Assert.assertTrue(productPage.isProductDetailInfoBlockVisible(),
                "Product detail information is not displayed");

        getTest().pass("Product detail page is displayed successfully");
    }

    //P
    @Test(groups = {"functional"}, priority = 16)
    public void verifyAddProductFromDetailsPage() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.openFirstProductDetails();
        productPage.setProductQuantity(3);
        productPage.addProductToCartFromDetails();
        productPage.goToCart();

        Assert.assertEquals(productPage.getCartQuantity(), "3", 
                "Cart does not show correct quantity after adding from details page");

        getTest().pass("Product added to cart with correct quantity from details page");
    }

    //P
    @Test(groups = {"functional"}, priority = 17)
    public void verifyFilterProductsByCategory() {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.selectCategory("Women");
        productPage.selectSubCategory("Dress");

        Assert.assertTrue(productPage.areFilteredProductsDisplayed(), "No products displayed for Women > Dress");

        Assert.assertTrue(productPage.areFilteredProductsOfCategory("Women"), "Not all products belong to Women category");
    }

    //P
    @Test(groups = {"functional"}, priority = 18)
    public void verifyFilterProductsByBrand() {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.filterByBrand("Polo");

        Assert.assertTrue(productPage.areFilteredProductsDisplayed(), "No products displayed after filtering by Polo");

        boolean allCorrect = productPage.areFilteredProductsOfBrand("Polo");
        Assert.assertTrue(allCorrect,"Not all products belong to brand Polo");
    }

    //P
    @Test(groups = {"functional"}, priority = 19)
    public void verifySubmitProductReview() {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.openFirstProductDetail();

        productPage.submitReview("Benakesh", "benakesh@test.com", "Great product, highly recommend!");

        Assert.assertTrue(productPage.isReviewSubmitted(), "Review submission failed!");
    }

    //N
    @Test(groups = {"functional"}, priority = 20)
    public void verifyReviewWithInvalidEmail() throws IOException {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.openFirstProductDetails();
        productPage.submitReview("Ben", "b@", "Invalid email test");

        String validationMsg = productPage.getEmailValidationMessage();

        Assert.assertTrue(validationMsg.contains("@"),
            "Unexpected validation message: " + validationMsg);

        ScreenshotUtilities.captureScreen(getDriver(), "InvalidEmailReview");
    }

    //N
    @Test(groups = {"functional"}, priority = 21)
    public void verifyReviewWithInvalidEmail2() throws IOException {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.openFirstProductDetails();
        productPage.submitReview("Ben", "@i", "Invalid email test");

        String validationMsg = productPage.getEmailValidationMessage();

        Assert.assertTrue(validationMsg.contains("@"),
            "Unexpected validation message: " + validationMsg);

        ScreenshotUtilities.captureScreen(getDriver(), "InvalidEmailReview");
    }

    //N
    @Test(groups = {"functional"}, priority = 21)
    public void verifySearchWithEmptyInput() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchWithEmptyInput();

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SearchEmptyInput_NegativeTest");
        getTest().pass("Attempted search with empty input - site loaded all products instead of showing no results")
                 .addScreenCaptureFromPath(screenshotPath);

        System.out.println("NOTE: Negative test - site loads all products when search input is empty.");
    }

    //P
    @Test(groups = {"functional"}, priority = 22)
    public void verifySearchWithPartialProductName() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchWithPartialName("Tsh");
        Assert.assertTrue(productPage.areSearchResultsDisplayed(), "Search results not displayed for partial name");

        getTest().pass("Search results displayed successfully for partial product name");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "SearchPartialName"));
    }

    //P
    @Test(groups = {"functional"}, priority = 23)
    public void verifySearchWithCaseInsensitiveName() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchProduct("tSHIRT");
        Assert.assertTrue(productPage.areSearchResultsDisplayed(), "Search results not displayed for mixed case input");

        getTest().pass("Search results correctly displayed for uppercase/lowercase input");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "SearchCaseInsensitive"));
    }

    //P
    @Test(groups = {"functional"}, priority = 24)
    public void verifySearchWithSpecialCharacters() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchWithSpecialCharacters("@#$%");
        boolean isMessageDisplayed = productPage.isNoSearchResultsMessageDisplayed();

        Assert.assertTrue(isMessageDisplayed, "Expected no results for special characters input");

        getTest().pass("Proper message displayed for search with special characters");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "SearchSpecialChars"));
    }

    //N
    @Test(groups = {"functional"}, priority = 25)
    public void verifySearchWithSpacesOnly() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchProduct("   ");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SearchSpacesOnly_NegativeTest");
        getTest().pass("Attempted search with spaces only - site loaded products instead of showing no results")
                 .addScreenCaptureFromPath(screenshotPath);

        System.out.println("NOTE: Negative test - site loads products even when search input is spaces only.");
    }

    //N
    @Test(groups = {"functional"}, priority = 33)
    public void verifySearchWithAlternateProductNames() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.searchProduct("Tee");

        boolean isBlank = productPage.isSearchResultPageBlank();
        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SearchAlternateNameBlank");

        Assert.assertTrue(isBlank, "Search should show no results for alternate name 'Tee'");
        getTest().pass("Verified alternate product search shows blank page")
                 .addScreenCaptureFromPath(screenshotPath);
    }

    //P
    @Test(groups = {"functional"}, priority = 26)
    public void verifyViewProductIconNavigatesToDetails() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.clickFirstViewProduct();

        Assert.assertTrue(productPage.isProductDetailsDisplayed(), "Clicking View Product did not navigate to product details");

        getTest().pass("View Product icon navigates correctly to product details page");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "ViewProductNavigation"));
    }

    //N
    @Test(groups = {"negative"}, priority = 27)
    public void verifyProductImageZoomNotSupported() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.clickFirstViewProduct();
        productPage.clickProductImage();

        Assert.assertTrue(productPage.isProductImageZoomUnsupported(),"Zoom overlay appeared but zoom is not supposed to be supported");

        getTest().pass("Verified: Product image zoom is not supported on this site");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "ProductImageZoomUnsupported"));
    }



    //P
    @Test(groups = {"functional"}, priority = 28)
    public void verifyViewCartFromPopupRedirectsToCart() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.addFirstProductToCart();
        productPage.clickViewCartFromPopup();

        Assert.assertTrue(productPage.isCartPageDisplayed(),"Clicking 'View Cart' did not redirect to Cart page");

        getTest().pass("Verified 'View Cart' from popup redirects correctly to Cart page");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "ViewCartRedirect"));
    }

    //N
    @Test(groups = {"negative"}, priority = 29)
    public void verifySubscriptionWithInvalidEmail_BAt() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.subscribeNewsletter("B@");
        String validationMsg = productPage.getSubscriptionEmailValidationMessage();

        Assert.assertTrue(validationMsg.contains("@") || validationMsg.contains("valid"),
                "Unexpected validation message: " + validationMsg);

        getTest().pass("Validation correctly shown for invalid email 'B@'");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "SubscriptionInvalid_BAt"));
    }

    //N
    @Test(groups = {"negative"}, priority = 29)
    public void verifySubscriptionWithInvalidEmail() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.subscribeNewsletter("b");
        String validationMsg = productPage.getSubscriptionEmailValidationMessage();

        Assert.assertTrue(validationMsg.contains("@") || validationMsg.contains("valid"),
                "Unexpected validation message: " + validationMsg);

        getTest().pass("Validation correctly shown for invalid email 'b'");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "SubscriptionInvalid_b"));
    }

    //N
    @Test(groups = {"negative"}, priority = 30)
    public void verifySubscriptionWithInvalidEmail1() throws Exception {
        getDriver().get("https://automationexercise.com/products");
        ProductPage productPage = new ProductPage(getDriver());

        productPage.subscribeNewsletter("@b");
        String validationMsg = productPage.getSubscriptionEmailValidationMessage();

        Assert.assertTrue(validationMsg.contains("@") || validationMsg.contains("valid"),
                "Unexpected validation message: " + validationMsg);

        getTest().pass("Validation correctly shown for invalid email '@b'");
        getTest().addScreenCaptureFromPath(ScreenshotUtilities.captureScreen(getDriver(), "SubscriptionInvalid_Atb"));
    }

   
}
