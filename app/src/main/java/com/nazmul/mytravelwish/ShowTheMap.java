package com.nazmul.mytravelwish;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ShowTheMap extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    String wishId;
    FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_the_map);
        mapView = findViewById(R.id.fullMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        firebaseService = new FirebaseService();

        Intent intent = getIntent();
        wishId = intent.getStringExtra("wishId");

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;

        DocumentReference docRef = firebaseService.db.collection("locations").document(wishId);
        // GoqcfATljXOcpoxE6Qkx dhaka 23.810331 90.412521
        // diwF6Gf8iHQPNI13DiDH COPENHAGEN 55.676098 12.568337
        // fIxllKMNjinWwCMhPeJp aarhus

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        GeoPoint geoPoint = document.getGeoPoint("location");
                        LatLng location = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                        map.addMarker(new MarkerOptions().position(location).title("Marker"));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

   public void getLatLongitude(String id){

       //DocumentReference docRef = firebaseService.db.collection("locations").document("FO4FGoVAQ8oaqrcNwx2a");
       /*DocumentReference docRef = firebaseService.db.collection("locations").document("AZ8oLBXyk92KZxIAlrrV");

       docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       GeoPoint geoPoint = document.getGeoPoint("location");
                       locations.add(geoPoint.getLatitude());
                       locations.add(geoPoint.getLongitude());
                       //double latitude = geoPoint.getLatitude();
                       //double longitude = geoPoint.getLongitude();
                       //locationHolder.setLatitudeData(latitude);
                       //locationHolder.setLongitudeData(longitude);
                   } else {
                       Log.d(TAG, "No such document");
                   }
               } else {
                   Log.d(TAG, "get failed with ", task.getException());
               }
           }
       });*/
   }

} // class ends here
