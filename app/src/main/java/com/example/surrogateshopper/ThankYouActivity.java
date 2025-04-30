package com.example.surrogateshopper;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surrogateshopper.services.ApiClient;

public class ThankYouActivity extends AppCompatActivity {

    EditText volunteerIdInput, messageInput;
    Button sendBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        volunteerIdInput = findViewById(R.id.volunteerIdInput);
        messageInput = findViewById(R.id.messageInput);
        sendBtn = findViewById(R.id.sendMessageBtn);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String email = prefs.getString("email", "");

        sendBtn.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            String volunteerIdStr = volunteerIdInput.getText().toString().trim();

            if (message.isEmpty() || volunteerIdStr.isEmpty()) {
                Toast.makeText(this, "Please enter a message and volunteer ID", Toast.LENGTH_SHORT).show();
                return;
            }

            int volunteerId = Integer.parseInt(volunteerIdStr);
            ApiClient.sendMessage(this, email, volunteerId, message);
        });
    }
}
