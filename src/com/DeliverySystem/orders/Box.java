package com.DeliverySystem.orders;
import java.util.Objects;

public class Box {

    private double length;
    private double width;
    private double height;

    public Box() {
    }

    public Box(float l, float w, float h) {
        length = l;
        width = w;
        height = h;
    }

    @Override
    public String toString() {
        return "Length: " + length + " feet, Width: " + width + " feet, Height: " + height + " feet";
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
