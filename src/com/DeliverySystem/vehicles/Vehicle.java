package com.DeliverySystem.vehicles;

public abstract class Vehicle {
    
    String name;
    double costRate;
    final double weightCapacity;

    public Vehicle() {
        weightCapacity = 1000;
    }
    public Vehicle(String newName, double rate, double capacity) {
        name = newName;
        costRate = rate;
        weightCapacity = capacity;
    }

    public String getName() { return name; }
    public void setName(String newName) { name = newName; }

    public Location getRate() { return costRate; }
    public void setRate(double rate) { costRate = rate; }

    public String getCapacity() { return weightCapacity; }

    @Override
    public String toString() {
        return "Vehicle Name: " + name;
    }
}
