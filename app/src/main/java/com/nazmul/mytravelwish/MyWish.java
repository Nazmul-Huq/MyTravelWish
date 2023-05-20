package com.nazmul.mytravelwish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This page show all the wishes of a specific user
 */
public class MyWish extends AppCompatActivity {
    Button addWishBtn;
    ListView wishList;
    ArrayList<Wish> wishArray = new ArrayList<Wish>();
    MyWishAdapter wishAdapter;
    FirebaseService firebaseService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_my);

        firebaseService = new FirebaseService();

        addWishBtn = (Button) findViewById(R.id.addWishBtn);

        getAllWishes();

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

    /**
     * get all the wishes from firebase and re-lode the adapter
     */
    public void getAllWishes(){
        firebaseService.db.collection("wishes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Wish wish = new Wish();
                                wish.setId(document.getId());
                                wish.setDestination(document.getString("destinationName"));
                                wish.setNote(document.getString("note"));
                                wish.setCity(document.getString("destinationCity"));
                                wish.setCountry(document.getString("destinationCountry"));
                                wishArray.add(wish);
                            }
                        } else {
                            Log.w("gettingfirebasedata", "Error getting documents.", task.getException());
                        }
                        wishAdapter.notifyDataSetChanged();
                    }
                });
    }

}
