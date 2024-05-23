package com.example.pollutionpals.UI.UpdateInfoPage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pollutionpals.Data.Repository.Repository;

public class UpdateInfoModule {
    Context context;
    Repository repository;
    public UpdateInfoModule(Context context) {
        this.context = context;
        this.repository = new Repository(context);
    }
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreference();
    }

}
