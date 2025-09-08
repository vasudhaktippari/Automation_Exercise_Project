 package com.automationexercise.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automationexercise.utilities.ScreenshotUtilities;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameOnCard = By.name("name_on_card");
    private By cardNumber = By.name("card_number");
    private By cvc = By.name("cvc");
    private By expiryMonth = By.name("expiry_month");
    private By expiryYear = By.name("expiry_year");
    private By payBtn = By.id("submit");
    private By orderPlacedMsg = By.xpath("//p[contains(text(),'Order Placed!')]");
    private By downloadInvoiceBtn = By.xpath("//a[contains(text(),'Download Invoice')]");
    private By productsLink = By.xpath("//a[@href='/products']");
    private By viewCartLink = By.linkText("View Cart");
    private By placeOrderBtn = By.xpath("//a[@href='/payment']");
    private By proceedToCheckOut = By.xpath("//*[@id='do_action']/div[1]/div/div/a");
    private By cartTable = By.xpath("//*[@id=\"cart_info\"]");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Faster
    }

    // --- Navigation ---
    public void goToProductsPage() {
        driver.findElement(productsLink).click();
        wait.until(ExpectedConditions.urlContains("/products"));
    }

    public void safeGet(WebDriver driver, String url) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                driver.get(url);
                return;
            } catch (TimeoutException e) {
                System.out.println("Retrying load: " + url);
                attempts++;
            }
        }
        throw new RuntimeException("Failed to load after retries: " + url);
    }
    
    // Login
    public LoginPage goToLogin() {
	    By loginLink = By.xpath("//a[@href='/login']");
	    WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
	    loginBtn.click();
	    wait.until(ExpectedConditions.urlContains("/login"));
	    return new LoginPage(driver);
	}
    
    //Checkout page displayed
    public boolean isCheckoutPageDisplayed() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement checkoutHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
	    		 By.xpath("//*[@id='cart_items']/div/div[1]/ol/li[2]")));
		return checkoutHeading.isDisplayed();
	}
    public boolean isProductInCart(String productName) {
        return driver.findElement(cartTable).getText().contains(productName);
    }
    
    //Capture screenshot
    public String captueCartPageScreenshot(String screenshotName) {
        try {
            return ScreenshotUtilities.captureScreen(driver, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Click View cart
    public void clickViewCart() {
        driver.findElement(viewCartLink).click();
        wait.until(ExpectedConditions.urlContains("/view_cart"));
    }
    
    public void clickAddToCart(String productId) {
        By addBtn = By.xpath(String.format("//a[@data-product-id='%s']", productId));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewCartLink));
    }

    public void clickProceedToCheckout() {
        driver.findElement(proceedToCheckOut).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='cart_items']/div/div[1]/ol/li[2]")));
    }

    public void clickPlaceOrderAndWait() {
        WebElement placeOrder = wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrder);
        wait.until(ExpectedConditions.urlContains("/payment"));
    }

    // --- Payment Form ---
    public void enterNameOnCard(String name) {
        WebElement field = driver.findElement(nameOnCard);
        field.clear();
        field.sendKeys(name);
    }

    public void enterCardNumber(String number) {
        WebElement field = driver.findElement(cardNumber);
        field.clear();
        field.sendKeys(number);
    }

    public void enterCVC(String code) {
        WebElement field = driver.findElement(cvc);
        field.clear();
        field.sendKeys(code);
    }

    public void enterExpiry(String month, String year) {
        driver.findElement(expiryMonth).clear();
        driver.findElement(expiryMonth).sendKeys(month);
        driver.findElement(expiryYear).clear();
        driver.findElement(expiryYear).sendKeys(year);
    }

    public void clickPayAndConfirm() {
        driver.findElement(payBtn).click();
    }

    // --- Validations ---
    public boolean isOrderPlacedMessageDisplayed() {
        return !driver.findElements(orderPlacedMsg).isEmpty();
    }

    public boolean isDownloadInvoiceButtonDisplayed() {
        return !driver.findElements(downloadInvoiceBtn).isEmpty();
    }

    public void clickDownloadInvoice() {
        driver.findElement(downloadInvoiceBtn).click();
    }

    public boolean isNameAlphabetic(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    public boolean isCardNumberNumeric(String number) {
        return number.matches("\\d+");
    }

    public boolean isCvcNumeric(String cvcValue) {
        return cvcValue.matches("\\d+");
    }

    public boolean isCvcThreeDigits(String cvcValue) {
        return cvcValue.matches("\\d{3}");
    }

    public boolean isExpiryValid(String month, String year) {
        return month.matches("0[1-9]|1[0-2]") && year.matches("\\d{4}");
    }
}
