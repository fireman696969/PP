package com.example.pollutionpals.UI.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText etFullname,  etAge, etId, etPass, etConPass;
    private Button btnSubmit, btnMoveToLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFullname = findViewById(R.id.etFullName);
        etAge = findViewById(R.id.etAge);
        etId = findViewById(R.id.etIDNumber);
        etPass = findViewById(R.id.etPassword);
        etConPass = findViewById(R.id.etConfirmPassword);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnMoveToLog = findViewById(R.id.btnLogin);
        btnSubmit.setOnClickListener(this);
        btnMoveToLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(btnSubmit == view){
            MyDatabaseHelper Citizens = new MyDatabaseHelper(this);
            if(     etFullname.getText().toString().length() == 0 ||
                    etAge.getText().toString().length() == 0 ||
                    etId.getText().toString().length() == 0 ||
                    etPass.getText().toString().length() == 0 ||
                    etConPass.getText().toString().length() == 0 ){
                Toast.makeText(this, "Fill ALL Fields", Toast.LENGTH_SHORT).show();
            }
            else if( Integer.parseInt(etAge.getText().toString()) <18){
                Toast.makeText(this, "Age Must be OVER 18", Toast.LENGTH_SHORT).show();
            }
            else if(!etConPass.getText().toString().equals(etPass.getText().toString())){
                Toast.makeText(this, "Confirm right password", Toast.LENGTH_SHORT).show();
            }
            else if(Citizens.CheckIfAlreadyExists(etId.getText().toString())){
                Toast.makeText(this, "Id already in use", Toast.LENGTH_SHORT).show();
            }
            else{

                Citizens.AddCitizen(etFullname.getText().toString(),
                        Integer.parseInt(etAge.getText().toString()) ,
                        etId.getText().toString(),
                        etPass.getText().toString()
                        );
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }


        }
        if(btnMoveToLog == view){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }
}