package com.example.pollutionpals.UI.QuestionsAndAnswersPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MainPage.MainPage;

public class QuestionsAndAnswersPage extends AppCompatActivity implements View.OnClickListener {
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_and_answers_page);
        btnBack = findViewById(R.id.btnBackQA);
        btnBack.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(btnBack == view){
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }
    }
}