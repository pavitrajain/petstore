package io.swagger.pet.handler;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.pet.model.PetResponse;
import io.swagger.pet.model.Tag;
import io.swagger.util.APILogger;
import io.swagger.util.MicroservicesEnvConfig;
import io.swagger.util.RestAssuredLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class AddPetServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddPetServiceHandler.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);

    /**
     * Method to return add pet service resource path
     * @return add pet services resource path as String
     */
    private static String addPetResource() {
        return "/pet";
    }

    /**
     * Method to return add pet service payload
     * @param petId - pet id
     * @param categoryId - category id
     * @param categoryName - category name
     * @param petName - pet name
     * @param photoUrl - photo url
     * @param tagId - tag id
     * @param tagName - tag name
     * @param petStatus - pet status e.g. available / pending / sold
     * @return add pet service payload as String
     */
    private static String addPetPayload(long petId, long categoryId, String categoryName, String petName, String photoUrl, long tagId, String tagName, String petStatus) {
        String payload ;
        if(petId < 0 && categoryId < 0 && tagId < 0 && isNull(petStatus)) {
            payload = "{\n" +
                    "  \"name\": \"" + petName + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"" + photoUrl + "\"\n" +
                    "  ]\n" +
                    "}";
        } else if(petId < 0 && categoryId < 0 && tagId < 0) {
            payload = "{\n" +
                    "  \"name\": \"" + petName + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"" + photoUrl + "\"\n" +
                    "  ],\n" +
                    "  \"status\": \"" + petStatus + "\"\n" +
                    "}";
        } else if(petId < 0 && categoryId < 0) {
            payload = "{\n" +
                    "  \"name\": \"" + petName + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"" + photoUrl + "\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": " + tagId + ",\n" +
                    "      \"name\": \"" + tagName + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"" + petStatus + "\"\n" +
                    "}";
        } else if(petId < 0) {
            payload = "{\n" +
                    "  \"category\": {\n" +
                    "    \"id\": " + categoryId + ",\n" +
                    "    \"name\": \"" + categoryName + "\"\n" +
                    "  },\n" +
                    "  \"name\": \"" + petName + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"" + photoUrl + "\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": " + tagId + ",\n" +
                    "      \"name\": \"" + tagName + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"" + petStatus + "\"\n" +
                    "}";
        } else {
            payload = "{\n" +
                    "  \"id\": " + petId + ",\n" +
                    "  \"category\": {\n" +
                    "    \"id\": " + categoryId + ",\n" +
                    "    \"name\": \"" + categoryName + "\"\n" +
                    "  },\n" +
                    "  \"name\": \"" + petName + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"" + photoUrl + "\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": " + tagId + ",\n" +
                    "      \"name\": \"" + tagName + "\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \"" + petStatus + "\"\n" +
                    "}";
        }
        return payload;
    }

    /**
     * Method to return add pet service request specification
     * @param petId - pet id
     * @param categoryId - category id
     * @param categoryName - category name
     * @param petName - pet name
     * @param photoUrl - photo url
     * @param tagId - tag id
     * @param tagName - tag name
     * @param petStatus - pet status e.g. available / pending / sold
     * @return add pet service request specification
     */
    private static RequestSpecification addPetRequest(long petId, long categoryId, String categoryName, String petName, String photoUrl, long tagId, String tagName, String petStatus) {
        RestAssured.baseURI = MicroservicesEnvConfig.BASEURL;
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));

        APILogger.logInfo(LOGGER,"Add Pet Service Request...");
        return RestAssured.given().header("Content-Type", "application/json").body(addPetPayload(petId, categoryId, categoryName, petName, photoUrl, tagId, tagName, petStatus)).log().all();
    }

    /**
     * Method to call add pet service and return response
     * @param petId - pet id
     * @param categoryId - category id
     * @param categoryName - category name
     * @param petName - pet name
     * @param photoUrl - photo url
     * @param tagId - tag id
     * @param tagName - tag name
     * @param petStatus - pet status e.g. available / pending / sold
     * @return add pet service response
     */
    public static Response addPetServiceCall(long petId, long categoryId, String categoryName, String petName, String photoUrl, long tagId, String tagName, String petStatus) {
        Response response = AddPetServiceHandler.addPetRequest(petId, categoryId, categoryName, petName, photoUrl, tagId, tagName, petStatus).when().post(AddPetServiceHandler.addPetResource());
        APILogger.logInfo(LOGGER, "Add Pet Service Response...");
        response.then().log().all();
        return response;
    }

    /**
     * Method to verify add pet service response status code is 200
     * @param response - add pet service Response
     */
    public static void verifySuccessStatusCodeInAddPetResponse(Response response) {
        int statusCode = response.then().extract().statusCode();
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - status code, Expected: 200, Actual: " + statusCode);
        MicroservicesEnvConfig.softAssert.assertEquals(statusCode, 200, "Add Pet service response - status code error");
    }

    /**
     * Method to verify add pet service response details
     * @param response - Add pet service response specification
     * @param expectedPetId - expected pet id
     * @param expectedCategoryId - expected category id
     * @param expectedCategoryName - expected category name
     * @param expectedPetName - expected pet name
     * @param expectedPhotoUrl - expected photo urls
     * @param expectedTagId - expected tag id
     * @param expectedTagName - expected tag name
     * @param expectedPetStatus - expected pet status
     */
    public static void verifyAddPetResponse(Response response, long expectedPetId, long expectedCategoryId, String expectedCategoryName, String expectedPetName, String expectedPhotoUrl, long expectedTagId, String expectedTagName, String expectedPetStatus) {
        verifySuccessStatusCodeInAddPetResponse(response);

        PetResponse petResponse = response.as(PetResponse.class);

        long actualPetId = petResponse.getId();
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - id, Actual: " + actualPetId + " , Expected: " + expectedPetId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetId, expectedPetId, "Add Pet service response - id field error");

        long actualCategoryId = petResponse.getCategory().getId();
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - category - id, Actual: " + actualCategoryId + " , Expected: " + expectedCategoryId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualCategoryId, expectedCategoryId, "Add Pet service response - Category - id field error");

        String actualCategoryName = petResponse.getCategory().getName();
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - category - name, Actual: " + actualCategoryName + " , Expected: " + expectedCategoryName);
        MicroservicesEnvConfig.softAssert.assertEquals(actualCategoryName, expectedCategoryName, "Add Pet service response - category - name field error");

        String actualPetName = petResponse.getName();
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - name, Actual: " + actualPetName + " , Expected: " + expectedPetName);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetName, expectedPetName, "Add Pet service response - name field error");

        List<String> actualPhotoUrls = petResponse.getPhotoUrls();
        List<String> expectedPhotoUrls = Arrays.asList(expectedPhotoUrl.split(","));
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - photo url, Actual: " + actualPhotoUrls.toString() + " , Expected: " + expectedPhotoUrls.toString());
        MicroservicesEnvConfig.softAssert.assertEquals(actualPhotoUrls, expectedPhotoUrls, "Add Pet service response - photo url field error");

        Optional<Tag> actualTags = petResponse.getTags().stream().filter((e)->e.getId() == expectedTagId).findFirst();
        if(actualTags.isPresent()) {
            String actualTagName = actualTags.get().getName();
            APILogger.logInfo(LOGGER,"Verifying Add Pet service response - tags - name, Actual: " + actualTagName + " , Expected: " + expectedTagName);
            MicroservicesEnvConfig.softAssert.assertTrue(true);
        } else {
            MicroservicesEnvConfig.softAssert.fail("Add Pet service response - tags with id: " + expectedTagId + " is not present in the response");
        }

        String actualPetStatus = petResponse.getStatus();
        APILogger.logInfo(LOGGER,"Verifying Add Pet service response - status, Actual: " + actualPetStatus + " , Expected: " + expectedPetStatus);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetStatus, expectedPetStatus, "Add Pet service response - status field error");
    }

    /**
     * Method to confirm pet details are added
     * @param expectedPetId - pet id
     * @param expectedCategoryId - category id
     * @param expectedCategoryName - category name
     * @param expectedPetName - pet name
     * @param expectedPhotoUrl - photo url
     * @param expectedTagId - tag id
     * @param expectedTagName - tag name
     * @param expectedPetStatus - pet status
     */
    public static void verifyPetDetailsAreAdded(long expectedPetId, long expectedCategoryId, String expectedCategoryName, String expectedPetName, String expectedPhotoUrl, long expectedTagId, String expectedTagName, String expectedPetStatus) {
        Response response = GetPetServiceHandler.getPetServiceCall(expectedPetId);
        GetPetServiceHandler.verifyPetResponse(response, expectedPetId, expectedCategoryId, expectedCategoryName, expectedPetName, expectedPhotoUrl, expectedTagId, expectedTagName, expectedPetStatus);
    }

}
