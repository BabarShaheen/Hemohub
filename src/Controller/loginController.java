package Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class loginController  {
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
    public void loginButtonOnAction(ActionEvent e){
        if(usernameTextField.getText().isBlank()  && passwordPasswordField.getText().isBlank())
        {
            loginLabel.setText("Enter username and password first");
        }
        else {
            loginLabel.setText("Login Button Clicked");
        }


    }

    public void closeButtonOnAction(ActionEvent e){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
