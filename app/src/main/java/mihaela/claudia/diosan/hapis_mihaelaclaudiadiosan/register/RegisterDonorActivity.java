package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class RegisterDonorActivity extends AppCompatActivity {


    Dialog succesRegisterDialog;
    MaterialButton registerDonorBtn;
    ImageView rdClosePopUp;
    MaterialButton rdOKBtn;
    TextView rdPopupMessage;
    TextView rdPopupTitle;
    TextView rdAcceptTermsTV;
    CheckBox acceptTermsCheckbox;

    TextInputEditText donorUsernameEditText;
    TextInputEditText donorEmailEditText;
    TextInputEditText donorPasswordEditText;
    TextInputEditText donorFirstNameEditText;
    TextInputEditText donorLastNameEditText;
    TextInputEditText donorPhoneEditText;

    String donorUsernameValue;
    String donorEmailValue;
    String donorPasswordValue;
    String donorFirstNameValue;
    String donorLastNameValue;
    String donorPhoneValue;

    SharedPreferences preferences;
    private AwesomeValidation awesomeValidation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);

        registerDonorBtn = findViewById(R.id.register_donor_button);
        succesRegisterDialog = new Dialog(this);
        rdAcceptTermsTV = findViewById(R.id.accept_terms_text_view);
        rdAcceptTermsTV.setMovementMethod(LinkMovementMethod.getInstance());

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        acceptTermsCheckbox = findViewById(R.id.donor_terms_checkbox);

        donorUsernameEditText = findViewById(R.id.donor_username_edit_text);
        donorEmailEditText = findViewById(R.id.donor_email_edit_text);
        donorPasswordEditText = findViewById(R.id.donor_password_edit_text);
        donorFirstNameEditText = findViewById(R.id.donor_first_name_edit_text);
        donorLastNameEditText = findViewById(R.id.donor_last_name_edit_text);
        donorPhoneEditText = findViewById(R.id.donor_phone_edit_text);

        awesomeValidation.addValidation(this, R.id.donor_username_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error_text);
        awesomeValidation.addValidation(this, R.id.donor_email_edit_text, Patterns.EMAIL_ADDRESS, R.string.email_error_text);
        awesomeValidation.addValidation(this, R.id.donor_password_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.password_error_text);


        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);

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


        //Register donor button
        registerDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acceptTermsCheckbox.isChecked() && awesomeValidation.validate() && isValidPhoneNumber(donorPhoneEditText.getText().toString())) {
                    getInfo();
                    showPopUpDialog();
                }else if (!acceptTermsCheckbox.isChecked()){
                    showToast();
                }else if (!isValidPhoneNumber(donorPhoneEditText.getText().toString())){
                    Toast.makeText(RegisterDonorActivity.this, getString(R.string.phone_error_text), Toast.LENGTH_SHORT).show();
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




    public void showToast(){
        Toast toast = Toast.makeText(RegisterDonorActivity.this, getString(R.string.register_donor_terms_error), Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toast.show();
    }



    public void showPopUpDialog(){
        succesRegisterDialog.setContentView(R.layout.register_donor_popup);
        rdClosePopUp =  succesRegisterDialog.findViewById(R.id.rd_close_pop_up);
        rdOKBtn =  succesRegisterDialog.findViewById(R.id.rp_ok_button);
        rdPopupTitle =  succesRegisterDialog.findViewById(R.id.rp_title_popup);
        rdPopupMessage =  succesRegisterDialog.findViewById(R.id.rp_message_pop_up);



        rdClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                succesRegisterDialog.dismiss();
            }
        });

        Objects.requireNonNull(succesRegisterDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        succesRegisterDialog.show();

        rdOKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegisterDonorActivity.this, LoginActivity.class);
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
