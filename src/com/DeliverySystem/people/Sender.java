package com.DeliverySystem.people;

import java.util.ArrayList;
import java.util.Objects;

import com.DeliverySystem.exceptions.InvalidDeliveryException;
import com.DeliverySystem.orders.Shipment;
import org.apache.log4j.Logger;

public class Sender extends Person{
    private static final Logger logger = Logger.getLogger(Sender.class.getName());
    //Members
    private final ArrayList<Shipment> orders;

    //Constructors
    public Sender() {
        super();
        orders = new ArrayList<>();
        logger.info("Sender created.");
    }

    public Sender(String fullname, String phoneNum, Location fulladdress) {
        super(fullname, phoneNum, fulladdress);
        orders = new ArrayList<>();
        logger.info("Sender created.");
    }

    public void addOrder(Shipment order) {
        try {
            if (order.getInsurance() == null ||
                    order.getRecipient() == null ||
                    order.getVehicle() == null ||
                    order.getPackage() == null ||
                    order.getRoute() == null) {
                throw new InvalidDeliveryException("Null values attached to this shipment");
            } else if (order.getSender() != this) {
                throw new InvalidDeliveryException("Sender is attempting to make a shipment under another person's name");
            } else orders.add(order);
        } catch (InvalidDeliveryException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    //to get the list of all packages this recipient has received
    public void getOrders() {
        logger.info("Shipments ordered by " + super.getName() + ": ");
        for (Shipment s : orders) {
            logger.info(s);
        }
    }

    //Class Overrides
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Sender)) return false;
        Sender per = (Sender) obj;

        return (Objects.equals(this.getName(), per.getName()) &&
                Objects.equals(this.getNumber(), per.getNumber()) &&
                this.getAddress() == per.getAddress() &&
                this.orders == per.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getNumber(), this.getAddress(), orders);
    }
}
