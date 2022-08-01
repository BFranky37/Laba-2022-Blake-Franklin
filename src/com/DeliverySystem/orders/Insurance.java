package com.DeliverySystem.orders;

import java.util.Objects;

public class Insurance implements Description {
    
    private String name;
    private double baseCost; //base price of insurance
    private double priceRate; //extra cost added on based on value of the object being shipped (Usually between 1.5 - 5%)

    public Insurance() {
    }
    public Insurance(String nam, double cost, double rate) {
        name = nam;
        baseCost = cost;
        priceRate = rate;
    }

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        name = newName;
    }

    public double getCost() {
        return baseCost;
    }
    public void setCost(double cost) {
        baseCost = cost;
    }

    public double getRate() {
        return priceRate;
    }
    public void setRate(double rate) {
        priceRate = rate;
    }

    public double calculatePrice(double value) {
        return baseCost + (priceRate * value);
    }

    @Override
    public void displayInfo() {
        System.out.println("Purchasing insurance for your shipment means that if the item you are shipping is lost or damaged, " +
                            "you will be reimbursed for the full value of the item. Different insurance plans are available for " +
                            "items of different value.");
    }

    @Override
    public String toString() {
        return ("Name: " + name + ", Base Cost: " + baseCost + ", Price Rate: " + (priceRate * 100) + "%");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Insurance)) return false;
        Insurance ins = (Insurance) obj;

        return (Objects.equals(this.name, ins.name) &&
                this.baseCost == ins.baseCost &&
                this.priceRate == ins.priceRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, baseCost, priceRate);
    }
}
