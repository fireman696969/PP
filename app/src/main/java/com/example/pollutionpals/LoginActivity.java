package com.example.pollutionpals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText  etIdNumber, etPassword;
    private Button btnLogin, btnSignUp;

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






    }

    @Override
    public void onClick(View view) {
        if(btnLogin == view){
                try {
                    if (etIdNumber.getText().toString().length() == 0 ||
                            etPassword.getText().toString().length() == 0) {
                        Toast.makeText(this, "Fill ALL Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        MyDatabaseHelper Citizens = new MyDatabaseHelper(this);
                        if (Citizens.CheckCitizen(etIdNumber.getText().toString(), etPassword.getText().toString())) {
                            Toast.makeText(this, "exists", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, MainPage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e){
                    Log.d("exception" , " here");
                }
        }
        if(btnSignUp == view){
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }

    }
}