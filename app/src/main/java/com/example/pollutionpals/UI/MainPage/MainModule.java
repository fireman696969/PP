package com.example.pollutionpals.UI.MainPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.pollutionpals.Data.Repository.Repository;

public class MainModule {
    Repository rep;
    public Context context;
    SharedPreferences sharedPreference;

    public MainModule(Context context) {
        rep = new Repository(context);
        this.context = context;
        sharedPreference = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }
    public String GetUserName(){
        String Username = sharedPreference.getString("UserName", "UserName");
        return Username;

    }


}
