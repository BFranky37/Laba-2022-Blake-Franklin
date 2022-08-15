package main.java.com.DeliverySystem.orders;

import main.java.com.DeliverySystem.exceptions.UnloadedDataException;

public interface ShippingPlan {
    public void determineShippingPlan() throws UnloadedDataException;
}
