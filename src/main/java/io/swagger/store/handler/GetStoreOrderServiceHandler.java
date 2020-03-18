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

public class GetStoreOrderServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetStoreOrderServiceHandler.class);
    private static final RestAssuredLogger RSLOGGER = new RestAssuredLogger(LOGGER);

    /**
     * Method to return get store order service resource path
     * @return get store order services resource path as String
     */
    private static String getStoreOrderResource() {
        return "/store/order/";
    }

    /**
     * Method to return get store order service request specification
     * @return get store order service request specification
     */
    private static RequestSpecification getStoreOrderRequest() {
        RestAssured.baseURI = MicroservicesEnvConfig.BASEURL;
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(RSLOGGER.getPrintStream()));
        APILogger.logInfo(LOGGER,"Get Store Order Service Request...");
        return RestAssured.given().header("Content-Type", "application/json").log().all();
    }

    /**
     * Method to call get store order service and return response
     * @param orderId - order id
     * @return get store order service response
     */
    public static Response getStoreOrderServiceCall(long orderId) {
        Response response = getStoreOrderRequest().when().get(getStoreOrderResource() + orderId);
        APILogger.logInfo(LOGGER, "Get Store Order Service Response...");
        response.then().log().all();
        return response;
    }

    /**
     * Method to verify get store order service response details when pet details are available in store
     * @param response - get store order service response
     * @param expectedId - expected id
     * @param expectedPetId - expected pet id
     * @param expectedQuantity - expected quantity
     * @param expectedShipDate - expected ship date
     * @param expectedStatus - expected status
     * @param expectedCompleted - expected completed
     */
    public static void verifyGetStoreOrderResponse(Response response, long expectedId, long expectedPetId, int expectedQuantity, String expectedShipDate, String expectedStatus, boolean expectedCompleted) {
        StoreOrderResponse storeOrderResponse = response.as(StoreOrderResponse.class);

        long actualId = storeOrderResponse.getId();
        APILogger.logInfo(LOGGER,"Verifying Get Store Order service response - id, Actual: " + actualId + " , Expected: " + expectedId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualId, expectedId, "Get Store Order service response - id field error");

        long actualPetId = storeOrderResponse.getPetId();
        APILogger.logInfo(LOGGER,"Verifying Get Store Order service response - pet id, Actual: " + actualPetId + " , Expected: " + expectedPetId);
        MicroservicesEnvConfig.softAssert.assertEquals(actualPetId, expectedPetId, "Get Store Order service response - pet id field error");

        int actualQuantity = storeOrderResponse.getQuantity();
        APILogger.logInfo(LOGGER,"Verifying Get Store Order service response - quantity, Actual: " + actualQuantity + " , Expected: " + expectedQuantity);
        MicroservicesEnvConfig.softAssert.assertEquals(actualQuantity, expectedQuantity, "Get Store Order service response - quantity field error");

        String actualShipDate = storeOrderResponse.getShipDate().substring(0,23);
        expectedShipDate = expectedShipDate.replace("Z", "");
        APILogger.logInfo(LOGGER,"Verifying Get Store Order service response - ship date, Actual: " + actualShipDate + " , Expected: " + expectedShipDate);
        MicroservicesEnvConfig.softAssert.assertEquals(actualShipDate, expectedShipDate, "Get Store Order service response - ship date field error");

        String actualStatus = storeOrderResponse.getStatus();
        APILogger.logInfo(LOGGER,"Verifying Get Store Order service response - status, Actual: " + actualStatus + " , Expected: " + expectedStatus);
        MicroservicesEnvConfig.softAssert.assertEquals(actualStatus, expectedStatus, "Get Store Order service response - status field error");

        boolean actualCompleted = storeOrderResponse.isComplete();
        APILogger.logInfo(LOGGER,"Verifying Get Store Order service response - complete, Actual: " + actualCompleted + " , Expected: " + expectedCompleted);
        MicroservicesEnvConfig.softAssert.assertEquals(actualCompleted, expectedCompleted, "Get Store Order service response - complete field error");
    }
}
