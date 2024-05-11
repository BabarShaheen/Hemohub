package Controller;

import Classes.Admin;
import Classes.Appointment;
import Classes.Donor;
import Classes.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.time.LocalDate;

public class adminHomeController {

    Admin admin;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label promptLabel;
    @FXML
    private AnchorPane addUserAnchor;
    @FXML
    private AnchorPane manageUsersAnchor;
    @FXML
    private AnchorPane viewTableAnchor;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField roleTextField;
    @FXML
    private TextField bloodGroupTextField;
    @FXML
    private TableView viewUsersTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, Integer> userIdColumn;

    public void initData(Admin admin) {
        this.admin = admin;
        welcomeLabel.setText("Welcome, " + admin.getName());
    }

    public void manageUsersButtonOnAction(ActionEvent e)
    {
        manageUsersAnchor.setVisible(true);
        addUserAnchor.setVisible(false);
        viewTableAnchor.setVisible(false);

    }

    public void addUserButtonOnAction(ActionEvent e)
    {
        addUserAnchor.setVisible(true);
        viewTableAnchor.setVisible(false);


    }

    public void viewUsersButtonOnAction(ActionEvent e)
    {
        viewTableAnchor.setVisible(true);
        addUserAnchor.setVisible(false);


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));





        User users = new User();
        ObservableList<User> list = users.getAllUsers();
        viewUsersTable.setItems(list);
    }


    public void deleteUserButtonOnAction(ActionEvent actionEvent) {
    }

    public void applyButtonOnAction(ActionEvent actionEvent) {
        if(admin.addUser(nameTextField.getText(),emailTextField.getText(),passwordTextField.getText(),roleTextField.getText(),bloodGroupTextField.getText()))
        {
            promptLabel.setText(roleTextField.getText() + " has been registered successfully!");
        }
        else {
            promptLabel.setText("Cannot register user, please try again");
        }
    }

    public void manageRequestButtonOnAction(ActionEvent actionEvent) {
    }

    public void manageInventoryButtonOnAction(ActionEvent actionEvent) {
    }

    public void logoutButtonOnAction(ActionEvent actionEvent) {
    }
}
