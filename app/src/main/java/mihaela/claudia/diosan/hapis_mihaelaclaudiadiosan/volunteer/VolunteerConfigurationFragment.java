package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;

public class VolunteerConfigurationFragment extends PreferenceFragmentCompat {

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    /*SharePreferences*/
    private SharedPreferences preferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.volunteer_preferences, rootKey);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        initFirebase();
        phonePrefs();
        passwordPrefs();
        visualizationPrefs();

    }

    private void initFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void phonePrefs(){
        final EditTextPreference phonePreference = (EditTextPreference) findPreference("phone_volunteer");
        phonePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String document = user.getEmail();

                if (!HelpActivity.isValidPhoneNumber(newValue.toString())){
                    HelpActivity.showErrorToast(getActivity(),getString(R.string.phone_error_text));
                }else{
                    mFirestore.collection("volunteers").document(document).update("phone", newValue);
                    HelpActivity.showSuccessToast(getActivity(),getString(R.string.config_changed_phone_toast));
                }
                return false;
            }
        });
    }

    private void passwordPrefs(){
        final EditTextPreference passwordPreference = (EditTextPreference) findPreference("password_vol");
        final String oldPassword = preferences.getString("passwordValue", "");

        if (passwordPreference!= null) {
            passwordPreference.setOnBindEditTextListener(
                    new EditTextPreference.OnBindEditTextListener() {
                        @Override
                        public void onBindEditText(@NonNull EditText editText) {
                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    });
        }

        passwordPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue) {
                final String email = user.getEmail();

                AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword );
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            user.updatePassword(newValue.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        Snackbar snackbar_fail = Snackbar
                                                .make(getActivity().findViewById(android.R.id.content), getString(R.string.snackbar_failed), Snackbar.LENGTH_LONG);
                                        snackbar_fail.setBackgroundTint(Color.RED);
                                        snackbar_fail.show();
                                    }else{
                                        Snackbar snackbar_su = Snackbar
                                                .make(getActivity().findViewById(android.R.id.content), getString(R.string.snackbar_succes), Snackbar.LENGTH_LONG);
                                        snackbar_su.setBackgroundTint(Color.GREEN);
                                        snackbar_su.setTextColor(Color.BLACK);
                                        snackbar_su.show();
                                    }
                                }
                            });
                        }
                        else {
                            Snackbar snackbar_su = Snackbar
                                    .make(getActivity().findViewById(android.R.id.content), getString(R.string.snackbar_auth), Snackbar.LENGTH_LONG);
                            snackbar_su.setBackgroundTint(Color.RED);
                            snackbar_su.show();
                        }
                    }
                });

                return false;
            }
        });
    }

    private void visualizationPrefs(){
        final ListPreference visualizationPrefs = (ListPreference) findPreference("visualization");
        visualizationPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preferences.edit().putString("visualizationPref", newValue.toString()).apply();
                visualizationPrefs.setValue(newValue.toString());
                return false;
            }
        });
    }


}