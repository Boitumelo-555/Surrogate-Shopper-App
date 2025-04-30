package com.example.surrogateshopper.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.surrogateshopper.MainActivity;
import com.example.surrogateshopper.models.ShoppingItem;
import com.example.surrogateshopper.models.ShoppingRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final String BASE_URL = "https://lamp.ms.wits.ac.za/home/s2656353";

    private static void showToast(Context context, String message) {
        new Handler(context.getMainLooper()).post(() ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }


    public static void login(Context context, String email, String password) {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/login.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Login failed: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() == null) {
                    showToast(context, "Empty response");
                    return;
                }
                String res = response.body().string();
                try {
                    JSONObject json = new JSONObject(res);
                    if (json.getBoolean("success")) {
                        JSONObject user = json.getJSONObject("user");
                        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("name", user.getString("name"));
                        editor.putString("email", user.getString("email"));
                        editor.putString("role", user.getString("role"));
                        editor.apply();

                        showToast(context, "Login successful!");
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        showToast(context, json.getString("message"));
                    }
                } catch (JSONException e) {
                    showToast(context, "Parsing error: " + e.getMessage());
                }
            }
        });
    }

    public static void register(Context context, String name, String email, String password, String role) {
        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .add("role", role)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/register.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Registration failed: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() == null) {
                    showToast(context, "Empty response");
                    return;
                }

                String res = response.body().string();
                if (response.isSuccessful() && res.contains("success")) {
                    showToast(context, "Registration successful!");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    showToast(context, "Registration failed: " + res);
                }
            }
        });
    }

    public static void createRequest(Context context, String title, String description) {
        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("notes", description)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/create_request.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Failed to create request: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body() != null ? response.body().string() : "Empty response";
                showToast(context, res.contains("success") ? "Request created!" : "Failed: " + res);
            }
        });
    }

    public interface RequestsCallback {
        void onSuccess(List<ShoppingRequest> requests);
        void onError(String message);
    }

    public static void getRequests(Context context, RequestsCallback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/get_requests.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onError("Failed to load requests: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body() != null ? response.body().string() : "";
                if (response.isSuccessful()) {
                    ShoppingRequest[] reqs = gson.fromJson(res, ShoppingRequest[].class);
                    callback.onSuccess(Arrays.asList(reqs));
                } else {
                    callback.onError("Server error: " + res);
                }
            }
        });
    }

    public interface ItemsCallback {
        void onSuccess(List<ShoppingItem> items);
        void onError(String message);
    }

    public static void getItems(Context context, int requestId, ItemsCallback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/get_items.php?request_id=" + requestId)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onError("Failed to load items: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body() != null ? response.body().string() : "";
                if (response.isSuccessful()) {
                    ShoppingItem[] items = gson.fromJson(res, ShoppingItem[].class);
                    callback.onSuccess(Arrays.asList(items));
                } else {
                    callback.onError("Server error: " + res);
                }
            }
        });
    }

    public static void claimRequest(Context context, int requestId) {
        RequestBody body = new FormBody.Builder()
                .add("request_id", String.valueOf(requestId))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/claim_request.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Failed to claim request: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                showToast(context, response.body() != null && response.body().string().contains("success")
                        ? "Request claimed!"
                        : "Failed to claim");
            }
        });
    }

    public static void completeRequest(Context context, int requestId) {
        RequestBody body = new FormBody.Builder()
                .add("request_id", String.valueOf(requestId))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/complete_request.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Failed to complete request: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                showToast(context, response.body() != null && response.body().string().contains("success")
                        ? "Request completed!"
                        : "Failed to complete");
            }
        });
    }

    public static void updateProfile(Context context, String email, String name, String address, String bio, String phone) {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("name", name)
                .add("address", address)
                .add("bio", bio)
                .add("phone", phone)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/update_profile.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Failed to update profile: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body() != null ? response.body().string() : "";
                showToast(context, response.isSuccessful() && res.contains("success")
                        ? "Profile updated!"
                        : "Failed: " + res);
            }
        });
    }

    public static void sendMessage(Context context, String email, int volunteerId, String message) {
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("volunteer_id", String.valueOf(volunteerId))
                .add("message", message)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/send_message.php")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                showToast(context, "Failed to send message: " + e.getMessage());
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                showToast(context, response.body() != null && response.body().string().contains("success")
                        ? "Message sent!"
                        : "Failed to send");
            }
        });
    }
}

