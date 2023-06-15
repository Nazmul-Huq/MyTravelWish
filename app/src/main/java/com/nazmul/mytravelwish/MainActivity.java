package com.nazmul.mytravelwish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    Button goToSignupButton, loginButton;
    FirebaseService firebaseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseService = new FirebaseService();
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
                                    viewMyActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username and Password didn't match", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // An error occurred while checking for user existence
                                // Handle the error
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