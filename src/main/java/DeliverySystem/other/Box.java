package main.java.DeliverySystem.other;

import main.java.DeliverySystem.exceptions.ExceedsLimitsException;
import main.java.DeliverySystem.exceptions.NegativeValueException;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Box implements Shape {
    private static final Logger logger = Logger.getLogger(Box.class.getName());
    //Dimensions measured in inches
    private double length;
    private double width;
    private double height;
    public static final double sizeLimit = 500000;

    //Constructors
    public Box() {
        logger.info("Box created");
    }
    public Box(double l, double w, double h) {
        length = l;
        width = w;
        height = h;
        logger.info("Box created with dimensions length: " + length + " width: " + width + " height: " + height );
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

    public static double validateSize(double l, double w, double h) throws ExceedsLimitsException, NegativeValueException {
        if (l*w*h > sizeLimit) {
            throw new ExceedsLimitsException("Size exceeds limit");
        } else if (l < 0 || w < 0 || h < 0) {
            throw new NegativeValueException("Got a negative value for size");
        }
        else return l*w*h;
    }

    @Override
    public final double getArea() { //return area in cubic inches
        return (length * width * height);
    }

    //Class Overrides
    @Override
    public String toString() {
        return "Length: " + length + " inches, Width: " + width + " inches,  Height: " + height + " inches";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Box)) return false;
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
