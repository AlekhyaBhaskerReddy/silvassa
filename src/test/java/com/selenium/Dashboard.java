package com.selenium;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.apache.http.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
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
import com.selenium.model.ServerCredentials;

public class Dashboard extends BaseTestReport {
	static WebDriver driver;
	ServerCredentials serverconfig;

	@BeforeClass
	public void setUp() {
		// extentReports = new ExtentReports();
		// spark = new ExtentSparkReporter("results/Dashboard.html");
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
		loginDetails(driver, extentReports);
	}

	@Test(priority = 2)
	public void revenueDashboardOpen() {
		revenuedashboardOpen(driver, extentReports);

	}

	@Test(priority = 3)
	public void getSummaryCards() {
		WebElement dashboardSummary = driver.findElement(By.className("dashboardSummary"));
		List<WebElement> summaryCards = dashboardSummary.findElements(By.className("dash-summary-cards"));

		for (WebElement summaryCard : summaryCards) {
			WebElement cardNameElement = summaryCard.findElement(By.tagName("b"));
			String cardName = cardNameElement.getText();

			extentReports.createTest("Total Tabs").log(Status.PASS,
					"Available tabs in Dashboard").log(Status.INFO, "Summary Card Name: " + cardName);
		}

	}

	@Test(priority = 4)
	public void revenueDatesEnabled() {
		revenueDatesEnabled(driver, extentReports);
	}

