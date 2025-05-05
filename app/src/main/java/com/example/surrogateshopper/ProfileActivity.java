package com.example.surrogateshopper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class ProfileActivity extends AppCompatActivity {

    EditText nameField, phoneField, addressField, bioField;
    Button updateBtn;
    SharedPreferences prefs;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameField = findViewById(R.id.nameField);
        phoneField = findViewById(R.id.phoneField);
        addressField = findViewById(R.id.addressField);
        bioField = findViewById(R.id.bioField);
        updateBtn = findViewById(R.id.updateProfileBtn);

        prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        email = prefs.getString("email", null);

        loadProfile();

        updateBtn.setOnClickListener(v -> updateProfile());
    }

    private void loadProfile() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("email", email).build();
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2656353/get_profile.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String json = response.body().string();
                runOnUiThread(() -> {
                    try {
                        JSONObject obj = new JSONObject(json);
                        nameField.setText(obj.optString("name", ""));
                        phoneField.setText(obj.optString("phone", ""));
                        addressField.setText(obj.optString("address", ""));
                        bioField.setText(obj.optString("bio", ""));
                    } catch (JSONException e) {
                        Toast.makeText(ProfileActivity.this, "Error parsing profile", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateProfile() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("name", nameField.getText().toString())
                .add("phone", phoneField.getText().toString())
                .add("address", addressField.getText().toString())
                .add("bio", bioField.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2656353/update_profile.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
