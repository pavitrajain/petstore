package io.swagger.store.model;

public class StoreOrderResponse {

    long id;
    long petId;
    int quantity;
    String shipDate;
    String status;
    boolean complete;

    public long getId() { return id; }

    public long getPetId() { return petId; }

    public int getQuantity() { return quantity; }

    public String getShipDate() { return shipDate; }

    public String getStatus() { return status; }

    public boolean isComplete() { return complete; }
}
