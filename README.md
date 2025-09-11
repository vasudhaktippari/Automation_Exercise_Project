Automation Project A1
Overview

This is a Maven-based UI test automation project using Java 17 + TestNG and the Page Object Model (POM).
It includes test scripts, reusable page objects/utilities, test data, dated reports, and screenshots.

Project Structure
Automation_project_A1/
├─ pom.xml                         # Maven build & dependencies
├─ testng.xml                      # Test suite configuration
├─ src/
│  └─ test/
│     ├─ java/
│     │  └─ com/automationexercise/
│     │     ├─ base/              # WebDriver setup/teardown, hooks, listeners
│     │     ├─ pages/             # Page Object classes
│     │     ├─ tests/             # TestNG test classes
│     │     └─ utilities/         # Helpers: waits, readers, common utils
│     └─ resources/
│        ├─ Reports/              # Dated execution reports
│        │  ├─ 20250910/
│        │  └─ 20250911/
│        ├─ Screenshots/          # Failure/evidence screenshots
│        └─ Testdata/             # Test data & sample assets
│           ├─ dynamicdata.xlsx
│           ├─ sample_file.txt
│           ├─ sample_image.jpg
│           └─ sample_pdf.pdf
├─ target/                         # Maven build artifacts (compiled classes, surefire reports)
└─ test-output/                    # TestNG default HTML reports

Prerequisites

Java 17

Maven 3.8+

A modern browser (e.g., Chrome/Edge) and matching WebDriver (or WebDriverManager)

IDE (IntelliJ IDEA / Eclipse) – optional but recommended

Setup
# From the project root
mvn -v               # verify Maven is installed
java -version        # verify Java 17 is active
mvn clean            # clean previous builds

How to Run
# Run the suite defined in testng.xml
mvn clean test -DsuiteXmlFile=testng.xml


Examples:

# Run a single TestNG class by fully qualified name
mvn -Dtest=com.automationexercise.tests.LoginTest test

# Run tests with a TestNG group (if groups are configured)
mvn clean test -Dgroups=smoke

Reports & Artifacts

TestNG HTML reports: test-output/

Custom/extent reports (dated): src/test/resources/Reports/

Screenshots: src/test/resources/Screenshots/

Notes

Follow the POM pattern: keep locators & page actions in pages/, test logic in tests/, and common code in utilities/.

Keep test data in src/test/resources/Testdata/ to simplify CI/CD packaging.
