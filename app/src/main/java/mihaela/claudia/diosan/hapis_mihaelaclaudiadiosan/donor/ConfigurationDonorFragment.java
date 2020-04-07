package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.ConfigurationVolunteerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationDonorFragment extends PreferenceFragmentCompat {

    private static final String PREF_LANGUAGE ="language_pref";



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.donor_configuration_preference, rootKey);



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
                        .replace(R.id.donor_fragment_container, new ConfigurationDonorFragment())
                        .commit();
                return true;
            }
        });

    }


}
