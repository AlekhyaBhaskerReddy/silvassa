package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class UserZoneAssign extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/UserZoneAssign.html");
        // extentReports.attachReporter(spark);
        // extentTest = extentReports.createTest("demo");

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
    public void revenueDashboard() {
        Dashboard.revenuedashboardOpen(driver, extentReports);
    }

    @Test(priority = 3)
    public void userMgmtPage() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[6]")).click();

            WebElement assignmentPageLink = driver
                    .findElement(By.xpath("//*[@id='page-wraper']/div/div/nav/ul/li[6]/ul/li[4]"));
            if (assignmentPageLink.isDisplayed()) {
                assignmentPageLink.click();
                extentReports.createTest("User Zone Assignment Page ").log(Status.PASS,
                        "Successfully Open the User Zone Assignment page");
            } else {
                extentReports.createTest("User Zone Assignment Page ").log(Status.INFO,
                        "User Zone Assignment page not found");
                Assert.fail("User Zone Assignment page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("User Zone Assignment Page ").log(Status.FAIL,
                    "Failed to click the Zone Assignment page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void officerCount() {
        try {
            Thread.sleep(5000);
            WebElement dropdown = driver.findElement(By.name("officerTableList_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            String popup1Text = dropdown.getText();

            extentReports.createTest("select officer Count").log(Status.PASS, "Successfully selected as" + popup1Text);
        } catch (Exception e) {
            extentReports.createTest("select officer Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 5)
    public void searchOfficer() {
        try {
            Thread.sleep(5000);
            WebElement searchInput = driver.findElement(By.cssSelector("input[aria-controls='officerTableList']"));
            searchInput.sendKeys("NORTH");
            searchInput.sendKeys(Keys.RETURN);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#officerTableList tbody tr")));

            List<WebElement> searchResults = driver.findElements(By.cssSelector("#officerTableList tbody tr"));

            extentReports.createTest("Search officer tab ")
                    .log(Status.PASS, "Successfully performed search with ")
                    .info("Number of search results: " + searchResults.size());
        } catch (Exception e) {
            extentReports.createTest("Search Officer tab")
                    .log(Status.FAIL, "Failed to perform search. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void clickAddMoreOfficerButton() {
        try {
            Thread.sleep(3000);
            WebElement addMoreButton = driver.findElement(By.className("btnUpdateOfficer"));
            addMoreButton.click();

            extentReports.createTest("Click Add More Button")
                    .log(Status.PASS, "Clicked on the 'Add More' button successfully.");
        } catch (Exception e) {
            extentReports.createTest("Click Add More Button")
                    .log(Status.FAIL, "Failed to click the 'Add More' button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void selectOfficerList() {
        try {
            WebElement selectElement = driver.findElement(By.className("pOfficerList"));
            Select select = new Select(selectElement);
            Thread.sleep(3000);
            java.util.List<WebElement> options = select.getOptions();

            if (options.isEmpty()) {
                extentReports.createTest("Select Officer list ").log(Status.PASS,
                        "No options found in the dropdown. Test case passed.");
                System.out.println("No options found in the dropdown.");
            } else {
                extentReports.createTest("Select Officer list ").log(Status.INFO,
                        "Options in the dropdown: " + options.toString());
                System.out.println("Options in the dropdown: " + options.toString());

                select.selectByVisibleText(options.get(0).getText());
                System.out.println("Option selected: " + options.get(0).getText());

                extentReports.createTest("Select Officer list ").log(Status.PASS, "Option selected successfully.");
            }

        } catch (Exception e) {
            extentReports.createTest("Select Officer list ").log(Status.FAIL, "Exception occurred: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void addMorePopUpClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@data-dismiss='modal']")).click();
            extentReports.createTest("In User Zone Assignment officer POP Up").log(Status.PASS,
                    "Successfully closed the pop up of add more");
        } catch (Exception e) {
            extentReports.createTest("In User Zone Assignment officer POP Up ").log(Status.FAIL,
                    "Failed to close the pop up of add more. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void clickOperatorTab() {
        try {
            Thread.sleep(5000);
            WebElement attendersTab = driver.findElement(By.id("pills-operator-tab"));
            attendersTab.click();

            extentReports.createTest("Click operator tab").log(Status.PASS,
                    "operator tab clicked successfully.");

            extentReports.createTest("Click operator tab").log(Status.INFO,
                    "Additional information or data about the operator tab.");

        } catch (Exception e) {
            extentReports.createTest("Click operator tab").log(Status.FAIL,
                    "Failed to click operator tab. Exception: " + e.getMessage());

            extentReports.createTest("Click operator tab").log(Status.INFO,
                    "Additional information or data about the failure.");
        }
    }

    @Test(priority = 10)
    public void operatorCount() {
        try {
            Thread.sleep(5000);
            WebElement dropdown = driver.findElement(By.name("operatorTableList_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            String popup1Text = dropdown.getText();

            extentReports.createTest("select operator Count").log(Status.PASS, "Successfully selected as" + popup1Text);
        } catch (Exception e) {
            extentReports.createTest("select operator Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void searchOperator() {
        try {
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.cssSelector("input[aria-controls='operatorTableList']"));
            searchInput.sendKeys("NORTH");
            searchInput.sendKeys(Keys.RETURN);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#operatorTableList tbody tr")));

            List<WebElement> searchResults = driver.findElements(By.cssSelector("#operatorTableList tbody tr"));

            extentReports.createTest("Search operator tab ")
                    .log(Status.PASS, "Successfully performed search with ")
                    .info("Number of search results: " + searchResults.size());
        } catch (Exception e) {
            extentReports.createTest("Search operator tab")
                    .log(Status.FAIL, "Failed to perform search. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 12)
    public void selectOperatorList() {
        try {
            WebElement selectElement = driver.findElement(By.className("pOperatorList"));
            Select select = new Select(selectElement);
            Thread.sleep(3000);
            java.util.List<WebElement> options = select.getOptions();

            if (options.isEmpty()) {
                extentReports.createTest("Select operator tab ").log(Status.PASS,
                        "No options found in the dropdown. Test case passed.");
                System.out.println("No options found in the dropdown.");
            } else {
                extentReports.createTest("Select operator tab ").log(Status.INFO,
                        "Options in the dropdown: " + options.toString());
                System.out.println("Options in the dropdown: " + options.toString());

                select.selectByVisibleText(options.get(0).getText());
                System.out.println("Option selected: " + options.get(0).getText());

                extentReports.createTest("Select operator tab ").log(Status.PASS, "Option selected successfully.");
            }

        } catch (Exception e) {
            extentReports.createTest("Select operator tab ").log(Status.FAIL, "Exception occurred: " + e.getMessage());
        }
    }

    @Test(priority = 13)
    public void addMoreOperatorPopUpClose() {
        addMorePopUpClose();
    }

    @Test(priority = 14)
    public void clickMonitorTab() {
        try {
            Thread.sleep(5000);
            WebElement attendersTab = driver.findElement(By.id("pills-monitor-tab"));
            attendersTab.click();

            extentReports.createTest("Click Monitor tab").log(Status.PASS,
                    "operator tab clicked successfully.");

            extentReports.createTest("Click Monitor tab").log(Status.INFO,
                    "Additional information or data about the Monitor tab.");

        } catch (Exception e) {
            extentReports.createTest("Click Monitor tab").log(Status.FAIL,
                    "Failed to click Monitor tab. Exception: " + e.getMessage());

            extentReports.createTest("Click Monitor tab").log(Status.INFO,
                    "Additional information or data about the failure.");
        }
    }

    @Test(priority = 15)
    public void monitorCount() {
        try {
            Thread.sleep(5000);
            WebElement dropdown = driver.findElement(By.name("monitorTableList_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            String popup1Text = dropdown.getText();

            extentReports.createTest("select Monitor Count").log(Status.PASS, "Successfully selected as" + popup1Text);
        } catch (Exception e) {
            extentReports.createTest("select Monitor Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 16)
    public void searchMonitor() {
        try {
            Thread.sleep(5000);
            WebElement searchInput = driver.findElement(By.cssSelector("input[aria-controls='monitorTableList']"));
            searchInput.sendKeys("monitor9");
            searchInput.sendKeys(Keys.RETURN);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#monitorTableList tbody tr")));

            List<WebElement> searchResults = driver.findElements(By.cssSelector("#monitorTableList tbody tr"));

            extentReports.createTest("Search Monitor tab ")
                    .log(Status.PASS, "Successfully performed search with ")
                    .info("Number of search results: " + searchResults.size());
        } catch (Exception e) {
            extentReports.createTest("Search Monitor tab")
                    .log(Status.FAIL, "Failed to perform search. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void clickAddMoreMonitorButton() {
        try {
            Thread.sleep(3000);

            WebElement addMoreButton = driver.findElement(By.className("btnUpdateMonitor"));
            addMoreButton.click();

            extentReports.createTest("Click Add More Button")
                    .log(Status.PASS, "Clicked on the 'Add More' button successfully.");
        } catch (Exception e) {
            extentReports.createTest("Click Add More Button")
                    .log(Status.FAIL, "Failed to click the 'Add More' button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 18)
    public void selectMonitor() {
        try {
            WebElement selectElement = driver.findElement(By.className("pMonitorList"));
            Select select = new Select(selectElement);
            Thread.sleep(3000);
            java.util.List<WebElement> options = select.getOptions();

            if (options.isEmpty()) {
                extentReports.createTest("Select Monitor list ").log(Status.PASS,
                        "No options found in the dropdown. Test case passed.");
                System.out.println("No options found in the dropdown.");
            } else {
                extentReports.createTest("Select Monitor list ").log(Status.INFO,
                        "Options in the dropdown: " + options.toString());
                System.out.println("Options in the dropdown: " + options.toString());

                select.selectByVisibleText(options.get(0).getText());
                System.out.println("Option selected: " + options.get(0).getText());

                extentReports.createTest("Select Monitor list ").log(Status.PASS, "Option selected successfully.");
            }

        } catch (Exception e) {
            extentReports.createTest("Select Monitor list ").log(Status.FAIL, "Exception occurred: " + e.getMessage());
        }
    }

    @Test(priority = 19)
    public void addMoreMonitorPopUpClose() {
        addMorePopUpClose();
    }

    @Test(priority = 20)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("User Zone Assignment LogOut ").log(Status.PASS,
                    "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("User Zone Assignment LogOut").log(Status.FAIL,
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
