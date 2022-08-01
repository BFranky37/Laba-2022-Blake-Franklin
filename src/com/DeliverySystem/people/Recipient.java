package com.DeliverySystem.people;

import java.util.ArrayList;
import java.util.Objects;

import com.DeliverySystem.other.Location;
import com.DeliverySystem.orders.Package;

public class Recipient extends Person{
    //Members
    private final ArrayList<Package> packagesRecieved;

    //Constructors
    public Recipient() {
        super();
        packagesRecieved = new ArrayList<>();
    }

    public Recipient(String fullname, String phoneNum, Location fulladdress) {
        super(fullname, phoneNum, fulladdress);
        packagesRecieved = new ArrayList<>();
    }

    public void recievePackage(Package dropOff) {
        packagesRecieved.add(dropOff);
    }

    //to get the list of all packages this recipient has recieved
    public void getPackagesRecieved() {
        System.out.println("Packages recieved by" + super.getName() + ": ");
        for (Package p : packagesRecieved) {
            System.out.println(p);
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
                this.getAddress() == per.getAddress() &&
                this.packagesRecieved == per.packagesRecieved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getNumber(), this.getAddress(), packagesRecieved);
    }
}
