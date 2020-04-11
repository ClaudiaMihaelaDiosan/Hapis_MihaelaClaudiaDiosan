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

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class StatisticsActivity extends AppCompatActivity {

    MaterialButton backLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        checkConection();

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

    public void checkConection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            //Toast.makeText(getApplicationContext(), R.string.wifi_connected, Toast.LENGTH_SHORT).show();

        } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            //Toast.makeText(getApplicationContext(), R.string.mobile_connected, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), R.string.no_network_operating, Toast.LENGTH_SHORT).show();

        }
    }
}
