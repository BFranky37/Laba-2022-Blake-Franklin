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
        System.out.println("Now we need information about the package you are sending.");
         do {
            System.out.println("Please enter the length, width, and height of your package in inches, one by one: ");

            l = input.nextDouble();
            w = input.nextDouble();
            h = input.nextDouble();

            if (l*w*h > 500000) {
                System.out.println("Sorry, we do not accept packages that big. Try a smaller package.");
            }
        } while (l*w*h > 500000);
        
        Box box = new Box(l, w, h);

        System.out.println("What is the weight of your package in pounds: ");
        double weight = input.nextDouble();
        System.out.println("What is the value of the item you are shipping in dollars: ");
        double value = input.nextDouble();
        input.nextLine();
        System.out.println("Is the item you are shipping fragile? (y/n): ");
        char fragility = input.next().charAt(0);
        if (fragility == 'y' || fragility == 'Y') {
            System.out.println("Noting package as fragile...");
        }
        input.nextLine();

        Package shippingPackage = new Package(box, weight, value, (fragility == 'y' || fragility == 'Y'));

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
        if (shippingPackage.getValue() > 200)
            insuranceType = 3;
        else if (shippingPackage.getFragility())
            insuranceType = 2;
        else insuranceType = 1;
        System.out.println("Would you like to purchase insurance for this package to be reimbursed in the case it is lost or damaged? " +
                            "\nThe price for insurance for your item would be " +
                            "$" + Math.round(getInsurancePlans().get(insuranceType).calculatePrice(value) * 100.0) / 100.0 + " (y/n):");
        if (input.next().charAt(0) == 'n' || input.next().charAt(0) == 'N') {
            insuranceType = 0;
        }
        else System.out.println("Insurance added.");
        input.nextLine();

        //SHIPMENT
        System.out.println("Would you like to pay for priority shipping to ensure your package reaches the destination quickly? (y/n): ");
        char priority = input.next().charAt(0);
        if (priority == 'y' || priority == 'Y') {
            System.out.println("Priority Shipping added.");
        }
        Shipment shipment = new Shipment(sender, recipient, shippingPackage, getInsurancePlans().get(insuranceType), (priority == 'y' || priority == 'Y'));

        System.out.println("The final price for this shipment comes out to $" + Math.round(shipment.getPrice() * 100.0) / 100.0);
        System.out.println("Would you like to finalize this shipment? (y/n)");
        if (input.next().charAt(0) == 'y' || input.next().charAt(0) == 'Y') {
            sender.addOrder(shipment);
            recipient.recievePackage(shipment.getPackage());
            System.out.println("Package Sent!");
        }
        input.nextLine();

        System.out.println(shipment);

        System.out.println("Exiting Program...");
    }
}
