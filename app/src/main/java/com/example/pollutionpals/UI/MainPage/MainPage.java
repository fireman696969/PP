package com.example.pollutionpals.UI.MainPage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.pollutionpals.MainActivity;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
import com.example.pollutionpals.UI.MyProfilePage.MyProfilePage;
import com.example.pollutionpals.UI.MyReportsPage.MyReportsPage;
import com.example.pollutionpals.UI.NewReportPage.NewReportPage;
import com.example.pollutionpals.UI.QuestionsAndAnswersPage.QuestionsAndAnswersPage;

/**
 * MainPage class represents the main page of the application.
 * It displays user information and provides navigation to various functionalities.
 */
public class MainPage extends AppCompatActivity implements View.OnClickListener {

    // UI components
    TextView tvUserName;
    Button btnMyProfilePage, btnNewReport, btnMyReports, btnQA;
    // Instance of MainModule for handling main page operations
    MainModule mainModule;

    /**
     * Called when the activity is first created.
     * Initializes UI components and sets up click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize MainModule
        mainModule = new MainModule(this);

        // Retrieve SharedPreferences
        SharedPreferences sharedPreference = mainModule.getSharedPreference();

        // Check if user is logged in
        if(sharedPreference.getInt("Age", -1) == -1){
            // Redirect to login page if user is not logged in
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        // Initialize UI components
        tvUserName = findViewById(R.id.tvUserName);
        btnMyProfilePage = findViewById(R.id.btnMyProfilePage);
        btnNewReport = findViewById(R.id.btnNewReport);
        btnMyReports = findViewById(R.id.btnAllReports);
        btnQA = findViewById(R.id.btnQA);

        // Set username text
        tvUserName.setText(mainModule.GetUserName());

        // Set button text based on user ID
        if(mainModule.GetUserId().equals("329455109")){
            btnMyReports.setText("ALL REPORTS");
        }

        // Set click listeners for buttons
        btnMyProfilePage.setOnClickListener(this);
        btnNewReport.setOnClickListener(this);
        btnMyReports.setOnClickListener(this);
        btnQA.setOnClickListener(this);
    }

    /**
     * Handles button click events.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if(btnMyProfilePage == view){
            // Navigate to My Profile page
            Intent intent = new Intent(this, MyProfilePage.class);
            startActivity(intent);
        }
        if(btnNewReport == view){
            // Navigate to New Report page
            Intent intent = new Intent(this, NewReportPage.class);
            startActivity(intent);
        }
        if(btnMyReports == view){
            // Navigate to My Reports page
            Intent intent = new Intent(this, MyReportsPage.class);
            startActivity(intent);
        }
        if(btnQA == view){
            // Navigate to Questions and Answers page
            Intent intent = new Intent(this, QuestionsAndAnswersPage.class);
            startActivity(intent);
        }
    }
}


//package com.example.pollutionpals.UI.MainPage;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.pollutionpals.MainActivity;
//import com.example.pollutionpals.R;
//import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
//import com.example.pollutionpals.UI.MyProfilePage.MyProfilePage;
//import com.example.pollutionpals.UI.MyReportsPage.MyReportsPage;
//import com.example.pollutionpals.UI.NewReportPage.NewReportPage;
//import com.example.pollutionpals.UI.QuestionsAndAnswersPage.QuestionsAndAnswersPage;
//
//public class MainPage extends AppCompatActivity implements View.OnClickListener {
//    TextView tvUserName;
//    MainModule mainModule;
//    Button btnMyProfilePage, btnNewReport, btnMyReports, btnQA;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_page);
//        mainModule = new MainModule(this);
//        SharedPreferences sharedPreference = mainModule.getSharedPreference();
//        if(sharedPreference.getInt("Age", -1) == -1){
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }
//        tvUserName = findViewById(R.id.tvUserName);
//
//        tvUserName.setText(mainModule.GetUserName());
//        btnMyProfilePage = findViewById(R.id.btnMyProfilePage);
//        btnNewReport = findViewById(R.id.btnNewReport);
//        btnMyReports = findViewById(R.id.btnAllReports);
//
//        if(mainModule.GetUserId().equals("329455109")){
//            btnMyReports.setText("ALL REPORTS");
//        }
//        btnQA = findViewById(R.id.btnQA);
//
//
//
//
//        btnMyProfilePage.setOnClickListener(this);
//        btnNewReport.setOnClickListener(this);
//        btnMyReports.setOnClickListener(this);
//        btnQA.setOnClickListener(this);
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(btnMyProfilePage == view){
//            Intent intent = new Intent(this, MyProfilePage.class);
//            startActivity(intent);
//        }
//        if(btnNewReport == view){
//            Intent intent = new Intent(this, NewReportPage.class);
//            startActivity(intent);
//        }
//        if(btnMyReports == view){
//            Intent intent = new Intent(this, MyReportsPage.class);
//            startActivity(intent);
//        }
//        if(btnQA == view){
//            Intent intent = new Intent(this, QuestionsAndAnswersPage.class);
//            startActivity(intent);
//        }
//
//
//    }
//}