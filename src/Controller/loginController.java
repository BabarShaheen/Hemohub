package Controller;

import Classes.Donor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.sql.PreparedStatement;
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
    public void loginAdminButtonOnAction(ActionEvent e) {
        if(validateLogin("admin") == true)
        {

        }
        else {

        }
    }

    public void loginPatientButtonOnAction(ActionEvent e) {


    }

    public void loginDonorButtonOnAction(ActionEvent e) {
        if(validateLogin("donor") == true)
        {
                int user_id;
                Donor donor = new Donor();
                user_id = donor.getUserIdFromDb(usernameTextField.getText(),passwordPasswordField.getText());
                donor = donor.getDonor(user_id);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/donorHome.fxml"));
                    Parent root = loader.load();

                    donorHomeController controller = loader.getController();
                    if (controller == null) {
                        throw new RuntimeException("Failed to get controller from FXML loader");
                    }

                    controller.initData(donor);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

        }
        else {

        }
    }
    public void signupButtonOnAction(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/signup.fxml"));
            Parent root = loader.load();
            signupController signupcontroller = loader.getController();

            // Get the current stage
            Stage stage = (Stage) signupButton.getScene().getWindow();

            // Set the scene to the signup page
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public boolean validateLogin(String role){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLoginQuery = "Select count(1) FROM users where Email = ? AND password = ? AND role = ?";
        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLoginQuery);
            statement.setString(1, usernameTextField.getText());
            statement.setString(2, passwordPasswordField.getText());
            statement.setString(3, role);
            ResultSet queryResult = statement.executeQuery();


            while(queryResult.next())
            {
                if(queryResult.getInt(1)==1)
                {
                    loginLabel.setText("Welcome!");
                    return true;

                }
                else {
                    loginLabel.setText("Invalid Login. Please try again");
                    return false;

                }

            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void closeButtonOnAction(ActionEvent e){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
