package avms.customer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import avms.databaseHandler.AVMS_Database;
import avms.entity.BookingLog;

import java.sql.SQLException;
import java.time.LocalTime;

public class CurrentBookingController {

    @FXML
    VBox bookingData;
    @FXML
    Label bookingID;
    @FXML
    Label vehicleID;
    @FXML
    Label parkingSpaceID;
    @FXML
    Label slotID;
    @FXML
    Label bookedOn;
    @FXML
    Label reservedTill;


    public void initialize(){
        BookingLog bookingLog = MainController.customerData.getCurrentBooking();
        if(bookingLog!=null){
            bookingID.setText(bookingLog.getBookingID()+"");
            vehicleID.setText(bookingLog.getVehicleID()+"");
            parkingSpaceID.setText(bookingLog.getParkingServiceID()+"");
            slotID.setText(bookingLog.getSlotID());
            bookedOn.setText(bookingLog.getBookedOn().toString());
            LocalTime reservedTime=bookingLog.getReservedTime();
            if(reservedTime!=null)
                reservedTill.setText(reservedTime.toString());
            else
                reservedTill.setText("null");
        }
        else{
            clearBookingData();
        }
    }

    public void clearBookingData(){
        bookingData.getChildren().clear();
        Label noData = new Label("No Current Booking!");
        noData.setFont(Font.font("Monospace", 25));
        bookingData.setAlignment(Pos.CENTER_LEFT);
        bookingData.getChildren().add(noData);
    }

    public void checkout(ActionEvent actionEvent) {
        try{
            AVMS_Database database = new AVMS_Database();
            int checkoutID = database.addCheckoutLog(Integer.parseInt(bookingID.getText()));
            alert("Checkout successfully! checkout ID:"+checkoutID, Alert.AlertType.INFORMATION);
            MainController.customerData.setCurrentBooking(null);
            database.close();
            clearBookingData();
        }catch (SQLException|ClassNotFoundException e){
            System.out.println(e);
            alert("Error: Unable to checkout :(", Alert.AlertType.ERROR);
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }

}
