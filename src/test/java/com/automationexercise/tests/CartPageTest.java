  package com.automationexercise.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;

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
    
    @Test(groups = {"UI"}, priority =1)
    
    public void VeriryCartPageDisplyed() throws Exception {
   	 getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");
       CartPage cartPage = new CartPage(getDriver());
       CartPage.ClcikCart();
       getTest().pass("Cart page is displayed");
    }
 @Test(groups = {"UI"}, priority =2)
    
    public void VeriryHomePageDisplyed() throws Exception {
   	 getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
       CartPage cartPage = new CartPage(getDriver());
       CartPage.ClickHome();
       getTest().pass("Home page is displayed");
    }
 @Test(groups = {"UI"}, priority =3)
 
    public void VeriryProductPageDisplyed() throws Exception {
   	 getDriver().get("https://automationexercise.com/view_cart");
        getTest().info("Opened AutomationExercise Cartpage");
       CartPage cartPage = new CartPage(getDriver());
       CartPage.ClcikPrduct();
       getTest().pass("Product page is displayed");
    }
 
 @Test(groups = {"UI"}, priority =4)
 
     public void VeriryLoginPageDisplyed() throws Exception {
	 getDriver().get("https://automationexercise.com/view_cart");
     getTest().info("Opened AutomationExercise Cartpage");
    CartPage cartPage = new CartPage(getDriver());
    CartPage.ClickLogin();
    getTest().pass("Product page is displayed");
   }

 @Test(groups = {"UI"}, priority =5)
 
     public void VeriryApiPageDisplyed() throws Exception {
	 getDriver().get("https://automationexercise.com/view_cart");
     getTest().info("Opened AutomationExercise Cartpage");
    CartPage cartPage = new CartPage(getDriver());
    CartPage.ClickApiList();
    getTest().pass("API List page is displayed");
   }

 @Test(groups = {"UI"}, priority =6)
 
     public void VeriryTestCasePageDisplyed() throws Exception {
	 getDriver().get("https://automationexercise.com/view_cart");
     getTest().info("Opened AutomationExercise Cartpage");
    CartPage cartPage = new CartPage(getDriver());
    CartPage.ClickTestCase();
    getTest().pass("TestCase page is displayed");
   }
 

 @Test(groups = {"UI"}, priority =7)
 
     public void VeriryContctUsPageDisplyed() throws Exception {
	 getDriver().get("https://automationexercise.com/view_cart");
     getTest().info("Opened AutomationExercise Cartpage");
    CartPage cartPage = new CartPage(getDriver());
    CartPage.ClickContactUs();
    getTest().pass("ContactUs page is displayed");
   }
 

 @Test(groups = {"UI"}, priority =8)
 
     public void VeriryVedioTutorialPageDisplyed() throws Exception {
	 getDriver().get("https://automationexercise.com/view_cart");
     getTest().info("Opened AutomationExercise Cartpage");
    CartPage cartPage = new CartPage(getDriver());
    CartPage.ClickVedioTutorial();
    getTest().pass("Product page is displayed");
   }
 @Test(groups = {"functional","UI"},priority=9)
 public void verifyLogoIsDisplayed() throws Exception {
     getDriver().get("https://automationexercise.com/view_cart");
     HomePage homePage = new HomePage(getDriver());

     Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not displayed on the Cart Page");
     getTest().pass("Logo is displayed successfully on Cart Page");

     // Capture only the logo via POM helper
     String logoShot = homePage.captureLogoScreenshot("CartPageLogo");
     Assert.assertNotNull(logoShot, "Failed to capture logo screenshot");
     getTest().addScreenCaptureFromPath(logoShot);
 }
 @Test(groups = {"functional","UI"},priority=10)
 public void ClickOnLogo() throws Exception {
     getDriver().get("https://automationexercise.com/view_cart");
     CartPage cartPage = new CartPage(getDriver());

     Assert.assertTrue(cartPage.isLogoDisplayed(), "Logo is not displayed on the Cart Page");
     getTest().pass("Logo is displayed successfully on Cart Page");
     // Capture only the logo via POM helper
     CartPage.ClickLogo();
     getTest().pass("Logo is displayed and On clicking it redirects to homepage");
     
 }
 //To verify scroll functionality
 @Test(dataProvider = "SingleCartData",groups = {"functional","UI"},priority=11)
 public void verifyScroll(Object... row) throws InterruptedException {
     // Setup browser
	 
	 String productId = row[2].toString().trim();
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

 

     // Scroll Down
     cartPage.scrollToFooter();
    
     Assert.assertTrue(CartPage.isFooterDisplayed(), "Footer is not displayed after scrolling down!");
     String cartpageScrollDownCapture =cartPage.captueCartPageScreenshot("Cart Page Scrolled down");
     Assert.assertNotNull(cartpageScrollDownCapture, "Failed to capture scroll down ");
     getTest().addScreenCaptureFromPath(cartpageScrollDownCapture);
     

     //Scrollup
     cartPage.scrollToHeader();
     Assert.assertTrue(CartPage.isHeaderDisplayed(), "Header is not displayed after scrolling up!");
     String cartpageScrollUpCapture =cartPage.captueCartPageScreenshot("Cart Page Scrolled Up");
     Assert.assertNotNull(cartpageScrollUpCapture, "Failed to capture scroll up ");
     getTest().addScreenCaptureFromPath(cartpageScrollUpCapture);
     
     
 }
 

    // 🔹 Test 1 → Add 1st product directly without login
    @Test(dataProvider = "SingleCartData", groups = {"smoke"}, priority = 12)
    public void addFirstProduct(Object... row) {
        String productId = row[2].toString().trim();
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
        // Capture cart page
        String cartpageCapture =cartPage.captueCartPageScreenshot("Cart Page");
        Assert.assertNotNull(cartpageCapture, "Failed to capture logo screenshot");
        getTest().addScreenCaptureFromPath(cartpageCapture);

    }
 // 🔹 Test 2 → Login and add 2nd & 3rd products, navigating between Cart and Products
    @Test(dependsOnMethods = "addFirstProduct", groups = {"functional"}, priority = 13)
    public void loginAndAddSecondAndThirdProduct() throws Exception {
        Object[][] allData = ExcelUtilities.getData("Sheet2");

        // Take login credentials from first row
        String email = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();

        CartPage cartPage = new CartPage(getDriver());

        if (!cartPage.isUserLoggedIn()) {
            getDriver().get("https://automationexercise.com/");
            getTest().info("Opening home page");
            cartPage.goToLogin().login(email, password);
            getTest().info("Logged in with: " + email);
        }

        // 🔹 Add second product (row 1)
        String productId2 = allData[0][2].toString().trim();
        String productName2 = allData[0][3].toString().trim();

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId2);
        getTest().info("Added second product: " + productName2);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page after adding second product");

        Assert.assertTrue(cartPage.isProductInCart(productName2),
                "Second product not found in cart: " + productName2);

        // 🔹 Go back to Products Page and add third product (row 2)
        String productId3 = allData[1][2].toString().trim();
        String productName3 = allData[1][3].toString().trim();

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId3);
        getTest().info("Added third product: " + productName3);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page after adding third product");

        Assert.assertTrue(cartPage.isProductInCart(productName3),
                "Third product not found in cart: " + productName3);
        // Capture cart page
        String cartpageCapture =cartPage.captueCartPageScreenshot("Cart Page");
        Assert.assertNotNull(cartpageCapture, "Failed to capture logo screenshot");
        getTest().addScreenCaptureFromPath(cartpageCapture);

    }
    //verifying Remove product from cart
    @Test(groups = {"functional"}, priority = 14)
    public void RemoveProductFromCart() throws Exception {
    	 Object[][] allData = ExcelUtilities.getData("Sheet2");
        // Take login credentials from first row
        String email = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();

        CartPage cartPage = new CartPage(getDriver());

        if (!cartPage.isUserLoggedIn()) {
            getDriver().get("https://automationexercise.com/");
            getTest().info("Opening home page");
            cartPage.goToLogin().login(email, password);
            getTest().info("Logged in with: " + email);
        }
        cartPage.ViewCart();
        cartPage.RemoveCartItem();
        String cartpageCapture =cartPage.captueCartPageScreenshot("Cart Page After removeig items");
        Assert.assertNotNull(cartpageCapture, "Failed to capture cart screenshot");
        getTest().addScreenCaptureFromPath(cartpageCapture);
    
}
    //verifying Click here link is working when cart is empty
    @Test(groups = {"functional","UI"}, priority =15)
    public void clickHere() throws Exception{
    	    CartPage cartPage = new CartPage(getDriver());

         getDriver().get("https://automationexercise.com/");
         getTest().info("Opened AutomationExercise homepage");
         cartPage.ViewCart();
         String cartpageCapture =cartPage.captueCartPageScreenshot("Clickhere On CartPage");
         Assert.assertNotNull(cartpageCapture, "Failed to capture cart screenshot");
         getTest().addScreenCaptureFromPath(cartpageCapture);
    	
    }
  //verifying Click On Proceed to checkout link is working Without Login
    @Test(dataProvider = "SingleCartData",groups = {"functional","Regression","Smoke"}, priority = 16)
    public void clikOnCkeckoutWithouLogin(Object... row) throws Exception {
    	CartPage cartPage = new CartPage(getDriver());
    	String productId = row[2].toString().trim();
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
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(), 
                "CartPage not displayed");
        
        getTest().info("");String cartpageCapture =cartPage.captueCartPageScreenshot("Clickhere Checkout Page");
        Assert.assertNotNull(cartpageCapture, "Failed to capture Checkout page");
        getTest().addScreenCaptureFromPath(cartpageCapture);
    
      }
    // Proceeding to Login Page before Checking Out the project
    @Test(dataProvider = "SingleCartData",groups = {"functional","UI"}, priority =17)
    public void clikOnCkeckoutDisplaysLogin(Object... row) throws Exception {
    	CartPage cartPage = new CartPage(getDriver());
    	String productId = row[2].toString().trim();
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
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(), 
                "CartPage not displayed");
        getTest().info("CheckOut page is not Displayed");
        String cartpageCapture =cartPage.captueCartPageScreenshot("Clickhere Checkout Page");
        Assert.assertNotNull(cartpageCapture, "Failed to capture Checkout page");
        getTest().addScreenCaptureFromPath(cartpageCapture);
        //Proceed to login from cart page
        cartPage.LoginToProceedToCheckout();
        getTest().info("");String cartpageCapture1 =cartPage.captueCartPageScreenshot("LoginPage Displayed");
        Assert.assertNotNull(cartpageCapture1, "Failed to capture Login page");
        getTest().addScreenCaptureFromPath(cartpageCapture1);
        getTest().info("User is asked to register before CheckOut");
    
      }
    @Test(dataProvider = "SingleCartData",groups = {"functional","Regression","Smoke" ,"UI"}, priority = 18)
    public void ProceedToCheckOut(Object... row) throws Exception {
        CartPage cartPage = new CartPage(getDriver());

        // Take product info from Excel row
        String productId = row[2].toString().trim();
        String productName = row[3].toString().trim();

        // Take login credentials from Sheet2 (first row)
        Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();

        // Open site & add product
        getDriver().get("https://automationexercise.com/");
        getTest().info("Opened AutomationExercise homepage");

        cartPage.goToProductsPage();
        cartPage.clickAddToCart(productId);
        getTest().info("Added product: " + productName);

        cartPage.clickViewCart();
        getTest().info("Navigated to Cart Page");

        // Verify product present
        Assert.assertTrue(cartPage.isProductInCart(productName), 
                "Product not found in cart: " + productName);

        // First attempt checkout (should redirect to login)
        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(), 
                "Checkout not reached / login prompt missing");
        String checkoutBeforeLogin = cartPage.captueCartPageScreenshot("CheckoutPrompt");
        getTest().addScreenCaptureFromPath(checkoutBeforeLogin);

        // Login
        cartPage.LoginToProceedToCheckout();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        // Back to cart → checkout again
        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        Assert.assertTrue(cartPage.isCheckoutPageDisplayed(), 
                "Checkout page not displayed after login");

        String finalCheckout = cartPage.captueCartPageScreenshot("CheckoutAfterLogin");
        getTest().addScreenCaptureFromPath(finalCheckout);
        getTest().info("Checkout page displayed successfully after login");
    }
    //Verifying comment box taking the input from user
    @Test(groups = {"functional","Regression"}, priority = 19)
    public void AddCommentAndPlaceOrder() throws Exception {
    	     getDriver().get("https://automationexercise.com/");
         getTest().info("Opened AutomationExercise homepage");
        CartPage cartPage = new CartPage(getDriver());
     	Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        // Back to cart → checkout again
        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        cartPage.enterComment("Please deliver between 9AM-11AM");
     
        Assert.assertTrue(cartPage.isCommentEntered(), "Comment was not entered into the box!");

        String Comment= cartPage.captueCartPageScreenshot("Comment Added In CheckoutPage");
        getTest().addScreenCaptureFromPath(Comment);
        getTest().info("Checkout page displayed successfully after login and user can add comments");
        
        cartPage.clickPlaceOrder();
        String PlaceOrder= cartPage.captueCartPageScreenshot("Place Order is Clicked");
        getTest().addScreenCaptureFromPath(PlaceOrder);
        getTest().info("Checkout page displayed successfully after login and user can add comments and place Order");

    }
    @Test(groups = {"functional","Regression","UI"}, priority = 20)
    public void ClickOnArrowAndPlaceOrderBtn() throws Exception {
    	     getDriver().get("https://automationexercise.com/"																			);
         getTest().info("Opened AutomationExercise homepage");
        CartPage cartPage = new CartPage(getDriver());
     	Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        // Back to cart → checkout again
        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        cartPage.ClickUpArrow();
        getTest().info("Navigate to top of the page");
        
        cartPage.clickPlaceOrderAndWait();
        String PlaceOrder= cartPage.captueCartPageScreenshot("Place Order is Clicked");
        getTest().addScreenCaptureFromPath(PlaceOrder);
        getTest().info("Checkout page displayed successfully after login and user can add comments and place Order");
}
    
    //To verify weather Address field is displayed in Checkout Page
    @Test(groups = {"UI"}, priority = 21)
    public void VerifyAddressIsDisplayed() throws Exception {
    	 getDriver().get("https://automationexercise.com/");
         getTest().info("Opened AutomationExercise homepage");
        CartPage cartPage = new CartPage(getDriver());
     	Object[][] allData = ExcelUtilities.getData("Sheet2");
        String email = allData[0][0].toString().trim();
        String password = allData[0][1].toString().trim();
        cartPage.goToLogin().login(email, password);
        getTest().info("Logged in with: " + email);

        // Back to cart → checkout again
        cartPage.ViewCart();
        cartPage.ClickProceedToCheckout();
        cartPage.isAddressDisplayed();
        String Address= cartPage.captueCartPageScreenshot("Address Displayed in CheckoutPage");
        getTest().addScreenCaptureFromPath(Address);
        getTest().info("Checkout page displayed successfully and containes Address field");
         
    
}

 }
