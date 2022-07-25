package com.DeliverySystem.orders;
import java.util.Objects;

public class Box {

    private float length;
    private float width;
    private float height;

    public Box() {
    }

    public Box(float l, float w, float h) {
        length = l;
        width = w;
        height = h;
    }

    @Override
    public String toString() {
        return "Length: " + length + " inches, Width: " + width + " inches, Height: " + height + " inches";
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
