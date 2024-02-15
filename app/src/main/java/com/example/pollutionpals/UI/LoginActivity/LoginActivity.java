package com.example.pollutionpals.UI.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.SignUp.SignUp;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText  etIdNumber, etPassword;
    private Button btnLogin, btnSignUp;
    private LoginModule loginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etIdNumber = findViewById(R.id.etIdNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        loginModule = new LoginModule(this);







    }

    @Override
    public void onClick(View view) {
        if(btnLogin == view){
            loginModule.CheckCitizen(etIdNumber,etPassword);

        }
        if(btnSignUp == view){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }


    }
}