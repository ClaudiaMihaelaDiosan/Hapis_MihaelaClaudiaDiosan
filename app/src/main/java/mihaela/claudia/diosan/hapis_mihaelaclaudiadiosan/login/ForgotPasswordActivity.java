package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button recoverPassword;

    //Pop Up
    Dialog successRecoverDialog;
    TextView messageTV;
    ImageView closePopUp;
    Button goLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        recoverPassword = findViewById(R.id.recover_password_button);
        successRecoverDialog = new Dialog(this);



        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpDialog();
            }
        });


    }

    public void showPopUpDialog(){
        successRecoverDialog.setContentView(R.layout.forgot_password_popup);
        closePopUp = (ImageView) successRecoverDialog.findViewById(R.id.close_pop_up);
        goLoginBtn = (Button) successRecoverDialog.findViewById(R.id.go_login_button);
        messageTV = (TextView) successRecoverDialog.findViewById(R.id.message_pop_up);

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
}
