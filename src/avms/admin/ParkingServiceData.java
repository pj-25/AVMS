package avms.admin;

import avms.entity.ParkingService;
import avms.entity.User;

import java.util.HashMap;

public class ParkingServiceData {
    private User manager;
    private HashMap<Integer, ParkingService> parkingServices = new HashMap<>();

    public ParkingServiceData(User manager) {
        this.manager = manager;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public HashMap<Integer, ParkingService> getParkingServices() {
        return parkingServices;
    }

    public void setParkingServices(HashMap<Integer, ParkingService> parkingServices) {
        this.parkingServices = parkingServices;
    }
}
