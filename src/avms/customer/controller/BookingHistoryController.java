package avms.customer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import avms.databaseHandler.AVMS_Database;
import avms.entity.BookingLog;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public class BookingHistoryController {
    @FXML
    TableView<BookingLog> bookingHistory;
    @FXML
    TableColumn<BookingLog, String> column1;
    @FXML
    TableColumn<BookingLog, String> column2;
    @FXML
    TableColumn<BookingLog, String> column3;
    @FXML
    TableColumn<BookingLog, String> column4;
    @FXML
    TableColumn<BookingLog, LocalDateTime> column5;
    @FXML
    TableColumn<BookingLog, LocalTime> column6;



    @FXML
    public void initialize(){
        column1.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        column3.setCellValueFactory(new PropertyValueFactory<>("parkingServiceID"));
        column4.setCellValueFactory(new PropertyValueFactory<>("slotID"));
        column5.setCellValueFactory(new PropertyValueFactory<>("bookedOn"));
        column6.setCellValueFactory(new PropertyValueFactory<>("reservedTime"));

        try{
            AVMS_Database database = new AVMS_Database();
            HashMap<Integer, BookingLog> bookingLogHashMap = database.getAllBookingLogFor(MainController.customerData.getUser().getUserID());
            if(bookingLogHashMap!=null){
                for(BookingLog bookingLog:bookingLogHashMap.values()){
                    bookingHistory.getItems().add(bookingLog);
                }
            }
        }
        catch (SQLException|ClassNotFoundException e){
            System.out.println(e);
        }
    }
}
