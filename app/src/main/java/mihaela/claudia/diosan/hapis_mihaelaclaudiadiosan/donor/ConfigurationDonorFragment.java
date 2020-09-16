package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class ConfigurationDonorFragment extends PreferenceFragmentCompat {

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    /*SharePreferences*/
    private SharedPreferences preferences;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        initFirebase();
        phonePrefs();
        networkPrefs();
    }

    private void initFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void phonePrefs(){
        final EditTextPreference phonePreference = (EditTextPreference) findPreference("phone");
        phonePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String document = user.getEmail();

                if (!isValidPhoneNumber(newValue.toString())){
                    showErrorToast(getString(R.string.phone_error_text));
                }else{
                    mFirestore.collection("donors").document(document).update("donorPhone", newValue);
                    showSuccessToast(getString(R.string.config_changed_phone_toast));
                }
                return false;
            }
        });
    }

    private void networkPrefs(){
        final ListPreference networkPreference = (ListPreference) findPreference("map");
        networkPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preferences.edit().putString("networkPreference", newValue.toString()).apply();
                networkPreference.setValue(newValue.toString());
                return false;
            }
        });
    }


    private  boolean isValidPhoneNumber(CharSequence target) {
        if (target== null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }


    public void showErrorToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_drawable,0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }


    private void showSuccessToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.GREEN);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_drawable,0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }

}
