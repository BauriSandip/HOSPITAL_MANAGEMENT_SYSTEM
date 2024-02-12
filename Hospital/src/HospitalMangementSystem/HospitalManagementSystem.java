package HospitalMangementSystem;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalManagementSystem { // Driver class
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Sandip@2000";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        // Connection database
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection, scanner);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appoinment");
                System.out.println("5. Exit");
                System.out.println("6. Enter Your Choice");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // add patient
                        patient.addPatient();
                        System.out.println();
                    case 2:
                        // view patient
                        patient.viewPatient();
                        System.out.println();
                    case 3:
                        // view doctor
                        doctor.viewDoctor();
                        System.out.println();
                    case 4:
                        // book appoinment
                        bookAppointment(patient, doctor, connection, scanner);
                        ;
                        System.out.println();
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // object object
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter patient ID...");
        int patientId = scanner.nextInt();
        int doctorId = scanner.nextInt();
        System.out.println("Enter appoinment date (YYYY-MM-DD)");
        String appoinmentDate = scanner.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
            if (checkDoctorAvailability(doctorId, appoinmentDate, connection)) {
                String appoinmentQuery = "INSERT INTO appointment(patient_id ,doctor_id ,appointment_date) VALUES (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appoinmentQuery);
                    // place Holder
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appoinmentDate);
                    int rowAffected = preparedStatement.executeUpdate();

                    if (rowAffected > 0) {
                        System.out.println("Appoinment Booked");
                    } else {
                        System.out.println("Failed to Booked Appoinment");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date");
            }
        } else {
            System.out.println("Either doctor or patient doesn't exist !!!");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appoinmentDate, Connection connection) {
        String query = "SELECT COUNT(*)FROM appointment WHERE patient_id=? AND appointment_date=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appoinmentDate);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                int count = resultset.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
