package avms.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingLog {
    private int bookingID;
    private int vehicleID;
    private int parkingServiceID;
    private String slotID;
    private LocalDateTime bookedOn;
    private LocalTime reservedTime;

    public BookingLog(int bookingID, int vehicleID, int parkingServiceID, String slotID, LocalDateTime bookedOn, LocalTime reservedTime) {
        this.bookingID = bookingID;
        this.vehicleID = vehicleID;
        this.parkingServiceID = parkingServiceID;
        this.slotID = slotID;
        this.bookedOn = bookedOn;
        this.reservedTime = reservedTime;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getParkingServiceID() {
        return parkingServiceID;
    }

    public void setParkingServiceID(int parkingServiceID) {
        this.parkingServiceID = parkingServiceID;
    }

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    public LocalDateTime getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(LocalDateTime bookedOn) {
        this.bookedOn = bookedOn;
    }

    public LocalTime getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(LocalTime reservedTime) {
        this.reservedTime = reservedTime;
    }
}
