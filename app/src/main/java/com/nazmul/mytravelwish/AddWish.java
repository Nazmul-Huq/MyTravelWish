package com.nazmul.mytravelwish;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This page show all the wishes of a specific user
 */
public class AddWish extends AppCompatActivity {

    EditText destinationName, note, destinationCity, destinationCountry;
    Button addWishFinalBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_add);
        destinationName = (EditText) findViewById(R.id.destinationName);
        note = (EditText) findViewById(R.id.note);
        destinationCity = (EditText) findViewById(R.id.destinationCity);
        destinationCountry = (EditText) findViewById(R.id.destinationCountry);
        addWishFinalBtn = (Button) findViewById(R.id.addWishFinalBtn);


        addWishFinalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destinationNameStr = destinationName.getText().toString();
                String noteStr = note.getText().toString();
                String destinationCityStr = destinationCity.getText().toString();
                String destinationCountryStr = destinationCountry.getText().toString();
            }
        });
    }





}
