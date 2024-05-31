package com.example.pollutionpals.UI.UpdateInfoPage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pollutionpals.Data.Repository.Repository;

/**
 * UpdateInfoModule class provides methods for updating user information.
 */
public class UpdateInfoModule {
    Context context;
    Repository repository;

    /**
     * Constructor for UpdateInfoModule class.
     *
     * @param context The context of the application.
     */
    public UpdateInfoModule(Context context) {
        this.context = context;
        this.repository = new Repository(context);
    }

    /**
     * Retrieves the shared preferences object from the repository.
     *
     * @return The SharedPreferences object.
     */
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreference();
    }
    public void UpdateInfoById(String id, String fullname, int age, String pass){
        repository.UpdateInfoById(id,fullname,age,pass);
    }
    public void updateSharedPreference(String userName, int age, int points, String id, String pass){
        repository.updateSharedPreference(userName,age,points,id,pass);
    }
    public SharedPreferences getSharedPreference(){
        return repository.getSharedPreference();
    }
}


//package com.example.pollutionpals.UI.UpdateInfoPage;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.example.pollutionpals.Data.Repository.Repository;
//
//public class UpdateInfoModule {
//    Context context;
//    Repository repository;
//    public UpdateInfoModule(Context context) {
//        this.context = context;
//        this.repository = new Repository(context);
//    }
//    public SharedPreferences getSharedPreferences() {
//        return repository.getSharedPreference();
//    }
//
//}
