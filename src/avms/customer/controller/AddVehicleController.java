package avms.customer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import avms.databaseHandler.AVMS_Database;
import avms.Main;

import java.sql.SQLException;

public class AddVehicleController {
    @FXML
    TextField vehicleType;

    @FXML
    TextField vehicleNumber;

    @FXML
    TextField modelName;

    @FXML
    Button submitBtn;

    @FXML
    public void submit(ActionEvent actionEvent){
        AVMS_Database database=null;
        try{
            database = new AVMS_Database();
            System.out.println(MainController.customerData);
            int vehicleId = database.addVehicle(Main.userData.getUserID(), vehicleType.getText(), vehicleNumber.getText(), modelName.getText());
            if(vehicleId!=-1){
                MainController.customerData.getSavedVehicles().put(vehicleId, database.getVehicleById(vehicleId));
                alert("Vehicle added successfully! :)", Alert.AlertType.INFORMATION);
            }
            else
                throw new SQLException();
        }catch (SQLException e){
            System.out.println(e);
            alert("Invalid vehicle Data :(", Alert.AlertType.ERROR);
        }catch (ClassNotFoundException e){
            System.out.println(e);
        }
        finally {
            try {
                database.close();
            }catch (SQLException e){
            }
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }

}
