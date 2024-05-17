package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.model.ServerCredentials;

public class SiteConfig extends BaseTestReport {
    WebDriver driver;

    ServerCredentials serverconfig;
    // public WebDriver setBrowserDriver(String driverName) {
    // if (driverName.equalsIgnoreCase("chrome")) {
    // return new ChromeDriver();
    // } else if (driverName.equalsIgnoreCase("firefox")) {
    // FirefoxOptions options = new FirefoxOptions();
    // options.setHeadless(true); // Set Firefox to run in headless mode
    // return new FirefoxDriver(options);
    // }
    // return null;
    // }

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/SanityTestReport.html");
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
    public void siteConfigurationPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[5]")).click();
            driver.findElement(By.className("sm-pg")).click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 4)
    public void jstreeOrg() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    }

    @Test(priority = 5)
    public void jstreeZone() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    }

    @Test(priority = 6)
    public void jstreeSites() throws InterruptedException {
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    }

    @Test(priority = 7)
    public void jstreeOFFstreet() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
    }

    @Test(priority = 8)
    public void jstreeOFFstreetDevices() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61_anchor']")).click();
    }

    @Test(priority = 9)
    public void addSingleDevicebtn() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='btnAddDevices']")).click();
    }

    @Test(priority = 10)
    public void selectFasTag() {
        try {
            WebElement selectElement = driver.findElement(By.id("deviceCatagory"));

            selectElement.click();
            Select select = new Select(selectElement);
            select.selectByVisibleText("Fast Tag Controller");

            Select dropdown = new Select(selectElement);
            Thread.sleep(1000);

            List<WebElement> options = dropdown.getOptions();
            for (WebElement option : options) {
                System.out.println("Device_Type: " + option.getAttribute("value"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 11)
    public void fasTagControllerDetails() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.id("fcDeviceName")).sendKeys("fasttag123");
        Thread.sleep(1000);
        driver.findElement(By.id("deviceMacId")).sendKeys("&&&&&");
        driver.findElement(By.id("ftcpresentat")).sendKeys("Entry");
        Thread.sleep(1000);
        driver.findElement(By.id("btnSaveDevices")).click();
        Thread.sleep(1000);

        String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

        System.out.println("Status:" + result);

        String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

        System.out.println("FasTagController:" + resultdescription);

        driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        Thread.sleep(3000);

        driver.navigate().refresh();
    }

    @Test(priority = 12)
    public void openTreeSecondTime() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1500);
    }

    @Test(priority = 13)
    public void addFasTag() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftBtnAdddevices']")).click();
    }

    @Test(priority = 14)
    public void addFasTagSelectftReader() throws InterruptedException {
        Thread.sleep(3000);
        WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("fastagDeviceCatagory"));
        Select select = new Select(fastagDeviceCategoryDropdown);
        select.selectByValue("FastagReader");
    }

    @Test(priority = 15)
    public void addFasTagEnterDetails() throws InterruptedException {
        driver.findElement(By.id("fastagDeviceName")).sendKeys("1234556");
        Thread.sleep(1000);
        driver.findElement(By.id("fastagDeviceMacId")).sendKeys("45709");
        driver.findElement(By.id("fastagpresentat")).sendKeys("Exit");

        Thread.sleep(1000);
    }

    @Test(priority = 16)
    public void addbtnFasTagSelectftReader() throws InterruptedException {
        driver.findElement(By.id("btnFastagSaveDevices")).click();
        Thread.sleep(1000);

        String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

        System.out.println("Status:" + result);

        String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

        System.out.println("FasTagReader:" + resultdescription);

        driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();
    }

    @Test(priority = 17)
    public void openTreeThirdTime() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(9000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftBtnAdddevices']")).click();
    }

    @Test(priority = 18)
    public void addFasTagSelectftBarrier() throws InterruptedException {
        Thread.sleep(3000);
        WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("fastagDeviceCatagory"));
        Select select = new Select(fastagDeviceCategoryDropdown);
        Thread.sleep(2000);
        select.selectByValue("FastagBoomBarrier");
    }

    @Test(priority = 19)
    public void addFtBarrierDetails() throws InterruptedException {
        driver.findElement(By.id("fastagDeviceName")).sendKeys("barrier3");
        Thread.sleep(1000);
        driver.findElement(By.id("fastagDeviceMacId")).sendKeys("barrier4");
        driver.findElement(By.id("fastagpresentat")).sendKeys("Exit");

        Thread.sleep(3000);
    }

    @Test(priority = 20)
    public void addbtnFasTagSelectftBarrier() throws InterruptedException {

        driver.findElement(By.id("btnFastagSaveDevices")).click();
        Thread.sleep(3000);

        String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

        System.out.println("Status:" + result);

        String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

        System.out.println("FasTagBarrier:" + resultdescription);

        driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();
    }

    @Test(priority = 21)
    public void openTreeForthTime() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(7000);
        WebElement city = driver.findElement(By.xpath("//*[@id='city_5']/i"));
        city.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftBtnAdddevices']")).click();
    }

    @Test(priority = 22)
    public void addFasTagSelectftDisplay() throws InterruptedException {
        Thread.sleep(3000);
        WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("fastagDeviceCatagory"));
        Select select = new Select(fastagDeviceCategoryDropdown);
        select.selectByValue("FastagDisplay");
    }

    @Test(priority = 23)
    public void addFtDisplayDetails() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id("fastagDeviceName")).sendKeys("display3");
        Thread.sleep(1000);
        driver.findElement(By.id("fastagDeviceMacId")).sendKeys("display3");
        driver.findElement(By.id("fastagpresentat")).sendKeys("Entry");
        Thread.sleep(1000);
    }

    @Test(priority = 24)
    public void addbtnFasTagSelectftDisplay() throws InterruptedException {

        driver.findElement(By.id("btnFastagSaveDevices")).click();
        Thread.sleep(3000);
        String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

        System.out.println("Status:" + result);

        String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

        System.out.println("FasTagDisplay:" + resultdescription);

        driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();

    }

    @Test(priority = 25)
    public void openTreeFifthTime() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftBtnAdddevices']")).click();
    }

    @Test(priority = 26)
    public void addFasTagSelectftPrinter() throws InterruptedException {
        Thread.sleep(3000);
        WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("fastagDeviceCatagory"));
        Select select = new Select(fastagDeviceCategoryDropdown);
        select.selectByValue("FastagMobilePrinter");
    }

    @Test(priority = 27)
    public void addFtPrinterDetails() throws InterruptedException {
        driver.findElement(By.id("fastagDeviceName")).sendKeys("1234556");
        Thread.sleep(1000);
        driver.findElement(By.id("fastagDeviceMacId")).sendKeys("45709");
        driver.findElement(By.id("fastagpresentat")).sendKeys("Exit");

        Thread.sleep(3000);
    }

    @Test(priority = 28)
    public void addbtnFtPrinter() throws InterruptedException {

        driver.findElement(By.id("btnFastagSaveDevices")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();
    }

    public void popUpPrinter() throws InterruptedException {

        driver.findElement(By.id("btnFastagSaveDevices")).click();
        Thread.sleep(3000);
        String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

        System.out.println("Status:" + result);

        String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

        System.out.println("FasTagPrinter:" + resultdescription);

        driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();
    }

    @Test(priority = 29)
    public void openTreeSixthTime() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftBtnAdddevices']")).click();
    }

    @Test(priority = 30)
    public void addFasTagSelectFtmPOS() throws InterruptedException {
        Thread.sleep(3000);
        WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("fastagDeviceCatagory"));
        Select select = new Select(fastagDeviceCategoryDropdown);
        Thread.sleep(3000);
        select.selectByValue("FastagMpos");
    }

    @Test(priority = 31)
    public void addFtmPOSDetails() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.id("fastagDeviceName")).sendKeys("mPOS3");
        Thread.sleep(3000);
        WebElement ftMposMacid = driver.findElement(By.id("ftMposMacid"));
        Select select = new Select(ftMposMacid);
        select.selectByValue("867016061245328");
        Thread.sleep(1000);
        driver.findElement(By.id("fastagpresentat")).sendKeys("Exit");
        Thread.sleep(1000);

    }

    @Test(priority = 32)
    public void addbtnFasTagFtmPOS() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.id("btnFastagSaveDevices")).click();
        Thread.sleep(3000);

        String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

        System.out.println("Status:" + result);

        String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

        System.out.println("FasTagMPOS:" + resultdescription);

        driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        Thread.sleep(3000);
        driver.navigate().refresh();
    }

    @Test(priority = 33)
    public void openTreeSeventhTime() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    }

    @Test(priority = 34)
    public void updateController() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("ftControllerPresentAt")));
        element.click();
        WebElement presentAtDropdown = driver.findElement(By.id("ftControllerPresentAt"));
        Select select = new Select(presentAtDropdown);
        select.selectByVisibleText("Exit");

        driver.navigate().refresh();
    }

    @Test(priority = 35)
    public void OpenTreeEighthTime() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);

        // Controller "&&&&&&&&&&"
        driver.findElement(By.id("ftControllerID_23")).click();
        Thread.sleep(1000);
    }

    @Test(priority = 36)
    public void deleteController() throws InterruptedException {
        // driver.findElement(By.id("ftControllerDeleteBtn")).click();
        Thread.sleep(2000);
        // driver.findElement(By.xpath("//button[@class='swal2-confirm
        // swal2-styled']")).click();
    }

    @Test(priority = 37)
    public void OpenTreeNinthTime() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(4000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    }

    @Test(priority = 38)
    public void controllerDetailsView() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@id='ftDisplayftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@id='ftReaderftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    }

    @Test(priority = 39)
    public void fasTagReaderUpdate() throws InterruptedException {
        try {
            driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
            Thread.sleep(1000);
            WebElement presentAtDropdown = driver.findElement(By.id("ftDevicePresentAt"));
            Select select = new Select(presentAtDropdown);

            select.selectByVisibleText("Exit");
            driver.findElement(By.id("ftUpdateBtn")).click();
            Thread.sleep(2000);
            String result = driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

            System.out.println("Status:" + result);

            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("FasTagReaderUpdate:" + resultdescription);

            driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
            Thread.sleep(3000);
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException occurred: " + e.getMessage());
            System.out.println("Reason: The element you are trying to locate does not exist on the page.");
        }
    }

    @Test(priority = 40)
    public void OpenTreeTenthTime() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
        Thread.sleep(1500);
        WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
        jstreeElement.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("ftControllerID_2")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@id='ftDisplay_79_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    }
    // @Test(priority=41)
    // public void fasTagReaderDelete() throws InterruptedException {
    // Thread.sleep(3000);
    // try{
    // driver.findElement(By.xpath("//*[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // }
    // catch (NoSuchElementException e) {
    // System.out.println("The XPath ftreader80_anchor is deleted already in
    // previous sessions."+ e.getCause());
    // }
    // }
    // @Test(priority=42)
    // public void OpenTreeEleventhTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // }
    // @Test(priority=43)
    // public void fasTagDispalyUpdate() throws InterruptedException {
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//a[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // WebElement presentAtDropdown =
    // driver.findElement(By.id("ftDevicePresentAt"));
    // Select select = new Select(presentAtDropdown);
    // select.selectByVisibleText("Entry");
    // driver.findElement(By.id("ftUpdateBtn")).click();
    // Thread.sleep(3000);
    // driver.navigate().refresh();

    // }
    // @Test(priority=44)
    // public void OpenTreeTwelvethTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(5000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // }
    // @Test(priority=45)
    // public void fasTagDisplayDelete() throws InterruptedException {
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']")).click();
    // Thread.sleep(5000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // // driver.findElement(By.id("ftDeleteBtn")).click();
    // Thread.sleep(2000);
    // driver.navigate().refresh();

    // }
    // @Test(priority=46)
    // public void OpenTreeThirteenthTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(5000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//a[@id='ftBoomBarrier81_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // }
    // @Test(priority=47)
    // public void fasTagBarrierUpdate() throws InterruptedException {
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//a[@id='ftBoomBarrier81_anchor']")).click();
    // Thread.sleep(1000);
    // WebElement presentAtDropdown =
    // driver.findElement(By.id("ftDevicePresentAt"));
    // Select select = new Select(presentAtDropdown);
    // select.selectByVisibleText("Exit");
    // driver.findElement(By.id("ftUpdateBtn")).click();
    // Thread.sleep(2000);
    // String result =
    // driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();
    // System.out.println("Status:"+result);
    // String resultdescription =
    // driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
    // System.out.println("FasTagBarrierUpdate:"+resultdescription);
    // driver.findElement(By.xpath("//button[@class='swal2-confirm
    // swal2-styled']")).click();
    // Thread.sleep(3000);
    // }
    // @Test(priority=48)
    // public void OpenTreeFouteenthTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(3000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftBoomBarrier81_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // }
    // @Test(priority=49)
    // public void fasTagBarrierDelete() throws InterruptedException {
    // Thread.sleep(4000);
    // driver.findElement(By.xpath("//a[@id='ftBoomBarrier81_anchor']")).click();
    // Thread.sleep(1000);
    // // driver.findElement(By.id("ftDeleteBtn")).click();
    // Thread.sleep(2000);
    // driver.navigate().refresh();

    // }
    // @Test(priority=50)
    // public void OpenTreeFifteenTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(5000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(3000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBoomBarrier81_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // } @Test(priority=51)
    // public void fasTagPrinterUpdate() throws InterruptedException {
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // Thread.sleep(1000);
    // WebElement presentAtDropdown =
    // driver.findElement(By.id("ftDevicePresentAt"));
    // Select select = new Select(presentAtDropdown);
    // select.selectByVisibleText("Exit");
    // driver.findElement(By.id("ftUpdateBtn")).click();
    // Thread.sleep(2000);
    // String result =
    // driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

    // System.out.println("Status:"+result);

    // String resultdescription =
    // driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

    // System.out.println("FasTagPrinterUpdate:"+resultdescription);

    // driver.findElement(By.xpath("//button[@class='swal2-confirm
    // swal2-styled']")).click();
    // Thread.sleep(3000);
    // }
    // @Test(priority=52)
    // public void OpenTreeSixteenthTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(6000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBoomBarrier81_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // }
    // @Test(priority=53)
    // public void fasTagPrinterDelete() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // Thread.sleep(1000);
    // // driver.findElement(By.id("ftDeleteBtn")).click();
    // Thread.sleep(2000);
    // driver.navigate().refresh();

    // }
    // @Test(priority=54)
    // public void OpenTreeSeventeenthTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(5000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='ftControllerID_2']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBoomBarrier81_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftMpos73_anchor']")).click();
    // }
    // @Test(priority=55)
    // public void fasTagmPOSUpdate() throws InterruptedException {
    // Thread.sleep(1000);
    // WebElement presentAtDropdown =
    // driver.findElement(By.id("ftDevicePresentAt"));
    // Select select = new Select(presentAtDropdown);
    // select.selectByVisibleText("Exit");
    // Thread.sleep(2000);
    // driver.findElement(By.id("ftUpdateBtn")).click();
    // Thread.sleep(2000);

    // String result =
    // driver.findElement(By.xpath("//h2[@class='swal2-title']")).getText();

    // System.out.println("Status:"+result);

    // String resultdescription =
    // driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

    // System.out.println("FasTagmPOSUpdate:"+resultdescription);

    // driver.findElement(By.xpath("//button[@class='swal2-confirm
    // swal2-styled']")).click();
    // Thread.sleep(3000);
    // }
    // @Test(priority=56)
    // public void OpenTreeEighteenthTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(6000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.id("ftControllerID_2")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBoomBarrier81_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftMpos73_anchor']")).click();
    // }
    // @Test(priority=57)
    // public void fasTagmPOSDelete() throws InterruptedException {
    // Thread.sleep(1000);
    // // driver.findElement(By.id("ftDeleteBtn")).click();
    // Thread.sleep(2000);
    // driver.navigate().refresh();

    // }
    // @Test(priority=58)
    // public void OpenTreeNineteenthTime() throws InterruptedException {
    // Thread.sleep(3000);
    // driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
    // Thread.sleep(1500);
    // WebElement jstreeElement =
    // driver.findElement(By.xpath("//li[@role='treeitem']"));
    // jstreeElement.click();
    // Thread.sleep(7000);
    // driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='site_56']/i")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='site_56']/ul")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='level_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderlevel_61']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//li[@id='ftControllerID_2']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2']/i")).click();
    // driver.findElement(By.xpath("//*[@id='ftControllerID_2_anchor']")).click();
    // Thread.sleep(2000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplayftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftDisplay_79_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftReaderftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//a[@id='ftreader80_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBarrierftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftBoomBarrier81_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftPrinterftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMobilePrinter55_anchor']")).click();
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2_anchor']")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMposftControllerID_2']/i")).click();
    // Thread.sleep(1000);
    // driver.findElement(By.xpath("//*[@id='ftMpos73_anchor']")).click();
    // }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}