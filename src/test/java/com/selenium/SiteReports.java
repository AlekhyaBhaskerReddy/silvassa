package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class SiteReports extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/SiteReports.html");
        // extentReports.attachReporter(spark);
        // extentTest = extentReports.createTest("demo");

        String browserPlugin = "firefox";
        System.setProperty(
                "webdriver.chrome.driver",
                ServerCredentials.setDriverPath(browserPlugin));
        // driver = new ChromeDriver();
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
    public void reportsAnalyticsPage() {
        try {
            Thread.sleep(4000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();

            extentReports.createTest("Reports and Analytics Page").log(Status.PASS,
                    "Successfully Open Reports and Analytics Page");
        } catch (Exception e) {
            extentReports.createTest("Reports and Analytics Page").log(Status.FAIL,
                    "Failed to Open the Reports and Analytics Page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void siteReportPage() {
        try {
            Thread.sleep(4000);
            driver.findElement(By.className("sr-pg")).click();
            extentReports.createTest("Site Reports Page  ").log(Status.PASS,
                    "Successfully Open site reports  Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Site reports  Page Reports ").log(Status.FAIL,
                    "Failed to Open the site reports  Reports . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void siteReportsDatesEnabled() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            site.click();

            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[3]"))
                    .click();
            Thread.sleep(4000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[4]"))
                    .click();
            Thread.sleep(8000);
            extentReports.createTest("Site Reports Dates Enabled ").log(Status.PASS,
                    "Successfully selecting the dates day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Site Reports Dates Enabled ").log(Status.FAIL,
                    "Failed to select the dates day/week/month. Exception: " + e.getCause());

        }
    }

    @Test(priority = 8)
    public void getDaySiteRevenue() {
        try {
            Thread.sleep(4000);
            WebElement dayButton = driver
                    .findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[2]"));
            dayButton.click();
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='total_site_revenue_summary']"));
            String value2 = oneDay.getText();
            System.out.println("One-Day: " + value2);
            extentReports.createTest("Day Revenu in Site Reports is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully : " + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Site Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void getWeekSiteRevenue() {
        try {
            Thread.sleep(4000);
            WebElement weekButton = driver
                    .findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[3]"));
            weekButton.click();
            WebElement oneWeek = driver.findElement(By.id("total_revenue1"));
            String value2 = oneWeek.getText();
            System.out.println("One-week: " + value2);
            extentReports.createTest("Week Revenu in Site Reports is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully :" + value2);
        } catch (Exception e) {
            extentReports.createTest("Week Revenue in Site Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void getMonthRevenue() {
        try {
            Thread.sleep(10000);
            WebElement monthButton = driver
                    .findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[4]"));
            monthButton.click();
            WebElement oneMonth = driver.findElement(By.id("total_revenue1"));
            String value1 = oneMonth.getText();
            System.out.println("One-Month: " + value1);
            extentReports.createTest(" Month Revenue in Site Reports").log(Status.PASS,
                    "Successfully display in Site Reports month revenue : " + value1);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Site Reports").log(Status.FAIL,
                    "Failed to display in Site Reports month revenue. Exception: " + e.getCause());

        }
    }

    @Test(priority = 9)
    public void selectSiteRevenueCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("siteRevenueTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("select Site Revenue Count").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("select Site Revenue Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void siteRevenueTable() {
        try {
            Thread.sleep(5000);
            WebElement table = driver.findElement(By.id("siteRevenueTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In Site Reports Revenue table ").log(Status.FAIL, "No data available");
                return;
            }

            System.out.println(
                    "-----------------------------------------------------------Site Revenue---------------------------------------------------------------------");
            System.out.println(
                    "|Vehicle type  | Cash Amount | Card Amount | UPI Amount | Pass Amount |Reservation Amount|Due Amount|Reservation Count|Pass Count|Due Count|Vehicle Count| ");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String vehicleType = cells.get(0).getText();
                    String CashAmount = cells.get(1).getText();
                    String CardAmount = cells.get(2).getText();
                    String UPIAmount = cells.get(3).getText();
                    String PassAmount = cells.get(4).getText();
                    String ReservationAmount = cells.get(5).getText();
                    String DueAmount = cells.get(6).getText();
                    String ReservationCount = cells.get(7).getText();
                    String PassCount = cells.get(8).getText();
                    String DueCount = cells.get(9).getText();
                    String VehicleCount = cells.get(10).getText();

                    System.out.printf("| %-20s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s %n",
                            vehicleType, CashAmount,
                            CardAmount, UPIAmount, PassAmount, ReservationAmount, DueAmount, ReservationCount,
                            PassCount, DueCount,
                            VehicleCount);
                }
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Site Reports Revenue Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Site Reports Revenue Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void validateVehicleType() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table VehicleType ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {

            WebElement VehicleTypeCell = row.findElements(By.tagName("td")).get(0);

            String VehicleType = VehicleTypeCell.getText();

            if (VehicleType.equalsIgnoreCase("null")) {
                isAnyNullVehicleType = true;
                break;
            }
        }
        if (isAnyNullVehicleType) {
            extentReports.createTest("In Site Reports table VehicleType").log(Status.FAIL, "Found a null VehicleType");
        } else {
            extentReports.createTest("In Site Reports table VehicleType").log(Status.PASS, "No null VehicleType found");
        }
    }

    @Test(priority = 12)
    public void validateSiteReportsCashAmount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table CashAmount").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCashAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 2) {
                WebElement CashAmountCell = cells.get(1);
                String CashAmount = CashAmountCell.getText();

                if (CashAmount.equalsIgnoreCase("null")) {
                    isAnyNullCashAmount = true;
                    break;
                }
            }
            if (isAnyNullCashAmount) {
                extentReports.createTest("In Site Reports table CashAmount").log(Status.FAIL,
                        "Found a null CashAmount");
            } else {
                extentReports.createTest("In Site Reports table CashAmount").log(Status.PASS,
                        "No null CashAmount found");
            }
        }
    }

    @Test(priority = 13)
    public void validateSiteReportsCardAmount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table CardAmount").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCardAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 3) {
                WebElement CardAmountCell = cells.get(2);
                String CardAmount = CardAmountCell.getText();

                if (CardAmount.equalsIgnoreCase("null")) {
                    isAnyNullCardAmount = true;
                    break;
                }
            }
            if (isAnyNullCardAmount) {
                extentReports.createTest("In Site Reports table CardAmount").log(Status.FAIL,
                        "Found a null CardAmount");
            } else {
                extentReports.createTest("In Site Reports table CardAmount").log(Status.PASS,
                        "No null CardAmount found");
            }
        }
    }

    @Test(priority = 14)
    public void validateSiteReportsUPIAmount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table UPIAmount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullUPIAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 4) {
                WebElement UPIAmountCell = cells.get(3);
                String UPIAmount = UPIAmountCell.getText();

                if (UPIAmount.equalsIgnoreCase("null")) {
                    isAnyNullUPIAmount = true;
                    break;
                }
            }
            if (isAnyNullUPIAmount) {
                extentReports.createTest("In Site Reports table UPIAmount").log(Status.FAIL, "Found a null UPIAmount");
            } else {
                extentReports.createTest("In Site Reports table UPIAmount").log(Status.PASS, "No null UPIAmount found");
            }
        }
    }

    @Test(priority = 15)
    public void validateSiteReportsPassAmount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("Site Reports Pass Amount").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyDecimalPassAmount = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 5) {
                WebElement PassAmountCell = cells.get(4);
                String PassAmount = PassAmountCell.getText();

                if (isDecimal(PassAmount)) {
                    isAnyDecimalPassAmount = true;
                    extentReports.createTest("Site Reports Pass Amount").log(Status.INFO,
                            "Decimal Pass Amount found: " + PassAmount);
                }
            }

            if (isAnyDecimalPassAmount) {
                extentReports.createTest("Site Reports Pass Amount").log(Status.PASS, "Decimal Pass Amount(s) found");
            } else {
                extentReports.createTest("Site Reports Pass Amount").log(Status.PASS, "No decimal Pass Amount found");
            }
        }
    }

    private boolean isDecimal(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Test(priority = 16)
    public void validateSiteReports_ReservationAmount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table ReservationAmount").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidReservationAmount = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 6) {
                WebElement ReservationAmountCell = cells.get(5);
                String ReservationAmount = ReservationAmountCell.getText();

                if (ReservationAmount.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidReservationAmount = true;
                    break;
                }
            }

            if (isAnyInvalidReservationAmount) {
                extentReports.createTest("In Site Reports table ReservationAmount").log(Status.FAIL,
                        "Found a invalid ReservationAmount");
                Assert.fail("Test case failed because an invalid ReservationAmount was found.");
            } else {
                extentReports.createTest("In Site Reports table ReservationAmounte").log(Status.PASS,
                        "No invalid ReservationAmount found");
            }
        }
    }

    @Test(priority = 17)
    public void validateSiteReports_DueAmount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table DueAmount").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidDueAmount = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 7) {
                WebElement DueAmountCell = cells.get(6);
                String DueAmount = DueAmountCell.getText();

                if (DueAmount.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidDueAmount = true;
                    break;
                }
            }

            if (isAnyInvalidDueAmount) {
                extentReports.createTest("In Site Reports table DueAmount").log(Status.FAIL,
                        "Found a invalid DueAmount");
                Assert.fail("Test case failed because an invalid DueAmount was found.");
            } else {
                extentReports.createTest("In Site Reports table DueAmount").log(Status.PASS,
                        "No invalid DueAmount found");
            }
        }
    }

    @Test(priority = 18)
    public void validateSiteReports_ReservationCount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table ReservationCount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullReservationCount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 8) {
                WebElement ReservationCountCell = cells.get(7);
                String ReservationCount = ReservationCountCell.getText();

                if (ReservationCount.equalsIgnoreCase("null")) {
                    isAnyNullReservationCount = true;
                    break;
                }
            }
            if (isAnyNullReservationCount) {
                extentReports.createTest("In Site Reports table ReservationCount").log(Status.FAIL,
                        "Found a null ReservationCount");
            } else {
                extentReports.createTest("In Site Reports table ReservationCount").log(Status.PASS,
                        "No null ReservationCount found");
            }
        }
    }

    @Test(priority = 19)
    public void validateSiteReports_PassCount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In  Site Reports table PassCount  ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullPassCount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 9) {
                WebElement PassCountCell = cells.get(8);
                String PassCount = PassCountCell.getText();

                if (PassCount.equalsIgnoreCase("null")) {
                    isAnyNullPassCount = true;
                    break;
                }
            }
            if (isAnyNullPassCount) {
                extentReports.createTest("In Site Reports table PassCount").log(Status.FAIL, "Found a null PassCount");
            } else {
                extentReports.createTest("In Site Reports table PassCount").log(Status.PASS, "No null PassCount found");
            }
        }
    }

    @Test(priority = 20)
    public void validateSiteReports_DueCount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table DueCount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullDueCount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 10) {
                WebElement DueCountCell = cells.get(9);

                String DueCount = DueCountCell.getText();

                if (DueCount.equalsIgnoreCase("null")) {
                    isAnyNullDueCount = true;
                    break;
                }
            }
            if (isAnyNullDueCount) {
                extentReports.createTest("In Site Reports table DueCount").log(Status.FAIL, "Found a null DueCount");
            } else {
                extentReports.createTest("In Site Reports table DueCount").log(Status.PASS, "No null DueCount found");
            }
        }
    }

    @Test(priority = 21)
    public void validateSiteReports_VehicleCount() {
        WebElement table = driver.findElement(By.id("siteRevenueTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Site Reports table VehicleCount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleCount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 11) {
                WebElement VehicleCountCell = cells.get(10);
                String VehicleCount = VehicleCountCell.getText();

                if (VehicleCount.equalsIgnoreCase("null")) {
                    isAnyNullVehicleCount = true;
                    break;
                }
            }
            if (isAnyNullVehicleCount) {
                extentReports.createTest("In Site Reports table VehicleCount").log(Status.FAIL,
                        "Found a null VehicleCount");
            } else {
                extentReports.createTest("In Site Reports table VehicleCount").log(Status.PASS,
                        "No null VehicleCount found");
            }
        }
    }

    @Test(priority = 22)
    public void siteRevenueViewDetailsDay() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[2]"))
                    .click();
            Thread.sleep(6000);
            driver
                    .findElement(By.className("viewbtn"))
                    .click();
            extentReports.createTest("In Site reports click the view details button of a day").log(Status.PASS,
                    "Successfully clicking the view details button in Site Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In Site reports click the view details button of a day ").log(Status.FAIL,
                    "Failed to click view details button in Site Revenue tab. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void siteRevenueDayCSV() {
        try {
            Thread.sleep(4000);
            driver.findElement(By.id("viewdetCsv")).click();
            extentReports.createTest("In Site reports download CSV file of one week").log(Status.PASS,
                    "Successfully download the CSV file in site revenue");
        } catch (Exception e) {
            extentReports.createTest("In Site reports downlaod CSV file of one week").log(Status.FAIL,
                    "Failed to download the CSV file in site Revenue tab. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 24)
    public void siteRevenueDayPDF() {
        try {
            Thread.sleep(4000);
            String mainWindowHandle = driver.getWindowHandle();
            driver.findElement(By.id("viewdetPdf")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(mainWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    driver.close();

                    driver.switchTo().window(mainWindowHandle);
                    extentReports.createTest("In Site reports Download the PDF file of one week").log(Status.PASS,
                            "Successfully download the PDF file in POS revenue");
                    return;
                }
            }
        } catch (Exception e) {
            extentReports.createTest("In Site reports download the PDF file of one week").log(Status.FAIL,
                    "Failed to download the PDF file in site Revenue tab. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 25)
    public void siteRevenueDayPopUPClose() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.className("close")).click();
            extentReports.createTest("In Site reports POP Up of a week").log(Status.PASS,
                    "Successfully closed the pop up in Site revenue");
        } catch (Exception e) {
            extentReports.createTest("In Site reports POP Up of a week").log(Status.FAIL,
                    "Failed to close the pop up in Site Revenue tab. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 28)
    public void siteRevenueViewDetailsWeek() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[3]"))
                    .click();
            Thread.sleep(6000);
            driver
                    .findElement(By.className("viewbtn"))
                    .click();
            extentReports.createTest("In Site reports click the view details button of a week").log(Status.PASS,
                    "Successfully clicking the view details button in Site Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In Site reports click the view details button of a week ").log(Status.FAIL,
                    "Failed to click view details button in Site Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 29)
    public void siteRevenueWeekCSV() {
        siteRevenueDayCSV();
    }

    @Test(priority = 30)
    public void siteRevenueWeekPDF() {
        siteRevenueDayPDF();
    }

    @Test(priority = 31)
    public void siteRevenueWeekPopUPClose() {
        siteRevenueDayPopUPClose();
    }

    @Test(priority = 32)
    public void siteRevenueMonthViewDetails() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[4]"))
                    .click();
            Thread.sleep(2000);
            driver
                    .findElement(By.className("viewbtn"))
                    .click();
            extentReports.createTest("In Site reports click the view details button of a month").log(Status.PASS,
                    "Successfully clicking the view details button in Site Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In Site reports click the view details button of a month").log(Status.FAIL,
                    "Failed to click view details button in Site Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 33)
    public void siteRevenueMonthCSV() throws InterruptedException {
        Thread.sleep(5000);
        siteRevenueDayCSV();
    }

    @Test(priority = 34)
    public void siteRevenueMonthPDF() {
        siteRevenueDayPDF();
    }

    @Test(priority = 35)
    public void siteRevenueMonthPopUPClose() {
        siteRevenueDayPopUPClose();
    }

    @Test(priority = 36)
    public void selectRevenueStartDate() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='chartRevenueLineSite']/div/div/div[1]/div[2]/div/div/label[1]"))
                    .click();
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
            dateElement.click();
            extentReports.createTest("Start Date in Site Revenue").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in Site Revenue").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 37)
    public void selectRevenueEndDate() {
        try {
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath(
                            "//td[@class='available' and @data-title='r3c4']"));
            dateElement.click();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("End Date in Site Revenue").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Site Revenue").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 38)
    public void siteRevenueSelectedViewDetails() {
        try {
            Thread.sleep(20000);
            driver
                    .findElement(By.className("viewbtn"))
                    .click();
            extentReports.createTest("In Site reports click the view details button of a selected dates ").log(
                    Status.PASS,
                    "Successfully clicking the view details button in Site Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In Site reports click the view details button of a selected dates").log(
                    Status.FAIL,
                    "Failed to click view details button in Site Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 39)
    public void siteRevenueSelectedDateCSV() {
        siteRevenueDayCSV();
    }

    @Test(priority = 40)
    public void siteRevenueSelectedPDF() {
        siteRevenueDayPDF();
    }

    @Test(priority = 41)
    public void siteRevenueSelectedPopUPClose() {
        siteRevenueDayPopUPClose();
    }

    @Test(priority = 42)
    public void searchSiteRevenueFilter() {
        try {
            Thread.sleep(5000);
            WebElement searchInput = driver.findElement(By.cssSelector("#siteRevenueTable_filter input"));
            searchInput.clear();
            searchInput.sendKeys("TWO");
            extentReports.createTest("Site Reports Revenue tab search as Two wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type two wheeler");

            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("Site Reports Revenue tab search as Two wheeler").log(Status.FAIL,
                    "Failed to search the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 43)
    public void viewSitesList() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            site.click();

            extentReports.createTest("Displaying the sites list ").log(Status.PASS,
                    " Successfully displaying the sites list");
        } catch (Exception e) {
            extentReports.createTest("Displaying the sites list").log(Status.FAIL,
                    "Failed displaying the sites list. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 44)
    public void siteDeviceStatus() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("device_site_status_summary")).click();

            extentReports.createTest("Site Reports Device Status tab ").log(Status.PASS,
                    "Successfully Open device status tab page");
        } catch (Exception e) {
            extentReports.createTest("Site Reports Device Status tab ").log(Status.FAIL,
                    "Failed to open the device status tab page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 45)
    public void siteReportDeviceStatusCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("allSiteDiviceTbl1_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("select Site Revenue Count").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("select Site Revenue Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 46)
    public void siteRevenueDeviceStatusTable() {
        try {
            WebElement table = driver.findElement(By.id("allSiteDiviceTbl1"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In Site Reports Device status table ").log(Status.FAIL, "No data available");
                return;
            }
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------");
            System.out.println(
                    "| Device  | Active | InActive | Fault | Maintenance |");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 5) {

                    String Device = cells.get(0).getText();
                    String active = cells.get(1).getText();
                    String inActive = cells.get(2).getText();
                    String fault = cells.get(3).getText();
                    String maintenance = cells.get(4).getText();

                    System.out.printf("| %-20s | %-10s | %-10s | %-10s |%-10s |%n", Device, active,
                            inActive, fault, maintenance);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Site Reports Device status Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Site Reports Device status Table ").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 47)
    public void searchSiteReportsDeviceGateway() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#allSiteDiviceTbl1_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "GATEWAY";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("allSiteDiviceTbl1"));
            String tableData = table.getText();

            extentReports.createTest("Site Reports Devise status tab search as gateway").log(Status.PASS,
                    "Successfully search the  : " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Site Reports Devise status tab search as gateway").log(Status.FAIL,
                    "Failed to search the gateway. Exception: " + e.getCause());

        }
    }

    @Test(priority = 48)
    public void searchSiteReportsDevicePOS() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#allSiteDiviceTbl1_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "POS";
            searchInput.sendKeys(searchValue);

            WebElement table = driver.findElement(By.id("allSiteDiviceTbl1"));
            String tableData = table.getText();

            extentReports.createTest("Site Reports Devise status tab search as POS").log(Status.PASS,
                    "Successfully search the : " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Site Reports Devcie status tab search as POS").log(Status.FAIL,
                    "Failed to search the POS. Exception: " + e.getCause());

        }
    }

    @Test(priority = 49)
    public void searchSiteReportsDeviceSensor() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#allSiteDiviceTbl1_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "SENSOR";
            searchInput.sendKeys(searchValue);

            WebElement table = driver.findElement(By.id("allSiteDiviceTbl1"));
            String tableData = table.getText();

            extentReports.createTest("Site Reports Devise status tab search as SENSOR").log(Status.PASS,
                    "Successfully search the : " + searchValue).log(Status.INFO,
                            "Successfully searched for  Table Data:" + tableData);
            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("Site Reports Device status tab search as SENSOR").log(Status.FAIL,
                    "Failed to search . Exception: " + e.getCause());

        }
    }

    @Test(priority = 50)
    public void alertsCount() {
        try {
            Thread.sleep(3000);
            WebElement alerts = driver.findElement(By.xpath("sites_alerts1"));
            String value = alerts.getText();
            System.out.println("Alerts/Notifications : " + value);
            driver.findElement(By.tagName("tr"));
            Thread.sleep(5000);
            extentReports.createTest("Site Reports Alerts Count").log(Status.PASS,
                    "Successfully displaying the Alerts Count : " + value);
        } catch (Exception e) {
            extentReports.createTest("Site Reports Alerts Count").log(Status.PASS,
                    "Failed to displaying the Alerts Count" + e.getCause());
        }

    }

    @Test(priority = 51)
    public void siteAlerts() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("sites_site_alerts_summary")).click();

            extentReports.createTest("Site Reports alerts tab").log(Status.PASS, "Successfully Open alerts tab page");
        } catch (Exception e) {
            extentReports.createTest("Site Reports alerts tab").log(Status.FAIL,
                    "Failed to open the alerts tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 52)
    public void siteReportAlertsCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("allSiteAlertTbl1_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("select Site Revenue Count").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("select Site Revenue Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 53)
    public void devicesStatusTable() {
        try {
            WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            System.out.println(
                    "-----------------------------------------------------Site Alerts------------------------------------------------");
            System.out.println(
                    "| Alert Id  | Device SLNo | Device Type | Alert Type | Action |");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 5) {

                    String alertId = cells.get(0).getText();
                    String deviceSlNo = cells.get(1).getText();
                    String deviceType = cells.get(2).getText();
                    String alertType = cells.get(3).getText();
                    String action = cells.get(4).getText();

                    System.out.printf("| %-40s | %-10s | %-10s | %-10s |%-10s |%n", alertId, deviceSlNo,
                            deviceType, alertType, action);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Site Reports Alerts Table").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Site Reports Alerts Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 54)
    public void validateAlertId() {
        WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In site repoorts Alerts ").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullAlertId = false;

        for (WebElement row : rows) {

            WebElement AlertIdCell = row.findElements(By.tagName("td")).get(0);

            String AlertId = AlertIdCell.getText();

            if (AlertId.equalsIgnoreCase("null")) {
                isAnyNullAlertId = true;
                break;
            }
        }
        if (isAnyNullAlertId) {
            extentReports.createTest("In Site Reports Alert table").log(Status.FAIL, "Found a null in alertId");
        } else {
            extentReports.createTest("In Site Reports Alert table").log(Status.PASS, "No null alertId found");
        }
    }

    @Test(priority = 55)
    public void validateDeviceSlNo() {
        WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In site reports Alerts DeviceSlNo ").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullDeviceSlNo = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() >= 2) {
                String DeviceSlNo = cells.get(1).getText();

                if (DeviceSlNo.equalsIgnoreCase("null")) {
                    isAnyNullDeviceSlNo = true;
                    break;
                }
            } else {
                System.out.println("Row does not have enough cells");
            }
        }

        if (isAnyNullDeviceSlNo) {
            extentReports.createTest("In Site Reports Alert table").log(Status.FAIL, "Found a null in DeviceSlNo");
        } else {
            extentReports.createTest("In Site Reports Alert table").log(Status.PASS, "No null DeviceSlNo found");
        }
    }

    @Test(priority = 56)
    public void validateDeviceType() {
        WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In site reports Alerts DeviceType ").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullDeviceType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                String DeviceType = cells.get(2).getText();

                if (DeviceType.equalsIgnoreCase("null")) {
                    isAnyNullDeviceType = true;
                    break;
                }
            } else {
                System.out.println("Row does not have enough cells");
            }
        }
        if (isAnyNullDeviceType) {
            extentReports.createTest("In Site Reports Alert table").log(Status.FAIL, "Found a null in DeviceType");
        } else {
            extentReports.createTest("In Site Reports Alert table").log(Status.PASS, "No null DeviceType found");
        }
    }

    @Test(priority = 57)
    public void validateAlertType() {
        WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In site reports Alerts AlertType ").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullAlertType = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                String AlertType = cells.get(3).getText();

                if (AlertType.equalsIgnoreCase("null")) {
                    isAnyNullAlertType = true;
                    break;
                }
            } else {
                System.out.println("Row does not have enough cells");
            }
        }
        if (isAnyNullAlertType) {
            extentReports.createTest("In Site Reports Alert table").log(Status.FAIL, "Found a null in AlertType");
        } else {
            extentReports.createTest("In Site Reports Alert table").log(Status.PASS, "No null AlertType found");
        }
    }

    @Test(priority = 58)
    public void validateAction() {
        WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In site reports Alerts Action ").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyCompletedAction = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                String Action = cells.get(4).getText();

                if (Action.equalsIgnoreCase("null")) {
                    isAnyCompletedAction = true;
                    break;
                }
            }
        }
        if (isAnyCompletedAction) {
            extentReports.createTest("In Site Reports Alert table").log(Status.FAIL, "Found a null in Action");
        } else {
            extentReports.createTest("In Site Reports Alert table").log(Status.PASS, "No null Action found");
        }
    }

    @Test(priority = 59)
    public void validateActionAsComplete() {
        WebElement table = driver.findElement(By.id("allSiteAlertTbl1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In site reports Alerts Action ").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyCompletedAction = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                String Action = cells.get(4).getText();

                if (Action.equalsIgnoreCase("completed")) {
                    isAnyCompletedAction = true;
                    break;
                }
            }
        }
        if (isAnyCompletedAction) {
            extentReports.createTest("In Site Reports Alert table").log(Status.FAIL, "Found a completed in Action");
        } else {
            extentReports.createTest("In Site Reports Alert table").log(Status.PASS, "No completed Action found");
        }
    }

    @Test(priority = 80)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Site Reports LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Site Reports LogOut").log(Status.FAIL,
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
