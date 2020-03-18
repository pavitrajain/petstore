package io.swagger.util;

import org.slf4j.Logger;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static java.util.Objects.isNull;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.slf4j.LoggerFactory.getLogger;

public class MicroservicesEnvConfig {
    private static final Logger LOGGER = getLogger(MicroservicesEnvConfig.class);
    public static SoftAssert  softAssert = new SoftAssert();
    public static String BASEURL;
    static {
        WebServiceConfig WEBSERVICECONFIG = create(WebServiceConfig.class);
        String RUN_TIME_PARAM1 = "baseURL";
        BASEURL = System.getProperty(RUN_TIME_PARAM1);
        if(isNull(BASEURL)) {
            BASEURL = WEBSERVICECONFIG.baseURL();
            LOGGER.info(RUN_TIME_PARAM1 + " is not provided. Using Base URL: " + BASEURL + " for test execution");
        } else {
            LOGGER.info("Starting execution on Base URL: " + BASEURL);
        }
    }
}