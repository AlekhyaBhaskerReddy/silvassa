package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class LiveInfo extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/LiveInfo.html");
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
    public void liveInfoPage() {
        try {
            Thread.sleep(2000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[3]"))
                    .click();

            extentReports.createTest("LiveInfo Page").log(Status.PASS,
                    "Successfully Open LiveInfo Page");
        } catch (Exception e) {
            extentReports.createTest("LiveInfo Page").log(Status.FAIL,
                    "Failed to Open the LiveInfo Page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void siteStatusPage() {
        try {
            Thread.sleep(3000);
            WebElement siteStatusLink = driver
                    .findElement(By.className("ss-pg"));
            if (siteStatusLink.isDisplayed()) {
                siteStatusLink.click();
                extentReports.createTest("Site Status page ").log(Status.PASS,
                        "Successfully Open Site Status Page");
            } else {
                extentReports.createTest("Site Status  Page ").log(Status.INFO,
                        "Site Status page not found");
                Assert.fail("Site Status not found");
            }

        } catch (Exception e) {
            extentReports.createTest("Site Status Page  ").log(Status.FAIL,
                    "Failed to Open the Site Status page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void siteStatusZoomOut() {
        try {
            Thread.sleep(5000);
            WebElement zoomOutElement = driver
                    .findElement(By.cssSelector("a.leaflet-control-zoom-out[title='Zoom out']"));
            zoomOutElement.click();

            extentReports.createTest("Site Status Page Zoom out ").log(Status.PASS,
                    "Successfully zoom out the Site Status Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Status Page zoom out  ").log(Status.FAIL,
                    "Failed to zoom out the Site Status page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void siteStatusZoomIn() {
        try {
            Thread.sleep(5000);
            WebElement zoomInElement = driver.findElement(By.cssSelector("a.leaflet-control-zoom-in[title='Zoom in']"));
            zoomInElement.click();
            zoomInElement.click();

            WebElement elementToHover = driver
                    .findElement(By.cssSelector("img[src='../assets/images/icons/location-ACTIVE.png']"));
            String script = "var element = arguments[0];" +
                    "var mouseEvent = document.createEvent('MouseEvents');" +
                    "mouseEvent.initEvent('mouseover', true, true);" +
                    "element.dispatchEvent(mouseEvent);";
            ((JavascriptExecutor) driver).executeScript(script, elementToHover);

            extentReports.createTest("Site Status Page Zoom in ").log(Status.PASS,
                    "Successfully zoom in the Site Status Page");
        } catch (Exception e) {
            extentReports.createTest("Site Status Page zoom in  ").log(Status.FAIL,
                    "Failed to zoom in the Site Status page . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void getPopUpSiteDetails() {
        try {
            WebElement popupContent = driver.findElement(By.cssSelector(".leaflet-popup-content"));
            String popupDetails = popupContent.getText();
            extentReports.createTest("Hover, Click, and Right-Click on Leaf Item").log(Status.PASS,
                    "Successfully hovered over the parking location symbol, clicked the leaf item, and right-clicked on it.:"
                            + popupDetails);

        } catch (Exception e) {
            extentReports.createTest("Hover, Click, and Right-Click on Leaf Item").log(Status.FAIL,
                    "Failed to hover over the parking location symbol, click the leaf item, or right-click on it. Exception: "
                            + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void deviceOverviewPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("dov-pg")).click();
            extentReports.createTest("Device Overview Page  ").log(Status.PASS,
                    "Successfully Open Device Overview Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Device Overview Page  ").log(Status.FAIL,
                    "Failed to Open the Device Overview page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void deviceOverviewZoomOut() {
        siteStatusZoomOut();
    }

    @Test(priority = 10)
    public void deviceOverviewZoomIn() {
        siteStatusZoomIn();
    }

    @Test(priority = 11)
    public void selectDeviceLocation() {
        try {
            Thread.sleep(5000);
            WebElement elementToHover = driver
                    .findElement(By.cssSelector("img[src='../assets/images/icons/location-ACTIVE.png']"));
            String script = "var element = arguments[0];" +
                    "var mouseEvent = document.createEvent('MouseEvents');" +
                    "mouseEvent.initEvent('mouseover', true, true);" +
                    "element.dispatchEvent(mouseEvent);";
            ((JavascriptExecutor) driver).executeScript(script, elementToHover);

            extentReports.createTest("Select the Device Location ").log(Status.PASS,
                    "Successfully Select the Device Location  in the Device Status Page");
        } catch (Exception e) {
            extentReports.createTest("Select the Device Location  ").log(Status.FAIL,
                    "Failed to Select the Site Location  in the Device Status page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void getDeviceLocationDetails() {
        try {
            Thread.sleep(5000);

            WebElement popupContent = driver.findElement(By.cssSelector(".leaflet-popup-content"));
            String popupDetails = popupContent.getText();

            extentReports.createTest("Device details ").log(Status.PASS,
                    "Site Location with Parking Location Symbol: " + popupDetails);

        } catch (Exception e) {
            extentReports.createTest("Device details  ").log(Status.FAIL,
                    "Failed to get Device details. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 13)
    public void deviceStatus_Page() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("ds-pg")).click();
            extentReports.createTest("Device Status Page  ").log(Status.PASS,
                    "Successfully Open Device Status Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Device Status Page  ").log(Status.FAIL,
                    "Failed to Open the Device Status page . Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void deviceStatus_SelectSite() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
            driver.findElement(By.id("pos_site_name"));
            WebElement site = siteList.get(1);
            String selectedSiteName = site.getText();
            site.click();
            // List<WebElement> siteList =
            // ReservationReports.selectReservationSitesList(driver);
            // driver.findElement(By.id("pos_site_name"));
            // WebElement site = siteList.get(1);
            // String selectedSiteName = site.getText();
            // site.click();
            extentReports.createTest("Device Status site slected").log(Status.PASS,
                    "Successfully selecting the site name : " + selectedSiteName);
        } catch (Exception e) {
            extentReports.createTest("Device Status site selected  ").log(Status.FAIL,
                    "Failed to select the site name . Exception: " + e.getCause());

        }
    }

    @Test(priority = 15)
    public void deviceStatus_SelectDevice() {
        try {
            Thread.sleep(2000);
            WebElement selectElement = driver.findElement(By.id("deviceType"));
            Select select = new Select(selectElement);
            java.util.List<WebElement> options = select.getOptions();

            for (WebElement option : options) {
                Thread.sleep(1000);
                select.selectByVisibleText(option.getText());
                System.out.println("Selected device: " + option.getText());

                extentReports.createTest("In Device Status select device ").log(Status.PASS,
                        "Successfully select the device in device status : " + option.getText());
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("In Device Status select device ").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
            transactionPage(driver, "exception_screenshot");
        }
    }

    @Test(priority = 15)
    public void transactionsPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("tl-pg")).click();
            extentReports.createTest("Transaction Page  ").log(Status.PASS,
                    "Successfully Open  Transactions Page");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Transactions Page  ").log(Status.FAIL,
                    "Failed to Open the Transactions page . Exception: " + e.getCause());
            transactionPage(driver, "exception_screenshot");
        }
    }

    @Test(priority = 16)
    public void transactionsCSV() {
        try {
            Thread.sleep(10000);
            driver.findElement(By.xpath("//div[@id='dailyTransnTable_wrapper']/div[1]/button[1]")).click();
            extentReports.createTest("LiveInfo Transactions Page - CSV").log(Status.PASS,
                    "Successfully download the CSV file in transaction page");
        } catch (Exception e) {
            extentReports.createTest("LiveInfo Transactions page -CSV").log(Status.FAIL,
                    "Failed to download the CSV file in transaction page. Exception: " + e.getCause());
            transactionPage(driver, "exception_screenshot");
        }
    }

    @Test(priority = 17)
    public void transactionsPDF() {
        try {
            Thread.sleep(2000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='dailyTransnTable_wrapper']/div[1]/button[2]"));
            String mainWindowHandle = driver.getWindowHandle();

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

                extentReports.createTest("LiveInfo Transactions Page - PDF").log(Status.PASS,
                        "Successfully downloaded the PDF file in the transactions page and closed the window");
            } else {
                extentReports.createTest("LiveInfo Transactions Page - PDF").log(Status.INFO,
                        "PDF button not found on the transactions page");
            }
        } catch (Exception e) {
            extentReports.createTest("LiveInfo Transactions Page - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in transactions page. Exception: " + e.getCause());
            transactionPage(driver, "exception_screenshot");
        }
    }

    @Test(priority = 18)
    public void nextPageTransactions() {
        try {
            Thread.sleep(3000);
            WebElement secondPageLink = driver.findElement(By.xpath("//a[@data-dt-idx='2']"));

            secondPageLink.click();
            WebElement firstPageLink = driver.findElement(By.xpath("//a[@data-dt-idx='1']"));

            firstPageLink.click();
            Thread.sleep(3000);
            extentReports.createTest("Transactions clicking the next page").log(Status.PASS,
                    "Successfully clicking the next page");
        } catch (Exception e) {
            extentReports.createTest("Transactions clicking the next page").log(Status.FAIL,
                    "Failed to clicking the next page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 19)
    public void getSiteNamesTransactions() {
        try {
            Thread.sleep(2000);

            WebElement table = driver.findElement(By.id("dailyTransnTable"));

            List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

            for (WebElement row : rows) {
                WebElement siteNameElement = row.findElement(By.cssSelector("td.sorting_1"));
                String siteName = siteNameElement.getText();
                extentReports.createTest("Transactions page").log(Status.PASS, "Parking Site Name: " + siteName);
            }

        } catch (Exception e) {
            extentReports.createTest("Transactions page").log(Status.FAIL,
                    "Failed to get parking site names. Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void searchSiteName() {
        try {
            Thread.sleep(2000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#dailyTransnTable_filter input[type='search']"));
            searchInput.clear();
            searchInput.sendKeys("Naroli Road");

            String searchedSiteName = searchInput.getAttribute("value");

            extentReports.createTest("Transactions page").log(Status.PASS,
                    "Successfully searched: " + searchedSiteName);

            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Transactions page").log(Status.FAIL,
                    "Failed to search. Exception: " + e.getCause());
        }
    }

    @Test(priority = 21)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            System.out.println("Logout from LiveInfo page ");
            extentReports.createTest("LiveInfo LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("LiveInfo LogOut").log(Status.FAIL,
                    "Failed to LogOut. Exception: " + e.getCause());
            transactionPage(driver, "exception_screenshot");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            extentReports.flush();
            driver.quit();
        }
    }

    public static void transactionPage(WebDriver driver, String screenshotName) {
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
