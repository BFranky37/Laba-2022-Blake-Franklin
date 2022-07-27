package com.DeliverySystem.orders;

import java.util.Objects;
import com.DeliverySystem.vehicles;
import com.DeliverySystem.people;


public class Shipment {

    private Sender sender;
    private Recipient recipient;
    private Package shippingPackage;
    private Insurance insurance;

    private Route travelRoute;
    private Vehicle vehicle;

    private double totalPrice;
    
    public Shipment(Sender send, Recipent recieve, Package pack, Insurance plan) {
        sender = send;
        recipient = recieve;
        shippingPackage = pack;
        insurance = plan;

        travelRoute = new Route(send.getAddress(), recieve.getAddress());
        determineShippingPlan();
    }

    public Sender getSender() { return sender; }
    public void setSender(Sender send) {
        sender = send;

        System.out.println("As the sender for this shipment has been changed, we must re-verufy the shipping method and update the price.");
        setRoute();
        determineShippingPlan();
    }

    public Recipient getRecipient() { return recipient; }
    public void setRecipient(Recipient recieve) {
        recipient = recieve;

        System.out.println("As the recipient for this shipment has been changed, we must re-verufy the shipping method and update the price.");
        setRoute();
        determineShippingPlan();
    }

    public Package getPackage() { return shippingPackage; }
    public void setPackage(Package pack) {
        shippingPackage = pack;

        System.out.println("As the package for this shipment has been changed, we must re-verufy the shipping method and update the price.");
        determineShippingPlan();
    }

    public Insurance getInsurance() { return insurance; }
    public void setInsurance(Insurance plan) {
        insurance = plan;
        calculateTotalPrice();
    }

    public Route getRoute() { return travelRoute; }
    public void setRoute() {
        travelRoute.setFromLocation(sender.getAddress());
        travelRoute.setToLocation(recipient.getAddress());
    }

    public Vehicle getVehicle() { return vehicle; }
    private void setVehicle(Vehicle newVehicle) { vehicle = newVehicle; }

    public double getTotalPrice() { return totalPrice; }
    public void calculateTotalPrice() {
        totalPrice = shippingPackage.getCost() + travelRoute.getPrice() + vehicle.getRate() + insurance.calculateFullCost();
    }

    public void determineShippingPlan() {
        //This is the function that will promt the user for the desired priority of the delivery,
        //then it will choose a vehicle based on factors such as distance, priority and size of package
        // it will either be vehicle = new Truck() or vehicle = new Plane(), and then fill in the remaining fields with a vehicle preset
        
        //To be completed

        calculateTotalPrice();
    }

}
