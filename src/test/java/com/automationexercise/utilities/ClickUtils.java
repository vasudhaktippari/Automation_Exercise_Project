// File: src/main/java/com/automationexercise/utilities/ClickUtils.java
package com.automationexercise.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class ClickUtils {

    private static final Duration CLICK_WAIT = Duration.ofSeconds(15);
    private static final By ADS_IFRAMES = By.cssSelector(
        "iframe[id^='aswift_'], iframe[id^='google_ads_iframe']"
    );

    /**
     * Public helper to proactively suppress common ad iframes/overlays.
     */
    public static void suppressAds(WebDriver driver) {
        hideAdIframes(driver);
    }

    /**
     * Try to click an element even if an ad overlay is on top or the element goes stale.
     * Strategy:
     *  1) Wait presence ➜ scroll into view ➜ refreshed(clickable) ➜ move ➜ click
     *  2) On intercept: hide ad iframes and retry
     *  3) On last attempt: JS click
     */
    public static void safeClick(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, CLICK_WAIT);

        for (int attempt = 0; attempt < 4; attempt++) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                scrollIntoView(driver, el);

                el = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(el)));
                new Actions(driver).moveToElement(el).pause(Duration.ofMillis(80)).perform();
                el.click();
                return;

            } catch (ElementClickInterceptedException e) {
                hideAdIframes(driver);
                if (attempt == 3) {
                    WebElement el = driver.findElement(locator);
                    jsClick(driver, el);
                    return;
                }
                sleep(200);

            } catch (StaleElementReferenceException e) {
                // Element got detached; retry the loop to refetch
                sleep(120);

            } catch (TimeoutException e) {
                // Could not reach clickable state in time, try JS as a last resort
                try {
                    WebElement el = driver.findElement(locator);
                    jsClick(driver, el);
                    return;
                } catch (Exception ignore) {
                    // fall through to next attempt
                }
            }
        }
        throw new TimeoutException("Could not click: " + locator);
    }

    /** Ensure checkbox matches desired state. */
    public static void setCheckbox(WebDriver driver, By locator, boolean shouldBeChecked) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        if (el.isSelected() != shouldBeChecked) {
            safeClick(driver, locator);
        }
    }

    // ---- Private helpers ----
    private static void hideAdIframes(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            List<WebElement> frames = driver.findElements(ADS_IFRAMES);
            for (WebElement f : frames) {
                try {
                    js.executeScript(
                        "try{arguments[0].style.pointerEvents='none';}catch(e){}" +
                        "try{arguments[0].style.display='none';}catch(e){}", f);
                } catch (Exception ignore) {}
            }
            // Also attempt to close common ad overlays if present
            try {
                js.executeScript(
                    "var closeBtns=document.querySelectorAll(\"[id*='close'], .close, .ad_close, .adsbygoogle\");" +
                    "closeBtns.forEach(function(b){try{b.click()}catch(e){}});"
                );
            } catch (Exception ignore) {}
        } catch (Exception ignore) {}
    }

    private static void scrollIntoView(WebDriver driver, WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", el);
        } catch (Exception ignore) {}
    }

    private static void jsClick(WebDriver driver, WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
