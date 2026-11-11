package edu.utsa.cs3443.easyconnect.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Map;
import java.util.HashMap;

public class sideBarController {
private final ToggleGroup toggleGroup = new ToggleGroup();
@FXML private VBox menu;
private final Map<String, ToggleButton> sideItems = new HashMap<>();
private final SceneManager scenes = SceneManager.getInstance();
private final Session session = Session.getInstance();


private void addItem(String name, String fxml){
    ToggleButton button = new ToggleButton(name);
    button.getStyleClass().add("sidebar-item");
    button.setToggleGroup(toggleGroup);
    button.setMaxWidth(Double.MAX_VALUE);

    button.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if(newValue) {
            button.getStyleClass().remove("sidebar-item-selected");
            button.setGraphic(null);

        }
        else {
            button.getStyleClass().remove("sidebar-item-selected");
            button.setGraphic(dotSelection());
        }
    });


    button.setOnAction(e -> {
        if(button.isSelected()) {
            scenes.navigateScreen(fxml);
        }
        else{
            button.setSelected(true);
        }
    });
    sideItems.put(fxml, button);
    menu.getChildren().add(button);
}

private Circle dotSelection(){
    return new Circle(3, Color.web("#6C71E0"));
}

public void selectItem(String fxml){
    ToggleButton button = sideItems.get(fxml);
    if(button != null) {
        button.setSelected(true);
    }
}

public void createMenu() {
    menu.getChildren().clear();
    sideItems.clear();

    if (session.isDoctor()) {
        addItem("Dashboard", "doctorDashboard.fxml");
        addItem("Patient Lookup", "doctorLookup.fxml");
        addItem("Appointments", "doctorAppointments.fxml");
        addItem("Prescriptions", "doctorMedications.fxml");
        addItem("Write Notes", "doctorRecomendations.fxml");
        addItem("Account", "account.fxml");

    } else {
        addItem("Dashboard", "patientDashboard.fxml");
        addItem("Patient Form", "patientForm.fxml");
        addItem("Appointments", "patientAppointments.fxml");
        addItem("Billing", "billing.fxml");
        addItem("Medications", "patientMedications.fxml");
        addItem("Doctor's Notes", "patientRecomendations.fxml");
        addItem("Account", "account.fxml");
    }
    ((ToggleButton) menu.getChildren().get(0)).fire();
}

    @FXML
    private void onLogout(){
    session.logout();
    scenes.showRoleSelection();
}


}
