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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class MyReportsPage extends AppCompatActivity implements View.OnClickListener {
    TableLayout tblReports;
    SharedPreferences sharedPreference;

    Button btnBack;
    MyDatabaseHelper citizens;
    ReportsDatabase reports;

    int [] idReportArr;
    TableRow [] rowArr;
    Cursor cursor;
    MainModule mainModule;
    String idSharedPrefrence;
    TextView tvLocation;
    int bigi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports_page);

        tvLocation = findViewById(R.id.tvLocation);
        tblReports = findViewById(R.id.tblReports);
         reports = new ReportsDatabase(this);
        citizens = new MyDatabaseHelper(getBaseContext());
        sharedPreference = this.getSharedPreferences("user", Context.MODE_PRIVATE);
         idSharedPrefrence = sharedPreference.getString("Id", "id");
        if(idSharedPrefrence.equals("329455109")){
            cursor = reports.GetAllReports();
            tvLocation.setText(" Id");
        }
        else{
            cursor = reports.GetReportsById(idSharedPrefrence);
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);



        // Check if cursor has data
        if (cursor != null && cursor.getCount() > 0) {
            // Move cursor to the first row
            cursor.moveToFirst();

            idReportArr = new int [cursor.getCount()];
            rowArr = new TableRow[cursor.getCount()];

            for (int i = 0; i < cursor.getCount(); i++) {
                TableRow row = new TableRow(this);

                TextView numTextView = new TextView(this);


                TextView dateTextView = new TextView(this);

                TextView descriptionTextView = new TextView(this);

                TextView statusTextView = new TextView(this);

                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;

                statusTextView.setLayoutParams(params);
                numTextView.setLayoutParams(params);
                dateTextView.setLayoutParams(params);


                TableRow.LayoutParams params2 = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.weight = 2;
                descriptionTextView.setLayoutParams(params2);

                TableLayout.LayoutParams params3 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params3.topMargin = 40;
                row.setLayoutParams(params3);

                statusTextView.setGravity(Gravity.CENTER);
                numTextView.setGravity(Gravity.CENTER);
                dateTextView.setGravity(Gravity.CENTER);
                descriptionTextView.setGravity(Gravity.CENTER);

                // Retrieve data from cursor

                String date = cursor.getString(5);
                String location = cursor.getString(4);
                String status = cursor.getString(7);
                String id = cursor.getString(1);

                // Set text to TextViews
                numTextView.setText((i + 1) + "");
                dateTextView.setText(date);
                if(idSharedPrefrence.equals("329455109")){
                    descriptionTextView.setText(id);
                }
                else{
                    descriptionTextView.setText(location);
                }

                statusTextView.setText(status);

                // Add TextViews to the row
                row.addView(numTextView);
                row.addView(dateTextView);
                row.addView(descriptionTextView);
                row.addView(statusTextView);

                // Add the row to the table layout
                tblReports.addView(row);

                // Add the id of the report to the idArray
                idReportArr[i] = cursor.getInt(0);

                // make the row clickable
                rowArr[i] = row;
                row.setOnClickListener(this);
                rowArr[i].setOnClickListener(this);


                cursor.moveToNext();
            }
        }







    }

    @Override
    public void onClick(View view) {
        if(btnBack == view){
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            if(rowArr[i] == view){
                bigi = i;
                Dialog dialog = new Dialog(this);
                dialog.setCancelable(false);



                cursor.moveToPosition(i);
                String idPerson = cursor.getString(1);
                String date = cursor.getString(5);
                String description = cursor.getString(3);
                String status = cursor.getString(7);
                int points = cursor.getInt(6);
                String location = cursor.getString(4);
                byte[] byteArray = cursor.getBlob(2);
                Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                ImageView imgvDialog;
                TextView tvDescription;
                TextView tvDate;
                TextView tvLocation;
                TextView tvPoints;
                TextView tvStatus;
                TextView tvId;


                if(idSharedPrefrence.equals("329455109")){
                    dialog.setContentView(R.layout.custom_dialog_admin);
                     imgvDialog = dialog.findViewById(R.id.imgvCameraDialogAdmin);
                     tvDescription = dialog.findViewById(R.id.tvDescriptionDialogAdmin);
                     tvDate = dialog.findViewById(R.id.tvDateDialogAdmin);
                     tvLocation = dialog.findViewById(R.id.tvLocationDialogAdmin);
                     tvPoints = dialog.findViewById(R.id.tvPointsDialogAdmin);
                     tvStatus = dialog.findViewById(R.id.tvStatusDialogAdmin);
                     tvId = dialog.findViewById(R.id.tvIdDialogAdmin);
                     tvId.setText("Id: " +idPerson);

                     // check if report has already been approved/ denied
                    if(cursor.getString(7).equals("denied") || cursor.getString(7).equals("approved")){
                        LinearLayout layApproveDeny = dialog.findViewById(R.id.layApproveDeny);
                        RelativeLayout layDialogAdmin = dialog.findViewById(R.id.layDialogAdmin);
                        layDialogAdmin.removeView(layApproveDeny);
                        TextView tvIdAdmin = dialog.findViewById(R.id.tvIdDialogAdmin);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);

                              // Position btn back below tvIdAdmin
                            params.addRule(RelativeLayout.BELOW, tvIdAdmin.getId());
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        Button btnBackDialogAdmin = dialog.findViewById(R.id.btnBackDialogAdmin);

                        btnBackDialogAdmin.setLayoutParams(params);
                    }
                    else{
                        Button btnApprove = dialog.findViewById(R.id.btnApprove);
                        Button btnDeny = dialog.findViewById(R.id.btnDeny);

                        btnApprove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // add points to citizens in database
                                int points = cursor.getInt(6);
                                String id = cursor.getString(1);
                                citizens.UpdatePointsById(id,points);

                                // change status to approved in database
                                reports.updateReportStatus(String.valueOf(idReportArr[bigi]),"approved");
                                if(cursor.getString(1).equals("329455109")){
                                    // update Shared prefrence
                                    SharedPreferences sharedPreference = getBaseContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreference.edit();
                                    Cursor cursorCitizen = citizens.getUserById(cursor.getString(1));
                                    if(cursorCitizen.moveToFirst()){
                                        editor.putString("UserName", cursorCitizen.getString(1) );
                                        editor.putInt("Age",cursorCitizen.getInt(2));
                                        editor.putInt("Points",cursorCitizen.getInt(3));
                                        editor.putString("Id", cursorCitizen.getString(4));
                                        editor.putString("Pass", cursorCitizen.getString(5));
                                        editor.apply();
                                    }
                                }
                                dialog.cancel();
                                finish();
                                startActivity(getIntent());
                            }
                        });
                        btnDeny.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // change status to approved in database
                                reports.updateReportStatus(String.valueOf(idReportArr[bigi]),"denied");
                                dialog.cancel();
                            }
                        });
                    }



                }
                else{
                    dialog.setContentView(R.layout.custom_dialog_report);

                     imgvDialog = dialog.findViewById(R.id.imgvCameraDialog);
                     tvDescription = dialog.findViewById(R.id.tvDescriptionDialog);
                     tvDate = dialog.findViewById(R.id.tvDateDialog);
                     tvLocation = dialog.findViewById(R.id.tvLocationDialog);
                     tvPoints = dialog.findViewById(R.id.tvPointsDialog);
                     tvStatus = dialog.findViewById(R.id.tvStatusDialog);

                }





                // put parameters of report into boxes
                imgvDialog.setImageBitmap(img);
                tvDescription.setText("description: " + description);
                tvDate.setText("date:  \n" +date);
                tvLocation.setText("Location: "+location);
                tvPoints.setText("points: "+points + "");
                tvStatus.setText("status: " +status);


                if(idSharedPrefrence.equals("329455109")){
                    Button btnBackDialog = dialog.findViewById(R.id.btnBackDialogAdmin);

                    btnBackDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
                else{
                    Button btnBackDialog = dialog.findViewById(R.id.btnBackDialog);
                    btnBackDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                }



                dialog.show();


            }
        }

    }
}