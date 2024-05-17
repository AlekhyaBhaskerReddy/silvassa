package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class Slot_Analysis extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Slot_Analysis.html");
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
    public void redirectSlotAnalysis() {
        try {
            Thread.sleep(2000);
            WebElement SlotAnalysisPage = driver.findElement(By.className("slta-pg"));
            if (SlotAnalysisPage.isDisplayed()) {
                SlotAnalysisPage.click();
                extentReports.createTest("Slot Analysis Page  ").log(Status.PASS,
                        "Successfully Open Slot analysis  Reports Page");
            } else {
                extentReports.createTest("Slot Analysis Page").log(Status.INFO,
                        "Slot Analysis Page not found");
                Assert.fail("Slot Analysis Page not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Slot Analysis  Page Reports ").log(Status.FAIL,
                    "Failed to Open the Slot analysis  Reports . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void SlotAnalysis_selectSite() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            site.click();
            String selectedSiteName = site.getText();

            extentReports.createTest("Displaying the sites list in audit trail page").log(Status.PASS,
                    "Successfully selected the second site: " + selectedSiteName);
        } catch (Exception e) {
            extentReports.createTest("Displaying the sites list audit trail page tab").log(Status.FAIL,
                    "Failed to select the second site. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void getSlot_AnalysisTab() {
        WebElement dashboardSummary = driver.findElement(By.className("dashboardSummary"));
        List<WebElement> summaryCards = dashboardSummary.findElements(By.className("site-summary-cards"));

        for (WebElement summaryCard : summaryCards) {
            WebElement cardNameElement = summaryCard.findElement(By.tagName("b"));
            String cardName = cardNameElement.getText();

            extentReports.createTest("Total Tabs").log(Status.PASS,
                    "Available tabs in Slot Analysis").log(Status.INFO, "Summary Card Name: " + cardName);
        }

    }

    @Test(priority = 7)
    public void dayHour_Occupancy_Dates() {
        try {
            driver.findElement(By.xpath("//div[@id='dayHourOccupancyContainer']//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            driver.findElement(By.xpath("//div[@id='dayHourOccupancyContainer']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='dayHourOccupancyContainer']//div[@id='dayRangeFilter']/label[3]"))
                    .click();
            Thread.sleep(2000);

            extentReports.createTest("Slot Analysis Day Hour Occupancy Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Slot Analysis Day Hour Occupancy Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void dayHour_Occupancy_Menu_ToClickSgv() {
        try {
            Thread.sleep(2000);
            WebElement dayHourMenu = driver
                    .findElement(By.xpath("//div[@id='apexchartst1wyudouk']/div[3]"));
            if (dayHourMenu.isDisplayed()) {
                dayHourMenu.click();
                extentReports.createTest(" Day Hour Occupancy menu button ").log(Status.PASS,
                        "Present and successfully clicked the menu details button");
            } else {
                extentReports.createTest(" Day Hour Occupancy menu button ").log(Status.INFO,
                        " Day Hour Occupancy menu button not found");
                Assert.fail(" Day Hour Occupancy menu button not found");
            }
        } catch (Exception e) {
            extentReports.createTest(" Day Hour Occupancy menu button").log(Status.FAIL,
                    "Failed to click  Day Hour Occupancy menu button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void dayHour_Occupancy_Sgv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.xpath("//div[@id='apexchartst1wyudouk']/div[3]/div[2]/div[1]"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In Day Hour Occupancy download SGV file").log(Status.PASS,
                        "Successfully download the SGV file in Day Hour Occupancy");
            } else {
                extentReports.createTest("In Day Hour Occupancy download SGV file").log(Status.WARNING,
                        "SGV button not found in Day Hour Occupancy");
            }

        } catch (Exception e) {
            extentReports.createTest("In Day Hour Occupancy downlaod SGV file ").log(Status.FAIL,
                    "Failed to download the SGV file in Day Hour Occupancy. Exception: " + e.getCause());

        }
    }

    @Test(priority = 10)
    public void dayHour_Occupancy_Menu_ToClickPng() {
        dayHour_Occupancy_Menu_ToClickSgv();
    }

    @Test(priority = 11)
    public void dayHour_Occupancy_Png() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='apexchartst1wyudouk']/div[3]/div[2]/div[2]"));
            String mainWindowHandle = driver.getWindowHandle();

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.numberOfWindowsToBe(2));

                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }

                driver.close();
                driver.switchTo().window(mainWindowHandle);

                extentReports.createTest("In Day Hour Occupancy download PNG Button").log(Status.PASS,
                        "Successfully clicked the PNG button");
            } else {
                extentReports.createTest("In Day Hour Occupancy download PNG Button").log(Status.INFO,
                        "PNG button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In Day Hour Occupancy downlaod PNG Button").log(Status.FAIL,
                    "Failed to click the PNG button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 13)
    public void peakHoursTab() {
        try {
            Thread.sleep(2000);
            WebElement peakHoursTab = driver.findElement(By.xpath("//div[@id='slotPeakHours']"));

            if (peakHoursTab.isDisplayed()) {
                peakHoursTab.click();
                extentReports.createTest("Peak Hours Tab is clicked").log(Status.PASS,
                        "Successfully clicked the Peak Hours Tab");

            } else {
                extentReports.createTest("Peak Hours Tab is clicked ").log(Status.WARNING,
                        "Peak Hours tab not found");
                Assert.fail("Peak Hours tab not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Peak Hours Tab is not clicked").log(Status.FAIL,
                    "Failed to click the Peak Hours. Exception: " + e.getCause());
            siteAnalysisTabCapture(driver, "exception_screenshot");

        }
    }

    @Test(priority = 14)
    public void select_PeakHours_TwoWheeler() {
        try {
            Thread.sleep(2000);
            WebElement twoWheelerType = driver.findElement(By.xpath("//div[@id='wheelerFilter']/label[1]/input"));
            if (twoWheelerType.isDisplayed()) {
                twoWheelerType.click();
                extentReports.createTest("Slot Analysis Page select vehicle type ").log(Status.PASS,
                        "Successfully select the vehicle type");
            } else {
                extentReports.createTest("Slot Analysis Page select vehicle type").log(Status.INFO,
                        "Vehicle type not found");
                Assert.fail("Slot Analysis select vehicle type not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Slot Analysis Page select vehicle type ").log(Status.FAIL,
                    "Failed to select vehicle type in clot analysis . Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void peakHour_Dates_2W() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='slotPeakHourContainer']//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='slotPeakHourContainer']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='slotPeakHourContainer']//div[@id='dayRangeFilter']/label[3]"))
                    .click();

            extentReports.createTest("Slot Analysis Peak Hour Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Slot Analysis Peak Hour Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 16)
    public void select_PeakHours_FourWheeler() {
        try {
            Thread.sleep(2000);
            WebElement fourWheelerType = driver.findElement(By.xpath("//div[@id='wheelerFilter']/label[2]/input"));
            if (fourWheelerType.isDisplayed()) {
                fourWheelerType.click();
                extentReports.createTest("Slot Analysis Page select vehicle type 4W").log(Status.PASS,
                        "Successfully select the vehicle type");
            } else {
                extentReports.createTest("Slot Analysis Page select vehicle type 4W").log(Status.INFO,
                        "Vehicle type not found");
                Assert.fail("Slot Analysis select vehicle type not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Slot Analysis Page select vehicle type 4W").log(Status.FAIL,
                    "Failed to select vehicle type in clot analysis . Exception: " + e.getCause());
        }
    }

    @Test(priority = 17)
    public void peakHour_Dates_4W() {
        peakHour_Dates_2W();
    }

    @Test(priority = 18)
    public void slotAvgDuration_Tab() {
        try {
            Thread.sleep(2000);
            WebElement slotAvgDurationTab = driver.findElement(By.xpath("//div[@id='slotOccupancy']"));

            if (slotAvgDurationTab.isDisplayed()) {
                slotAvgDurationTab.click();
                extentReports.createTest("Slot Average Duration Tab is clicked").log(Status.PASS,
                        "Successfully clicked the Slot Average Duration Tab");

            } else {
                extentReports.createTest("Slot Average Duration Tab is clicked ").log(Status.WARNING,
                        "Slot Average Duration tab not found");
                Assert.fail("Slot Average Duration tab not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Slot Average Duration Tab is not clicked").log(Status.FAIL,
                    "Failed to click the Slot Average Duration. Exception: " + e.getCause());
            siteAnalysisTabCapture(driver, "exception_screenshot");

        }
    }

    @Test(priority = 19)
    public void slotAvgDuration_Dates() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='averageDurationContainer']//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='averageDurationContainer']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='averageDurationContainer']//div[@id='dayRangeFilter']/label[3]"))
                    .click();

            extentReports.createTest("Site Analysis Slot Average Duration Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Site Analysis Slot Average Duration Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void slotRotation_Tab() {
        try {
            Thread.sleep(2000);
            WebElement slotRotationTab = driver.findElement(By.xpath("//div[@id='slotRotation']"));

            if (slotRotationTab.isDisplayed()) {
                slotRotationTab.click();
                extentReports.createTest("Slot Rotation Tab is clicked").log(Status.PASS,
                        "Successfully clicked the Slot Rotation Tab");

            } else {
                extentReports.createTest("Slot Rotation Tab is clicked ").log(Status.WARNING,
                        "Slot Rotation tab not found");
                Assert.fail("Slot Rotation tab not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Slot Rotation Tab is not clicked").log(Status.FAIL,
                    "Failed to click the Slot Rotation. Exception: " + e.getCause());
            siteAnalysisTabCapture(driver, "exception_screenshot");

        }
    }

    @Test(priority = 21)
    public void slotRotation_Dates() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='averageDurationContainer']//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='averageDurationContainer']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='averageDurationContainer']//div[@id='dayRangeFilter']/label[3]"))
                    .click();

            extentReports.createTest("Site Analysis Slot Rotation Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Site Analysis Slot Rotation Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 22)
    public void slotRotation_Menu_ToClickSgv() {
        try {
            Thread.sleep(2000);
            WebElement slotRotationMenu = driver
                    .findElement(By.xpath("//div[@id='apexchartstf7s0qew']/div[3]/div[1]"));
            if (slotRotationMenu.isDisplayed()) {
                slotRotationMenu.click();
                extentReports.createTest(" Slot Rotation menu button ").log(Status.PASS,
                        "Present and successfully clicked the menu details button");
            } else {
                extentReports.createTest(" Slot Rotation menu button ").log(Status.INFO,
                        " Slot Rotation menu button not found");
                Assert.fail(" Slot Rotation menu button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Slot Rotation menu button").log(Status.FAIL,
                    "Failed to click Slot Rotation menu button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 23)
    public void slotRotation_Sgv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.xpath("//div[@id='apexchartstf7s0qew']/div[3]/div[2]/div[1]"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In Slot Rotation download SGV file").log(Status.PASS,
                        "Successfully download the SGV file in Slot Rotation");
            } else {
                extentReports.createTest("In Slot Rotation download SGV file").log(Status.WARNING,
                        "SGV button not found in Slot Rotation");
            }

        } catch (Exception e) {
            extentReports.createTest("In Slot Rotation downlaod SGV file ").log(Status.FAIL,
                    "Failed to download the SGV file in Slot Rotation. Exception: " + e.getCause());

        }
    }

    @Test(priority = 24)
    public void slotRotation_Menu_ToClickPng() {
        slotRotation_Menu_ToClickSgv();
    }

    @Test(priority = 25)
    public void slotRotation_Png() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='apexchartstf7s0qew']/div[3]/div[2]/div[2]"));
            String mainWindowHandle = driver.getWindowHandle();

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.numberOfWindowsToBe(2));

                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }

                driver.close();
                driver.switchTo().window(mainWindowHandle);

                extentReports.createTest("In Slot Rotation download PNG Button").log(Status.PASS,
                        "Successfully clicked the PNG button");
            } else {
                extentReports.createTest("In Slot Rotation download PNG Button").log(Status.INFO,
                        "PNG button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In Slot Rotation downlaod PNG Button").log(Status.FAIL,
                    "Failed to click the PNG button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 26)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Audit Trail page Slot Analysis LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail page Slot Analysis LogOut").log(Status.FAIL,
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

    public static void siteAnalysisTabCapture(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
            extentReports.createTest("Screenshot ").addScreenCaptureFromBase64String(
                    Base64.getEncoder().encodeToString(screenshotBytes), screenshotName);
        } catch (Exception e) {
            extentReports.createTest("Screenshot ").log(Status.WARNING,
                    "Failed to capture screenshot: " + e.getMessage());
        }
    }
}
