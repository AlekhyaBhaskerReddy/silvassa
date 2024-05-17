package com.selenium;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.model.ServerCredentials;

public class PosManagement extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        extentReports = new ExtentReports();
        spark = new ExtentSparkReporter("results/PosManagemenet.html");
        extentReports.attachReporter(spark);
        extentTest = extentReports.createTest("demo");

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
    public void posConfigurationPage() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[5]")).click();
            Thread.sleep(3000);

            driver.findElement(By.className("pm-pg")).click();
            extentReports.createTest("POS Management ").log(Status.PASS, "Successfully Open POS Management  ");

        } catch (Exception e) {
            extentReports.createTest("POS Management ").log(Status.FAIL,
                    "Failed to Open the POS Management . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void parkingVehicleTab() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.id("pop2-tab")).click();

            extentReports.createTest("POS Management vehicle type").log(Status.PASS,
                    "Successfully Open parking vehicle type ");

        } catch (Exception e) {
            extentReports.createTest("POS Management vehicle type").log(Status.FAIL,
                    "Failed to parking vehicle type . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectSiteName() {
        try {
            Thread.sleep(3000);
            List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
            WebElement siteInput = driver.findElement(By.id("pos_site_name"));
            WebElement site = siteList.get(1);
            site.click();
            String selectedSiteName = siteInput.getText();

            extentReports.createTest("POS Management selected site ").log(Status.PASS,
                    "Successfully select the site name : " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("POS Management selected site ").log(Status.FAIL,
                    "Failed to select the site name . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void clickAddParkVehicle() {

        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//div[@id='parkingVechTables']/button")).click();

            extentReports.createTest("POS Management add vehicle type").log(Status.PASS,
                    "Successfully Open click the add vehicle type ");

        } catch (Exception e) {
            extentReports.createTest("POS Management add vehicle type").log(Status.FAIL,
                    "Failed to click the add vehicle type . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void selectVehicle() {

        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//form[@id='addPrkVechDetail']//select[@id='vehicleType']")).click();
            Thread.sleep(2000);
            List<WebElement> vehicleType = driver
                    .findElements(By.xpath("//form[@id='addPrkVechDetail']//select[@id='vehicleType']/option"));
            vehicleType.get(3).click();
            System.out.println(vehicleType.get(3).getText());

            for (WebElement option : vehicleType) {
                if (option.getText().equalsIgnoreCase("FOUR")) {
                    option.click();
                    break;
                }
            }
            extentReports.createTest("POS Management select vehicle type").log(Status.PASS,
                    "List of vehicle types: ");
        } catch (Exception e) {
            extentReports.createTest("POS Management select vehicle type").log(Status.FAIL,
                    "Failed to enter the vehicle type 'EV Two wheeler'. Exception: " + e.getMessage());
        }

    }

    @Test(priority = 8)
    public void addVehicleBtn() {

        try {
            Thread.sleep(5000);
            driver.findElement(By.id("btnSavePkVech")).click();
            extentReports.createTest("POS Management select vehicle type button ").log(Status.PASS,
                    "Successfully click the vehicle type button");
        } catch (Exception e) {
            extentReports.createTest("POS Management select vehicle type ").log(Status.FAIL,
                    "Failed to click the vehicle type button . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 9)
    public void extractPopupTextAddVehicle() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();

            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text in add Parking vehicle").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text in add Parking vehicle").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text in add Parking vehicle").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 10)
    public void statusOKAddVehicle() {
        try {
            Thread.sleep(2000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status").log(Status.PASS,
                    "Clicked the 'OK' button in add parking vehicle.");
        } catch (Exception e) {
            extentReports.createTest("Status").log(Status.FAIL,
                    "Failed to click the 'OK' button in add parking vehicle. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void parkingChargesTab() {

        try {
            Thread.sleep(3000);
            driver.findElement(By.id("pop3-tab")).click();

            extentReports.createTest("POS Management parking charges").log(Status.PASS,
                    "Successfully click parking charges");

        } catch (Exception e) {
            extentReports.createTest("POS Management parking charges").log(Status.FAIL,
                    "Failed to click parking charges . Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void parkingChargesView() {

        try {
            Thread.sleep(3000);
            WebElement button = driver.findElement(By.className("viewParkBtn"));
            button.click();

            extentReports.createTest("Parking charges view").log(Status.PASS,
                    "Successfully click parking charges view button");

        } catch (Exception e) {
            extentReports.createTest("Parking charges view").log(Status.FAIL,
                    "Failed to click parking charges view button . Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void selectAllWeekButtons() {
        try {
            Thread.sleep(3000);
            WebElement buttonGroup = driver.findElement(By.id("fairtablerange"));
            List<WebElement> buttons = buttonGroup.findElements(By.tagName("label"));

            for (WebElement button : buttons) {
                Thread.sleep(1000);
                button.click();

                extentReports.createTest("Select All Week Buttons").log(Status.PASS,
                        "Successfully clicked all week buttons (MON to SUN)");
            }
        } catch (Exception e) {
            extentReports.createTest("Select All Week Buttons").log(Status.FAIL,
                    "Failed to click all week buttons. Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void selectWednesdayButton() {
        try {
            Thread.sleep(3000);
            WebElement wednesdayButton = driver.findElement(By.xpath("//div[@id='fairtablerange']/label[3]"));

            wednesdayButton.click();

            extentReports.createTest("Select Wednesday Button").log(Status.PASS,
                    "Successfully selected Wednesday (WED) button");
        } catch (Exception e) {
            extentReports.createTest("Select Wednesday Button").log(Status.FAIL,
                    "Failed to select Wednesday (WED) button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 15)
    public void updateInputValue() {
        try {
            Thread.sleep(3000);
            WebElement inputElement = driver.findElement(By.cssSelector("input.fareInput.inputDiscount"));
            inputElement.clear();
            String elementValue = "10";
            inputElement.sendKeys(elementValue);

            extentReports.createTest("Update Input Value in parking charges").log(Status.PASS,
                    "Successfully updated the input value to : " + elementValue);
        } catch (Exception e) {
            extentReports.createTest("Update Input Value in parking charges").log(Status.FAIL,
                    "Failed to update the input value. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 16)
    public void pChargesSubmitBtn() {
        try {
            Thread.sleep(2000);
            WebElement submitButton = driver.findElement(By.id("fairTableSubmit"));
            submitButton.click();

            extentReports.createTest("Click Submit Button in parking charges").log(Status.PASS,
                    "Successfully clicked the Submit button");
        } catch (Exception e) {
            extentReports.createTest("Click Submit Button in parking charges").log(Status.FAIL,
                    "Failed to click the Submit button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void extractUpdateParckingChargesText() {
        try {
            Thread.sleep(2000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text in Parking Charges").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text in Parking Charges").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text in Parking Charges").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 18)
    public void parkingChargeStatusOK() {
        try {
            Thread.sleep(1000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status").log(Status.PASS,
                    "Clicked the 'OK' button in parking charges.");
        } catch (Exception e) {
            extentReports.createTest("Status").log(Status.FAIL,
                    "Failed to click the 'OK' button in parking charges. Exception: " + e.getCause());
        }
    }

    @Test(priority = 21)
    public void selectReserveCharges() {
        try {
            WebElement selectElement = driver.findElement(By.id("selectParkChrging"));
            Select select = new Select(selectElement);
            select.selectByValue("2");

            extentReports.createTest("Select Reservation Charges").log(Status.PASS,
                    "Successfully select the reservation charges.");
        } catch (Exception e) {
            extentReports.createTest("Select Reservation Charges").log(Status.FAIL,
                    "Failed to select the reservation charges. Exception: " + e.getCause());
        }
    }

    @Test(priority = 22)
    public void reserveChargesEditBtn() {
        try {
            WebElement editButton = driver
                    .findElement(By.xpath("//button[@id='editChargesBtn' and @pcid='3729']"));

            editButton.click();

            extentReports.createTest("Click Edit Button in Reservation charges").log(Status.PASS,
                    "Successfully clicked the Edit button");
        } catch (Exception e) {
            extentReports.createTest("Click Edit Button in reservation Charges").log(Status.FAIL,
                    "Failed to click the Edit button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void updatereservationCharges() {
        try {
            WebElement chargesInput = driver.findElement(By.id("rnCharges"));
            chargesInput.clear();
            String updateCharge = "10";
            chargesInput.sendKeys(updateCharge);

            extentReports.createTest("Update Reservation Charges").log(Status.PASS,
                    "Successfully set the charges to : " + updateCharge);
        } catch (Exception e) {
            extentReports.createTest("Update Reservation Charges").log(Status.FAIL,
                    "Failed to set the charges. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 24)
    public void updateReservationsSubmitBtn() {
        try {
            Thread.sleep(2000);
            WebElement updateButton = driver
                    .findElement(By.id("btnSaveEdtChrg"));
            if (updateButton.isDisplayed()) {
                updateButton.click();
                extentReports.createTest("Click Update Button of Reservation charges").log(Status.PASS,
                        "Successfully clicked the Update button ");
            } else {
                extentReports.createTest("Click Update Button of Reservation charges").log(Status.INFO,
                        "Update button not found to click");
            }
        } catch (Exception e) {
            extentReports.createTest("Click Update Button of Reservation charges").log(Status.FAIL,
                    "Failed to click the Update button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 25)
    public void reserveChargesUpdatePopupText() {
        extractUpdateParckingChargesText();
    }

    @Test(priority = 26)
    public void reserveChargeUpdateStatusOK() {
        parkingChargeStatusOK();
    }

    @Test(priority = 27)
    public void selectParkingChargesTabTwice() {
        parkingChargesTab();
    }

    @Test(priority = 28)
    public void clickVerifyAddReserveBtn() {
        try {
            Thread.sleep(3000);
            WebElement addReserveButton = driver
                    .findElement(By.cssSelector("button#add-reservation-chgs-btn"));
            addReserveButton.click();
            if (addReserveButton.isDisplayed()) {
                extentReports.createTest("Click add reserve Button of Reservation charges").log(Status.PASS,
                        "Successfully clicked the add reserve button ");
            } else {
                extentReports.createTest("Click add reserve Button of Reservation charges").log(Status.INFO,
                        "Add reserve button not found to click");
            }
        } catch (Exception e) {
            extentReports.createTest("Click add reserve Button of Reservation charges").log(Status.FAIL,
                    "Failed to click the add reserve button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 29)
    public void addReservationChargesVehicleType() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//form[@id='addResCHrDetail']//select[@id='vehicleType']")).click();
            Thread.sleep(2000);
            List<WebElement> vehicleType = driver
                    .findElements(By.xpath("//form[@id='addResCHrDetail']//select[@id='vehicleType']/option"));
            vehicleType.get(2).click();
            System.out.println(vehicleType.get(1).getText());

            for (WebElement option : vehicleType) {
                if (option.getText().equalsIgnoreCase("FOUR")) {
                    option.click();
                    break;
                }
            }

            extentReports.createTest("Add Reservation Charges vehicle type").log(Status.PASS,
                    "Successfully selected the desired vehicle type in reservation charges ");
        } catch (Exception e) {
            extentReports.createTest("Add Reservation Charges vehicle type").log(Status.FAIL,
                    "Failed to select the vehicle type in reservation charges. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 30)
    public void addReservationInputCharges() {
        try {
            Thread.sleep(3000);
            WebElement chargesInput = driver.findElement(By.cssSelector("input#rCharges"));
            chargesInput.clear();
            String addReserveCharges = "03";
            chargesInput.sendKeys(addReserveCharges);

            extentReports.createTest("Charges filled ").log(Status.PASS,
                    "Reservation Charges filled successfully : " + addReserveCharges);
        } catch (Exception e) {
            extentReports.createTest("Charges filled").log(Status.FAIL,
                    "Failed to filled Reservation Charges. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 31)
    public void addReservationChargesDateSelection() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//form[@id='addResCHrDetail']//input[@id='siteDatePicker1']")).click();

            Thread.sleep(2000);
            WebElement startDateElement = driver
                    .findElement(By
                            .xpath("//div[@class='drp-calendar left']//td[@class='available' and text()='2']"));
            startDateElement.click();

            Thread.sleep(2000);
            WebElement endDateElement = driver.findElement(
                    By.xpath("//div[@class='drp-calendar left']//td[@class='available' and text()='18']"));
            endDateElement.click();

            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "$('.applyBtn').click();";
            js.executeScript(script);

            extentReports.createTest("Add Reservation Charges selected date ranges").log(Status.PASS,
                    "Reservation Charges dates ranges selected successfully");
        } catch (Exception e) {
            extentReports.createTest("Add Reservation Charges selected date ranges").log(Status.FAIL,
                    "Failed to select Reservation date ranges. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 32)
    public void addReservationChargesDay() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//form[@id='addResCHrDetail']//select[@id='chargeDays']")).click();
            List<WebElement> chargeDays = driver
                    .findElements(By.xpath("//form[@id='addResCHrDetail']//select[@id='chargeDays']/option"));
            chargeDays.get(1).click();

            for (WebElement option : chargeDays) {
                if (option.getText().equalsIgnoreCase("All days")) {
                    option.click();
                    break;
                }
            }

            extentReports.createTest("Add Reservation Charges selected day as").log(Status.PASS,
                    "Successfully selected the duration days :");
        } catch (Exception e) {
            extentReports.createTest("Add Reservation Charges selected day as").log(Status.FAIL,
                    "Failed to select the duration days in Reservation Charges. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 34)
    public void clickVerifySaveReserveBtn() {
        try {
            Thread.sleep(6000);
            WebElement saveReserveButton = driver
                    .findElement(By.xpath("//button[@id='btnSaveRkChrg']"));
            saveReserveButton.click();

            if (saveReserveButton.isDisplayed()) {
                extentReports.createTest("Click save reserve Button of Reservation charges").log(Status.PASS,
                        "Successfully clicked the save reserve button ");
            } else {
                extentReports.createTest("Click save reserve Button of Reservation charges").log(Status.INFO,
                        "Save reserve button not found to click");
            }
        } catch (Exception e) {
            extentReports.createTest("Click save reserve Button of Reservation charges").log(Status.FAIL,
                    "Failed to click the save reserve button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 35)
    public void extractAddReservationCharges() {
        extractUpdateParckingChargesText();
    }

    @Test(priority = 36)
    public void okStatusAddReservation() {
        parkingChargeStatusOK();
    }

    @Test(priority = 37)
    public void popUpCloseAddReservation() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='resvCharModal']/div/div/div[1]/button")).click();
            Thread.sleep(2000);
            extentReports.createTest("In add Reservation charges POP Up").log(Status.PASS,
                    "Successfully closed the pop-up");

            extentReports.createTest("In add Reservation charges POP Up").log(Status.INFO,
                    "Pop-up did not appear after clicking the reserve button.");

        } catch (Exception e) {
            extentReports.createTest("In add Reservation charges POP Up").log(Status.FAIL,
                    "Failed to close the pop-up. Exception: " + e.getCause());
        }
    }

    @Test(priority = 38)
    public void selectParkingChargesTabThrice() {
        parkingChargesView();
        selectReserveCharges();
    }

    @Test(priority = 39)
    public void selectSpecialCharges() {
        try {
            Thread.sleep(5000);
            WebElement selectElement = driver.findElement(By.id("selectParkChrging"));
            Select select = new Select(selectElement);
            select.selectByValue("3");

            extentReports.createTest("Select Special Charges").log(Status.PASS,
                    "Successfully select the Special charges.");
        } catch (Exception e) {
            extentReports.createTest("Select Special Charges").log(Status.FAIL,
                    "Failed to select the Special charges. Exception: " + e.getCause());
        }
    }

    @Test(priority = 40)
    public void splChargesWeekBtn() {
        selectAllWeekButtons();
    }

    @Test(priority = 41)
    public void selectWednesdayButtonTwice() {
        selectWednesdayButton();
    }

    @Test(priority = 42)
    public void clickSpecialChargeAddBtn() {
        try {
            Thread.sleep(2000);
            WebElement addButton = driver.findElement(By.xpath("//*[@id='addSplCharge']"));
            addButton.click();

            extentReports.createTest("Click Add Button in special charges").log(Status.PASS,
                    "Successfully clicked the Add button");
        } catch (Exception e) {
            extentReports.createTest("Click Add Button in special charges").log(Status.FAIL,
                    "Failed to click the Add button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 43)
    public void splChargesStartHour() {
        try {
            Thread.sleep(2000);
            WebElement startHourOption = driver.findElement(By.xpath("//div[@id='addSplModal']//input[@type='time']"));
            startHourOption.click();
            String startHour = "11:00";
            startHourOption.sendKeys(startHour);

            extentReports.createTest("Enter the start hours").log(Status.PASS,
                    "Successfully clicked hour place : " + startHour);
        } catch (Exception e) {
            extentReports.createTest("Enter the Start hours").log(Status.FAIL,
                    "Failed to click the hour. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 44)
    public void splChargesEndHour() {
        try {
            Thread.sleep(2000);
            WebElement endHourOption = driver.findElement(By.xpath("//div[@id='addSplModal']//input[@id='endHour']"));
            endHourOption.click();
            Actions actions = new Actions(driver);
            actions.moveToElement(endHourOption).click().sendKeys("17:00").build().perform();

            extentReports.createTest("Enter the End hours").log(Status.PASS,
                    "Successfully clicked hour place  ");
        } catch (Exception e) {
            extentReports.createTest("Enter the End hours").log(Status.FAIL,
                    "Failed to click the hour. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 45)
    public void splChargesEnterCharges() {
        try {
            Thread.sleep(2000);
            WebElement chargesOption = driver.findElement(By.xpath("//div[@id='addSplModal']//input[@id='charges']"));
            chargesOption.click();
            String specialCharges = "10";
            chargesOption.sendKeys(specialCharges);

            extentReports.createTest("Enter the Charges").log(Status.PASS,
                    "Successfully enterd charges : " + specialCharges);
        } catch (Exception e) {
            extentReports.createTest("Enter the Charges").log(Status.FAIL,
                    "Failed to enter the charges. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 46)
    public void splChargesEnterDisCharges() {
        try {
            Thread.sleep(2000);
            WebElement chargesOption = driver.findElement(By.xpath("//div[@id='addSplModal']//input[@id='discount']"));
            chargesOption.click();
            String discountCharges = "10";
            chargesOption.sendKeys(discountCharges);

            extentReports.createTest("Enter the discount").log(Status.PASS,
                    "Successfully enterd discount : " + discountCharges);
        } catch (Exception e) {
            extentReports.createTest("Enter the discount").log(Status.FAIL,
                    "Failed to enter the discount. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 47)
    public void splChargeCardDisCharges() {
        try {
            Thread.sleep(2000);
            WebElement cardDiscountchargesOption = driver
                    .findElement(By.xpath("//div[@id='addSplModal']//input[@id='cardDiscount']"));
            cardDiscountchargesOption.click();
            String cardDiscount = "20";
            cardDiscountchargesOption.sendKeys(cardDiscount);

            extentReports.createTest("Enter the cardDiscount").log(Status.PASS,
                    "Successfully enterd cardDiscount : " + cardDiscount);
        } catch (Exception e) {
            extentReports.createTest("Enter the cardDiscount").log(Status.FAIL,
                    "Failed to enter the cardDiscount. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 48)
    public void splChargeUPIDisCharges() {
        try {
            Thread.sleep(2000);
            WebElement upiDiscountchargesOption = driver
                    .findElement(By.xpath("//div[@id='addSplModal']//input[@id='upiDiscount']"));
            upiDiscountchargesOption.click();
            String upiDiscount = "10";
            upiDiscountchargesOption.sendKeys(upiDiscount);

            extentReports.createTest("Enter the upiDiscount").log(Status.PASS,
                    "Successfully enterd upiDiscount : " + upiDiscount);
        } catch (Exception e) {
            extentReports.createTest("Enter the upiDiscount").log(Status.FAIL,
                    "Failed to enter the upiDiscount. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 49)
    public void addSplChargesSubmitBtn() {
        try {
            Thread.sleep(2000);
            WebElement splChargeButtun = driver
                    .findElement(By.xpath("//div[@id='addSplModal']//button[@id='submitAddSplCharges']"));
            splChargeButtun.click();

            extentReports.createTest("Special Charges Submit button").log(Status.PASS,
                    "Successfully clicked Special Charges Submit button");
        } catch (Exception e) {
            extentReports.createTest("Special Charges Submit button").log(Status.FAIL,
                    "Failed to click Special Charges Submit button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 50)
    public void addSpecialChargesPopUptext() {
        extractPopupTextAddVehicle();
    }

    @Test(priority = 51)
    public void addSpecialChargesStatusOK() {
        statusOKAddVehicle();
    }

    @Test(priority = 52)
    public void specialChargesView() {
        selectReserveCharges();
        selectSpecialCharges();
    }

    @Test(priority = 53)
    public void selectWednesdayButtonThrice() {
        selectWednesdayButton();
    }

    @Test(priority = 54)
    public void deleteSpecialCharges() {
        try {
            Thread.sleep(5000);
            WebElement specialChargeDeleteElement = driver
                    .findElement(By.xpath("//table[@id='DataTables_Table_3']/tbody/tr/td[9]/button"));
            specialChargeDeleteElement.click();

            extentReports.createTest("Select delete button in special charges").log(Status.PASS,
                    "Successfully select delete button in special charges.");
            extentReports.createTest("No special charges").log(Status.WARNING,
                    "There is no special charges to delete .");
        } catch (Exception e) {
            extentReports.createTest("Select delete button in special charges").log(Status.FAIL,
                    "Failed to select delete button in special charges. Exception: " + e.getCause());
        }
    }

    @Test(priority = 55)
    public void deleteSpecialChargesPopUptext() {
        extractPopupTextAddVehicle();
    }

    @Test(priority = 56)
    public void deleteSpecialChargesStatusOK() {
        statusOKAddVehicle();
    }

    @Test(priority = 57)
    public void parkingChargesViewTwice() {
        parkingChargesView();
    }

    @Test(priority = 58)
    public void selectSpecialChargesTwice() {
        selectSpecialCharges();
    }

    @Test(priority = 73)
    public void parkingPassesTab() {
        try {
            driver.navigate().refresh();
            Thread.sleep(3000);
            driver.findElement(By.id("pop6-tab")).click();

            extentReports.createTest("POS Management Parking Passes").log(Status.PASS,
                    "Successfully Open parking Parking Passes ");

        } catch (Exception e) {
            extentReports.createTest("POS Management Parking Passes").log(Status.FAIL,
                    "Failed to parking Parking Passes . Exception: " + e.getCause());
        }
    }

    @Test(priority = 74)
    public void clickParkingPassesEditBtn() {
        try {
            Thread.sleep(3000);
            // WebElement editButton = driver.findElement(By.cssSelector(
            // "button.btn.btn-outline-info.btn-sm.editSitePass[data-toggle='modal'][data-target='#editSitePassModal'][pass-id='145'][site-id='54']"));
            WebElement editButton = driver.findElement(By.xpath(
                    "//table[@id='passTable']/tbody/tr[1]/td[5]/button"));
            editButton.click();

            extentReports.createTest("Click Edit Button Parking Passes").log(Status.PASS,
                    "Edit button clicked successfully");
        } catch (Exception e) {
            extentReports.createTest("Click Edit Button Parking Passes").log(Status.FAIL,
                    "Failed to click the edit button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 75)
    public void setPassPrice() {
        try {
            Thread.sleep(2000);
            WebElement passPriceInput = driver.findElement(By.id("passPrice"));
            passPriceInput.clear();
            String passPrice = "30";
            passPriceInput.sendKeys(passPrice);

            extentReports.createTest("Set Pass Price Parking Passes ").log(Status.PASS,
                    "Pass Price set successfully : " + passPrice);
        } catch (Exception e) {
            extentReports.createTest("Set Pass Price Parking Passes").log(Status.FAIL,
                    "Failed to set the Pass Price. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 76)
    public void clickUpdatePassButton() {
        try {
            Thread.sleep(2000);
            WebElement updatePassButton = driver.findElement(By.id("btnSavePassDtl"));
            updatePassButton.click();

            extentReports.createTest("Click Update Pass Button in Parking Passes").log(Status.PASS,
                    "Update Pass button clicked successfully");
        } catch (Exception e) {
            extentReports.createTest("Click Update Pass Button in Parking Passes").log(Status.FAIL,
                    "Failed to click the Update Pass button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 77)
    public void updatePassPopupText() {
        extractUpdateParckingChargesText();
    }

    @Test(priority = 78)
    public void updatePassstatusOK() {
        parkingChargeStatusOK();
    }

    @Test(priority = 79)
    public void selectOptionFromDropdown() {
        try {
            Thread.sleep(3000);
            WebElement selectElement = driver.findElement(By.name("passTable_length"));
            Select select = new Select(selectElement);
            select.selectByValue("25");

            extentReports.createTest("Select Option from Dropdown Parking Passes").log(Status.PASS,
                    "Option '25' selected successfully");
        } catch (Exception e) {
            extentReports.createTest("Select Option from Dropdown Parking Passes").log(Status.FAIL,
                    "Failed to select the option. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 80)
    public void performPassesTabSearch() {
        try {
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.cssSelector("#passTable_filter input"));
            searchInput.clear();
            String searchTwo = "TWO";
            searchInput.sendKeys(searchTwo);

            extentReports.createTest("Perform Search Parking Passes").log(Status.PASS,
                    "Search query entered successfully : " + searchTwo);
        } catch (Exception e) {
            extentReports.createTest("Perform Search Parking Passes").log(Status.FAIL,
                    "Failed to enter the search query. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 81)
    public void attenderShiftsTab() {

        try {
            Thread.sleep(3000);
            driver.findElement(By.id("pop4-tab")).click();

            extentReports.createTest("POS Management Attender Shifts").log(Status.PASS,
                    "Successfully Open parking Attender Shifts");

        } catch (Exception e) {
            extentReports.createTest("POS Management Attender Shifts").log(Status.FAIL,
                    "Failed to parking Attender Shifts . Exception: " + e.getCause());
        }
    }

    @Test(priority = 84)
    public void openModalDialog() {
        try {
            Thread.sleep(3000);
            WebElement editButton = driver.findElement(By.cssSelector(".editAtndrShift"));
            editButton.click();

            extentReports.createTest("Open Modal Dialog Attender Shifts").log(Status.PASS,
                    "Clicked the 'Edit' button to open the modal dialog");
        } catch (Exception e) {
            extentReports.createTest("Open Modal Dialog Attender Shifts").log(Status.FAIL,
                    "Failed to click the 'Edit' button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 85)
    public void updateShifts() {
        try {
            Thread.sleep(3000);
            WebElement editAtndrShiftForm = driver.findElement(By.id("editAtndrShift"));

            WebElement startHourInput = editAtndrShiftForm.findElement(By.id("atndrStartHour"));
            startHourInput.clear();
            String startHour = "08:00 AM";
            startHourInput.sendKeys(startHour);

            WebElement endHourInput = editAtndrShiftForm.findElement(By.id("atndrEndHour"));
            endHourInput.clear();
            String endHour = "02:00 PM";
            endHourInput.sendKeys(endHour);

            WebElement submitButton = editAtndrShiftForm.findElement(By.id("btnEditPkShfts"));
            submitButton.click();

            extentReports.createTest("Update Shifts").log(Status.PASS, "Shifts updated successfully : " + startHour
                    + endHour);
        } catch (Exception e) {
            extentReports.createTest("Update Shifts").log(Status.FAIL,
                    "Failed to update shifts. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 86)
    public void updateShiftPopupText() throws InterruptedException {
        Thread.sleep(2000);
        extractUpdateParckingChargesText();
    }

    @Test(priority = 87)
    public void updateShiftstatusOK() throws InterruptedException {
        Thread.sleep(2000);
        parkingChargeStatusOK();
    }

    @Test(priority = 88)
    public void clickAddAttenderShiftButton() {
        try {
            Thread.sleep(3000);
            WebElement addButton = driver.findElement(By.cssSelector("button.btn-assign.add-shift"));
            addButton.click();

            extentReports.createTest("Click Add Attender Shift Button").log(Status.PASS,
                    "Clicked the Add Attender Shift button");
        } catch (Exception e) {
            extentReports.createTest("Click Add Attender Shift Button").log(Status.FAIL,
                    "Failed to click the button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 89)
    public void addAttenderShift() {
        try {
            Thread.sleep(3000);
            List<WebElement> elements = driver.findElements(By.id("selectWeeks"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }

            WebElement eveningShift = driver
                    .findElement(By.xpath("//select[@id='selectWeeks']/option[@value='EVENING']"));
            eveningShift.click();

            Thread.sleep(3000);
            WebElement startHourInput = driver.findElement(By.id("atndrStartHour"));
            String startHourValue = "08:00 AM";
            startHourInput.sendKeys(startHourValue);

            Thread.sleep(3000);
            WebElement endHourInput = driver.findElement(By.id("atndrEndHour"));
            String endHourValue = "04:00 PM";
            endHourInput.sendKeys(endHourValue);

            String extentReportsReportMessage = "Start Hour: " + startHourValue + ", End Hour: " + endHourValue;

            WebElement saveButton = driver.findElement(By.id("btnSavePkShfts"));
            saveButton.click();
            Thread.sleep(3000);

            extentReports.createTest("Add Attender Shifts").log(Status.PASS,
                    "Successfully added Attender Shifts. " + extentReportsReportMessage);
        } catch (Exception e) {
            extentReports.createTest("Add Attender Shifts").log(Status.FAIL,
                    "Failed to add Attender Shifts. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 90)
    public void addShiftPopupText() {
        extractUpdateParckingChargesText();
    }

    @Test(priority = 91)
    public void addShiftstatusOK() {
        parkingChargeStatusOK();
    }

    @Test(priority = 92)
    public void addShiftPopUPClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='attenderShiftModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In Attender Shifts POP Up of a add").log(Status.PASS,
                    "Successfully closed the pop up in Attender Shifts");
        } catch (Exception e) {
            extentReports.createTest("In Attender Shifts POP Up of a add").log(Status.FAIL,
                    "Failed to close the pop up in Attender Shifts tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 93)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("POS Management LOGOUT ").log(Status.PASS,
                    "Successfully LOGOUT from the Portal");
        } catch (Exception e) {
            extentReports.createTest("POS Management LOGOUT").log(Status.FAIL,
                    "Failed to LOGOUT. Exception: " + e.getCause());
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