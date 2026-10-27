package edu.utsa.cs3443.easyconnect.model;

import java.util.ArrayList;

public class MedicalRecord implements toCSV {

    ArrayList<String> symptoms;
    ArrayList<Medication> medications;
    String medicalHistory;
    String filePath;

    public MedicalRecord(String m, String f) {
        medicalHistory = m;
        filePath = f;
        symptoms = new ArrayList<>();
        medications = new ArrayList<>();
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public void setMedications(ArrayList<Medication> medications) {
        this.medications = medications;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    public Medication getMedication(String name) {
        for (Medication m: medications)
            if (m.getName().equals(name))
                return m;
        return null;
    }

    public Medication removeMedication(String name) {
        for (int i = 0; i < medications.size(); i++) {
            Medication medication = medications.get(i);
            if(medication.getName().equals(name)) {
                medications.remove(i);
                return medication;
            }
        }
        return null;
    }

    public void addSymptom(String symptom) {
        symptoms.add(symptom);
    }

    public String removeSymptom(String name) {
        for (int i = 0; i < symptoms.size(); i++) {
            String symptom = symptoms.get(i);
            if(symptom.equals(name)) {
                symptoms.remove(i);
                return symptom;
            }
        }
        return null;
    }

    public String toCSV() {
        return null;
    }
}
