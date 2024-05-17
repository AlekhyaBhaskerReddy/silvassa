package com.selenium;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class AudiTrail_PosDevice extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/AuditTrail_Reports.html");
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
    public void statusTypeALL() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement statusDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("statusType")));
            Select select = new Select(statusDropdown);
            select.selectByValue("ALL");
            String selectedValue = statusDropdown.getAttribute("value");
            System.out.println("Status Type :" + selectedValue);

            for (WebElement option : statusDropdown.findElements(By.tagName("option"))) {
                String optionValue = option.getAttribute("value");
                System.out.println("Status Type :" + optionValue);
                extentReports.createTest("Status Type ").log(Status.INFO, "Selected Status: " + optionValue);
            }

            extentReports.createTest("Audi Trail Page Status Type All ").log(Status.PASS,
                    "Successfully Open All in Status in POS device in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type All").log(Status.FAIL,
                    "Failed to Open All in Status in POS device in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 26)
    public void allPOSDatesEnabled() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='pills-pos']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='pills-pos']//div[@id='dayRangeFilter']/label[3]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='pills-pos']//div[@id='dayRangeFilter']/label[4]"))
                    .click();

            extentReports.createTest("Audit Trail POS Dates Enabled").log(Status.PASS,
                    "Successfully selecting the week/month");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail POS Dates Enabled").log(Status.FAIL,
                    "Failed to select the week/month. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 27)
    public void selectPosDevice_AllTrans_StartDate() {
        try {
            driver.findElement(By.xpath("//div[@id='pills-pos']//div[@id='dayRangeFilter']/label[1]"));
            Thread.sleep(1000);

            WebElement startRangeDate1 = driver.findElement(By.cssSelector(".startRangeDate"));
            WebElement endRangeDate1 = driver.findElement(By.cssSelector(".endRangeDate"));

            extentReports.createTest("Initial date ranges in Dashboard ").log(Status.INFO,
                    "Start Date: " + startRangeDate1.getText() + " | End Date: " + endRangeDate1.getText());

            Thread.sleep(3000);
            driver.findElement(By.xpath("//div[@id='pills-pos']//div[@id='dayRangeFilter']/label[1]")).click();
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
            dateElement.click();
            extentReports.createTest("Start Date in Dashboard").log(Status.PASS,
                    "Successfully selected Start Date: ");

        } catch (Exception e) {
            extentReports.createTest("Start Date in Dashboard").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 28)
    public void selectPosDevice_AllTrans_EndDate() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath(
                            "//td[@class='available' and @data-title='r3c4']"));
            dateElement.click();

            extentReports.createTest("End Date in Dashboard").log(Status.PASS,
                    "Successfully selected End Date: ");
            Thread.sleep(3000);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]"));
            Thread.sleep(1000);

            WebElement startRangeDate1 = driver.findElement(By.cssSelector(".startRangeDate"));
            WebElement endRangeDate1 = driver.findElement(By.cssSelector(".endRangeDate"));

            extentReports.createTest("After selecting Dates Ranges in Dashboard ").log(Status.INFO,
                    "Start Date: " + startRangeDate1.getText() + " | End Date: " + endRangeDate1.getText());
        } catch (Exception e) {

            extentReports.createTest("End Date in Dashboard").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 27)
    public void selectStartDateAll() {
        try {
            Thread.sleep(2000);
            WebElement calendar = driver
                    .findElement(By.xpath("//div[@id='pills-pos']//div[@id='dayRangeFilter']/label[1]"));
            calendar.click();

            Thread.sleep(1000);
            WebElement startDate = driver
                    .findElement(By.xpath("//div[@class='drp-calendar left']//td[@data-title='r0c0']"));
            startDate.click();
            Thread.sleep(1000);
            WebElement startDate1 = driver
                    .findElement(By.xpath("//div[@class='drp-calendar left']//td[@data-title='r0c0']"));
            startDate1.click();
            Thread.sleep(1000);
            WebElement startDate2 = driver
                    .findElement(By.xpath("//div[@class='drp-calendar left']//td[@data-title='r0c0']"));
            startDate2.click();
            Thread.sleep(1000);

            WebElement startDateElement = driver
                    .findElement(By.xpath("//div[@class='drp-calendar left']//td[@data-title='r1c1']"));
            String selectedEndDate = startDateElement.getText();
            startDateElement.click();

            extentReports.createTest("Start Date All").log(Status.PASS,
                    "Successfully select Start Date:" + selectedEndDate);
        } catch (Exception e) {

            extentReports.createTest("Start Date All").log(Status.FAIL,
                    "Failed select Start Date.Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 28)
    public void selectEndDateAll() {
        try {
            WebElement endDateElement = driver
                    .findElement(By.xpath("//div[@class='drp-calendar right']//td[@data-title='r5c6']"));
            String selectedEndDate = endDateElement.getText();
            endDateElement.click();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("End Date All").log(Status.PASS,
                    "Successfully select End Date : " + selectedEndDate);

            WebElement startRangeDate1 = driver.findElement(By.cssSelector(".startRangeDate"));
            WebElement endRangeDate1 = driver.findElement(By.cssSelector(".endRangeDate"));

            extentReports.createTest("After selecting Dates Ranges in Dashboard ").log(Status.INFO,
                    "Start Date: " + startRangeDate1.getText() + " | End Date: " + endRangeDate1.getText());
        } catch (Exception e) {

            extentReports.createTest("End Date in All").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 32)
    public void selectCountAllDevice50() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("posAuditTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("50");
            extentReports.createTest("POSAuditTable_length as 50").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("POSAuditTable_length as 50").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 33)
    public void selectCountAllDevice25() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("posAuditTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("POSAuditTable_length as 25").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("POSAuditTable_length as 25").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 33)
    public void selectCountAllDevice10() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("posAuditTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("10");
            extentReports.createTest("POSAuditTable_length as 10").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("POSAuditTable_length as 10").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    // @Test(priority = 34)
    public void clickPages() {
        try {
            Thread.sleep(3000);
            WebElement nextButton = driver.findElement(By.id("posAuditTable_next"));

            while (!nextButton.getAttribute("class").contains("disabled")) {
                nextButton.click();
                Thread.sleep(3000);
                nextButton = driver.findElement(By.id("posAuditTable_next"));
            }
            WebElement previousButton = driver.findElement(By.id("posAuditTable_previous"));
            previousButton.click();

            extentReports.createTest("In POS device all transactions ").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extentReports.createTest("In POS device all transactions ").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    @Test(priority = 35)
    public void viewDetails1All() {
        try {
            Thread.sleep(2000);
            WebElement viewButton = driver.findElement(By.className("btn-outline-info"));
            viewButton.click();
            extentReports.createTest("View Details1 All").log(Status.PASS, "Successfully displayed");
        } catch (Exception e) {
            extentReports.createTest("View Details1 All").log(Status.FAIL,
                    "Failed to View Details1 All. Exception: " + e.getCause());

        }
    }

    @Test(priority = 36)
    public void viewDetailPopUpClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='viewTransModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In view deatils of first transaction POP Up").log(Status.PASS,
                    "Successfully closed the pop up in view deatils in audit trail");
        } catch (Exception e) {
            extentReports.createTest("In view deatils POP Up").log(Status.FAIL,
                    "Failed to close the pop up in view deatils in audit trail. Exception: " + e.getCause());
        }
    }

    @Test(priority = 37)
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

    @Test(priority = 38)
    public void allTransactionsTable() {
        try {
            WebElement table = driver.findElement(By.id("posAuditTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            if (rows.isEmpty()) {
                extentReports.createTest("Audit Trail All transactional Reports Page Table").log(Status.PASS,
                        "No User data available");
                return;
            }

            extentReports.createTest("Audit Trail All transactional Reports Page Table").log(Status.INFO,
                    "Displaying table data:");

            System.out.println(
                    "---------------------------------------- All Transactions ----------------------------------------------------------");
            System.out.println(
                    "| Ticket No | Transaction Type | Vehicle Number| Attender | Entry Time |Exit Time|");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 6) {
                    String TicketNo = cells.get(0).getText();
                    String TransactionType = cells.get(1).getText();
                    String VehicleNumber = cells.get(2).getText();
                    String Attender = cells.get(3).getText();
                    String EntryTime = cells.get(4).getText();
                    String ExitTime = cells.get(5).getText();

                    System.out.printf("| %-20s | %-10s | %-10s | %-10s |%-10s |%-10s |%n", TicketNo, TransactionType,
                            VehicleNumber, Attender, EntryTime, ExitTime);

                    extentReports.createTest("Audit Trail All transactional Reports Page Table").log(Status.INFO,
                            "Row Data: Ticket No: " + TicketNo + ", Transaction Type: " + TransactionType
                                    + ", Vehicle Number: " + VehicleNumber + ", Attender: " + Attender
                                    + ", Entry Time: " + EntryTime + ", Exit Time: " + ExitTime);
                }

                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
                extentReports.createTest("Audit Trail All transactional Reports Page Table").log(Status.PASS,
                        "Displaying Successfully");
            }
        } catch (Exception e) {
            extentReports.createTest("Audit Trail All transactional Reports Page Table").log(Status.FAIL,
                    "Failed to Display. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 39)
    public void compareEntryExitDateTime() {
        try {
            WebElement table = driver.findElement(By.id("posAuditTable"));
            WebElement tbody = table.findElement(By.tagName("tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                WebElement entryTimeCell = row.findElements(By.tagName("td")).get(4);
                WebElement exitTimeCell = row.findElements(By.tagName("td")).get(5);
                WebElement vehicleNumberCell = row.findElements(By.tagName("td")).get(2);
                String entryTime = entryTimeCell.getText();
                String exitTime = exitTimeCell.getText();
                String vehicleNumber = vehicleNumberCell.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

                Date entryDateTime = sdf.parse(entryTime);
                Date exitDateTime = sdf.parse(exitTime);

                if (entryDateTime.after(exitDateTime)) {
                    extentReports.createTest("Audit Reports all transactions  - Compare Entry and Exit Date/Time")
                            .log(Status.PASS, "Entry time is after exit time. Entry Time: " + entryTime
                                    + ", Exit Time: " + exitTime + ", Vehicle Number: " + vehicleNumber);
                } else {
                    long duration = exitDateTime.getTime() - entryDateTime.getTime();
                    if (duration < 0) {
                        extentReports.createTest("Audit Reports all transactions  - Compare Entry and Exit Date/Time")
                                .log(Status.FAIL, "Negative duration detected. Entry Time: " + entryTime
                                        + ", Exit Time: " + exitTime + ", Vehicle Number: " + vehicleNumber);
                    }
                }
            }

        } catch (Exception e) {
            extentReports.createTest("In Audit Reports all transactions  - Compare Entry and Exit Date/Time").log(
                    Status.FAIL,
                    "An exception occurred while comparing date/time. Exception: " + e.getCause());
            pdfAudiTrailCapture(driver, "exception_screenshot");
        }
    }

    @Test(priority = 40)
    public void viewDetails2All() {
        try {
            driver.findElement(By.xpath("//*[@id='posAuditTable']/tbody/tr[2]/td[8]/button"));
            Thread.sleep(2000);
            extentReports.createTest("POSAuditTable_View details").log(Status.PASS,
                    "Successfully displaying the view details2");
        } catch (Exception e) {
            extentReports.createTest("POSAuditTable_View details").log(Status.FAIL,
                    "Failed to display the vie details2. Exception: " + e.getCause());

        }
    }

    @Test(priority = 41)
    public void allCsv() {
        try {
            Thread.sleep(3000);
            WebElement csvButton = driver
                    .findElement(By.id("posAuditCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In POS Audit and download CSV file").log(Status.PASS,
                        "Successfully download the CSV file in POS Device");
            } else {
                extentReports.createTest("In POS Audit and download CSV file").log(Status.INFO,
                        "CSV button not found in POS Device");
            }
        } catch (Exception e) {
            extentReports.createTest("In POS Audit and download CSV file").log(Status.FAIL,
                    "Failed to download the CSV file in POS Device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 42)
    public void allPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.id("posAuditPdf"));

            String mainWindowHandle = driver.getWindowHandle();
            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
                extentReports.createTest("PDF Button in POS device").log(Status.PASS,
                        "Successfully clicked the PDF button");
            } else {
                extentReports.createTest("PDF Button in POS Device").log(Status.INFO, "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("PDF Button").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getCause());
            pdfAudiTrailCapture(driver, "pdf in AudiTrail");
        }
    }

    @Test(priority = 43)
    public void statusTypeEntry() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("statusType")));
            Select select = new Select(selectElement);
            select.selectByValue("ACTIVE");
            extentReports.createTest("Audi Trail Page Status Type ACTIVE ").log(Status.PASS,
                    "Successfully Open ACTIVE in Status in POS device in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type ACTIVE").log(Status.FAIL,
                    "Failed to Open ACTIVE in Status in POS device in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 44)
    public void activeStatusDatesEnabled() {
        allPOSDatesEnabled();
    }

    @Test(priority = 45)
    public void selectStartDateActive() {
        selectPosDevice_AllTrans_StartDate();
    }

    @Test(priority = 46)
    public void selectEndDateActive() {
        selectPosDevice_AllTrans_EndDate();
    }

    @Test(priority = 47)
    public void selectCountActive50() {
        selectCountAllDevice50();
    }

    @Test(priority = 48)
    public void selectCountActive25() {
        selectCountAllDevice25();
    }

    @Test(priority = 49)
    public void selectCountActive10() {
        selectCountAllDevice10();
    }

    @Test(priority = 50)
    public void activeCsv() {
        allCsv();
    }

    @Test(priority = 51)
    public void activePdf() {
        allPdf();
    }

    @Test(priority = 52)
    public void statusTypeExit() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("statusType")));
            Select select = new Select(selectElement);
            select.selectByValue("INACTIVE");
            extentReports.createTest("Audi Trail Page Status Type INACTIVE ").log(Status.PASS,
                    "Successfully Open INACTIVE in Status in POS device in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type INACTIVE").log(Status.FAIL,
                    "Failed to Open INACTIVE in Status in POS device in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 53)
    public void inActiveStatusDatesEnabled() {
        allPOSDatesEnabled();
    }

    @Test(priority = 54)
    public void selectStartDateInActive() {
        selectPosDevice_AllTrans_StartDate();
    }

    @Test(priority = 55)
    public void selectEndDateInActive() {
        selectPosDevice_AllTrans_EndDate();
    }

    @Test(priority = 56)
    public void inActiveCsv() {
        allCsv();
    }

    @Test(priority = 57)
    public void inActivePdf() {
        allPdf();
    }

    @Test(priority = 58)
    public void typeMaintenance() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("statusType")));
            Select select = new Select(selectElement);
            select.selectByValue("MAINTENANCE");
            extentReports.createTest("Audi Trail Page Status Type MAINTENANCE ").log(Status.PASS,
                    "Successfully Open MAINTENANCE in Status in POS device in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type MAINTENANCE").log(Status.FAIL,
                    "Failed to Open MAINTENANCE in Status in POS device in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 59)
    public void maintenanceDatesEnabled() {
        allPOSDatesEnabled();
    }

    @Test(priority = 60)
    public void selectStartDateMaintenance() {
        selectPosDevice_AllTrans_StartDate();
    }

    @Test(priority = 61)
    public void selectEndDateMaintenance() {
        selectPosDevice_AllTrans_EndDate();
    }

    @Test(priority = 62)
    public void maintenanceCsv() {
        allCsv();
    }

    @Test(priority = 63)
    public void maintenancePdf() {
        allPdf();
    }

    @Test(priority = 64)
    public void statusTypeForceExit() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("statusType")));
            Select select = new Select(selectElement);
            select.selectByValue("FORCEEXITREPORTS");
            extentReports.createTest("Audi Trail Page Status Type FORCEEXITREPORTS ").log(Status.PASS,
                    "Successfully Open MAINTENANCE in Status in POS device in Audit Trail Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail Page Status Type FORCEEXITREPORTS").log(Status.FAIL,
                    "Failed to Open MAINTENANCE in Status in POS device in Audit Trail Reports page . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 65)
    public void forceExitStatusDatesEnabled() {
        allPOSDatesEnabled();
    }

    @Test(priority = 66)
    public void selectStartDateForceExit() {
        selectPosDevice_AllTrans_StartDate();
    }

    @Test(priority = 67)
    public void selectEndDateForceExit() {
        selectPosDevice_AllTrans_EndDate();
    }

    @Test(priority = 68)
    public void forceExitCsv() {
        allCsv();
    }

    @Test(priority = 69)
    public void forceExitPdf() {
        allPdf();
    }

    @Test(priority = 70)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Audit Trail page POS DEVICE LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail page POS DEVICE LogOut").log(Status.FAIL,
                    "Failed to LogOut. Exception: " + e.getCause());
        }
    }

    public void clickCancellation() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pills-cancel-tab")).click();
            extentReports.createTest("Audit Trail select Cancellation in Reports page ")
                    .log(Status.PASS, "Successfully select Cancellation in Audit Trail Reports Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Audit Trail select Cancellation in Reports page").log(Status.FAIL,
                    "Failed to select Cancellation in Audit Trail Reports page . Exception: " +
                            e.getCause());
        }
    }

    public void audiTrailCancelDatesEnabled() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//div[@id='cancel-pos']//div[@id='dayRangeFilter']/label[2]")))
                    .click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//div[@id='cancel-pos']//div[@id='dayRangeFilter']/label[3]")))
                    .click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//div[@id='cancel-pos']//div[@id='dayRangeFilter']/label[4]")))
                    .click();

            extentReports.createTest("Audit Trail POS Dates Enabled").log(Status.PASS,
                    "Successfully selecting the week/month");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail POS Dates Enabled").log(Status.FAIL,
                    "Failed to select the week/month. Exception: " + e.getMessage());
        }
    }

    public void selectStartDateCancel() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='cancel-pos']//div[@id='dayRangeFilter']/label/i")).click();
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date Cancel").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date Cancel").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateCancel() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='31']"));

            dateElement.click();
            Thread.sleep(2000);
            extentReports.createTest("End Date Cancel").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Cancel").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            extentReports.flush();
            driver.quit();
        }
    }

    public static void pdfAudiTrailCapture(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
            extentReports.createTest("Screenshot ").addScreenCaptureFromBase64String(
                    Base64.getEncoder().encodeToString(screenshotBytes), screenshotName);
        } catch (Exception e) {
            extentReports.createTest("Screenshot ").log(Status.WARNING,
                    "Failed to capture screenshot: " + e.getMessage());
        }
    }
}