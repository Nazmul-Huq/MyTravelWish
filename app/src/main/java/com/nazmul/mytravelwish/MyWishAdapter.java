package com.nazmul.mytravelwish;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
            holder.country = (TextView) row.findViewById(R.id.destinationCountryRow);
            holder.editButton = (Button) row.findViewById(R.id.editButton);
            holder.deleteButton = (Button) row.findViewById(R.id.deleteButton);
            holder.hiddenText = (TextView) row.findViewById(R.id.hiddenText);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);
            holder.defaultMapIcon = (ImageView) row.findViewById(R.id.defaultMapIcon);
            holder.hiddenText = (TextView) row.findViewById(R.id.hiddenText);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        Wish wish = data.get(position);
        holder.destinationName.setText(wish.getDestination());
        holder.note.setText(wish.getNote());
        holder.city.setText(wish.getCity());
        holder.country.setText(wish.getCountry());
        holder.hiddenText.setText(wish.getId());
        holder.imageView.setImageResource(R.drawable.default_wish_image);
        holder.defaultMapIcon.setImageResource(R.drawable.default_map_image);

        // add edit button click event
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wishId = getInfoOnClick(view, 8); // get the id of the wish, that was clicked
                Intent intent=new Intent(view.getContext(), AddWish.class); // set intent
                intent.putExtra("wishId", getInfoOnClick(view, 8)); // set image id into intent
                intent.putExtra("destinationName", getInfoOnClick(view, 0)); // set image id into intent
                intent.putExtra("note", getInfoOnClick(view, 1)); // set image id into intent
                intent.putExtra("city", getInfoOnClick(view, 2)); // set image id into intent
                intent.putExtra("country", getInfoOnClick(view, 3)); // set image id into intent
                intent.putExtra("editWish", "true");


                view.getContext().startActivity(intent); // start the page (ImageHandler)
            }
        });

        // add delete button click event
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO delete a wish
            }
        });

        // add image click event
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wishId = getInfoOnClick(view, 8); // get the id of the wish, that was clicked
                Intent intent=new Intent(view.getContext(), ImageHandler.class); // set intent
                intent.putExtra("wishId", wishId); // set image id into intent
                view.getContext().startActivity(intent); // start the page (ImageHandler)
            }
        });

        // add map click event
        holder.defaultMapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wishId = getInfoOnClick(view, 8); // get the id of the wish, that was clicked
                Intent intent=new Intent(view.getContext(), ShowTheMap.class); // set intent
                intent.putExtra("wishId", wishId); // set image id into intent
                view.getContext().startActivity(intent); // start the page (ImageHandler)
            }
        });

        return row;
    }

    static class UserHolder {
        TextView destinationName;
        TextView note;
        TextView city;
        TextView country;
        ImageView imageView;
        ImageView defaultMapIcon;
        Button editButton;
        Button deleteButton;
        TextView hiddenText;
    }

    public String getInfoOnClick(View view, int position){
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        TextView child = (TextView)relativeLayout.getChildAt(position);
        String info = child.getText().toString();
        return info;
    }

}
