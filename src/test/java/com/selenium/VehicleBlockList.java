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

public class VehicleBlockList extends BaseTestReport {
  WebDriver driver;
  ServerCredentials serverconfig;

  @BeforeClass
  public void setUp() {

    // extentReports = new ExtentReports();
    // spark = new ExtentSparkReporter("results/VehicleBlockList.html");
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
  public void redirectVehicleBlockListPage() {
    try {
      Thread.sleep(2000);
      driver
          .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
          .click();
      driver.findElement(By.className("vb-pg")).click();
      extentReports.createTest("Redirect Vehicle BlockList Reports page ").log(Status.PASS,
          "Successfully Open vehicle blocklist Reports Page");
      Thread.sleep(2000);
    } catch (Exception e) {
      extentReports.createTest("Redirect Vehicle BlockList Reports page").log(Status.FAIL,
          "Failed to Open the vehicle blocklist Reports page . Exception: " + e.getCause());
    }
  }

  @Test(priority = 4)
  public void viewBlocklistSiteList() {
    try {
      Thread.sleep(3000);
      List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
      driver.findElement(By.id("pos_site_name"));
      WebElement site = siteList.get(1);
      String selectedSiteName = site.getText();
      site.click();

      extentReports.createTest("Displaying the sites list in Vehicle Blocklist ").log(Status.PASS,
          " Successfully displaying the sites list : " + selectedSiteName);
    } catch (Exception e) {
      extentReports.createTest("Displaying the sites list in Vehicle Blocklist ").log(Status.FAIL,
          "Failed displaying the sites list. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 5)
  public void vehicleBlockListDates() {
    Dashboard.revenueDatesEnabled(driver, extentReports);
  }

  @Test(priority = 6)
  public void selectBlocklistStartDate() {
    Dashboard.selectDashboardStartDate(driver, extentReports);
  }

  @Test(priority = 7)
  public void selectBlockListEndDate() {
    Dashboard.selectDashboardEndDate(driver, extentReports);
  }

  @Test(priority = 8)
  public void vehicleBlockDayCsv() {
    try {
      Thread.sleep(1000);
      driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]")).click();

      WebElement downloadLink = driver.findElement(By.id("vehicleBlockcsv"));
      downloadLink.click();
      System.out.println("CSV Report Downloaded Successfully");

      extentReports.createTest("In vehicle Blocklist Csv file ").log(Status.PASS,
          "Successfully download the day CSV file in vehicle blocklist");
    } catch (Exception e) {
      extentReports.createTest("In vehicle Blocklist Csv file ").log(Status.FAIL,
          "Failed to download the day CSV file in ehicle blocklist. Exception: " + e.getCause());

    }
  }

  @Test(priority = 9)
  public void vehicleBlockDayPdf() {
    try {
      Thread.sleep(2000);
      WebElement downloadLink = driver.findElement(By.id("vehicleBlockpdf"));
      downloadLink.click();
      System.out.println("PDF Report Downloaded Successfully");

      extentReports.createTest("In vehicle Blocklist PDF file ").log(Status.PASS,
          "Successfully download the day PDF file in vehicle blocklist");
    } catch (Exception e) {
      extentReports.createTest("In vehicle BlockList PDF file ").log(Status.FAIL,
          "Failed to download day the PDF file in vehicle blocklist. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 10)
  public void vehicleBlockWeekCsv() {
    driver.findElement(By.xpath("//*[@id='dayRangeFilter']/label[3]")).click();
    vehicleBlockDayCsv();
  }

  @Test(priority = 11)
  public void vehicleBlockWeekPdf() {
    vehicleBlockDayPdf();
  }

  @Test(priority = 12)
  public void vehicleBlockMonthCsv() {
    driver.findElement(By.xpath("//*[@id='dayRangeFilter']/label[4]")).click();
    vehicleBlockDayCsv();
  }

  @Test(priority = 13)
  public void vehicleBlockMonthPdf() {
    vehicleBlockDayPdf();
  }

  @Test(priority = 14)
  public void selectListCount() {
    try {
      Thread.sleep(2000);
      WebElement dropdown = driver.findElement(By.name("blockListTable_length"));
      Select select = new Select(dropdown);
      select.selectByValue("25");
      String dropDownList = dropdown.getText();
      extentReports.createTest("BlockList Table length").log(Status.PASS, "Successfully selected as" + dropDownList);
    } catch (Exception e) {
      extentReports.createTest("BlockList Table length").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());

    }
  }

  @Test(priority = 15)
  public void vehicleBlockListTable() {
    try {
      WebElement table = driver.findElement(By.id("blockListTable"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      if (rows.isEmpty()) {
        extentReports.createTest("In Site Reports Device status table ").log(Status.FAIL, "No data available");
        return;
      }
      System.out.println(
          "----------------------------------------Vehicle Blocklist-------------------------------------------------------");
      System.out.println(
          "| Date  | User | Mobile | Vehicle Number | Amount | Status | Action |");
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------------");

      for (WebElement row : rows) {
        List<WebElement> cells = row.findElements(By.tagName("td"));

        if (cells.size() >= 7) {

          String Date = cells.get(0).getText();
          String User = cells.get(1).getText();
          String Mobile = cells.get(2).getText();
          String VehicleNumber = cells.get(3).getText();
          String Amount = cells.get(4).getText();
          String Status = cells.get(5).getText();
          String Action = cells.get(6).getText();

          System.out.printf("| %-20s | %-10s | %-10s | %-10s |%-10s |%n", Date, User,
              Mobile, VehicleNumber, Amount, Status, Action);
        }
        System.out.println(
            "----------------------------------------------------------------------------------------------------------------");

        Thread.sleep(3000);
      }
      extentReports.createTest("Vehicle Blocklist Table ").log(Status.PASS, "Displaying Successfully");
    } catch (Exception e) {
      extentReports.createTest("Vehicle Blocklist Table ").log(Status.FAIL,
          "Failed to Displaying. Exception: " + e.getCause());
    }
  }

  @Test(priority = 16)
  public void searchVehicleNumber() {
    try {
      Thread.sleep(4000);
      WebElement searchInput = driver.findElement(By.cssSelector("#blockListTable_filter input[type='search']"));
      searchInput.clear();
      searchInput.sendKeys("DS24FR455");

      Thread.sleep(4000);

      WebElement searchResult = driver.findElement(By.cssSelector("#blockListTable tbody td"));

      if (searchResult.getText().equals("Data not available")) {
        extentReports.createTest("Site Reports Device status tab search as DS24FR455").log(Status.FAIL,
            "Data not available for the specified vehicle number");
      } else {
        extentReports.createTest("Site Reports Device status tab search as DS24FR455").log(Status.PASS,
            "Successfully searched for the Vehicle number");
      }
    } catch (Exception e) {
      extentReports.createTest("Site Reports Device status tab search as DS24FR455").log(Status.FAIL,
          "Failed to search the Vehicle Number. Exception: " + e.getCause());
    }
  }

  @Test(priority = 17)
  public void searchBlockListMobileNumber() {
    try {
      Thread.sleep(4000);
      WebElement searchInput = driver.findElement(By.cssSelector("#blockListTable_filter input[type='search']"));
      searchInput.clear();
      searchInput.sendKeys("7842137994");

      Thread.sleep(4000);

      WebElement searchResult = driver.findElement(By.cssSelector("#blockListTable tbody td"));

      if (searchResult.getText().equals("Data not available")) {
        extentReports.createTest("Vehicle BlockList page  search as 7842137994").log(Status.FAIL,
            "Data not available for the specified vehicle number");
      } else {
        extentReports.createTest("Vehicle BlockList page search as 7842137994").log(Status.PASS,
            "Successfully searched for the Vehicle number");
      }
    } catch (Exception e) {
      extentReports.createTest("Vehicle BlockList page search as 7842137994").log(Status.FAIL,
          "Failed to search the Mobile Number. Exception: " + e.getCause());
    }
  }

  @Test(priority = 18)
  public void validateBlockListDate() {
    WebElement table = driver.findElement(By.id("blockListTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Vehicle BlockList page Date ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullDate = false;

    for (WebElement row : rows) {

      WebElement DateCell = row.findElements(By.tagName("td")).get(0);
      String Date = DateCell.getText();

      if (Date.equalsIgnoreCase("null")) {
        isAnyNullDate = true;
        break;
      }
    }
    if (isAnyNullDate) {
      extentReports.createTest("In Vehicle BlockList table Date").log(Status.FAIL, "Found a null Date");
    } else {
      extentReports.createTest("In Vehicle BlockList table Date").log(Status.PASS, "No null Date found");
    }
  }

  @Test(priority = 19)
  public void validateBlockListUser() {
    WebElement table = driver.findElement(By.id("blockListTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Vehicle BlockList page User ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullUser = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() >= 2) {
        WebElement UserCell = cells.get(1);
        String User = UserCell.getText();

        if (User.equalsIgnoreCase("null")) {
          isAnyNullUser = true;
          break;
        }
      }
      if (isAnyNullUser) {
        extentReports.createTest("In Vehicle BlockList table User").log(Status.FAIL, "Found a null User");
      } else {
        extentReports.createTest("In Vehicle BlockList table User").log(Status.PASS, "No null User found");
      }
    }
  }

  @Test(priority = 20)
  public void validateBlockListMobileNum() {
    WebElement table = driver.findElement(By.id("blockListTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Vehicle BlockList page MobileNum ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullMobileNum = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() >= 3) {
        WebElement MobileNumCell = cells.get(2);
        String MobileNum = MobileNumCell.getText();

        if (MobileNum.equalsIgnoreCase("null")) {
          isAnyNullMobileNum = true;
          break;
        }
      }
      if (isAnyNullMobileNum) {
        extentReports.createTest("In Vehicle BlockList table MobileNum").log(Status.FAIL, "Found a null MobileNum");
      } else {
        extentReports.createTest("In Vehicle BlockList table MobileNum").log(Status.PASS, "No null MobileNum found");
      }
    }
  }

  @Test(priority = 21)
  public void validateBlockListVehicleNum() {
    WebElement table = driver.findElement(By.id("blockListTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Vehicle BlockList page VehicleNum ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyNullVehicleNum = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() >= 4) {
        WebElement VehicleNumCell = cells.get(3);
        String VehicleNum = VehicleNumCell.getText();

        if (VehicleNum.equalsIgnoreCase("null")) {
          isAnyNullVehicleNum = true;
          break;
        }
      }
      if (isAnyNullVehicleNum) {
        extentReports.createTest("In Vehicle BlockList table VehicleNum").log(Status.FAIL, "Found a null VehicleNum");
      } else {
        extentReports.createTest("In Vehicle BlockList table VehicleNum").log(Status.PASS, "No null VehicleNum found");
      }
    }
  }

  @Test(priority = 22)
  public void validateBlockListAmount() {
    WebElement table = driver.findElement(By.id("blockListTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Vehicle BlockList page Amount ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyZeroAmount = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() >= 5) {
        WebElement AmountCell = cells.get(4);
        String Amount = AmountCell.getText();

        if (Amount.equalsIgnoreCase("null")) {
          isAnyZeroAmount = true;
          break;
        }
      }
      if (isAnyZeroAmount) {
        extentReports.createTest("In Vehicle BlockList table Amount").log(Status.FAIL, "Found a Zero Amount");
      } else {
        extentReports.createTest("In Vehicle BlockList table Amount").log(Status.PASS, "No Zero Amount found");
      }
    }
  }

  @Test(priority = 23)
  public void validateBlockListStatus() {
    WebElement table = driver.findElement(By.id("blockListTable"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
    if (rows.isEmpty()) {
      extentReports.createTest("In Vehicle BlockList page Status ").log(Status.FAIL, "No data available");
      return;
    }

    boolean isAnyUnBlockStatus = false;

    for (WebElement row : rows) {

      List<WebElement> cells = row.findElements(By.tagName("td"));
      if (cells.size() >= 6) {
        WebElement StatusCell = cells.get(5);
        String Status = StatusCell.getText();

        if (Status.equalsIgnoreCase("UnBlock")) {
          isAnyUnBlockStatus = true;
          break;
        }
      }
      if (isAnyUnBlockStatus) {
        extentReports.createTest("In Vehicle BlockList table Status").log(Status.FAIL, "Found a UnBlock Status");
      } else {
        extentReports.createTest("In Vehicle BlockList table Status").log(Status.PASS, "No UnBlock Status found");
      }
    }
  }

  @Test(priority = 24)
  public void selectAction() {
    try {
      Thread.sleep(5000);
      driver.findElement(By.xpath("//*[@id='blockListTable']/tbody/tr[1]/td[7]")).click();
      extentReports.createTest("In Vehicle BlockList table Action").log(Status.PASS,
          "Successfully Clicking the Action");
      Thread.sleep(2000);
    } catch (Exception e) {
      extentReports.createTest("In Vehicle BlockList table Action").log(Status.FAIL,
          "Failed to Clicking the Action . Exception: " + e.getMessage());
    }
  }

  @Test(priority = 25)
  public void logout() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.id("userName")).click();
      driver.findElement(By.id("btnLogout")).click();
      extentReports.createTest("Vehicle BlockList LogOut ").log(Status.PASS,
          "Successfully LogOut from the Portal");
    } catch (Exception e) {
      extentReports.createTest("Vehicle BlockList  LogOut").log(Status.FAIL,
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
