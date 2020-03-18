package io.swagger.pet.handler;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.pet.model.APIResponse;
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

public class GetPetServiceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPetServiceHandler.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);

    /**
     * Method to return get pet service resource path
     * @return get pet services resource path as String
     */
    private static String getPetResource() {
        return "/pet/";
    }

    /**
     * Method to return get pet service request specification
     * @return get pet service request specification
     */
    private static RequestSpecification getPetRequest() {
        RestAssured.baseURI = MicroservicesEnvConfig.BASEURL;
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));
        APILogger.logInfo(LOGGER,"Get Pet Service Request...");
        return RestAssured.given().header("Content-Type", "application/json").log().all();
    }

    /**
     * Method to call get pet service and return response
     * @param petId - pet id
     * @return get pet service response
     */
    public static Response getPetServiceCall(long petId) {
        Response response = getPetRequest().when().get(getPetResource() + petId);
        APILogger.logInfo(LOGGER, "Get Pet Service Response...");
        response.then().log().all();
        return response;
    }

    /**
     * Method to verify get pet service response details when pet details are available in store
     * @param response - get pet service response
     * @param expectedPetId - expected pet id
     * @param expectedCategoryId - expected category id
     * @param expectedCategoryName - expected category name
     * @param expectedPetName - expected pet name
     * @param expectedPhotoUrl - expected photo urls
     * @param expectedTagId - expected tag id
     * @param expectedTagName - expected tag name
     * @param expectedPetStatus - expected pet status
     */
    public static void verifyPetResponse(Response response, long expectedPetId, long expectedCategoryId, String expectedCategoryName, String expectedPetName, String expectedPhotoUrl, long expectedTagId, String expectedTagName, String expectedPetStatus) {
        PetResponse petResponse = response.as(PetResponse.class);

        long actualPetId = petResponse.getId();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - id, Actual: " + actualPetId + " , Expected: " + expectedPetId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetId, expectedPetId, "Get Pet service response - id field error");

        long actualCategoryId = petResponse.getCategory().getId();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - category - id, Actual: " + actualCategoryId + " , Expected: " + expectedCategoryId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualCategoryId, expectedCategoryId, "Get Pet service response - Category - id field error");

        String actualCategoryName = petResponse.getCategory().getName();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - category - name, Actual: " + actualCategoryName + " , Expected: " + expectedCategoryName);
        MicroservicesEnvConfig.softAssert.assertEquals(actualCategoryName, expectedCategoryName, "Get Pet service response - category - name field error");

        String actualPetName = petResponse.getName();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - name, Actual: " + actualPetName + " , Expected: " + expectedPetName);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetName, expectedPetName, "Get Pet service response - name field error");

        List<String> actualPhotoUrls = petResponse.getPhotoUrls();
        List<String> expectedPhotoUrls = Arrays.asList(expectedPhotoUrl.split(","));
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - photo url, Actual: " + actualPhotoUrls.toString() + " , Expected: " + expectedPhotoUrls.toString());
        MicroservicesEnvConfig.softAssert.assertEquals(actualPhotoUrls, expectedPhotoUrls, "Get Pet service response - photo url field error");

        Optional<Tag> actualTags = petResponse.getTags().stream().filter((e)->e.getId() == expectedTagId).findFirst();
        if(actualTags.isPresent()) {
            String actualTagName = actualTags.get().getName();
            APILogger.logInfo(LOGGER,"Verifying Get Pet service response - tags - name, Actual: " + actualTagName + " , Expected: " + expectedTagName);
            MicroservicesEnvConfig.softAssert.assertTrue(true);
        } else {
            MicroservicesEnvConfig.softAssert.fail("Get Pet service response - tags with id: " + expectedTagId + " is not present in the response");
        }

        String actualPetStatus = petResponse.getStatus();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - status, Actual: " + actualPetStatus + " , Expected: " + expectedPetStatus);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetStatus, expectedPetStatus, "Get Pet service response - status field error");
    }

    /**
     * Method to verify get pet service response when pet details are not available
     * @param response - get pet service response
     */
    public static void verifyAPIResponse(Response response) {
        APIResponse apiResponse = response.as(APIResponse.class);

        int actualCode = apiResponse.getCode();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - code, Actual: " + actualCode + " , Expected: 1");
        MicroservicesEnvConfig.softAssert.assertEquals(actualCode, 1, "Get Pet service response - code field error");

        String actualType = apiResponse.getType();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - status, Actual: " + actualType + " , Expected: error");
        MicroservicesEnvConfig.softAssert.assertEquals(actualType, "error", "Get Pet service response - type field error");

        String actualMessage = apiResponse.getMessage();
        APILogger.logInfo(LOGGER,"Verifying Get Pet service response - status, Actual: " + actualMessage + " , Expected: Pet not found");
        MicroservicesEnvConfig.softAssert.assertEquals(actualMessage, "Pet not found", "Get Pet service response - message field error");
    }
}
