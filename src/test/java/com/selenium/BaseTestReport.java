package com.selenium;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTestReport {
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    public static ExtentSparkReporter spark;

    @BeforeSuite
    public void setUp() {
        extentReports = new ExtentReports();
        spark = new ExtentSparkReporter("results/Silvassa-Report_April_4.html");
        extentReports.attachReporter(spark);
        extentTest = extentReports.createTest("JMC_WebApplication");

    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, MarkupHelper
                    .createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS,
                    MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
        } else {
            extentTest.log(Status.SKIP,
                    MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
            extentTest.skip(result.getThrowable());
        }
    }

    @AfterSuite
    public void tearDown() {
        extentReports.flush();
    }
}
