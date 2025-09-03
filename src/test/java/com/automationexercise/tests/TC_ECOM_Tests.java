package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.TestCasesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TC_ECOM_Tests extends BaseTest {

    @Test(groups = {"smoke", "functional", "ui"})
    public void TC_ECOM_TestCase_001() {
        getTest().info("Starting TC_ECOM_TestCase_001");

        TestCasesPage tcPage = new TestCasesPage(getDriver());
        long start = System.currentTimeMillis();

        getTest().info("Opening Test Cases page");
        tcPage.openTestCasesPage();

        long loadTime = System.currentTimeMillis() - start;
        getTest().info("Page load time: " + loadTime + " ms");

        Assert.assertTrue(tcPage.isTestCasesPageLoaded(), "Test Cases page did not load.");
        Assert.assertTrue(loadTime < 5000, "Page load time exceeded.");

        getTest().pass("TC_ECOM_TestCase_001 passed successfully!");
    }

    @Test(groups = {"functional", "ui"})
    public void TC_ECOM_TestCase_002() {
        getTest().info("Starting TC_ECOM_TestCase_003");

        TestCasesPage tcPage = new TestCasesPage(getDriver());
        tcPage.openTestCasesPage();

        getTest().info("Clicking first scenario");
        tcPage.clickScenario(0);

        List<WebElement> links = getDriver().findElements(org.openqa.selenium.By.cssSelector(".panel-body a"));
        getTest().info("Found " + links.size() + " links inside scenario");

        for (WebElement link : links) {
            String url = link.getAttribute("href");
            getTest().info("Navigating to: " + url);

            getDriver().navigate().to(url);
            Assert.assertTrue(getDriver().getCurrentUrl().contains(url), "Invalid URL navigation: " + url);

            getDriver().navigate().back();
        }

        getTest().pass("TC_ECOM_TestCase_003 passed successfully!");
    }

    @Test(groups = {"functional", "ui"})
    public void TC_ECOM_TestCase_003() {
        getTest().info("Starting TC_ECOM_TestCase_004");

        TestCasesPage tcPage = new TestCasesPage(getDriver());
        tcPage.openTestCasesPage();

        getTest().info("Clicking Feedback link");
        tcPage.clickFeedback();

        Assert.assertTrue(getDriver().getTitle().contains("Contact"), "Feedback page not loaded.");

        getTest().pass("TC_ECOM_TestCase_004 passed successfully!");
    }

    @Test(groups = {"functional", "ui"})
    public void TC_ECOM_TestCase_004() {
        getTest().info("Starting TC_ECOM_TestCase_005");

        TestCasesPage tcPage = new TestCasesPage(getDriver());
        tcPage.openTestCasesPage();

        String email = "test@example.com";
        getTest().info("Entering subscription email: " + email);

        tcPage.enterSubscriptionEmail(email);
        tcPage.submitSubscription();

        Assert.assertTrue(tcPage.isSubscriptionSuccess(), "Subscription failed.");

        getTest().pass("TC_ECOM_TestCase_005 passed successfully!");
    }

    @Test(groups = {"functional", "ui"})
    public void TC_ECOM_TestCase_005() {
        getTest().info("Starting TC_ECOM_TestCase_006");

        TestCasesPage tcPage = new TestCasesPage(getDriver());
        tcPage.openTestCasesPage();

        String email = "validemail@example.com";
        getTest().info("Entering subscription email: " + email);

        tcPage.enterSubscriptionEmail(email);
        tcPage.submitSubscription();

        Assert.assertTrue(tcPage.isSubscriptionSuccess(), "Email not subscribed successfully.");

        getTest().pass("TC_ECOM_TestCase_006 passed successfully!");
    }
}
