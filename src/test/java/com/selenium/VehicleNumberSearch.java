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

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class VehicleNumberSearch extends BaseTestReport {
  WebDriver driver;
  ServerCredentials serverconfig;

  @BeforeClass
  public void setUp() {

    // extentReports = new ExtentReports();
    // spark = new ExtentSparkReporter("results/VehicleNumberSearch.html");
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
  public void redirectVehicleNumberSearchPage() {
    try {
      Thread.sleep(5000);
      driver
          .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
          .click();
      WebElement vehicleNumberSeacrhPageLink = driver.findElement(By.className("vns-pg"));
      if (vehicleNumberSeacrhPageLink.isDisplayed()) {
        vehicleNumberSeacrhPageLink.click();
        extentReports.createTest("Vehicle Number Search Page ").log(Status.PASS,
            "Successfully open Vehicle Number search Reports Page");
      } else {
        extentReports.createTest("Vehicle Number Search Page").log(Status.INFO,
            "Vehicle Number Search Page not found");
        Assert.fail("Vehicle Number Search Page not found");
      }
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Search Page").log(Status.FAIL,
          "Failed to open the Vehicle Number Search Page . Exception: " + e.getCause());
    }
  }

  @Test(priority = 4)
  public void vehicleNumberEnter() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.id("vehicleSearch")).sendKeys("MA12AA2222");
      extentReports.createTest("Vehicle Number Entry ").log(Status.PASS,
          "Successfully enter the number in Vehicle Number search Reports Page");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Enter ").log(Status.FAIL,
          "Failed to enter the Vehicle number in Vehicle Number search Reports . Exception: " + e.getCause());
    }
  }

  @Test(priority = 5)
  public void vehicleNumberSubmit() {
    TicketNumberSearch.ticketNumberSubmit(driver, extentReports);
  }

  @Test(priority = 6)
  public void vehicleNumberTransactions() {
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("pop1-tab")));
      element.click();
      driver.findElement(By.id("pop1-tab")).click();
      extentReports.createTest("Vehicle Number Transactions Tab").log(Status.PASS,
          "Successfully clicking the Transactions table");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Transactions Table").log(Status.FAIL,
          "Failed to click the Transactions table. Exception: " + e.getCause());
    }
  }

  @Test(priority = 7)
  public void vehicleNumberTransactionsDates() {
    try {
      driver.findElement(By.xpath("//div[@id='dayRangeFilter1']/label[2]")).click();
      driver.findElement(By.xpath("//div[@id='dayRangeFilter1']/label[3]")).click();
      Thread.sleep(2000);
      driver.findElement(By.xpath("//div[@id='dayRangeFilter1']/label[4]")).click();
      Thread.sleep(2000);

      extentReports.createTest("Vehicle Number Transactions Dates  enabled ").log(Status.PASS,
          "Successfully selecting the day/week/month");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Transactions Dates enabled").log(Status.FAIL,
          "Failed to selecting the day/week/month . Exception: " + e.getCause());
    }
  }

  @Test(priority = 8)
  public void selectVNStartDateTransactions() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.xpath("//div[@id='dayRangeFilter1']/label[1]")).click();
      Thread.sleep(1000);

      driver.findElement(By.xpath("//th[@class='prev available']")).click();
      Thread.sleep(1000);
      driver.findElement(By.xpath("//th[@class='prev available']")).click();
      Thread.sleep(1000);
      WebElement dateElement = driver.findElement(By.xpath("//td[text()='7']"));

      dateElement.click();
      extentReports.createTest("Vehicle Number Transactions Start Date").log(Status.PASS,
          "Successfully select Start Date");
    } catch (Exception e) {

      extentReports.createTest("Vehicle Number Transactions Start Date").log(Status.FAIL,
          "Failed to select Start Date. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 9)
  public void selectVNEndDateTransactions() {
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

      extentReports.createTest("Vehicle Number Transactions End Date ").log(Status.PASS,
          "Successfully select End Date");
    } catch (Exception e) {

      extentReports.createTest("Vehicle Number Transactions End Date ").log(Status.FAIL,
          "Failed to select End Date. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 10)
  public void selectVNTransactionCount() {
    try {
      Thread.sleep(2000);
      WebElement dropdown = driver.findElement(By.name("vechiletab1_length"));
      Select select = new Select(dropdown);
      select.selectByValue("25");
      String countValue = "25";
      extentReports.createTest("select Transaction Count").log(Status.PASS, "Successfully selected as :" + countValue);
    } catch (Exception e) {
      extentReports.createTest("select Transaction Count").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());
    }
  }

  @Test(priority = 11)
  public void vehicleNumberTransactionsTable() {
    try {
      WebElement table = driver.findElement(By.id("vechiletab1"));
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

      extentReports.createTest("Transaction Table in Vehicle Number Search Reports page").log(Status.PASS,
          "Displaying Successfully" + tableData);
    } catch (Exception e) {
      extentReports.createTest("Transaction Table in Vehicle Number Search Reports page").log(Status.FAIL,
          "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 12)
  public void validateVNTransactionSiteName() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
      extentReports.createTest("In Transaction table SiteName in vehicle number search").log(Status.FAIL,
          "Found a null SiteName");
    } else {
      extentReports.createTest("In Transaction table SiteName in vehicle number search").log(Status.PASS,
          "No null SiteName found");
    }
  }

  @Test(priority = 13)
  public void validateVTransactionVehicleNumber() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table VehicleNumber").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleNumber = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 2) {
        WebElement VehicleNumberCell = cells.get(1);
        String VehicleNumber = VehicleNumberCell.getText();

        if (VehicleNumber.equalsIgnoreCase("null")) {
          isAnyNullVehicleNumber = true;
          break;
        }
      }
      if (isAnyNullVehicleNumber) {
        extentReports.createTest("In Transaction table VehicleNumber").log(Status.FAIL, "Found a null Vehicle Number");
      } else {
        extentReports.createTest("In Transaction table VehicleNumber").log(Status.PASS,
            "No null vehicle numbers found");
      }
    }
  }

  @Test(priority = 14)
  public void validateVTransactionVehicleType() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table VehicleType").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleType = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 3) {
        WebElement VehicleTypeCell = cells.get(2);
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
  }

  @Test(priority = 15)
  public void validateVTransactionType() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table Type ").log(Status.FAIL, "No data available");
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
        extentReports.createTest("In Transaction table Type").log(Status.FAIL, "Found a null Type");
      } else {
        extentReports.createTest("In Transaction table Type").log(Status.PASS, "No null Type found");
      }
    }
  }

  @Test(priority = 16)
  public void validateVTransactionCreatedDate() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In ransaction table Created Date").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyInvalidCreatedDate = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 4) {
        WebElement createdDateCell = cells.get(3);
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
        extentReports.createTest("In Transaction table Created Date").log(Status.PASS, "No null Created Date found");
      }
    }
  }

  @Test(priority = 17)
  public void validateVTransactionStartDate() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table Start Date ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyInvalidStartDate = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 5) {
        WebElement startDateCell = cells.get(4);
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
  }

  @Test(priority = 18)
  public void validateVTransactionEndDate() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table End Date").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyInvalidEndDate = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 6) {
        WebElement endDateCell = cells.get(5);
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
  }

  @Test(priority = 19)
  public void validateVTransactionPayU_reference() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table PayU_reference ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullPayU_reference = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 7) {
        WebElement PayU_referenceCell = cells.get(6);
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
  }

  @Test(priority = 20)
  public void validateVTransactionId() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In  Transaction table Transaction id  ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullTransactionID = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 8) {
        WebElement TransactionIDCell = cells.get(7);
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
  }

  @Test(priority = 21)
  public void validateVTransactionAmount() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table Amount ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullAmount = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 9) {
        WebElement AmountCell = cells.get(8);
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
  }

  @Test(priority = 22)
  public void validateVTransactionStatus() {
    WebElement table = driver.findElement(By.id("vechiletab1"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Transaction table Status ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullStatus = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 10) {
        WebElement StatusCell = cells.get(9);
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
  }

  @Test(priority = 23)
  public void vehicleNumberPass() {
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("pop2-tab")));
      element.click();
      driver.findElement(By.id("pop2-tab")).click();
      extentReports.createTest("Vehicle Number Pass Table").log(Status.PASS,
          "Successfully clicking the Pass table");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Pass Table").log(Status.FAIL,
          "Failed to click the Pass table. Exception: " + e.getCause());
    }
  }

  @Test(priority = 24)
  public void vehicleNumberPassDates() {
    MobileNumberSearch.mobileNumberPassDates(driver, extentReports);
  }

  @Test(priority = 25)
  public void selectVStartDatePasses() {
    try {
      driver.findElement(By.xpath("//*[@id='dayRangeFilter2']/label[1]")).click();
      Thread.sleep(2000);

      WebElement startDate = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
      startDate.click();

      extentReports.createTest("Start Date in BlockList").log(Status.PASS, "Successfully select Start Date");
    } catch (Exception e) {

      extentReports.createTest("Start Date in BlockList").log(Status.FAIL,
          "Failed to select Start Date. Exception: " + e.getMessage());

    }

  }

  @Test(priority = 26)
  public void selectVEndDatePasses() {
    try {
      Thread.sleep(2000);
      WebElement endDate = driver.findElement(By.xpath("//td[@class='available' and text()='20']"));
      endDate.click();
      Thread.sleep(3000);

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String script = "$('.applyBtn').click();";
      js.executeScript(script);
      Thread.sleep(3000);
      extentReports.createTest("End Date in BlockList").log(Status.PASS, "Successfully select End Date");
    } catch (Exception e) {

      extentReports.createTest("End Date in BlockList").log(Status.FAIL,
          "Failed to select End Date. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 27)
  public void selectVPassCount() {
    try {
      WebElement dropdown = driver.findElement(By.name("vechiletab2_length"));
      Select select = new Select(dropdown);
      String countValue = "25";
      select.selectByValue(countValue);
      Thread.sleep(2000);
      extentReports.createTest("Passes table dropdown count ").log(Status.PASS,
          "Successfully selected as : " + countValue);
    } catch (Exception e) {
      extentReports.createTest("Passes Table dropdown count").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());
    }
  }

  @Test(priority = 28)
  public void passTable() {
    try {
      WebElement table = driver.findElement(By.id("vechiletab2"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      if (rows.isEmpty()) {
        extentReports.createTest("In Passes table Status").log(Status.FAIL, "No data available");
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

      extentReports.createTest("Passes Table in Vehicle Number Search Reports page").log(Status.PASS,
          "Displaying Successfully" + tableData);
    } catch (Exception e) {
      extentReports.createTest("Passes Table in Vehicle Number Search Reports page").log(Status.FAIL,
          "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 32)
  public void validatePassesSiteName() {
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In Passes table VehicleNumber").log(Status.FAIL, "Found a null Vehicle Number");
      } else {
        extentReports.createTest("In Passes table VehicleNumber").log(Status.PASS, "No null vehicle numbers found");
      }
    }
  }

  @Test(priority = 34)
  public void validatePassesVehicleType() {
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab2"));

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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab2"));

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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab2"));

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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In Passes table PayU_reference").log(Status.FAIL, "Found a null PayU_reference");
      } else {
        extentReports.createTest("In Passes table PayU_reference").log(Status.PASS, "No null PayU_reference found");
      }
    }
  }

  @Test(priority = 43)
  public void validatePassesTransactionId() {
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In Passes table Transaction id ").log(Status.FAIL, "Found a null Transaction id");
      } else {
        extentReports.createTest("In Passes table Transaction id").log(Status.PASS, "No null Transaction id found");
      }
    }
  }

  @Test(priority = 44)
  public void validatePassesAmount() {
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

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
    WebElement table = driver.findElement(By.id("vechiletab2"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In  Passes table Status").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullStatus = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 11) {
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
      extentReports.createTest("Vehicle Number Reservations Table").log(Status.PASS,
          "Successfully clicking the Reservations table");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Reservationns Table").log(Status.FAIL,
          "Failed to click the Reservations table. Exception: " + e.getCause());
    }
  }

  @Test(priority = 47)
  public void vehicleNumberReservationDates() {
    MobileNumberSearch.mobileReservationDates(driver, extentReports);
  }

  // @Test(priority = 48)
  public void selectStartDateReservations() {
    try {
      Thread.sleep(2000);
      WebElement element = driver.findElement(By.xpath("//*[@id='dayRangeFilter3']/label[1]"));
      element.click();
      Thread.sleep(2000);
      WebElement startDate = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
      startDate.click();
      extentReports.createTest("Start Date in Reservations").log(Status.PASS, "Successfully select Start Date");
    } catch (Exception e) {

      extentReports.createTest("Start Date in Reservations").log(Status.FAIL,
          "Failed to select Start Date. Exception: " + e.getMessage());
    }
  }

  // @Test(priority = 49)
  public void selectEndDateReservations() {
    try {
      Thread.sleep(2000);
      WebElement endDate = driver.findElement(By.xpath("//td[@class='available' and text()='21']"));
      endDate.click();
      Thread.sleep(3000);

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String script = "$('.applyBtn').click();";
      js.executeScript(script);
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
      WebElement dropdown = driver.findElement(By.name("vechiletab3_length"));
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
      WebElement table = driver.findElement(By.id("vechiletab3"));
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

      extentReports.createTest("Reservations Table in Vehicle Number Search Reports page").log(Status.PASS,
          "Displaying Successfully" + tableData);
    } catch (Exception e) {
      extentReports.createTest("Reservations Table in vehicle Number Search Reports page").log(Status.FAIL,
          "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 52)
  public void validateReservationsSiteName() {
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table VehicleNumber").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleNumber = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 2) {
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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table VehicleType ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleType = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 3) {
        WebElement VehicleTypeCell = cells.get(2);
        String VehicleType = VehicleTypeCell.getText();

        if (VehicleType.equalsIgnoreCase("null")) {
          isAnyNullVehicleType = true;
          break;
        }
      }
      if (isAnyNullVehicleType) {
        extentReports.createTest("In Reservations table VehicleType").log(Status.FAIL, "Found a null VehicleType");
      } else {
        extentReports.createTest("In Reservations table VehicleType").log(Status.PASS, "No null VehicleType found");
      }
    }
  }

  @Test(priority = 55)
  public void validateReservationsType() {
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table Type").log(Status.FAIL, "No data available");
      return;
    }
    boolean isAnyNullType = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 4) {
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
    WebElement table = driver.findElement(By.id("vechiletab3"));

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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table Created Date ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyInvalidCreatedDate = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 5) {
        WebElement endDateCell = cells.get(4);
        String createdDate = endDateCell.getText();

        if (createdDate.equalsIgnoreCase("Invalid date")) {
          isAnyInvalidCreatedDate = true;
          break;
        }
      }
      if (isAnyInvalidCreatedDate) {
        extentReports.createTest("In Reservations table Created Date").log(Status.FAIL, "Found a null Created Date");
        Assert.fail("Test case failed because an invalid Created Date was found.");
      } else {
        extentReports.createTest("In Reservations table Created Date").log(Status.PASS, "No null Created Date found");
      }
    }
  }

  @Test(priority = 58)
  public void validateReserveStartDateTimeHeader() {
    WebElement table = driver.findElement(By.id("vechiletab3"));

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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table Start Date").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyInvalidStartDate = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 6) {
        WebElement endDateCell = cells.get(5);
        String startDate = endDateCell.getText();

        if (startDate.equalsIgnoreCase("Invalid date")) {
          isAnyInvalidStartDate = true;
          break;
        }
      }

      if (isAnyInvalidStartDate) {
        extentReports.createTest("In Reservations table Start Date").log(Status.FAIL, "Found a invalid Start Date");
        Assert.fail("Test case failed because an invalid Start Date was found.");
      } else {
        extentReports.createTest("In Reservations table Start Date").log(Status.PASS, "No invalid Start Date found");
      }
    }
  }

  @Test(priority = 60)
  public void validateReserveEndDateTimeHeader() {
    WebElement table = driver.findElement(By.id("vechiletab3"));

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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table End Date").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyInvalidEndDate = false;

    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 7) {
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
        extentReports.createTest("In Reservations table End Date").log(Status.PASS, "No invalid End Date found");
      }
    }
  }

  @Test(priority = 62)
  public void validateReservationsPayU_reference() {
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table PayU_reference").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullPayU_reference = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 8) {
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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table TransactionID").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullTransactionID = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 9) {
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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullAmount = false;
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table Amount").log(Status.FAIL, "No data available");
      return;
    }

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 10) {
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
    WebElement table = driver.findElement(By.id("vechiletab3"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Reservations table Status").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullStatus = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() > 11) {
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
  public void vehicleNumberCheckInOut() {
    try {
      Thread.sleep(1000);
      driver.findElement(By.id("pop4-tab")).click();
      Thread.sleep(2000);
      extentReports.createTest("Vehicle Number CheckIn/Out Table").log(Status.PASS,
          "Successfully clicking the CheckIn/Out table");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number CheckIn/Out Table").log(Status.FAIL,
          "Failed to click the CheckIn/Out table. Exception: " + e.getCause());
    }
  }

  @Test(priority = 67)
  public void vehicleNumberCheckInOutDates() {
    MobileNumberSearch.mobileNumberCheckInOutDates(driver, extentReports);
  }

  // @Test(priority = 68)
  public void selectVStartDateCheckInOut() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.xpath("//div[@id='dayRangeFilter4']/label[1]")).click();
      Thread.sleep(2000);

      WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));

      dateElement.click();
      extentReports.createTest("Start Date in CheckInOut").log(Status.PASS, "Successfully select Start Date");
    } catch (Exception e) {

      extentReports.createTest("Start Date in CheckInOut").log(Status.FAIL,
          "Failed to select Start Date. Exception: " + e.getMessage());
    }
  }

  // @Test(priority = 69)
  public void selectVEndDateCheckInOut() {
    try {
      Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='next available']")).click();
      // Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='next available']")).click();
      WebElement dateElement = driver
          .findElement(By.xpath("//td[@class='available' and text()='21']"));

      dateElement.click();
      Thread.sleep(3000);

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String script = "$('.applyBtn').click();";
      js.executeScript(script);
      Thread.sleep(3000);

      extentReports.createTest("End Date in CheckInOut").log(Status.PASS, "Successfully select End Date");
    } catch (Exception e) {

      extentReports.createTest("End Date in CheckInOut").log(Status.FAIL,
          "Failed to select End Date. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 70)
  public void selectVPassCheckInOut() {
    try {
      WebElement dropdown = driver.findElement(By.name("vechiletab4_length"));
      Select select = new Select(dropdown);
      select.selectByValue("25");
      Thread.sleep(2000);
      extentReports.createTest("CheckInOut table dropdown count ").log(Status.PASS, "Successfully selected as");
    } catch (Exception e) {
      extentReports.createTest("CheckInOut Table dropdown count").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());
    }
  }

  @Test(priority = 71)
  public void checkInOutVTable() {

    try {
      WebElement table = driver.findElement(By.id("vechiletab4"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      if (rows.isEmpty()) {
        extentReports.createTest("In CheckIn/Out table").log(Status.FAIL, "No data available");
        return;
      }

      System.out.println(
          "-----------------------------------------------------------------CheckIn/Out-----------------------------------------");
      System.out.println(
          "| Site Name    | Vehicle Type | Vehicle Number | CheckIn Date/Time | CheckOut Date/Time |Type |PayU Reference|Transaction ID| Amount| Due Amount|Status| ");
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------------");

      for (WebElement row : rows) {
        List<WebElement> cells = row.findElements(By.tagName("td"));

        if (cells.size() >= 11) {

          String siteName = cells.get(0).getText();
          String VehicleType = cells.get(1).getText();
          String VehicleNumber = cells.get(2).getText();
          String CheckInDateTime = cells.get(3).getText();
          String CheckOutDateTime = cells.get(4).getText();
          String Type = cells.get(5).getText();
          String PayUReference = cells.get(6).getText();
          String TransactionID = cells.get(7).getText();
          String Amount = cells.get(8).getText();
          String DueAmount = cells.get(9).getText();
          String Status = cells.get(10).getText();

          System.out.printf(
              "| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s|%-10s|%-10s |%-10s| %n",
              siteName, VehicleType,
              VehicleNumber, CheckInDateTime, CheckOutDateTime, Type, PayUReference,
              TransactionID, Amount, DueAmount,
              Status);
        }
        System.out.println(
            "----------------------------------------------------------------------------------------------------------------");

        Thread.sleep(3000);
      }
      extentReports.createTest("CheckInOut Table ").log(Status.PASS, "Displaying Successfully");
    } catch (Exception e) {
      extentReports.createTest("CheckInOut Table").log(Status.FAIL, "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 72)
  public void validateVCheckInOutSiteName() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
  public void validateVCheckInOutVehicleType() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table VehicleType ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleType = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 2) {
        WebElement VehicleTypeCell = cells.get(1);
        String VehicleType = VehicleTypeCell.getText();

        if (VehicleType.equalsIgnoreCase("null")) {
          isAnyNullVehicleType = true;
          break;
        }
      }
      if (isAnyNullVehicleType) {
        extentReports.createTest("In CheckInOut table Vehicle Type").log(Status.FAIL, "Found a null Vehicle Type");
      } else {
        extentReports.createTest("In CheckInOut table Vehicle Type").log(Status.PASS, "No null Vehicle Type found");
      }
    }
  }

  @Test(priority = 74)
  public void validateVCheckInOutVehicleNumber() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table VehicleNumber").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleNumber = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 3) {
        WebElement VehicleNumberCell = cells.get(2);
        String VehicleNumber = VehicleNumberCell.getText();

        if (VehicleNumber.equalsIgnoreCase("null")) {
          isAnyNullVehicleNumber = true;
          break;
        }
      }
      if (isAnyNullVehicleNumber) {
        extentReports.createTest("In CheckInOut table VehicleNumber").log(Status.FAIL, "Found a null VehicleNumber");
      } else {
        extentReports.createTest("In CheckInOut table VehicleNumber").log(Status.PASS, "No null VehicleNumber found");
      }
    }
  }

  @Test(priority = 75)
  public void validateCheckInDateTimeHeader() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement thead = table.findElement(By.tagName("thead"));
    WebElement fourthTh = thead.findElement(By.xpath(".//tr/th[4]"));

    String headerText = fourthTh.getText();
    if (headerText.equals("CheckIn Date / Time")) {
      extentReports.createTest("CheckIn Date / Time Header Validation in vehicle number search").log(Status.PASS,
          "Header content is 'CheckIn Date / Time'");
    } else {
      extentReports.createTest("CheckIn Date / Time Header Validation in vehicle number search ").log(Status.FAIL,
          "Header content is not 'CheckIn Date / Time'");
    }
  }

  @Test(priority = 76)
  public void validateVCheckInRow() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table CheckIn").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullCheckIn = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 4) {
        WebElement CheckInCell = cells.get(3);
        String CheckIn = CheckInCell.getText();

        if (CheckIn.equalsIgnoreCase("null")) {
          isAnyNullCheckIn = true;
          break;
        }
      }
      if (isAnyNullCheckIn) {
        extentReports.createTest("In CheckInOut table CheckIn Row").log(Status.FAIL, "Found a null CheckIn row");
      } else {
        extentReports.createTest("In CheckInOut table CheckIn Row").log(Status.PASS, "No null CheckIn row found");
      }
    }
  }

  @Test(priority = 77)
  public void validateCheckOutDateTimeHeader() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement thead = table.findElement(By.tagName("thead"));
    WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

    String headerText = fifthTh.getText();
    if (headerText.equals("CheckOut Date / Time")) {
      extentReports.createTest("CheckOut Date / Time Header Validation in vehicle number search").log(Status.PASS,
          "Header content is 'CheckOut Date / Time'");
    } else {
      extentReports.createTest("CheckOut Date / Time Header Validation in vehicle number search").log(Status.FAIL,
          "Header content is not 'CheckOut Date / Time'");
    }
  }

  @Test(priority = 78)
  public void validateVCheckOutRow() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table CheckOut ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullCheckOut = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 5) {
        WebElement CheckOutCell = cells.get(4);
        String CheckOut = CheckOutCell.getText();

        if (CheckOut.equalsIgnoreCase("null")) {
          isAnyNullCheckOut = true;
          break;
        }
      }
      if (isAnyNullCheckOut) {
        extentReports.createTest("In CheckInOut table CheckOut Row").log(Status.FAIL, "Found a null CheckOut row");
      } else {
        extentReports.createTest("In CheckInOut table CheckOut Row").log(Status.PASS, "No null CheckOut row found");
      }
    }
  }

  @Test(priority = 79)
  public void validateVCheckInOutType() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table Type").log(Status.FAIL, "No data available");
      return;
    }
    boolean isAnyNullType = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 6) {
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
  public void validateVCheckInOutPayU_reference() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table PayU_reference").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullPayU_reference = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 7) {
        WebElement PayU_referenceCell = cells.get(6);

        String PayU_reference = PayU_referenceCell.getText();

        if (PayU_reference.equalsIgnoreCase("null")) {
          isAnyNullPayU_reference = true;
          break;
        }
      }
      if (isAnyNullPayU_reference) {
        extentReports.createTest("In CheckInOut table PayU_reference").log(Status.FAIL, "Found a null PayU_reference");
      } else {
        extentReports.createTest("In CheckInOut table PayU_reference").log(Status.PASS,
            "No null PayU_reference found");
      }
    }
  }

  @Test(priority = 81)
  public void validateVCheckInOutTransactionId() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table TransactionID").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullTransactionID = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 8) {
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
  public void validateVCheckInOutAmount() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullAmount = false;
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table Amount").log(Status.FAIL, "No data available");
      return;
    }

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 9) {
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
  public void validateVCheckInOutDueAmount() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullDueAmount = false;
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table DueAmount").log(Status.FAIL, "No data available");
      return;
    }

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 10) {
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
  public void validateVCheckInOutStatus() {
    WebElement table = driver.findElement(By.id("vechiletab4"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In CheckInOut table Status").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullStatus = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 11) {
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
  public void vehicleNumberDueAmount() {
    try {
      driver.findElement(By.id("pop5-tab")).click();
      Thread.sleep(2000);
      extentReports.createTest("Vehicle Number DueAmount Table").log(Status.PASS,
          "Successfully clicking the DueAmount table");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number DueAmount Table").log(Status.FAIL,
          "Failed to click the DueAmount table. Exception: " + e.getCause());
    }
  }

  @Test(priority = 86)
  public void vehicleNumberDueAmountDates() {
    MobileNumberSearch.mobileNumberDueAmountDates(driver, extentReports);
  }

  // @Test(priority = 87)
  public void selectVStartDateDueAmount() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.xpath("//div[@id='dayRangeFilter5']/label[1]")).click();
      // Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='prev available']")).click();
      // Thread.sleep(1000);
      // driver.findElement(By.xpath("//th[@class='prev available']")).click();
      WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
      dateElement.click();

      extentReports.createTest("Start Date in DueAmount").log(Status.PASS, "Successfully select Start Date");
    } catch (Exception e) {

      extentReports.createTest("Start Date in DueAmount").log(Status.FAIL,
          "Failed to select Start Date. Exception: " + e.getMessage());
    }
  }

  // @Test(priority = 88)
  public void selectVEndDateDueAmount() {
    try {
      Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='next available']")).click();
      // Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='next available']")).click();
      WebElement dateElement = driver
          .findElement(By.xpath("//td[@class='available' and text()='21']]"));

      dateElement.click();
      Thread.sleep(3000);

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String script = "$('.applyBtn').click();";
      js.executeScript(script);
      Thread.sleep(3000);

      extentReports.createTest("End Date in DueAmount").log(Status.PASS, "Successfully select End Date");
    } catch (Exception e) {

      extentReports.createTest("End Date in DueAmount").log(Status.FAIL,
          "Failed to select End Date. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 89)
  public void selectVDueAmountCount() {
    try {
      Thread.sleep(2000);
      WebElement dropdown = driver.findElement(By.name("vechiletab5_length"));
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
  public void dueAmountVTable() {

    try {
      WebElement table = driver.findElement(By.id("vechiletab5"));
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
  public void validateVDueAmountSiteName() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
  public void validateVDueAmountVehicleNumber() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In DueAmount table VehicleNumber").log(Status.FAIL, "Found a null Vehicle Number");
      } else {
        extentReports.createTest("In DueAmount table VehicleNumber").log(Status.PASS, "No null vehicle numbers found");
      }
    }
  }

  @Test(priority = 93)
  public void validateVDueAmountVehicleType() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In DueAmount table VehicleType").log(Status.PASS, "No null VehicleType found");
      }
    }
  }

  @Test(priority = 94)
  public void validateVDueAmountPayU_reference() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In DueAmount table PayU_reference").log(Status.FAIL, "Found a null PayU_reference");
      } else {
        extentReports.createTest("In DueAmount table PayU_reference").log(Status.PASS, "No null PayU_reference found");
      }
    }
  }

  @Test(priority = 95)
  public void validateDueAmountCreatedDateTimeHeader() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement thead = table.findElement(By.tagName("thead"));
    WebElement fifthTh = thead.findElement(By.xpath(".//tr/th[5]"));

    String headerText = fifthTh.getText();
    if (headerText.equals("Created Date / Time")) {
      extentReports.createTest("Created Date / Time Header Validation in vehicle number search").log(Status.PASS,
          "Header content is 'Created Date / Time'");
    } else {
      extentReports.createTest("Created Date / Time Header Validation in vehicle number search").log(Status.FAIL,
          "Header content is not 'Created Date / Time'");
    }
  }

  @Test(priority = 96)
  public void validateVDueAmountCreatedDate() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In DueAmount table Created Date").log(Status.FAIL, "Found a null Created Date");
        Assert.fail("Test case failed because an invalid Created Date was found.");
      } else {
        extentReports.createTest("In DueAmount table Created Date").log(Status.PASS, "No null Created Date found");
      }
    }
  }

  @Test(priority = 97)
  public void validateVDueAmountsTransactionId() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In DueAmount table Transaction id ").log(Status.FAIL, "Found a null Transaction id");
      } else {
        extentReports.createTest("In DueAmount table Transaction id").log(Status.PASS, "No null Transaction id found");
      }
    }
  }

  @Test(priority = 98)
  public void validateVDueAmountAmount() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

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
  public void validateVDueAmountStatus() {
    WebElement table = driver.findElement(By.id("vechiletab5"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
  public void vehicleNumberBlockListClick() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.id("pop7-tab")).click();
      extentReports.createTest("Vehicle Number BlockList Click ").log(Status.PASS,
          "Successfully clicking the blocklist ");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number BlockList Click ").log(Status.FAIL,
          "Failed to click the blocklist. Exception: " + e.getCause());
    }
  }

  @Test(priority = 101)
  public void vehicleNumberBlockListDates() {
    MobileNumberSearch.mobileNumberFeedbackDates(driver, extentReports);
  }

  // @Test(priority = 102)
  public void selectVStartDateBlockList() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.xpath("//div[@id='dayRangeFilter6']/label[1]")).click();
      // Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='prev available']")).click();
      // Thread.sleep(1000);
      // driver.findElement(By.xpath("//th[@class='prev available']")).click();
      WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));

      dateElement.click();
      extentReports.createTest("Start Date in BlockList").log(Status.PASS, "Successfully select Start Date");
    } catch (Exception e) {

      extentReports.createTest("Start Date in BlockList").log(Status.FAIL,
          "Failed to select Start Date. Exception: " + e.getMessage());
    }
  }

  // @Test(priority = 103)
  public void selectVEndDateBlockList() {
    try {
      Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='next available']")).click();
      // Thread.sleep(2000);
      // driver.findElement(By.xpath("//th[@class='next available']")).click();
      WebElement dateElement = driver
          .findElement(By.xpath("//td[@class='available' and text()='21']"));

      dateElement.click();
      Thread.sleep(3000);

      JavascriptExecutor js = (JavascriptExecutor) driver;
      String script = "$('.applyBtn').click();";
      js.executeScript(script);
      Thread.sleep(3000);

      extentReports.createTest("End Date in BlockList").log(Status.PASS, "Successfully select End Date");
    } catch (Exception e) {

      extentReports.createTest("End Date in BlockList").log(Status.FAIL,
          "Failed to select End Date. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 104)
  public void selectVBlockListCount() {
    try {
      WebElement dropdown = driver.findElement(By.name("vechiletab7_length"));
      Select select = new Select(dropdown);
      select.selectByValue("25");
      Thread.sleep(2000);
      extentReports.createTest("select BlockList Count").log(Status.PASS, "Successfully selected as");
    } catch (Exception e) {
      extentReports.createTest("select BlockList Count").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());
    }
  }

  @Test(priority = 105)
  public void vBlockListTable() {
    try {
      WebElement table = driver.findElement(By.id("vechiletab7"));
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
      extentReports.createTest("BlockList Table").log(Status.FAIL, "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 106)
  public void validateVBlockListSiteName() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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

  @Test(priority = 107)
  public void validateVBlockListVehicleNumber() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In BlockList table VehicleNumber").log(Status.FAIL, "Found a null Vehicle Number");
      } else {
        extentReports.createTest("In BlockList table VehicleNumber").log(Status.PASS, "No null vehicle numbers found");
      }
    }
  }

  @Test(priority = 108)
  public void validateVBlockListVehicleType() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In BlockList table VehicleType").log(Status.PASS, "No null VehicleType found");
      }
    }
  }

  @Test(priority = 109)
  public void validateBlockListCreatedDateTimeHeader() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement thead = table.findElement(By.tagName("thead"));
    WebElement fourthTh = thead.findElement(By.xpath(".//tr/th[4]"));

    String headerText = fourthTh.getText();
    if (headerText.equals("Created Date / Time")) {
      extentReports.createTest("Created Date / Time Header Validation in vehicle number search").log(Status.PASS,
          "Header content is 'Created Date / Time'");
    } else {
      extentReports.createTest("Created Date / Time Header Validation in vehicle number search").log(Status.FAIL,
          "Header content is not 'Created Date / Time'");
    }
  }

  @Test(priority = 110)
  public void validateVBlockListnCreatedDate() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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
        extentReports.createTest("In BlockList table Created Date").log(Status.FAIL, "Found a null Created Date");
        Assert.fail("Test case failed because an invalid Created Date was found.");
      } else {
        extentReports.createTest("In BlockList table Created Date").log(Status.PASS, "No null Created Date found");
      }
    }
  }

  @Test(priority = 111)
  public void validateVBlockListAmount() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
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

  @Test(priority = 112)
  public void validateVBlockListStatus() {
    WebElement table = driver.findElement(By.id("vechiletab7"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In BlockList table Status ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullStatus = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() > 6) {
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

  @Test(priority = 113)
  public void vehicleNumberGuestPasses() {
    try {
      driver.findElement(By.id("pop8-tab")).click();
      Thread.sleep(2000);
      extentReports.createTest("Vehicle Number Guest Passes Table").log(Status.PASS,
          "Successfully clicking the Guest Passes table");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Guest Passes Table").log(Status.FAIL,
          "Failed to click the Guest Passes table. Exception: " + e.getCause());
    }
  }

  @Test(priority = 114)
  public void vehicleNumberGuestPassesDates() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.xpath("//*[@id='dayRangeFilter8']/label[2]")).click();
      Thread.sleep(2000);
      driver.findElement(By.xpath("//*[@id='dayRangeFilter8']/label[3]")).click();
      Thread.sleep(2000);
      driver.findElement(By.xpath("//*[@id='dayRangeFilter8']/label[4]")).click();
      Thread.sleep(2000);
      extentReports.createTest("Vehicle Number Guest Passes Dates  enabled ").log(Status.PASS,
          "Successfully selecting the day/week/month");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Guest Passes Dates enabled").log(Status.FAIL,
          "Failed to selecting the day/week/month . Exception: " + e.getCause());
    }
  }

  @Test(priority = 115)
  public void logout() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.id("userName")).click();
      driver.findElement(By.id("btnLogout")).click();
      extentReports.createTest("Vehicle Number Search LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Number Search LogOut").log(Status.FAIL,
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
