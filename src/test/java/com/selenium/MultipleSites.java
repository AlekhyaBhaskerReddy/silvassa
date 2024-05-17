package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

class ServerConfigMultipleSites {
    String userName;
    String password;
    Boolean production;

    public ServerConfigMultipleSites(String userName, String password, Boolean production) {
        this.userName = userName;
        this.password = password;
        this.production = production;
    }

    public String getUrl() {
        if (production) {
            return "https://admin-parking.pcmcsmartsarathi.org/SmartCity/ui/admin";
            // return "https://parking-jhansi.eparke.in/SmartCity/ui/admin/";
        }
        return "https://pcmc-parking.eparke.in/SmartCity/ui/admin/";
        // return "https://jhansi.eparke.in/SmartCity/ui/admin/";
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

public class MultipleSites {
    WebDriver driver;
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;

    public String setDriverPath(String driverName) {
        if (driverName.equalsIgnoreCase("chrome")) {
            return "/home/iramtech/iRam_Folder/web_driver/114/chromedriver";
        }
        return "/home/iramtech/iRam_Folder/web_driver/firefox/117/geckodriver";
    }

    public WebDriver setBrowserDriver(String driverName) {
        if (driverName.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        }
        return new FirefoxDriver();
    }

    @BeforeClass
    public void setUp() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("reports/Login.html");
        extent.attachReporter(spark);
        String browserPlugin = "firefox";
        System.setProperty(
                "webdriver.chrome.driver",
                setDriverPath(browserPlugin));
        // driver = new ChromeDriver();
        driver = setBrowserDriver(browserPlugin);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void login() {
        // ServerConfigMultipleSites s = new ServerConfigMultipleSites("testuser",
        // "welcome@123", false);
        // ServerConfigMultipleSites s = new ServerConfigMultipleSites("pcmcadmin",
        // "@Pcmc#Admin#@", false);
        // ServerConfigMultipleSites s = new ServerConfigMultipleSites("guestuser",
        // "welcome@1", true);
        ServerConfigMultipleSites s = new ServerConfigMultipleSites("pcmcadmin", "?ca2b_vr48", true);
        try {
            System.out.println(s.getUrl());
            driver.get(s.getUrl());
            driver.findElement(By.id("username")).sendKeys(s.getUserName());
            driver.findElement(By.id("password")).sendKeys(s.getPassword());
            driver.findElement(By.name("login")).click();
            extent.createTest("Login").log(Status.PASS, "Successfully Login");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Login").log(Status.FAIL, "Failed to login. Exception: " + e.getCause());
        }
    }

    @Test(priority = 2)
    public void revenueDashboardOpen() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.className("db-pg")).click();
            extent.createTest("Dashboard ").log(Status.PASS, "Successfully Open Dashboard Revenue tab");
        } catch (Exception e) {
            extent.createTest("Dashboard").log(Status.FAIL, "Failed to Open the dashboard. Exception: " + e.getCause());
        }
    }

    @Test(priority = 3)
    public void siteConfigurationPage() {

        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[5]")).click();
            Thread.sleep(3000);

            driver.findElement(By.className("sm-pg")).click();
            extent.createTest("Site Management ").log(Status.PASS, "Successfully Open Site Management  ");

        } catch (Exception e) {
            extent.createTest("Site Management ").log(Status.FAIL,
                    "Failed to Open the Site Management . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void pcsclSmartParkingOpen() {

        try {
            Thread.sleep(5000);
            WebElement jsTreeElement = driver.findElement(By.xpath("//li[@id='1']/i"));

            if (jsTreeElement.isDisplayed() && jsTreeElement.isEnabled()) {
                jsTreeElement.click();
                extent.createTest("Open JS Tree of pcscl Smart Parking ").log(Status.PASS,
                        "JS Tree element opened successfully.");
            } else {
                extent.createTest("Open JS Tree of pcscl Smart Parking ").log(Status.FAIL,
                        "JS Tree element is not clickable.");
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            extent.createTest("Open JS Tree pcscl Smart Parking").log(Status.FAIL, "JS Tree element is not present.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 5)
    public void pimpiriChinchwadOpen() {

        try {
            Thread.sleep(3000);
            WebElement jsTreeElement = driver.findElement(By.xpath("//li[@id='city_1']/i"));

            if (jsTreeElement.isDisplayed() && jsTreeElement.isEnabled()) {
                jsTreeElement.click();
                extent.createTest("Open JS Tree of Pimpri-Chinchwad City ").log(Status.PASS,
                        "JS Tree element opened successfully.");
            } else {
                extent.createTest("Open JS Tree of Pimpri-Chinchwad City").log(Status.FAIL,
                        "JS Tree element is not clickable.");
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            extent.createTest("Open JS Tree Pimpri-Chinchwad City").log(Status.FAIL, "JS Tree element is not present.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 6)
    public void clickZoneE() {

        try {
            Thread.sleep(3000);
            WebElement jsTreeElement = driver.findElement(By.xpath("//a[@id='zone_3_anchor']"));

            if (jsTreeElement.isDisplayed() && jsTreeElement.isEnabled()) {
                jsTreeElement.click();
                extent.createTest("Click Zone_E ").log(Status.PASS,
                        "Zone_E element opened successfully.");
            } else {
                extent.createTest("Click Zone_E ").log(Status.FAIL,
                        "Zone_E element is not clickable.");
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            extent.createTest("Click Zone_E ").log(Status.FAIL, "Zone_E element is not present.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 7)
    public void clickAddSite() {

        try {
            Thread.sleep(3000);
            WebElement btnAddSiteOptn = driver.findElement(By.id("btnAddSiteOptn"));

            if (btnAddSiteOptn.isDisplayed() && btnAddSiteOptn.isEnabled()) {
                btnAddSiteOptn.click();
                extent.createTest("Click Add site ").log(Status.PASS,
                        "Add site element opened successfully.");
            } else {
                extent.createTest("Click Add site ").log(Status.FAIL,
                        "Add Site element is not clickable.");
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            extent.createTest("Click Add Site ").log(Status.FAIL, "Add site element is not present.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 8)
    public void fillSite1Details() {
        try {
            Thread.sleep(2000);

            WebElement siteNameInput = driver.findElement(By.id("siteName"));
            siteNameInput.sendKeys("One");
            Thread.sleep(2000);

            WebElement siteAddress = driver.findElement(By.id("siteAddress"));
            siteAddress.sendKeys("PUNE");
            Thread.sleep(2000);

            WebElement latitude = driver.findElement(By.id("latitude"));
            latitude.sendKeys(" 18.530823");
            Thread.sleep(2000);

            WebElement longitude = driver.findElement(By.id("longitude"));
            longitude.sendKeys(" 73.847466");
            Thread.sleep(2000);

            WebElement selectElement = driver.findElement(By.id("paymentMode"));
            Select select = new Select(selectElement);
            select.selectByValue("POSTPAID");
            Thread.sleep(2000);

            WebElement selectEvent = driver.findElement(By.id("event"));
            Select select1 = new Select(selectEvent);
            select1.selectByValue("PUBLIC");
            Thread.sleep(2000);

            WebElement selectType = driver.findElement(By.id("siteType"));
            Select select2 = new Select(selectType);
            select2.selectByValue("ONSTREET");
            Thread.sleep(2000);

            driver.findElement(By.id("parkingLevelName")).sendKeys("level1");

            Thread.sleep(3000);

            WebElement submit = driver.findElement(By.id("btnSaveSite"));
            submit.click();
            Thread.sleep(3000);
            extent.createTest("Fill Site Details").log(Status.PASS, "Successfully filled site details and submitted");
        } catch (Exception e) {
            extent.createTest("Fill Site Details").log(Status.FAIL,
                    "Failed to fill site details. Exception: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            extent.flush();
        }
    }
}
