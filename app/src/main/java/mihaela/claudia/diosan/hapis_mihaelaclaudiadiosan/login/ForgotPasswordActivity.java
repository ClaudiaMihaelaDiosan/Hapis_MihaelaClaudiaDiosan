package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class ForgotPasswordActivity extends MainActivity {


    AlertDialog dialogBuilder;
    /*Buttons*/
    Button recoverPassword;

    /*EditTexts*/
    TextInputEditText forgotPasswordEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        makeFullscreenActivity();
        initViews();
        onClickButtons();

    }



    private void initViews(){
        recoverPassword = findViewById(R.id.recover_password_button);
        forgotPasswordEmail = findViewById(R.id.forgot_password_email);
    }

    private void onClickButtons(){
        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid()){
                    showPopUpDialog();
                }else{
                    showErrorToast();
                }
            }
        });
    }

    private void makeFullscreenActivity() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    public void showErrorToast(){
        Toast toast = Toast.makeText(ForgotPasswordActivity.this, getString(R.string.is_email_valid_error), Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_drawable, 0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }


    private void showPopUpDialog(){
            dialogBuilder = new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_check_circle_black_24dp)
                    .setMessage(getString(R.string.pop_up_message))
                    .setPositiveButton(getString(R.string.pop_up_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goLogin = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
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

    boolean isEmailValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(forgotPasswordEmail.getText().toString()).matches() && !forgotPasswordEmail.getText().toString().isEmpty();
    }

}
