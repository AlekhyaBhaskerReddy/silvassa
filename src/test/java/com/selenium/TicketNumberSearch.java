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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class TicketNumberSearch extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/TicketNumberSearch.html");
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
    public void clickReportsAnalyticsPage() {
        try {
            Thread.sleep(3000);
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
    public void ticketNumberSearchPage() {
        try {
            Thread.sleep(4000);
            driver.findElement(By.className("tns-pg")).click();
            extentReports.createTest("Ticket Number Search Page Reports ").log(Status.PASS,
                    "Successfully Open Mobile Number search Reports Page");

        } catch (Exception e) {
            extentReports.createTest("Ticket Number Search Page Reports ").log(Status.FAIL,
                    "Failed to Open the Ticket Number search Reports . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void ticketNumberEnterInvalid() {
        try {
            Thread.sleep(2000);
            WebElement ticketSearch = driver.findElement(By.id("ticketSearch"));
            String input = "1234";
            ticketSearch.sendKeys(input);

            if (input.matches(".*[a-zA-Z].*")) {
                extentReports.createTest("Ticket Number Enter").log(Status.PASS,
                        "Successfully entered the valid ticket number in Ticket Number search Reports Page");
            } else {
                extentReports.createTest("Ticket Number Enter").log(Status.FAIL,
                        "Failed to enter a valid ticket number in Ticket Number search Reports Page. Numeric-only input is not allowed.");
            }
        } catch (Exception e) {
            extentReports.createTest("Ticket Number Enter").log(Status.FAIL,
                    "Failed to enter the Ticket number in Ticket Number search Reports. Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void ticketNumberSubmit() {
        ticketNumberSubmit(driver, extentReports);
    }

    @Test(priority = 7)
    public void selectTicketNumberSearchCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("ticketSearchTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("select Ticket Count").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("select Ticket Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void ticketNumberTransactionsTable() {
        try {
            WebElement table = driver.findElement(By.id("ticketSearchTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In Transaction table ").log(Status.FAIL, "No data available");
                return;
            }

            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(
                    "| Transaction Id  | Transaction Type | Vehicle Number | Date/Time | Start Date|End Date|Amount|Status| ");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 8) {

                    String TransactionId = cells.get(0).getText();
                    String TransactionType = cells.get(1).getText();
                    String VehicleNumber = cells.get(2).getText();
                    String DateTime = cells.get(3).getText();
                    String StartDate = cells.get(4).getText();
                    String EndDate = cells.get(5).getText();
                    String Amount = cells.get(6).getText();
                    String Status = cells.get(7).getText();

                    System.out.printf("| %-40s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            TransactionId, TransactionType,
                            VehicleNumber, DateTime, StartDate, EndDate, Amount,
                            Status);
                }
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Ticket Number Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Ticket Number Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void ticketNumberSearchPageAgain() {
        ticketNumberSearchPage();
    }

    @Test(priority = 10)
    public void ticketNumberEnterValid() {
        try {
            Thread.sleep(2000);
            WebElement ticketSearch = driver.findElement(By.id("ticketSearch"));
            String input = "JMC54z00015N";
            ticketSearch.sendKeys(input);

            if (input.matches(".*[a-zA-Z].*")) {
                extentReports.createTest("Ticket Number Enter").log(Status.PASS,
                        "Successfully entered the valid ticket number in Ticket Number search Reports Page");
            } else {
                extentReports.createTest("Ticket Number Enter").log(Status.FAIL,
                        "Failed to enter a valid ticket number in Ticket Number search Reports Page. Numeric-only input is not allowed.");
            }
        } catch (Exception e) {
            extentReports.createTest("Ticket Number Enter").log(Status.FAIL,
                    "Failed to enter the Ticket number in Ticket Number search Reports. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void ticketNumberSubmitAgain() {
        ticketNumberSubmit();
    }

    @Test(priority = 12)
    public void selectTicketNumberSearchCountAgain() {
        selectTicketNumberSearchCount();
    }

    @Test(priority = 13)
    public void ticketNumberTableValid() {
        ticketNumberTransactionsTable();
    }

    @Test(priority = 14)
    public void validateTicketTransactionId() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table TransactionId ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionId = false;

        for (WebElement row : rows) {

            WebElement TransactionIdCell = row.findElements(By.tagName("td")).get(0);

            String TransactionId = TransactionIdCell.getText();

            if (TransactionId.equalsIgnoreCase("null")) {
                isAnyNullTransactionId = true;
                break;
            }
        }
        if (isAnyNullTransactionId) {
            extentReports.createTest("In Ticket table TransactionId").log(Status.FAIL, "Found a null TransactionId");
        } else {
            extentReports.createTest("In Ticket table TransactionId").log(Status.PASS, "No null TransactionId found");
        }
    }

    @Test(priority = 15)
    public void validateTicketTransactionType() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table TransactionId ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionType = false;

        for (WebElement row : rows) {

            WebElement TransactionTypeCell = row.findElements(By.tagName("td")).get(1);

            String TransactionType = TransactionTypeCell.getText();

            if (TransactionType.equalsIgnoreCase("null")) {
                isAnyNullTransactionType = true;
                break;
            }
        }
        if (isAnyNullTransactionType) {
            extentReports.createTest("In Ticket table TransactionType").log(Status.FAIL,
                    "Found a null TransactionType");
        } else {
            extentReports.createTest("In Ticket table TransactionType").log(Status.PASS,
                    "No null TransactionType found");
        }
    }

    @Test(priority = 16)
    public void validateTicketVehicleNumber() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table VehicleNumber ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            WebElement VehicleNumberCell = row.findElements(By.tagName("td")).get(2);

            String VehicleNumber = VehicleNumberCell.getText();

            if (VehicleNumber.equalsIgnoreCase("null")) {
                isAnyNullVehicleNumber = true;
                break;
            }
        }
        if (isAnyNullVehicleNumber) {
            extentReports.createTest("In Ticket table VehicleNumber").log(Status.FAIL, "Found a null VehicleNumber");
        } else {
            extentReports.createTest("In Ticket table VehicleNumber").log(Status.PASS, "No null VehicleNumber found");
        }
    }

    @Test(priority = 17)
    public void validateTicketDateTime() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table DateTime ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullDateTime = false;

        for (WebElement row : rows) {

            WebElement DateTimeCell = row.findElements(By.tagName("td")).get(3);

            String DateTime = DateTimeCell.getText();

            if (DateTime.equalsIgnoreCase("null")) {
                isAnyNullDateTime = true;
                break;
            }
        }
        if (isAnyNullDateTime) {
            extentReports.createTest("In Ticket table DateTime").log(Status.FAIL, "Found a null DateTime");
        } else {
            extentReports.createTest("In Ticket table DateTime").log(Status.PASS, "No null DateTime found");
        }
    }

    @Test(priority = 18)
    public void validateTicketStartDate() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table StartDate ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStartDate = false;

        for (WebElement row : rows) {

            WebElement StartDateCell = row.findElements(By.tagName("td")).get(4);

            String StartDate = StartDateCell.getText();

            if (StartDate.equalsIgnoreCase("null")) {
                isAnyNullStartDate = true;
                break;
            }
        }
        if (isAnyNullStartDate) {
            extentReports.createTest("In Ticket table StartDate").log(Status.FAIL, "Found a null StartDate");
        } else {
            extentReports.createTest("In Ticket table StartDate").log(Status.PASS, "No null StartDate found");
        }
    }

    @Test(priority = 19)
    public void validateTicketEndDate() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table EndDate ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullEndDate = false;

        for (WebElement row : rows) {

            WebElement EndDateCell = row.findElements(By.tagName("td")).get(5);

            String EndDate = EndDateCell.getText();

            if (EndDate.equalsIgnoreCase("null")) {
                isAnyNullEndDate = true;
                break;
            }
        }
        if (isAnyNullEndDate) {
            extentReports.createTest("In Ticket table EndDate").log(Status.FAIL, "Found a null EndDate");
        } else {
            extentReports.createTest("In Ticket table EndDate").log(Status.PASS, "No null EndDate found");
        }
    }

    @Test(priority = 20)
    public void validateTicketAmount() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table Amount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullAmount = false;

        for (WebElement row : rows) {

            WebElement AmountCell = row.findElements(By.tagName("td")).get(6);

            String Amount = AmountCell.getText();

            if (Amount.equalsIgnoreCase("null")) {
                isAnyNullAmount = true;
                break;
            }
        }
        if (isAnyNullAmount) {
            extentReports.createTest("In Ticket table Amount").log(Status.FAIL, "Found a null Amount");
        } else {
            extentReports.createTest("In Ticket table Amount").log(Status.PASS, "No null Amount found");
        }
    }

    @Test(priority = 21)
    public void validateTicketStatus() {
        WebElement table = driver.findElement(By.id("ticketSearchTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Ticket table Status ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {

            WebElement StatusCell = row.findElements(By.tagName("td")).get(7);

            String Status = StatusCell.getText();

            if (Status.equalsIgnoreCase("null")) {
                isAnyNullStatus = true;
                break;
            }
        }
        if (isAnyNullStatus) {
            extentReports.createTest("In Ticket table Status").log(Status.FAIL, "Found a null Status");
        } else {
            extentReports.createTest("In Ticket table Status").log(Status.PASS, "No null Status found");
        }
    }

    @Test(priority = 22)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Ticket Number Search LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Ticket Number Search LogOut").log(Status.FAIL,
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

    public static void ticketNumberSubmit(WebDriver driver, ExtentReports extentReports) {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("submitSearch")).click();
            extentReports.createTest("Ticket Number Submit ").log(Status.PASS,
                    "Successfully Submit the number in Ticket Number search Reports Page");
        } catch (Exception e) {
            extentReports.createTest("Ticket Number Submit ").log(Status.FAIL,
                    "Failed to Submit the Ticket number in Ticket Number search Reports . Exception: "
                            + e.getCause());
        }
    }
}