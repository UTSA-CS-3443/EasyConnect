package edu.utsa.cs3443.easyconnect.model;

import java.util.ArrayList;

public class Doctor extends User implements toCSV {

    private ArrayList<Appointment> appointments;
    private ArrayList<String> patients;
    private ArrayList<String> notes;

    public Doctor(String name, String password, String email, String address, String gender,
                  String dateOfBirth, String phoneNumber, int id, ArrayList<Appointment> appointments) {
        super(name, password, email, address, gender, dateOfBirth, phoneNumber, id);

        this.appointments = appointments;
        this.patients = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void prescribeMedication(Patient patient, Medication medication) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient is required");
        }
        if (medication == null) {
            throw new IllegalArgumentException("Medication is required");
        }

        patient.getMedications().add(medication);
    }

    public void writeNote(Patient patient, String note) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient is required");
        }

        if (note == null || note.trim().isEmpty()) {
            throw new IllegalArgumentException("Note cannot be empty");
        }

        patient.getNotes().add(note.trim());
    }

    //Check later
    @Override
    public String toCSV() {
        return null;
    }
}
