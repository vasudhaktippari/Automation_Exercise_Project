package com.automationexercise.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.base.BaseTest;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;

public class ContactUsTest extends BaseTest {

    // ------------------- Data Provider -------------------
    @DataProvider(name = "contactUsData")
    public Object[][] getContactUsData() throws IOException {
        return ExcelUtilities.getData("ContactUs");
    }

    // ------------------- Data-driven Contact Us Form -------------------
    @Test(dataProvider = "contactUsData", groups = {"functional", "Regression", "Data-driven"})
    public void testContactUsForm(String name, String email, String subject, String message, String expectedMessage) {
        try {
            getDriver().get("https://automationexercise.com/contact_us");
            getTest().info("Opened Contact Us page");

            WebElement nameField = getDriver().findElement(By.name("name"));
            WebElement emailField = getDriver().findElement(By.name("email"));
            WebElement subjectField = getDriver().findElement(By.name("subject"));
            WebElement messageField = getDriver().findElement(By.name("message"));
            WebElement submitBtn = getDriver().findElement(By.name("submit"));

            // Fill form
            nameField.clear();
            nameField.sendKeys(name);
            emailField.clear();
            emailField.sendKeys(email);
            subjectField.clear();
            subjectField.sendKeys(subject);
            messageField.clear();
            messageField.sendKeys(message);

            getTest().info(String.format("Filled form -> name: %s | email: %s | subject: %s | message: %s",
                    name, email, subject, message));

            // Submit
            submitBtn.click();
            getTest().info("Clicked Submit");

            // ---------- INVALID NAME CASE ----------
            boolean invalidNameCase = expectedMessage.equalsIgnoreCase("INVALID_NAME");
            if (invalidNameCase) {
                // Wait for banner (success or error)
                By bannerLocator = By.cssSelector(".status.alert-success, .status.alert, .error");
                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
                WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(bannerLocator));
                String actualMessage = banner.getText().trim();

                if (actualMessage.toLowerCase().contains("success") || actualMessage.toLowerCase().contains("sent")) {
                    // ❌ App wrongly accepted invalid name
                    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "InvalidName_UnexpectedSuccess");
                    getTest().fail("App accepted invalid name! Expected rejection, but got success: " + actualMessage)
                            .addScreenCaptureFromPath(screenshotPath);
                    assert false : "App wrongly accepted invalid name input.";
                } else {
                    getTest().pass("Application correctly rejected invalid name. Message: " + actualMessage);
                }
                return; // stop here for invalid name case
            }

