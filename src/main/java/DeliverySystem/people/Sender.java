package DeliverySystem.people;

import DeliverySystem.exceptions.InvalidDeliveryException;
import DeliverySystem.orders.Shipment;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Objects;

public class Sender extends Person{
    private static final Logger LOGGER = Logger.getLogger(Sender.class.getName());
    //Members
    private final ArrayList<Shipment> orders;
    private Discount discount;

    //Constructors
    public Sender() {
        super();
        orders = new ArrayList<>();
        LOGGER.info("Sender created.");
    }

    public Sender(String fullname, String phoneNum, Location fulladdress) {
        super(fullname, phoneNum, fulladdress);
        orders = new ArrayList<>();
        LOGGER.info("Sender created.");
        discount = Discount.NO_DISCOUNT;
    }

    public void addOrder(Shipment order) {
        try {
            if (order.getInsurance() == null ||
                    order.getRecipient() == null ||
                    order.getVehicle() == null ||
                    order.getPackage() == null ||
                    order.getRoute() == null) {
                throw new InvalidDeliveryException("Null values attached to this shipment");
            } else if (order.getSender() != this) {
                throw new InvalidDeliveryException("Sender is attempting to make a shipment under another person's name");
            } else orders.add(order);
        } catch (InvalidDeliveryException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    //to get the list of all packages this recipient has received
    public ArrayList<Shipment> getOrders() {
        return orders;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    //Class Overrides
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Sender)) return false;
        Sender per = (Sender) obj;

        return (Objects.equals(this.getName(), per.getName()) &&
                Objects.equals(this.getNumber(), per.getNumber()) &&
                this.getAddress() == per.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getNumber(), this.getAddress());
    }
}
