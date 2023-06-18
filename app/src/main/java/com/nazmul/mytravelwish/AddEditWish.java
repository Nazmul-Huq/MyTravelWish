package com.nazmul.mytravelwish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This allow to edit an existing wish or add a new wish
 */
public class AddEditWish extends AppCompatActivity {

    private String isEditWish, wishIdString, destinationNameString, cityString, noteString, countryString, appUserName, username;
    EditText destinationName, note, destinationCity, destinationCountry;
    Button addWishFinalBtn, cancelBtn;
    FirebaseService firebaseService;
    private TextView welcomeHeader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_add);
        firebaseService = new FirebaseService();
        destinationName = (EditText) findViewById(R.id.destinationName);
        note = (EditText) findViewById(R.id.note);
        destinationCity = (EditText) findViewById(R.id.destinationCity);
        destinationCountry = (EditText) findViewById(R.id.destinationCountry);
        addWishFinalBtn = (Button) findViewById(R.id.addWishFinalBtn);
        cancelBtn = (Button) findViewById(R.id.cancel_button);


        SharedPreferences sharedPreferences = getSharedPreferences("AppUserData", Context.MODE_PRIVATE);
        appUserName = sharedPreferences.getString("appUserName", null);
        username = sharedPreferences.getString("username", null);

        welcomeHeader = (TextView) findViewById(R.id.welcomeHeader);
        String welcomeStr = appUserName + "'s Travel Wish";
        welcomeHeader.setText(welcomeStr);

        Intent intent = getIntent();
        isEditWish = intent.getStringExtra("editWish");

        wishIdString = intent.getStringExtra("wishId");
        destinationNameString = intent.getStringExtra("destinationName");
        cityString = intent.getStringExtra("city");
        noteString = intent.getStringExtra("note");
        countryString = intent.getStringExtra("country");

        if (isEditWish.equals("true")){
            destinationName.setText(destinationNameString);
            destinationCity.setText(cityString);
            note.setText(noteString);
            destinationCountry.setText(countryString);
            addWishFinalBtn.setText("Edit Wish");
        }


        addWishFinalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destinationNameStr = destinationName.getText().toString();
                String noteStr = note.getText().toString();
                String destinationCityStr = destinationCity.getText().toString();
                String destinationCountryStr = destinationCountry.getText().toString();

                if (noteStr.matches("")) noteStr = "x";
                if (destinationCityStr.matches("")) destinationCityStr = "x";
                if (destinationCountryStr.matches("")) destinationCountryStr = "x";

                if (isEditWish.equals("true")) {
                    // edit an existing wish
                    firebaseService.editWish(wishIdString, destinationNameStr, noteStr, destinationCityStr, destinationCountryStr, username);
                } else {
                    // add a new wish
                    firebaseService.addWish(destinationNameStr, noteStr, destinationCityStr, destinationCountryStr, username);
                }

                goToHomepage();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomepage();
            }
        });


    }

    private void goToHomepage(){
        Intent intent = new Intent(this, MyWish.class);
        startActivity(intent);
    }

}
