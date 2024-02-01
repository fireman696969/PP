package com.example.pollutionpals.Data.Repository;

import android.content.Context;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;

public class Repository {
    MyDatabaseHelper DbCitizens;
    public Repository(Context context){
        DbCitizens = new MyDatabaseHelper(context);
    }
    public void AddCitizen(String Fname, String Lname, int Age, String Address, String Email, String Id, String Pass){
        DbCitizens.AddCitizen(Fname, Lname, Age , Address , Email , Id, Pass);
    }
}
