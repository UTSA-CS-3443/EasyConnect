package edu.utsa.cs3443.easyconnect.model;

public class Appointment implements toCSV {

    private String date;
    private String time;
    private String patientId;
    private String doctorID;
    public Appointment(String pId, String dId, String d, String t) {
        patientId = pId;
        doctorID = dId;
        date = d;
        time = t;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatientId(){
        return patientId;
    }

    public void setPatientId(String id){
        patientId = id;
    }

    public String getDoctorID(){
        return doctorID;
    }

    public void setDoctorID(String id){
        doctorID = id;
    }

    public String toCSV() {
        return null;
    }
}
