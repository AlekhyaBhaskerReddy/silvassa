package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
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

public class PayUReports extends BaseTestReport {
  WebDriver driver;
  ServerCredentials serverconfig;

  @BeforeClass
  public void setUp() {

    // extentReports = new ExtentReports();
    // spark = new ExtentSparkReporter("results/PayUReports.html");
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
  public void redirectRevenueDashboard() {
    Dashboard.revenuedashboardOpen(driver, extentReports);
  }

  @Test(priority = 3)
  public void redirectPayUReportsPage() {
    try {
      Thread.sleep(3000);
      driver.findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]")).click();
      Thread.sleep(3000);
      WebElement payUReportsPageLink = driver
          .findElement(By.className("pur-pg"));
      if (payUReportsPageLink.isDisplayed()) {
        payUReportsPageLink.click();
        extentReports.createTest("PayU Reports Page ").log(Status.PASS,
            "Successfully Open the PayU Reports Page");
      } else {
        extentReports.createTest("PayU Reports Page ").log(Status.WARNING,
            "PayU Reports Page not found");
        Assert.fail("PayU Reports Page  not found");
      }

    } catch (Exception e) {
      extentReports.createTest("PayU Reports Page").log(Status.FAIL, "Failed to Open the PauYReports Page");
    }
  }

  @Test(priority = 4)
  public void payUDatesEnabled() {
    Dashboard.revenueDatesEnabled(driver, extentReports);
  }

  @Test(priority = 6)
  public void selectPayUStartDate() {
    Dashboard.selectDashboardStartDate(driver, extentReports);
  }

  @Test(priority = 7)
  public void selectPayUEndDate() {
    Dashboard.selectDashboardEndDate(driver, extentReports);
  }

