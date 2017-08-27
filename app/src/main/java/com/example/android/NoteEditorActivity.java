package com.example.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notelist.R;
import com.example.android.notelist.com.example.android.notelist.data.NoteItem;

/**
 * Created by dozie on 2017-05-13.
 */

public class NoteEditorActivity extends AppCompatActivity {

    private NoteItem note;
    TextView displayText;
    String textOnScreen;
    private LocationManager locationManager;
    private LocationListener listener;
    double longi_tude;
    double lati_tude;

    TextView textView;

    String loc = "doesnt work";

    int locationSearch = 0;

    String map;
    String lati;
    String longi;
    String loca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayText = (TextView) findViewById(R.id.noteText);//                 OVER HERE
        Intent intent = this.getIntent();
        note = new NoteItem();
        note.setKey(intent.getStringExtra("key"));
        note.setText(intent.getStringExtra("text"));
        note.setLati_tude(intent.getDoubleExtra("lati_tude", lati_tude));
        note.setLongi_tude(intent.getDoubleExtra("longi_tude", longi_tude));
        map = "geo:";
        lati = String.valueOf(lati_tude);
        longi = String.valueOf(longi_tude);
        loca = map + lati + "," + longi;

        EditText et = (EditText) findViewById(R.id.noteText);
        et.setText(note.getText());
        //textOnScreen = et.getText().toString();
        et.setSelection(et.getText().length());
        Log.d("CREATION", "setSelection position " + et.getText().length());
        //displayText.setText(textOnScreen);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("CREATION", "it has entered");
                //loc = ("\n " + location.getLongitude() + " " + location.getLatitude());
                if(locationSearch == 0) {
                    longi_tude = location.getLongitude();
                    lati_tude = location.getLatitude();
                    Toast.makeText(getApplicationContext(), "Location saved", Toast.LENGTH_SHORT).show();
                    locationSearch = 1;
                    lati = String.valueOf(lati_tude);
                    longi = String.valueOf(longi_tude);
                    loca = map + lati + "," + longi;
                    //saveAndFinish();
                    //Log.d("CREATION", "this is the location in the first place " + loc);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        //configure_button();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.find_location);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.get_loc) {
                    Uri gmmIntentUri = Uri.parse(loca);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {

        //locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        Log.d("CREATION", "it gets here" );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);

            }


            return;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.location, menu);
        return true;
    }


    private void saveAndFinish() {
        EditText et = (EditText) findViewById(R.id.noteText);
        String noteText = et.getText().toString();

        Intent intent = new Intent();
        //Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", noteText);
        intent.putExtra("lati_tude", lati_tude);
        intent.putExtra("longi_tude", longi_tude);
        //Log.d("CREATION", "enters here 3");
        //intent.putExtra("text", noteText);
        if (noteText.length() > 0) {
            setResult(RESULT_OK, intent);
            //Log.d("CREATION", "enters here 4");
        }
        finish();
        //Log.d("CREATION", "enters here 5");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.d("CREATION", "enters here 1");
        if (item.getItemId() == android.R.id.home) {
            saveAndFinish();
        }

        else if (item.getItemId() == R.id.location) {
            Toast.makeText(getApplicationContext(), "Getting location", Toast.LENGTH_LONG).show();
            locationSearch = 0;
            configure_button();
        }

        return false;
    }



    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}
