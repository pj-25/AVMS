package avms.entity;

import java.time.LocalTime;

public class ParkingService {
    private int psID;
    private String name;
    private String managerID;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int totalParkingSlots;
    private int freeSlots;
    private String status;
    private int address;
    private float price;

    public ParkingService(int psID, String name, String managerID, LocalTime openingTime, LocalTime closingTime, int totalParkingSlots, int freeSlots, String status, int address, float price) {
        this.psID = psID;
        this.name = name;
        this.managerID = managerID;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.totalParkingSlots = totalParkingSlots;
        this.freeSlots = freeSlots;
        this.status = status;
        this.address = address;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPsID() {
        return psID;
    }

    public void setPsID(int psID) {
        this.psID = psID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public int getTotalParkingSlots() {
        return totalParkingSlots;
    }

    public void setTotalParkingSlots(int totalParkingSlots) {
        this.totalParkingSlots = totalParkingSlots;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
