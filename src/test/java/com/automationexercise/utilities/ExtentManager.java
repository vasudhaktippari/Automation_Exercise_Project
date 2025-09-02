package com.automationexercise.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentManager {

    private static volatile ExtentReports extent;
    private static volatile Path lastReportPath;

    // Reports will go under src/test/resources/Reports
    private static final String DEFAULT_REPORT_DIR = "src/test/resources/Reports";

    /** Singleton accessor. Creates a single ExtentReports per JVM run. */
    public static ExtentReports getInstance(String suiteName) {
        if (extent == null) {
            synchronized (ExtentManager.class) {
                if (extent == null) {
                    extent = createInstance(suiteName);
                }
            }
        }
        return extent;
    }

    private static ExtentReports createInstance(String suiteName) {
        String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dateFolder  = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String safeSuite   = (suiteName == null ? "TestSuite"
                           : suiteName.replaceAll("[^a-zA-Z0-9._-]", "_"));

        // <project-root>/src/test/resources/Reports/<YYYYMMDD>/<suite>_<timestamp>.html
        String reportDir = System.getProperty("report.dir", DEFAULT_REPORT_DIR);
        Path reportsDir  = Paths.get(System.getProperty("user.dir"), reportDir, dateFolder);
        try {
            Files.createDirectories(reportsDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create reports directory: " + reportsDir, e);
        }

        String fileName = safeSuite + "_" + timestamp + ".html";
        lastReportPath  = reportsDir.resolve(fileName);

        ExtentSparkReporter spark = new ExtentSparkReporter(lastReportPath.toString());
        // UI/UX configuration
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Automation Exercise - " + safeSuite);
        spark.config().setEncoding("utf-8");
        spark.config().setTimelineEnabled(true); // Timeline tab
        spark.config().setOfflineMode(true);     // Embed resources for offline viewing
        spark.viewConfigurer().viewOrder()
             .as(new ViewName[] {
                 ViewName.DASHBOARD,
                 ViewName.TEST,
                 ViewName.CATEGORY,
                 ViewName.AUTHOR,
                 ViewName.DEVICE,
                 ViewName.EXCEPTION
             }).apply();

        ExtentReports er = new ExtentReports();
        er.attachReporter(spark);

        // System info
        er.setSystemInfo("Project Name", "Automation Exercise");
        er.setSystemInfo("Suite", safeSuite);
        er.setSystemInfo("Environment", System.getProperty("env", "QA"));
        er.setSystemInfo("Tester", System.getProperty("user.name", "unknown"));
        er.setSystemInfo("OS", System.getProperty("os.name"));
        er.setSystemInfo("OS Version", System.getProperty("os.version"));
        er.setSystemInfo("Java Version", System.getProperty("java.version"));

        return er;
    }

    /** Path to the last generated HTML report (useful for logging/CI artifacts). */
    public static Path getLastReportPath() {
        return lastReportPath;
    }

    /** Optional: reset if you run multiple suites in the same JVM. */
    public static synchronized void reset() {
        extent = null;
        lastReportPath = null;
    }
}
