package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.Doctor;
import edu.utsa.cs3443.easyconnect.model.Medication;
import edu.utsa.cs3443.easyconnect.model.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;

public class DoctorPrescriptionController {
    @FXML private TextField patientTextField;
    @FXML private TextField doctorIdField;
    @FXML private TextArea medicationArea;
    @FXML private TextField dosageField;
    @FXML private TextField frequencyField;
    @FXML private Button savePrescriptionButton;
    @FXML private Label statusLabel;

    private final Session session = Session.getInstance();
    private final DataHandler dataHandler = Session.getDataHandler();

    @FXML
    public void initialize() {
        savePrescriptionButton.setDisable(!session.isDoctor());

        if (session.isDoctor()) {
            doctorIdField.setText(session.getCurrentUser().getId());
        } else {
            statusLabel.setText("Please log in as a doctor");
        }
    }

    @FXML
    public void handlePrescribe(ActionEvent event) {
        if (!session.isDoctor()) {
            statusLabel.setText("Please log in as a doctor");
            return;
        }

        Patient patient = dataHandler.getPatient(
                patientTextField.getText().trim());

        if (patient == null) {
            statusLabel.setText("Enter an existing patient ID");
            return;
        }

        String name = medicationArea.getText().trim();
        String dosage = dosageField.getText().trim();

        if (!validText(name) || !validText(dosage)) {
            statusLabel.setText(
                    "Enter medication and dosage on one line without commas");
            return;
        }

        int frequency;
        try {
            frequency = Integer.parseInt(frequencyField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Enter a whole number for times per day");
            return;
        }

        if (frequency < 1) {
            statusLabel.setText("Times per day must be greater than zero");
            return;
        }

        Doctor doctor = (Doctor) session.getCurrentUser();
        Medication medication = new Medication(
                patient.getId(), doctor.getId(), name, dosage, frequency);

        doctor.prescribeMedication(patient, medication);

        try {
            dataHandler.saveMedications();
            medicationArea.clear();
            dosageField.clear();
            frequencyField.clear();
            statusLabel.setText("Prescription saved for " + patient.getName());
        } catch (IOException e) {
            patient.getMedications().remove(medication);
            statusLabel.setText("Unable to save the prescription. Try again");
        }
    }

    private boolean validText(String text) {
        return !text.isEmpty()
                && !text.contains(",")
                && !text.contains("\n")
                && !text.contains("\r");
    }
}
