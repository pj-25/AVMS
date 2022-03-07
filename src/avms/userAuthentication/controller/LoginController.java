package avms.userAuthentication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import avms.databaseHandler.AVMS_Database;
import avms.Main;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    TextField loginId;

    @FXML
    TextField password;

    @FXML
    CheckBox showBox;

    @FXML
    Pane mainPane;

    @FXML
    public void initialize(){
        Tooltip passwordShow = new Tooltip();
        //passwordShow.setAutoHide(false);
        passwordShow.textProperty().bind(password.textProperty());
        password.setTooltip(passwordShow);
    }

    @FXML
    public void login(ActionEvent actionEvent){
        String errorMsg = "Invalid loginId/Password :(";
        AVMS_Database database = null;
        try{
            if(loginId.getText().isEmpty()){
                alert("Login ID is required!", Alert.AlertType.ERROR);
            }
            else if(password.getText().isEmpty()){
                alert("Password is required!", Alert.AlertType.ERROR);
            }
            else{
                database = new AVMS_Database();
                if(database.isValidUser(loginId.getText(), password.getText())) {
                    Main.userData = database.getUserById(loginId.getText());
                    if(Main.userData.getUserType().equals("PSP")){
                        jumpToAdminApp();
                    }
                    else{
                        jumpToCustomerApp();
                    }
                }
                else{
                    throw new SQLException();
                }
            }
        }
        catch (SQLException | ClassNotFoundException | IOException e){
            System.err.println(e);
            alert(errorMsg, Alert.AlertType.ERROR);
        }finally {
            try{
                if(database!=null){
                    database.close();
                }
            }catch (SQLException e){
            }
        }
    }

    private void alert(String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void showPassword(ActionEvent actionEvent){
        if(showBox.isSelected()){
            double x = password.getScene().getWindow().getX();
            double y = password.getScene().getWindow().getY();
            Bounds localBounds = password.localToScene(password.getBoundsInLocal());

            password.getTooltip().show(password, x+ localBounds.getMinX(), y+ localBounds.getMaxY());
        }
        else{
            password.getTooltip().hide();
        }
    }

    @FXML
    public void register() throws IOException{
        jumpTo("../res/layouts/register");
    }

    public void jumpTo(String layout) throws IOException{
        password.getTooltip().hide();
        mainPane.getChildren().remove(0);
        mainPane.getChildren().add(FXMLLoader.load(getClass().getResource(layout+".fxml")));
    }

    public void jumpToCustomerApp() throws IOException {
        jumpTo("/avms/customer/res/layouts/main");
    }

    public void jumpToAdminApp() throws IOException{
        jumpTo("/avms/admin/res/layouts/main");
    }
}
