package avms.entity;

public class Vehicle {
    private int vehicleID;
    private String userID;
    private String vehicleType;
    private String vehicleNumber;
    private String modelName;

    public Vehicle(int vehicleID, String userID, String vehicleType, String vehicleNumber, String modelName) {
        this.vehicleID = vehicleID;
        this.userID = userID;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.modelName = modelName;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
