package DeliverySystem;

import DeliverySystem.exceptions.InvalidInputException;
import DeliverySystem.fileUtils.FileUtilities;
import DeliverySystem.orders.Insurance;
import DeliverySystem.orders.Package;
import DeliverySystem.other.DataLoader;
import DeliverySystem.other.Session;
import DeliverySystem.other.ValidateInput;
import DeliverySystem.people.Recipient;
import DeliverySystem.people.Sender;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class DeliveryMain {
    private static final Logger logger = Logger.getLogger(DeliveryMain.class.getName());

    public enum Menu {
        SHIP_PACKAGE (1, "Ship package"),
        EDIT_PROFILE (2, "Edit your profile information"),
        ADD_RECIPIENT (3, "Add recipient"),
        VIEW_PROFILES (4, "View recipient profiles"),
        OPERATING_CITIES (5, "View operating cities"),
        EXIT_PROGRAM (6, "Exit program");

        private final int selectionNum;
        private final String name;

        Menu(int selectionNum, String name) {
            this.selectionNum = selectionNum;
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public int getSelectionNum() {
            return selectionNum;
        }

        public void printMenu() {
            int num = 1;
            for (Menu option : getClass().getEnumConstants()) {
                logger.info(num + ". " + option.getName() + "\n");
                num++;
            }
        }

        public Menu makeSelection(int selection) throws InvalidInputException {
            for (Menu option : getClass().getEnumConstants()) {
                if (selection == option.getSelectionNum())
                     return option;
            }
            throw new InvalidInputException("Menu selection not in range.");
        }
    }

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
        logger.info("First we need some information about you");
        Sender sender = Session.getUserInfo();
        Session.addProfile(sender);

        //MENU
        Menu menu = Menu.SHIP_PACKAGE;
        boolean exit = false;
        boolean validInput = false;
        do {
            menu.printMenu(); //Print menu
            while (!validInput) {
                try {
                    menu = menu.makeSelection(input.nextInt()); //Select menu option
                    validInput = true;
                } catch (InvalidInputException e) {
                    logger.warn(e.getMessage() + " Please try again.");
                }
            }

            switch (menu) {
                case SHIP_PACKAGE:
                    //GOING THROUGH THE ORDER PROCESS
                    boolean sendAnother;
                    do {
                        boolean valid = false;
                        sendAnother = false;
                        //PACKAGE
                        logger.info("We need information about the package you are sending.");
                        Package shippingPackage = Session.getPackageInfo();
                        //RECIPIENT
                        logger.info("We now need to know who you want to send this package to.");
                        Recipient recipient = Session.getRecipientInfo();
                        Session.addProfile(recipient);
                        //INSURANCE
                        Insurance insuranceType = Session.getInsuranceType(shippingPackage);
                        //SHIPMENT
                        Session.finalizeShipment(sender, recipient, shippingPackage, insuranceType);

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
                    logger.info("Printing Receipts...");
                    try {
                        Session.printReceipt(sender);
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        throw new RuntimeException(e);
                    }

                case EDIT_PROFILE:
                    Session.getProfiles().remove(sender);
                    sender = Session.getUserInfo();
                    Session.addProfile(sender);
                    logger.info("User information saved.");

                case ADD_RECIPIENT:
                    Session.addProfile(Session.getRecipientInfo());
                    logger.info("Recipient profile added.");

                case VIEW_PROFILES:
                    logger.info("Recipient profiles created:");
                    Session.getProfiles().forEach((n) -> System.out.println(n.getName() + ": " + n.getAddress()));

                case OPERATING_CITIES:
                    if (Session.getCities().getLength() == 0)
                        logger.info("Not delivering packages in any cities currently.");
                    else {
                        logger.info("Currently operating in these cities:");
                        logger.info(Session.getCities());
                    }

                case EXIT_PROGRAM:
                    logger.info("Thank you for using the Delivery App!");
                    logger.info("Exiting Program...");
                    exit = true;
            }
        } while (exit);

    }
}
