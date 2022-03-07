package avms.userProfile.controller;

import avms.Main;
import avms.databaseHandler.AVMS_Database;
import avms.entity.Address;
import avms.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditProfileController {

    @FXML
    Label userId;
    @FXML
    PasswordField newPassword;
    @FXML
    PasswordField cpassword;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField age;

    @FXML
    TextField addressLine;
    @FXML
    TextField city;
    @FXML
    TextField state;
    @FXML
    TextField country;
    @FXML
    TextField zipcode;

    @FXML
    TextField emailId;
    @FXML
    TextField phoneNumber;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    RadioButton other;

    @FXML
    Button editBtn;


    public User loadEditedUserProfile(){
        String password = Main.userData.getLoginPassword();
        if(!newPassword.getText().isEmpty()){
            password = newPassword.getText();
            if(!password.equals(cpassword.getText())){
                return null;
            }
        }
        return new User(userId.getText(), password, firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()), Main.userData.getUserType(), addressLine.getText(), city.getText(), state.getText(), country.getText(), zipcode.getText(),emailId.getText(), phoneNumber.getText(), getGender());
    }

    public char getGender(){
        char gender = 'M';
        if(female.isSelected()){
            gender = 'F';
        }
        else if(other.isSelected()){
            gender = 'O';
        }
        return gender;
    }

    @FXML
    public void initialize(){
        User userProfile = Main.userData;
        userId.setText(userProfile.getUserID());
        newPassword.setPromptText("Create new password");
        cpassword.setPromptText("Verify new password");
        firstName.setText(userProfile.getFirstName());
        lastName.setText(userProfile.getLastName());
        age.setText(userProfile.getAge()+"");
        Address userAddress = userProfile.getAddress();
        addressLine.setText(userAddress.getAddressLine());
        city.setText(userAddress.getCity());
        state.setText(userAddress.getState());
        country.setText(userAddress.getCountry());
        zipcode.setText(userAddress.getZipcode());
        emailId.setText(userProfile.getEmailID());
        phoneNumber.setText(userProfile.getPhoneNumber());
        ToggleGroup gender = new ToggleGroup();
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);
        other.setToggleGroup(gender);

        male.setSelected(true);
        if(userProfile.getGender() == 'F'){
            female.setSelected(true);
        }
        else{
            other.setSelected(true);
        }
    }

    @FXML
    public void verifyPassword(ActionEvent actionEvent){
        Stage verifyWindow = new Stage();

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Verify your password...");

        Button okBtn = new Button("Ok");
        okBtn.setOnAction(e->{
            verifyWindow.hide();
            if(!passwordField.getText().equals(Main.userData.getLoginPassword())){
                alert("Incorrect Password :(", Alert.AlertType.ERROR);
            }
            else{
                edit();
            }
        });
        VBox vBox = new VBox(new Label("Enter your password:"), passwordField, okBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        verifyWindow.setTitle("Verify to continue");
        verifyWindow.setScene(new Scene(vBox));
        verifyWindow.show();
    }


    public void edit(){
        AVMS_Database database = null;
        try{
            database = new AVMS_Database();
            User newUserProfile = loadEditedUserProfile();
            if(newUserProfile!=null){
                if(database.updateUser(newUserProfile)){
                    alert("Profile Edited Successfully!", Alert.AlertType.INFORMATION);
                    Main.userData = newUserProfile;
                }
                else{
                    throw new SQLException();
                }
            }
            else{
                alert("New Password not verified :(", Alert.AlertType.ERROR);
            }
        }catch (SQLException|ClassNotFoundException exception){
            alert("Unable to edit!", Alert.AlertType.ERROR);
        }catch (NumberFormatException numberFormatException){
            alert("Invalid age!", Alert.AlertType.ERROR);
        }
        finally {
            if(database!=null){
                try{
                    database.close();
                }
                catch (SQLException sqlException){}
            }
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alertPopup = new Alert(alertType);
        alertPopup.setContentText(msg);
        alertPopup.show();
    }
}
