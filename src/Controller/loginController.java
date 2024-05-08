package Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import sample.DatabaseConnection;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginController  {
    public Button signupButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label loginLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    public void loginButtonOnAction(ActionEvent e) {
        validateLogin();
    }
    public void signupButtonOnAction(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/signup.fxml"));
            Parent root = loader.load();
            signupController signupController = loader.getController();

            // Get the current stage
            Stage stage = (Stage) signupButton.getScene().getWindow();

            // Set the scene to the signup page
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLoginQuery = "Select count(1) FROM admin where name = '" + usernameTextField.getText() + "' and password = '" + passwordPasswordField.getText() +"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLoginQuery);

            while(queryResult.next())
            {
                if(queryResult.getInt(1)==1)
                {
                    loginLabel.setText("Welcome!");
                }
                else {
                    loginLabel.setText("Invalid Login. Please try again");

                }

            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeButtonOnAction(ActionEvent e){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
