package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;

public class HelpTextActivity extends NetworkInfo {

    TextView help_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        help_tv = findViewById(R.id.lg_help_title);
        help_tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}