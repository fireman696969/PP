package com.example.pollutionpals.UI.MainPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MyProfilePage.MyProfilePage;
import com.example.pollutionpals.UI.MyReportsPage.MyReportsPage;
import com.example.pollutionpals.UI.NewReportPage.NewReportPage;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
    TextView tvUserName;
    MainModule mainModule;
    Button btnMyProfilePage, btnNewReport, btnMyReports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        tvUserName = findViewById(R.id.tvUserName);
        mainModule = new MainModule(this);
        tvUserName.setText(mainModule.GetUserName());
        btnMyProfilePage = findViewById(R.id.btnMyProfilePage);
        btnNewReport = findViewById(R.id.btnNewReport);
        btnMyReports = findViewById(R.id.btnAllReports);
        if(mainModule.GetUserId().equals("329455109")){
            btnMyReports.setText("ALL REPORTS");
        }



        btnMyProfilePage.setOnClickListener(this);
        btnNewReport.setOnClickListener(this);
        btnMyReports.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(btnMyProfilePage == view){
            Intent intent = new Intent(this, MyProfilePage.class);
            startActivity(intent);
        }
        if(btnNewReport == view){
            Intent intent = new Intent(this, NewReportPage.class);
            startActivity(intent);
        }
        if(btnMyReports == view){
            Intent intent = new Intent(this, MyReportsPage.class);
            startActivity(intent);
        }


    }
}