package avms.admin.controller;

import avms.admin.ParkingLog;
import avms.databaseHandler.AVMS_Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class ViewParkingLogController {
    @FXML
    TableView<ParkingLog> parkingLogTableView;
    @FXML
    TableColumn<ParkingLog, Integer> column1;
    @FXML
    TableColumn<ParkingLog, Integer> column2;
    @FXML
    TableColumn<ParkingLog, Integer> column3;
    @FXML
    TableColumn<ParkingLog, Integer> column4;
    @FXML
    TableColumn<ParkingLog, String> column5;
    @FXML
    TableColumn<ParkingLog, String> column6;
    @FXML
    TableColumn<ParkingLog, String> column7;
    @FXML
    TableColumn<ParkingLog, LocalDateTime> column8;

    @FXML
    Button viewBtn;

    @FXML
    ChoiceBox psId;

    @FXML
    public void initialize(){
        column1.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        column2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        column3.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        column4.setCellValueFactory(new PropertyValueFactory<>("slotId"));
        column5.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        column6.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        column7.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        column8.setCellValueFactory(new PropertyValueFactory<>("bookedOn"));

        Set<Integer> psIds = MainController.parkingServiceData.getParkingServices().keySet();
        if(psIds.isEmpty()){
            psId.getItems().add("No data found!");
            viewBtn.setDisable(true);
        }{
            psId.getItems().addAll(psIds);
        }

    }

    @FXML
    public void viewParkingLog(ActionEvent actionEvent){
        AVMS_Database database = null;
        try{
            database = new AVMS_Database();
            ArrayList<ParkingLog> parkingLogs = database.getAllParkingLogFor((Integer)psId.getValue());
            if(parkingLogs!=null){
                parkingLogTableView.getItems().clear();
                parkingLogTableView.getItems().addAll(parkingLogs);
            }
            else{
                alert("No parking data found!", Alert.AlertType.ERROR);
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException);
            alert("Unable to connect to database", Alert.AlertType.ERROR);
        }
        catch (ClassNotFoundException exception){
        }
        finally {
            if(database!=null){
                try{
                    database.close();
                }
                catch (SQLException e){
                }
            }
        }
    }

    @FXML
    public void alert(String msg, Alert.AlertType alertType){
        Alert alertPopup = new Alert(alertType);
        alertPopup.setContentText(msg);
        alertPopup.show();
    }
}
