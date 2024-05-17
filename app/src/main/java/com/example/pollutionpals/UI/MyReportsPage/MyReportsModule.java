package com.example.pollutionpals.UI.MyReportsPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.pollutionpals.Data.Repository.Repository;

public class MyReportsModule {
    Repository rep;
    public Context context;

    public MyReportsModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }
    public String getId(){
        return rep.getId();
    }
    public Cursor getAllReports(){
        return rep.getAllReports();
    }
    public Cursor getReportsById(String id){
        return rep.getReportsById(id);
    }
    public void updatePointsById(String id, int points){
        rep.updatePointsById(id, points);
    }
    public void updateReportStatus(String reportId, String status){
        rep.updateReportStatus(reportId, status);
    }
    public Cursor getUserById(String id){
        return rep.getUserById(id);
    }
    public void updateSharedPreference(String userName, int age, int points, String id, String pass){
        rep.updateSharedPreference(userName, age, points, id, pass);
    }

}
