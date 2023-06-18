package com.nazmul.mytravelwish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    Button goToSignupButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.loginUsername);
        password = (EditText) findViewById(R.id.loginPassword);

        // get login button
        loginButton  = (Button) findViewById(R.id.loginButton);
        // set event on login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

                CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("users");
                Query query = usersCollection.whereEqualTo("username", usernameStr)
                        .whereEqualTo("password", passwordStr)
                        .limit(1);
                query.get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (!querySnapshot.isEmpty()) {

                                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                    String appUserName = documentSnapshot.getString("name");

                                    SharedPreferences sharedPreferences = getSharedPreferences("AppUserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("appUserName", appUserName); // Replace `userId` with the actual user ID
                                    editor.putString("username", usernameStr); // Replace `userId` with the actual user ID
                                    editor.apply();

                                    viewMyActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username and Password didn't match", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Something Wrong! Try later", Toast.LENGTH_LONG).show();
                            }
                        });


            }
        });

        goToSignupButton = (Button) findViewById(R.id.goToSignupButton);
        goToSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }

    public void viewMyActivity(){
        Intent intent = new Intent(this, MyWish.class);
        startActivity(intent);
    }




}