package main.java.com.DeliverySystem.other;

import main.java.com.DeliverySystem.exceptions.UnloadedDataException;
import main.java.com.DeliverySystem.orders.Insurance;
import main.java.com.DeliverySystem.vehicles.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public final class DataLoader {
    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static ArrayList<Insurance> insurancePlans = new ArrayList<>();

    private DataLoader() {}

    public static void loadData() {
        loadVehicles();
        loadInsurancePlans();
        logger.info("Data loaded.");
    }

    private static void loadVehicles() {
        vehicles.clear();
        vehicles.add(new Truck("Standard Delivery Car", 2.0, "00-000000", 150.0, 65000.0));
        vehicles.add(new Truck("Heavy Delivery Truck", 3.0, "01-000000", 300.0, 90000.0));
        vehicles.add(new Truck("Priority Delivery Car", 6.0, "02-000000", 150.0, 65000.0));
        vehicles.add(new Truck("Delivery Freight Truck", 8.0, "03-000000", 1000.0, 500000.0));

        vehicles.add(new Plane("Standard Delivery Plane", 10.0, "04-000000", 300.0, 90000.0));
        vehicles.add(new Plane("Heavy Cargo Plane", 20.0, "05-000000", 3000, 500000.0));
    }

    private static void loadInsurancePlans() {
        insurancePlans.clear();
        insurancePlans.add(new Insurance("No Insurance", 0, 0));
        insurancePlans.add(new Insurance("Basic Insurance", 0.3, .015));
        insurancePlans.add(new Insurance("Fragility Insurance", 0.5, .03));
        insurancePlans.add(new Insurance("High Value Insurance", 1.0, .05));
    }

    public static void checkDataLoaded() throws UnloadedDataException {
        if (vehicles.isEmpty() || insurancePlans.isEmpty()) {
            throw new UnloadedDataException("Attempting to access DataLoader data that has not been loaded");
        }
    }

    public static ArrayList<Vehicle> getVehicles() throws UnloadedDataException {
        try {
            checkDataLoaded();
            return vehicles;
        } catch (UnloadedDataException e) {
            throw new UnloadedDataException(e.getMessage());
        }
    }

    public static ArrayList<Insurance> getInsurancePlans() throws UnloadedDataException {
        try {
            checkDataLoaded();
            return insurancePlans;
        } catch (UnloadedDataException e) {
            throw new UnloadedDataException(e.getMessage());
        }
    }
}
