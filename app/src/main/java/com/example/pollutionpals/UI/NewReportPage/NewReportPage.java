package com.example.pollutionpals.UI.NewReportPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pollutionpals.R;

public class NewReportPage extends AppCompatActivity implements View.OnClickListener {
    ImageView imgvCamera;
    NewReportModule newReportModule;
    static final int REQUEST_CODE = 22;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report_page);
        imgvCamera = findViewById(R.id.imgvCamera);
        imgvCamera.setOnClickListener(this);
        newReportModule = new NewReportModule(this);

    }

    @Override
    public void onClick(View view) {
        if (imgvCamera == view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE);
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
