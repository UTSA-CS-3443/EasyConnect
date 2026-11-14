package edu.utsa.cs3443.easyconnect.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import edu.utsa.cs3443.easyconnect.model.Patient;
import edu.utsa.cs3443.easyconnect.model.Payment;
public class PatientBillingController {
@FXML private Label balanceDueLabel;
@FXML private Label dueDateLabel;
double amount;
String date;
Patient patient;
@FXML private void initialize(){
    Session session = Session.getInstance();
    patient = (Patient) session.getCurrentUser();
     amount = patient.getPayment().getAmount();
    balanceDueLabel.setText(amount + "");
    date =  patient.getPayment().getDate();
    dueDateLabel.setText(date);
}

@FXML private void makePayment(){
    amount = 0.0;
    date = "";
    patient.getPayment().setAmount(amount);
    patient.getPayment().setDate(date);
    balanceDueLabel.setText(amount + "");
    dueDateLabel.setText(date);
}

}
