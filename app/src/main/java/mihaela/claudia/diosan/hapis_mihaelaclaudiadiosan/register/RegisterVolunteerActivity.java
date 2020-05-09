package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class RegisterVolunteerActivity extends MainActivity {

    Dialog succesRegisterDialog;
    MaterialButton registerVolunteerBtn;
    ImageView rvClosePopUp;
    MaterialButton rvOKBtn;
    TextView rvPopupMessage;
    TextView rvPopupTitle;
    TextView rvAcceptTerms;
    CheckBox rvAcceptTermsCheckbox;

    TextInputEditText volunteerUsernameEditText;
    TextInputEditText volunteerEmailEditText;
    TextInputEditText volunteerPasswordEditText;
    TextInputEditText volunteerFirstNameEditText;
    TextInputEditText volunteerLastNameEditText;
    TextInputEditText volunteerPhoneEditText;

    String volunteerUsernameValue;
    String volunteerEmailValue;
    String volunteerPasswordValue;
    String volunteerFirstNameValue;
    String volunteerLastNameValue;
    String volunteerPhoneValue;

    SharedPreferences preferences;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_volunteer);

        registerVolunteerBtn = findViewById(R.id.register_volunteer_button);
        succesRegisterDialog = new Dialog(this);
        rvAcceptTerms = findViewById(R.id.volunteer_accept_terms_text_view);
        rvAcceptTerms.setMovementMethod(LinkMovementMethod.getInstance());

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        rvAcceptTermsCheckbox = findViewById(R.id.volunteer_terms_checkbox);

        volunteerUsernameEditText = findViewById(R.id.volunteer_username_edit_text);
        volunteerEmailEditText = findViewById(R.id.volunteer_email_edit_text);
        volunteerPasswordEditText = findViewById(R.id.volunteer_password_edit_text);
        volunteerFirstNameEditText = findViewById(R.id.volunteer_first_name_edit_text);
        volunteerLastNameEditText = findViewById(R.id.volunteer_last_name_edit_text);
        volunteerPhoneEditText = findViewById(R.id.volunteer_phone_edit_text);

        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        awesomeValidation.addValidation(this, R.id.volunteer_username_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error_text);
        awesomeValidation.addValidation(this, R.id.volunteer_email_edit_text, Patterns.EMAIL_ADDRESS, R.string.email_error_text);
        awesomeValidation.addValidation(this, R.id.volunteer_password_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.password_error_text);



        rvAcceptTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvAcceptTermsCheckbox.isChecked()){
                    rvAcceptTermsCheckbox.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    rvAcceptTermsCheckbox.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });



        //Register volunteer button
        registerVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvAcceptTermsCheckbox.isChecked() && awesomeValidation.validate() && isValidPhoneNumber(volunteerPhoneEditText.getText().toString())) {
                    getInfo();
                    showPopUpDialog();
                }else if (!rvAcceptTermsCheckbox.isChecked()){
                    showToast();
                }else if (!isValidPhoneNumber(volunteerPhoneEditText.getText().toString())){
                    Toast.makeText(RegisterVolunteerActivity.this, getString(R.string.phone_error_text), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public  boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() == 0){
            return true;
        }else if (target== null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }


    public void getInfo(){

        volunteerUsernameValue = volunteerUsernameEditText.getText().toString();
        volunteerEmailValue = volunteerEmailEditText.getText().toString();
        volunteerPasswordValue = volunteerPasswordEditText.getText().toString();
        volunteerFirstNameValue = volunteerFirstNameEditText.getText().toString();
        volunteerLastNameValue = volunteerLastNameEditText.getText().toString();
        volunteerPhoneValue = volunteerPhoneEditText.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("volunteerUsername", volunteerUsernameValue);
        editor.putString("volunteerEmail",volunteerEmailValue);
        editor.putString("volunteerPassword", volunteerPasswordValue);
        editor.putString("volunteerFirstName",volunteerFirstNameValue);
        editor.putString("volunteerLastName", volunteerLastNameValue);
        editor.putString("volunteerPhone", volunteerPhoneValue);
        editor.apply();
    }



    public void showToast(){
        Toast toast = Toast.makeText(RegisterVolunteerActivity.this, getString(R.string.register_volunteer_terms_error), Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toast.show();
    }

    public void showPopUpDialog(){
        succesRegisterDialog.setContentView(R.layout.register_volunteer_popup);
        rvClosePopUp =  succesRegisterDialog.findViewById(R.id.rv_close_pop_up);
        rvOKBtn =  succesRegisterDialog.findViewById(R.id.rv_ok_button);
        rvPopupTitle =  succesRegisterDialog.findViewById(R.id.rv_title_popup);
        rvPopupMessage =  succesRegisterDialog.findViewById(R.id.rv_message_pop_up);



        rvClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                succesRegisterDialog.dismiss();
            }
        });

        Objects.requireNonNull(succesRegisterDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        succesRegisterDialog.show();

        rvOKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegisterVolunteerActivity.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
