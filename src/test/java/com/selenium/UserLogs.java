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

public class UserLogs extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/UserLogs.html");
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
    public void userLogsActivityPage() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[8]")).click();

            WebElement assignmentPageLink = driver
                    .findElement(By.className("al-pg"));
            if (assignmentPageLink.isDisplayed()) {
                assignmentPageLink.click();
                extentReports.createTest("User Logs Activity Log Page ").log(Status.PASS,
                        "Successfully Open the User Logs page");
            } else {
                extentReports.createTest("User Logs Activity Log Page ").log(Status.INFO,
                        "User Logs Activity Log not found");
                Assert.fail("User Logs Activity Log page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("User Logs Activity Log Page ").log(Status.FAIL,
                    "Failed to click the UserLogs Activity Log page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectAdminUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("ADMIN");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 5)
    public void selectUserRoleList() {
        try {
            Thread.sleep(3000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            List<WebElement> allUsers = select.getOptions();
            StringBuilder userListInfo = new StringBuilder("List of Users Roles: ");
            for (WebElement user : allUsers) {
                userListInfo.append(user.getText()).append(", ");
            }

            extentReports.createTest("In UserLogs Activity Log user role list ").log(Status.INFO,
                    userListInfo.toString());

            String selectedRoleType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role list ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedRoleType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role list").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 6)
    public void selectAdminUser() {
        try {
            Thread.sleep(3000);
            WebElement dropdownElement = driver.findElement(By.id("userList"));
            // Select select = new Select(type);
            // select.selectByValue("testuser");

            Select select = new Select(dropdownElement);
            int indexToSelect = 1;
            select.selectByIndex(indexToSelect);
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user  ").log(Status.PASS,
                    "Successfully selecting the user in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user ").log(Status.FAIL,
                    "Failed to selecting the user in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 7)
    public void selectUserList() {
        try {
            Thread.sleep(3000);
            WebElement userListElement = driver.findElement(By.id("userList"));
            Select select = new Select(userListElement);
            List<WebElement> allUsers = select.getOptions();

            StringBuilder userListInfo = new StringBuilder("List of Users: ");

            for (WebElement user : allUsers) {
                userListInfo.append(user.getText()).append(", ");
            }

            extentReports.createTest("In UserLogs Activity Log user").log(Status.INFO, userListInfo.toString());

            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user").log(Status.PASS,
                    "Successfully selected the user in Activity Log: " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user").log(Status.FAIL,
                    "Failed to select the user in Activity Log. Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void activityDatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 9)
    public void tableNextPage() {
        try {
            Thread.sleep(3000);
            WebElement dayButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]"));
            dayButton.click();

            Thread.sleep(3000);
            WebElement nextButton = driver.findElement(By.id("activityLogTable_next"));

            while (!nextButton.getAttribute("class").contains("disabled")) {
                nextButton.click();
                Thread.sleep(3000);
                nextButton = driver.findElement(By.id("activityLogTable_next"));
            }
            WebElement previousButton = driver.findElement(By.id("activityLogTable_previous"));
            previousButton.click();

            extentReports.createTest("Opened table list ").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extentReports.createTest("Opened table list").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void downloadAdminCSV() {
        try {
            Thread.sleep(3000);
            WebElement csvButton = driver
                    .findElement(By.xpath("//div[@id='activityLogTable_wrapper']/div[1]/button[1]"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("CSV Button").log(Status.PASS, "Successfully clicked the CSV button");
            } else {
                extentReports.createTest("CSV Button").log(Status.INFO, "CSV button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("CSV Button").log(Status.FAIL,
                    "Failed to click the CSV button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void downlaodAdminPDFButton() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='activityLogTable_wrapper']/div[1]/button[2]"));

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait1.until(ExpectedConditions.numberOfWindowsToBe(2));
                String mainWindowHandle = driver.getWindowHandle();
                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {

                        driver.switchTo().window(windowHandle);
                        driver.close();

                        driver.switchTo().window(mainWindowHandle);
                        extentReports.createTest("PDF Button").log(Status.PASS, "Successfully clicked the PDF button");
                        return;

                    }
                }

            } else {
                extentReports.createTest("PDF Button").log(Status.INFO, "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("PDF Button").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void selectAccountUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("ACCOUNT");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 13)
    public void selectAccountUser() {
        selectAdminUser();
    }

    @Test(priority = 14)
    public void downloadAccountCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 15)
    public void downlaodAccountPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 16)
    public void selectAttenderUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("ATTENDER");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 17)
    public void selectAttenderUser() {
        selectAdminUser();
    }

    @Test(priority = 18)
    public void downloadAttenderCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 19)
    public void downlaodAttenderPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 20)
    public void selectHelpDeskUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("HELPDESK");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 21)
    public void selectHelpDeskUser() {
        selectAdminUser();

    }

    @Test(priority = 22)
    public void downloadHelpDeskCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 23)
    public void downlaodAHelpDeskPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 24)
    public void selectManagerUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("MANAGER");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 25)
    public void selectManagerUser() {
        selectAdminUser();

    }

    @Test(priority = 26)
    public void downloadManagerCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 27)
    public void downlaodManagerPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 28)
    public void selectMonitorUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("MONITOR");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 29)
    public void selectMonitorUser() {
        selectAdminUser();

    }

    @Test(priority = 30)
    public void downloadMonitorCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 31)
    public void downlaodMonitorPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 32)
    public void selectOperatorUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("OPERATOR");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 33)
    public void selectOperatorUser() {
        selectAdminUser();

    }

    @Test(priority = 34)
    public void downloadOperatorCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 35)
    public void downlaodOperatorPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 36)
    public void selectServiceUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("SERVICE");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 37)
    public void selectServiceUser() {
        selectAdminUser();

    }

    @Test(priority = 38)
    public void downloadServiceCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 39)
    public void downlaodServicePDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 40)
    public void selectSupportUserRole() {
        try {
            Thread.sleep(5000);
            WebElement type = driver.findElement(By.id("userRoles"));
            Select select = new Select(type);
            select.selectByValue("SUPPORT");
            String selectedUserType = select.getFirstSelectedOption().getText();

            extentReports.createTest("In UserLogs Activity Log user role ").log(Status.PASS,
                    "Successfully selecting the user role in Activity Log " + selectedUserType);
        } catch (Exception e) {
            extentReports.createTest("In UserLogs Activity Log user role").log(Status.FAIL,
                    "Failed to selecting the user role in Activity Log . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 41)
    public void selectSupportUser() {
        selectAdminUser();

    }

    @Test(priority = 42)
    public void downloadSupportCSV() {
        downloadAdminCSV();
    }

    @Test(priority = 43)
    public void downlaodSupportPDFButton() {
        downlaodAdminPDFButton();
    }

    @Test(priority = 44)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Activity  page LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Activity page LogOut").log(Status.FAIL,
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
