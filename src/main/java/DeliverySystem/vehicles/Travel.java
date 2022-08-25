package DeliverySystem.vehicles;

import DeliverySystem.orders.Shipment;
import org.apache.log4j.Logger;

public class Travel implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Travel.class.getName());

    Shipment shipment;
    final Object lock1;
    final Object lock2;

    public Travel(Shipment shipment, Object Lock1, Object Lock2) {
        this.shipment = shipment;
        lock1 = Lock1;
        lock2 = Lock2;
    }

    @Override
    public void run() {
        synchronized (lock1) {
            LOGGER.info("Holding delivery lock1");
            try {
                //Delivery vehicle traveling the distance of the route
                Thread.sleep(100L * shipment.getRoute().getDistance());
                synchronized (lock2) {
                    LOGGER.info("Holding delivery lock2");
                    LOGGER.info(shipment.getVehicle().toString() + " has completed delivery");
                    shipment.setDelivered(true);
                }
            } catch (InterruptedException e) {
                LOGGER.info("Delivery Vehicle interrupted while on route.");
            }
        }
    }
}
