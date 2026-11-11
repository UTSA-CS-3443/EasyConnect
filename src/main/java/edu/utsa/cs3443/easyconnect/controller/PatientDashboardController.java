package edu.utsa.cs3443.easyconnect.controller;
import edu.utsa.cs3443.easyconnect.model.Medication;
import edu.utsa.cs3443.easyconnect.model.Patient;
import edu.utsa.cs3443.easyconnect.model.Appointment;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

public class PatientDashboardController {
    private final SceneManager sceneManager = SceneManager.getInstance();
    @FXML private Label appointmentsLabel;
    @FXML private Label medicationsLabel;
    @FXML private Label paymentDueLabel;

    @FXML
    public void initialize(){
        Session session = Session.getInstance();

        if(session.isPatient()){
            Patient patient = (Patient) session.getCurrentUser();
            Appointment firstAppointment = patient.getFirstAppointment();

            if(firstAppointment != null){
                String date = firstAppointment.getDate();
                appointmentsLabel.setText(date);
            }
            else if (firstAppointment == null){
                appointmentsLabel.setText("No appointments");
            }

            Medication FirstMedication = patient.getFirstMedication();
            if(FirstMedication != null){
                String medicationName =  FirstMedication.getName() + "...";
                medicationsLabel.setText(medicationName);
            }
            else if(FirstMedication == null){
                medicationsLabel.setText("No medications");
            }
         /*
         Finish the payment label
          */

        }
    }

    @FXML
    public void onFillOutForm(){
        sceneManager.navigateScreen("billing.fxml");
    }
    @FXML
    public void onScheduleAppointment(){
        sceneManager.navigateScreen("patientAppointments.fxml");
    }


}
