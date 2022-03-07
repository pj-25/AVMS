package avms.admin.controller;

import avms.Main;
import avms.databaseHandler.AVMS_Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;


import java.sql.SQLException;
import java.time.LocalTime;

public class RegisterParkingServiceController {
    @FXML
    TextField psName;
    @FXML
    TextField totalLots;
    @FXML
    Spinner<Integer> openHour;
    @FXML
    Spinner<Integer> openMinute;
    @FXML
    Spinner<Integer> closeHour;
    @FXML
    Spinner<Integer> closeMinute;
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
    TextField price;

    @FXML
    public void register(ActionEvent actionEvent){
        try{
            AVMS_Database database = new AVMS_Database();
            int newSlots =Integer.parseInt(totalLots.getText());
            int psID = database.addParkingService(psName.getText(), Main.userData.getUserID(), LocalTime.of(openHour.getValue(),openMinute.getValue()), LocalTime.of(closeHour.getValue(), closeMinute.getValue()), newSlots, newSlots,'O', addressLine.getText(), city.getText(), state.getText(), country.getText(),zipcode.getText(), Float.parseFloat(price.getText()));
            if(psID!=-1){
                MainController.parkingServiceData.getParkingServices().put(psID, database.getParkingServiceById(psID));
                alert("Parking service registered successfully! ID:"+psID, Alert.AlertType.INFORMATION);
            }
            else{
                throw new SQLException();
            }
        }
        catch (ClassNotFoundException e){
            System.err.println(e);
        }catch (SQLException sqlException){
            System.err.println(sqlException.getMessage());
            alert("Unable to register! Invalid Data :(", Alert.AlertType.ERROR);
        }
    }


    private void alert(String msg, Alert.AlertType alertType){
        Alert alertPopup = new Alert(alertType);
        alertPopup.setContentText(msg);
        alertPopup.show();
    }
}
