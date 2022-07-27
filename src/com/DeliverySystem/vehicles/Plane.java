package com.DeliverySystem.vehicles;

import java.util.Objects;

public class Plane extends Vehicle{
    
    private String flightNumber; 

    public Plane(Plane pl, String flightNum) {
        super(pl.getName(), pl.getRate(), pl.getMaxWeight(), pl.getMaxSize());
        flightNumber = flightNum;
    }
    public Plane(String newName, double rate, String num, double capacity, double maxSize) {
        super(newName, rate, capacity, maxSize);
        flightNumber = num;
    }

    public String getFlighNum() { return flightNumber; }
    public void setFlightNum(String newNum) { flightNumber = newNum; }

    @Override
    public String toString() {
        return (super.toString() + ", Truck number: " + flightNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Plane)) return false;
        Plane pln = (Plane) obj;

        return (this.getName() == pln.getName() &&
                this.getRate() == pln.getRate() &&
                this.getMaxWeight() == pln.getMaxWeight() &&
                this.getMaxSize() == pln.getMaxSize() &&
                this.flightNumber == pln.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getRate(), this.getMaxWeight(), this.getMaxSize(), flightNumber);
    }
}
