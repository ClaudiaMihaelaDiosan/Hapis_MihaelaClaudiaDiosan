package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import java.util.Locale;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class ConfigurationVolunteerFragment extends PreferenceFragmentCompat{

    private static final String PREF_PHONE = "change_phone_key";
    private static final String PREF_LANGUAGE ="language_pref";
    private Locale locale = null;



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.volunteer_configuration_preference, rootKey);

        androidx.preference.EditTextPreference editTextPreference = getPreferenceManager().findPreference(PREF_PHONE);
        editTextPreference.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });



        //TODO:FIX THIS METHOD
        androidx.preference.ListPreference listPreference = getPreferenceManager().findPreference(PREF_LANGUAGE);
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // Get selected language
                String selectedLanguage = newValue.toString();
                preference.setSummary(selectedLanguage);

                // Change language
                Locale locale;
                if (selectedLanguage.equals("English")) {
                    locale = new Locale("es");
                } else {
                    locale = new Locale("en");
                }
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getActivity().getApplicationContext().getResources().updateConfiguration(config, null);

                // Refresh fragment
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ConfigurationVolunteerFragment())
                        .commit();
                return true;
            }
        });

    }

}



