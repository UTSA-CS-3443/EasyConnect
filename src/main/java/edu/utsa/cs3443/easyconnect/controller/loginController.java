package edu.utsa.cs3443.easyconnect.controller;
import edu.utsa.cs3443.easyconnect.model.DataHandler;
import edu.utsa.cs3443.easyconnect.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class loginController {
    private final Session session = Session.getInstance();
    private final DataHandler dataHandler = session.getDataHandler();
    private final SceneManager sceneManager = SceneManager.getInstance();

    private String role;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Hyperlink createAccount;
    @FXML private TextField emailField;


    public void setRole(String role){
        this.role = role;
    }

    @FXML
    private void onLogin(){
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        if(email.isEmpty() || password.isEmpty()){
            errorLabel.setText("Please enter your email and password.");
            return;
        }

        User user = dataHandler.findUsers(email, password);
        boolean found = user != null;
        if(!found){
            errorLabel.setText("Invalid email or password.");
            passwordField.clear();
            return;
        }
        session.setCurrentUser(user);
        sceneManager.buildShell();

    }
    @FXML
    private void onBack(){
        sceneManager.showRoleSelection();
    }

    @FXML
    private void onCreateAccount(){
        sceneManager.showOtherScreen("createAccount.fxml");
    }


}
