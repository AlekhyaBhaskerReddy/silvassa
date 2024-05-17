package com.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;




public class DashboardRevenue {
	WebDriver driver;
	private static ExtentReports extent;
	private static ExtentSparkReporter spark;  
	
public static void main(String[] args){
	DashboardRevenue selenium = new DashboardRevenue();
	selenium.alertsCount();
}
	public void setUp() {
	  extent = new ExtentReports();
			  spark = new ExtentSparkReporter("reports/Login.html");
			  extent.attachReporter(spark);
	  System.setProperty(
		"webdriver.chrome.driver",
		"/home/iramtech/iRam_Folder/web_driver/114/chromedriver"
	  );
	  driver = new ChromeDriver();
	//   ChromeOptions options = new ChromeOptions();
	//   options.addArguments("headless");      
	//   driver = new ChromeDriver(options);
	  driver.manage().window().maximize();
    }

    public void login() throws InterruptedException{

        driver.get("https://jhansi.eparke.in/SmartCity/ui/admin/");
		driver.findElement(By.id("username")).sendKeys("testuser");
		driver.findElement(By.id("password")).sendKeys("welcome@123");
		driver.findElement(By.name("login")).click();
        extent.createTest("Login").log(Status.PASS,"Successfully Login");
        Thread.sleep(2000);
    }
    public void revenueDashboardOpen() throws InterruptedException{
		Thread.sleep(1000);
		driver.findElement(By.className("db-pg")).click();
		extent.createTest("Dashboard ").log(Status.PASS,"Successfully Open Dashboard Revenue tab");
        Thread.sleep(2000);
		}
    public void revenueDatesEnabled() throws InterruptedException{
		try {
      
			WebElement dayButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]"));
			dayButton.click();
			Thread.sleep(1000);
			
			WebElement weekButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]"));
			weekButton.click();
			Thread.sleep(1000);
	
			WebElement monthButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[4]"));
			monthButton.click();
			Thread.sleep(1000);
	
