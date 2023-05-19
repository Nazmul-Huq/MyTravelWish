package com.nazmul.mytravelwish;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyWishAdapter extends ArrayAdapter<Wish> {

    Context context;
    int layoutResourceId;
    ArrayList<Wish> data = new ArrayList<Wish>();

    public MyWishAdapter(Context context, int layoutResourceId, ArrayList<Wish> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.destinationName = (TextView) row.findViewById(R.id.destinationNameRow);
            holder.note = (TextView) row.findViewById(R.id.noteRow);
            holder.city = (TextView) row.findViewById(R.id.destinationCityRow);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        Wish wish = data.get(position);
        holder.destinationName.setText(wish.getDestination());
        holder.note.setText(wish.getNote());
        holder.city.setText(wish.getCity());
        return row;
    }

    static class UserHolder {
        TextView destinationName;
        TextView note;
        TextView city;
    }
}