            // ---------- INVALID / BLANK EMAIL ----------
            String expLower = expectedMessage == null ? "" : expectedMessage.toLowerCase();
            if (email.trim().isEmpty() || !email.contains("@")) {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", emailField);
                String vmLower = validationMessage == null ? "" : validationMessage.toLowerCase();
                getTest().info("Browser validation message: " + validationMessage);

                boolean matches;
                if (email.trim().isEmpty()) {
                    matches = vmLower.contains("fill out this field") || vmLower.contains("please fill");
                } else {
                    matches = vmLower.contains("include an '@'") || vmLower.contains("valid email");
                }

                if (matches) {
                    getTest().pass("Validation message matched expectation for invalid/blank email.");
                } else {
                    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "InvalidOrBlankEmail");
                    getTest().fail("Validation mismatch. Expected hint: [" + expectedMessage + "], Actual: [" + validationMessage + "]")
                            .addScreenCaptureFromPath(screenshotPath);
                }
                assert matches : "Expected: [" + expectedMessage + "], Actual: [" + validationMessage + "]";
                return;
            }

            // ---------- VALID CASE ----------
            try {
                WebDriverWait waitAlert = new WebDriverWait(getDriver(), Duration.ofSeconds(8));
                waitAlert.until(ExpectedConditions.alertIsPresent());
                Alert confirmAlert = getDriver().switchTo().alert();
                getTest().info("Confirmation alert: " + confirmAlert.getText());
                confirmAlert.accept();
                getTest().info("Alert accepted");
            } catch (Exception e) {
                getTest().info("No confirmation alert appeared.");
            }

            By successBanner = By.cssSelector(".status.alert-success, .status.alert");
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
            WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(successBanner));
            String actualMessage = banner.getText().trim();

            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                getTest().pass("Success message matched expected: " + expectedMessage);
            } else {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), name + "_ContactUs_SuccessMismatch");
                getTest().fail("Success message mismatch. Expected: [" + expectedMessage + "], Actual: [" + actualMessage + "]")
                        .addScreenCaptureFromPath(screenshotPath);
            }
            assert actualMessage.equalsIgnoreCase(expectedMessage)
                    : "Expected: [" + expectedMessage + "] but got: [" + actualMessage + "]";

        } catch (Exception e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "Exception_ContactUs");
                getTest().fail("Test failed with exception: " + e.getMessage())
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ioEx) {
                getTest().fail("Failed to capture screenshot: " + ioEx.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    // ------------------- UI Tests -------------------
    @Test(groups = {"ui", "Regression","smoke"})
    public void verifyContactUsPageTitle() {
        getDriver().get("https://automationexercise.com/contact_us");
        String title = getDriver().getTitle();
        getTest().info("Page title is: " + title);
        assert title.contains("Contact Us") : "Contact Us page title mismatch!";
        getTest().pass("Contact Us page title verified successfully");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyFeedbackSectionVisible() {
        getDriver().get("https://automationexercise.com/contact_us");
        WebElement feedbackSection = getDriver().findElement(By.cssSelector(".contact-form"));
        assert feedbackSection.isDisplayed() : "Feedback section is not visible!";
        getTest().pass("Feedback section is visible");
    }

    @Test(groups = {"ui", "Regression","smoke"})
    public void verifyContactUsPageLoadsProperly() {
        getDriver().get("https://automationexercise.com/contact_us");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        assert nameField.isDisplayed() : "Page did not load properly!";
        getTest().pass("Contact Us page loaded properly");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyAllInputFieldsVisible() {
        getDriver().get("https://automationexercise.com/contact_us");

        WebElement nameField = getDriver().findElement(By.name("name"));
        WebElement emailField = getDriver().findElement(By.name("email"));
        WebElement subjectField = getDriver().findElement(By.name("subject"));
        WebElement messageField = getDriver().findElement(By.name("message"));
        WebElement fileUpload = getDriver().findElement(By.name("upload_file"));

        assert nameField.isDisplayed() : "Name field not visible";
        assert emailField.isDisplayed() : "Email field not visible";
        assert subjectField.isDisplayed() : "Subject field not visible";
        assert messageField.isDisplayed() : "Message field not visible";
        assert fileUpload.isDisplayed() : "File upload field not visible";

        getTest().pass("All input fields are visible");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifySubmitButtonEnabled() {
        getDriver().get("https://automationexercise.com/contact_us");
        WebElement submitBtn = getDriver().findElement(By.name("submit"));
        assert submitBtn.isEnabled() : "Submit button is not enabled";
        getTest().pass("Submit button is enabled");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyPlaceholderTexts() {
        getDriver().get("https://automationexercise.com/contact_us");

        WebElement nameField = getDriver().findElement(By.name("name"));
        WebElement emailField = getDriver().findElement(By.name("email"));
        WebElement subjectField = getDriver().findElement(By.name("subject"));
        WebElement messageField = getDriver().findElement(By.name("message"));

        assert nameField.getAttribute("placeholder").equalsIgnoreCase("Name") : "Name placeholder mismatch";
        assert emailField.getAttribute("placeholder").equalsIgnoreCase("Email") : "Email placeholder mismatch";
        assert subjectField.getAttribute("placeholder").equalsIgnoreCase("Subject") : "Subject placeholder mismatch";
        assert messageField.getAttribute("placeholder").equalsIgnoreCase("Your message here") : "Message placeholder mismatch";

        getTest().pass("All placeholder texts are correct");
    }
}
