package com.example.surrogateshopper;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.surrogateshopper.services.ApiClient;

public class CreateRequestActivity extends AppCompatActivity {

    EditText titleInput, descriptionInput;
    Button submitRequestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        submitRequestBtn = findViewById(R.id.submitRequestBtn);

        submitRequestBtn.setOnClickListener(view -> {
            String title = titleInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                ApiClient.createRequest(this, title, description);
            }
        });
    }
}
