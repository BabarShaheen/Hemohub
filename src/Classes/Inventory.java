package Classes;

import sample.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {

    private int inventory_id;
    private int quantity;
    private String bloodGroup;


    public Inventory()
    {

    }
    public Inventory(int inventory_id, int quantity, String bloodGroup) {
        this.inventory_id = inventory_id;
        this.quantity = quantity;
        this.bloodGroup = bloodGroup;
    }

    public boolean addToInventory(Appointment appointment)
    {
        Donor donor = appointment.getDonor();
        String query1 = "INSERT INTO inventory (quantity, blood_group)\n" +
                "VALUES (?, ?)\n" +
                "ON DUPLICATE KEY UPDATE quantity = quantity + " + appointment.getQuantity();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();



        try{
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setInt(1, appointment.getQuantity());
            statement.setString(2, donor.getBloodGroup());

            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0)
            {
                System.out.println("Data inserted");
            }
            else {
                System.out.println("Data insertion failed");
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
