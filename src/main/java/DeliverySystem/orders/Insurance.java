package DeliverySystem.orders;

public enum Insurance {
    NONE ("No Insurance", 0, 0),
    BASIC ("Basic Insurance", 0.3, .015),
    FRAGILITY ("Fragility Insurance", 0.5, .03),
    HIGH_VALUE ("High Value Insurance", 1.0, .05);

    //MEMBERS
    private String name;
    private double baseCost; //base price of insurance
    private double priceRate; //extra cost added on based on value of the object being shipped (Usually between 1.5 - 5%)

    //Constructors
    Insurance(String nam, double cost, double rate) {
        name = nam;
        baseCost = cost;
        priceRate = rate;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public double getCost() {
        return baseCost;
    }

    public void setCost(double cost) {
        baseCost = cost;
    }

    public double getRate() {
        return priceRate;
    }

    public void setRate(double rate) {
        priceRate = rate;
    }

    public double calculatePrice(double value) {
        return baseCost + (priceRate * value);
    }

    //Class Overrides
    @Override
    public String toString() {
        return ("Name: " + name + ", Base Cost: " + baseCost + ", Price Rate: " + (priceRate * 100) + "%");
    }
}
