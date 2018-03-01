package com.example.jiun.sookpam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageDepartListAdapter extends BaseAdapter {
    private ArrayList<MessageDepartItem> messageDepartItems = new ArrayList<MessageDepartItem>();

    public static class MessageDepartViewHolder {
        TextView category;
        TextView title;
    }

    @Override
    public int getCount() {
        return messageDepartItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        MessageDepartViewHolder holder;
        holder = new MessageDepartViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_depart_item, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.message_depart_category);
            holder.title = (TextView) convertView.findViewById(R.id.message_depart_title);
            convertView.setTag(holder);
        } else
            holder = (MessageDepartViewHolder) convertView.getTag();

        MessageDepartItem messageDepartItem = messageDepartItems.get(position);

        holder.title.setText(messageDepartItem.getTitle());
        holder.category.setText(messageDepartItem.getCategory());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return messageDepartItems.get(position);
    }

    public void addItem(String title, String category) {
        MessageDepartItem item = new MessageDepartItem();
        item.setCategory(category);
        item.setTitle(title);
        messageDepartItems.add(item);
    }

    public void clear() {
        messageDepartItems.clear();
    }
}