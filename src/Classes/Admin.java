package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
