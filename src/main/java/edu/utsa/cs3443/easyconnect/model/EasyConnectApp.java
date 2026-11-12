package edu.utsa.cs3443.easyconnect.model;

import edu.utsa.cs3443.easyconnect.controller.SceneManager;
import edu.utsa.cs3443.easyconnect.controller.Session;
import javafx.application.Application;
import javafx.stage.Stage;

public class EasyConnectApp extends Application {
    @Override
    public void start(Stage stage){
        DataHandler dataHandler = Session.getDataHandler();
        dataHandler.loadPatients();
        dataHandler.loadDoctors();
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.intialize(stage);
        stage.setTitle("EasyConnect");
        sceneManager.showRoleSelection();
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
