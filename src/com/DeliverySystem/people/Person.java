package com.DeliverySystem.people;

import java.util.Objects;

import com.DeliverySystem.orders.Location;

public class Person {
    
    private String firstName = "Gordon";
    private String lastName = "Ramsay";
    private Location address = new Location();

    public Person() {
    }
    public Person(String fname, String lname, Location fulladdress) {
        firstName = fname;
        lastName = lname;
        address = fulladdress;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\nAddress: " + address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Person per = (Person) obj;

        return (this.firstName == per.firstName &&
                this.lastName == per.lastName &&
                this.address == per.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address);
    }
}
