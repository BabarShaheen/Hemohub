package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    protected String name;
    protected String email;
    protected String password;
    protected int user_id;

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

}
