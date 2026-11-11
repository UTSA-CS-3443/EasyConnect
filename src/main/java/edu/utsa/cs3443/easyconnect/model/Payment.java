package edu.utsa.cs3443.easyconnect.model;

public class Payment implements toCSV{

    private double amount;
    private String date;
    private String patientId;

    public Payment(String patientId, double amount, String date) {
        this.patientId = patientId;
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPatientId(String id) {
        this.patientId = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%.2f,%s", patientId, amount, date);
    }
}
