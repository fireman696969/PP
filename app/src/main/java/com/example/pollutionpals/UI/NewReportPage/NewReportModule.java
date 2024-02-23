package com.example.pollutionpals.UI.NewReportPage;

import android.content.Context;

import com.example.pollutionpals.Data.Repository.Repository;

public class NewReportModule {

    Repository rep;
    public Context context;

    public NewReportModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }
    public void AddPhoto(){


    }

}
