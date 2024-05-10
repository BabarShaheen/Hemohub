package Controller;

import Classes.Admin;
import Classes.Appointment;
import Classes.Donor;
import Classes.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

public class adminHomeController {

    Admin admin;
    @FXML
    private Label welcomeLabel;
    @FXML
    private AnchorPane addUserAnchor;
    @FXML
    private AnchorPane manageUsersAnchor;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField roleTextField;
    @FXML
    private TableView viewUsersTable;

    public void initData(Admin admin) {
        this.admin = admin;
        welcomeLabel.setText("Welcome, " + admin.getName());
    }

    public void manageUsersButtonOnAction(ActionEvent e)
    {
        manageUsersAnchor.setVisible(true);
    }

    public void addUserButtonOnAction(ActionEvent e)
    {
        addUserAnchor.setVisible(true);
        viewUsersTable.setVisible(false);

    }
    public void viewUsersButtonOnAction(ActionEvent e)
    {
        addUserAnchor.setVisible(false);
        viewUsersTable.setVisible(true);

        User users = new User();
        ObservableList<User> list = users.getAllUsers();
        viewUsersTable.setItems(list);
    }


}
