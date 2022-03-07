package avms.entity;


import avms.databaseHandler.InvalidDataException;
import avms.databaseHandler.AVMS_Database;

import java.sql.SQLException;

public class Address {
    private int addressID = -1;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    public Address(int addressID, String addressLine, String city, String state, String country, String zipcode) {
        this.addressID = addressID;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    public Address(String addressLine, String city, String state, String country, String zipcode) {
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    private int writeToDatabase() throws InvalidDataException {
        AVMS_Database databaseHandler = null;
        try{
            databaseHandler = new AVMS_Database();
            int addressID = databaseHandler.addAddress(addressLine, city, state, country,zipcode);
            databaseHandler.close();
            return addressID;
        }
        catch (SQLException e) {
            System.err.println(e);
            throw new InvalidDataException("Invalid address");
        }
        catch (ClassNotFoundException e){
            System.err.println(e);
            throw new InvalidDataException("database connection class error");
        }
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
