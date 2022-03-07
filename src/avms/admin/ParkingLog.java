package avms.admin;

import java.time.LocalDateTime;

public class ParkingLog {
    private int bookingId;
    private String customerId;
    private int vehicleId;
    private int slotId;
    private String customerName;
    private String emailId;
    private String phoneNumber;
    private LocalDateTime bookedOn;

    public  ParkingLog(int bookingId, String customerId, int vehicleId, int slotId, String customerName, String emailId, String phoneNumber, LocalDateTime bookedOn) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.customerName = customerName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.bookedOn = bookedOn;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(LocalDateTime bookedOn) {
        this.bookedOn = bookedOn;
    }
}
