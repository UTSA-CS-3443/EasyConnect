package edu.utsa.cs3443.easyconnect.model;

public class Note implements toCSV{

    private String note;
    private String patientId;
    private String doctorId;

    public Note(String patientId, String doctorId, String note) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPatientId(String id) {
        this.patientId = id;
    }

    public void setDoctorId(String id) {
        this.doctorId = id;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s", patientId, doctorId, note);
    }
}
