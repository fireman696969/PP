package com.example.pollutionpals.UI.NewReportPage;

import static android.text.InputType.TYPE_NULL;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
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
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pollutionpals.Data.Repository.Repository;
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

/**
 * NewReportPage class handles the functionality of creating a new report.
 */
public class NewReportPage extends AppCompatActivity implements View.OnClickListener {
    ImageView imgvCamera;
    NewReportModule newReportModule;
    static final int REQUEST_CODE = 22;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    EditText edDate, edDescription, edLocation;
    Spinner pointsSpinner;
    Button btnSubmit, btnBackNewReport;

    Bitmap photo;
    ImageView imgvGetLocation, imgvInformation;
    FusedLocationProviderClient fusedLocationClient;
    final Context context = this;
    String user_id;

    /**
     * Method called when the activity is created.
     *
     * @param savedInstanceState The saved instance state Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report_page);

        imgvCamera = findViewById(R.id.imgvCamera);
        imgvCamera.setTag("noPic");
        imgvCamera.setOnClickListener(this);
        newReportModule = new NewReportModule(this);
        btnBackNewReport = findViewById(R.id.btnBackNewReport);
        btnBackNewReport.setOnClickListener(this);

        imgvGetLocation = findViewById(R.id.imgvGetLocation);
        imgvGetLocation.setOnClickListener(this);
        user_id = newReportModule.getId();
        imgvInformation = findViewById(R.id.imgvGetInformation);
        imgvInformation.setOnClickListener(this);

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

    /**
     * Launches an action based on the clicked view.
     *
     * @param view The clicked view.
     */
    @Override
    public void onClick(View view) {
        if (imgvInformation == view) {
            // Display information dialog
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.information_dialog);
            dialog.show();
            dialog.setCancelable(true);
        }
        if (imgvGetLocation == view) {
            // Request location permissions
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("getting address");
            pd.setCancelable(true);
            pd.show();
            getLocation(new locationFetched() {
                @Override
                public void onLocationFetched(String address) {
                    if (address.equals("Address Not Found")) {
                        Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        return;
                    }
                    Toast.makeText(context, "Location fetched", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        if (imgvCamera == view) {
            // Prompt user to choose image capture method (Gallery or Camera)
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Which way would you prefer")
                    .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (ActivityCompat.checkSelfPermission(NewReportPage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                Intent galleryIntent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                                try {
                                    GalleryResultLauncher.launch(galleryIntent);
                                } catch (Exception e) {
                                    // Fallback for older Android versions
                                    OlderGalleryResultActivity.launch(new PickVisualMediaRequest.Builder()
                                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                            .build());
                                }
                            }
                        }
                    })
                    .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            someActivityResultLauncher.launch(cameraIntent);
                        }
                    }).show();
        }
        if (edDate == view) {
            // Show date dialog
            newReportModule.DateDialog(edDate);
        }
        if (btnSubmit == view) {
            if (imgvCamera.getTag().toString().equals("noPic")) {
                Toast.makeText(this, "Add a photo", Toast.LENGTH_SHORT).show();
            } else if (edDescription.getText().toString().equals("")) {
                Toast.makeText(this, "Add a Description", Toast.LENGTH_SHORT).show();
            } else if (edLocation.getText().toString().equals("")) {
                Toast.makeText(this, "Add the location", Toast.LENGTH_SHORT).show();
            } else if (edDate.getText().toString().equals("")) {
                Toast.makeText(this, "Pick a Date", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(pointsSpinner.getSelectedItem().toString()) == 0) {
                Toast.makeText(this, "Estimate points", Toast.LENGTH_SHORT).show();
            } else {
                // Add report to reports
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

    /**
     * Handles the result of permission requests.
     *
     * @param requestCode  The request code passed to requestPermissions().
     * @param permissions  The requested permissions.
     * @param grantResults The results of the permission requests.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permissions granted
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Interface for handling location fetched event.
     */
    public interface locationFetched {
        void onLocationFetched(String address);
    }

    /**
     * Retrieves the current location.
     *
     * @param callback Callback for handling location fetched event.
     */
    public void getLocation(locationFetched callback) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Location is disabled", Toast.LENGTH_SHORT).show();
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Get address from location
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
                            callback.onLocationFetched(edLocation.getText().toString());
                        } else callback.onLocationFetched("Address Not Found");
                    }
                });
    }

    /**
     * Launches an activity to capture an image from the camera.
     */
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        photo = (Bitmap) data.getExtras().get("data");
                        imgvCamera.setImageBitmap(photo);
                        int height = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 150)));
                        int width = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 250)));
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                        params.addRule(RelativeLayout.BELOW, R.id.tv1);
                        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        imgvCamera.setLayoutParams(params);
                        imgvCamera.setTag("Pic");
                    }
                }
            });

    /**
     * Launches an activity to select an image from the gallery.
     */
    ActivityResultLauncher<Intent> GalleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (!getContentResolver().getType((result.getData().getData())).startsWith("image/")) {
                            Toast.makeText(NewReportPage.this, "Images only!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Uri uriPhoto = result.getData().getData();
                        imgvCamera.setImageURI(uriPhoto);
                        try {
                            photo = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uriPhoto), 240, 320, false);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        imgvCamera.setTag("Pic");
                    }
                }
            });

    /**
     * Launches an activity to select an image from the gallery for older Android versions.
     */
    ActivityResultLauncher<PickVisualMediaRequest> OlderGalleryResultActivity =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    imgvCamera.setImageURI(uri);
                    try {
                        photo = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 240, 320, false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    imgvCamera.setTag("Pic");
                }
            });

    /**
     * Callback interface to handle location permission requests.
     */
    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (fineLocationGranted != null && fineLocationGranted) {
                    // Precise location access granted.
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Only approximate location access granted.
                } else {
                    // No location access granted.
                }
            });
}




