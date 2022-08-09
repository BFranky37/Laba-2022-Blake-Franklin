package com.DeliverySystem.orders;

import com.DeliverySystem.people.Location;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.lang.Math;

public class Route implements Charge {
    private static final Logger logger = Logger.getLogger(Route.class.getName());
    //Members
    private Location fromLocation;
    private Location toLocation;
    private int distance;
    private double price;
    private static final double basePrice = .10;

    //Constructors
    public Route(Location from, Location to) {
        fromLocation = from;
        toLocation = to;
        logger.info("Route created.");
        calculatePrice();
    }

    //Getters and Setters
    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location from) {
        fromLocation = from;
        calculatePrice();
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location to) {
        toLocation = to;
        calculatePrice();
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void calculatePrice() { //Generally speaking, the bigger the difference in Zip Code, the further away the post offices are
        distance = Math.abs(fromLocation.getZipcode() - toLocation.getZipcode());
        if (distance < 100) {
            price = (Math.round(distance * 100.0) / 100000.0) + basePrice;
        }
        else {
            price = (Math.round(distance * 100.0) / 2000000.0) + basePrice;
        }
    }

    //Class Overrides
    @Override
    public String toString() {
        return ("From " + fromLocation.getCity() + " to " + toLocation.getCity());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Route)) return false;
        Route route = (Route) obj;

        return (this.fromLocation == route.fromLocation &&
                this.toLocation == route.toLocation &&
                this.price == route.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromLocation, toLocation, price);
    }
}
