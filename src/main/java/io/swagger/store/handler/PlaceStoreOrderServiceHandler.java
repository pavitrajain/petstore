package io.swagger.store.handler;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.store.model.StoreOrderResponse;
import io.swagger.util.APILogger;
import io.swagger.util.MicroservicesEnvConfig;
import io.swagger.util.RestAssuredLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceStoreOrderServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceStoreOrderServiceHandler.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);

    /**
     * Method to return place order service resource path
     * @return place order services resource path as String
     */
    private static String placeOrderResource() {
        return "/store/order";
    }

    /**
     * Method to return place order service payload
     * @param id - id
     * @param petId - pet id
     * @param quantity - quantity
     * @param shipDate - ship date
     * @param status - status e.g. placed / approved / delivered
     * @param completed - true / false
     * @return place order service payload as String
     */
    private static String placeOrderPayload(long id, long petId, int quantity, String shipDate, String status, boolean completed) {
        return "{\n" +
                    "  \"id\": \"" + id + "\",\n" +
                    "  \"petId\": \"" + petId + "\",\n" +
                    "  \"quantity\": \"" + quantity + "\",\n" +
                    "  \"shipDate\": \"" + shipDate + "\",\n" +
                    "  \"status\": \"" + status + "\",\n" +
                    "  \"complete\": \"" + completed + "\"\n" +
                    "}";
    }

    /**
     * Method to return place order service request specification
     * @param id - id
     * @param petId - pet id
     * @param quantity - quantity
     * @param shipDate - ship date
     * @param status - status e.g. placed / approved / delivered
     * @param completed - true / false
     * @return place order service request specification
     */
    private static RequestSpecification placeOrderRequest(long id, long petId, int quantity, String shipDate, String status, boolean completed) {
        RestAssured.baseURI = MicroservicesEnvConfig.BASEURL;
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));

        APILogger.logInfo(LOGGER,"Place Store Order service Request...");
        return RestAssured.given().header("Content-Type", "application/json").body(placeOrderPayload(id, petId, quantity, shipDate, status, completed)).log().all();
    }

    /**
     * Method to call place order service and return response
     * @param id - id
     * @param petId - pet id
     * @param quantity - quantity
     * @param shipDate - ship date
     * @param status - status e.g. placed / approved / delivered
     * @param completed - true / false
     * @return place order service response
     */
    public static Response placeOrderServiceCall(long id, long petId, int quantity, String shipDate, String status, boolean completed) {
        Response response = placeOrderRequest(id, petId, quantity, shipDate, status, completed).when().post(placeOrderResource());
        APILogger.logInfo(LOGGER, "Place Store Order service Response...");
        response.then().log().all();
        return response;
    }

    /**
     * Method to verify place order service response status code is 200
     * @param response - place order service Response
     */
    public static void verifySuccessStatusCodeInPlaceStoreOrderResponse(Response response) {
        int statusCode = response.then().extract().statusCode();
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - status code, Expected: 200, Actual: " + statusCode);
        MicroservicesEnvConfig.softAssert.assertEquals(statusCode, 200, "Place Store Order service response - status code error");
    }

    /**
     * Method to verify place order service response details
     * @param response - Add pet service response specification
     * @param expectedId - expected id
     * @param expectedPetId - expected pet id
     * @param expectedQuantity - expected quantity
     * @param expectedShipDate - expected ship date
     * @param expectedStatus - expected status
     * @param expectedCompleted - expected completed
     */
    public static void verifyPlaceStoreOrderResponse(Response response, long expectedId, long expectedPetId, int expectedQuantity, String expectedShipDate, String expectedStatus, boolean expectedCompleted) {
        verifySuccessStatusCodeInPlaceStoreOrderResponse(response);

        StoreOrderResponse storeOrderResponse = response.as(StoreOrderResponse.class);

        long actualId = storeOrderResponse.getId();
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - id, Actual: " + actualId + " , Expected: " + expectedId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualId, expectedId, "Place Store Order service response - id field error");

        long actualPetId = storeOrderResponse.getPetId();
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - pet id, Actual: " + actualPetId + " , Expected: " + expectedPetId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetId, expectedPetId, "Place Store Order service response - pet id field error");

        int actualQuantity = storeOrderResponse.getQuantity();
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - quantity, Actual: " + actualQuantity + " , Expected: " + expectedQuantity);
        MicroservicesEnvConfig.softAssert.assertEquals(actualQuantity, expectedQuantity, "Place Store Order service response - quantity field error");

        String actualShipDate = storeOrderResponse.getShipDate().substring(0,23);
        expectedShipDate = expectedShipDate.replace("Z", "");
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - ship date, Actual: " + actualShipDate + " , Expected: " + expectedShipDate);
        MicroservicesEnvConfig.softAssert.assertEquals(actualShipDate, expectedShipDate, "Place Store Order service response - ship date field error");

        String actualStatus = storeOrderResponse.getStatus();
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - status, Actual: " + actualStatus + " , Expected: " + expectedStatus);
        MicroservicesEnvConfig.softAssert.assertEquals(actualStatus, expectedStatus, "Place Store Order service response - status field error");

        boolean actualCompleted = storeOrderResponse.isComplete();
        APILogger.logInfo(LOGGER,"Verifying Place Store Order service response - complete, Actual: " + actualCompleted + " , Expected: " + expectedCompleted);
        MicroservicesEnvConfig.softAssert.assertEquals(actualCompleted, expectedCompleted, "Place Store Order service response - complete field error");
    }

    /**
     * Method to confirm order is placed
     * @param expectedId - expected id
     * @param expectedPetId - expected pet id
     * @param expectedQuantity - expected quantity
     * @param expectedShipDate - expected ship date
     * @param expectedStatus - expected status
     * @param expectedCompleted - expected completed
     */
    public static void verifyOrderIsPlaced(long expectedId, long expectedPetId, int expectedQuantity, String expectedShipDate, String expectedStatus, boolean expectedCompleted) {
        Response response = GetStoreOrderServiceHandler.getStoreOrderServiceCall(expectedId);
        GetStoreOrderServiceHandler.verifyGetStoreOrderResponse(response, expectedId, expectedPetId, expectedQuantity, expectedShipDate, expectedStatus, expectedCompleted);
    }
}
