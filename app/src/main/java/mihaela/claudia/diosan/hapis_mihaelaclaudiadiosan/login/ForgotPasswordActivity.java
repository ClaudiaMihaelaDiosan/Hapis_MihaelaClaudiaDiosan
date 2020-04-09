package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

import com.google.android.material.textfield.TextInputEditText;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    //Pop Up
    Dialog successRecoverDialog;

    TextView messageTV;
    ImageView closePopUp;

    Button goLoginBtn;
    Button recoverPassword;

    TextInputEditText forgotPasswordEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        recoverPassword = findViewById(R.id.recover_password_button);
        successRecoverDialog = new Dialog(this);
        forgotPasswordEmail = findViewById(R.id.forgot_password_email);

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

    public void showPopUpDialog(){
        successRecoverDialog.setContentView(R.layout.forgot_password_popup);
        closePopUp =  successRecoverDialog.findViewById(R.id.close_pop_up);
        goLoginBtn =  successRecoverDialog.findViewById(R.id.go_login_button);
        messageTV =   successRecoverDialog.findViewById(R.id.message_pop_up);

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successRecoverDialog.dismiss();
            }
        });

        successRecoverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        successRecoverDialog.show();

        goLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });
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
