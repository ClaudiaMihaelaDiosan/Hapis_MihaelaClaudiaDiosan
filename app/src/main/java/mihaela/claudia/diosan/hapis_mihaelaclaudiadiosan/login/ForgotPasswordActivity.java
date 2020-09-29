package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;

public class ForgotPasswordActivity extends NetworkInfo implements View.OnClickListener {


    /*Buttons*/
    Button recoverPassword;

    /*EditTexts*/
    TextInputEditText forgotPasswordEmail;

    /*Firebase*/
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        initViews();

        recoverPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recover_password_button){
            if (isEmailValid()){
                resetPassword(forgotPasswordEmail.getText().toString());
            }else{
                forgotPasswordEmail.setError(getString(R.string.is_email_valid_error));
            }
        }
    }

    private void initViews(){
        recoverPassword = findViewById(R.id.recover_password_button);
        forgotPasswordEmail = findViewById(R.id.forgot_password_email);
    }


    private void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showPopUp();
                        } else {
                            HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.fp_recover_failed));
                        }
                    }
                });
    }


    private boolean isEmailValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(forgotPasswordEmail.getText().toString()).matches() && !forgotPasswordEmail.getText().toString().isEmpty();
    }


    public void showPopUp(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setTitle(getString(R.string.pop_up_title))
                .setMessage(getString(R.string.pop_up_message))
                .setIcon(R.drawable.check_drawable)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.pop_up_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                    }
                });

        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }
}
