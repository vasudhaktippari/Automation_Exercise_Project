package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;

public class ProductPage {
    WebDriver driver;

    // Locators 
    By productsHeader      = By.xpath("//h2[text()='All Products']");
    By searchInput         = By.id("search_product");
    By searchButton        = By.id("submit_search");
    By productCards        = By.xpath("//div[@class='product-image-wrapper']");
    By addToCartButton     = By.xpath("//a[contains(text(),'Add to cart')]");
    By viewCartButton      = By.xpath("//u[contains(text(),'View Cart')]");
    By cartItems           = By.xpath("//tr[contains(@id,'product-')]");
    By brandList           = By.xpath("//div[@class='brands_products']//a");
    By priceLabel          = By.xpath("//div[@class='productinfo text-center']/h2");
    By quantityInput       = By.name("quantity");
    By proceedToCheckout   = By.xpath("//a[text()='Proceed To Checkout']");
    By placeOrderButton    = By.xpath("//a[contains(text(),'Place Order')]");
    By overlayLocator = By.cssSelector(".modal, .loading-overlay, .newsletter-popup");
    
    By continueShoppingBtn = By.xpath("//button[text()='Continue Shopping']");

    By cartTotal = By.cssSelector(".cart_total_price");
    By removeProductBtn    = By.xpath("//a[@class='cart_quantity_delete']");
    By categoryMenu        = By.xpath("//div[@class='left-sidebar']//a");
    By recommendedSection  = By.xpath("//div[@class='recommended_items']");
    By recommendedAddToCart= By.xpath("//div[@class='recommended_items']//a[contains(text(),'Add to cart')]");
    By subscriptionField   = By.id("susbscribe_email");
    By subscriptionBtn     = By.id("subscribe");
    By subscriptionSuccess = By.xpath("//*[contains(text(),'You have been successfully subscribed!')]");
    By emptyCartMessage = By.xpath("//*[contains(text(),'Cart is empty')]");

    By viewProductLinks   = By.xpath("//a[contains(text(),'View Product')]");
    By productInfoSection = By.cssSelector(".product-information");
    By quantityInputField = By.name("quantity");
    By addToCartFromDetailsBtn = By.xpath("//button[contains(text(),'Add to cart')]");
    By cartQuantityValue  = By.xpath("//td[@class='cart_quantity']/button");
    

    By addToCartButtonList = By.xpath("//a[contains(text(),'Add to cart')]");
    By addToCartButtonDetail = By.xpath("//button[@class='btn btn-default cart']");
    
    By addToCartBtnFromList = By.xpath("(//button[@class='btn btn-default cart'])[1]");
    By viewCartBtnFromPopup = By.xpath("//u[normalize-space()='View Cart']");
    
    
 // Cart quantity update
    By cartQuantityInputBox = By.xpath("//input[@class='cart_quantity_input']");
    By cartUpdateButton = By.xpath("//button[@class='cart_quantity_update']");
    // Remove all items
    By removeAllProductsBtn = By.xpath("//a[@class='cart_quantity_delete']");
    // Continue Shopping from Cart
    By continueShoppingFromCartBtn = By.xpath("//a[contains(text(),'Continue Shopping')]");
    
    By cartQuantityInput = By.cssSelector("input.cart_quantity_input");
    By continueShoppingBtnPopup = By.cssSelector(".btn.btn-success.close-modal.btn-block"); 
    
    By productImage = By.cssSelector(".product-information img");
    By zoomedImageOverlay = By.cssSelector(".zoomWindow, .zoom-overlay, .fancybox-opened");
    
    By continueShoppingButton = By.cssSelector(".btn.btn-success.close-modal.btn-block");
    
    By newsletterInput = By.id("susbscribe_email");
    By newsletterSubmitButton = By.id("subscribe");
    By subscriptionSuccessMessage = By.xpath("//div[@class='alert-success alert']");
    By subscriptionErrorMessage = By.xpath("//input[@id='susbscribe_email']");


    // Dummy
    By nameOnCardField     = By.name("name_on_card");
    By cardNumberField     = By.name("card_number");
    By cvcField            = By.name("cvc");
    By expiryMonthField    = By.name("expiry_month");
    By expiryYearField     = By.name("expiry_year");
    By payConfirmButton    = By.id("submit"); 
    
