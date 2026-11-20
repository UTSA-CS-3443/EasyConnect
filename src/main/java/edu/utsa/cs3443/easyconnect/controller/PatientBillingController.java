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
    /* amount = patient.getPayment().getAmount();
    balanceDueLabel.setText(amount + "");
    date =  patient.getPayment().getDate();
    dueDateLabel.setText(date);
    */
    showPayment();

}

@FXML private void makePayment(){
    Payment payment = patient.getPayment();
    if(payment == null){
        return;
    }
    payment.setAmount(0.0);
    payment.setDate("");
    showPayment();
}
    private void showPayment(){
    Payment payment = patient.getPayment();
    if (payment == null || payment.getAmount() == 0) {
        balanceDueLabel.setText("$0.00");
        dueDateLabel.setText("No payment due");
        return;
    }
    balanceDueLabel.setText(String.format("%.2f", payment.getAmount()));
    dueDateLabel.setText(payment.getDate());
    }


}
