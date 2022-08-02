import com.DeliverySystem.exceptions.*;
import com.DeliverySystem.orders.Package;
import com.DeliverySystem.orders.Shipment;
import com.DeliverySystem.other.*;
import com.DeliverySystem.people.*;

import java.util.Scanner;

import static com.DeliverySystem.other.DataLoader.getInsurancePlans;
import static com.DeliverySystem.other.DataLoader.loadData;

public class DeliveryMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        try {
            loadData();

            //SENDER
            System.out.println("Welcome to the DeliveryApp. We will be happy to ship your package. First we need some information about you");
            System.out.println("Please enter your name: ");
            String name = input.nextLine();
            System.out.println("Please enter your phone number: ");
            String phoneNumber = input.nextLine();
            System.out.println("Please enter your street address: ");
            String address = input.nextLine();
            System.out.println("Please enter your city: ");
            String city = input.nextLine();
            System.out.println("Please enter your zipcode or postal code: ");
            int zipcode = input.nextInt();

            Location senderAddress = new Location(address, city, zipcode);
            Sender sender = new Sender(name, phoneNumber, senderAddress);

            //PACKAGE
            double l = 0, w = 0, h = 0;
            double weight = 0;
            System.out.println("Now we need information about the package you are sending.");
            do {
                try {
                    System.out.println("Please enter the length, width, and height of your package in inches, one by one: ");
                    l = input.nextDouble();
                    w = input.nextDouble();
                    h = input.nextDouble();
                    Box.validateSize(l, w, h);

                    System.out.println("What is the weight of your package in pounds: ");
                    weight = Package.validateWeight(input.nextDouble());
                    valid = true;
                } catch (ExceedsLimitsException e) {
                    System.out.println("This package exceeds our limits. Try using a smaller box or breaking your item up into lighter packages.");
                } catch (NegativeValueException e) {
                    System.out.println("Please enter a valid size and weight.");
                }
            } while (!valid);
            Box box = new Box(l, w, h);


            System.out.println("What is the value of the item you are shipping in dollars: ");
            double value = input.nextDouble();
            input.nextLine();

            boolean fragility = false;
            valid = false;
            do {
                try {
                    System.out.println("Is the item you are shipping fragile? (y/n): ");
                    fragility = ValidateInput.validateYesNo(input.nextLine());
                    valid = true;
                } catch (InvalidInputException e) {
                    System.out.println("Please enter a valid input (y/n)");
                }
            } while (!valid);

            Package shippingPackage = new Package(box, weight, value, fragility);

            //RECIPIENT
            System.out.println("We now need to know who you want to send this package to.");
            System.out.println("Please enter the recipient's name: ");
            name = input.nextLine();
            System.out.println("Please enter the recipient's phone number: ");
            phoneNumber = input.nextLine();
            System.out.println("Please enter the recipient's street address: ");
            address = input.nextLine();
            System.out.println("Please enter the recipient's city: ");
            city = input.nextLine();
            System.out.println("Please enter the recipient's zipcode or postal code: ");
            zipcode = input.nextInt();
            input.nextLine();

            Location recipientAddress = new Location(address, city, zipcode);
            Recipient recipient = new Recipient(name, phoneNumber, recipientAddress);

            //INSURANCE
            int insuranceType;
            boolean insurancePurchased =false;
            if (shippingPackage.getValue() > 200)
                insuranceType = 3;
            else if (shippingPackage.getFragility())
                insuranceType = 2;
            else insuranceType = 1;
            valid = false;
            do {
                try {
                    System.out.println("Would you like to purchase insurance for this package to be reimbursed in the case it is lost or damaged? ");
                    System.out.println("The price for insurance for your item would be " +
                            "$" + Math.round(getInsurancePlans().get(insuranceType).calculatePrice(value) * 100.0) / 100.0 + " (y/n):");
                    insurancePurchased = ValidateInput.validateYesNo(input.nextLine());
                    valid = true;
                } catch (InvalidInputException e) {
                    System.out.println("Please enter a valid input (y/n)");
                }
            } while (!valid);

            if (!insurancePurchased)
                insuranceType = 0;
            else System.out.println("Insurance added.");

            //SHIPMENT
            boolean priority = false;
            valid = false;
            do {
                try {
                    System.out.println("Would you like to pay for priority shipping to ensure your package reaches the destination quickly? (y/n): ");
                    priority = ValidateInput.validateYesNo(input.nextLine());
                    valid = true;
                } catch (InvalidInputException e) {
                    System.out.println("Please enter a valid input (y/n)");
                }
            } while (!valid);
            if (priority) {
                System.out.println("Priority Shipping added.");
            }

            Shipment shipment = new Shipment(sender, recipient, shippingPackage, getInsurancePlans().get(insuranceType), priority);
            System.out.println("The final price for this shipment comes out to $" + Math.round(shipment.getPrice() * 100.0) / 100.0);
            boolean shipmentFinalized = false;
            valid = false;
            do {
                try {
                    System.out.println("Would you like to finalize this shipment? (y/n)");
                    shipmentFinalized = ValidateInput.validateYesNo(input.nextLine());
                    valid = true;
                } catch (InvalidInputException e) {
                    System.out.println("Please enter a valid input (y/n)");
                }
            } while (!valid);
            if (shipmentFinalized) {
                sender.addOrder(shipment);
                recipient.recievePackage(shipment.getPackage());
                System.out.println("Package Sent!");
            }

            System.out.println(shipment);
            System.out.println("Exiting Program...");
        } catch (UnloadedDataException e) {
            System.out.println(e.getMessage() + ", existing program...");
            throw new RuntimeException(e);
        }
    }
}
