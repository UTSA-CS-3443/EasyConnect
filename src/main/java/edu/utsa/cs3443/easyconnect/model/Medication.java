package edu.utsa.cs3443.easyconnect.model;

public class Medication implements toCSV{

    String name;
    String dosage;
    int frequency;
    private String patientID;
    private String doctorID;

    public Medication(String patientID, String doctorID, String n, String d, int f) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        name = n;
        dosage = d;
        frequency = f;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getPatientID(){
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setPatientID(String id){
        this.patientID = id;
    }

    public void setDoctorID(String id) {
        this.doctorID = id;
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%s,%d", patientID, doctorID, name, dosage, frequency);
    }
}
