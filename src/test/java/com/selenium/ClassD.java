package com.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

class ServerConfigClassD {
    String userName;
    String password;
    Boolean production;

    public ServerConfigClassD(String userName, String password, Boolean production) {
        this.userName = userName;
        this.password = password;
        this.production = production;
    }

    public String getUrl() {
        if (production) {
            // return "https://admin-parking.pcmcsmartsarathi.org/SmartCity/ui/admin/";
            return "https://parking-jhansi.eparke.in/SmartCity/ui/admin/";
        }
        // return "https://pcmc-parking.eparke.in/SmartCity/ui/admin/";
        return "https://jhansi.eparke.in/SmartCity/ui/admin/";
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

public class ClassD extends BaseTestReport {
    WebDriver driver;

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
        // FirefoxOptions options = new FirefoxOptions();
        // options.setHeadless(true);
        // return new FirefoxDriver(options);
        return new FirefoxDriver();
    }

    @BeforeClass
    public void setUp() {
        extentReports = new ExtentReports();
        spark = new ExtentSparkReporter("results/SanityTestReport.html");
        extentReports.attachReporter(spark);
        extentTest = extentReports.createTest("demo");

        String browserPlugin = "firefox";
        System.setProperty(
                "webdriver.chrome.driver",
                setDriverPath(browserPlugin));

        driver = setBrowserDriver(browserPlugin);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void login() {
        ServerConfigClassD s = new ServerConfigClassD("testuser",
                "welcome@123", false);
        // ServerConfigRevision1261 s = new ServerConfigRevision1261("pcmcadmin",
        // "@Pcmc#Admin#@", false);
        // ServerConfigRevision1261 s = new ServerConfigRevision1261("guestuser",
        // "welcome@1", true);
        // ServerConfigRevision1261 s = new ServerConfigRevision1261("pcmcadmin",
        // "?ca2b_vr48", true);
        try {
            System.out.println(s.getUrl());
            driver.get(s.getUrl());
            driver.findElement(By.id("username")).sendKeys(s.getUserName());
            driver.findElement(By.id("password")).sendKeys(s.getPassword());
            driver.findElement(By.name("login")).click();
            extentReports.createTest("Login").log(Status.PASS, "Successfully Login");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Login").log(Status.FAIL, "Failed to login. Exception: " + e.getCause());
        }
    }

    @Test(priority = 2)
    public void revenueDashboardOpen() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.className("db-pg")).click();
            extentReports.createTest("Dashboard ").log(Status.PASS, "Successfully Open Dashboard Revenue tab");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Dashboard").log(Status.FAIL,
                    "Failed to Open the dashboard. Exception: " + e.getCause());
        }
    }

    @Test(priority = 3)
    public void configurationPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[5]")).click();

            extentReports.createTest("Configuartion ").log(Status.PASS, "Successfully Open Configuartion");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Configuartion").log(Status.FAIL,
                    "Failed to Open the Configuartion. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void siteConfigurationPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("sm-pg")).click();
            extentReports.createTest("Site Configuration ").log(Status.PASS, "Successfully Open Site Configuration ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration ").log(Status.FAIL,
                    "Failed to Open the Site Configuration . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void jstreeOrg() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath(("//ul[@role='group']/li/i"))).click();
            extentReports.createTest("Site Configuration organisation info").log(Status.PASS,
                    "Successfully Open Site Configuration organisation information  ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration organisation info ").log(Status.FAIL,
                    "Failed to Open the Site Configuration organisation information  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void jstreeCity() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//li[@id='city_5']/i")).click();
            extentReports.createTest("Site Configuration click city").log(Status.PASS,
                    "Successfully Open Site Configuration city will dsiplay the zones ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration city ").log(Status.FAIL,
                    "Failed to Open the Site Configuration city to display the zones . Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void jstreeZone() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//li[@id='zone_1']/i")).click();
            extentReports.createTest("Site Configuration click zone").log(Status.PASS,
                    "Successfully Open Site Configuration zone will display the sites ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration zone ").log(Status.FAIL,
                    "Failed to Open the Site Configuration zone to display the sites. Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void jstreeSite() {
        try {
            Thread.sleep(2000);
            WebElement siteElement = driver.findElement(By.xpath("//li[@id='site_56']/i"));

            if (siteElement.isDisplayed()) {
                siteElement.click();
                extentReports.createTest("Site Configuration site ").log(Status.PASS, "Clicked JS Tree for site_56");
            } else {
                extentReports.createTest("Site Configuration site ").log(Status.INFO, "Site_56 is not displayed");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration site ").log(Status.FAIL,
                    "Failed to Open the Site Configuration site . Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void jstreeOffStreet() {
        try {
            Thread.sleep(2000);
            WebElement anchorElement = driver.findElement(By.xpath("//a[@id='level_61_anchor']"));
            anchorElement.click();
            WebElement siteElement = driver.findElement(By.xpath("//li[@id='level_61']/i"));

            if (siteElement.isDisplayed()) {
                siteElement.click();
                extentReports.createTest("Site Configuration OffStreet ").log(Status.PASS,
                        "Clicked JS Tree for OffStreet");
            } else {
                extentReports.createTest("Site Configuration OffStreet ").log(Status.INFO,
                        "OffStreet is not displayed");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration OffStreet ").log(Status.FAIL,
                    "Failed to Open the Site Configuration OffStreet . Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void offStreetCarSlotsCountUpdate() {
        By inputFieldXPath = By.id("CarSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String carCount = "40";
                inputField.sendKeys(carCount);
                extentReports.createTest("Site Configuration car slots ").log(Status.PASS,
                        "Input Field is editable.to update the slots : " + carCount);
            } else {
                extentReports.createTest("Site Configuration car slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration car slots update ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 11)
    public void offStreetSlotsUpdateButton() {
        try {
            Thread.sleep(2000);
            WebElement updateButton = driver
                    .findElement(By.xpath("//div[@id='levelEditDetails']/div//button[@id='btnSaveSite']"));
            if (updateButton.isDisplayed()) {
                updateButton.click();
                extentReports.createTest("Site Configuration OffStreet update button").log(Status.PASS,
                        "Clicked update button of slots");
            } else {
                extentReports.createTest("Site Configuration OffStreet update button  ").log(Status.INFO,
                        "Update button is not found ");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Site Configuration OffStreet update button").log(Status.FAIL,
                    "Failed to update the slots count . Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void extractOffStreetUpdateSlots() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));
            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text in offstreet to update the slots ").log(Status.PASS,
                    "Extracted text : " + popupText + popup1Text);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text in in offstreet to update the slots ").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 13)
    public void updateOffstreetSlotsStatus() {
        try {
            Thread.sleep(2000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Offstreet to update the slots status ").log(Status.PASS,
                    "Clicked the 'OK' button in offtreet.");
        } catch (Exception e) {
            extentReports.createTest("Offstreet to update the slots status").log(Status.FAIL,
                    "Failed to click the 'OK' button in offstreet. Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void viewJsTreeUpToOffStreet() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 15)
    public void offStreetVipSlotsUpdate() {
        By inputFieldXPath = By.id("totalVipSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String vipCount = "5";
                inputField.sendKeys(vipCount);
                extentReports.createTest("Site Configuration vip slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + vipCount);
            } else {
                extentReports.createTest("Site Configuration vip slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration vip slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 16)
    public void offStreetVipReserveSlotsUpdate() {
        By inputFieldXPath = By.id("totalResVipSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String vipReserveCount = "2";
                inputField.sendKeys(vipReserveCount);
                extentReports.createTest("Site Configuration vip Reserve slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + vipReserveCount);
            } else {
                extentReports.createTest("Site Configuration vip Reserve slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration vip Reserve slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void offStreetVipSlotsUpdateButton() {
        offStreetSlotsUpdateButton();
        extractOffStreetUpdateSlots();
        updateOffstreetSlotsStatus();
    }

    @Test(priority = 18)
    public void viewJsTreeUpToOffStreetThrice() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 19)
    public void offStreetBikeSlotsCountUpdate() {
        By inputFieldXPath = By.id("totalBikeSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String bikeCount = "20";
                inputField.sendKeys(bikeCount);
                extentReports.createTest("Site Configuration bike slots ").log(Status.PASS,
                        "Input Field is editable.to update the slots : " + bikeCount);
            } else {
                extentReports.createTest("Site Configuration bike slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration bike slots update ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 20)
    public void offStreetBikeSlotsUpdateButton() {
        offStreetSlotsUpdateButton();
        extractOffStreetUpdateSlots();
        updateOffstreetSlotsStatus();
    }

    @Test(priority = 21)
    public void viewJsTreeUpToOffStreetFourTh() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 22)
    public void offStreetTruckSlotsCountUpdate() {
        By inputFieldXPath = By.id("totalTruckSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalTruckCount = "20";
                inputField.sendKeys(totalTruckCount);
                extentReports.createTest("Site Configuration total truck slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalTruckCount);
            } else {
                extentReports.createTest("Site Configuration total truck slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total truck slots ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void offStreetTruckSlotsSensorsCountUpdate() {
        By inputFieldXPath = By.id("totalTruckSensor");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalTruckSensorCount = "20";
                inputField.sendKeys(totalTruckSensorCount);
                extentReports.createTest("Site Configuration total truck sensor slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalTruckSensorCount);
            } else {
                extentReports.createTest("Site Configuration total truck sensor slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total truck sensor slots ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 24)
    public void offStreetTruckSlotsUpdateButton() {
        offStreetSlotsUpdateButton();
        extractOffStreetUpdateSlots();
        updateOffstreetSlotsStatus();
    }

    @Test(priority = 25)
    public void viewJsTreeUpToOffStreetFifTh() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 26)
    public void offStreetTotalFireSlotsUpdate() {
        By inputFieldXPath = By.id("totalFireSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalFireSlot = "5";
                inputField.sendKeys(totalFireSlot);
                extentReports.createTest("Site Configuration total Fire slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalFireSlot);
            } else {
                extentReports.createTest("Site Configuration total Fire slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total fire slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 27)
    public void offStreetTotalReserveFireSlotsUpdate() {
        By inputFieldXPath = By.id("totalResFireSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalResFireSlots = "5";
                inputField.sendKeys(totalResFireSlots);
                extentReports.createTest("Site Configuration total Reserve Fire slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalResFireSlots);
            } else {
                extentReports.createTest("Site Configuration total Reserve Fire slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total Reserve fire slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 28)
    public void offStreetTotalPassFireSlotsUpdate() {
        By inputFieldXPath = By.id("totalPassFireSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalPassFireSlots = "5";
                inputField.sendKeys(totalPassFireSlots);
                extentReports.createTest("Site Configuration total Pass Fire slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalPassFireSlots);
            } else {
                extentReports.createTest("Site Configuration total Pass Fire slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total Pass fire slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 29)
    public void offStreetFireSlotsUpdateButton() {
        offStreetSlotsUpdateButton();
        extractOffStreetUpdateSlots();
        updateOffstreetSlotsStatus();
    }

    @Test(priority = 30)
    public void viewJsTreeUpToOffStreetSixTh() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 31)
    public void offStreetTotalPhyChallengeSlotsUpdate() {
        By inputFieldXPath = By.id("totalPhChalSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalPhChalSlots = "5";
                inputField.sendKeys(totalPhChalSlots);
                extentReports.createTest("Site Configuration total physically challenged slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalPhChalSlots);
            } else {
                extentReports.createTest("Site Configuration total physically challenged slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total physically challenged slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 32)
    public void offStreetTotalPhyChalangeReserveSlotsUpdate() {
        By inputFieldXPath = By.id("totalResPhChalSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalResPhChalSlots = "5";
                inputField.sendKeys(totalResPhChalSlots);
                extentReports.createTest("Site Configuration total physically challenged Reserve slots ").log(
                        Status.PASS,
                        "Input Field is editable to update the slots : " + totalResPhChalSlots);
            } else {
                extentReports.createTest("Site Configuration total physically challenged Reserve slots ").log(
                        Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total physically challenged Reserve slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 33)
    public void offStreetTotalPassPhyChalangeSlotsUpdate() {
        By inputFieldXPath = By.id("totalPassPhChalSlots");

        try {
            WebElement inputField = driver.findElement(inputFieldXPath);

            if (inputField.isEnabled()) {
                inputField.clear();
                String totalPassPhChalSlots = "5";
                inputField.sendKeys(totalPassPhChalSlots);
                extentReports.createTest("Site Configuration total physically challenged Pass slots ").log(Status.PASS,
                        "Input Field is editable to update the slots : " + totalPassPhChalSlots);
            } else {
                extentReports.createTest("Site Configuration total physically challenged Pass slots ").log(Status.INFO,
                        "Input Field is not editable to update the slots");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration total physically challenged Pass slots  ").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 34)
    public void offStreetPhyChalangeSlotsUpdateButton() {
        offStreetSlotsUpdateButton();
        extractOffStreetUpdateSlots();
        updateOffstreetSlotsStatus();
    }

    @Test(priority = 35)
    public void viewJsTreeUpToOffStreetSevenTh() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 36)
    public void offStreetAddSingleButton() {
        By inputFieldXPath = By.xpath("//button[@id='btnAddDevices']");
        try {
            WebElement inputField = driver.findElement(inputFieldXPath);
            if (inputField.isEnabled()) {
                inputField.click();
                extentReports.createTest("Site Configuration  Add Single Device ").log(Status.PASS,
                        "In offStreet successfully clicked  Add Single Device ");
            } else {
                extentReports.createTest("Site Configuration  Add Single Device").log(Status.INFO,
                        " Add Single Device not found ");
            }
        } catch (Exception e) {
            extentReports.createTest("Site Configuration  Add Single Device").log(Status.FAIL,
                    "An error occurred: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 37)
    public void selectFasTag() {
        try {
            WebElement selectElement = driver.findElement(By.id("deviceCatagory"));
            selectElement.click();
            Select select = new Select(selectElement);
            select.selectByVisibleText("Fast Tag Controller");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedDeviceName = selectedOption.getAttribute("value");
            System.out.println("Selected Device Type: " + selectedDeviceName);

            Select dropdown = new Select(selectElement);
            Thread.sleep(1000);

            List<WebElement> options = dropdown.getOptions();
            for (WebElement option : options) {
                System.out.println("Device_Type: " + option.getAttribute("value"));
            }

            extentReports.createTest("Select single device ").log(Status.PASS,
                    "Successfully selected the device: " + selectedDeviceName);
        } catch (Exception e) {
            extentReports.createTest("Select single device").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 38)
    public void fasTagControllerDetails() {
        try {
            Thread.sleep(1000);
            String deviceName = "fasttag123";
            driver.findElement(By.id("fcDeviceName")).sendKeys(deviceName);
            Thread.sleep(1000);
            String deviceMacId = "&&&&&&";
            driver.findElement(By.id("deviceMacId")).sendKeys(deviceMacId);
            String presentAt = "Entry";
            driver.findElement(By.id("ftcpresentat")).sendKeys(presentAt);

            extentReports.createTest("Select single device fastTag Controller details ").log(Status.PASS,
                    "Successfully selected the device controller details fcDeviceName : " + deviceName).log(Status.PASS,
                            "Successfully selected the device controller details deviceMacId : " + deviceMacId)
                    .log(Status.PASS,
                            "Successfully selected the device controller details ftcpresentat : " + presentAt);
        } catch (Exception e) {
            extentReports.createTest("Select single device fasTag Controller details").log(Status.FAIL,
                    "Failed to enter the fastTag controller details. Exception: " + e.getCause());
        }
    }

    @Test(priority = 39)
    public void ftControllerSaveButton() {
        try {
            Thread.sleep(2000);
            WebElement updateButton = driver
                    .findElement(By.xpath("//form[@id='addAllDeviceDetail']//button[@id='btnSaveDevices']"));
            if (updateButton.isDisplayed()) {
                updateButton.click();
                extentReports.createTest("Single device add fasTag Controller button").log(Status.PASS,
                        "Clicked fasTag controller save button");
            } else {
                extentReports.createTest("Single device and fasTag Controller button").log(Status.INFO,
                        "Save button is not found ");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Single device fasTag Controller button").log(Status.FAIL,
                    "Failed to click the save button . Exception: " + e.getCause());
        }
    }

    @Test(priority = 40)
    public void ftControllerSavePopUp() {
        extractOffStreetUpdateSlots();
        updateOffstreetSlotsStatus();
    }

    // @Test(priority = 41)
    public void viewJsTreeUpToOffStreetEighTh() {
        jstreeOrg();
        jstreeCity();
        jstreeZone();
        jstreeSite();
        jstreeOffStreet();
    }

    @Test(priority = 43)
    public void selectMobilePrinter() {
        try {
            WebElement selectElement = driver.findElement(By.id("deviceCatagory"));
            selectElement.click();
            Select select = new Select(selectElement);
            select.selectByVisibleText("Mobile Printer");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedDeviceName = selectedOption.getAttribute("value");
            System.out.println("Selected Device Type: " + selectedDeviceName);

            Select dropdown = new Select(selectElement);
            Thread.sleep(1000);

            List<WebElement> options = dropdown.getOptions();
            for (WebElement option : options) {
                System.out.println("Device_Type: " + option.getAttribute("value"));
            }

            extentReports.createTest("Select single device ").log(Status.PASS,
                    "Successfully selected the device: " + selectedDeviceName);
        } catch (Exception e) {
            extentReports.createTest("Select single device").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 44)
    public void MobilePrinterDetails() {
        try {
            Thread.sleep(1000);
            String deviceName = "bluetooth";
            driver.findElement(By.id("bluetoothName")).sendKeys(deviceName);
            Thread.sleep(1000);
            String deviceMacId = "ed4rDF55g";
            driver.findElement(By.id("deviceMacId")).sendKeys(deviceMacId);
            String presentAt = "Entry";
            driver.findElement(By.id("ftcpresentat")).sendKeys(presentAt);

            extentReports.createTest("Select single device fastTag Controller details ").log(Status.PASS,
                    "Successfully selected the device controller details fcDeviceName : " + deviceName).log(Status.PASS,
                            "Successfully selected the device controller details deviceMacId : " + deviceMacId)
                    .log(Status.PASS,
                            "Successfully selected the device controller details ftcpresentat : " + presentAt);
        } catch (Exception e) {
            extentReports.createTest("Select single device fasTag Controller details").log(Status.FAIL,
                    "Failed to enter the fastTag controller details. Exception: " + e.getCause());
        }
    }

    @AfterClass
    public void tearDown() {
        extentReports.flush();
        driver.quit();
    }
}
