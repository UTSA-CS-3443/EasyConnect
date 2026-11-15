package edu.utsa.cs3443.easyconnect.controller;
import edu.utsa.cs3443.easyconnect.model.Patient;
import edu.utsa.cs3443.easyconnect.model.Medication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PatientMedicationsController {
    private final Session session = Session.getInstance();
    @FXML
    private VBox medicationList;

    @FXML private void initialize() {
        medicationList.getChildren().clear();
        Patient patient = (Patient) session.getCurrentUser();
        ArrayList<Medication> medications = patient.getMedications();

        if(medications == null || medications.isEmpty()){
            createRow("No medications found");
            return;
        }
        for(Medication medication : medications){
            createRow("\u2022 " + medication.getName() + " " + medication.getDosage() + " " + medication.getFrequency());
        }
    }

    private void createRow(String text){
        Label row  = new Label(text);
        row.setStyle("-fx-font-family: 'JetBrains Mono'; -fx-font-size: 13px;");
        row.setWrapText(true);
        row.setMaxWidth(Double.MAX_VALUE);
        medicationList.getChildren().add(row);
    }
}
