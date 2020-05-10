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

public class RegisterDonorActivity extends MainActivity {

    /* ImageView */
    ImageView regUserImg;
    ImageView closePopUpImg;

    /* Buttons */
    MaterialButton registerDonorBtn;
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
    TextInputEditText donorUsernameEditText;
    TextInputEditText donorEmailEditText;
    TextInputEditText donorPasswordEditText;
    TextInputEditText donorFirstNameEditText;
    TextInputEditText donorLastNameEditText;
    TextInputEditText donorPhoneEditText;

    /* SharedPreferences*/
    SharedPreferences preferences;
    String donorUsernameValue;
    String donorEmailValue;
    String donorPasswordValue;
    String donorFirstNameValue;
    String donorLastNameValue;
    String donorPhoneValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        findViews();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        setAwesomeValidation();

        /*Accept Terms Checkbox*/
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

        /*Register button*/
        registerDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtn();
            }

        });

    }

    public void findViews(){
        regUserImg = findViewById(R.id.register_user_image);
        regUserImg.setImageResource(R.drawable.donor_card_view_image);

        succesRegisterDialog = new Dialog(this);

        acceptTermsTV = findViewById(R.id.accept_terms_text_view);
        acceptTermsTV.setMovementMethod(LinkMovementMethod.getInstance());

        acceptTermsCheckbox = findViewById(R.id.user_terms_checkbox);

        registerDonorBtn = findViewById(R.id.register_user_button);


        donorUsernameEditText = findViewById(R.id.user_username_edit_text);
        donorEmailEditText = findViewById(R.id.user_email_edit_text);
        donorPasswordEditText = findViewById(R.id.user_password_edit_text);
        donorFirstNameEditText = findViewById(R.id.user_first_name_edit_text);
        donorLastNameEditText = findViewById(R.id.user_last_name_edit_text);
        donorPhoneEditText = findViewById(R.id.user_phone_edit_text);
    }


    public void setAwesomeValidation(){
        awesomeValidation.addValidation(this, R.id.user_username_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error_text);
        awesomeValidation.addValidation(this, R.id.user_email_edit_text, Patterns.EMAIL_ADDRESS, R.string.email_error_text);
        awesomeValidation.addValidation(this, R.id.user_password_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.password_error_text);
    }

    public void registerBtn(){
        if (acceptTermsCheckbox.isChecked() && awesomeValidation.validate() && isValidPhoneNumber(donorPhoneEditText.getText().toString())) {
            getInfo();
            showPopUp();
        }else if (!acceptTermsCheckbox.isChecked()){
            showToast();
        }else if (!isValidPhoneNumber(donorPhoneEditText.getText().toString())){
            donorPhoneEditText.setError(getString(R.string.phone_error_text));
          //  Toast.makeText(RegisterDonorActivity.this, getString(R.string.phone_error_text), Toast.LENGTH_SHORT).show();
        }
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


    public void showToast(){
        Toast toast = Toast.makeText(RegisterDonorActivity.this, getString(R.string.register_donor_terms_error), Toast.LENGTH_LONG);
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
                        Intent goLogin = new Intent(RegisterDonorActivity.this, LoginActivity.class);
                        startActivity(goLogin);
                    }
                })

                .show();
    }

    public void getInfo(){

        donorUsernameValue = donorUsernameEditText.getText().toString();
        donorEmailValue = donorEmailEditText.getText().toString();
        donorPasswordValue = donorPasswordEditText.getText().toString();
        donorFirstNameValue = donorFirstNameEditText.getText().toString();
        donorLastNameValue = donorLastNameEditText.getText().toString();
        donorPhoneValue = donorPhoneEditText.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("donorUsername", donorUsernameValue);
        editor.putString("donorEmail",donorEmailValue);
        editor.putString("donorPassword", donorPasswordValue);
        editor.putString("donorFirstName",donorFirstNameValue);
        editor.putString("donorLastName", donorLastNameValue);
        editor.putString("donorPhone", donorPhoneValue);
        editor.apply();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
