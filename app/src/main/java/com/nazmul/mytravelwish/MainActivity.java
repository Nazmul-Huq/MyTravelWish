package com.nazmul.mytravelwish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get login button
        Button loginButton = (Button) findViewById(R.id.loginButton);
        // set event on login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewMyActivity();
            }
        });
    }

    public void viewMyActivity(){
        Intent intent = new Intent(this, MyWish.class);
        startActivity(intent);
    }


}