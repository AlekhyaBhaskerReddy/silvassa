package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.model.ServerCredentials;

public class Roles extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {
        extentReports = new ExtentReports();
        spark = new ExtentSparkReporter("results/Roles.html");
        extentReports.attachReporter(spark);
        extentTest = extentReports.createTest("demo");

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
    public void revenueDashboard() {
        Dashboard.revenuedashboardOpen(driver, extentReports);
    }

    @Test(priority = 3)
    public void userMgmtPage() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[6]")).click();

            WebElement rolesPageLink = driver
                    .findElement(By.className("ur-pg"));
            if (rolesPageLink.isDisplayed()) {
                rolesPageLink.click();
                extentReports.createTest("Roles Page ").log(Status.PASS, "Successfully Open the Roles page");
            } else {
                extentReports.createTest("Roles Page ").log(Status.INFO, "Roles page not found");
                Assert.fail("Roles page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("Roles Page ").log(Status.FAIL,
                    "Failed to click the Roles page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void clickAddRoleBtn() {
        try {
            Thread.sleep(5000);
            WebElement addRoleButton = driver.findElement(By.id("addUserMng"));
            addRoleButton.click();

            extentReports.createTest("Click Add Role Button").log(Status.PASS,
                    "Successfully clicked the Add Role button");
        } catch (Exception e) {
            extentReports.createTest("Click Add Role Button").log(Status.FAIL,
                    "Failed to click the Add Role button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void selectAdminRole() {
        try {
            WebElement userRoleDropdown = driver.findElement(By.id("userRole"));
            Select select = new Select(userRoleDropdown);
            select.selectByValue("ADMIN");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedRole = selectedOption.getText();

            Assert.assertEquals(selectedRole, "Admin", "Failed to select Admin role");

            extentReports.createTest("Select Admin Role").log(Status.PASS, "Successfully selected :  " + selectedRole);
        } catch (Exception e) {
            extentReports.createTest("Select Admin Role").log(Status.FAIL,
                    "Failed to select Admin role. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void verifyAutoFillForAdminRole() {
        try {
            WebElement userRoleDropdown = driver.findElement(By.id("userRole"));
            Select userRoleSelect = new Select(userRoleDropdown);
            userRoleSelect.selectByValue("ADMIN");

            Thread.sleep(3000);

            WebElement accessTypeDropdown = driver.findElement(By.id("accessType"));
            WebElement accessLevelDropdown = driver.findElement(By.id("accessLevel"));

            Select accessTypeSelect = new Select(accessTypeDropdown);
            Select accessLevelSelect = new Select(accessLevelDropdown);

            WebElement selectedAccessType = accessTypeSelect.getFirstSelectedOption();
            WebElement selectedAccessLevel = accessLevelSelect.getFirstSelectedOption();

            String accessTypeValue = selectedAccessType.getText();
            String accessLevelValue = selectedAccessLevel.getText();

            Assert.assertEquals(accessTypeValue, "Auto-Filled-Value-For-AccessType", null);
            Assert.assertEquals(accessLevelValue, "Auto-Filled-Value-For-AccessLevel", null);

            extentReports.createTest("Verify Auto-fill for Admin Role")
                    .log(Status.PASS, "Auto-fill verified successfully. Access Type: " + accessTypeValue +
                            ", Access Level: " + accessLevelValue);
        } catch (Exception e) {
            extentReports.createTest("Verify Auto-fill for Admin Role")
                    .log(Status.FAIL, "Failed to verify auto-fill. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 7)
    public void clickSaveButton() {
        try {
            WebElement saveButton = driver.findElement(By.id("btnSaveRole"));
            saveButton.click();

            extentReports.createTest("Click Save Button").log(Status.PASS, "Save button clicked successfully");
        } catch (Exception e) {
            extentReports.createTest("Click Save Button").log(Status.FAIL,
                    "Failed to click the Save button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void extractPopupText() {
        try {
            Thread.sleep(2000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while creating role").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while creating role").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while creating role").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 9)
    public void statusOK() {
        try {
            Thread.sleep(2000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status").log(Status.PASS,
                    "Clicked the 'OK' button in roles page.");
        } catch (Exception e) {
            extentReports.createTest("Status").log(Status.FAIL,
                    "Failed to click the 'OK' button in roles page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 10)
    public void addRolesPopUpClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@data-dismiss='modal']")).click();
            extentReports.createTest("In roles page POP Up").log(Status.PASS,
                    "Successfully closed the pop up of add roles page");
        } catch (Exception e) {
            extentReports.createTest("In roles page POP Up ").log(Status.FAIL,
                    "Failed to close the pop up of roles page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void verifyAdminActions() {
        try {
            List<WebElement> adminRoleRows = driver.findElements(By.xpath("//tbody/tr[contains(td[2], 'ADMIN')]"));

            for (WebElement adminRoleRow : adminRoleRows) {
                WebElement editButton = adminRoleRow.findElement(By.cssSelector("button.editUserRole"));
                WebElement deleteButton = adminRoleRow.findElement(By.cssSelector("button.delUserRole"));

                if (editButton.isDisplayed() && !editButton.isEnabled()) {
                    extentReports.createTest("Edit Button Status").log(Status.PASS,
                            "Edit button for ADMIN role is present but uneditable");
                } else {
                    extentReports.createTest("Edit Button Status").log(Status.FAIL,
                            "Edit button for ADMIN role is unexpectedly editable");
                }

                if (deleteButton.isDisplayed() && !deleteButton.isEnabled()) {
                    extentReports.createTest("Delete Button Status").log(Status.PASS,
                            "Delete button for ADMIN role is present but uneditable");
                } else {
                    extentReports.createTest("Delete Button Status").log(Status.FAIL,
                            "Delete button for ADMIN role is unexpectedly editable");
                }
            }
        } catch (Exception e) {
            extentReports.createTest("Admin Role Buttons Status").log(Status.FAIL,
                    "Failed to verify admin role buttons status. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 12)
    public void clickCSVButton() {
        try {
            Thread.sleep(3000);
            WebElement csvButton = driver
                    .findElement(By.xpath("//div[@id='userRoleTable_wrapper']/div[1]/button[1]"));

            if (csvButton.isDisplayed()) {
                csvButton.click();
                extentReports.createTest("CSV Button").log(Status.PASS, "Successfully clicked the CSV button");
            } else {
                extentReports.createTest("CSV Button").log(Status.INFO, "CSV button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("CSV Button").log(Status.FAIL,
                    "Failed to click the CSV button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void clickPDFButton() {
        try {
            Thread.sleep(3000);
            WebElement pdfButton = driver
                    .findElement(By.xpath("//div[@id='userRoleTable_wrapper']/div[1]/button[2]"));

            if (pdfButton.isDisplayed()) {
                pdfButton.click();

                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait1.until(ExpectedConditions.numberOfWindowsToBe(2));
                String mainWindowHandle = driver.getWindowHandle();
                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    if (!windowHandle.equals(mainWindowHandle)) {

                        driver.switchTo().window(windowHandle);
                        driver.close();

                        driver.switchTo().window(mainWindowHandle);
                        extentReports.createTest("PDF Button").log(Status.PASS, "Successfully clicked the PDF button");
                        return;

                    }
                }

            } else {
                extentReports.createTest("PDF Button").log(Status.INFO, "PDF button not found");
            }
        } catch (

        Exception e) {
            extentReports.createTest("In POS reports download the PDF file ").log(Status.FAIL,
                    "Failed to download the PDF file in POS Revenue tab. Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void performManagerRoleEdit() {
        try {
            Thread.sleep(4000);
            WebElement managerRoleRow = driver.findElement(By.xpath("//td[text()='MANAGER']/ancestor::tr"));

            WebElement editButton = managerRoleRow.findElement(By.cssSelector("button.editUserRole"));
            if (editButton.isDisplayed() && editButton.isEnabled()) {
                editButton.click();

                extentReports.createTest("Edit Manager Role").log(Status.PASS, "Edit button is present and editable");
            } else {
                extentReports.createTest("Edit Manager Role").log(Status.FAIL,
                        "Edit button is not present or not enabled");
            }
        } catch (Exception e) {
            extentReports.createTest("Manager Role Actions").log(Status.FAIL,
                    "Failed to perform manager role actions. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 15)
    public void selectAccessTypeRead() {
        try {
            WebElement accessTypeDropdown = driver
                    .findElement(By.xpath("//select[@id='accessType']/option[@value='WRITE']"));
            accessTypeDropdown.click();
            extentReports.createTest("Select Access Type Read").log(Status.PASS,
                    "Successfully selected 'Read' in Access Type dropdown");
        } catch (Exception e) {
            extentReports.createTest("Select Access Type Read").log(Status.FAIL,
                    "Failed to select 'Read' in Access Type dropdown. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 16)
    public void getEditRoleDetails() {
        try {
            Thread.sleep(3000);
            WebElement userRoleIdInput = driver.findElement(By.id("userRoleid"));
            WebElement userRoleInput = driver.findElement(By.id("userRole"));
            WebElement accessLevelInput = driver.findElement(By.id("accessLevel"));
            WebElement accessTypeDropdown = driver.findElement(By.id("accessType"));

            String userRoleId = userRoleIdInput.getAttribute("value");
            String userRole = userRoleInput.getAttribute("value");
            String accessLevel = accessLevelInput.getAttribute("value");

            Select selectAccessType = new Select(accessTypeDropdown);
            WebElement selectedAccessTypeOption = selectAccessType.getFirstSelectedOption();
            String accessType = selectedAccessTypeOption.getText();

            extentReports.createTest("Edit Role Details")
                    .log(Status.INFO, "User Role ID: " + userRoleId)
                    .log(Status.INFO, "User Role: " + userRole)
                    .log(Status.INFO, "Access Level: " + accessLevel)
                    .log(Status.INFO, "Access Type: " + accessType)
                    .log(Status.PASS, "Successfully retrieved role details");
        } catch (Exception e) {
            extentReports.createTest("Edit Role Details")
                    .log(Status.FAIL, "Failed to retrieve role details. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    public void checkUpdateButton() {
        try {
            Thread.sleep(3000);
            WebElement updateButton = driver.findElement(By.id("btnEditRole"));

            if (updateButton.isDisplayed()) {
                extentReports.createTest("Update Button").log(Status.INFO, "Update button is present");
                updateButton.click();
            } else {
                extentReports.createTest("Update Button").log(Status.INFO, "Update button is not present");
            }

        } catch (Exception e) {
            extentReports.createTest("Update Button").log(Status.FAIL,
                    "Failed to check Update button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 18)
    public void updateUser() {
        extractPopupText();
    }

    @Test(priority = 19)
    public void updateStatusOK() {
        statusOK();
    }

    @Test(priority = 20)
    public void performManagerRoleDelete() {
        try {
            Thread.sleep(5000);
            WebElement managerRoleRow = driver.findElement(By.xpath("//td[text()='MANAGER']/ancestor::tr"));

            WebElement deleteButton = managerRoleRow.findElement(By.cssSelector("button.delUserRole"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();
                extentReports.createTest("Delete Manager Role").log(Status.PASS,
                        "Delete button is present and enabled");
            } else {
                extentReports.createTest("Delete Manager Role").log(Status.FAIL,
                        "Delete button is not present or not enabled");
            }
        } catch (Exception e) {
            extentReports.createTest("Manager Role Actions").log(Status.FAIL,
                    "Failed to perform manager role actions. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 21)
    public void confirmDeleteAction() {
        try {
            WebElement deleteButton = driver.findElement(By.cssSelector("button.swal2-confirm"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();

                extentReports.createTest("Confirm Delete").log(Status.PASS, "Clicked the 'Yes, Delete it!' button");
            } else {
                extentReports.createTest("Confirm Delete").log(Status.FAIL,
                        "'Yes, Delete it!' button is not present or not enabled");
            }

        } catch (Exception e) {
            extentReports.createTest("Confirm Delete").log(Status.FAIL,
                    "Failed to confirm delete action. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 22)
    public void verifyManagerRoleDeletion() {
        try {

            String managerRoleName = "MANAGER";

            WebElement managerRoleElement = driver.findElement(By.xpath("//td[text()='" + managerRoleName + "']"));

            if (!managerRoleElement.isDisplayed()) {
                extentReports.createTest("Verify Manager Role Deletion").log(Status.PASS,
                        "Manager role deleted successfully");
            } else {
                extentReports.createTest("Verify Manager Role Deletion").log(Status.FAIL,
                        "Manager role is still present after deletion");
            }

        } catch (Exception e) {
            extentReports.createTest("Verify Manager Role Deletion").log(Status.FAIL,
                    "Failed to verify manager role deletion. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 23)
    public void tableNextPage() {
        try {
            Thread.sleep(3000);
            WebElement nextButton = driver.findElement(By.id("userRoleTable_next"));

            while (!nextButton.getAttribute("class").contains("disabled")) {
                nextButton.click();
                Thread.sleep(3000);
                nextButton = driver.findElement(By.id("userRoleTable_next"));
            }
            WebElement previousButton = driver.findElement(By.id("userRoleTable_previous"));
            previousButton.click();

            extentReports.createTest("Opened table list ").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extentReports.createTest("Opened table list").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    @Test(priority = 24)
    public void clickAddRoleBtnTwice() {
        clickAddRoleBtn();
    }

    @Test(priority = 25)
    public void verifyAutoFillForAttenderRole() {
        try {
            WebElement userRoleDropdown = driver.findElement(By.id("userRole"));
            Select userRoleSelect = new Select(userRoleDropdown);
            userRoleSelect.selectByValue("ATTENDER");

            Thread.sleep(3000);

            WebElement accessTypeDropdown = driver.findElement(By.id("accessType"));
            WebElement accessLevelDropdown = driver.findElement(By.id("accessLevel"));

            Select accessTypeSelect = new Select(accessTypeDropdown);
            Select accessLevelSelect = new Select(accessLevelDropdown);

            WebElement selectedAccessType = accessTypeSelect.getFirstSelectedOption();
            WebElement selectedAccessLevel = accessLevelSelect.getFirstSelectedOption();

            String accessTypeValue = selectedAccessType.getText();
            String accessLevelValue = selectedAccessLevel.getText();

            Assert.assertEquals(accessTypeValue, "Auto-Filled-Value-For-AccessType", null);
            Assert.assertEquals(accessLevelValue, "Auto-Filled-Value-For-AccessLevel", null);

            extentReports.createTest("Verify Auto-fill for Admin Role")
                    .log(Status.PASS, "Auto-fill verified successfully. Access Type: " + accessTypeValue +
                            ", Access Level: " + accessLevelValue);
        } catch (Exception e) {
            extentReports.createTest("Verify Auto-fill for Admin Role")
                    .log(Status.FAIL, "Failed to verify auto-fill. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 26)
    public void clickSaveButtonTwice() {
        clickSaveButton();
    }

    @Test(priority = 27)
    public void extractPopupTextTwice() {
        extractPopupText();
    }

    @Test(priority = 28)
    public void statusOKTwice() {
        statusOK();
    }

    @Test(priority = 29)
    public void addRolesPopUpCloseTwice() {
        addRolesPopUpClose();
    }

    @Test(priority = 30)
    public void performAttenderRoleEdit() {
        try {
            Thread.sleep(4000);
            WebElement attenderRoleRow = driver.findElement(By.xpath("//td[text()='ATTENDER']/ancestor::tr"));

            WebElement editButton = attenderRoleRow.findElement(By.cssSelector("button.editUserRole"));
            if (editButton.isDisplayed() && editButton.isEnabled()) {
                editButton.click();

                extentReports.createTest("Edit Attender Role").log(Status.PASS, "Edit button is present and editable");
            } else {
                extentReports.createTest("Edit Attender Role").log(Status.FAIL,
                        "Edit button is not present or not enabled");
            }
        } catch (Exception e) {
            extentReports.createTest("Attender Role Actions").log(Status.FAIL,
                    "Failed to perform Attender role actions. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 31)
    public void selectAccessTypeAttenderRead() {
        try {
            WebElement accessTypeDropdown = driver
                    .findElement(By.xpath("//select[@id='accessType']/option[@value='READ']"));
            accessTypeDropdown.click();
            extentReports.createTest("Select Access Type of attender Read").log(Status.PASS,
                    "Successfully selected 'Read' in Access Type dropdown");
        } catch (Exception e) {
            extentReports.createTest("Select Access Type of attender Read").log(Status.FAIL,
                    "Failed to select 'Read' in Access Type dropdown. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 32)
    public void getEditAttenderRoleDetails() {
        try {
            Thread.sleep(3000);
            WebElement userRoleIdInput = driver.findElement(By.id("userRoleid"));
            WebElement userRoleInput = driver.findElement(By.id("userRole"));
            WebElement accessLevelInput = driver.findElement(By.id("accessLevel"));
            WebElement accessTypeDropdown = driver.findElement(By.id("accessType"));

            String userRoleId = userRoleIdInput.getAttribute("value");
            String userRole = userRoleInput.getAttribute("value");
            String accessLevel = accessLevelInput.getAttribute("value");

            Select selectAccessType = new Select(accessTypeDropdown);
            WebElement selectedAccessTypeOption = selectAccessType.getFirstSelectedOption();
            String accessType = selectedAccessTypeOption.getText();

            extentReports.createTest("Edit Role Details")
                    .log(Status.INFO, "User Role ID: " + userRoleId)
                    .log(Status.INFO, "User Role: " + userRole)
                    .log(Status.INFO, "Access Level: " + accessLevel)
                    .log(Status.INFO, "Access Type: " + accessType)
                    .log(Status.PASS, "Successfully retrieved attender role details");
        } catch (Exception e) {
            extentReports.createTest("Edit Role Details")
                    .log(Status.FAIL, "Failed to retrieve attender role details. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 33)
    public void checkAttenderUpdateButton() {
        try {
            Thread.sleep(3000);
            WebElement updateButton = driver.findElement(By.id("btnEditRole"));

            if (updateButton.isDisplayed()) {
                extentReports.createTest("Update Button").log(Status.INFO, "Update button is present");
                updateButton.click();
            } else {
                extentReports.createTest("Update Button").log(Status.INFO, "Update button is not present");
            }

        } catch (Exception e) {
            extentReports.createTest("Update Button").log(Status.FAIL,
                    "Failed to check Update button. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 34)
    public void updateUserTwice() {
        extractPopupText();
    }

    @Test(priority = 35)
    public void updateStatusOKTwice() {
        statusOK();
    }

    @Test(priority = 36)
    public void performAttenderRoleDelete() {
        try {
            Thread.sleep(5000);
            WebElement attenderRoleRow = driver.findElement(By.xpath("//td[text()='ATTENDER']/ancestor::tr"));

            WebElement deleteButton = attenderRoleRow.findElement(By.cssSelector("button.delUserRole"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();
                extentReports.createTest("Delete Attender Role").log(Status.PASS,
                        "Delete button is present and enabled");
            } else {
                extentReports.createTest("Delete Attender Role").log(Status.FAIL,
                        "Delete button is not present or not enabled");
            }
        } catch (Exception e) {
            extentReports.createTest("Attender Role Actions").log(Status.FAIL,
                    "Failed to perform manager role actions. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 37)
    public void confirmAttenderDeleteAction() {
        try {
            WebElement deleteButton = driver.findElement(By.cssSelector("button.swal2-confirm"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();

                extentReports.createTest("Confirm Delete").log(Status.PASS,
                        "Clicked the 'Yes, Delete it!' button of attender");
            } else {
                extentReports.createTest("Confirm Delete").log(Status.FAIL,
                        "'Yes, Delete it!' button is not present or not enabled");
            }

        } catch (Exception e) {
            extentReports.createTest("Confirm Delete").log(Status.FAIL,
                    "Failed to confirm delete action of attender. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 38)
    public void verifyAttenderRoleDeletion() {
        try {

            String attenderRoleName = "ATTENDER";

            WebElement attenderRoleElement = driver.findElement(By.xpath("//td[text()='" + attenderRoleName + "']"));

            if (!attenderRoleElement.isDisplayed()) {
                extentReports.createTest("Verify attender Role Deletion").log(Status.PASS,
                        "Attender role deleted successfully");
            } else {
                extentReports.createTest("Verify attender Role Deletion").log(Status.FAIL,
                        "Attender role is still present after deletion");
            }

        } catch (Exception e) {
            extentReports.createTest("Verify attender Role Deletion").log(Status.FAIL,
                    "Failed to verify manager role deletion. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 39)
    public void searchUserRole() {
        try {
            Thread.sleep(5000);
            WebElement searchInput = driver.findElement(By.cssSelector("input[aria-controls='userRoleTable']"));
            searchInput.sendKeys("city");
            searchInput.sendKeys(Keys.RETURN);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#userRoleTable tbody tr")));

            List<WebElement> searchResults = driver.findElements(By.cssSelector("#userRoleTable tbody tr"));

            extentReports.createTest("Search User Role")
                    .log(Status.PASS, "Successfully performed search with 'city'")
                    .info("Number of search results: " + searchResults.size());
        } catch (Exception e) {
            extentReports.createTest("Search User Role")
                    .log(Status.FAIL, "Failed to perform search. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 40)
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