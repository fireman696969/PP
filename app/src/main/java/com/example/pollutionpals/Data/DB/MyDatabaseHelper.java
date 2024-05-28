
package com.example.pollutionpals.Data.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

// SQLiteOpenHelper class to manage database creation and version management.
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context; // Context for displaying Toast messages.
    private static final String DATABASE_NAME = "Citizens.db"; // Database name.
    private static final int DATABASE_VERSION = 1; // Database version.

    // Table and column names.
    private static final String TABLE_NAME = "Citizens";
    private static final String COLUMN_CitizenNum = "CitizenNum";
    private static final String COLUMN_Fullname = "Fullname";
    private static final String COLUMN_Age = "Age";
    private static final String COLUMN_Points = "Points";
    private static final String COLUMN_Id = "Id";
    private static final String COLUMN_Pass = "Pass";

    // Constructor to initialize the database helper.
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // onCreate method to create the database table when the database is first created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_CitizenNum + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Fullname + " TEXT, " + // fullname: 1
                COLUMN_Age + " INTEGER, " + // age: 2
                COLUMN_Points + " INTEGER, " + // points: 3
                COLUMN_Id + " TEXT, " + // id: 4
                COLUMN_Pass + " TEXT);"; // pass: 5
        db.execSQL(query);
    }

    // onUpgrade method to handle database schema changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to add a new citizen record to the database.
    public void AddCitizen(String Fullname, int Age, String Id, String Pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_Fullname, Fullname);
        cv.put(COLUMN_Age, Age);
        cv.put(COLUMN_Points, 0); // Initial points set to 0.
        cv.put(COLUMN_Id, Id);
        cv.put(COLUMN_Pass, Pass);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update points for a citizen by their ID.
    public void UpdatePointsById(String id, int pointsToAdd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Points, getPointsById(id) + pointsToAdd); // Get current points and add new points.

        long result = db.update(TABLE_NAME, cv, COLUMN_Id + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update points", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Points updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update citizen information by their ID.
    public void UpdateInfoById(String id, String fullname, int age, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_Fullname, fullname);
        cv.put(COLUMN_Age, age);
        cv.put(COLUMN_Pass, pass);

        long result = db.update(TABLE_NAME, cv, COLUMN_Id + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update info", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Info updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to get the current points for a citizen by their ID.
    private int getPointsById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_Points + " FROM " + TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }
        return 0; // If no record found, return 0 as default points.
    }

    // Method to read all data from the citizens table.
    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        return db != null ? db.rawQuery(query, null) : null;
    }

    // Method to get a citizen's data by their ID.
    public Cursor getUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    // Method to update a citizen's data by their row ID.
    public void updateData(String row_id, String Fullname, int Age, String Pass, String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_Fullname, Fullname);
        cv.put(COLUMN_Age, Age);
        cv.put(COLUMN_Id, Id);
        cv.put(COLUMN_Pass, Pass);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete a citizen's record by their row ID.
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete all data from the citizens table.
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    // Method to check if a citizen exists with the given ID and password.
    public Boolean CheckCitizen(String IdNumber, String Password) {
        boolean result = false;
        try {
            String query = " SELECT EXISTS (\n" +
                    "  SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + IdNumber + "' AND " + COLUMN_Pass + " = '" + Password + "' \n" +
                    ")";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int exists = cursor.getInt(0);
                    result = (exists == 1);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.d("exception", " here");
            result = false;
        }
        return result;
    }

    // Method to check if a citizen already exists with the given ID.
    public boolean CheckIfAlreadyExists(String IdNumber) {
        boolean result = false;
        try {
            String query = " SELECT EXISTS (\n" +
                    "  SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + IdNumber + ")";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int exists = cursor.getInt(0);
                    result = (exists == 1);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.d("exception", " here");
            result = false;
        }
        return result;
    }
}


