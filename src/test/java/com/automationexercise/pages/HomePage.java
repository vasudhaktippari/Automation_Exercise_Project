package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automationexercise.utilities.ScreenshotUtilities;

public class HomePage {
    WebDriver driver;

    // Locators (same visibility style as LoginPage - no 'private')
    By logo = By.xpath("//div[@class='logo pull-left']");
    
    // ---------------- Navigation Locators ----------------
    By productsLink   = By.cssSelector("a[href='/products']");
    By cartLink       = By.cssSelector("a[href='/view_cart']");
    By loginLink      = By.cssSelector("a[href='/login']");
    By testCasesLink  = By.cssSelector("a[href='/test_cases']");
    By apiTestingLink = By.cssSelector("a[href='/api_list']");
    By contactUsLink  = By.cssSelector("a[href='/contact_us']");
 // --------------- Slider Controls Locators ----------------
    By sliderRoot      = By.cssSelector("#slider-carousel.carousel");
    By leftArrow       = By.cssSelector("a.left.control-carousel.hidden-xs[data-slide='prev']");
    By rightArrow      = By.cssSelector("a.right.control-carousel.hidden-xs[data-slide='next']");
    By activeItem      = By.cssSelector("#slider-carousel .carousel-inner .item.active");
    By indicators      = By.cssSelector("#slider-carousel .carousel-indicators li");
    By activeIndicator = By.cssSelector("#slider-carousel .carousel-indicators li.active");
    
 // --------- Carousel Button Locators ---------
    
 // ---------- Carousel button locators ----------
    By apiListLink   = By.cssSelector("a.apis_list[href='/api_list']");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Fetch page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Simple check if Home Page is loaded (mirrors the simple checks in LoginPage)
    public boolean isHomePageDisplayed() {
        try {
            return driver.getTitle().equals("Automation Exercise");
        } catch (Exception e) {
            return false;
        }
    }

    // Verify logo visible
    public boolean isLogoDisplayed() {
        try {
            return driver.findElement(logo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ---------------- Navigation Methods ----------------
    public void goToProducts() {
        driver.findElement(productsLink).click();
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
    }

    public void goToLogin() {
        driver.findElement(loginLink).click();
    }

    public void goToTestCases() {
        driver.findElement(testCasesLink).click();
    }

    public void goToApiTesting() {
        driver.findElement(apiTestingLink).click();
    }

    public void goToContactUs() {
        driver.findElement(contactUsLink).click();
    }
    
    // --------------- Slider Methods ----------------
 // ---- Slider helpers ----
    public void waitForSliderVisible() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(sliderRoot));
    }

    public String getActiveSlideIndex() {
        waitForSliderVisible();
        org.openqa.selenium.WebElement dot = driver.findElement(activeIndicator);
        String idx = dot.getAttribute("data-slide-to");
        if (idx != null) return idx.trim();
        // fallback: compute index by position
        return String.valueOf(dot.findElements(org.openqa.selenium.By.xpath("preceding-sibling::li")).size());
    }

    private void jsClick(org.openqa.selenium.WebElement el) {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        js.executeScript("arguments[0].click();", el);
    }

    private void clickArrow(By arrow) {
        waitForSliderVisible();
        org.openqa.selenium.support.ui.WebDriverWait wait =
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        org.openqa.selenium.WebElement btn =
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(arrow));
        try {
            btn.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException | org.openqa.selenium.JavascriptException ex) {
            jsClick(btn);
        }
    }
 // --------- Carousel Button Methods ---------
   
    // Actions
    public void clickNextSlider()     { clickArrow(rightArrow); }
    public void clickPreviousSlider() { clickArrow(leftArrow); }

    // Wait until index changes (more robust than title)
    public void waitForSlideIndexChange(String previousIndex) {
        org.openqa.selenium.support.ui.WebDriverWait wait =
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(8));
        wait.until(d -> !previousIndex.equals(getActiveSlideIndex()));
    }
    
    public void clickCarouselTestCases() {
        driver.findElement(testCasesLink).click();
    }

    public void clickCarouselApiList() {
        driver.findElement(apiListLink).click();
    }


    // Capture screenshot of Home Page
    public String captureHomePageScreenshot(String screenshotName) {
        try {
            return ScreenshotUtilities.captureScreen(driver, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Capture screenshot of only the logo
    public String captureLogoScreenshot(String screenshotName) {
        try {
            WebElement logoElement = driver.findElement(logo);
            return ScreenshotUtilities.captureElement(driver, logoElement, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
