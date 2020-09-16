package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.AuxiliaryMethods;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.HomeDonor;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.MainActivityLG;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomeVolunteer;

public class LoginActivity extends MainActivity implements View.OnClickListener{

    /*TextViews*/
    private TextView forgotPassword;

    /*Buttons*/
    private MaterialButton loginBtn;
    private MaterialButton registerBtn;
    private MaterialButton lgBtn;

    /*EditTexts*/
    private TextInputEditText loginEmailEditText;
    private TextInputEditText loginPasswordEditText;

    private String loginEmailValue;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        AuxiliaryMethods.makeActivityFullScreen(getWindow(), getSupportActionBar());
        initViews();

        loginBtn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        lgBtn.setOnClickListener(this);
    }


    private void initViews() {
        forgotPassword = findViewById(R.id.forgot_password_text_view);
        registerBtn = findViewById(R.id.signup);
        loginEmailEditText = findViewById(R.id.login_email_edit_text);
        loginPasswordEditText = findViewById(R.id.login_password_edit_text);
        loginBtn = findViewById(R.id.login_button);
        lgBtn = findViewById(R.id.liquid_galaxy_tv);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgot_password_text_view:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class ));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.signup:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.liquid_galaxy_tv:
                startActivity(new Intent(LoginActivity.this, MainActivityLG.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.login_button:
                validateForm();
                login();

        }
    }

    public void login(){
        loginEmailValue = loginEmailEditText.getText().toString();
        String loginPasswordValue = loginPasswordEditText.getText().toString();

        if (loginEmailValue.isEmpty() || loginPasswordValue.isEmpty()){
            loginEmailEditText.setError(getString(R.string.email_error_text));
        }else{
            mAuth.signInWithEmailAndPassword(loginEmailValue, loginPasswordValue)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                setDialog();
                                isDonor(loginEmailValue);
                                isVolunteer(loginEmailValue);
                            } else {
                                HelpActivity.showErrorToast(LoginActivity.this, getString(R.string.error_login));
                            }
                        }
                    });
        }
    }

    private void isDonor(String email){
        DocumentReference donorsDocument = mFirestore.collection("donors").document(email);

        if (email.isEmpty()){
            loginEmailEditText.setError(getString(R.string.email_error_text));
        }else{
            donorsDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()){
                            startActivity(new Intent(LoginActivity.this, HomeDonor.class));
                        }
                    }
                }
            });
        }
    }


    private void isVolunteer(String email){
        DocumentReference volunteersDocument = mFirestore.collection("volunteers").document(email);

        if (email.isEmpty()){
            loginEmailEditText.setError(getString(R.string.email_error_text));
        }else{
            volunteersDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()){
                            startActivity(new Intent(LoginActivity.this, HomeVolunteer.class));
                        }
                    }
                }
            });
        }
    }

    private void validateForm(){
        if (loginEmailEditText.getText().toString().isEmpty()){
            loginEmailEditText.setError(getString(R.string.email_error_text));
        }

        if (loginPasswordEditText.getText().toString().isEmpty()){
            loginPasswordEditText.setError(getString(R.string.password_error_text));
        }
    }

    private void setDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress);
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
