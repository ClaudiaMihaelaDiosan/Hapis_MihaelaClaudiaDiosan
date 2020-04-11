package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfigurationVolunteerFragment extends Fragment {


    SharedPreferences preferences;
    TextInputEditText phoneNumber;
    MaterialButton saveConfigBtn;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.volunteer_configuration_fragment, container, false);



        preferences = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);

        phoneNumber = view.findViewById(R.id.configuration_phone_number);
        saveConfigBtn = view.findViewById(R.id.configuration_button_save);


        saveConfigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidPhoneNumber(phoneNumber.getText().toString())){
                    changePhone();
                    Toast.makeText(getActivity(), getString(R.string.changed_phone_toast), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), getString(R.string.phone_error_text), Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    public void changePhone(){
        String phoneNumberValue = phoneNumber.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("volunteerPhone", phoneNumberValue);
        editor.apply();
    }

    public  boolean isValidPhoneNumber(CharSequence target) {
        if (target== null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            try {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}