//package com.example.pollutionpals.UI.NewReportPage;
//
//import static android.text.InputType.TYPE_NULL;
//
//import static androidx.core.content.ContentProviderCompat.requireContext;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.PickVisualMediaRequest;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.pollutionpals.Data.DB.ReportsDatabase;
//
//
//import com.example.pollutionpals.R;
//import com.example.pollutionpals.UI.MainPage.MainPage;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Locale;
//
//public class NewReportPage extends AppCompatActivity implements View.OnClickListener {
//    ImageView imgvCamera;
//    NewReportModule newReportModule;
//    static final int REQUEST_CODE = 22;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
//    EditText edDate, edDescription, edLocation;
//    Spinner pointsSpinner;
//    Button btnSubmit, btnBackNewReport;
//
//    Bitmap photo;
//    ImageView imgvGetLocation, imgvInformation;
//    FusedLocationProviderClient fusedLocationClient;
//    final Context context = this;
//    String user_id;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_report_page);
//
//        imgvCamera = findViewById(R.id.imgvCamera);
//        imgvCamera.setTag("noPic");
//        imgvCamera.setOnClickListener(this);
//        newReportModule = new NewReportModule(this);
//        btnBackNewReport = findViewById(R.id.btnBackNewReport);
//        btnBackNewReport.setOnClickListener(this);
//
//        imgvGetLocation = findViewById(R.id.imgvGetLocation);
//        imgvGetLocation.setOnClickListener(this);
//        user_id = newReportModule.getId();
//        imgvInformation = findViewById(R.id.imgvGetInformation);
//        imgvInformation.setOnClickListener(this);
//
//        imgvGetLocation.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                TextView tvLocationInfo = findViewById(R.id.tvLocationInfo);
//                tvLocationInfo.setVisibility(View.VISIBLE);
//                // Start a timer to hide the text view after a delay (e.g., 2 seconds)
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvLocationInfo.setVisibility(View.GONE);
//                    }
//                }, 2000);
//                return true;
//            }
//        });
//
//
//        newReportModule = new NewReportModule(this);
//
//        edDate = findViewById(R.id.edDate);
//        edDate.setOnClickListener(this);
//        edDate.setRawInputType(TYPE_NULL);
//
//        pointsSpinner = findViewById(R.id.spinnerPoints);
//
//        List<Integer> lst = new LinkedList<>();
//        for (int i = 0; i < 101; i++) {
//            lst.add(i);
//        }
//
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lst);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        pointsSpinner.setAdapter(adapter);
//        pointsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        btnSubmit = findViewById(R.id.btnSubmit);
//        btnSubmit.setOnClickListener(this);
//
//        edDescription = findViewById(R.id.edDiscription);
//        edLocation = findViewById(R.id.edLocation);
//
//
//
//    }
//    ActivityResultLauncher<String[]> locationPermissionRequest =
//            registerForActivityResult(new ActivityResultContracts
//                            .RequestMultiplePermissions(), result -> {
//                        Boolean fineLocationGranted = result.getOrDefault(
//                                Manifest.permission.ACCESS_FINE_LOCATION, false);
//                        Boolean coarseLocationGranted = result.getOrDefault(
//                                Manifest.permission.ACCESS_COARSE_LOCATION,false);
//                        if (fineLocationGranted != null && fineLocationGranted) {
//                            // Precise location access granted.
//                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
//                            // Only approximate location access granted.
//                        } else {
//                            // No location access granted.
//                        }
//                    }
//            );
//    @Override
//    public void onClick(View view) {
//        if(imgvInformation == view){
//
//            Dialog dialog=new Dialog(this);
//            dialog.setContentView(R.layout.information_dialog);
//            dialog.show();
//            dialog.setCancelable(true);
//
//        }
//        if (imgvGetLocation == view) {
//
//            //Asking if Location Permission is Granted
//
//            //If not granted asking for permission:
//            locationPermissionRequest.launch(new String[] {
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//            });
//            ProgressDialog pd = new ProgressDialog(this);
//            pd.setTitle("getting address");
//            pd.setCancelable(false);
//            pd.show();
//            getLocation(new locationFetched() {
//                @Override
//                public void onLocationFetched(String address) {
//                    if(address.equals("Address Not Found"))
//                    {
//                        Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show();
//                        pd.dismiss();
//                        return;
//                    }
//                    Toast.makeText(context, "Location fetched", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
//                }
//            });
//        }
//
//        if (imgvCamera == view) {
////            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////            someActivityResultLauncher.launch(intent);
//            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//            dialog.setMessage("Which way would you prefer")
//                    .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            if(ActivityCompat.checkSelfPermission(NewReportPage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                                Intent galleryIntent = new Intent(MediaStore.ACTION_PICK_IMAGES);
//                                try {
//                                    GalleryResultLauncher.launch(galleryIntent);
//                                } catch (Exception e)
//                                {
//                                    OlderGalleryResultActivity.launch(new PickVisualMediaRequest.Builder()
//                                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
//                                            .build());
//
//                                }
//                            }
//                        }
//                    })
//                    .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            someActivityResultLauncher.launch(cameraIntent);
//
//
//                        }
//                    }).show();
//
//        }
//        if (edDate == view) {
//            // show date dialog
//            newReportModule.DateDialog(edDate);
//        }
//        if (btnSubmit == view) {
//            if(imgvCamera.getTag().toString().equals("noPic")){
//                Toast.makeText(this, "Add a photo", Toast.LENGTH_SHORT).show();
//            }
//            else if (edDescription.getText().toString().equals("")) {
//                Toast.makeText(this, "Add a Description", Toast.LENGTH_SHORT).show();
//            } else if (edLocation.getText().toString().equals("")) {
//                Toast.makeText(this, "Add the location", Toast.LENGTH_SHORT).show();
//            } else if (edDate.getText().toString().equals("")) {
//                Toast.makeText(this, "Pick a Date", Toast.LENGTH_SHORT).show();
//            } else if (Integer.parseInt(pointsSpinner.getSelectedItem().toString()) == 0) {
//                Toast.makeText(this, "Estimate points", Toast.LENGTH_SHORT).show();
//            } else {
//                newReportModule.AddReportToReports(user_id, photo, edDescription, edLocation, edDate, pointsSpinner);
//                Intent intent = new Intent(this, MainPage.class);
//                startActivity(intent);
//            }
//        }
//        if (btnBackNewReport == view) {
//            Intent intent = new Intent(this, MainPage.class);
//            startActivity(intent);
//        }
//
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//            } else {
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    public interface locationFetched
//    {
//        void onLocationFetched(String address);
//    }
//    public void getLocation(locationFetched callback) {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, "Location is disabled", Toast.LENGTH_SHORT).show();
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Get the address from the location
//                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                            try {
//                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                                if (addresses.size() > 0) {
//                                    Address address = addresses.get(0);
//                                    String addressString = address.getAddressLine(0);
//                                    edLocation.setText(addressString);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            callback.onLocationFetched(edLocation.getText().toString());
//                        }
//                        else callback.onLocationFetched("Address Not Found");
//                    }
//                });
//    }
//
//
//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                        photo = (Bitmap) data.getExtras().get("data");
//                        imgvCamera.setImageBitmap(photo);
//                        int height = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 150)));
//                        int width = Integer.parseInt(String.valueOf(Math.round(getBaseContext().getResources().getDisplayMetrics().density * 250)));
//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
//                        params.addRule(RelativeLayout.BELOW, R.id.tv1);
//                        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                        imgvCamera.setLayoutParams(params);
//                        imgvCamera.setTag("Pic");
//
//                    }
//                }
//            });
//
//
//
//ActivityResultLauncher<Intent> GalleryResultLauncher = registerForActivityResult(
//        new ActivityResultContracts.StartActivityForResult(),
//        new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    // There are no request codes
//                    if(!getContentResolver().getType((result.getData().getData())).startsWith("image/"))
//                    {
//                        Toast.makeText(NewReportPage.this, "Images only!", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    Uri uriPhoto = result.getData().getData();
//                    imgvCamera.setImageURI(uriPhoto);
//                    try {
//                        photo = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uriPhoto),240,320, false);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    imgvCamera.setTag("Pic");
//
//                }
//            }
//        });
//    ActivityResultLauncher<PickVisualMediaRequest> OlderGalleryResultActivity =
//            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
//                if (uri != null) {
//                    // There are no request codes
//                    imgvCamera.setImageURI(uri);
//                    try {
//                        photo = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 240, 320, false);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    imgvCamera.setTag("Pic");
//                }
//            });
//
//
//}
//
