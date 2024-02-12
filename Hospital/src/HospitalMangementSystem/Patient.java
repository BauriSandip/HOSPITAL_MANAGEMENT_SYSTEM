package HospitalMangementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    // create constractor;
    private Patient(Connection connection, Scanner scanner) {
        // value assign
        this.connection = connection;
        this.scanner = scanner;
    }

    // 1st Method
    public void addPatient() {
        System.out.println("Enter Patient Name : ");
        String name = scanner.next();
        System.out.println("Enter Patient age : ");
        int age = scanner.nextInt();
        System.out.println("Enter Gender : ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(id,name,age,gender) VALUES (?,?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement.setInt(1, id);
            PreparedStatement.setString(2, name);
            PreparedStatement.setString(3, age);
            PreparedStatement.setString(4, gender);
            int affectedRow = preparedStatement.executeUpdate();

            if (affectedRow > 0) {
                System.out.println("patients added successfully");
            } else {
                System.out.println("Failed to add patients");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2nd Method
    public void viewPatient() {
        String query = "SELECT * FROM hospital.patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // The resultset holds the data that comes from the database table
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+------------+----------------+------+----------------+");
            System.out.println("| Patient Id |      Name      | age  |     gnder      |");
            System.out.println("+------------+----------------+------+----------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");

                System.out.printf("|%-12s|%-16s|%-6s|%-16s|\n", id, name, age, gender);// printf helps to format
                System.out.println("+------------+----------------+------+----------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3rd Method
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM hospital.patients WHER id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) { // data base thake kono data ale ture nahole false
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
