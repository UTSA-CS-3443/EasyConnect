package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.Doctor;
import edu.utsa.cs3443.easyconnect.model.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DoctorNoteController {
    @FXML private TextField patientField;
    @FXML private TextField doctorIdField;
    @FXML private TextArea recommendationArea;
    @FXML private Button saveNoteButton;
    @FXML private Label statusLabel;

    private final Session session = Session.getInstance();
    private final DataHandler dataHandler = Session.getDataHandler();

    @FXML
    public void initialize() {
        saveNoteButton.setDisable(!session.isDoctor());

        if (session.isDoctor()) {
            doctorIdField.setText(session.getCurrentUser().getId());
        } else {
            statusLabel.setText("Please log in as a doctor");
        }
    }

    @FXML
    private void onSavePatientNoteButtonClick(ActionEvent event) {
        if (!session.isDoctor()) {
            statusLabel.setText("Please log in as a doctor");
            return;
        }

        Patient patient = dataHandler.getPatient(patientField.getText().trim());
        if (patient == null) {
            statusLabel.setText("Enter an existing patient ID");
            return;
        }

        String note = recommendationArea.getText().trim();
        if (!validText(note)) {
            statusLabel.setText("Enter a note on one line without commas");
            return;
        }

        Doctor doctor = (Doctor) session.getCurrentUser();
        doctor.writeNote(patient, note);
        dataHandler.saveNotes();
        clearNoteFields();
        statusLabel.setText("Note saved for " + patient.getName());
    }

    private boolean validText(String text) {
        return !text.isEmpty()
                && !text.contains(",")
                && !text.contains("\n")
                && !text.contains("\r");
    }

    private void clearNoteFields() {
        recommendationArea.clear();
    }
}
