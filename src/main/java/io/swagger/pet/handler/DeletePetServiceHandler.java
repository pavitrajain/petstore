package io.swagger.pet.handler;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.util.APILogger;
import io.swagger.util.MicroservicesEnvConfig;
import io.swagger.util.RestAssuredLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static java.util.Objects.isNull;

public class DeletePetServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletePetServiceHandler.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);

    /**
     * Method to return delete pet service resource path
     * @return delete pet services resource path as String
     */
    private static String deletePetResource() {
        return "/pet/";
    }

    /**
     * Method to return delete pet service request specification
     * @param apiKey - api key
     * @return delete pet service request specification
     */
    private static RequestSpecification deletePetRequest(String apiKey) {
        RestAssured.baseURI = MicroservicesEnvConfig.BASEURL;
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));
        RequestSpecification request;
        if(isNull(apiKey)) {
            request = RestAssured.given().header("Content-Type", "application/json");
        } else {
            request = RestAssured.given().header("Content-Type", "application/json").header("api_key", apiKey);
        }
        APILogger.logInfo(LOGGER,"Delete Pet Service Request...");
        request.log().all();
        return request;
    }

    /**
     * Method to call delete pet service and return response
     * @param apiKey - api key
     * @param petId - pet id
     * @return delete pet service response
     */
    public static Response deletePetServiceCall(String apiKey, long petId) {
        if(petId == -1) {
            Assert.fail("Incorrect/Blank pet id provided");
            return null;
        } else {
            Response response = deletePetRequest(apiKey).when().delete(deletePetResource() + petId);
            APILogger.logInfo(LOGGER, "Delete Pet Service Response...");
            response.then().log().all();
            return response;
        }
    }

    /**
     * Method to verify delete pet service response status code is 200
     * @param response - delete pet service Response
     */
    public static void verifySuccessStatusCodeInDeletePetResponse(Response response) {
        int statusCode = response.then().extract().statusCode();
        APILogger.logInfo(LOGGER,"Verifying Delete Pet service response - status code, Expected: 200, Actual: " + statusCode);
        MicroservicesEnvConfig.softAssert.assertEquals(statusCode, 200, "Delete Pet service response - status code error");
    }

    /**
     * Method to verify pet information is deleted
     * @param petId - pet id
     */
    public static void verifyPetInformationIsDeleted(long petId) {
        Response response = GetPetServiceHandler.getPetServiceCall(petId);
        GetPetServiceHandler.verifyAPIResponse(response);
    }
}
