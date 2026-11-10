package edu.utsa.cs3443.easyconnect.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DoctorDashboardController {
    private final SceneManager scenes = SceneManager.getInstance();

    @FXML
    public void onPatientLookupButtonClick(ActionEvent e) { //Quick Action Button
        scenes.navigateScreen("doctorLookup.fxml"); //Loads patient lookup screen
    }

    @FXML
    public void onNoteButtonClick(ActionEvent e) { //Quick Action Button
        scenes.navigateScreen("doctorRecomendations.fxml"); //Loads write note screen
    }

}
