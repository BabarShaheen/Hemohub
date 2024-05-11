package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DatabaseConnection;

import java.time.LocalDate;
import java.sql.*;

public class Request {
    private LocalDate requestDate;



    private Patient patient;
    private int quantity;
    private int request_id;
    private String status;
    private String bloodGroup;


    private int patient_id;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }


    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public int getPatient_id() { return patient_id;}


    public Request() {
    }

    public Request(LocalDate requestDateDate, Patient patient, int quantity) {
        this.requestDate = requestDateDate;
        this.patient = patient;
        this.quantity = quantity;
    }
    public Request(LocalDate requestDateDate, Patient patient,int quantity, String status, String bloodGroup) {
        this.requestDate = requestDateDate;
        this.patient = patient;
        this.quantity = quantity;
        this.status = status;
        this.bloodGroup = bloodGroup;
    }
    public Request(LocalDate requestDate, Patient patient, int quantity, int request_id, String status, String bloodGroup, int patient_id) {
        this.requestDate = requestDate;
        this.patient = patient;
        this.quantity = quantity;
        this.request_id = request_id;
        this.status = status;
        this.bloodGroup = bloodGroup;
        this.patient_id = patient_id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public boolean setRequest(String status)
    {
        Date sqlDate = Date.valueOf(this.requestDate);
        System.out.println("this is : " + patient.getPatientID());

        String query = "INSERT INTO request (date, patient_id, quantity, blood_group, status) VALUES (?, ?, ?, ?, ?)";

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDate(1, sqlDate);
            statement.setInt(2, patient.getPatientID());
            statement.setInt(3, this.quantity);
            statement.setString(4, patient.getBloodGroup());
            statement.setString(5, status); // Set the status parameter

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted");
                return true;
            } else {
                System.out.println("Data insertion failed");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }


    public ObservableList<Request> getRequestsList(int patientID) {
        ObservableList<Request> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM request WHERE patient_id = ?";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        Date sqlDate;


        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, patientID);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sqlDate = resultSet.getDate("date");
                LocalDate date = sqlDate.toLocalDate();
                int quantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");
                String bloodGroup = resultSet.getString("blood_group");
                System.out.println(date + " " + quantity + " " + status + " " + bloodGroup);

                Request request = new Request(date, this.patient, quantity, status, bloodGroup);
                list.add(request);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Request> getAllRequestsList() {
        ObservableList<Request> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM request";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        Date sqlDate;


        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int reqID = resultSet.getInt("request_id");
                int patientID = resultSet.getInt("patient_id");
                sqlDate = resultSet.getDate("date");
                LocalDate date = sqlDate.toLocalDate();
                int quantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");
                String bloodGroup = resultSet.getString("blood_group");
                System.out.println(date + " " + quantity + " " + status + " " + bloodGroup);

                Request request = new Request(date, this.patient, quantity, reqID,status, bloodGroup,patientID);
                list.add(request);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
