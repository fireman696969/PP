package com.example.pollutionpals.UI.MyProfilePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

/**
 * MyProfilePage class represents the user profile page.
 * It allows users to view their profile information and perform actions like logging out or updating information.
 */
public class MyProfilePage extends AppCompatActivity implements View.OnClickListener {

    // UI components
    Button btnBackProfile;
    TextView tvName, tvPointsProfile;
    LinearLayout layData, layLogOut;

    // Instance of MyProfileModule for handling profile operations
    MyProfileModule myProfileModule;

    /**
     * Called when the activity is first created.
     * Initializes UI components and sets up click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);

        // Initialize UI components
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

    /**
     * Handles button click events.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if(layLogOut == view){ // If logout layout is clicked
            myProfileModule = new MyProfileModule(this);
            myProfileModule.LogOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if(layData == view){ // If data layout is clicked
            Intent intent = new Intent(this, UpdateInfoPage.class);
            startActivity(intent);
        }
        if(btnBackProfile == view){ // If back button is clicked
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }
    }
}


//package com.example.pollutionpals.UI.MyProfilePage;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.pollutionpals.R;
//import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
//import com.example.pollutionpals.UI.MainPage.MainModule;
//import com.example.pollutionpals.UI.MainPage.MainPage;
//import com.example.pollutionpals.UI.UpdateInfoPage.UpdateInfoPage;
//
//public class MyProfilePage extends AppCompatActivity implements View.OnClickListener {
//    Button btnBackProfile;
//    TextView tvName, tvPointsProfile;
//
//    MyProfileModule myProfileModule;
//    LinearLayout layData,layLogOut;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_profile_page);
//
//        layData = findViewById(R.id.layData);
//        layLogOut = findViewById(R.id.layLogOut);
//        layData.setOnClickListener(this);
//        layLogOut.setOnClickListener(this);
//
//        btnBackProfile = findViewById(R.id.btnBackProfile);
//        btnBackProfile.setOnClickListener(this);
//
//        tvName = findViewById(R.id.tvName);
//        MainModule mainModule = new MainModule(this);
//        tvName.setText(mainModule.GetUserName());
//
//        tvPointsProfile = findViewById(R.id.tvPointsProfile);
//        tvPointsProfile.setText(mainModule.GetUserPoints()+"");
//
//
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(layLogOut == view){
//            myProfileModule = new MyProfileModule(this);
//            myProfileModule.LogOut();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }
//        if(layData == view){
//            Intent intent = new Intent(this, UpdateInfoPage.class);
//            startActivity(intent);
//        }
//        if(btnBackProfile == view){
//            Intent intent = new Intent(this, MainPage.class);
//            startActivity(intent);
//        }
//
//    }
//}