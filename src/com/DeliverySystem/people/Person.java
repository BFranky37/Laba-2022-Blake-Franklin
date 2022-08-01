package com.DeliverySystem.people;

import com.DeliverySystem.other.Location;

public abstract class Person implements Profile{
    
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

    @Override
    public String getName() {
        return name;
    }
    public void setName(String fullname) {
        name = fullname;
    }

    @Override
    public String getNumber() {
        return phoneNumber;
    }
    public void setNumber(String num) {
        phoneNumber = num;
    }

    @Override
    public Location getAddress() {
        return mainAddress;
    }
    public void setAddress(Location newAddress) {
        mainAddress = newAddress;
    }

    @Override
    public String toString() {
        return ("Name: " + name + ", Phone Number: " + phoneNumber);
    }
}
