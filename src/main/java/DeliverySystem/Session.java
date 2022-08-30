package DeliverySystem;

import DeliverySystem.exceptions.ExceedsLimitsException;
import DeliverySystem.exceptions.InvalidInputException;
import DeliverySystem.exceptions.NegativeValueException;
import DeliverySystem.exceptions.UnloadedDataException;
import DeliverySystem.functionalInterfaces.IEditString;
import DeliverySystem.orders.Box;
import DeliverySystem.orders.Insurance;
import DeliverySystem.orders.Package;
import DeliverySystem.orders.Shipment;
import DeliverySystem.other.CustomList;
import DeliverySystem.other.ValidateInput;
import DeliverySystem.people.*;
import DeliverySystem.threads.DropOff;
import DeliverySystem.threads.Travel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;

public final class Session {
    private static final Logger LOGGER = Logger.getLogger(Session.class.getName());
    private static final Scanner input = new Scanner(System.in);
    private static LinkedHashSet<Person> profiles = new LinkedHashSet<Person>();
    private static CustomList<String> cities = new CustomList<String>();
    private static boolean valid;
    private static int numShipments = 0;

    public static void addProfile(Person individual) {
        profiles.add(individual);
    }

    public static void updateProfile(Person individual) {
        profiles.remove(individual);
        addProfile(individual);
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
        input.nextLine();
        LOGGER.info("Please enter your name: ");
        String name = input.nextLine();
        LOGGER.info("Please enter your phone number: ");
        String phoneNumber = input.nextLine();
        LOGGER.info("Please enter your street address: ");
        String address = input.nextLine();
        LOGGER.info("Please enter your city: ");
        String city = input.nextLine();
        StringUtils.capitalize(city);
        addCity(city);
        LOGGER.info("Please enter the your zipcode or postal code: ");
        int zipcode = 0;
        do {
            try {
                zipcode = ValidateInput.validateZip(input.nextInt());
                valid = true;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage() + "Invalid zipcode input");
                LOGGER.info("Please enter a valid 6-digit zipcode:");
            }
        } while (!valid);

