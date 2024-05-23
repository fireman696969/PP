package com.example.pollutionpals.UI.NewReportPage;

import static android.text.InputType.TYPE_NULL;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pollutionpals.Data.DB.ReportsDatabase;


import com.example.pollutionpals.R;
import com.example.pollutionpals.UI.MainPage.MainPage;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NewReportPage extends AppCompatActivity implements View.OnClickListener {
    ImageView imgvCamera;
    NewReportModule newReportModule;
    static final int REQUEST_CODE = 22;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    EditText edDate, edDescription, edLocation;
    Spinner pointsSpinner;
    Button btnSubmit, btnBackNewReport;

    Bitmap photo;
    ImageView imgvGetLocation;
    FusedLocationProviderClient fusedLocationClient;
    final Context context = this;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report_page);

        imgvCamera = findViewById(R.id.imgvCamera);
        imgvCamera.setOnClickListener(this);
        newReportModule = new NewReportModule(this);
        btnBackNewReport = findViewById(R.id.btnBackNewReport);
        btnBackNewReport.setOnClickListener(this);

        imgvGetLocation = findViewById(R.id.imgvGetLocation);
        imgvGetLocation.setOnClickListener(this);
        user_id = newReportModule.getId();

        imgvGetLocation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView tvLocationInfo = findViewById(R.id.tvLocationInfo);
                tvLocationInfo.setVisibility(View.VISIBLE);
                // Start a timer to hide the text view after a delay (e.g., 2 seconds)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvLocationInfo.setVisibility(View.GONE);
                    }
                }, 2000);
                return true;
            }
        });


        newReportModule = new NewReportModule(this);

        edDate = findViewById(R.id.edDate);
        edDate.setOnClickListener(this);
        edDate.setRawInputType(TYPE_NULL);

        pointsSpinner = findViewById(R.id.spinnerPoints);

        List<Integer> lst = new LinkedList<>();
        for (int i = 0; i < 101; i++) {
            lst.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lst);
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

        edDescription = findViewById(R.id.edDiscription);
        edLocation = findViewById(R.id.edLocation);



    }

    @Override
    public void onClick(View view) {
        if (imgvGetLocation == view) {
            getLocation();
        }

        if (imgvCamera == view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            someActivityResultLauncher.launch(intent);
        }
        if (edDate == view) {
            // show date dialog
            newReportModule.DateDialog(edDate);
        }
        if (btnSubmit == view) {
            if (edDescription.getText().toString().equals("")) {
                Toast.makeText(this, "Add a Description", Toast.LENGTH_SHORT).show();
            } else if (edLocation.getText().toString().equals("")) {
                Toast.makeText(this, "Add the location", Toast.LENGTH_SHORT).show();
            } else if (edDate.getText().toString().equals("")) {
                Toast.makeText(this, "Pick a Date", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(pointsSpinner.getSelectedItem().toString()) == 0) {
                Toast.makeText(this, "Estimate points", Toast.LENGTH_SHORT).show();
            } else {
                newReportModule.AddReportToReports(user_id, photo, edDescription, edLocation, edDate, pointsSpinner);
                Intent intent = new Intent(this, MainPage.class);
                startActivity(intent);
            }
        }
        if (btnBackNewReport == view) {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {


                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Get the address from the location
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses.size() > 0) {
                                    Address address = addresses.get(0);
                                    String addressString = address.getAddressLine(0);
                                    edLocation.setText(addressString);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        photo = (Bitmap) data.getExtras().get("data");
                        imgvCamera.setImageBitmap(photo);
                        int height = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 150)));
                        int width = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 250)));
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                        params.addRule(RelativeLayout.BELOW, R.id.tv1);
                        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        imgvCamera.setLayoutParams(params);

                    }
                }
            });

    }


