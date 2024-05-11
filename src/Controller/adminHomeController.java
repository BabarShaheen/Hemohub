package Controller;

import Classes.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
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
    private AnchorPane manageRequestsAnchor;
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
    @FXML
    private TableView<Request> viewRequestTable;
    @FXML
    private TableColumn<Request,Integer> patientIDColumn;
    @FXML
    private TableColumn<Request,Integer> requestIdColumn;
    @FXML
    private TableColumn<Request, LocalDate> dateColumn;
    @FXML
    private TableColumn<Request, String> statusColumn;
    @FXML
    private TableColumn<Request, Integer> quantityColumn;
    @FXML
    private TableColumn<Request, String> bloodGroupColumn;


    public void initData(Admin admin) {
        this.admin = admin;
        welcomeLabel.setText("Welcome, " + admin.getName());
    }

    // ----------------- Manage Users ----------------------
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

    //--------------------- Manage Requests ------------------------
    public void manageRequestButtonOnAction(ActionEvent actionEvent) {

        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("request_id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        patientIDColumn.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        bloodGroupColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));

        manageUsersAnchor.setVisible(false);
        addUserAnchor.setVisible(false);
        viewTableAnchor.setVisible(false);
        manageRequestsAnchor.setVisible(true);


        Request request = new Request();
        ObservableList<Request> list = request.getAllRequestsList();
        viewRequestTable.setItems(list);

    }

    public void manageInventoryButtonOnAction(ActionEvent actionEvent) {
    }



    public void acceptRequestButtonOnAction(ActionEvent actionEvent) {
    }

    public void declineRequestButtonOnAction(ActionEvent actionEvent) {
    }

    public void logoutButtonOnAction(ActionEvent e)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/login.fxml"));
            Parent root = loader.load();

            loginController controller = loader.getController();
            if (controller == null) {
                throw new RuntimeException("Failed to get controller from FXML loader");
            }


            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
