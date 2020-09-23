package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.EditText;

import androidx.preference.PreferenceManager;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGConnectionManager;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.lg_settings_preferences);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        slavesPrefs();

        bindPreferenceSummaryToValue(findPreference("SSH-USER"));
        bindPreferenceSummaryToValue(findPreference("SSH-PASSWORD"));
        bindPreferenceSummaryToValue(findPreference("SSH-IP"));
        bindPreferenceSummaryToValue(findPreference("SSH-PORT"));


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {

            android.preference.ListPreference listPreference = (android.preference.ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference.getKey().toLowerCase().contains("password")) {
            EditText edit = ((EditTextPreference) preference).getEditText();
            String pref = edit.getTransformationMethod().getTransformation(stringValue, edit).toString();
            preference.setSummary(pref);

        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }

        return true;
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

    private void slavesPrefs(){

        final ListPreference logosPrefs = (ListPreference) findPreference("logos_preference");
        final ListPreference homelessPrefs = (ListPreference) findPreference("homeless_preference");
        final ListPreference localPrefs = (ListPreference) findPreference("local_preference");
        final ListPreference globalPrefs = (ListPreference) findPreference("global_preference");

        logosPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preferences.edit().putString("logos_preference", newValue.toString()).apply();
                logosPrefs.setValue(newValue.toString());
                return false;
            }
        });

        homelessPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preferences.edit().putString("homeless_preference", newValue.toString()).apply();
                homelessPrefs.setValue(newValue.toString());
                return false;
            }
        });

        localPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preferences.edit().putString("local_preference", newValue.toString()).apply();
                localPrefs.setValue(newValue.toString());
                return false;
            }
        });

        globalPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preferences.edit().putString("global_preference", newValue.toString()).apply();
                globalPrefs.setValue(newValue.toString());
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LGConnectionManager.getInstance().setData(prefs.getString("SSH-USER", "lg"), prefs.getString("SSH-PASSWORD", "lqgalaxy"), prefs.getString("SSH-IP", "192.168.1.76"), Integer.parseInt(prefs.getString("SSH-PORT", "22")));
    }
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LGConnectionManager.getInstance().setData(prefs.getString("SSH-USER", "lg"), prefs.getString("SSH-PASSWORD", "lqgalaxy"), prefs.getString("SSH-IP", "192.168.1.76"), Integer.parseInt(prefs.getString("SSH-PORT", "22")));
        HelpActivity.showSuccessToast(getApplicationContext(),  getString(R.string.connectivity_toast));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

}