    By firstProductViewLink = By.xpath("(//a[contains(text(),'View Product')])[1]");
    By productDetailName = By.xpath("//h2[@class='title text-center']");
    By productDetailCategory = By.xpath("//p[contains(text(),'Category')]");
    By productDetailPrice = By.xpath("//span/span[contains(text(),'Rs.')]");
    By productDetailAvailability = By.xpath("//b[contains(text(),'Availability:')]");
    By productDetailCondition = By.xpath("//b[contains(text(),'Condition:')]");
    By productDetailBrand = By.xpath("//p[b[text()='Brand:']]");

    By productDetailInfoBlock = By.cssSelector(".product-information");

    
    By productQuantityInput = By.id("quantity");
    By productDetailAddToCart = By.xpath("//button[contains(., 'Add to cart')]");
    By cartQuantity = By.xpath("//td[@class='cart_quantity']//button");

    By addToCartPopup = By.cssSelector("div.modal-content");
    By viewCartBtn = By.cssSelector("a[href='/view_cart']");
    
    By orderCommentBox = By.name("message");
    
    By viewCartButtonInPopup = By.xpath("//u[normalize-space()='View Cart']/parent::a");

    

    By categoryLinks = By.cssSelector(".left-sidebar .category-products a");
    By brandLinks = By.cssSelector(".left-sidebar .brands_products a");
    
 // Locators for review
    By nameInput = By.id("name");
    By emailInput = By.id("email");
    By reviewTextarea = By.id("review");
    By submitReviewButton = By.id("button-review");
    By successAlert = By.xpath("//span[contains(text(),'Thank you for your review.')]");
    
    By commentTextArea = By.name("message"); // locator for comment box in checkout page

    

    By orderSuccessMessage = By.xpath("//*[contains(text(),'Congratulations! Your order has been confirmed!')]");
	By orderFailMessage = By.xpath("//*[contains(text(),'Payment failed')]");
	
	

	/*from selenium import webdriver
	 *from selenium.webdriver.chrome.options import Options

	 *options = Options()
     *options.binary_location = '/Applications/Brave Browser.app/Contents/MacOS/Brave Browser'
     *driver_path = '/usr/local/bin/chromedriver'
     *drvr = webdriver.Chrome(options = options, executable_path = driver_path)
     *drvr.get('https://stackoverflow.com') */

    // Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    //Page Check
    public boolean isProductPageDisplayed() {
        try {
            return driver.findElement(productsHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getEmailValidationMessage() {
        WebElement emailField = driver.findElement(emailInput);
        return emailField.getAttribute("validationMessage"); 
    }
    

    //Search Functionality
    public void searchProduct(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        driver.findElement(searchButton).click();
    }

    public boolean areSearchResultsDisplayed() {
        try {
            List<WebElement> results = driver.findElements(productCards);
            return results != null && results.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isNoSearchResultsMessageDisplayed() {
        List<WebElement> products = driver.findElements(By.cssSelector(".features_items .product-image-wrapper"));
        return products.isEmpty(); // true if no products found
    }
    
    public void openFirstProductDetails() {
        driver.findElement(firstProductViewLink).click();
    }
    
    public void enterOrderComment(String comment) {
        WebElement commentBox = driver.findElement(orderCommentBox);
        commentBox.clear();
        commentBox.sendKeys(comment);
    }

    // Verify comment entered
    public boolean isOrderCommentEntered(String expectedText) {
        String actual = driver.findElement(orderCommentBox).getAttribute("value");
        return actual.equals(expectedText);
    }

    public boolean isProductDetailDisplayed() {
        return driver.findElement(productDetailName).isDisplayed() &&
               driver.findElement(productDetailCategory).isDisplayed() &&
               driver.findElement(productDetailPrice).isDisplayed() &&
               driver.findElement(productDetailAvailability).isDisplayed() &&
               driver.findElement(productDetailCondition).isDisplayed() &&
               driver.findElement(productDetailBrand).isDisplayed();
    }
    
 // Navigate to first product detail page
    public void openFirstProductDetail() {
        driver.findElement(firstProductViewLink).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }
    
    public void clickProductImage() {
        driver.findElement(productImage).click();
    }
    
    
    public boolean isAddToCartPopupDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(addToCartPopup)
            );
            return popup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areAddToCartPopupOptionsPresent() {
        try {
            WebElement continueBtn = driver.findElement(continueShoppingBtn);
            WebElement viewCart = driver.findElement(viewCartBtn);
            return continueBtn.isDisplayed() && viewCart.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    public void clickAddToCartBtnFromList() {
        driver.findElement(addToCartBtnFromList).click();
    }

    public void clickViewCartBtnFromPopup() {
        driver.findElement(viewCartBtnFromPopup).click();
    }
    
 // Method: click "View Cart" from popup
    public void clickViewCartFromPopup() {
        driver.findElement(viewCartButtonInPopup).click();
    }

    // Method: verify Cart page is displayed
    public boolean isCartPageDisplayed() {
        return driver.getCurrentUrl().contains("view_cart") 
            || driver.getTitle().contains("Cart");
    }


    // Method to check that zoom is NOT supported
    public boolean isProductImageZoomUnsupported() {
        return driver.findElements(zoomedImageOverlay).isEmpty();
    }

    // Fill review form
    public void submitReview(String name, String email, String reviewText) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(reviewTextarea).sendKeys(reviewText);
        driver.findElement(submitReviewButton).click();
    }

    // Verify success message
    public boolean isReviewSubmitted() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(successAlert))
            .isDisplayed();
    }

    
    
 // Click parent category (Women, Men, Kids, etc.)
    public void selectCategory(String parentCategoryName) {
        List<WebElement> categories = driver.findElements(categoryLinks);
        for (WebElement cat : categories) {
            if (cat.getText().trim().equalsIgnoreCase(parentCategoryName)) {
                cat.click();
                break;
            }
        }
    }

    // Click subcategory (Dress, Tops, Saree, etc.)
    public void selectSubCategory(String subCategoryName) {
        List<WebElement> subCategories = driver.findElements(categoryLinks);
        for (WebElement subCat : subCategories) {
            if (subCat.getText().trim().equalsIgnoreCase(subCategoryName)) {
                subCat.click();
                break;
            }
        }
    }

    // Validate that at least one product is displayed
    public boolean areFilteredProductsDisplayed() {
        return !driver.findElements(productCards).isEmpty();
    }

    
 // Check all displayed products belong to a top-level category
    public boolean areAllProductsInTopCategory(String categoryName) {
        List<WebElement> products = driver.findElements(productCards);

        for (WebElement product : products) {
            product.findElement(By.xpath(".//a[contains(text(),'View Product')]")).click();
            String categoryText = driver.findElement(productDetailCategory).getText(); // e.g. "Category: Women > Dress"
            if (!categoryText.contains(categoryName)) return false;
            driver.navigate().back();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
        }
        return true;
    }

    // Check all displayed products belong to a specific brand
    public boolean areAllProductsOfBrand(String brandName) {
        List<WebElement> products = driver.findElements(productCards);

        for (WebElement product : products) {
            product.findElement(By.xpath(".//a[contains(text(),'View Product')]")).click();
            String brandText = driver.findElement(productDetailBrand).getText(); // e.g. "Brand: Polo"
            if (!brandText.contains(brandName)) return false;
            driver.navigate().back();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
        }
        return true;
    }


    // Filter by category
    public void filterByCategory(String categoryName) {
        List<WebElement> categories = driver.findElements(categoryLinks);
        for (WebElement cat : categories) {
            if (cat.getText().trim().equalsIgnoreCase(categoryName)) {
                cat.click();
                break;
            }
        }
    }
    
    
    // Click a brand in the side-bar
    public void filterByBrand(String brandName) {
        List<WebElement> brands = driver.findElements(brandLinks);
        for (WebElement brand : brands) {
            String brandText = brand.getText().trim();  // e.g., "Polo (5)"
            if (brandText.toLowerCase().contains(brandName.toLowerCase())) {
                brand.click();
                return;
            }
        }
        throw new RuntimeException("Brand not found in sidebar: " + brandName);
    }

    
    public boolean areFilteredProductsOfBrand(String expectedBrand) {
        List<WebElement> products = driver.findElements(productCards);

        for (int i = 0; i < products.size(); i++) {
            // Re-fetch product list to avoid stale element
            products = driver.findElements(productCards);

            // Open the i-th product detail page
            products.get(i).findElement(By.xpath(".//a[contains(text(),'View Product')]")).click();

            // Wait for brand label inside product detail page
            WebElement brandElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(productDetailBrand));

            String brandText = brandElement.getText().trim(); // e.g., "Brand: Polo"
            System.out.println("DEBUG - Extracted brand text: [" + brandText + "]");

            // Check
            if (!brandText.equals("Brand: " + expectedBrand)) {
                driver.navigate().back();
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
                return false;
            }

            // Navigate back to products page
            driver.navigate().back();

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
        }

        return true;
    }
    
    // Get HTML5 validation message when invalid email is entered
    public String getSubscriptionEmailValidationMessage() {
        WebElement emailField = driver.findElement(newsletterInput);
        return emailField.getAttribute("validationMessage"); // standard HTML5 browser message
    }


    // Validate that all displayed products contain a keyword
    public boolean doAllProductsContain(String keyword) {
        List<WebElement> products = driver.findElements(productCards);
        for (WebElement product : products) {
            if (!product.getText().toLowerCase().contains(keyword.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    
    public void addCheckoutComment(String comment) {
        driver.findElement(commentTextArea).sendKeys(comment);
    }

    // Method: Click Place Order
    public void clickPlaceOrder() {
        driver.findElement(placeOrderButton).click();
    }
    
    public boolean areFilteredProductsOfCategory(String categoryName) {
        int count = driver.findElements(productCards).size();

        for (int i = 0; i < count; i++) {
            // Always re-fetch the list to avoid stale element
            List<WebElement> products = driver.findElements(productCards);

            // Click the i-th product's "View Product" link
            products.get(i).findElement(By.xpath(".//a[contains(text(),'View Product')]")).click();

            // Wait for category label to be visible
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(productDetailCategory));

            String categoryText = driver.findElement(productDetailCategory).getText();
            if (!categoryText.contains(categoryName)) return false;

            driver.navigate().back();

            // Wait until products are visible again
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
        }
        return true;
    }


    
 // Open first product's detail page
    public void clickFirstProductDetailLink() {
        driver.findElements(viewProductLinks).get(0).click();
    }

    // Verify product detail info block is shown
    public boolean isProductDetailInfoBlockVisible() {
        return !driver.findElements(productDetailInfoBlock).isEmpty();
    }

    
    public void setProductQuantity(int qty) {
        WebElement quantity = driver.findElement(productQuantityInput);
        quantity.clear();
        quantity.sendKeys(String.valueOf(qty));
    }

    public void addProductToCartFromDetails() {
        driver.findElement(productDetailAddToCart).click();
    }

    public String getCartQuantity() {
        return driver.findElement(cartQuantity).getText();
    }
    
    
    public void searchWithPartialName(String partialName) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(partialName);
        driver.findElement(searchButton).click();
    }
    
    public void searchWithSpecialCharacters(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        driver.findElement(searchButton).click();
    }
    
    public void searchWithSpacesOnly() {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys("   "); // spaces
        driver.findElement(searchButton).click();
    }
    
 // ProductPage.java
    public boolean isSearchResultPageBlank() {
        try {
            // Wait a bit to allow page to load
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            List<WebElement> results = driver.findElements(productCards);
            return results.isEmpty();  // true if no products
        } catch (Exception e) {
            return true; // Treat any exception as blank page
        }
    }

    
    
 // Search with empty input
    public void searchWithEmptyInput() {
        driver.findElement(searchInput).clear();
        driver.findElement(searchButton).click();
    }

    // Check if no results message appears
    public boolean isNoResultsMessageDisplayed() {
        return !driver.findElements(By.cssSelector(".features_items .product-image-wrapper")).isEmpty();
    }


    // Add to Cart Functionality
    public void addFirstProductToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait until the "View Cart" button in the modal is clickable
        WebElement viewCartBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//u[contains(text(),'View Cart')]"))
        );
        viewCartBtn.click();
    }
    
    public void addToCartButton() {
        driver.findElement(addToCartButton).click();
    }

    // Method: Click View Cart
    public void viewCartButton() {
        driver.findElement(viewCartButton).click();
    }
    
    
    public boolean isProductInCart(String productName) {
        try {
            List<WebElement> items = driver.findElements(cartItems);
            for (WebElement item : items) {
                if (item.getText().contains(productName)) {
                    return true;
                }
            }
            return false;
        } 
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean isCartQuantityInputPresent() {
        return !driver.findElements(cartQuantityInput).isEmpty();
    }
    
    public void clickFirstViewProduct() {
        driver.findElements(viewProductLinks).get(0).click();
    }

    public boolean isProductDetailsDisplayed() {
        return !driver.findElements(productInfoSection).isEmpty();
    }

    public void setQuantity(String qty) {
        WebElement qtyInput = driver.findElement(quantityInputField);
        qtyInput.clear();
        qtyInput.sendKeys(qty);
    }

    public void addToCartFromDetails() {
        driver.findElement(addToCartFromDetailsBtn).click();
    }

    public String getQuantityInCart() {
        return driver.findElement(cartQuantityValue).getText().trim();
    }

    public void addFirstProductFromList() {
        driver.findElements(addToCartButtonList).get(0).click();
    }

    public void addProductFromDetailPage(int qty) {
        WebElement qtyInput = driver.findElement(quantityInput);
        qtyInput.clear();
        qtyInput.sendKeys(String.valueOf(qty));

        driver.findElement(addToCartButtonDetail).click();
    }
    
    public void clickContinueShoppingFromPopup() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement continueBtn = wait.until(ExpectedConditions
            .elementToBeClickable(continueShoppingBtnPopup));
        continueBtn.click();
    }


    // Update quantity in cart
    public void updateCartItemQuantity(String newQty) {
        WebElement qtyInput = driver.findElement(cartQuantityInputBox);
        qtyInput.clear();
        qtyInput.sendKeys(newQty);
        driver.findElement(cartUpdateButton).click();
    }

    public String getUpdatedCartQuantity() {
        return driver.findElement(cartQuantityInputBox).getAttribute("value");
    }


    // Continue shopping from cart
    public void clickContinueShoppingFromCart() {
        driver.findElement(continueShoppingFromCartBtn).click();
    }


    // Price Check
    public String getFirstProductPrice() {
        return driver.findElement(priceLabel).getText();
    }

    // Brand filter functionality
    public void selectBrand(String brandName) {
        List<WebElement> brands = driver.findElements(brandList);
        for (WebElement brand : brands) {
            if (brand.getText().equalsIgnoreCase(brandName)) {
                brand.click();
                break;
            }
        }
    }
    
    public void addMultipleProductsToCart(int count) {
        List<WebElement> addButtons = driver.findElements(addToCartButton);

        Actions actions = new Actions(driver);

        for (int i = 0; i < count && i < addButtons.size(); i++) {
            WebElement button = addButtons.get(i);

            // Hover over product before clicking
            actions.moveToElement(button).perform();

            button.click();

            // Wait for "Continue Shopping" modal and click it
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement continueBtn = wait.until(
                ExpectedConditions.elementToBeClickable(continueShoppingBtn)
            );
            continueBtn.click();
        }
    }
    
 // In ProductPage.java
    public void removeAllProductsFromCart() {
        List<WebElement> removeButtons = driver.findElements(removeProductBtn);

        for (WebElement button : removeButtons) {
            try {
                button.click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.stalenessOf(button)); // wait until removed
            } catch (Exception e) {
                System.out.println("Could not remove a product: " + e.getMessage());
            }
        }
    }

    
    // Checkout
    public void proceedToCheckout() {
        driver.findElement(proceedToCheckout).click();
    }

    public void placeOrder() {
        driver.findElement(placeOrderButton).click();
    }

    public void enterPaymentDetails(String name, String cardNumber, String cvc, String month, String year) {
        driver.findElement(nameOnCardField).sendKeys(name);
        driver.findElement(cardNumberField).sendKeys(cardNumber);
        driver.findElement(cvcField).sendKeys(cvc);
        driver.findElement(expiryMonthField).sendKeys(month);
        driver.findElement(expiryYearField).sendKeys(year);
        driver.findElement(payConfirmButton).click();
    }

    public boolean isOrderSuccessMessageDisplayed() {
        try {
            return driver.findElement(orderSuccessMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOrderFailureMessageDisplayed() {
        try {
            return driver.findElement(orderFailMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



     public void removeFirstProductFromCart() {
            driver.findElement(removeProductBtn).click();
        }

     public boolean isCartEmpty() {
    	    try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    	        // Wait until either cart items disappear OR empty message appears
    	        boolean noItems = wait.until(ExpectedConditions.invisibilityOfElementLocated(cartItems));
    	        boolean emptyMessage = !driver.findElements(emptyCartMessage).isEmpty();

    	        return noItems || emptyMessage;
    	    } catch (Exception e) {
    	        return false;
    	    }
    	}
    

  // Get cart total method
     public String getCartTotal() {
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         WebElement totalElement = wait.until(
             ExpectedConditions.visibilityOfElementLocated(cartTotal)
         );
         return totalElement.getText();
     }

     


        // Recommended Items
     public boolean isRecommendedSectionVisible() {
            return driver.findElement(recommendedSection).isDisplayed();
        }

     public void addRecommendedItemToCart() {
            driver.findElement(recommendedAddToCart).click();
        }

        // Subscription 
     public void subscribeNewsletter(String email) {
            driver.findElement(subscriptionField).sendKeys(email);
            driver.findElement(subscriptionBtn).click();
        }

     public boolean isSubscriptionSuccessDisplayed() {
            return driver.findElement(subscriptionSuccess).isDisplayed();
        }

}
