package com.selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

class ServerConfigAlarms1 {
    String userName;
    String password;
    Boolean production;

    public ServerConfigAlarms1(String userName, String password, Boolean production) {
        this.userName = userName;
        this.password = password;
        this.production = production;
    }

    public String getUrl() {
        if (production) {
            return "https://parking-jhansi.eparke.in/SmartCity/ui/admin/";
            // return "https://admin-parking.pcmcsmartsarathi.org/SmartCity/ui/admin/";
        }
        return "https://jhansi.eparke.in/SmartCity/ui/admin/";
        // return "https://pcmc-parking.eparke.in/SmartCity/ui/admin/";
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

public class AlarmsNotifications1 {
    WebDriver driver;
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    App app = new App();

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

    public void login() {
        ServerConfigAlarms1 s = new ServerConfigAlarms1("testuser",
                "welcome@123", false);
        // ServerConfigAlarms s = new ServerConfigAlarms("pcmcadmin",
        // "@Pcmc#Admin#@", false);
        // ServerConfigAlarms s = new ServerConfigAlarms("guestuser",
        // "welcome@1", true);
        // ServerConfigSiteReports s = new
        // ServerConfigSiteReports("pcmcadmin","?ca2b_vr48", true);
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

    public void revenueDashboardOpen() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.className("db-pg")).click();
            extent.createTest("Dashboard ").log(Status.PASS, "Successfully Open Dashboard Revenue tab");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Dashboard").log(Status.FAIL, "Failed to Open the dashboard. Exception: " + e.getCause());
        }
    }

