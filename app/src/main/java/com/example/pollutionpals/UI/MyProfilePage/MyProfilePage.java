package com.example.pollutionpals.UI.MyProfilePage;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.LoginActivity.LoginActivity;
import com.example.pollutionpals.UI.MainPage.MainModule;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.UI.NewReportPage.NewReportPage;
import com.example.pollutionpals.UI.UpdateInfoPage.UpdateInfoPage;

/**
 * MyProfilePage class represents the user profile page.
 * It allows users to view their profile information and perform actions like logging out or updating information.
 */
public class MyProfilePage extends AppCompatActivity implements View.OnClickListener {

    // UI components
    Button btnBackProfile;
    TextView tvName, tvPointsProfile;
    LinearLayout layData, layLogOut, layDeleteUser;

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
        layDeleteUser = findViewById(R.id.layDeleteUser);
        layDeleteUser.setOnClickListener(this);
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
        if(layDeleteUser == view){ // if delete user layout is clicked
            alertUser();
        }
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
    public void alertUser(){
        // Prompt user to choose whether to delete their account or not
        android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myProfileModule = new MyProfileModule(getBaseContext());
                        myProfileModule.deleteUser();
                        Intent intent = new Intent(MyProfilePage.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}
