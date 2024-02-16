package com.example.pollutionpals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.UI.SignUp.SignUp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        SharedPreferences sharedPreference = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sharedPreference.getInt("Age", -1) == -1){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, MainPage.class);
            startActivity(intent);
        }



    }

    @Override
    public void onClick(View view) {
        if(btnSignUp == view){
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        }
    }
}