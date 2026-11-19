package edu.utsa.cs3443.easyconnect.controller;
import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.Medication;
import edu.utsa.cs3443.easyconnect.model.MedicalRecord;
import edu.utsa.cs3443.easyconnect.model.Patient;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.util.ArrayList;

public class PatientFormController {
    @FXML private VBox symptomList;
    @FXML private VBox medicalHistoryList;
    @FXML private VBox currentMedicationList;

    @FXML private HBox symptomInputRow;
    @FXML private HBox historyInputRow;
    @FXML private HBox medicationInputRow;

    @FXML private TextField symptomInput;
    @FXML private TextField historyInput;
    @FXML private TextField medicationInput;
    @FXML private Button saveButton;
    DataHandler dataHandler = Session.getInstance().getDataHandler();
    private Patient currPatient;

    @FXML
    public void initialize() {

        Session session = Session.getInstance();
        this.currPatient = (Patient) session.getCurrentUser();
        MedicalRecord record = currPatient.getMedicalRecord();
        if(isRecordEmpty(record)) {
            makeEdit(true);
        }
        else{
            for(String symptom : record.getSymptoms()) {
                symptomList.getChildren().add(customLabel(symptom));
            }

            for(String medHistory: record.getMedicalHistory()){
                medicalHistoryList.getChildren().add(customLabel(medHistory));
            }

            for(Medication medication : record.getMedications()){
                currentMedicationList.getChildren().add(customLabel(medication.toString()));
            }

        }

    }

    private boolean isRecordEmpty(MedicalRecord record) {
        if (record == null) {
            return true;
        }
        return record.getSymptoms().isEmpty() && record.getMedications().isEmpty() && record.getMedicalHistory().isEmpty();
    }

    private void makeEdit(boolean edit){
        symptomInputRow.setVisible(edit);
        symptomInputRow.setManaged(edit);
        historyInputRow.setVisible(edit);
        historyInputRow.setManaged(edit);
        medicationInputRow.setVisible(edit);
        medicationInputRow.setManaged(edit);
        saveButton.setVisible(edit);
        saveButton.setManaged(edit);
    }

    private Label customLabel(String text) {
        Label label = new Label("• " + text);
        label.setWrapText(true);
        label.getStyleClass().add("component-Text");
        return label;
    }

    @FXML
    public void onAddSymptom(){
        String line = symptomInput.getText().trim();
        if(!line.isEmpty()){
            symptomList.getChildren().add(customLabel(line));
            symptomInput.clear();
        }
    }
    @FXML
    public void onAddMedication(){
        String line = medicationInput.getText().trim();
        if(!line.isEmpty()){
            currentMedicationList.getChildren().add(customLabel(line));
            medicationInput.clear();
        }
    }

    @FXML
    void onAddHistory(){
        String line = historyInput.getText().trim();
        if(!line.isEmpty()){
            medicalHistoryList.getChildren().add(customLabel(line));
            historyInput.clear();
        }
    }

    private ArrayList<String> grabItems(VBox vBox){
        ArrayList<String> items = new ArrayList<>();
        for(Node node : vBox.getChildren()){
            if(node instanceof Label){
                String text = ((Label)node).getText();
                if(text.startsWith("• ")){
                    text = text.substring(2).trim();
                }
                items.add(text);
            }
        }
        return items;
    }

    @FXML public void onSaveRecord(){
        if (currPatient == null) {
            return;
        }
        MedicalRecord record = currPatient.getMedicalRecord();
        if(record == null) {
            record = new MedicalRecord(currPatient.getId(), currPatient.getName(), currPatient.getDateOfBirth(), "data/medicalRecord.txt");
            currPatient.setMedicalRecord(record);
        }
        record.setSymptoms(grabItems(symptomList));
        record.setMedicalHistory(grabItems(medicalHistoryList));

        ArrayList<Medication> medications = new ArrayList<>();
        for(String medT: grabItems(currentMedicationList)){
            medications.add(new Medication(currPatient.getId(), "", medT, "", 0));
        }
        record.setMedications(medications);
        //dataHandler.loadPatients();
        if (dataHandler.findPatient(currPatient.getId()) != -1) {
            dataHandler.updatePatient(currPatient);
        }
        else{
            dataHandler.addPatient(currPatient);
        }
        dataHandler.save();

        makeEdit(false);
    }
}
