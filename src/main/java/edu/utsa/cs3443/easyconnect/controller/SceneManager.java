package edu.utsa.cs3443.easyconnect.controller;
import java.io.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class SceneManager {
    private Stage stage;
    private BorderPane shell;
    private sideBarController sideMenu;
    private static SceneManager instance;
    private SceneManager() {}
    private static final String FXML_PATH = "/edu/utsa/cs3443/easyconnect/layouts/";
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
    private <T> T loadFile(FXMLLoader loader) {
        try{
            return loader.load();
        }
        catch(IOException e){
            throw new UncheckedIOException(e);
        }
    }
    public void showOtherScreen(String fxml){
        Parent root = loadFile(new FXMLLoader(getClass().getResource(FXML_PATH+fxml)));
        stage.setScene(newScene(root));
    }
    public void showRoleSelection(){
        showOtherScreen("roleSelect.fxml");
    }

    public void showLogin(String role){
        String fxml = role.equals("doctor") ? "doctorLogin.fxml" : "patientLogin.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH+fxml));
        Parent root = loadFile(loader);
        loginController lC = loader.getController();
        lC.setRole(role);
        stage.setScene(newScene(root));

    }




    public void intialize(Stage stage) {
        this.stage = stage;
    }

    public void buildShell(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH+"main.fxml"));
        shell = loadFile(loader);

        sideMenu = (sideBarController) loader.getNamespace().get("sideMenu");
        sideMenu.createMenu();
        stage.setScene(newScene(shell));
    }

    public void showMainScreen(String fxml){
        Parent mainScreen = loadFile(new FXMLLoader(getClass().getResource(FXML_PATH+fxml)));
        shell.setCenter(mainScreen);
    }

    public void navigateScreen(String fxml){
        sideMenu.selectItem(fxml);
        showMainScreen(fxml);
    }
    private Scene newScene(Parent root){
        Scene scene  = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/edu/utsa/cs3443/easyconnect/stylesheets/styles.css").toExternalForm());
        return scene;
    }
}
