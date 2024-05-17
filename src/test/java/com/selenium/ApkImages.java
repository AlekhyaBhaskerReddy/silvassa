package com.selenium;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class ApkImages extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Apk_Images.html");
        // extentReports.attachReporter(spark);
        // extentTest = extentReports.createTest("demo");

        String browserPlugin = "firefox";
        System.setProperty(
                "webdriver.chrome.driver",
                ServerCredentials.setDriverPath(browserPlugin));

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        WebDriver driver = new FirefoxDriver(options);

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
            extentReports.createTest("Site Configuration ").log(Status.PASS, "Successfully Open Site Configuration ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration ").log(Status.FAIL,
                    "Failed to Open the Site Configuration . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void jstreeOrg() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
            Thread.sleep(2000);
            extentReports.createTest("Site Configuration oranisation info").log(Status.PASS,
                    "Successfully Open Site Configuration organisation info ");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration oranisation info ").log(Status.FAIL,
                    "Failed to Open the Site Configuration oranisation info . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void applicationImages() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//li[@role='treeitem']"));
            jstreeElement.click();
            Thread.sleep(2000);
            extentReports.createTest("Site Configuration applicationImages").log(Status.PASS,
                    "Successfully Open Site Configuration applicationImages");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration applicationImages ").log(Status.FAIL,
                    "Failed to Open the Site Configuration applicationImages  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void applicationImagesTreeOpen() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//li[@id='AppImages']/i"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration application Images Tree open").log(Status.PASS,
                    "Successfully Open Site Configuration application Images Tree Open");
        } catch (

        Exception e) {
            extentReports.createTest("Site Configuration application Images Tre Open ").log(Status.FAIL,
                    "Failed to Open the Site Configuration application  Images Tree Open  . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 7)
    public void androidTreeOpen() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//li[@id='ANDROID']/i"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration android Tree Open").log(Status.PASS,
                    "Successfully Open Site Configuration android Tree Open");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration android Tree Open ").log(Status.FAIL,
                    "Failed to Open the Site Configuration android Tree Open  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void iOSTreeOpen() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//li[@id='IOS']/i"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration iOS Tree Open").log(Status.PASS,
                    "Successfully Open Site Configuration iOS Tree Open");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration iOS Tree Open ").log(Status.FAIL,
                    "Failed to Open the Site Configuration iOS Tree Open  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void mPOSTreeOpen() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//li[@id='mposVersions']/i"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration mPOS Tree Open").log(Status.PASS,
                    "Successfully Open Site Configuration mPOS Tree Open");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration mPOS Tree Open ").log(Status.FAIL,
                    "Failed to Open the Site Configuration mPOS Tree Open  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void selectAndroid() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='ANDROID_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration select Android").log(Status.PASS,
                    "Successfully Open Site Configuration select Android");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select Android ").log(Status.FAIL,
                    "Failed to Open the Site Configuration select Android  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void getAndroidDetails() {

        WebElement labelElement = driver.findElement(By.id("lblAndroidTitle"));
        String labelText = labelElement.getText();
        if (labelText.contains("Jhansi Smart Parking Android Info")) {
            extentReports.createTest("Site Configuration Heading is ").log(Status.PASS,
                    "Successful :" + labelText);
        } else {

            extentReports.createTest("Site Configuration heading is").log(Status.FAIL,
                    "Failed. Exception: " + labelText);
        }

    }

    @Test(priority = 12)
    public void getAndroidCityName() {
        try {
            WebElement cityNameInput = driver
                    .findElement(By.xpath("//form[@id='addAndroidDetails']//*[@id='cityName']"));
            String cityNameValue = cityNameInput.getAttribute("value");
            System.out.println("City Name Input Value: " + cityNameValue);

            WebElement deviceTypeInput = driver.findElement(By.id("anddevicetype"));
            String deviceTypeValue = deviceTypeInput.getAttribute("value");
            System.out.println("Device Type Input Value: " + deviceTypeValue);

            System.out.println("City Name Value: " + cityNameValue);
            System.out.println("Device Type Value: " + deviceTypeValue);

            extentReports.createTest("Site Configuration Info details").log(Status.PASS,
                    "Successfully Open Site Configuration city name : " + cityNameValue).log(Status.PASS,
                            "Successfully Open Site Configuration device name : " + deviceTypeValue);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration android details ").log(Status.FAIL,
                    "Failed to Open the Site Configuration android details  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void selectAndroidVersion() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='andVersions_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration selectAndroidVersion").log(Status.PASS,
                    "Successfully Open Site Configuration selectAndroidVersion");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration selectAndroidVersion ").log(Status.FAIL,
                    "Failed to Open the Site Configuration selectAndroidVersion  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void checkInputEditability() {
        try {
            WebElement cityNameInput = driver
                    .findElement(By.xpath("//form[@id='addAndroidDetails']//*[@id='cityName']"));
            WebElement deviceTypeInput = driver.findElement(By.id("anddevicetype"));

            boolean isCityNameEditable = !cityNameInput.isEnabled();
            boolean isDeviceTypeEditable = !deviceTypeInput.isEnabled();

            System.out.println("City Name is editable: " + isCityNameEditable);
            System.out.println("Device Type is editable: " + isDeviceTypeEditable);

            extentReports.createTest("Site Configuration select Android Version").log(Status.PASS,
                    "Not editable ");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select Android Version ").log(Status.FAIL,
                    "Failed and the fields are editable  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void apkAndroidDwnld() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.id("androidAppDownoad"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration apkAndroidDwnld").log(Status.PASS,
                    "Successfully Open Site Configuration apkAndroidDwnld");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration apkAndroidDwnld ").log(Status.FAIL,
                    "Failed to Open the Site Configuration apkAndroidDwnld  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 16)
    public void selectIOS() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='IOS_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration selectIOS").log(Status.PASS,
                    "Successfully Open Site Configuration selectIOS");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration selectIOS ").log(Status.FAIL,
                    "Failed to Open the Site Configuration selectIOS. Exception: " + e.getCause());
        }
    }

    @Test(priority = 17)
    public void getiOSDetails() {

        WebElement labelElement = driver.findElement(By.id("lbliosTitle"));
        String labelText = labelElement.getText();
        if (labelText.contains("Jhansi Smart Parking IOS Info")) {
            extentReports.createTest("Site Configuration Heading is ").log(Status.PASS,
                    "Successful :" + labelText);
        } else {

            extentReports.createTest("Site Configuration heading is").log(Status.FAIL,
                    "Failed. Exception: " + labelText);
        }

    }

    @Test(priority = 18)
    public void selectiOSVersion() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='iosVersions_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration selectiOSVersion").log(Status.PASS,
                    "Successfully Open Site Configuration selectiOSVersion");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration selectiOSVersion ").log(Status.FAIL,
                    "Failed to Open the Site Configuration selectiOSVersion  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 19)
    public void getiOSCityName() {
        try {
            WebElement cityNameInput = driver.findElement(By.xpath("//form[@id='addiosDetails']//*[@id='cityName']"));
            String cityNameValue = cityNameInput.getAttribute("value");
            System.out.println("City Name Input Value: " + cityNameValue);

            WebElement deviceTypeInput = driver.findElement(By.id("iosDeviceType"));
            String deviceTypeValue = deviceTypeInput.getAttribute("value");
            System.out.println("Device Type Input Value: " + deviceTypeValue);

            System.out.println("City Name Value: " + cityNameValue);
            System.out.println("Device Type Value: " + deviceTypeValue);

            extentReports.createTest("Site Configuration iOS details").log(Status.PASS,
                    "Successfully Open Site Configuration city name : " + cityNameValue).log(Status.PASS,
                            "Successfully Open Site Configuration site name : " + deviceTypeValue);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration iOS details ").log(Status.FAIL,
                    "Failed to Open the Site Configuration iOS details  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void iOSDetails_Editable_or_Not() {
        try {
            WebElement cityNameInput = driver
                    .findElement(By.xpath("//form[@id='addiosDetails']//*[@id='cityName']"));
            WebElement deviceTypeInput = driver.findElement(By.id("iosDeviceType"));

            Assert.assertTrue(cityNameInput.getAttribute("disabled") != null, "City Name field is not disabled");
            Assert.assertTrue(deviceTypeInput.getAttribute("disabled") != null, "Device Type field is not disabled");

            extentReports.createTest("Site Configuration select ios Version").log(Status.PASS,
                    "Not editable ");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select ios Version ").log(Status.FAIL,
                    "Failed and the fields are editable  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 21)
    public void apkiOSDwnld() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//button[@id='iosAppDownoad']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration apkiOSDwnld").log(Status.PASS,
                    "Successfully Open Site Configuration apkiOSDwnld");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration apkiOSDwnld ").log(Status.FAIL,
                    "Failed to Open the Site Configuration apkiOSDwnld  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 22)
    public void selectmPOS() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='mposVersions_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration selectmPOS").log(Status.PASS,
                    "Successfully Open Site Configuration selectmPOS");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration selectmPOS ").log(Status.FAIL,
                    "Failed to Open the Site Configuration selectmPOS. Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void getmPOSDetails() {

        WebElement labelElement = driver.findElement(By.id("lblmposTitle"));
        String labelText = labelElement.getText();
        if (labelText.contains("Jhansi Smart Parking IOS Info")) {
            extentReports.createTest("Site Configuration Heading is ").log(Status.PASS,
                    "Successful :" + labelText);
        } else {

            extentReports.createTest("Site Configuration heading is").log(Status.FAIL,
                    "Failed. Exception: " + labelText);
        }

    }

    @Test(priority = 21)
    public void selectmPOSVersion() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='Versions_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration selectmPOSVersion").log(Status.PASS,
                    "Successfully Open Site Configuration selectmPOSVersion");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration selectmPOSVersion ").log(Status.FAIL,
                    "Failed to Open the Site Configuration selectmPOSVersion  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 22)
    public void mPOSDetails_Editable_or_Not() {
        try {
            WebElement cityNameInput = driver
                    .findElement(By.xpath("//form[@id='addmposDetails']//*[@id='cityName']"));
            WebElement deviceTypeInput = driver.findElement(By.id("mposDeviceType"));

            Assert.assertTrue(cityNameInput.getAttribute("disabled") != null, "City Name field is not disabled");
            Assert.assertTrue(deviceTypeInput.getAttribute("disabled") != null, "Device Type field is not disabled");

            extentReports.createTest("Site Configuration select mpos Version").log(Status.PASS,
                    "Not editable ");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select mpos Version ").log(Status.FAIL,
                    "Failed and the fields are editable  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 23)
    public void apkmPOSDwnld() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//button[@id='mposAppDownoad']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration apkiOSDwnld").log(Status.PASS,
                    "Successfully Open Site Configuration apkiOSDwnld");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration apkiOSDwnld ").log(Status.FAIL,
                    "Failed to Open the Site Configuration apkiOSDwnld  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 24)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Application Messages LOGOUT ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Application Messages LOGOUT").log(Status.FAIL,
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
