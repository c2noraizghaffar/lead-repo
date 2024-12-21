package com.task.lead;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class ExtentReport implements ITestListener {
    private static ExtentReports extentReports;
    static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public ExtentReport() {}

    @Override
    public void onStart(ITestContext context) {
        String path = System.getProperty("user.dir");
        String fullReportPath = path + "\\report";
        extentReports = ExtentReportConfiguration.createInstance(fullReportPath, "QA Report", "QA Report");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a test node in ExtentReports for the current test method
        String testCaseName = result.getMethod().getMethodName();
        ExtentTest testNode = extentReports.createTest(testCaseName);

        // Add additional details if the test method has description or group
        if (result.getMethod().getDescription() != null) {
            testNode.assignCategory(result.getMethod().getGroups());
            testNode.info(result.getMethod().getDescription());
        }

        // Set the test node in the thread-local variable
        extentTest.set(testNode);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.pass("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.fail("Test Failed");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.skip("Test Skipped: " + result.getThrowable().getMessage());
        }
    }
}
