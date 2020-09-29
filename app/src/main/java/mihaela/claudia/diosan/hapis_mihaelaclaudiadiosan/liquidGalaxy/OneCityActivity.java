package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.tasks.GetSessionTask;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.tasks.VisitPoiTask;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass.buildCommand;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass.showAllDonors;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass.showAllHomeless;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass.showAllVolunteers;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass.showHomelessInfo;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpCityClass.showLocalStatistics;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.createPOI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController.cleanKmls;

public class OneCityActivity extends NetworkInfo implements View.OnClickListener {

    TextView city_tv, country_tv;
    MaterialCardView homeless, donors, volunteers,liveOverview ;
    ImageView goHome;

    SharedPreferences defaultPrefs, preferences, usersPrefs;

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    String homeless_slave, local_statistics_slave, logos_slave, global_statistics_slave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_city);

        mFirestore = FirebaseFirestore.getInstance();

        GetSessionTask getSessionTask = new GetSessionTask(this);
        getSessionTask.execute();

        initViews();
        getPreferences();

        homeless.setOnClickListener(this);
        donors.setOnClickListener(this);
        volunteers.setOnClickListener(this);
        goHome.setOnClickListener(this);
        liveOverview.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String city = preferences.getString("city","");
        String hostIP = defaultPrefs.getString("SSH-IP", "192.168.1.76");

        switch (v.getId()){
            case R.id.homeless_cv:
                cleanKmls();
                usersPrefs.edit().putString("userType", "homeless").apply();
                showAllHomeless(city , mFirestore,hostIP );
                startActivity(new Intent(OneCityActivity.this, UserListActivity.class));
                break;
            case R.id.donors_cv:
                POIController.cleanKmls();
                usersPrefs.edit().putString("userType", "donor").apply();
                showAllDonors(city, mFirestore, hostIP);
                startActivity(new Intent(OneCityActivity.this, UserListActivity.class));
                break;
            case R.id.volunteers_cv:
                POIController.cleanKmls();
                usersPrefs.edit().putString("userType", "volunteer").apply();
                showAllVolunteers(city, mFirestore, hostIP);
                startActivity(new Intent(OneCityActivity.this, UserListActivity.class));
                break;
            case R.id.go_home_iv:
                startActivity(new Intent(OneCityActivity.this, MainActivityLG.class));
                break;
            case R.id.live_overview_cv:
                POIController.cleanKmls();
                HelpCityClass.cleanKmls(homeless_slave, local_statistics_slave, global_statistics_slave);
                showLocalStatistics(city, mFirestore, local_statistics_slave);
                showAllHomeless(city , mFirestore,hostIP);
                showHomelessInfo(city, mFirestore, homeless_slave);
                liveOverview(city);
                break;
        }
    }


    private void getPreferences(){
        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        homeless_slave = defaultPrefs.getString("homeless_preference","");
        local_statistics_slave = defaultPrefs.getString("local_preference","");
        logos_slave = defaultPrefs.getString("logos_preference","");
        global_statistics_slave = defaultPrefs.getString("global_preference","");

        usersPrefs = this.getSharedPreferences("listUsersInfo", MODE_PRIVATE);

        preferences = this.getSharedPreferences("cityInfo", MODE_PRIVATE);
        String city = preferences.getString("city","");
        String country = preferences.getString("country","");

        city_tv.setText(city);
        country_tv.setText(country);

    }

    private void initViews(){
        city_tv = findViewById(R.id.city_text);
        country_tv = findViewById(R.id.country_text);
        homeless = findViewById(R.id.homeless_cv);
        donors = findViewById(R.id.donors_cv);
        volunteers = findViewById(R.id.volunteers_cv);
        goHome = findViewById(R.id.go_home_iv);
        liveOverview = findViewById(R.id.live_overview_cv);

    }

    private void liveOverview(String city){

        mFirestore.collection("cities").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            final String cityWS = document.getString("cityWS");
                            final String latitude = document.getString("latitude");
                            final String longitude = document.getString("longitude");
                            final String altitude = document.getString("altitude");

                            POI cityPOI = createPOI(cityWS, latitude, longitude, altitude);

                            String command = buildCommand(cityPOI);
                            VisitPoiTask visitPoiTask = new VisitPoiTask(command,cityPOI, true, OneCityActivity.this, OneCityActivity.this);
                            visitPoiTask.execute();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OneCityActivity.this, CitiesActivity.class));

    }

}