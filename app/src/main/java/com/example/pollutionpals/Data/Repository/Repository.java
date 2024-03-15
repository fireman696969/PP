package com.example.pollutionpals.Data.Repository;

import android.content.Context;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;

public class Repository {
    MyDatabaseHelper DbCitizens;
    public Repository(Context context){
        DbCitizens = new MyDatabaseHelper(context);
    }
    public void AddCitizen(String Fullname, int Age, String Id, String Pass){
        DbCitizens.AddCitizen(Fullname, Age , Id, Pass);
    }
}
