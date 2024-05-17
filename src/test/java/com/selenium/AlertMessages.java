package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
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



public class AlertMessages extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverConfig;

    @BeforeClass
    public void setUp() {
        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/AlertMessages.html");
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
            Thread.sleep(3000);
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
            Thread.sleep(2000);
            driver.findElement(By.xpath("//li[@id='AppMessages']/i")).click();
            extentReports.createTest("Site Management application messages ").log(Status.PASS,
                    "Successfully Open Site Management application messages  ");

        } catch (Exception e) {
            extentReports.createTest("Site Management application messages").log(Status.FAIL,
                    "Failed to Open  Site Management application messages  . Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void applicationMsgsAnchor() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//a[@id='AppMessages_anchor']")).click();
            extentReports.createTest("Site Management application messages details tree ").log(Status.PASS,
                    "Successfully Open Site Management application messages tree ");

        } catch (Exception e) {
            extentReports.createTest("Site Management application messages details tree").log(Status.FAIL,
                    "Failed to Open  Site Management application messages tree . Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void selectEng() {
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
            select.selectByIndex(1);
            extentReports.createTest("Selecting language English ").log(Status.PASS,
                    "Successfully Selecting language English  ");

        } catch (Exception e) {
            extentReports.createTest("Selecting language English ").log(Status.FAIL,
                    "Failed to Selecting language English . Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void createMessage_English() {
        try {
            WebElement messageTextarea = driver.findElement(By.id("messageText"));
            messageTextarea.clear();
            String textToEnter = "Remove your vehicle from the parking slot";

            messageTextarea.sendKeys(textToEnter);

            extentReports.createTest("Create message in English ").log(Status.PASS,
                    "Successfully cretaing the messages: " + textToEnter);

        } catch (Exception e) {
            extentReports.createTest("Create message in English").log(Status.FAIL,
                    "Failed to Create message in English . Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void msgSubmit() {
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

    @Test(priority = 10)
    public void alertStatus() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();
            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("Created alert Status:" + resultdescription);

            extentReports.createTest("In English the Alert Created as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In English the Alert Created as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 11)
    public void engStatusOk() {
        try {
            Thread.sleep(3000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("English alert Message").log(Status.PASS,
                    "Clicked the 'OK' button in English alert message.");
        } catch (Exception e) {
            extentReports.createTest("English alert Message").log(Status.FAIL,
                    "Failed to click the 'OK' button in English alert message. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void messagesToView() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath("//a[@id='MessagesList_anchor']")).click();
            extentReports.createTest("Site Management messages details tree ").log(Status.PASS,
                    "Successfully Open Site Management messages tree ");

        } catch (Exception e) {
            extentReports.createTest("Site Management  messages details tree").log(Status.FAIL,
                    "Failed to Open  Site Management messages tree . Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void getAlertMessagesTableDetails() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("alertMessagesTbl")));
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            if (rows.isEmpty()) {
                extentReports.createTest("Alert Messages Table").log(Status.FAIL, "No data available");
                return;
            }

            StringBuilder tableInfo = new StringBuilder();
            tableInfo.append(
                    "| Id    | Message | Language | Language Code | Status | Created Time| \n");

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() >= 7) {
                    String id = cells.get(0).getText();
                    String message = cells.get(1).getText();
                    String language = cells.get(2).getText();
                    String languageCode = cells.get(3).getText();
                    String status = cells.get(4).getText();
                    String createdTime = cells.get(5).getText();

                    tableInfo.append(String.format("| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%n", id,
                            message, language, languageCode, status, createdTime));
                }
            }
            extentReports.createTest("Alert Messages Table")
                    .log(Status.PASS, "Displaying Successfully")
                    .log(Status.INFO, "Displaying message details:\n" + tableInfo.toString());

        } catch (TimeoutException e) {
            extentReports.createTest("Alert Messages Table").log(Status.FAIL,
                    "Timed out waiting for the Alert Messages Table");
        } catch (Exception e) {
            extentReports.createTest("Alert Messages Table").log(Status.FAIL,
                    "Failed to retrieve table details. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 14)
    public void engMsgDeleteRow() {
        try {
            WebElement table = driver.findElement(By.id("alertMessagesTbl"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            if (rows.isEmpty()) {
                extentReports.createTest("Alert Messages  ").log(Status.PASS,
                        "No User data available");
                return;
            }
            WebElement alertMessage = driver.findElement(By.xpath("//td[contains(text(), 'Remove your vehicle')]"));

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

    @Test(priority = 15)
    public void engMsgDeleteBtn() {
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

    @Test(priority = 16)
    public void alertDelStatus() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();
            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("Delete alert Status:" + resultdescription);

            extentReports.createTest("In English the Alert delete as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In English the Alert delete as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void engDeleteOK() {
        engStatusOk();
    }

    @Test(priority = 18)
    public void applicationMsgs_ToCreate_Hindi() {
        applicationMsgsAnchor();
    }

    @Test(priority = 19)
    public void selectHindi() {
        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("languageType")));
            List<WebElement> elements = driver.findElements(By.id("languageType"));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Visible Element: " + element.getText());
                    break;
                }
            }
            selectElement.isDisplayed();
            System.out.println("selectElement.isDisplayed" + selectElement.isDisplayed());
            System.out.println("selectElement.isEnabled" + selectElement.isEnabled());
            System.out.println("selectElement.isSelected" + selectElement.isSelected());

            Select select = new Select(selectElement);
            select.selectByIndex(2);
            extentReports.createTest("Selecting language type Hindi ").log(Status.PASS,
                    "Successfully Selecting language type Hindi ");

        } catch (Exception e) {
            extentReports.createTest("Selecting language type Hindi").log(Status.FAIL,
                    "Failed to Selecting language type Hindi . Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void createMessageHindi() {
        try {
            WebElement messageTextarea = driver.findElement(By.id("messageText"));
            messageTextarea.clear();
            String textToEnter = "पार्किंग स्थल का इंतजार किया जा रहा है";

            messageTextarea.sendKeys(textToEnter);

            extentReports.createTest("Create message in Hindi ").log(Status.PASS,
                    "Successfully cretaing the messages " + textToEnter);

        } catch (Exception e) {
            extentReports.createTest("Create message in Hindi").log(Status.FAIL,
                    "Failed to Create message in Hindi . Exception: " + e.getCause());
        }
    }

    @Test(priority = 21)
    public void hindiSubmit() {
        msgSubmit();
    }

    @Test(priority = 22)
    public void alertHindiStatus() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();
            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("Hindi Created alert Status:" + resultdescription);

            Thread.sleep(3000);
            extentReports.createTest("In Hindi the Alert Created as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In Hindi the Alert Created as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void hindiOK() {
        engStatusOk();
    }

    @Test(priority = 24)
    public void messagesToView_AfterCreate_Hindi() {
        messagesToView();
    }

    @Test(priority = 25)
    public void messsgesAnchorHindi() {
        getAlertMessagesTableDetails();
    }

    @Test(priority = 26)
    public void hindiMsgDeleteRow() {
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

    @Test(priority = 27)
    public void deleteAlertMsgHindi() {
        engMsgDeleteBtn();
    }

    @Test(priority = 28)
    public void alertHindiDelStatus() {
        try {
            Thread.sleep(3000);

            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();
            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();
            System.out.println("Hindi Delete alert Status:" + resultdescription);

            Thread.sleep(3000);
            extentReports.createTest("In Hindi the Alert delete as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In Hindi the Alert delete as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 29)
    public void hindiDeleteOK() {
        engStatusOk();
    }

    @Test(priority = 30)
    public void applicationMsgs_ToCreate_TwiceEnglish() {
        applicationMsgsAnchor();
    }

    @Test(priority = 31)
    public void secondSelectEng() {
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
            select.selectByIndex(1);
            extentReports.createTest("Selecting language English ").log(Status.PASS,
                    "Successfully Selecting language English  ");

        } catch (Exception e) {
            extentReports.createTest("Selecting language English ").log(Status.FAIL,
                    "Failed to Selecting language English . Exception: " + e.getCause());
        }
    }

    @Test(priority = 32)
    public void secondCreateEng() {
        try {
            WebElement messageTextarea = driver.findElement(By.id("messageText"));
            messageTextarea.clear();
            String textToEnter = "Remove your vehicle";

            messageTextarea.sendKeys(textToEnter);

            extentReports.createTest("Create message in English ").log(Status.PASS,
                    "Successfully cretaing the messages " + textToEnter);

        } catch (Exception e) {
            extentReports.createTest("Create message in English").log(Status.FAIL,
                    "Failed to Create message in English . Exception: " + e.getCause());
        }
    }

    @Test(priority = 33)
    public void secondMsgSubmit() {
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

    @Test(priority = 34)
    public void secondStatus() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//h2[@id='swal2-title']")).getText();
            String resultdescription = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

            System.out.println("Created alert Status:" + resultdescription);

            Thread.sleep(3000);
            extentReports.createTest("In English the Alert Created as").log(Status.PASS,
                    "Successfully " + resultdescription);
        } catch (Exception e) {
            extentReports.createTest("In English the Alert Created as").log(Status.FAIL,
                    "Failed . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 35)
    public void secondStatusOk() {
        engStatusOk();
    }

    @Test(priority = 36)
    public void messagesToView_AfterCreate_TwiceEnglish() {
        messagesToView();
    }

    @Test(priority = 37)
    public void applicationMsgs_ToCreate_Marathi() {
        applicationMsgsAnchor();
    }

    @Test(priority = 38)
    public void selectMarathi() {
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
            select.selectByIndex(3);
            extentReports.createTest("Selecting language Marathi ").log(Status.PASS,
                    "Successfully Selecting language Marathi  ");

        } catch (Exception e) {
            extentReports.createTest("Selecting language Marathi ").log(Status.FAIL,
                    "Failed to Selecting language Marathi . Exception: " + e.getCause());
        }
    }

    @Test(priority = 39)
    public void createMessageMarathi() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='messageText']")).sendKeys("स्लॉटमधून जागा रिकामी करा");

            extentReports.createTest("Create message in Marathi ").log(Status.PASS,
                    "Successfully cretaing the messages ");

        } catch (Exception e) {
            extentReports.createTest("Create message in Marathi").log(Status.FAIL,
                    "Failed to Create message in Marathi . Exception: " + e.getCause());
        }

    }

    @Test(priority = 40)
    public void MarathiSubmit() {
        msgSubmit();
    }

    @Test(priority = 41)
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

    @Test(priority = 42)
    public void marathiOK() {
        engStatusOk();
    }

    @Test(priority = 43)
    public void messagesToView_AfterCreate_Marathi() {
        messagesToView();
    }

    @Test(priority = 44)
    public void messsgesAnchorMarathi() {
        getAlertMessagesTableDetails();
    }

    @Test(priority = 45)
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

    @Test(priority = 46)
    public void deleteAlertMsgMarathi() {
        engMsgDeleteBtn();
    }

    @Test(priority = 47)
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

    @Test(priority = 48)
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

    @Test(priority = 49)
    public void logout() {
        try {
            Thread.sleep(5000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Alert Messages LOGOUT ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Alert Messages LOGOUT").log(Status.FAIL,
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
