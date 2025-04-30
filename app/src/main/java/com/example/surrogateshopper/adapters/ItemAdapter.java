package com.example.surrogateshopper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.surrogateshopper.R;
import com.example.surrogateshopper.models.ShoppingItem;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingItem> items;

    public ItemAdapter(Context context, List<ShoppingItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopping_item, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemQuantity = convertView.findViewById(R.id.itemQuantity);

        ShoppingItem currentItem = items.get(position);

        itemName.setText(currentItem.getName());
        itemQuantity.setText("Quantity: " + currentItem.getQuantity());

        return convertView;
    }
}
