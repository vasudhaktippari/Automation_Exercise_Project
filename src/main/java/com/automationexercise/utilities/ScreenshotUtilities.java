package com.automationexercise.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

public class ScreenshotUtilities {

    // Wait until page is fully loaded
    private static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    // Capture visible viewport (full page if configured with AShot strategy)
    public static String captureScreen(WebDriver driver, String testName) throws IOException {
        waitForPageLoad(driver);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String projectPath = System.getProperty("user.dir");
        String screenPath = projectPath + "/src/test/resources/Screenshots/"
                            + testName + "_" + timestamp + ".png";

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        File destFile = new File(screenPath);
        ImageIO.write(screenshot.getImage(), "PNG", destFile);

        return screenPath;
    }

    // Capture screenshot of a specific WebElement
    public static String captureElement(WebDriver driver, WebElement element, String elementName) throws IOException {
        waitForPageLoad(driver);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String projectPath = System.getProperty("user.dir");
        String screenPath = projectPath + "/src/test/resources/Screenshots/"
                            + elementName + "_" + timestamp + ".png";

        Screenshot elementScreenshot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, element);

        File destFile = new File(screenPath);
        ImageIO.write(elementScreenshot.getImage(), "PNG", destFile);

        return screenPath;
    }
}
