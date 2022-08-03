package com.DeliverySystem.orders;

import com.DeliverySystem.exceptions.UnloadedDataException;

public interface ShippingPlan {
    public void determineShippingPlan() throws UnloadedDataException;
}
