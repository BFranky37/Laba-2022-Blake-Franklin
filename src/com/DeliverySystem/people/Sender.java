package com.DeliverySystem.people;

import java.util.ArrayList;
import java.util.Objects;
import com.DeliverySystem.orders.Shipment;

public class Sender extends Person{

    private ArrayList<Shipment> orders = new ArrayList<>();

    public Sender() {
        super();
    }
    public Sender(String fullname, String phoneNum, Location fulladdress) {
        super(fullname, phoneNum, fulladdress);
    }

    public void addOrder(Shipment order) {
        orders.add(order);
    }
    //to get the list of all packages this recipient has recieved
    public void getOrders() {
        System.out.println("Shipments ordered by" + super.getName() + ": ");
        for (Shipment s : orders) { System.out.println(s);}
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Sender)) return false;
        Sender per = (Sender) obj;

        return (super.firstName == per.firstName &&
                super.lastName == per.lastName &&
                super.mainAddress == per.mainAddress &&
                this.orders == obj.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.name, super.phoneNumber, super.mainAddress, orders);
    }
}
