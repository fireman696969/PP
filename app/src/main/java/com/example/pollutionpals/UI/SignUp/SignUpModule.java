package com.example.pollutionpals.UI.SignUp;

import android.content.Context;

import com.example.pollutionpals.Data.Repository.Repository;

public class SignUpModule {
    Repository rep;
    public Context context;

    public SignUpModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }
    public boolean CheckIfAlreadyExists(String id){
        return rep.CheckIfAlreadyExists(id);
    }
    public void AddCitizen(String Fullname, int Age, String Id, String Pass){
        rep.AddCitizen(Fullname, Age, Id, Pass);
    }
}
