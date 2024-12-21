package com.task.lead;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.http.Header;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExtentReportConfiguration {
    public static ExtentReports extentReports;

    public ExtentReportConfiguration() {
    }

    public static ExtentReports createInstance(String fileName, String reportName, String documentTitle) {
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
        extentSparkReporter.config().setReportName(reportName);
        extentSparkReporter.config().setDocumentTitle(documentTitle);
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setEncoding("utf-8");
        extentReports = new ExtentReports();
        extentReports.attachReporter(new ExtentObserver[]{extentSparkReporter});
        return extentReports;
    }

    public static void logPassDetails(String log) {
        ((ExtentTest)ExtentReport.extentTest.get()).pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
    }

    public static void logFailureDetails(String log) {
        ((ExtentTest)ExtentReport.extentTest.get()).fail(MarkupHelper.createLabel(log, ExtentColor.RED));
    }

    public static void logExceptionDetails(String log) {
        ((ExtentTest)ExtentReport.extentTest.get()).fail(log);
    }

    public static void logInfoDetails(String log) {
        ((ExtentTest)ExtentReport.extentTest.get()).info(MarkupHelper.createLabel(log, ExtentColor.GREY));
    }

    public static void logJson(String json) {
        ((ExtentTest)ExtentReport.extentTest.get()).info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }

    public static void logWarningDetails(String log) {
        ((ExtentTest)ExtentReport.extentTest.get()).warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
    }
}
