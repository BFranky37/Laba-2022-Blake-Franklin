import java.util.Objects;

public class Person {
    
    private String firstName = "Gordon";
    private String lastName = "Ramsay";
    private AddressLocation address = new AddressLocation();

    public Person() {
    }
    public Person(String fname, String lname, AddressLocation fulladdress) {
        firstName = fname;
        lastName = lname;
        address = fulladdress;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\nAddress: " + address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Person per = (Person) obj;

        return (this.firstName == per.firstName &&
                this.lastName == per.lastName &&
                this.address == per.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address);
    }
}
