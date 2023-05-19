package com.nazmul.mytravelwish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This page show all the wishes of a specific user
 */
public class MyWish extends AppCompatActivity {
    Button addWishBtn;
    ListView wishList;
    ArrayList<Wish> wishArray = new ArrayList<>();
    MyWishAdapter wishAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_my);
        addWishBtn = (Button) findViewById(R.id.addWishBtn);

        wishArray.add(new Wish("Dhaka", "Old city", "Bangladesh"));
        wishArray.add(new Wish("Little Mermaid", "mermaid", "Copenhagen"));

        wishAdapter = new MyWishAdapter(this, R.layout.row, wishArray);

        wishList = (ListView) findViewById(R.id.wishList);
        wishList.setAdapter(wishAdapter);

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
