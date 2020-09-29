package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;

public class AboutActivity extends NetworkInfo {

    TextView about_photos, about_photos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about_photos = findViewById(R.id.about_photos_tv);
        about_photos1 = findViewById(R.id.about_photos_tv_1);

        about_photos.setMovementMethod(LinkMovementMethod.getInstance());
        about_photos1.setMovementMethod(LinkMovementMethod.getInstance());
    }
}