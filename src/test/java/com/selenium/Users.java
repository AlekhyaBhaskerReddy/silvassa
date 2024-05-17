package com.selenium;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.model.ServerCredentials;

public class Users extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Users.html");
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

            WebElement usersPageLink = driver
                    .findElement(By.className("um-pg"));
            if (usersPageLink.isDisplayed()) {
                usersPageLink.click();
                extentReports.createTest("Users Page ").log(Status.PASS, "Successfully Open the users page");
            } else {
                extentReports.createTest("Users Page ").log(Status.INFO, "Users page not found");
                Assert.fail("Users page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("Users Page ").log(Status.FAIL,
                    "Failed to click the users page. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 4)
    public void clickAddUserButton() {
        try {
            Thread.sleep(3000);
            WebElement addUserButton = driver.findElement(By.id("addUserMng"));
            addUserButton.click();

            extentReports.createTest("Click Add User Button")
                    .log(Status.PASS, "Successfully clicked the 'Add User' button");
        } catch (Exception e) {
            extentReports.createTest("Click Add User Button")
                    .log(Status.FAIL, "Failed to click the 'Add User' button. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 5)
    public void fillAndSubmitUserForm() {
        try {
            WebElement userNameInput = driver.findElement(By.name("UserName"));
            WebElement userRoleDropdown = driver.findElement(By.id("userRole"));
            WebElement emailInput = driver.findElement(By.name("Email"));
            WebElement mobileInput = driver.findElement(By.name("Mobile"));
            WebElement departmentInput = driver.findElement(By.name("Department"));
            WebElement addressInput = driver.findElement(By.name("Address"));
            WebElement passwordInput = driver.findElement(By.name("Password"));
            WebElement confirmPasswordInput = driver.findElement(By.name("cnfrmPwd"));

            String userName = "desk";
            String userRole = "ADMIN";
            String userEmail = "desk@example.com";
            String userMobile = "9874563210";
            String userDepartment = "IT";
            String userAddress = "123 Main Street, Bangalore, Karnataka";
            String userPassword = "welcome@1";
            String confirmPassword = "welcome@1";

            userNameInput.sendKeys(userName);
            Select userRoleSelect = new Select(userRoleDropdown);
            userRoleSelect.selectByValue(userRole);
            emailInput.sendKeys(userEmail);
            mobileInput.sendKeys(userMobile);
            departmentInput.sendKeys(userDepartment);
            addressInput.sendKeys(userAddress);
            passwordInput.sendKeys(userPassword);
            confirmPasswordInput.sendKeys(confirmPassword);

            extentReports.createTest("Fill User Form")
                    .log(Status.PASS, "user Name: " + userName)
                    .log(Status.PASS, "user Role: " + userRole)
                    .log(Status.PASS, "user Email: " + userEmail)
                    .log(Status.PASS, "user Mobile: " + userMobile)
                    .log(Status.PASS, "user Department: " + userDepartment)
                    .log(Status.PASS, "user Address : " + userAddress)
                    .log(Status.PASS, "user Password: " + userPassword)
                    .log(Status.PASS, "confirm Password: " + confirmPassword);

        } catch (Exception e) {
            extentReports.createTest("Fill User Form")
                    .log(Status.FAIL, "Failed to fill the user form. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 6)
    public void clickSaveButton() {
        try {
            Thread.sleep(3000);
            WebElement saveButton = driver.findElement(By.id("btnSaveMngUser"));
            saveButton.click();

            extentReports.createTest("Click Save Button")
                    .log(Status.PASS, "Successfully clicked the Save button");
        } catch (Exception e) {
            extentReports.createTest("Click Save Button")
                    .log(Status.FAIL, "Failed to click the Save button. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 7)
    public void extractPopupText() {
        try {
            Thread.sleep(3000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while creating user").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while creating user").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while creating user").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 8)
    public void statusOK() {
        try {
            Thread.sleep(5000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status of adding admin user").log(Status.PASS,
                    "Clicked the 'OK' button in users page.");
        } catch (Exception e) {
            extentReports.createTest("Status of adding admin user").log(Status.FAIL,
                    "Failed to click the 'OK' button in users page. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 9)
    public void addUsersPopUpClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='addUsrMngModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In users page POP Up").log(Status.PASS,
                    "Successfully closed the pop up of add users page");
        } catch (Exception e) {
            extentReports.createTest("In users page POP Up ").log(Status.FAIL,
                    "Failed to close the pop up of users page. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 10)
    public void verifyAdminUserDetailsInTable() {
        try {
            Thread.sleep(5000);
            String expectedUserName = "desk";
            String expectedUserRole = "ADMIN";
            String expectedUserEmail = "desk@example.com";

            String userRowXPath = "//tr[contains(.,'" + expectedUserName + "') and contains(.,'" + expectedUserRole
                    + "') and contains(.,'" + expectedUserEmail + "')]";

            WebElement userRow = driver.findElement(By.xpath(userRowXPath));

            Assert.assertNotNull(userRow);

            extentReports.createTest("Verify Admin User Details in Table")
                    .log(Status.PASS, "Admin user details are present in the userMgmtTable");

        } catch (Exception e) {
            extentReports.createTest("Verify Admin User Details in Table")
                    .log(Status.FAIL,
                            "Failed to verify admin user details in the userMgmtTable. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 10)
    public void clickAddUserButtonTwice() throws InterruptedException {
        Thread.sleep(5000);
        clickAddUserButton();
    }

    // @Test(priority = 11)
    public void fillAttederUserForm() {
        try {
            WebElement userNameInput = driver.findElement(By.name("UserName"));
            WebElement userRoleDropdown = driver.findElement(By.id("userRole"));
            WebElement emailInput = driver.findElement(By.name("Email"));
            WebElement mobileInput = driver.findElement(By.name("Mobile"));
            WebElement departmentInput = driver.findElement(By.name("Department"));
            WebElement addressInput = driver.findElement(By.name("Address"));
            WebElement passwordInput = driver.findElement(By.name("Password"));
            WebElement confirmPasswordInput = driver.findElement(By.name("cnfrmPwd"));

            String userName = "mpos";
            String userRole = "ATTENDER";
            String userEmail = "mpos@example.com";
            String userMobile = "7418529630";
            String userDepartment = "IT";
            String userAddress = "123 Main Street, Bangalore, Karnataka";
            String userPassword = "welcome@1";
            String confirmPassword = "welcome@1";

            userNameInput.sendKeys(userName);
            Select userRoleSelect = new Select(userRoleDropdown);
            userRoleSelect.selectByValue(userRole);
            emailInput.sendKeys(userEmail);
            mobileInput.sendKeys(userMobile);
            departmentInput.sendKeys(userDepartment);
            addressInput.sendKeys(userAddress);
            passwordInput.sendKeys(userPassword);
            confirmPasswordInput.sendKeys(confirmPassword);

            extentReports.createTest("Fill Attender User Form")
                    .log(Status.PASS, "user Name: " + userName)
                    .log(Status.PASS, "user Role: " + userRole)
                    .log(Status.PASS, "user Email: " + userEmail)
                    .log(Status.PASS, "user Mobile: " + userMobile)
                    .log(Status.PASS, "user Department: " + userDepartment)
                    .log(Status.PASS, "user Address : " + userAddress)
                    .log(Status.PASS, "user Password: " + userPassword)
                    .log(Status.PASS, "confirm Password: " + confirmPassword);

        } catch (Exception e) {
            extentReports.createTest("Fill Attender User Form")
                    .log(Status.FAIL, "Failed to fill the user form. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 12)
    public void clickAttenderSaveButton() {
        clickSaveButton();
    }

    // @Test(priority = 13)
    public void extractPopupTextAttender() {
        extractPopupText();
    }

    // @Test(priority = 14)
    public void statusOKAttender() {
        statusOK();
    }

    // @Test(priority = 15)
    public void addAttenderUsersPopUpClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='addUsrMngModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In users page POP Up").log(Status.PASS,
                    "Successfully closed the pop up of add users page");
        } catch (Exception e) {
            extentReports.createTest("In users page POP Up ").log(Status.FAIL,
                    "Failed to close the pop up of users page. Exception: " + e.getCause());
        }
    }

    // @Test(priority = 16)
    public void verifyAttenderUserDetailsInTable() {
        try {
            Thread.sleep(5000);
            String expectedUserName = "mpos";
            String expectedUserRole = "ATTENDER";
            String expectedUserEmail = "mpos@example.com";

            String userRowXPath = "//tr[contains(.,'" + expectedUserName + "') and contains(.,'" + expectedUserRole
                    + "') and contains(.,'" + expectedUserEmail + "')]";

            WebElement userRow = driver.findElement(By.xpath(userRowXPath));

            Assert.assertNotNull(userRow);

            extentReports.createTest("Verify Attender User Details in Table")
                    .log(Status.PASS, "Attender user details are present in the user management Table");

        } catch (Exception e) {
            extentReports.createTest("Verify Attender User Details in Table")
                    .log(Status.FAIL,
                            "Failed to verify Attender user details in the user management table. Exception: "
                                    + e.getMessage());
        }
    }

    // @Test(priority = 17)
    public void verifyAdminActions() {
        try {
            List<WebElement> adminRoleRows = driver.findElements(By.xpath("//tbody/tr[contains(td[2], 'ADMIN')]"));

            for (WebElement adminUserRow : adminRoleRows) {
                WebElement editButton = adminUserRow.findElement(By.cssSelector("button.editUserRole"));
                WebElement deleteButton = adminUserRow.findElement(By.cssSelector("button.delUserRole"));

                if (editButton.isDisplayed() && !editButton.isEnabled()) {
                    extentReports.createTest("Edit Button Status").log(Status.PASS,
                            "Edit button for ADMIN user is present but uneditable");
                } else {
                    extentReports.createTest("Edit Button Status").log(Status.FAIL,
                            "Edit button for ADMIN user is unexpectedly editable");
                }

                if (deleteButton.isDisplayed() && !deleteButton.isEnabled()) {
                    extentReports.createTest("Delete Button Status").log(Status.PASS,
                            "Delete button for ADMIN user is present but uneditable");
                } else {
                    extentReports.createTest("Delete Button Status").log(Status.FAIL,
                            "Delete button for ADMIN user is unexpectedly editable");
                }
            }
        } catch (Exception e) {
            extentReports.createTest("Admin user Buttons Status").log(Status.FAIL,
                    "Failed to verify admin user buttons status. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 18)
    public void verifyAttenderUserEdit() {
        try {
            List<WebElement> attenderRows = driver.findElements(By.xpath("//tbody/tr[contains(td[3], 'ATTENDER')]"));

            for (WebElement attenderRow : attenderRows) {
                WebElement editButton = attenderRow.findElement(By.cssSelector("button.editUserMng"));

                if (editButton.isEnabled()) {
                    extentReports.createTest("Edit Button Status").log(Status.PASS,
                            "Edit button for ATTENDER user is present and editable");
                } else {
                    extentReports.createTest("Edit Button Status").log(Status.FAIL,
                            "Edit button for ATTENDER user is either not present or not editable");
                }
            }
        } catch (Exception e) {
            extentReports.createTest("Attender User edit Buttons Status").log(Status.FAIL,
                    "Failed to verify ATTENDER user buttons status. Exception: " + e.getMessage());
        }
    }

    public void updateAttenderUserForm() {
        try {
            WebElement attenderRow = driver.findElement(By.xpath(
                    "//button[contains(@class, 'editUserMng') and contains(@class, 'btn-outline-info') and contains(@class, 'btn-sm')]"));
            attenderRow.click();
            WebElement addressInput = driver.findElement(By.name("userAdd"));
            WebElement passwordInput = driver.findElement(By.id("userPwd"));
            WebElement confirmPasswordInput = driver.findElement(By.name("cnfrmPwd"));

            String userAddress = "123 Main Street, Bangalore, Karnataka";
            String userPassword = "welcome@1";
            String confirmPassword = "welcome@1";

            addressInput.sendKeys(userAddress);
            passwordInput.sendKeys(userPassword);
            confirmPasswordInput.sendKeys(confirmPassword);

            extentReports.createTest("Fill the update attendert User Form")
                    .log(Status.PASS, "user Address : " + userAddress)
                    .log(Status.PASS, "user Password: " + userPassword)
                    .log(Status.PASS, "confirm Password: " + confirmPassword);

        } catch (Exception e) {
            extentReports.createTest("Fill the update attender User Form")
                    .log(Status.FAIL, "Failed to fill the user form. Exception: " + e.getMessage());
        }
    }

    public void extractPopupTextUpdateAttender() {
        extractPopupText();
    }

    public void statusOKUpdateAttender() {
        statusOK();
    }

    // @Test(priority = 22)
    public void verifyAttenderUserDelete() {
        try {
            Thread.sleep(3000);
            WebElement attenderUserRow = driver.findElement(By.xpath("//tbody/tr[contains(td[3], 'ATTENDER')]"));
            WebElement deleteButton = attenderUserRow.findElement(By.cssSelector("button.delUserMng"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();
                extentReports.createTest("Delete Attender user").log(Status.PASS,
                        "Delete button is present and enabled");
            } else {
                extentReports.createTest("Delete Attender user").log(Status.FAIL,
                        "Delete button is not present or not enabled");
            }
        } catch (Exception e) {
            extentReports.createTest("Attender user Actions").log(Status.FAIL,
                    "Failed to perform manager user actions. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 23)
    public void confirmAttenderDeleteAction() {
        try {
            WebElement deleteButton = driver.findElement(By.cssSelector("button.swal2-confirm"));

            if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
                deleteButton.click();

                extentReports.createTest("Confirm Delete attender user").log(Status.PASS,
                        "Clicked the 'Yes, Delete it!' button of attender user");
            } else {
                extentReports.createTest("Confirm Delete attender user").log(Status.FAIL,
                        "'Yes, Delete it!' button is not present or not enabled");
            }

        } catch (Exception e) {
            extentReports.createTest("Confirm Delete attender user").log(Status.FAIL,
                    "Failed to confirm delete action of attender. Exception: " + e.getMessage());
        }
    }

    // @Test(priority = 24)
    public void extractPopupTextDeleteAttender() throws InterruptedException {
        Thread.sleep(5000);
        extractPopupText();
    }

    // @Test(priority = 25)
    public void statusOKDeleteAttender() {
        statusOK();
    }

    // @Test(priority = 26)
    public void verifyAttenderRoleDeletion() {
        try {
            Thread.sleep(3000);
            String attenderUsername = "mpos";

            List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='userMgmtTable']/tbody/tr"));

            boolean attenderUserFound = false;

            for (WebElement row : tableRows) {
                WebElement usernameCell = row.findElement(By.xpath("td[2]"));
                if (usernameCell.getText().equalsIgnoreCase(attenderUsername)) {
                    attenderUserFound = true;
                    break;
                }
            }

            if (attenderUserFound) {
                extentReports.createTest("Verify Deleted Attender User")
                        .log(Status.FAIL, "Deleted attender user is unexpectedly present in the table");
            } else {
                extentReports.createTest("Verify Deleted Attender User")
                        .log(Status.PASS, "Deleted attender user is not present in the table");
            }

        } catch (Exception e) {
            extentReports.createTest("Verify Deleted Attender User")
                    .log(Status.FAIL, "Failed to verify deleted attender user. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 27)
    public void tableNextPage() {
        try {
            Thread.sleep(3000);
            WebElement nextButton = driver.findElement(By.id("userMgmtTable_next"));

            while (!nextButton.getAttribute("class").contains("disabled")) {
                nextButton.click();
                Thread.sleep(3000);
                nextButton = driver.findElement(By.id("userMgmtTable_next"));
            }
            WebElement previousButton = driver.findElement(By.id("userMgmtTable_previous"));
            previousButton.click();

            extentReports.createTest("Opened table list ").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extentReports.createTest("Opened table list").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    @Test(priority = 28)
    public void searchAndVerifyAdminCount() {
        try {
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.cssSelector("#userMgmtTable_filter input"));

            searchInput.sendKeys("ADMIN");
            Thread.sleep(2000);
            searchInput.clear();
            Thread.sleep(2000);

            int adminCount = driver.findElements(By.xpath("//tbody/tr[contains(td[3], 'ADMIN')]")).size();

            extentReports.createTest("Admin Count Verification").log(Status.PASS,
                    "Number of admin roles: " + adminCount);

        } catch (Exception e) {
            extentReports.createTest("Admin Count Verification").log(Status.FAIL,
                    "Failed to search for admin roles. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 29)
    public void downloadCSV() {
        try {
            Thread.sleep(5000);
            List<WebElement> dataTableButtons = driver.findElements(By.className("dt-button"));

            WebElement csvButton = null;
            for (WebElement button : dataTableButtons) {
                if (button.getText().equalsIgnoreCase("CSV")) {
                    csvButton = button;
                    break;
                }
            }

            if (csvButton != null) {
                csvButton.click();
                extentReports.createTest("CSV Download").log(Status.PASS, "CSV file is downloaded successfully");
            } else {
                extentReports.createTest("CSV Download").log(Status.FAIL, "CSV button not found");
            }

        } catch (Exception e) {
            extentReports.createTest("CSV Download").log(Status.FAIL,
                    "Failed to download CSV file. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 30)
    public void downloadPDF() {
        try {
            Thread.sleep(3000);
            List<WebElement> dataTableButtons = driver.findElements(By.className("dt-button"));

            WebElement pdfButton = null;
            for (WebElement button : dataTableButtons) {
                if (button.getText().equalsIgnoreCase("PDF")) {
                    pdfButton = button;
                    break;
                }
            }

            if (pdfButton != null) {
                pdfButton.click();
                extentReports.createTest("PDF Download").log(Status.PASS, "PDF file is downloaded successfully");
            } else {
                extentReports.createTest("PDF Download").log(Status.FAIL, "PDF button not found");
            }

        } catch (Exception e) {
            extentReports.createTest("PDF Download").log(Status.FAIL,
                    "Failed to download PDF file. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 31)
    public void logout() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.id("userName")).click();
            driver.findElement(By.id("btnLogout")).click();
            extentReports.createTest("Users Page LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
        } catch (Exception e) {
            extentReports.createTest("Users Page LogOut").log(Status.FAIL,
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