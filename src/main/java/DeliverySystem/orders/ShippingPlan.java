package main.java.DeliverySystem.orders;

import main.java.DeliverySystem.exceptions.UnloadedDataException;

public interface ShippingPlan {
    public void determineShippingPlan() throws UnloadedDataException;
}
