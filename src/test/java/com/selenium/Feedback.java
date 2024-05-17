package com.selenium;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class Feedback extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Feedback.html");
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
    public void revenueDashboard() {
        Dashboard.revenuedashboardOpen(driver, extentReports);
    }

    @Test(priority = 3)
    public void redirectFeedBackPage() {
        try {
            Thread.sleep(3000);
            WebElement feedbackPageLink = driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[8]"));

            if (feedbackPageLink.isDisplayed()) {
                feedbackPageLink.click();
                extentReports.createTest("Feedback Page ").log(Status.PASS,
                        "Successfully Open the Feedback page ");
            } else {
                extentReports.createTest("Feedback Page ").log(Status.INFO,
                        "Feedback Page not found");
                Assert.fail("Feedback page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("Feedback Page ").log(Status.FAIL,
                    "Failed to click the Feedback page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectFeedbackSitesList() {
        try {
            Thread.sleep(5000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            site.click();
            String selectedSiteName = site.getText();

            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.PASS, "Successfully open dropdown and selected the " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected  . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void feedbackDatesEnabled() {
        try {
            Thread.sleep(1000);
            WebElement dayButton = driver.findElement(By.xpath("//div[@id='dateRangeFeedback']/label[2]"));
            dayButton.click();

            WebElement weekButton = driver.findElement(By.xpath("//div[@id='dateRangeFeedback']/label[3]"));
            weekButton.click();
            Thread.sleep(1000);

            WebElement monthButton = driver.findElement(By.xpath("//div[@id='dateRangeFeedback']/label[4]"));
            monthButton.click();
            Thread.sleep(1000);

            extentReports.createTest("Feedback page Dates is clicked").log(Status.PASS,
                    "Successfully clicked the feedback day/week/month");
        } catch (Exception e) {

            extentReports.createTest("Feedback page Dates is not clicked").log(Status.FAIL,
                    "Failed to click the feedback day/week/month button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void selectFeedbackStartDate() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='dateRangeFeedback']/label[1]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in Feedback").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in Feedback").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void selectFeedbackEndDate() {
        try {
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            WebElement dateElement = driver
                    .findElement(By.xpath(
                            "//td[@class='available' and @data-title='r3c4']"));
            dateElement.click();

            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("End Date in Feedback").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Feedback").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void clickComplaintsTab() {
        try {
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(5000);
            WebElement attendersTab = driver.findElement(By.id("pills-complaint-tab"));
            attendersTab.click();

            extentReports.createTest("Click complaint tab").log(Status.PASS,
                    "operator tab clicked successfully.");

            extentReports.createTest("Click complaint tab").log(Status.INFO,
                    "Additional information or data about the complaint tab.");

        } catch (Exception e) {
            extentReports.createTest("Click complaint tab").log(Status.FAIL,
                    "Failed to click complaint tab. Exception: " + e.getMessage());

            extentReports.createTest("Click complaint tab").log(Status.INFO,
                    "Additional information or data about the failure.");
        }
    }

    @Test(priority = 9)
    public void selectComplaintSitesList() {
        try {
            Thread.sleep(5000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            site.click();
            String selectedSiteName = site.getText();

            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.PASS, "Successfully open dropdown and selected the " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected  . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 10)
    public void complaintsDatesEnabled() {
        try {
            Thread.sleep(1000);
            WebElement dayButton = driver.findElement(By.xpath("//div[@id='dateRangeComplaint']/label[2]"));
            dayButton.click();

            WebElement weekButton = driver.findElement(By.xpath("//div[@id='dateRangeComplaint']/label[3]"));
            weekButton.click();
            Thread.sleep(1000);

            WebElement monthButton = driver.findElement(By.xpath("//div[@id='dateRangeComplaint']/label[4]"));
            monthButton.click();
            Thread.sleep(1000);

            extentReports.createTest("complaint page Dates is clicked").log(Status.PASS,
                    "Successfully clicked the complaint day/week/month");
        } catch (Exception e) {

            extentReports.createTest("complaint page Dates is not clicked").log(Status.FAIL,
                    "Failed to click the complaint day/week/month button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void selectComplaintStartDate() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='dateRangeComplaint']/label[1]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(2000);
            WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));

            dateElement.click();
            extentReports.createTest("Start Date in Complaint").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in Complaint").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 12)
    public void selectComplaintEndDate() {
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

            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("End Date in Complaint").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in Complaint").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 20)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Feedback/Complaint page LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Feedback/Complaint page LogOut").log(Status.FAIL,
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
