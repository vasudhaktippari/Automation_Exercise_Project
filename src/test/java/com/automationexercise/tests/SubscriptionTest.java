package com.automationexercise.tests;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.SubscriptionPage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;

public class SubscriptionTest extends BaseTest {

    // ------------------- Data Provider for emails -------------------
    @DataProvider(name = "subscriptionData")
    public Object[][] getSubscriptionData() throws IOException {
        return ExcelUtilities.getData("Subscription"); 
    }

    // ------------------- Data Provider for pages -------------------
    @DataProvider(name = "pagesToTest")
    public Object[][] getPages() {
        return new Object[][] {
            {"https://automationexercise.com/"},
            {"https://automationexercise.com/products"},
            {"https://automationexercise.com/cart"},
            {"https://automationexercise.com/contact_us"},
            {"https://automationexercise.com/login"},
            {"https://automationexercise.com/test_cases"},
            {"https://automationexercise.com/api_list"}
        };
    }

    // ------------------- Data-driven Subscription Test -------------------
    @Test(dataProvider = "subscriptionData", groups = {"functional", "Regression", "Data-Driven"})
    public void testSubscriptionForm(String email, String expectedMessage) {
        try {
            getDriver().get("https://automationexercise.com/");
            getTest().info("Opened Homepage for subscription");

            SubscriptionPage subPage = new SubscriptionPage(getDriver());

            subPage.enterEmail(email);
            getTest().info("Entered Email: " + email);

            subPage.clickSubscribe();
            getTest().info("Clicked Subscribe button");

            if (email.isEmpty() || !email.contains("@")) {
                getTest().pass("Invalid/Blank email triggered browser validation: " + expectedMessage);
            } else {
                String actualMessage = subPage.getSuccessMessage();
                getTest().info("Actual subscription message: " + actualMessage);

                if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                    getTest().pass("Message matched expected: " + expectedMessage);
                } else {
                    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "Subscription_" + email);
                    getTest().fail("Message mismatch. Expected: [" + expectedMessage + "], Actual: [" + actualMessage + "]")
                            .addScreenCaptureFromPath(screenshotPath);
                }

                assert actualMessage.equalsIgnoreCase(expectedMessage)
                        : "Expected: [" + expectedMessage + "] but got: [" + actualMessage + "]";
            }

        } catch (Exception e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "Exception_Subscription");
                getTest().fail("Test failed with exception: " + e.getMessage())
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ioEx) {
                getTest().fail("Failed to capture screenshot: " + ioEx.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    

    @Test(dataProvider = "pagesToTest", groups = {"ui", "Regression"})
    public void verifySubscriptionSectionVisible(String pageUrl) {
        try {
            getDriver().get(pageUrl);
            SubscriptionPage subPage = new SubscriptionPage(getDriver());
            assert subPage.isSubscriptionSectionVisible() : "Subscription section not visible on " + pageUrl;
            getTest().pass("Subscription section is visible on " + pageUrl);
        } catch (AssertionError e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SubscriptionSection_" + pageUrl.hashCode());
                getTest().fail("Subscription section not visible on " + pageUrl)
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ignored) {}
            throw e;
        }
    }

    @Test(dataProvider = "pagesToTest", groups = {"ui", "Regression"})
    public void verifyEmailFieldEnabled(String pageUrl) {
        try {
            getDriver().get(pageUrl);
            SubscriptionPage subPage = new SubscriptionPage(getDriver());
            assert subPage.isEmailFieldEnabled() : "Email field not enabled on " + pageUrl;
            getTest().pass("Email field is enabled on " + pageUrl);
        } catch (AssertionError e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "EmailField_" + pageUrl.hashCode());
                getTest().fail("Email field not enabled on " + pageUrl)
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ignored) {}
            throw e;
        }
    }

    @Test(dataProvider = "pagesToTest", groups = {"ui", "Regression"})
    public void verifyEmailPlaceholder(String pageUrl) {
        try {
            getDriver().get(pageUrl);
            SubscriptionPage subPage = new SubscriptionPage(getDriver());
            String placeholder = subPage.getEmailFieldPlaceholder();
            assert placeholder.equalsIgnoreCase("Your email address") : "Placeholder mismatch on " + pageUrl;
            getTest().pass("Email placeholder is correct on " + pageUrl);
        } catch (AssertionError e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "EmailPlaceholder_" + pageUrl.hashCode());
                getTest().fail("Placeholder mismatch on " + pageUrl)
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ignored) {}
            throw e;
        }
    }

    @Test(dataProvider = "pagesToTest", groups = {"ui", "Regression"})
    public void verifySubscribeButtonEnabled(String pageUrl) {
        try {
            getDriver().get(pageUrl);
            SubscriptionPage subPage = new SubscriptionPage(getDriver());
            assert subPage.isSubscribeButtonEnabled() : "Subscribe button not enabled on " + pageUrl;
            getTest().pass("Subscribe button is enabled on " + pageUrl);
        } catch (AssertionError e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "SubscribeButton_" + pageUrl.hashCode());
                getTest().fail("Subscribe button not enabled on " + pageUrl)
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ignored) {}
            throw e;
        }
    }
}
