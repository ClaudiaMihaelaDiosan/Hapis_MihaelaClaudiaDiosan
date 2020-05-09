package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.textfield.TextInputEditText;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.HomeDonor;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomeVolunteer;

public class LoginActivity extends MainActivity {

    TextView forgotPassword;
    TextView signUp;
    TextView statistics;

    Button loginBtn;

    TextInputEditText loginUsernameEditText;
    TextInputEditText loginPasswordEditText;

    String loginUsernameValue;
    String loginPasswordValue;

    SharedPreferences preferences;
    private AwesomeValidation awesomeValidation;

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
        loginUsernameEditText = findViewById(R.id.login_username_edit_text);
        loginPasswordEditText = findViewById(R.id.login_password_edit_text);

        loginBtn = findViewById(R.id.login_button);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        awesomeValidation.addValidation(this, R.id.login_username_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error_text);
        awesomeValidation.addValidation(this, R.id.login_password_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.password_error_text);


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
                if (awesomeValidation.validate()){
                    login();
                }

            }
        });
    }

    public void login(){
        loginUsernameValue = loginUsernameEditText.getText().toString();
        loginPasswordValue = loginPasswordEditText.getText().toString();

            String registeredDonorUsername = preferences.getString("donorUsername","");
            String registeredDonorPassword = preferences.getString("donorPassword", "");
            String registeredVolunteerUsername = preferences.getString("volunteerUsername","");
            String registeredVolunteerPassword = preferences.getString("volunteerPassword", "");

            if (loginUsernameValue.equals(registeredDonorUsername) && loginPasswordValue.equals(registeredDonorPassword)){
                Intent donorIntent = new Intent(LoginActivity.this, HomeDonor.class);
                startActivity(donorIntent);

            }else if (loginUsernameValue.equals(registeredVolunteerUsername) && loginPasswordValue.equals(registeredVolunteerPassword)){
                Intent volunteerIntent = new Intent(LoginActivity.this, HomeVolunteer.class);
                startActivity(volunteerIntent);

            }else{
                Toast.makeText(LoginActivity.this, getString(R.string.error_login), Toast.LENGTH_SHORT).show();
            }

        }



    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }



}
