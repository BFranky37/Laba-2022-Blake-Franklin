package DeliverySystem.other;

import DeliverySystem.exceptions.ExceedsLimitsException;
import DeliverySystem.exceptions.NegativeValueException;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Box implements Shape {
    private static final Logger logger = Logger.getLogger(Box.class.getName());
    //Dimensions measured in inches
    private double length;
    private double width;
    private double height;
    private static final double sizeLimit = 500000;

    public enum SizeMeasurement {
        INCHES ("inches", 2.54),
        CENTIMETERS ("centimeters", 0.39);

        private final String name;
        private final double conversionRate;

        SizeMeasurement(String name, double conversionRate) {
            this.name = name;
            this.conversionRate = conversionRate;
        }

        public String getName() {
            return name;
        }

        public double getConversionRate() {
            return conversionRate;
        }

        public double convert(double value) {
            return value * conversionRate;
        }
    }

    //Constructors
    public Box() {
        logger.info("Box created");
    }
    public Box(double l, double w, double h) {
        length = SizeMeasurement.CENTIMETERS.convert(l);
        width = SizeMeasurement.CENTIMETERS.convert(w);
        height = SizeMeasurement.CENTIMETERS.convert(h);
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

    public static void validateSize(double l, double w, double h) throws ExceedsLimitsException, NegativeValueException {
        double size = SizeMeasurement.CENTIMETERS.convert(l*w*h);
        if (size > sizeLimit) {
            throw new ExceedsLimitsException("Size exceeds limit");
        } else if (l < 0 || w < 0 || h < 0) {
            throw new NegativeValueException("Got a negative value for size");
        }
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
