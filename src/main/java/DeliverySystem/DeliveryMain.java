package DeliverySystem;

import DeliverySystem.exceptions.*;
import DeliverySystem.orders.Package;
import DeliverySystem.orders.Shipment;
import DeliverySystem.other.*;
import DeliverySystem.people.*;

import org.apache.log4j.Logger;
import java.util.Scanner;

public class DeliveryMain {
    private static final Logger logger = Logger.getLogger(DeliveryMain.class.getName());

    public static void main(String[] args) {
        logger.info("Set up Logger");

        Scanner input = new Scanner(System.in);
        boolean valid = false;

        DataLoader.loadData();
        logger.info("Welcome to the DeliveryApp. We will be happy to ship your package.");

        //SENDER
        logger.info("First we need some information about you");
        logger.info("Please enter your name: ");
        String name = input.nextLine();
        logger.info("Please enter your phone number: ");
        String phoneNumber = input.nextLine();
        logger.info("Please enter your street address: ");
        String address = input.nextLine();
        logger.info("Please enter your city: ");
        String city = input.nextLine();
        SavedData.addCity(city);
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
        Sender sender = new Sender(name, phoneNumber, senderAddress);
        SavedData.addProfile(sender);

        //GOING THROUGH THE ORDER PROCESS
        boolean sendAnother;
        do {
            //PACKAGE
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

            Package shippingPackage = new Package(box, weight, value, fragility);

            //RECIPIENT
            logger.info("We now need to know who you want to send this package to.");
            logger.info("Please enter the recipient's name: ");
            name = input.nextLine();
            logger.info("Please enter the recipient's phone number: ");
            phoneNumber = input.nextLine();
            logger.info("Please enter the recipient's street address: ");
            address = input.nextLine();
            logger.info("Please enter the recipient's city: ");
            city = input.nextLine();
            SavedData.addCity(city);
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
            Recipient recipient = new Recipient(name, phoneNumber, recipientAddress);
            SavedData.addProfile(recipient);

            //INSURANCE
            int insuranceType;
            boolean insurancePurchased = false;
            if (shippingPackage.getValue() > 200)
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

            //SHIPMENT
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
                sender.addOrder(shipment);
                recipient.recievePackage(shipment.getPackage());
                logger.info("Shipment finalized");
                logger.info("Package Sent!");
            }

            sendAnother = false;
            do {
                try {
                    valid = false;
                    logger.info("Would you like to send another package? (y/n)");
                    sendAnother = ValidateInput.validateYesNo(input.nextLine());
                    valid = true;
                } catch (InvalidInputException e) {
                    logger.warn(e.getMessage() + "Invalid yes/no input");
                    logger.info("Please enter a valid input (y/n)");
                }
            } while (!valid);
        } while (sendAnother);

        logger.info("\nAll shipments finalized.");
        logger.info("Profiles created:");
        for (Object profile : SavedData.getProfiles()) {
            logger.info(profile);
        }
        logger.info("\nCurrently operating in these cities::");
        logger.info(SavedData.getCities());
        logger.info("\nPackages shipped:");
        sender.getOrders();
        logger.info("\nExiting Program...");
    }
}
