package com.example.pollutionpals.UI.UpdateInfoPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MyProfilePage.MyProfilePage;

/**
 * The UpdateInfoPage class handles the functionality for updating user information.
 */
public class UpdateInfoPage extends AppCompatActivity implements View.OnClickListener {
    TextView tvId;
    EditText edFullName, edAge, edPass;
    SharedPreferences sharedPreferences;
    Button btnBack, btnUpdateInfo;
    UpdateInfoModule updateInfoModule;

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_page);
        updateInfoModule = new UpdateInfoModule(this);
        sharedPreferences = updateInfoModule.getSharedPreferences();
        String fullName = sharedPreferences.getString("UserName", "");
        String id = sharedPreferences.getString("Id", "");
        int age = sharedPreferences.getInt("Age", -1);
        String pass = sharedPreferences.getString("Pass", "");

        tvId = findViewById(R.id.tvId);
        tvId.setText(id);

        edFullName = findViewById(R.id.edFullNameUpdateInfo);
        edFullName.setText(fullName);

        edAge = findViewById(R.id.edAgeUpdateInfo);
        edAge.setText(String.valueOf(age));

        edPass = findViewById(R.id.edPassUpdateInfo);
        edPass.setText(pass);

        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
        btnUpdateInfo.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBackUpdateInfo);
        btnBack.setOnClickListener(this);
    }

    /**
     * Handles click events for the back and update buttons.
     *
     * @param view The clicked view.
     */
    @Override
    public void onClick(View view) {
        if (btnBack == view) {
            Intent intent = new Intent(this, MyProfilePage.class);
            startActivity(intent);
        }
        if (btnUpdateInfo == view) {
            UpdateInfo();
            UpdateSharedPreference();
            Intent intent = new Intent(this, MyProfilePage.class);
            startActivity(intent);
        }
    }

    /**
     * Updates the user information in the database.
     */
    public void UpdateInfo() {

        String id = tvId.getText().toString().trim();
        String fullname = edFullName.getText().toString().trim();
        int age = Integer.parseInt(edAge.getText().toString().trim());
        String pass = edPass.getText().toString().trim();
        updateInfoModule.UpdateInfoById(id,fullname,age,pass);
    }

    /**
     * Updates the shared preferences with the new user information.
     */
    public void UpdateSharedPreference() {
        String id = tvId.getText().toString().trim();
        String fullname = edFullName.getText().toString().trim();
        int age = Integer.parseInt(edAge.getText().toString().trim());
        String pass = edPass.getText().toString().trim();
        sharedPreferences = updateInfoModule.getSharedPreferences();
        int points = sharedPreferences.getInt("Points", 0);
        updateInfoModule.updateSharedPreference(fullname, age, points, id, pass );
    }
}


//package com.example.pollutionpals.UI.UpdateInfoPage;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
//import com.example.pollutionpals.R;
//import com.example.pollutionpals.UI.MyProfilePage.MyProfilePage;
//
//public class UpdateInfoPage extends AppCompatActivity implements View.OnClickListener {
//    TextView tvId;
//    EditText edFullName, edAge, edPass;
//    SharedPreferences sharedPreferences;
//    Button btnBack, btnUpdateInfo;
//    UpdateInfoModule updateInfoModule;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_info_page);
//        updateInfoModule = new UpdateInfoModule(this);
//        sharedPreferences = updateInfoModule.getSharedPreferences();
//        String fullName = sharedPreferences.getString("UserName","");
//        String id = sharedPreferences.getString("Id","");
//        int age = sharedPreferences.getInt("Age",-1);
//        String pass = sharedPreferences.getString("Pass","");
//
//        tvId = findViewById(R.id.tvId);
//        tvId.setText(id);
//
//        edFullName = findViewById(R.id.edFullNameUpdateInfo);
//        edFullName.setText(fullName);
//
//        edAge = findViewById(R.id.edAgeUpdateInfo);
//        edAge.setText(age+"");
//
//        edPass = findViewById(R.id.edPassUpdateInfo);
//        edPass.setText(pass);
//
//        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);
//        btnUpdateInfo.setOnClickListener(this);
//
//        btnBack = findViewById(R.id.btnBackUpdateInfo);
//        btnBack.setOnClickListener(this);
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(btnBack == view){
//            Intent intent = new Intent(this, MyProfilePage.class);
//            startActivity(intent);
//        }
//        if(btnUpdateInfo == view){
//            UpdateInfo();
//            UpdateSharedPreference();
//            Intent intent = new Intent(this, MyProfilePage.class);
//            startActivity(intent);
//        }
//
//    }
//
//    public void UpdateInfo(){
//        MyDatabaseHelper citizens = new MyDatabaseHelper(this);
//        String id = tvId.getText().toString().trim();
//        String fullname = edFullName.getText().toString().trim();
//        int age = Integer.parseInt(edAge.getText().toString().trim());
//        String pass = edPass.getText().toString().trim();
//        citizens.UpdateInfoById(id,fullname,age,pass);
//
//    }
//    public void UpdateSharedPreference(){
//        String id = tvId.getText().toString().trim();
//        String fullname = edFullName.getText().toString().trim();
//        int age = Integer.parseInt(edAge.getText().toString().trim());
//        String pass = edPass.getText().toString().trim();
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("UserName");
//        editor.remove("Id");
//        editor.remove("Pass");
//
//        editor.putString("UserName", fullname);
//        editor.putInt("Age", age);
//        editor.putString("Id", id);
//        editor.putString("Pass", pass);
//
//        editor.apply();
//    }
//}