package edu.utsa.cs3443.easyconnect.model;

import java.util.ArrayList;
import java.io.IOException;
public class Patient extends User implements toCSV {
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

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public ArrayList<String> getNotes() {
        return notes;
    }
    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
    public ArrayList<Medication> getMedications() {
        return medications;
    }
    public void setMedications(ArrayList<Medication> medications) {
        this.medications = medications;
    }
    /*
    NOTE* Work on this later
    @Override
    public String toString() {
        return
    }

     */
    public void submitForm(MedicalRecord newRecord) {
        this.medicalRecord = newRecord;
    }

    public void scheduleAppointment(String d, String t) {
        this.appointments.add(new Appointment(d,t));
    }
    public void makePayment(double p){
        if(p <= 0){
            throw new IllegalArgumentException("Payment must be greater than 0");

        }
        if(p > balance){
            throw new IllegalArgumentException("Payment is more than the balance due");
        }
        balance = balance - p;
        //Note use toCSV
    }

    public String toCSV(){

        return "";
    }







}
