package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

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

// import com.aventstack.extentReportsreports.extentReportsReports;
// import com.aventstack.extentReportsreports.reporter.extentReportsSparkReporter;

class ServerConfigAppMessages {
    String userName;
    String password;
    Boolean production;

    public ServerConfigAppMessages(String userName, String password, Boolean production) {
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

public class AppMessages extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/AppMessages.html");
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
            extentReports.createTest("Site Management ").log(Status.PASS, "Successfully Open Site Management  ");

        } catch (Exception e) {
            extentReports.createTest("Site Management ").log(Status.FAIL,
                    "Failed to Open the Site Management . Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void jstreeOrg() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath(("//i[@class='jstree-icon jstree-ocl']"))).click();
            driver.findElement(By.xpath(("//li[@id='2']/i"))).click();
            extentReports.createTest("Site Management Organisation Info ").log(Status.PASS,
                    "Successfully Open Site Management organisation  ");

        } catch (Exception e) {
            extentReports.createTest("Site Management ").log(Status.FAIL,
                    "Failed to Open  Site Management organisation   . Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void applicationMsgs() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//li[@id='AppMessages']/i")).click();
            extentReports.createTest("Site Management application messages ").log(Status.PASS,
                    "Successfully Open Site Management application messages  ");

        } catch (Exception e) {
            extentReports.createTest("Site Management application messages").log(Status.FAIL,
                    "Failed to Open  Site Management application messages  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void applMsgsAnchor() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id='AppMessages_anchor']")).click();
            extentReports.createTest("Site Management application messages details tree ").log(Status.PASS,
                    "Successfully Open Site Management application messages tree ");

        } catch (Exception e) {
            extentReports.createTest("Site Management application messages details tree").log(Status.FAIL,
                    "Failed to Open  Site Management application messages tree . Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void selectByPartialText() {
        // try {
        // Thread.sleep(3000);
        // WebElement dropdown = driver.findElement(By.id("languageType"));
        // String optionValue = "Marathi";
        // ((JavascriptExecutor) driver).executeScript("arguments[0].value =
        // arguments[1];", dropdown, optionValue);

        // extentReports.createTest("Selecting language type Marathi ").log(Status.PASS,
        // "Successfully Selecting language type Marathi " + optionValue);

        // } catch (

        // Exception e) {
        // extentReports.createTest("Selecting language type Marathi").log(Status.FAIL,
        // "Failed to Selecting language type Marathi . Exception: " + e.getMessage());
        // }
        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("languageType")));
            List<WebElement> elements = driver.findElements(By.id("languageType"));
            StringBuilder elementTexts = new StringBuilder();

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    elementTexts.append("\"").append(element.getText()).append("\"\n");
                }
            }

            String allLanguages = "Select Language: " + elementTexts.toString();

            System.out.println(allLanguages);

            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isEnabled" + selectElement.isEnabled());
            System.out.println("selectElement.isSelected" + selectElement.isSelected());

            Select select = new Select(selectElement);
            select.selectByValue("Marathi");
            extentReports.createTest("Selecting language Marathi ").log(Status.PASS,
                    "Successfully Selecting language Marathi  ");

        } catch (Exception e) {
            extentReports.createTest("Selecting language Marathi ").log(Status.FAIL,
                    "Failed to Selecting language Marathi . Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void createMessageMarathi() {
        try {
            Thread.sleep(5000);
            // WebElement messageTextarea = driver.findElement(By.id("messageText"));
            // messageTextarea.clear();
            // String textToEnter = "स्लॉटमधून जागा रिकामी करा";

            // messageTextarea.sendKeys(textToEnter);
            driver.findElement(By.xpath("//*[@id='messageText']")).sendKeys("स्लॉटमधून जागा रिकामी करा");

            extentReports.createTest("Create message in Marathi ").log(Status.PASS,
                    "Successfully cretaing the messages ");

        } catch (Exception e) {
            extentReports.createTest("Create message in Marathi").log(Status.FAIL,
                    "Failed to Create message in Marathi . Exception: " + e.getCause());
        }

    }

    @Test(priority = 9)
    public void MarathiSubmit() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.id("addMessageallsite")).click();
            extentReports.createTest("Submit button while creating the Eng alert ").log(
                    Status.PASS,
                    "Successfully clicking the submit button ");
        } catch (Exception e) {
            extentReports.createTest("Submit button while creating the Eng alert ").log(
                    Status.FAIL,
                    "Failed to click the submit button . Exception: " + e.getCause());
        }
    }

    @Test(priority = 33)
    public void alertMarathiStatus() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();

            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("Marathi Created alert Status:" + resultdescription);

            Thread.sleep(3000);
            extentReports.createTest("In Marathi the Alert Created as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In Marathi the Alert Created as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 10)
    public void marathiOK() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Marathi alert Message").log(Status.PASS,
                    "Clicked the 'OK' button in Marathi alert message.");
        } catch (Exception e) {
            extentReports.createTest("Marathi alert Message").log(Status.FAIL,
                    "Failed to click the 'OK' button in Marathi alert message. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void appMarathiMsgsAnchorforth() {
        applMsgsAnchor();
    }

    @Test(priority = 12)
    public void messsgesAnchorMarathi() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id='MessagesList_anchor']")).click();
            extentReports.createTest("Site Management messages details tree ").log(Status.PASS,
                    "Successfully Open Site Management  messages tree ");

        } catch (Exception e) {
            extentReports.createTest("Site Management  messages details tree").log(Status.FAIL,
                    "Failed to Open  Site Management  messages tree . Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void marathiMsgDeleteRow() {
        try {
            WebElement table = driver.findElement(By.id("alertMessagesTbl"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("Alert Messages").log(Status.PASS,
                        "No User data available");
                return;
            }

            WebElement alertMessage = driver
                    .findElement(By.xpath("//td[contains(text(), 'पार्किंग स्लॉटची वाट पाहत आहे')]"));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", table);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.stalenessOf(alertMessage));

            extentReports.createTest("Clicking on Row").log(Status.FAIL,
                    "Clicked on the table, and the alert message is deleted.");

        } catch (Exception e) {
            extentReports.createTest("Clicking on Row").log(Status.PASS,
                    "Clicked on the table, and the alert message is still present.");
        }
    }

    @Test(priority = 14)
    public void deleteAlertMsgMarathi() {
        try {
            Thread.sleep(2000);
            WebElement table = driver.findElement(By.id("alertMessagesTbl"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("Alert Messages").log(Status.PASS,
                        "No User data available");
                return;
            }
            WebElement deleteButton = driver.findElement(By.xpath(
                    "//button[@class='btn btn-outline-danger btn-sm delAlertmesg']/i[@class='fa fa-trash-o button']"));
            deleteButton.click();

            extentReports.createTest("Clicking on delete button").log(Status.PASS,
                    "Clicked on the table, and the alert message is still present.");
        } catch (Exception e) {
            extentReports.createTest("Clicking on delete button").log(Status.FAIL,
                    "Clicked on the table, and the alert message is deleted.");
        }
    }

    @Test(priority = 15)
    public void alertMarathiDelStatus() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();

            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("Marathi Delete alert Status:" + resultdescription);

            Thread.sleep(3000);
            extentReports.createTest("In Marathi the Alert delete as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In Marathi the Alert delete as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 16)
    public void marathiDeleteOK() {
        try {
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Marathi alert Message while deleting").log(Status.PASS,
                    "Clicked the 'OK' button in Marathi alert message.");
        } catch (Exception e) {
            extentReports.createTest("Marathi alert Message while deleting").log(Status.FAIL,
                    "Failed to click the 'OK' button in Marathi alert message. Exception: " + e.getCause());
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
