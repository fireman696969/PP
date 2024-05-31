package com.example.pollutionpals.Data.Repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.DB.ReportsDatabase;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
import com.example.pollutionpals.UI.MyProfilePage.MyProfilePage;

/**
 * Repository class that acts as a bridge between the application and the databases.
 */
public class Repository {
    // Database helpers for citizens and reports databases
    MyDatabaseHelper DbCitizens;
    ReportsDatabase DbReports;
    // SharedPreferences instance for storing user data
    SharedPreferences sharedPreference;

    /**
     * Constructor for the Repository class
     * @param context - application context
     */
    public Repository(Context context) {
        DbCitizens = new MyDatabaseHelper(context);
        DbReports = new ReportsDatabase(context);
        sharedPreference = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    // connects to sharedprefrence
    /**
     * Retrieves the user ID from shared preferences
     * @return user ID as a String
     */
    public String getId() {
        return sharedPreference.getString("Id", "");
    }

    /**
     * Updates shared preferences with user data
     * @param userName - user name
     * @param age - user age
     * @param points - user points
     * @param id - user ID
     * @param pass - user password
     */
    public void updateSharedPreference(String userName, int age, int points, String id, String pass) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("UserName", userName);
        editor.putInt("Age", age);
        editor.putInt("Points", points);
        editor.putString("Id", id);
        editor.putString("Pass", pass);
        editor.apply();
    }

    /**
     * Logs out the user by clearing shared preferences
     */
    public void logOut() {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.remove("UserName");
        editor.clear();
        editor.apply();
    }

    /**
     * Retrieves the shared preferences instance
     * @return SharedPreferences instance
     */
    public SharedPreferences getSharedPreference() {
        return sharedPreference;
    }

    /**
     * Checks if a user is logged in by verifying the presence of user data in shared preferences
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return sharedPreference.contains("UserName");
    }

    // connects to reports database
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Retrieves all reports from the reports database
     * @return Cursor containing all reports
     */
    public Cursor getAllReports() {
        return DbReports.GetAllReports();
    }

    /**
     * Retrieves reports by user ID from the reports database
     * @param id - user ID
     * @return Cursor containing reports for the specified user
     */
    public Cursor getReportsById(String id) {
        return DbReports.GetReportsById(id);
    }

    /**
     * Updates the status of a report in the reports database
     * @param reportId - report ID
     * @param status - new status to be set
     */
    public void updateReportStatus(String reportId, String status) {
        DbReports.updateReportStatus(reportId, status);
    }

    /**
     * Adds a new report to the reports database
     * @param id - user ID
     * @param imgsrc - image source as byte array
     * @param Description - description of the report
     * @param Location - location of the report
     * @param Date - date of the report
     * @param Points - points associated with the report
     */
    public void addReport(String id, byte[] imgsrc, String Description, String Location, String Date, Integer Points) {
        DbReports.AddReport(id, imgsrc, Description, Location, Date, Points);
    }


    public Cursor getReportsByIdSortedByStatus(String id){
        return DbReports.getReportsByIdSortedByStatus(id);
    }
    public Cursor getAllReportsSortedByStatus(){
        return DbReports.getAllReportsSortedByStatus();
    }


    /// connects to citizens database
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // deletes the user and all his reports from the database and logs out
    public void deleteUser(){
        DbCitizens.deleteUser(sharedPreference.getString("Id", ""));
        DbReports.deleteReportsByUserId(sharedPreference.getString("Id", ""));
        logOut();
    }




    /**
     * Adds a new citizen to the citizens database
     * @param Fullname - full name of the citizen
     * @param Age - age of the citizen
     * @param Id - ID of the citizen
     * @param Pass - password of the citizen
     */
    public void AddCitizen(String Fullname, int Age, String Id, String Pass) {
        DbCitizens.AddCitizen(Fullname, Age, Id, Pass);
    }

    /**
     * Updates the points of a citizen in the citizens database by user ID
     * @param id - user ID
     * @param points - points to be updated
     */
    public void updatePointsById(String id, int points) {
        DbCitizens.UpdatePointsById(id, points);
    }

    /**
     * Retrieves a user by ID from the citizens database
     * @param id - user ID
     * @return Cursor containing user data
     */
    public Cursor getUserById(String id) {
        return DbCitizens.getUserById(id);
    }

    /**
     * Checks if a citizen already exists in the citizens database by ID number
     * @param IdNumber - ID number to be checked
     * @return true if the citizen exists, false otherwise
     */
    public boolean CheckIfAlreadyExists(String IdNumber) {
        return DbCitizens.CheckIfAlreadyExists(IdNumber);
    }
    public void UpdateInfoById(String id, String fullname, int age, String pass){
        DbCitizens.UpdateInfoById(id, fullname, age, pass);
    }
}
