package com.example.pollutionpals.UI.MainPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.Toast;

import com.example.pollutionpals.Data.Repository.Repository;

public class MainModule {
    Repository rep;
    public Context context;
    SharedPreferences sharedPreference;

    public MainModule(Context context) {
        rep = new Repository(context);
        this.context = context;
        sharedPreference = rep.getSharedPreference();
    }
    public String GetUserName(){
        String Username = sharedPreference.getString("UserName", "UserName");


        return Username;
    }
    public int GetUserPoints(){
        return sharedPreference.getInt("Points", -1);
    }
    public String GetUserId(){
        return sharedPreference.getString("Id", "1");
    }

    public SharedPreferences getSharedPreference() {
        return rep.getSharedPreference();
    }
}