//package com.example.pollutionpals.Data.DB;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//import android.widget.Toast;
//import androidx.annotation.Nullable;
//
//public class MyDatabaseHelper extends SQLiteOpenHelper {
//
//    private Context context;
//    private static final String DATABASE_NAME = "Citizens.db";
//    private static final int DATABASE_VERSION = 1;
//
//
//
//    private static final String TABLE_NAME = "Citizens";
//    private static final String COLUMN_CitizenNum = "CitizenNum";
//    private static final String COLUMN_Fullname = "Fullname";
//    private static final String COLUMN_Age = "Age";
//    private static final String COLUMN_Points = "Points";
//    private static final String COLUMN_Id = "Id";
//    private static final String COLUMN_Pass = "Pass";
//
//
//    public MyDatabaseHelper(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String query = "CREATE TABLE " + TABLE_NAME +
//                        " (" + COLUMN_CitizenNum + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        COLUMN_Fullname + " TEXT, " + // fullname: 1
//                        COLUMN_Age + " INTEGER, " + // age: 2
//                        COLUMN_Points + " INTEGER, " + // points: 3
//                        COLUMN_Id + " TEXT, " + // id: 4
//                        COLUMN_Pass + " TEXT);"; // pass: 5
//        db.execSQL(query);
//    }
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    public void AddCitizen(String Fullname, int Age, String Id, String Pass){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_Fullname, Fullname);
//        cv.put(COLUMN_Age, Age);
//        int i =0;
//        cv.put(COLUMN_Points, i);
//        cv.put(COLUMN_Id, Id);
//        cv.put(COLUMN_Pass, Pass);
//
//
//
//        long result = db.insert(TABLE_NAME,null, cv);
//        if(result == -1){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void UpdatePointsById(String id, int pointsToAdd) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_Points, getPointsById(id) + pointsToAdd); // Get current points and add new points
//
//        long result = db.update(TABLE_NAME, cv, COLUMN_Id + "=?", new String[]{id});
//
//        if (result == -1) {
//            Toast.makeText(context, "Failed to update points", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Points updated successfully!", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void UpdateInfoById(String id, String fullname, int age, String pass) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_Fullname, fullname);
//        cv.put(COLUMN_Age, age);
//        cv.put(COLUMN_Pass, pass);
//
//        long result = db.update(TABLE_NAME, cv, COLUMN_Id + "=?", new String[]{id});
//
//        if (result == -1) {
//            Toast.makeText(context, "Failed to update info", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Info updated successfully!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    // Helper method to get current points before update
//    private int getPointsById(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT " + COLUMN_Points + " FROM " + TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + id + "'";
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                return cursor.getInt(0);
//            }
//        }
//        // If no record found, return 0 as default points
//        return 0;
//    }
//
//    public Cursor readAllData(){
//        String query = "SELECT * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        if(db != null){
//            cursor = db.rawQuery(query, null);
//        }
//        return cursor;
//    }
//    public Cursor getUserById(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + id + "'";
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//        return cursor;
//    }
//
//
//    public void updateData(String row_id, String Fullname, int Age, String Pass, String Id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_Fullname, Fullname);
//        cv.put(COLUMN_Age, Age);
//        cv.put(COLUMN_Id, Id);
//        cv.put(COLUMN_Pass, Pass);
//
//        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
//        if(result == -1){
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void deleteOneRow(String row_id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
//        if(result == -1){
//            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void deleteAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_NAME);
//    }
//    public Boolean CheckCitizen(String IdNumber, String Password) {
//        boolean result = false;
//        try {
//            String query = " SELECT EXISTS (\n" +
//                    "  SELECT * FROM " + TABLE_NAME + "  WHERE " + COLUMN_Id + " = '" + IdNumber + "' AND " + COLUMN_Pass + " = '" + Password + "' \n" +
//                    ")";
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(query, null);
//
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    int exists = cursor.getInt(0);
//                    result = (exists == 1);
//                }
//                cursor.close();
//            }
//
//        } catch (Exception e) {
//            Log.d("exception", " here");
//            result = false;
//        }
//        return result;
//    }
//    public boolean CheckIfAlreadyExists(String IdNumber){
//        boolean result = false;
//        try {
//            String query = " SELECT EXISTS (\n" +
//                    "  SELECT * FROM " + TABLE_NAME + "  WHERE " + COLUMN_Id + " = '" + IdNumber + ")";
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(query, null);
//
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    int exists = cursor.getInt(0);
//                    result = (exists == 1);
//                }
//                cursor.close();
//            }
//
//        } catch (Exception e) {
//            Log.d("exception", " here");
//            result = false;
//        }
//        return result;
//    }
//
//}
