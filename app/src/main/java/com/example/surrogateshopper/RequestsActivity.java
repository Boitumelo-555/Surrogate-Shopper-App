package com.example.surrogateshopper;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surrogateshopper.adapters.RequestAdapter;
import com.example.surrogateshopper.models.ShoppingRequest;
import com.example.surrogateshopper.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {

    private ListView requestListView;
    private RequestAdapter adapter;
    private final List<ShoppingRequest> requestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        requestListView = findViewById(R.id.requestListView);
        adapter = new RequestAdapter(this, requestList);
        requestListView.setAdapter(adapter);

        loadRequests();
    }

    private void loadRequests() {
        ApiClient.getRequests(this, new ApiClient.RequestsCallback() {
            @Override
            public void onSuccess(List<ShoppingRequest> requests) {
                runOnUiThread(() -> {
                    requestList.clear();
                    requestList.addAll(requests);
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> Toast.makeText(RequestsActivity.this, "Failed to load requests: " + errorMessage, Toast.LENGTH_SHORT).show());
            }
        });
    }
}
