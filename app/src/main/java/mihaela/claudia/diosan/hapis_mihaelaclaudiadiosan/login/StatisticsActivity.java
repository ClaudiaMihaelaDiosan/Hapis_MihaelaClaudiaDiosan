package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class StatisticsActivity extends MainActivity {

    MaterialButton backLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        backLoginBtn = findViewById(R.id.statistics_back_login);

        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(StatisticsActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
