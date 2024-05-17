package com.selenium;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class ApplicationImages extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/ApplicationImages.html");
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
    public void getAndroidHeading() {
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
    public void androidDetails_Editable_Or_Not() {
        try {
            WebElement cityNameField = driver
                    .findElement(By.xpath("//form[@id='addAndroidDetails']//*[@id='cityName']"));
            WebElement deviceTypeField = driver.findElement(By.id("anddevicetype"));

            boolean isCityNameEditable = cityNameField.isEnabled();
            boolean isDeviceTypeEditable = deviceTypeField.isEnabled();

            String cityName = cityNameField.getAttribute("value");
            String deviceType = deviceTypeField.getAttribute("value");

            extentReports.createTest("Check Android Details Fields Editable and Get Data")
                    .log(Status.INFO, "City Name Field Editable: " + isCityNameEditable)
                    .log(Status.INFO, "City Name: " + cityName)
                    .log(Status.INFO, "Device Type Field Editable: " + isDeviceTypeEditable)
                    .log(Status.INFO, "Device Type: " + deviceType);

            extentReports.createTest("Check Android Details Fields Editable ").log(Status.PASS,
                    "Field editability check and data retrieval completed successfully.");
        } catch (Exception e) {
            extentReports.createTest("Check Android Details Fields Editable ").log(Status.FAIL,
                    "Failed to check field editability and retrieve data. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 13)
    public void getAndroidInfo() {
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

            extentReports.createTest("Site Configuration android Info details").log(Status.PASS,
                    "Successfully Open Site Configuration city name : " + cityNameValue).log(Status.PASS,
                            "Successfully Open Site Configuration device name : " + deviceTypeValue);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration android Info details ").log(Status.FAIL,
                    "Failed to Open the Site Configuration android Info details  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void selectAndroidVersion() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='andVersions_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration select Android Version").log(Status.PASS,
                    "Successfully Open Site Configuration select Android Version");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select Android Version ").log(Status.FAIL,
                    "Failed to Open the Site Configuration select Android Version  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void getAndroidVersionDetails_Heading() {

        WebElement labelElement = driver.findElement(By.id("lblandroidVersionTitle"));
        String labelText = labelElement.getText();
        if (labelText.contains("Android APK Details")) {
            extentReports.createTest("Android version Heading is ").log(Status.PASS,
                    "Successful :" + labelText);
        } else {
            extentReports.createTest("Android version heading is").log(Status.FAIL,
                    "Failed. Exception: " + labelText);
        }

    }

    @Test(priority = 16)
    public void androidVersion_Editable_or_Not() {
        try {
            WebElement appNameField = driver.findElement(By.id("androidApp_name"));
            WebElement appSizeField = driver.findElement(By.id("androidApp_size"));
            WebElement versionNameField = driver.findElement(By.id("androidVersion_Name"));
            WebElement uploadedTimeField = driver.findElement(By.id("androidUploaded_Time"));

            boolean isAppNameEditable = appNameField.isEnabled();
            boolean isAppSizeEditable = appSizeField.isEnabled();
            boolean isVersionNameEditable = versionNameField.isEnabled();
            boolean isUploadedTimeEditable = uploadedTimeField.isEnabled();

            String appName = appNameField.getAttribute("value");
            String appSize = appSizeField.getAttribute("value");
            String versionName = versionNameField.getAttribute("value");
            String uploadedTime = uploadedTimeField.getAttribute("value");

            extentReports.createTest("Check Android App Fields Editable or not").log(Status.PASS,
                    "Field editability check and data retrieval completed successfully.")
                    .log(Status.INFO, "App Name Field Editable: " + isAppNameEditable)
                    .log(Status.INFO, "App Name: " + appName)
                    .log(Status.INFO, "App Size Field Editable: " + isAppSizeEditable)
                    .log(Status.INFO, "App Size: " + appSize)
                    .log(Status.INFO, "Version Name Field Editable: " + isVersionNameEditable)
                    .log(Status.INFO, "Version Name: " + versionName)
                    .log(Status.INFO, "Uploaded Time Field Editable: " + isUploadedTimeEditable)
                    .log(Status.INFO, "Uploaded Time: " + uploadedTime);
            ;
        } catch (Exception e) {
            extentReports.createTest("Check Android App Fields Editable and Get Data").log(Status.FAIL,
                    "Failed to check field editability and retrieve data. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void apkAndroidDwnld() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.id("androidAppDownoad"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration apk Android download").log(Status.PASS,
                    "Successfully Open Site Configuration apk Android download");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration apk Android download ").log(Status.FAIL,
                    "Failed to Open the Site Configuration apk Android download  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 18)
    public void selectIOS() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='IOS_anchor']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration select IOS").log(Status.PASS,
                    "Successfully Open Site Configuration select IOS");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select IOS ").log(Status.FAIL,
                    "Failed to Open the Site Configuration select IOS. Exception: " + e.getCause());
        }
    }

    @Test(priority = 19)
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

    @Test(priority = 20)
    public void iOSDetails_Editable_Or_Not() {
        try {
            WebElement cityNameField = driver.findElement(By.xpath("//form[@id='addiosDetails']//*[@id='cityName']"));
            WebElement deviceTypeField = driver.findElement(By.id("iosDeviceType"));

            boolean isCityNameEditable = cityNameField.isEnabled();
            boolean isDeviceTypeEditable = deviceTypeField.isEnabled();

            String cityName = cityNameField.getAttribute("value");
            String deviceType = deviceTypeField.getAttribute("value");

            extentReports.createTest("Check iOS Details Fields Editable and Get Data")
                    .log(Status.INFO, "City Name Field Editable: " + isCityNameEditable)
                    .log(Status.INFO, "City Name: " + cityName)
                    .log(Status.INFO, "Device Type Field Editable: " + isDeviceTypeEditable)
                    .log(Status.INFO, "Device Type: " + deviceType);

            extentReports.createTest("Check ios Details Fields Editable ").log(Status.PASS,
                    "Field editability check and data retrieval completed successfully.");
        } catch (Exception e) {
            extentReports.createTest("Check ios Details Fields Editable ").log(Status.FAIL,
                    "Failed to check field editability and retrieve data. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 21)
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

    @Test(priority = 22)
    public void iOSVersion_Editable_or_Not() {
        try {
            WebElement appNameField = driver.findElement(By.id("iosApp_name"));
            WebElement appSizeField = driver.findElement(By.id("iosApp_size"));
            WebElement versionNameField = driver.findElement(By.id("iosVersion_Name"));
            WebElement uploadedTimeField = driver.findElement(By.id("iosUploaded_Time"));

            boolean isAppNameEditable = appNameField.isEnabled();
            boolean isAppSizeEditable = appSizeField.isEnabled();
            boolean isVersionNameEditable = versionNameField.isEnabled();
            boolean isUploadedTimeEditable = uploadedTimeField.isEnabled();

            String appName = appNameField.getAttribute("value");
            String appSize = appSizeField.getAttribute("value");
            String versionName = versionNameField.getAttribute("value");
            String uploadedTime = uploadedTimeField.getAttribute("value");

            extentReports.createTest("Check iOS App Fields Editable or not").log(Status.PASS,
                    "Field editability check and data retrieval completed successfully.")
                    .log(Status.INFO, "App Name Field Editable: " + isAppNameEditable)
                    .log(Status.INFO, "App Name: " + appName)
                    .log(Status.INFO, "App Size Field Editable: " + isAppSizeEditable)
                    .log(Status.INFO, "App Size: " + appSize)
                    .log(Status.INFO, "Version Name Field Editable: " + isVersionNameEditable)
                    .log(Status.INFO, "Version Name: " + versionName)
                    .log(Status.INFO, "Uploaded Time Field Editable: " + isUploadedTimeEditable)
                    .log(Status.INFO, "Uploaded Time: " + uploadedTime);
            ;
        } catch (Exception e) {
            extentReports.createTest("Check iOS App Fields Editable and Get Data").log(Status.FAIL,
                    "Failed to check field editability and retrieve data. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void getiOSVersionDetails_Heading() {

        WebElement labelElement = driver.findElement(By.id("lbliosVersionTitle"));
        String labelText = labelElement.getText();
        if (labelText.contains("IOS App Info")) {
            extentReports.createTest("IOS version Heading is ").log(Status.PASS,
                    "Successful :" + labelText);
        } else {
            extentReports.createTest("IOS version heading is").log(Status.FAIL,
                    "Failed. Exception: " + labelText);
        }

    }

    @Test(priority = 24)
    public void apkiOSDwnld() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//button[@id='iosAppDownoad']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration apk iOS download").log(Status.PASS,
                    "Successfully Open Site Configuration apk iOS download");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration apk iOS download ").log(Status.FAIL,
                    "Failed to Open the Site Configuration apk iOS download  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 25)
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

    @Test(priority = 26)
    public void getmPOSDetails() {

        WebElement labelElement = driver.findElement(By.id("lblmposTitle"));
        String labelText = labelElement.getText();
        if (labelText.contains("Jhansi Smart Parking Mpos Info")) {
            extentReports.createTest("MPOS heading is ").log(Status.PASS,
                    "Successful :" + labelText);
        } else {

            extentReports.createTest("MPOS heading is").log(Status.FAIL,
                    "Failed. Exception: " + labelText);
        }

    }

    @Test(priority = 27)
    public void mPOSDetails_Editable_Or_Not() {
        try {
            WebElement cityNameField = driver
                    .findElement(By.xpath("//form[@id='addmposDetails']//*[@id='cityName']"));
            WebElement deviceTypeField = driver.findElement(By.id("mposDeviceType"));

            boolean isCityNameEditable = cityNameField.isEnabled();
            boolean isDeviceTypeEditable = deviceTypeField.isEnabled();

            String cityName = cityNameField.getAttribute("value");
            String deviceType = deviceTypeField.getAttribute("value");

            extentReports.createTest("Check mPOS Details Fields Editable and Get Data")
                    .log(Status.INFO, "City Name Field Editable: " + isCityNameEditable)
                    .log(Status.INFO, "City Name: " + cityName)
                    .log(Status.INFO, "Device Type Field Editable: " + isDeviceTypeEditable)
                    .log(Status.INFO, "Device Type: " + deviceType);

            extentReports.createTest("Check mPOS Details Fields Editable ").log(Status.PASS,
                    "Field editability check and data retrieval completed successfully.");
        } catch (Exception e) {
            extentReports.createTest("Check mPOS Details Fields Editable ").log(Status.FAIL,
                    "Failed to check field editability and retrieve data. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 28)
    public void selectmPOSVersion() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//a[@id='Versions_anchor']"));

            if (jstreeElement.isDisplayed()) {
                jstreeElement.click();
                Thread.sleep(1000);
                extentReports.createTest("Site Configuration selectmPOSVersion").log(Status.PASS,
                        "Successfully Open Site Configuration selectmPOSVersion");
            } else {
                extentReports.createTest("Site Configuration select mPOS Version").log(Status.INFO,
                        "jstree element is not present. Getting data as No element found.");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration select mPOS Version ").log(Status.FAIL,
                    "Failed to Open the Site Configuration select mPOS Version. Exception: " + e.getCause());
        }
    }

    @Test(priority = 29)
    public void apkmPOSDwnld() {
        try {
            Thread.sleep(3000);
            WebElement jstreeElement = driver.findElement(By.xpath("//button[@id='mposAppDownoad']"));
            jstreeElement.click();
            Thread.sleep(1000);
            extentReports.createTest("Site Configuration apk mPOS download").log(Status.PASS,
                    "Successfully Open Site Configuration apk mpos download");
        } catch (Exception e) {
            extentReports.createTest("Site Configuration apk mpos download ").log(Status.FAIL,
                    "Failed to Open the Site Configuration apk mpos download  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 30)
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
