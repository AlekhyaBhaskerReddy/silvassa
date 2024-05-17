package com.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class App {
    WebDriver driver;
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;

    public void setUp() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("reports/Login.html");
        extent.attachReporter(spark);
        System.setProperty(
                "webdriver.chrome.driver",
                "/home/iramtech/iRam_Folder/web_driver/114/chromedriver");
        driver = new ChromeDriver();
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("headless");
        // driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    public void selectServiceUser() {
        try {
            List<WebElement> elements = driver.findElements(By.id("manualoptName"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(3000);

            WebElement selectElement = driver.findElement(By.id("manualoptName"));
            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isEnabled" + selectElement.isEnabled());
            System.out.println("selectElement.isSelected" + selectElement.isSelected());
            Select select = new Select(selectElement);
            select.selectByVisibleText("serviceuser");
            extent.createTest("Select User from Dropdown in Manual task").log(Status.PASS,
                    "Successfully selected the user with value '25'");

        } catch (Exception e) {
            extent.createTest("Select User from Dropdown in Manual Task").log(Status.FAIL,
                    "Failed to select the user from the dropdown. Exception: " + e.getCause());
        }

    }

    public void selectAlertType() {
        try {
            WebElement alertTypeDropdown = driver.findElement(By.id("alertType"));
            Select select = new Select(alertTypeDropdown);
            String textToSelect = "Site Issue";

            select.selectByVisibleText(textToSelect);
            // driver.findElement(By.id("alertType")).sendKeys("SITE");

            extent.createTest("Select Alert Type in Manual Task").log(Status.PASS,
                    "Successfully selected alert type: ");
        } catch (Exception e) {
            extent.createTest("Select Alert Type in Manual Task").log(Status.FAIL,
                    "Failed to select alert type. Exception: " + e.getCause());
        }
    }

    public static void main(String[] args) {
        App p = new App();
        p.selectServiceUser();
        p.selectAlertType();
    }
}
