package edu.utsa.cs3443.easyconnect.model;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandler {

    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;

    private final String doctorFile = "data/doctor.csv";
    private final String userFile = "data/user.csv";
    private final String medicalRecordFile = "data/medicalRecord.csv";
    private final String appointmentFile = "data/appointment.csv";
    private final String medicationFile = "data/medication.csv";
    private final String noteFile = "data/note.csv";

    public DataHandler() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
    }

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
     * Loads all the patient and doctor data from the files into the lists.
     *
     * Loads all other files as well to get respective data.
     */
    public void loadUserFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/patient.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                User u = new User(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8]);
                if (s[1].equals("Patient")) {
                    String id = s[0];

                    MedicalRecord mr = loadMedicalRecord(id);
                    ArrayList<Appointment> appointments = loadAppointments(id);
                    ArrayList<Note> notes = loadNotes(id);
                    ArrayList<Medication> medications = loadMedications(id);

                    //id,name,password,email,address,gender,dateOfBirth,phoneNumber,balance,appointments,medications,notes,medicalRecord
                    patients.add(new Patient(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8], Double.parseDouble(s[9]), appointments, medications, notes, mr));
                } else if (s[1].equals("Doctor")) {
//                    doctors.add(new Doctor(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8],));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
     * @return the medical record if found. Otherise returns {@code null}.
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

    public void saveMedicalRecords() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(medicalRecordFile));

        for(Patient p: patients) {
            writer.write(p.getMedicalRecord().toCSV());
            writer.newLine();
        }

        writer.close();
    }

    public ArrayList<Appointment> loadAppointments(String id) throws IOException {
        Scanner aFile = new Scanner(new File(appointmentFile));
        ArrayList<Appointment> appointments = new ArrayList<>();

        while (aFile.hasNext()) {
            String[] aLine = aFile.nextLine().split(",");
            if (id.equals(aLine[0])) {
                Appointment a = new Appointment(id, aLine[1], aLine[2], aLine[3]);
                appointments.add(a);
            }
        }

        aFile.close();
        return appointments;
    }

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

    public ArrayList<Note> loadNotes(String id) throws IOException {
        Scanner nFile = new Scanner(new File(noteFile));
        ArrayList<Note> notes = new ArrayList<>();

        while (nFile.hasNext()) {
            String[] nLine = nFile.nextLine().split(",");
            if (id.equals(nLine[0])) {
                Note n = new Note(nLine[0], nLine[1]);
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

    public ArrayList<Medication> loadMedications(String id) throws IOException {
        Scanner mFile = new Scanner(new File(medicationFile));
        ArrayList<Medication> medications = new ArrayList<>();

        while (mFile.hasNext()) {
            String[] mLine = mFile.nextLine().split(",");
            if (id.equals(mLine[0])) {
                Medication a = new Medication(id, mLine[1], mLine[2], Integer.parseInt(mLine[3]));
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
