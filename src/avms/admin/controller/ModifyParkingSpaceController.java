package avms.admin.controller;

import avms.databaseHandler.AVMS_Database;
import avms.entity.ParkingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.Set;


public class ModifyParkingSpaceController {
    @FXML
    ChoiceBox psId;

    @FXML
    TableView<ParkingService> psData;
    @FXML
    TableColumn<ParkingService, Integer> column1;
    @FXML
    TableColumn<ParkingService, Integer> column2;
    @FXML
    TableColumn<ParkingService, Integer> column3;
    @FXML
    TableColumn<ParkingService, Integer> column4;
    @FXML
    TableColumn<ParkingService, Integer> column5;
    @FXML
    TableColumn<ParkingService, Integer> column6;

    @FXML
    RadioButton delete;
    @FXML
    RadioButton update;


    @FXML
    TextField newTotalSlots;

    @FXML
    HBox newTotalSlotsPane;

    @FXML
    Button submitBtn;

    @FXML
    public void initialize(){
        column1.setCellValueFactory(new PropertyValueFactory<>("psID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3.setCellValueFactory(new PropertyValueFactory<>("totalParkingSlots"));
        column4.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        column5.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
        column6.setCellValueFactory(new PropertyValueFactory<>("freeSlots"));

        loadPSData();

        Set<Integer> psIds = MainController.parkingServiceData.getParkingServices().keySet();
        if(psIds.isEmpty()){
            psId.getItems().add("No data found!");
            submitBtn.setDisable(true);
        }{
            psId.getItems().addAll(psIds);
        }

        ToggleGroup choice = new ToggleGroup();
        delete.setToggleGroup(choice);
        update.setToggleGroup(choice);
        newTotalSlotsPane.disableProperty().bind(update.selectedProperty().not());
    }


    public void loadPSData(){
        psData.getItems().clear();
        for(ParkingService ps: MainController.parkingServiceData.getParkingServices().values()){
            psData.getItems().add(ps);
        }
        psData.refresh();
    }

    @FXML
    public void submit(ActionEvent actionEvent){
        try{
            AVMS_Database database = new AVMS_Database();
            int ps_id = Integer.parseInt(psId.getValue()+"");
            if(delete.isSelected()){
                if(database.removeParkingSpace(ps_id)){
                    alert("Parking space removed successfully!", Alert.AlertType.INFORMATION);
                    MainController.parkingServiceData.getParkingServices().remove(ps_id);
                    loadPSData();
                }
                else{
                    alert("Could not delete as referenced elsewhere!", Alert.AlertType.ERROR);
                }
            }
            else{
                int newSlots = Integer.parseInt(newTotalSlots.getText());
                ParkingService updatingData = MainController.parkingServiceData.getParkingServices().get(ps_id);
                int oldSlots = updatingData.getTotalParkingSlots();
                int oldFreeSlots = updatingData.getFreeSlots();
                int newFreesSlots;
                if(newSlots < oldSlots - oldFreeSlots){
                    alert("New Slots must be greater than allotted slots!", Alert.AlertType.ERROR);
                }
                else{
                    if(newSlots < oldSlots){
                        newFreesSlots = oldFreeSlots - (oldSlots-newSlots);
                        if(!database.removeParkingSlots(ps_id, oldSlots - newSlots) && !database.updateParkingSpace(ps_id, newSlots, newFreesSlots)){
                            throw new NumberFormatException();
                        }
                    }
                    else{
                        newFreesSlots = oldFreeSlots + (newSlots-oldSlots);
                        database.addParkingSlots(ps_id, updatingData.getTotalParkingSlots()+1,newFreesSlots);
                    }
                    alert("Updated Successfully!", Alert.AlertType.INFORMATION);
                    updatingData.setTotalParkingSlots(newSlots);
                    updatingData.setFreeSlots(newFreesSlots);
                    loadPSData();
                }
            }
        }
        catch (NumberFormatException numberFormatException){
            alert("Invalid number of new slots :(", Alert.AlertType.ERROR);
        }
        catch (SQLException exception){
            if(delete.isSelected()){
                System.out.println(exception);
                alert("Unable to delete!", Alert.AlertType.ERROR);
            }
            else{
                alert("Unable to remove!", Alert.AlertType.ERROR);
            }
        }
        catch (ClassNotFoundException e){
            System.out.println(e);
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alertPopup = new Alert(alertType);
        alertPopup.setContentText(msg);
        alertPopup.show();
    }

}
