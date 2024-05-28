package com.example.pollutionpals.UI.NewReportPage;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.pollutionpals.Data.Repository.Repository;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * NewReportModule class provides methods for creating a new report.
 */
public class NewReportModule {
    int y=0,m=0,d=0;

    // Instance of Repository for accessing data operations
    Repository rep;
    public Context context;

    /**
     * Constructor to initialize NewReportModule with the given context.
     *
     * @param context The context in which the NewReportModule is created.
     */
    public NewReportModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }

    /**
     * Displays a date picker dialog for selecting a date.
     *
     * @param edDate The EditText to display the selected date.
     */
    public void DateDialog(EditText edDate){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year,month,day);

        Calendar c2 = Calendar.getInstance();
        int year2 = c2.get(Calendar.YEAR)-70;
        int month2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        c2.set(year2,month2,day2);

        DatePickerDialog dateDialog = new DatePickerDialog(context);
        dateDialog.setCancelable(false);

        dateDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        dateDialog.getDatePicker().setMinDate(c2.getTimeInMillis());

        if(y!=0){
            dateDialog.updateDate(y,m,d);
        }
        dateDialog.show();

        dateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edDate.setText(i + "/ " + (i1+1) + " / " + i2);
                y=i;
                m= i1;
                d= i2;
            }
        });
    }

    /**
     * Adds a new report to the database.
     *
     * @param id The ID of the user submitting the report.
     * @param photo The photo attached to the report.
     * @param edDescription The description of the report.
     * @param edLocation The location of the report.
     * @param edDate The date of the report.
     * @param pointsSpinner The points assigned to the report.
     */
    public void AddReportToReports(String id, Bitmap photo, EditText edDescription, EditText edLocation, EditText edDate, Spinner pointsSpinner){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        rep.addReport(id,
                byteArray,
                edDescription.getText().toString(),
                edLocation.getText().toString(),
                edDate.getText().toString(),
                Integer.parseInt(pointsSpinner.getSelectedItem().toString()));
    }

    /**
     * Retrieves the ID of the current user.
     *
     * @return The ID of the current user.
     */
    public String getId(){
        return rep.getId();
    }
}


//package com.example.pollutionpals.UI.NewReportPage;
//
//import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
//
//import android.app.Activity;
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//
//import com.example.pollutionpals.Data.DB.ReportsDatabase;
//import com.example.pollutionpals.Data.Repository.Repository;
//import com.example.pollutionpals.R;
//import com.example.pollutionpals.UI.MainPage.MainPage;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class NewReportModule {
//    int y=0,m=0,d=0;
//
//    Repository rep;
//    public Context context;
//
//    public NewReportModule(Context context) {
//        rep = new Repository(context);
//        this.context = context;
//    }
//
//    public void DateDialog(EditText edDate){
//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        c.set(year,month,day);
//
//        Calendar c2 = Calendar.getInstance();
//        int year2 = c2.get(Calendar.YEAR)-70;
//        int month2 = c2.get(Calendar.MONTH);
//        int day2 = c2.get(Calendar.DAY_OF_MONTH);
//        c2.set(year2,month2,day2);
//
//        DatePickerDialog dateDialog = new DatePickerDialog(context);
//        dateDialog.setCancelable(false);
//
//        dateDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
//        dateDialog.getDatePicker().setMinDate(c2.getTimeInMillis());
//
//        if(y!=0){
//            dateDialog.updateDate(y,m,d);
//        }
//        dateDialog.show();
//
//        dateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                edDate.setText(i + "/ " + (i1+1) + " / " + i2);
//                y=i;
//                m= i1;
//                d= i2;
//            }
//        });
//    }
//    public void AddReportToReports(String id, Bitmap photo, EditText edDescription, EditText edLocation, EditText edDate, Spinner pointsSpinner){
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//                rep.addReport(id,
//                byteArray,
//                edDescription.getText().toString(),
//                edLocation.getText().toString(),
//                edDate.getText().toString(),
//                Integer.parseInt(pointsSpinner.getSelectedItem().toString())  );
//    }
//    public String getId(){
//
//        return rep.getId();
//    }
////    public void Photo( Bitmap photo, ImageView imgvCamera){
////        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
////                new ActivityResultContracts.StartActivityForResult(),
////                new ActivityResultCallback<ActivityResult>() {
////                    @Override
////                    public void onActivityResult(ActivityResult result) {
////                        if (result.getResultCode() == Activity.RESULT_OK) {
////                            // There are no request codes
////                            Intent data = result.getData();
////                            photo = (Bitmap) data.getExtras().get("data");
////                            imgvCamera.setImageBitmap(photo);
////                            int height = Integer.parseInt(String.valueOf(Math.round(context.getResources().getDisplayMetrics().density * 150)));
////                            int width = Integer.parseInt(String.valueOf(Math.round(context.getResources().getDisplayMetrics().density * 250)));
////                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
////                            params.addRule(RelativeLayout.BELOW, R.id.tv1);
////                            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
////                            imgvCamera.setLayoutParams(params);
////
////                        }
////                    }
////                });
////
////    }
//}
//
//
//
//
