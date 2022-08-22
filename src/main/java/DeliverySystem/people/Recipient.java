package DeliverySystem.people;

import DeliverySystem.exceptions.InvalidDeliveryException;
import org.apache.log4j.Logger;
import DeliverySystem.orders.Package;

import java.util.ArrayList;
import java.util.Objects;

public class Recipient extends Person{
    private static final Logger logger = Logger.getLogger(Recipient.class.getName());
    //Members
    private final ArrayList<Package> packagesRecieved;

    //Constructors
    public Recipient() {
        super();
        packagesRecieved = new ArrayList<>();
        logger.info("Recipient created.");
    }

    public Recipient(String fullname, String phoneNum, Location fulladdress) {
        super(fullname, phoneNum, fulladdress);
        packagesRecieved = new ArrayList<>();
        logger.info("Recipient created.");
    }

    public void receivePackage(Package dropOff) {
        try {
            if (dropOff.getBox() == null) {
                throw new InvalidDeliveryException("Null values attached to this shipment");
            } else packagesRecieved.add(dropOff);
        } catch (InvalidDeliveryException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //to get the list of all packages this recipient has received
    public void getPackagesRecieved() {
        logger.info("Packages received by " + super.getName() + ": ");
        for (Package p : packagesRecieved) {
            logger.info(p);
        }
    }

    //Class Overrides
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Recipient)) return false;
        Recipient per = (Recipient) obj;

        return (Objects.equals(this.getName(), per.getName()) &&
                Objects.equals(this.getNumber(), per.getNumber()) &&
                this.getAddress() == per.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getNumber(), this.getAddress());
    }
}
