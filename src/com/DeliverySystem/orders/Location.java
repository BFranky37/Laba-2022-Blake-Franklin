package com.DeliverySystem.orders;
import java.util.Objects;

public class Location {

    private String streetAddress = "123 Home St";
    private String city = "Hometown";
    private String state = "New York";
    private int zipcode = 11111;
    private String country = "USA";

    public Location() {
    }
    public Location(String saddress, String c, String s, int zip, String nation) {
        streetAddress = saddress;
        city = c;
        state = s;
        zipcode = zip;
        country = nation;
    }

    @Override
    public String toString() {
        return streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Location address = (Location) obj;

        return (this.streetAddress == address.streetAddress &&
                this.city == address.city &&
                this.state == address.state &&
                this.zipcode == address.zipcode &&
                this.country == address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, state, zipcode, country);
    }

}
