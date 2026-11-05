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

}
