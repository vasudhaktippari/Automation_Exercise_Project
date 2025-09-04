package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automationexercise.utilities.ScreenshotUtilities;

public class HomePage {
    WebDriver driver;

    // ==============================
    //           Locators
    // ==============================

    // Logo
    By logo = By.xpath("//div[@class='logo pull-left']");

    // Top navigation links
    By productsLink   = By.cssSelector("a[href='/products']");
    By cartLink       = By.cssSelector("a[href='/view_cart']");
    By loginLink      = By.cssSelector("a[href='/login']");
    By testCasesLink  = By.cssSelector("a[href='/test_cases']");
    By apiTestingLink = By.cssSelector("a[href='/api_list']");
    By contactUsLink  = By.cssSelector("a[href='/contact_us']");

    // Home page slider carousel
    By sliderRoot      = By.cssSelector("#slider-carousel.carousel");
    By leftArrow       = By.cssSelector("a.left.control-carousel.hidden-xs[data-slide='prev']");
    By rightArrow      = By.cssSelector("a.right.control-carousel.hidden-xs[data-slide='next']");
    By activeItem      = By.cssSelector("#slider-carousel .carousel-inner .item.active");
    By indicators      = By.cssSelector("#slider-carousel .carousel-indicators li");
    By activeIndicator = By.cssSelector("#slider-carousel .carousel-indicators li.active");

    // Carousel button locators
    By apiListLink = By.cssSelector("a.apis_list[href='/api_list']");

    // Sections on home page
    By categorySection      = By.xpath("//h2[text()='Category']");
    By featuresItemsSection = By.xpath("//h2[text()='Features Items']");

    // Category toggles
    By womenToggle = By.xpath("//a[@data-toggle='collapse' and (contains(@href,'#Women') or normalize-space()='Women')]");
    By menToggle   = By.xpath("//a[@data-toggle='collapse' and (contains(@href,'#Men')   or normalize-space()='Men')]");
    By kidsToggle  = By.xpath("//a[@data-toggle='collapse' and (contains(@href,'#Kids')  or normalize-space()='Kids')]");

    // Category panels when expanded
    By womenPanelOpen = By.xpath("//*[@id='Women' and contains(@class,'panel-collapse') and contains(@class,'in')]");
    By menPanelOpen   = By.xpath("//*[@id='Men'   and contains(@class,'panel-collapse') and contains(@class,'in')]");
    By kidsPanelOpen  = By.xpath("//*[@id='Kids'  and contains(@class,'panel-collapse') and contains(@class,'in')]");

    // Feature items section
    By featuresHeader = By.xpath("//h2[normalize-space()='Features Items' or normalize-space()='FEATURES ITEMS']");
    By featuresGrid   = By.cssSelector(".features_items");
    By featureCards   = By.cssSelector(".features_items .col-sm-4");

    // Brands section
    By brandsHeader = By.xpath("//h2[normalize-space()='Brands' or normalize-space()='BRANDS']");
    By brandsBox    = By.cssSelector(".brands_products");
    By brandRows    = By.cssSelector(".brands_products .brands-name li");

    // Recommended items section
    By recommendedHeader    = By.xpath("//h2[normalize-space()='recommended items' or normalize-space()='RECOMMENDED ITEMS']");
    By recommendedContainer = By.cssSelector(".recommended_items");
    By recommendedActiveRow = By.cssSelector(".recommended_items .carousel-inner .item.active");
    By recommendedCards     = By.cssSelector(".recommended_items .carousel-inner .item.active .col-sm-4");

    // ==============================
    //            Constructor
    // ==============================

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ==============================
    //     Basic page validations
    // ==============================

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isHomePageDisplayed() {
        try {
            return driver.getTitle().equals("Automation Exercise");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoDisplayed() {
        try {
            return driver.findElement(logo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==============================
    // Navigation methods
    // ==============================

    public void goToProducts()   { driver.findElement(productsLink).click(); }
    public void goToCart()       { driver.findElement(cartLink).click(); }
    public void goToLogin()      { driver.findElement(loginLink).click(); }
    public void goToTestCases()  { driver.findElement(testCasesLink).click(); }
    public void goToApiTesting() { driver.findElement(apiTestingLink).click(); }
    public void goToContactUs()  { driver.findElement(contactUsLink).click(); }

    // ==============================
    // Slider methods
    // ==============================

    public void waitForSliderVisible() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(sliderRoot));
    }

    public String getActiveSlideIndex() {
        waitForSliderVisible();
        WebElement dot = driver.findElement(activeIndicator);
        String idx = dot.getAttribute("data-slide-to");
        if (idx != null) return idx.trim();
        // fallback: compute index by sibling count
        return String.valueOf(dot.findElements(By.xpath("preceding-sibling::li")).size());
    }

    // Helper to do JS click if normal click fails
    private void jsClick(WebElement el) {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        js.executeScript("arguments[0].click();", el);
    }

    // Generic left/right arrow click
    private void clickArrow(By arrow) {
        waitForSliderVisible();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement btn = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(arrow));
        try {
            btn.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException | org.openqa.selenium.JavascriptException ex) {
            jsClick(btn);
        }
    }

    public void clickNextSlider()     { clickArrow(rightArrow); }
    public void clickPreviousSlider() { clickArrow(leftArrow); }

    // Wait for slide to change index
    public void waitForSlideIndexChange(String previousIndex) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(8));
        wait.until(d -> !previousIndex.equals(getActiveSlideIndex()));
    }

