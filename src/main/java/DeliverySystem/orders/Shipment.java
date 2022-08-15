package main.java.DeliverySystem.orders;

import main.java.DeliverySystem.exceptions.UnloadedDataException;
import main.java.DeliverySystem.people.Recipient;
import main.java.DeliverySystem.people.Sender;
import main.java.DeliverySystem.vehicles.Plane;
import main.java.DeliverySystem.vehicles.Truck;
import main.java.DeliverySystem.vehicles.Vehicle;
import org.apache.log4j.Logger;

import java.util.Objects;

import static main.java.DeliverySystem.other.DataLoader.getVehicles;
import static main.java.DeliverySystem.other.DataLoader.loadData;


public class Shipment implements Charge, ShippingPlan{
    private static final Logger logger = Logger.getLogger(Shipment.class.getName());
    //MEMBERS
    private Sender sender;
    private Recipient recipient;
    private Package shippingPackage;
    private Insurance insurance;

    private Route travelRoute;
    private Vehicle vehicle;

    private boolean priority;
    private double totalPrice;

    //Constructor
    public Shipment(Sender send, Recipient receive, Package pack, Insurance plan, boolean prio) throws UnloadedDataException {
        sender = send;
        recipient = receive;
        shippingPackage = pack;
        insurance = plan;
        priority = prio;

        travelRoute = new Route(send.getAddress(), receive.getAddress());
        determineShippingPlan();
        logger.info("Shipment created.");
    }

    //Getters and Setters
    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender send) throws UnloadedDataException {
        sender = send;
        //logger.info("As the sender for this shipment has been changed, we must re-verify the shipping method and update the price.");
        setRoute();
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient receive) throws UnloadedDataException {
        recipient = receive;
        //logger.info("As the recipient for this shipment has been changed, we must re-verify the shipping method and update the price.");
        setRoute();
    }

    public Package getPackage() {
        return shippingPackage;
    }

    public void setPackage(Package pack) throws UnloadedDataException {
        shippingPackage = pack;
        //logger.info("As the package for this shipment has been changed, we must re-verify the shipping method and update the price.");
        determineShippingPlan();
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance plan) {
        insurance = plan;
        calculatePrice();
    }

    public Route getRoute() {
        return travelRoute;
    }

    public void setRoute() throws UnloadedDataException {
        travelRoute.setFromLocation(sender.getAddress());
        travelRoute.setToLocation(recipient.getAddress());
        determineShippingPlan();
    }

    public void setRoute(Route newRoute) throws UnloadedDataException {
        travelRoute = newRoute;
        determineShippingPlan();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    private void setVehicle(Vehicle newVehicle) {
        vehicle = newVehicle;
    }

    public boolean getPrio() {
        return priority;
    }

    public void setPrio(boolean prio) throws UnloadedDataException {
        priority = prio;
        determineShippingPlan();
    }

    @Override
    public double getPrice() {
        return totalPrice;
    }

    @Override
    public void calculatePrice() {
        totalPrice = shippingPackage.getPrice() + travelRoute.getPrice() + vehicle.getPrice()
                    + insurance.calculatePrice(shippingPackage.getValue());
    }

    @Override
    public void determineShippingPlan() throws UnloadedDataException {
        //This is the function that will prompt the user for the desired priority of the delivery,
        //then it will choose a vehicle based on factors such as distance, priority and size of package
        // it will either be vehicle = new Truck() or vehicle = new Plane(), and then fill in the remaining fields with a vehicle preset
        int vehicleNumber = (int)Math.floor(Math.random()*(999999-100000+1)+100000);

        try {
            if (!priority && travelRoute.getDistance() < 1000 && shippingPackage.getWeight() <= 150 && shippingPackage.getBox().getArea() <= 65000)
                vehicle = new Truck(getVehicles().get(0), "00-" + vehicleNumber); //Standard Delivery Car
            else if (!priority && travelRoute.getDistance() < 1000 && shippingPackage.getWeight() <= 300 && shippingPackage.getBox().getArea() <= 90000)
                vehicle = new Truck(getVehicles().get(1), "01-" + vehicleNumber); //Heavy Delivery Truck
            else if (priority && travelRoute.getDistance() < 1000 && shippingPackage.getWeight() <= 150 && shippingPackage.getBox().getArea() <= 65000)
                vehicle = new Truck(getVehicles().get(2), "02-" + vehicleNumber); //Priority Delivery Car
            else if (!priority && shippingPackage.getWeight() <= 1000 && shippingPackage.getBox().getArea() <= 500000.0)
                vehicle = new Truck(getVehicles().get(3), "03-" + vehicleNumber); //Delivery Freight Truck

            else if (shippingPackage.getWeight() <= 300 && shippingPackage.getBox().getArea() <= 90000)
                vehicle = new Plane(getVehicles().get(4), "04-" + vehicleNumber); //Standard Delivery Plane
            else vehicle = new Plane(getVehicles().get(5), "05-" + vehicleNumber); //Heavy Cargo Plane
        } catch (UnloadedDataException e) {
            logger.info("Trying to access vehicle data that has not been loaded");
            loadData();
            determineShippingPlan();
        }
        calculatePrice();
    }

    //Class Overrides
    @Override
    public String toString() {
        return ("Sender: " + sender + "\nRecipient: " + recipient +
                "\nRoute: " + travelRoute +
                "\nShipping method: " + vehicle + "\nTotal Price: " + Math.round(totalPrice * 100.0) / 100.0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Shipment)) return false;
        Shipment ship = (Shipment) obj;

        return (this.sender == ship.sender &&
                this.recipient == ship.recipient &&
                this.shippingPackage == ship.shippingPackage &&
                this.insurance == ship.insurance &&
                this.vehicle == ship.vehicle &&
                this.totalPrice == ship.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, shippingPackage, insurance, vehicle, totalPrice);
    }
}
