package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.HomeDonor;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomeVolunteer;

public class LoginActivity extends AppCompatActivity {

    TextView forgotPassword;
    TextView signUp;
    TextView statistics;

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        forgotPassword = (TextView) findViewById(R.id.forgot_password_text_view);
        signUp = (TextView) findViewById(R.id.signup);
        statistics = findViewById(R.id.statistics_tv);

        loginBtn = findViewById(R.id.login_button);


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class );
                startActivity(forgotPassActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsActivity = new Intent(LoginActivity.this, StatisticsActivity.class);
                startActivity(statisticsActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donorActivity = new Intent(LoginActivity.this, HomeDonor.class);
                startActivity(donorActivity);
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }
}
