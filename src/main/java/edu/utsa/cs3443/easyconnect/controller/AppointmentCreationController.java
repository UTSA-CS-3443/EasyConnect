package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppointmentCreationController {

    @FXML DatePicker dateBox;
    @FXML ComboBox<String> timeBox;
    @FXML ComboBox<String> userChoiceBox;
    @FXML Label errorMsg;
    @FXML Label userLabel;

    private ArrayList<Doctor> doctors = Session.getDataHandler().getDoctors();
    private ArrayList<Patient> patients = Session.getDataHandler().getPatients();

    User chosenUser;
    private Session session = Session.getInstance();
    private DataHandler dataHandler = Session.getDataHandler();
    @FXML
    public void initialize() {

        dateBox.setDisable(true);
        timeBox.setDisable(true);

        //Initialize choices
        if (Session.getInstance().isPatient()) {
            userLabel.setText("Doctor:");
            for(Doctor doctor: doctors) {
                userChoiceBox.getItems().add(doctor.getName());
            }
        }
        else {
            userLabel.setText("Patient:");
            for(Patient patient: patients) {
                userChoiceBox.getItems().add(patient.getName());
            }
        }

        dateBox.setDayCellFactory(p -> new DateCell() {
            @Override
            public void updateItem(LocalDate d, boolean empty) {
                super.updateItem(d, empty);
                setDisable(empty || d.isBefore(LocalDate.now()));
            }
        });
    }

    @FXML
    private void updateDates() {
        int i = userChoiceBox.getSelectionModel().getSelectedIndex();
        if (i < 0) return;

        chosenUser = session.isPatient() ? doctors.get(i) : patients.get(i);

        dateBox.setValue(null);
        dateBox.setDisable(false);
        updateTimes();            // with no date, this clears and locks the time box
        errorMsg.setText("");
    }

    @FXML
    private void updateTimes() {
        timeBox.getItems().clear();
        timeBox.setValue(null);

        LocalDate date = dateBox.getValue();
        if (date == null || chosenUser == null) {
            timeBox.setDisable(true);
            return;
        }

        ArrayList<Appointment> mine = session.isPatient()
                ? ((Patient) session.getCurrentUser()).getAppointments()
                : ((Doctor) session.getCurrentUser()).getAppointments();
        ArrayList<Appointment> theirs = session.isPatient()
                ? ((Doctor) chosenUser).getAppointments()
                : ((Patient) chosenUser).getAppointments();

        Set<String> booked = new HashSet<>();
        for (Appointment a : mine)   if (date.toString().equals(a.getDate())) booked.add(a.getTime());
        for (Appointment a : theirs) if (date.toString().equals(a.getDate())) booked.add(a.getTime());


        // 30-minute slots from 8:00 a.m. to 6:00 p.m.
        for (LocalTime t = LocalTime.of(8, 0); t.isBefore(LocalTime.of(18, 0)); t = t.plusMinutes(30)) {
            if (date.equals(LocalDate.now()) && t.isBefore(LocalTime.now())) continue;  // skip past times today
            if (!booked.contains(t.toString())) {
                timeBox.getItems().add(t.toString());
            }
        }

        if (timeBox.getItems().isEmpty()) {
            errorMsg.setText("No times available on this day.");
            timeBox.setDisable(true);
        } else {
            errorMsg.setText("");
            timeBox.setDisable(false);
        }
    }

    @FXML
    public void createAppointment(ActionEvent e) {
        if (dateBox.getValue() == null || timeBox.getValue() == null) {
            errorMsg.setText("Please choose a person, date, and time.");
            return;
        }

        //FINISH THIS HERE
        String dateStr  = dateBox.getValue().toString();
        String timeStr = timeBox.getValue().toString();
        Patient patient;
        Doctor doctor;

        if(session.isPatient()){
            patient = (Patient) session.getCurrentUser();
            doctor = (Doctor) chosenUser;
        }
        else {
            doctor = (Doctor) session.getCurrentUser();
            patient = (Patient) chosenUser;
        }
        //String pId, String dId, String d, String t
        Appointment appointment = new Appointment(patient.getId(), doctor.getId(), dateStr, timeStr);

        if(!patient.getAppointments().contains(appointment)) {
            patient.getAppointments().add(appointment);
        }
        if(!doctor.getAppointments().contains(appointment)) {
            doctor.getAppointments().add(appointment);
        }
        dataHandler.save();
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).close();

    }
}