	@Test(priority = 7)
	public void getDayRevenue() {
		try {

			Thread.sleep(4000);
			WebElement dayButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]"));
			dayButton.click();
			WebElement oneDay = driver.findElement(By.xpath("//*[@id='total_revenue']"));
			String value2 = oneDay.getText();
			System.out.println("One-Day: " + value2);
			extentReports.createTest("Day Revenu is Displaying ").log(Status.PASS,
					"Amount is displaying Successfully" + value2);
		} catch (Exception e) {
			extentReports.createTest("Day Revenue is not displaying").log(Status.FAIL,
					"Failed Amount is not Displaying. Exception: " + e.getMessage());
		}
	}

	@Test(priority = 6)
	public void getWeekRevenue() {
		try {
			Thread.sleep(3000);
			WebElement weekButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]"));
			weekButton.click();
			WebElement oneWeek = driver.findElement(By.xpath("//*[@id='total_revenue']"));
			String value2 = oneWeek.getText();
			System.out.println("One-week: " + value2);
			extentReports.createTest("Week Revenu is Displaying ").log(Status.PASS,
					"Amount is displaying Successfully" + value2);
		} catch (Exception e) {
			extentReports.createTest("Week Revenue is not displaying").log(Status.FAIL,
					"Failed Amount is not Displaying. Exception: " + e.getMessage());
		}
	}

	@Test(priority = 5)
	public void getMonthRevenue() {
		try {
			Thread.sleep(4000);
			WebElement monthButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[4]"));
			monthButton.click();
			WebElement oneMonth = driver.findElement(By.xpath("//*[@id='total_revenue']"));
			String value1 = oneMonth.getText();
			System.out.println("One-Month: " + value1);
			extentReports.createTest("Dashboard Month Revenue").log(Status.PASS,
					"Successfully display Dashboard month revenue" + value1);
		} catch (Exception e) {
			extentReports.createTest("Dashboard Month Revenue").log(Status.FAIL,
					"Failed to display Dashboard month revenue. Exception: " + e.getCause());

		}
	}

	@Test(priority = 8)
	public void selectCountDashboard() {
		try {
			Thread.sleep(2000);
			WebElement dropdown = driver.findElement(By.name("summaryTable_length"));
			Select select = new Select(dropdown);
			Thread.sleep(2000);
			select.selectByValue("25");
			String countValue = "25";
			extentReports.createTest("Dashboard select the Display List count").log(Status.PASS,
					"Successfully selected as : " + countValue);

			Thread.sleep(4000);
		} catch (Exception e) {
			extentReports.createTest("Dashboard select the Display List count").log(Status.FAIL,
					"Failed to select the count. Exception: " + e.getCause());

		}
	}

	@Test(priority = 9)
	public void selectDashboardStartDate() {
		selectDashboardStartDate(driver, extentReports);
	}

	@Test(priority = 10)
	public void selectDashboardEndDate() {
		selectDashboardEndDate(driver, extentReports);
	}

	@Test(priority = 11)
	public void sitenamesDashboard() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("summaryTable")));
			List<WebElement> rows = table.findElements(By.tagName("tr"));

			if (rows.isEmpty()) {
				extentReports.createTest("Summary Table").log(Status.FAIL, "No data available");
				return;
			}

			StringBuilder tableInfo = new StringBuilder();
			tableInfo.append(
					"| Site Name    | Revenue | Two Wheeler Revenue | Two Wheeler Count |four Wheeler Revenue |four Wheeler Count| \n");

			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));

				if (cells.size() >= 6) {
					String siteName = cells.get(0).getText();
					String revenue = cells.get(1).getText();
					String twoWheelerRevenue = cells.get(2).getText();
					String TwoWheelerCount = cells.get(3).getText();
					String fourWheelerRevenue = cells.get(4).getText();
					String fourWheelerCount = cells.get(5).getText();

					tableInfo.append(String.format("| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%n", siteName,
							revenue, twoWheelerRevenue, TwoWheelerCount, fourWheelerRevenue, fourWheelerCount));
				}
			}

			extentReports.createTest("Summary Table")
					.log(Status.INFO, "Displaying site details:\n" + tableInfo.toString())
					.log(Status.PASS, "Displaying Successfully");

		} catch (TimeoutException e) {
			extentReports.createTest("Summary Table").log(Status.FAIL, "Timed out waiting for the summary table");
		} catch (Exception e) {
			extentReports.createTest("Summary Table").log(Status.FAIL,
					"Failed to Display. Exception: " + e.getMessage());
		}
	}

	@Test(priority = 12)
	public void validateRevenue() throws java.text.ParseException {
		try {
			WebElement table = driver.findElement(By.id("summaryTable"));
			WebElement tbody = table.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));

			if (rows.isEmpty()) {
				extentReports.createTest("In Dashboard revenue validation").log(Status.FAIL, "No data available");
				return;
			}

			NumberFormat numberFormat = new DecimalFormat("#,##0.00");

			for (WebElement row : rows) {
				List<WebElement> amountCells = row.findElements(By.cssSelector("td[class*='sorting']"));

				boolean hasNegativeValues = false;
				boolean hasDecimalValues = false;

				String siteName = row.findElement(By.tagName("td")).getText();

				for (WebElement amountCell : amountCells) {
					String amountText = amountCell.getText();

					try {
						double amountValue = numberFormat.parse(amountText).doubleValue();

						if (amountValue < 0) {
							hasNegativeValues = true;
						}

						if (amountValue % 1 != 0) {
							hasDecimalValues = true;
						}

					} catch (ParseException e) {
						extentReports.createTest("Amount Validation").log(Status.FAIL,
								"Failed to parse amount: ").log(Status.WARNING,
										"Failed to parse amount: " + amountText);
					}
				}

				if (hasNegativeValues || hasDecimalValues) {
					extentReports.createTest("Amount Validation").log(Status.FAIL,
							"Row details for site ").log(Status.WARNING,
									"Row details for site " + siteName + ": " + row.getText()
											+ " contains negative or decimal values");
					extentReports.createTest("Amount Validation").log(Status.PASS, "Validation completed");
				}
			}
		} catch (Exception e) {
			extentReports.createTest("Amount Validation").log(Status.FAIL, "Failed to validate");
		}

	}

	@Test(priority = 13)
	public void searchSiteName() {
		try {
			Thread.sleep(4000);
			WebElement searchInput = driver.findElement(By.cssSelector("#summaryTable_filter input[type='search']"));
			searchInput.clear();
			String searchValue = "Inside the Motilal complex";
			searchInput.sendKeys(searchValue);

			Thread.sleep(4000);

			WebElement searchResult = driver.findElement(By.cssSelector("#summaryTable tbody td"));
			WebElement table = driver.findElement(By.id("summaryTable"));
			String tableData = table.getText();

			if (searchResult.getText().equals("Data not available")) {
				extentReports.createTest("Dashboard summary table with site name").log(Status.FAIL,
						"Data not available for the specified Status");
			} else {
				extentReports.createTest("Dashboard summary table with sitename").log(Status.PASS,
						"Successfully searched for: " + searchValue).log(Status.INFO,
								"Successfully searched for  Table Data:" + tableData);
			}
		} catch (Exception e) {
			extentReports.createTest("Dashboard summary table with site name").log(Status.FAIL,
					"Failed to search the Status. Exception: " + e.getCause());
		}
	}

	@Test(priority = 14)
	public void cityRevenueViewButton() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.id("city_revenue_details")).click();

			extentReports.createTest("View Revenue Details Button").log(Status.PASS,
					"Successfully clicked the view button ");
		} catch (Exception e) {

			extentReports.createTest("View Revenue Details Button").log(Status.FAIL,
					"Failed to click the view button. Exception: " + e.getCause());
		}
		extentReports.flush();
	}

	@Test(priority = 15)
	public void count_InViewDetails() {
		try {
			Thread.sleep(2000);
			WebElement dropdown = driver.findElement(By.name("revenueDetailTable_length"));
			Select select = new Select(dropdown);
			Thread.sleep(2000);
			select.selectByValue("25");
			String countValue = "25";
			extentReports.createTest("Select count in view details ").log(Status.PASS,
					"Successfully selected as : " + countValue);

			Thread.sleep(4000);
		} catch (Exception e) {
			extentReports.createTest("Select count in view details ").log(Status.FAIL,
					"Failed to select the count. Exception: " + e.getCause());

		}
	}

	@Test(priority = 16)
	public void searchSiteName_InVieDetails() {
		try {
			Thread.sleep(4000);
			WebElement searchInput = driver
					.findElement(By.cssSelector("#revenueDetailTable_filter input[type='search']"));
			searchInput.clear();
			String searchValue = "Front of Vishal mega mart";
			searchInput.sendKeys(searchValue);

			Thread.sleep(4000);

			WebElement searchResult = driver.findElement(By.cssSelector("#revenueDetailTable tbody td"));
			WebElement table = driver.findElement(By.id("revenueDetailTable"));
			String tableData = table.getText();

			if (searchResult.getText().equals("Data not available")) {
				extentReports.createTest("Search site name in revenue table ").log(Status.FAIL,
						"Data not available for the specified Status");
			} else {
				extentReports.createTest("Search site in revenue table ").log(Status.PASS,
						"Successfully searched for: " + searchValue).log(Status.INFO,
								"Successfully searched for  Table Data:" + tableData);
			}
		} catch (Exception e) {
			extentReports.createTest("Search site name in revenue table ").log(Status.FAIL,
					"Failed to search the Status. Exception: " + e.getCause());
		}
	}

	@Test(priority = 17)
	public void cityRevenueCSVButton() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.id("cityRevenueCsv")).click();
			extentReports.createTest("CSV in City Revenue Details ").log(Status.PASS,
					"Successfully clicked/download the CSV file ");
		} catch (Exception e) {

			extentReports.createTest("CSV in City Revenue Details ").log(Status.FAIL,
					"Failed to clicked/download the CSV file. Exception: " + e.getCause());
		}
	}

	@Test(priority = 18)
	public void cityRevenuePDFButton() {
		try {
			Thread.sleep(2000);
			String mainWindowHandle = driver.getWindowHandle();
			driver.findElement(By.id("cityRevenuePdf")).click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));

			Set<String> windowHandles = driver.getWindowHandles();
			for (String windowHandle : windowHandles) {
				if (!windowHandle.equals(mainWindowHandle)) {
					driver.switchTo().window(windowHandle);
					driver.close();

					driver.switchTo().window(mainWindowHandle);
					extentReports.createTest("PDF in City Revenue Details").log(Status.PASS,
							"Successfully clicked/download the PDF file ");
					return;
				}
			}
		} catch (Exception e) {

			extentReports.createTest("PDF in City Revenue Details ").log(Status.FAIL,
					"Failed to clicked/download the PDF file. Exception: " + e.getCause());
		}
	}

	@Test(priority = 19)
	public void cityRevenuePopUpClose() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.className("close")).click();
			extentReports.createTest("POP Up in City Revenue Details").log(Status.PASS, "Successfully closed ");
		} catch (Exception e) {

			extentReports.createTest("POP Up in City Revenue Details ").log(Status.FAIL,
					"Failed to closed. Exception: " + e.getCause());
		}
	}

	@Test(priority = 20)
	public void cityRevenueColumnFilterBtn() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.id("tableEdit")).click();
			extentReports.createTest("Column Filter in City Revenue Details").log(Status.PASS, "Successfully Clicked ");
		} catch (Exception e) {

			extentReports.createTest("POP Up in City Revenue Details ").log(Status.FAIL,
					"Failed to clicked. Exception: " + e.getCause());
		}
	}

	@Test(priority = 21)
	public void columnFilterSelectVehicleTypes() {
		try {
			String[] checkboxIds = {
					"2wCol", "3wCol", "4wCol", "6wCol", "8wCol", "10wCol", "12wCol", "otherwCol"
			};

			for (String checkboxId : checkboxIds) {
				WebElement checkbox = driver.findElement(By.id(checkboxId));
				if (!checkbox.isSelected()) {
					checkbox.click();
				}
				Thread.sleep(2000);
			}

			for (String checkboxId : checkboxIds) {
				WebElement checkbox = driver.findElement(By.id(checkboxId));
				if (checkbox.isSelected()) {
					checkbox.click();
				}
				Thread.sleep(2000);
			}
			extentReports.createTest("Vehicle deselected in Column Filter").log(Status.PASS,
					"Successfully de-selected ");
		} catch (Exception e) {
			extentReports.createTest("Vehicle deselected in Column Filter").log(Status.FAIL, "Failed to de-select. ");

		}
	}

	@Test(priority = 22)
	public void columnFilterPopUpClose() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='tableEditModal']/div/div/div[1]/button")).click();
			extentReports.createTest("POP Up in Column Filter").log(Status.PASS, "Successfully closed ");
		} catch (Exception e) {
			extentReports.createTest("POP Up in Column Filter").log(Status.FAIL,
					"Failed to closed. Exception: " + e.getCause());
		}
	}

	@Test(priority = 23)
	public void occupancyTabDashboard() {
		try {
			Thread.sleep(2000);
			WebElement occupancyTab = driver.findElement(By.xpath("//div[@id='occupancy_chart_summary']"));

			if (occupancyTab.isDisplayed()) {
				occupancyTab.click();
				extentReports.createTest("Occupancy Tab is clicked").log(Status.PASS,
						"Successfully clicked the Occupancy Tab");

			} else {
				extentReports.createTest("Occupancy Tab is clicked ").log(Status.WARNING, "Ocupancy tab not found");
				Assert.fail("Occupancy tab not found");
			}
		} catch (Exception e) {
			extentReports.createTest("Occupancy Tab is not clicked").log(Status.FAIL,
					"Failed to click the Occupancy. Exception: " + e.getCause());
			occupancyTabCapture(driver, "exception_screenshot");

		}
	}

	@Test(priority = 24)
	public void getOccupancyPercentage() {
		try {
			WebElement avgOccupancy = driver.findElement(By.xpath("//div[@id='occupancy']"));
			String value = avgOccupancy.getText();
			System.out.println("Displayed Average-Occupancy: " + value);
			if (avgOccupancy.isDisplayed()) {
				avgOccupancy.click();
				extentReports.createTest("Occupancy Percentage").log(Status.PASS,
						"Successfully displaying the Occupancy percent" + value);

			} else {
				extentReports.createTest("Occupancy Percentage").log(Status.FAIL, "Ocupancy tab not found");
				Assert.fail("Occupancy tab not found");
			}
		} catch (Exception e) {
			extentReports.createTest("Occupancy Percentage").log(Status.FAIL,
					"Failed to displaying the Occupancy percent.Exception" + e.getCause());
		}
	}

	@Test(priority = 25)
	public void getOperatingSitesPercentage() {
		try {
			WebElement operatingSites = driver.findElement(By.xpath("//div[@id='operating_sites']"));
			String value = operatingSites.getText();
			System.out.println("Displayed operating site percentage: " + value);
			if (operatingSites.isDisplayed()) {
				operatingSites.click();
				extentReports.createTest("Operating Sites Percentage").log(Status.PASS,
						"Successfully displaying the operating_sites percent")
						.log(Status.INFO,
								"Successfully displaying the operating_sites percent" + value);
			} else {
				extentReports.createTest("Operating Sites Percentage").log(Status.WARNING,
						"Operating Sites tab not found");
				Assert.fail("Operating Sites tab not found");
			}
		} catch (Exception e) {
			extentReports.createTest("Operating Sites Percentage").log(Status.FAIL,
					"Failed to displaying the operating_sites percent.Exception" + e.getCause());
		}
	}

	@Test(priority = 26)
	public void operatingsSitesSelectCount() {
		try {
			Thread.sleep(2000);
			WebElement dropdown = driver.findElement(By.name("allSiteTable_length"));
			Select select = new Select(dropdown);
			select.selectByValue("25");
			extentReports.createTest("Operating Site Status select the Display List count").log(Status.PASS,
					"Successfully selected as ");
			Thread.sleep(2000);
		} catch (Exception e) {
			extentReports.createTest("Operating Site Status select the Display List count").log(Status.FAIL,
					"Failed to select the count. Exception: " + e.getMessage());
		}
	}

	@Test(priority = 28)
	public void searchSiteStatus_OperationalTab() {
		try {
			Thread.sleep(4000);
			WebElement searchInput = driver
					.findElement(By.cssSelector("#allSiteTable_filter input[type='search']"));
			searchInput.clear();
			String searchValue = "Partial Operational";
			searchInput.sendKeys(searchValue);

			Thread.sleep(4000);
			WebElement table = driver.findElement(By.id("allSiteTable"));
			String tableData = table.getText();

			extentReports.createTest("Search site status in operational table ").log(Status.PASS,
					"Successfully searched for: " + searchValue).log(Status.INFO,
							"Successfully searched for  Table Data:" + tableData);

		} catch (Exception e) {
			extentReports.createTest("Search site status in operational table ").log(Status.FAIL,
					"Failed to search the Status. Exception: " + e.getCause());
		}
	}

	@Test(priority = 29)
	public void operatingsitesList() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.id("operating_sites")).click();
			WebElement sitestatus = driver.findElement(By.xpath("//div[@id='operating_sites']"));
			String overallStatus = sitestatus.getText();

			System.out.println("Site Status-Percentage : " + overallStatus);
			System.out.println("------------------------------------");
			System.out.println("| Site Name    | Status | ");

			WebElement table = driver.findElement(By.id("allSiteTable"));
			WebElement tbody = table.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));

			StringBuilder siteDetailsInfo = new StringBuilder();

			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));

				if (cells.size() >= 2) {
					String siteName = cells.get(0).getText();
					String status = cells.get(1).getText();

					System.out.printf("| %-50s | %-15s |%n", siteName, status);
					System.out.println("----------------------------------------");

					siteDetailsInfo.append("Site Name: ").append(siteName).append(", Status: ").append(status)
							.append("\n");
				}
			}

			extentReports.createTest("SitesName and Status ")
					.log(Status.PASS, "Successfully displaying the names and status ")
					.log(Status.INFO, "Overall Status: " + overallStatus)
					.log(Status.INFO, "Site Details:\n" + siteDetailsInfo.toString());

		} catch (Exception e) {
			extentReports.createTest("SitesName and Status ").log(Status.FAIL,
					"Failed displaying the names and status. Exception: " + e.getMessage());
		}
	}

	@Test(priority = 30)
	public void deviceStatusTab() {
		try {

			WebElement deviceStatus = driver.findElement(By.xpath("//*[@id='device_status_summary']"));
			String value = deviceStatus.getText();
			if (deviceStatus.isDisplayed()) {
				deviceStatus.click();
				extentReports.createTest("Device Status Tab is clicked").log(Status.PASS,
						"Successfully clicked the Device status Tab : ").log(Status.PASS,
								"Device status Tab : " + value);
			} else {
				extentReports.createTest("Device Status Tab is clicked").log(Status.WARNING,
						"Device Status tab not found");
				Assert.fail("Device Status tab not found");
			}
		} catch (Exception e) {

			extentReports.createTest("Device Status Tab is clicked").log(Status.FAIL,
					"Failed to click the Device status. Exception: " + e.getCause());
		}
	}

	@Test(priority = 31)
	public void selectCountDeviceStatus() {
		try {
			WebElement dropdown = driver.findElement(By.name("allSiteDiviceTbl_length"));
			Select select = new Select(dropdown);
			select.selectByValue("25");
			extentReports.createTest("Dashboard Device Status select the Display List count").log(Status.PASS,
					"Successfully selected as : ");
			Thread.sleep(4000);
		} catch (Exception e) {
			extentReports.createTest("Dashboard Device Status select the Display List count").log(Status.FAIL,
					"Failed to select the count. Exception: " + e.getCause());
		}
	}

	@Test(priority = 32)
	public void deviceStatusPercentage() {
		try {
			WebElement deviceStatus = driver.findElement(By.xpath("//*[@id='device_status_summary']/div"));
			String value = deviceStatus.getText();
			System.out.println("DeviceStatus-Percentage : " + value);
			driver.findElement(By.tagName("tr"));
			Thread.sleep(3000);
			extentReports.createTest("Device status Percentage").log(Status.PASS,
					"Successfully displaying the Device status percent" + value);
		} catch (Exception e) {
			extentReports.createTest("Device Status Percentage").log(Status.FAIL,
					"Failed to get the Device status percent. Exception: " + e.getCause());
		}
	}

	@Test(priority = 33)
	public void devicesStatusTable() {
		try {
			WebElement table = driver.findElement(By.id("allSiteDiviceTbl"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));

			StringBuilder tableInfo = new StringBuilder();
			tableInfo.append(
					"----------------------------------------------------------------------------------------------------------------\n");
			tableInfo.append("| Site Name    | Active | InActive | Fault | Maintenance |\n");
			tableInfo.append(
					"-----------------------------------------------------------------------------------------------------------------\n");

			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));

				if (cells.size() >= 5) {
					String siteName = cells.get(0).getText();
					String active = cells.get(1).getText();
					String inActive = cells.get(2).getText();
					String fault = cells.get(3).getText();
					String maintenance = cells.get(4).getText();

					tableInfo.append(String.format("| %-40s | %-10s | %-10s | %-10s |%-10s |%n", siteName, active,
							inActive, fault, maintenance));

				}
			}

			extentReports.createTest("Dashboard of device status")
					.log(Status.INFO, "Displaying device status details:\n" + tableInfo.toString())
					.log(Status.PASS, "Displaying Successfully");
		} catch (Exception e) {
			extentReports.createTest("Dashboard of device status").log(Status.FAIL,
					"Failed to Displaying. Exception: " + e.getMessage());
			occupancyTabCapture(driver, "exception_screenshot");
		}
	}

	@Test(priority = 34)
	public void alertsTab() {
		try {
			WebElement alertTab = driver.findElement(By.xpath("//*[@id='sites_alerts']"));
			alertTab.click();
			String value = alertTab.getText();
			if (alertTab.isDisplayed()) {
				alertTab.click();
				extentReports.createTest("Alerts Tab is clicked").log(Status.PASS,
						"Successfully clicked the Alerts Tab : " + value);
			} else {
				extentReports.createTest("Alert  Tab is clicked").log(Status.WARNING, "Alert tab not found");
				Assert.fail("Alert tab not found");
			}
		} catch (Exception e) {

			extentReports.createTest("Alerts Tab is clicked").log(Status.FAIL,
					"Failed to click the Alerts Tab. Exception: " + e.getCause());
			occupancyTabCapture(driver, "exception_screenshot");
		}
	}

	@Test(priority = 35)
	public void alertsCount() {
		try {
			WebElement alerts = driver.findElement(By.xpath("//*[@id='sites_alerts']"));
			String value = alerts.getText();
			System.out.println("Alerts&Notifications : " + value);
			driver.findElement(By.tagName("tr"));
			Thread.sleep(5000);
			extentReports.createTest("Alerts Count").log(Status.PASS,
					"Successfully displaying the Alerts Count : " + value);
		} catch (Exception e) {
			extentReports.createTest("Alerts Count").log(Status.PASS,
					"Failed to displaying the Alerts Count" + e.getCause());
			occupancyTabCapture(driver, "exception_screenshot");
		}

	}

	@Test(priority = 36)
	public void logout() {
		try {
			Thread.sleep(2000);
			driver.findElement(By.id("userName")).click();
			driver.findElement(By.id("btnLogout")).click();
			extentReports.createTest("Dashboard LOGOUT ").log(Status.PASS, "Successfully logout from the Portal");
		} catch (Exception e) {
			extentReports.createTest("Dashboard LOGOUT ").log(Status.FAIL,
					"Failed to LogOut. Exception: " + e.getCause());
			occupancyTabCapture(driver, "exception_screenshot");
		}
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			extentReports.flush();
			driver.quit();
		}
	}

	public static void occupancyTabCapture(WebDriver driver, String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
			extentReports.createTest("Screenshot ").addScreenCaptureFromBase64String(
					Base64.getEncoder().encodeToString(screenshotBytes), screenshotName);
		} catch (Exception e) {
			extentReports.createTest("Screenshot ").log(Status.WARNING,
					"Failed to capture screenshot: " + e.getMessage());
		}
	}

	public static void loginDetails(WebDriver driver, ExtentReports extentReports) throws IOException {
		ServerCredentials server = new ServerCredentials();
		try {
			System.out.println(server.getUrl());
			driver.get(server.getUrl());
			Thread.sleep(2000);
			driver.findElement(By.id("username")).sendKeys(server.getUserName());
			driver.findElement(By.name("password")).sendKeys(server.getPassword());
			driver.findElement(By.name("login")).click();
			extentReports.createTest("Login details").log(Status.PASS,
					"Successfully enter the url details : " + server.getUrl()).log(Status.PASS,
							"Successfully enter the username details : " + server.getUserName())
					.log(Status.PASS,
							"Successfully enter the password details :" + server.getPassword());
		} catch (Exception e) {
			extentReports.createTest("Login details").log(Status.FAIL,
					"Failed to enter the login details. Exception: " + e.getCause());
		}
	}

	public static void revenuedashboardOpen(WebDriver driver, ExtentReports extentReports) {
		try {
			Thread.sleep(5000);
			driver.findElement(By.className("db-pg")).click();
			WebElement element = driver.findElement(By.xpath("//*[@id='page-wraper']/header/h5/span"));
			String textContent = element.getText();
			System.out.println("Text Content: " + textContent);

			extentReports.createTest("Dashboard ").log(Status.PASS, "Successfully Open Dashboard Revenue tab")
					.log(Status.INFO, "Page Title : " + textContent);

		} catch (Exception e) {
			extentReports.createTest("Dashboard").log(Status.FAIL,
					"Failed to Open the dashboard. Exception: " + e.getCause());
		}
	}

	public static void revenueDatesEnabled(WebDriver driver, ExtentReports extentReports) {
		try {
			Thread.sleep(1000);

			WebElement startRangeDate1 = driver.findElement(By.cssSelector(".startRangeDate"));
			WebElement endRangeDate1 = driver.findElement(By.cssSelector(".endRangeDate"));
			String initialStartDate = startRangeDate1.getText();
			String initialEndDate = endRangeDate1.getText();

			WebElement dayButton2 = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]"));
			dayButton2.click();
			Thread.sleep(1000);

			WebElement startRangeDate2 = driver.findElement(By.cssSelector(".startRangeDate"));
			WebElement endRangeDate2 = driver.findElement(By.cssSelector(".endRangeDate"));

			if (!initialStartDate.equals(startRangeDate2.getText())
					|| !initialEndDate.equals(endRangeDate2.getText())) {

				extentReports.createTest("Dates after clicking Day ").log(Status.FAIL,
						"Dates are different after clicking Day button: ").log(Status.INFO,
								"Start Date: " + startRangeDate2.getText() + " | End Date: " + endRangeDate2.getText());

				System.out.println("Dates are different after clicking Day button.");
			} else {
				extentReports.createTest("Dates after clicking Day ").log(Status.PASS,
						"Dates are the same after clicking Day  button: ").log(Status.INFO,
								"Start Date: " + startRangeDate2.getText() + " | End Date: "
										+ startRangeDate2.getText());
				System.out.println("Dates are the same after clicking Day button.");
			}

			WebElement weekButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]"));
			weekButton.click();
			Thread.sleep(1000);

			WebElement startRangeDate3 = driver.findElement(By.cssSelector(".startRangeDate"));
			WebElement endRangeDate3 = driver.findElement(By.cssSelector(".endRangeDate"));

			if (!initialStartDate.equals(startRangeDate3.getText())
					|| !initialEndDate.equals(endRangeDate3.getText())) {

				extentReports.createTest("Dates after clicking Week ").log(Status.PASS,
						"Dates are different after clicking Week button: ").log(Status.INFO,
								"Start Date: " + startRangeDate3.getText() + " | End Date: " + endRangeDate3.getText());

				System.out.println("Dates are different after clicking Week button.");
			} else {
				extentReports.createTest("Dates after clicking Week ").log(Status.FAIL,
						"Dates are the same after clicking Week  button: ").log(Status.INFO,
								"Start Date: " + startRangeDate3.getText() + " | End Date: "
										+ startRangeDate3.getText());
				System.out.println("Dates are the same after clicking Week button.");
			}

			WebElement monthButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[4]"));
			monthButton.click();
			Thread.sleep(1000);

			WebElement startRangeDate4 = driver.findElement(By.cssSelector(".startRangeDate"));
			WebElement endRangeDate4 = driver.findElement(By.cssSelector(".endRangeDate"));

			if (!initialStartDate.equals(startRangeDate4.getText())
					|| !initialEndDate.equals(endRangeDate4.getText())) {

				extentReports.createTest("Dates after clicking Month ").log(Status.PASS,
						"Dates are different after clicking Month button: ").log(Status.INFO,
								"Start Date: " + startRangeDate4.getText() + " | End Date: " + endRangeDate4.getText());

				System.out.println("Dates are different after clicking Month  button.");
			} else {
				extentReports.createTest("Dates after clicking Month ").log(Status.FAIL,
						"Dates are the same after clicking Month  button: ").log(Status.INFO,
								"Start Date: " + startRangeDate4.getText() + " | End Date: " + endRangeDate4.getText());
				System.out.println("Dates are the same after clicking Month button.");
			}

			extentReports.createTest("Dashboard Dates is clicked").log(Status.PASS,
					"Successfully clicked the Dashboard day/week/month");
		} catch (Exception e) {
			extentReports.createTest("Dashboard Dates is not clicked").log(Status.FAIL,
					"Failed to click the Dashboard day/week/month button. Exception: " + e.getCause());
		}
	}

	public static void selectDashboardStartDate(WebDriver driver, ExtentReports extentReports) {
		try {
			driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]"));
			Thread.sleep(1000);

			WebElement startRangeDate1 = driver.findElement(By.cssSelector(".startRangeDate"));
			WebElement endRangeDate1 = driver.findElement(By.cssSelector(".endRangeDate"));

			extentReports.createTest("Initial date ranges in Dashboard ").log(Status.INFO,
					"Start Date: " + startRangeDate1.getText() + " | End Date: " + endRangeDate1.getText());

			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]")).click();
			driver.findElement(By.xpath("//th[@class='prev available']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//th[@class='prev available']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//th[@class='prev available']")).click();
			Thread.sleep(1000);
			WebElement dateElement = driver.findElement(By.xpath("//td[@class='available' and text()='7']"));
			dateElement.click();
			extentReports.createTest("Start Date in Dashboard").log(Status.PASS,
					"Successfully selected Start Date: ");

		} catch (Exception e) {
			extentReports.createTest("Start Date in Dashboard").log(Status.FAIL,
					"Failed to select Start Date. Exception: " + e.getMessage());
		}
	}

	public static void selectDashboardEndDate(WebDriver driver, ExtentReports extentReports) {
		try {
			Thread.sleep(2000);
			driver.findElement(By.xpath("//th[@class='next available']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//th[@class='next available']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//th[@class='next available']")).click();
			WebElement dateElement = driver
					.findElement(By.xpath(
							"//td[@class='available' and @data-title='r3c4']"));
			dateElement.click();

			extentReports.createTest("End Date in Dashboard").log(Status.PASS,
					"Successfully selected End Date: ");
			Thread.sleep(3000);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "$('.applyBtn').click();";
			js.executeScript(script);

			driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[1]"));
			Thread.sleep(1000);

			WebElement startRangeDate1 = driver.findElement(By.cssSelector(".startRangeDate"));
			WebElement endRangeDate1 = driver.findElement(By.cssSelector(".endRangeDate"));

			extentReports.createTest("After selecting Dates Ranges in Dashboard ").log(Status.INFO,
					"Start Date: " + startRangeDate1.getText() + " | End Date: " + endRangeDate1.getText());
		} catch (Exception e) {

			extentReports.createTest("End Date in Dashboard").log(Status.FAIL,
					"Failed to select End Date. Exception: " + e.getMessage());
		}
	}
}
