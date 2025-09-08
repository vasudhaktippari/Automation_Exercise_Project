package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.VideoTutorialsPage;
import com.automationexercise.utilities.ScreenshotUtilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_VideoTutorialsTest extends BaseTest {

    @Test(groups = {"smoke", "functional"})
    public void TC_CheckVideoTutorialsIcon() throws Exception {
        getTest().info("Starting Video Tutorials test");

        getDriver().get("https://automationexercise.com/");
        VideoTutorialsPage videoPage = new VideoTutorialsPage(getDriver());

        getTest().info("Checking if video tutorials icon is visible");
        Assert.assertTrue(videoPage.isVideoIconVisible(), "Video Tutorials icon is not visible");

        getTest().info("Checking if video tutorials icon is clickable");
        Assert.assertTrue(videoPage.isVideoIconClickable(), "Video Tutorials icon is not clickable");

        getTest().info("Clicking on Video Tutorials icon");
        videoPage.clickVideoIcon();

        // Optional: wait for navigation and validate YouTube URL
        Thread.sleep(3000);
        String currentUrl = videoPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("youtube.com"), "Did not navigate to YouTube");

        String screenshotPath = ScreenshotUtilities.captureScreen(getDriver(), "TC_CheckVideoTutorialsIcon");
        getTest().addScreenCaptureFromPath(screenshotPath);

        getTest().pass("Video Tutorials test passed successfully!");
    }
}
