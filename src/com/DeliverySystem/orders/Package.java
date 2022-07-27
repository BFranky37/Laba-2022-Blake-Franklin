package com.DeliverySystem.orders;

import java.util.Objects;

public class Package {

    private Box box;
    private double weight;
    private boolean fragility;
    private double value;
    private double cost;

    private final static double costRate = 2.3;

    public Package(Box bx, double wgt, boolean fragile) {
        box = bx;
        weight = wgt;
        fragility = fragile;
        calculateCost();
    }

    public Box getBox() { return box; }
    public void setBox(Box bx) { 
        box = bx; 
        calculateCost();
    }

    public double getWeight() { return weight; }
    public void setWeight(double wgt) { weight = wgt; }

    public boolean getFragility() { return fragility; }
    public void setFragility(boolean fragile) { fragility = fragile; }

    public boolean getValue() { return value; }
    public void setValue(boolean val) { value = val; }

    public double getCost() { return cost; }
    public void calculateCost() {
        // area / 100 * costRate
        cost = (box.getArea() / 100 * 2.3);
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

        return (this.box == pkg.box &&
                this.weight == pkg.weight &&
                this.fragility == pkg.fragility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(box, weight, fragility);
    }
}
