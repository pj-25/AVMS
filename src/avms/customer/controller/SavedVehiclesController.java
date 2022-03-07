package avms.customer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import avms.entity.Vehicle;
import avms.Main;


public class SavedVehiclesController {
    @FXML
    TableView<Vehicle> savedVehicles;
    @FXML
    TableColumn<Vehicle, String> column1;
    @FXML
    TableColumn<Vehicle, String> column2;
    @FXML
    TableColumn<Vehicle, String> column3;
    @FXML
    TableColumn<Vehicle, String> column4;


    @FXML
    public void initialize(){
        column1.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        column3.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        column4.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        if(MainController.customerData.getSavedVehicles()!=null){
            for(Vehicle v:MainController.customerData.getSavedVehicles().values()){
                savedVehicles.getItems().add(v);
            }
        }
    }
}
