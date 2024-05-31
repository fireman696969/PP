package com.example.pollutionpals.UI.MyReportsPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.pollutionpals.Data.Repository.Repository;

/**
 * MyReportsModule class provides methods to handle operations related to user reports.
 */
public class MyReportsModule {
    // Instance of Repository for accessing data operations
    Repository rep;
    public Context context;

    /**
     * Constructor to initialize MyReportsModule with the given context.
     *
     * @param context The context in which the MyReportsModule is created.
     */
    public MyReportsModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }

    /**
     * Retrieves the ID of the current user.
     *
     * @return The ID of the current user.
     */
    public String getId(){
        return rep.getId();
    }

    /**
     * Retrieves all reports.
     *
     * @return A Cursor containing all reports.
     */
    public Cursor getAllReports(){
        return rep.getAllReports();
    }

    /**
     * Retrieves reports by user ID.
     *
     * @param id The ID of the user.
     * @return A Cursor containing reports of the specified user.
     */
    public Cursor getReportsById(String id){
        return rep.getReportsById(id);
    }

    /**
     * Updates points of a user by ID.
     *
     * @param id The ID of the user.
     * @param points The new points to be updated.
     */
        public void updatePointsById(String id, int points){
        rep.updatePointsById(id, points);
    }

    /**
     * Updates the status of a report.
     *
     * @param reportId The ID of the report.
     * @param status The new status to be updated.
     */
    public void updateReportStatus(String reportId, String status){
        rep.updateReportStatus(reportId, status);
    }

    /**
     * Retrieves user information by ID.
     *
     * @param id The ID of the user.
     * @return A Cursor containing information of the specified user.
     */
    public Cursor getUserById(String id){
        return rep.getUserById(id);
    }

    /**
     * Updates shared preferences with user information.
     *
     * @param userName The username of the user.
     * @param age The age of the user.
     * @param points The points of the user.
     * @param id The ID of the user.
     * @param pass The password of the user.
     */
    public void updateSharedPreference(String userName, int age, int points, String id, String pass){
        rep.updateSharedPreference(userName, age, points, id, pass);
    }
}


//package com.example.pollutionpals.UI.MyReportsPage;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//
//import com.example.pollutionpals.Data.Repository.Repository;
//
//public class MyReportsModule {
//    Repository rep;
//    public Context context;
//
//    public MyReportsModule(Context context) {
//        rep = new Repository(context);
//        this.context = context;
//    }
//    public String getId(){
//        return rep.getId();
//    }
//    public Cursor getAllReports(){
//        return rep.getAllReports();
//    }
//    public Cursor getReportsById(String id){
//        return rep.getReportsById(id);
//    }
//    public void updatePointsById(String id, int points){
//        rep.updatePointsById(id, points);
//    }
//    public void updateReportStatus(String reportId, String status){
//        rep.updateReportStatus(reportId, status);
//    }
//    public Cursor getUserById(String id){
//        return rep.getUserById(id);
//    }
//    public void updateSharedPreference(String userName, int age, int points, String id, String pass){
//        rep.updateSharedPreference(userName, age, points, id, pass);
//    }
//
//}
