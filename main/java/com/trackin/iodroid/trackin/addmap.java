package com.trackin.iodroid.trackin;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class addmap extends FragmentActivity implements OnMapReadyCallback,OnMapClickListener {

    private GoogleMap mMap;

    private TextView mTapTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
    public int getZoomLevel(Circle circle)
    {
        int zoomlevel=11;
        if(circle!=null)
        {
            double radius=circle.getRadius() + circle.getRadius()/2;
            double scale=radius/500;
            zoomlevel=(int) (16-Math.log(scale)/Math.log(2));
        }
        return zoomlevel;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Bundle bundle = getIntent().getExtras();
        mMap = googleMap;
        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());
        String addre=bundle.getString("data");; //To Be Changed
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(addre, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        double r_lat=(double)(addresses.get(0).getLatitude());
        double r_long=(double)(addresses.get(0).getLongitude());
        LatLng mark = new LatLng(r_lat, r_long);
        CircleOptions circleoptions=new CircleOptions().strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
        mMap.addMarker(new MarkerOptions().position(mark).title(""+r_lat+" and "+r_long));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mark));
        Circle circle=mMap.addCircle(circleoptions.center(mark).radius(5000.0));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter(),getZoomLevel(circle)));
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
            Toast.makeText(addmap.this,"GPS not enabled!",Toast.LENGTH_LONG);
        }
        return "Invalid Location";
    }
    @Override
    public void onMapClick(LatLng point) {
        mMap.clear();
        TextView cordText = new TextView(this);
        cordText=(TextView)findViewById(R.id.cord);
        CircleOptions circleoptions=new CircleOptions().strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
        mMap.addMarker(new MarkerOptions().position(point).title(getAddress(point)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        cordText.setText("Lattitude: "+point.latitude+"\n"+"Longitude: "+point.longitude);
        Circle circle=mMap.addCircle(circleoptions.center(point).radius(5000.0));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter(),getZoomLevel(circle)));
        final TextView textViewToChange = (TextView) findViewById(R.id.cord);
        textViewToChange.setText("Latittude:"+point.latitude+"/nLongitude"+point.longitude);
    }

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
}