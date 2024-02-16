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

public class MainPage extends AppCompatActivity implements View.OnClickListener {
    TextView tvUserName;
    MainModule mainModule;
    Button btnMyProfilePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        tvUserName = findViewById(R.id.tvUserName);
        mainModule = new MainModule(this);
        tvUserName.setText(mainModule.GetUserName());
        btnMyProfilePage = findViewById(R.id.btnMyProfilePage);
        btnMyProfilePage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(btnMyProfilePage == view){
            Intent intent = new Intent(this, MyProfilePage.class);
            startActivity(intent);
        }

    }
}