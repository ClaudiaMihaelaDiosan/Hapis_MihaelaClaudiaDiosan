package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.text.InputType;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;


public class DonorConfigurationFragment extends PreferenceFragmentCompat {

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    /*SharePreferences*/
    private SharedPreferences preferences;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.donor_preferences, rootKey);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        initFirebase();
        phonePrefs();
        passwordPrefs();
        deletePrefs();
        networkPrefs();
    }




    private void initFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void phonePrefs(){
        final EditTextPreference phonePreference = findPreference("phone");
        phonePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            String document = user.getEmail();

            if (!HelpActivity.isValidPhoneNumber(newValue.toString())){
                HelpActivity.showErrorToast(getActivity(),getString(R.string.phone_error_text));
            }else{
                mFirestore.collection("donors").document(document).update("phone", newValue);
                HelpActivity.showSuccessToast(getActivity(),getString(R.string.config_changed_phone_toast));
            }
            return false;
        });
    }

    private void passwordPrefs(){
        final EditTextPreference passwordPreference = findPreference("password");
        final String oldPassword = preferences.getString("passwordValue", "");

        if (passwordPreference!= null) {
            passwordPreference.setOnBindEditTextListener(
                    editText -> editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        }

        passwordPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            final String email = user.getEmail();


            AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword );
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    user.updatePassword(newValue.toString()).addOnCompleteListener(task1 -> {
                        if (!task1.isSuccessful()){
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
                    });
                }
                else {
                    Snackbar snackbar_su = Snackbar
                            .make(getActivity().findViewById(android.R.id.content), getString(R.string.snackbar_auth), Snackbar.LENGTH_LONG);
                    snackbar_su.setBackgroundTint(Color.RED);
                    snackbar_su.show();
                }
            });

            return false;
        });
    }

    private void deletePrefs(){
        final ListPreference deletePrefs = findPreference("delete_prefs");
        deletePrefs.setOnPreferenceChangeListener((preference, newValue) -> {
            preferences.edit().putString("deleteAccountPreference", newValue.toString()).apply();
            deletePrefs.setValue(newValue.toString());

            if (newValue.equals("yes")){
                final String email = user.getEmail();

                DocumentReference donorsDocument = mFirestore.collection("donors").document(email);
                donorsDocument.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()){
                            mFirestore.collection("donors").document(email).delete();
                        }
                    }
                });

                user.delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                HelpActivity.showSuccessToast(getContext(),getString(R.string.snackbar_delete_ok) );
                            }else {
                                HelpActivity.showSuccessToast(getContext(),getString(R.string.snackbar_failed) );
                            }
                        });
            }
            return false;
        });
    }


    private void networkPrefs(){
        final ListPreference networkPreference = findPreference("map");
        networkPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            preferences.edit().putString("networkPreference", newValue.toString()).apply();
            networkPreference.setValue(newValue.toString());
            return false;
        });
    }


}
