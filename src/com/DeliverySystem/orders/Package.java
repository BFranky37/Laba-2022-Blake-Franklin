package com.DeliverySystem.orders;

import com.DeliverySystem.other.Box;

import java.util.Objects;

public class Package implements Charge, Description {

    private Box box;
    private double weight;
    private boolean fragility;
    private double value;
    private double cost;
    final static double costRate = 2.3;

    public Package(Box bx, double wgt, boolean fragile) {
        box = bx;
        weight = wgt;
        fragility = fragile;
        calculatePrice();
    }

    public Box getBox() {
        return box;
    }
    public void setBox(Box bx) { 
        box = bx;
        calculatePrice();
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double wgt) {
        weight = wgt;
    }

    public boolean getFragility() {
        return fragility;
    }
    public void setFragility(boolean fragile) {
        fragility = fragile;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double val) {
        value = val;
    }

    @Override
    public double getPrice() {
        return cost;
    }
    @Override
    public void calculatePrice() {
        // area / 100 * costRate
        cost = (box.getArea() / 100 * costRate);
    }

    @Override
    public void displayInfo() {
        System.out.println("Your Package represents the item you are shipping, including its weight, value, and fragility, " +
                            "as well as the box it is packed into.");
    }

    @Override
    public String toString() {
        return (box.toString() + "\nWeight: " + weight + " pounds, Fragile: " + fragility);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Package)) return false;
        Package pkg = (Package) obj;

        return (this.box == pkg.box && this.weight == pkg.weight && this.fragility == pkg.fragility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(box, weight, fragility);
    }
}
