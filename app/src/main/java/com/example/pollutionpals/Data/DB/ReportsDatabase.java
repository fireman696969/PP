package com.example.pollutionpals.Data.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class ReportsDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Citizens.db";
    private static final int DATABASE_VERSION = 1;



    private static final String TABLE_NAME = "Reports";
    private static final String COLUMN_ReportNum = "ReportNum";
    private static final String COLUMN_Id = "Id";
    private static final String COLUMN_Img = "Img";
    private static final String COLUMN_Description = "Description";
    private static final String COLUMN_Location = "Location";
    private static final String COLUMN_Date = "Date";
    private static final String COLUMN_Points = "Points";
    private static final String COLUMN_Status = "Status";





    public ReportsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

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
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void AddReport(String id, byte[] imgsrc, String Description, String Location, String Date, Integer Points ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_Id, id);
        cv.put(COLUMN_Img, imgsrc);
        cv.put(COLUMN_Description, Description);
        cv.put(COLUMN_Location, Location);
        cv.put(COLUMN_Date, Date);
        cv.put(COLUMN_Points, Points);
        String status = "Pending";
        cv.put(COLUMN_Status, status);





        long result = db.insert(TABLE_NAME,null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added report Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor GetReportsById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + COLUMN_Id + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }


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

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
