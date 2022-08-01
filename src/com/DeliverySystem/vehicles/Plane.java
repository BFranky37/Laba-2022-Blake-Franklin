package com.DeliverySystem.vehicles;

import java.util.Objects;

public class Plane extends Vehicle{
    //Members
    private String flightNumber; 

    //Constructors
    public Plane(Vehicle pl, String flightNum) {
        super(pl.getName(), pl.getPrice(), pl.getMaxWeight(), pl.getMaxSize());
        flightNumber = flightNum;
    }
    public Plane(String newName, double rate, String num, double capacity, double maxSize) {
        super(newName, rate, capacity, maxSize);
        flightNumber = num;
    }

    //Getters and Setters
    public String getFlightNum() {
        return flightNumber;
    }

    public void setFlightNum(String newNum) {
        flightNumber = newNum;
    }

    //Class Overrides
    @Override
    public String toString() {
        return ("Vehicle name: " + this.getName() + ", Flight number: " + flightNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Plane)) return false;
        Plane pln = (Plane) obj;

        return (Objects.equals(this.getName(), pln.getName()) &&
                this.getPrice() == pln.getPrice() &&
                this.getMaxWeight() == pln.getMaxWeight() &&
                this.getMaxSize() == pln.getMaxSize() &&
                Objects.equals(this.flightNumber, pln.flightNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPrice(), this.getMaxWeight(), this.getMaxSize(), flightNumber);
    }
}
