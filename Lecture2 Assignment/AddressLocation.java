import java.util.Objects;

public class AddressLocation {
    
    private String streetAddress = "123 Home St";
    private String city = "Hometown";
    private String state = "New York";
    private int zipcode = 11111;
    private String country = "USA";

    public AddressLocation() {   
    }
    public AddressLocation(String saddress, String c, String s, int zip, String nation) {  
        streetAddress = saddress;
        city = c;
        state = s;
        zipcode = zip;
        country = nation;
    }

    @Override
    public String toString() {
        return streetAddress + ", " + city + ", " + state + " " + zipcode + ", " + country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        AddressLocation address = (AddressLocation) obj;

        return (this.streetAddress == address.streetAddress &&
                this.city == address.city &&
                this.state == address.state &&
                this.zipcode == address.zipcode && 
                this.country == address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, state, zipcode, country);
    }

}
