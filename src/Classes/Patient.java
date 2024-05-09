package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient extends User{

    private String bloodGroup;

    public Patient(String bloodGroup, String name, String email, String password)
    {
        this.bloodGroup = bloodGroup;
        this.name = name;
        this.email = email;
        this.password= password;
    }

    public void registerPatient()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String query1 = "Insert into users (Name, Email, password) VALUES (?,?,?)";
        //String query1 = "Insert into users (Name, Email, password) VALUES ('Usman Afzal','usman@gmail.com','usman')";
        String query2 = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";



        try{
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setString(1, this.name);
            statement.setString(2, this.email);
            statement.setString(3, this.password);

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
                int userid = resultset.getInt("user_id");
                System.out.println(userid);
                String query3 = "INSERT INTO hemohub.patient (blood_group, user_id) VALUES (?,?)";
                statement = connection.prepareStatement(query3);
                statement.setString(1, this.bloodGroup);
                statement.setInt(2, userid);
                statement.executeUpdate();

            }
            else {
                System.out.println("No rows found");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }



    }


}
