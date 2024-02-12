package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// **Doctor add function administrator through 
public class Doctor {
    private Connection connection;
    private Scanner scanner;

    // create constractor;
    private Doctor(Connection connection) {
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
            String query = "INSERT INTO patients(name,age,gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            PreparedStatement.setString(1, name);
            PreparedStatement.setString(2, age);
            PreparedStatement.setString(3, gender);
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
    public void viewDoctor() {
        String query = "SELECT * FROM hospital.doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // The resultset holds the data that comes from the database table
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Doctor");
            System.out.println("+-----------+----------------+------------------+");
            System.out.println("| Doctor Id |      Name      |  Specilization   |");
            System.out.println("+-----------+----------------+------------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");

                System.out.printf("|%-11s|%-16s|%-18s|\n", id, name, specialization);// printf helps to format
                System.out.println("+-----------+----------------+------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3rd Method
    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM hospital.  WHER id=?";

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
