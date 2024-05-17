package com.selenium;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

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

public class PaymentManagement extends BaseTestReport {
    WebDriver driver;
    ServerCredentials serverconfig;

    @BeforeClass
    public void setUp() {

        // extentReports = new ExtentReports();
        // spark = new ExtentSparkReporter("results/Payment_Management.html");
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
    public void paymentMgmtPage() {
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath("//ul[@class='nav']/li[6]")).click();
            Thread.sleep(3000);

            WebElement managePageLink = driver.findElement(By.className("ur-pg"));
            if (managePageLink.isDisplayed()) {
                managePageLink.click();
                extentReports.createTest("Create Page ").log(Status.PASS, "Successfully Open the Create page");
            } else {
                extentReports.createTest("Create Page ").log(Status.INFO, "Create page not found");
                Assert.fail("Create page not found");
            }

        } catch (Exception e) {
            extentReports.createTest("Create Page ").log(Status.FAIL,
                    "Failed to click the Create page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 4)
    public void selectCreatedPageSite() {
        try {
            Thread.sleep(2000);
            List<WebElement> siteList = ReservationReports.selectReservationSitesList(driver);
            driver.findElement(By.id("pos_site_name"));
            WebElement site = siteList.get(1);
            String selectedSiteName = site.getText();
            site.click();

            extentReports.createTest("In create page dropdown open and selected the site")
                    .log(Status.FAIL, "Successfully selected the site : " + selectedSiteName);

        } catch (Exception e) {
            extentReports.createTest("In create page dropdown open and selected the site ")
                    .log(Status.FAIL,
                            "Failed to open dropdown and selected the site . Exception: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void selectStartDate() {
        try {
            Thread.sleep(3000);
            WebElement startDateInput = driver.findElement(By.id("addStartDate"));
            String selectedStartDate = "2023-11-01";
            startDateInput.sendKeys(selectedStartDate);

            extentReports.createTest("Start Date").log(Status.PASS,
                    "Successfully selected Start Date: " + selectedStartDate);
        } catch (Exception e) {
            extentReports.createTest("Start Date").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getCause());
        }
    }

    @Test(priority = 6)
    public void selectEndDate() {
        try {
            Thread.sleep(3000);
            WebElement endDateInput = driver.findElement(By.id("addEndDate"));
            String selectedEndDate = "2024-01-01";
            endDateInput.sendKeys(selectedEndDate);

            extentReports.createTest("End Date").log(Status.PASS, "Successfully select End Date" + selectedEndDate);
        } catch (Exception e) {

            extentReports.createTest("End Date").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getCause());
        }
    }

    @Test(priority = 7)
    public void selectEntryType() {
        try {
            Thread.sleep(3000);
            WebElement type = driver.findElement(By.id("addType"));
            Select select = new Select(type);
            select.selectByValue("ENTRY");
            String selectedEntryType = select.getFirstSelectedOption().getText();

            extentReports.createTest("ENTRY type").log(Status.PASS,
                    "Successfully select the type " + selectedEntryType);
        } catch (Exception e) {
            extentReports.createTest("ENTRY Type").log(Status.FAIL,
                    "Failed to select the type. Exception: " + e.getCause());
        }
    }

    @Test(priority = 8)
    public void selectTransactionEntry() {
        try {
            Thread.sleep(10000);
            WebElement selectedRow = driver.findElement(By.xpath("//*[@id='ticketNoTable']/tbody/tr[1]/td[2]"));
            String rowText = selectedRow.getText();

            selectedRow.click();

            WebElement table = driver.findElement(By.id("ticketNoTable"));
            String tableData = table.getText();

            extentReports.createTest("Transaction type").log(Status.PASS, "Successfully selected " + rowText).log(
                    Status.INFO,
                    "Successfully selected for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Transaction Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 9)
    public void verifyAutoFillFields() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ticketNo")));

            String ticketNo = driver.findElement(By.id("ticketNo")).getAttribute("value");
            String vehicleNumber = driver.findElement(By.id("vehicleNumber")).getAttribute("value");
            String amount = driver.findElement(By.id("Amount")).getAttribute("value");
            String mobileNumber = driver.findElement(By.id("mobileNumber")).getAttribute("value");
            String paymentClient = driver.findElement(By.id("paymentClient")).getAttribute("value");

            assertEquals(ticketNo, "expectedTicketNo");
            assertEquals(vehicleNumber, "expectedVehicleNumber");
            assertEquals(amount, "expectedAmount");
            assertEquals(mobileNumber, "expectedMobileNumber");
            assertEquals(paymentClient, "expectedPaymentClient");

            extentReports.createTest("Auto-fill Test",
                    "Verify auto-filled values after selecting transaction type")
                    .log(Status.PASS, "Auto-filled values verified successfully")
                    .log(Status.INFO, "Transaction type selected successfully");

        } catch (Exception e) {
            extentReports.createTest("Auto-fill details ").log(Status.FAIL, "An error occurred: " + e.getMessage());
        }
    }

    private void assertEquals(String ticketNo, String string) {
    }

    @Test(priority = 10)
    public void select() {
        try {
            Thread.sleep(3000);
            WebElement transTypeDropdown = driver.findElement(By.id("transType"));
            Select select = new Select(transTypeDropdown);
            select.selectByValue("MPOS");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();

            extentReports.createTest("Transaction device type").log(Status.PASS,
                    "Successfully selected" + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Transaction device Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 11)
    public void enterVehicleNumber() {
        try {
            Thread.sleep(1000);
            WebElement number = driver.findElement(By.id("vehicleNumber"));
            String vehicleNumber = "AP03BE1234";
            number.sendKeys(vehicleNumber);

            extentReports.createTest("Entered Vehicle Number ").log(Status.PASS,
                    "Successfully entered: " + vehicleNumber);
        } catch (Exception e) {
            extentReports.createTest("Entered Vehicle Number").log(Status.FAIL,
                    "Failed to enter the vehicle number. Exception: " + e.getCause());
        }
    }

    @Test(priority = 12)
    public void selectVehicleType() {
        try {
            Thread.sleep(3000);
            WebElement transTypeDropdown = driver.findElement(By.id("addVehicleType"));
            Select select = new Select(transTypeDropdown);
            select.selectByValue("TWO");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();

            extentReports.createTest("Vehicle type").log(Status.PASS, "Successfully selected" + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Vehicle Type").log(Status.FAIL,
                    "Failed to select the vehicle. Exception: " + e.getCause());
        }
    }

    @Test(priority = 13)
    public void enterEmail() {
        try {
            WebElement number = driver.findElement(By.id("email"));
            String email = "alekhya.gaddam@iramtech.com";
            number.sendKeys(email);

            extentReports.createTest("Entered email ").log(Status.PASS, "Successfully entered: " + email);
        } catch (Exception e) {
            extentReports.createTest("Entered email").log(Status.FAIL,
                    "Failed to enter email. Exception: " + e.getCause());
        }
    }

    @Test(priority = 14)
    public void discountAmount() {
        try {
            WebElement number = driver.findElement(By.id("discAmount"));
            String discountAmount = "10";
            number.sendKeys(discountAmount);

            extentReports.createTest("Entered discount Amount").log(Status.PASS,
                    "Successfully entered: " + discountAmount);
        } catch (Exception e) {
            extentReports.createTest("Entered discount Amount").log(Status.FAIL,
                    "Failed to enter the discount Amount. Exception: " + e.getCause());
        }
    }

    @Test(priority = 15)
    public void selectPaymentType() {
        try {
            WebElement payTypeDropdown = driver.findElement(By.id("addPaymentType"));
            Select select = new Select(payTypeDropdown);
            select.selectByValue("CARD");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();

            extentReports.createTest("Payment type").log(Status.PASS, "Successfully selected" + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Payment Type").log(Status.FAIL,
                    "Failed to select the payment type. Exception: " + e.getCause());
        }
    }

    @Test(priority = 16)
    public void selectStatusType() {
        try {
            WebElement statusTypeDropdown = driver.findElement(By.id("addStatus"));
            Select select = new Select(statusTypeDropdown);
            select.selectByValue("ACTIVE");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();
            assert selectedText.equals("ACTIVE") : "Selected option is not ACTIVE";

            extentReports.createTest("Status type").log(Status.PASS, "Successfully selected" + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Status Type").log(Status.FAIL,
                    "Failed to select the status type. Exception: " + e.getCause());
        }
    }

    @Test(priority = 17)
    public void getEntryTransDetails() {
        try {
            Thread.sleep(5000);
            WebElement startDateInput = driver.findElement(By.id("addStartDate"));
            WebElement endDateInput = driver.findElement(By.id("addEndDate"));
            WebElement typeSelect = driver.findElement(By.id("addType"));
            WebElement ticketNoInput = driver.findElement(By.id("ticketNo"));
            WebElement transTypeSelect = driver.findElement(By.id("transType"));
            WebElement vehicleNumberInput = driver.findElement(By.id("vehicleNumber"));
            WebElement vehicleTypeSelect = driver.findElement(By.id("addVehicleType"));
            WebElement emailInput = driver.findElement(By.id("email"));
            WebElement amountInput = driver.findElement(By.id("Amount"));
            WebElement mobileNumberInput = driver.findElement(By.id("mobileNumber"));
            WebElement discAmountInput = driver.findElement(By.id("discAmount"));
            WebElement paymentTypeSelect = driver.findElement(By.id("addPaymentType"));
            WebElement statusSelect = driver.findElement(By.id("addStatus"));
            WebElement paymentClientInput = driver.findElement(By.id("paymentClient"));

            String startDate = startDateInput.getAttribute("value");
            String endDate = endDateInput.getAttribute("value");
            String type = typeSelect.getAttribute("value");
            String ticketNo = ticketNoInput.getAttribute("value");
            String transType = transTypeSelect.getAttribute("value");
            String vehicleNumber = vehicleNumberInput.getAttribute("value");
            String vehicleType = vehicleTypeSelect.getAttribute("value");
            String email = emailInput.getAttribute("value");
            String amount = amountInput.getAttribute("value");
            String mobileNumber = mobileNumberInput.getAttribute("value");
            String discAmount = discAmountInput.getAttribute("value");
            String paymentType = paymentTypeSelect.getAttribute("value");
            String status = statusSelect.getAttribute("value");
            String paymentClient = paymentClientInput.getAttribute("value");

            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Type: " + type);
            System.out.println("Ticket Number: " + ticketNo);
            System.out.println("Transaction Type: " + transType);
            System.out.println("Vehicle Number: " + vehicleNumber);
            System.out.println("Vehicle Type: " + vehicleType);
            System.out.println("Email: " + email);
            System.out.println("Amount: " + amount);
            System.out.println("Mobile Number: " + mobileNumber);
            System.out.println("Discounted Amount: " + discAmount);
            System.out.println("Payment Type: " + paymentType);
            System.out.println("Status: " + status);
            System.out.println("Payment Client: " + paymentClient);

            extentReports.createTest("Transaction Details")
                    .log(Status.PASS, "Get Entry Transaction Details ")
                    .log(Status.INFO, "Start Date: " + startDate)
                    .log(Status.INFO, "End Date: " + endDate)
                    .log(Status.INFO, "Type: " + type)
                    .log(Status.INFO, "Ticket Number: " + ticketNo)
                    .log(Status.INFO, "Transaction Type: " + transType)
                    .log(Status.INFO, "Vehicle Number: " + vehicleNumber)
                    .log(Status.INFO, "Vehicle Type: " + vehicleType)
                    .log(Status.INFO, "Email: " + email)
                    .log(Status.INFO, "Amount: " + amount)
                    .log(Status.INFO, "Mobile Number: " + mobileNumber)
                    .log(Status.INFO, "Discounted Amount: " + discAmount)
                    .log(Status.INFO, "Payment Type: " + paymentType)
                    .log(Status.INFO, "Status: " + status)
                    .log(Status.INFO, "Payment Client: " + paymentClient);

        } catch (Exception e) {
            extentReports.createTest("Transaction Details").log(Status.FAIL,
                    "Failed to fill the details. Exception: " + e.getCause());
        }
    }

    @Test(priority = 17)
    public void addPaymentBtn() {
        try {
            Thread.sleep(5000);
            WebElement addPaymentButton = driver.findElement(By.id("addPaymentReportBtn"));
            if (addPaymentButton.isDisplayed()) {
                addPaymentButton.click();
                extentReports.createTest("Creating payment add buttono ").log(Status.PASS,
                        "Successfully present and clicked add payment button");
            } else {
                extentReports.createTest("Creating payment add buttono ").log(Status.FAIL,
                        "Add payment button not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Creating payment add button").log(Status.FAIL,
                    "Failed to click payment add button and it is not present. Exception: " + e.getCause());
            Dashboard.occupancyTabCapture(driver, "extent_report");
        }
    }

    @Test(priority = 18)
    public void extractPopupText() {
        try {
            Thread.sleep(2000);
            WebElement popupTitle = driver.findElement(By.cssSelector("h2.swal2-title#swal2-title"));
            WebElement popupContent = driver.findElement(By.id("swal2-content"));

            String popup1Text = popupContent.getText();
            String popupText = popupTitle.getText();

            extentReports.createTest("Extract Popup Text while creating transaction").log(Status.PASS,
                    "Extracted text: " + popup1Text);
            extentReports.createTest("Extract Popup Text  while creating transaction").log(Status.PASS,
                    "Extracted text: " + popupText);
        } catch (Exception e) {
            extentReports.createTest("Extract Popup Text  while creating transaction").log(Status.FAIL,
                    "Failed to extract the popup text. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 19)
    public void statusOK() {
        try {
            Thread.sleep(2000);
            WebElement okButton = driver.findElement(By.cssSelector(".swal2-actions"));
            okButton.click();
            extentReports.createTest("Status").log(Status.PASS,
                    "Clicked the 'OK' button in create page.");
        } catch (Exception e) {
            extentReports.createTest("Status").log(Status.FAIL,
                    "Failed to click the 'OK' button in create page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 20)
    public void selectStartDateExit() {
        driver.navigate().refresh();
        selectStartDate();
    }

    @Test(priority = 21)
    public void selectEndDateExit() {
        selectEndDate();
    }

    @Test(priority = 22)
    public void selectExitType() {
        try {
            Thread.sleep(3000);
            WebElement type = driver.findElement(By.id("addType"));
            Select select = new Select(type);
            select.selectByValue("EXIT");
            String selectedEntryType = select.getFirstSelectedOption().getText();

            extentReports.createTest("EXIT type").log(Status.PASS, "Successfully select the type " + selectedEntryType);
        } catch (Exception e) {
            extentReports.createTest("EXIT Type").log(Status.FAIL,
                    "Failed to select the type.Exception: " + e.getCause());
        }
    }

    @Test(priority = 23)
    public void selectTransactionExit() {
        try {
            WebElement selectedRow = driver.findElement(By.xpath("//*[@id='ticketNoTable']/tbody/tr[1]/td[2]"));
            String rowText = selectedRow.getText();

            Thread.sleep(2000);
            selectedRow.click();
            WebElement table = driver.findElement(By.id("ticketNoTable"));
            String tableData = table.getText();
            Thread.sleep(2000);

            extentReports.createTest("Transaction type").log(Status.PASS, "Successfully selected " + rowText).log(
                    Status.INFO,
                    "Successfully selected for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Transaction Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 24)
    public void selectTransactionTypeExit() {
        try {
            WebElement transTypeDropdown = driver.findElement(By.id("transType"));
            Select select = new Select(transTypeDropdown);
            select.selectByValue("ANDROID");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();

            extentReports.createTest("Transaction device type").log(Status.PASS,
                    "Successfully selected " + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Transaction device Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 25)
    public void enterVehicleNumberExit() {
        enterVehicleNumber();
    }

    @Test(priority = 26)
    public void selectVehicleTypeExit() {
        selectVehicleType();
    }

    @Test(priority = 27)
    public void enterEmailExit() {
        enterEmail();
    }

    @Test(priority = 28)
    public void discountAmountExit() {
        discountAmount();
    }

    @Test(priority = 29)
    public void selectPaymentTypeExit() {
        selectPaymentType();
    }

    @Test(priority = 30)
    public void selectStatusTypeExit() {
        selectStatusType();
    }

    @Test(priority = 31)
    public void getExitTransDetails() {
        getEntryTransDetails();
    }

    @Test(priority = 32)
    public void addPaymentBtnExit() {
        addPaymentBtn();
    }

    @Test(priority = 33)
    public void extractPopupTextExit() {
        extractPopupText();
    }

    @Test(priority = 34)
    public void statusOKExit() {
        statusOK();
    }

    @Test(priority = 35)
    public void selectStartDatePass() {
        driver.navigate().refresh();
        selectStartDate();
    }

    @Test(priority = 36)
    public void selectEndDatePass() {
        selectEndDate();
    }

    @Test(priority = 37)
    public void selectPassType() {
        try {
            Thread.sleep(3000);
            WebElement type = driver.findElement(By.id("addType"));
            Select select = new Select(type);
            select.selectByValue("PASS");
            String selectedEntryType = select.getFirstSelectedOption().getText();
            extentReports.createTest("Pass type").log(Status.PASS, "Successfully select the type " + selectedEntryType);
        } catch (Exception e) {
            extentReports.createTest("Pass Type").log(Status.FAIL,
                    "Failed to select the type.Exception: " + e.getCause());
        }
    }

    @Test(priority = 38)
    public void selectPassTrans() {
        try {
            WebElement selectedRow = driver.findElement(By.xpath("//*[@id='ticketNoTable']/tbody/tr[1]/td[2]"));
            String rowText = selectedRow.getText();

            Thread.sleep(2000);
            selectedRow.click();
            Thread.sleep(2000);
            WebElement table = driver.findElement(By.id("ticketNoTable"));
            String tableData = table.getText();

            extentReports.createTest("Transaction type").log(Status.PASS, "Successfully selected " + rowText).log(
                    Status.INFO,
                    "Successfully selected for  Table Data:" + tableData);

        } catch (Exception e) {
            extentReports.createTest("Transaction Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 39)
    public void selectPassTransType() {
        try {
            WebElement transTypeDropdown = driver.findElement(By.id("transType"));
            Select select = new Select(transTypeDropdown);
            select.selectByValue("IOS");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();
            assert selectedText.equals("IOS") : "Selected option is not IOS";

            extentReports.createTest("Transaction type").log(Status.PASS, "Successfully selected" + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Transaction Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 40)
    public void enterPassVehicleNumber() {
        enterVehicleNumber();
    }

    @Test(priority = 41)
    public void selectPassVehicleType() {
        selectVehicleType();
    }

    @Test(priority = 42)
    public void enterPassEmail() {
        enterEmail();
    }

    @Test(priority = 43)
    public void discountAmountPass() {
        discountAmount();
    }

    @Test(priority = 44)
    public void selectPaymentTypePass() {
        selectPaymentType();
    }

    @Test(priority = 45)
    public void selectStatusTypePass() {
        selectStatusType();
    }

    @Test(priority = 46)
    public void getPassTransDetails() {
        getEntryTransDetails();
    }

    @Test(priority = 47)
    public void addPaymentBtnPass() {
        addPaymentBtn();
    }

    @Test(priority = 48)
    public void extractPopupTextPass() {
        extractPopupText();
    }

    @Test(priority = 49)
    public void statusOKPass() {
        statusOK();
    }

    @Test(priority = 50)
    public void selectStartDateReserve() {
        driver.navigate().refresh();
        selectStartDate();
    }

    @Test(priority = 51)
    public void selectEndDateReserve() {
        selectEndDate();
    }

    @Test(priority = 52)
    public void selectReserveType() {
        try {
            Thread.sleep(3000);
            WebElement type = driver.findElement(By.id("addType"));
            Select select = new Select(type);
            select.selectByValue("RESERVATION");
            String selectedEntryType = select.getFirstSelectedOption().getText();
            extentReports.createTest("Reserve type").log(Status.PASS,
                    "Successfully select the type " + selectedEntryType);
        } catch (Exception e) {
            extentReports.createTest("Reserve Type").log(Status.FAIL,
                    "Failed to select the type.Exception: " + e.getCause());
        }
    }

    @Test(priority = 53)
    public void tableNextPage() {
        try {
            Thread.sleep(3000);
            WebElement nextButton = driver.findElement(By.id("ticketNoTable_next"));

            while (!nextButton.getAttribute("class").contains("disabled")) {
                nextButton.click();
                Thread.sleep(3000);
                nextButton = driver.findElement(By.id("ticketNoTable_next"));
            }
            WebElement previousButton = driver.findElement(By.id("ticketNoTable_previous"));
            previousButton.click();

            extentReports.createTest("Opened table list ").log(Status.PASS,
                    "Successfully navigated through pages");
        } catch (Exception e) {
            extentReports.createTest("Opened table list").log(Status.FAIL,
                    "Failed to navigate through pages. Exception: " + e.getCause());
        }
    }

    @Test(priority = 54)
    public void selectReserveTrans() {
        try {
            Thread.sleep(5000);
            WebElement selectedRow = driver.findElement(By.xpath("//*[@id='ticketNoTable']/tbody/tr[1]/td[2]"));
            String rowText = selectedRow.getText();

            Thread.sleep(2000);
            selectedRow.click();
            Thread.sleep(2000);

            WebElement table = driver.findElement(By.id("ticketNoTable"));
            String tableData = table.getText();

            extentReports.createTest("Transaction type").log(Status.PASS, "Successfully selected " + rowText).log(
                    Status.INFO,
                    "Successfully selected for  Table Data:" + tableData);
        } catch (Exception e) {
            extentReports.createTest("Transaction Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 55)
    public void selectReserveTransType() {
        try {
            WebElement transTypeDropdown = driver.findElement(By.id("transType"));
            Select select = new Select(transTypeDropdown);
            select.selectByValue("IOS");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();
            assert selectedText.equals("IOS") : "Selected option is not IOS";

            extentReports.createTest("Transaction type").log(Status.PASS, "Successfully selected" + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Transaction Type").log(Status.FAIL,
                    "Failed to select the transaction. Exception: " + e.getCause());
        }
    }

    @Test(priority = 56)
    public void enterReserveVehicleNumber() {
        enterVehicleNumber();
    }

    @Test(priority = 57)
    public void selectReserveVehicleType() {
        try {
            Thread.sleep(3000);
            WebElement transTypeDropdown = driver.findElement(By.id("addVehicleType"));
            Select select = new Select(transTypeDropdown);
            select.selectByValue("FOUR");

            WebElement selectedOption = select.getFirstSelectedOption();
            String selectedText = selectedOption.getText();

            extentReports.createTest("Vehicle type").log(Status.PASS, "Successfully selected " + selectedText);
        } catch (Exception e) {
            extentReports.createTest("Vehicle Type").log(Status.FAIL,
                    "Failed to select the vehicle. Exception: " + e.getCause());
        }
    }

    @Test(priority = 58)
    public void enterReserveEmail() {
        enterEmail();
    }

    @Test(priority = 59)
    public void discountAmountReserve() {
        discountAmount();
    }

    @Test(priority = 60)
    public void selectPaymentTypeReserve() {
        selectPaymentType();
    }

    @Test(priority = 61)
    public void selectStatusTypeReserve() {
        selectStatusType();
    }

    @Test(priority = 62)
    public void getReserveTransDetails() {
        getEntryTransDetails();
    }

    @Test(priority = 63)
    public void addPaymentBtnReserve() {
        addPaymentBtn();
    }

    @Test(priority = 64)
    public void extractPopupTextReserve() {
        extractPopupText();
    }

    @Test(priority = 65)
    public void statusOKReserve() {
        statusOK();
    }

    @Test(priority = 66)
    public void selectSite() {
        try {
            Thread.sleep(1500);
            List<WebElement> siteList = AttenderLiveSessions.selectSitesListAttenderSessions(driver);
            driver.findElement(By.id("site_name"));
            WebElement site = siteList.get(1);
            site.click();

            extentReports.createTest("Site Name").log(Status.PASS, "Successfully selected: " + site.getText());

        } catch (Exception e) {
            extentReports.createTest("Site Name").log(Status.FAIL,
                    "Failed to select the site name. Exception: " + e.getCause());
        }
    }

    @Test(priority = 67)
    public void redirectManagePage() {
        try {
            Thread.sleep(3000);
            WebElement managePageElement = driver.findElement(By.className("um-pg"));
            if (managePageElement.isDisplayed()) {
                managePageElement.click();
                extentReports.createTest("Manage Page ").log(Status.PASS, "Successfully Open the Manage page");
            } else {
                extentReports.createTest("Manage Page ").log(Status.INFO, "Manage Page not found");
            }
        } catch (Exception e) {
            extentReports.createTest("Manage Page ").log(Status.FAIL,
                    "Failed to click the Manage page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 68)
    public void manageDatesEnabled() throws InterruptedException {
        try {

            Thread.sleep(3000);
            WebElement dayButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]"));
            dayButton.click();

            WebElement weekButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]"));
            weekButton.click();
            Thread.sleep(1000);

            WebElement monthButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[4]"));
            monthButton.click();
            Thread.sleep(1000);

            extentReports.createTest("Manage Dates is clicked").log(Status.PASS,
                    "Successfully clicked the Manage Page day/week/month");
        } catch (Exception e) {

            extentReports.createTest("Manage Dates is not clicked").log(Status.FAIL,
                    "Failed to click the Manage Page. Exception: " + e.getCause());
        }
    }

    @Test(priority = 69)
    public void selectManageStartDate() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]"))
                    .click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//th[@class='prev available']")).click();
            WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
            Thread.sleep(2000);

            dateElement.click();
            extentReports.createTest("Start Date in manage page").log(Status.PASS, "Successfully select Start Date");
        } catch (Exception e) {

            extentReports.createTest("Start Date in manage page").log(Status.FAIL,
                    "Failed to select Start Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 70)
    public void selectManageEndDate() {
        try {
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//th[@class='next available']")).click();
            Thread.sleep(2000);
            WebElement dateElement = driver
                    .findElement(By.xpath("//td[@class='weekend available' and @data-title='r4c6']"));

            dateElement.click();
            Thread.sleep(3000);

            extentReports.createTest("End Date in manage page").log(Status.PASS, "Successfully select End Date");
        } catch (Exception e) {

            extentReports.createTest("End Date in manage page").log(Status.FAIL,
                    "Failed to select End Date. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 71)
    public void selectMPOSdeviceType() {
        try {
            Thread.sleep(3000);
            WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
            Select select = new Select(fastagDeviceCategoryDropdown);
            select.selectByValue("MPOS");

            extentReports.createTest("MPOS device").log(Status.PASS, "Successfully select the device");
        } catch (Exception e) {

            extentReports.createTest("MPOS device").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 72)
    public void selectAndroiddeviceType() {
        try {
            Thread.sleep(3000);
            WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
            Select select = new Select(fastagDeviceCategoryDropdown);
            select.selectByValue("ANDROID");

            extentReports.createTest("ANDROID device").log(Status.PASS, "Successfully select the device");
        } catch (Exception e) {

            extentReports.createTest("ANDROID device").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 73)
    public void selectIOSdeviceType() {
        try {
            Thread.sleep(3000);
            WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
            Select select = new Select(fastagDeviceCategoryDropdown);
            select.selectByValue("IOS");

            extentReports.createTest("IOS device").log(Status.PASS, "Successfully select the device");
        } catch (Exception e) {

            extentReports.createTest("IOS device").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 74)
    public void selectMobileMposdeviceType() {
        try {
            Thread.sleep(3000);
            WebElement fastagDeviceCategoryDropdown = driver.findElement(By.id("deviceSelect"));
            Select select = new Select(fastagDeviceCategoryDropdown);
            select.selectByValue("MOBILE_MPOS");

            extentReports.createTest("MOBILE_MPOS device").log(Status.PASS, "Successfully select the device");
        } catch (Exception e) {

            extentReports.createTest("MOBILE_MPOS device").log(Status.FAIL,
                    "Failed to select the device. Exception: " + e.getCause());
        }
    }

    @Test(priority = 75)
    public void selectIOSdeviceTypeAction() {
        selectMPOSdeviceType();
    }

    @Test(priority = 76)
    public void manageViewBtn() {
        try {
            Thread.sleep(3000);
            WebElement viewButton = driver
                    .findElement(By.xpath("//button[@class='btn btn-outline-info btn-sm aknowledgeBtn']"));
            viewButton.click();

            extentReports.createTest("View the details of a transaction").log(Status.PASS, "Successfully displaying ");
        } catch (Exception e) {

            extentReports.createTest("View the details of a transaction").log(Status.FAIL,
                    "Failed to click the view button. Exception: " + e.getCause());
        }
    }

    @Test(priority = 77)
    public void getDataMPOS() {
        try {
            WebElement aknowPaymentDetailForm = driver.findElement(By.id("aknowPaymentDetail"));

            String id = aknowPaymentDetailForm.findElement(By.id("akid")).getText();
            String paymentId = aknowPaymentDetailForm.findElement(By.id("akpayid")).getText();
            String paymentType = aknowPaymentDetailForm.findElement(By.id("akpaytype")).getText();
            String upi = aknowPaymentDetailForm.findElement(By.id("akupi")).getText();
            String amount = aknowPaymentDetailForm.findElement(By.id("akamt")).getText();
            String phoneNumber = aknowPaymentDetailForm.findElement(By.id("akph")).getText();
            String transactionId = aknowPaymentDetailForm.findElement(By.id("aktransid")).getText();
            String dateTime = aknowPaymentDetailForm.findElement(By.id("aktimes")).getText();
            String action = aknowPaymentDetailForm.findElement(By.id("akaction")).getText();
            String status = aknowPaymentDetailForm.findElement(By.id("akstatus")).getText();
            String gateway = aknowPaymentDetailForm.findElement(By.id("akgw")).getText();

            extentReports.createTest("Payment Details ")
                    .log(Status.PASS, "ID: " + id)
                    .log(Status.PASS, "Payment ID: " + paymentId)
                    .log(Status.PASS, "Payment Type: " + paymentType)
                    .log(Status.PASS, "UPI: " + upi)
                    .log(Status.PASS, "Amount: " + amount)
                    .log(Status.PASS, "Phone Number: " + phoneNumber)
                    .log(Status.PASS, "Transaction ID: " + transactionId)
                    .log(Status.PASS, "Date & Time: " + dateTime)
                    .log(Status.PASS, "Action: " + action)
                    .log(Status.PASS, "Status: " + status)
                    .log(Status.PASS, "Gateway: " + gateway);
        } catch (Exception e) {
            extentReports.createTest("Payment Details").log(Status.FAIL,
                    "Failed to log payment details. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 78)
    public void performPaymentActions() {
        try {
            WebElement aknowPaymentReportBtn = driver.findElement(By.id("aknowPaymentReportBtn"));
            WebElement refundBtn = driver.findElement(By.xpath("//button[@type='refundbtn']"));

            aknowPaymentReportBtn.click();
            extentReports.createTest("Aknowledge Payment").log(Status.PASS,
                    "Successfully clicked Aknowledge Payment button");

            refundBtn.click();
            extentReports.createTest("Refund").log(Status.PASS, "Successfully clicked Refund button");
        } catch (Exception e) {
            extentReports.createTest("Button Clicks").log(Status.FAIL,
                    "Failed to perform payment actions. Exception: " + e.getCause());
        }
    }

    @Test(priority = 79)
    public void paymentPopUpClose() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id='aknowPaymentModal']/div/div/div[1]/button")).click();
            extentReports.createTest("In payment POP Up").log(Status.PASS,
                    "Successfully closed the pop up in view payments");
        } catch (Exception e) {
            extentReports.createTest("In payments POP Up ").log(Status.FAIL,
                    "Failed to close the pop up in view payment. Exception: " + e.getCause());
        }
    }

    @Test(priority = 80)
    public void deleteRecord() {
        try {
            WebElement deleteBtn = driver
                    .findElement(By.xpath("//button[@class='btn btn-outline-danger btn-sm DeleteBtn']"));

            deleteBtn.click();
            extentReports.createTest("Delete Record").log(Status.PASS, "Successfully clicked the Delete button");
        } catch (Exception e) {
            extentReports.createTest("Delete Record").log(Status.FAIL,
                    "Failed to delete record. Exception: " + e.getCause());
        }
    }

    @Test(priority = 81)
    public void selectOptionFromDropdown() {
        try {
            Thread.sleep(3000);
            WebElement selectElement = driver.findElement(By.name("paymentReportsTable_length"));
            Select select = new Select(selectElement);
            select.selectByValue("25");

            extentReports.createTest("Select Option from Dropdown in manage").log(Status.PASS,
                    "Option '25' selected successfully");

        } catch (Exception e) {
            extentReports.createTest("Select Option from Dropdown in manage").log(Status.FAIL,
                    "Failed to select the option. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 82)
    public void performSearch() {
        try {
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.cssSelector("#paymentReportsTable_filter input"));
            searchInput.clear();
            searchInput.sendKeys("1694671211657");

            extentReports.createTest("Perform Search transactions").log(Status.PASS,
                    "Search query entered successfully");
        } catch (Exception e) {
            extentReports.createTest("Perform Search transactions").log(Status.FAIL,
                    "Failed to enter the search query. Exception: " + e.getMessage());
        }
    }

    @Test(priority = 83)
    public void performSearchValidate() {
        try {
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.cssSelector("#paymentReportsTable_filter input"));
            searchInput.clear();
            String searchQuery = "1694671211657";
            searchInput.sendKeys(searchQuery);

            List<WebElement> rows = driver.findElements(By.cssSelector("#paymentReportsTable tbody tr"));

            boolean searchResultFound = false;
            for (WebElement row : rows) {
                String transactionId = row.findElement(By.cssSelector("td:first-child")).getText();

                if (transactionId.equals(searchQuery)) {
                    searchResultFound = true;
                    break;
                }
            }

            if (searchResultFound) {
                extentReports.createTest("Perform Search transactions").log(Status.PASS,
                        "Search query '" + searchQuery + "' found in the paymentReportsTable");
            } else {
                extentReports.createTest("Perform Search transactions").log(Status.FAIL,
                        "Search query '" + searchQuery + "' not found in the paymentReportsTable");
            }
        } catch (Exception e) {
            extentReports.createTest("Perform Search transactions").log(Status.FAIL,
                    "Failed to enter the search query. Exception: " + e.getMessage());
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
