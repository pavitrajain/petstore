package io.swagger.extent_report;

import com.relevantcodes.extentreports.ExtentReports;
import io.swagger.util.MicroservicesEnvConfig;

import static java.util.Objects.isNull;

public class ExtentReportManager {

    private static ExtentReports reporter;

    public static ExtentReports getReporter() {
        if (isNull(reporter)) {
            reporter =  new ExtentReports(System.getProperty("user.dir") + "/target/ExtentReport/ExtentReport.html", true);
            reporter.addSystemInfo("Base URL", MicroservicesEnvConfig.BASEURL);
        }
        return reporter;
    }
}
