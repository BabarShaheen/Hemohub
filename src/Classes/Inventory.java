package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

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

    public int getInventory_id() {
        return inventory_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBloodGroup() {
        return bloodGroup;
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


    public ObservableList<Inventory> getInventoryList() {
        ObservableList<Inventory> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM inventory";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();
        Date sqlDate;


        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int inventoryID = resultSet.getInt("inventory_id");
                int quantity = resultSet.getInt("quantity");
                String bloodGroup = resultSet.getString("blood_group");

                Inventory inventory = new Inventory(inventoryID, quantity,bloodGroup);
                list.add(inventory);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateInventory(int inventory_id, int quantity)
    {
        String query = "UPDATE inventory\n" +
                "SET quantity = " + quantity + "\n" +
                "WHERE inventory_id = " + inventory_id + " ;\n";

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getConnection();



        try{
            PreparedStatement statement = connection.prepareStatement(query);

            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0)
            {
                System.out.println("Data inserted");
                return true;
            }
            else {
                System.out.println("Data insertion failed");
                return false;
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
