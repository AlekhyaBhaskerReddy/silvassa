package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
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

public class UserSiteAssign extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/UserSiteAssign.html");
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
            Thread.sleep(3000);

            WebElement assignmentPageLink = driver
                    .findElement(By.className("asn-pg"));
            if (assignmentPageLink.isDisplayed()) {
                assignmentPageLink.click();
                extentReports.createTest("User Site Assignment Page ").log(Status.PASS,
                        "Successfully Open the User Site Assignment page");
            } else {
                extentReports.createTest("User Site Assignment Page ").log(Status.INFO,
                        "User Site Assignment page not found");
                Assert.fail("User Site Assignment page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("User Site Assignment Page ").log(Status.FAIL,
                    "Failed to click the Site Assignment page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectManagerFromDropdown() {
        try {
            Thread.sleep(3000);
            WebElement dropdownButton = driver.findElement(By.id("btn-mng-view"));
            dropdownButton.click();

            extentReports.createTest("Dropdown open")
                    .log(Status.PASS, "Successfully open dropdown ");

        } catch (Exception e) {
            extentReports.createTest("Dropdown open")
                    .log(Status.FAIL,
                            "Failed to open dropdown of manager. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void selectManager3() throws InterruptedException {
        WebElement autocompleteInput = driver.findElement(By.id("managerListDn"));
        autocompleteInput.click();
        Thread.sleep(3000);

        List<WebElement> autocompleteOptions = driver.findElements(By.className("ui-menu-item-wrapper"));
        System.out.println(autocompleteOptions.size());

        for (WebElement option : autocompleteOptions) {
            System.out.println(option.getText());
            option.click();
            extentReports.createTest("Dropdown open and selected the manager ")
                    .log(Status.PASS, "Successfully open dropdown and selected the manager ");
        }
        extentReports.createTest("Dropdown open and selected the manager ")
                .log(Status.FAIL, "Failed open dropdown and selected the manager ");

    }

    @Test(priority = 6)
    public void managerCount() {
        try {
            Thread.sleep(5000);
            WebElement dropdown = driver.findElement(By.name("managerTableList_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            extentReports.createTest("select manager Count").log(Status.PASS, "Successfully selected as");
        } catch (Exception e) {
            extentReports.createTest("select manager Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void clickUnAssignButton() {
        try {
            Thread.sleep(5000);
            WebElement unassignButton = driver
                    .findElement(By.cssSelector("button.btn-outline-danger[siteid='3'][managerid='208']"));
            unassignButton.click();
            String selectedSite = unassignButton.getText();

            extentReports.createTest("Unassign Button Click").log(Status.PASS,
                    "Successfully clicked the Unassign button." + selectedSite);

        } catch (Exception e) {
            extentReports.createTest("Unassign Button Click").log(Status.FAIL,
                    "Failed to click the Unassign button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void confirmManagerUnassignAction() {
        try {
            WebElement deleteButton = driver.findElement(By.cssSelector("button.swal2-confirm"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();

                extentReports.createTest("Confirm Unassign site").log(Status.PASS,
                        "Clicked the 'Yes, Delete it!' button of unassign");
            } else {
                extentReports.createTest("Confirm UnAssign site").log(Status.FAIL,
                        "'Yes, Delete it!' button is not present or not enabled");
            }

        } catch (Exception e) {
            extentReports.createTest("Confirm unassign site").log(Status.FAIL,
                    "Failed to confirm delete action of unassign. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void extractPopupTextManager() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while unassign site").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while unassign site").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while unassign site").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 9)
    public void statusOKManager() {
        try {
            Thread.sleep(5000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status of unassign manager to the site").log(Status.PASS,
                    "Clicked the 'OK' button in users page.");
        } catch (Exception e) {
            extentReports.createTest("Status of unassign manager to the site").log(Status.FAIL,
                    "Failed to click the 'OK' button in users page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void verifyTableAfterUnAssign() {
        try {
            String siteIdToVerify = "3";

            WebElement unassignButton = driver.findElement(By.xpath("//tr[td[text()='" + siteIdToVerify
                    + "']]//button[@class='btn btn-outline-danger btn-sm unassign']"));
            unassignButton.click();
            extentReports.createTest("Verify Unassign Button").log(Status.FAIL,
                    "Unassign button for Site ID " + siteIdToVerify + " is present.");

        } catch (NoSuchElementException e) {
            extentReports.createTest("Verify Unassign Button").log(Status.PASS,
                    "Unassign button for the specified site ID is not present.");

        } catch (Exception e) {
            extentReports.createTest("Verify Unassign Button").log(Status.FAIL,
                    "An error occurred. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 11)
    public void clickAssignSiteButton() {
        try {
            WebElement assignSiteButton = driver
                    .findElement(By.cssSelector("button.btn-outline-primary.float-right.btn-assign"));

            assignSiteButton.click();

            extentReports.createTest("Click Assign Site Button").log(Status.PASS,
                    "Successfully clicked the Assign Site button.");

        } catch (Exception e) {
            extentReports.createTest("Click Assign Site Button").log(Status.FAIL,
                    "Failed to click the Assign Site button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 13)
    public void selectSiteFromDropdown() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement dropdown = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("assignSiteMngr")));
            dropdown.click();
            Thread.sleep(3000);

            extentReports.createTest("Select Site from Dropdown").log(Status.PASS,
                    "Successfully selected the site from the dropdown.");

        } catch (Exception e) {
            extentReports.createTest("Select Site from Dropdown").log(Status.FAIL,
                    "Failed to select the site from the dropdown. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 14)
    public void selectSite() {

        WebElement searchField = driver.findElement(By.id("assignSiteMngr"));

        List<WebElement> sitesList = searchField.findElements(By.xpath("//li[@role='treeitem']"));
        for (WebElement list : sitesList) {
            System.out.println(list.getText());
            list.click();
            System.out.println(sitesList.size());
            searchField.sendKeys("ITI Road");

            extentReports.createTest("Dropdown open and selected the site ")
                    .log(Status.PASS, "Successfully open dropdown and selected the site " + sitesList);
        }
        extentReports.createTest("Dropdown open and selected the site ")
                .log(Status.FAIL, "Failed open dropdown and selected the site " + sitesList);

    }

    @Test(priority = 15)
    public void clickAddSitesButton() {
        try {
            WebElement assignSitesButton = driver.findElement(By.id("btnSitrAsgn"));
            Thread.sleep(3000);

            assignSitesButton.click();

            extentReports.createTest("Click add Assign Sites Button").log(Status.PASS,
                    "Successfully clicked the Assign Sites button.");

        } catch (Exception e) {
            extentReports.createTest("Click add Assign Sites Button").log(Status.FAIL,
                    "Failed to click the Assign Sites button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 16)
    public void extractPopupTextAssign() {
        extractPopupTextManager();
    }

    @Test(priority = 17)
    public void statusOKAssign() {
        try {
            Thread.sleep(5000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status of assign manager to the site").log(Status.PASS,
                    "Clicked the 'OK' button in users page.");
        } catch (Exception e) {
            extentReports.createTest("Status of assign manager to the site").log(Status.FAIL,
                    "Failed to click the 'OK' button in users page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 18)
    public void verifyTableAfterAssign() {
        try {
            String siteIdToVerify = "3";

            extentReports.createTest("Verify Assign Button").log(Status.PASS,
                    "Unassign button for Site ID " + siteIdToVerify + " is present.");

        } catch (NoSuchElementException e) {
            extentReports.createTest("Verify Assign Button").log(Status.FAIL,
                    "Unassign button for the specified site ID is not present.");

        } catch (Exception e) {
            extentReports.createTest("Verify Assign Button").log(Status.FAIL,
                    "An error occurred. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 19)
    public void clickAttendersTab() {
        try {
            Thread.sleep(5000);
            WebElement attendersTab = driver.findElement(By.id("pills-attender-tab"));
            attendersTab.click();

            extentReports.createTest("Click Attender tab").log(Status.PASS,
                    "Attenders tab clicked successfully.");

            extentReports.createTest("Click Attender tab").log(Status.INFO,
                    "Additional information or data about the Attenders tab.");

        } catch (Exception e) {
            extentReports.createTest("Click Attender tab").log(Status.FAIL,
                    "Failed to click Attenders tab. Exception: " + e.getMessage());

            extentReports.createTest("Click Attender tab").log(Status.INFO,
                    "Additional information or data about the failure.");
        }
    }

    @Test(priority = 20)
    public void unassignAttender() {
        try {
            WebElement unassignButton = driver
                    .findElement(By.cssSelector("i.fa.fa-times.unassignAttender[attenderid='211'][siteid='2']"));
            unassignButton.click();

            extentReports.createTest("Select Attender").log(Status.PASS,
                    "Attender selected successfully.");

        } catch (Exception e) {
            extentReports.createTest("Select Attender").log(Status.FAIL,
                    "Failed to select attender. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 21)
    public void extractPopupWarningTextAttender() {
        try {
            Thread.sleep(5000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Warning Popup Text while unassign attender").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while unassign attender").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Warning Popup Text  while unassign attender").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 22)
    public void statusOKAttender() {
        try {
            Thread.sleep(3000);
            WebElement yesButton = driver.findElement(By.className("swal2-confirm"));
            yesButton.click();
            String button = yesButton.getText();

            extentReports.createTest("Status of unassign attender to the site").log(Status.PASS,
                    "Clicked the 'OK' button in users page. " + button);
        } catch (Exception e) {
            extentReports.createTest("Status of unassign attender to the site").log(Status.FAIL,
                    "Failed to click the 'OK' button in users page. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void extractPopupTextAttender() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while unassign site").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while unassign site").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while unassign site").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 24)
    public void statusOKUnAssignAttender() {
        try {
            Thread.sleep(5000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status of unassign attender to the site").log(Status.PASS,
                    "Clicked the 'OK' button in users page.");
        } catch (Exception e) {
            extentReports.createTest("Status of unassign attender to the site").log(Status.FAIL,
                    "Failed to click the 'OK' button in users page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 25)
    public void attendreCount() {
        try {
            Thread.sleep(5000);
            WebElement dropdown = driver.findElement(By.name("attenderTableList_length"));
            Select select = new Select(dropdown);
            select.selectByValue("25");
            String popup1Text = dropdown.getText();

            extentReports.createTest("select attender Count").log(Status.PASS, "Successfully selected as" + popup1Text);
        } catch (Exception e) {
            extentReports.createTest("select attender Count").log(Status.FAIL,
                    "Failed to select the count. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 26)
    public void searchAttender() {
        try {
            Thread.sleep(5000);
            WebElement searchInput = driver.findElement(By.cssSelector("input[aria-controls='attenderTableList']"));
            searchInput.sendKeys("");
            searchInput.sendKeys(Keys.RETURN);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#attenderTableList tbody tr")));

            List<WebElement> searchResults = driver.findElements(By.cssSelector("#attenderTableList tbody tr"));

            extentReports.createTest("Search attender")
                    .log(Status.PASS, "Successfully performed search with 'city'")
                    .info("Number of search results: " + searchResults.size());
        } catch (Exception e) {
            extentReports.createTest("Search attender")
                    .log(Status.FAIL, "Failed to perform search. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 27)
    public void clickAddMoreButton() {
        try {
            Thread.sleep(3000);
            WebElement addMoreButton = driver
                    .findElement(By.xpath("//*[@id='attenderTableList']/tbody/tr[2]/td[4]/button"));
            addMoreButton.click();

            extentReports.createTest("Click Add More Button")
                    .log(Status.PASS, "Clicked on the 'Add More' button successfully.");
        } catch (Exception e) {
            extentReports.createTest("Click Add More Button")
                    .log(Status.FAIL, "Failed to click the 'Add More' button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 28)
    public void clickDropdownButton() {
        try {
            Thread.sleep(3000);
            WebElement dropdownButton = driver.findElement(By.id("addAttendrSite"));
            dropdownButton.click();

            extentReports.createTest("Click Dropdown Button")
                    .log(Status.PASS, "Clicked on the dropdown button successfully.");
        } catch (Exception e) {
            extentReports.createTest("Click Dropdown Button")
                    .log(Status.FAIL, "Failed to click the dropdown button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 29)
    public void selectAttenderListItem() {
        try {
            WebElement searchField = driver.findElement(By.id("addAttendrSite"));

            List<WebElement> sitesList = searchField
                    .findElements(By.xpath("//li[@role='treeitem']"));
            for (WebElement list : sitesList) {
                System.out.println(list.getText());
                list.click();
                System.out.println(sitesList.size());
                searchField.sendKeys("alekhya ");

                extentReports.createTest("Dropdown open and selected the attender ")
                        .log(Status.PASS, "Successfully open dropdown and selected the attender " + sitesList);
            }
            extentReports.createTest("Dropdown open and selected the attender ")
                    .log(Status.FAIL, "Failed open dropdown and selected the attender " + sitesList);

        } catch (Exception e) {
            extentReports.createTest("Dropdown open and selected the attender ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected the attender . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 30)
    public void clickAssignAttenderButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement dropdown = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSiteAttndr")));
            dropdown.click();

            extentReports.createTest("Click Assign Attender Button")
                    .log(Status.PASS, "Clicked on the 'Assign Attender' button successfully.");
        } catch (Exception e) {
            extentReports.createTest("Click Assign Attender Button")
                    .log(Status.FAIL, "Failed to click the 'Assign Attender' button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 31)
    public void extractPopupTextAddAttender() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while assign attender to the site ").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while assign attendr to the site").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while assign attendr to the site").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 32)
    public void statusOKAddAttender() {
        try {
            Thread.sleep(5000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status of assign attendr to the site").log(Status.PASS,
                    "Clicked the 'OK' button in users page.");
        } catch (Exception e) {
            extentReports.createTest("Status of assign attendr to the site").log(Status.FAIL,
                    "Failed to click the 'OK' button in users page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 33)
    public void logout() {
        try {
            Thread.sleep(2000);

            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("LogOut").log(Status.FAIL, "Failed to LogOut. Exception: " + e.getCause());
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