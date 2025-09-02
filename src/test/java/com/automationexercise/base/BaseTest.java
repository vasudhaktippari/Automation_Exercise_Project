package com.automationexercise.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

// Chrome
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// Edge
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

// Firefox
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.automationexercise.utilities.ExtentManager;
import com.automationexercise.utilities.ScreenshotUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    // ===== Extent (1 per suite) =====
    protected static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> TL_TEST = new ThreadLocal<>();

    // ===== Driver (1 per method/test) =====
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    // Toggle: prefix first group in test name, e.g., "[smoke] verifyHomePageTitle"
    private static final boolean PREFIX_GROUP_IN_NAME = true;

    protected WebDriver getDriver() { return TL_DRIVER.get(); }
    protected ExtentTest getTest()  { return TL_TEST.get();  }

    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        extent = ExtentManager.getInstance("AutomationExercise_TestSuite");
        if (extent == null) {
            throw new IllegalStateException("ExtentReports was not initialized. Check ExtentManager.getInstance()");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("Extent HTML: " + ExtentManager.getLastReportPath());
        }
    }

    @Parameters({"browser", "headless"}) // optional; can be overridden by -Dbrowser / -Dheadless
    @BeforeMethod(alwaysRun = true)
    public void setup(
            @Optional("chrome") String browser,
            @Optional("false") String headless,
            Method method,
            Object[] testData
    ) {
        // CLI/system properties override testng.xml parameters
        String targetBrowser = System.getProperty("browser", browser).toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", headless));

        WebDriver driver = createDriver(targetBrowser, isHeadless);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        TL_DRIVER.set(driver);

        // Build base test name (include first DataProvider param like username)
        String baseName = method.getName();
        if (testData != null && testData.length > 0 && testData[0] != null) {
            baseName += " [" + String.valueOf(testData[0]) + "]";
        }

        // Read TestNG groups and build final test name
        Test testAnno = method.getAnnotation(Test.class);
        String[] groups = (testAnno != null && testAnno.groups() != null) ? testAnno.groups() : new String[0];

        String testName = baseName;
        if (PREFIX_GROUP_IN_NAME && groups.length > 0 && groups[0] != null && !groups[0].isBlank()) {
            testName = "[" + groups[0].trim() + "] " + baseName;
        }

        ExtentTest et = extent.createTest("[" + targetBrowser + (isHeadless ? " (headless)" : "") + "] " + testName);

        // Map TestNG groups -> Extent categories
        for (String g : groups) {
            if (g != null && !g.isBlank()) {
                et.assignCategory(g.trim());
            }
        }
        // Device badge (browser)
        et.assignDevice(targetBrowser + (isHeadless ? " (headless)" : ""));

        TL_TEST.set(et);
    }

    private WebDriver createDriver(String browser, boolean headless) {
        switch (browser) {
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--disable-notifications", "--remote-allow-origins=*");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                if (headless) options.addArguments("--headless=new");

                // Edge (Chromium) prefs
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
                prefs.put("profile.default_content_setting_values.autofill", 2);
                prefs.put("autofill.profile_enabled", false);
                options.setExperimentalOption("prefs", prefs); // W3C-compliant

                WebDriver d = new EdgeDriver(options);
                d.manage().window().maximize();
                return d;
            }
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions options = new FirefoxOptions();
                options.setAcceptInsecureCerts(true);
                options.addPreference("dom.webnotifications.enabled", false);
                options.addPreference("signon.rememberSignons", false);
                options.addPreference("browser.download.useDownloadDir", true);

                if (headless) {
                    // In headless mode, maximize() does nothing → set explicit size
                    options.addArguments("-headless");
                    options.addArguments("--width=1920");
                    options.addArguments("--height=1080");
                }

                WebDriver d = new FirefoxDriver(options);

                if (!headless) {
                    try {
                        d.manage().window().maximize();
                        // Fallback in case maximize is ignored
                        org.openqa.selenium.Dimension s = d.manage().window().getSize();
                        if (s.getWidth() < 1200 || s.getHeight() < 800) {
                            d.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
                            d.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
                        }
                    } catch (Exception e) {
                        d.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
                        d.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
                    }
                }

                return d;
            }

            case "chrome":
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized", "--disable-notifications", "--remote-allow-origins=*");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                if (headless) options.addArguments("--headless=new");

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
                prefs.put("profile.default_content_setting_values.autofill", 2);
                prefs.put("autofill.profile_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                return new ChromeDriver(options);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) throws IOException {
        ExtentTest test = getTest();
        WebDriver driver = getDriver();

        try {
            if (test != null) {
                switch (result.getStatus()) {
                    case ITestResult.SUCCESS:
                        test.pass("Test passed");
                        break;
                    case ITestResult.FAILURE: {
                        String screenshotPath = ScreenshotUtilities.captureScreen(driver, result.getName());
                        test.addScreenCaptureFromPath(screenshotPath);
                        test.fail(result.getThrowable());

                        // 👉 so PDFReporter can embed the same screenshot
                        result.setAttribute("screenshotPath", screenshotPath);
                        break;
                    }
                    case ITestResult.SKIP:
                        test.skip("Test skipped");
                        break;
                    default:
                        test.info("TestNG status: " + result.getStatus());
                }
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
            // Clear ThreadLocals to avoid leaks between tests
            TL_DRIVER.remove();
            TL_TEST.remove();
        }
    }
}
