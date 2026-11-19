package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.Doctor;
import edu.utsa.cs3443.easyconnect.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class AccountController {
    @FXML private Label nameLabel;
    @FXML private Label idTitleLabel;
    @FXML private Label idLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;
    @FXML private Label genderLabel;
    @FXML private Label dateOfBirthLabel;

    @FXML
    public void initialize() {
        User user = Session.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        idTitleLabel.setText(user instanceof Doctor ? "Doctor ID" : "User ID");
        nameLabel.setText(user.getName());
        idLabel.setText(user.getId());
        emailLabel.setText(user.getEmail());
        phoneLabel.setText(user.getPhoneNumber());
        addressLabel.setText(user.getAddress());
        genderLabel.setText(user.getGender());
        dateOfBirthLabel.setText(user.getDateOfBirth());
    }
}
