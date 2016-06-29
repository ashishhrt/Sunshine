package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //open settings activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_view_map) {
            //view location on map
            String location = "geo:0,0?q=";
            location += PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.key_location), getString(R.string.default_location_value));
            //create location Uri
            Uri loactionUri = Uri.parse(location);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(loactionUri);

            if(intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            else {
                String message = "Install Google maps !";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }


        }

        return super.onOptionsItemSelected(item);
    }
}
