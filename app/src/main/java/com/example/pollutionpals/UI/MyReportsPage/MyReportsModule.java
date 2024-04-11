package com.example.pollutionpals.UI.MyReportsPage;

import android.content.Context;

import com.example.pollutionpals.Data.Repository.Repository;

public class MyReportsModule {
    Repository rep;
    public Context context;

    public MyReportsModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }

}
