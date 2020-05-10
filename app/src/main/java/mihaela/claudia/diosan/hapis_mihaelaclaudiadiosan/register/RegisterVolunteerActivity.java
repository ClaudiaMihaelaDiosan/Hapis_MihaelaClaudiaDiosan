package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class RegisterVolunteerActivity extends MainActivity {

    /* ImageView */
    ImageView regUserImg;
    ImageView closePopUpImg;

    /* Buttons */
    MaterialButton registerVolunteerBtn;
    MaterialButton popUpBtn;

    /* Alert Dialogs */
    Dialog succesRegisterDialog;

    /* TextViews */
    TextView acceptTermsTV;


    /* Validate */
    private AwesomeValidation awesomeValidation;

    /* Check Box */
    CheckBox acceptTermsCheckbox;

    /* EditTexts*/
    TextInputEditText volunteerUsernameEditText;
    TextInputEditText volunteerEmailEditText;
    TextInputEditText volunteerPasswordEditText;
    TextInputEditText volunteerFirstNameEditText;
    TextInputEditText volunteerLastNameEditText;
    TextInputEditText volunteerPhoneEditText;

    /* SharedPreferences*/
    SharedPreferences preferences;
    String volunteerUsernameValue;
    String volunteerEmailValue;
    String volunteerPasswordValue;
    String volunteerFirstNameValue;
    String volunteerLastNameValue;
    String volunteerPhoneValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        initViews();
        setAwesomeValidation();
        onClickButtons();
    }


    public void setAwesomeValidation(){
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.user_username_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error_text);
        awesomeValidation.addValidation(this, R.id.user_email_edit_text, Patterns.EMAIL_ADDRESS, R.string.email_error_text);
        awesomeValidation.addValidation(this, R.id.user_password_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.password_error_text);

    }

    public void initViews(){
        regUserImg = findViewById(R.id.register_user_image);
        regUserImg.setImageResource(R.drawable.volunteer_card_view_image);


        registerVolunteerBtn = findViewById(R.id.register_user_button);
        succesRegisterDialog = new Dialog(this);

        acceptTermsTV = findViewById(R.id.accept_terms_text_view);
        acceptTermsTV.setMovementMethod(LinkMovementMethod.getInstance());

        acceptTermsCheckbox = findViewById(R.id.user_terms_checkbox);

        volunteerUsernameEditText = findViewById(R.id.user_username_edit_text);
        volunteerEmailEditText = findViewById(R.id.user_email_edit_text);
        volunteerPasswordEditText = findViewById(R.id.user_password_edit_text);
        volunteerFirstNameEditText = findViewById(R.id.user_first_name_edit_text);
        volunteerLastNameEditText = findViewById(R.id.user_last_name_edit_text);
        volunteerPhoneEditText = findViewById(R.id.user_phone_edit_text);
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


    public void registerUserBtn(){
        if (acceptTermsCheckbox.isChecked() && awesomeValidation.validate() && isValidPhoneNumber(volunteerPhoneEditText.getText().toString())) {
            getInfo();
            showPopUp();
        }else if (!acceptTermsCheckbox.isChecked()){
            showToast();
        }else if (!isValidPhoneNumber(volunteerPhoneEditText.getText().toString())){
            volunteerPhoneEditText.setError(getString(R.string.phone_error_text));
          //  Toast.makeText(RegisterVolunteerActivity.this, getString(R.string.phone_error_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickButtons(){
        acceptTermsCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acceptTermsCheckbox.isChecked()){
                    acceptTermsCheckbox.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    acceptTermsCheckbox.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });


        //Register volunteer button
        registerVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserBtn();
            }
        });
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

    public void showPopUp(){
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.register_pop_up_title))
                .setMessage(getString(R.string.register_pop_up_message))
                .setIcon(R.drawable.ic_check_circle_black_24dp)
                .setPositiveButton(getString(R.string.register_pop_up_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goLogin = new Intent(RegisterVolunteerActivity.this, LoginActivity.class);
                        startActivity(goLogin);
                    }
                })

                .show();
    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
