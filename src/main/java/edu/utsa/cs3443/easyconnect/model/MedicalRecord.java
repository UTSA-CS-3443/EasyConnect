package edu.utsa.cs3443.easyconnect.model;

import java.util.ArrayList;

public class MedicalRecord implements toCSV {

    private ArrayList<String> symptoms;
    private ArrayList<Medication> medications;
    private String medicalHistory;
    private String filePath;
    private String id;

    public MedicalRecord(String id, String m, String f) {
        this.id = id;
        medicalHistory = m;
        filePath = f;
        symptoms = new ArrayList<>();
        medications = new ArrayList<>();
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
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
    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",\n");
        sb.append("MEDICAL RECORD\n");
        sb.append("     Patient ID: ").append(id).append("\n\n");

        sb.append("     MEDICAL HISTORY\n");
        if(medicalHistory != null && !medicalHistory.trim().isEmpty()) {
            sb.append("- ").append(medicalHistory).append("\n\n");
        }
        else{
            sb.append("- \n\n");
        }
        sb.append("     CURRENT MEDICATIONS\n");
        if(medications != null && !medications.isEmpty()){
            for(Medication m: medications){
                sb.append("- ").append(m.toString()).append("\n\n");
            }
        }
        else{
            sb.append("- \n\n");
        }
        sb.append("     CURRENT SYMPTOMS\n");
        if(symptoms != null && !symptoms.isEmpty()){
            for(String s: symptoms){
                sb.append("- ").append(s).append("\n\n");
            }
        }
        else{
            sb.append("- \n\n");
        }
        return sb.toString().trim();
    }
}
