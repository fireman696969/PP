package com.example.pollutionpals.UI.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.Repository.Repository;
import com.example.pollutionpals.UI.MainPage.MainPage;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

/**
 * LoginModule class handles user login functionality.
 */
public class LoginModule {
    // Repository instance for database interactions
    Repository rep;
    // Context for accessing application resources
    public Context context;
//    private FirebaseAuth mAuth;

    /**
     * Constructor for the LoginModule class
     * @param context - application context
     */
    public LoginModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }

    /**
     * Sets user credentials in shared preferences.
     * @param checkbox - CheckBox instance (not used in the method)
     * @param userName - user name
     * @param Age - user age
     * @param Points - user points
     * @param Id - user ID
     * @param Pass - user password
     */
    public void SetCredentials(CheckBox checkbox, String userName, int Age, int Points, String Id, String Pass) {
        rep.updateSharedPreference(userName, Age, Points, Id, Pass);
    }

    /**
     * Checks if a citizen exists in the database and logs them in if credentials are valid.
     * @param etIdNumber - EditText containing user ID
     * @param etPassword - EditText containing user password
     */
    public void CheckCitizen(EditText etIdNumber, EditText etPassword) {
        if (etIdNumber.getText().toString().length() == 0 ||
                etPassword.getText().toString().length() == 0) {
            Toast.makeText(context, "Fill ALL Fields", Toast.LENGTH_SHORT).show();
        } else {
            MyDatabaseHelper Citizens = new MyDatabaseHelper(context);
            if (Citizens.CheckCitizen(etIdNumber.getText().toString(), etPassword.getText().toString())) {
                Toast.makeText(context, "exists", Toast.LENGTH_SHORT).show();
                Cursor cursor = rep.getUserById(etIdNumber.getText().toString());

                cursor.moveToFirst();
                String fullname = cursor.getString(1);
                int age = cursor.getInt(2);
                int points = cursor.getInt(3);
                String id = cursor.getString(4);
                String pass = cursor.getString(5);
                SetCredentials(new CheckBox(context), fullname, age, points, id, pass);
                Intent intent = new Intent(context, MainPage.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "does not exist", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public SharedPreferences getSharedPreference() {
        return rep.getSharedPreference();
    }

//    public void AddFirebase(EditText et1, EditText et2){
//        mAuth = FirebaseAuth.getInstance();
//        String id = et1.getText().toString().trim();
//        String pass = et2.getText().toString().trim();
//
//        mAuth.createUserWithEmailAndPassword(id, pass)
//                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
//
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//
//                            Toast.makeText(context, "exists", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//    }
}


//package com.example.pollutionpals.UI.LoginActivity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
//import com.example.pollutionpals.Data.Repository.Repository;
//import com.example.pollutionpals.UI.MainPage.MainPage;
////import com.google.android.gms.tasks.OnCompleteListener;
////import com.google.android.gms.tasks.Task;
////import com.google.firebase.auth.AuthResult;
////import com.google.firebase.auth.FirebaseAuth;
////import com.google.firebase.auth.FirebaseUser;
//
//public class LoginModule {
//    Repository rep;
//    public Context context;
////    private FirebaseAuth mAuth;
//
//
//
//    public LoginModule(Context context) {
//        rep = new Repository(context);
//        this.context = context;
//    }
//
//
//    public void SetCredentials(CheckBox checkbox, String userName, int Age, int Points, String Id, String Pass) {
//        rep.updateSharedPreference(userName, Age, Points, Id, Pass);
//
//    }
//
//    public void CheckCitizen(EditText etIdNumber, EditText etPassword) {
//
//        if (etIdNumber.getText().toString().length() == 0 ||
//                etPassword.getText().toString().length() == 0) {
//            Toast.makeText(context, "Fill ALL Fields", Toast.LENGTH_SHORT).show();
//        } else {
//            MyDatabaseHelper Citizens = new MyDatabaseHelper(context);
//            if (Citizens.CheckCitizen(etIdNumber.getText().toString(), etPassword.getText().toString())) {
//                Toast.makeText(context, "exists", Toast.LENGTH_SHORT).show();
//                Cursor cursor = rep.getUserById(etIdNumber.getText().toString());
//
//                cursor.moveToFirst();
//                String fullname = cursor.getString(1);
//                int age = cursor.getInt(2);
//                int points = cursor.getInt(3);
//                String id = cursor.getString(4);
//                String pass = cursor.getString(5);
//                SetCredentials(new CheckBox(context),fullname,age,points,id,pass);
//                Intent intent = new Intent(context, MainPage.class);
//                context.startActivity(intent);
//
//            } else {
//                Toast.makeText(context, "does not exist", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }
////    public void AddFirebase( EditText et1, EditText et2){
////        mAuth = FirebaseAuth.getInstance();
////        String id = et1.getText().toString().trim();
////        String pass = et2.getText().toString().trim();
////
////        mAuth.createUserWithEmailAndPassword(id, pass)
////                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if (task.isSuccessful()) {
////                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
////
////                            FirebaseUser user = mAuth.getCurrentUser();
////
////                        } else {
////                            // If sign in fails, display a message to the user.
////
////                            Toast.makeText(context, "exists", Toast.LENGTH_SHORT).show();
////
////                        }
////                    }
////                });
////
////    }
//}