  @Test(priority = 5)
  public void payUReportCount() {
    try {
      Thread.sleep(5000);
      WebElement dropdown = driver.findElement(By.name("payuReportsTable_length"));
      Select select = new Select(dropdown);
      select.selectByValue("25");
      extentReports.createTest("select PayUReports Count").log(Status.PASS, "Successfully selected as");
    } catch (Exception e) {
      extentReports.createTest("select PayUReports Count").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());
    }
  }

  @Test(priority = 8)
  public void downloadPayUReportCSVfile() {
    try {
      WebElement payuCsvButton = driver.findElement(By.id("payucsv"));

      if (payuCsvButton.isDisplayed() && payuCsvButton.isEnabled()) {
        payuCsvButton.click();
        Thread.sleep(2000);
        extentReports.createTest("PayUReports Downloading the CSV file")
            .log(Status.PASS, "Downloading CSV file Successfully");

      } else {
        extentReports.createTest("PayUReports csv Button not Present or Enabled")
            .log(Status.FAIL, "Button not present or enabled. Cannot proceed to download CSV file.");
      }
    } catch (Exception e) {
      extentReports.createTest("PayUReports Test Execution Failed")
          .log(Status.FAIL, "Exception during test execution: " + e.getMessage());
    }
  }

  @Test(priority = 9)
  public void downloadPayUReportPDFfile() {
    try {
      String mainWindowHandle = driver.getWindowHandle();

      driver.findElement(By.id("payupdf")).click();

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      wait.until(ExpectedConditions.numberOfWindowsToBe(2));

      Set<String> windowHandles = driver.getWindowHandles();
      for (String windowHandle : windowHandles) {
        if (!windowHandle.equals(mainWindowHandle)) {
          driver.switchTo().window(windowHandle);
          driver.close();

          driver.switchTo().window(mainWindowHandle);

          extentReports.createTest("PayUReports Downloading the pdf file").log(Status.PASS,
              "Downloading pdf file Successfully");
          return;
        }
      }

      extentReports.createTest("PayUReports not Downloading the pdf file").log(Status.FAIL,
          "Failed to download the pdf file. Window handle not found.");
    } catch (Exception e) {
      extentReports.createTest("PayUReports not Downloading the pdf file").log(Status.FAIL,
          "Failed to download the pdf file. Exception: " + e.getCause());
    }
  }

  @Test(priority = 10)
  public void nextPage() {
    try {
      Thread.sleep(5000);
      WebElement nextButton = driver.findElement(By.id("payuReportsTable_next"));

      while (!nextButton.getAttribute("class").contains("disabled")) {
        nextButton.click();
        Thread.sleep(3000);
        nextButton = driver.findElement(By.id("payuReportsTable_next"));
      }
      WebElement previousButton = driver.findElement(By.id("payuReportsTable_previous"));
      previousButton.click();

      extentReports.createTest("PayU Reports table list ").log(Status.PASS,
          "Successfully navigated through pages");
    } catch (Exception e) {
      extentReports.createTest("PayU Reports table list").log(Status.FAIL,
          "Failed to navigate through pages. Exception: " + e.getCause());
    }
  }

  @Test(priority = 11)
  public void payUReportsTable() {
    try {
      Thread.sleep(3000);
      WebElement table = driver.findElement(By.id("payuReportsTable"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      if (rows.isEmpty()) {
        extentReports.createTest("In PayUReports Page Table").log(Status.FAIL,
            "No User data available");
        return;
      }

      System.out.println(
          "-----------------------------------------------------PayUReports------------------------------------------------");
      System.out.println(
          "| Date  | Mobile | Email | Product Info | Bank Name | Bank Ref No |Amount|Payu Id|UPI Id|Transaction Id|Mode|Issuing Bank|IP|Status|Error Code|Action|");
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------------");

      for (WebElement row : rows) {
        List<WebElement> cells = row.findElements(By.tagName("td"));

        if (cells.size() >= 16) {

          String Date = cells.get(0).getText();
          String Mobile = cells.get(1).getText();
          String Email = cells.get(2).getText();
          String ProductInfo = cells.get(3).getText();
          String BankName = cells.get(4).getText();
          String BankRefNo = cells.get(5).getText();
          String Amount = cells.get(6).getText();
          String PayuId = cells.get(7).getText();
          String UPIId = cells.get(8).getText();
          String TransactionId = cells.get(9).getText();
          String Mode = cells.get(10).getText();
          String IssuingBank = cells.get(11).getText();
          String IP = cells.get(12).getText();
          String Status = cells.get(13).getText();
          String ErrorCode = cells.get(14).getText();
          String Action = cells.get(15).getText();

          System.out.printf(
              "| %-40s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%-10s |%n",
              Date, Mobile,
              Email, ProductInfo, BankName, BankRefNo, Amount, PayuId,
              UPIId, TransactionId, Mode, IssuingBank, IP, Status, ErrorCode, Action);
        }
        System.out.println(
            "----------------------------------------------------------------------------------------------------------------");

        Thread.sleep(3000);
      }
      extentReports.createTest("PayUReports Table").log(Status.PASS, "Displaying Successfully");
    } catch (Exception e) {
      extentReports.createTest("PayUReports Table").log(Status.FAIL,
          "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 12)
  public void validatePayUAmount() {
    WebElement table = driver.findElement(By.id("payuReportsTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In PayU Reports table Amount").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyzeroAmount = false;
    for (WebElement row : rows) {

      WebElement AmountCell = row.findElements(By.tagName("td")).get(6);
      String Amount = AmountCell.getText();

      if (Amount.equalsIgnoreCase("zero")) {
        isAnyzeroAmount = true;
        break;
      }
    }
    if (isAnyzeroAmount) {
      extentReports.createTest("In PayU Reports table Amount").log(Status.FAIL,
          "Found a zero Amount");
      Assert.fail("Test case failed because an zero Amount was found.");
    } else {
      extentReports.createTest("In PayU Reports table Amount").log(Status.PASS,
          "No zero Amount found");
    }
  }

  @Test(priority = 13)
  public void validatePayUStatus() {
    WebElement table = driver.findElement(By.id("payuReportsTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In PayU Reports table Status").log(Status.FAIL, "No data available");
      return;
    }
    boolean isAnyzeroStatus = false;

    for (WebElement row : rows) {
      WebElement statusCell = row.findElements(By.tagName("td")).get(13);
      String status = statusCell.getText();

      if (status.equalsIgnoreCase("zero")) {
        isAnyzeroStatus = true;
        break;
      }
    }

    if (isAnyzeroStatus) {
      extentReports.createTest("In PayU Reports table Status").log(Status.FAIL,
          "Found a failure Status");
      Assert.fail("Test case failed because an failure Status was found.");
    } else {
      extentReports.createTest("In PayU Reports table Status").log(Status.PASS,
          "No failure Status found");
    }
  }

  @Test(priority = 14)
  public void againDownloadCsv() {
    downloadPayUReportCSVfile();
  }

  @Test(priority = 15)
  public void againDownloadPdf() {
    downloadPayUReportPDFfile();
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      extentReports.flush();
      driver.quit();
    }
  }
}