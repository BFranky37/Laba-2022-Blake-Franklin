package DeliverySystem.other;

import DeliverySystem.exceptions.UnloadedDataException;
import DeliverySystem.vehicles.Plane;
import DeliverySystem.vehicles.Truck;
import DeliverySystem.vehicles.Vehicle;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public final class DataLoader {
    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();

    private DataLoader() {}

    public static void loadData() {
        loadVehicles();
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

    public static void checkDataLoaded() throws UnloadedDataException {
        if (vehicles.isEmpty()) {
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
}
