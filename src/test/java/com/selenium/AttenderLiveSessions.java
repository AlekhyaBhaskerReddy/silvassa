package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

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


public class AttenderLiveSessions extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/AttenderLive_Sessions.html");
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
    public void revenueDashboard() {
        Dashboard.revenuedashboardOpen(driver, extentReports);
    }

    @Test(priority = 3)
    public void attenderLiveSessionsPage() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[8]")).click();

            WebElement assignmentPageLink = driver
                    .findElement(By.className("atl-pg"));
            if (assignmentPageLink.isDisplayed()) {
                assignmentPageLink.click();
                extentReports.createTest("User Logs Attender Live Sessions Page ").log(Status.PASS,
                        "Successfully Open the User Logs page");
            } else {
                extentReports.createTest("User Logs Attender Live Sessions Page ").log(Status.INFO,
                        "User Logs Attender Live Sessions not found");
                Assert.fail("User Logs Attender Live Sessions page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("User Logs Attender Live Sessions Page ").log(Status.FAIL,
                    "Failed to click the UserLogs Attender Live Sessions page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectSitesList() {
        try {
            Thread.sleep(5000);

            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            String selectedSiteName = site.getText();
            site.click();

            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.PASS, "Successfully open dropdown and selected the : " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected  . Exception: " + e.getMessage());
        }

    }

    @Test(priority = 5)
    public void attenderSessionCount() {
        try {
            Thread.sleep(5000);
            WebElement dropdown = driver.findElement(By.name("attenderLoginsTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("select attender session Count").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("select attender session Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 6)
    public void searchAttenderSession() {
        try {
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.cssSelector("#attenderLoginsTable_filter input"));

            searchInput.sendKeys("attender2");
            Thread.sleep(2000);

            Thread.sleep(2000);
            int adminCount = driver.findElements(By.xpath("//tbody/tr[contains(td[3], 'Attender')]")).size();

            extentReports.createTest("Search attender session")
                    .log(Status.PASS, "Successfully performed search with ")
                    .info("Number of search results: " + adminCount);
        } catch (Exception e) {
            extentReports.createTest("Search attender session")
                    .log(Status.FAIL, "Failed to perform search. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void clickLogoutButton() {
        try {
            WebElement logoutButton = driver.findElement(By.className("btnLogoutAttnder"));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            logoutButton.click();

            extentReports.createTest("Click Logout Button of an attender")
                    .log(Status.PASS, "Successfully clicked the Logout button");

        } catch (Exception e) {
            extentReports.createTest("Click Logout Button of an attender")
                    .log(Status.FAIL, "Failed to click the Logout button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void confirmAttenderLogoutAction() {
        try {
            WebElement deleteButton = driver.findElement(By.cssSelector("button.swal2-confirm"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();

                extentReports.createTest("Confirm Logout attender user").log(Status.PASS,
                        "Clicked the 'Yes, Delete it!' button of attender user");
            } else {
                extentReports.createTest("Confirm Logout attender user").log(Status.FAIL,
                        "'Yes, Delete it!' button is not present or not enabled");
            }

        } catch (Exception e) {
            extentReports.createTest("Confirm Logout attender user").log(Status.FAIL,
                    "Failed to confirm delete action of attender. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 9)
    public void selectReason() {
        try {
            Thread.sleep(3000);
            WebElement type = driver.findElement(By.id("logoutReason"));
            Select select = new Select(type);
            select.selectByValue("REASON 1");
            String selectedReasonType = select.getFirstSelectedOption().getText();

            extentReports.createTest("Select Reason").log(Status.PASS,
                    "Successfully select the reason " + selectedReasonType);
        } catch (Exception e) {
            extentReports.createTest("Select Reason").log(Status.FAIL,
                    "Failed to select the reason. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void clickSubmitButtonWithExplicitWait() {
        try {
            WebElement submitButton = driver.findElement(By.id("btnLogoutReason"));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            submitButton.click();

            extentReports.createTest("Click Submit Button ")
                    .log(Status.PASS, "Successfully clicked the Submit button");

        } catch (Exception e) {
            extentReports.createTest("Click Submit Button ")
                    .log(Status.FAIL, "Failed to click the Submit button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 11)
    public void extractPopupText() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text of attender logout").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text of attender logout").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text of attender logout").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 12)
    public void statusOK() {
        try {
            Thread.sleep(5000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status of attender logout").log(Status.PASS,
                    "Clicked the 'OK' button attender logout.");
        } catch (Exception e) {
            extentReports.createTest("Status of attender logout").log(Status.FAIL,
                    "Failed to click the 'OK' button attender logout. Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void selectSitesListTwice() {
        try {
            Thread.sleep(5000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(3);
            String selectedSiteName = site.getText();
            site.click();

            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.PASS, "Successfully open dropdown and selected the : " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("Dropdown open and selected ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected  . Exception: " + e.getMessage());
        }

    }

    @Test(priority = 14)
    public void logout() {
        try {
            Thread.sleep(5000);

            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("LogOut").log(Status.FAIL, "Failed to LogOut. Exception: " + e.getCause());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            extentReports.flush();
            driver.quit();
        }
    }

    public static List<WebElement> selectSitesListAttenderSessions(WebDriver driver) throws InterruptedException {
        driver.findElement(By.id("site_name"));
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