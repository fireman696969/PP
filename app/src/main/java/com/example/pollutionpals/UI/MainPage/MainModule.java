package com.example.pollutionpals.UI.MainPage;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.pollutionpals.Data.Repository.Repository;

/**
 * MainModule class handles operations related to the main page of the application.
 * It retrieves user information and preferences from SharedPreferences via the Repository.
 */
public class MainModule {
    // Instance of Repository for accessing user data
    private Repository rep;
    // Context of the application
    public Context context;
    // SharedPreferences for storing user preferences
    private SharedPreferences sharedPreference;

    /**
     * Constructor for MainModule class.
     * Initializes Repository, context, and SharedPreferences.
     *
     * @param context The context of the application.
     */
    public MainModule(Context context) {
        // Initialize Repository
        rep = new Repository(context);
        this.context = context;
        // Retrieve SharedPreferences from Repository
        sharedPreference = rep.getSharedPreference();
    }

    /**
     * Retrieves the username of the logged-in user from SharedPreferences.
     *
     * @return The username of the logged-in user.
     */
    public String GetUserName() {
        // Retrieve username from SharedPreferences
        String Username = sharedPreference.getString("UserName", "UserName");
        return Username;
    }

    /**
     * Retrieves the points of the logged-in user from SharedPreferences.
     *
     * @return The points of the logged-in user.
     */
    public int GetUserPoints() {
        // Retrieve points from SharedPreferences
        return sharedPreference.getInt("Points", -1);
    }

    /**
     * Retrieves the ID of the logged-in user from SharedPreferences.
     *
     * @return The ID of the logged-in user.
     */
    public String GetUserId() {
        // Retrieve ID from SharedPreferences
        return sharedPreference.getString("Id", "1");
    }

    /**
     * Retrieves the SharedPreferences instance.
     *
     * @return The SharedPreferences instance.
     */
    public SharedPreferences getSharedPreference() {
        return rep.getSharedPreference();
    }
}


//
//
//package com.example.pollutionpals.UI.MainPage;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.example.pollutionpals.Data.Repository.Repository;
//
//public class MainModule {
//    Repository rep;
//    public Context context;
//    SharedPreferences sharedPreference;
//
//    public MainModule(Context context) {
//        rep = new Repository(context);
//        this.context = context;
//        sharedPreference = rep.getSharedPreference();
//    }
//    public String GetUserName(){
//        String Username = sharedPreference.getString("UserName", "UserName");
//
//
//        return Username;
//    }
//    public int GetUserPoints(){
//        return sharedPreference.getInt("Points", -1);
//    }
//    public String GetUserId(){
//        return sharedPreference.getString("Id", "1");
//    }
//
//    public SharedPreferences getSharedPreference() {
//        return rep.getSharedPreference();
//    }
//}
