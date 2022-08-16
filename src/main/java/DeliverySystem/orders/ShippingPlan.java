package DeliverySystem.orders;

import DeliverySystem.exceptions.UnloadedDataException;

public interface ShippingPlan {
    public void determineShippingPlan() throws UnloadedDataException;
}
