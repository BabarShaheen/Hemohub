package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("hello babar. i am working in Usman branch");
        Parent root = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.setTitle("Hemo-HUB");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}