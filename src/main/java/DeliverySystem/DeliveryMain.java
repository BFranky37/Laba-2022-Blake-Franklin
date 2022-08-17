package DeliverySystem;

import DeliverySystem.exceptions.*;
import DeliverySystem.fileUtils.FileUtilities;
import DeliverySystem.orders.Package;
import DeliverySystem.orders.Shipment;
import DeliverySystem.other.*;
import DeliverySystem.people.*;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class DeliveryMain {
    private static final Logger logger = Logger.getLogger(DeliveryMain.class.getName());

    public static void main(String[] args) {
        logger.info("Set up Logger");

        Scanner input = new Scanner(System.in);

        try {
            FileUtilities.countUniqueWords("src/main/java/DeliverySystem/fileUtils/UniqueWords.txt");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        DataLoader.loadData();
        logger.info("Welcome to the DeliveryApp. We will be happy to ship your package.");

        //Get user information
        Sender sender = Session.getUserInfo();
        Session.addProfile(sender);

        //GOING THROUGH THE ORDER PROCESS
        boolean sendAnother;
        do {
            boolean valid = false;
            sendAnother = false;
            //PACKAGE
            Package shippingPackage = Session.getPackageInfo();
            //RECIPIENT
            Recipient recipient = Session.getRecipientInfo();
            Session.addProfile(recipient);
            //INSURANCE
            int insuranceType = Session.getInsuranceType(shippingPackage);
            //SHIPMENT
            Shipment shipment = Session.finalizeShipment(sender, recipient, shippingPackage, insuranceType);

            do {
                try {
                    logger.info("Would you like to send another package? (y/n)");
                    sendAnother = ValidateInput.validateYesNo(input.nextLine());
                    valid = true;
                } catch (InvalidInputException e) {
                    logger.warn(e.getMessage() + "Invalid yes/no input");
                    logger.info("Please enter a valid input (y/n)");
                }
            } while (!valid);
        } while (sendAnother);

        logger.info("All shipments finalized.");
        logger.info("Profiles created:");
        for (Object profile : Session.getProfiles()) {
            logger.info(profile);
        }
        logger.info("Currently operating in these cities::");
        logger.info(Session.getCities());
        logger.info("Printing Receipts:");
        try {
            Session.printReceipt(sender);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
