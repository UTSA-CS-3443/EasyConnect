package edu.utsa.cs3443.easyconnect.model;

import java.awt.peer.ListPeer;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandler {

    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private ArrayList<User> users;
    private final String userFile = "data/user.csv";
    private final String medicalRecordFile = "data/medicalRecord.txt";
    private final String appointmentFile = "data/appointment.csv";
    private final String medicationFile = "data/medication.csv";
    private final String noteFile = "data/note.csv";

    public DataHandler() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        users = new ArrayList<>();
    }

    public User findUsers(String email, String password){
        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    /**
     * Saves all information to the data files.
     */
    public void save() {
        try {
            saveUsers();
            saveMedicalRecords();
            saveMedications();
            saveAppointments();
            saveNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the given patient to the list
     *
     * @param patient the patient we want to add
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Adds the given doctor to the list
     *
     * @param doctor the doctor we want to add
     */
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    /**
     * Returns the index of the patient found using the passed id
     *
     * @param id the id of the patient
     * @return the index the patient is found at. {@code -1} if not found.
     */
    public int findPatient(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if(patients.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }

    /**
     * Returns the index of the doctor found using the passed id
     *
     * @param id the id of the doctor
     * @return the index the doctor is found at. {@code -1} if not found.
     */
    public int findDoctor(String id) {
        for (int i = 0; i < doctors.size(); i++) {
            if(doctors.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }

    /**
     * Finds and returns the patient object based on the id.
     *
     * @param id The id of the patient
     * @return the patient object searched for. {@code null} if not found
     */
    public Patient getPatient(String id) {
        int index = findPatient(id);
        if(index != -1)
            return patients.get(index);
        else return null;
    }

    /**
     * Finds and returns the doctor object based on its id.
     *
     * @param id The id of the doctor
     * @return the doctor object searched for. {@code null} if not found
     */
    public Doctor getDoctor(String id) {
        int index = findDoctor(id);
        if(index != -1)
            return doctors.get(index);
        else return null;
    }

    /**
     * Deletes a patient based on the passed id.
     *
     * @param id The id of the patient
     * @return {@code true} if the deletion was successful. {@code false} if not.
     */
    public boolean deletePatient(String id) {
        int index = findPatient(id);
        if(index == -1)
            return false;
        return patients.remove(index) != null;
    }

    /**
     * Deletes a doctor based on the passed id.
     *
     * @param id The id of the doctor
     * @return {@code true} if the deletion was successful. {@code false} if not.
     */
    public boolean deleteDoctor(String id) {
        int index = findDoctor(id);
        if(index == -1)
            return false;
        return doctors.remove(index) != null;
    }

    /**
     * Updates a patient's information with a passed patient object created in the controller
     *
     * @param p New patient with values we want to copy
     */
    public void updatePatient(Patient p) {
        patients.set(findPatient(p.getId()), p);
    }

    /**
     * Updates a doctor's information with a passed doctor object created in the controller
     *
     * @param d New doctor with values we want to copy
     */
    public void updateDoctor(Doctor d) {
        doctors.set(findDoctor(d.getId()), d);
    }


    /**
     * Loads all patients from the user file. Finds relevant information in other files.
     */
    public void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/user.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                if (s[1].equals("patient")) {
                    String id = s[0];

                    MedicalRecord mr = loadMedicalRecord(id);
                    ArrayList<Appointment> appointments = loadAppointments(id, true);
                    ArrayList<Note> notes = loadNotes(id, true);
                    ArrayList<Medication> medications = loadMedications(id, true);

                    //id,name,password,email,address,gender,dateOfBirth,phoneNumber,balance,appointments,medications,notes,medicalRecord
                    patients.add(new Patient(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8], Double.parseDouble(s[9]), appointments, medications, notes, mr));
                    users.add(new User(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all doctors from the user file. Finds relevant information in other files.
     */
    public void loadDoctors() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/user.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                if (s[1].equals("doctor")) {
                    String id = s[0];

                    ArrayList<Appointment> appointments = loadAppointments(id, false);
                    ArrayList<Note> notes = loadNotes(id, false);

                    //id,name,password,email,address,gender,dateOfBirth,phoneNumber,appointments,notes
                    doctors.add(new Doctor(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8], appointments, notes));
                    users.add(new User(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all user information to the user file. Both patients and doctors.
     *
     * @throws IOException if the writer encounters any issues.
     */
    public void saveUsers() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(userFile));

        for(Patient p: patients) {
            writer.write(p.toCSV());
            writer.newLine();
        }

        for(Doctor d: doctors) {
            writer.write(d.toCSV());
            writer.newLine();
        }

        writer.close();
    }

    /**
     * Returns a medical record based on the given id. Searches the medical record file.
     *
     * @param id The id whose medical record is being searched for.
     * @return the medical record if found. Otherwise, returns {@code null}.
     * @throws FileNotFoundException if the medical record file is not found correctly.
     */
    public MedicalRecord loadMedicalRecord(String id) throws FileNotFoundException {
        Scanner mRFile = new Scanner(new File(medicalRecordFile));
        MedicalRecord mr;

        while (mRFile.hasNext()) {
            String[] mRLine = mRFile.nextLine().split(",");
            if (id.equals(mRLine[0])) {
                // mr = new MedicalRecord()
                mRFile.close();
                //return mr;
            }
        }
        mRFile.close();
        return null;
    }

    /**
     * Saves all medical record data to the medical record file. Uses the toCSV method.
     *
     * @throws IOException if the writer encounters any issues.
     */
    public void saveMedicalRecords() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(medicalRecordFile));

        for(Patient p: patients) {
            writer.write(p.getMedicalRecord().toCSV());
            writer.newLine();
        }

        writer.close();
    }

    /**
     * Loads the appointments associated with a specific id. Can be a patient or doctor.
     *
     * @param id The patient or doctor id.
     * @param isPatient Whether the id is a patient or doctor.
     * @return Returns a list of the appointments.
     * @throws IOException if the loader encounters any issues.
     */
    public ArrayList<Appointment> loadAppointments(String id, boolean isPatient) throws IOException {
        Scanner aFile = new Scanner(new File(appointmentFile));
        ArrayList<Appointment> appointments = new ArrayList<>();

        while (aFile.hasNext()) {
            String[] aLine = aFile.nextLine().split(",");
            if (isPatient && id.equals(aLine[0])) {
                Appointment a = new Appointment(id, aLine[1], aLine[2], aLine[3]);
                appointments.add(a);
            }
            else if (!isPatient && id.equals(aLine[1])) {
                Appointment a = new Appointment(aLine[0], id, aLine[2], aLine[3]);
                appointments.add(a);
            }
        }

        aFile.close();
        return appointments;
    }

    /**
     * Saves all appointment data to the appointment file. Uses the toCSV method.
     *
     * @throws IOException if the writer encounters any issues.
     */
    public void saveAppointments() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentFile));

        for(Patient p: patients) {
            for(Appointment a: p.getAppointments()) {
                writer.write(a.toCSV());
                writer.newLine();
            }
        }

        writer.close();
    }

    /**
     * Loads the notes associated with a specific id. Can be a patient or doctor.
     *
     * @param id The patient or doctor id.
     * @param isPatient Whether the id is a patient or doctor.
     * @return Returns a list of the notes.
     * @throws IOException if the loader encounters any issues.
     */
    public ArrayList<Note> loadNotes(String id, boolean isPatient) throws IOException {
        Scanner nFile = new Scanner(new File(noteFile));
        ArrayList<Note> notes = new ArrayList<>();

        while (nFile.hasNext()) {
            String[] nLine = nFile.nextLine().split(",");
            if (isPatient && id.equals(nLine[0])) {
                Note n = new Note(id, nLine[1], nLine[2]);
                notes.add(n);
            }
            else if(!isPatient && id.equals(nLine[1])) {
                Note n = new Note(nLine[0], id, nLine[2]);
                notes.add(n);
            }
        }

        nFile.close();
        return notes;
    }

    /**
     * Saves all note data to the note file. Uses the toCSV method.
     *
     * @throws IOException if the writer encounters any issues.
     */
    public void saveNotes() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(noteFile));

        for(Patient p: patients) {
            for(Note n: p.getNotes()) {
                writer.write(n.toCSV());
                writer.newLine();
            }
        }
    }

    /**
     * Loads the medications associated with a specific id. Can be a patient or doctor.
     *
     * @param id The patient or doctor id.
     * @param isPatient Whether the id is a patient or doctor.
     * @return Returns a list of the medications.
     * @throws IOException if the loader encounters any issues.
     */
    public ArrayList<Medication> loadMedications(String id, boolean isPatient) throws IOException {
        Scanner mFile = new Scanner(new File(medicationFile));
        ArrayList<Medication> medications = new ArrayList<>();

        while (mFile.hasNext()) {
            String[] mLine = mFile.nextLine().split(",");
            if (isPatient && id.equals(mLine[0])) {
                Medication a = new Medication(id, mLine[1], mLine[2], mLine[3], Integer.parseInt(mLine[3]));
                medications.add(a);
            }
            else if(!isPatient && id.equals(mLine[1])) {
                Medication a = new Medication(mLine[0], id, mLine[2], mLine[3], Integer.parseInt(mLine[3]));
                medications.add(a);
            }
        }

        mFile.close();
        return medications;
    }

    /**
     * Saves all medication data to the medication file. Uses the toCSV method.
     *
     * @throws IOException if the writer encounters any issues.
     */
    public void saveMedications() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(medicationFile));

        for(Patient p: patients) {
            for(Medication m: p.getMedications()) {
                writer.write(m.toCSV());
                writer.newLine();
            }
        }

        writer.close();
    }
}