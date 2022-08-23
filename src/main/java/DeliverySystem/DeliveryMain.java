package DeliverySystem;

import DeliverySystem.exceptions.InvalidInputException;
import DeliverySystem.fileUtils.FileUtilities;
import DeliverySystem.functionalInterfaces.IFilter;
import DeliverySystem.functionalInterfaces.IReturnNum;
import DeliverySystem.orders.Insurance;
import DeliverySystem.orders.Package;
import DeliverySystem.other.CustomList;
import DeliverySystem.other.DataLoader;
import DeliverySystem.other.Menu;
import DeliverySystem.other.ValidateInput;
import DeliverySystem.people.Discount;
import DeliverySystem.people.Person;
import DeliverySystem.people.Recipient;
import DeliverySystem.people.Sender;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DeliveryMain {
    private static final Logger LOGGER = Logger.getLogger(DeliveryMain.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Set up Logger");

        Scanner input = new Scanner(System.in);

        try {
            FileUtilities.countUniqueWords("src/main/java/DeliverySystem/fileUtils/UniqueWords.txt");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

        DataLoader.loadData();
        LOGGER.info("Welcome to the DeliveryApp. We will be happy to ship your package. ");

        //Get user information
        LOGGER.info("First we need some information about you. Press enter to continue");
        Sender sender = Session.getUserInfo();
        Session.addProfile(sender);

        //MENU
        Menu menu = Menu.SHIP_PACKAGE;
        boolean exit = false;
        boolean valid = false;
        do {
            boolean validInput = false;
            menu.printMenu(); //Print menu
            while (!validInput) {
                try {
                    menu = menu.makeSelection(Integer.parseInt(input.nextLine())); //Select menu option
                    validInput = true;
                } catch (InvalidInputException | NumberFormatException e) {
                    LOGGER.warn(e.getMessage() + " Please try again.");
                }
            }

            switch (menu) {
                case SHIP_PACKAGE:
                    //GOING THROUGH THE ORDER PROCESS
                    boolean sendAnother;
                    do {
                        valid = false;
                        sendAnother = false;
                        //PACKAGE
                        LOGGER.info("We need information about the package you are sending.");
                        Package shippingPackage = Session.getPackageInfo();

                        //RECIPIENT
                        LOGGER.info("We now need to know who you want to send this package to. Press enter to continue");
                        Recipient recipient = null;
                        boolean previousRecipient;
                        do {
                            try { //SELECT FROM PREVIOUS RECIPIENTS
                                LOGGER.info("Have you sent to this person before or added their information? (y/n)");
                                previousRecipient = ValidateInput.validateYesNo(input.nextLine());
                                if (previousRecipient) {
                                    int num = 1;
                                    IFilter<LinkedHashSet<Person>, List<Person>> filterProfiles = (profiles) -> //Convert profiles to List of Recipient names
                                            (List<Person>) profiles.stream().filter(name -> !name.getClass().equals(Sender.class)).collect(Collectors.toList());
                                    List<Person> profiles = Session.getProfiles().stream().filter(name -> !name.getClass().equals(Sender.class)).collect(Collectors.toList());
                                    for (Person profile : profiles) {
                                        LOGGER.info(num + ". " + profile.getName() + ": " + profile.getAddress());
                                        num++;
                                    }
                                    LOGGER.info("Please select the recipient for this shipment.");
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
                                LOGGER.info(e.getMessage());
                            }
                        } while (!valid);

                        //INSURANCE
                        Insurance insuranceType = Session.getInsuranceType(shippingPackage);
                        //SHIPMENT
                        Session.finalizeShipment(sender, recipient, shippingPackage, insuranceType);

                        do {
                            try {
                                valid = false;
                                LOGGER.info("Would you like to send another package? (y/n)");
                                sendAnother = ValidateInput.validateYesNo(input.nextLine());
                                valid = true;
                            } catch (InvalidInputException e) {
                                LOGGER.warn(e.getMessage() + "Invalid yes/no input");
                                LOGGER.info("Please enter a valid input (y/n)");
                            }
                        } while (!valid);
                    } while (sendAnother);

                    LOGGER.info("All shipments finalized.");
                    LOGGER.info("Printing Receipts...");
                    try {
                        Session.printReceipt(sender);
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                        throw new RuntimeException(e);
                    }
                    break;

                case EDIT_PROFILE:
                    Session.getProfiles().remove(sender);
                    //LOGGER.info("old user profile removed.");
                    sender = Session.getUserInfo();
                    //LOGGER.info("Got new user information");
                    Session.addProfile(sender);
                    LOGGER.info("User information saved.");
                    break;

                case CHANGE_DISCOUNT:
                    StringBuilder discountName = new StringBuilder("");
                    double discountRate = 0.0;
                    LOGGER.info("Please answer the following yes or no questions (y/n) to apply for discount:");
                    for (Discount discount : Discount.values()) {
                        do {
                            try {
                                if (discount != Discount.NO_DISCOUNT) {
                                    valid = false;
                                    LOGGER.info("Are you a " + discount.getName() + "?");
                                    if (ValidateInput.validateYesNo(input.nextLine())) {
                                        discountName.append(discount.getName()).append(" ");
                                        discountRate += discount.getDiscountRate();
                                    }
                                    valid = true;
                                }
                            } catch (InvalidInputException e) {
                                LOGGER.warn(e.getMessage() + "Invalid yes/no input");
                                LOGGER.info("Please enter a valid input (y/n)");
                            }
                        } while (!valid);
                    }

                    if (discountRate == 0)
                        sender.setDiscount(Discount.NO_DISCOUNT);
                    else {
                        sender.getDiscount().setName(discountName.toString());
                        sender.getDiscount().setDiscountRate(discountRate);
                    }
                    LOGGER.info("Discount set to " + sender.getDiscount().getName() + " with a rate of " + sender.getDiscount().getDiscountRate());
                    break;

                case ADD_RECIPIENT:
                    Session.addProfile(Session.getRecipientInfo());
                    LOGGER.info("Recipient profile added.");
                    break;

                case VIEW_PROFILES:
                    LOGGER.info("Recipient profiles created:");
                    Session.getProfiles().forEach((n) -> LOGGER.info(n.getName() + ": " + n.getAddress()));
                    break;

                case OPERATING_CITIES:
                    IReturnNum<CustomList> numberOfCities = (c) -> c.getLength() - 1;
                    if (Session.getCities().getLength() <= 0)
                        LOGGER.info("Not delivering packages in any cities currently.");
                    else {
                        LOGGER.info("Currently operating in " + numberOfCities.getItem(Session.getCities()) + " cities:");
                        LOGGER.info(Session.getCities());
                        LOGGER.info("Covering a total distance of");
                    }
                    break;

                case EXIT_PROGRAM:
                    LOGGER.info("Thank you for using the Delivery App!");
                    LOGGER.info("Exiting Program...");
                    exit = true;
                    break;
            }
        } while (!exit);

    }
}
