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

import com.example.pollutionpals.Data.DB.ReportsDatabase;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.UI.NewReportPage.NewReportPage;

import java.sql.Blob;

public class MyReportsPage extends AppCompatActivity implements View.OnClickListener {
    TableLayout tblReports;
    SharedPreferences sharedPreference;

    Button btnBack;

    int [] idReportArr;
    TableRow [] rowArr;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_reports_page);
        tblReports = findViewById(R.id.tblReports);
        ReportsDatabase reports = new ReportsDatabase(this);
        sharedPreference = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String id = sharedPreference.getString("Id", "id");
         cursor = reports.GetReportsById(id);

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

                // Set text to TextViews
                numTextView.setText((i + 1) + "");
                dateTextView.setText(date);
                descriptionTextView.setText(location);
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
                Dialog dialog = new Dialog(this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.custom_dialog_report);
                cursor.moveToPosition(i);

                String date = cursor.getString(5);
                String description = cursor.getString(3);
                String status = cursor.getString(7);
                int points = cursor.getInt(6);
                String location = cursor.getString(4);
                byte[] byteArray = cursor.getBlob(2);
                Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                ImageView imgvDialog = dialog.findViewById(R.id.imgvCameraDialog);
                TextView tvDescription = dialog.findViewById(R.id.tvDescriptionDialog);
                TextView tvDate = dialog.findViewById(R.id.tvDateDialog);
                TextView tvLocation = dialog.findViewById(R.id.tvLocationDialog);
                TextView tvPoints = dialog.findViewById(R.id.tvPointsDialog);
                TextView tvStatus = dialog.findViewById(R.id.tvStatusDialog);

                // put parameters of report into boxes
                imgvDialog.setImageBitmap(img);
                tvDescription.setText("description: " + description);
                tvDate.setText("date:  \n" +date);
                tvLocation.setText("Location: "+location);
                tvPoints.setText("points: "+points + "");
                tvStatus.setText("status: " +status);

                Button btnBackDialog = dialog.findViewById(R.id.btnBackDialog);
                btnBackDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();


            }
        }

    }
}