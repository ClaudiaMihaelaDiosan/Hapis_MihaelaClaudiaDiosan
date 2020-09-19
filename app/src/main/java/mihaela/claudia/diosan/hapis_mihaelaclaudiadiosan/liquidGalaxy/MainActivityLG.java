package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGConnectionManager;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;

public class MainActivityLG extends AppCompatActivity implements View.OnClickListener {

    MaterialCardView cities, globalStatistics, cityStatistics;
    MaterialButton stopStatistics;
    SharedPreferences preferences;

    private Map<String, String> cityInfo = new HashMap<>();
    private Map<String, String> globalInfo = new HashMap<>();

    /*Firebase*/
    private FirebaseFirestore mFirestore;

    final Handler handler = new Handler();
    Runnable runnable;
    String sentence = "sleep 15";

    String logos_slave, homeless_slave, local_statistics_slave, global_statistics_slave, live_overview_homeless, hostname;


    public static final POI EARTH_POI = new POI()
            .setName("Earth")
            .setLongitude(-3.629954d)  //10.52668d
            .setLatitude(40.769083d)  //40.085941d
            .setAltitude(0.0d)
            .setHeading(0.0d)      //90.0d
            .setTilt(0.0d)
            .setRange(10000000.0d)  //10000000.0d
            .setAltitudeMode("relativeToSeaFloor");

    public static final POI STATISTICS = new POI()
            .setName("GLOBAL_STATISTICS")
            .setLongitude(40.331103d)
            .setLatitude(75.297993d)
            .setAltitude(0.0d)
            .setHeading(90.0d)
            .setTilt(0.0d)
            .setRange(10000000.0d)
            .setAltitudeMode("relativeToSeaFloor");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_l_g);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mFirestore = FirebaseFirestore.getInstance();

        getPreferences();
        initViews();

        cleanKmls(logos_slave, homeless_slave, local_statistics_slave, global_statistics_slave, live_overview_homeless);
        POIController.getInstance().moveToPOI(EARTH_POI, null);

        cities.setOnClickListener(this);
        globalStatistics.setOnClickListener(this);
        cityStatistics.setOnClickListener(this);
        stopStatistics.setOnClickListener(this);

    }



    private void initViews() {
        cities = findViewById(R.id.cities_cv);
        globalStatistics = findViewById(R.id.statistics_cv);
        cityStatistics = findViewById(R.id.city_statistics);
        stopStatistics = findViewById(R.id.stop_statistics);
    }

    private void getPreferences(){
        logos_slave = preferences.getString("logos_preference", "");
        homeless_slave = preferences.getString("homeless_preference", "");
        local_statistics_slave = preferences.getString("local_preference", "");
        global_statistics_slave = preferences.getString("global_preference", "");
        live_overview_homeless = preferences.getString("live_overview_homeless", "");

        String user = preferences.getString("SSH-USER", "lg");
        String password = preferences.getString("SSH-PASSWORD", "lqgalaxy");
        hostname = preferences.getString("SSH-IP", "");
        String port = preferences.getString("SSH-PORT", "22");

        LGConnectionManager lgConnectionManager = new LGConnectionManager();
        lgConnectionManager.setData(user, password, hostname, Integer.parseInt(port));

    }

    public static void cleanKmls(String logos_slave, String homeless_slave, String local_statistics_slave, String global_statistics_slave, String live_overview_homeless) {
        POIController.cleanKmls();
        POIController.cleanKmlSlave(homeless_slave);
        POIController.cleanKmlSlave(local_statistics_slave);
        POIController.cleanKmlSlave(global_statistics_slave);
        POIController.cleanKmlSlave(live_overview_homeless);
        POIController.setLogos(logos_slave);


    }



    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        String user = preferences.getString("SSH-USER", "lg");
        String password = preferences.getString("SSH-PASSWORD", "lqgalaxy");
        String hostname = preferences.getString("SSH-IP", "192.168.1.76");
        String port = preferences.getString("SSH-PORT", "22");

        LGConnectionManager lgConnectionManager = new LGConnectionManager();
        lgConnectionManager.setData(user, password, hostname, Integer.parseInt(port));

    }
}