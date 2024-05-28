package com.example.pollutionpals.UI.SignUp;

import android.content.Context;

import com.example.pollutionpals.Data.Repository.Repository;

/**
 * SignUpModule class provides methods for signing up a new user.
 */
public class SignUpModule {
    Repository rep;
    public Context context;

    /**
     * Constructor for SignUpModule class.
     *
     * @param context The context of the application.
     */
    public SignUpModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }

    /**
     * Checks if a user with the given ID already exists in the database.
     *
     * @param id The ID to check.
     * @return True if the user already exists, otherwise false.
     */
    public boolean CheckIfAlreadyExists(String id) {
        return rep.CheckIfAlreadyExists(id);
    }

    /**
     * Adds a new citizen to the database.
     *
     * @param Fullname The full name of the user.
     * @param Age      The age of the user.
     * @param Id       The ID of the user.
     * @param Pass     The password of the user.
     */
    public void AddCitizen(String Fullname, int Age, String Id, String Pass) {
        rep.AddCitizen(Fullname, Age, Id, Pass);
    }
}


//package com.example.pollutionpals.UI.SignUp;
//
//import android.content.Context;
//
//import com.example.pollutionpals.Data.Repository.Repository;
//
//public class SignUpModule {
//    Repository rep;
//    public Context context;
//
//    public SignUpModule(Context context) {
//        rep = new Repository(context);
//        this.context = context;
//    }
//    public boolean CheckIfAlreadyExists(String id){
//        return rep.CheckIfAlreadyExists(id);
//    }
//    public void AddCitizen(String Fullname, int Age, String Id, String Pass){
//        rep.AddCitizen(Fullname, Age, Id, Pass);
//    }
//}
