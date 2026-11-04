package edu.utsa.cs3443.easyconnect.controller;
import edu.utsa.cs3443.easyconnect.model.Doctor;
import edu.utsa.cs3443.easyconnect.model.User;
import edu.utsa.cs3443.easyconnect.model.Patient;
public class Session {
    private static Session instance;
    private User currentUser;

    private Session(){}


    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void logout(){
        currentUser = null;
    }

    public boolean isLoggedIn(){
        return currentUser != null;
    }
    public boolean isDoctor(){
        return currentUser instanceof Doctor;
    }
    public boolean isPatient(){
        return currentUser instanceof Patient;
    }

}
