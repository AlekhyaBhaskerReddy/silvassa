package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class POSReports extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/POS_Report.html");
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
        System.out.println("Login in POS Reports page ");
        Dashboard.loginDetails(driver, extentReports);
    }

    @Test(priority = 2)
    public void revenueDashboardOpen() {
        Dashboard.revenuedashboardOpen(driver, extentReports);
    }

    @Test(priority = 3)
    public void posRevenuepage() {
        try {
            Thread.sleep(3000);
            driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[2]"))
                    .click();
            Thread.sleep(3000);
            driver.findElement(By.className("pr-pg")).click();
            extentReports.createTest("POS Reports Page ").log(Status.PASS, "Successfully Open pos reports page");
        } catch (Exception e) {
            extentReports.createTest("POS Reports Page ").log(Status.FAIL,
                    "Failed to open the pos reports page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void posRevenueSelectSite() {
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
            extentReports.createTest("POS Reports site slected").log(Status.PASS,
                    "Successfully selecting the site name : " + selectedSiteName);
        } catch (Exception e) {
            extentReports.createTest("POS Reports site selected  ").log(Status.FAIL,
                    "Failed to select the site name . Exception: " + e.getCause());

        }
    }

    @Test(priority = 4)
    public void posRevenuedatesEnabled() {
        Dashboard.revenueDatesEnabled(driver, extentReports);
    }

    @Test(priority = 5)
    public void selectPosRevenueStartDate() {
        Dashboard.selectDashboardStartDate(driver, extentReports);
    }

    @Test(priority = 6)
    public void selectReserveEndDate() {
        Dashboard.selectDashboardEndDate(driver, extentReports);
    }

    @Test(priority = 7)
    public void posRevenueSelectTwoWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Two Wheeler");
            extentReports.createTest("POS Reports vehicle type as Two wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type two wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Two wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 8)
    public void posRevenueposNumber() {
        try {
            List<WebElement> siteList = PosHourlyReport.selectPOSNumber(driver);
            driver.findElement(By.id("pos-sl-number"));
            WebElement site = siteList.get(1);
            String selectedPosNumber = site.getText();
            site.click();

            extentReports.createTest("In POS reports Select the POS Serial Number").log(Status.PASS,
                    "Successfully selecting POS Serial Number:" + selectedPosNumber);

        } catch (Exception e) {
            extentReports.createTest("In POS reports Select the POS Serial Number").log(Status.FAIL,
                    "Failed to select the POS serail Number. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void posRevenueSubmitButtonTwo() {
        try {
            driver.findElement(By.xpath("//button[@id='posRevenueBtn']")).click();
            Thread.sleep(1000);
            extentReports.createTest("In POS reports click the submit button").log(Status.PASS,
                    "Successfully clicking the submit button in Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In POS reports click the submit button").log(Status.FAIL,
                    "Failed to click the submit button in Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void posRevenueFormTwoW() {
        try {
            Thread.sleep(5000);
            WebElement revenueViewdetails = driver
                    .findElement(By
                            .xpath("//*[@id='posRevenueForm']/div/div[4]/button[2]"));
            revenueViewdetails.click();
            extentReports.createTest("In POS reports click the view details button").log(Status.PASS,
                    "Successfully clicking the view details button in Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In POS reports click the view details button").log(Status.FAIL,
                    "Failed to click view details button in Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void posRevenueShiftMorningTwoW() {
        try {
            driver.findElement(By.id("vehicleReportsFilter")).sendKeys("Morning");
            Thread.sleep(2000);
            extentReports.createTest("In POS reports select the shift as Morning").log(Status.PASS,
                    "Successfully select the morning shift in POS revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports select the shift as Morning").log(Status.FAIL,
                    "Failed to select the morning shift in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void posRevenueShiftEveningTwoW() {
        try {
            driver.findElement(By.id("vehicleReportsFilter")).sendKeys("Evening");
            Thread.sleep(2000);
            extentReports.createTest("In POS reports select the shift as Evening").log(Status.PASS,
                    "Successfully select the Evening shift in POS revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports select the shift as Evening").log(Status.FAIL,
                    "Failed to select the Evening shift in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void posRevenueShiftAllTwoW() {
        try {
            driver.findElement(By.id("vehicleReportsFilter")).sendKeys("All");
            Thread.sleep(2000);
            extentReports.createTest("In POS reports select the shift as All").log(Status.PASS,
                    "Successfully select the All shift in POS revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports select the shift as All").log(Status.FAIL,
                    "Failed to select the All shift in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void posRevenueCSVTwoW() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("posRevenueCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In POS reports download CSV file").log(Status.PASS,
                        "Successfully clicked the CSV button");
            } else {
                extentReports.createTest("In POS reports download CSV file").log(Status.INFO, "CSV button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In POS reports downlaod CSV file").log(Status.FAIL,
                    "Failed to download the CSV file in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void posRevenuePDFTwoW() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='posRevModal']/div/div//button[@id='posRevenuePdf']"));
            pdfButton.click();
            Thread.sleep(3000);
            String mainWindowHandle = driver.getWindowHandle();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='posRevenuePdf']")));

            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isEnabled" + selectElement.isEnabled());
            System.out.println("selectElement.isSelected" + selectElement.isSelected());

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait1.until(ExpectedConditions.numberOfWindowsToBe(2));

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
        } catch (

        Exception e) {
            extentReports.createTest("In POS reports download the PDF file ").log(Status.FAIL,
                    "Failed to download the PDF file in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 16)
    public void posRevenuePopUPCloseTwoW() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.className("close")).click();
            extentReports.createTest("In POS reports POP Up").log(Status.PASS,
                    "Successfully closed the pop up in POS revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports POP Up").log(Status.FAIL,
                    "Failed to close the pop up in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 17)
    public void twoWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Two Wheeler : " + value);
            extentReports.createTest("In POS reports Two Wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Two Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    @Test(priority = 18)
    public void posRevenueSelectFourWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Four Wheeler");
            extentReports.createTest("POS Reports vehicle type as Four wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type Four wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Four wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 19)
    public void posRevenueSubmitBtnFourW() {
        posRevenueSubmitButtonTwo();
    }

    @Test(priority = 20)
    public void posRevenueFormFourW() {
        posRevenueFormTwoW();
    }

    @Test(priority = 21)
    public void posRevenueShiftMorningFourW() {
        posRevenueShiftMorningTwoW();
    }

    @Test(priority = 22)
    public void posRevenueShiftEveningFouW() {
        posRevenueShiftEveningTwoW();
    }

    @Test(priority = 23)
    public void posRevenueShiftAllFourW() {
        posRevenueShiftAllTwoW();
    }

    @Test(priority = 24)
    public void posRevenueCSVFourW() {
        posRevenueCSVTwoW();
    }

    @Test(priority = 25)
    public void posRevenuePDFFourW() {
        posRevenuePDFTwoW();
    }

    @Test(priority = 26)
    public void posRevenuePopUPCloseFourW() {
        posRevenuePopUPCloseTwoW();
    }

    @Test(priority = 27)
    public void fourWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Four Wheeler: " + value);
            extentReports.createTest("In POS reports Four wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Foure Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    @Test(priority = 28)
    public void posRevenueSelectThreeWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Three Wheeler");
            extentReports.createTest("POS Reports vehicle type as Three wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type Three wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Three wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 29)
    public void posRevenueSubmitBtnThreeW() {
        posRevenueSubmitButtonTwo();
    }

    @Test(priority = 30)
    public void posRevenueFormThreeW() {
        posRevenueFormTwoW();
    }

    @Test(priority = 31)
    public void posRevenueShiftMorningThreeW() {
        posRevenueShiftMorningTwoW();
    }

    @Test(priority = 32)
    public void posRevenueShiftEveningThreeW() {
        posRevenueShiftEveningTwoW();
    }

    @Test(priority = 33)
    public void posRevenueShiftAllThreeW() {
        posRevenueShiftAllTwoW();
    }

    @Test(priority = 34)
    public void posRevenueCSVThreeW() {
        posRevenueCSVTwoW();
    }

    @Test(priority = 35)
    public void posRevenuePDFThreeW() {
        posRevenuePDFTwoW();
    }

    @Test(priority = 36)
    public void posRevenuePopUPCloseThreeW() {
        posRevenuePopUPCloseTwoW();
    }

    @Test(priority = 37)
    public void threeWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Three Wheeler: " + value);
            extentReports.createTest("In POS reports Four wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Foure Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 38)
    public void posRevenueSelectSixWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Six Wheeler");
            extentReports.createTest("POS Reports vehicle type as Six wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type Three wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Six wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    // @Test(priority = 39)
    public void posRevenueSubmitBtnSixW() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 40)
    public void posRevenueFormSixW() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 41)
    public void posRevenueShiftMorningSixW() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 42)
    public void posRevenueShiftEveningSixW() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 43)
    public void posRevenueShiftAllSixW() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 44)
    public void posRevenueCSVSixW() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 45)
    public void posRevenuePDFSixW() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 46)
    public void posRevenuePopUPCloseSixW() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 47)
    public void sixWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Six Wheeler: " + value);
            extentReports.createTest("In POS reports Six wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Six Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 48)
    public void posRevenueSelectEightWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Eight Wheeler");
            extentReports.createTest("POS Reports vehicle type as Eight wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type Eight wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Eight wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    // @Test(priority = 49)
    public void posRevenueSubmitBtnEightW() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 50)
    public void posRevenueFormEightW() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 51)
    public void posRevenueShiftMorningEightW() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 52)
    public void posRevenueShiftEveningEightW() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 53)
    public void posRevenueShiftAllEightW() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 54)
    public void posRevenueCSVEightW() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 55)
    public void posRevenuePDFEightW() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 56)
    public void posRevenuePopUPCloseEightW() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 57)
    public void eightWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Eight Wheeler: " + value);
            extentReports.createTest("In POS reports Eight wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Eight Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 58)
    public void posRevenueSelectTenWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Ten Wheeler");
            extentReports.createTest("POS Reports vehicle type as Ten wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type Ten wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Ten wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    // @Test(priority = 59)
    public void posRevenueSubmitBtnTenW() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 60)
    public void posRevenueFormTenW() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 61)
    public void posRevenueShiftMorningTenW() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 62)
    public void posRevenueShiftEveningTenW() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 63)
    public void posRevenueShiftAllTenW() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 64)
    public void posRevenueCSVTenW() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 65)
    public void posRevenuePDFTenW() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 66)
    public void posRevenuePopUPCloseTenW() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 67)
    public void tenWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Ten Wheeler: " + value);
            extentReports.createTest("In POS reports Ten wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Ten Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 68)
    public void posRevenueSelectTwelveWheeler() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("vehicleType")).sendKeys("Twelve Wheeler");
            extentReports.createTest("POS Reports vehicle type as Twelve wheeler").log(Status.PASS,
                    "Successfully selecting vehicle type Twelve wheeler");

        } catch (Exception e) {
            extentReports.createTest("POS Reports Vehicle type as Twelve wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    // @Test(priority = 69)
    public void posRevenueSubmitBtnTwelveW() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 70)
    public void posRevenueFormTwelveW() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 71)
    public void posRevenueShiftMorningTwelveW() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 72)
    public void posRevenueShiftEveningTwelveW() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 73)
    public void posRevenueShiftAllTwelveW() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 74)
    public void posRevenueCSVTwelveW() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 75)
    public void posRevenuePDFTwelveW() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 76)
    public void posRevenuePopUPCloseTwelveW() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 77)
    public void twelveWheelerPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue Twelve Wheeler: " + value);
            extentReports.createTest("In POS reports Twelve wheeler Revenue ").log(Status.PASS,
                    "Successfully displaying the POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Twelve Wheeler Revenue").log(Status.FAIL,
                    "Failed to display the POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 78)
    public void selectRevenueIOS() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("IOS")).click();

            extentReports.createTest("In POS reports select IOS ").log(Status.PASS,
                    "Successfully selecting the IOS in POS Revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports select IOS ").log(Status.FAIL,
                    "Failed to select the IOS in POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 79)
    public void posRevenueSubmitBtnIOS() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 80)
    public void posRevenueFormIOS() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 81)
    public void posRevenueShiftMorningIOS() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 82)
    public void posRevenueShiftEveningIOS() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 83)
    public void posRevenueShiftAllIOS() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 84)
    public void posRevenueCSVIOS() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 85)
    public void posRevenuePDFIOS() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 86)
    public void posRevenuePopUPCloseIOS() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 87)
    public void iOSPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue IOS Wheeler: " + value);
            extentReports.createTest("In POS reports IOS Revenue ").log(Status.PASS,
                    "Successfully displaying the IOS POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports IOS Revenue").log(Status.FAIL,
                    "Failed to display the IOS POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 88)
    public void selectRevenueAndroid() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("ANDROID")).click();

            extentReports.createTest("In POS reports select ANDROID ").log(Status.PASS,
                    "Successfully selecting the ANDROID in POS Revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports select ANDROID ").log(Status.FAIL,
                    "Failed to select the ANDROID in POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 89)
    public void posRevenueSubmitBtnAndroid() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 90)
    public void posRevenueFormAndroid() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 91)
    public void posRevenueShiftMorningAndroid() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 92)
    public void posRevenueShiftEveningAndroid() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 93)
    public void posRevenueShiftAllAndroid() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 94)
    public void posRevenueCSVAndroid() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 95)
    public void posRevenuePDFAndroid() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 96)
    public void posRevenuePopUPCloseAndroid() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 97)
    public void androidPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue ANDROID Wheeler: " + value);
            extentReports.createTest("In POS reports ANDROID Revenue ").log(Status.PASS,
                    "Successfully displaying the ANDROID POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports ANDROID Revenue").log(Status.FAIL,
                    "Failed to display the IOS ANDROID Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 98)
    public void selectRevenueFasTag() {
        try {
            driver.findElement(By.id("FASTAG")).click();

            extentReports.createTest("In POS reports select FASTAG ").log(Status.PASS,
                    "Successfully selecting the FASTAG in POS Revenue");
        } catch (Exception e) {
            extentReports.createTest("In POS reports select FASTAG ").log(Status.FAIL,
                    "Failed to select the FASTAG in POS Revenue. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 99)
    public void fasTagRevenueStatus() {
        try {
            Thread.sleep(3000);

            String title = driver.findElement(By.className("swal2-header")).getText();
            String resultDescription = driver.findElement(By.id("swal2-content")).getText();

            System.out.println("Site have FasTag : " + resultDescription);

            Thread.sleep(3000);

            extentReports.createTest("Site have FasTag").log(Status.PASS,
                    "FasTag successfully selected device: " + title + resultDescription)
                    .log(Status.WARNING,
                            "FasTag successfully selected device: " + title + resultDescription);
        } catch (Exception e) {
            extentReports.createTest("Site have FasTag").log(Status.FAIL,
                    "Failed to the site dont have fastag device. Exception: " + e.getMessage());

            // dashboard.occupancyTabCapture(driver, "exception_screenshot");

        }
    }

    // @Test(priority = 100)
    public void clickRevenueGotItBtn() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            okButton.click();
            extentReports.createTest("Alert after selecting the fastag ").log(
                    Status.PASS,
                    "Clicked the 'GotIt' button in pop-up to close.");
        } catch (Exception e) {
            extentReports.createTest("Alert after selecting the fastag").log(
                    Status.FAIL,
                    "Failed to click the 'GotIt' button in popup to close. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 101)
    public void posRevenueSubmitBtnFastag() {
        posRevenueSubmitButtonTwo();
    }

    // @Test(priority = 102)
    public void posRevenueFormFastTag() {
        posRevenueFormTwoW();
    }

    // @Test(priority = 103)
    public void posRevenueShiftMorningFasTag() {
        posRevenueShiftMorningTwoW();
    }

    // @Test(priority = 104)
    public void posRevenueShiftEveningFasTag() {
        posRevenueShiftEveningTwoW();
    }

    // @Test(priority = 105)
    public void posRevenueShiftAllFasTag() {
        posRevenueShiftAllTwoW();
    }

    // @Test(priority = 106)
    public void posRevenueCSVFasTag() {
        posRevenueCSVTwoW();
    }

    // @Test(priority = 107)
    public void posRevenuePDFFasTag() {
        posRevenuePDFTwoW();
    }

    // @Test(priority = 108)
    public void posRevenuePopUPCloseFasTag() {
        posRevenuePopUPCloseTwoW();
    }

    // @Test(priority = 109)
    public void fasTagPOSRevenue() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_revenue']"));
            String value = revenue.getText();
            System.out.println("POS Revenue FASTAG Wheeler: " + value);
            extentReports.createTest("In POS reports FASTAG Revenue ").log(Status.PASS,
                    "Successfully displaying the FASTAG POS Revenue" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports FASTAG Revenue").log(Status.FAIL,
                    "Failed to display the FASTAG POS Revenue. Exception: " + e.getCause());
        }
    }

    @Test(priority = 110)
    public void posTransactions() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pos_transaction")).click();

            extentReports.createTest("POS Transactions tab in POS Reports Page ").log(Status.PASS,
                    "Successfully Open Pos Transactions page");
        } catch (Exception e) {
            extentReports.createTest("POS Transactions tab in POS Reports Page ").log(Status.FAIL,
                    "Failed to open the Pos Transactions page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 111)
    public void posTransactionsDateRange() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//div[@id='posTransactionContainer']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='posTransactionContainer']//div[@id='dayRangeFilter']/label[3]"))
                    .click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//div[@id='posTransactionContainer']//div[@id='dayRangeFilter']/label[4]"))
                    .click();
            extentReports.createTest("POS Reports in POS Transaction Dates Enabled ").log(Status.PASS,
                    "Successfully selecting the dates day/week/month");
        } catch (Exception e) {
            extentReports.createTest("POS Reports in POS Transaction Dates Enabled ").log(Status.FAIL,
                    "Failed to select the dates day/week/month. Exception: " + e.getCause());
        }
    }

    @Test(priority = 112)
    public void posTransaction_StartDate() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='posTransactionContainer']//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
            Thread.sleep(2000);

            dateElement.click();
            extentReports.createTest("Start Date in POS Transaction Reports").log(Status.PASS,
                    "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in POS Transaction Reports").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 113)
    public void posTransaction_EndDate() {
        try {
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='available' and @data-title='r3c4']"));

            dateElement.click();
            Thread.sleep(3000);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("End Date in POS Transaction reports").log(Status.PASS,
                    "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in POS Transaction Reports").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 114)
    public void posTransaction_TwoWheeler() {
        try {
            Thread.sleep(2000);
            WebElement vehicleTypeElement = driver
                    .findElement(By.xpath("//div[@id='posTransactionContainer']//select[@id='vehicleType']"));
            String selectedTransactionType = "Two Wheeler";
            vehicleTypeElement.sendKeys(selectedTransactionType);

            extentReports.createTest("POS Reports transaction tab vehicle type ").log(Status.PASS,
                    "Successfully selecting vehicle type  :" + selectedTransactionType);

        } catch (Exception e) {
            extentReports.createTest("POS Reports transaction tab Vehicle type as Two wheeler").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 115)
    public void transaction_TwoWheeler_EntryType() {
        try {
            Thread.sleep(2000);
            WebElement transactionTypeElement = driver
                    .findElement(By.xpath("//div[@id='posTransactionContainer']//select[@id='transType']"));
            String selectedTransactionType = "Entry";
            transactionTypeElement.sendKeys(selectedTransactionType);

            extentReports.createTest("POS Reports transaction tab transaction type ").log(Status.PASS,
                    "Successfully selected transaction type: " + selectedTransactionType);

        } catch (Exception e) {
            extentReports.createTest("POS Reports transaction type ").log(Status.FAIL,
                    "Failed to select the transaction type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 116)
    public void transaction_TwoWheeler_PosSerial() {
        try {
            List<WebElement> siteList = PosHourlyReport.selectPOSNumber(driver);
            driver.findElement(By.id("pos-sl-number"));
            WebElement site = siteList.get(2);
            String selectedPosNumber = site.getText();
            site.click();

            extentReports.createTest("In POS reports transaction tab  & Select the POS Serial Number").log(Status.PASS,
                    "Successfully selecting POS Serial Number:" + selectedPosNumber);

        } catch (Exception e) {
            extentReports.createTest("In POS reports transaction tab & Select the POS Serial Number").log(Status.FAIL,
                    "Failed to select the POS serail Number. Exception: " + e.getCause());
        }
    }

    @Test(priority = 117)
    public void transaction_TwoWheeler_Entry_SubmitBtn() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//button[@id='posTransBtn']")).click();
            extentReports.createTest("In POS reports in transaction tab click the submit button").log(Status.PASS,
                    "Successfully clicking the submit button in Revenue Tab");
        } catch (Exception e) {
            extentReports.createTest("In POS reports in transaction tab click the submit button").log(Status.FAIL,
                    "Failed to click the submit button in Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 118)
    public void twoWheeler_Entry_Transaction() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = revenue.getText();
            System.out.println("POS Transaction Two Wheeler Entry Transaction: " + value);
            extentReports.createTest("In POS reports Two Wheeler Transaction ").log(Status.PASS,
                    "Successfully displaying the POS Transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Two Wheeler Entry Transaction").log(Status.FAIL,
                    "Failed to display the POS Transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 119)
    public void transaction_TwoWheeler_Entry_ViewDetails() {
        try {
            Thread.sleep(2000);
            driver
                    .findElement(By.xpath("//*[@id='posTransactionForm']/div/div[4]/button[2]"))
                    .click();
            extentReports.createTest("In POS reports in transaction tab click the view details button").log(Status.PASS,
                    "Successfully clicking the view details button in transaction tab");
        } catch (Exception e) {
            extentReports.createTest("In POS reports in transaction tab click the view details button").log(Status.FAIL,
                    "Failed to click view details button in transaction tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 120)
    public void transaction_TwoWheeler_Entry_length() {
        try {
            Thread.sleep(2000);
            WebElement dropdown = driver.findElement(By.name("posTransTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("Pos Transaction table length").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("Pos Transaction table length").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 121)
    public void transaction_TwoWheeler_Entry_CsvFile() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("posTransCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In POS reports transaction tab download CSV file").log(Status.PASS,
                        "Successfully clicked the CSV button");
            } else {
                extentReports.createTest("In POS reports transaction tab download CSV file").log(Status.INFO,
                        "CSV button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In POS reports transaction tab downlaod CSV file").log(Status.FAIL,
                    "Failed to download the CSV file in POS transaction tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 122)
    public void transaction_TwoWheeler_Entry_PdfFile() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.id("posTransPdf"));
            pdfButton.click();
            Thread.sleep(3000);
            String mainWindowHandle = driver.getWindowHandle();

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait1.until(ExpectedConditions.numberOfWindowsToBe(2));

                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {

                        driver.switchTo().window(windowHandle);
                        driver.close();

                        driver.switchTo().window(mainWindowHandle);
                        extentReports.createTest("PDF Button transaction tab ").log(Status.PASS,
                                "Successfully clicked the PDF button");
                        return;

                    }
                }

            } else {
                extentReports.createTest("PDF Button transaction tab").log(Status.INFO, "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In POS reports transaction tab download the PDF file ").log(Status.FAIL,
                    "Failed to download the PDF file in POS transaction tab. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 123)
    public void transaction_TwoWheeler_Entry_PopUpClose() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//div[@id='posTransModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In POS reports transaction tab POP Up").log(Status.PASS,
                    "Successfully closed the pop up in POS transaction tab");
        } catch (Exception e) {
            extentReports.createTest("In POS reports transaction tab POP Up").log(Status.FAIL,
                    "Failed to close the pop up in POS transaction tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 124)
    public void transaction_TwoWheeler_ExitType() {
        try {
            Thread.sleep(2000);
            WebElement transactionTypeElement = driver
                    .findElement(By.xpath("//div[@id='posTransactionContainer']//select[@id='transType']"));
            String selectedTransactionType = "Exit";
            transactionTypeElement.sendKeys(selectedTransactionType);

            extentReports.createTest("POS Reports transaction tab transaction type ").log(Status.PASS,
                    "Successfully selected transaction type: " + selectedTransactionType);

        } catch (Exception e) {
            extentReports.createTest("POS Reports transaction tab transaction type ").log(Status.FAIL,
                    "Failed to select the transaction type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 125)
    public void transaction_TwoWheeler_Exit_SubmitBtn() {
        transaction_TwoWheeler_Entry_SubmitBtn();
    }

    @Test(priority = 126)
    public void twoWheeler_Exit_Transactions() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = revenue.getText();
            System.out.println("POS Transaction Two Wheeler Exit transaction: " + value);
            extentReports.createTest("In POS reports Two Wheeler Exit transaction ").log(Status.PASS,
                    "Successfully displaying the POS transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Two Wheeler Exit transaction").log(Status.FAIL,
                    "Failed to display the POS transactions. Exception: " + e.getCause());
        }
    }

    @Test(priority = 127)
    public void transaction_TwoWheeler_Exit_ViewDetails() {
        transaction_TwoWheeler_Entry_ViewDetails();
    }

    @Test(priority = 128)
    public void transaction_TwoWheeler_Exit_Length() {
        transaction_TwoWheeler_Entry_length();
    }

    @Test(priority = 129)
    public void transaction_TwoWheeler_Exit_CsvFile() {
        transaction_TwoWheeler_Entry_CsvFile();
    }

    @Test(priority = 130)
    public void transaction_TwoWheeler_Exit_PdfFile() {
        transaction_TwoWheeler_Entry_PdfFile();
    }

    @Test(priority = 131)
    public void transaction_TwoWheeler_Exit_PopUpClose() {
        transaction_TwoWheeler_Entry_PopUpClose();
    }

    @Test(priority = 132)
    public void posTransaction_FourWheeler() {
        try {
            Thread.sleep(2000);
            WebElement vehicleTypeElement = driver
                    .findElement(By.xpath("//div[@id='posTransactionContainer']//select[@id='vehicleType']"));
            String selectedTransactionType = "Four Wheeler";
            vehicleTypeElement.sendKeys(selectedTransactionType);

            extentReports.createTest("POS Reports transaction tab vehicle type ").log(Status.PASS,
                    "Successfully selecting vehicle type  :" + selectedTransactionType);

        } catch (Exception e) {
            extentReports.createTest("POS Reports transaction tab Vehicle type ").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 133)
    public void transaction_FourWheeler_EntryType() {
        transaction_TwoWheeler_EntryType();
    }

    @Test(priority = 134)
    public void transaction_FourWheeler_PosSerial() {
        transaction_TwoWheeler_PosSerial();
    }

    @Test(priority = 135)
    public void transaction_FourWheeler_Entry_SubmitBtn() {
        transaction_TwoWheeler_Entry_SubmitBtn();
    }

    @Test(priority = 136)
    public void fourWheeler_Entry_Transaction() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = revenue.getText();
            System.out.println("POS transaction Four Wheeler Entry transaction: " + value);
            extentReports.createTest("In POS reports Four Wheeler transaction ").log(Status.PASS,
                    "Successfully displaying the POS transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Four Wheeler Entry transaction").log(Status.FAIL,
                    "Failed to display the POS transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 137)
    public void transaction_FourWheeler_Entry_ViewDetails() {
        transaction_TwoWheeler_Entry_ViewDetails();
    }

    @Test(priority = 138)
    public void transaction_FourWheeler_Entry_Length() {
        transaction_TwoWheeler_Entry_length();
    }

    @Test(priority = 139)
    public void transaction_FourWheeler_Entry_CsvFile() {
        transaction_TwoWheeler_Entry_CsvFile();
    }

    @Test(priority = 143)
    public void transaction_FourWheeler_Entry_PdfFile() {
        transaction_TwoWheeler_Entry_PdfFile();
    }

    @Test(priority = 144)
    public void transaction_FourWheeler_Entry_PopUpClose() {
        transaction_TwoWheeler_Entry_PopUpClose();
    }

    @Test(priority = 145)
    public void transaction_FourWheeler_ExitType() {
        transaction_TwoWheeler_ExitType();
    }

    @Test(priority = 146)
    public void transaction_FourWheeler_Exit_SubmitBtn() {
        transaction_TwoWheeler_Entry_SubmitBtn();
    }

    @Test(priority = 147)
    public void fourWheeler_Exit_Transaction() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = revenue.getText();
            System.out.println("POS Transaction Four Wheeler Exit transaction: " + value);
            extentReports.createTest("In POS reports Four Wheeler Exit transaction ").log(Status.PASS,
                    "Successfully displaying the POS transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Four Wheeler Exit transaction").log(Status.FAIL,
                    "Failed to display the POS transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 148)
    public void transaction_FourWheeler_Exit_ViewDetails() {
        transaction_TwoWheeler_Entry_ViewDetails();
    }

    @Test(priority = 149)
    public void transaction_FourWheeler_Exit_Length() {
        transaction_TwoWheeler_Entry_length();
    }

    @Test(priority = 150)
    public void transaction_FourWheeler_Exit_CsvFile() {
        transaction_TwoWheeler_Entry_CsvFile();
    }

    @Test(priority = 115)
    public void transaction_FourWheeler_Exit_PdfFile() {
        transaction_TwoWheeler_Entry_PdfFile();
    }

    @Test(priority = 151)
    public void transaction_FourWheeler_Exit_PopUpClose() {
        transaction_TwoWheeler_Entry_PopUpClose();
    }

    @Test(priority = 152)
    public void posTransaction_AllWheeler() {
        try {
            Thread.sleep(2000);
            WebElement vehicleTypeElement = driver
                    .findElement(By.xpath("//div[@id='posTransactionContainer']//select[@id='vehicleType']"));
            String selectedTransactionType = "ALL";
            vehicleTypeElement.sendKeys(selectedTransactionType);

            extentReports.createTest("POS Reports transaction tab vehicle type ").log(Status.PASS,
                    "Successfully selecting vehicle type  :" + selectedTransactionType);

        } catch (Exception e) {
            extentReports.createTest("POS Reports transaction tab Vehicle type ").log(Status.FAIL,
                    "Failed to select the vehicle type. Exception: " + e.getCause());

        }
    }

    @Test(priority = 153)
    public void transaction_allWheeler_EntryType() {
        transaction_TwoWheeler_EntryType();
    }

    @Test(priority = 154)
    public void transaction_allWheeler_PosSerial() {
        transaction_TwoWheeler_PosSerial();
    }

    @Test(priority = 155)
    public void transaction_allWheeler_Entry_SubmitBtn() {
        transaction_TwoWheeler_Entry_SubmitBtn();
    }

    @Test(priority = 156)
    public void all_Entry_Transaction() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = revenue.getText();
            System.out.println("POS Transaction All Entry transaction: " + value);
            extentReports.createTest("In POS reports All Entry transaction ").log(Status.PASS,
                    "Successfully displaying the POS transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports All Entry transaction").log(Status.FAIL,
                    "Failed to display the POS transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 157)
    public void transaction_allWheeler_Entry_ViewDetails() {
        transaction_TwoWheeler_Entry_ViewDetails();
    }

    @Test(priority = 158)
    public void transaction_allWheeler_Entry_Length() {
        transaction_TwoWheeler_Entry_length();
    }

    @Test(priority = 159)
    public void transaction_allWheeler_Entry_CsvFile() {
        transaction_TwoWheeler_Entry_CsvFile();
    }

    @Test(priority = 160)
    public void transaction_allWheeler_Entry_PdfFile() {
        transaction_TwoWheeler_Entry_PdfFile();
    }

    @Test(priority = 161)
    public void transaction_allWheeler_Entry_PopUpClose() {
        transaction_TwoWheeler_Entry_PopUpClose();
    }

    @Test(priority = 161)
    public void transaction_allWheeler_ExitType() {
        transaction_TwoWheeler_ExitType();
    }

    @Test(priority = 162)
    public void transaction_allWheeler_Exit_SubmitBtn() {
        transaction_TwoWheeler_Entry_SubmitBtn();
    }

    @Test(priority = 163)
    public void all_Exit_Transaction() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = revenue.getText();
            System.out.println("POS Transaction All Exit transaction: " + value);
            extentReports.createTest("In POS reports All Exit transaction ").log(Status.PASS,
                    "Successfully displaying the POS transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports All Exit transaction").log(Status.FAIL,
                    "Failed to display the POS transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 164)
    public void transaction_allWheeler_Exit_ViewDetails() {
        transaction_TwoWheeler_Entry_ViewDetails();
    }

    @Test(priority = 165)
    public void transaction_allWheeler_Exit_Length() {
        transaction_TwoWheeler_Entry_length();
    }

    @Test(priority = 166)
    public void transaction_allWheeler_Exit_CsvFile() {
        transaction_TwoWheeler_Entry_CsvFile();
    }

    @Test(priority = 167)
    public void transaction_allWheeler_Exit_PdfFile() {
        transaction_TwoWheeler_Entry_PdfFile();
    }

    @Test(priority = 168)
    public void transaction_allWheeler_Exit_PopUpClose() {
        transaction_TwoWheeler_Entry_PopUpClose();
    }

    // @Test(priority = 169)
    public void selectTrans_IOS() {
        selectRevenueIOS();
    }

    // @Test(priority = 170)
    public void trans_IOS_SubmitBtn() {
        transaction_FourWheeler_Entry_SubmitBtn();
    }

    // @Test(priority = 171)
    public void trans_IOS_ViewDetails() {
        transaction_FourWheeler_Entry_ViewDetails();
    }

    // @Test(priority = 172)
    public void trans_IOS_CsvFile() {
        transaction_FourWheeler_Entry_Length();
        transaction_FourWheeler_Entry_CsvFile();
    }

    // @Test(priority = 173)
    public void trans_IOS_PdfFile() {
        transaction_FourWheeler_Entry_PdfFile();
    }

    // @Test(priority = 174)
    public void trans_IOS_PopUpClose() {
        transaction_FourWheeler_Entry_PopUpClose();
    }

    // @Test(priority = 175)
    public void iOSPosTransaction() {
        try {
            Thread.sleep(2000);
            WebElement transaction = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = transaction.getText();
            System.out.println("POS Transaction IOS : " + value);
            extentReports.createTest("In POS reports IOS Transaction ").log(Status.PASS,
                    "Successfully displaying the IOS POS Transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports IOS Transaction").log(Status.FAIL,
                    "Failed to display the IOS POS Transaction. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 176)
    public void selectTrans_Android() {
        selectRevenueAndroid();
    }

    // @Test(priority = 178)
    public void trans_Android_SubmitBtn() {
        transaction_FourWheeler_Entry_SubmitBtn();
    }

    // @Test(priority = 179)
    public void trans_Android_ViewDetails() {
        transaction_FourWheeler_Entry_ViewDetails();
    }

    // @Test(priority = 180)
    public void trans_Android_CsvFile() {
        transaction_FourWheeler_Entry_Length();
        transaction_FourWheeler_Entry_CsvFile();
    }

    // @Test(priority = 181)
    public void trans_Android_PdfFile() {
        transaction_FourWheeler_Entry_PdfFile();
    }

    // @Test(priority = 182)
    public void trans_Android_PopUpClose() {
        transaction_FourWheeler_Entry_PopUpClose();
    }

    // @Test(priority = 183)
    public void androidPosTransaction() {
        try {
            Thread.sleep(2000);
            WebElement transaction = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = transaction.getText();
            System.out.println("POS Transaction Android : " + value);
            extentReports.createTest("In POS reports Android Transaction ").log(Status.PASS,
                    "Successfully displaying the Android POS Transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Android Transaction").log(Status.FAIL,
                    "Failed to display the Android POS Transaction. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 184)
    public void selectTransFastag() {
        selectRevenueFasTag();
    }

    // @Test(priority = 185)
    public void fasTagTransStatus() {
        fasTagRevenueStatus();
    }

    // @Test(priority = 186)
    public void clickTransGotItBtn() {
        clickRevenueGotItBtn();
    }

    // @Test(priority = 187)
    public void trans_FasTag_SubmitBtn() {
        transaction_FourWheeler_Entry_SubmitBtn();
    }

    // @Test(priority = 188)
    public void trans_FasTag_ViewDetails() {
        transaction_FourWheeler_Entry_ViewDetails();
    }

    // @Test(priority = 189)
    public void trans_FasTag_CsvFile() {
        transaction_FourWheeler_Entry_Length();
        transaction_FourWheeler_Entry_CsvFile();
    }

    // @Test(priority = 190)
    public void trans_FasTag_PdfFile() {
        transaction_FourWheeler_Entry_PdfFile();
    }

    // @Test(priority = 191)
    public void trans_FasTag_PopUpClose() {
        transaction_FourWheeler_Entry_PopUpClose();
    }

    // @Test(priority = 192)
    public void fasTagPosTransaction() {
        try {
            Thread.sleep(2000);
            WebElement transaction = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = transaction.getText();
            System.out.println("POS Transaction FasTag : " + value);
            extentReports.createTest("In POS reports FasTag Transaction ").log(Status.PASS,
                    "Successfully displaying the FasTag POS Transaction" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports FasTag Transaction").log(Status.FAIL,
                    "Failed to display the FasTag POS Transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 193)
    public void posAttender() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pos_attender")).click();
            extentReports.createTest("Pos_Attender Page ").log(Status.PASS, "Successfully Open Pos_Attender page");
        } catch (Exception e) {
            extentReports.createTest("Pos_Attender Page ").log(Status.FAIL,
                    "Failed to open the Pos Pos_Attender page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 194)
    public void posAttenderDay() {
        try {
            WebElement radioButton = driver
                    .findElement(
                            By.xpath("//div[@id='posAttenderContainer']//div[@id='dayRangeFilter']/label[1]"));
            radioButton.click();
            Thread.sleep(3000);
            if (radioButton.isSelected()) {
                System.out.println("Radio button is selected.");
            } else {
                System.out.println("Radio button is not selected.");
            }
            extentReports.createTest("POS Reports in POS Attender Day Enabled ").log(Status.PASS,
                    "Successfully selecting the dates day");
        } catch (Exception e) {
            extentReports.createTest("POS Reports in POS Attender Day Enabled ").log(Status.FAIL,
                    "Failed to select the dates day. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 195)
    public void attenderUsageDay() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_attender']"));
            String value = revenue.getText();
            System.out.println("Attender Usage Day: " + value);
            extentReports.createTest("Attender Usage in a Day").log(Status.PASS,
                    "Successfully displaying Attender Usage in a Day" + value);
        } catch (Exception e) {
            extentReports.createTest("Attender Usage in a Day").log(Status.FAIL,
                    "Failed to display Attender Usage in a Day. Exception: " + e.getCause());
        }
    }

    @Test(priority = 196)
    public void posAttenderWeek() {
        try {
            WebElement radioButtonWeek = driver
                    .findElement(By.xpath("//div[@id='posAttenderContainer']//div[@id='dayRangeFilter']/label[2]"));
            radioButtonWeek.click();
            Thread.sleep(3000);
            if (radioButtonWeek.isSelected()) {
                System.out.println("Radio button is selected.");
            } else {
                System.out.println("Radio button is not selected.");
            }
            extentReports.createTest("POS Reports in POS Attender Week Enabled ").log(Status.PASS,
                    "Successfully selecting the dates week");
        } catch (Exception e) {
            extentReports.createTest("POS Reports in POS Attender Week Enabled ").log(Status.FAIL,
                    "Failed to select the dates week. Exception: " + e.getCause());
        }
    }

    @Test(priority = 197)
    public void attenderUsageWeek() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_attender']"));
            String value = revenue.getText();
            System.out.println("Attender Usage Week: " + value);
            extentReports.createTest("Attender Usage in a Week").log(Status.PASS,
                    "Successfully displaying Attender Usage in a week" + value);
        } catch (Exception e) {
            extentReports.createTest("Attender Usage in a Week").log(Status.FAIL,
                    "Failed to display Attender Usage in a week. Exception: " + e.getCause());
        }
    }

    @Test(priority = 198)
    public void posAttenderMonth() {
        try {
            WebElement radioButtonMonth = driver
                    .findElement(By.xpath("//div[@id='posAttenderContainer']//div[@id='dayRangeFilter']/label[3]"));
            radioButtonMonth.click();
            Thread.sleep(3000);
            if (radioButtonMonth.isSelected()) {
                System.out.println("Radio button is selected.");
            } else {
                System.out.println("Radio button is not selected.");
            }
            extentReports.createTest("POS Reports in POS Transaction month Enabled ").log(Status.PASS,
                    "Successfully selecting the dates month");
        } catch (Exception e) {
            extentReports.createTest("POS Reports in POS Transaction month Enabled ").log(Status.FAIL,
                    "Failed to select the dates month. Exception: " + e.getCause());
        }
    }

    @Test(priority = 199)
    public void attenderUsageMonth() {
        try {
            Thread.sleep(2000);
            WebElement revenue = driver.findElement(
                    By.xpath("//div[@id='pos_attender']"));
            String value = revenue.getText();
            System.out.println("Attender Usage Month: " + value);
            extentReports.createTest("Attender Usage in a Month").log(Status.PASS,
                    "Successfully displaying Attender Usage in a Month" + value);
        } catch (Exception e) {
            extentReports.createTest("Attender Usage in a Month").log(Status.FAIL,
                    "Failed to display Attender Usage in a Month. Exception: " + e.getCause());
        }
    }

    @Test(priority = 200)
    public void attender_ViewDetails() {
        try {
            Thread.sleep(2000);
            driver
                    .findElement(By.xpath("//*[@id='posAttenderForm']/div/div[2]/button"))
                    .click();
            extentReports.createTest("In posAttenderForm-- click the view details button").log(Status.PASS,
                    "Successfully clicking the view details button in Attender Tab");
        } catch (Exception e) {
            extentReports.createTest("In posAttenderForm-- click the view details button").log(Status.FAIL,
                    "Failed to click view details button in Attender tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 201)
    public void attender_CsvFile() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("posAttenderCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("Click and download CSV file").log(Status.PASS,
                        "Successfully clicked the CSV button in POS attender");
            } else {
                extentReports.createTest("Click and download CSV file").log(Status.INFO, "CSV button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Click and download CSV file").log(Status.FAIL,
                    "Failed to download the CSV file in POS attender tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 202)
    public void attender_PdfFile() {
        try {
            Thread.sleep(2000);
            WebElement pdfButton = driver
                    .findElement(By.id("posAttenderPdf"));

            if (pdfButton.isDisplayed()) {
                pdfButton.click();
                String mainWindowHandle = driver.getWindowHandle();
                if (pdfButton.isDisplayed()) {
                    pdfButton.click();

                    Set<String> windowHandles = driver.getWindowHandles();

                    for (String windowHandle : windowHandles) {
                        if (!windowHandle.equals(mainWindowHandle)) {
                            driver.switchTo().window(windowHandle);
                            break;
                        }
                    }
                    driver.close();
                    driver.switchTo().window(mainWindowHandle);

                    extentReports.createTest("Click and download PDF Button").log(Status.PASS,
                            "Successfully clicked the PDF button in POS Attender");
                } else {
                    extentReports.createTest("Click and download PDF Button").log(Status.WARNING,
                            "PDF button not found");
                }
            }
        } catch (Exception e) {
            extentReports.createTest("Click and download the PDF file ").log(Status.FAIL,
                    "Failed to download the PDF file in POS attender tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 203)
    public void attender_PopUPClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='posAttenderModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In POS reports pop-up in attender tab").log(Status.PASS,
                    "Successfully closed the pop up in POS Reports page");
        } catch (Exception e) {
            extentReports.createTest("In POS reports pop-up in attender tab").log(Status.FAIL,
                    "Failed to close the pop up in POS Reports tab. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 204)
    public void select_AttenderIOS() {
        selectRevenueIOS();
    }

    // @Test(priority = 205)
    public void attender_iOS_ViewDetails() {
        attender_ViewDetails();
    }

    // @Test(priority = 206)
    public void attender_iOS_CsvFile() {
        attender_CsvFile();
    }

    @Test(priority = 207)
    public void attender_iOS_PdfFile() {
        attender_PdfFile();
    }

    // @Test(priority = 208)
    public void posAttenderPopUPCloseSecond() {
        attender_PopUPClose();
    }

    // @Test(priority = 209)
    public void iOSPosAttender() {
        try {
            Thread.sleep(2000);
            WebElement transaction = driver.findElement(
                    By.xpath("//div[@id='pos_attender']"));
            String value = transaction.getText();
            System.out.println("POS Attender IOS : " + value);
            extentReports.createTest("In iOS Pos Attender").log(Status.PASS,
                    "Successfully displaying the IOS POS attender" + value);
        } catch (Exception e) {
            extentReports.createTest("In iOS Pos Attender").log(Status.FAIL,
                    "Failed to display the IOS POS attender. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 210)
    public void select_AttenderAndroid() {
        selectRevenueAndroid();
    }

    // @Test(priority = 211)
    public void attender_Android_ViewDetails() {
        attender_ViewDetails();
    }

    // @Test(priority = 212)
    public void attender_Android_CsvFile() {
        attender_CsvFile();
    }

    // @Test(priority = 213)
    public void attender_Android_PdfFile() {
        attender_PdfFile();
    }

    // @Test(priority = 214)
    public void attender_CsvFile_PopUPClose() {
        attender_PopUPClose();
    }

    // @Test(priority = 215)
    public void androidPosAttender() {
        try {
            Thread.sleep(2000);
            WebElement attender = driver.findElement(
                    By.xpath("//div[@id='pos_transaction']"));
            String value = attender.getText();
            System.out.println("POS attender Android : " + value);
            extentReports.createTest("In POS reports Android attender ").log(Status.PASS,
                    "Successfully displaying the Android POS attender" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS reports Android attender").log(Status.FAIL,
                    "Failed to display the Android POS attender. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 216)
    public void select_AttenderFasTag() {
        selectRevenueFasTag();
    }

    // @Test(priority = 217)
    public void selectAttenderFasTagPopUp() {
        fasTagRevenueStatus();
    }

    // @Test(priority = 218)
    public void fasTagFasTagStatus() {
        clickRevenueGotItBtn();
    }

    // @Test(priority = 219)
    public void attender_FasTag_ViewDetails() {
        attender_ViewDetails();
    }

    // @Test(priority = 220)
    public void attender_FasTag_CsvFile() {
        attender_CsvFile();
    }

    // @Test(priority = 221)
    public void attender_FasTag_PdfFile() {
        attender_PdfFile();
    }

    // @Test(priority = 222)
    public void attender_FasTag_PopUPClose() {
        attender_PopUPClose();
    }

    // @Test(priority = 223)
    public void fasTagPosAttender() {
        try {
            Thread.sleep(2000);
            WebElement attender = driver.findElement(
                    By.xpath("//div[@id='pos_attender']"));
            String value = attender.getText();
            System.out.println("POS Transaction FasTag : " + value);
            extentReports.createTest("In POS attender FasTag  ").log(Status.PASS,
                    "Successfully displaying the FasTag POS attender" + value);
        } catch (Exception e) {
            extentReports.createTest("In POS attender FasTag ").log(Status.FAIL,
                    "Failed to display the FasTag POS attender. Exception: " + e.getCause());
        }
    }

    @Test(priority = 224)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            System.out.println("Logout from the POS Reports page");
            extentReports.createTest("LOGOUT in POS Reports page ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("LOGOUT in POS Reports page ").log(Status.FAIL,
                    "Failed to LogOut. Exception: " + e.getCause());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            extentReports.flush();
        }
    }
}