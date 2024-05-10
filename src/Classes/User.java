package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class User {
    protected String name;
    protected String email;
    protected String password;
    protected String role;
    protected int user_id;

    public User()
    {

    }

    public User(String name, String email, String password, String role, int user_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.user_id = user_id;
    }
    public String getName()
    {
        return this.name;
    }

    public int getUserIdFromDb(String email, String password)
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLoginQuery = "Select * FROM users where Email = ? AND password = ?";
        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLoginQuery);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet queryResult = statement.executeQuery();


            while(queryResult.next())
            {
               this.user_id = queryResult.getInt("user_id");

            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return this.user_id;
    }

    public ObservableList<User> getAllUsers(){

        ObservableList<User> list = FXCollections.observableArrayList();
        String query = "Select * from users";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        Date sqlDate;

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                int user_id = resultSet.getInt("user_id");

                User user = new User(name,email,password,role,user_id);
                list.add(user);

            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
