package com.trackin.iodroid.trackin;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class GPSActivity extends FragmentActivity implements OnMapReadyCallback, OnMapClickListener {
    private GoogleMap mMap;
    private Location mLocation;
    private  com.trackin.iodroid.trackin.GPSTracker gpsTracker;
    double latitude, longitude;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {

            gpsTracker = new com.trackin.iodroid.trackin.GPSTracker(getApplicationContext());
            mLocation = gpsTracker.getLocation();

            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
          /*  Geocoder geocoder = new Geocoder(this);
            geocoder.get*/
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            System.out.println("60");
            lat = gpsTracker.getLocation().getLatitude();
            lng = gpsTracker.getLocation().getLongitude();

        } catch (Exception e) {
            Toast.makeText(GPSActivity.this,"GPS not enabled!",Toast.LENGTH_LONG);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public int getZoomLevel(Circle circle) {
        int zoomlevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomlevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomlevel;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        System.out.println("118");
    }
    public String getAddress(LatLng point)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            return address;
        }
        catch(Exception e)
        {
            Toast.makeText(GPSActivity.this,"GPS not enabled!",Toast.LENGTH_LONG);
        }
        return "Invalid Location";
    }
    @Override
    public void onMapClick(LatLng point) {
        mMap.clear();
        System.out.println("125");
        CircleOptions circleoptions = new CircleOptions().strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
        try {

            com.trackin.iodroid.trackin.GPSTracker gpsTracker = new com.trackin.iodroid.trackin.GPSTracker(getApplicationContext());
            mLocation = gpsTracker.getLocation();

            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
          /*  Geocoder geocoder = new Geocoder(this);
            geocoder.get*/
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            lat = gpsTracker.getLocation().getLatitude();
            lng = gpsTracker.getLocation().getLongitude();
            LatLng currloc = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(currloc).title(getAddress(currloc)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currloc));
            Circle circle = mMap.addCircle(circleoptions.center(currloc).radius(5000.0));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter(), getZoomLevel(circle)));
            final TextView textViewToChange = (TextView) findViewById(R.id.cord);
            textViewToChange.setText("Latittude:"+point.latitude+"\nLongitude"+point.longitude);
            System.out.println("149");
        } catch (Exception e) {
            //toast

        }
        System.out.println("153");
    }

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
}