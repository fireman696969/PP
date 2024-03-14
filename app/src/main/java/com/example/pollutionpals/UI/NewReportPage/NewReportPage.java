package com.example.pollutionpals.UI.NewReportPage;

import static android.text.InputType.TYPE_NULL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pollutionpals.R;

import java.util.Calendar;

public class NewReportPage extends AppCompatActivity implements View.OnClickListener {
    ImageView imgvCamera;
    NewReportModule newReportModule;
    static final int REQUEST_CODE = 22;
    EditText edDate;
    int y=0,m=0,d=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report_page);

        imgvCamera = findViewById(R.id.imgvCamera);
        imgvCamera.setOnClickListener(this);

        newReportModule = new NewReportModule(this);

        edDate = findViewById(R.id.edDate);
        edDate.setOnClickListener(this);
        edDate.setRawInputType(TYPE_NULL);

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
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
