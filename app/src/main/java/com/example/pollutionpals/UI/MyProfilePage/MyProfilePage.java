package com.example.pollutionpals.UI.MyProfilePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
import com.example.pollutionpals.UI.MainPage.MainModule;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.UI.UpdateInfoPage.UpdateInfoPage;

public class MyProfilePage extends AppCompatActivity implements View.OnClickListener {
    Button btnBackProfile;
    TextView tvName, tvPointsProfile;

    MyProfileModule myProfileModule;
    LinearLayout layData,layLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);

        layData = findViewById(R.id.layData);
        layLogOut = findViewById(R.id.layLogOut);
        layData.setOnClickListener(this);
        layLogOut.setOnClickListener(this);

        btnBackProfile = findViewById(R.id.btnBackProfile);
        btnBackProfile.setOnClickListener(this);

        tvName = findViewById(R.id.tvName);
        MainModule mainModule = new MainModule(this);
        tvName.setText(mainModule.GetUserName());

        tvPointsProfile = findViewById(R.id.tvPointsProfile);
        tvPointsProfile.setText(mainModule.GetUserPoints()+"");




    }

    @Override
    public void onClick(View view) {
        if(layLogOut == view){
            myProfileModule = new MyProfileModule(this);
            myProfileModule.LogOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if(layData == view){
            Intent intent = new Intent(this, UpdateInfoPage.class);
            startActivity(intent);
        }
        if(btnBackProfile == view){
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }

    }
}