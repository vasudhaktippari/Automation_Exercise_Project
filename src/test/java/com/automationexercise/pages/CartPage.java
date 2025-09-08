 package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automationexercise.utilities.ScreenshotUtilities;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class CartPage {

    private static WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productsLink = By.xpath("//a[@href='/products']");
    private By viewCartLink =By.linkText("View Cart");
    private By cartTable = By.xpath("//*[@id=\"cart_info\"]");
    private static By cart=By.xpath("//a[@href='/view_cart']");
    private By removeItem = By.xpath("//*[@id='product-2']/td[6]/a");
    private By clickHereCart=By.xpath("//*[@id=\"empty_cart\"]/p/a/u");
    private By ProceedToCheckOut=By.xpath("//*[@id=\"do_action\"]/div[1]/div/div/a");
    private By LoginToCheckout=By.xpath("//*[@id='checkoutModal']//a[@href='/login']");
    private By commentBox=By.xpath("//*[@id=\"ordermsg\"]/textarea");
    private By placeOrderBtn=By.xpath("//a[@href='/payment']");
    private By UpArrow=By.id("scrollup");
    private static By Home=By.xpath("//a[@href='/']");
    private static By Product=By.xpath("//a[@href='/products']\r\n");
    private static By Login=By.xpath("//a[@href='/login']");
    private static By ApiList=By.xpath("//a[@href='/api_list']");
    private static By TestCase=By.xpath("//a[@href='/test_cases']\r\n");
    private static By ContactUs=By.xpath("//a[@href='/contact_us']\r\n");
    private static By Vedio=By.id("header");
    private static By logo = By.xpath("//div[@class='logo pull-left']");
    private static By logoClick = By.xpath("//div[@id='header']//a/img\r\n");
    private static  By footer = By.id("header"); // Example footer element
    private static  By header = By.id("footer");  // Example header element
      
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
     public CartPage openHome() {
         driver.get("https://automationexercise.com/");
         return this;
     }

     public LoginPage goToLogin() {
    	    By loginLink = By.xpath("//a[@href='/login']");
    	    WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
    	    loginBtn.click();
    	    wait.until(ExpectedConditions.urlContains("/login"));
    	    return new LoginPage(driver);
    	}

     public CartPage login(String email, String password) {
         // These fields have stable data-qa attributes on the site
         By emailField = By.cssSelector("input[data-qa='login-email']");
         By passwordField = By.cssSelector("input[data-qa='login-password']");
         By loginBtn = By.cssSelector("button[data-qa='login-button']");
         

         wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
         driver.findElement(emailField).sendKeys(email);
         driver.findElement(passwordField).clear();
         driver.findElement(passwordField).sendKeys(password);
         driver.findElement(loginBtn).click();

         // Verify login badge: "Logged in as <name>"
         By loggedInAs = By.xpath("//a[contains(.,'Logged in as')]");
         wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAs));
         return this;
     }
  // Check if user is already logged in
     public boolean isUserLoggedIn() {
         try {
             // Look for Logout button/link (only appears when logged in)
             WebElement logoutLink = new WebDriverWait(driver, Duration.ofSeconds(5))
                     .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Logout']")));
             return logoutLink.isDisplayed();
         } catch (Exception e) {
             return false; // If not found within timeout, user is not logged in
         }
     }


    // Navigate to Products Page
    public void goToProductsPage() {
        driver.findElement(productsLink).click();
        wait.until(ExpectedConditions.urlContains("/products"));
    }

    // Click Add to Cart dynamically by productId
    public void clickAddToCart(String productId) {
        By addBtn = By.xpath(String.format("//a[@data-product-id='%s']", productId));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(addBtn));

        // Scroll into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            // If still intercepted, click with JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }

        // Wait for modal or cart confirmation
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewCartLink));
    }


    // Click "View Cart" in popup
    public void clickViewCart() {
        driver.findElement(viewCartLink).click();
        wait.until(ExpectedConditions.urlContains("/view_cart"));
    }

    // Verify product in cart
    public boolean isProductInCart(String productName) {
        return driver.findElement(cartTable).getText().contains(productName);
    }
    public String captueCartPageScreenshot(String screenshotName) {
        try {
            return ScreenshotUtilities.captureScreen(driver, screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	public void ViewCart() {
		// TODO Auto-generated method stub
		driver.findElement(cart).click();
		 By  cartpage =(By.xpath("//*[@id=\"cart_items\"]/div/div[1]/ol/li[2]"));
         wait.until(ExpectedConditions.visibilityOfElementLocated(cartpage));
		 
		
	}
	public void RemoveCartItem() {
		    driver.findElement(removeItem).click();
		    // Optionally wait for the row to disappear (stale element or invisibility)
		    wait.until(ExpectedConditions.invisibilityOfElementLocated(removeItem));
		
	}
	public void ClickHereInCart() {
	    driver.findElement(clickHereCart).click();
	    // Optionally wait for the row to disappear (stale element or invisibility)
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("/html/body/section[2]/div/div/div[2]/div/h2" )));
	}
	public void ClickProceedToCheckout() {
		// TODO Auto-generated method stub
		driver.findElement(ProceedToCheckOut).click();
		 By checkout=By.xpath("//*[@id=\"cart_items\"]/div/div[1]/ol/li[2]");
         wait.until(ExpectedConditions.visibilityOfElementLocated( checkout));
		
	}
	public boolean isCheckoutPageDisplayed() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement checkoutHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
	    		 By.xpath("//*[@id='cart_items']/div/div[1]/ol/li[2]")));
		return checkoutHeading.isDisplayed();
	}
	public void LoginToProceedToCheckout() {
		// TODO Auto-generated method stub
		driver.findElement(LoginToCheckout).click();
		 By  LoginPage=By.xpath("//*[@id=\"form\"]/div/div/div[1]/div/h2");
         wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage));
	}

	public void enterComment(String text) {
	    WebElement box = driver.findElement(commentBox);

	    // Scroll into view
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", box);

	    // Click into box using Actions (in case ad overlay interferes)
	    Actions actions = new Actions(driver);
	    actions.moveToElement(box).click().perform();

	    box.clear();
	    box.sendKeys(text);
	}

	    public void clickPlaceOrder() {
	        driver.findElement(placeOrderBtn).click();
	        /*
	        By PlaceOrderPage=By.xpath("//*[@id=\"cart_items\"]/div/div[2]/h2");
	         wait.until(ExpectedConditions.visibilityOfElementLocated(Pl
	         */
	    }
	    public void clickPlaceOrderAndWait() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        WebElement placeOrder = wait.until(
	                ExpectedConditions.presenceOfElementLocated(placeOrderBtn)
	        );

	        // Scroll into view
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrder);

	        // Instead of normal click, use JS click (bypasses overlays)
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrder);

	        // Wait for next page to load (payment page header)
	        wait.until(ExpectedConditions.urlContains("/payment"));
	    }



	    private int extractNumber(String text) {
	        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
	    }
	    public boolean isCommentEntered() {
	        WebElement box = driver.findElement(commentBox);
	        String enteredText = box.getAttribute("value");  // get current text inside the textarea
	        return enteredText != null && !enteredText.trim().isEmpty();
	    }
	    public void ClickUpArrow() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        try {
	            // Scroll down first so the up arrow becomes visible
	            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

	            // Wait until the parent up arrow is visible
	            WebElement arrow = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(By.id("scrollUp"))
	            );

	            // Wait until clickable
	            wait.until(ExpectedConditions.elementToBeClickable(arrow));

	            // Try normal click
	            arrow.click();
	            System.out.println(" Clicked Up Arrow with normal click");

	        } catch (Exception e) {
	            // Fallback: click with JavaScript
	            WebElement arrow = driver.findElement(By.id("scrollUp"));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrow);
	            System.out.println("Normal click failed, used JS click instead");
	        }
	    }
	    public boolean isAddressDisplayed() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        try {
	            // Adjust the locator to match your page's Address section header
	            By addressSection = By.xpath("//h2[contains(text(),'Address Details')]");

	            WebElement address = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(addressSection)
	            );

	            return address.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }
		public static void ClcikCart() {
			// TODO Auto-generated method stub
             try {
				driver.findElement(cart).click();
			 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			
		}
		public static void ClickHome() {
			driver.findElement(Home).click();
		}
		public static void ClcikPrduct() {
			// TODO Auto-generated method stub
			driver.findElement(Product).click();
			
		}
		public static void ClickLogin() {
			// TODO Auto-generated method stub
			
			driver.findElement(Login).click();
		}
		public static void ClickApiList() {
			// TODO Auto-generated method stub
			
			driver.findElement(ApiList).click();
		}
		public static void ClickTestCase() {
			// TODO Auto-generated method stub
			
			driver.findElement(TestCase).click();
		}
		public static void ClickContactUs() {
			// TODO Auto-generated method stub
			
			driver.findElement(ContactUs).click();
		}
		public static void ClickVedioTutorial() {
			// TODO Auto-generated method stub
			
			driver.findElement(Vedio).click();
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
		 public boolean isLogoDisplayed() {
		        try {
		            return driver.findElement(logo).isDisplayed();
		        } catch (Exception e) {
		            return false;
		        }
		    }
		 public static void ClickLogo() {
				// TODO Auto-generated method stub
				
				driver.findElement(logo).click();
			}


		    public void scrollToFooter() {
		        WebElement el = driver.findElement(footer);
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
		    }

		    public void scrollToHeader() {
		        WebElement el = driver.findElement(header);
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
		    }

		    public static boolean isFooterDisplayed() {
		        return driver.findElement(footer).isDisplayed();
		    }

		    public static boolean isHeaderDisplayed() {
		        return driver.findElement(header).isDisplayed();
		    }
		
}