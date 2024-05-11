package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient extends User{

    private String bloodGroup;
    private int patient_id;

    public Patient(){}
    public Patient getPatient(int user_id)
    {
        Patient patient = new Patient();
        patient.user_id = user_id;

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            String query1 = "select * from patient where user_id = ?";
            String query2 = "select * from users where user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setInt(1,patient.user_id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                patient.patient_id = resultSet.getInt("patient_id");
                patient.bloodGroup = resultSet.getString("blood_Group");

            }
            statement = connection.prepareStatement(query2);
            statement.setInt(1,patient.user_id);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                patient.name = resultSet.getString("Name");
                patient.email = resultSet.getString("Email");
                patient.password = resultSet.getString("password");

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return patient;

    }

    public Patient(String bloodGroup, String name, String email, String password)
    {
        this.bloodGroup = bloodGroup;
        this.name = name;
        this.email = email;
        this.password= password;
    }
    public String getName(){
        return this.name;

    }
    public int getPatientID()
    {
        return this.patient_id;
    }

    public boolean registerPatient()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String query1 = "Insert into users (Name, Email, password,role) VALUES (?,?,?,?)";
        //String query1 = "Insert into users (Name, Email, password) VALUES ('babar','babar@gmail.com','babar')";
        String query2 = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";



        try {
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setString(1, this.name);
            statement.setString(2, this.email);
            statement.setString(3, this.password);
            statement.setString(4, "donor");
            //ResultSet resultSet = statement.executeQuery(query1);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted");
            } else {
                System.out.println("Data insertion failed");

            }


            ResultSet resultset = statement.executeQuery(query2);
            if (resultset.next()) {
                int userid = resultset.getInt("user_id");
                System.out.println(userid);
                String query3 = "INSERT INTO hemohub.patient (blood_group, user_id) VALUES (?,?)";
                statement = connection.prepareStatement(query3);
                statement.setString(1, this.bloodGroup);
                statement.setInt(2, userid);
                statement.executeUpdate();
                return true;

            } else {
                System.out.println("No rows found");
                return false;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

}
