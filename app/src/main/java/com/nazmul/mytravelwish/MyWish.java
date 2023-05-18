package com.nazmul.mytravelwish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This page show all the wishes of a specific user
 */
public class MyWish extends AppCompatActivity {

    EditText destinationText;
    Button addWishBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_my);
        addWishBtn = (Button) findViewById(R.id.addWishBtn);


        addWishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddWishPage();
            }
        });
    }

    public void goToAddWishPage(){
        Intent intent = new Intent(this, AddWish.class);
        startActivity(intent);
    }



}
