package edu.utsa.cs3443.easyconnect.model;

public class Appointment implements toCSV {

    String date;
    String time;

    public Appointment(String d, String t) {
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

    public String toCSV() {
        return null;
    }
}
