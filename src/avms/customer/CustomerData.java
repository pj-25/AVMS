package avms.customer;

import avms.databaseHandler.AVMS_Database;
import avms.entity.BookingLog;
import avms.entity.User;
import avms.entity.Vehicle;

import java.sql.SQLException;
import java.util.HashMap;

public class CustomerData {
    private User user=null;
    private HashMap<Integer, Vehicle> savedVehicles = new HashMap<>();
    private BookingLog currentBooking=null;
    private HashMap<Integer, BookingLog> bookingLogHistory = new HashMap<>();

    public CustomerData(User user) {
        this.user = user;
        loadAllData();
    }

    public User getUser() {
        return user;
    }

    public void loadAllVehicles(){
        try {
            AVMS_Database database = new AVMS_Database();
            savedVehicles = database.getAllVehiclesOf(user.getUserID());
            database.close();
        }catch (SQLException|ClassNotFoundException e){
            System.err.println(e);
        }
    }

    public void loadAllBookingHistory(){
        try{
            AVMS_Database databaseConnection = new AVMS_Database();
            bookingLogHistory = databaseConnection.getAllBookingLogFor(user.getUserID());
            databaseConnection.close();
        }catch (SQLException | ClassNotFoundException e){
            System.err.println(e);
        }
    }

    public void loadCurrentBooking(){
        try{
            AVMS_Database AVMSDatabase = new AVMS_Database();
            currentBooking = AVMSDatabase.getCurrentBookingLogFor(user.getUserID());
            AVMSDatabase.close();
        }
        catch (SQLException|ClassNotFoundException e){
            System.err.println(e);
        }
    }

    public boolean loadAllData(){
        if(user!=null){
            loadAllVehicles();
            loadCurrentBooking();
            loadAllBookingHistory();
        return true;
        }
        return false;
    }

    public void setUser(User user) {
        this.user = user;
        loadAllData();
    }

    public HashMap<Integer, Vehicle> getSavedVehicles() {
        return savedVehicles;
    }

    public void setSavedVehicles(HashMap<Integer, Vehicle> savedVehicles) {
        this.savedVehicles = savedVehicles;
    }

    public BookingLog getCurrentBooking() {
        return currentBooking;
    }

    public void setCurrentBooking(BookingLog currentBooking) {
        this.currentBooking = currentBooking;
    }
}
