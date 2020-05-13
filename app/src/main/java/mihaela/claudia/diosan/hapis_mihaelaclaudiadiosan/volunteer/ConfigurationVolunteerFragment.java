package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfigurationVolunteerFragment extends Fragment implements View.OnClickListener {


    /*EditText*/
    private TextInputEditText phoneNumber;

    /*Buttons */
    private MaterialButton saveConfigBtn;

    /*View fragment*/
    private View view;


    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.configuration_fragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        initViews();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        saveConfigBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.config_configuration_button_save){
            changePhone();
        }
    }

    public void changePhone(){

        String document = user.getEmail();

        if (!isValidPhoneNumber(phoneNumber.getText().toString())){
            phoneNumber.setError(getString(R.string.phone_error_text));
        }else{
            mFirestore.collection("volunteers").document(document).update("volunteerPhone", phoneNumber.getText().toString());
            showSuccessToast(getString(R.string.config_changed_phone_toast));
        }
    }



    private void initViews() {
        phoneNumber = view.findViewById(R.id.configuration_phone_number);
        saveConfigBtn = view.findViewById(R.id.config_configuration_button_save);
    }



    public  boolean isValidPhoneNumber(CharSequence target) {
        if (target== null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public void showSuccessToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.BLUE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_black_24dp,0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }



}