    public void alarmsNotificationsPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("ml-pg")).click();
            extent.createTest("Alarms and Notifications Page  ").log(Status.PASS,
                    "Successfully Open Alarms and Notifications Page ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications Page   ").log(Status.FAIL,
                    "Failed to Open the Alarms and Notifications Page  . Exception: " + e.getCause());
        }
    }

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
            extent.createTest("Alarms and Notifications select the manual alert ")
                    .log(Status.PASS, "Successfully select the manual alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications select the manual alert").log(Status.FAIL,
                    "Failed to select the manual alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    public void createManualTask() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='page-name']/div/div/div[2]/div/div[2]/button")).click();
            extent.createTest("Alarms and Notifications select the manual task ")
                    .log(Status.PASS, "Successfully select the manual task in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications select the manual task").log(Status.FAIL,
                    "Failed to select the manual task in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    public void createMsgSelectSite() {
        try {
            List<WebElement> elements = driver.findElements(By.id("userSiteListDn"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            WebElement dropdown = driver.findElement(By.id("userSiteListDn"));
            Select select = new Select(dropdown);
            select.selectByVisibleText("In front of Rani Mahal");
            // select.selectByVisibleText("K. Madhukar Pawle Uddanpul");

            String selectedSite = select.getFirstSelectedOption().getText();

            extent.createTest("Select Site from Dropdown in manual task").log(Status.PASS,
                    "Selected Site manual task: " + selectedSite);
        } catch (Exception e) {
            extent.createTest("Select Site from Dropdown in manual task").log(Status.FAIL,
                    "Failed to select Site from Dropdown in manual task. Exception: " + e.getCause());
        }
    }

    public void selectUserFromDropdown() {
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
            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isEnabled" + selectElement.isEnabled());
            System.out.println("selectElement.isSelected" + selectElement.isSelected());
            Select select = new Select(selectElement);
            select.selectByVisibleText("serviceuser");
            extent.createTest("Select User from Dropdown in Manual task").log(Status.PASS,
                    "Successfully selected the user with value '25'");

        } catch (Exception e) {
            extent.createTest("Select User from Dropdown in Manual Task").log(Status.FAIL,
                    "Failed to select the user from the dropdown. Exception: " + e.getCause());
        }

        app.selectServiceUser();
    }

    public void selectAlertType() {
        try {
            WebElement alertTypeDropdown = driver.findElement(By.id("alertType"));
            Select select = new Select(alertTypeDropdown);
            String textToSelect = "Site Issue";

            select.selectByVisibleText(textToSelect);
            // driver.findElement(By.id("alertType")).sendKeys("SITE");

            extent.createTest("Select Alert Type in Manual Task").log(Status.PASS,
                    "Successfully selected alert type: ");
        } catch (Exception e) {
            extent.createTest("Select Alert Type in Manual Task").log(Status.FAIL,
                    "Failed to select alert type. Exception: " + e.getCause());
        }
    }

    public void alertDescription() {
        try {
            WebElement messageTextarea = driver.findElement(By.id("alertDesc"));
            messageTextarea.clear();
            String textToEnter = "SITE ";

            messageTextarea.sendKeys(textToEnter);

            extent.createTest("Alert Type in Manual Task description").log(Status.PASS,
                    "Successfully entering description " + "textToEnter");

        } catch (Exception e) {
            extent.createTest("Alert Type in Manual Task").log(Status.FAIL,
                    "Failed to enter the description . Exception: " + e.getCause());
        }
    }

    public void manualTaskSubmit() {
        try {
            Thread.sleep(2000);
            WebElement taskButton = driver.findElement(By.id("btnTaskEdit"));
            taskButton.click();
            extent.createTest("In task details click the submit button ").log(Status.PASS,
                    "Successfully click the submit button ");
        } catch (Exception e) {
            extent.createTest("In task details click the submit button").log(Status.FAIL,
                    "Failed to click the submit button. Exception: "
                            + e.getCause());
        }
    }

    public void manualTaskAlertStatus() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//*[@id='swal2-title']")).getText();

            String resultdescription = driver.findElement(By.xpath("//*[@id='swal2-content']")).getText();

            System.out.println("Manaul Task submit status :" + resultdescription);

            Thread.sleep(3000);
            extent.createTest("Manual task After submit the status ").log(Status.PASS,
                    "Successfully submit the task" + resultdescription);
        } catch (Exception e) {
            extent.createTest("Manual task After submit the status of a task ").log(Status.FAIL,
                    "Failed to submit the task . Exception: " + e.getMessage());
        }
    }

    public void clickManualAlertOKButton() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            okButton.click();
            extent.createTest("After submit the status of a task user in manual task").log(Status.PASS,
                    "Clicked the 'OK' button in SweetAlert2 pop-up.");
        } catch (Exception e) {
            extent.createTest("After submit the status of a task user in manula task").log(Status.FAIL,
                    "Failed to click the 'OK' button. Exception: " + e.getCause());
        }
    }

    public void manualTaskCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageManualAlertTable_wrapper']/div[1]/button[1]")))
                    .click();

            extent.createTest("In Alarms and Notifications device manula alerts - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for device alerts");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts manual - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for device alerts. File not found.");
        }
    }

    public void manualTaskPdf() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageManualAlertTable_wrapper']/div[1]/button[2]")))
                    .click();
            extent.createTest("In Alarms and Notifications device alerts manula- PDF").log(Status.PASS,
                    "Successfully downloaded the PDF file in  Alarms and Notifications");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts manual - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    public void clickOverTime() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("pills-overtime-tab")).click();
            extent.createTest("Alarms and Notifications select the overtime alert ")
                    .log(Status.PASS, "Successfully select the overtime vehicle alerts in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications select the overtime alert vehicle alerts").log(Status.FAIL,
                    "Failed to select the overtime vehcile alerts in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    public void getSitesList() {
        try {
            Thread.sleep(3000);
            WebElement siteSearchDiv = driver.findElement(By.className("alert_site_name"));
            WebElement selectButton = siteSearchDiv.findElement(By.id("btn-site-view"));
            selectButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By dropdownOptionsLocator = By.cssSelector("ul.ui-autocomplete li");

            WebElement dropdownOptionsList = wait
                    .until(ExpectedConditions.presenceOfElementLocated(dropdownOptionsLocator));

            WebElement secondOption = dropdownOptionsList.findElements(By.tagName("li")).get(1);
            secondOption.click();

            String selectedSiteName = secondOption.getText();

            extent.createTest("Displaying the sites list in overtime vehicles page").log(Status.PASS,
                    "Successfully selected the second site: " + selectedSiteName);
        } catch (Exception e) {
            extent.createTest("Displaying the sites list in overtime vehicles  page ").log(Status.FAIL,
                    "Failed to select the second site. Exception: " + e.getMessage());
        }
    }

    public void overTimeCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageVechOvertimeTable']/div[1]/button[1]")))
                    .click();

            extent.createTest("In Alarms and Notifications - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for over time vehcile alerts");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications  - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for overtime vehcile alerts. File not found.");
        }
    }

    public void overTimePdf() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageVechOvertimeTable']/div[1]/button[2]")))
                    .click();
            extent.createTest("In Alarms and Notifications overtime vehicle alerts - PDF").log(Status.PASS,
                    "Successfully downloaded the PDF file in  Alarms and Notifications");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications  overtime vehicle alerts - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    public void selectFourWheelerOption() {
        try {
            WebElement fourWheelerLabel = driver.findElement(By.cssSelector("label[for='overtime_report_four']"));

            if (fourWheelerLabel.isDisplayed()) {
                fourWheelerLabel.click();
                extent.createTest("Select Four Wheeler Option").log(Status.PASS, "Four Wheeler selected");
            } else {
                extent.createTest("Select Four Wheeler Option").log(Status.FAIL, "Four Wheeler option not available");
            }
        } catch (Exception e) {
            extent.createTest("Select Four Wheeler Option").log(Status.FAIL,
                    "Failed to select Four Wheeler Option. Exception: " + e.getCause());
        }
    }

    public void clickTransactionAlert() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pills-transaction-tab")).click();
            extent.createTest("Alarms and Notifications select the transaction alert ")
                    .log(Status.PASS, "Successfully select the transaction alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications select the transaction alert").log(Status.FAIL,
                    "Failed to select the transaction alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    public void trasnactionCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageTransactionAlertsTable']/div[1]/button[1]")))
                    .click();

            extent.createTest("In Alarms and Notifications - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for transaction alerts");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications  - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for transaction alerts. File not found.");
        }
    }

    public void transactionPdf() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageTransactionAlertsTable']/div[1]/button[2]")))
                    .click();
            extent.createTest("In Alarms and Notifications transaction alerts - PDF").log(Status.PASS,
                    "Successfully downloaded the PDF file in  Alarms and Notifications");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications transaction alerts - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    public void getSitesListTransAlerts() {
        try {
            Thread.sleep(3000);
            WebElement siteSearchDiv = driver.findElement(By.className("alert_site_name"));
            WebElement selectButton = siteSearchDiv.findElement(By.id("btn-site-view"));
            selectButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By dropdownOptionsLocator = By.cssSelector("ul.ui-autocomplete li");

            WebElement dropdownOptionsList = wait
                    .until(ExpectedConditions.presenceOfElementLocated(dropdownOptionsLocator));

            WebElement secondOption = dropdownOptionsList.findElements(By.tagName("li")).get(1);
            secondOption.click();

            String selectedSiteName = secondOption.getText();

            extent.createTest("Displaying the sites list in Transaction alerts page").log(Status.PASS,
                    "Successfully selected the second site: " + selectedSiteName);
        } catch (Exception e) {
            extent.createTest("Displaying the sites list in Transaction alerts page ").log(Status.FAIL,
                    "Failed to select the second site. Exception: " + e.getMessage());
        }
    }

    public void clickPOSAlert() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("pills-pos-tab")).click();
            extent.createTest("Alarms and Notifications select the transaction alert ")
                    .log(Status.PASS, "Successfully select the transaction alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications select the transaction alert").log(Status.FAIL,
                    "Failed to select the transaction alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

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

            extent.createTest("Alarms&Notification POS alerts dates enabled").log(Status.PASS,
                    "Successfully selecting the da/week/month");
        } catch (Exception e) {
            extent.createTest("Alarms&Notification POS alerts dates enabled").log(Status.FAIL,
                    "Failed to select the day/week/month. Exception: " + e.getMessage());
        }
    }

    public void posCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPosDetailsTable_wrapper']/div[1]/button[1]")))
                    .click();

            extent.createTest("In Alarms and Notifications POS alerts - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for POS alerts");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications  - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for POS alerts. File not found.");
        }
    }

    public void posPdf() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPosDetailsTable_wrapper']/div[1]/button[2]")))
                    .click();
            extent.createTest("In Alarms and Notifications POS alerts - PDF").log(Status.PASS,
                    "Successfully downloaded the PDF file in  Alarms and Notifications");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications POS alerts - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

    public void clickDeviceAlert() {
        try {
            Thread.sleep(5000);
            List<WebElement> elements = driver.findElements(By.id("pills-device-tab"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            driver.findElement(By
                    .id("pills-device-tab"))
                    .click();
            extent.createTest("Alarms and Notifications select the device alert ")
                    .log(Status.PASS, "Successfully select the device alert in Alarms&Notifications");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Alarms and Notifications select the device alert").log(Status.FAIL,
                    "Failed to select the device alert in Alarms&Notifications . Exception: " +
                            e.getCause());
        }
    }

    public void devcieAlertsCsv() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageDeviceAlertTable_wrapper']/div[1]/button[1]")))
                    .click();

            extent.createTest("In Alarms and Notifications device alerts -  CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for device alerts");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts - CSV").log(Status.FAIL,
                    "Failed to download the CSV file for device alerts. File not found.");
        }
    }

    public void deviceAlertsPdf() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(
                            By.xpath("//div[@id='alertsPageDeviceAlertTable_wrapper']/div[1]/button[2]")))
                    .click();
            extent.createTest("In Alarms and Notifications device alerts - PDF").log(Status.PASS,
                    "Successfully downloaded the PDF file in Alarms and Notifications");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts - PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications. File not found.");
        }

    }

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

            extent.createTest("In Alarms and Notifications device alerts").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    public void editTaskDeviceAlerts() {
        try {
            Thread.sleep(2000);
            WebElement editButton = driver.findElement(By.cssSelector("a.btnAsgnTsk[title='Edit']"));
            editButton.click();
            extent.createTest("Devcie alerts to edit the task details ").log(Status.PASS,
                    "Successfully click the task edit details button ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Devcie alerts to edit the task details ").log(Status.FAIL,
                    "Failed to click the task edit details button . Exception: " + e.getCause());
        }
    }

    public void selectServiceUser() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("optName")));
            Select select = new Select(selectElement);
            select.selectByValue("20");
            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isDisplayed" + selectElement.isEnabled());
            System.out.println("selectElement.isDisplayed" + selectElement.isSelected());

            extent.createTest("In task details select the user ").log(Status.PASS,
                    "Successfully selecting the service user ");
        } catch (Exception e) {
            extent.createTest("In task details select the user ").log(Status.FAIL,
                    "Failed to selecting the service user . Exception: "
                            + e.getCause());
        }
    }

    public void taskSubmitBtn() {
        try {
            Thread.sleep(2000);
            WebElement taskButton = driver.findElement(By.id("btnTaskEdit"));
            taskButton.click();
            extent.createTest("In task details click the submit button").log(Status.PASS,
                    "Successfully click the submit button ");
        } catch (Exception e) {
            extent.createTest("In task details click the submit button").log(Status.FAIL,
                    "Failed to click the submit button. Exception: "
                            + e.getCause());
        }
    }

    public void taskAlertStatus() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//*[@id='swal2-title']")).getText();

            String resultdescription = driver.findElement(By.xpath("//*[@id='swal2-content']")).getText();

            System.out.println("Task submit status in assign :" + resultdescription);

            Thread.sleep(3000);
            extent.createTest("After submit the status of a task user").log(Status.PASS,
                    "Successfully assigned the task" + resultdescription);
        } catch (Exception e) {
            extent.createTest("After submit the status of a task user").log(Status.FAIL,
                    "Failed to assign the task . Exception: " + e.getMessage());
        }
    }

    public void clickSweetAlertOKButton() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            okButton.click();
            extent.createTest("After submit the status of a task user").log(Status.PASS,
                    "Clicked the 'OK' button in SweetAlert2 pop-up.");
        } catch (Exception e) {
            extent.createTest("After submit the status of a task user").log(Status.FAIL,
                    "Failed to click the 'OK' button. Exception: " + e.getCause());
        }
    }

    public void taskPopUPClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("close")).click();
            extent.createTest("In alarms and notification task POP Up").log(Status.PASS,
                    "Successfully closed the pop up in device alerts");
        } catch (Exception e) {
            extent.createTest("In alarms and notification POP Up").log(Status.FAIL,
                    "Failed to closed the pop up in device alerts. Exception: " + e.getCause());
        }
    }

    public void clickTaskViewButton() {
        try {
            Thread.sleep(3000);
            WebElement viewButton = driver
                    .findElement(By.xpath("//*[@id='alertsPageDeviceAlertTable']/tbody/tr[1]/td[9]/a[2]"));

            viewButton.click();

            extent.createTest("In alarms and notification task view").log(Status.PASS,
                    "Clicked the 'View' button to open the modal.");

        } catch (Exception e) {
            extent.createTest("In alarms and notification task view").log(Status.FAIL,
                    "Failed to click the 'View' button. Exception: " + e.getCause());
        }
    }

    public void viewTaskCsv() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("deviceAlertCsvBtn"))
                    .click();

            extent.createTest("In Alarms and Notifications device alerts - CSV").log(Status.PASS,
                    "Successfully downloaded the CSV file for device alerts of view task");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts - CSV ").log(Status.FAIL,
                    "Failed to download the CSV file for device alerts of view task. File not found.");
        }
    }

    public void viewTaskPdf() {
        try {
            driver.findElement(By.id("deviceAlertPdfBtn"))
                    .click();
            extent.createTest("In Alarms and Notifications device alerts - PDF").log(Status.PASS,
                    "Successfully downloaded the PDF file for device alerts of view task");
        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts -PDF").log(Status.FAIL,
                    "Failed to download the PDF file in Alarms and Notifications for device alerts of view task. File not found.");
        }

    }

    public void logAlertDetails() {
        try {
            WebElement detailsTable = driver.findElement(By.cssSelector("tbody"));

            List<WebElement> tdElements = detailsTable.findElements(By.cssSelector("td"));

            for (int i = 0; i < tdElements.size(); i += 2) {
                String label = tdElements.get(i).getText();
                String value = tdElements.get(i + 1).getText();

                extent.createTest("In Alarms and Notifications device alerts view log details").log(Status.INFO,
                        label + ": " + value);
            }

            extent.createTest("In Alarms and Notifications device alerts view log details").log(Status.PASS,
                    "Logged the alert details.");

        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts log details of an alert").log(Status.FAIL,
                    "Failed to log the alert details. Exception: " + e.getCause());
        }
    }

    public void clickCompleteStatusButton() {
        try {
            Thread.sleep(3000);
            WebElement completeStatusButton = driver.findElement(By.cssSelector("input[alerttype='Devicealert']"));

            completeStatusButton.click();

            extent.createTest("In Alarms and Notifications device alerts view log details").log(Status.PASS,
                    "Clicked the 'Complete Status' button.");

        } catch (Exception e) {
            extent.createTest("In Alarms and Notifications device alerts view log details").log(Status.FAIL,
                    "Failed to click the 'Complete Status' button. Exception: " + e.getCause());
        }
    }

    public void completedStatusSweetAlert() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//*[@id='swal2-title']")).getText();

            String resultdescription = driver.findElement(By.xpath("//*[@id='swal2-content']")).getText();

            System.out.println("Task Completed status in Device Details:" +
                    resultdescription);

            Thread.sleep(3000);
            extent.createTest("After complete the pop up of an alert ").log(Status.PASS,
                    "Successfully completed the task" + resultdescription);
        } catch (Exception e) {
            extent.createTest("After complete the pop up of an alert ").log(Status.FAIL,
                    "Failed to completed the task . Exception: " + e.getMessage());
        }
    }

    public void clickCompleteSweetAlertOKButton() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            okButton.click();
            extent.createTest("After completed the status of an alert ").log(Status.PASS,
                    "Clicked the 'OK' button in SweetAlert2 pop-up.");
        } catch (Exception e) {
            extent.createTest("After completed the status of an alert ").log(Status.FAIL,
                    "Failed to click the 'OK' button. Exception: " + e.getCause());
        }
    }

    public void deviceDetailsPopUPClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.className("close")).click();
            extent.createTest("In alarms and notification task complete device details pop Up").log(Status.PASS,
                    "Successfully closed the pop up in device details in device alerts");
        } catch (Exception e) {
            extent.createTest("In alarms and notification complete device details pop Up").log(Status.FAIL,
                    "Failed to closed the pop up in device details in device alerts. Exception: "
                            + e.getCause());
        }
    }

    public void unAssignAlert() {
        try {
            Thread.sleep(2000);
            WebElement unassignAlertButton = driver
                    .findElement(By.cssSelector("a.btnUnassignDevice[title='Unassign Alert']"));
            unassignAlertButton.click();
            extent.createTest("Devcie alerts to click the unassign button").log(Status.PASS,
                    "Successfully click the unassign button ");
            Thread.sleep(2000);
        } catch (Exception e) {
            extent.createTest("Devcie alerts to click the unassign button").log(Status.FAIL,
                    "Failed to click the unassign button . Exception: " + e.getCause());
        }
    }

    public void unAssignAlertStatus() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//*[@id='swal2-title']")).getText();

            String resultdescription = driver.findElement(By.xpath("//*[@id='swal2-content']")).getText();

            System.out.println("Task submit status in Unassign:" + resultdescription);

            Thread.sleep(3000);
            extent.createTest("After submit the status of a task user").log(Status.PASS,
                    "Successfully assigned the task" + resultdescription);
        } catch (Exception e) {
            extent.createTest("After submit the status of a task user").log(Status.FAIL,
                    "Failed to assign the task . Exception: " + e.getMessage());
        }
    }

    public void unAssignSweetAlertOKButton() {
        try {
            Thread.sleep(2000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-confirm"));
            okButton.click();
            extent.createTest("After submit the status of a task user").log(Status.PASS,
                    "Clicked the 'OK' button in SweetAlert2 pop-up.");
        } catch (Exception e) {
            extent.createTest("After submit the status of a task user").log(Status.FAIL,
                    "Failed to click the 'OK' button. Exception: " + e.getCause());
        }
    }

    public void validateActionStatus() {
        WebElement table = driver.findElement(By.id("alertsPageDeviceAlertTable"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            extent.createTest("In PayU Reports table Status").log(Status.FAIL, "No data available");
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
            extent.createTest("In Alarms and Notifications status").log(Status.FAIL,
                    "Found a completed status alert");

        } else {
            extent.createTest("In Alarms and Notifications status").log(Status.PASS,
                    "No completed status alert found");
        }
    }

    public static void main(String[] args) {
        AlarmsNotifications1 p = new AlarmsNotifications1();
        p.selectServiceUser();
        p.selectAlertType();
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            extent.flush();
        }
    }
}