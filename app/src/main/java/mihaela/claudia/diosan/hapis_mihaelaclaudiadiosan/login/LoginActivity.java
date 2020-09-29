package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.HomeDonor;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.MainActivityLG;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomeVolunteer;

public class LoginActivity extends NetworkInfo implements View.OnClickListener{

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
    private Map<String,String> userToken = new HashMap<>();

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        HelpActivity.makeActivityFullScreen(getWindow(), getSupportActionBar());
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
              //  startActivity(new Intent(LoginActivity.this, MainActivityLG.class));
                showLGDialog();
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
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("passwordValue", loginPasswordValue).apply();

        if (loginEmailValue.isEmpty() || loginPasswordValue.isEmpty()){
            loginEmailEditText.setError(getString(R.string.email_error_text));
        }else{
            mAuth.signInWithEmailAndPassword(loginEmailValue, loginPasswordValue)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null){
                                    user.reload();
                                    user = mAuth.getCurrentUser();
                                    if (user.isEmailVerified()){
                                        setDialog();
                                        isDonor(loginEmailValue);
                                        isVolunteer(loginEmailValue);
                                    }else{
                                        HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.verify_email_error));
                                    }
                                }

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
                            getDonorToken(email);
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

    private void getDonorToken(String email){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String idToken = task.getResult().getToken();
                        userToken.put("idToken", idToken);
                        mFirestore.collection("donors").document(email).set(userToken, SetOptions.merge());
                        createNotificationChannel();
                    }
                });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(getString(R.string.notification_channel), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showLGDialog(){
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setTitle(getString(R.string.popup_info_title))
                .setMessage(getString(R.string.popup_info_message))
                .setIcon(R.drawable.error_drawable)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.pop_up_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(LoginActivity.this, MainActivityLG.class));
                    }
                });


        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        builder.setNegativeButton(R.string.delete_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        builder.show();



    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
