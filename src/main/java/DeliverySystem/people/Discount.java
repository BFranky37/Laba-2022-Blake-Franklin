package DeliverySystem.people;

import org.apache.log4j.Logger;

public enum Discount {
    MILITARY ("Veteran", .10),
    SENIOR ("Senior Citizen", .05),
    EMPLOYEE ("Employee", .10),
    MEMBERSHIP ("Membership Holder", .15),
    NO_DISCOUNT ("None", 0);

    private static final Logger LOGGER = Logger.getLogger(Discount.class.getName());

    String name;
    double discountRate;

    Discount (String name, double discountRate) {
        this.name = name;
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double rate) {
        this.discountRate = rate;
    }

    public double applyDiscount(double price) {
        return price * discountRate;
    }
}
