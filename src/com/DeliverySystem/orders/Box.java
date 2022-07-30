package com.DeliverySystem.orders;

import java.util.Objects;

public class Box implements Shape {
    //Dimensions measured in inches
    private double length;
    private double width;
    private double height;

    public Box() {
    }
    public Box(double l, double w, double h) {
        length = l;
        width = w;
        height = h;
    }

    public double getLength() {
        return length;
    }
    public void setLength(double l) {
        length = l;
    }

    public double getWidth() {
        return width;
    }
    public void setWidth(double w) {
        width = w;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double h) {
        height = h;
    }

    @Override
    public final double getArea() { //return area in cubic inches
        return (length * width * height);
    }

    @Override
    public String toString() {
        return "Length: " + length + " inches, Width: " + width + " inches,  Height: " + height + " inches";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Box per = (Box) obj;

        return (this.length == per.length &&
                this.width == per.width &&
                this.height == per.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, width, height);
    }
}
