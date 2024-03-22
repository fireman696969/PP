package com.example.pollutionpals.UI.MyReportsPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class MyReportsPage extends AppCompatActivity implements View.OnClickListener {
    TableLayout tblReports;
    SharedPreferences sharedPreference;

    TextView tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_reports_page);
        tblReports = findViewById(R.id.tblReports);
        ReportsDatabase reports = new ReportsDatabase(this);
        sharedPreference = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String id = sharedPreference.getString("Id", "id");
        Cursor cursor = reports.GetReportsById(id);
        tvId = findViewById(R.id.tvId);
        tvId.setText(id);

        // Check if cursor has data
        if (cursor != null && cursor.getCount() > 0) {
            // Move cursor to the first row
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                TableRow row = new TableRow(this);
                TextView numTextView = new TextView(this);


                TextView dateTextView = new TextView(this);

                TextView descriptionTextView = new TextView(this);

                TextView statusTextView = new TextView(this);

                TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                statusTextView.setLayoutParams(params);
                numTextView.setLayoutParams(params);
                dateTextView.setLayoutParams(params);

                TableLayout.LayoutParams params2 = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.weight = 2;
                descriptionTextView.setLayoutParams(params2);

                // Retrieve data from cursor

                String date = cursor.getString(5);
                String description = cursor.getString(3);
                String status = cursor.getString(7);

                // Set text to TextViews
                numTextView.setText((i + 1) + "");
                dateTextView.setText(date);
                descriptionTextView.setText(description);
                statusTextView.setText(status);

                // Add TextViews to the row
                row.addView(numTextView);
                row.addView(dateTextView);
                row.addView(descriptionTextView);
                row.addView(statusTextView);

                // Add the row to the table layout
                tblReports.addView(row);
                cursor.moveToNext();
            }
        }







    }

    @Override
    public void onClick(View view) {

    }
}