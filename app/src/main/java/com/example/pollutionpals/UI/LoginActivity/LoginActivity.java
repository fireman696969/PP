

package com.example.pollutionpals.UI.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.DB.ReportsDatabase;
import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.example.pollutionpals.UI.SignUp.SignUp;


/**
 * LoginActivity class handles user login functionality.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI components
    private EditText etIdNumber, etPassword;
    private Button btnLogin, btnSignUp;
    // Instance of LoginModule for handling login operations
    private LoginModule loginModule;

    /**
     * Called when the activity is first created.
     * Initializes UI components and sets up click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        etIdNumber = findViewById(R.id.etIdNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Set click listeners for buttons
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        // Initialize LoginModule
        loginModule = new LoginModule(this);

        // check if the user is still connected, if so move him to the main page
        SharedPreferences sharedPreference = loginModule.getSharedPreference();
        if(!(sharedPreference.getString("UserName","").isEmpty())){
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }


    }

    /**
     * Handles button click events.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (btnLogin == view) { // If login button is clicked
            // Call method to check user credentials
            loginModule.CheckCitizen(etIdNumber, etPassword);
        }
        if (btnSignUp == view) { // If sign up button is clicked
            // Navigate to sign up activity
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
    }
}


//package com.example.pollutionpals.UI.LoginActivity;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.pollutionpals.UI.MainPage.MainPage;
//import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
//import com.example.pollutionpals.R;
//import com.example.pollutionpals.UI.SignUp.SignUp;
//
//public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
//    private EditText  etIdNumber, etPassword;
//    private Button btnLogin, btnSignUp;
//    private LoginModule loginModule;
//    private TextView tvUserName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        etIdNumber = findViewById(R.id.etIdNumber);
//        etPassword = findViewById(R.id.etPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        btnSignUp = findViewById(R.id.btnSignUp);
//        btnLogin.setOnClickListener(this);
//        btnSignUp.setOnClickListener(this);
//        loginModule = new LoginModule(this);
//
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(btnLogin == view){
//            loginModule.CheckCitizen(etIdNumber,etPassword);
////            loginModule.AddFirebase(etIdNumber,etPassword);
//
//        }
//        if(btnSignUp == view){
//            Intent intent = new Intent(this, SignUp.class);
//            startActivity(intent);
//        }
//
//
//    }
//}