package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class MobileNumberSearch extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/MobileNumberSearch.html");
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
    public void redirectMobileNumberSearchPage() {
        try {
            Thread.sleep(3000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();
            Thread.sleep(2000);
            WebElement mobileNumberSeacrhPageLink = driver.findElement(By.className("mns-pg"));
            if (mobileNumberSeacrhPageLink.isDisplayed()) {
                mobileNumberSeacrhPageLink.click();
                extentReports.createTest("Mobile Number Search Page ").log(Status.PASS,
                        "Successfully open Mobile Number search Reports Page");
            } else {
                extentReports.createTest("Mobile Number Search Page").log(Status.INFO,
                        "Mobile Number Search Page not found");
                Assert.fail("Mobile Number Search Page not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Search Page").log(Status.FAIL,
                    "Failed to open the Mobile Number Search Page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void mobileNumberEnter() {
        try {
            Thread.sleep(2000);
            WebElement mobileNumberElement = driver.findElement(By.id("mobileSearch"));
            String enterMobileNumber = "7842137994";
            mobileNumberElement.sendKeys(enterMobileNumber);

            extentReports.createTest("Mobile Number Enter ").log(Status.PASS,
                    "Successfully enter the number in Mobile Number search Reports Page : " + enterMobileNumber);
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Enter ").log(Status.FAIL,
                    "Failed to enter the mobile number in Mobile Number search Reports . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void mobileNumberSubmit() {
        TicketNumberSearch.ticketNumberSubmit(driver, extentReports);
    }

    @Test(priority = 6)
    public void mobileNumberTransactions() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("pop1-tab")));
            element.click();
            driver.findElement(By.id("pop1-tab")).click();
            extentReports.createTest("Mobile Number Transactions Tab").log(Status.PASS,
                    "Successfully clicking the Transactions table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Transactions Table").log(Status.FAIL,
                    "Failed to click the Transactions table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void mobileNumberTransactionsDates() {
        try {
            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]")).click();
            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[4]")).click();
            Thread.sleep(2000);

            extentReports.createTest("Mobile Number Transactions Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Transactions Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void selectStartDateTransactions() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]")).click();
            Thread.sleep(1000);

            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Mobile Number Transactions Start Date").log(Status.PASS,
                    "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Mobile Number Transactions Start Date").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 9)
    public void selectEndDateTransactions() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='31']"));
            dateElement.click();
            Thread.sleep(2000);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("Mobile Number Transactions End Date ").log(Status.PASS,
                    "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("Mobile Number Transactions End Date ").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 10)
    public void selectTransactionCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("mobileTab1_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            select.selectByValue("25");
            String countValue = "25";

            extentReports.createTest("select Transaction Count").log(Status.PASS,
                    "Successfully selected as : " + countValue);
        } catch (Exception e) {
            extentReports.createTest("select Transaction Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void mobileNumberTransactionsTable() {
        try {
            WebElement table = driver.findElement(By.id("mobileTab1"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In Transaction table ").log(Status.FAIL, "No data available");
                return;
            }

            String tableData = "\n";
            tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";
            tableData += "| Site Name    | Vehicle Number | Vehicle Type | Type | Created Date / Time | Start Date / Time | End Date / Time | PayU Reference | Transaction ID | Amount | Status |\n";
            tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {
                    String siteName = cells.get(0).getText();
                    String VehicleNumber = cells.get(1).getText();
                    String VehicleType = cells.get(2).getText();
                    String Type = cells.get(3).getText();
                    String CreatedDate = cells.get(4).getText();
                    String StartDate = cells.get(5).getText();
                    String EndDate = cells.get(6).getText();
                    String PayUReference = cells.get(7).getText();
                    String TransactionID = cells.get(8).getText();
                    String Amount = cells.get(9).getText();
                    String Status = cells.get(10).getText();

                    tableData += String.format(
                            "| %-40s | %-13s | %-13s | %-9s | %-12s | %-11s | %-9s | %-13s | %-14s | %-9s |\n",
                            siteName, VehicleNumber, VehicleType, Type, CreatedDate, StartDate, EndDate, PayUReference,
                            TransactionID, Amount, Status);
                }
                tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";

                Thread.sleep(3000);
            }

            extentReports.createTest("Transaction Table in Mobile Number Search Reports page").log(Status.PASS,
                    "Displaying Successfully" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Transaction Table in Mobile Number Search Reports page").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void validateTransactionSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table SiteName ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {

            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);

            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In Transaction table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In Transaction table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 13)
    public void validateTransactionVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            WebElement VehicleNumberCell = row.findElements(By.tagName("td")).get(1);
            String VehicleNumber = VehicleNumberCell.getText();

            if (VehicleNumber.equalsIgnoreCase("null")) {
                isAnyNullVehicleNumber = true;
                break;
            }
        }
        if (isAnyNullVehicleNumber) {
            extentReports.createTest("In Transaction table VehicleNumber").log(Status.FAIL,
                    "Found a null Vehicle Number");
        } else {
            extentReports.createTest("In Transaction table VehicleNumber").log(Status.PASS,
                    "No null vehicle numbers found");
        }
    }

    @Test(priority = 14)
    public void validateTransactionVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table VehicleType").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {
            WebElement VehicleTypeCell = row.findElements(By.tagName("td")).get(2);
            String VehicleType = VehicleTypeCell.getText();

            if (VehicleType.equalsIgnoreCase("null")) {
                isAnyNullVehicleType = true;
                break;
            }
        }
        if (isAnyNullVehicleType) {
            extentReports.createTest("In Transaction table VehicleType").log(Status.FAIL, "Found a null VehicleType");
        } else {
            extentReports.createTest("In Transaction table VehicleType").log(Status.PASS, "No null VehicleType found");
        }
    }

    @Test(priority = 15)
    public void validateTransactionType() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table Type ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullType = false;

        for (WebElement row : rows) {

            WebElement TypeCell = row.findElements(By.tagName("td")).get(3);

            String Type = TypeCell.getText();

            if (Type.equalsIgnoreCase("null")) {
                isAnyNullType = true;
                break;
            }
        }
        if (isAnyNullType) {
            extentReports.createTest("In Transaction table Type").log(Status.FAIL, "Found a null Type");
            Assert.fail("Test case failed because an null type was found.");
        } else {
            extentReports.createTest("In Transaction table Type").log(Status.PASS, "No null Type found");
        }
    }

    @Test(priority = 16)
    public void validateCreatedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

        String headerText = fifthTh.getText();
        if (headerText.equals("Created Date / Time")) {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Created Date / Time'");
        } else {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Created Date / Time'");
        }
    }

    @Test(priority = 17)
    public void validateTransactionCreatedDate() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In ransaction table Created Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidCreatedDate = false;

        for (WebElement row : rows) {
            WebElement createdDateCell = row.findElements(By.tagName("td")).get(4);
            String createdDate = createdDateCell.getText();

            if (createdDate.equalsIgnoreCase("Invalid date")) {
                isAnyInvalidCreatedDate = true;
                break;
            }
        }
        if (isAnyInvalidCreatedDate) {
            extentReports.createTest("In Transaction table Created Date").log(Status.FAIL, "Found a null Created Date");
            Assert.fail("Test case failed because an invalid Created Date was found.");
        } else {
            extentReports.createTest("In Transaction table Created Date").log(Status.PASS,
                    "No null Created Date found");
        }
    }

    @Test(priority = 18)
    public void validateStartDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement sixthTh = thead.findElement(By.xpath(".//tr/th[6]"));

        String headerText = sixthTh.getText();
        if (headerText.equals("Start Date / Time")) {
            extentReports.createTest("Start Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Start Date / Time'");
        } else {
            extentReports.createTest("Start Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Start Date / Time'");
        }
    }

    @Test(priority = 19)
    public void validateTransactionStartDate() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table Start Date ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidStartDate = false;

        for (WebElement row : rows) {
            WebElement startDateCell = row.findElements(By.tagName("td")).get(5);
            String startDate = startDateCell.getText();

            if (startDate.equalsIgnoreCase("Invalid date")) {
                isAnyInvalidStartDate = true;
                break;
            }
        }

        if (isAnyInvalidStartDate) {
            extentReports.createTest("In Transaction table Start Date").log(Status.FAIL, "Found a invalid Start Date");
            Assert.fail("Test case failed because an invalid Start Date was found.");
        } else {
            extentReports.createTest("In Transaction table Start Date").log(Status.PASS, "No invalid Start Date found");
        }
    }

    @Test(priority = 20)
    public void validateEndDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement seventhTh = thead.findElement(By.xpath(".//tr/th[7]"));

        String headerText = seventhTh.getText();
        if (headerText.equals("End Date / Time")) {
            extentReports.createTest("End Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'End Date / Time'");
        } else {
            extentReports.createTest("End Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'End Date / Time'");
        }
    }

    @Test(priority = 21)
    public void validateTransactionEndDate() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table End Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidEndDate = false;

        for (WebElement row : rows) {
            WebElement endDateCell = row.findElements(By.tagName("td")).get(6);
            String endDate = endDateCell.getText();

            if (endDate.equalsIgnoreCase("Invalid date")) {
                isAnyInvalidEndDate = true;
                break;
            }
        }

        if (isAnyInvalidEndDate) {
            extentReports.createTest("In Transaction table End Date").log(Status.FAIL, "Found a invalid End Date");
            Assert.fail("Test case failed because an invalid End Date was found.");
        } else {
            extentReports.createTest("In Transaction table End Date").log(Status.PASS, "No invalid End Date found");
        }
    }

    @Test(priority = 22)
    public void validateTransactionPayU_reference() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table PayU_reference ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullPayU_reference = false;

        for (WebElement row : rows) {

            WebElement PayU_referenceCell = row.findElements(By.tagName("td")).get(7);

            String PayU_reference = PayU_referenceCell.getText();

            if (PayU_reference.equalsIgnoreCase("null")) {
                isAnyNullPayU_reference = true;
                break;
            }
        }
        if (isAnyNullPayU_reference) {
            extentReports.createTest("In Transaction table PayU_reference").log(Status.FAIL,
                    "Found a null PayU_reference");
        } else {
            extentReports.createTest("In Transaction table PayU_reference").log(Status.PASS,
                    "No null PayU_reference found");
        }
    }

    @Test(priority = 23)
    public void validateTransactionId() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In  Transaction table Transaction id  ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionID = false;

        for (WebElement row : rows) {

            WebElement TransactionIDCell = row.findElements(By.tagName("td")).get(8);

            String TransactionID = TransactionIDCell.getText();

            if (TransactionID.equalsIgnoreCase("null")) {
                isAnyNullTransactionID = true;
                break;
            }
        }
        if (isAnyNullTransactionID) {
            extentReports.createTest("In Transaction table Transaction id ").log(Status.FAIL,
                    "Found a null Transaction id");
        } else {
            extentReports.createTest("In Transaction table Transaction id").log(Status.PASS,
                    "No null Transaction id found");
        }
    }

    @Test(priority = 24)
    public void validateTransactionAmount() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table Amount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullAmount = false;

        for (WebElement row : rows) {

            WebElement AmountCell = row.findElements(By.tagName("td")).get(9);

            String Amount = AmountCell.getText();

            if (Amount.equalsIgnoreCase("null")) {
                isAnyNullAmount = true;
                break;
            }
        }
        if (isAnyNullAmount) {
            extentReports.createTest("In Transaction table Amount").log(Status.FAIL, "Found a null Amount");
        } else {
            extentReports.createTest("In Transaction table Amount").log(Status.PASS, "No null Amount found");
        }
    }

    @Test(priority = 25)
    public void validateTransactionStatus() {
        WebElement table = driver.findElement(By.id("mobileTab1"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Transaction table Status ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {

            WebElement StatusCell = row.findElements(By.tagName("td")).get(10);

            String Status = StatusCell.getText();

            if (Status.equalsIgnoreCase("null")) {
                isAnyNullStatus = true;
                break;
            }
        }
        if (isAnyNullStatus) {
            extentReports.createTest("In Transaction table Status").log(Status.FAIL, "Found a null Status");
        } else {
            extentReports.createTest("In Transaction table Status").log(Status.PASS, "No null Status found");
        }
    }

    @Test(priority = 26)
    public void mobileNumberPass() {
        try {
            WebElement passTab = driver.findElement(By.id("pop2-tab"));
            passTab.click();
            Thread.sleep(2000);
            WebElement passTable = driver.findElement(By.id("pop2"));
            List<WebElement> rows = passTable.findElements(By.tagName("tr"));

            if (rows.isEmpty()) {
                extentReports.createTest("Pass Pop Table Data Retrieval").log(Status.FAIL,
                        "No data available in the Pass table");
            } else {
                extentReports.createTest("Pass Pop Table Data Retrieval").log(Status.PASS, "Data from the Pass table:");

                for (WebElement row : rows) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));

                    StringBuilder rowData = new StringBuilder();
                    for (WebElement cell : cells) {
                        rowData.append(cell.getText()).append(" | ");
                    }
                }
            }
        } catch (Exception e) {
            extentReports.createTest("Pass Pop Table Data Retrieval").log(Status.FAIL,
                    "Failed to retrieve data from the Pass table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 27)
    public void mobileNumberPassDates() {
        mobileNumberPassDates(driver, extentReports);
    }

    // @Test(priority = 28)
    public void selectStartDatePasses() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter2']/label[1]")).click();

            for (int i = 0; i < driver.findElements(By.cssSelector("ul.tabs li")).size(); i++) {

                // Click on the current tab
                driver.findElement(By.cssSelector("ul.tabs li:nth-child(" + (i + 1) + ")")).click();

                // Locate "Prev" and "Next" buttons
                WebElement prevButton = driver.findElement(By.cssSelector(".calendar-container .prev-button"));
                WebElement nextButton = driver.findElement(By.cssSelector(".calendar-container .next-button"));

                // Check if buttons are displayed and enabled
                if (prevButton.isDisplayed() && prevButton.isEnabled()) {
                    // Click "Prev" button and verify date change
                    prevButton.click();
                    // Implement your logic to verify the date change
                    Thread.sleep(1000); // Adjust sleep time based on calendar animation speed
                }

                if (nextButton.isDisplayed() && nextButton.isEnabled()) {
                    // Click "Next" button and verify date change
                    nextButton.click();
                    // Implement your logic to verify the date change
                    Thread.sleep(1000); // Adjust sleep time based on calendar animation speed
                }
            }
            extentReports.createTest("End Date in Transactions").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Transactions").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 29)
    public void selectEndDatePasses() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@class='drp-calendar right']//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@class='drp-calendar right']//th[@class='next available']")).click();

            WebElement dateElement = driver
                    .findElement(By.xpath(
                            "//div[@class='drp-calendar right']//td[@class='available' and @data-title='r4c2']"));
            dateElement.click();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);
            Thread.sleep(3000);

            extentReports.createTest("End Date in Transactions").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Transactions").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 30)
    public void selectPassCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("mobileTab2_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            select.selectByValue("25");
            String countValue = "25";
            extentReports.createTest("Passes table dropdown count ").log(Status.PASS,
                    "Successfully selected as : " + countValue);
        } catch (Exception e) {
            extentReports.createTest("Passes Table dropdown count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 31)
    public void passTable() {

        try {
            WebElement table = driver.findElement(By.id("mobileTab2"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In Passes table SiteName ").log(Status.FAIL, "No data available");
                return;
            }
            System.out.println(
                    "-----------------------------------------------------------------Passes-----------------------------------------");
            System.out.println(
                    "| Site Name    | Vehicle Number | Vehicle Type | Type | Created Date / Time |Start Date / Time |End Date / Time |PayU Reference|Transaction ID|Amount|Status| ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String siteName = cells.get(0).getText();
                    String VehicleNumber = cells.get(1).getText();
                    String VehicleType = cells.get(2).getText();
                    String Type = cells.get(3).getText();
                    String CreatedDate = cells.get(4).getText();
                    String StartDate = cells.get(5).getText();
                    String EndDate = cells.get(6).getText();
                    String PayUReference = cells.get(7).getText();
                    String TransactionID = cells.get(8).getText();
                    String Amount = cells.get(9).getText();
                    String Status = cells.get(10).getText();

                    System.out.printf(
                            "| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            siteName, VehicleNumber,
                            VehicleType, Type, CreatedDate, StartDate, EndDate, PayUReference, TransactionID, Amount,
                            Status);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Passes Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Passes Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 32)
    public void validatePassesSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table SiteName").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {

            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);

            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In Passes table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In Passes table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 33)
    public void validatePassesVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();
                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnyNullVehicleNumber = true;
                    break;
                }
            }
            if (isAnyNullVehicleNumber) {
                extentReports.createTest("In Passes table VehicleNumber").log(Status.FAIL,
                        "Found a null Vehicle Number");
            } else {
                extentReports.createTest("In Passes table VehicleNumber").log(Status.PASS,
                        "No null vehicle numbers found");
            }
        }
    }

    @Test(priority = 34)
    public void validatePassesVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table VehicleType").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() >= 3) {
                WebElement VehicleTypeCell = cells.get(2);
                String VehicleType = VehicleTypeCell.getText();
                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnyNullVehicleType = true;
                    break;
                }
            }
            if (isAnyNullVehicleType) {
                extentReports.createTest("In Passes table VehicleType").log(Status.FAIL, "Found a null VehicleType");
            } else {
                extentReports.createTest("In Passes table VehicleType").log(Status.PASS, "No null VehicleType found");
            }
        }
    }

    @Test(priority = 35)
    public void validatePassesType() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table Type").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() >= 4) {
                WebElement TypeCell = cells.get(3);
                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnyNullType = true;
                    break;
                }
            }
            if (isAnyNullType) {
                extentReports.createTest("In Passes table Type").log(Status.FAIL, "Found a null Type");
            } else {
                extentReports.createTest("In Passes table Type").log(Status.PASS, "No null Type found");
            }
        }
    }

    @Test(priority = 36)
    public void validatePassCreatedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

        String headerText = fifthTh.getText();
        if (headerText.equals("Created Date / Time")) {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Created Date / Time'");
        } else {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Created Date / Time'");
        }
    }

    @Test(priority = 37)
    public void validatePassesCreatedDate() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table Created Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidCreatedDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement createdDateCell = cells.get(4);
                String createdDate = createdDateCell.getText();

                if (createdDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidCreatedDate = true;
                    break;
                }
            }
            if (isAnyInvalidCreatedDate) {
                extentReports.createTest("In Passes table Created Date").log(Status.FAIL, "Found a null Created Date");
                Assert.fail("Test case failed because an invalid Created Date was found.");
            } else {
                extentReports.createTest("In Passes table Created Date").log(Status.PASS, "No null Created Date found");
            }
        }
    }

    @Test(priority = 38)
    public void validatePassStartDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement sixthTh = thead.findElement(By.xpath(".//tr/th[6]"));

        String headerText = sixthTh.getText();
        if (headerText.equals("Start Date / Time")) {
            extentReports.createTest("Start Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Start Date / Time'");
        } else {
            extentReports.createTest("Start Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Start Date / Time'");
        }
    }

    @Test(priority = 39)
    public void validatePassesStartDate() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table Start Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidStartDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement startDateCell = cells.get(5);
                String startDate = startDateCell.getText();

                if (startDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidStartDate = true;
                    break;
                }
            }

            if (isAnyInvalidStartDate) {
                extentReports.createTest("In Passes table Start Date").log(Status.FAIL, "Found a invalid Start Date");
                Assert.fail("Test case failed because an invalid Start Date was found.");
            } else {
                extentReports.createTest("In Passes table Start Date").log(Status.PASS, "No invalid Start Date found");
            }
        }
    }

    @Test(priority = 40)
    public void validatePassEndDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement seventhTh = thead.findElement(By.xpath(".//tr/th[7]"));

        String headerText = seventhTh.getText();
        if (headerText.equals("End Date / Time")) {
            extentReports.createTest("End Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'End Date / Time'");
        } else {
            extentReports.createTest("End Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'End Date / Time'");
        }
    }

    @Test(priority = 41)
    public void validatePassesEndDate() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table End Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidEndDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement endDateCell = cells.get(6);
                String endDate = endDateCell.getText();

                if (endDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidEndDate = true;
                    break;
                }
            }

            if (isAnyInvalidEndDate) {
                extentReports.createTest("In Passes table End Date").log(Status.FAIL, "Found a invalid End Date");
                Assert.fail("Test case failed because an invalid End Date was found.");
            } else {
                extentReports.createTest("In Passes table End Date").log(Status.PASS, "No invalid End Date found");
            }
        }
    }

    @Test(priority = 42)
    public void validatePassesPayU_reference() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table PayU_reference").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullPayU_reference = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement PayU_referenceCell = cells.get(7);
                String PayU_reference = PayU_referenceCell.getText();

                if (PayU_reference.equalsIgnoreCase("null")) {
                    isAnyNullPayU_reference = true;
                    break;
                }
            }
            if (isAnyNullPayU_reference) {
                extentReports.createTest("In Passes table PayU_reference").log(Status.FAIL,
                        "Found a null PayU_reference");
            } else {
                extentReports.createTest("In Passes table PayU_reference").log(Status.PASS,
                        "No null PayU_reference found");
            }
        }
    }

    @Test(priority = 43)
    public void validatePassesTransactionId() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table Transaction id ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionID = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement TransactionIDCell = cells.get(8);
                String TransactionID = TransactionIDCell.getText();
                if (TransactionID.equalsIgnoreCase("null")) {
                    isAnyNullTransactionID = true;
                    break;
                }
            }
            if (isAnyNullTransactionID) {
                extentReports.createTest("In Passes table Transaction id ").log(Status.FAIL,
                        "Found a null Transaction id");
            } else {
                extentReports.createTest("In Passes table Transaction id").log(Status.PASS,
                        "No null Transaction id found");
            }
        }
    }

    @Test(priority = 44)
    public void validatePassesAmount() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        boolean isAnyNullAmount = false;
        if (rows.isEmpty()) {
            extentReports.createTest("In Passes table Amount").log(Status.FAIL, "No data available");
            return;
        }

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 10) {
                WebElement AmountCell = cells.get(9);
                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("null")) {
                    isAnyNullAmount = true;
                    break;
                }
            }
            if (isAnyNullAmount) {
                extentReports.createTest("In Passes table Amount").log(Status.FAIL, "Found a null Amount");
            } else {
                extentReports.createTest("In Passes table Amount").log(Status.PASS, "No null Amount found");
            }
        }
    }

    @Test(priority = 45)
    public void validatePassesStatus() {
        WebElement table = driver.findElement(By.id("mobileTab2"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In  Passes table Status").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 11) {
                WebElement StatusCell = cells.get(10);
                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In Passes table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In Passes table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    @Test(priority = 46)
    public void mobileNumberReservation() {
        try {
            driver.findElement(By.id("pop3-tab")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number Reservations Table").log(Status.PASS,
                    "Successfully clicking the Reservations table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Reservationns Table").log(Status.FAIL,
                    "Failed to click the Reservations table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 47)
    public void mobileReservationDates() {
        mobileReservationDates(driver, extentReports);
    }

    public void selectStartDateReservations() {
        try {
            List<WebElement> elements = driver.findElements(By.id("dayRangeFilter3"));

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.xpath("//div[@id='dayRangeFilter3']/label[1]/i"));
            element.click();
            Thread.sleep(2000);
            WebElement childElement = element
                    .findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']"));
            childElement.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in Reservations").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in Reservations").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateReservations() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in Reservations").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Reservations").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 50)
    public void selectReservationsCount() {
        try {
            WebElement dropdown = driver.findElement(By.name("mobileTab3_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            select.selectByValue("25");
            String countValue = "25";
            Thread.sleep(2000);
            extentReports.createTest("Reservations table dropdown count ").log(Status.PASS,
                    "Successfully selected as : " + countValue);
        } catch (Exception e) {
            extentReports.createTest("Reservations Table dropdown count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 51)
    public void reservationsTable() {
        try {
            WebElement table = driver.findElement(By.id("mobileTab3"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In  Reservations table Status").log(Status.FAIL, "No data available");
                return;
            }
            String tableData = "\n";
            tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";
            tableData += "| Site Name    | Vehicle Number | Vehicle Type | Type | Created Date / Time | Start Date / Time | End Date / Time | PayU Reference | Transaction ID | Amount | Status |\n";
            tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {
                    String siteName = cells.get(0).getText();
                    String VehicleNumber = cells.get(1).getText();
                    String VehicleType = cells.get(2).getText();
                    String Type = cells.get(3).getText();
                    String CreatedDate = cells.get(4).getText();
                    String StartDate = cells.get(5).getText();
                    String EndDate = cells.get(6).getText();
                    String PayUReference = cells.get(7).getText();
                    String TransactionID = cells.get(8).getText();
                    String Amount = cells.get(9).getText();
                    String Status = cells.get(10).getText();

                    tableData += String.format(
                            "| %-40s | %-13s | %-13s | %-9s | %-12s | %-11s | %-9s | %-13s | %-14s | %-9s |\n",
                            siteName, VehicleNumber, VehicleType, Type, CreatedDate, StartDate, EndDate, PayUReference,
                            TransactionID, Amount, Status);
                }
                tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";

                Thread.sleep(3000);
            }

            extentReports.createTest("Reservations Table in Mobile Number Search Reports page").log(Status.PASS,
                    "Displaying Successfully" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Reservations Table in Mobile Number Search Reports page").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 52)
    public void validateReservationsSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table SiteName").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {

            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);

            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In Reservations table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In Reservations table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 53)
    public void validateReservationsVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnyNullVehicleNumber = true;
                    break;
                }
            }
            if (isAnyNullVehicleNumber) {
                extentReports.createTest("In Reservations table VehicleNumber").log(Status.FAIL,
                        "Found a null Vehicle Number");
            } else {
                extentReports.createTest("In Reservations table VehicleNumber").log(Status.PASS,
                        "No null vehicle numbers found");
            }
        }
    }

    @Test(priority = 54)
    public void validateReservationsVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table VehicleType ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement VehicleTypeCell = cells.get(2);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnyNullVehicleType = true;
                    break;
                }
            }
            if (isAnyNullVehicleType) {
                extentReports.createTest("In Reservations table VehicleType").log(Status.FAIL,
                        "Found a null VehicleType");
            } else {
                extentReports.createTest("In Reservations table VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 55)
    public void validateReservationsType() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Type").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullType = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement TypeCell = cells.get(3);
                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnyNullType = true;
                    break;
                }
            }
            if (isAnyNullType) {
                extentReports.createTest("In Reservations table Type").log(Status.FAIL, "Found a null Type");
            } else {
                extentReports.createTest("In Reservations table Type").log(Status.PASS, "No null Type found");
            }
        }
    }

    @Test(priority = 56)
    public void validateReserveCreatedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

        String headerText = fifthTh.getText();
        if (headerText.equals("Created Date / Time")) {
            extentReports.createTest("Created Date / Time Header Validation in Reservation").log(Status.PASS,
                    "Header content is 'Created Date / Time'");
        } else {
            extentReports.createTest("Created Date / Time Header Validation in Reservation").log(Status.FAIL,
                    "Header content is not 'Created Date / Time'");
        }
    }

    @Test(priority = 57)
    public void validateReservationsCreatedDate() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Created Date ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidCreatedDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement createdDateCell = cells.get(4);
                String createdDate = createdDateCell.getText();
                if (createdDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidCreatedDate = true;
                    break;
                }
            }
            if (isAnyInvalidCreatedDate) {
                extentReports.createTest("In Reservations table Created Date").log(Status.FAIL,
                        "Found a null Created Date");
                Assert.fail("Test case failed because an invalid Created Date was found.");
            } else {
                extentReports.createTest("In Reservations table Created Date").log(Status.PASS,
                        "No null Created Date found");
            }
        }
    }

    @Test(priority = 58)
    public void validateReserveStartDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement sixthTh = thead.findElement(By.xpath(".//tr/th[6]"));

        String headerText = sixthTh.getText();
        if (headerText.equals("Start Date / Time")) {
            extentReports.createTest("Start Date / Time Header Validation in Reservation").log(Status.PASS,
                    "Header content is 'Start Date / Time'");
        } else {
            extentReports.createTest("Start Date / Time Header Validation in Reservation").log(Status.FAIL,
                    "Header content is not 'Start Date / Time'");
        }
    }

    @Test(priority = 59)
    public void validateReservationsStartDate() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Start Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidStartDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement startDateCell = cells.get(5);
                String startDate = startDateCell.getText();

                if (startDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidStartDate = true;
                    break;
                }
            }

            if (isAnyInvalidStartDate) {
                extentReports.createTest("In Reservations table Start Date").log(Status.FAIL,
                        "Found a invalid Start Date");
                Assert.fail("Test case failed because an invalid Start Date was found.");
            } else {
                extentReports.createTest("In Reservations table Start Date").log(Status.PASS,
                        "No invalid Start Date found");
            }
        }
    }

    @Test(priority = 60)
    public void validateReserveEndDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement seventhTh = thead.findElement(By.xpath(".//tr/th[7]"));

        String headerText = seventhTh.getText();
        if (headerText.equals("End Date / Time")) {
            extentReports.createTest("End Date / Time Header Validation in Reservation").log(Status.PASS,
                    "Header content is 'End Date / Time'");
        } else {
            extentReports.createTest("End Date / Time Header Validation in Reservation").log(Status.FAIL,
                    "Header content is not 'End Date / Time'");
        }
    }

    @Test(priority = 61)
    public void validateReservationsEndDate() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table End Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidEndDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement endDateCell = cells.get(6);
                String endDate = endDateCell.getText();

                if (endDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidEndDate = true;
                    break;
                }
            }

            if (isAnyInvalidEndDate) {
                extentReports.createTest("In Reservations table End Date").log(Status.FAIL, "Found a invalid End Date");
                Assert.fail("Test case failed because an invalid End Date was found.");
            } else {
                extentReports.createTest("In Reservations table End Date").log(Status.PASS,
                        "No invalid End Date found");
            }
        }
    }

    @Test(priority = 62)
    public void validateReservationsPayU_reference() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table PayU_reference").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullPayU_reference = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement PayU_referenceCell = cells.get(7);
                String PayU_reference = PayU_referenceCell.getText();

                if (PayU_reference.equalsIgnoreCase("null")) {
                    isAnyNullPayU_reference = true;
                    break;
                }
            }
            if (isAnyNullPayU_reference) {
                extentReports.createTest("In Reservations table PayU_reference").log(Status.FAIL,
                        "Found a null PayU_reference");
            } else {
                extentReports.createTest("In Reservations table PayU_reference").log(Status.PASS,
                        "No null PayU_reference found");
            }
        }
    }

    @Test(priority = 63)
    public void validateReservationsTransactionId() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table TransactionID").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionID = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement TransactionIDCell = cells.get(8);
                String TransactionID = TransactionIDCell.getText();

                if (TransactionID.equalsIgnoreCase("null")) {
                    isAnyNullTransactionID = true;
                    break;
                }
            }
            if (isAnyNullTransactionID) {
                extentReports.createTest("In Reservations table Transaction id ").log(Status.FAIL,
                        "Found a null Transaction id");
            } else {
                extentReports.createTest("In Reservations table Transaction id").log(Status.PASS,
                        "No null Transaction id found");
            }
        }
    }

    @Test(priority = 64)
    public void validateReservationsAmount() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        boolean isAnyNullAmount = false;
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Amount").log(Status.FAIL, "No data available");
            return;
        }

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 10) {
                WebElement AmountCell = cells.get(9);
                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("null")) {
                    isAnyNullAmount = true;
                    break;
                }
            }
            if (isAnyNullAmount) {
                extentReports.createTest("In Reservations table Amount").log(Status.FAIL, "Found a null Amount");
            } else {
                extentReports.createTest("In Reservations table Amount").log(Status.PASS, "No null Amount found");
            }
        }
    }

    @Test(priority = 65)
    public void validateReservationsStatus() {
        WebElement table = driver.findElement(By.id("mobileTab3"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Status").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 11) {
                WebElement StatusCell = cells.get(10);
                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In Reservations table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In Reservations table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    @Test(priority = 66)
    public void mobileNumberCheckInOut() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.id("pop4-tab")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number CheckIn/Out Table").log(Status.PASS,
                    "Successfully clicking the CheckIn/Out table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number CheckIn/Out Table").log(Status.FAIL,
                    "Failed to click the CheckIn/Out table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 67)
    public void mobileNumberCheckInOutDates() {
        mobileNumberCheckInOutDates(driver, extentReports);
    }

    public void selectStartDateCheckInOut() {
        try {
            List<WebElement> elements = driver.findElements(By.id("dayRangeFilter4"));

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.xpath("//div[@id='dayRangeFilter4']/label[1]/i"));
            element.click();
            Thread.sleep(2000);
            WebElement childElement = element
                    .findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']"));
            childElement.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in CheckInOut").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in CheckInOut").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateCheckInOut() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in CheckInOut").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in CheckInOut").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 70)
    public void selectCountCheckInOut() {
        try {
            WebElement dropdown = driver.findElement(By.name("mobileTab4_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            select.selectByValue("25");
            String countValue = "25";
            extentReports.createTest("CheckInOut table dropdown count ").log(Status.PASS,
                    "Successfully selected as :" + countValue);
        } catch (Exception e) {
            extentReports.createTest("CheckInOut Table dropdown count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 71)
    public void checkInOutTable() {
        try {
            WebElement table = driver.findElement(By.id("mobileTab4"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In CheckIn/Out table").log(Status.FAIL, "No data available");
                return;
            }

            System.out.println(
                    "-----------------------------------------------------------------CheckIn/Out-----------------------------------------");
            System.out.println(
                    "| Site Name    | Vehicle Type | Vehicle Number | CheckIn | CheckOut |Type |PayU Reference|Transaction ID| Amount| Due Amount|Status| ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String siteName = cells.get(0).getText();
                    String VehicleType = cells.get(1).getText();
                    String VehicleNumber = cells.get(2).getText();
                    String CheckIn = cells.get(3).getText();
                    String CheckOut = cells.get(4).getText();
                    String Type = cells.get(5).getText();
                    String PayUReference = cells.get(6).getText();
                    String TransactionID = cells.get(7).getText();
                    String Amount = cells.get(8).getText();
                    String DueAmount = cells.get(9).getText();
                    String Status = cells.get(10).getText();

                    System.out.printf(
                            "| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s|%-10s|%-10s |%-10s| %n",
                            siteName, VehicleType,
                            VehicleNumber, CheckIn, CheckOut, Type, PayUReference,
                            TransactionID, Amount, DueAmount,
                            Status);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("CheckInOut Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("CheckInOut Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 72)
    public void validateCheckInOutSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table SiteName ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {

            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);

            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In CheckInOut table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In CheckInOut table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 73)
    public void validateCheckInOutVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table VehicleType ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleTypeCell = cells.get(1);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnyNullVehicleType = true;
                    break;
                }
            }
            if (isAnyNullVehicleType) {
                extentReports.createTest("In CheckInOut table Vehicle Type").log(Status.FAIL,
                        "Found a null Vehicle Type");
            } else {
                extentReports.createTest("In CheckInOut table Vehicle Type").log(Status.PASS,
                        "No null Vehicle Type found");
            }
        }
    }

    @Test(priority = 74)
    public void validateCheckInOutVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement VehicleNumberCell = cells.get(2);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnyNullVehicleNumber = true;
                    break;
                }
            }
            if (isAnyNullVehicleNumber) {
                extentReports.createTest("In CheckInOut table VehicleNumber").log(Status.FAIL,
                        "Found a null VehicleNumber");
            } else {
                extentReports.createTest("In CheckInOut table VehicleNumber").log(Status.PASS,
                        "No null VehicleNumber found");
            }
        }
    }

    @Test(priority = 75)
    public void validateCheckInDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fourthTh = thead.findElement(By.xpath(".//tr/th[4]"));

        String headerText = fourthTh.getText();
        if (headerText.equals("CheckIn Date / Time")) {
            extentReports.createTest("CheckIn Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'CheckIn Date / Time'");
        } else {
            extentReports.createTest("CheckIn Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'CheckIn Date / Time'");
        }
    }

    @Test(priority = 76)
    public void validateCheckInRow() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table CheckIn").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCheckIn = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement CheckInCell = cells.get(3);
                String CheckIn = CheckInCell.getText();

                if (CheckIn.equalsIgnoreCase("null")) {
                    isAnyNullCheckIn = true;
                    break;
                }
            }
            if (isAnyNullCheckIn) {
                extentReports.createTest("In CheckInOut table CheckIn Row").log(Status.FAIL,
                        "Found a null CheckIn row");
            } else {
                extentReports.createTest("In CheckInOut table CheckIn Row").log(Status.PASS,
                        "No null CheckIn row found");
            }
        }
    }

    @Test(priority = 77)
    public void validateCheckOutDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

        String headerText = fifthTh.getText();
        if (headerText.equals("CheckOut Date / Time")) {
            extentReports.createTest("CheckOut Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'CheckOut Date / Time'");
        } else {
            extentReports.createTest("CheckOut Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'CheckOut Date / Time'");
        }
    }

    @Test(priority = 78)
    public void validateCheckOutRow() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table CheckOut ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCheckOut = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement CheckOutCell = cells.get(4);
                String CheckOut = CheckOutCell.getText();

                if (CheckOut.equalsIgnoreCase("null")) {
                    isAnyNullCheckOut = true;
                    break;
                }
            }
            if (isAnyNullCheckOut) {
                extentReports.createTest("In CheckInOut table CheckOut Row").log(Status.FAIL,
                        "Found a null CheckOut row");
            } else {
                extentReports.createTest("In CheckInOut table CheckOut Row").log(Status.PASS,
                        "No null CheckOut row found");
            }
        }
    }

    @Test(priority = 79)
    public void validateCheckInOutType() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table Type").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement TypeCell = cells.get(5);
                String Type = TypeCell.getText();

                if (Type.equalsIgnoreCase("null")) {
                    isAnyNullType = true;
                    break;
                }
            }
            if (isAnyNullType) {
                extentReports.createTest("In CheckInOut table Type").log(Status.FAIL, "Found a null Type");
            } else {
                extentReports.createTest("In CheckInOut table Type").log(Status.PASS, "No null Type found");
            }
        }
    }

    @Test(priority = 80)
    public void validateCheckInOutPayU_reference() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table PayU_reference").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullPayU_reference = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement PayU_referenceCell = cells.get(6);
                String PayU_reference = PayU_referenceCell.getText();

                if (PayU_reference.equalsIgnoreCase("null")) {
                    isAnyNullPayU_reference = true;
                    break;
                }
            }
            if (isAnyNullPayU_reference) {
                extentReports.createTest("In CheckInOut table PayU_reference").log(Status.FAIL,
                        "Found a null PayU_reference");
            } else {
                extentReports.createTest("In CheckInOut table PayU_reference").log(Status.PASS,
                        "No null PayU_reference found");
            }
        }
    }

    @Test(priority = 81)
    public void validateCheckInOutTransactionId() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table TransactionID").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionID = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement TransactionIDCell = cells.get(7);
                String TransactionID = TransactionIDCell.getText();

                if (TransactionID.equalsIgnoreCase("null")) {
                    isAnyNullTransactionID = true;
                    break;
                }
            }
            if (isAnyNullTransactionID) {
                extentReports.createTest("In CheckInOut table Transaction id ").log(Status.FAIL,
                        "Found a null Transaction id");
            } else {
                extentReports.createTest("In CheckInOut table Transaction id").log(Status.PASS,
                        "No null Transaction id found");
            }
        }
    }

    @Test(priority = 82)
    public void validateCheckInOutAmount() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        boolean isAnyNullAmount = false;
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table Amount").log(Status.FAIL, "No data available");
            return;
        }

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 9) {
                WebElement AmountCell = cells.get(8);
                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("null")) {
                    isAnyNullAmount = true;
                    break;
                }
            }
            if (isAnyNullAmount) {
                extentReports.createTest("In CheckInOut table Amount").log(Status.FAIL, "Found a null Amount");
            } else {
                extentReports.createTest("In CheckInOut table Amount").log(Status.PASS, "No null Amount found");
            }
        }
    }

    @Test(priority = 83)
    public void validateCheckInOutDueAmount() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        boolean isAnyNullDueAmount = false;
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table DueAmount").log(Status.FAIL, "No data available");
            return;
        }

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 10) {
                WebElement DueAmountCell = cells.get(9);
                String DueAmount = DueAmountCell.getText();

                if (DueAmount.equalsIgnoreCase("null")) {
                    isAnyNullDueAmount = true;
                    break;
                }
            }
            if (isAnyNullDueAmount) {
                extentReports.createTest("In CheckInOut table Due Amount").log(Status.FAIL, "Found a null Due Amount");
            } else {
                extentReports.createTest("In CheckInOut table Due Amount").log(Status.PASS, "No null Due Amount found");
            }
        }
    }

    @Test(priority = 84)
    public void validateCheckInOutStatus() {
        WebElement table = driver.findElement(By.id("mobileTab4"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In CheckInOut table Status").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 11) {
                WebElement StatusCell = cells.get(10);
                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In CheckInOut table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In CheckInOut table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    @Test(priority = 85)
    public void mobileNumberDueAmount() {
        try {
            driver.findElement(By.id("pop5-tab")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number DueAmount Table").log(Status.PASS,
                    "Successfully clicking the DueAmount table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number DueAmount Table").log(Status.FAIL,
                    "Failed to click the DueAmount table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 86)
    public void mobileNumberDueAmountDates() {
        mobileNumberDueAmountDates(driver, extentReports);
    }

    public void selectStartDateDueAmount() {
        try {
            List<WebElement> elements = driver.findElements(By.id("dayRangeFilter5"));

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.xpath("//div[@id='dayRangeFilter5']/label[1]/i"));
            element.click();
            Thread.sleep(2000);
            WebElement childElement = element
                    .findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']"));
            childElement.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in DueAmount").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in DueAmount").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateDueAmount() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in DueAmount").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in DueAmount").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 89)
    public void selectDueAmountCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("mobileTab5_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            String countValue = "25";
            select.selectByValue(countValue);
            extentReports.createTest("DueAmount table dropdown count ").log(Status.PASS,
                    "Successfully selected as : " + countValue);
        } catch (Exception e) {
            extentReports.createTest("DueAmount Table dropdown count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 90)
    public void dueAmountTable() {

        try {
            WebElement table = driver.findElement(By.id("mobileTab5"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In  DueAmount table Status").log(Status.FAIL, "No data available");
                return;
            }
            System.out.println(
                    "-----------------------------------------------------------------DueAmount-----------------------------------------");
            System.out.println(
                    "| Site Name    | Vehicle Number | Vehicle Type | PayU Reference | Created Date/Time |Transaction ID|Due Amount|Status| ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 8) {

                    String siteName = cells.get(0).getText();
                    String VehicleNumber = cells.get(1).getText();
                    String VehicleType = cells.get(2).getText();
                    String PayUReference = cells.get(3).getText();
                    String CreatedDate = cells.get(4).getText();
                    String TransactionID = cells.get(5).getText();
                    String DueAmount = cells.get(6).getText();
                    String Status = cells.get(7).getText();

                    System.out.printf(
                            "| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            siteName, VehicleNumber,
                            VehicleType, CreatedDate, PayUReference, TransactionID, DueAmount,
                            Status);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("DueAmount Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("DueAmount Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 91)
    public void validateDueAmountSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In DueAmount table SiteName").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {

            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);

            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In DueAmount table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In DueAmount table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 92)
    public void validateDueAmountVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In DueAmount table VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnyNullVehicleNumber = true;
                    break;
                }
            }
            if (isAnyNullVehicleNumber) {
                extentReports.createTest("In DueAmount table VehicleNumber").log(Status.FAIL,
                        "Found a null Vehicle Number");
            } else {
                extentReports.createTest("In DueAmount table VehicleNumber").log(Status.PASS,
                        "No null vehicle numbers found");
            }
        }
    }

    @Test(priority = 93)
    public void validateDueAmountVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In DueAmount table VehicleType ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement VehicleTypeCell = cells.get(2);
                String VehicleType = VehicleTypeCell.getText();

                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnyNullVehicleType = true;
                    break;
                }
            }
            if (isAnyNullVehicleType) {
                extentReports.createTest("In DueAmount table VehicleType").log(Status.FAIL, "Found a null VehicleType");
            } else {
                extentReports.createTest("In DueAmount table VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 94)
    public void validateRDueAmountPayU_reference() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In DueAmount table PayU_reference").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullPayU_reference = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement PayU_referenceCell = cells.get(3);
                String PayU_reference = PayU_referenceCell.getText();

                if (PayU_reference.equalsIgnoreCase("null")) {
                    isAnyNullPayU_reference = true;
                    break;
                }
            }
            if (isAnyNullPayU_reference) {
                extentReports.createTest("In DueAmount table PayU_reference").log(Status.FAIL,
                        "Found a null PayU_reference");
            } else {
                extentReports.createTest("In DueAmount table PayU_reference").log(Status.PASS,
                        "No null PayU_reference found");
            }
        }
    }

    @Test(priority = 95)
    public void validateDueAmountCreatedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

        String headerText = fifthTh.getText();
        if (headerText.equals("Created Date / Time")) {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Created Date / Time'");
        } else {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Created Date / Time'");
        }
    }

    @Test(priority = 96)
    public void validateDueAmountCreatedDate() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In DueAmount table Created Date ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidCreatedDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement createdDateCell = cells.get(4);
                String createdDate = createdDateCell.getText();

                if (createdDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidCreatedDate = true;
                    break;
                }
            }
            if (isAnyInvalidCreatedDate) {
                extentReports.createTest("In DueAmount table Created Date").log(Status.FAIL,
                        "Found a null Created Date");
                Assert.fail("Test case failed because an invalid Created Date was found.");
            } else {
                extentReports.createTest("In DueAmount table Created Date").log(Status.PASS,
                        "No null Created Date found");
            }
        }
    }

    @Test(priority = 97)
    public void validateDueAmountsTransactionId() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In DueAmount table TransactionID").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullTransactionID = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement TransactionIDCell = cells.get(5);
                String TransactionID = TransactionIDCell.getText();
                if (TransactionID.equalsIgnoreCase("null")) {
                    isAnyNullTransactionID = true;
                    break;
                }
            }
            if (isAnyNullTransactionID) {
                extentReports.createTest("In DueAmount table Transaction id ").log(Status.FAIL,
                        "Found a null Transaction id");
            } else {
                extentReports.createTest("In DueAmount table Transaction id").log(Status.PASS,
                        "No null Transaction id found");
            }
        }
    }

    @Test(priority = 98)
    public void validateDueAmountAmount() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        boolean isAnyNullAmount = false;
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Amount").log(Status.FAIL, "No data available");
            return;
        }

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement AmountCell = cells.get(6);
                String Amount = AmountCell.getText();
                if (Amount.equalsIgnoreCase("null")) {
                    isAnyNullAmount = true;
                    break;
                }
            }
            if (isAnyNullAmount) {
                extentReports.createTest("In DueAmount table Amount").log(Status.FAIL, "Found a null Amount");
            } else {
                extentReports.createTest("In DueAmount table Amount").log(Status.PASS, "No null Amount found");
            }
        }
    }

    @Test(priority = 99)
    public void validateDueAmountStatus() {
        WebElement table = driver.findElement(By.id("mobileTab5"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Status").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 8) {
                WebElement StatusCell = cells.get(7);
                String Status = StatusCell.getText();
                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In DueAmount table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In DueAmount table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    @Test(priority = 100)
    public void mobileNumberFeedback() {
        try {
            driver.findElement(By.id("pop6-tab")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number Feedback Table").log(Status.PASS,
                    "Successfully clicking the Feedback table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Feedback Table").log(Status.FAIL,
                    "Failed to click the Feedback table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 101)
    public void mobileNumberFeedbackDates() {
        mobileNumberFeedbackDates(driver, extentReports);
    }

    public void selectStartDateFeedback() {
        try {
            List<WebElement> elements = driver.findElements(By.id("dayRangeFilter6"));

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.xpath("//div[@id='dayRangeFilter6']/label[1]/i"));
            element.click();
            Thread.sleep(2000);
            WebElement childElement = element
                    .findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']"));
            childElement.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in Feedback").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in Feedback").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateFeedback() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in Feedback").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Feedback").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 104)
    public void selectPassFeedback() {
        try {
            WebElement dropdown = driver.findElement(By.name("mobileTab6_length"));
            Select select = new Select(dropdown);
            String countValue = "25";
            select.selectByValue(countValue);
            Thread.sleep(2000);
            extentReports.createTest("Feedback table dropdown count ").log(Status.PASS,
                    "Successfully selected as :" + countValue);
        } catch (Exception e) {
            extentReports.createTest("Feedback Table dropdown count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 105)
    public void FeedbackTable() {

        try {
            WebElement table = driver.findElement(By.id("mobileTab6"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In CheckIn/Out table").log(Status.FAIL, "No data available");
                return;
            }

            System.out.println(
                    "-----------------------------------------------------------------Feedback-----------------------------------------");
            System.out.println(
                    "| Site Name    | Category | Feedback Id | Feedback Type | Created Date/Time | Status | Description | ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 11) {

                    String siteName = cells.get(0).getText();
                    String Category = cells.get(1).getText();
                    String FeedbackId = cells.get(2).getText();
                    String FeedbackType = cells.get(3).getText();
                    String CreatedDate = cells.get(4).getText();
                    String Status = cells.get(5).getText();
                    String Description = cells.get(6).getText();

                    System.out.printf(
                            "| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%-15s | %n",
                            siteName, Category,
                            FeedbackId, FeedbackType, CreatedDate, Status, Description);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("Feedback Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("Feedback Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 106)
    public void validateFeedbackSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table SiteName ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {
            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);
            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In Feedback table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In Feedback table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 107)
    public void validateFeedbackCategory() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table Category ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCategory = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                WebElement CategoryCell = cells.get(1);
                String Category = CategoryCell.getText();

                if (Category.equalsIgnoreCase("null")) {
                    isAnyNullCategory = true;
                    break;
                }
            }
            if (isAnyNullCategory) {
                extentReports.createTest("In Feedback table Category").log(Status.FAIL, "Found a null Category");
            } else {
                extentReports.createTest("In Feedback table Category").log(Status.PASS, "No null Category found");
            }
        }
    }

    @Test(priority = 108)
    public void validateFeedbackId() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table FeedbackId ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullFeedbackId = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement FeedbackIdCell = cells.get(2);
                String FeedbackId = FeedbackIdCell.getText();

                if (FeedbackId.equalsIgnoreCase("null")) {
                    isAnyNullFeedbackId = true;
                    break;
                }
            }
            if (isAnyNullFeedbackId) {
                extentReports.createTest("In Feedback table Feedback Id").log(Status.FAIL, "Found a null Feedback Id");
            } else {
                extentReports.createTest("In Feedback table Feedback Id").log(Status.PASS, "No null Feedback Id found");
            }
        }
    }

    @Test(priority = 109)
    public void validateFeedbackType() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table FeedbackType").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullFeedbackType = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() >= 4) {
                WebElement FeedbackTypeCell = cells.get(3);
                String FeedbackType = FeedbackTypeCell.getText();

                if (FeedbackType.equalsIgnoreCase("null")) {
                    isAnyNullFeedbackType = true;
                    break;
                }
            }
            if (isAnyNullFeedbackType) {
                extentReports.createTest("In Feedback table FeedbackType").log(Status.FAIL,
                        "Found a null FeedbackType");
            } else {
                extentReports.createTest("In Feedback table FeedbackType").log(Status.PASS,
                        "No null FeedbackType found");
            }
        }
    }

    @Test(priority = 110)
    public void validateFeedBackCreatedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

        String headerText = fifthTh.getText();
        if (headerText.equals("Created Date / Time")) {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Created Date / Time'");
        } else {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Created Date / Time'");
        }
    }

    @Test(priority = 111)
    public void validateFeedbackCreatedDate() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table CreatedDate").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullCreatedDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 5) {
                String CreatedDate = cells.get(4).getText();

                if (CreatedDate.equalsIgnoreCase("null")) {
                    isAnyNullCreatedDate = true;
                    break;
                }
            } else {
                System.out.println("Row does not have enough cells");
            }
        }

        if (isAnyNullCreatedDate) {
            extentReports.createTest("In Feedback table").log(Status.FAIL, "Found a null in CreatedDate");
        } else {
            extentReports.createTest("In Feedback table").log(Status.PASS, "No null CreatedDate found");
        }
    }

    @Test(priority = 112)
    public void validateFeedbackStatus() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table Status").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement StatusCell = cells.get(5);
                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In Feedback table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In Feedback table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    @Test(priority = 113)
    public void validateFeedbackDescription() {
        WebElement table = driver.findElement(By.id("mobileTab6"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Feedback table Description").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullDescription = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() >= 7) {
                WebElement DescriptionCell = cells.get(6);
                String Description = DescriptionCell.getText();

                if (Description.equalsIgnoreCase("null")) {
                    isAnyNullDescription = true;
                    break;
                }
            }
            if (isAnyNullDescription) {
                extentReports.createTest("In Feedback table Description").log(Status.FAIL, "Found a null Description");
            } else {
                extentReports.createTest("In Feedback table Description").log(Status.PASS, "No null Description found");
            }
        }
    }

    @Test(priority = 114)
    public void mobileNumberBlockListClick() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pop7-tab")).click();
            extentReports.createTest("Mobile Number BlockList Click ").log(Status.PASS,
                    "Successfully clicking the blocklist ");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number BlockList Click ").log(Status.FAIL,
                    "Failed to click the blocklist. Exception: " + e.getCause());
        }
    }

    @Test(priority = 115)
    public void mobileNumberBlockListDates() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter7']/label[2]")).click();
            driver.findElement(By.xpath("//*[@id='dayRangeFilter7']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter7']/label[4]")).click();
            Thread.sleep(2000);

            extentReports.createTest("Mobile Number BlockList Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number BlockList Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    public void selectStartDateBlockList() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter7']/label[1]/i")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in BlockList").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in BlockList").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateBlockList() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in BlockList").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in BlockList").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 118)
    public void selectBlockListCount() {
        try {
            WebElement dropdown = driver.findElement(By.name("mobileTab7_length"));
            Select select = new Select(dropdown);
            String countValue = "25";
            select.selectByValue(countValue);
            Thread.sleep(2000);
            extentReports.createTest("select BlockList Count").log(Status.PASS,
                    "Successfully selected as : " + countValue);
        } catch (Exception e) {
            extentReports.createTest("select BlockList Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 119)
    public void BlockListTable() {
        try {
            WebElement table = driver.findElement(By.id("mobileTab7"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In BlockList table ").log(Status.FAIL, "No data available");
                return;
            }

            System.out.println(
                    "-------------------------------------------------------------BlockList----------------------------------------------------------------------");
            System.out.println(
                    "| Site Name    | Vehicle Number | Vehicle Type |  Created Date /Time | Amount | Status | ");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 6) {

                    String siteName = cells.get(0).getText();
                    String VehicleNumber = cells.get(1).getText();
                    String VehicleType = cells.get(2).getText();
                    String CreatedDate = cells.get(3).getText();
                    String Amount = cells.get(4).getText();
                    String Status = cells.get(5).getText();

                    System.out.printf("| %-40s | %-10s | %-10s | %-10s |%-10s |%-10s | %n",
                            siteName, VehicleNumber,
                            VehicleType, CreatedDate, Amount,
                            Status);
                }
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("BlockList Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("BlockList Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 120)
    public void validateBlockListSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In BlockList table SiteName ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullSiteName = false;

        for (WebElement row : rows) {

            WebElement SiteNameCell = row.findElements(By.tagName("td")).get(0);

            String SiteName = SiteNameCell.getText();

            if (SiteName.equalsIgnoreCase("null")) {
                isAnyNullSiteName = true;
                break;
            }
        }
        if (isAnyNullSiteName) {
            extentReports.createTest("In Transaction table SiteName").log(Status.FAIL, "Found a null SiteName");
        } else {
            extentReports.createTest("In Transaction table SiteName").log(Status.PASS, "No null SiteName found");
        }
    }

    @Test(priority = 121)
    public void validateBlockListVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In BlockList table VehicleNumber").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleNumber = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() >= 2) {
                WebElement VehicleNumberCell = cells.get(1);
                String VehicleNumber = VehicleNumberCell.getText();

                if (VehicleNumber.equalsIgnoreCase("null")) {
                    isAnyNullVehicleNumber = true;
                    break;
                }
            }
            if (isAnyNullVehicleNumber) {
                extentReports.createTest("In BlockList table VehicleNumber").log(Status.FAIL,
                        "Found a null Vehicle Number");
            } else {
                extentReports.createTest("In BlockList table VehicleNumber").log(Status.PASS,
                        "No null vehicle numbers found");
            }
        }
    }

    @Test(priority = 122)
    public void validateBlockListVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In BlockList table VehicleType").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullVehicleType = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                WebElement VehicleTypeCell = cells.get(2);
                String VehicleType = VehicleTypeCell.getText();
                if (VehicleType.equalsIgnoreCase("null")) {
                    isAnyNullVehicleType = true;
                    break;
                }
            }
            if (isAnyNullVehicleType) {
                extentReports.createTest("In BlockList table VehicleType").log(Status.FAIL, "Found a null VehicleType");
            } else {
                extentReports.createTest("In BlockList table VehicleType").log(Status.PASS,
                        "No null VehicleType found");
            }
        }
    }

    @Test(priority = 123)
    public void validateBlockListCreatedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement fourthTh = thead.findElement(By.xpath(".//tr/th[4]"));

        String headerText = fourthTh.getText();
        if (headerText.equals("Created Date / Time")) {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.PASS,
                    "Header content is 'Created Date / Time'");
        } else {
            extentReports.createTest("Created Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not 'Created Date / Time'");
        }
    }

    @Test(priority = 124)
    public void validateBlockListnCreatedDate() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In BlockList table Created Date").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidCreatedDate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                WebElement createdDateCell = cells.get(3);
                String createdDate = createdDateCell.getText();

                if (createdDate.equalsIgnoreCase("Invalid date")) {
                    isAnyInvalidCreatedDate = true;
                    break;
                }
            }
            if (isAnyInvalidCreatedDate) {
                extentReports.createTest("In BlockList table Created Date").log(Status.FAIL,
                        "Found a null Created Date");
                Assert.fail("Test case failed because an invalid Created Date was found.");
            } else {
                extentReports.createTest("In BlockList table Created Date").log(Status.PASS,
                        "No null Created Date found");
            }
        }
    }

    @Test(priority = 125)
    public void validateBlockListAmount() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In BlockList table Amount ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullAmount = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 5) {
                WebElement AmountCell = cells.get(4);
                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("null")) {
                    isAnyNullAmount = true;
                    break;
                }
            }
            if (isAnyNullAmount) {
                extentReports.createTest("In BlockList table Amount").log(Status.FAIL, "Found a null Amount");
            } else {
                extentReports.createTest("In BlockList table Amount").log(Status.PASS, "No null Amount found");
            }
        }
    }

    @Test(priority = 126)
    public void validateBlockListStatus() {
        WebElement table = driver.findElement(By.id("mobileTab7"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In BlockList table Status ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {

            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                WebElement StatusCell = cells.get(5);
                String Status = StatusCell.getText();
                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In BlockList table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In BlockList table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    @Test(priority = 127)
    public void mobileNumberUnAccountedClick() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pop9-tab")).click();
            extentReports.createTest("Mobile Number UnAccounted Click ").log(Status.PASS,
                    "Successfully clicking the UnAccounted ");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number UnAccounted Click ").log(Status.FAIL,
                    "Failed to click the UnAccounted. Exception: " + e.getCause());
        }
    }

    @Test(priority = 128)
    public void mobileNumberUnAccountedDates() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter9']/label[2]")).click();
            driver.findElement(By.xpath("//*[@id='dayRangeFilter9']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter9']/label[4]")).click();

            extentReports.createTest("Mobile Number UnAccounted Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number UnAccounted Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    public void selectStartDateUnAccounted() {
        try {
            List<WebElement> elements = driver.findElements(By.id("dayRangeFilter9"));

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.xpath("//div[@id='dayRangeFilter9']/label[1]/i"));
            element.click();
            Thread.sleep(2000);
            WebElement childElement = element
                    .findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']"));
            childElement.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@class='calendar-table']//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in UnAccounted").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in UnAccounted").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    public void selectEndDateUnAccounted() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in UnAccounted").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in UnAccounted").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 131)
    public void selectUnAccountedCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("mobileTab9_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            select.selectByValue("25");
            extentReports.createTest("UnAccounted table dropdown count ").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("UnAccounted Table dropdown count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 132)
    public void unAccountedTable() {

        try {
            WebElement table = driver.findElement(By.id("mobileTab9"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In  UnAccounted table Status").log(Status.FAIL, "No data available");
                return;
            }
            System.out.println(
                    "-----------------------------------------------------------------UnAccounted-----------------------------------------");
            System.out.println(
                    "| Ticket No   | Mobile Number | date / time | payuId | Bank ref No |Amount|Mode|Status| ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 8) {

                    String TicketNo = cells.get(0).getText();
                    String MobileNumber = cells.get(1).getText();
                    String date = cells.get(2).getText();
                    String payuId = cells.get(3).getText();
                    String BankrefNo = cells.get(4).getText();
                    String Amount = cells.get(5).getText();
                    String Mode = cells.get(6).getText();
                    String Status = cells.get(7).getText();

                    System.out.printf(
                            "| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%n",
                            TicketNo, MobileNumber,
                            date, payuId, BankrefNo, Amount, Mode,
                            Status);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("UnAccounted Table ").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest("UnAccounted Table").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 133)
    public void validateUnAccountedSiteName() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table TicketNo").log(Status.FAIL, "No data available");
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
            extentReports.createTest("In UnAccounted table TicketNo").log(Status.FAIL, "Found a null TicketNo");
        } else {
            extentReports.createTest("In UnAccounted table TicketNo").log(Status.PASS, "No null TicketNo found");
        }
    }

    @Test(priority = 134)
    public void validateUnAccountedVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table Mobile Number").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullMobileNumber = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 7) {
                WebElement MobileNumberCell = cells.get(1);
                String MobileNumber = MobileNumberCell.getText();

                if (MobileNumber.equalsIgnoreCase("null")) {
                    isAnyNullMobileNumber = true;
                    break;
                }
            }
            if (isAnyNullMobileNumber) {
                extentReports.createTest("In UnAccounted table MobileNumber").log(Status.FAIL,
                        "Found a null Vehicle Number");
            } else {
                extentReports.createTest("In UnAccounted table MobileNumber").log(Status.PASS,
                        "No null vehicle numbers found");
            }
        }
    }

    @Test(priority = 135)
    public void validateUnAccountedDateTimeHeader() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement third = thead.findElement(By.xpath(".//tr/th[3]"));

        String headerText = third.getText();
        if (headerText.equals(" Date / Time")) {
            extentReports.createTest(" Date / Time Header Validation").log(Status.PASS,
                    "Header content is ' Date / Time'");
        } else {
            extentReports.createTest(" Date / Time Header Validation").log(Status.FAIL,
                    "Header content is not ' Date / Time'");
        }
    }

    @Test(priority = 136)
    public void validateUnAccountedVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table date ").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNulldate = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 7) {
                WebElement dateCell = cells.get(2);
                String date = dateCell.getText();

                if (date.equalsIgnoreCase("null")) {
                    isAnyNulldate = true;
                    break;
                }
            }
            if (isAnyNulldate) {
                extentReports.createTest("In UnAccounted table date").log(Status.FAIL, "Found a null date");
            } else {
                extentReports.createTest("In UnAccounted table date").log(Status.PASS, "No null date found");
            }
        }
    }

    @Test(priority = 137)
    public void validateUnAccountedType() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table payuId").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullpayuId = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > 7) {
                WebElement payuIdCell = cells.get(3);
                String payuId = payuIdCell.getText();

                if (payuId.equalsIgnoreCase("null")) {
                    isAnyNullpayuId = true;
                    break;
                }
            }
            if (isAnyNullpayuId) {
                extentReports.createTest("In UnAccounted table payuId").log(Status.FAIL, "Found a null payuId");
            } else {
                extentReports.createTest("In UnAccounted table payuId").log(Status.PASS, "No null payuId found");
            }
        }
    }

    @Test(priority = 138)
    public void validateUnAccountedBankrefNo() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table Bank ref No").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullBankrefNo = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 7) {
                WebElement BankrefNoCell = cells.get(4);
                String payuId = BankrefNoCell.getText();

                if (payuId.equalsIgnoreCase("null")) {
                    isAnyNullBankrefNo = true;
                    break;
                }
            }
            if (isAnyNullBankrefNo) {
                extentReports.createTest("In UnAccounted table BankrefNo").log(Status.FAIL, "Found a null BankrefNo");
            } else {
                extentReports.createTest("In UnAccounted table BankrefNo").log(Status.PASS, "No null BankrefNo found");
            }
        }
    }

    @Test(priority = 139)
    public void validateUnAccountedAmount() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table Amount").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullAmount = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.size() > 7) {
                WebElement AmountCell = cells.get(5);
                String Amount = AmountCell.getText();

                if (Amount.equalsIgnoreCase("null")) {
                    isAnyNullAmount = true;
                    break;
                }
            } else {
                System.out.println("Row does not have enough cells");
            }
        }

        if (isAnyNullAmount) {
            extentReports.createTest("In UnAccounted table").log(Status.FAIL, "Found a null in Amount");
        } else {
            extentReports.createTest("In UnAccounted table").log(Status.PASS, "No null Amount found");
        }
    }

    @Test(priority = 140)
    public void validateUnAccountedMode() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In UnAccounted table Mode").log(Status.FAIL, "No data available");
            return;
        }
        boolean isAnyNullMode = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement ModeCell = cells.get(6);
                String Mode = ModeCell.getText();

                if (Mode.equalsIgnoreCase("null")) {
                    isAnyNullMode = true;
                    break;
                }
            }
            if (isAnyNullMode) {
                extentReports.createTest("In UnAccounted table Mode").log(Status.FAIL, "Found a null Mode");
            } else {
                extentReports.createTest("In UnAccounted table Mode").log(Status.PASS, "No null Mode found");
            }
        }
    }

    @Test(priority = 141)
    public void validateUnAccountedStatus() {
        WebElement table = driver.findElement(By.id("mobileTab9"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Reservations table Status").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyNullStatus = false;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 7) {
                WebElement StatusCell = cells.get(7);
                String Status = StatusCell.getText();

                if (Status.equalsIgnoreCase("null")) {
                    isAnyNullStatus = true;
                    break;
                }
            }
            if (isAnyNullStatus) {
                extentReports.createTest("In UnAccounted table Status").log(Status.FAIL, "Found a null Status");
            } else {
                extentReports.createTest("In UnAccounted table Status").log(Status.PASS, "No null Status found");
            }
        }
    }

    // @Test(priority = 142)
    public void mobileNumberVehicleList() {
        try {
            driver.findElement(By.id("pop8"));
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number Vehicle List Table").log(Status.PASS,
                    "Successfully clicking the Vehicle List table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Vehicle List Table").log(Status.FAIL,
                    "Failed to click the Vehicle List table. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 143)
    public void vehicleTable() {

        try {
            WebElement table = driver.findElement(By.id("mobileTab8"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("In  Vehicle List ").log(Status.FAIL, "No data available");
                return;
            }
            System.out.println(
                    "-----------------------------------------------------------------Vehicle List-----------------------------------------");
            System.out.println(
                    "| Vehicle Number | Vehicle Type| ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 2) {

                    String VehicleNumber = cells.get(0).getText();
                    String VehicleType = cells.get(1).getText();

                    System.out.printf(
                            "| %-30s | %-10s | %-10s |%n",
                            VehicleNumber, VehicleType);
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------");

                Thread.sleep(3000);
            }
            extentReports.createTest("In Vehicle List").log(Status.PASS, "Displaying Successfully");
        } catch (Exception e) {
            extentReports.createTest(" In Vehicle List").log(Status.FAIL,
                    "Failed to Displaying. Exception: " + e.getCause());
        }
    }

    @Test(priority = 144)
    public void validateVehicleNumber() {
        WebElement table = driver.findElement(By.id("mobileTab8"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Vehicle table Vehicle Number").log(Status.FAIL, "No data available");
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
            extentReports.createTest("In Vehicle table VehicleNumber").log(Status.FAIL, "Found a null VehicleNumber");
        } else {
            extentReports.createTest("In Vehicle table VehicleNumber").log(Status.PASS, "No null Status VehicleNumber");
        }
    }

    @Test(priority = 145)
    public void validateVehicleType() {
        WebElement table = driver.findElement(By.id("mobileTab8"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Vehicle table Vehicle Type").log(Status.FAIL, "No data available");
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
            extentReports.createTest("In Vehicle table VehicleType").log(Status.FAIL, "Found a null VehicleType");
        } else {
            extentReports.createTest("In Vehicle table VehicleType").log(Status.PASS, "No null Status VehicleType");
        }
    }

    @Test(priority = 146)
    public void mobileNumberGuestPasses() {
        try {
            driver.findElement(By.id("pop10-tab")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number Guest Passes Table").log(Status.PASS,
                    "Successfully clicking the Guest Passes table");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Guest Passes Table").log(Status.FAIL,
                    "Failed to click the Guest Passes table. Exception: " + e.getCause());
        }
    }

    @Test(priority = 147)
    public void mobileNumberGuestPassesDates() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter10']/label[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter10']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter10']/label[4]")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number Guest Passes Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Guest Passes Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 148)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Mobile Number Search Page LOGOUT ").log(Status.PASS,
                    "Successfully LOGOUT from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Search PageLOGOUT").log(Status.FAIL,
                    "Failed to LOGOUT. Exception: " + e.getCause());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            extentReports.flush();
            driver.quit();
        }
    }

    public static void mobileNumberPassDates(WebDriver driver, ExtentReports extentReports) {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter2']/label[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter2']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter2']/label[4]")).click();

            extentReports.createTest("Mobile Number Passes Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Passes Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    public static void mobileReservationDates(WebDriver driver, ExtentReports extentReports) {
        try {
            driver.findElement(By.xpath("//*[@id='dayRangeFilter3']/label[2]")).click();
            driver.findElement(By.xpath("//*[@id='dayRangeFilter3']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter3']/label[4]")).click();
            Thread.sleep(2000);

            extentReports.createTest("Mobile Number Reservations Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Reservations Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    public static void mobileNumberCheckInOutDates(WebDriver driver, ExtentReports extentReports) {
        try {
            driver.findElement(By.xpath("//div[@id='dayRangeFilter4']/label[2]")).click();
            driver.findElement(By.xpath("//div[@id='dayRangeFilter4']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter4']/label[4]")).click();
            Thread.sleep(2000);
            extentReports.createTest("Mobile Number CheckIn/Out Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number CheckIn/Out Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    public static void mobileNumberDueAmountDates(WebDriver driver, ExtentReports extentReports) {
        try {
            driver.findElement(By.xpath("//*[@id='dayRangeFilter5']/label[2]")).click();
            driver.findElement(By.xpath("//*[@id='dayRangeFilter5']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter5']/label[4]")).click();
            Thread.sleep(2000);

            extentReports.createTest("Mobile Number DueAmount Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number DueAmount Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    public static void mobileNumberFeedbackDates(WebDriver driver, ExtentReports extentReports) {
        try {
            driver.findElement(By.xpath("//div[@id='dayRangeFilter6']/label[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter6']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter6']/label[4]")).click();

            extentReports.createTest("Mobile Number Feedback Dates  enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Number Feedback Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }
}
