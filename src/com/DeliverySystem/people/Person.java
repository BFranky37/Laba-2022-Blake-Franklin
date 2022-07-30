package com.DeliverySystem.people;

import com.DeliverySystem.other.Location;

public abstract class Person {
    
    private String name;
    private String phoneNumber;
    private Location mainAddress;

    public Person() {
        mainAddress = new Location();
    }

    public Person(String fullname, String phoneNum, Location fulladdress) {
        name = fullname;
        phoneNumber = phoneNum;
        mainAddress = fulladdress;
    }

    public String getName() {
        return name;
    }
    public void setName(String fullname) {
        name = fullname;
    }

    public String getNumber() {
        return phoneNumber;
    }
    public void setNumber(String num) {
        phoneNumber = num;
    }

    public Location getAddress() {
        return mainAddress;
    }
    public void setAddress(Location newAddress) {
        mainAddress = newAddress;
    }

    @Override
    public String toString() {
        return ("Name: " + name + "\nPhone Number: " + phoneNumber);
    }
}
