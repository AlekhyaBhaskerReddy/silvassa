package com.selenium;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class Coupons extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Coupons.html");
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
    public void revenueDashboard() {
        Dashboard.revenuedashboardOpen(driver, extentReports);
    }

    @Test(priority = 3)
    public void attenderLiveSessionsPage() {
        try {
            Thread.sleep(4000);
            WebElement couponPageLink = driver
                    .findElement(By.className("cn-pg"));
            if (couponPageLink.isDisplayed()) {
                couponPageLink.click();
                extentReports.createTest("Coupon Page ").log(Status.PASS,
                        "Successfully Open the Coupons page ");
            } else {
                extentReports.createTest("Coupons Page ").log(Status.INFO,
                        "Coupons Page not found");
                Assert.fail("Coupons page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("Coupons Page ").log(Status.FAIL,
                    "Failed to click the Coupons page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectSitesList() {
        try {
            Thread.sleep(5000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(2);
            String selectedSiteName = site.getText();
            site.click();

            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.PASS, "Successfully open dropdown and selected the " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected  . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void enterVehicleNumber() {
        try {
            String vehicleNumber = "AP09AB8963";
            driver.findElement(By.id("vehicleNumber")).sendKeys(vehicleNumber);
            extentReports.createTest("Entered vehicle number ")
                    .log(Status.PASS, "Successfully entered the vehicle number: " + vehicleNumber);

        } catch (Exception e) {
            extentReports.createTest("Entered vehicle number ")
                    .log(Status.FAIL,
                            "Failed to enter the vehicle number. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void enterMobileNumber() {
        try {
            String mobileNumber = "9874561230";
            driver.findElement(By.id("mobileNumber")).sendKeys(mobileNumber);
            extentReports.createTest("Entered mobile number ")
                    .log(Status.PASS, "Successfully entered the mobile number: " + mobileNumber);

        } catch (Exception e) {
            extentReports.createTest("Entered mobile number ")
                    .log(Status.FAIL,
                            "Failed to enter the mobile number. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void entercouponAmount() {
        try {
            String couponAmount = "10";
            driver.findElement(By.id("couponAmount")).sendKeys(couponAmount);
            extentReports.createTest("Entered coupon Amount ")
                    .log(Status.PASS, "Successfully entered the couponAmount: " + couponAmount);

        } catch (Exception e) {
            extentReports.createTest("Entered coupon Amount ")
                    .log(Status.FAIL,
                            "Failed to enter the coupon Amount. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void enterCouponCode() {
        try {
            String couponCode = "jhansi";
            driver.findElement(By.id("couponCode")).sendKeys(couponCode);
            extentReports.createTest("Entered coupon Code ")
                    .log(Status.PASS, "Successfully entered the coupon code: " + couponCode);

        } catch (Exception e) {
            extentReports.createTest("Entered coupon code ")
                    .log(Status.FAIL,
                            "Failed to enter the coupon code. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 9)
    public void generateButton() {
        try {
            Thread.sleep(3000);
            WebElement generateButton = driver.findElement(By.id("btnCoupon"));
            generateButton.click();
            Thread.sleep(3000);
            extentReports.createTest("Generate the coupon button")
                    .log(Status.PASS, "Successfully click the generate button");

        } catch (Exception e) {
            extentReports.createTest("Generate the coupon button")
                    .log(Status.FAIL,
                            "Failed to click the generate button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 10)
    public void extractPopupText() {
        try {
            Thread.sleep(2000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while creating coupon").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while creating coupon").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while creating coupon").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 11)
    public void statusOK() {
        try {
            Thread.sleep(2000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status").log(Status.PASS,
                    "Clicked the 'OK' button in coupon generation page.");
        } catch (Exception e) {
            extentReports.createTest("Status").log(Status.FAIL,
                    "Failed to click the 'OK' button in coupon generation page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 25)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Coupons page LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Coupons page LogOut").log(Status.FAIL,
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
