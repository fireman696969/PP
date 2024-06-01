package com.example.pollutionpals.UI.MyReportsPage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.DB.ReportsDatabase;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MainPage.MainModule;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.UI.NewReportPage.NewReportPage;

import java.sql.Blob;

// This class is used to display the user's reports in a table layout.
public class MyReportsPage extends AppCompatActivity implements View.OnClickListener {

    // Declare the UI elements
    TableLayout tblReports;

   CheckBox srtStatus;
    Button btnBack;

    // Declare variables to store the report data
    int[] idReportArr;
    TableRow[] rowArr;
    Cursor cursor;

    String idUser;
    TextView tvLocation;
    int bigi = 0;
    MyReportsModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports_page);

        // Initialize the UI elements
        tvLocation = findViewById(R.id.tvLocation);
        tblReports = findViewById(R.id.tblReports);
        module = new MyReportsModule(this);
        idUser = module.getId();


        srtStatus = findViewById(R.id.srtStatus);
        srtStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sortByStatus();
                }
                else{
                    // Check if the user is an admin
                    if (idUser.equals("329455109")) {
                        // Get all reports from the database
                        cursor = module.getAllReports();
                    } else {
                        // Get reports by the user's ID
                        cursor = module.getReportsById(idUser);
                    }
                    removeAllRows();
                    fillTable();
                }
            }
        });

        // Check if the user is an admin
        if (idUser.equals("329455109")) {
            // Get all reports from the database
            cursor = module.getAllReports();
            tvLocation.setText(" Id");
        } else {
            // Get reports by the user's ID
            cursor = module.getReportsById(idUser);
        }

        // Initialize the back button
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        fillTable();




    }

        public void removeAllRows(){
            int childCount = tblReports.getChildCount();
            for (int i = childCount - 1; i >= 1; i--) {
                View child = tblReports.getChildAt(i);
                tblReports.removeView(child);
            }
        }


    public void fillTable(){
        // Check if the cursor has data
        if (cursor != null && cursor.getCount() > 0) {
            // Move the cursor to the first row
            cursor.moveToFirst();

            // Initialize the arrays to store the report data
            idReportArr = new int[cursor.getCount()];
            rowArr = new TableRow[cursor.getCount()];

            // Loop through the cursor and create a row for each report
            for (int i = 0; i < cursor.getCount(); i++) {
                // Create a new table row
                TableRow row = new TableRow(this);

                // Create the text views for the report data
                TextView numTextView = new TextView(this);
                TextView dateTextView = new TextView(this);
                TextView descriptionTextView = new TextView(this);
                TextView statusTextView = new TextView(this);

                // Set the layout parameters for the text views
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                statusTextView.setLayoutParams(params);
                numTextView.setLayoutParams(params);
                dateTextView.setLayoutParams(params);

                TableRow.LayoutParams params2 = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.weight = 2;
                descriptionTextView.setLayoutParams(params2);

                // Set the layout parameters for the row
                TableLayout.LayoutParams params3 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params3.topMargin = 40;
                row.setLayoutParams(params3);

                // Set the gravity for the text views
                statusTextView.setGravity(Gravity.CENTER);
                numTextView.setGravity(Gravity.CENTER);
                dateTextView.setGravity(Gravity.CENTER);
                descriptionTextView.setGravity(Gravity.CENTER);

                // Retrieve the data from the cursor
                String date = cursor.getString(5);
                String points = cursor.getString(6);
                String status = cursor.getString(7);
                String id = cursor.getString(1);

                // Set the text for the text views
                numTextView.setText((i + 1) + "");
                dateTextView.setText(date);
                if (idUser.equals("329455109")) {
                    descriptionTextView.setText(id);
                } else {
                    descriptionTextView.setText(points);
                }
                statusTextView.setText(status);

                // Add the text views to the row
                row.addView(numTextView);
                row.addView(dateTextView);
                row.addView(descriptionTextView);
                row.addView(statusTextView);

                // Add the row to the table layout
                tblReports.addView(row);

                // Store the report ID in the array
                idReportArr[i] = cursor.getInt(0);

                // Make the row clickable
                rowArr[i] = row;
                row.setOnClickListener(this);
                rowArr[i].setOnClickListener(this);

                // Move the cursor to the next row
                cursor.moveToNext();
            }
        }
    }
    public void sortByStatus(){
        removeAllRows();
        if(idUser.equals("329455109")){
            cursor = module.getAllReportsSortedByStatus();
        }
        else{
            cursor = module.getReportsByIdSortedByStatus(idUser);
        }
        fillTable();
    }

    @Override
    public void onClick(View view) {
        // If the back button is clicked, go back to the main page
        if (btnBack == view) {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }
        // if sort by status has been clicked
        if(srtStatus == view){

        }

        // If a row is clicked, show the report details
        for (int i = 0; i < cursor.getCount(); i++) {
            if (rowArr[i] == view) {
                bigi = i;
                Dialog dialog = new Dialog(this);
                dialog.setCancelable(false);

                // Move the cursor to the clicked row
                cursor.moveToPosition(i);
                // Get the report data from the cursor
                String idPerson = cursor.getString(1);
                String date = cursor.getString(5);
                String description = cursor.getString(3);
                String status = cursor.getString(7);
                int points = cursor.getInt(6);
                String location = cursor.getString(4);
                byte[] byteArray = cursor.getBlob(2);
                Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                // Initialize the dialog UI elements
                ImageView imgvDialog;
                TextView tvDescription;
                TextView tvDate;
                TextView tvLocation;
                TextView tvPoints;
                TextView tvStatus;
                TextView tvId;

                // Check if the user is an admin
                if (idUser.equals("329455109")) {
                    // Set the layout for the admin dialog
                    dialog.setContentView(R.layout.custom_dialog_admin);
                    imgvDialog = dialog.findViewById(R.id.imgvCameraDialogAdmin);
                    tvDescription = dialog.findViewById(R.id.tvDescriptionDialogAdmin);
                    tvDate = dialog.findViewById(R.id.tvDateDialogAdmin);
                    tvLocation = dialog.findViewById(R.id.tvLocationDialogAdmin);
                    tvPoints = dialog.findViewById(R.id.tvPointsDialogAdmin);
                    tvStatus = dialog.findViewById(R.id.tvStatusDialogAdmin);
                    tvId = dialog.findViewById(R.id.tvIdDialogAdmin);
                    tvId.setText("Id: " + idPerson);

                    // Check if the report has been approved or denied
                    if (cursor.getString(7).equals("denied") || cursor.getString(7).equals("approved")) {
                        // Remove the approve/deny buttons
                        LinearLayout layApproveDeny = dialog.findViewById(R.id.layApproveDeny);
                        RelativeLayout layDialogAdmin = dialog.findViewById(R.id.layDialogAdmin);
                        layDialogAdmin.removeView(layApproveDeny);

                        // Reposition the back button
                        TextView tvIdAdmin = dialog.findViewById(R.id.tvIdDialogAdmin);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.BELOW, tvIdAdmin.getId());
                        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        Button btnBackDialogAdmin = dialog.findViewById(R.id.btnBackDialogAdmin);
                        btnBackDialogAdmin.setLayoutParams(params);
                    } else {
                        // Initialize the approve and deny buttons
                        Button btnApprove = dialog.findViewById(R.id.btnApprove);
                        Button btnDeny = dialog.findViewById(R.id.btnDeny);

                        // Set the click listener for the approve button
                        btnApprove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Add points to the citizen
                                int points = cursor.getInt(6);
                                String id = cursor.getString(1);
                                module.updatePointsById(id, points);

                                // Change the status to approved
                                module.updateReportStatus(String.valueOf(idReportArr[bigi]), "approved");

                                // Update the shared preferences if the user is an admin
                                if (cursor.getString(1).equals("329455109")) {
                                    Cursor cursorCitizen = module.getUserById(cursor.getString(1));
                                    if(cursorCitizen.moveToFirst()){
                                        module.updateSharedPreference(cursorCitizen.getString(1), cursorCitizen.getInt(2), cursorCitizen.getInt(3), cursorCitizen.getString(4), cursorCitizen.getString(5));
                                    }
                                }

                                // Close the dialog and refresh the activity
                                dialog.cancel();
                                finish();
                                startActivity(getIntent());
                            }
                        });

                        // Set the click listener for the deny button
                        btnDeny.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Change the status to denied
                                module.updateReportStatus(String.valueOf(idReportArr[bigi]), "denied");

                                // Close the dialog and refresh the activity
                                dialog.cancel();
                                finish();
                                startActivity(getIntent());
                            }
                        });
                    }
                } else {
                    // Set the layout for the user dialog
                    dialog.setContentView(R.layout.custom_dialog_report);
                    imgvDialog = dialog.findViewById(R.id.imgvCameraDialog);
                    tvDescription = dialog.findViewById(R.id.tvDescriptionDialog);
                    tvDate = dialog.findViewById(R.id.tvDateDialog);
                    tvLocation = dialog.findViewById(R.id.tvLocationDialog);
                    tvPoints = dialog.findViewById(R.id.tvPointsDialog);
                    tvStatus = dialog.findViewById(R.id.tvStatusDialog);
                }

                // Set the report data in the dialog
                imgvDialog.setImageBitmap(img);
                tvDescription.setText("description: " + description);
                tvDate.setText("date:  \n" + date);
                tvLocation.setText("Location: " + location);
                tvPoints.setText("points: " + points + "");
                tvStatus.setText("status: " + status);

                // Set the click listener for the back button
                if (idUser.equals("329455109")) {
                    Button btnBackDialog = dialog.findViewById(R.id.btnBackDialogAdmin);
                    btnBackDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                } else {
                    Button btnBackDialog = dialog.findViewById(R.id.btnBackDialog);
                    btnBackDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }

                // Show the dialog

                dialog.show();
            }
        }
    }
}




