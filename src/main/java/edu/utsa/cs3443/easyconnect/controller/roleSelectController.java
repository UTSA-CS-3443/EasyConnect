package edu.utsa.cs3443.easyconnect.controller;
import javafx.fxml.FXML;
public class roleSelectController {

    private final SceneManager scenes = SceneManager.getInstance();

    @FXML
    private void patientLogin(){
        scenes.showLogin("patient");
    }

    @FXML
    private void doctorLogin(){
        scenes.showLogin("doctor");
    }

}
