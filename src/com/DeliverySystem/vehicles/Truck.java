package com.DeliverySystem.vehicles;

import java.util.Objects;

public class Truck extends Vehicle{
    
    private String truckNumber; 

    public Truck(Truck tr, String truckNum) {
        super(tr.getName(), tr.getRate(), tr.getMaxWeight(), tr.getMaxSize());
        truckNumber = truckNum;
    }
    public Truck(String newName, double rate, String num, double capacity, double maxSize) {
        super(newName, rate, capacity, maxSize);
        truckNumber = num;
    }

    public String getTruckNumber() { return truckNumber; }
    public void setTruckNumber(String newNum) { truckNumber = newNum; }

    @Override
    public String toString() {
        return (super.toString() + ", Truck number: " + truckNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Truck)) return false;
        Truck car = (Truck) obj;

        return (this.getName() == car.getName() &&
                this.getRate() == car.getRate() &&
                this.getMaxWeight() == car.getMaxWeight() &&
                this.getMaxSize() == car.getMaxSize() &&
                this.truckNumber == car.truckNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getRate(), this.getMaxWeight(), this.getMaxSize(), truckNumber);
    }
}