        Location senderAddress = new Location(address, city, zipcode);
        return new Sender(name, phoneNumber, senderAddress);
    }

    public static Package getPackageInfo() {
        //Getting Box info
        double l = 0, w = 0, h = 0;
        double weight = 0;
        do {
            try {
                valid = false;
                LOGGER.info("Please enter the length, width, and height of your package in centimeters, one by one: ");
                l = input.nextDouble();
                w = input.nextDouble();
                h = input.nextDouble();
                Box.validateSize(l, w, h);

                LOGGER.info("What is the weight of your package in kilograms: ");
                weight = Package.validateWeight(input.nextDouble());
                valid = true;
            } catch (ExceedsLimitsException e) {
                //e.printStackTrace();
                LOGGER.warn(e.getMessage());
                LOGGER.info("This package exceeds our limits. Try using a smaller box or breaking your item up into lighter packages.");
            } catch (NegativeValueException e) {
                //e.printStackTrace();
                LOGGER.warn(e.getMessage());
                LOGGER.info("Please enter a valid size and weight.");
            }
        } while (!valid);
        //Using reflection to create Box and call its methods
        Box box;
        try {
            box = Box.class.getConstructor(double.class, double.class, double.class).newInstance(l, w, h);
            Method returnArea = Box.class.getMethod("getArea");
            double area = (double) returnArea.invoke(box);
            LOGGER.info("the area of this box is " + area + "inches");
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("What is the value of the item you are shipping in dollars: ");
        double value = input.nextDouble();
        input.nextLine();

        boolean fragility = false;
        valid = false;
        do {
            try {
                LOGGER.info("Is the item you are shipping fragile? (y/n): ");
                fragility = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage());
                LOGGER.info("Please enter a valid input (y/n)");
            }
        } while (!valid);

        return new Package(box, weight, value, fragility);
    }

    public static Recipient getRecipientInfo() {
        input.nextLine();
        LOGGER.info("Please enter the recipient's name: ");
        String name = input.nextLine();
        LOGGER.info("Please enter the recipient's phone number: ");
        String phoneNumber = input.nextLine();
        LOGGER.info("Please enter the recipient's street address: ");
        String address = input.nextLine();
        LOGGER.info("Please enter the recipient's city: ");
        String city = input.nextLine();
        city = StringUtils.capitalize(city);
        addCity(city);
        int zipcode = 11111;
        do {
            try {
                valid = false;
                LOGGER.info("Please enter the recipient's zipcode or postal code: ");
                zipcode = ValidateInput.validateZip(input.nextInt());
                valid = true;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage() + "Invalid zipcode input");
                LOGGER.info("Please enter a valid 6-digit zipcode:");
            }
        } while (!valid);
        input.nextLine();

        Location recipientAddress = new Location(address, city, zipcode);
        return new Recipient(name, phoneNumber, recipientAddress);
    }

    public static Insurance getInsuranceType(Package shippingPackage) {
        Insurance insuranceType;
        boolean insurancePurchased = false;
        double value = shippingPackage.getValue();
        if (value > 200)
            insuranceType = Insurance.HIGH_VALUE;
        else if (shippingPackage.getFragility())
            insuranceType = Insurance.FRAGILITY;
        else insuranceType = Insurance.BASIC;
        valid = false;
        do {
            try {
                LOGGER.info("Would you like to purchase insurance for this package to be reimbursed in the case it is lost or damaged? ");
                LOGGER.info("The price for insurance for your item would be " +
                    "$" + Math.round(insuranceType.calculatePrice(value) * 100.0) / 100.0 + " (y/n):");
                insurancePurchased = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage() + "Invalid yes/no input");
                LOGGER.info("Please enter a valid input (y/n)");
            }
        } while (!valid);

        if (!insurancePurchased)
            insuranceType = Insurance.NONE;
        else {
            LOGGER.info("Insurance added: " + insuranceType.getName());
        }
        return insuranceType;
    }

    public static Shipment finalizeShipment(Sender sender, Recipient recipient, Package shippingPackage, Insurance insuranceType) {
        boolean priority = false;
        do {
            try {
                valid = false;
                LOGGER.info("Would you like to pay for priority shipping to ensure your package reaches the destination quickly? (y/n): ");
                priority = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage() + "Invalid yes/no input");
                LOGGER.info("Please enter a valid input (y/n)");
            }
        } while (!valid);
        if (priority) {
            LOGGER.info("Priority Shipping added.");
        }

        Shipment shipment;
        try {
            shipment = new Shipment(sender, recipient, shippingPackage, insuranceType, priority);
        } catch (UnloadedDataException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

        LOGGER.info("The final price for this shipment comes out to $" + Math.round(shipment.getPrice() * 100.0) / 100.0);
        if (sender.getDiscount() != Discount.NO_DISCOUNT)
            LOGGER.info("with " + sender.getDiscount().getName() + " discount of " + sender.getDiscount().getDiscountRate() * 100 + "% applied.");
        boolean shipmentFinalized = false;
        do {
            try {
                valid = false;
                LOGGER.info("Would you like to finalize this shipment? (y/n)");
                shipmentFinalized = ValidateInput.validateYesNo(input.nextLine());
                valid = true;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage() + "Invalid yes/no input");
                LOGGER.info("Please enter a valid input (y/n)");
            }
        } while (!valid);

        if (shipmentFinalized) {
            numShipments ++;
            //LOGGER.info("num shipments increase to " + numShipments);
            sender.addOrder(shipment);
            //LOGGER.info("shipment added to sender orders");
            //recipient.receivePackage(shipment.getPackage());
            //LOGGER.info("shipment added to recipient orders");
            LOGGER.info("Shipment finalized");
            LOGGER.info("Package Sent!");

            Object Lock1 = new Object();
            Object Lock2 = new Object();
            Thread travel = new Thread(new Travel(shipment, Lock1, Lock2));
            Thread receivePackage = new Thread(new DropOff(shipment, Lock1, Lock2));
            travel.start();
            receivePackage.start();
            LOGGER.info("Delivery vehicle has begun it's transport");
        }
        return shipment;
    }

    public static void printReceipt(Sender sender) throws IOException {
        if (numShipments == 0)
            return;
        Date date = new java.util.Date();
        double totalPrice = 0;
        File file = FileUtils.getFile("Receipts.txt");
        IEditString<String> punctuationAppend = (string, punctuation) -> string + punctuation;
        String writeToFile = ("Receipt for " + date);
        while (numShipments > 0) {
            Shipment shipment = sender.getOrders().get(sender.getOrders().size() - numShipments);
            writeToFile = punctuationAppend.append(writeToFile + "\n\nRecipient: " + shipment.getRecipient() +
                                                    "\nRoute: " + shipment.getRoute() +
                                                    "\nShipping method: " + shipment.getVehicle() +
                                                    "\nPrice: $" + String.format("%.2f", shipment.getPrice()) + "\n", ",");
            totalPrice += shipment.getPrice();
            numShipments--;
        }
        writeToFile = punctuationAppend.append(writeToFile + "\nTotal: $" + String.format("%.2f", totalPrice) + "\n\n\n", "...");
        FileUtils.writeStringToFile(file, writeToFile, Charset.defaultCharset(), true);
    }
}
