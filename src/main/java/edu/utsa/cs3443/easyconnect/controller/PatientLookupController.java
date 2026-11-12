package edu.utsa.cs3443.easyconnect.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.Patient;
import javafx.scene.control.TextArea;

public class PatientLookupController {
    @FXML
    private ListView<String> patientNamesList; //Will display patient names in the fxml view

    @FXML
    private TextArea patientInfoArea; //Will display in the fxml view the information of the selected patient

    private final DataHandler dataHandler = Session.getDataHandler(); //Gets the shared DataHandler to access the loaded patient records


    @FXML
    public void initialize() {
        for (Patient patient : dataHandler.getPatients()) { //Gets the loaded patient list and gets each Patient object
            patientNamesList.getItems().add(patient.getName()); //Gets that patient name and adds it as a row in the ListView
        }
    }

    @FXML
    public void onPatientSelected() {
        int index = patientNamesList.getSelectionModel().getSelectedIndex(); //Gets the selected row position

        if(index >= 0) {
            Patient patient = dataHandler.getPatients().get(index);//Gets the patient in that position

            //Displays the patient information
            patientInfoArea.setText(
                            "Name: " + patient.getName() + "\n"
                            + "ID: " + patient.getId() + "\n"
                            + "Email: " + patient.getEmail() + "\n"
                            + "Phone: " + patient.getPhoneNumber()
            );
        }
    }
}
