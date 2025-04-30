package com.example.surrogateshopper;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surrogateshopper.services.ApiClient;

public class RegisterActivity extends AppCompatActivity {
    EditText nameInput, emailInput, passwordInput;
    RadioGroup roleGroup;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.name);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        roleGroup = findViewById(R.id.roleGroup);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String role = roleGroup.getCheckedRadioButtonId() == R.id.radioRequester ? "requester" : "volunteer";

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                ApiClient.register(RegisterActivity.this, name, email, password, role);
            }
        });

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}
