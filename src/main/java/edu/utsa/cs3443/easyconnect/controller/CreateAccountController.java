package edu.utsa.cs3443.easyconnect.controller;
import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class CreateAccountController {
@FXML private Label errorLabel;
@FXML private TextField nameField, emailField, phoneField, dobField, addressField;
@FXML private PasswordField passwordField, confirmPasswordField;
@FXML private ComboBox<String> genderBox;
SceneManager sceneManager = SceneManager.getInstance();
DataHandler dataHandler = Session.getInstance().getDataHandler();

private static final DateTimeFormatter normDate = DateTimeFormatter.ofPattern("MM-dd-YYYY").withResolverStyle(ResolverStyle.STRICT);

@FXML private void onCreateAccount(){
    String name = nameField.getText().trim();
    String email = emailField.getText().trim();
    String password = passwordField.getText().trim();
    String phone = phoneField.getText().trim();
    String dob = dobField.getText().trim();
    String address = addressField.getText().trim();
    String confirm = confirmPasswordField.getText().trim();
    String gender = genderBox.getSelectionModel().getSelectedItem().toString();




    //id,name,password,email,address,gender,dateOfBirth,phoneNumber,balance
    String id = dataHandler.createPatientId();
    Patient patient = new Patient(id, name, password, email, address, gender, dob, phone, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    dataHandler.addPatient(patient);
    dataHandler.save();
    sceneManager.showLogin("patient");
}

private String validate(String name, String email, String password, String confirm, String phone, String address, String gender, String dob){
    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty() || phone.isEmpty() || address.isEmpty() || gender.isEmpty() || dob.isEmpty()) {
        return "Please fill all the fields";
    }

    if(dataHandler.emailExists(email)){
        return "Email already exists";
    }

    if(!password.equals(confirm)){
        return "Passwords do not match";
    }
    if(phone.replaceAll("\\D", "").length() < 10){
        return "Phone number is too short";
    }

    try {
        LocalDate birthDate = LocalDate.parse(dob, normDate);
        if (birthDate.isAfter(LocalDate.now()) || birthDate.getYear() < 1900) {
            return "Enter a real date";
        }
    }
        catch(DateTimeParseException e){
            return "DOB must format like  mm-dd-YYYY";
        }
    return null;
}



@FXML private void onBack() {
    sceneManager.showRoleSelection();
}


}
