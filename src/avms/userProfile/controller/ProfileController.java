package avms.userProfile.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import avms.entity.User;
import avms.Main;


public class ProfileController {
    @FXML
    Label userId;
    @FXML
    Label firstName;
    @FXML
    Label lastName;
    @FXML
    Label age;
    @FXML
    Label userType;
    @FXML
    Label addressLine;
    @FXML
    Label city;
    @FXML
    Label state;
    @FXML
    Label country;
    @FXML
    Label zipcode;
    @FXML
    Label emailId;
    @FXML
    Label phoneNumber;
    @FXML
    Label gender;

    @FXML
    protected void initialize(){
        User user = Main.userData;
        userId.setText(user.getUserID());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        age.setText(user.getAge()+"");
        userType.setText(user.getUserType());
        addressLine.setText(user.getAddress().getAddressLine());
        city.setText(user.getAddress().getCity());
        state.setText(user.getAddress().getState());
        country.setText(user.getAddress().getCountry());
        zipcode.setText(user.getAddress().getZipcode());
        emailId.setText(user.getEmailID());
        phoneNumber.setText(user.getPhoneNumber());
        if(user.getGender() == 'M'){
            gender.setText("Male");
        }
        else if(user.getGender() == 'F'){
            gender.setText("Female");
        }
        else {
            gender.setText("Other");
        }
    }

}
