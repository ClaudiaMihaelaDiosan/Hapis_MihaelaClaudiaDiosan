package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class RegisterDonorActivity extends MainActivity implements View.OnClickListener {

    /* ImageView */
    ImageView regUserImg;

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


    String donorUsernameValue;
    String donorEmailValue;
    String donorFirstNameValue;
    String donorLastNameValue;
    String donorPhoneValue;



    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    private Map<String,String> user = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        awesomeValidation();
        initViews();

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        registerDonorBtn.setOnClickListener(this);
        acceptTermsCheckbox.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.register_user_button:
                getInfo();
                registerUser();
                break;
            case R.id.user_terms_checkbox:
                if (acceptTermsCheckbox.isChecked()){
                    acceptTermsCheckbox.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    acceptTermsCheckbox.setTextColor(getResources().getColor(R.color.grey));
                }
        }
        }

    public void registerUser(){

        if (acceptTermsCheckbox.isChecked() && isValidForm()) {

            createEmailPasswordAccount(donorEmailEditText.getText().toString(), donorPasswordEditText.getText().toString());

        }else if (!acceptTermsCheckbox.isChecked()){
            showTermsErrorToast();
        }
    }

    public void createEmailPasswordAccount(String email, String password){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showPopUp();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterDonorActivity.this, getString(R.string.user_exists),
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterDonorActivity.this, getString(R.string.error_login),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
        });
    }

    public void getInfo() {

        donorUsernameValue = donorUsernameEditText.getText().toString();
        donorEmailValue = donorEmailEditText.getText().toString();
        donorFirstNameValue = donorFirstNameEditText.getText().toString();
        donorLastNameValue = donorLastNameEditText.getText().toString();
        donorPhoneValue = donorPhoneEditText.getText().toString();

        user.put("donorUsername", donorUsernameValue);
        user.put("donorEmail", donorEmailValue);
        user.put("donorFirstName", donorFirstNameValue);
        user.put("donorLastName", donorLastNameValue);
        user.put("donorPhone", donorPhoneValue);

        mFirestore.collection("donors").document(donorEmailValue).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      //  Toast.makeText(RegisterDonorActivity.this, "Donor data recorded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(RegisterDonorActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void initViews(){
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

    private boolean isValidForm(){
        if (!isValidPhoneNumber(donorPhoneEditText.getText().toString())){
            donorPhoneEditText.setError(getString(R.string.phone_error_text));
        }else if (!isPasswordValid(donorPasswordEditText.getText().toString())){
            donorPasswordEditText.setError(getString(R.string.password_error_text));
        }
        return  awesomeValidation.validate() && isValidPhoneNumber(donorPhoneEditText.getText().toString()) && isPasswordValid(donorPasswordEditText.getText().toString());
    }

    public void awesomeValidation() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.user_username_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error_text);
    }


    public  boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() == 0){
            return true;
        }else if (target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    private boolean isPasswordValid(CharSequence password){
        if (password.length() > 6) {
            return true;
        }
        return false;
    }

    public void showTermsErrorToast(){
        Toast toast = Toast.makeText(RegisterDonorActivity.this, getString(R.string.register_user_terms_error), Toast.LENGTH_LONG);
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


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }


}
