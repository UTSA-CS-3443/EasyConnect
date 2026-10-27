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


}
