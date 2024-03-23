package com.example.pollutionpals.UI.MyProfilePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;

public class MyProfilePage extends AppCompatActivity implements View.OnClickListener {

    Button btnLogOut;
    MyProfileModule myProfileModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);
//        btnLogOut = findViewById(R.id.btnLogOut);
//        btnLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if(btnLogOut == view){
//            myProfileModule = new MyProfileModule(this);
//            TextView tvUserName = findViewById(R.id.tvUserName);
//            myProfileModule.LogOut();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }

    }
}