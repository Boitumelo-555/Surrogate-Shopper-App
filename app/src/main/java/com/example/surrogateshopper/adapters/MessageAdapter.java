package com.example.surrogateshopper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.surrogateshopper.R;
import com.example.surrogateshopper.models.ThankYouMessage;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<ThankYouMessage> messages;

    public MessageAdapter(Context context, List<ThankYouMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_thank_you_message, parent, false);
        }

        TextView thankYouMessageText = convertView.findViewById(R.id.thankYouMessageText);

        ThankYouMessage currentMessage = messages.get(position);

        thankYouMessageText.setText(currentMessage.getMessage());

        return convertView;
    }
}
