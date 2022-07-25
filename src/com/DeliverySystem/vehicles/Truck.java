package com.DeliverySystem.vehicles;

public class Truck extends Vehicle{
    
    String truckNumber; 

    public Truck() {
        super();
    }
    public Truck(String newName, double rate, String num, double capacity) {
        super(newName, rate, capacity);
        truckNumber = num;
    }

    public String getTruckNumber() { return truckNumber; }
    public void setTruckNumber(String newNum) { truckNumber = newNum; }


}
