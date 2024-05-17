package com.example.pollutionpals.UI.MyProfilePage;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.pollutionpals.Data.Repository.Repository;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;

public class MyProfileModule {
    Repository rep;
    public Context context;

    public MyProfileModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }
    public void LogOut(){
        rep.logOut();
    }
}
