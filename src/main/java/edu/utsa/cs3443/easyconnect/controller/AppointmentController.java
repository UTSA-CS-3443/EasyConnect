package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.Appointment;
import edu.utsa.cs3443.easyconnect.model.Doctor;
import edu.utsa.cs3443.easyconnect.model.Patient;
import edu.utsa.cs3443.easyconnect.model.User;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class AppointmentController {

    @FXML ComboBox<String> monthBox;
    @FXML ComboBox<Integer> yearBox;
    @FXML GridPane calendar;
    @FXML Pane overlay;

    Session session = Session.getInstance();
    SceneManager sceneManager = SceneManager.getInstance();

    private final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @FXML
    public void initialize() {
        for (Month m : Month.values()) {
            monthBox.getItems().add(months[m.getValue()-1]);
        }

        int year = LocalDate.now().getYear();
        for (int y = year-5; y <= year+20; y++) {
            yearBox.getItems().add(y);
        }

        LocalDate today = LocalDate.now();
        monthBox.getSelectionModel().select(today.getMonthValue() - 1);
        yearBox.setValue(today.getYear());

        monthBox.setOnAction(e -> updateCalendar());
        yearBox.setOnAction(e -> updateCalendar());
        calendar.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> overlay.getChildren().clear());

        updateCalendar();
    }

    @FXML
    public void onScheduleAppointmentButtonClick(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/utsa/cs3443/easyconnect/layouts/appointmentCreation.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("New Appointment");
        stage.initModality(Modality.APPLICATION_MODAL);          // Blocks the main window until closed
        stage.initOwner(calendar.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void updateCalendar() {

        calendar.getChildren().clear();
        overlay.getChildren().clear();

        for (int c = 0; c < 7; c++) {
            Label label = new Label(days[c]);
            label.setAlignment(Pos.CENTER);
            label.getStyleClass().add("component-Text");
            label.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            calendar.add(label, c, 0);
        }

        YearMonth ym = YearMonth.of((int) yearBox.getValue(), monthBox.getSelectionModel().getSelectedIndex() + 1);
        int offset = ym.atDay(1).getDayOfWeek().getValue() % 7;

        ArrayList<Appointment> appointments;

        if (session.isPatient()) {
            Patient patient = (Patient) session.getCurrentUser();
            appointments = patient.getAppointments();
        }
        else {
            Doctor doctor = (Doctor) session.getCurrentUser();
            appointments = doctor.getAppointments();
        }

        LocalDate today = LocalDate.now();

        for (int day = 1; day <= ym.lengthOfMonth(); day++) {
            LocalDate date = ym.atDay(day);

            boolean hasAppointment = false;
            for (Appointment a : appointments) {
                if (date.toString().equals(a.getDate())) {
                    hasAppointment = true;
                    break;
                }
            }

            Label label = new Label(String.valueOf(day));
            if (hasAppointment) {
                label.getStyleClass().addAll("button-Background", "button-Text");
            } else if (date.equals(today)) {
                label.getStyleClass().addAll("component-Background");
                label.setStyle("-fx-background-color: #F1F1F1;");
            } else {
                label.getStyleClass().add("component-Text");
            }

            label.setAlignment(Pos.CENTER);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setMaxHeight(Double.MAX_VALUE);

            label.setOnMouseClicked(e -> showDayInfo(label, date, appointments));
            label.setCursor(Cursor.HAND);   // optional: hand cursor on hover

            int cell = offset + day - 1;
            calendar.add(label, cell % 7, 1 + cell / 7);
        }
    }

    private void showDayInfo(Label anchor, LocalDate date, ArrayList<Appointment> appointments) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 6;"
                + "-fx-border-color: #bbbbbb; -fx-border-radius: 6;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");

        Label title = new Label(date.format(DateTimeFormatter.ofPattern("EEE, MMM d")));
        title.setStyle("-fx-font-weight: bold;");
        box.getChildren().add(title);

        boolean found = false;
        for (Appointment a : appointments) {
            if (date.toString().equals(a.getDate())) {
                Label label = new Label();

                User other;

                if (session.isPatient()) other = Session.getDataHandler().getDoctor(a.getDoctorID());
                else other = Session.getDataHandler().getPatient(a.getPatientId());

                label.setText(String.format("%s at %s with %s.", a.getDate(), a.getTime(), other.getName()));

                box.getChildren().add(label);
                found = true;
            }
        }
        if (!found) {
            box.getChildren().add(new Label("No appointments"));
        }

        overlay.getChildren().setAll(box);     // replaces any box that's already showing

        // Size it now so we can keep it inside the calendar's edges
        box.applyCss();
        box.autosize();

        Bounds b = overlay.sceneToLocal(anchor.localToScene(anchor.getBoundsInLocal()));
        double x = Math.min(b.getMinX(), overlay.getWidth() - box.getWidth());
        double y = b.getMaxY();
        if (y + box.getHeight() > overlay.getHeight()) {
            y = b.getMinY() - box.getHeight();   // not enough room below, so open above
        }
        box.setLayoutX(Math.max(0, x));
        box.setLayoutY(Math.max(0, y));
    }

    @FXML
    private void openCreateAppointment() {
        Stage stage = new Stage();
        stage.setTitle("New Appointment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(calendar.getScene().getWindow());

        DatePicker datePicker = new DatePicker(LocalDate.now());

        ComboBox<String> timeBox = new ComboBox<>();
        for (LocalTime t = LocalTime.of(8, 0); t.isBefore(LocalTime.of(17, 30)); t = t.plusMinutes(30)) {
            timeBox.getItems().add(t.toString());          // 08:00, 08:30, ...
        }
        timeBox.setPromptText("Select time");

        TextField notesField = new TextField();
        notesField.setPromptText("Reason / notes");

        Label error = new Label();
        error.setStyle("-fx-text-fill: red;");

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> stage.close());

        save.setOnAction(e -> {
            if (datePicker.getValue() == null || timeBox.getValue() == null) {
                error.setText("Please choose a date and time.");
                return;
            }

            String date = datePicker.getValue().toString();    // yyyy-MM-dd, matches your calendar
            String time = timeBox.getValue();
            String notes = notesField.getText();

            // TODO: build your Appointment and add it to the current user's list.
            // Appointment appt = new Appointment(date, time, notes);
            // Session session = Session.getInstance();
            // if (session.isPatient()) ((Patient) session.getCurrentUser()).getAppointments().add(appt);
            // else ((Doctor) session.getCurrentUser()).getAppointments().add(appt);

            stage.close();
            updateCalendar();      // redraw so the new date gets highlighted
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Date:"), datePicker);
        form.addRow(1, new Label("Time:"), timeBox);
        form.addRow(2, new Label("Notes:"), notesField);

        HBox buttons = new HBox(10, save, cancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(15, form, error, buttons);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
