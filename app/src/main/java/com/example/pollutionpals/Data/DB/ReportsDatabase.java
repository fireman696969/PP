package com.example.pollutionpals.Data.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ReportsDatabase extends SQLiteOpenHelper {

    // Database name and version
    private Context context;
    private static final String DATABASE_NAME = "Reports.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "Reports";
    private static final String COLUMN_ReportNum = "ReportNum";
    private static final String COLUMN_Id = "Id";
    private static final String COLUMN_Img = "Img";
    private static final String COLUMN_Description = "Description";
    private static final String COLUMN_Location = "Location";
    private static final String COLUMN_Date = "Date";
    private static final String COLUMN_Points = "Points";
    private static final String COLUMN_Status = "Status";

    /**
     * Constructor for ReportsDatabase
     * @param context - application context
     */
    public ReportsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * onCreate method to create the database table
     * @param db - SQLiteDatabase instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ReportNum + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Id + " TEXT, " +
                COLUMN_Img + " BLOB, " +
                COLUMN_Description + " TEXT, " +
                COLUMN_Location + " TEXT, " +
                COLUMN_Date + " TEXT, " +
                COLUMN_Points + " INTEGER, " +
                COLUMN_Status + " TEXT);";
        db.execSQL(query);
    }

    /**
     * onUpgrade method to handle database schema changes
     * @param db - SQLiteDatabase instance
     * @param oldVersion - old database version
     * @param newVersion - new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Method to delete all reports of a specific user ID from the database
     * @param id - user ID whose reports need to be deleted
     */
    public void deleteReportsByUserId(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_Id + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete reports for user ID: " + id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Reports for user ID: " + id + " deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to retrieve reports by user ID
     * @param id - user ID
     * @return Cursor containing the report data
     */
    public Cursor GetReportsById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * Method to add a new report to the database
     * @param id - user ID
     * @param imgsrc - image source as byte array
     * @param Description - description of the report
     * @param Location - location of the report
     * @param Date - date of the report
     * @param Points - points associated with the report
     */
    public void AddReport(String id, byte[] imgsrc, String Description, String Location, String Date, Integer Points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_Id, id); // User ID 1
        cv.put(COLUMN_Img, imgsrc); // Image 2
        cv.put(COLUMN_Description, Description); // Description 3
        cv.put(COLUMN_Location, Location); // Location 4
        cv.put(COLUMN_Date, Date); // Date 5
        cv.put(COLUMN_Points, Points); // Points 6
        String status = "Pending";
        cv.put(COLUMN_Status, status); // Status 7

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added report Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to retrieve all reports from the database
     * @return Cursor containing all report data
     */
    public Cursor GetAllReports() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    /**
     * Method to update the status of a report
     * @param reportId - report ID
     * @param newStatus - new status to be set
     */
    public void updateReportStatus(String reportId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Status, newStatus);

        long result = db.update(TABLE_NAME, cv, COLUMN_ReportNum + "=?", new String[]{reportId});

        if (result == -1) {
            Toast.makeText(context, "Failed to update report status", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Report status updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to delete a specific report by its ID
     * @param row_id - report ID
     */
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to delete all reports from the database
     */
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
