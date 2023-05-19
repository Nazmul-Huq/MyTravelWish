package com.nazmul.mytravelwish;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FirebaseService {

    // reference
    //https://firebase.google.com/docs/firestore/quickstart?authuser=0

    // create an instance of firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Add document to the firestore database
     * @param destinationNameStr
     * @param noteStr
     * @param destinationCityStr
     * @param destinationCountryStr
     */
    public void addWish(String destinationNameStr, String noteStr, String destinationCityStr, String destinationCountryStr){
        DocumentReference ref = db.collection("wishes").document();
        Map<String, Object> wish = new HashMap<>();
        wish.put("destinationName", destinationNameStr);
        wish.put("note", noteStr);
        wish.put("destinationCity", destinationCityStr);
        wish.put("destinationCountry", destinationCountryStr);
        ref.set(wish);
    }

    public void getWishes(){
        db.collection("wishes")
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
                                //Log.d("gettingfirebasedata", wish.getDestination() + " "  + wish.getCity());
                                //wishes.add(wish);
                            }
                        } else {
                            Log.w("gettingfirebasedata", "Error getting documents.", task.getException());
                        }

                    }
                });
    }

}
