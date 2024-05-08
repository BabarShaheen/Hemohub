package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class signupController {

    public Label registerLabel;
    @FXML
    private javafx.scene.control.TextField roleTextField;
    @FXML
    private javafx.scene.control.TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private javafx.scene.control.Button registerButton;
    @FXML
    private Button backButton;

    public void registerButtonOnAction(ActionEvent e)
    {
        if(!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank())
        {
            registerLabel.setText("Registration Successful");
        }
        else
        {
            registerLabel.setText("Enter Role, Username and Password");
        }
    }





    public void backButtonOnAction(ActionEvent e){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }



}
