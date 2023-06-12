package com.nazmul.mytravelwish;

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

public class ShowTheMap extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_the_map);
        mapView = findViewById(R.id.fullMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        //setupLocationService();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        map = googleMap;
        LatLng location = new LatLng(55.5969712679851, 12.315673828125); // my home
        map.addMarker(new MarkerOptions().position(location).title("Marker"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
        Log.d("ismapcalled", "Map was called");
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



/*    holder.mapView.getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            map = googleMap;
            // Add a marker at a specific location and move the camera
            LatLng location = new LatLng(37.7749, -122.4194); // Example location (San Francisco)
            map.addMarker(new MarkerOptions().position(location).title("Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
            Log.d("ismapcalled", "Map was called");
        }
    });*/

} // class ends here
