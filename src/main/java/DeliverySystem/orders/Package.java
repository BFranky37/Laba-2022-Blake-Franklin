package DeliverySystem.orders;

import DeliverySystem.exceptions.ExceedsLimitsException;
import DeliverySystem.exceptions.NegativeValueException;
import DeliverySystem.other.Box;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Package implements Charge {
    private static final Logger logger = Logger.getLogger(Package.class.getName());
    //Members
    private Box box;
    private double weight;
    private boolean fragility;
    private double value;
    private double cost;
    final static double costRate = 2.3;
    public static final double weightLimit = 3000;

    public enum WeightMeasurement {
        POUNDS ("pounds", 0.45),
        KILOGRAMS ("kilograms", 2.2);

        private final String name;
        private final double conversionRate;

        WeightMeasurement(String name, double conversionRate) {
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
    public Package(Box bx, double wgt, double val, boolean fragile) {
        box = bx;
        weight = WeightMeasurement.KILOGRAMS.convert(wgt); //weight is initially given in Kilos. Convert to pounds
        value = val;
        fragility = fragile;
        logger.info("Package created");
        calculatePrice();
    }

    //Getters and Setters
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

    public static double validateWeight(double weight) throws ExceedsLimitsException, NegativeValueException {
        weight = WeightMeasurement.KILOGRAMS.convert(weight); //weight is initially given in Kilos. Convert to pounds
        if (weight > weightLimit) {
            throw new ExceedsLimitsException("Size exceeds limit");
        } else if (weight < 0) {
            throw new NegativeValueException("Got a negative value for size");
        }
        else return weight;
    }

    //Class Overrides
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
