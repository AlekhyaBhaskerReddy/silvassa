package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
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

public class AlarmsNotifications extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverConfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/AlarmsNotifications.html");
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
    public void alarmsNotificationsPage() {
        try {
            Thread.sleep(2000);
            WebElement alarmsNotificationsLink = driver.findElement(By.className("ml-pg"));
            if (alarmsNotificationsLink.isDisplayed()) {
                alarmsNotificationsLink.click();
                extentReports.createTest("Alarms and Notifications Page  ").log(Status.PASS,
                        "Successfully Open Alarms and Notifications Page ");
            } else {
                extentReports.createTest("Alarms and Notifications Page").log(Status.WARNING,
                        "Alarms and Notifications Page not found");
                Assert.fail("Alarms and Notifications Page not found");
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications Page   ").log(Status.FAIL,
                    "Failed to Open the Alarms and Notifications Page  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void clickManualAlert() {
        try {
            Thread.sleep(5000);
            List<WebElement> elements = driver.findElements(By.id("pills-manual-tab"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            driver.findElement(By
                    .id("pills-manual-tab"))
                    .click();
            extentReports.createTest("Alarms and Notifications select the manual alert ")
                    .log(Status.PASS, "Successfully select the manual alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications select the manual alert").log(Status.FAIL,
                    "Failed to select the manual alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 5)
    public void createManualTask() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='page-name']/div/div/div[2]/div/div[2]/button")).click();
            extentReports.createTest("Alarms and Notifications select the manual task ")
                    .log(Status.PASS, "Successfully select the manual task in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications select the manual task").log(Status.FAIL,
                    "Failed to select the manual task in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 6)
    public void createMsgSelectSite() {
        try {
            WebElement dropdown = driver.findElement(By.id("userSiteListDn"));
            Select select = new Select(dropdown);
            int indexToSelect = 2;
            select.selectByIndex(indexToSelect);
            String selectedSite = select.getFirstSelectedOption().getText();

            extentReports.createTest("Select Site from Dropdown in manual task").log(Status.PASS,
                    "Selected Site manual task: " + selectedSite);
        } catch (Exception e) {
            extentReports.createTest("Select Site from Dropdown in manual task").log(Status.FAIL,
                    "Failed to select Site from Dropdown in manual task. Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void selectManualServiceUser() {
        try {
            List<WebElement> elements = driver.findElements(By.id("optName"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            Thread.sleep(3000);

            WebElement selectElement = driver.findElement(By.id("manualoptName"));
            Select select = new Select(selectElement);
            int indexToSelect = 2;
            select.selectByIndex(indexToSelect);

            extentReports.createTest("Select User from Dropdown in Manual task").log(Status.PASS,
                    "Successfully selected the user with value '25'");

        } catch (Exception e) {
            extentReports.createTest("Select User from Dropdown in Manual Task").log(Status.FAIL,
                    "Failed to select the user from the dropdown. Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void selectAlertType() {
        try {
            WebElement alertTypeDropdown = driver.findElement(By.id("alertType"));
            Select select = new Select(alertTypeDropdown);
            int indexToSelect = 2;
            select.selectByIndex(indexToSelect);

            extentReports.createTest("Select Alert Type in Manual Task").log(Status.PASS,
                    "Successfully selected alert type: ");
        } catch (Exception e) {
            extentReports.createTest("Select Alert Type in Manual Task").log(Status.FAIL,
                    "Failed to select alert type. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void alertDescription() {
        try {
            WebElement messageTextarea = driver.findElement(By.id("alertDesc"));
            messageTextarea.clear();
            String textToEnter = "Locate the site ";
            messageTextarea.sendKeys(textToEnter);

            extentReports.createTest("Alert Type in Manual Task description").log(Status.PASS,
                    "Successfully entering description " + "textToEnter");

        } catch (Exception e) {
            extentReports.createTest("Alert Type in Manual Task").log(Status.FAIL,
                    "Failed to enter the description . Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void manualTaskSubmit() {
        try {
            Thread.sleep(2000);
            WebElement taskButton = driver.findElement(By.id("btnManualAlert"));
            taskButton.click();
            extentReports.createTest("In task details click the submit button in Manul task in manual alerts tab").log(
                    Status.PASS,
                    "Successfully click the submit button ");
        } catch (Exception e) {
            extentReports.createTest("In task details click the submit button in Manual task manual alerts tab").log(
                    Status.FAIL,
                    "Failed to click the submit button. Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 11)
    public void manualTaskAlertStatus() {
        try {
            Thread.sleep(3000);

            String title = driver.findElement(By.id("swal2-title")).getText();
            String resultDescription = driver.findElement(By.id("swal2-content")).getText();

            System.out.println("Task created manual task in manual alerts tab: " + resultDescription);

            Thread.sleep(3000);

            if (title.equals("Error")) {
                extentReports.createTest("After creating the manual task in manual alerts tab").log(Status.FAIL,
                        "Failed to creating the task. Status: " + title + ", Description: " + resultDescription);
            } else {
                extentReports.createTest("After creating the manual task in manual alerts tab").log(Status.PASS,
                        "Task creating successfully: " + resultDescription);
            }
        } catch (Exception e) {
            extentReports.createTest("After creating the manual task in manual alerts tab").log(Status.FAIL,
                    "Failed to creating the task. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 12)
    public void clickManualAlertOKButton() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            okButton.click();
            extentReports.createTest("After submit the status of a task user of manual task in manual alerts tab").log(
                    Status.PASS,
                    "Clicked the 'OK' button in SweetAlert2 pop-up.");
        } catch (Exception e) {
            extentReports.createTest("After submit the status of a task user of manula task in manual alerts tab").log(
                    Status.FAIL,
                    "Failed to click the 'OK' button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void manualTaskCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//*[@id='alertsPageManualAlertTable_wrapper']/div[1]/button[1]")))
                    .click();

            extentReports.createTest("In Alarms and Notifications device manula alerts - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for device alerts");
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts manual - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for device alerts. File not found.");
        }
    }

    @Test(priority = 14)
    public void manualTaskPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//*[@id='alertsPageManualAlertTable_wrapper']/div[1]/button[2]"));

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
                extentReports.createTest("In Alarms and Notifications device alerts manula- PDF").log(Status.PASS,
                        "Successfully downloaded the PDF file in  Alarms and Notifications");
            }
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts manual - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    @Test(priority = 15)
    public void clickOverTime() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("pills-overtime-tab")).click();
            extentReports.createTest("Alarms and Notifications select the overtime alert ")
                    .log(Status.PASS, "Successfully select the overtime vehicle alerts in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications select the overtime alert vehicle alerts").log(
                    Status.FAIL,
                    "Failed to select the overtime vehcile alerts in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 16)
    public void selectSiteOverTimeVehicle() {
        try {
            driver.findElement(By.id("alert_site_name"));
            driver.findElement(By.id("btn-site-view")).click();
            Thread.sleep(3000);
            List<WebElement> sitesList = driver.findElements(By.className("ui-menu-item-wrapper"));
            WebElement site = sitesList.get(1);
            site.click();
            System.out.println(sitesList.size());

            extentReports.createTest("Select Site from Dropdown").log(Status.PASS,
                    "Successfully selected the site from the dropdown :");

        } catch (Exception e) {
            extentReports.createTest("Select Site from Dropdown").log(Status.FAIL,
                    "Failed to select the site from the dropdown. Exception: " + e.getCause());
        }
    }

    @Test(priority = 17)
    public void downloadOTCSVFiles() {
        try {
            WebElement csvButton = driver
                    .findElement(By.xpath("//*[@id='alertsPageVechOvertimeTable_wrapper']/div[1]/button[1]"));

            csvButton.click();

            extentReports.createTest("Download CSV  Files in vehicle OT").log(Status.PASS,
                    "Successfully downloaded CSV files in vehicle overtime.");
        } catch (Exception e) {
            extentReports.createTest("Download CSV Files in vehicle OT").log(Status.FAIL,
                    "Failed to download CSV files in vehicle overtime. Exception: " + e.getCause());
        }
    }

    @Test(priority = 18)
    public void downloadOTPDFFiles() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//*[@id='alertsPageVechOvertimeTable_wrapper']/div[1]/button[2]"));

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

                extentReports.createTest("Download PDF Files in vehicle overtime").log(Status.PASS,
                        "Successfully downloaded PDF files in vehicle overtime.");
            }
        } catch (Exception e) {
            extentReports.createTest("Download PDF Files in vehicle overtime").log(Status.FAIL,
                    "Failed to download PDF files in vehicle overtime. Exception: " + e.getCause());
        }
    }

    @Test(priority = 19)
    public void selectOTFourWheeler() {
        try {
            Thread.sleep(3000);
            WebElement fourWheelerRadio = driver
                    .findElement(By.xpath("//div[@id='pills-overtime-alert']/div[1]/div/label[2]"));
            fourWheelerRadio.click();
            extentReports.createTest("Select Four Wheeler").log(Status.PASS,
                    "Successfully selected Four Wheeler in Vehicle Overtime");

        } catch (Exception e) {
            extentReports.createTest("Select Four Wheeler Types").log(Status.FAIL,
                    "Failed to select Four wheeler types in vehicle overtime. Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void selectOTTwoWheeler() {
        try {
            WebElement twoWheelerRadio = driver
                    .findElement(By.xpath("//div[@id='pills-overtime-alert']/div[1]/div/label[1]"));

            twoWheelerRadio.click();
            extentReports.createTest("Select Two Wheeler").log(Status.PASS,
                    "Successfully selected Two Wheeler in vehicle overtime");

        } catch (Exception e) {
            extentReports.createTest("Select Two Wheeler Types").log(Status.FAIL,
                    "Failed to select wheeler types in vehicle overtime. Exception: " + e.getCause());
        }
    }

    @Test(priority = 21)
    public void clickTransactionAlert() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pills-transaction-tab")).click();
            extentReports.createTest("Alarms and Notifications select the transaction alert ")
                    .log(Status.PASS, "Successfully select the transaction alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications select the transaction alert").log(Status.FAIL,
                    "Failed to select the transaction alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 22)
    public void trasnactionCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//*[@id='alertsPageTransactionAlertsTable_wrapper']/div[1]/button[1]")))
                    .click();

            extentReports.createTest("In Alarms and Notifications transaction alert - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for transaction alerts");
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications transaction alert - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for transaction alerts. File not found.");
        }
    }

    @Test(priority = 23)
    public void transactionPdf() {
        try {

            WebElement pdfButton = driver
                    .findElement(By.xpath("//*[@id='alertsPageTransactionAlertsTable_wrapper']/div[1]/button[2]"));

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
                extentReports.createTest("In Alarms and Notifications transaction alerts - PDF").log(Status.PASS,
                        "Successfully downloaded the PDF file in  Alarms and Notifications");
            }
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications transaction alerts - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    @Test(priority = 24)
    public void clickPOSAlert() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pills-pos-tab")).click();
            extentReports.createTest("Alarms and Notifications select the POS alert ")
                    .log(Status.PASS, "Successfully select the POS alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications select the POS alert").log(Status.FAIL,
                    "Failed to select the POS alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 25)
    public void posAlertsDatesEnabled() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dayRangeFilterPos']/label[1]")))
                    .click();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(3));

            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dayRangeFilterPos']/label[2]")))
                    .click();
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dayRangeFilterPos']/label[3]")))
                    .click();

            extentReports.createTest("Alarms&Notification POS alerts dates enabled").log(Status.PASS,
                    "Successfully selecting the da/week/month");
        } catch (Exception e) {
            extentReports.createTest("Alarms&Notification POS alerts dates enabled").log(Status.FAIL,
                    "Failed to select the day/week/month. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 26)
    public void posCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//*[@id='alertsPosDetailsTable_wrapper']/div[1]/button[1]")))
                    .click();

            extentReports.createTest("In Alarms and Notifications POS alerts - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for POS alerts");
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications  - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for POS alerts. File not found.");
        }
    }

    @Test(priority = 27)
    public void posPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//*[@id='alertsPosDetailsTable_wrapper']/div[1]/button[2]"));

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
                extentReports.createTest("In Alarms and Notifications POS alerts - PDF").log(Status.PASS,
                        "Successfully downloaded the PDF file in  Alarms and Notifications");
            }
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications POS alerts - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    @Test(priority = 28)
    public void clickDeviceAlert() {
        try {
            Thread.sleep(5000);
            driver.findElement(By
                    .id("pills-device-tab"))
                    .click();
            extentReports.createTest("Alarms and Notifications select the device alert tab ")
                    .log(Status.PASS, "Successfully select the device alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Alarms and Notifications select the device alert tab").log(Status.FAIL,
                    "Failed to select the device alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    @Test(priority = 29)
    public void devcieAlertsCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageDeviceAlertTable_wrapper']/div[1]/button[1]")))
                    .click();

            extentReports.createTest("In Alarms and Notifications device alerts tab-  CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for device alerts");
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts tab - CSV").log(Status.FAIL,
                    "Failed to download the CSV file for device alerts. File not found.");
        }
    }

    @Test(priority = 30)
    public void deviceAlertsPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='alertsPageDeviceAlertTable_wrapper']/div[1]/button[2]"));

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
                extentReports.createTest("In Alarms and Notifications device alerts tab - PDF").log(Status.PASS,
                        "Successfully downloaded the PDF file in Alarms and Notifications");
            }
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts tab - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    @Test(priority = 31)
    public void deviceAlertsNextPage() {
        try {
            Thread.sleep(3000);
            WebElement nextButton = driver.findElement(By.id("alertsPageDeviceAlertTable_next"));

            while (!nextButton.getAttribute("class").contains("disabled")) {
                nextButton.click();
                Thread.sleep(3000);
                nextButton = driver.findElement(By.id("alertsPageDeviceAlertTable_next"));
            }
            WebElement previousButton = driver.findElement(By.id("alertsPageDeviceAlertTable_previous"));
            previousButton.click();

            extentReports.createTest("In Alarms and Notifications device alerts").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    @Test(priority = 32)
    public void editTaskDeviceAlerts() {
        try {
            Thread.sleep(2000);
            WebElement editButton = driver.findElement(By.cssSelector("a.btnAsgnTsk[title='Edit']"));
            editButton.click();
            extentReports.createTest("Devcie alerts to edit the task details in device alerts tab ").log(Status.PASS,
                    "Successfully click the task edit details button ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Device alerts to edit the task details in device alert tab ").log(Status.FAIL,
                    "Failed to click the task edit details button . Exception: " + e.getCause());
        }
    }

    @Test(priority = 33)
    public void selectServiceUser() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("optName")));
            Select select = new Select(selectElement);
            select.selectByValue("20");
            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isEnabled" + selectElement.isEnabled());
            System.out.println("selectElement.isSelected" + selectElement.isSelected());

            extentReports.createTest("In task details select the user in device alert tab ").log(Status.PASS,
                    "Successfully selecting the service user ");
        } catch (Exception e) {
            extentReports.createTest("In task details select the user in device alert tab ").log(Status.FAIL,
                    "Failed to selecting the service user . Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 34)
    public void taskSubmitBtn() {
        try {
            Thread.sleep(2000);
            WebElement taskButton = driver.findElement(By.id("btnTaskEdit"));
            taskButton.click();
            extentReports.createTest("In task details click the submit button in device alert tab").log(Status.PASS,
                    "Successfully click the submit button ");
        } catch (Exception e) {
            extentReports.createTest("In task details click the submit button in device alert tab").log(Status.FAIL,
                    "Failed to click the submit button. Exception: "
                            + e.getCause());
        }
    }

    @Test(priority = 35)
    public void taskAlertStatus() {
        try {
            Thread.sleep(3000);

            String title = driver.findElement(By.id("swal2-title")).getText();
            String resultDescription = driver.findElement(By.id("swal2-content")).getText();

            System.out.println("Task assigned status in Device Details: " + resultDescription);

            Thread.sleep(3000);

            if (title.equals("Success")) {
                extentReports.createTest("After completing the task assign of an alert").log(Status.PASS,
                        "Task completed successfully: " + resultDescription);
            } else {
                extentReports.createTest("After completing the task assign of an alert").log(Status.FAIL,
                        "Failed to complete the task. Status: " + title + ", Description: " + resultDescription);
            }
        } catch (Exception e) {
            extentReports.createTest("After completing the task assign of an alert").log(Status.FAIL,
                    "Failed to complete the task. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 36)
    public void clickSweetAlertOKButton() {
        clickManualAlertOKButton();
    }

    @Test(priority = 37)
    public void taskPopUPClose() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.className("close")).click();
            extentReports.createTest("In alarms and notification task POP Up").log(Status.PASS,
                    "Successfully closed the pop up in device alerts");
        } catch (Exception e) {
            extentReports.createTest("In alarms and notification POP Up").log(Status.FAIL,
                    "Failed to closed the pop up in device alerts. Exception: " + e.getCause());
        }
    }

    @Test(priority = 38)
    public void clickTaskViewButton() {
        try {
            Thread.sleep(3000);
            WebElement viewButton = driver
                    .findElement(By.xpath("//*[@id='alertsPageDeviceAlertTable']/tbody/tr[1]/td[9]/a[2]"));

            viewButton.click();

            extentReports.createTest("In alarms and notification task view in device alert tab").log(Status.PASS,
                    "Clicked the 'View' button to open the modal.");

        } catch (Exception e) {
            extentReports.createTest("In alarms and notification task view in device alert tab").log(Status.FAIL,
                    "Failed to click the 'View' button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 39)
    public void viewTaskCsv() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("deviceAlertCsvBtn"))
                    .click();

            extentReports.createTest("In Alarms and Notifications device details alerts - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for device alerts of view task");
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device details alerts - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for device alerts of view task. File not found.");
        }
    }

    @Test(priority = 40)
    public void viewTaskPdf() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.id("deviceAlertPdfBtn"));

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
                extentReports.createTest("In Alarms and Notifications device details alerts - PDF").log(Status.PASS,
                        "Successfully downloaded the PDF file for device alerts of view task");
            }
        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device details alerts -PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications for device alerts of view task. File not found.");
        }

    }

    @Test(priority = 41)
    public void logAlertDetails() {
        try {
            WebElement detailsTable = driver.findElement(By.cssSelector("tbody"));
            List<WebElement> tdElements = detailsTable.findElements(By.cssSelector("td"));

            for (int i = 0; i < tdElements.size(); i += 2) {
                String label = tdElements.get(i).getText();
                String value = tdElements.get(i + 1).getText();

                extentReports.createTest("In Alarms and Notifications device alerts details").log(Status.PASS,
                        "Logged the alert details.").log(Status.INFO,
                                label + ": " + value);
            }

        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts details").log(
                    Status.FAIL,
                    "Failed to log the alert details. Exception: " + e.getCause());
        }
    }

    @Test(priority = 42)
    public void clickCompleteStatusButton() {
        try {
            Thread.sleep(3000);
            WebElement completeStatusButton = driver.findElement(By.cssSelector("input[alerttype='Devicealert']"));

            completeStatusButton.click();

            extentReports.createTest("In Alarms and Notifications device alerts view log details").log(Status.PASS,
                    "Clicked the 'Complete Status' button.");

        } catch (Exception e) {
            extentReports.createTest("In Alarms and Notifications device alerts view log details").log(Status.FAIL,
                    "Failed to click the 'Complete Status' button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 43)
    public void completedStatusSweetAlert() {
        try {
            Thread.sleep(3000);

            String title = driver.findElement(By.id("swal2-title")).getText();
            String resultDescription = driver.findElement(By.id("swal2-content")).getText();

            System.out.println("Task Completed status in Device Details: " + resultDescription);

            Thread.sleep(3000);

            if (title.equals("Success")) {
                extentReports.createTest("After completing the pop-up of an alert").log(Status.PASS,
                        "Task completed successfully: " + resultDescription);
            } else {
                extentReports.createTest("After completing the pop-up of an alert").log(Status.FAIL,
                        "Failed to complete the task. Status: " + title + ", Description: " + resultDescription);
            }
        } catch (Exception e) {
            extentReports.createTest("After completing the pop-up of an alert").log(Status.FAIL,
                    "Failed to complete the task. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 44)
    public void clickCompleteSweetAlertOKButton() {
        clickManualAlertOKButton();
    }

    @Test(priority = 45)
    public void deviceDetailsPopUPClose() {
        try {
            Thread.sleep(4000);
            driver.findElement(By.xpath("//div[@id='taskViewModal']/div/div/div[1]/div/button[3]")).click();
            extentReports
                    .createTest("In alarms and notification task complete device details pop Up in device alert tab")
                    .log(
                            Status.PASS,
                            "Successfully closed the pop up in device details in device alerts");
        } catch (Exception e) {
            extentReports.createTest("In alarms and notification complete device details pop Up in device alert tab")
                    .log(
                            Status.FAIL,
                            "Failed to closed the pop up in device details in device alerts. Exception: "
                                    + e.getCause());
        }
    }

    @Test(priority = 46)
    public void unAssignAlert() {
        try {
            Thread.sleep(2000);
            WebElement unassignAlertButton = driver
                    .findElement(By.cssSelector("a.btnUnassignDevice[title='Unassign Alert']"));
            unassignAlertButton.click();
            extentReports.createTest("Devcie alerts to click the unassign button").log(Status.PASS,
                    "Successfully click the unassign button ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extentReports.createTest("Devcie alerts to click the unassign button").log(Status.FAIL,
                    "Failed to click the unassign button . Exception: " + e.getCause());
        }
    }

    @Test(priority = 47)
    public void unAssignAlertStatus() {
        try {
            Thread.sleep(3000);

            String title = driver.findElement(By.xpath("//*[@id='swal2-title']")).getText();
            String resultDescription = driver.findElement(By.xpath("//*[@id='swal2-content']")).getText();

            if ("Error Title".equals(title)) {
                extentReports.createTest("After submit to unassign the alert and status in device alert tab").log(
                        Status.FAIL,
                        "Failed to assign the task. Error: " + resultDescription);
            } else {
                extentReports.createTest("After submit to unassign the alert and status in device alert tab").log(
                        Status.PASS,
                        "Successfully assigned the task. Status: " + resultDescription);
            }

            Thread.sleep(3000);
        } catch (Exception e) {
            extentReports.createTest("After submit to unassign the alert and status in device alert tab").log(
                    Status.FAIL,
                    "Failed to unassign the task. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 48)
    public void unAssignSweetAlertOKButton() {
        clickManualAlertOKButton();
    }

    @Test(priority = 49)
    public void validateActionStatus() {
        WebElement table = driver.findElement(By.id("alertsPageDeviceAlertTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extentReports.createTest("In Alerts table Status").log(Status.FAIL, "No data available");
            return;
        }

        boolean isAnycompletedstatus = false;

        for (WebElement row : rows) {
            WebElement statusCell = row.findElements(By.tagName("td")).get(5);
            String status = statusCell.getText();

            if (status.equalsIgnoreCase("completed")) {
                isAnycompletedstatus = true;
                break;
            }
        }

        if (isAnycompletedstatus) {
            extentReports.createTest("In Alarms and Notifications status").log(Status.FAIL,
                    "Found a completed status alert");

        } else {
            extentReports.createTest("In Alarms and Notifications status").log(Status.PASS,
                    "No completed status alert found");
        }
    }

    @Test(priority = 50)
    public void logout() {
        try {
            Thread.sleep(5000);

            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest(" Alarms and Notifications LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest(" Alarms and Notifications LogOut").log(Status.FAIL,
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