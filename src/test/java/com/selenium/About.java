package com.selenium;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class About extends BaseTestReport {
    static WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/About.html");
        // extentReports.attachReporter(spark);
        // extentTest = extentReports.createTest("demo");

        String browserPlugin = "firefox";
        System.setProperty(
                "webdriver.chrome.driver",
                ServerCredentials.setDriverPath(browserPlugin));

        driver = ServerCredentials.setBrowserDriver(browserPlugin);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void login() throws IOException {
        Dashboard.loginDetails(driver, extentReports);
    }

    @Test(priority = 2)
    public void revenueDashboardOpen() {
        Dashboard.revenuedashboardOpen(driver, extentReports);

    }

    @Test(priority = 3)
    public void aboutPage() {
        try {
            Thread.sleep(3000);

            WebElement aboutPageLink = driver
                    .findElement(By.className("abt-pg"));
            if (aboutPageLink.isDisplayed()) {
                aboutPageLink.click();
                extentReports.createTest("About Page ").log(Status.PASS, "Successfully Open the About page");
            } else {
                extentReports.createTest("About Page ").log(Status.INFO, "About page not found");
                Assert.fail("About page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("About Page ").log(Status.FAIL,
                    "Failed to click the About page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void application_Details() {
        try {
            Thread.sleep(3000);
            WebElement productNameElement = driver.findElement(By.xpath("//li[contains(text(), 'Product Name')]"));
            String productName = productNameElement.getText().split(":")[1].trim();

            WebElement partNumberElement = driver.findElement(By.xpath("//li[contains(text(), 'Part Number')]"));
            String partNumber = partNumberElement.getText().split(":")[1].trim();

            WebElement versionElement = driver.findElement(By.xpath("//li[contains(text(), 'Version')]"));
            String version = versionElement.getText().split(":")[1].trim();

            WebElement licenseElement = driver.findElement(By.xpath("//li[contains(text(), 'License')]"));
            String license = licenseElement.getText().split(":")[1].trim();
            extentReports.createTest("About Page Info ").log(Status.PASS, "About page Info")
                    .log(Status.INFO, "Product Name: " + productName)
                    .log(Status.INFO, "Part Number: " + partNumber).log(Status.INFO, "Version: " + version)
                    .log(Status.INFO, "License: " + license);

        } catch (Exception e) {
            extentReports.createTest("About Page Info ").log(Status.FAIL,
                    "Failed to click the About page info. Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Users Page LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Users Page LogOut").log(Status.FAIL,
                    "Failed to LogOut. Exception: " + e.getCause());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            extentReports.flush();
            driver.quit();
        }
    }
}
