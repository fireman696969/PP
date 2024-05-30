package com.example.pollutionpals.UI.MyProfilePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import com.example.pollutionpals.Data.Repository.Repository;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;

/**
 * MyProfileModule class handles operations related to the user profile page.
 * It provides functionalities such as logging out.
 */
public class MyProfileModule {

    // Instance of Repository for accessing user data
    Repository rep;
    // Application context
    public Context context;

    /**
     * Constructor to initialize MyProfileModule.
     *
     * @param context The context of the calling activity.
     */
    public MyProfileModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }

    /**
     * Logs out the current user by clearing shared preferences.
     */
    public void LogOut(){
        rep.logOut();
    }
    public void deleteUser(){
        rep.deleteUser();
    }
}
