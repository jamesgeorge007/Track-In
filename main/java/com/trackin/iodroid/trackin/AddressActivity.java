package com.trackin.iodroid.trackin;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class AddressActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private String latt,longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
    }
    public void Retrieve(View view) {
        editText1 = (EditText) findViewById(R.id.editText2);
        latt = editText1.getText().toString();
        editText2 = (EditText) findViewById(R.id.editText);
        longi = editText2.getText().toString();
        if (latt.isEmpty() || longi.isEmpty()) {
            Toast.makeText(AddressActivity.this, "Invalid Data given!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent1 = new Intent(AddressActivity.this, cordmap.class);
        Bundle bundle=new Bundle();
        bundle.putDouble("lattitude",Double.parseDouble(latt));
        bundle.putDouble("longitude",Double.parseDouble(longi));
        intent1.putExtras(bundle);
        startActivity(intent1);
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
            Toast.makeText(AddressActivity.this,"GPS not enabled!",Toast.LENGTH_LONG);
        }
        return "Invalid Location";
    }
}

