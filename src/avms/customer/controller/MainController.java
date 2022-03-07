package avms.customer.controller;

import avms.customer.CustomerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import avms.Main;

import java.io.IOException;

public class MainController {

    public static CustomerData customerData =null;

    public MainController(){
        customerData = new CustomerData(Main.userData);
    }

    @FXML
    Button profileBtn;

    @FXML
    ScrollPane viewPane;

    @FXML
    Pane mainPane;

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

    public void openAddVehicle(ActionEvent actionEvent) throws IOException {
        openViewPane("../res/layouts/addVehicle");
    }

    public void openViewPane(String viewPaneName) throws IOException{
        viewPane.setContent(FXMLLoader.load(getClass().getResource(viewPaneName+".fxml")));
    }

    @FXML
    public void initialize(){
        Main.mainStage.setTitle("[Customer]Automated Vehicle Management System");
        customerData = new CustomerData(Main.userData);
    }


    @FXML
    public void openEditProfile(ActionEvent actionEvent) throws IOException{
        openViewPane("/avms/userProfile/res/layouts/editProfile");
    }

    @FXML
    public void openSavedVehicles(ActionEvent actionEvent) throws IOException{
        openViewPane("../res/layouts/savedVehicles");
    }

    @FXML
    public void openBookParkingSpace(ActionEvent actionEvent) throws IOException{
        openViewPane("../res/layouts/bookParking");
    }

    @FXML
    public void openCurrentBooking(ActionEvent actionEvent) throws IOException{
        openViewPane("../res/layouts/currentBooking");
    }

    @FXML
    public void openBookingHistory(ActionEvent actionEvent) throws IOException{
        openViewPane("../res/layouts/bookingHistory");
    }
}
