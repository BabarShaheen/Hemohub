package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
