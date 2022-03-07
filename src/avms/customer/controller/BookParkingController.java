package avms.customer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import avms.databaseHandler.AVMS_Database;
import avms.entity.ParkingService;
import avms.entity.Vehicle;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;

public class BookParkingController {
    @FXML
    TextField psSearchValue;

    @FXML
    ChoiceBox vehicleID;
    @FXML
    Button bookBtn;
    @FXML
    TextField psID;
    @FXML
    TableView<ParkingService> psData;
    @FXML
    TableColumn<ParkingService, String> column1;
    @FXML
    TableColumn<ParkingService, String> column2;
    @FXML
    TableColumn<ParkingService, Integer> column3;
    @FXML
    TableColumn<ParkingService, LocalTime> column4;
    @FXML
    TableColumn<ParkingService, LocalTime> column5;
    @FXML
    TableColumn<ParkingService, Integer> column6;
    @FXML
    TableColumn<ParkingService, Float> column7;

    @FXML
    Spinner<Integer> hour;
    @FXML
    Spinner<Integer> minute;


    public void initialize(){
            column1.setCellValueFactory(new PropertyValueFactory<>("psID"));
            column2.setCellValueFactory(new PropertyValueFactory<>("name"));
            column3.setCellValueFactory(new PropertyValueFactory<>("totalParkingSlots"));
            column4.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
            column5.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
            column6.setCellValueFactory(new PropertyValueFactory<>("freeSlots"));
            column7.setCellValueFactory(new PropertyValueFactory<>("price"));

        HashMap<Integer, Vehicle> myVehicles = MainController.customerData.getSavedVehicles();
        if(myVehicles!=null){
            vehicleID.getItems().addAll(myVehicles.keySet());
        }
        else{
            vehicleID.setValue("No saved Vehicles");
            vehicleID.setDisable(true);
            bookBtn.setDisable(true);
        }


    }

    @FXML
    public void searchParkingSpace(ActionEvent actionEvent){
        if(!psSearchValue.getText().isEmpty()){
            AVMS_Database avms_database;
            try{
                avms_database  = new AVMS_Database();
                HashMap<Integer, ParkingService> parkingServiceHashMap = avms_database.getAllParkingServices(psSearchValue.getText());
                psData.getItems().addAll(parkingServiceHashMap.values());
            }catch (SQLException|ClassNotFoundException e){
                System.err.println(e);
                System.out.println("No Data Found!");
            }
        }
    }

    @FXML
    public void bookParkingSpace(){
        AVMS_Database AVMSDatabase = null;
        try{
            AVMSDatabase = new AVMS_Database();
            int bookingID = AVMSDatabase.addBookingLog((Integer) vehicleID.getValue(), Integer.parseInt(psID.getText()), LocalTime.of(hour.getValue(), minute.getValue()));
            if(bookingID!=-1){
                alert("Booked successfully: booking ID-"+bookingID+"\n(*Jump to current booking for further details)", Alert.AlertType.INFORMATION);
                MainController.customerData.setCurrentBooking(AVMSDatabase.getBookingLogById(bookingID));
            }
            else{
                alert("Unable to book :(", Alert.AlertType.ERROR);
            }
        }
        catch (SQLException|ClassNotFoundException e){
            System.out.println(e);
            alert("Database error", Alert.AlertType.ERROR);
        }
        finally {
            if(AVMSDatabase!=null){
                try {
                    AVMSDatabase.close();
                } catch (SQLException sqlException) {
                }
            }
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }
}
