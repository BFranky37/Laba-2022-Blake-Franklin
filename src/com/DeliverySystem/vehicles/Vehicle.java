package com.DeliverySystem.vehicles;

import java.util.Objects;

public abstract class Vehicle {
    
    private String name;
    private double costRate;
    private double weightCapacity; //max weight of packages in pounds
    //for reference, UPS separates weight class at the 70 and 150lbs mark for standard cars, with no limit on freight trucks
    private double spaceCapacity; //max size of packages in cubic inches

    public Vehicle() {
        
    }
    public Vehicle(String newName, double rate, double maxWeight, double maxSize) {
        name = newName;
        costRate = rate;
        weightCapacity = maxWeight;
        spaceCapacity = maxSize;
    }

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        name = newName;
    }

    public double getRate() {
        return costRate;
    }
    public void setRate(double rate) {
        costRate = rate;
    }

    public double getMaxWeight() {
        return weightCapacity;
    }
    public void setMaxWeight(double weight) {
        weightCapacity = weight;
    }

    public double getMaxSize() {
        return spaceCapacity;
    }
    public void setMaxSize(double size) {
        spaceCapacity = size;
    }

    @Override
    public String toString() {
        return ("Vehicle Name: " + name + ", Cost Rate: " + costRate + ", Max Weight: " + weightCapacity + ", Max Size: " + spaceCapacity);
    }
}
