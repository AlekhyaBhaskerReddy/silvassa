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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class Site_Analysis extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Site_Analysis.html");
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
    public void redirectSiteAnalysis() {
        try {
            Thread.sleep(2000);
            WebElement siteAnalysisPage = driver.findElement(By.className("sita-pg"));
            if (siteAnalysisPage.isDisplayed()) {
                siteAnalysisPage.click();
                extentReports.createTest("Site Analysis Page  ").log(Status.PASS,
                        "Successfully Open site analysis  Reports Page");
            } else {
                extentReports.createTest("Site Analysis Page").log(Status.INFO,
                        "Site Analysis Page not found");
                Assert.fail("Site Analysis Page not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis  Page Reports ").log(Status.FAIL,
                    "Failed to Open the site analysis  Reports . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void siteAnalysis_selectSite() {
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
    public void getSite_AnalysisTab() {
        WebElement dashboardSummary = driver.findElement(By.className("dashboardSummary"));
        List<WebElement> summaryCards = dashboardSummary.findElements(By.className("site-summary-cards"));

        for (WebElement summaryCard : summaryCards) {
            WebElement cardNameElement = summaryCard.findElement(By.tagName("b"));
            String cardName = cardNameElement.getText();

            extentReports.createTest("Total Tabs").log(Status.PASS,
                    "Available tabs in site analysis").log(Status.INFO, "Summary Card Name: " + cardName);
        }

    }

    @Test(priority = 7)
    public void select_AvgDuration_TwoWheeler() {
        try {
            Thread.sleep(2000);
            WebElement twoWheelerType = driver.findElement(By.xpath("//div[@id='wheelerFilter']/label[1]"));
            if (twoWheelerType.isDisplayed()) {
                twoWheelerType.click();
                extentReports.createTest("Site Analysis Page select vehicle type ").log(Status.PASS,
                        "Successfully select the vehicle type");
            } else {
                extentReports.createTest("Site Analysis Page select vehicle type").log(Status.INFO,
                        "Vehicle type not found");
                Assert.fail("Site Analysis select vehicle type not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis Page select vehicle type ").log(Status.FAIL,
                    "Failed to select vehicle type in site analysis . Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void avgDuration_2WDates() {
        try {
            driver.findElement(By.xpath("//div[@id='siteAvgDuration']//div[@id='dayRangeFilter']/label[1]")).click();
            driver.findElement(By.xpath("//div[@id='siteAvgDuration']//div[@id='dayRangeFilter']/label[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='siteAvgDuration']//div[@id='dayRangeFilter']/label[3]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='siteAvgDuration']//div[@id='dayRangeFilter']/label[4]")).click();

            extentReports.createTest("Site Analysis avg duration Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Site Analysis avg duration Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void avgDuration_ViewDetails() {
        try {
            Thread.sleep(2000);
            WebElement avgDetails = driver
                    .findElement(By.xpath("//button[@id='siteTwoAvgDetails']/i"));
            if (avgDetails.isDisplayed()) {
                avgDetails.click();
                extentReports.createTest("Average Duration view button ").log(Status.PASS,
                        "Present and successfully clicked the view details button");
            } else {
                extentReports.createTest("Average Duration view button ").log(Status.INFO,
                        "Avg Details button not found");
                Assert.fail("Average Duration view button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Average Duration view button").log(Status.FAIL,
                    "Failed to click Average Duration view button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void avgDuration_2WCsv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("siteTwoAvgDurCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In average duration download 2W CSV file").log(Status.PASS,
                        "Successfully download the CSV file in average duration");
            } else {
                extentReports.createTest("In average duration download 2W CSV file").log(Status.WARNING,
                        "CSV button not found in average duration");
            }

        } catch (Exception e) {
            extentReports.createTest("In average duration downlaod 2W CSV file ").log(Status.FAIL,
                    "Failed to download the CSV file in average duration. Exception: " + e.getCause());

        }
    }

    @Test(priority = 11)
    public void avgDuration_2WPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver.findElement(By.id("siteTwoAvgDurPdf"));
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

                extentReports.createTest("In average duration download PDF Button").log(Status.PASS,
                        "Successfully clicked the PDF button");
            } else {
                extentReports.createTest("In average duration download PDF Button").log(Status.INFO,
                        "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In average duration downlaod PDF Button").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 12)
    public void avgDuration_2WSelectCount_25() {
        try {
            WebElement dropdown = driver.findElement(By.name("siteTwoAvgDurTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            Thread.sleep(2000);
            extentReports.createTest("In avg duration select length of 2W").log(Status.PASS,
                    "Successfully selected as 10");
        } catch (Exception e) {
            extentReports.createTest("In avg duration select length of 2W").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 13)
    public void avgDuration_2WSearch() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#siteTwoAvgDurTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "More than 120 Minutes";
            searchInput.sendKeys(searchValue);

            WebElement table = driver.findElement(By.id("siteTwoAvgDurTable"));
            String tableData = table.getText();

            extentReports.createTest("Site Analysis search of two wheeler").log(Status.PASS,
                    "Successfully search the : " + searchValue).log(Status.INFO,
                            "Successfully searched for Table Data:" + tableData);
            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis search of two wheeler").log(Status.FAIL,
                    "Failed to search . Exception: " + e.getCause());

        }
    }

    @Test(priority = 14)
    public void avgDuration_2WPoPup_Close() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='siteTwoAvgDurModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In avg duration 2W POP Up").log(Status.PASS,
                    "Successfully closed the pop up ");
        } catch (Exception e) {
            extentReports.createTest("In avg duration 2W POP Up").log(Status.FAIL,
                    "Failed to close the pop up in image in audit trail. Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void select_AvgDuration_FourWheeler() {
        try {
            Thread.sleep(2000);
            WebElement fourWheelerType = driver.findElement(By.xpath("//div[@id='wheelerFilter']/label[2]"));
            if (fourWheelerType.isDisplayed()) {
                fourWheelerType.click();
                extentReports.createTest("Site Analysis Page select vehicle type 4W ").log(Status.PASS,
                        "Successfully select the vehicle type ");
            } else {
                extentReports.createTest("Site Analysis Page select vehicle type 4W").log(Status.INFO,
                        "Vehicle type not found");
                Assert.fail("Site Analysis select vehicle type not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis Page select vehicle type 4W ").log(Status.FAIL,
                    "Failed to select vehicle type in site analysis . Exception: " + e.getCause());
        }
    }

    // @Test(priority = 16)
    public void avgDuration_4WDates() {
        avgDuration_2WDates();
    }

    @Test(priority = 17)
    public void avgDuration_ViewDetails_4W() {
        try {
            Thread.sleep(2000);
            WebElement avgDetails = driver
                    .findElement(By.xpath("//button[@id='siteFourAvgDetails']/i"));
            if (avgDetails.isDisplayed()) {
                avgDetails.click();
                extentReports.createTest("Average Duration view button after selecting 4W").log(Status.PASS,
                        "Present and successfully clicked the view details button");
            } else {
                extentReports.createTest("Average Duration view button after selecting 4W ").log(Status.INFO,
                        "Avg Details button not found");
                Assert.fail("Average Duration view button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Average Duration view button after selecting 4W").log(Status.FAIL,
                    "Failed to click Average Duration view button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 18)
    public void avgDuration_4WCsv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("siteFourAvgDurCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In average duration download 4W CSV file").log(Status.PASS,
                        "Successfully download the CSV file in average duration");
            } else {
                extentReports.createTest("In average duration download 4W CSV file").log(Status.WARNING,
                        "CSV button not found in average duration");
            }

        } catch (Exception e) {
            extentReports.createTest("In average duration downlaod 4W CSV file ").log(Status.FAIL,
                    "Failed to download the CSV file in average duration. Exception: " + e.getCause());

        }
    }

    @Test(priority = 19)
    public void avgDuration_4WPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver.findElement(By.id("siteFourAvgDurPdf"));
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

                extentReports.createTest("In average duration download PDF Button after selecting 4W").log(Status.PASS,
                        "Successfully clicked the PDF button");
            } else {
                extentReports.createTest("In average duration download PDF Button after selecting 4W").log(Status.INFO,
                        "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In average duration downlaod PDF Button after selecting 4W").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 20)
    public void avgDuration_4WSelectCount_25() {
        try {
            WebElement dropdown = driver.findElement(By.name("siteFourAvgDurTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            Thread.sleep(2000);
            extentReports.createTest("In avg duration select length of 4W").log(Status.PASS,
                    "Successfully selected as 10");
        } catch (Exception e) {
            extentReports.createTest("In avg duration select length of 4W").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 21)
    public void avgDuration_4WSearch() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#siteFourAvgDurTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "More than 120 Minutes";
            searchInput.sendKeys(searchValue);

            WebElement table = driver.findElement(By.id("siteFourAvgDurTable"));
            String tableData = table.getText();

            extentReports.createTest("Site Analysis search of four wheeler").log(Status.PASS,
                    "Successfully search the : " + searchValue).log(Status.INFO,
                            "Successfully searched for Table Data:" + tableData);
            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis search of four wheeler").log(Status.FAIL,
                    "Failed to search . Exception: " + e.getCause());

        }
    }

    @Test(priority = 22)
    public void avgDuration_4WPoPup_Close() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='siteFourAvgDurModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In avg duration 4W POP Up").log(Status.PASS,
                    "Successfully closed the pop up ");
        } catch (Exception e) {
            extentReports.createTest("In avg duration 4W POP Up").log(Status.FAIL,
                    "Failed to close the pop up in image in audit trail. Exception: " + e.getCause());
        }
    }

    @Test(priority = 23)
    public void transaction_FrequencyTab() {
        try {
            Thread.sleep(2000);
            WebElement transFreqTab = driver.findElement(By.xpath("//div[@id='trans_freq_summary']"));

            if (transFreqTab.isDisplayed()) {
                transFreqTab.click();
                extentReports.createTest("Transaction Frequency Tab is clicked").log(Status.PASS,
                        "Successfully clicked the Transaction Frequency Tab");

            } else {
                extentReports.createTest("Transaction Frequency Tab is clicked ").log(Status.WARNING,
                        "Transaction Frequency tab not found");
                Assert.fail("Transaction Frequency tab not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Transaction Frequency Tab is not clicked").log(Status.FAIL,
                    "Failed to click the Transaction Frequency. Exception: " + e.getCause());
            siteAnalysisTabCapture(driver, "exception_screenshot");

        }
    }

    @Test(priority = 24)
    public void transFrequency_Dates() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='siteTransationFreq']//div[@id='dayRangeFilter']/label[1]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='siteTransationFreq']//div[@id='dayRangeFilter']/label[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='siteTransationFreq']//div[@id='dayRangeFilter']/label[3]")).click();

            extentReports.createTest("Site Analysis Transaction Frequency Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Site Analysis Transaction Frequency Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 25)
    public void transFrequency_ViewDetails() {
        try {
            Thread.sleep(2000);
            WebElement avgDetails = driver
                    .findElement(By.xpath("//button[@id='transationFreqDetails']/i"));
            if (avgDetails.isDisplayed()) {
                avgDetails.click();
                extentReports.createTest("Transaction Frequency view button ").log(Status.PASS,
                        "Present and successfully clicked the view details button");
            } else {
                extentReports.createTest("Transaction Frequency view button ").log(Status.INFO,
                        "Transaction Frequency view Details button not found");
                Assert.fail("Transaction Frequency view button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Transaction Frequency view button").log(Status.FAIL,
                    "Failed to click Transaction Frequency view button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 26)
    public void transFrequency_Csv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("transFreqCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In Transaction Frequency download CSV file").log(Status.PASS,
                        "Successfully download the CSV file in Transaction Frequency");
            } else {
                extentReports.createTest("In Transaction Frequency download CSV file").log(Status.WARNING,
                        "CSV button not found in Transaction Frequency");
            }

        } catch (Exception e) {
            extentReports.createTest("In Transaction Frequency downlaod CSV file ").log(Status.FAIL,
                    "Failed to download the CSV file in Transaction Frequency. Exception: " + e.getCause());

        }
    }

    @Test(priority = 27)
    public void transFrequency_Pdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver.findElement(By.id("transFreqPdf"));
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

                extentReports.createTest("In Transaction Frequency download PDF Button ").log(
                        Status.PASS,
                        "Successfully clicked the PDF button");
            } else {
                extentReports.createTest("In Transaction Frequency download PDF Button ").log(
                        Status.INFO,
                        "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In Transaction Frequency downlaod PDF Button ").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 28)
    public void transFrequency_SelectCount_25() {
        try {
            WebElement dropdown = driver.findElement(By.name("transFreqTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            Thread.sleep(2000);
            extentReports.createTest("In Transaction Frequency select length ").log(Status.PASS,
                    "Successfully selected as 10");
        } catch (Exception e) {
            extentReports.createTest("In Transaction Frequency select length ").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 29)
    public void transFrequency_Search() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#transFreqTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "More than 120 Minutes";
            searchInput.sendKeys(searchValue);

            WebElement table = driver.findElement(By.id("siteFourAvgDurTable"));
            String tableData = table.getText();

            extentReports.createTest("Site Analysis search of Transaction Frequency").log(Status.PASS,
                    "Successfully search the : " + searchValue).log(Status.INFO,
                            "Successfully searched for Table Data:" + tableData);
            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis search of Transaction Frequency").log(Status.FAIL,
                    "Failed to search . Exception: " + e.getCause());

        }
    }

    @Test(priority = 30)
    public void transFrequency_PoPup_Close() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='transationFreqModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In Transaction Frequency POP Up").log(Status.PASS,
                    "Successfully closed the pop up in Transaction Frequency");
        } catch (Exception e) {
            extentReports.createTest("In Transaction Frequency POP Up").log(Status.FAIL,
                    "Failed to close the pop up in Transaction Frequency. Exception: " + e.getCause());
        }
    }

    @Test(priority = 31)
    public void occupancyMinutes_Tab() {
        try {
            Thread.sleep(2000);
            WebElement occupancyMinTab = driver.findElement(By.xpath("//div[@id='parking_insts_summary']"));

            if (occupancyMinTab.isDisplayed()) {
                occupancyMinTab.click();
                extentReports.createTest("Occupancy (Min) Tab is clicked").log(Status.PASS,
                        "Successfully clicked the Occupancy (Min) Tab");

            } else {
                extentReports.createTest("Occupancy (Min) Tab is clicked ").log(Status.WARNING,
                        "Occupancy (Min) tab not found");
                Assert.fail("Occupancy (Min) tab not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Occupancy (Min) Tab is not clicked").log(Status.FAIL,
                    "Failed to click the Occupancy (Min). Exception: " + e.getCause());
            siteAnalysisTabCapture(driver, "exception_screenshot");

        }
    }

    @Test(priority = 32)
    public void occupancyMinutes_Dates() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='site_parking_instances']//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='site_parking_instances']//div[@id='dayRangeFilter']/label[2]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='site_parking_instances']//div[@id='dayRangeFilter']/label[3]"))
                    .click();

            extentReports.createTest("Site Analysis Occupancy (Min) Dates enabled ").log(Status.PASS,
                    "Successfully selecting the day/week/month");
        } catch (Exception e) {
            extentReports.createTest("Site Analysis Occupancy (Min) Dates enabled").log(Status.FAIL,
                    "Failed to selecting the day/week/month . Exception: " + e.getCause());
        }
    }

    @Test(priority = 33)
    public void occupancyMinutes_ViewDetails() {
        try {
            Thread.sleep(2000);
            WebElement occupancyDetails = driver
                    .findElement(By.xpath("//button[@id='siteAvgOccupancyDetails']/i"));
            if (occupancyDetails.isDisplayed()) {
                occupancyDetails.click();
                extentReports.createTest("Occupancy (Min) view button ").log(Status.PASS,
                        "Present and successfully clicked the view details button");
            } else {
                extentReports.createTest("Occupancy (Min) view button ").log(Status.INFO,
                        "Occupancy (Min) view Details button not found");
                Assert.fail("Occupancy (Min) view button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Occupancy (Min) view button").log(Status.FAIL,
                    "Failed to click Occupancy (Min) view button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 34)
    public void occupancyMinutes_Csv() {
        try {
            Thread.sleep(2000);
            WebElement csvButton = driver
                    .findElement(By.id("siteAvgOccupancyCsv"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("In Occupancy (Min) download CSV file").log(Status.PASS,
                        "Successfully download the CSV file in Occupancy (Min)");
            } else {
                extentReports.createTest("In Occupancy (Min) download CSV file").log(Status.WARNING,
                        "CSV button not found in Occupancy (Min)");
            }

        } catch (Exception e) {
            extentReports.createTest("In Occupancy (Min) downlaod CSV file ").log(Status.FAIL,
                    "Failed to download the CSV file in Occupancy (Min). Exception: " + e.getCause());

        }
    }

    @Test(priority = 35)
    public void occupancyMinutes_Pdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver.findElement(By.id("siteAvgOccupancyPdf"));
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

                extentReports.createTest("In Occupancy (Min) download PDF Button ").log(
                        Status.PASS,
                        "Successfully clicked the PDF button");
            } else {
                extentReports.createTest("In Occupancy (Min) download PDF Button ").log(
                        Status.INFO,
                        "PDF button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("In Occupancy (Min) downlaod PDF Button ").log(Status.FAIL,
                    "Failed to click the PDF button. Exception: " + e.getLocalizedMessage());
        }
    }

    @Test(priority = 36)
    public void occupancyMinutes_SelectCount_25() {
        try {
            WebElement dropdown = driver.findElement(By.name("siteAvgOccupancyTable_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            Thread.sleep(2000);
            extentReports.createTest("In Occupancy (Min) select length ").log(Status.PASS,
                    "Successfully selected as 10");
        } catch (Exception e) {
            extentReports.createTest("In Occupancy (Min) select length ").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());

        }
    }

    @Test(priority = 37)
    public void occupancyMinutes_Search() {
        try {
            Thread.sleep(4000);
            WebElement searchInput = driver
                    .findElement(By.cssSelector("#siteAvgOccupancyTable_filter input[type='search']"));
            searchInput.clear();
            String searchValue = "20";
            searchInput.sendKeys(searchValue);

            WebElement table = driver.findElement(By.id("siteAvgOccupancyTable"));
            String tableData = table.getText();

            extentReports.createTest("Site Analysis search of Occupancy (Min)").log(Status.PASS,
                    "Successfully search the : " + searchValue).log(Status.INFO,
                            "Successfully searched for Table Data:" + tableData);
            Thread.sleep(4000);
        } catch (Exception e) {
            extentReports.createTest("Site Analysis search of Occupancy (Min)").log(Status.FAIL,
                    "Failed to search . Exception: " + e.getCause());

        }
    }

    @Test(priority = 38)
    public void occpancyMinutes_PoPup_Close() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='siteAvgOccupancyModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In Occupancy (Min) POP Up").log(Status.PASS,
                    "Successfully closed the pop up in Occupancy (Min)");
        } catch (Exception e) {
            extentReports.createTest("In Occupancy (Min) POP Up").log(Status.FAIL,
                    "Failed to close the pop up in Occupancy (Min). Exception: " + e.getCause());
        }
    }

    @Test(priority = 39)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Audit Trail page Site Analysis LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Audit Trail page Site Analysis LogOut").log(Status.FAIL,
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