    // Carousel buttons
    public void clickCarouselTestCases() { driver.findElement(testCasesLink).click(); }
    public void clickCarouselApiList()   { driver.findElement(apiListLink).click(); }

    // ==============================
    //       Section methods
    // ==============================

    public boolean isCategoryDisplayed() {
        try { return driver.findElement(categorySection).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isFeaturesItemsDisplayed() {
        try { return driver.findElement(featuresItemsSection).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // Scroll to category section
    public void scrollCategoryIntoView() {
        WebElement sec = driver.findElement(categorySection);
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", sec);
    }

    // Category visibility checks
    public boolean isWomenVisible() { return driver.findElement(womenToggle).isDisplayed(); }
    public boolean isMenVisible()   { return driver.findElement(menToggle).isDisplayed(); }
    public boolean isKidsVisible()  { return driver.findElement(kidsToggle).isDisplayed(); }

    // Expand category panels
    private boolean isOpen(By openLocator) {
        return !driver.findElements(openLocator).isEmpty() &&
               driver.findElement(openLocator).isDisplayed();
    }

    private void ensureExpanded(By toggle, By openLocator) {
        scrollCategoryIntoView();
        if (!isOpen(openLocator)) {
            WebElement t = new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(toggle));
            try { t.click(); } 
            catch (org.openqa.selenium.ElementClickInterceptedException e) { jsClick(t); }
            new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(openLocator));
        }
    }

    public void expandWomen() { ensureExpanded(womenToggle, womenPanelOpen); }
    public void expandMen()   { ensureExpanded(menToggle,   menPanelOpen); }
    public void expandKids()  { ensureExpanded(kidsToggle,  kidsPanelOpen); }

    // ==============================
    //      Feature items methods
    // ==============================

    public void scrollFeaturesIntoView() {
        WebElement header = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(featuresHeader));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", header);
    }

    public void waitForFeaturesVisible() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(featuresGrid));
    }

    public boolean isFeaturesSectionVisible() {
        try { return driver.findElement(featuresGrid).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public int getVisibleFeatureItemCount() {
        waitForFeaturesVisible();
        return driver.findElements(featureCards).stream()
                .filter(WebElement::isDisplayed)
                .toList()
                .size();
    }

    public String captureFeaturesSectionScreenshot(String name) {
        try {
            WebElement grid = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(featuresGrid));
            return ScreenshotUtilities.captureElement(driver, grid, name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==============================
    //         Brand methods
    // ==============================

    public void scrollBrandsIntoView() {
        WebElement hdr = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(brandsHeader));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", hdr);
    }

    public void waitForBrandsVisible() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(brandsBox));
    }

    public boolean isBrandsSectionVisible() {
        try { return driver.findElement(brandsBox).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public int getVisibleBrandCount() {
        waitForBrandsVisible();
        return driver.findElements(brandRows).stream()
                .filter(WebElement::isDisplayed)
                .toList()
                .size();
    }

    public String captureBrandsScreenshot(String name) {
        try {
            WebElement box = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(brandsBox));
            return ScreenshotUtilities.captureElement(driver, box, name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==============================
    //     Recommended items methods
    // ==============================

    public void scrollRecommendedIntoView() {
        WebElement hdr = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(recommendedHeader));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", hdr);
    }

    public void waitForRecommendedVisible() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(recommendedContainer));
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(recommendedActiveRow));
    }

    public boolean isRecommendedSectionVisible() {
        try { return driver.findElement(recommendedContainer).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public int getVisibleRecommendedCount() {
        waitForRecommendedVisible();
        return driver.findElements(recommendedCards).stream()
                .filter(WebElement::isDisplayed)
                .toList()
                .size();
    }

    public String captureRecommendedFullPage(String name) {
        try {
            return ScreenshotUtilities.captureScreen(driver, name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==============================
    //     Screenshot helpers
    // ==============================

    public String captureHomePageScreenshot(String screenshotName) {
        try {
            return ScreenshotUtilities.captureScreen(driver, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
