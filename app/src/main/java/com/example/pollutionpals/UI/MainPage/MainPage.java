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
import com.example.pollutionpals.UI.NewReportPage.NewReportPage;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
    TextView tvUserName;
    MainModule mainModule;
    Button btnMyProfilePage, btnNewReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        tvUserName = findViewById(R.id.tvUserName);
        mainModule = new MainModule(this);
        tvUserName.setText(mainModule.GetUserName());
        btnMyProfilePage = findViewById(R.id.btnMyProfilePage);
        btnNewReport = findViewById(R.id.btnNewReport);


        btnMyProfilePage.setOnClickListener(this);
        btnNewReport.setOnClickListener(this);


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


    }
}