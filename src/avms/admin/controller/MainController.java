package avms.admin.controller;

import avms.Main;
import avms.admin.ParkingServiceData;
import avms.databaseHandler.AVMS_Database;
import avms.entity.ParkingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class MainController {
    @FXML
    VBox mainPane;

    @FXML
    ScrollPane viewPane;

    public static ParkingServiceData parkingServiceData;

    @FXML
    public void initialize(){
        Main.mainStage.setTitle("[Admin]Automated Vehicle Management System");
        parkingServiceData = new ParkingServiceData(Main.userData);
        loadParkingSpaceData();
    }

    @FXML
    public void loadParkingSpaceData(){
        AVMS_Database database =null;
        try{
            database = new AVMS_Database();
            HashMap<Integer, ParkingService> parkingServiceHashMap = database.getAllParkingServicesByManagerId(parkingServiceData.getManager().getUserID());
            if(parkingServiceHashMap!=null){
                MainController.parkingServiceData.setParkingServices(parkingServiceHashMap);
            }
        }
        catch (SQLException|ClassNotFoundException e){
            System.err.println(e);
        }
        finally {
            if(database!=null){
                try{
                    database.close();
                }catch (SQLException e){
                }
            }
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alertPopup = new Alert(alertType);
        alertPopup.setContentText(msg);
        alertPopup.show();
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        mainPane.getChildren().clear();
        Main.mainStage.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Logout successfully!");
        alert.show();
    }

    @FXML
    public void openProfile(ActionEvent actionEvent) throws IOException{
        openViewPane("/avms/userProfile/res/layouts/profile");
    }

    @FXML
    public void openEditProfile(ActionEvent actionEvent) throws IOException{
        openViewPane("/avms/userProfile/res/layouts/editProfile");
    }

    public void openViewPane(String viewPaneName) throws IOException{
        viewPane.setContent(FXMLLoader.load(getClass().getResource(viewPaneName+".fxml")));
    }

    @FXML
    public void openViewParkingLog(ActionEvent actionEvent) throws IOException {
        openViewPane("../res/layouts/viewParkingLog");
    }

    @FXML
    public void openRegisterParkingSpace() throws IOException{
        openViewPane("../res/layouts/registerParkingService");
    }

    @FXML
    public void openModifyParkingSpace(ActionEvent actionEvent) throws IOException{
        openViewPane("../res/layouts/modifyParkingSpace");
    }

}
