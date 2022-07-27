package com.DeliverySystem.orders;

import java.util.Objects;
import java.lang.Math;

public class Route{
    
    private Location fromLocation;
    private Location toLocation;
    private int fromZip;
    private int toZip;
    private int distance;
    private double price;

    public Route(Location from, Location to) {
        fromLocation = from;
        toLocation = to;
        calculateRoute();
    }

    public Location getFromLocation() { return fromLocation; }
    public void setFromLocation(Location from) { 
        fromLocation = from; 
        calculateRoute();
    }

    public Location getToLocation() { return toLocation; }
    public void setToLocation(Location to) { 
        toLocation = to; 
        calculateRoute();
    }

    public int getDistance() { return distance; }
    
    public double getPrice() { return price; }

    public void calculateRoute() { //Generally speaking, the bigger the difference in Zip Code, the further away the post offices are
        double basePrice = .10;
        fromZip = fromLocation.getZipcode();
        toZip = toLocation.getZipcode();

        distance = abs(fromZip - toZip);
        if (distance < 100) {
            price = (Math.round(distance * 100.0) / 100000.0) + basePrice;
        }
        else {
            price = (Math.round(distance * 100.0) / 2000000.0) + basePrice;
        }

    }

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
