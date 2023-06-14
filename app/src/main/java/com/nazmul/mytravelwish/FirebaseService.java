package com.nazmul.mytravelwish;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class FirebaseService {

    // reference
    //https://firebase.google.com/docs/firestore/quickstart?authuser=0

    // create an instance of firestore

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;
    public FirebaseService(){
        storageRef = FirebaseStorage.getInstance().getReference();
    }



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

    /**
     * this method is moved to MyWish.java
     */
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

    public void saveImage(byte[] img, String id){
        Log.i("firebase123", "save image called");
        String pathString = id + ".jpg";
        StorageReference imgRef = FirebaseStorage.getInstance().getReference().child(pathString);
        UploadTask task = imgRef.putBytes(img);  // putStream(s) for larger files
        task.addOnSuccessListener(taskSnapshot -> {
            Log.i("firebase123", "OK uploading " + taskSnapshot.getBytesTransferred());
        });
        task.addOnFailureListener(exception->{
            Log.i("firebase123", "error uploading " + exception);
        });
    }


    /**
     * add location to the firesotre
     * doc id will be the same as correspondent wish id
     * @param latitude
     * @param longitude
     * @param wishId
     */
    public void saveLocationToFirestor(double latitude, double longitude, String wishId) {
        Map<String, Object> data = new HashMap<>();
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        data.put("location", geoPoint);

        CollectionReference collectionRef = db.collection("locations");
        DocumentReference docRef = collectionRef.document(wishId);
        docRef.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("locationadded", "success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("locationadded", "failed");
                    }
                });
    }

    /**
     * update the location
     * @param latitude
     * @param longitude
     * @param wishId
     */
    public void updateLocationToFirestor(double latitude, double longitude, String wishId) {
        Map<String, Object> data = new HashMap<>();
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        data.put("location", geoPoint);

        DocumentReference documentRef = db.collection("locations").document(wishId);
        documentRef.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle error
                    }
                });
    }

    public void editWish(String wishId, String destinationNameStr, String noteStr, String destinationCityStr, String destinationCountryStr) {
        Map<String, Object> data = new HashMap<>();
        data.put("destinationName", destinationNameStr);
        data.put("note", noteStr);
        data.put("destinationCity", destinationCityStr);
        data.put("destinationCountry", destinationCountryStr);

        DocumentReference documentRef = db.collection("wishes").document(wishId);
        documentRef.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle error
                    }
                });
    }
}
