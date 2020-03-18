package io.swagger.petAPI;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.swagger.extent_report.ExtentTestManager;
import io.swagger.pet.handler.AddPetServiceHandler;
import io.swagger.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

@Listeners(io.swagger.extent_report.TestListner.class)
public class AddPetTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddPetTest.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);
    private static long petId, categoryId, tagId;
    private static String categoryName, petName, photoUrls, tagName, petStatus;

    @BeforeClass
    public static void configLogs() {
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));
    }

    @BeforeMethod
    public void initializeTest() {
        petId = -1;
        categoryId = -1;
        categoryName = null;
        petName = null;
        photoUrls = null;
        tagId = -1;
        tagName = null;
        petStatus = null;
        MicroservicesEnvConfig.softAssert = new SoftAssert();
    }

    @Test(groups = {"positive_scenario", "Test_1"})
    public void verify_AddPet_Request_With_ValidValues(Method method) {
        APILogger.logTestStart(LOGGER, method,"=== verify_AddPet_Request_With_ValidValues ===");
        ExtentTestManager.getTest().assignCategory("Add Pet Service", "Positive Scenario");

        /* Setting test data */
        petId = Long.parseLong(RandomDataGenerator.generateRandomNumeric(6));
        categoryId =  Long.parseLong(RandomDataGenerator.generateRandomNumeric(5));
        categoryName = "Category_" + RandomDataGenerator.generateRandomAlpha(5);
        petName = "Dog_" + RandomDataGenerator.generateRandomAlpha(6);
        photoUrls = "petstore.swagger.io/photo/" + RandomDataGenerator.generateRandomAlpha(7);
        tagId = Long.parseLong(RandomDataGenerator.generateRandomNumeric(5));
        tagName = "Tag_" + RandomDataGenerator.generateRandomAlpha(5);
        petStatus = "available";

        /* Calling add pet service */
        Response response = AddPetServiceHandler.addPetServiceCall(petId, categoryId, categoryName, petName, photoUrls, tagId, tagName, petStatus);

        /* Verifying pet information added successfully */
        AddPetServiceHandler.verifyAddPetResponse(response, petId, categoryId, categoryName, petName, photoUrls, tagId, tagName, petStatus);
        AddPetServiceHandler.verifyPetDetailsAreAdded(petId, categoryId, categoryName, petName, photoUrls, tagId, tagName, petStatus);
        TestDataUtil.addPetId(Long.toString(petId));
        MicroservicesEnvConfig.softAssert.assertAll();
    }
}
