package com.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class PaymentReports extends BaseTestReport {

  WebDriver driver;
  // private static extentReportsReports extentReports;
  // private static extentReportsSparkReporter spark;

  @BeforeClass
  public void setUp() {

    extentReports = new ExtentReports();
    spark = new ExtentSparkReporter("results/SanityTestReport.html");
    extentReports.attachReporter(spark);
    extentTest = extentReports.createTest("demo");

    System.setProperty(
        "webdriver.chrome.driver",
        "/home/iramtech/iRam_Folder/web_driver/114/chromedriver");
    driver = new ChromeDriver();
    driver.manage().window().maximize();
  }

  @Test(priority = 1)
  public void login() throws InterruptedException {
    driver.get("https://jhansi.eparke.in/SmartCity/ui/admin/");
    driver.findElement(By.id("username")).sendKeys("testuser");
    driver.findElement(By.id("password")).sendKeys("welcome@123");
    driver.findElement(By.name("login")).click();
    extentReports.createTest("login").log(Status.PASS, "Successfully Login");
    extentReports.flush();
    Thread.sleep(2000);
  }

  @Test(priority = 2)
  public void paymentReportsPage() throws InterruptedException {
    driver.findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]")).click();
    Thread.sleep(3000);
    driver.findElement(By.className("pgr-pg")).click();
    Thread.sleep(3000);
    extentReports.createTest("paymentReportsPage").log(Status.PASS, "Successfully Open the PaymentReports Page");
    extentReports.flush();
  }

  @Test(priority = 3)
  public void paymentReportsDates() throws InterruptedException {
    driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]")).click();
    Thread.sleep(1000);
    driver.findElement(By.xpath("//*[@id='dayRangeFilter']/label[4]")).click();
    Thread.sleep(1000);
    extentReports.createTest("paymentReportsDates").log(Status.PASS, "Successfully clicking the day/week/month");
    extentReports.flush();
  }

  @Test(priority = 4)
  public void selectDeviceTypeMPOS() throws InterruptedException {
    WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
    Select select = new Select(fastagDeviceCategoryDropdown);
    select.selectByValue("MPOS");
    Thread.sleep(1000);
    extentReports.createTest("select Device MPOS").log(Status.PASS, "Successfully selecting MPOS");
    extentReports.flush();
  }

  @Test(priority = 5)
  public void validateMPOSData() {
    WebElement table = driver.findElement(By.id("paymentReportsTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullTransactionNumber = false;

    for (WebElement row : rows) {

      WebElement transactionNumberCell = row.findElements(By.tagName("td")).get(1);

      String transactionNumber = transactionNumberCell.getText();

      if (transactionNumber.equalsIgnoreCase("null")) {
        isAnyNullTransactionNumber = true;
        break;
      }
    }
    if (isAnyNullTransactionNumber) {
      extentReports.createTest("GET MPOS data").log(Status.FAIL, "Found a null Transaction number");
    } else {
      extentReports.createTest("GET MPOS data").log(Status.PASS, "No null vehicle numbers found");
      extentReports.flush();
    }
  }

  @Test(priority = 6)
  public void tabularReport() {
    WebElement table = driver.findElement(By.id("paymentReportsTable"));
    List<WebElement> rows = table.findElements(By.tagName("tr"));
    System.out.println(
        "------------------------------------------------------------------------------------------------------------------");
    System.out.println(
        "|  ID   | Ticket No | Vehicle Number | Mobile Numberr | Vehicle Type | Amount | Discounted Amount | PaymentType |  Status | Type |  PaymentClient  |  Email |");
    System.out.println(
        "------------------------------------------------------------------------------------------------------------------");
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));

      if (cells.size() >= 12) {
        String ID = cells.get(0).getText();
        String TicketNo = cells.get(1).getText();
        String VehicleNumber = cells.get(2).getText();
        String MobileNumber = cells.get(3).getText();
        String VehicleType = cells.get(4).getText();
        String Amount = cells.get(5).getText();
        String DiscountedAmount = cells.get(6).getText();
        String PaymentType = cells.get(7).getText();
        String Status = cells.get(8).getText();
        String Type = cells.get(9).getText();
        String PaymentClient = cells.get(10).getText();
        String Email = cells.get(11).getText();

        // Print the values in a tabular format with borders
        System.out.printf(
            "| %-10s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s|%-10s |%-10s | %-10s | %-10s | %-10s |  %n",
            ID,
            TicketNo,
            VehicleNumber,
            MobileNumber,
            VehicleType,
            Amount,
            DiscountedAmount,
            PaymentType,
            Status,
            Type,
            PaymentClient,
            Email);
      }
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------------");
    }
    extentReports.createTest("selectDeviceMPOS").log(Status.PASS, "Successfully selecting MPOS");
    extentReports.flush();
  }

  @Test(priority = 7)
  public void selectDeviceTypeAndroid() throws InterruptedException {
    WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
    Select select = new Select(fastagDeviceCategoryDropdown);
    select.selectByValue("ANDROID");
    extentReports.createTest("select Device Android").log(Status.PASS, "Successfully selecting ANDROID");
    extentReports.flush();
    Thread.sleep(1000);
  }

  @Test(priority = 8)
  public void validateAndrioidData() {
    WebElement table = driver.findElement(By.id("paymentReportsTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullTransactionNumber = false;
    for (WebElement row : rows) {
      WebElement transactionNumberCell = row.findElements(By.tagName("td")).get(1);

      String transactionNumber = transactionNumberCell.getText();

      if (transactionNumber.equalsIgnoreCase("null")) {
        isAnyNullTransactionNumber = true;
        break;
      }
    }
    if (isAnyNullTransactionNumber) {
      extentReports.createTest("Get ANDROID Data").log(Status.FAIL, "Found a null Transaction number");
    } else {
      extentReports.createTest("Get ANDROID Data").log(Status.PASS, "No null vehicle numbers found");
      extentReports.flush();
    }
  }

  @Test(priority = 9)
  public void selectDeviceTypeIOS() throws InterruptedException {
    WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
    Select select = new Select(fastagDeviceCategoryDropdown);
    select.selectByValue("IOS");
    extentReports.createTest("select Device IOS").log(Status.PASS, "Successfully selecting IOS");
    extentReports.flush();
    Thread.sleep(1000);
  }

  @Test(priority = 10)
  public void validateIOSData() {
    WebElement table = driver.findElement(By.id("paymentReportsTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullTransactionNumber = false;

    for (WebElement row : rows) {

      WebElement transactionNumberCell = row.findElements(By.tagName("td")).get(0);

      String transactionNumber = transactionNumberCell.getText();

      if (transactionNumber.equalsIgnoreCase("null")) {
        isAnyNullTransactionNumber = true;
        break;
      }
    }
    if (isAnyNullTransactionNumber) {
      extentReports.createTest("GET IOS data").log(Status.FAIL, "Found a null Transaction number");
    } else {
      extentReports.createTest("GET IOS data").log(Status.PASS, "No null vehicle numbers found");
      extentReports.flush();
    }
  }

  @Test(priority = 11)
  public void selectDeviceTypeMobileMpos() throws InterruptedException {
    WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
    Select select = new Select(fastagDeviceCategoryDropdown);
    select.selectByValue("MOBILE_MPOS");
    extentReports.createTest("select Device Mobile MPOS").log(Status.PASS, "Successfully selecting MOBILE MPOS");
    extentReports.flush();
    Thread.sleep(3000);
  }

  @Test(priority = 12)
  public void validateMOBILE_MPOSData() {
    WebElement table = driver.findElement(By.id("paymentReportsTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));

    boolean isAnyNullTransactionNumber = false;

    for (WebElement row : rows) {
      // Use index 0 to access the first cell in each row
      WebElement transactionNumberCell = row.findElements(By.tagName("td")).get(0);

      String transactionNumber = transactionNumberCell.getText();

      if (transactionNumber.equalsIgnoreCase("null")) {
        isAnyNullTransactionNumber = true;
        break;
      }
    }

    if (isAnyNullTransactionNumber) {
      extentReports.createTest("GET MobileMPOS data").log(Status.FAIL, "Found a null Transaction number");
    } else {
      extentReports.createTest("GET MOBILE_MPOS data").log(Status.PASS, "No null Transaction numbers found");
    }

    extentReports.flush();
  }

  @AfterClass
  public void tearDown() {
    // Close the WebDriver instance
    if (driver != null) {
      driver.quit();
    }
  }
}