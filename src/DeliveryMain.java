import com.DeliverySystem.orders.Location;
import com.DeliverySystem.orders.Box;
import com.DeliverySystem.people.Person;

public class DeliveryMain {
    public static void main(String[] args) {
        
        Box box1 = new Box(1, 1, .5);
        Box box2 = new Box(1, 1, 1);

        Location address1 = new Location();
        Location address2 = new Location("111 Main St", "Bigtown", "New York", 11112, "USA");

        Person sender = new Person("Bob Miller", "1112223344", address1);
        Person reciever = new Person("Steven Jackson", "1112225566", address2);

        System.out.println("Box1 dimesnsions - " + box1);
        System.out.println("Are Box1 and Box2 equal: " + (box1 == box2) + "\n\n");

        System.out.println("address1 is - " + address1);
        System.out.println("Are address1 and address2 equal: " + (address1 == address2));
    }
}
