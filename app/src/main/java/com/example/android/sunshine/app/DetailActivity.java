
package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up navigation to parent activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        switch(id){
            case R.id.action_settings:
                //open settings activity
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            //up navigation to parent activity handler
            case android.R.id.home:
                //when this activity is invokable from within this app
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private static final String FORECAST_SHARE_HASTAG = " #SunshineApp";
        private ShareActionProvider shareActionProvider = null;
        private String mforecastString = "";

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent = getActivity().getIntent();
            if(intent != null && intent.hasExtra(ForecastFragment.EXTRA_FORECAST)){

                mforecastString = intent.getStringExtra(ForecastFragment.EXTRA_FORECAST);

                TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
                textView.setText(mforecastString);

            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);

            inflater.inflate(R.menu.detailsfragment, menu);

            //make share menu visible
            MenuItem item = menu.findItem(R.id.action_share);
            //get share action provider
            shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            //call createShareIntent() to attach an intent with shareActionProvider
            createShareIntent();
        }

        //call it to update share intent
        private void setShareIntent(Intent shareIntent){

            if(shareIntent != null) {
                shareActionProvider.setShareIntent(shareIntent);
            }
            else{
                Log.e(LOG_TAG, "Share Action Provider is null ?");
            }

        }

        //call to create and attach an intent with shareActionProvider
        private void createShareIntent(){

            //create share intent and call setShareIntent
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/*");
            //get data to share
            String locationPin = "\nLocation PIN: "+PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.key_location), getString(R.string.default_location_value));
            String weather = mforecastString + FORECAST_SHARE_HASTAG + locationPin;
            intent.putExtra(Intent.EXTRA_TEXT, weather);
            setShareIntent(intent);

        }

    }
}