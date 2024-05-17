package com.selenium;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.model.ServerCredentials;

public class MobileReports extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        extentReports = new ExtentReports();
        spark = new ExtentSparkReporter("results/MobileReports.html");
        extentReports.attachReporter(spark);
        extentTest = extentReports.createTest("demo");

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
    public void reportsAnalyticsPage() {
        try {
            Thread.sleep(2000);
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
    public void mobileReportsPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("mr-pg")).click();
            extentReports.createTest("Mobile Reports Page  ").log(Status.PASS,
                    "Successfully Open Mobile Reports Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Page  ").log(Status.FAIL,
                    "Failed to Open the Mobile Reports . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void mobileReportsOneDay() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]"))
                    .click();

            extentReports.createTest("Mobile Reports Revenue Dates Enabled ").log(Status.PASS,
                    "Successfully selecting the dates day");
        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Revenue Dates Enabled ").log(Status.FAIL,
                    "Failed to select the dates day. Exception: " + e.getCause());

        }
    }

    @Test(priority = 6)
    public void selectAndroid() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("mobile-select")).sendKeys("ANDROID");
            extentReports.createTest("Mobile Reports vehicle type as ANDROID").log(Status.PASS,
                    "Successfully selecting vehicle type ANDROID");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as ANDROID").log(Status.FAIL,
                    "Failed to select the ANDROID. Exception: " + e.getCause());

        }
    }

    @Test(priority = 7)
    public void selectOneDayVehicleTypeAll() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleType")));
            Select select = new Select(selectElement);
            select.selectByValue("ALL");
            extentReports.createTest("Mobile Reports vehicle type as All").log(Status.PASS,
                    "Successfully selecting vehicle type All");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as All").log(Status.FAIL,
                    "Failed to select the All. Exception: " + e.getCause());

        }
    }

    @Test(priority = 8)
    public void dayRSubmitButton() {
        try {
            driver.findElement(By.xpath("//button[@id='mobileRevenueBtn']")).click();
            Thread.sleep(1000);
            extentReports.createTest("In Mobile reports day revenue click the submit button").log(Status.PASS,
                    "Successfully clicking the submit button in Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In Mobile reports day revenue click the submit button").log(Status.FAIL,
                    "Failed to click the submit button in Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void mobileDayRViewDetails() {
        try {
            driver
                    .findElement(By.xpath("//div[@id='mobileRevenueForm']/div/div[3]/button[2]"))
                    .click();
            Thread.sleep(2000);
            extentReports.createTest("In Mobile reports click the view details button of day revenue").log(Status.PASS,
                    "Successfully clicking the view details button in Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In Mobile reports click the view details button of day revenue").log(Status.FAIL,
                    "Failed to click view details button in Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void dayRPopUPClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("close")).click();
            Thread.sleep(2000);
            extentReports.createTest("In Mobile reports POP Up closed").log(Status.PASS,
                    "Successfully closed the pop up in Mobile reports");
        } catch (Exception e) {
            extentReports.createTest("In Mobile reports POP Up closed ").log(Status.FAIL,
                    "Failed to close the pop up in Mobile Reports tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void getDayMobileRevenue() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-Day Revenue All vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Day Revenue in Mobile Reports is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Mobile Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 12)
    public void selectVehicleTypeTwo() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleType")));
            Select select = new Select(selectElement);
            select.selectByValue("TWO");
            extentReports.createTest("Mobile Reports vehicle type as TWO wheeler of a day ").log(Status.PASS,
                    "Successfully selecting vehicle type TWO wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as TWO wheeler of a day").log(Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 13)
    public void dayRSubmitButtonTwo() {
        dayRSubmitButton();
    }

    @Test(priority = 14)
    public void dayRevenueViewDetailsTWO() {
        mobileDayRViewDetails();
    }

    @Test(priority = 15)
    public void dayRPopUPCloseTWO() {
        dayRPopUPClose();
    }

    @Test(priority = 16)
    public void getDayRevenueTWO() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-Day Revenue TWO wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Day Revenue in Mobile Reports is Displaying of two wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Mobile Reports is not displaying of two wheeler").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void selectVehicleTypeFOUR() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleType")));
            Select select = new Select(selectElement);
            select.selectByValue("FOUR");
            extentReports.createTest("Mobile Reports vehicle type as FOUR wheeler of a day ").log(Status.PASS,
                    "Successfully selecting vehicle type FOUR wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as FOUR wheeler of a day").log(Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 18)
    public void dayRSubmitButtonFOUR() {
        dayRSubmitButton();
    }

    @Test(priority = 19)
    public void dayRViewDetailsFOUR() {
        mobileDayRViewDetails();
    }

    @Test(priority = 20)
    public void dayRevenuePopUPCloseFOUR() {
        dayRPopUPClose();
    }

    @Test(priority = 21)
    public void getDayRevenueFOUR() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-Day Revenue FOUR wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Day Revenue in Mobile Reports is Displaying of FOUR wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Mobile Reports is not displaying of FOUR wheeler").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 22)
    public void selectIOS() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("mobile-select")).sendKeys("IOS");
            extentReports.createTest("Mobile Reports vehicle type as IOS").log(Status.PASS,
                    "Successfully selecting vehicle type IOS");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as IOS").log(Status.FAIL,
                    "Failed to select the IOS. Exception: " + e.getCause());

        }
    }

    @Test(priority = 23)
    public void selectIOSAll() {
        selectOneDayVehicleTypeAll();
    }

    @Test(priority = 24)
    public void dayRSubmitButtonIOS() {
        dayRSubmitButton();
    }

    @Test(priority = 25)
    public void dayRViewDetailsIOS() {
        mobileDayRViewDetails();
    }

    @Test(priority = 26)
    public void dayRevenuePopUPCloseIOS() {
        dayRPopUPClose();
    }

    @Test(priority = 27)
    public void getDayMobileRevenueIOS() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-Day Revenue of IOS All vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Day Revenue in Mobile Reports IOS is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Mobile Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 28)
    public void selectOneDayTypeTwoIOS() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleType")));
            Select select = new Select(selectElement);
            select.selectByValue("TWO");
            extentReports.createTest("Mobile Reports IOS vehicle type as TWO wheeler of a day ").log(Status.PASS,
                    "Successfully selecting vehicle type TWO wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports IOS Vehicle type as TWO wheeler of a day").log(Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 29)
    public void dayRSubmitButtonTwoIOS() {
        dayRSubmitButton();
    }

    @Test(priority = 30)
    public void dayRViewDetailsTWOIOS() {
        mobileDayRViewDetails();
    }

    @Test(priority = 31)
    public void dayRPopUPCloseTWOIOS() {
        dayRPopUPClose();
    }

    @Test(priority = 32)
    public void getDayRevenueTWOIOS() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-Day Revenue TWO wheeler vehicle type of IOS : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Day Revenue in Mobile Reports IOS is Displaying of two wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Mobile Reports IOS is not displaying of two wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 33)
    public void selectOneDayTypeFOURIOS() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleType")));
            Select select = new Select(selectElement);
            select.selectByValue("FOUR");
            extentReports.createTest("Mobile Reports IOS vehicle type as FOUR wheeler of a day ").log(Status.PASS,
                    "Successfully selecting vehicle type FOUR wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports IOS Vehicle type as FOUR wheeler of a day").log(Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 34)
    public void dayRSubmitButtonFOURIOS() {
        dayRSubmitButton();
    }

    @Test(priority = 35)
    public void dayViewDetailsFOURIOS() {
        mobileDayRViewDetails();
    }

    @Test(priority = 36)
    public void dayRPopUPCloseFOURIOS() {
        dayRPopUPClose();
    }

    @Test(priority = 37)
    public void getDayRevenueFOURIOS() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-Day Revenue of IOS FOUR wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Day Revenue in Mobile Reports IOS is Displaying of FOUR wheeler ").log(
                    Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Day Revenue in Mobile Reports IOS is not displaying of FOUR wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 38)
    public void mobileReportsWEEK() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter']/label[3]"))
                    .click();

            extentReports.createTest("Mobile Reports Revenue Dates Enabled ").log(Status.PASS,
                    "Successfully selecting the dates WEEK");
        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Revenue Dates Enabled ").log(Status.FAIL,
                    "Failed to select the dates WEEK. Exception: " + e.getCause());

        }
    }

    @Test(priority = 39)
    public void selectAndroidWEEK() {
        selectAndroid();
    }

    @Test(priority = 40)
    public void selectAllWEEK() {
        selectOneDayVehicleTypeAll();
    }

    @Test(priority = 41)
    public void weekRSubmitButton() {
        dayRSubmitButton();
    }

    @Test(priority = 42)
    public void weekRViewDetails() {
        mobileDayRViewDetails();
    }

    @Test(priority = 43)
    public void weekRPopUPClose() {
        dayRPopUPClose();
    }

    @Test(priority = 44)
    public void getWEEKMobileRevenueALL() {
        try {
            WebElement oneDay = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneDay.getText();
            System.out.println("Mobile One-WEEK Revenue All vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("WEEK Revenue in Mobile Reports is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("WEEK Revenue in Mobile Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 45)
    public void weeklyAndroidTypeTwo() {
        selectVehicleTypeTwo();
    }

    @Test(priority = 46)
    public void weekAndroidSubmitButtonTwo() {
        dayRSubmitButton();
    }

    @Test(priority = 47)
    public void weeklyAndroidViewDetailsTWO() {
        mobileDayRViewDetails();
    }

    @Test(priority = 48)
    public void weekRPopUPCloseTWO() {
        dayRPopUPClose();
    }

    @Test(priority = 49)
    public void getWeekRevenueTWO() {
        try {
            WebElement oneWEEK = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneWEEK.getText();
            System.out.println("Mobile One-WEEK Revenue TWO wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("WEEK Revenue in Mobile Reports is Displaying of two wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("WEEK Revenue in Mobile Reports is not displaying of two wheeler").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 50)
    public void weeklyAndroidTypeFour() {
        selectOneDayTypeFOURIOS();
    }

    @Test(priority = 51)
    public void weeklyAndroidSubmitButtonFOUR() {
        dayRSubmitButton();
    }

    @Test(priority = 52)
    public void weeklyAndroidViewDetailsFOUR() {
        mobileDayRViewDetails();
    }

    @Test(priority = 53)
    public void weeklyAndroidPopUPCloseFOUR() {
        dayRPopUPClose();
    }

    @Test(priority = 54)
    public void getWeekRevenueFOUR() {
        try {
            WebElement oneWeek = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneWeek.getText();
            System.out.println("Mobile One-WEEK Revenue FOUR wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("WEEK Revenue in Mobile Reports is Displaying of FOUR wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("WEEK Revenue in Mobile Reports is not displaying of FOUR wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 55)
    public void selectIOSWEEK() {
        selectIOS();
    }

    @Test(priority = 56)
    public void weekyIOSTypeAll() {
        selectOneDayVehicleTypeAll();
    }

    @Test(priority = 57)
    public void weeklyIOSSubmitButtonAll() {
        dayRSubmitButton();
    }

    @Test(priority = 58)
    public void weeklyIOSViewDetailsAll() {
        mobileDayRViewDetails();
    }

    @Test(priority = 59)
    public void weeklyIOSPopUPCloseAll() {
        dayRPopUPClose();
    }

    @Test(priority = 60)
    public void getWeekMobileRevenueIOS() {
        try {
            WebElement oneWEEK = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneWEEK.getText();
            System.out.println("Mobile One-WEEK Revenue of IOS All vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("WEEK Revenue in Mobile Reports IOS is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("WEEK Revenue in Mobile Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 61)
    public void selectWEEKTypeTwoIOS() {
        selectOneDayTypeTwoIOS();
    }

    @Test(priority = 62)
    public void weeklyIOSSubmitButtonTwo() {
        dayRSubmitButton();
    }

    @Test(priority = 63)
    public void weeklyIOSViewDetailsTwo() {
        mobileDayRViewDetails();
    }

    @Test(priority = 64)
    public void weeklyIOSPopUPCloseTwo() {
        dayRPopUPClose();
    }

    @Test(priority = 65)
    public void getWeekRevenueTwoIOS() {
        try {
            WebElement oneWEEK = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneWEEK.getText();
            System.out.println("Mobile One-WEEK Revenue TWO wheeler vehicle type of IOS : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("WEEK Revenue in Mobile Reports IOS is Displaying of two wheeler ").log(
                    Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("WEEK Revenue in Mobile Reports IOS is not displaying of two wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 66)
    public void selectWEEKTypeFOURIOS() {
        selectOneDayTypeFOURIOS();
    }

    @Test(priority = 67)
    public void weeklyIOSSubmitButtonFOUR() {
        dayRSubmitButton();
    }

    @Test(priority = 68)
    public void weeklyIOSViewDetailsFOUR() {
        mobileDayRViewDetails();
    }

    @Test(priority = 69)
    public void weeklyIOSPopUPCloseFOUR() {
        dayRPopUPClose();
    }

    @Test(priority = 70)
    public void getWeekRevenueFOURIOS() {
        try {
            WebElement oneWEEK = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneWEEK.getText();
            System.out.println("Mobile One-WEEK Revenue of IOS FOUR wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("WEEK Revenue in Mobile Reports IOS is Displaying of FOUR wheeler ").log(
                    Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("WEEK Revenue in Mobile Reports IOS is not displaying of FOUR wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 71)
    public void mobileReportsMonth() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id='dayRangeFilter']/label[4]"))
                    .click();

            extentReports.createTest("Mobile Reports Revenue Dates Enabled ").log(Status.PASS,
                    "Successfully selecting the dates Month");
        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Revenue Dates Enabled ").log(Status.FAIL,
                    "Failed to select the dates Month. Exception: " + e.getCause());

        }
    }

    @Test(priority = 72)
    public void selectMonthAndroid() {
        selectAndroid();
    }

    @Test(priority = 73)
    public void monthlyAndroidTypeAll() {
        selectOneDayVehicleTypeAll();
    }

    @Test(priority = 74)
    public void monthlyAndroidSubmitButtonAll() {
        dayRSubmitButton();
    }

    @Test(priority = 75)
    public void monthlyAndroidViewDetailsAll() {
        mobileDayRViewDetails();
    }

    @Test(priority = 76)
    public void monthlyAndroidPopUPCloseAll() {
        dayRPopUPClose();
    }

    @Test(priority = 77)
    public void getMonthMobileRevenue() {
        try {
            WebElement oneMonth = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneMonth.getText();
            System.out.println("Mobile One-Month Revenue All vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Month Revenue in Mobile Reports is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Mobile Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 78)
    public void monthlyAndroidTypeTwo() {
        selectOneDayTypeTwoIOS();
    }

    @Test(priority = 79)
    public void monthlyAndroidSubmitButtonTwo() {
        dayRSubmitButton();
    }

    @Test(priority = 80)
    public void monthlyAndroidViewDetailsTwo() {
        mobileDayRViewDetails();
    }

    @Test(priority = 81)
    public void monthlyAndroidPopUPCloseTwo() {
        dayRPopUPClose();
    }

    @Test(priority = 82)
    public void getMonthRevenueTWO() {
        try {
            WebElement oneMonth = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneMonth.getText();
            System.out.println("Mobile One-Month Revenue TWO wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Month Revenue in Mobile Reports is Displaying of two wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Mobile Reports is not displaying of two wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 83)
    public void selectMonthTypeFOUR() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehicleType")));
            Select select = new Select(selectElement);
            select.selectByValue("FOUR");
            extentReports.createTest("Mobile Reports vehicle type as FOUR wheeler of a Month ").log(Status.PASS,
                    "Successfully selecting vehicle type FOUR wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as FOUR wheeler of a Month").log(Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 84)
    public void monthlyAndroidSubmitButtonFOUR() {
        dayRSubmitButton();
    }

    @Test(priority = 85)
    public void monthlyAndroidViewDetailsFOUR() {
        mobileDayRViewDetails();
    }

    @Test(priority = 86)
    public void monthlyAndroidPopUPCloseFOUR() {
        dayRPopUPClose();
    }

    @Test(priority = 87)
    public void getMonthRevenueFOUR() {
        try {
            WebElement oneMonth = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneMonth.getText();
            System.out.println("Mobile One-Month Revenue FOUR wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Month Revenue in Mobile Reports is Displaying of FOUR wheeler ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Mobile Reports is not displaying of FOUR wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 88)
    public void selectMonthIOS() {
        selectIOS();
    }

    @Test(priority = 89)
    public void monthlyIOSTypeAll() {
        selectOneDayVehicleTypeAll();
    }

    @Test(priority = 90)
    public void monthlyIOSSubmitButtonAll() {
        dayRSubmitButton();
    }

    @Test(priority = 91)
    public void monthlyIOSViewDetailsAll() {
        mobileDayRViewDetails();
    }

    @Test(priority = 92)
    public void monthlyIOSPopUPCloseAll() {
        dayRPopUPClose();
    }

    @Test(priority = 93)
    public void getMonthMobileRevenueIOS() {
        try {
            WebElement oneMonth = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneMonth.getText();
            System.out.println("Mobile One-Month Revenue of IOS All vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Month Revenue in Mobile Reports IOS is Displaying ").log(Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Mobile Reports is not displaying").log(Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 94)
    public void selectMonthTypeTwoIOS() {
        selectOneDayTypeTwoIOS();
    }

    @Test(priority = 95)
    public void monthlyIOSSubmitButtonTwo() {
        dayRSubmitButton();
    }

    @Test(priority = 96)
    public void monthlyIOSViewDetailsTwo() {
        mobileDayRViewDetails();
    }

    @Test(priority = 97)
    public void monthlyIOSPopUPCloseTwo() {
        dayRPopUPClose();
    }

    @Test(priority = 98)
    public void getMonthRevenueTWOIOS() {
        try {
            WebElement oneMonth = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneMonth.getText();
            System.out.println("Mobile One-Month Revenue TWO wheeler vehicle type of IOS : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Month Revenue in Mobile Reports IOS is Displaying of two wheeler ").log(
                    Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Mobile Reports IOS is not displaying of two wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 99)
    public void selectMonthTypeFOURIOS() {
        selectOneDayTypeFOURIOS();
    }

    @Test(priority = 100)
    public void monthlyIOSSubmitButtonFOUR() {
        dayRSubmitButton();
    }

    @Test(priority = 101)
    public void monthlyIOSViewDetailsFOUR() {
        mobileDayRViewDetails();
    }

    @Test(priority = 102)
    public void monthlyIOSPopUPCloseFOUR() {
        dayRPopUPClose();
    }

    @Test(priority = 103)
    public void getMonthRevenueFOURIOS() {
        try {
            WebElement oneMonth = driver.findElement(By.xpath("//*[@id='mobile_revenue']"));
            String value2 = oneMonth.getText();
            System.out.println("Mobile One-Month Revenue of IOS FOUR wheeler vehicle type : " + value2);
            Thread.sleep(4000);
            extentReports.createTest("Month Revenue in Mobile Reports IOS is Displaying of FOUR wheeler ").log(
                    Status.PASS,
                    "Amount is displaying Successfully" + value2);
        } catch (Exception e) {
            extentReports.createTest("Month Revenue in Mobile Reports IOS is not displaying of FOUR wheeler").log(
                    Status.FAIL,
                    "Failed Amount is not Displaying. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 104)
    public void selectMobileRevenueStartDate() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 105)
    public void selectMobileRevenueEndDate() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 106)
    public void mobileTransactions() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("mobile_occupancy")).click();
            extentReports.createTest("Mobile Transactions Page ").log(Status.PASS,
                    "Successfully Open Mobile Transactions page");
        } catch (Exception e) {
            extentReports.createTest("Mobile Transactions Page ").log(Status.FAIL,
                    "Failed to open the Pos Mobile Transactions page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 107)
    public void mobileTransactionDates() {
        try {
            Thread.sleep(2000);
            WebElement dayButton = driver
                    .findElement(
                            By.xpath("//div[@class='row siteAnalytics3']//*[@id='dayRangeFilter']/label[1]"));

            dayButton.click();
            Thread.sleep(2000);
            WebElement weekButton = driver
                    .findElement(
                            By.xpath("//div[@class='row siteAnalytics3']//*[@id='dayRangeFilter']/label[2]"));

            weekButton.click();
            Thread.sleep(2000);
            WebElement monthButton = driver
                    .findElement(
                            By.xpath("//div[@class='row siteAnalytics3']//*[@id='dayRangeFilter']/label[3]"));
            monthButton.click();

            extentReports.createTest("Mobile Reports Transactions Dates Enabled ").log(Status.PASS,
                    "Successfully selecting the dates day");
        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Transactions Dates Enabled ").log(Status.FAIL,
                    "Failed to select the dates day. Exception: " + e.getCause());

        }
    }

    @Test(priority = 108)
    public void selectTransactionsAndroid() {
        selectAndroid();
    }

    @Test(priority = 109)
    public void selectTransactionsVehicleTypeTwo() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@id='mobileTransactionContainer']//select[@id='vehicleType']")));
            Select select = new Select(selectElement);
            select.selectByValue("TWO");
            extentReports.createTest("Mobile Reports vehicle type as TWO wheeler of a day ").log(Status.PASS,
                    "Successfully selecting vehicle type TWO wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Vehicle type as TWO wheeler of a day").log(Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 110)
    public void transSubmitBtnTwo() {
        try {
            driver.findElement(By.xpath("//button[@id='mobileTransBtn']")).click();
            Thread.sleep(3000);
            extentReports.createTest("In Mobile reports day Transactions click the submit button of TWO wheeler").log(
                    Status.PASS,
                    "Successfully clicking the submit button in Transactions Tab");
        } catch (Exception e) {
            extentReports.createTest("In Mobile reports day Transactions click the submit button of TWO wheeler").log(
                    Status.FAIL,
                    "Failed to click the submit button in Transactions tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 111)
    public void transViewDetailsTWO() {
        try {
            driver
                    .findElement(By.xpath("//*[@id='mobileTransactionForm']/div/div[3]/button[2]"))
                    .click();
            Thread.sleep(2000);
            extentReports.createTest("In Mobile reports click the view details button of Transactions of two wheeler")
                    .log(
                            Status.PASS,
                            "Successfully clicking the view details button in Transactions Tab");
        } catch (Exception e) {
            extentReports.createTest("In Mobile reports click the view details button of Transactions of two wheeler")
                    .log(
                            Status.FAIL,
                            "Failed to click view details button in Transactions tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 112)
    public void dayTransPopUPCloseTWO() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='mobileTransModal']/div/div/div[1]/button")).click();
            Thread.sleep(2000);
            extentReports.createTest("In Mobile reports transactions POP Up closed").log(Status.PASS,
                    "Successfully closed the pop up in Mobile reports");
        } catch (Exception e) {
            extentReports.createTest("In Mobile reports transactions POP Up closed ").log(Status.FAIL,
                    "Failed to close the pop up in Mobile Reports tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 113)
    public void selectTransactionsTypeFOUR() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@id='mobileTransactionContainer']//select[@id='vehicleType']")));
            Select select = new Select(selectElement);
            select.selectByValue("FOUR");
            extentReports.createTest("Mobile Reports Transactions vehicle type as FOUR wheeler of a day ").log(
                    Status.PASS,
                    "Successfully selecting vehicle type FOUR wheeler");

        } catch (Exception e) {
            extentReports.createTest("Mobile Reports Transactions Vehicle type as FOUR wheeler of a day").log(
                    Status.FAIL,
                    "Failed to select the TWO wheeler. Exception: " + e.getCause());

        }
    }

    @Test(priority = 114)
    public void dayTransSubmitBtnFOUR() {
        transSubmitBtnTwo();
    }

    @Test(priority = 115)
    public void dayTransViewDetailsFOUR() {
        transViewDetailsTWO();
    }

    @Test(priority = 116)
    public void dayTransPopUPCloseFOUR() {
        dayTransPopUPCloseTWO();
    }

    @Test(priority = 117)
    public void selectTransIOS() {
        selectIOS();
    }

    @Test(priority = 118)
    public void selectTransactionsTypeTwoIOS() {
        selectTransactionsVehicleTypeTwo();
    }

    @Test(priority = 119)
    public void transSubmitBtnTwoIOS() {
        transSubmitBtnTwo();
    }

    @Test(priority = 120)
    public void transViewDetailsTWOIOS() {
        transViewDetailsTWO();

    }

    @Test(priority = 121)
    public void transPopUPCloseTWOIOS() {
        dayTransPopUPCloseTWO();
    }

    @Test(priority = 122)
    public void selectTransactionsTypeFourIOS() {
        selectTransactionsTypeFOUR();
    }

    @Test(priority = 123)
    public void transSubmitBtnFourIOS() {
        transSubmitBtnTwo();
    }

    @Test(priority = 124)
    public void transViewDetailsFourIOS() {
        transViewDetailsTWO();

    }

    @Test(priority = 125)
    public void transPopUPCloseFourIOS() {
        dayTransPopUPCloseTWO();
    }

    @Test(priority = 126)
    public void logout() {
        try {
            Thread.sleep(2000);

            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Mobile Reporst page LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Mobile Reports pageLogOut").log(Status.FAIL,
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
