package DeliverySystem.orders;

public enum SizeMeasurement {
    INCHES ("inches", 2.54),
    CENTIMETERS ("centimeters", 0.39);

    private String name;
    private double conversionRate;

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
