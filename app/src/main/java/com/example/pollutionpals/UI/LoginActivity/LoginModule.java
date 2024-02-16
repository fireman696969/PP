package com.example.pollutionpals.UI.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.Repository.Repository;
import com.example.pollutionpals.UI.MainPage.MainPage;

public class LoginModule {
    Repository rep;
    public Context context;

    public LoginModule(Context context) {
        rep = new Repository(context);
        this.context = context;
    }


    public void SetCredentials(CheckBox checkbox, String Fname, String Lname, int Age, String Address, String Email, String Id, String Pass) {
        SharedPreferences sharedPreference = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        String userName = Fname + " " + Lname;
        editor.putString("UserName", userName);
        editor.putString("Email", Email);
        editor.putInt("Age", Age);
        editor.putString("Address", Address);
        editor.putString("Id", Id);
        editor.putString("Pass", Pass);
        editor.apply();

    }

    public void CheckCitizen(EditText etIdNumber, EditText etPassword) {

        if (etIdNumber.getText().toString().length() == 0 ||
                etPassword.getText().toString().length() == 0) {
            Toast.makeText(context, "Fill ALL Fields", Toast.LENGTH_SHORT).show();
        } else {
            MyDatabaseHelper Citizens = new MyDatabaseHelper(context);
            if (Citizens.CheckCitizen(etIdNumber.getText().toString(), etPassword.getText().toString())) {
                Toast.makeText(context, "exists", Toast.LENGTH_SHORT).show();
                Cursor cursor = Citizens.getUserById(etIdNumber.getText().toString());

                cursor.moveToFirst();
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                int age = cursor.getInt(3);
                String email = cursor.getString(4);
                String address = cursor.getString(5);
                String id = cursor.getString(6);
                String pass = cursor.getString(7);
                SetCredentials(new CheckBox(context),fname,lname,age,address,email,id,pass);
                Intent intent = new Intent(context, MainPage.class);
                context.startActivity(intent);

            } else {
                Toast.makeText(context, "does not exist", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
