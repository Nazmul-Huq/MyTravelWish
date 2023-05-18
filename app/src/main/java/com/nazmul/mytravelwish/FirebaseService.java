package com.nazmul.mytravelwish;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

}
