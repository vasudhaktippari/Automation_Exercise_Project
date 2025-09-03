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

            // Locate form fields
            WebElement nameField = getDriver().findElement(By.name("name"));
            WebElement emailField = getDriver().findElement(By.name("email"));
            WebElement subjectField = getDriver().findElement(By.name("subject"));
            WebElement messageField = getDriver().findElement(By.name("message"));
            WebElement submitBtn = getDriver().findElement(By.name("submit"));

            // Fill form
            nameField.clear();
            nameField.sendKeys(name);
            getTest().info("Entered Name: " + name);

            emailField.clear();
            emailField.sendKeys(email);
            getTest().info("Entered Email: " + email);

            subjectField.clear();
            subjectField.sendKeys(subject);
            getTest().info("Entered Subject: " + subject);

            messageField.clear();
            messageField.sendKeys(message);
            getTest().info("Entered Message: " + message);

            // Click Submit
            submitBtn.click();
            getTest().info("Clicked Submit button");

            // ------------------- Handle unexpected alert -------------------
            try {
                WebDriverWait waitAlert = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
                waitAlert.until(ExpectedConditions.alertIsPresent());
                Alert alertBox = getDriver().switchTo().alert();
                getTest().info("Unexpected alert detected: " + alertBox.getText());
                alertBox.accept();
                getTest().info("Alert accepted");
            } catch (Exception e) {
                getTest().info("No alert appeared after submit");
            }

            // ------------------- Verify success/error message -------------------
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            WebElement alertMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".status.alert")));
            String actualMessage = alertMsg.getText().trim();
            getTest().info("Actual message displayed: " + actualMessage);

            if (actualMessage.equalsIgnoreCase(expectedMessage)) {
                getTest().pass("Message matched expected: " + expectedMessage);
            } else {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), name + "_ContactUs");
                getTest().fail("Message did not match. Expected: [" + expectedMessage + "], Actual: [" + actualMessage + "]")
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

    // ------------------- UI Test 1: Verify Page Title -------------------
    @Test(groups = {"ui", "Regression","smoke"})
    public void verifyContactUsPageTitle() {
        getDriver().get("https://automationexercise.com/contact_us");
        String title = getDriver().getTitle();
        getTest().info("Page title is: " + title);
        assert title.contains("Contact Us") : "Contact Us page title mismatch!";
        getTest().pass("Contact Us page title verified successfully");
    }

    // ------------------- UI Test 2: Verify Feedback Section Visible -------------------
    @Test(groups = {"ui", "Regression"})
    public void verifyFeedbackSectionVisible() {
        getDriver().get("https://automationexercise.com/contact_us");
        WebElement feedbackSection = getDriver().findElement(By.cssSelector(".contact-form"));
        assert feedbackSection.isDisplayed() : "Feedback section is not visible!";
        getTest().pass("Feedback section is visible");
    }

    // ------------------- UI Test 3: Verify Page Loads Properly -------------------
    @Test(groups = {"ui", "Regression","smoke"})
    public void verifyContactUsPageLoadsProperly() {
        getDriver().get("https://automationexercise.com/contact_us");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        assert nameField.isDisplayed() : "Page did not load properly!";
        getTest().pass("Contact Us page loaded properly");
    }

    // ------------------- UI Test 4: Verify All Input Fields Are Visible -------------------
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

    // ------------------- UI Test 5: Verify Submit Button is Enabled -------------------
    @Test(groups = {"ui", "Regression"})
    public void verifySubmitButtonEnabled() {
        getDriver().get("https://automationexercise.com/contact_us");
        WebElement submitBtn = getDriver().findElement(By.name("submit"));
        assert submitBtn.isEnabled() : "Submit button is not enabled";
        getTest().pass("Submit button is enabled");
    }
 // ------------------- UI Test 6: Verify Placeholder Texts -------------------
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
