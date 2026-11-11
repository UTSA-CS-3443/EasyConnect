package edu.utsa.cs3443.easyconnect.model;

import java.util.ArrayList;
import java.io.IOException;

public class Patient extends User implements toCSV {
    private ArrayList<Appointment> appointments;
    private MedicalRecord medicalRecord;
    private Payment payment;
    private ArrayList<Note> notes;
    private ArrayList<Medication> medications;
    public Patient(String id, String n, String p, String e, String a, String g, String d, String pN, Payment payment, ArrayList<Appointment> appointments, ArrayList<Medication> medications, ArrayList<Note> notes, MedicalRecord record) {
        super(id, n, p, e, a, g, d, pN);
        this.appointments = (appointments != null) ? appointments : new ArrayList<>();
        this.medications = (medications != null) ? medications : new ArrayList<>();
        this.medicalRecord = record;
        this.payment = payment;
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

    public ArrayList<Note> getNotes() {
        return notes;
    }
    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
    public ArrayList<Medication> getMedications() {
        return medications;
    }
    public Appointment getFirstAppointment() {
        return appointments.get(0);
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

    public void scheduleAppointment(String piD, String diD, String d, String t) {
        this.appointments.add(new Appointment(piD,diD, d,t));
    }
    public void makePayment(double p){
        if(p <= 0){
            throw new IllegalArgumentException("Payment must be greater than 0");

        }
        if(p > payment.getAmount()){
            throw new IllegalArgumentException("Payment is more than the balance due");
        }
        payment.setAmount(payment.getAmount()-p);
        //Note use toCSV
    }

    public String toCSV(){

        return "";
    }
    public Medication getFirstMedication(){
        return medications.get(0);
    }







}
