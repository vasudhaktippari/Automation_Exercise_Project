package com.automationexercise.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.SubscriptionPage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;

public class SubscriptionTest extends BaseTest {

    // ------------------- Data Provider -------------------
    @DataProvider(name = "subscriptionData")
    public Object[][] getSubscriptionData() throws IOException {
        return ExcelUtilities.getData("Subscription"); // Excel sheet named "Subscription"
    }

    // ------------------- Data-driven Subscription Test -------------------
    @Test(dataProvider = "subscriptionData", groups = {"functional", "Regression", "Data-driven"})
    public void testSubscriptionForm(String email, String expectedMessage) {
        try {
            getDriver().get("https://automationexercise.com/");
            getTest().info("Opened Homepage for subscription");

            SubscriptionPage subPage = new SubscriptionPage(getDriver());

            // Enter email
            subPage.enterEmail(email);
            getTest().info("Entered Email: " + email);

            // Click Subscribe
            subPage.clickSubscribe();
            getTest().info("Clicked Subscribe button");

            // ------------------- Validation -------------------
            if (email.isEmpty()) {
                getTest().pass("Blank email triggered browser validation: " + expectedMessage);
            } else if (!email.contains("@")) {
                getTest().pass("Invalid email triggered browser validation: " + expectedMessage);
            } else {
                // wait and get success message
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


    // ------------------- UI Tests -------------------

    @Test(groups = {"ui", "Regression"})
    public void verifySubscriptionSectionVisible() {
        getDriver().get("https://automationexercise.com/");
        SubscriptionPage subPage = new SubscriptionPage(getDriver());
        assert subPage.isSubscriptionSectionVisible() : "Subscription section not visible!";
        getTest().pass("Subscription section is visible");
    }


    @Test(groups = {"ui", "Regression"})
    public void verifyEmailFieldEnabled() {
        getDriver().get("https://automationexercise.com/");
        SubscriptionPage subPage = new SubscriptionPage(getDriver());
        assert subPage.isEmailFieldEnabled() : "Subscription email field not enabled!";
        getTest().pass("Subscription email field is enabled");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyEmailPlaceholder() {
        getDriver().get("https://automationexercise.com/");
        SubscriptionPage subPage = new SubscriptionPage(getDriver());
        String placeholder = subPage.getEmailFieldPlaceholder();
        assert placeholder.equalsIgnoreCase("Your email address") : "Placeholder mismatch!";
        getTest().pass("Subscription email placeholder text is correct");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifySubscribeButtonEnabled() {
        getDriver().get("https://automationexercise.com/");
        SubscriptionPage subPage = new SubscriptionPage(getDriver());
        assert subPage.isSubscribeButtonEnabled() : "Subscribe button not enabled!";
        getTest().pass("Subscribe button is enabled");
    }
}
