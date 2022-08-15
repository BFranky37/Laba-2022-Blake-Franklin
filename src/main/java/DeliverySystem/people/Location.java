package main.java.DeliverySystem.people;

import org.apache.log4j.Logger;

import java.util.Objects;

public class Location {
    private static final Logger logger = Logger.getLogger(Location.class.getName());
    //Members
    private String streetAddress;
    private String city;
    private int zipcode;

    //Constructors
    public Location() {
    }
    public Location(String saddress, String c, int zip) {
        streetAddress = saddress;
        city = c;
        zipcode = zip;
        logger.info("Address created.");
    }

    //Getters and Setters
    public String getAddress() {
        return streetAddress;
    }

    public void setAddress(String address) {
        streetAddress = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        city = newCity;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zip) {
        zipcode = zip;
    }

    //Class Overrides
    @Override
    public String toString() {
        return streetAddress + ", " + city + ", " + zipcode + ", ";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Location)) return false;
        Location address = (Location) obj;

        return (Objects.equals(this.streetAddress, address.streetAddress) &&
                Objects.equals(this.city, address.city) &&
                this.zipcode == address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, zipcode);
    }
}
