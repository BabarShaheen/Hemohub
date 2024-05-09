package Controller;

import Classes.Donor;
import Classes.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class signupController {

    public Label registerLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField passwordPasswordField1;
    @FXML
    private TextField bloodGroupTextField;
    @FXML
    private Button backButton;

//    public void registerButtonOnAction(ActionEvent e)
//    {
//        if(!nameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank())
//        {
//            if(passwordPasswordField1.getText().equals(passwordPasswordField.getText()))
//            {
//                registerLabel.setText("Registration Successful");
//            }
//            else
//            {
//                registerLabel.setText("Passwords do not match");
//            }
//
//        }
//        else
//        {
//            registerLabel.setText("Enter Role, Username and Password");
//        }
//    }
    public void registerDonorButtonOnAction(ActionEvent e)
    {
        Donor donor = new Donor(bloodGroupTextField.getText(),nameTextField.getText(),emailTextField.getText(),passwordPasswordField.getText());
        donor.registerDonor();
        registerLabel.setText("Registered Successfully");
    }
    public void registerPatientButtonOnAction(ActionEvent e)
    {
        Patient patient = new Patient(bloodGroupTextField.getText(),nameTextField.getText(),emailTextField.getText(),passwordPasswordField.getText());
        patient.registerPatient();
        registerLabel.setText("Registered Successfully");

    }

    public void backButtonOnAction(ActionEvent e){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }



}
