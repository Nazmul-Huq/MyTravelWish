package com.nazmul.mytravelwish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private FirebaseService firebaseService;
    private EditText name, userName, password;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signupButton = (Button) findViewById(R.id.signupButton);
        name = (EditText) findViewById(R.id.name);
        userName = (EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firebaseService = new FirebaseService();


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString();
                String userNameStr = userName.getText().toString();
                String passwordStr = password.getText().toString();

                firebaseService.addUser(nameStr, userNameStr, passwordStr);

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
