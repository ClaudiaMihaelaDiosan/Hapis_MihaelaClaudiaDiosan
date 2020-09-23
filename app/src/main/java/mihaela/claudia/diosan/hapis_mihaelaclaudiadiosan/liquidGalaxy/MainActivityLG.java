package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGCommand;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGConnectionManager;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities.AboutActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities.SettingsActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities.ToolsActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGCommand.CRITICAL_MESSAGE;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.EARTH_POI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.STATISTICS;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.createPOI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.buildCityStatistics;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.buildGlobalStatistics;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityClothes;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityDonorsNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityFood;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityHomelessNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityHygiene;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityLodging;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityVolunteersNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getCityWork;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getClothes;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getDonorsNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getFood;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getHomelessNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getHygiene;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getLodging;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getPersonallyNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getThroughVolunteerNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getTotalCities;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getVolunteersNumber;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils.HelpBuildingStatistics.getWork;

public class MainActivityLG extends AppCompatActivity implements View.OnClickListener {

    MaterialCardView cities, globalStatistics, cityStatistics;
    MaterialButton stopStatistics;
    SharedPreferences preferences;

    /*Firebase*/
    private FirebaseFirestore mFirestore;

    final Handler handler = new Handler();
    Runnable runnable;
    String sentence = "sleep 15";

    String logos_slave, homeless_slave, local_statistics_slave, global_statistics_slave, live_overview_homeless, hostname;



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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cities_cv:
                startActivity(new Intent(MainActivityLG.this, CitiesActivity.class));
                POIController.getInstance().moveToPOI(EARTH_POI, null);
                break;
            case R.id.statistics_cv:
                POIController.cleanKmls();
                cleanKmls(logos_slave, homeless_slave, local_statistics_slave, global_statistics_slave, live_overview_homeless);

                getGlobalStatistics();
                globalStatistics();
                break;
            case R.id.city_statistics:
                POIController.cleanKmls();
                cleanKmls(logos_slave, homeless_slave, local_statistics_slave, global_statistics_slave, live_overview_homeless);
                stopStatistics.setVisibility(View.VISIBLE);

                localStatistics();
                break;
            case R.id.stop_statistics:
              stopStatistics();
              break;
        }
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

    private void getGlobalStatistics() {
        getTotalCities(mFirestore);
        getHomelessNumber(mFirestore);
        getDonorsNumber(mFirestore);
        getVolunteersNumber(mFirestore);
        getFood(getString(R.string.chip_food), mFirestore);
        getWork(getString(R.string.chip_work), mFirestore);
        getLodging(getString(R.string.chip_lodging), mFirestore);
        getClothes(getString(R.string.chip_clothes), mFirestore);
        getHygiene(getString(R.string.chip_hygiene_products), mFirestore);
        getPersonallyNumber(mFirestore);
        getThroughVolunteerNumber(mFirestore);
    }



    private void globalStatistics() {
        mFirestore.collection("statistics")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                final String cities = document.getString("cities");
                                final String homeless = document.getString("homeless");
                                final String donors = document.getString("donors");
                                final String volunteers = document.getString("volunteers");
                                final String food = document.getString("food");
                                final String clothes = document.getString("clothes");
                                final String work = document.getString("work");
                                final String lodging = document.getString("lodging");
                                final String hygiene = document.getString("hygieneProducts");
                                final String personallyDonations = document.getString("personallyStatistics");
                                final String throughVolunteerDonations = document.getString("throughVolunteerStatistics");
                                final String image = document.getString("image");

                                POIController.getInstance().moveToPOI(STATISTICS, null);
                                String sentence = "cd /var/www/html/hapis/balloons/statistics/ ;curl -o " + STATISTICS.getName() + " " + image;
                                LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, CRITICAL_MESSAGE, null));
                                POIController.getInstance().showBalloonOnSlave(STATISTICS, null, buildGlobalStatistics(cities, homeless, donors, volunteers, food, clothes, work, lodging, hygiene, personallyDonations, throughVolunteerDonations), "http://lg1:81/hapis/balloons/statistics/", STATISTICS.getName(), global_statistics_slave);
                            }
                        }
                    }
                });
    }

    private void getLocalStatistics(String city){
        getCityHomelessNumber(city, mFirestore);
        getCityDonorsNumber(city, mFirestore);
        getCityVolunteersNumber(city, mFirestore);
        getCityFood(city, getString(R.string.chip_food), mFirestore);
        getCityClothes(city, getString(R.string.chip_clothes), mFirestore);
        getCityWork(city,  getString(R.string.chip_work), mFirestore);
        getCityLodging(city, getString(R.string.chip_lodging), mFirestore);
        getCityHygiene(city, getString(R.string.chip_hygiene_products), mFirestore);
    }

    private void localStatistics(){
        mFirestore.collection("cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                final String city = document.getString("city");
                                final String cityWS = document.getString("cityWS");
                                final String latitude = document.getString("latitude");
                                final String longitude = document.getString("longitude");
                                final String altitude = document.getString("altitude");
                                final String homeless = document.getString("homelessNumber");
                                final String donors = document.getString("donorsNumber");
                                final String volunteers = document.getString("volunteersNumber");
                                final String foodSt = document.getString("foodSt");
                                final String clothesSt = document.getString("clothesSt");
                                final String workSt = document.getString("workSt");
                                final String lodgingSt = document.getString("lodgingSt");
                                final String hygieneSt = document.getString("hygieneSt");

                               getLocalStatistics(city);

                                handler.postDelayed( runnable = new Runnable() {
                                    public void run() {
                                        POI cityPOI = createPOI(cityWS, latitude, longitude, altitude);

                                        POIController.getInstance().showBalloonOnSlave(cityPOI, null, buildCityStatistics(city, homeless, donors, volunteers, foodSt, clothesSt, workSt, lodgingSt, hygieneSt), "http://lg1:81/hapis/balloons/statistics/cities/", cityPOI.getName(), local_statistics_slave);
                                        POIController.getInstance().moveToPOI(cityPOI, null);
                                        LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, CRITICAL_MESSAGE, null));
                                    }
                                }, 1000);

                            }
                        }
                    }
                });
    }

    private void stopStatistics(){
        stopStatistics.setVisibility(View.GONE);
        handler.removeCallbacksAndMessages(null);
        POIController.cleanKmls();
        MainActivityLG.cleanKmls(logos_slave, homeless_slave, local_statistics_slave, global_statistics_slave, live_overview_homeless);
        POIController.getInstance().moveToPOI(EARTH_POI, null);
        Toast.makeText(MainActivityLG.this, getString(R.string.stop_statistics), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lg_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_lg:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_tools_lg:
                startActivity(new Intent(this, ToolsActivity.class));
                return true;
            case R.id.help_lg:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            case R.id.action_about_lg:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivityLG.this, LoginActivity.class));

    }
}