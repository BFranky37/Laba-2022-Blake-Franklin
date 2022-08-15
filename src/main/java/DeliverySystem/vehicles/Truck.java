package main.java.DeliverySystem.vehicles;

import java.util.Objects;

public class Truck extends Vehicle{
    //Members
    private String truckNumber; 

    //Constructors
    public Truck(Vehicle tr, String truckNum) {
        super(tr.getName(), tr.getPrice(), tr.getMaxWeight(), tr.getMaxSize());
        truckNumber = truckNum;
    }
    public Truck(String newName, double rate, String num, double capacity, double maxSize) {
        super(newName, rate, capacity, maxSize);
        truckNumber = num;
    }

    //Getters and Setters
    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String newNum) {
        truckNumber = newNum;
    }

    //Class Overrides
    @Override
    public String toString() {
        return ("Vehicle name: " + this.getName() + ", Truck number: " + truckNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Truck)) return false;
        Truck car = (Truck) obj;

        return (Objects.equals(this.getName(), car.getName()) &&
                this.getPrice() == car.getPrice() &&
                this.getMaxWeight() == car.getMaxWeight() &&
                this.getMaxSize() == car.getMaxSize() &&
                Objects.equals(this.truckNumber, car.truckNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPrice(), this.getMaxWeight(), this.getMaxSize(), truckNumber);
    }
}
