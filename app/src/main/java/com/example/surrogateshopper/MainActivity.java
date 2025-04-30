package com.example.surrogateshopper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.surrogateshopper.services.LocationService;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    Button viewRequestsBtn, createRequestBtn, profileBtn, thankYouBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewRequestsBtn = findViewById(R.id.viewRequestsBtn);
        createRequestBtn = findViewById(R.id.createRequestBtn);
        profileBtn = findViewById(R.id.profileBtn);
        thankYouBtn = findViewById(R.id.thankYouBtn);

        checkLocationPermission();

        viewRequestsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RequestsActivity.class);
            startActivity(intent);
        });

        createRequestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateRequestActivity.class);
            startActivity(intent);
        });

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        thankYouBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThankYouActivity.class);
            startActivity(intent);
        });
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Location permission is required to update your delivery location.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startLocationService() {
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
    }
}
