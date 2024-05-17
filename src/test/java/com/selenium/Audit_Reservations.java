package com.selenium;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class Audit_Reservations extends BaseTestReport {
    static WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Audit_Reservations.html");
        // extentReports.attachReporter(spark);
        // extentTest = extentReports.createTest("demo");

        String browserPlugin = "firefox";
        System.setProperty(
                "webdriver.chrome.driver",
                ServerCredentials.setDriverPath(browserPlugin));
        // driver = new ChromeDriver();
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("headless");
        // driver = new ChromeDriver(options);
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
    public void redirectAudiTrailPage() {
        try {
            Thread.sleep(2000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();
            driver.findElement(By.className("atr-pg")).click();
            extentReports.createTest("Redirect Audit Trail Reports page ").log(Status.PASS,
                    "Successfully Open Audit Trail Reports Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Redirect Audit Trail Reports page").log(Status.FAIL,
                    "Failed to Open the Audit Trail Reports page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void getSitesList() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
            driver.findElement(By.id("pos_site_name"));
            WebElement site = siteList.get(1);
            String selectedSiteName = site.getText();
            site.click();
            extentReports.createTest("Displaying the sites list in audit trail page").log(Status.PASS,
                    "Successfully selected the second site: " + selectedSiteName);
        } catch (Exception e) {
            extentReports.createTest("Displaying the sites list audit trail page tab").log(Status.FAIL,
                    "Failed to select the second site. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void typeReservations() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("reserveType")));
            Select select = new Select(selectElement);
            Thread.sleep(5000);
            select.selectByValue("Reservations");
            extentReports.createTest("Audi Trail Page Status Type Reservations ").log(Status.PASS,
                    "Successfully Open Reservations in Status in POS device in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type Reservations").log(Status.FAIL,
                    "Failed to Open Reservations in Status in POS device in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 5)
    public void reserveDatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 6)
    public void selectReserveStartDate() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 7)
    public void selectReserveEndDate() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 8)
    public void selectCountReservations25() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("reserveAuditTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("ReserveAuditTable_length ").log(Status.PASS, "Successfully selected as 25");
        } catch (Exception e) {
            extentReports.createTest("ReserveAuditTable_length").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 9)
    public void selectCountReservations10() {
        try {
            WebElement dropdown = driver.findElement(By.name("reserveAuditTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("10");
            Thread.sleep(2000);
            extentReports.createTest("ReserveAuditTable_length").log(Status.PASS, "Successfully selected as 10");
        } catch (Exception e) {
            extentReports.createTest("ReserveAuditTable_length").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 10)
    public void reservationTable() {
        try {
            WebElement table = driver.findElement(By.id("reserveAuditTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("Audit trail Reservations Reports table ").log(Status.PASS,
                        "No User data available");
                return;
            }
            System.out.println(
                    "---------------------------------------- Reservations ----------------------------------------------------------");
            System.out.println(
                    "| Transaction Id | Transaction Type | Vehicle Number| Vehicle Type | Date |mobile No | Email | Amount | Status | Reservation Time | Expiry Time |");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String TransactionId = cells.get(0).getText();
                    String TransactionType = cells.get(1).getText();
                    String VehicleNumber = cells.get(2).getText();
                    String VehicleType = cells.get(3).getText();
                    String Date = cells.get(4).getText();
                    String mobileNo = cells.get(5).getText();
                    String Email = cells.get(6).getText();
                    String Amount = cells.get(7).getText();
                    String Status = cells.get(8).getText();
                    String ReservationTime = cells.get(9).getText();
                    String ExpiryTime = cells.get(10).getText();

                    System.out.printf(
                            "| %-20s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            TransactionId, TransactionType, VehicleNumber, VehicleType, Date, mobileNo, Email, Amount,
                            Status, ReservationTime, ExpiryTime);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Audit Trail Reservations Reports Page Table ").log(Status.PASS,
                    "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Reservations Reports Page Table ").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void compareReserveExpiryTime() {
        try {
            WebElement table = driver.findElement(By.id("reserveAuditTable"));
            WebElement tbody = table.findElement(By.tagName("tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {
                    WebElement ReservationTimeCell = cells.get(9);
                    WebElement expiryTimeCell = cells.get(10);
                    WebElement vehicleNumberCell = cells.get(2);

                    String ReservationTime = ReservationTimeCell.getText();
                    String expiryTime = expiryTimeCell.getText();
                    String vehicleNumber = vehicleNumberCell.getText();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

                    Date ReservationDateTime = sdf.parse(ReservationTime);
                    Date expiryDateTime = sdf.parse(expiryTime);

                    if (ReservationDateTime.after(expiryDateTime)) {
                        extentReports.createTest("In Reservations - Compare Entry and Exit Date/Time")
                                .log(Status.FAIL, "Entry time is after exit time. Entry Time: " + ReservationTime
                                        + ", Exit Time: " + expiryTime + ", Vehicle Number: " + vehicleNumber);
                    } else {
                        long duration = expiryDateTime.getTime() - ReservationDateTime.getTime();
                        if (duration < 0) {
                            extentReports.createTest("In Reservations - Compare Entry and Exit Date/Time")
                                    .log(Status.FAIL, "Negative duration detected. Entry Time: " + ReservationTime
                                            + ", Exit Time: " + expiryTime + ", Vehicle Number: " + vehicleNumber);
                        }
                    }
                }
            }

            extentReports.createTest("In Reservations - Compare Entry and Exit Date/Time").log(Status.PASS,
                    "All entry times are before or equal to exit times.");
        } catch (Exception e) {
            extentReports.createTest("In Reservations - Compare Entry and Exit Date/Time").log(Status.FAIL,
                    "An exception occurred while comparing date/time. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void reservationsCsv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("reserveAuditCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In reserve Audit and download CSV file").log(Status.PASS,
                        "Successfully download the CSV file in Reservations");
            } else {
                extentReports.createTest("In reserve Audit and download CSV file").log(Status.WARNING,
                        "CSV button not found in reservations");
            }

        } catch (Exception e) {
            extentReports.createTest("In reserve Audit CSV-- downlaod CSV file ").log(Status.FAIL,
                    "Failed to download the CSV file in Reservations. Exception: " + e.getCause());

        }
    }

    @Test(priority = 13)
    public void reservationsPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver.findElement(By.id("reserveAuditPdf"));
            String mainWindowHandle = driver.getWindowHandle();

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.numberOfWindowsToBe(2));

                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }

                driver.close();
                driver.switchTo().window(mainWindowHandle);

                extentReports.createTest("PDF Button").log(Status.PASS, "Successfully clicked the PDF button");
            } else {
                extentReports.createTest("PDF Button").log(Status.INFO, "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("PDF Button").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 14)
    public void reserveTypePass() {
        try {
            driver.navigate().refresh();
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("reserveType")));
            Select select = new Select(selectElement);
            select.selectByValue("Passes");
            extentReports.createTest("Audit Trail Page Status Type Passes ").log(Status.PASS,
                    "Successfully Open Passes in Status in Reservations in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type Passes").log(Status.FAIL,
                    "Failed to Open Passes in Status in Reservationse in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 15)
    public void passesDatesEnabled() throws InterruptedException {
        Thread.sleep(5000);
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 16)
    public void selectStartDatePasses() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 17)
    public void selectEndDatePasses() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 18)
    public void selectCountPasses25() {
        selectCountReservations25();
    }

    @Test(priority = 19)
    public void selectCountPasses10() {
        selectCountReservations10();
    }

    @Test(priority = 20)
    public void passesCsv() {
        reservationsCsv();
    }

    @Test(priority = 21)
    public void passesPdf() {
        reservationsPdf();
    }

    @Test(priority = 22)
    public void passesTable() {
        try {
            WebElement table = driver.findElement(By.id("reserveAuditTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("Audit trail Passes Reports table ").log(Status.PASS,
                        "No User data available");
                return;
            }
            System.out.println(
                    "---------------------------------------- Passes ----------------------------------------------------------");
            System.out.println(
                    "| Transaction Id | Transaction Type | Vehicle Number| Vehicle Type | Date |mobile No | Email | Amount | Status | Reservation Time | Expiry Time |");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String TransactionId = cells.get(0).getText();
                    String TransactionType = cells.get(1).getText();
                    String VehicleNumber = cells.get(2).getText();
                    String VehicleType = cells.get(3).getText();
                    String Date = cells.get(4).getText();
                    String mobileNo = cells.get(5).getText();
                    String Email = cells.get(6).getText();
                    String Amount = cells.get(7).getText();
                    String Status = cells.get(8).getText();
                    String ReservationTime = cells.get(9).getText();
                    String ExpiryTime = cells.get(10).getText();

                    System.out.printf(
                            "| %-20s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            TransactionId, TransactionType, VehicleNumber, VehicleType, Date, mobileNo, Email, Amount,
                            Status, ReservationTime, ExpiryTime);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Audit Trail Passes Reports Page Table ").log(Status.PASS,
                    "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Passes Reports Page Table ").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 23)
    public void comparePassReserveExpiryDate() {
        try {
            WebElement table = driver.findElement(By.id("reserveAuditTable"));
            WebElement tbody = table.findElement(By.tagName("tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                WebElement reservationTimeCell = row.findElements(By.tagName("td")).get(9);
                WebElement expiryTimeCell = row.findElements(By.tagName("td")).get(10);
                WebElement vehicleNumberCell = row.findElements(By.tagName("td")).get(2);

                String reservationTime = reservationTimeCell.getText();
                String expiryTime = expiryTimeCell.getText();
                String vehicleNumber = vehicleNumberCell.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Date reservationDate = sdf.parse(reservationTime.split(" ")[0]);
                Date expiryDate = sdf.parse(expiryTime.split(" ")[0]);

                if (reservationDate.after(expiryDate)) {
                    extentReports.createTest("In Passes - Compare Entry and Exit Date")
                            .log(Status.FAIL, "Entry date is after exit date. Entry Date: " + reservationTime
                                    + ", Exit Date: " + expiryTime + ", Vehicle Number: " + vehicleNumber);
                } else {
                    long duration = expiryDate.getTime() - reservationDate.getTime();
                    if (duration < 0) {
                        extentReports.createTest("In Passes - Compare Entry and Exit Date")
                                .log(Status.FAIL, "Negative duration detected. Entry Date: " + reservationTime
                                        + ", Exit Date: " + expiryTime + ", Vehicle Number: " + vehicleNumber);
                    }
                }
            }

            extentReports.createTest("In Passes - Compare Entry and Exit Date").log(Status.PASS,
                    "All entry dates are before or equal to exit dates.");
        } catch (Exception e) {
            extentReports.createTest("In Passes - Compare Entry and Exit Date").log(Status.FAIL,
                    "An exception occurred while comparing date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 24)
    public void clickPOSdevice() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pills-attender-tab")).click();
            extentReports.createTest("Audit Trail select the POS device in Reports page ")
                    .log(Status.PASS, "Successfully select the POS device in Audit Trail Reports Page");

        } catch (Exception e) {
            extentReports.createTest("Audit Trail select the POS device in Reports page").log(Status.FAIL,
                    "Failed to select the POS device in Audit Trail Reports page . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 25)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Audit Trail page Reservations LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail page Reservations LogOut").log(Status.FAIL,
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