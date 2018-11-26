package com.example.android.popularmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovie.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().setTitle("Settings");

        // load settings fragment
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainPreferenceFragment())
                .commit();
    }


    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);


            final Preference myPref = findPreference(getString(R.string.pref_sort_order_key));
            setSummaryOnLoad(myPref);

            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity(), "Please Choose the order you want", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            myPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    Toast.makeText(getActivity(), "Settings Updated", Toast.LENGTH_SHORT).show();
                    updateback(getActivity());
                    return true;
                }


            });
        }

        private void updateback(Activity activity) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }

        public void setSummaryOnLoad(Preference pref) {
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(pref.getContext());
            String preferenceString = preferences.getString(pref.getKey(), "");

            String stringValue = preferenceString.toString();
            if (pref instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) pref;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    stringValue = (String) labels[prefIndex];
                }
            }
            pref.setSummary(stringValue);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}

