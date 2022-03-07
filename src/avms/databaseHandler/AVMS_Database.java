package avms.databaseHandler;

import avms.admin.ParkingLog;
import avms.entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class AVMS_Database extends avms.databaseHandler.DatabaseHandler{
    public AVMS_Database() throws ClassNotFoundException, SQLException {}

    private String getPassword(String userID) throws SQLException{
        String query = "select login_password from user where user_id=?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userID);
        ResultSet passwordInfo = preparedStatement.executeQuery();
        if(passwordInfo!=null && passwordInfo.next()){
            return passwordInfo.getString(1);
        }
        return null;
    }

    public boolean isValidUser(String userID, String password) throws SQLException{
        String correctPassword = getPassword(userID);
        System.out.println(correctPassword);
        return (correctPassword!=null && correctPassword.equals(password));
    }

    public int executeUpdateOnAddress(String query, String addressLine, String city, String state, String country, String zipcode) throws SQLException{
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, addressLine);
        preparedStatement.setString(2, city);
        preparedStatement.setString(3, state);
        preparedStatement.setString(4, country);
        preparedStatement.setString(5, zipcode);
        preparedStatement.executeUpdate();
        ResultSet addressID_Info = preparedStatement.getGeneratedKeys();
        if(addressID_Info!=null && addressID_Info.next()){
            return addressID_Info.getInt(1);
        }
        return -1;
    }

    public int addAddress(String addressLine, String city, String state, String country, String zipcode) throws SQLException{
        String query = "Insert into address(address_line, city, state, country, zipcode) values(?,?,?,?,?)";
        return executeUpdateOnAddress(query, addressLine, city, state, country, zipcode);
    }

    public boolean updateAddress(Address address) throws SQLException{
        if(address!=null){
            String query = "update address set address_line=?, city=?, state=?, country=?, zipcode=? where address_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address.getAddressLine());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getState());
            preparedStatement.setString(4, address.getCountry());
            preparedStatement.setString(5, address.getZipcode());
            preparedStatement.setInt(6, address.getAddressID());
            return preparedStatement.executeUpdate()!=0;
        }
        return false;
    }

    public boolean updateAddress(int addressID, boolean []isUpdated, String... values) throws SQLException{
        String []columns={"address_line", "city", "state", "country", "zipcode"};
        String query = "update address set ";
        for(int i=0;i<columns.length;i++){
            if(isUpdated[i])
                query += columns[i]+"='" + values[i] +"',";
        }
        query = query.substring(0, query.length()-1);
        query += " where address_id="+addressID;
        statement = connection.createStatement();
        return statement.executeUpdate(query)!=0;
    }

    public boolean addUser(String userID,String login_password,String first_name, String last_name, int age,String user_type, String addressLine, String city, String state, String country, String zipcode, String email_id, String phone_number, char gender) throws SQLException {
        int addressID = addAddress(addressLine, city, state, country, zipcode);
        if(addressID!=-1){
            String query = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, login_password);
            preparedStatement.setString(3, first_name);
            preparedStatement.setString(4, last_name);
            preparedStatement.setInt(5, age);
            preparedStatement.setString(6, user_type);
            preparedStatement.setInt(7, addressID);
            preparedStatement.setString(8, email_id);
            preparedStatement.setString(9, phone_number);
            preparedStatement.setString(10, gender+"");
            return preparedStatement.executeUpdate() != 0;
        }
        return false;
    }

    public boolean updateUser(User user) throws SQLException {
        if(user != null){
            updateAddress(user.getAddress());
            String query = "update user set first_name=?, last_name=?, age=?, email_id=?, phone_number=?, gender=?, login_password=? where user_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getEmailID());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getGender()+"");
            preparedStatement.setString(7, user.getLoginPassword());
            preparedStatement.setString(8, user.getUserID());
            return preparedStatement.executeUpdate()!=0;
        }
        return false;
    }

    public boolean isValidUserID(String userID) throws SQLException{
        String query = "Select * from user where user_id ='"+userID+"'";
        return statement.executeUpdate(query) != 1;
    }

    public int addParkingService(String name, String manager_id, LocalTime openingTime, LocalTime closingTime, int totalParkingSlots, int freeSlots, char status, String addressLine, String city, String state, String country, String zipcode, float price) throws SQLException{
        int addressID = addAddress(addressLine, city,state, country, zipcode);
        if(addressID!=-1){
            String query = "insert into parking_service(name, manager_id, opening_time,closing_time,total_parking_slots, free_slots, status, address_id, price) values(?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, manager_id);
            preparedStatement.setTime(3, Time.valueOf(openingTime));
            preparedStatement.setTime(4, Time.valueOf(closingTime));
            preparedStatement.setInt(5, totalParkingSlots);
            preparedStatement.setInt(6, freeSlots);
            preparedStatement.setString(7, status+"");
            preparedStatement.setInt(8, addressID);
            preparedStatement.setFloat(9, price);
            preparedStatement.executeUpdate();
            ResultSet psInfo = preparedStatement.getGeneratedKeys();
            if(psInfo!=null && psInfo.next()){
                int psId = psInfo.getInt(1);
                addParkingSlots(psId, totalParkingSlots);
                return psId;
            }
        }
        return -1;
    }

    public void addParkingSlots(int psId, int total_slots) throws SQLException{
        addParkingSlots(psId, 1, total_slots);
    }

    public boolean removeParkingSlots(int psID, int removeSlots) throws SQLException{
        String query = "delete from parking_slots where ps_id=? and booking_id IS NULL Limit ?";
        preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1, psID);
        preparedStatement.setInt(2, removeSlots);
        return preparedStatement.executeUpdate()!=0;
    }

    public boolean removeAllParkingSlots(int psID) throws SQLException{
        String query = "delete from parking_slots where ps_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, psID);
        return preparedStatement.executeUpdate()!=0;
    }

    public int getFreeSlot(int psID) throws SQLException{
        String query = "Select slot_id from parking_slots where ps_id=? and booking_id IS NULL LIMIT 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, psID);
        ResultSet parkingSlotsData = preparedStatement.executeQuery();
        if(parkingSlotsData!=null && parkingSlotsData.next()){
            return parkingSlotsData.getInt(1);
        }
        return -1;
    }

    public ArrayList<Integer> getAllFreeParkingSlots(int psID) throws SQLException{
        String query = "Select slot_id from parking_slots where ps_id=? and booking_id NOT null";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, psID);
        ResultSet parkingSlotsData = preparedStatement.executeQuery();
        if(parkingSlotsData!=null){
            ArrayList<Integer> parkingSlots = new ArrayList<>();
            while (parkingSlotsData.next()){
                parkingSlots.add(parkingSlotsData.getInt(1));
            }
            return parkingSlots;
        }
        return null;
    }

    public void addParkingSlots(int psID, int startingSlotId, int totalSlots) throws SQLException{
        String query = "Insert into parking_slots values(?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(2, psID);
        for(int i = startingSlotId; i<= totalSlots; i++){
            preparedStatement.setInt(1, i);
            preparedStatement.setString(3, null);
            preparedStatement.executeUpdate();
        }
    }


    public int addVehicle(String userID, String vehicleType, String vehicleNumber, String modelName) throws SQLException{
        String query = "insert into vehicle(user_id, vehicle_type,vehicle_number, model_name) values(?,?,?,?)";
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, userID);
        preparedStatement.setString(2, vehicleType);
        preparedStatement.setString(3, vehicleNumber);
        preparedStatement.setString(4, modelName);
        preparedStatement.executeUpdate();
        ResultSet vehicleInfo = preparedStatement.getGeneratedKeys();
        if(vehicleInfo!=null && vehicleInfo.next()){
            return vehicleInfo.getInt(1);
        }
        return -1;
    }

    public int addPayment(int checkoutID, float amount) throws SQLException{
        String query = "Insert into payment values(?,?)";
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, checkoutID);
        preparedStatement.setFloat(2, amount);
        preparedStatement.executeUpdate();
        ResultSet paymentInfo = preparedStatement.getGeneratedKeys();
        if(paymentInfo!=null && paymentInfo.next()){
            return paymentInfo.getInt(1);
        }
        return -1;
    }

    public int addBookingLog(int vehicleID, int psID, LocalTime reservedTill) throws SQLException{
        int freeSlotId = getFreeSlot(psID);
        if(freeSlotId!=-1){
            String query = "insert into booking_log(vehicle_id, ps_id, slot_id, reserved_till) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, vehicleID);
            preparedStatement.setInt(2, psID);
            preparedStatement.setString(3,freeSlotId+"");
            preparedStatement.setTime(4, Time.valueOf(reservedTill));
            preparedStatement.executeUpdate();
            ResultSet bookingInfo = preparedStatement.getGeneratedKeys();
            if(bookingInfo!=null && bookingInfo.next()){
                return bookingInfo.getInt(1);
            }
        }
        return -1;
    }


    public ArrayList<ParkingLog> readAllParkingLog(ResultSet parkingData) throws SQLException{
        if(parkingData!=null){
            ArrayList<ParkingLog> parkingLogs = new ArrayList<>();
            while(parkingData.next()){
                parkingLogs.add(new ParkingLog(parkingData.getInt(1), parkingData.getString(2), parkingData.getInt(3), parkingData.getInt(4), parkingData.getString(5)+" "+ parkingData.getString(6), parkingData.getString(7), parkingData.getString(8), parkingData.getTimestamp(9).toLocalDateTime()));
            }
            if(parkingLogs.size()>0){
                return parkingLogs;
            }
        }
        return null;
    }

    public ArrayList<ParkingLog> getAllParkingLogFor(int psID) throws SQLException{
        String query = "Select booking_id, vehicle.user_id, booking_log.vehicle_id, slot_id, first_name, last_name, email_id, phone_number, booked_on from booking_log, user,vehicle where booking_log.vehicle_id=vehicle.vehicle_id and vehicle.user_id=user.user_id and ps_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, psID);
        return readAllParkingLog(preparedStatement.executeQuery());
    }


    public Time getStartTime(int bookingID) throws SQLException{
        String query = "Select booked_on from booking_log where booking_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bookingID);
        ResultSet startTimeInfo = preparedStatement.executeQuery();
        if(startTimeInfo!=null && startTimeInfo.next()){
            return startTimeInfo.getTime(1);
        }
        return null;
    }

    public boolean updateTotalTime(int checkoutID) throws SQLException{
        String query = "update checkout_log set total_time = end_time - (select booked_on from booking_log where booking_id=checkout_log.booking_id) where checkout_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, checkoutID);
        return preparedStatement.executeUpdate()!=0;
    }

    public int addCheckoutLog(int booking_id) throws SQLException{
        String query = "Insert into checkout_log(booking_id) values(?)";
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, booking_id);
        preparedStatement.executeUpdate();
        ResultSet checkoutInfo = preparedStatement.getGeneratedKeys();
        if(checkoutInfo!=null && checkoutInfo.next()){
            int checkoutID = checkoutInfo.getInt(1);
            if(updateTotalTime(checkoutID))
                return checkoutID;
        }
        return -1;
    }

    public Address getAddressById(int addressId) throws SQLException{
        String query = "Select * from address where address_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, addressId);
        ResultSet addressData = preparedStatement.executeQuery();
        if(addressData!=null && addressData.next()){
            String addressLine = addressData.getString(2);
            String city = addressData.getString(3);
            String state = addressData.getString(4);
            String country = addressData.getString(5);
            String zipcode = addressData.getString(6);
            return new Address(addressId, addressLine, city, state, country, zipcode);
        }
        return null;
    }

    public User getUserById(String userId) throws SQLException{
        String query = "select * from user where user_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);
        ResultSet userData = preparedStatement.executeQuery();
        if(userData!=null && userData.next()){
            String loginPassword = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            int age = userData.getInt(5);
            String userType = userData.getString(6);
            int addressId = userData.getInt(7);
            String emailId = userData.getString(8);
            String phoneNumber = userData.getString(9);
            String gender = userData.getString(10);
            return new User(userId, loginPassword, firstName, lastName, age, userType, getAddressById(addressId),emailId, phoneNumber, gender.charAt(0));
        }
        return null;
    }


    public boolean updateParkingSpace(int psID, int newTotalSlots, int newFreeSlots) throws SQLException{
        String query = "update parking_service set total_parking_slots=?, free_slots=? WHERE ps_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, newTotalSlots);
        preparedStatement.setInt(2, newFreeSlots);
        preparedStatement.setInt(3, psID);
        return preparedStatement.executeUpdate()!=0;
    }

    public ParkingService getParkingServiceById(int psId) throws SQLException{
        String query = "select * from parking_service where ps_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, psId);
        ResultSet parkingServiceData = preparedStatement.executeQuery();
        if(parkingServiceData!=null && parkingServiceData.next()){
            String name = parkingServiceData.getString(2);
            String managerId = parkingServiceData.getString(3);
            LocalTime openingTime = parkingServiceData.getTime(4).toLocalTime();
            LocalTime closingTime = parkingServiceData.getTime(5).toLocalTime();
            int totalParkingSlots = parkingServiceData.getInt(6);
            int freeSlots = parkingServiceData.getInt(7);
            String status = parkingServiceData.getString(8);
            int addressId = parkingServiceData.getInt(9);
            float price = parkingServiceData.getFloat(10);
            return new ParkingService(psId, name, managerId, openingTime, closingTime, totalParkingSlots, freeSlots, status, addressId, price);
        }
        return null;
    }

    public Vehicle getVehicleById(int vehicleId) throws SQLException{
        String query = "select * from vehicle where vehicle_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, vehicleId);
        ResultSet vehicleData = preparedStatement.executeQuery();
        if(vehicleData!=null && vehicleData.next()){
            return readVehicle(vehicleData);
        }
        return null;
    }

    private Vehicle readVehicle(ResultSet vehicleData) throws SQLException{
        int vehicleId = vehicleData.getInt(1);
        String userId = vehicleData.getString(2);
        String vehicleType = vehicleData.getString(3);
        String vehicleNumber = vehicleData.getString(4);
        String modelName = vehicleData.getString(5);
        return new Vehicle(vehicleId, userId, vehicleType, vehicleNumber, modelName);
    }

    public BookingLog getBookingLogById(int bookingId) throws SQLException{
        String query = "select * from booking_log where booking_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bookingId);
        ResultSet bookingData = preparedStatement.executeQuery();
        if(bookingData!=null && bookingData.next()){
            return readBookingLog(bookingData);
        }
        return null;
    }

    private BookingLog readBookingLog(ResultSet bookingData) throws SQLException{
        int bookingId = bookingData.getInt(1);
        int vehicleId = bookingData.getInt(2);
        int psId = bookingData.getInt(3);
        String slotId = bookingData.getString(4);
        LocalDateTime bookedOn = bookingData.getTimestamp(5).toLocalDateTime();
        Time reservedTime = bookingData.getTime(6);
        LocalTime reservedTill = null;
        if(reservedTime!=null){
            reservedTill = reservedTime.toLocalTime();
        }
        return new BookingLog(bookingId,vehicleId, psId,slotId, bookedOn, reservedTill);
    }

    public CheckoutLog getCheckoutLogById(int checkoutLogId) throws SQLException{
        String query = "select * from checkout_log where checkout_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, checkoutLogId);
        ResultSet checkoutData = preparedStatement.executeQuery();
        if(checkoutData!=null && checkoutData.next()){
            int bookingId = checkoutData.getInt(2);
            LocalDateTime endTime = (LocalDateTime)checkoutData.getObject(3);
            LocalTime totalTime = checkoutData.getTime(4).toLocalTime();
            return new CheckoutLog(checkoutLogId, bookingId, endTime, totalTime);
        }
        return null;
    }

    public HashMap<Integer, Vehicle> getAllVehiclesOf(String userId) throws SQLException{
        String query = "Select * from vehicle where user_id='"+userId+"'";
        ResultSet vehiclesData = statement.executeQuery(query);
        if(vehiclesData!=null){
            HashMap<Integer, Vehicle> vehicleHashMap = new HashMap<>();
            while(vehiclesData.next()) {
                Vehicle vehicleRecord = readVehicle(vehiclesData);
                vehicleHashMap.put(vehicleRecord.getVehicleID(), vehicleRecord);
            }
            if(vehicleHashMap.size()>0){
                return vehicleHashMap;
            }
        }
        return null;
    }

    public BookingLog getCurrentBookingLogFor(String userId) throws SQLException{
        String query = "select b.booking_id, b.vehicle_id, b.ps_id, b.slot_id, b.booked_on, b.reserved_till from booking_log as b left join checkout_log as c on b.booking_id=c.booking_id, vehicle where b.vehicle_id = vehicle.vehicle_id and user_id='"+userId+"'";
        ResultSet bookingData = statement.executeQuery(query);
        if(bookingData!=null && bookingData.next()){
            return readBookingLog(bookingData);
        }
        return null;
    }

    public boolean removeParkingSpace(int psID) throws SQLException{
        if(removeAllParkingSlots(psID)){
            String query = "DELETE from parking_service where ps_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, psID);
            if(preparedStatement.executeUpdate()!=0){
                return true;
            }
        }
        return false;
    }


    public HashMap<Integer, ParkingService> getAllParkingServices(String psIdentifier) throws SQLException{
        String query = "Select * from parking_service, address where parking_service.address_id = address.address_id and ? IN (ps_id, name, manager_id, parking_service.address_id, address_line, city, state, country, zipcode)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, psIdentifier);
        return readAllParkingService(preparedStatement.executeQuery());
    }

    public HashMap<Integer, ParkingService> getAllParkingServicesByManagerId(String userId) throws SQLException{
        String query = "Select * from parking_service where manager_id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userId);
        ResultSet parkingServiceData = preparedStatement.executeQuery();
        return readAllParkingService(parkingServiceData);
    }

    public HashMap<Integer, ParkingService> readAllParkingService(ResultSet parkingServiceData) throws SQLException{
        if(parkingServiceData!=null){
            HashMap<Integer, ParkingService> parkingServiceHashMap = new HashMap<>();
            while (parkingServiceData.next()){
                ParkingService psData = readParkingService(parkingServiceData);
                parkingServiceHashMap.put(psData.getPsID(), psData);
            }
            return parkingServiceHashMap;
        }
        return null;
    }

    public ParkingService readParkingService(ResultSet resultSet) throws SQLException{
        return new ParkingService(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getTime(4).toLocalTime(), resultSet.getTime(5).toLocalTime(), resultSet.getInt(7), resultSet.getInt(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getFloat(10));
    }

    public HashMap<Integer, BookingLog> getAllBookingLogFor(String userID) throws SQLException{
        String query = "select booking_id, booking_log.vehicle_id, ps_id, slot_id, booked_on, reserved_till from booking_log, vehicle where booking_log.vehicle_id = vehicle.vehicle_id and user_id='"+userID+"'";
        ResultSet bookingData = statement.executeQuery(query);
        if(bookingData!=null){
            HashMap<Integer, BookingLog> bookingLogHashMap = new HashMap<>();
            while(bookingData.next()){
                BookingLog bookingRecord = readBookingLog(bookingData);
                bookingLogHashMap.put(bookingRecord.getBookingID(), bookingRecord);
            }
            if(bookingLogHashMap.size()>0)
                return bookingLogHashMap;
        }
        return null;
    }


}
