package edu.utsa.cs3443.easyconnect.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import edu.utsa.cs3443.easyconnect.model.Note;
import edu.utsa.cs3443.easyconnect.model.Patient;
import java.util.ArrayList;
public class PatientRecomendationController {

    @FXML private VBox recommendationList;

    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        Patient patient = (Patient) session.getCurrentUser();
        ArrayList<Note> notes = patient.getNotes();

        if (notes != null && !notes.isEmpty()) {
            for (Note note : notes) {
                recommendationList.getChildren().add(createLabel(note.getNote()));
            }
        } else {
            Label statusLabel = new Label("No recommendations found.");
            statusLabel.getStyleClass().add("component-Text");
            recommendationList.getChildren().add(statusLabel);
        }

    }

    private Label createLabel(String text){
        Label label = new Label("• "+ text);
        label.setWrapText(true);
        label.getStyleClass().add("component-Text");
        return label;
    }

}
