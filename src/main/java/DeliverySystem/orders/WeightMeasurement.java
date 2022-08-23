package DeliverySystem.orders;

public enum WeightMeasurement {
    POUNDS ("pounds", 0.45),
    KILOGRAMS ("kilograms", 2.2);

    private String name;
    private double conversionRate;

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
