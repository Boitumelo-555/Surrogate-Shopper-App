package com.example.surrogateshopper.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.*;

import java.io.IOException;

import okhttp3.*;

public class LocationService extends Service {

    private FusedLocationProviderClient fusedLocationClient;
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "https://lamp.ms.wits.ac.za/home/s2656353";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationUpdate();
        return START_NOT_STICKY;
    }

    private void requestLocationUpdate() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("LocationService", "Permission denied for location");
            stopSelf();
            return;
        }

        LocationRequest request = LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000)
                .setNumUpdates(1);

        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper());
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult result) {
            Location location = result.getLastLocation();
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                sendLocationToServer(latitude, longitude);
            }
            stopSelf(); // Stop service after sending
        }
    };

    private void sendLocationToServer(double latitude, double longitude) {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email == null) {
            Log.e("LocationService", "No email found in SharedPreferences");
            return;
        }

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("latitude", String.valueOf(latitude))
                .add("longitude", String.valueOf(longitude))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/update_location.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("LocationService", "Failed to send location", e);
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                Log.d("LocationService", "Location updated on server");
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
