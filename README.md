# Automation Testing of Ecommerce WEB Application Project

## Overview
This is a *Maven-based E-commerce test automation project* using **Java, Selenium WebDriver, and TestNG**.
It includes Page Object–based automation scripts and TestNG test cases for end-to-end flows.
**Reports** (TestNG/Extent) and **screenshots** are generated automatically after each run.



## Project Structure

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


## Prerequisites
- Java 17+  
- Maven 3.6+  
- Eclipse IDE or any Java IDE  
- Selenium/WebDriver dependencies (as needed)


## Setup Instructions
1. Clone the repository:
   ```bash
   git clone <repo-link>
  

2.	Import the project in Eclipse:
	•	File → Open Projects from File System…
	•	Select the cloned folder and click Finish.
3.	Build the project using Maven:

    ``` bash
    mvn clean install

4.	Run the automation tests:

    ```bash
    mvn test

## Notes
- Do not delete files from the repository. Only add/update code and test cases.
- Reports and screenshots will be generated automatically in the respective folders.
 
## Contributors
-Akash Mangond 
-Benakeshwar G K
-Nayeem Laheji 
-Nischith S Gowda 
-Vasudha K Tippari

