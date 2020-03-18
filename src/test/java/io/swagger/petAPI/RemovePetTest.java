package io.swagger.petAPI;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.swagger.extent_report.ExtentTestManager;
import io.swagger.pet.handler.DeletePetServiceHandler;
import io.swagger.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.management.ListenerNotFoundException;
import java.lang.reflect.Method;

import static java.util.Objects.isNull;

@Listeners(io.swagger.extent_report.TestListner.class)
public class RemovePetTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemovePetTest.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);
    private static long petId;
    private static String apiKey;

    @BeforeClass
    public static void configLogs() {
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));
    }

    @BeforeMethod
    public void initializeTest() {
        petId = -1;
        apiKey = null;
        MicroservicesEnvConfig.softAssert = new SoftAssert();
    }

    @Test(groups = {"positive_scenario", "Test_2"})
    public void verify_DeletePet_Request_With_ValidValues(Method method) {
        APILogger.logTestStart(LOGGER, method,"=== verify_DeletePet_Request_With_ValidValues ===");
        ExtentTestManager.getTest().assignCategory("Delete Pet Service", "Positive Scenario");

        /* Setting test data */
        String pet = TestDataUtil.getPet();
        petId = isNull(pet) ? -1 : Long.parseLong(pet);

        /* Calling add pet service */
        Response response = DeletePetServiceHandler.deletePetServiceCall(apiKey, petId);

        /* Verifying pet information deleted successfully */
        DeletePetServiceHandler.verifySuccessStatusCodeInDeletePetResponse(response);
        DeletePetServiceHandler.verifyPetInformationIsDeleted(petId);
        TestDataUtil.updateRemovedFlag(pet);
        MicroservicesEnvConfig.softAssert.assertAll();
    }

}
