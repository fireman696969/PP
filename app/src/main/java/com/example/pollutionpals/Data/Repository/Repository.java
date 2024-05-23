package com.example.pollutionpals.Data.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.DB.ReportsDatabase;

public class Repository {
    MyDatabaseHelper DbCitizens;
    ReportsDatabase DbReports;
    SharedPreferences sharedPreference;
    public Repository(Context context){
        DbCitizens = new MyDatabaseHelper(context);
        DbReports = new ReportsDatabase(context);
        sharedPreference = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }
    // connects to sharedprefrence
    public String getId(){
        return sharedPreference.getString("Id", "");

    }
    public void updateSharedPreference(String userName, int age, int points, String id, String pass){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("UserName", userName);
        editor.putInt("Age", age);
        editor.putInt("Points", points);
        editor.putString("Id", id);
        editor.putString("Pass", pass);
        editor.apply();
    }
    public void logOut(){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.remove("UserName");
        editor.clear();
        editor.apply();
    }
    public SharedPreferences getSharedPreference(){
        return sharedPreference;
    }
    public boolean isLoggedIn(){
        return sharedPreference.contains("UserName");
    }

    // connects to reports database
    public Cursor getAllReports(){
        return DbReports.GetAllReports();
    }
    public Cursor getReportsById(String id){
        return DbReports.GetReportsById(id);
    }
    public void updateReportStatus(String reportId, String status){
        DbReports.updateReportStatus(reportId, status);
    }
    public void addReport(String id, byte[] imgsrc, String Description, String Location, String Date, Integer Points ){
        DbReports.AddReport(id, imgsrc, Description, Location, Date, Points);
    }



    // connects to citizens database
    public void AddCitizen(String Fullname, int Age, String Id, String Pass){
        DbCitizens.AddCitizen(Fullname, Age , Id, Pass);
    }
    public void updatePointsById(String id, int points){
        DbCitizens.UpdatePointsById(id, points);
    }
    public Cursor getUserById(String id){
        return DbCitizens.getUserById(id);
    }
    public boolean CheckIfAlreadyExists(String IdNumber){
        return DbCitizens.CheckIfAlreadyExists(IdNumber);
    }


}
