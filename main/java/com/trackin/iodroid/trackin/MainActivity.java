package com.trackin.iodroid.trackin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        //PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocationPermission();
        setContentView(R.layout.activity_main);
    }
        public void Track (View view) {
          Intent intent1=new Intent(MainActivity.this,GPSActivity.class);
            startActivity(intent1);
        }
        public void address(View view)
        {
            Intent intent2=new Intent(MainActivity.this,AddressActivity.class);
            startActivity(intent2);
        }
        public void Coordinate(View view)
        {
            Intent intent3=new Intent(MainActivity.this,CoordinateActivity.class);
            startActivity(intent3);
        }
}




