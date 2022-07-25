public class DeliveryMain {
    public static void main(String[] args) {
        
        CardboardBox box1 = new CardboardBox(10, 10, 5);
        CardboardBox box2 = new CardboardBox(10, 10, 10);

        AddressLocation address1 = new AddressLocation();
        AddressLocation address2 = new AddressLocation("111 Main St", "Bigtown", "New York", 11112, "USA");

        Person sender = new Person("Bob", "Miller", address1);
        Person reciever = new Person("Steven", "Jackson", address2);

        System.out.println("Box1 dimesnsions - " + box1);
        System.out.println("Are Box1 and Box2 equal: " + (box1 == box2) + "\n\n");

        System.out.println("address1 is - " + address1);
        System.out.println("Are address1 and address2 equal: " + (address1 == address2));
    }
}
