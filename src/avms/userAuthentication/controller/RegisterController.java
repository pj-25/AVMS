package avms.userAuthentication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import avms.databaseHandler.InvalidDataException;
import avms.entity.User;

import java.io.IOException;

public class RegisterController {
    @FXML
    StackPane mainPane;
    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    PasswordField cpassword;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField age;
    @FXML
    ChoiceBox userType;
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

    private ToggleGroup gender = new ToggleGroup();

    @FXML
    public void initialize(){
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);
        other.setToggleGroup(gender);
        userType.getItems().addAll("PSP", "Customer");
    }

    @FXML
    public void register(){
        try{
            if(password.getText().equals(cpassword.getText())){
                    char genderChoice = 'M';
                    if(female.isSelected()){
                        genderChoice = 'F';
                    }
                    else if(other.isSelected()){
                        genderChoice = 'O';
                    }
                    User user = new User(userId.getText(), password.getText(), firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()), (String)userType.getValue(), addressLine.getText(), city.getText(), state.getText(), country.getText(), zipcode.getText(), emailId.getText(), phoneNumber.getText(), genderChoice);
                    user.writeToDatabase();
                    alert("Registered Successfully!", Alert.AlertType.INFORMATION);

            }
            else{
                showError("Password failed to verify:(");
            }
        }
        catch (NumberFormatException numberFormatException) {
            showError("Invalid age!");
        }
        catch (NullPointerException npe){
            showError("All fields are required*");
        }
        catch (InvalidDataException e){
            showError("Error: "+ e.getMessage());
        }
    }

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {
        jumpToLogin();
    }

    public void jumpToLogin() throws IOException {
        mainPane.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("../../userAuthentication/res/layouts/login.fxml"));
        mainPane.getChildren().addAll(root);
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }

    private void showError(String errMsg){
        alert(errMsg, Alert.AlertType.ERROR);
    }

}
