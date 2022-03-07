package avms.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CheckoutLog {
    private int checkoutID;
    private int bookingLogID;
    private LocalDateTime endTime;
    private LocalTime totalTime;

    public CheckoutLog(int checkoutID, int bookingLogID, LocalDateTime endTime, LocalTime totalTime) {
        this.checkoutID = checkoutID;
        this.bookingLogID = bookingLogID;
        this.endTime = endTime;
        this.totalTime = totalTime;
    }

    public int getCheckoutID() {
        return checkoutID;
    }

    public void setCheckoutID(int checkoutID) {
        this.checkoutID = checkoutID;
    }

    public int getBookingLogID() {
        return bookingLogID;
    }

    public void setBookingLogID(int bookingLogID) {
        this.bookingLogID = bookingLogID;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(LocalTime totalTime) {
        this.totalTime = totalTime;
    }
}
