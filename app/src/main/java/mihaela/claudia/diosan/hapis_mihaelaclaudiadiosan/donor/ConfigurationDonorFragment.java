package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationDonorFragment extends Fragment {

    /*Preferences*/
    private SharedPreferences preferences;

    /*EditText*/
    private TextInputEditText phoneNumber;

    /*Buttons */
    private MaterialButton saveConfigBtn;

    /*View fragment*/
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.configuration_fragment, container, false);

        preferences = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);

        initViews();
        saveButton();

        return view;
    }


    private void initViews(){
        phoneNumber = view.findViewById(R.id.configuration_phone_number);
        saveConfigBtn = view.findViewById(R.id.config_configuration_button_save);
    }

    private void saveButton(){
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
    }

    public void changePhone(){
        String phoneNumberValue = phoneNumber.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("donorPhone", phoneNumberValue);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
