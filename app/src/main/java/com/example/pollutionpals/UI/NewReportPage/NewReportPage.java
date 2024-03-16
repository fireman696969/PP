package com.example.pollutionpals.UI.NewReportPage;

import static android.text.InputType.TYPE_NULL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.pollutionpals.Data.DB.ReportsDatabase;
import com.example.pollutionpals.R;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class NewReportPage extends AppCompatActivity implements View.OnClickListener {
    ImageView imgvCamera;
    NewReportModule newReportModule;
    static final int REQUEST_CODE = 22;
    EditText edDate, edDescripton, edLocation;
    int y=0,m=0,d=0;
    Spinner pointsSpinner;
    Button btnSubmit;
    SharedPreferences sharedpreference;
    Bitmap photo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report_page);
        NewReportModule newReoprtsModule = new NewReportModule(this);

        imgvCamera = findViewById(R.id.imgvCamera);
        imgvCamera.setOnClickListener(this);

        newReportModule = new NewReportModule(this);

        edDate = findViewById(R.id.edDate);
        edDate.setOnClickListener(this);
        edDate.setRawInputType(TYPE_NULL);

        pointsSpinner = findViewById(R.id.spinnerPoints);
        List<Integer> lst = new LinkedList<>();
        for (int i = 0; i < 101; i++) {
            lst.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,lst);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pointsSpinner.setAdapter(adapter);
        pointsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        edDescripton = findViewById(R.id.edDiscription);
        edLocation = findViewById(R.id.edLocation);
        sharedpreference = this.getSharedPreferences("user", Context.MODE_PRIVATE);


    }

    @Override
    public void onClick(View view) {
        if (imgvCamera == view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE);
        }
        if(edDate == view){
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            c.set(year,month,day);

            Calendar c2 = Calendar.getInstance();
            int year2 = c2.get(Calendar.YEAR)-70;
            int month2 = c2.get(Calendar.MONTH);
            int day2 = c2.get(Calendar.DAY_OF_MONTH);
            c2.set(year2,month2,day2);

            DatePickerDialog dateDialog = new DatePickerDialog(this);
            dateDialog.setCancelable(false);

            dateDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            dateDialog.getDatePicker().setMinDate(c2.getTimeInMillis());

            if(y!=0){
                dateDialog.updateDate(y,m,d);
            }
            dateDialog.show();

            dateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    edDate.setText(i + "/ " + (i1+1) + " / " + i2);
                    y=i;
                    m= i1;
                    d= i2;
                }
            });
        }
        if(btnSubmit == view){
            if(edDescripton.getText().toString().equals("")){
                Toast.makeText(this, "Add a Description", Toast.LENGTH_SHORT).show();
            }
            else if(edLocation.getText().toString().equals("")){
                Toast.makeText(this, "Add the location", Toast.LENGTH_SHORT).show();
            }
            else if( edDate.getText().toString().equals("")){
                Toast.makeText(this, "Pick a Date", Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(pointsSpinner.getSelectedItem().toString()) == 0){
                Toast.makeText(this, "Estimate points", Toast.LENGTH_SHORT).show();
            }
            else{
                ReportsDatabase reports = new ReportsDatabase(this);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                reports.AddReport(sharedpreference.getString("Id", "0"), byteArray,edDescripton.getText().toString(), edLocation.getText().toString(),edDate.getText().toString(), Integer.parseInt(pointsSpinner.getSelectedItem().toString())  );
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
             photo = (Bitmap) data.getExtras().get("data");
            imgvCamera.setImageBitmap(photo);
            int height = Integer.parseInt(String.valueOf(Math.round(this.getResources().getDisplayMetrics().density * 150)));
            int width = Integer.parseInt(String.valueOf(Math.round(this.getResources().getDisplayMetrics().density * 250)));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
            params.addRule(RelativeLayout.BELOW, R.id.tv1);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imgvCamera.setLayoutParams(params);



        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
