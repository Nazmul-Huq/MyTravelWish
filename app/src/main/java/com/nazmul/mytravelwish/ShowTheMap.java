package com.nazmul.mytravelwish;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    private Button addEditMapButton;
    String wishId;
    FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_the_map);
        mapView = findViewById(R.id.fullMapView);
        addEditMapButton = findViewById(R.id.addEditMapButton);
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
                        addEditMapButton.setText("Edit Location");
                        addEditMapButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(view.getContext(), SetEditMap.class); // set intent
                                intent.putExtra("wishId", wishId); // set image id into intent
                                intent.putExtra("editMap", "true");
                                view.getContext().startActivity(intent); // start the page (ImageHandler)
                            }
                        });
                    } else {
                        map.setOnMapLongClickListener(latLng -> {
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Come here!"));
                        });
                        addEditMapButton.setText("Add Location");
                        addEditMapButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(view.getContext(), SetEditMap.class); // set intent
                                intent.putExtra("wishId", wishId); // set image id into intent
                                intent.putExtra("editMap", "false");
                                view.getContext().startActivity(intent); // start the page (ImageHandler)
                            }
                        });
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



} // class ends here
