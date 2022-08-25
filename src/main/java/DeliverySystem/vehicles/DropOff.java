package DeliverySystem.vehicles;

import DeliverySystem.orders.Shipment;
import org.apache.log4j.Logger;

public class DropOff implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(Travel.class.getName());

    Shipment shipment;
    final Object lock1;
    final Object lock2;

    public DropOff(Shipment shipment, Object Lock1, Object Lock2) {
        this.shipment = shipment;
        lock1 = Lock1;
        lock2 = Lock2;
    }

    @Override
    public void run() {
        synchronized (lock2) { //lock will be held until shipment has been transported to the recipient
            LOGGER.info("Holding delivery lock1");
            shipment.getRecipient().receivePackage(shipment.getPackage());
            synchronized (lock1) {
                LOGGER.info("Holding delivery lock2");
                LOGGER.info("Recipient " + shipment.getRecipient().getName() + " has received their package.");
            }
        }
    }
}
