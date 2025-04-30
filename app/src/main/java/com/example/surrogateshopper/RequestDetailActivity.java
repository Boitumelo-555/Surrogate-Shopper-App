package com.example.surrogateshopper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surrogateshopper.adapters.ItemAdapter;
import com.example.surrogateshopper.models.ShoppingItem;
import com.example.surrogateshopper.services.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailActivity extends AppCompatActivity {

    private List<ShoppingItem> shoppingItems = new ArrayList<>();
    private ItemAdapter itemAdapter;

    private int requestId = 1;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        TextView titleTextView = findViewById(R.id.detailTitle);
        TextView descriptionTextView = findViewById(R.id.detailDescription);
        Button claimButton = findViewById(R.id.claimButton);
        ListView itemListView = findViewById(R.id.itemListView);

        itemAdapter = new ItemAdapter(this, shoppingItems);
        itemListView.setAdapter(itemAdapter);

        titleTextView.setText("Sample Grocery Shopping");
        descriptionTextView.setText("Please buy milk, bread, eggs.");

        claimButton.setOnClickListener(v -> {
            ApiClient.claimRequest(this, requestId);
            Toast.makeText(this, "You have claimed this request!", Toast.LENGTH_SHORT).show();
            finish();
        });


    }

    private void loadItems() {
        ApiClient.getItems(this, requestId, new ApiClient.ItemsCallback() {
            @Override
            public void onSuccess(List<ShoppingItem> items) {
                shoppingItems.clear();
                shoppingItems.addAll(items);
                runOnUiThread(() -> itemAdapter.notifyDataSetChanged());
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> Toast.makeText(RequestDetailActivity.this, "Failed to load items", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
