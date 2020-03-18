package io.swagger.storeAPI;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.swagger.extent_report.ExtentTestManager;
import io.swagger.petAPI.RemovePetTest;
import io.swagger.store.handler.PlaceStoreOrderServiceHandler;
import io.swagger.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

import static java.util.Objects.isNull;

@Listeners(io.swagger.extent_report.TestListner.class)
public class PlaceOrderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemovePetTest.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);

    @BeforeClass
    public static void configLogs() {
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));
    }

    @BeforeMethod
    public void initializeTest() {
        MicroservicesEnvConfig.softAssert = new SoftAssert();
    }

    @Test(groups = {"positive_scenario", "Test_3"})
    public void verify_PlaceStoreOrder_Request_With_ValidValues(Method method) {
        APILogger.logTestStart(LOGGER, method,"=== verify_PlaceStoreOrder_Request_With_ValidValues ===");
        ExtentTestManager.getTest().assignCategory("Place Store Order Service", "Positive Scenario");

        /* Setting test data */
        long id = Long.parseLong(RandomDataGenerator.generateRandomNumeric(7));
        String pet = TestDataUtil.getPet();
        long petId = isNull(pet) ? -1 : Long.parseLong(pet);
        int quantity = Integer.parseInt(RandomDataGenerator.generateRandomNumeric(1));
        String shipDate = "2021-03-16T19:18:19.958Z";
        String status = "placed";

        /* Calling add pet service */
        Response response = PlaceStoreOrderServiceHandler.placeOrderServiceCall(id, petId, quantity, shipDate, status, true);

        /* Verifying pet information added successfully */
        PlaceStoreOrderServiceHandler.verifyPlaceStoreOrderResponse(response, id, petId, quantity, shipDate, status, true);
        PlaceStoreOrderServiceHandler.verifyOrderIsPlaced(id, petId, quantity, shipDate, status, true);
        MicroservicesEnvConfig.softAssert.assertAll();
    }
}
