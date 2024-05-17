package com.selenium;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.http.ParseException;
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

// new page in JMC admin portal as POS Hourly Report has been created as per
// bug.no 2213

public class PosHourlyReport extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/POSHourlyReport.html");
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
    public void redirectPosHourlyReportPage() {
        try {
            Thread.sleep(5000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();
            Thread.sleep(2000);
            WebElement posHorlyReportPageLink = driver.findElement(By.className("phr-pg"));
            if (posHorlyReportPageLink.isDisplayed()) {
                posHorlyReportPageLink.click();
                extentReports.createTest("POS Hourly Reports Page ").log(Status.PASS,
                        "Successfully open the POS Hourly Reports page");
            } else {
                extentReports.createTest("POS Hourly Reports Page ").log(Status.INFO,
                        "POS Hourly Reports page not found");
                Assert.fail("POS Hourly Reports page not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Redirect POS Hourly Reports page").log(Status.FAIL,
                    "Failed to open the POS Hourly Reports page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectSite() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
            driver.findElement(By.id("pos_site_name"));
            WebElement site = siteList.get(1);
            String selectedSiteName = site.getText();
            site.click();

            extentReports.createTest("Selected Site ").log(Status.PASS,
                    "Successfully selected site in pos hourly report page: " + selectedSiteName);
        } catch (Exception e) {
            extentReports.createTest("Selected Site ").log(Status.FAIL,
                    "Failed to select the site . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 5)
    public void selectPOSNumber() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = PosHourlyReport.selectPOSNumber(driver);
            driver.findElement(By.id("pos-sl-number"));
            WebElement site = siteList.get(0);
            String selectedPosNumber = site.getText();
            site.click();

            extentReports.createTest("Selected POS Number ").log(Status.PASS,
                    "Successfully selected pos number in pos hourly report page: " +
                            selectedPosNumber);
        } catch (Exception e) {
            extentReports.createTest("Selected POS Number ").log(Status.FAIL,
                    "Failed to select the pos number . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 6)
    public void hourlyReportVehicleType() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("TWO");
            extentReports.createTest("POS hourly Reports vehicle type as Two wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type two wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS hourly Reports Vehicle type as Two wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 7)
    public void selectDateRange() {
        try {
            Thread.sleep(5000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement dateRangeLabel = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dayRangeFilter']")));
            dateRangeLabel.click();

            extentReports.createTest("Selected Start&End Date ").log(Status.PASS,
                    "Successfully selected Start&End Date in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected Start&End Date").log(Status.FAIL,
                    "Failed to select the Start&End Date . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 8)
    public void selectStartDate() {
        try {
            Thread.sleep(5000);
            WebElement startDateElement = driver
                    .findElement(By.xpath("//td[@class='available' and text()='13']"));
            startDateElement.click();
            Thread.sleep(2000);
            extentReports.createTest("Selected Start Date ").log(Status.PASS,
                    "Successfully selected Start Date in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected Start Date").log(Status.FAIL,
                    "Failed to select the Start Date . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 9)
    public void selectEndDate() {
        try {
            Thread.sleep(5000);
            WebElement endDateElement = driver.findElement(By.xpath("//td[@class='available' and text()='29']"));
            endDateElement.click();
            extentReports.createTest("Selected End Date ").log(Status.PASS,
                    "Successfully selected End Date in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected End Date").log(Status.FAIL,
                    "Failed to select the End Date . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 10)
    public void selectStartHour() {
        try {
            Thread.sleep(5000);
            WebElement selectStrtHourElement = driver.findElement(By.className("hourselect"));
            Select select = new Select(selectStrtHourElement);
            select.selectByValue("12");

            extentReports.createTest("Selected Start hour ").log(Status.PASS,
                    "Successfully selected Start hour in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected Start hour Date").log(Status.FAIL,
                    "Failed to select the Start hour Date . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 11)
    public void selectStartMinutes() {
        try {
            Thread.sleep(5000);
            WebElement selectStrtMinuteElement = driver.findElement(By.className("minuteselect"));
            Select select1 = new Select(selectStrtMinuteElement);
            select1.selectByValue("0");
            extentReports.createTest("Selected Start minutes ").log(Status.PASS,
                    "Successfully selected Start minutes in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected Start minutes").log(Status.FAIL,
                    "Failed to select the Start minutes . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 12)
    public void selectStartHourAM() {
        try {
            Thread.sleep(2000);
            WebElement selectStrtMinuteElement = driver.findElement(By.className("ampmselect"));
            Select select1 = new Select(selectStrtMinuteElement);
            select1.selectByValue("AM");
            extentReports.createTest("Selected Start as AM ").log(Status.PASS,
                    "Successfully selected AM in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected Start as AM").log(Status.FAIL,
                    "Failed to select the Start AM . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 13)
    public void selectEndHour() {
        try {
            Thread.sleep(2000);
            WebElement selectEndHourElement = driver
                    .findElement(By.xpath("//div[@class='drp-calendar right']//select[@class='hourselect']"));
            Select select2 = new Select(selectEndHourElement);
            select2.selectByValue("12");

            extentReports.createTest("Selected End hour ").log(Status.PASS,
                    "Successfully selected End hour in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected End hour").log(Status.FAIL,
                    "Failed to select the End hour . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 14)
    public void selectEndMinutes() {
        try {
            Thread.sleep(2000);
            WebElement selectEndMinuteElement = driver
                    .findElement(By.xpath("//div[@class='drp-calendar right']//select[@class='minuteselect']"));
            Select select3 = new Select(selectEndMinuteElement);
            select3.selectByValue("0");

            extentReports.createTest("Selected End minutes ").log(Status.PASS,
                    "Successfully selected End minutes in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected End minutes").log(Status.FAIL,
                    "Failed to select the End minutes . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 15)
    public void selectEndHourPM() {
        try {
            Thread.sleep(2000);
            WebElement selectEndMinuteElement = driver
                    .findElement(By.xpath("//div[@class='drp-calendar right']//select[@class='ampmselect']"));
            Select select3 = new Select(selectEndMinuteElement);
            select3.selectByValue("PM");

            extentReports.createTest("Selected End hour as PM ").log(Status.PASS,
                    "Successfully selected End hour as PM in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Selected End hour as PM").log(Status.FAIL,
                    "Failed to select the End hour as PM . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 16)
    public void clickApplyDatesButton() {
        try {
            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("Clicked apply button ").log(Status.PASS,
                    "Successfully clicked apply button in pos hourly report page: ");

        } catch (Exception e) {
            extentReports.createTest("Clicked apply button ").log(Status.FAIL,
                    "Failed to clicked apply button . Exception: "
                            + e.getMessage() + e.getCause());
        }
    }

    @Test(priority = 17)
    public void submitHourlyReportTwoW() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//button[@id='posSubmitBtn']")).click();

            extentReports.createTest("POS hourly Reports submit button").log(Status.PASS,
                    "Successfully submit the button ");

        } catch (Exception e) {
            extentReports.createTest("POS hourly Reports submit button ").log(Status.FAIL,
                    "Failed to submit the button. Exception: " + e.getCause());

        }
    }

    @Test(priority = 18)
    public void downloadPosHourlyTwoWCSV() {
        try {
            Thread.sleep(4000);
            List<WebElement> dataTableButtons = driver.findElements(By.id("posCsvBtn"));

            WebElement csvButton = null;
            for (WebElement button : dataTableButtons) {
                if (button.getText().equalsIgnoreCase("CSV")) {
                    csvButton = button;
                    break;
                }
            }

            if (csvButton != null) {
                // Check if data is available
                WebElement dataElement = driver.findElement(By.xpath("//*[@id='swal2-content']"));
                if (dataElement.isDisplayed()) {
                    csvButton.click();
                    extentReports.createTest("CSV Download in POS Hourly Report").log(Status.PASS,
                            "CSV file is downloaded successfully");
                } else {
                    extentReports.createTest("CSV Download in POS Hourly Report").log(Status.WARNING,
                            "No data available for CSV download");
                }
            } else {
                extentReports.createTest("CSV Download in POS Hourly Report").log(Status.FAIL, "CSV button not found");
            }

        } catch (Exception e) {
            extentReports.createTest("CSV Download in POS Hourly Report").log(Status.FAIL,
                    "Failed to download CSV file. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 19)
    public void downloadPosHourlyTwoWPDF() {
        try {
            Thread.sleep(3000);
            List<WebElement> dataTableButtons = driver.findElements(By.id("posPdfBtn"));

            WebElement pdfButton = null;
            for (WebElement button : dataTableButtons) {
                if (button.getText().equalsIgnoreCase("PDF")) {
                    pdfButton = button;
                    break;
                }
            }

            if (pdfButton != null) {
                pdfButton.click();

                String mainWindowHandle = driver.getWindowHandle();
                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }
                driver.close();
                driver.switchTo().window(mainWindowHandle);

                extentReports.createTest("PDF Download in POS Hourly Report").log(Status.PASS,
                        "PDF file is downloaded successfully");
            } else {
                extentReports.createTest("PDF Download in POS Hourly Report").log(Status.FAIL,
                        "PDF button not found");
            }

        } catch (Exception e) {
            extentReports.createTest("PDF Download in POS Hourly Report").log(Status.FAIL,
                    "Failed to download PDF file. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 20)
    public void searchTwoWTimeRange() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#posHourlyRevTable_filter input[type='search']"));
            String searchValue = "9-10";
            searchInput.sendKeys(searchValue);
            Thread.sleep(3000);
            searchInput.clear();

            searchInput.click();

            extentReports.createTest("POS hourly Reports search field").log(Status.PASS,
                    "Successfully searched for: " + searchValue);

            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("POS hourly Reports search field").log(Status.FAIL,
                    "Failed to search. Exception: " + e.getCause());
        }
    }

    @Test(priority = 21)
    public void hourlyReportCount() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("posHourlyRevTable_length"));
            Select select = new Select(dropdown);
            Thread.sleep(2000);
            select.selectByValue("25");
            String countValue = "25";

            extentReports.createTest("Select POS hourly Reports Count").log(Status.PASS,
                    "Successfully selected as : " + countValue);
        } catch (Exception e) {
            extentReports.createTest("Select POS hourly Reports Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 22)
    public void posHourlyReportTwoWTable() {
        try {
            WebElement table = driver.findElement(By.id("posHourlyRevTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            if (rows.isEmpty()) {
                extentReports.createTest("In POS Hourly Report table").log(Status.FAIL, "No data available");
                return;
            }

            String tableData = "\n";
            tableData += "| Date   | Hour | Entry Count | Exit count | Cash Amount (₹) | Card Amount (₹) | UPI Amount (₹) | Total Amount (₹) |\n";
            tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 8) {
                    String Date = cells.get(0).getText();
                    String Hour = cells.get(1).getText();
                    String EntryCount = cells.get(2).getText();
                    String Exitcount = cells.get(3).getText();
                    String CashAmount = cells.get(4).getText();
                    String CardAmount = cells.get(5).getText();
                    String UPIAmount = cells.get(6).getText();
                    String TotalAmount = cells.get(7).getText();

                    tableData += String.format("| %-40s | %-13s | %-13s | %-9s | %-12s | %-11s | %-9s | %-13s |\n",
                            Date, Hour, EntryCount, Exitcount, CashAmount, CardAmount, UPIAmount, TotalAmount);
                }
            }

            tableData += "---------------------------------------------------------------------------------------------------------------------------------------\n";
            extentReports.createTest("In POS Hourly Report table").log(Status.PASS,
                    "Displaying Successfully" + tableData);

        } catch (Exception e) {
            extentReports.createTest("In POS Hourly Report table").log(Status.FAIL,
                    "Failed to Display. Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test(priority = 23)
    public void validateTwoWDateFormat() throws java.text.ParseException {
        WebElement table = driver.findElement(By.id("posHourlyRevTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            extentReports.createTest("In POS Hourly Report date format").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnyInvalidDate = false;

        SimpleDateFormat expectedDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        expectedDateFormat.setLenient(false);
        for (WebElement row : rows) {
            WebElement dateCell = row.findElements(By.tagName("td")).get(0);
            String dateText = dateCell.getText();

            try {
                expectedDateFormat.parse(dateText);
            } catch (ParseException e) {
                isAnyInvalidDate = true;
                break;
            }
        }

        if (isAnyInvalidDate) {
            extentReports.createTest("In POS Hourly Report date format").log(Status.FAIL,
                    "Found an invalid date format :");
        } else {
            extentReports.createTest("In POS Hourly Report date format").log(Status.PASS,
                    "All dates are in the correct format");
        }
    }

    @Test(priority = 24)
    public void validateAmountsTwoW() {
        try {
            WebElement table = driver.findElement(By.id("posHourlyRevTable"));
            WebElement tbody = table.findElement(By.tagName("tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            if (rows.isEmpty()) {
                extentReports.createTest("In POS Hourly Report revenue validation").log(Status.FAIL,
                        "No data available");
                return;
            }

            NumberFormat numberFormat = new DecimalFormat("#,##0.00");

            for (WebElement row : rows) {
                List<WebElement> amountCells = row.findElements(By.xpath(".//td[6]"));

                boolean hasNegativeValues = false;
                boolean hasDecimalValues = false;

                String siteName = row.findElement(By.tagName("td")).getText();

                for (WebElement amountCell : amountCells) {
                    String amountText = amountCell.getText();

                    try {
                        double amountValue = numberFormat.parse(amountText).doubleValue();

                        if (amountValue < 0) {
                            hasNegativeValues = true;
                        }

                        if (amountValue % 1 != 0) {
                            hasDecimalValues = true;
                        }

                    } catch (ParseException e) {
                        extentReports.createTest("POS Hourly Report Amount Validation").log(Status.FAIL,
                                "Failed to parse amount: ").log(Status.WARNING,
                                        "Failed to parse amount: " + amountText);
                    }
                }

                if (hasNegativeValues || hasDecimalValues) {
                    extentReports.createTest("POS Hourly Report Amount Validation").log(Status.FAIL,
                            "Row details for site " + siteName + ": " + row.getText()
                                    + " contains negative or decimal values");
                }
            }

            extentReports.createTest("POS Hourly Report Amount Validation").log(Status.PASS, "Validation completed");

        } catch (Exception e) {
            extentReports.createTest("POS Hourly Report Amount Validation").log(Status.FAIL, "Failed to validate");
        }
    }

    @Test(priority = 25)
    public void hourlyReportVehicleTypeFourW() {
        try {
            Thread.sleep(2000);
            WebElement vehicleTypeElement = driver.findElement(By.id("vehicleType"));
            String selectedVehicleType = "FOUR";

            vehicleTypeElement.sendKeys(selectedVehicleType);

            extentReports.createTest("POS Hourly Reports Vehicle Type Selection").log(Status.PASS,
                    "Successfully selected vehicle type: " + selectedVehicleType);

        } catch (Exception e) {
            extentReports.createTest("POS Hourly Reports Vehicle Type Selection").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());
        }
    }

    @Test(priority = 26)
    public void submitHourlyReportFourW() {
        submitHourlyReportTwoW();
    }

    @Test(priority = 27)
    public void downloadPosHourlyFourWCSV() {
        downloadPosHourlyTwoWCSV();
    }

    @Test(priority = 28)
    public void downloadPosHourlyFourWPDF() {
        downloadPosHourlyTwoWPDF();
    }

    @Test(priority = 29)
    public void searchFourWTimeRange() {
        searchTwoWTimeRange();
    }

    @Test(priority = 30)
    public void posHourlyReportFourWTable() {
        posHourlyReportTwoWTable();
    }

    @Test(priority = 31)
    public void validateFourWDateFormat() throws java.text.ParseException {
        validateTwoWDateFormat();

    }

    @Test(priority = 32)
    public void validateAmountsFourW() {
        validateAmountsTwoW();
    }

    @Test(priority = 33)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("POS Hourly Report LOGOUT ").log(Status.PASS,
                    "Successfully LOGOUT from the Portal");
        } catch (Exception e) {
            extentReports.createTest("POS Hourly Report LOGOUT").log(Status.FAIL,
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

    public static List<WebElement> selectPOSNumber(WebDriver driver) throws InterruptedException {
        driver.findElement(By.id("pos-sl-number"));
        driver.findElement(By.id("btn-pos-view")).click();
        Thread.sleep(3000);
        List<WebElement> posList = driver.findElements(By.className("ui-menu-item-wrapper"));
        System.out.println(posList.size());

        for (WebElement list : posList) {
            System.out.println(list.getText());
        }
        return posList;
    }
}
