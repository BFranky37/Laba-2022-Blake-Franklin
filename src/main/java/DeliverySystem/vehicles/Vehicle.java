package DeliverySystem.vehicles;

public abstract class Vehicle implements Limits {
    //Members
    private String name;
    private double costRate;
    private double weightCapacity; //max weight of packages in pounds
    //for reference, UPS separates weight class at the 70 and 150lbs mark for standard cars, with no limit on freight trucks
    private double spaceCapacity; //max size of packages in cubic inches

    //Constructors
    public Vehicle() {

    }
    public Vehicle(String newName, double rate, double maxWeight, double maxSize) {
        name = newName;
        costRate = rate;
        weightCapacity = maxWeight;
        spaceCapacity = maxSize;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public double getPrice() {
        return costRate;
    }

    public void setRate(double rate) {
        costRate = rate;
    }

    @Override
    public double getMaxWeight() {
        return weightCapacity;
    }

    public void setMaxWeight(double weight) {
        weightCapacity = weight;
    }

    @Override
    public double getMaxSize() {
        return spaceCapacity;
    }

    public void setMaxSize(double size) {
        spaceCapacity = size;
    }

    //Class Overrides
    @Override
    public String toString() {
        return ("Vehicle Name: " + name + ", Cost Rate: " + costRate + ", Max Weight: " + weightCapacity + ", Max Size: " + spaceCapacity);
    }
}
