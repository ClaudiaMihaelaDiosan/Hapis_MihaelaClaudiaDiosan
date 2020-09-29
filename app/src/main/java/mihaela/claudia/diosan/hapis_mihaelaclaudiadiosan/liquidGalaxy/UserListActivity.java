package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters.LgUserAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.tasks.GetSessionTask;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.DonorRecyclerView.setDonorRecyclerView;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.createPOI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HomelessRecyclerView.setHomelessReyclerView;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.VolunteerRecyclerView.setVolunteerRecyclerView;

public class UserListActivity extends NetworkInfo {

    /*Firebase*/
    private FirebaseFirestore mFirestore;

    /*SearchView*/
    private SearchView searchView;

    SharedPreferences defaultPrefs, preferences, usersPrefs;
    TextView city_tv, country_tv, from_tv;
    ImageView goHome;

    String city, country, userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mFirestore = FirebaseFirestore.getInstance();

        GetSessionTask getSessionTask = new GetSessionTask(this);
        getSessionTask.execute();

        initViews();
        getPreferences();
        setRecyclerView(city, mFirestore, searchView, UserListActivity.this);



        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserListActivity.this, MainActivityLG.class));
            }
        });

    }

    private void initViews() {
        searchView = findViewById(R.id.lg_users_search);
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        city_tv = findViewById(R.id.city_text_users);
        country_tv = findViewById(R.id.country_text_users);
        goHome = findViewById(R.id.go_home_iv_users);
        from_tv = findViewById(R.id.city_text_tv);

    }

    private void getPreferences(){
        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        usersPrefs = getSharedPreferences("listUsersInfo", MODE_PRIVATE);
        preferences = this.getSharedPreferences("cityInfo", MODE_PRIVATE);

        city = preferences.getString("city", "");
        country = preferences.getString("country", "");

        city_tv.setText(city);
        country_tv.setText(country);

        userType = usersPrefs.getString("userType", "");

        if (userType.equals("homeless")){
            from_tv.setText(getString(R.string.homeless_from));
        }else if (userType.equals("donor")){
            from_tv.setText(getString(R.string.donors_from));
        }else if (userType.equals("volunteer")){
            from_tv.setText(getString(R.string.volunteers_from));
        }

    }


    private void setRecyclerView(String city, FirebaseFirestore mFirestore, SearchView searchView, Activity activity){
        RecyclerView.LayoutManager mLayoutManager;
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_users_lg);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new GridLayoutManager(this, 4);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        if (userType.equals("homeless")){
            setHomelessReyclerView(recyclerView, city, mFirestore, searchView, activity);
        }else if (userType.equals("donor")){
            setDonorRecyclerView(recyclerView, city, mFirestore, searchView, activity);
        }else if (userType.equals("volunteer")){
            setVolunteerRecyclerView(recyclerView, city, mFirestore, searchView, activity);
        }

    }



    private void flyToCity(String city){
        mFirestore.collection("cities").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                final String city = document.getString("city");
                                final String latitude = document.getString("latitude");
                                final String longitude = document.getString("longitude");

                                POIController.cleanKmls();
                                POI cityPoi = createPOI(city, latitude, longitude);
                                POIController.getInstance().moveToPOI(cityPoi,null);
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        flyToCity(city);
        startActivity(new Intent(UserListActivity.this, OneCityActivity.class));
    }


}