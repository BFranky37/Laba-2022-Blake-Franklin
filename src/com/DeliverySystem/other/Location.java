package com.DeliverySystem.other;

import java.util.Objects;

public class Location {

    private String streetAddress;
    private String city;
    private int zipcode;

    public Location() {
    }
    public Location(String saddress, String c, int zip) {
        streetAddress = saddress;
        city = c;
        zipcode = zip;
    }

    public String getAddress() {
        return streetAddress;
    }
    public void setAddress(String address) {
        streetAddress = address;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String newcity) {
        city = newcity;
    }

    public int getZipcode() {
        return zipcode;
    }
    public void setZipcode(int zip) {
        zipcode = zip;
    }

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
