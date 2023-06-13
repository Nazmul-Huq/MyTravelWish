package com.nazmul.mytravelwish;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SetEditMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap googleMap;
    private MapView mapView;
    private Button saveLocationButton;
    private TextView locationText;
    private String wishId;
    FirebaseService firebaseService;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_edit_map);
        firebaseService = new FirebaseService();
        locationText = findViewById(R.id.locationText);
        saveLocationButton = findViewById(R.id.saveLocationButton);

        mapView = findViewById(R.id.setEditMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent intent = getIntent();
        wishId = intent.getStringExtra("wishId");

/*        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomethingWithCoordinates();
                //getLocationFromMap();
            }
        });*/

    }



/*    private void getLocationFromMap() {
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.getMyLocation();
            } else {
                // Request location permissions if not granted
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }*/

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Check location permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Set the touch listener
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Handle the touch event
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        String detailLocation = getLocation(latitude, longitude);
        locationText.setText(detailLocation);
        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocationToFirestor(latitude, longitude);
            }
        });
    }

    private void enableMyLocation() {
        try {
            // Enable the My Location layer if permission is granted
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                // Handle the case when location permission is not granted
                Toast.makeText(this, "Location permission not granted.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    // save location data to firestore
    private void saveLocationToFirestor(double latitude, double longitude) {
        Map<String, Object> data = new HashMap<>();
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        data.put("location", geoPoint);

        CollectionReference collectionRef = firebaseService.db.collection("locations");
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


    public String getLocation(double lat, double lng) {
        Geocoder geocoder = new Geocoder(SetEditMap.this, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add = add + "\n" + obj.getLocality();
            Log.e("IGA", "Address" + add);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return add;
    }


}
