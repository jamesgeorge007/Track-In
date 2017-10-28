package com.trackin.iodroid.trackin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.trackin.iodroid.trackin.R.id.editText;

public class CoordinateActivity extends AppCompatActivity {
    private EditText editText;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
        editText = (EditText) findViewById(R.id.editText3);
        address = editText.toString();

    }

    public void Retrieve(View view) {
        editText = (EditText) findViewById(R.id.editText3);
        address = editText.getText().toString();
        if (address.isEmpty()) {
            Toast.makeText(CoordinateActivity.this, "No city given!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent1 = new Intent(CoordinateActivity.this, addmap.class);
        Bundle bundle=new Bundle();
        bundle.putString("data", address);

//Add the bundle to the intent
        intent1.putExtras(bundle);
        startActivity(intent1);
    }
}
