package com.selenium;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class ReservationReports extends BaseTestReport {
  static WebDriver driver;
  ServerCredentials serverconfig;

  @BeforeClass
  public void setUp() {

    // extentReports = new ExtentReports();
    // spark = new ExtentSparkReporter("results/ReservationReports.html");
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
  public void redirectReservationReportsPage() {
    try {
      Thread.sleep(2000);
      driver
          .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
          .click();
      driver.findElement(By.className("rr-pg")).click();
      extentReports.createTest("Reservation Reports ").log(Status.PASS, "Successfully Open Reservation Reports Page");
      Thread.sleep(2000);
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports ").log(Status.FAIL,
          "Failed to Open the Reservation Reports . Exception: " + e.getCause());
    }
  }

  @Test(priority = 4)
  public void selectReservationSitesList() {
    try {
      List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
      WebElement siteInput = driver.findElement(By.id("pos_site_name"));
      WebElement site = siteList.get(1);
      site.click();
      String selectedSiteName = siteInput.getText();

      extentReports.createTest("Dropdown open and select the site in Reservation Reports page ")
          .log(Status.PASS, "Successfully open dropdown and selected the : " + selectedSiteName);

    } catch (Exception e) {
      extentReports.createTest("Dropdown open and select the site in Reservation Reports page ")
          .log(Status.FAIL,
              "Failed to open dropdown and selected  . Exception: " + e.getMessage());
    }
  }

  @Test(priority = 5)
  public void reservationReportDateEnabled() {
    Dashboard.revenueDatesEnabled(driver, extentReports);
  }

  @Test(priority = 6)
  public void selectReservationStartDate() {
    Dashboard.selectDashboardStartDate(driver, extentReports);
  }

  @Test(priority = 7)
  public void selectReservationEndDate() {
    Dashboard.selectDashboardEndDate(driver, extentReports);
  }

  @Test(priority = 8)
  public void selectCountReservations() {
    try {
      Thread.sleep(2000);
      WebElement dropdown = driver.findElement(By.name("reservationTable_length"));
      Select select = new Select(dropdown);
      select.selectByValue("25");
      extentReports.createTest("ReservationTable_length").log(Status.PASS, "Successfully selected as");
    } catch (Exception e) {
      extentReports.createTest("ReservationTable_length").log(Status.FAIL,
          "Failed to select the count. Exception: " + e.getCause());

    }
  }

  @Test(priority = 9)
  public void selectReservations() {
    try {
      Thread.sleep(2000);
      WebElement transactionType = driver.findElement(By.id("transType"));
      transactionType.sendKeys("Reservations");
      String selectedType = transactionType.getText();

      extentReports.createTest("Reservation Reports select reservations ").log(Status.PASS,
          "Successfully selecting the reservations : " + selectedType);
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports select reservations ").log(Status.FAIL,
          "Failed to selecting reservations . Exception: " + e.getCause());
    }
  }

  @Test(priority = 10)
  public void getreservationsList() {
    try {
      WebElement table = driver.findElement(By.id("reservationTable"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      System.out.println(
          "------------------------------------------------------------------------------------------------------------------");
      System.out.println(
          "| Transaction ID   | Transaction Type | Vehicle Type | Vehicle Number | Status |Created Time| Expired Time |Amount |");
      System.out.println(
          "------------------------------------------------------------------------------------------------------------------");
      for (WebElement row : rows) {
        List<WebElement> cells = row.findElements(By.tagName("td"));

        if (cells.size() >= 8) {
          String TransactionID = cells.get(0).getText();
          String TransactionType = cells.get(1).getText();
          String VehicleType = cells.get(2).getText();
          String VehicleNumber = cells.get(3).getText();
          String Status = cells.get(4).getText();
          String CreatedTime = cells.get(5).getText();
          String ExpiredTime = cells.get(6).getText();
          String Amount = cells.get(7).getText();
          System.out.printf(
              "| %-10s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s|%-10s | %n",
              TransactionID,
              TransactionType,
              VehicleType,
              VehicleNumber,
              Status,
              CreatedTime,
              ExpiredTime,
              Amount);
        }
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------------");
      }
      extentReports.createTest("Reservation Reports of reservations").log(Status.PASS,
          "Successfully displaying the reservations ");
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports of reservations ").log(Status.FAIL,
          "Failed to display the reservations . Exception: " + e.getCause());
    }
  }

  @Test(priority = 11)
  public void verify_ReservationsTiming() {
    try {
      WebElement table = driver.findElement(By.id("reservationTable"));
      WebElement tbody = table.findElement(By.tagName("tbody"));
      List<WebElement> cells = tbody.findElements(By.tagName("tr"));

      if (cells.size() >= 6) {
        {
          WebElement CreatedTimeCell = cells.get(5);
          WebElement ExpiredTimeCell = cells.get(6);
          WebElement vehicleNumberCell = cells.get(3);

          String ReservationTime = CreatedTimeCell.getText();
          String expiredTime = ExpiredTimeCell.getText();
          String vehicleNumber = vehicleNumberCell.getText();

          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date ReservationDateTime = sdf.parse(ReservationTime);
          Date expiryDateTime = sdf.parse(expiredTime);

          if (ReservationDateTime.after(expiryDateTime)) {
            extentReports.createTest("In Reservations - Compare Entry and Exit Date")
                .log(Status.FAIL, "Entry date is after exit date. Entry Date: " + ReservationTime
                    + ", Exit Date: " + expiryDateTime + ", Vehicle Number: " + vehicleNumber);

          } else {
            long duration = expiryDateTime.getTime() - ReservationDateTime.getTime();
            if (duration < 0) {
              extentReports.createTest("In Reservations - Compare Entry and Exit Date")
                  .log(Status.FAIL, "Negative duration detected. Entry Date: " + ReservationTime
                      + ", Exit Date: " + expiryDateTime + ", Vehicle Number: " + vehicleNumber);

            } else if (duration == 0) {
              extentReports.createTest("In Reservations - Compare Entry and Exit Date")
                  .log(Status.PASS, "Entry date is equal to exit date. Entry Date: " + ReservationTime
                      + ", Exit Date: " + expiryDateTime + ", Vehicle Number: " + vehicleNumber);
            }
          }
        }
      }
      extentReports.createTest("In Reservatoions - Compare Entry and Exit Date/Time").log(Status.PASS,
          "All entry times are before or equal to exit times.");
    } catch (

    Exception e) {
      extentReports.createTest("In Reservatoions - Compare Entry and Exit Date/Time").log(Status.FAIL,
          "An exception occurred while comparing date/time. Exception: " + e.getCause());
      Assert.fail("Reservation timings verification Test failed due to an exception: " + e.getMessage());
    }
  }

  @Test(priority = 13)
  public void selectPasses() {
    try {
      Thread.sleep(2000);
      WebElement transType = driver.findElement(By.id("transType"));
      transType.sendKeys("Passes");
      String value = transType.getText();
      Thread.sleep(2000);
      extentReports.createTest("Reservation Reports select Passes ").log(Status.PASS,
          "Successfully selecting the passes : " + value);
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports select Passes ").log(Status.FAIL,
          "Failed to selecting passes . Exception: " + e.getCause());
    }
  }

  @Test(priority = 14)
  public void getPassesList() {
    try {
      WebElement table = driver.findElement(By.id("reservationTable"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      if (rows.isEmpty()) {
        extentReports.createTest("Audit trail Passes Reports table ").log(Status.PASS,
            "No User data available");
        return;
      }
      System.out.println(
          "------------------------------------------------------------------------------------------------------------------");
      System.out.println(
          "| Transaction ID   | Transaction Type | Vehicle Type | Vehicle Number | Status |Created Time| Expired Time |Amount |");
      System.out.println(
          "------------------------------------------------------------------------------------------------------------------");
      for (WebElement row : rows) {
        List<WebElement> cells = row.findElements(By.tagName("td"));

        if (cells.size() >= 8) {
          String TransactionID = cells.get(0).getText();
          String TransactionType = cells.get(1).getText();
          String VehicleType = cells.get(2).getText();
          String VehicleNumber = cells.get(3).getText();
          String Status = cells.get(4).getText();
          String CreatedTime = cells.get(5).getText();
          String ExpiredTime = cells.get(6).getText();
          String Amount = cells.get(7).getText();

          System.out.printf(
              "| %-10s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s|%-10s | %n",
              TransactionID,
              TransactionType,
              VehicleType,
              VehicleNumber,
              Status,
              CreatedTime,
              ExpiredTime,
              Amount);
        }
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------------");
        extentReports.createTest("Reservation Reports of passes ").log(Status.PASS,
            "Successfully displaying the passes ");
      }
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports of passes").log(Status.FAIL,
          "Failed to display the passes . Exception: " + e.getCause());
    }
  }

  @Test(priority = 15)
  public void verify_PassBookingTimings() {
    try {
      WebElement table = driver.findElement(By.id("reservationTable"));
      WebElement tbody = table.findElement(By.tagName("tbody"));
      List<WebElement> cells = tbody.findElements(By.tagName("tr"));

      if (cells.size() >= 6) {
        {
          WebElement CreatedTimeCell = cells.get(5);
          WebElement ExpiredTimeCell = cells.get(6);
          WebElement vehicleNumberCell = cells.get(3);

          String ReservationTime = CreatedTimeCell.getText();
          String expiredTime = ExpiredTimeCell.getText();
          String vehicleNumber = vehicleNumberCell.getText();

          SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");

          Date reservationDate = sdf.parse(ReservationTime.split(" ")[0]);
          Date expiryDate = sdf.parse(expiredTime.split(" ")[0]);

          if (reservationDate.after(expiryDate)) {
            extentReports.createTest("In Passes - Compare Entry and Exit Date")
                .log(Status.FAIL, "Entry date is after exit date. Entry Date: " + ReservationTime
                    + ", Exit Date: " + expiredTime + ", Vehicle Number: " + vehicleNumber);
          } else {
            long duration = expiryDate.getTime() - reservationDate.getTime();
            if (duration < 0) {
              extentReports.createTest("In Passes - Compare Entry and Exit Date")
                  .log(Status.FAIL, "Negative duration detected. Entry Date: " + ReservationTime
                      + ", Exit Date: " + expiredTime + ", Vehicle Number: " + vehicleNumber);
            } else if (duration == 0) {
              extentReports.createTest("In Passes - Compare Entry and Exit Date")
                  .log(Status.PASS, "Entry date is equal to exit date. Entry Date: " + ReservationTime
                      + ", Exit Date: " + expiredTime + ", Vehicle Number: " + vehicleNumber);
            }
          }
        }
      }
      extentReports.createTest("In Passes - Compare Entry and Exit Date/Time").log(Status.PASS,
          "All entry times are before or equal to exit times.");
    } catch (Exception e) {
      extentReports.createTest("In Passes - Compare Entry and Exit Date/Time").log(Status.FAIL,
          "An exception occurred while comparing date/time. Exception: " + e.getCause());
      Assert.fail("Pass Booking timings verification Test failed due to an exception: " + e.getMessage());
    }
  }

  @Test(priority = 17)
  public void selectGuestpasses() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.id("transType")).sendKeys("gPasses");
      Thread.sleep(2000);
      extentReports.createTest("Reservation Reports select guest passes ").log(Status.PASS,
          "Successfully selecting the guest passes ");
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports select guest passes ").log(Status.FAIL,
          "Failed to selecting guest passes . Exception: " + e.getCause());
    }
  }

  // @Test(priority = 18)
  public void selectgPassesSitesList() {
    try {
      List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
      WebElement siteInput = driver.findElement(By.id("pos_site_name"));
      WebElement site = siteList.get(0);
      String selectedSiteName = siteInput.getText();
      site.click();

      extentReports.createTest("Dropdown open and select the site in Reservation Reports page ")
          .log(Status.PASS, "Successfully open dropdown and selected the : " + selectedSiteName);

    } catch (Exception e) {
      extentReports.createTest("Dropdown open and select the site in Reservation Reports page ")
          .log(Status.FAIL,
              "Failed to open dropdown and selected  . Exception: " + e.getMessage());
    }
  }

  @Test(priority = 19)
  public void selectgPassesStartDate() {
    selectReservationStartDate();
  }

  @Test(priority = 20)
  public void selectPassesEndDate() {
    selectReservationEndDate();
  }

  @Test(priority = 21)
  public void getGuestPassesList() {
    try {
      WebElement table = driver.findElement(By.id("reservationTable"));
      List<WebElement> rows = table.findElements(By.tagName("tr"));
      if (rows.isEmpty()) {
        extentReports.createTest("Audit trail Guest Passes Reports table ").log(Status.PASS,
            "No User data available");
        return;
      }
      Thread.sleep(5000);
      System.out.println(
          "------------------------------------------------------------------------------------------------------------------");
      System.out.println(
          "| PR ID   | Ticket Number | Vehicle Type | Pass Count | Start Date | End Date | Amount | Mobile |");
      System.out.println(
          "------------------------------------------------------------------------------------------------------------------");
      for (WebElement row : rows) {
        List<WebElement> cells = row.findElements(By.tagName("td"));

        if (cells.size() >= 8) {
          String PRID = cells.get(0).getText();
          String TicketNumber = cells.get(1).getText();
          String VehicleType = cells.get(2).getText();
          String PassCount = cells.get(3).getText();
          String StartDate = cells.get(4).getText();
          String EndDate = cells.get(5).getText();
          String Amount = cells.get(6).getText();
          String Mobile = cells.get(7).getText();
          System.out.printf(
              "| %-10s | %-10s | %-10s | %-10s |%-10s |%-10s |%-10s|%-10s | %n",
              PRID,
              TicketNumber,
              VehicleType,
              PassCount,
              StartDate,
              EndDate,
              Amount,
              Mobile);
        }
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------------");
      }
      extentReports.createTest("Reservation Reports of guest passes").log(Status.PASS,
          "Successfully displaying the guest passes ");

    } catch (Exception e) {
      extentReports.createTest("Reservation Reports of guest passes ").log(Status.FAIL,
          "Failed to display the guest passes . Exception: " + e.getCause());
    }
  }

  @Test(priority = 22)
  public void verify_GuestPass_EntryExitDateTime() {
    try {
      Thread.sleep(5000);
      WebElement table = driver.findElement(By.id("reservationTable"));
      WebElement tbody = table.findElement(By.tagName("tbody"));
      List<WebElement> cells = tbody.findElements(By.tagName("tr"));

      if (cells.size() >= 6) {
        {
          WebElement entryTimeCell = cells.get(3);
          WebElement exitTimeCell = cells.get(4);
          WebElement vehicleNumberCell = cells.get(0);

          String entryTime = entryTimeCell.getText();
          String exitTime = exitTimeCell.getText();
          String vehicleNumber = vehicleNumberCell.getText();

          SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

          Date reservationDate = sdf.parse(entryTime.split(" ")[0]);
          Date expiryDate = sdf.parse(exitTime.split(" ")[0]);

          if (reservationDate.after(expiryDate)) {
            extentReports.createTest("In Guest passes  - Compare Entry and Exit Date/Time")
                .log(Status.FAIL, "Entry time is after exit time. Entry Time: " + entryTime
                    + ", Exit Time: " + exitTime + ", Vehicle Number: " + vehicleNumber);
          } else {
            long duration = expiryDate.getTime() - reservationDate.getTime();
            if (duration < 0) {
              extentReports.createTest("In Guest passes - Compare Entry and Exit Date/Time")
                  .log(Status.FAIL, "Negative duration detected. Entry Time: " + entryTime
                      + ", Exit Time: " + exitTime + ", Vehicle Number: " + vehicleNumber);
            }
          }
        }
      }

      extentReports.createTest("In Guest passes - Compare Entry and Exit Date/Time").log(Status.PASS,
          "All entry times are before or equal to exit times.");
    } catch (Exception e) {
      extentReports.createTest("In guest passes - Compare Entry and Exit Date/Time").log(Status.FAIL,
          "An exception occurred while comparing date/time. Exception: " + e.getCause());
    }
  }

  @Test(priority = 24)
  public void guestPassesViewButton() {
    try {
      Thread.sleep(2000);
      WebElement viewElement = driver
          .findElement(By.xpath("//td/button[@class='btn btn-outline-info btn-sm guestPassBtn']"));

      viewElement.click();
      Thread.sleep(3000);

      extentReports.createTest("To view the guest pass validation tickets").log(Status.PASS,
          "Successfully clicking the view details button");
    } catch (Exception e) {

      extentReports.createTest("To view the guest pass validation tickets").log(Status.FAIL,
          "Failed to click the view details button. Exception: " + e.getMessage());
    }
  }

  @Test(priority = 25)
  public void guestPassPopUpClose() {
    try {
      Thread.sleep(2000);
      driver.findElement(By.className("close")).click();
      extentReports.createTest("In reservation reports page POP Up").log(Status.PASS,
          "Successfully closed the pop up to view guest passes");
    } catch (Exception e) {
      extentReports.createTest("In reservation reports page POP Up ").log(Status.FAIL,
          "Failed to close the pop up to view guest passes. Exception: " + e.getCause());
    }
  }

  @Test(priority = 26)
  public void logout() {
    try {
      Thread.sleep(5000);
      driver.findElement(By.id("userName")).click();
      driver.findElement(By.id("btnLogout")).click();
      System.out.println("Logout from reservation Reports page");
      extentReports.createTest("Reservations Reports Page LogOut ").log(Status.PASS,
          "Successfully LogOut from the Portal");
    } catch (Exception e) {
      extentReports.createTest("Reservation Reports page LogOut").log(Status.FAIL,
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

  public static List<WebElement> selectReservationSitesList(WebDriver driver) throws InterruptedException {
    driver.findElement(By.id("pos_site_name"));
    driver.findElement(By.id("btn-site-view")).click();
    Thread.sleep(3000);
    List<WebElement> sitesList = driver.findElements(By.className("ui-menu-item-wrapper"));
    System.out.println(sitesList.size());

    for (WebElement list : sitesList) {
      System.out.println(list.getText());
    }
    return sitesList;
  }
}
