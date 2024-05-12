package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private int admin_id;

    public Admin()
    {

    }
    public Admin(int admin_id)
    {
        this.admin_id = admin_id;
    }
    public Admin getAdmin(int user_id)
    {
        Admin admin = new Admin();
        admin.user_id = user_id;

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            String query1 = "select * from admin where user_id = ?";
            String query2 = "select * from users where user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setInt(1,admin.user_id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                admin.admin_id = resultSet.getInt("admin_id");

            }
            statement = connection.prepareStatement(query2);
            statement.setInt(1,admin.user_id);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                admin.name = resultSet.getString("Name");
                admin.email = resultSet.getString("Email");
                admin.password = resultSet.getString("password");

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return admin;

    }

    public boolean addUser(String name, String email, String password, String role, String bloodGroup)
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String query1 = "Insert into users (Name, Email, password,role) VALUES (?,?,?,?)";
        //String query1 = "Insert into users (Name, Email, password) VALUES ('Usman Afzal','usman@gmail.com','usman')";
        String query2 = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";



        try{
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4,role);

            //ResultSet resultSet = statement.executeQuery(query1);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0)
            {
                System.out.println("Data inserted");
            }
            else {
                System.out.println("Data insertion failed");
            }


            ResultSet resultset = statement.executeQuery(query2);
            if(resultset.next())
            {
                this.user_id= resultset.getInt("user_id");
                System.out.println(this.user_id);
                String query3 = "INSERT INTO " + role + "(blood_group, user_id) VALUES (?,?)";
                statement = connection.prepareStatement(query3);
                //statement.setString(1, role);
                statement.setString(1, bloodGroup);
                statement.setInt(2, user_id);
                statement.executeUpdate();
                return true;

            }
            else {
                System.out.println("No rows found");
                return false;

            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        try {
            // Execute SQL query to delete the user from the users table based on their user ID
            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteAppointmentByUserId(int userId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        try {
            // Retrieve the donor_id associated with the provided userId
            String donorIdQuery = "SELECT donor_id FROM donor WHERE user_id = ?";
            PreparedStatement donorIdStatement = connection.prepareStatement(donorIdQuery);
            donorIdStatement.setInt(1, userId);
            ResultSet donorIdResult = donorIdStatement.executeQuery();

            int donorId;
            if (donorIdResult.next()) {
                donorId = donorIdResult.getInt("donor_id");

                // Execute SQL query to delete data from the appointment table based on the donor_id
                String deleteQuery = "DELETE FROM appointment WHERE donor_id = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, donorId);
                int rowsAffected = deleteStatement.executeUpdate();
                return rowsAffected > 0;
            } else {
                // Donor with the given user ID does not exist
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteDonorByUserId(int userId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        try {
            // Execute SQL query to delete data from the donor table based on the user ID
            String query = "DELETE FROM donor WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserRole(int userId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        try {
            String query = "SELECT role FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
//            System.out.println("result set:");
//            System.out.println(resultSet);
            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                // User with the given user ID does not exist
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteRequestByUserId(int userId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        try {
            // Retrieve the patient_id associated with the provided userId
            String patientIdQuery = "SELECT patient_id FROM patient WHERE user_id = ?";
            PreparedStatement patientIdStatement = connection.prepareStatement(patientIdQuery);
            patientIdStatement.setInt(1, userId);
            ResultSet patientIdResult = patientIdStatement.executeQuery();

            List<Integer> patientIds = new ArrayList<>();
            while (patientIdResult.next()) {
                int patientId = patientIdResult.getInt("patient_id");
                patientIds.add(patientId);
            }

            if (!patientIds.isEmpty()) {
                // Construct the SQL query to delete requests based on patient_ids
                String deleteQuery = "DELETE FROM request WHERE patient_id IN (";
                for (int i = 0; i < patientIds.size(); i++) {
                    deleteQuery += "?";
                    if (i < patientIds.size() - 1) {
                        deleteQuery += ",";
                    }
                }
                deleteQuery += ")";

                // Execute the constructed SQL query
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                for (int i = 0; i < patientIds.size(); i++) {
                    deleteStatement.setInt(i + 1, patientIds.get(i));
                }
                int rowsAffected = deleteStatement.executeUpdate();
                return rowsAffected > 0;
            } else {
                // Patient with the given user ID does not exist
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deletePatientByUserId(int userId) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        try {
            // Execute SQL query to delete data from the patient table based on the user ID
            String query = "DELETE FROM patient WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
