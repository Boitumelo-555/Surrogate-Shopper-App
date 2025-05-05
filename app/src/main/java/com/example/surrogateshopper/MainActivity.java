package com.example.surrogateshopper;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.surrogateshopper.services.LocationService;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private Button viewRequestsBtn, createRequestBtn, profileBtn, thankYouBtn;
    private SharedPreferences prefs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);


        viewRequestsBtn = findViewById(R.id.viewRequestsBtn);
        createRequestBtn = findViewById(R.id.createRequestBtn);
        profileBtn = findViewById(R.id.profileBtn);
        thankYouBtn = findViewById(R.id.thankYouBtn);


        checkLocationPermission();

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        viewRequestsBtn.setOnClickListener(v -> {

            if (isVolunteer()) {
                startActivity(new Intent(MainActivity.this, RequestsActivity.class));
            } else {
                Toast.makeText(MainActivity.this,
                        "Only volunteers can view requests", Toast.LENGTH_SHORT).show();
            }
        });

        createRequestBtn.setOnClickListener(v -> {

            if (isRequester()) {
                startActivity(new Intent(MainActivity.this, CreateRequestActivity.class));
            } else {
                Toast.makeText(MainActivity.this,
                        "Only requesters can create shopping requests", Toast.LENGTH_SHORT).show();
            }
        });

        profileBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

        thankYouBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ThankYouActivity.class)));
    }

    private boolean isVolunteer() {
        String userType = prefs.getString("userType", "");
        return "volunteer".equals(userType);
    }

    private boolean isRequester() {
        String userType = prefs.getString("userType", "");
        return "requester".equals(userType);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {

            startLocationService();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this,
                        "Location permission is required for delivery coordination",
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startLocationService() {
  
        if (isRequester()) {
            Intent serviceIntent = new Intent(this, LocationService.class);
            startForegroundService(serviceIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        if (isVolunteer()) {
            createRequestBtn.setEnabled(false);
            viewRequestsBtn.setEnabled(true);
        } else if (isRequester()) {
            createRequestBtn.setEnabled(true);
            viewRequestsBtn.setEnabled(false);
        }
    }
