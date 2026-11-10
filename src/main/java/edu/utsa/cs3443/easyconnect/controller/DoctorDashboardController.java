package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.Appointment;
import edu.utsa.cs3443.easyconnect.model.Doctor;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import java.time.LocalDate;
import javafx.fxml.FXML;

public class DoctorDashboardController {
    private final SceneManager scenes = SceneManager.getInstance(); //Gets the shared SceneManager so this controller can navigate between screens (Quick Actions buttons)
    @FXML private Label appointmentsLabel;

    @FXML
    public void initialize() {
        Session session = Session.getInstance(); //Gets the session containing the logged in user

        if (session.isDoctor()) { //Checks if it is a doctor
            Doctor doctor = (Doctor) session.getCurrentUser(); //Assures that the user is treated as a doctor to access appointments
            String result = ""; //Initialize the text that will appear on screen
            String today = LocalDate.now().toString(); //wGets the current date as text in yyyy-MM-dd format

            for (Appointment appointment : doctor.getAppointments()) { //Reads each appointment already loaded for this doctor
                if (today.equals(appointment.getDate())) {//Compares the current date with the loaded appointment object
                    result += appointment.getTime() + " - Patient " + appointment.getPatientId() + "\n"; // Displays appointment time and patient ID
                }
            }

            if (result.isEmpty()) {
                appointmentsLabel.setText("No appointments today.");// Displays a message if no appointments match today's date.
            }
            else {
                appointmentsLabel.setText(result);//Display the message
            }
        }
    }

    @FXML
    public void onPatientLookupButtonClick(ActionEvent e) { //Quick Action Button
        scenes.navigateScreen("doctorLookup.fxml"); //Loads patient lookup screen
    }

    @FXML
    public void onNoteButtonClick(ActionEvent e) { //Quick Action Button
        scenes.navigateScreen("doctorRecomendations.fxml"); //Loads write note screen
    }

}
