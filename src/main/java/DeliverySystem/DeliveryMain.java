package DeliverySystem;

import DeliverySystem.exceptions.InvalidInputException;
import DeliverySystem.fileUtils.FileUtilities;
import DeliverySystem.orders.Insurance;
import DeliverySystem.orders.Package;
import DeliverySystem.other.CustomList;
import DeliverySystem.other.DataLoader;
import DeliverySystem.other.ValidateInput;
import DeliverySystem.people.Person;
import DeliverySystem.people.Recipient;
import DeliverySystem.people.Sender;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

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
            logger.info("Delivery App Menu:");
            for (Menu option : getClass().getEnumConstants()) {
                logger.info(num + ". " + option.getName());
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
        do {
            boolean validInput = false;
            menu.printMenu(); //Print menu
            while (!validInput) {
                try {
                    menu = menu.makeSelection(Integer.parseInt(input.nextLine())); //Select menu option
                    validInput = true;
                } catch (InvalidInputException | NumberFormatException e) {
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
                        Recipient recipient = null;
                        boolean previousRecipient;
                        do {
                            try { //SELECT FROM PREVIOUS RECIPIENTS
                                logger.info("Have you sent to this person before or added their information? (y/n)");
                                previousRecipient = ValidateInput.validateYesNo(input.nextLine());
                                if (previousRecipient) {
                                    int num = 1;
                                    List<Person> profiles = Session.getProfiles().stream().filter(name -> !name.getClass().equals(Sender.class)).collect(Collectors.toList());
                                    for (Person profile : profiles) {
                                        logger.info(num + ". " + profile.getName() + ": " + profile.getAddress());
                                        num++;
                                    }
                                    logger.info("Please select the recipient for this shipment.");
                                    int recipientSelection = Integer.parseInt(input.nextLine());
                                    if (recipientSelection > profiles.size())
                                        throw new InvalidInputException("Menu selection not in range.");
                                    recipient = (Recipient) profiles.get(recipientSelection - 1);
                                }
                                else { //CREATE NEW RECIPIENT PROFILE
                                    recipient = Session.getRecipientInfo();
                                    Session.addProfile(recipient);
                                }
                                valid = true;
                            } catch (InvalidInputException e) {
                                logger.info(e.getMessage());
                            }
                        } while (!valid);

                        //INSURANCE
                        Insurance insuranceType = Session.getInsuranceType(shippingPackage);
                        //SHIPMENT
                        Session.finalizeShipment(sender, recipient, shippingPackage, insuranceType);

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

                    logger.info("All shipments finalized.");
                    logger.info("Printing Receipts...");
                    try {
                        Session.printReceipt(sender);
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        throw new RuntimeException(e);
                    }
                    break;

                case EDIT_PROFILE:
                    Session.getProfiles().remove(sender);
                    //logger.info("old user profile removed.");
                    sender = Session.getUserInfo();
                    //logger.info("Got new user information");
                    Session.addProfile(sender);
                    logger.info("User information saved.");
                    break;

                case ADD_RECIPIENT:
                    Session.addProfile(Session.getRecipientInfo());
                    logger.info("Recipient profile added.");
                    break;

                case VIEW_PROFILES:
                    logger.info("Recipient profiles created:");
                    Session.getProfiles().forEach((n) -> System.out.println(n.getName() + ": " + n.getAddress()));
                    break;

                case OPERATING_CITIES:
                    ToIntFunction<CustomList> numberOfCities = (c) -> {return c.getLength();};
                    if (Session.getCities().getLength() == 0)
                        logger.info("Not delivering packages in any cities currently.");
                    else {
                        logger.info("Currently operating in " + numberOfCities.applyAsInt(Session.getCities()) + " cities:");
                        logger.info(Session.getCities());
                    }
                    break;

                case EXIT_PROGRAM:
                    logger.info("Thank you for using the Delivery App!");
                    logger.info("Exiting Program...");
                    exit = true;
                    break;
            }
        } while (!exit);

    }
}
