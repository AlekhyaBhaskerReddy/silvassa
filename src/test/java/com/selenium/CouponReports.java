package com.selenium;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class CouponReports extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/CouponReports.html");
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
    public void redirectCouponReportsPage() {
        try {
            Thread.sleep(2000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();
            driver.findElement(By.className("cpnr-pg")).click();
            extentReports.createTest("Redirect Coupon Reports Page ").log(Status.PASS,
                    "Successfully Open Coupon Reports Page ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Redirect Coupon Reports Page ").log(Status.FAIL,
                    "Failed to Open the Coupon Reports Page  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void viewCouponSiteList() {
        try {
            List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
            driver.findElement(By.id("pos_site_name"));
            WebElement site = siteList.get(1);
            String selectedSiteName = site.getText();
            site.click();

            extentReports.createTest("Displaying the sites list in Coupon Reports Page ").log(Status.PASS,
                    " Successfully displaying the sites list :" + selectedSiteName);
        } catch (Exception e) {
            extentReports.createTest("Displaying the sites list in Coupon Reports Page ").log(Status.FAIL,
                    "Failed displaying the sites list. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void vehicleBlockListDates() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 6)
    public void selectCouponStartDate() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 7)
    public void selectCouponEndDate() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 8)
    public void selectListCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("couponTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("100");
            extentReports.createTest("couponTable_length").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("couponTable_length").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 9)
    public void getCouponsTable() {
        try {
            WebElement table = driver.findElement(By.id("couponTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("Coupon Reports table ").log(Status.FAIL,
                        "No data available");
                return;
            }
            System.out.println(
                    "---------------------------------------- Coupon Reports-------------------------------------------------------");
            System.out.println(
                    "| Vehicle Number  | Mobile Number | Coupon Code | Coupon Amount | Status | Created Time | Start Date |End Date |");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 8) {

                    String VehicleNumber = cells.get(0).getText();
                    String MobileNumber = cells.get(1).getText();
                    String CouponCode = cells.get(2).getText();
                    String CouponAmount = cells.get(3).getText();
                    String Status = cells.get(4).getText();
                    String CreatedTime = cells.get(5).getText();
                    String StartTime = cells.get(6).getText();
                    String EndTime = cells.get(7).getText();

                    System.out.printf("| %-20s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            VehicleNumber,
                            MobileNumber,
                            CouponCode, CouponAmount, Status, CreatedTime, StartTime, EndTime);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Coupon Reports Page Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Coupon Reports Page Table ").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void searchVehicleNumber() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver.findElement(By.cssSelector("#couponTable_filter input[type='search']"));

            String searchValue = "AP24FR455";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("couponTable"));
            String tableData = table.getText();

            extentReports.createTest("Coupon Reports Page Table search as vehicle number").log(Status.PASS,
                    "Successfully searched for: " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);

        } catch (Exception e) {
            extentReports.createTest("Coupon Reports Page Table search as AP24FR455").log(Status.FAIL,
                    "Failed to search the Vehicle Number. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void searchCouponMobileNumber() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver.findElement(By.cssSelector("#couponTable_filter input[type='search']"));
            searchInput.clear();

            String searchValue = "7842137994";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("couponTable"));
            String tableData = table.getText();

            extentReports.createTest("Coupon Reports Page Table search for mobile number ").log(Status.PASS,
                    "Successfully searched for: " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);

        } catch (Exception e) {
            extentReports.createTest("Coupon Reports Page Table search as 7842137994").log(Status.FAIL,
                    "Failed to search the Mobile Number. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void searchCouponCode() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver.findElement(By.cssSelector("#couponTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "PREFIX00000O";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("couponTable"));
            String tableData = table.getText();

            extentReports.createTest("Coupon Reports Page Table search for coupon code ").log(Status.PASS,
                    "Successfully searched for: " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);

        } catch (Exception e) {
            extentReports.createTest("Coupon Reports Page Table search CouponCode as PREFIX00000O").log(Status.FAIL,
                    "Failed to search the CouponCode. Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void searchCouponStatus() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver.findElement(By.cssSelector("#couponTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "VALID";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("couponTable"));
            String tableData = table.getText();

            extentReports.createTest("Coupon Reports Page Table search for Status").log(Status.PASS,
                    "Successfully searched for: " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Coupon Reports Page Table search CouponCode as VALID").log(Status.FAIL,
                    "Failed to search the Status. Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void searchCouponCreatedTime() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver.findElement(By.cssSelector("#couponTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "2023-09-22 17:13:22";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("couponTable"));
            String tableData = table.getText();

            extentReports.createTest("Coupon Reports Page Table search for created time ").log(Status.PASS,
                    "Successfully searched for: " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Coupon Reports Page Table search CreatedTime as 2023-09-22 17:13:22").log(
                    Status.FAIL,
                    "Failed to search the CreatedTime. Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void validateCouponVehicleNumber() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            WebElement VehicleNumberCell = row.findElements(By.tagName("td")).get(0);
            String VehicleNumber = VehicleNumberCell.getText();

            if (VehicleNumber.equalsIgnoreCase("null")) {
                isAnyNullVehicleNumber = true;
                break;
            }
        }
        if (isAnyNullVehicleNumber) {
            extentReports.createTest("In Coupon Reports Page Table VehicleNumber").log(Status.FAIL,
                    "Found a null VehicleNumber");
        } else {
            extentReports.createTest("In Coupon Reports Page Table VehicleNumber").log(Status.PASS,
                    "No null VehicleNumber found");
        }
    }

    @Test(priority = 16)
    public void validateCouponMobileNumber() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page MobileNumber ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullMobileNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement MobileNumberCell = cells.get(1);
                String MobileNumber = MobileNumberCell.getText();

                if (MobileNumber.equalsIgnoreCase("null")) {
                    isAnyNullMobileNumber = true;
                    break;
                }
            }
            if (isAnyNullMobileNumber) {
                extentReports.createTest("In Coupon Reports Table Page MobileNumber").log(Status.FAIL,
                        "Found a null MobileNumber");
            } else {
                extentReports.createTest("In Coupon Reports Page Table MobileNumber").log(Status.PASS,
                        "No null MobileNumber found");
            }
        }
    }

    @Test(priority = 17)
    public void validateCouponCode() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page Coupon Code ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCouponCode = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement CouponCodeCell = cells.get(2);
                String CouponCode = CouponCodeCell.getText();

                if (CouponCode.equalsIgnoreCase("null")) {
                    isAnyNullCouponCode = true;
                    break;
                }
            }
            if (isAnyNullCouponCode) {
                extentReports.createTest("In Coupon Reports Page Table coupon code").log(Status.FAIL,
                        "Found a null coupon code");
            } else {
                extentReports.createTest("In Coupon Reports Page Table coupon code").log(Status.PASS,
                        "No null coupon code found");
            }
        }
    }

    @Test(priority = 18)
    public void validateCouponAmount() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page Coupon Amount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCouponAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement CouponAmountCell = cells.get(3);
                String CouponAmount = CouponAmountCell.getText();

                if (CouponAmount.equalsIgnoreCase("null")) {
                    isAnyNullCouponAmount = true;
                    break;
                }
            }
            if (isAnyNullCouponAmount) {
                extentReports.createTest("In Coupon Reports Page Table Coupon Amount").log(Status.FAIL,
                        "Found a null Coupon Amount");
            } else {
                extentReports.createTest("In Coupon Reports Page Table Coupon Amount").log(Status.PASS,
                        "No null Coupon Amount found");
            }
        }
    }

    @Test(priority = 19)
    public void validateCouponStatusNull() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page CouponS tatus ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCouponStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement CouponStatusCell = cells.get(4);
                String CouponStatus = CouponStatusCell.getText();

                if (CouponStatus.equalsIgnoreCase("null")) {
                    isAnyNullCouponStatus = true;
                    break;
                }
            }
            if (isAnyNullCouponStatus) {
                extentReports.createTest("In Coupon Reports Page Table Coupon Status").log(Status.FAIL,
                        "Found a null Coupon Status");
            } else {
                extentReports.createTest("In Coupon Reports Page Table Coupon Status").log(Status.PASS,
                        "No null Coupon Status found");
            }
        }
    }

    @Test(priority = 20)
    public void validateCouponStatusInvalid() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page CouponS tatus ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidCouponStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement CouponStatusCell = cells.get(4);
                String CouponStatus = CouponStatusCell.getText();

                if (CouponStatus.equalsIgnoreCase("Invalid")) {
                    isAnyInvalidCouponStatus = true;
                    break;
                }
            }
            if (isAnyInvalidCouponStatus) {
                extentReports.createTest("In Coupon Reports Page Table Coupon Status").log(Status.FAIL,
                        "Found a Invalid Coupon Status");
            } else {
                extentReports.createTest("In Coupon Reports Page Table Coupon Status").log(Status.PASS,
                        "No Invalid Coupon Status found");
            }
        }
    }

    @Test(priority = 21)
    public void validateCouponCreatedTime() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page VehicleNum ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCreatedTime = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement CreatedTimeCell = cells.get(5);
                String CreatedTime = CreatedTimeCell.getText();

                if (CreatedTime.equalsIgnoreCase("null")) {
                    isAnyNullCreatedTime = true;
                    break;
                }
            }
            if (isAnyNullCreatedTime) {
                extentReports.createTest("In Coupon Reports Page Table CreatedTime").log(Status.FAIL,
                        "Found a null CreatedTime");
            } else {
                extentReports.createTest("In Coupon Reports Page Table CreatedTime").log(Status.PASS,
                        "No null CreatedTime found");
            }
        }
    }

    @Test(priority = 22)
    public void validateCouponStartTime() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page Start Date ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnynullStartDate = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement StartDateCell = cells.get(6);

                String StartDate = StartDateCell.getText();

                if (StartDate.equalsIgnoreCase("null")) {
                    isAnynullStartDate = true;
                    break;
                }
            }
            if (isAnynullStartDate) {
                extentReports.createTest("In Coupon Reports Page Table Start Date").log(Status.FAIL,
                        "Found a null Start date");
            } else {
                extentReports.createTest("In Coupon Reports Page Table Start date").log(Status.PASS,
                        "No null start date found");
            }
        }
    }

    @Test(priority = 23)
    public void validateCouponEndDate() {
        WebElement table = driver.findElement(By.id("couponTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Coupon Reports Page EndDate ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnynullEndDate = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement EndDateCell = cells.get(7);

                String EndTime = EndDateCell.getText();

                if (EndTime.equalsIgnoreCase("null")) {
                    isAnynullEndDate = true;
                    break;
                }
            }
            if (isAnynullEndDate) {
                extentReports.createTest("In Coupon Reports Page Table End Date").log(Status.FAIL,
                        "Found a null End Date");
            } else {
                extentReports.createTest("In Coupon Reports Page Table End Date").log(Status.PASS,
                        "No null End Date found");
            }
        }
    }

    @Test(priority = 25)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Coupon Reports page LOGOUT ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Coupon Reports page LOGOUT").log(Status.FAIL,
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
