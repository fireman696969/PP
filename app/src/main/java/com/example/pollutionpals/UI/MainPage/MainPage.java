package com.example.pollutionpals.UI.MainPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pollutionpals.R;

public class MainPage extends AppCompatActivity {
    TextView tvUserName;
    MainModule mainModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        tvUserName = findViewById(R.id.tvUserName);
        mainModule = new MainModule(this);
        tvUserName.setText(mainModule.GetUserName());


    }
}