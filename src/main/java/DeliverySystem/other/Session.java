package DeliverySystem.other;

import DeliverySystem.exceptions.*;
import DeliverySystem.orders.Package;
import DeliverySystem.orders.Shipment;
import DeliverySystem.people.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;

public final class Session {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static final Scanner input = new Scanner(System.in);
    private static LinkedHashSet<Person> profiles = new LinkedHashSet<Person>();
    private static CustomList<String> cities = new CustomList<String>();
    private static boolean valid;
    private static int numShipments = 0;

    public static void addProfile(Person individual) {
        profiles.add(individual);
    }

    public static LinkedHashSet<Person> getProfiles() {
        return profiles;
    }

    public static void addCity(String city) {
        cities.add(city);
    }

    public static CustomList<String> getCities() {
        return cities;
    }

    public static int getNumShipments() {
        return numShipments;
    }

    public static Sender getUserInfo() {
        valid = false;
        logger.info("First we need some information about you");
        logger.info("Please enter your name: ");
        String name = input.nextLine();
        logger.info("Please enter your phone number: ");
        String phoneNumber = input.nextLine();
        logger.info("Please enter your street address: ");
        String address = input.nextLine();
        logger.info("Please enter your city: ");
        String city = input.nextLine();
        StringUtils.capitalize(city);
        addCity(city);
        logger.info("Please enter the your zipcode or postal code: ");
        int zipcode = 0;
        do {
            try {
                zipcode = ValidateInput.validateZip(input.nextInt());
                valid = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage() + "Invalid zipcode input");
                logger.info("Please enter a valid 6-digit zipcode:");
            }
        } while (!valid);

        Location senderAddress = new Location(address, city, zipcode);
        return new Sender(name, phoneNumber, senderAddress);
    }

    public static Package getPackageInfo() {
        double l = 0, w = 0, h = 0;
        double weight = 0;
        logger.info("We need information about the package you are sending.");
        do {
            try {
                valid = false;
                logger.info("Please enter the length, width, and height of your package in inches, one by one: ");
                l = input.nextDouble();
                w = input.nextDouble();
                h = input.nextDouble();
                Box.validateSize(l, w, h);

                logger.info("What is the weight of your package in pounds: ");
                weight = Package.validateWeight(input.nextDouble());
                valid = true;
            } catch (ExceedsLimitsException e) {
                //e.printStackTrace();
                logger.warn(e.getMessage());
                logger.info("This package exceeds our limits. Try using a smaller box or breaking your item up into lighter packages.");
            } catch (NegativeValueException e) {
                //e.printStackTrace();
                logger.warn(e.getMessage());
                logger.info("Please enter a valid size and weight.");
            }
        } while (!valid);
        Box box = new Box(l, w, h);

        logger.info("What is the value of the item you are shipping in dollars: ");
        double value = input.nextDouble();
        input.nextLine();

        boolean fragility = false;
        valid = false;
        do {
            try {
                logger.info("Is the item you are shipping fragile? (y/n): ");
                fragility = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage());
                logger.info("Please enter a valid input (y/n)");
            }
        } while (!valid);

        return new Package(box, weight, value, fragility);
    }

    public static Recipient getRecipientInfo() {
        logger.info("We now need to know who you want to send this package to.");
        logger.info("Please enter the recipient's name: ");
        String name = input.nextLine();
        logger.info("Please enter the recipient's phone number: ");
        String phoneNumber = input.nextLine();
        logger.info("Please enter the recipient's street address: ");
        String address = input.nextLine();
        logger.info("Please enter the recipient's city: ");
        String city = input.nextLine();
        StringUtils.capitalize(city);
        addCity(city);
        int zipcode = 11111;
        do {
            try {
                valid = false;
                logger.info("Please enter the recipient's zipcode or postal code: ");
                zipcode = ValidateInput.validateZip(input.nextInt());
                valid = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage() + "Invalid zipcode input");
                logger.info("Please enter a valid 6-digit zipcode:");
            }
        } while (!valid);
        input.nextLine();

        Location recipientAddress = new Location(address, city, zipcode);
        return new Recipient(name, phoneNumber, recipientAddress);
    }

    public static int getInsuranceType(Package shippingPackage) {
        int insuranceType;
        boolean insurancePurchased = false;
        double value = shippingPackage.getValue();
        if (value > 200)
            insuranceType = 3;
        else if (shippingPackage.getFragility())
            insuranceType = 2;
        else insuranceType = 1;
        valid = false;
        do {
            try {
                logger.info("Would you like to purchase insurance for this package to be reimbursed in the case it is lost or damaged? ");
                logger.info("The price for insurance for your item would be " +
                    "$" + Math.round(DataLoader.getInsurancePlans().get(insuranceType).calculatePrice(value) * 100.0) / 100.0 + " (y/n):");
                insurancePurchased = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage() + "Invalid yes/no input");
                logger.info("Please enter a valid input (y/n)");
            } catch (UnloadedDataException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } while (!valid);

        if (!insurancePurchased)
            insuranceType = 0;
        else {
            try {
                logger.info("Insurance added: " + DataLoader.getInsurancePlans().get(insuranceType).getName());
            } catch (UnloadedDataException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return insuranceType;
    }

    public static Shipment finalizeShipment(Sender sender, Recipient recipient, Package shippingPackage, int insuranceType) {
        boolean priority = false;
        do {
            try {
                valid = false;
                logger.info("Would you like to pay for priority shipping to ensure your package reaches the destination quickly? (y/n): ");
                priority = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage() + "Invalid yes/no input");
                logger.info("Please enter a valid input (y/n)");
            }
        } while (!valid);
        if (priority) {
            logger.info("Priority Shipping added.");
        }

        Shipment shipment;
        try {
            shipment = new Shipment(sender, recipient, shippingPackage, DataLoader.getInsurancePlans().get(insuranceType), priority);
        } catch (UnloadedDataException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        logger.info("The final price for this shipment comes out to $" + Math.round(shipment.getPrice() * 100.0) / 100.0);
        boolean shipmentFinalized = false;
        do {
            try {
                valid = false;
                logger.info("Would you like to finalize this shipment? (y/n)");
                shipmentFinalized = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage() + "Invalid yes/no input");
                logger.info("Please enter a valid input (y/n)");
            }
        } while (!valid);

        if (shipmentFinalized) {
            numShipments ++;
            sender.addOrder(shipment);
            recipient.recievePackage(shipment.getPackage());
            logger.info("Shipment finalized");
            logger.info("Package Sent!");
        }

        return shipment;
    }

    public static void printReceipt(Sender sender) throws IOException {
        if (numShipments == 0)
            return;
        Date date = new java.util.Date();
        double totalPrice = 0;
        File file = FileUtils.getFile("Receipts.txt");
        FileUtils.writeStringToFile(file, "Receipt for " + date, Charset.defaultCharset(), true);
        while (numShipments > 0) {
            Shipment shipment = sender.getOrders().get(sender.getOrders().size() - numShipments);
            String shipmentDetails = "\n\nRecipient: " + shipment.getRecipient() +
                                        "\nRoute: " + shipment.getRoute() +
                                        "\nShipping method: " + shipment.getVehicle() +
                                        "\nPrice: $" + String.format("%.2f", shipment.getPrice()) + "\n";
            FileUtils.writeStringToFile(file, shipmentDetails, Charset.defaultCharset(), true);
            totalPrice += shipment.getPrice();
            numShipments--;
        }
        FileUtils.writeStringToFile(file, "\nTotal: $" + totalPrice + "\n\n\n", Charset.defaultCharset(), true);
    }
}
