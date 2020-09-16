package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class ConfigurationVolunteerFragment extends PreferenceFragmentCompat{


    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private EditTextPreference phonePreference;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.volunteer_preferences, rootKey);
        /*SharedPreferences*/

        phonePreference = (EditTextPreference) findPreference("phone_volunteer");

        initFirebase();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        phonePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String document = user.getEmail();

                if (!isValidPhoneNumber(newValue.toString())){
                    showErrorToast(getString(R.string.phone_error_text));
                }else{
                    mFirestore.collection("volunteers").document(document).update("volunteerPhone", newValue);
                    showSuccessToast(getString(R.string.config_changed_phone_toast));
                }
                return false;
            }
        });
    }


    private void initFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
    }


    private boolean isValidPhoneNumber(CharSequence target) {
        if (target== null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    private void showSuccessToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.BLUE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_drawable,0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
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


}



