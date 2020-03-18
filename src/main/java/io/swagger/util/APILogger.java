package io.swagger.util;

import com.relevantcodes.extentreports.LogStatus;
import io.swagger.extent_report.ExtentTestManager;
import org.slf4j.Logger;

import java.lang.reflect.Method;

public class APILogger {

    public static void logInfo(Logger LOGGER, String message) {
        LOGGER.info(message);
        message = message.replace("\n","<br />");
        ExtentTestManager.getTest().log(LogStatus.INFO, message);
    }

    public static void logTestStart(Logger LOGGER, Method method, String message) {
        LOGGER.info(message);
        message = message.replace("\n","<br />");
        ExtentTestManager.startTest(method.getName(), message);
    }

    public static void logTestPassed(Logger LOGGER, String message) {
        LOGGER.info(message);
        message = message.replace("\n", "<br />");
        ExtentTestManager.getTest().log(LogStatus.PASS, message);
    }

    public static void logTestFailed(Logger LOGGER, String message) {
        LOGGER.info(message);
        message = message.replace("\n", "<br />");
        ExtentTestManager.getTest().log(LogStatus.FAIL, message);
    }
}
