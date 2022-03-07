package avms;

import avms.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static User userData;
    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/avms/userAuthentication/res/layouts/login.fxml"));
        primaryStage.setTitle("AVMS: Automated Vehicle Management System");
        Scene scene =new Scene(root);
        scene.getStylesheets().add("/avms/res/stylesheets/style.css");
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
