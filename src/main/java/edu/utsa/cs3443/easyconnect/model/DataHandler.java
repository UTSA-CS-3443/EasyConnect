package edu.utsa.cs3443.easyconnect.model;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandler {

    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private ArrayList<User> users;
    private ArrayList<Appointment> appointments;
    private ArrayList<String> notes;
    private ArrayList<Medication> medications;
    private final String doctorFile = "data/doctor.csv";
    private final String userFile = "data/user.csv";
    private final String medicalRecordFile = "data/medicalRecord.csv";
    private final String appointmentFile = "data/appointment.csv";
    private final String medicationFile = "data/medication.csv";
    private final String noteFile = "data/note.csv";

    public DataHandler() {
        patients = new ArrayList<>();
    }

    public void loadUserFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("data/patient.csv"))){
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] s = line.split(",");
                User u = new User(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8]);
                if(s[1].equals("Patient")) {
                    String id = s[0];

                    MedicalRecord mr = getMedicalRecord(id);
                    ArrayList<Appointment> appointments = getAppointments(id);
                    ArrayList<String> notes = getNotes(id);
                    ArrayList<Medication> medications = getMedications(id);

                    //id,name,password,email,address,gender,dateOfBirth,phoneNumber,balance,appointments,medications,notes,medicalRecord
                    patients.add(new Patient(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8], Double.parseDouble(s[9]), appointments, medications, notes, mr));
                }
                else if(s[1].equals("Doctor")){
//                    doctors.add(new Doctor(s[0], s[2], s[3], s[4], s[5], s[6], s[7], s[8],));
                }
                users.add(u);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public MedicalRecord getMedicalRecord(String id) throws FileNotFoundException {
        Scanner mRFile = new Scanner(new File(medicalRecordFile));
        MedicalRecord mr;

        while(mRFile.hasNext()) {
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

    public ArrayList<Appointment> getAppointments(String id) throws IOException {
        Scanner aFile = new Scanner(new File(appointmentFile));
        ArrayList<Appointment> appointments1 = new ArrayList<>();

        while(aFile.hasNext()) {
            String[] aLine = aFile.nextLine().split(",");
            if (id.equals(aLine[0])) {
                Appointment a = new Appointment(id, aLine[1], aLine[2], aLine[3]);
                appointments1.add(a);
                appointments.add(a);
            }
        }

        aFile.close();
        return appointments1;
    }

    public ArrayList<String> getNotes(String id) throws IOException {
        Scanner nFile = new Scanner(new File(noteFile));
        ArrayList<String> notes1 = new ArrayList<>();

        while(nFile.hasNext()) {
            String[] nLine = nFile.nextLine().split(",");
            if (id.equals(nLine[0])) {
                notes1.add(nLine[1]);
                notes.add(nLine[1]);
            }
        }

        nFile.close();
        return notes1;
    }

    public ArrayList<Medication> getMedications(String id) throws IOException {
        Scanner mFile = new Scanner(new File(medicationFile));
        ArrayList<Medication> medications1 = new ArrayList<>();

        while(mFile.hasNext()) {
            String[] mLine = mFile.nextLine().split(",");
            if (id.equals(mLine[0])) {
                Medication a = new Medication(id, mLine[1], mLine[2], Integer.parseInt(mLine[3]));
                medications1.add(a);
                medications.add(a);
            }
        }

        mFile.close();
        return medications1;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

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
}
