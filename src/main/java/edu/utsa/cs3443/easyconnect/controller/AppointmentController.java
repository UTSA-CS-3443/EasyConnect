package edu.utsa.cs3443.easyconnect.controller;

import edu.utsa.cs3443.easyconnect.model.Appointment;
import edu.utsa.cs3443.easyconnect.model.Doctor;
import edu.utsa.cs3443.easyconnect.model.Patient;
import edu.utsa.cs3443.easyconnect.model.User;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Locale;

public class AppointmentController {

    @FXML ComboBox<String> monthBox;
    @FXML ComboBox<Integer> yearBox;
    @FXML GridPane calendar;

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

        updateCalendar();
    }

    @FXML
    public void onScheduleAppointmentButtonClick(ActionEvent e) {
        // Finish this function
        System.out.println("appointment scheduler");
    }

    private void updateCalendar() {
        Session session = Session.getInstance();
        calendar.getChildren().clear();

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

            int cell = offset + day - 1;
            calendar.add(label, cell % 7, 1 + cell / 7);
        }
    }
}