			extent.createTest("Dashboard Dates is clicked").log(Status.PASS, "Successfully clicked the Dashboard day/week/month");
		} catch (Exception e) {
		  
			extent.createTest("Dashboard Dates is not clicked").log(Status.FAIL, "Failed to click the Dashboard day/week/month button. Exception: " + e.getCause());
		} 
	}
    public void getDayRevenue(){
			try{
			WebElement dayButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[2]"));
			dayButton.click();	
		    WebElement oneDay = driver.findElement(By.xpath("//*[@id='total_revenue']"));
		    String value2 = oneDay.getText(); 
		    System.out.println("One-Day: " + value2);
		    Thread.sleep(4000);
			extent.createTest("Day Revenu is Displaying ").log(Status.PASS, "Amount is displaying Successfully"  + value2);
			} catch (Exception e) {
			extent.createTest("Day Revenue is not displaying").log(Status.FAIL, "Failed Amount is not Displaying. Exception: " + e.getMessage());
			} 	
	}

    public void getWeekRevenue(){
			try{
			WebElement weekButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[4]"));
			weekButton.click();	
		    WebElement oneWeek = driver.findElement(By.xpath("//*[@id='total_revenue']"));
		    String value2 = oneWeek.getText(); 
		    System.out.println("One-week: " + value2);
		    Thread.sleep(4000);
			extent.createTest("Week Revenu is Displaying ").log(Status.PASS, "Amount is displaying Successfully"  + value2);
			} catch (Exception e) {
			extent.createTest("Week Revenue is not displaying").log(Status.FAIL, "Failed Amount is not Displaying. Exception: " + e.getMessage());
			} 	
	}

    public void getMonthRevenue(){
			try{
			WebElement monthButton = driver.findElement(By.xpath("//div[@id='dayRangeFilter']/label[3]"));
			monthButton.click();
			WebElement oneMonth = driver.findElement(By.xpath("//*[@id='total_revenue']"));
			String value1 = oneMonth.getText(); 
			System.out.println("One-Month: " + value1);
			extent.createTest("Dashboard Month Revenue").log(Status.PASS, "Successfully display Dashboard month revenue"  + value1);
			Thread.sleep(4000);
		}catch(Exception e){
			extent.createTest("Dashboard Month Revenue").log(Status.FAIL, "Failed to display Dashboard month revenue. Exception: " + e.getCause());

		} 		
	}
    public void selectCountDashboard(){
			try{
			WebElement dropdown = driver.findElement(By.name("summaryTable_length"));
			Select select = new Select(dropdown);
			select.selectByValue("25");
			extent.createTest("Dashboard select the Display List count").log(Status.PASS, "Successfully selected as : "  + dropdown);
			Thread.sleep(4000);
		}catch(Exception e){
			extent.createTest("Dashboard select the Display List count").log(Status.FAIL, "Failed to select the count. Exception: " + e.getCause());

		} 		
	}
	
    public void sitenamesDashboard() {
		
		try {
		WebElement table = driver.findElement(By.id("summaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"| Site Name    | Revenue | Two Wheeler Revenue | Two Wheeler Count |four Wheeler Revenue |four Wheeler Count| ");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------");

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			if (cells.size() >= 6) {

				String siteName = cells.get(0).getText();
				String revenue = cells.get(1).getText();
				String twoWheelerRevenue = cells.get(2).getText();
				String TwoWheelerCount = cells.get(3).getText();
				String fourWheelerRevenue = cells.get(4).getText();
				String fourWheelerCount = cells.get(5).getText();

				System.out.printf("| %-30s | %-10s | %-10s | %-10s |%-10s |%-10s |%n", siteName, revenue,
						twoWheelerRevenue, TwoWheelerCount, fourWheelerRevenue, fourWheelerCount);
			}
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------");

			Thread.sleep(3000);
		}
			extent.createTest("Summary Table ").log(Status.PASS, "Displaying Successfully" );
			} catch (Exception e) {
			extent.createTest("Summary Table").log(Status.FAIL, "Failed to Displaying. Exception: " + e.getCause());
			} 	
		}
    public void cityRevenueViewButton()   {
		try{
			Thread.sleep(2000);

			driver.findElement(By.id("city_revenue_details")).click();
			extent.createTest("View Revenue Details Button").log(Status.PASS, "Successfully clicked the view button ");
		} catch (Exception e) {
		  
			extent.createTest("View Revenue Details Button").log(Status.FAIL, "Failed to click the view button. Exception: " + e.getCause());
		} 
		  extent.flush();
	}		
    public void cityRevenueCSVButton()   {
		try{
			Thread.sleep(2000);

			driver.findElement(By.id("cityRevenueCsv")).click();
			extent.createTest("CSV in City Revenue Details ").log(Status.PASS, "Successfully clicked/download the CSV file ");
		} catch (Exception e) {
		  
			extent.createTest("CSV in City Revenue Details ").log(Status.FAIL, "Failed to clicked/download the CSV file. Exception: " + e.getCause());
		} 
		  extent.flush();
	}		

    public void cityRevenuePDFButton()   {
		try{
			Thread.sleep(2000);

			driver.findElement(By.id("cityRevenuePdf")).click();
			extent.createTest("PDF in City Revenue Details").log(Status.PASS, "Successfully clicked/download the PDF file ");
		} catch (Exception e) {
		  
			extent.createTest("PDF in City Revenue Details ").log(Status.FAIL, "Failed to clicked/download the PDF file. Exception: " + e.getCause());
		} 
		  extent.flush();
	}		
    public void cityRevenuePopUpClose()   {
		try{
			Thread.sleep(2000);

			driver.findElement(By.className("close")).click();
			extent.createTest("POP Up in City Revenue Details").log(Status.PASS, "Successfully closed ");
		} catch (Exception e) {
		  
			extent.createTest("POP Up in City Revenue Details ").log(Status.FAIL, "Failed to closed. Exception: " + e.getCause());
		} 
		  extent.flush();
	}		
    public void cityRevenueColumnFilterBtn()   {
		try{
			Thread.sleep(2000);

			driver.findElement(By.id("tableEdit")).click();
			extent.createTest("Column Filter in City Revenue Details").log(Status.PASS, "Successfully Clicked ");
		} catch (Exception e) {
		  
			extent.createTest("POP Up in City Revenue Details ").log(Status.FAIL, "Failed to clicked. Exception: " + e.getCause());
		} 
		  extent.flush();
	}		
    public void columnFilterSelectVehicleTypes()  {
        try{
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
		extent.createTest("Vehicle deselected in Column Filter").log(Status.PASS, "Successfully de-selected ");
	}catch(Exception e){
		extent.createTest("Vehicle deselected in Column Filter").log(Status.FAIL, "Failed to de-select. ");
		
	}
	}
    public void columnFilterPopUpClose()   {
		try{
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='tableEditModal']/div/div/div[1]/button")).click();
			extent.createTest("POP Up in Column Filter").log(Status.PASS, "Successfully closed ");
		} catch (Exception e) {		  
			extent.createTest("POP Up in Column Filter").log(Status.FAIL, "Failed to closed. Exception: " + e.getCause());
		} 
	}			
    public void occupancyTabDashboard()   {
		try{
			Thread.sleep(2000);

			driver.findElement(By.xpath("//div[@id='occupancy_chart_summary']")).click();
			extent.createTest("Occupancy Tab is clicked").log(Status.PASS, "Successfully clicked the Occupancy Tab");
		} catch (Exception e) {
		  
			extent.createTest("Occupancy Tab is not clicked").log(Status.FAIL, "Failed to click the Occupancy. Exception: " + e.getCause());
		} 
	}
    public void getOccupancyPercentage()   {
		try{
			WebElement avgOccupancy = driver.findElement(By.xpath("//div[@id='occupancy']"));
			String value = avgOccupancy.getText();
			System.out.println("Displayed Average-Occupancy: " + value);
			extent.createTest("Occupancy Percentage").log(Status.PASS, "Successfully displaying the Occupancy percent" +value);
		}catch(Exception e){
			extent.createTest("Occupancy Percentage").log(Status.FAIL, "Failed to displaying the Occupancy percent.Exception" +e.getCause());
		}			
	}
    public void getOperatingSitesPercentage()   {
		try{
			WebElement operatingSites = driver.findElement(By.xpath("//div[@id='operating_sites']"));
			String value = operatingSites.getText();
			System.out.println("Displayed operating site percentage: " + value);
			extent.createTest("Operating Sites Percentage").log(Status.PASS, "Successfully displaying the operating_sites percent" +operatingSites);
		}catch(Exception e){
			extent.createTest("Operating Sites Percentage").log(Status.FAIL, "Failed to displaying the operating_sites percent.Exception" +e.getCause());
		}			
	}
    public void operatingsSitesSelectCount(){
			try{
			Thread.sleep(2000);
			WebElement dropdown = driver.findElement(By.className("allSiteTable_length"));
			Select select = new Select(dropdown);
			Thread.sleep(2000);
			select.selectByValue("25");
			extent.createTest("Operating Site Status select the Display List count").log(Status.PASS, "Successfully selected as ");
			Thread.sleep(2000);
		}catch(Exception e){
			extent.createTest("Operating Site Status select the Display List count").log(Status.FAIL, "Failed to select the count. Exception: " + e.getMessage());
		} 		
	}
	public void operatingsitesList()   {
		try{

			Thread.sleep(2000);
			driver.findElement(By.id("operating_sites")).click();
			WebElement sitestatus = driver.findElement(By.xpath("//div[@id='operating_sites']"));
			String value = sitestatus.getText();
			System.out.println("Site Status-Percentage : " + value);
	
			System.out.println("------------------------------------");
			System.out.println("| Site Name    | Status | ");
	
			WebElement table = driver.findElement(By.id("allSiteTable"));
	
			WebElement tbody = table.findElement(By.tagName("tbody"));
			java.util.List<WebElement> rows = tbody.findElements(By.tagName("tr"));
	
			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
	
				if (cells.size() >= 2) {
					String siteName = cells.get(0).getText();
					String status = cells.get(1).getText();
	
					System.out.printf("| %-50s | %-15s |%n", siteName, status);
					System.out.println("----------------------------------------");
				}
			}
			extent.createTest("SitesName and Status ").log(Status.PASS, "Successfully displaying the names and status " + value);
		}catch(Exception e){
			extent.createTest("SitesName and Status ").log(Status.FAIL, "Failed displaying the names and status.Execption:" +e.getCause());
		}
	}
    public void deviceStatusTab() {		
		try{

			driver.findElement(By.xpath("//*[@id='device_status_summary']")).click();
			extent.createTest("Device Status Tab is clicked").log(Status.PASS, "Successfully clicked the Device status Tab");
		} catch (Exception e) {
		  
			extent.createTest("Device Status Tab is clicked").log(Status.FAIL, "Failed to click the Device status. Exception: " + e.getCause());
		} 
	}
    public void selectCountDeviceStatus(){
			try{
			WebElement dropdown = driver.findElement(By.name("allSiteDiviceTbl_length"));
			Select select = new Select(dropdown);
			select.selectByValue("25");
			extent.createTest("Dashboard Device Status select the Display List count").log(Status.PASS, "Successfully selected as : "  + dropdown);
			Thread.sleep(4000);
		}catch(Exception e){
			extent.createTest("Dashboard Device Status select the Display List count").log(Status.FAIL, "Failed to select the count. Exception: " + e.getCause());
		} 		
	}
    public void deviceStatusPercentage() throws InterruptedException {
		try{
			WebElement deviceStatus = driver.findElement(By.xpath("//*[@id='device_status_summary']/div"));
			String value = deviceStatus.getText();
			System.out.println("DeviceStatus-Percentage : " + value);
			driver.findElement(By.tagName("tr"));
			Thread.sleep(3000);
			extent.createTest("Device status Percentage").log(Status.PASS, "Successfully displaying the Device status percent" + value);
		}catch(Exception e){
			extent.createTest("Device Status Percentage").log(Status.FAIL, "Failed to get the Device status percent. Exception: " + e.getCause());
		}
	}
    public void devicesStatusTable() {
		try {
		WebElement table = driver.findElement(By.id("allSiteDiviceTbl"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"| Site Name    | Active | InActive | Fault | Maintenance |");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------");

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			if (cells.size() >= 5) {

				String siteName = cells.get(0).getText();
				String active = cells.get(1).getText();
				String inActive = cells.get(2).getText();
				String fault = cells.get(3).getText();
				String maintenance = cells.get(4).getText();

				System.out.printf("| %-40s | %-10s | %-10s | %-10s |%-10s |%n", siteName, active,
				inActive, fault, maintenance);
			}
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------");

			Thread.sleep(3000);
		}
			extent.createTest("Summary Table ").log(Status.PASS, "Displaying Successfully" );
			} catch (Exception e) {
			extent.createTest("Summary Table").log(Status.FAIL, "Failed to Displaying. Exception: " + e.getCause());
			} 	
		}
	public void alertsTab() {
		try{

			driver.findElement(By.xpath("//*[@id='sites_alerts']")).click();
			extent.createTest("Alerts Tab is clicked").log(Status.PASS, "Successfully clicked the Alerts Tab");
		} catch (Exception e) {
		  
			extent.createTest("Alerts Tab is clicked").log(Status.FAIL, "Failed to click the Alerts Tab. Exception: " + e.getCause());
		} 
	}
	public void alertsCount()   {
		try{

			WebElement alerts = driver.findElement(By.xpath("//*[@id='sites_alerts']"));
			String value = alerts.getText();
			System.out.println("Alerts/Notifications : " + value);
			driver.findElement(By.tagName("tr"));
			Thread.sleep(5000);
			extent.createTest("Alerts Count").log(Status.PASS, "Successfully displaying the Alerts Count" + value);
		}catch(Exception e){
			extent.createTest("Alerts Count").log(Status.PASS, "Failed to displaying the Alerts Count"+e.getCause());
		}

	}
 	public void logout() throws InterruptedException {
		Thread.sleep(2000);
		try{

			driver.findElement(By.id("userName")).click();
			driver.findElement(By.id("btnLogout")).click();
			extent.createTest("LogOut ").log(Status.PASS, "Successfully LogOut from the Portal");
		}catch(Exception e){
            extent.createTest("LogOut").log(Status.FAIL, "Failed to LogOut. Exception: " + e.getCause());
		}
	}
    public void tearDown() {
        if (driver != null) {
			extent.flush();
            driver.quit();
        }
    }
}
