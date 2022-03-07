package avms.entity;

import avms.databaseHandler.InvalidDataException;
import avms.databaseHandler.AVMS_Database;

import java.sql.SQLException;

public class User {
    private String userID;
    private String loginPassword;
    private String firstName;
    private String lastName;
    private int age;
    private String userType;
    private Address address;
    private String emailID;
    private String phoneNumber;
    private char gender;


    public User(String userID, String loginPassword, String firstName, String lastName, int age, String userType, String addressLine, String city, String state, String country, String zipcode, String emailID, String phoneNumber, char gender){
        this(userID, loginPassword, firstName, lastName, age, userType, new Address(addressLine, city, state, country, zipcode), emailID, phoneNumber, gender);
    }

    public User(String userID, String loginPassword, String firstName, String lastName, int age, String userType, Address address, String emailID, String phoneNumber, char gender) {
        this.userID = userID;
        this.loginPassword = loginPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userType = userType;
        this.address = address;
        this.emailID = emailID;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public boolean writeToDatabase() throws InvalidDataException{
        AVMS_Database databaseHandler = null;
        try{
            databaseHandler = new AVMS_Database();
            boolean status = (databaseHandler.addUser(userID,loginPassword, firstName, lastName, age,userType, address.getAddressLine(), address.getCity(), address.getState(), address.getCountry(), address.getZipcode(), emailID, phoneNumber, gender));
            databaseHandler.close();
            return status;
        }
        catch (SQLException e) {
            System.err.println(e);
            throw new InvalidDataException("Invalid address");
        }
        catch (ClassNotFoundException e){
            System.err.println(e);
            throw new InvalidDataException("database connection error");
        }
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
