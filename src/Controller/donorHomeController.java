package Controller;

import Classes.Donor;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import sample.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

import static java.sql.DriverManager.getConnection;

public class donorHomeController {
    private Donor donor;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private Label welcomeLabel;

    public void initData(Donor donor) {
        this.donor = donor;
        welcomeLabel.setText("Welcome, " + donor.getName());
    }
    public boolean checkAvailabilityButtonOnAction(ActionEvent e)
    {
        LocalDate date = appointmentDatePicker.getValue();
        if(checkIfExistsInDatabase(date) == false)
        {

        }
        return true;

    }
    public boolean checkIfExistsInDatabase(LocalDate date)
    {
        String query = "SELECT COUNT(*) FROM Appointment WHERE appointment_date = ?";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1,Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

}
