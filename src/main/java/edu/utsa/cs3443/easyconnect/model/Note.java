package edu.utsa.cs3443.easyconnect.model;

public class Note implements toCSV{

    private String note;
    private String id;

    public Note(String id, String note) {
        this.id = id;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String getId() {
        return id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%s", id, note);
    }
}
