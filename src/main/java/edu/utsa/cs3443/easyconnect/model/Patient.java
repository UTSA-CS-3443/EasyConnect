package edu.utsa.cs3443.easyconnect.model;
import java.util.ArrayList;
public class Patient extends User  {
    private ArrayList<Appointment> appointments;
    private MedicalRecord medicalRecord;
    private double balance;
    private ArrayList<String> notes;
    private ArrayList<Medication> medications;
    public Patient(String n, String p, String e, String a, String g, String d, String pN, int i, ArrayList<Appointment> appointments, ArrayList<Medication> medications, ArrayList<String> notes, MedicalRecord record, double balance) {
        super(n, p, e, a, g, d, pN, i);
        this.appointments = (appointments != null) ? appointments : new ArrayList<>();
        this.medications = (medications != null) ? medications : new ArrayList<>();
        this.medicalRecord = record;
        this.balance = balance;
        this.notes = (notes != null) ? notes : new ArrayList<>();

    }





}
