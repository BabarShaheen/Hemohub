package Classes;
import sample.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.*;

public class Donor extends User{

    private String bloodGroup;
    private int donor_id;

    public Donor()
    {

    }
    public Donor(String bloodGroup, String name, String email, String password)
    {
        this.bloodGroup = bloodGroup;
        this.name = name;
        this.email = email;
        this.password= password;
    }

    public Donor getDonor(int user_id)
    {
        Donor donor = new Donor();
        donor.user_id = user_id;

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            String query1 = "select * from donor where user_id = ?";
            String query2 = "select * from users where user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setInt(1,donor.user_id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                donor.donor_id = resultSet.getInt("donor_id");
                donor.bloodGroup = resultSet.getString("blood_Group");

            }
            statement = connection.prepareStatement(query2);
            statement.setInt(1,donor.user_id);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                donor.name = resultSet.getString("Name");
                donor.email = resultSet.getString("Email");
                donor.password = resultSet.getString("password");

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return donor;

    }
    public int getDonorID()
    {
        return this.donor_id;
    }

    public boolean registerDonor()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();

        String query1 = "Insert into users (Name, Email, password,role) VALUES (?,?,?,?)";
        //String query1 = "Insert into users (Name, Email, password) VALUES ('Usman Afzal','usman@gmail.com','usman')";
        String query2 = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";



        try{
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setString(1, this.name);
            statement.setString(2, this.email);
            statement.setString(3, this.password);
            statement.setString(4,"donor");

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
                String query3 = "INSERT INTO hemohub.donor (blood_group, user_id) VALUES (?,?)";
                statement = connection.prepareStatement(query3);
                statement.setString(1, this.bloodGroup);
                statement.setInt(2, this.user_id);
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
}
