package io.swagger.extent_report;

import com.relevantcodes.extentreports.LogStatus;
import io.swagger.util.APILogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListner implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListner.class);

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentReportManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        APILogger.logTestPassed(LOGGER, "=== Test Case: " + iTestResult.getMethod().getConstructorOrMethod().getName() + " : PASSED ===");
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String failureLog = iTestResult.getThrowable().toString();
        APILogger.logTestFailed(LOGGER, "=== Test Case: " + iTestResult.getMethod().getConstructorOrMethod().getName() + " : FAILED  === \n" + failureLog);
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        ExtentTestManager.getTest().log(LogStatus.SKIP, "TEST SKIPPED");
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }
}
