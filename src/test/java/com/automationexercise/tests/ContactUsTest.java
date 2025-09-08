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
import com.automationexercise.pages.ContactUsPage;
import com.automationexercise.utilities.ExcelUtilities;
import com.automationexercise.utilities.ScreenshotUtilities;

public class ContactUsTest extends BaseTest {

    // ------------------- Data Provider -------------------
    @DataProvider(name = "contactUsData")
    public Object[][] getContactUsData() throws IOException {
        return ExcelUtilities.getData("ContactUs");
    }

    // ------------------- Data-driven Contact Us Form -------------------
    @Test(dataProvider = "contactUsData", groups = {"functional", "Regression", "Data-Driven"})
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
                By bannerLocator = By.cssSelector(".status.alert-success, .status.alert, .error");
                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
                WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(bannerLocator));
                String actualMessage = banner.getText().trim();

                if (actualMessage.toLowerCase().contains("success") || actualMessage.toLowerCase().contains("sent")) {
                    String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "InvalidName_UnexpectedSuccess");
                    getTest().fail("App accepted invalid name! Expected rejection, but got success: " + actualMessage)
                            .addScreenCaptureFromPath(screenshotPath);
                    assert false : "App wrongly accepted invalid name input.";
                } else {
                    getTest().pass("Application correctly rejected invalid name. Message: " + actualMessage);
                }
                return;
            }

            // ---------- INVALID / BLANK EMAIL ----------
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

   

    @Test(groups = {"ui", "Regression"})
    public void verifyHomeIcon() {
        getDriver().get("https://automationexercise.com/contact_us");
        ContactUsPage contactPage = new ContactUsPage(getDriver());
        assert contactPage.isHomeIconVisible() : "Home icon is not visible";
        getTest().pass("Home icon is visible");

        boolean clicked = contactPage.clickHomeIcon();
        assert clicked : "Home icon is not clickable";
        getTest().pass("Home icon is clickable");

        String url = getDriver().getCurrentUrl();
        assert url.equals("https://automationexercise.com/") : "Home icon did not navigate to homepage";
        getTest().pass("Home icon navigates correctly to homepage");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyLogo() {
        getDriver().get("https://automationexercise.com/contact_us");
        ContactUsPage contactPage = new ContactUsPage(getDriver());
        assert contactPage.isLogoVisible() : "Automation Exercise logo is not visible";
        getTest().pass("Automation Exercise logo is visible");

        boolean clicked = contactPage.clickLogo();
        assert clicked : "Logo is not clickable";
        getTest().pass("Automation Exercise logo is clickable");

        String url = getDriver().getCurrentUrl();
        assert url.equals("https://automationexercise.com/") : "Logo did not redirect to homepage";
        getTest().pass("Automation Exercise logo hyperlink works");
    }

    private void verifyButtonVisibleClickableAndRedirect(By locator, String expectedUrl, String name) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        WebElement btn = getDriver().findElement(locator);

        assert btn.isDisplayed() : name + " is not visible";
        getTest().pass(name + " is visible");

        btn.click();
        getTest().info(name + " clicked");

        String url = getDriver().getCurrentUrl();
        assert url.contains(expectedUrl) : name + " did not redirect properly. Current URL: " + url;
        getTest().pass(name + " redirects correctly to " + expectedUrl);

        getDriver().navigate().back();
    }
 // ------------------- Data Provider for File Upload -------------------
    @DataProvider(name = "fileUploadData")
    public Object[][] getFileUploadData() {
        String basePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\";
        return new Object[][] {
            { basePath + "sample_file.txt" },
            { basePath + "sample_image.jpg" },
            { basePath + "sample_pdf.pdf" }
        };
    }

    @Test(dataProvider = "fileUploadData", groups = {"functional", "ui", "Regression","Data-Driven"})
    public void verifyFileUploadWithDifferentTypes(String filePath) {
        try {
            getDriver().get("https://automationexercise.com/contact_us");
            getTest().info("Opened Contact Us page");

            ContactUsPage contactPage = new ContactUsPage(getDriver());

            // Fill mandatory fields
            contactPage.fillForm("Test User", "testuser@example.com", "File Upload Test",
                    "Testing file upload with: " + filePath);
            getTest().info("Filled all mandatory fields");

            // File upload
            WebElement fileUploadField = getDriver().findElement(By.name("upload_file"));
            fileUploadField.sendKeys(filePath);
            getTest().info("Uploaded file: " + filePath);

            // Submit the form
            contactPage.submitForm();
            getTest().info("Clicked Submit button");

            // Handle confirmation alert if it appears
            try {
                WebDriverWait alertWait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
                alertWait.until(ExpectedConditions.alertIsPresent());
                Alert confirmAlert = getDriver().switchTo().alert();
                getTest().info("Confirmation alert appeared: " + confirmAlert.getText());
                confirmAlert.accept();
                getTest().info("Alert accepted successfully");
            } catch (Exception e) {
                getTest().info("No confirmation alert appeared");
            }

            // Wait for success message
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
            By successBanner = By.cssSelector(".status.alert-success, .status.alert");
            WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(successBanner));
            String actualMessage = banner.getText().trim();

            // Validate
            if (actualMessage.toLowerCase().contains("success") || actualMessage.toLowerCase().contains("sent")) {
                getTest().pass("File uploaded successfully: " + filePath + " | Message: " + actualMessage);
            } else {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(),
                        "FileUploadFailure_" + filePath.substring(filePath.lastIndexOf("/") + 1));
                getTest().fail("File upload failed or success message not found. File: " + filePath + " | Message: "
                        + actualMessage).addScreenCaptureFromPath(screenshotPath);
                assert false : "File upload failed for: " + filePath;
            }

        } catch (Exception e) {
            try {
                String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(),
                        "Exception_FileUpload_" + filePath.substring(filePath.lastIndexOf("/") + 1));
                getTest().fail("Test failed with exception for file: " + filePath + " | Exception: " + e.getMessage())
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ioEx) {
                getTest().fail("Failed to capture screenshot: " + ioEx.getMessage());
            }
            throw new RuntimeException(e);
        }
    }


    @Test(groups = {"ui", "Regression"})
    public void verifyTestCasesButton() {
        getDriver().get("https://automationexercise.com/contact_us");
        verifyButtonVisibleClickableAndRedirect(By.xpath("//a[text()=' Test Cases']"), "test_cases", "Test Cases button");
    }


    @Test(groups = {"ui", "Regression"})
    public void verifyAPITestingButton() {
        getDriver().get("https://automationexercise.com/contact_us");
        By apiButton = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[6]/a");
        verifyButtonVisibleClickableAndRedirect(apiButton, "api_list", "API Testing button");
    }


    @Test(groups = {"ui", "Regression"})
    public void verifyLoginSignupIcon() {
        getDriver().get("https://automationexercise.com/contact_us");
        By loginIcon = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[4]/a");
        verifyButtonVisibleClickableAndRedirect(loginIcon, "login", "Login/Signup icon");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyProductsIcon() {
        getDriver().get("https://automationexercise.com/contact_us");
        verifyButtonVisibleClickableAndRedirect(By.xpath("//a[text()=' Products']"), "products", "Products icon");
    }


    @Test(groups = {"ui", "Regression"})
    public void verifyVideoTutorialsIcon() {
        getDriver().get("https://automationexercise.com/contact_us");

        // Correct locator for the link wrapping the icon
        By videoTutorialsLink = By.xpath("//a[contains(@href,'youtube.com/c/AutomationExercise')]");

        verifyButtonVisibleClickableAndRedirect(
            videoTutorialsLink,
            "https://www.youtube.com/c/AutomationExercise",
            "Video Tutorials icon"
        );
    }




    @Test(groups = {"ui", "Regression"})
    public void verifyCartIcon() {
        getDriver().get("https://automationexercise.com/contact_us");
        verifyButtonVisibleClickableAndRedirect(By.cssSelector("i.fa-shopping-cart"), "view_cart", "Cart icon");
    }

    @Test(groups = {"ui", "Regression"})
    public void verifyNoteMessageOnContactUsPage() {
        getDriver().get("https://automationexercise.com/contact_us");
        ContactUsPage contactPage = new ContactUsPage(getDriver());

        assert contactPage.isNoteMessageVisible() : 
            "Note message is not visible on Contact Us page";
        getTest().pass("Note message is visible on Contact Us page");
    }
}
