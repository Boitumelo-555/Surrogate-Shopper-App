package com.example.surrogateshopper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.surrogateshopper.R;
import com.example.surrogateshopper.models.ShoppingRequest;

import java.util.List;

public class RequestAdapter extends BaseAdapter {
    private final Context context;
    private final List<ShoppingRequest> requestList;

    public RequestAdapter(Context context, List<ShoppingRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.request_list_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);  // Correct ID
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);  // Correct ID

        ShoppingRequest request = requestList.get(position);

        titleTextView.setText(request.getTitle());
        descriptionTextView.setText(request.getDescription());

        return convertView;
    }
}

