package edu.utsa.cs3443.easyconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DateCell;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.ArrayList;

import edu.utsa.cs3443.easyconnect.model.Appointment;
import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.Patient;
import edu.utsa.cs3443.easyconnect.model.Doctor;

public class PatientAppointmentsController {
    private final DataHandler dataHandler = Session.getInstance().getDataHandler();
    private Patient currPatient;
    @FXML private ComboBox<String> timeComboBox;
    @FXML private ComboBox<String> doctorComboBox;
    @FXML private DatePicker pickAppointmentDate;



    private void createCalendar() {

    }

    @FXML
    public void onBookAppointment() {
        if (currPatient == null) {
            return;
        }
        // Continue
    }

}
