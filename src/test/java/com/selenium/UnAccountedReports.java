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

public class UnAccountedReports extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/UnAccountedReports.html");
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
    public void redirectUnAccountedReportsPage() {
        try {
            Thread.sleep(2000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();
            driver.findElement(By.className("pgr-pg")).click();
            extentReports.createTest("Redirect UnAccounted payment Reports Page ").log(Status.PASS,
                    "Successfully Open UnAccounted payment Reports Page ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Redirect UnAccounted payment Report Page ").log(Status.FAIL,
                    "Failed to Open the UnAccounted payment Reports Page  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void viewUnAccountedSiteList() {
        try {

            Thread.sleep(3000);

            extentReports.createTest("Displaying the sites list in UnAccounted payment Page ").log(Status.PASS,
                    " Successfully displaying the sites list");
        } catch (Exception e) {
            extentReports.createTest("Displaying the sites list in UnAccounted payment Reports Page ").log(Status.FAIL,
                    "Failed displaying the sites list. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void selectDevicemPOS() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("deviceSelect")).sendKeys("MPOS");
            extentReports.createTest("In UnAccounted Payment reports page select the device type as MPOS ").log(
                    Status.PASS,
                    "Successfully select device type as MPOS ");
        } catch (Exception e) {
            extentReports.createTest("In UnAccounted Payment reports page select the device type as MPOS").log(
                    Status.FAIL,
                    "Failed to select device type as MPOS  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void mPOSDatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 7)
    public void selectMPOSStartDate() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 8)
    public void selectMPOSEndDate() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 9)
    public void selectMPOSListCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("paymentReportsTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("payment Reports Table length").log(Status.PASS,
                    "Successfully selected as" + select);
        } catch (Exception e) {
            extentReports.createTest("payment Reports Table length").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 10)
    public void getUnAccountedTableDataMPOS() {
        try {
            WebElement table = driver.findElement(By.id("paymentReportsTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("InUnAccounted Payment Reports table ").log(Status.PASS,
                        "No User data available");
                return;
            }
            System.out.println(
                    "---------------------------------------- UnAccounted Payment Reports -------------------------------------------------------");
            System.out.println(
                    "| Ticket No | Vehicle Number | Mobile Number | Vehicle Type | Amount | Discounted Amount | PaymentType |Status| Type| Payment Client| Email |");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String TicketNo = cells.get(0).getText();
                    String VehicleNumber = cells.get(1).getText();
                    String MobileNumber = cells.get(2).getText();
                    String VehicleType = cells.get(3).getText();
                    String Amount = cells.get(4).getText();
                    String DiscountedAmount = cells.get(5).getText();
                    String PaymentType = cells.get(6).getText();
                    String Status = cells.get(7).getText();
                    String Type = cells.get(8).getText();
                    String PaymentClient = cells.get(9).getText();
                    String Email = cells.get(10).getText();

                    System.out.printf(
                            "| %-20s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            TicketNo,
                            VehicleNumber,
                            MobileNumber, VehicleType, Amount, DiscountedAmount, PaymentType, Status, Type,
                            PaymentClient, Email);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("UnAccounted Payment Reports Page MPOS Table ").log(Status.PASS,
                    "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("UnAccounted Payment Reports Page MPOS Table ").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void searchMPOSVehicleNumber() {
        try {
            Thread.sleep(2000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#paymentReportsTable_filter input[type='search']"));
            searchInput.clear();
            searchInput.sendKeys("AP74FR455");
            searchInput.clear();
            Thread.sleep(2000);

            WebElement table = driver.findElement(By.cssSelector("#paymentReportsTable tbody"));

            List<WebElement> rows = table.findElements(By.tagName("tr"));

            if (rows.isEmpty()) {
                extentReports.createTest("UnAccounted Payment Reports ANDROID search as DS24FR455").log(Status.INFO,
                        "No data available for the specified vehicle number");
            } else {
                extentReports.createTest("UnAccounted Payment Reports ANDROID search as DS24FR455").log(Status.PASS,
                        "Successfully searched for the Vehicle number");
            }
        } catch (Exception e) {
            extentReports.createTest("UnAccounted Payment Reports ANDROID search as DS24FR455").log(Status.FAIL,
                    "Failed to search the Vehicle Number. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void validateMPOSTicketNo() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS TicketNo").log(Status.PASS,
                    "No user data available");
            return;
        }

        boolean isAnyNullTicketNo = false;

        for (WebElement row : rows) {
            WebElement TicketNoCell = row.findElements(By.tagName("td")).get(0);
            String TicketNo = TicketNoCell.getText();

            if (TicketNo.equalsIgnoreCase("null")) {
                isAnyNullTicketNo = true;
                break;
            }
        }

        if (isAnyNullTicketNo) {
            extentReports.createTest("In UnAccounted Payment Reports MPOS TicketNo").log(Status.FAIL,
                    "Found a null TicketNo");
        } else {
            extentReports.createTest("In UnAccounted Payment Reports MPOS TicketNo").log(Status.PASS,
                    "No null TicketNo found");
        }
    }

    @Test(priority = 13)
    public void validateMPOSVehicleNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS VehicleNumber ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnynullVehicleNumber = true;
                    break;
                }
            }
            if (isAnynullVehicleNumber) {
                extentReports.createTest("In UnAccounted Payment Reports MPOS VehicleNumber").log(Status.FAIL,
                        "Found a null VehicleNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports MPOS VehicleNumber").log(Status.PASS,
                        "No null VehicleNumber found");
            }
        }
    }

    @Test(priority = 14)
    public void validateMPOSMobileNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page MPOS MobileNumber ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullMobileNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement MobileNumberCell = cells.get(2);
                String MobileNumber = MobileNumberCell.getText();

                if (MobileNumber.equalsIgnoreCase("null")) {
                    isAnynullMobileNumber = true;
                    break;
                }
            }
            if (isAnynullMobileNumber) {
                extentReports.createTest("In UnAccounted Payment Reports MPOS MobileNumber").log(Status.FAIL,
                        "Found a null MobileNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports MPOS MobileNumber").log(Status.PASS,
                        "No null MobileNumber found");
            }
        }
    }

    @Test(priority = 15)
    public void validateMPOSVehicleType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page MPOS VehicleType ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement VehicleTypeCell = cells.get(3);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnynullVehicleType = true;
                    break;
                }
            }
            if (isAnynullVehicleType) {
                extentReports.createTest("In UnAccounted Payment Reports MPOS VehicleType").log(Status.FAIL,
                        "Found a null VehicleType");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports MPOS VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 16)
    public void validateMPOSAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS table Amount ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement AmountCell = cells.get(4);

                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("Zero")) {
                    isAnyZeroAmount = true;
                    break;
                }
            }
            if (isAnyZeroAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table Amount").log(Status.FAIL,
                        "Found a Zero Amount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table Amount").log(Status.PASS,
                        "No Zero Amount found");
            }
        }
    }

    @Test(priority = 17)
    public void validateMPOSDisCountedAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS table DisCountedAmount ").log(
                    Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroDisCountedAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement DisCountedAmountCell = cells.get(5);

                String DisCountedAmount = DisCountedAmountCell.getText();

                if (DisCountedAmount.equalsIgnoreCase("Zero")) {
                    isAnyZeroDisCountedAmount = true;
                    break;
                }
            }
            if (isAnyZeroDisCountedAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table DisCountedAmount").log(
                        Status.FAIL,
                        "Found a Zero DisCountedAmount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table DisCountedAmount").log(
                        Status.PASS,
                        "No Zero DisCountedAmount found");
            }
        }
    }

    @Test(priority = 18)
    public void validateMPOSPaymentType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS table PaymentTYpe ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullPaymentType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement PaymentTypeCell = cells.get(6);

                String PaymentType = PaymentTypeCell.getText();

                if (PaymentType.equalsIgnoreCase("null")) {
                    isAnynullPaymentType = true;
                    break;
                }
            }
            if (isAnynullPaymentType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table PaymentType").log(Status.FAIL,
                        "Found a Zero PaymentType");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table PaymentType").log(Status.PASS,
                        "No Zero PaymentType found");
            }
        }
    }

    @Test(priority = 19)
    public void validateMPOSStatus() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS table Status ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement StatusCell = cells.get(7);

                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnynullStatus = true;
                    break;
                }
            }
            if (isAnynullStatus) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table Status").log(Status.FAIL,
                        "Found a Zero Status");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table Status").log(Status.PASS,
                        "No Zero Status found");
            }
        }
    }

    @Test(priority = 20)
    public void validateMPOSType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MPOS table Type ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement TypeCell = cells.get(8);

                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnynullType = true;
                    break;
                }
            }
            if (isAnynullType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table Type").log(Status.FAIL,
                        "Found a Zero Type");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MPOS table Type").log(Status.PASS,
                        "No Zero Type found");
            }
        }
    }

    @Test(priority = 21)
    public void selectDeviceAndroid() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("deviceSelect")).sendKeys("ANDROID");
            extentReports.createTest("In UnAccounted Payment reports page select the device type as ANDROID ").log(
                    Status.PASS,
                    "Successfully select device type as ANDROID ");
        } catch (Exception e) {
            extentReports.createTest("In UnAccounted Payment reports page select the device type as ANDROID").log(
                    Status.FAIL,
                    "Failed to select device type as ANDROID  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 22)
    public void androidDatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 23)
    public void selectANDROIDStartDate() {
        selectMPOSStartDate();
    }

    @Test(priority = 24)
    public void selectANDROIDEndDate() {
        selectMPOSEndDate();
    }

    @Test(priority = 25)
    public void selectANDROIDListCount() {
        selectMPOSListCount();
    }

    @Test(priority = 26)
    public void unAccountedTableANDROID() {
        getUnAccountedTableDataMPOS();
    }

    @Test(priority = 27)
    public void searchANDROIDVehicleNumber() {
        searchMPOSVehicleNumber();
    }

    @Test(priority = 28)
    public void validateANDROIDTicketNo() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page ANDROID TicketNo").log(Status.PASS,
                    "No user data available");
            return;
        }

        boolean isAnyNullTicketNo = false;

        for (WebElement row : rows) {
            WebElement TicketNoCell = row.findElements(By.tagName("td")).get(0);
            String TicketNo = TicketNoCell.getText();

            if (TicketNo.equalsIgnoreCase("null")) {
                isAnyNullTicketNo = true;
                break;
            }
        }

        if (isAnyNullTicketNo) {
            extentReports.createTest("In UnAccounted Payment Reports ANDROID TicketNo").log(Status.FAIL,
                    "Found a null TicketNo");
        } else {
            extentReports.createTest("In UnAccounted Payment Reports ANDROID TicketNo").log(Status.PASS,
                    "No null TicketNo found");
        }
    }

    @Test(priority = 29)
    public void validateANDROIDVehicleNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page ANDROID VehicleNumber ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnynullVehicleNumber = true;
                    break;
                }
            }
            if (isAnynullVehicleNumber) {
                extentReports.createTest("In UnAccounted Payment Reports ANDROID VehicleNumber").log(Status.FAIL,
                        "Found a null VehicleNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports ANDROID VehicleNumber").log(Status.PASS,
                        "No null VehicleNumber found");
            }
        }
    }

    @Test(priority = 30)
    public void validateANDROIDMobileNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page ANDROID MobileNumber ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullMobileNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement MobileNumberCell = cells.get(2);
                String MobileNumber = MobileNumberCell.getText();

                if (MobileNumber.equalsIgnoreCase("null")) {
                    isAnynullMobileNumber = true;
                    break;
                }
            }
            if (isAnynullMobileNumber) {
                extentReports.createTest("In UnAccounted Payment Reports ANDROID MobileNumber").log(Status.FAIL,
                        "Found a null MobileNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports ANDROID MobileNumber").log(Status.PASS,
                        "No null MobileNumber found");
            }
        }
    }

    @Test(priority = 31)
    public void validateANDROIDVehicleType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page ANDROID VehicleType ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement VehicleTypeCell = cells.get(3);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnynullVehicleType = true;
                    break;
                }
            }
            if (isAnynullVehicleType) {
                extentReports.createTest("In UnAccounted Payment Reports ANDROID VehicleType").log(Status.FAIL,
                        "Found a null VehicleType");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports ANDROID VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 32)
    public void validateANDROIDAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page ANDROID table Amount ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement AmountCell = cells.get(4);
                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("Zero")) {
                    isAnyZeroAmount = true;
                    break;
                }
            }
            if (isAnyZeroAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table Amount").log(Status.FAIL,
                        "Found a Zero Amount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table Amount").log(Status.PASS,
                        "No Zero Amount found");
            }
        }
    }

    @Test(priority = 33)
    public void validateANDROIDDisCountedAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page ANDROID table DisCountedAmount ").log(
                    Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroDisCountedAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement DisCountedAmountCell = cells.get(5);

                String DisCountedAmount = DisCountedAmountCell.getText();

                if (DisCountedAmount.equalsIgnoreCase("Zero")) {
                    isAnyZeroDisCountedAmount = true;
                    break;
                }
            }
            if (isAnyZeroDisCountedAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table DisCountedAmount").log(
                        Status.FAIL,
                        "Found a Zero DisCountedAmount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table DisCountedAmount").log(
                        Status.PASS,
                        "No Zero DisCountedAmount found");
            }
        }
    }

    @Test(priority = 34)
    public void validateANDROIDPaymentType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page ANDROID table PaymentTYpe ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullPaymentType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement PaymentTypeCell = cells.get(6);
                String PaymentType = PaymentTypeCell.getText();

                if (PaymentType.equalsIgnoreCase("null")) {
                    isAnynullPaymentType = true;
                    break;
                }
            }
            if (isAnynullPaymentType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table PaymentType").log(
                        Status.FAIL,
                        "Found a Zero PaymentType");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table PaymentType").log(
                        Status.PASS,
                        "No Zero PaymentType found");
            }
        }
    }

    @Test(priority = 35)
    public void validateANDROIDStatus() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page ANDROID table Status ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement StatusCell = cells.get(7);

                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnynullStatus = true;
                    break;
                }
            }
            if (isAnynullStatus) {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table Status").log(Status.FAIL,
                        "Found a Zero Status");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table Status").log(Status.PASS,
                        "No Zero Status found");
            }
        }
    }

    @Test(priority = 36)
    public void validateANDROIDType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page ANDROID table Type ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement TypeCell = cells.get(8);
                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnynullType = true;
                    break;
                }
            }
            if (isAnynullType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table Type").log(Status.FAIL,
                        "Found a Zero Type");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page ANDROID table Type").log(Status.PASS,
                        "No Zero Type found");
            }
        }
    }

    @Test(priority = 37)
    public void selectDeviceiOS() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("deviceSelect")).sendKeys("IOS");
            extentReports.createTest("In UnAccounted Payment reports page select the device type as iOS ").log(
                    Status.PASS,
                    "Successfully select device type as iOS ");
        } catch (Exception e) {
            extentReports.createTest("In UnAccounted Payment reports page select the device type as iOS").log(
                    Status.FAIL,
                    "Failed to select device type as iOS  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 38)
    public void iOSDatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 39)
    public void selectiOSStartDate() {
        selectMPOSStartDate();
    }

    @Test(priority = 40)
    public void selectiOSEndDate() {
        selectMPOSEndDate();
    }

    @Test(priority = 41)
    public void selectiOSListCount() {
        selectMPOSListCount();
    }

    @Test(priority = 42)
    public void unAccountedTableiOS() {
        getUnAccountedTableDataMPOS();
    }

    @Test(priority = 43)
    public void searchiOSVehicleNumber() {
        searchMPOSVehicleNumber();
    }

    @Test(priority = 44)
    public void validateiOSTicketNo() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS TicketNo").log(Status.PASS,
                    "No user data available");
            return;
        }

        boolean isAnyNullTicketNo = false;

        for (WebElement row : rows) {
            WebElement TicketNoCell = row.findElements(By.tagName("td")).get(0);
            String TicketNo = TicketNoCell.getText();

            if (TicketNo.equalsIgnoreCase("null")) {
                isAnyNullTicketNo = true;
                break;
            }
        }

        if (isAnyNullTicketNo) {
            extentReports.createTest("In UnAccounted Payment Reports iOS TicketNo").log(Status.FAIL,
                    "Found a null TicketNo");
        } else {
            extentReports.createTest("In UnAccounted Payment Reports iOS TicketNo").log(Status.PASS,
                    "No null TicketNo found");
        }
    }

    @Test(priority = 45)
    public void validateiOSVehicleNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page iOS VehicleNumber ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnynullVehicleNumber = true;
                    break;
                }
            }
            if (isAnynullVehicleNumber) {
                extentReports.createTest("In UnAccounted Payment Reports iOS VehicleNumber").log(Status.FAIL,
                        "Found a null VehicleNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports iOS VehicleNumber").log(Status.PASS,
                        "No null VehicleNumber found");
            }
        }
    }

    @Test(priority = 46)
    public void validateiOSMobileNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page iOS MobileNumber ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnyemptyMobileNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement MobileNumberCell = cells.get(2);
                String MobileNumber = MobileNumberCell.getText();

                if (MobileNumber.equalsIgnoreCase("empty")) {
                    isAnyemptyMobileNumber = true;
                    break;
                }
            }
            if (isAnyemptyMobileNumber) {
                extentReports.createTest("In UnAccounted Payment Reports iOS MobileNumber").log(Status.FAIL,
                        "Found a empty MobileNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports iOS MobileNumber").log(Status.PASS,
                        "No empty MobileNumber found");
            }
        }
    }

    @Test(priority = 47)
    public void validateiOSVehicleType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page iOS VehicleType ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement VehicleTypeCell = cells.get(3);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnynullVehicleType = true;
                    break;
                }
            }
            if (isAnynullVehicleType) {
                extentReports.createTest("In UnAccounted Payment Reports iOS VehicleType").log(Status.FAIL,
                        "Found a null VehicleType");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports iOS VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 48)
    public void validateiOSAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table Amount ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement AmountCell = cells.get(4);

                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("Zero")) {
                    isAnyZeroAmount = true;
                    break;
                }
            }
            if (isAnyZeroAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table Amount").log(Status.FAIL,
                        "Found a Zero Amount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table Amount").log(Status.PASS,
                        "No Zero Amount found");
            }
        }
    }

    @Test(priority = 49)
    public void validateiOSDisCountedAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table DisCountedAmount ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroDisCountedAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement DisCountedAmountCell = cells.get(5);
                String DisCountedAmount = DisCountedAmountCell.getText();

                if (DisCountedAmount.equalsIgnoreCase("Zero")) {
                    isAnyZeroDisCountedAmount = true;
                    break;
                }
            }
            if (isAnyZeroDisCountedAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table DisCountedAmount").log(
                        Status.FAIL,
                        "Found a Zero DisCountedAmount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table DisCountedAmount").log(
                        Status.PASS,
                        "No Zero DisCountedAmount found");
            }
        }
    }

    @Test(priority = 50)
    public void validateiOSPaymentType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table PaymentTYpe ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullPaymentType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement PaymentTypeCell = cells.get(6);

                String PaymentType = PaymentTypeCell.getText();

                if (PaymentType.equalsIgnoreCase("null")) {
                    isAnynullPaymentType = true;
                    break;
                }
            }
            if (isAnynullPaymentType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table PaymentType").log(Status.FAIL,
                        "Found a Zero PaymentType");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table PaymentType").log(Status.PASS,
                        "No Zero PaymentType found");
            }
        }
    }

    @Test(priority = 51)
    public void validateiOSStatus() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table Status ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement StatusCell = cells.get(7);

                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnynullStatus = true;
                    break;
                }
            }
            if (isAnynullStatus) {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table Status").log(Status.FAIL,
                        "Found a Zero Status");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table Status").log(Status.PASS,
                        "No Zero Status found");
            }
        }
    }

    @Test(priority = 52)
    public void validateiOSType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table Type ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement TypeCell = cells.get(8);

                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnynullType = true;
                    break;
                }
            }
            if (isAnynullType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table Type").log(Status.FAIL,
                        "Found a Zero Type");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page iOS table Type").log(Status.PASS,
                        "No Zero Type found");
            }
        }
    }

    @Test(priority = 53)
    public void selectDeviceMobileMPOS() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("deviceSelect")).sendKeys("MOBILE_MPOS");
            extentReports.createTest("In UnAccounted Payment reports page select the device type as MOBILE_MPOS ").log(
                    Status.PASS,
                    "Successfully select device type as MOBILE_MPOS ");
        } catch (Exception e) {
            extentReports.createTest("In UnAccounted Payment reports page select the device type as MOBILE_MPOS").log(
                    Status.FAIL,
                    "Failed to select device type as MOBILE_MPOS  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 54)
    public void mobileMPOSDatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 55)
    public void selectMobileMOSStartDate() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 56)
    public void selectMobileMPOSEndDate() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 57)
    public void selectMobileMPOSListCount() {
        selectMPOSListCount();
    }

    @Test(priority = 58)
    public void unAccountedTableMobileMPOS() {
        getUnAccountedTableDataMPOS();
    }

    @Test(priority = 59)
    public void searchVehicleNumber() {
        searchMPOSVehicleNumber();
    }

    @Test(priority = 60)
    public void validateMmPOSTicketNo() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MOBILE_MPOS TicketNo").log(Status.PASS,
                    "No user data available");
            return;
        }

        boolean isAnyNullTicketNo = false;

        for (WebElement row : rows) {
            WebElement TicketNoCell = row.findElements(By.tagName("td")).get(0);
            String TicketNo = TicketNoCell.getText();

            if (TicketNo.equalsIgnoreCase("null")) {
                isAnyNullTicketNo = true;
                break;
            }
        }

        if (isAnyNullTicketNo) {
            extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS TicketNo").log(Status.FAIL,
                    "Found a null TicketNo");
        } else {
            extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS TicketNo").log(Status.PASS,
                    "No null TicketNo found");
        }
    }

    @Test(priority = 61)
    public void validateMmPOSVehicleNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page MOBILE_MPOS VehicleNumber ").log(
                    Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnynullVehicleNumber = true;
                    break;
                }
            }
            if (isAnynullVehicleNumber) {
                extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS VehicleNumber").log(Status.FAIL,
                        "Found a null VehicleNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS VehicleNumber").log(Status.PASS,
                        "No null VehicleNumber found");
            }
        }
    }

    @Test(priority = 62)
    public void validateMmPOSMobileNumber() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page MOBILE_MPOS MobileNumber ").log(
                    Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnyemptyMobileNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement MobileNumberCell = cells.get(2);
                String MobileNumber = MobileNumberCell.getText();

                if (MobileNumber.equalsIgnoreCase("empty")) {
                    isAnyemptyMobileNumber = true;
                    break;
                }
            }
            if (isAnyemptyMobileNumber) {
                extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS MobileNumber").log(Status.FAIL,
                        "Found a empty MobileNumber");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS MobileNumber").log(Status.PASS,
                        "No empty MobileNumber found");
            }
        }
    }

    @Test(priority = 63)
    public void validateMmPOSVehicleType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In In UnAccounted Payment Reports Page iOS VehicleType ").log(Status.FAIL,
                    "No User data available");
            return;
        }

        boolean isAnynullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement VehicleTypeCell = cells.get(3);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnynullVehicleType = true;
                    break;
                }
            }
            if (isAnynullVehicleType) {
                extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS VehicleType").log(Status.FAIL,
                        "Found a null VehicleType");
            } else {
                extentReports.createTest("In UnAccounted Payment Reports MOBILE_MPOS VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 64)
    public void validateMmPOSAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table Amount ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement AmountCell = cells.get(4);

                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("Zero")) {
                    isAnyZeroAmount = true;
                    break;
                }
            }
            if (isAnyZeroAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table Amount").log(
                        Status.FAIL,
                        "Found a Zero Amount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table Amount").log(
                        Status.PASS,
                        "No Zero Amount found");
            }
        }
    }

    @Test(priority = 65)
    public void validateMmPOSDisCountedAmount() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table DisCountedAmount ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnyZeroDisCountedAmount = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement DisCountedAmountCell = cells.get(5);

                String DisCountedAmount = DisCountedAmountCell.getText();

                if (DisCountedAmount.equalsIgnoreCase("Zero")) {
                    isAnyZeroDisCountedAmount = true;
                    break;
                }
            }
            if (isAnyZeroDisCountedAmount) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table DisCountedAmount").log(
                        Status.FAIL,
                        "Found a Zero DisCountedAmount");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table DisCountedAmount").log(
                        Status.PASS,
                        "No Zero DisCountedAmount found");
            }
        }
    }

    @Test(priority = 66)
    public void validateMmPOSPaymentType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page iOS table PaymentTYpe ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullPaymentType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement PaymentTypeCell = cells.get(6);

                String PaymentType = PaymentTypeCell.getText();

                if (PaymentType.equalsIgnoreCase("null")) {
                    isAnynullPaymentType = true;
                    break;
                }
            }
            if (isAnynullPaymentType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table PaymentType").log(
                        Status.FAIL,
                        "Found a Zero PaymentType");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table PaymentType").log(
                        Status.PASS,
                        "No Zero PaymentType found");
            }
        }
    }

    @Test(priority = 67)
    public void validateMmPOSStatus() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MOBILE_MPOS table Status ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement StatusCell = cells.get(7);

                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnynullStatus = true;
                    break;
                }
            }
            if (isAnynullStatus) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table Status").log(
                        Status.FAIL,
                        "Found a Zero Status");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table Status").log(
                        Status.PASS,
                        "No Zero Status found");
            }
        }
    }

    @Test(priority = 68)
    public void validateMmPOSType() {
        WebElement table = driver.findElement(By.id("paymentReportsTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted Payment Reports Page MOBILE_MPOS table Type ").log(Status.FAIL,
                    "No data available");
            return;
        }

        boolean isAnynullType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement TypeCell = cells.get(8);
                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnynullType = true;
                    break;
                }
            }
            if (isAnynullType) {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table Type").log(Status.FAIL,
                        "Found a Zero Type");
            } else {
                extentReports.createTest("In  UnAccounted Payment Reports Page MOBILE_MPOS table Type").log(Status.PASS,
                        "No Zero Type found");
            }
        }
    }

    @Test(priority = 69)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("UnAccounted Reports page LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("UnAccounted Reports page LogOut").log(Status.FAIL,
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
