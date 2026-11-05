package edu.utsa.cs3443.easyconnect.model;

public class Medication implements toCSV{

    String name;
    String dosage;
    int frequency;

    private String id;

    public Medication(String id, String n, String d, int f) {
        this.id = id;
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

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String toCSV() {
        return null;
    }
}
