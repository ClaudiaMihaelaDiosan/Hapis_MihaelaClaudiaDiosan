package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters.CitiesCardsAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.logic.Cities;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.createPOI;

public class CitiesActivity extends AppCompatActivity {

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    /*SearchView*/
    private SearchView searchView;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        mFirestore = FirebaseFirestore.getInstance();
        preferences = this.getSharedPreferences("cityInfo", MODE_PRIVATE);

        initViews();
        setRecyclerView();
    }

    private void initViews(){
        searchView =findViewById(R.id.city_search);
        searchView.onActionViewExpanded();
        searchView.clearFocus();

    }

    private void setRecyclerView(){
        RecyclerView.LayoutManager mLayoutManager ;
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_cities);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(this, 2);
        }else {
            mLayoutManager = new GridLayoutManager(this, 4);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        final List<Cities> cities = new ArrayList<>();


        mFirestore.collection("cities")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            final String city = document.getString("city");
                            final String country = document.getString("country");
                            final String image = document.getString("image");
                            final String latitude = document.getString("latitude");
                            final String longitude = document.getString("longitude");
                            final String altitude = document.getString("altitude");

                            final Cities oneCity = new Cities(city, country, image, latitude, longitude, altitude);
                            cities.add(oneCity);


                            final CitiesCardsAdapter citiesCardsAdapter = new CitiesCardsAdapter(cities);
                            searchText(citiesCardsAdapter);
                            recyclerView.setAdapter(citiesCardsAdapter);

                            citiesCardsAdapter.setOnItemClickListener(position -> {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("city",  cities.get(position).getCity()).apply();
                                editor.putString("country", cities.get(position).getCountry()).apply();
                                editor.apply();

                                POIController.cleanKmls();

                                POI cityPoi = createPOI(cities.get(position).getCity(),cities.get(position).getLatitude(), cities.get(position).getLongitude(), cities.get(position).getAltitude());
                                POIController.getInstance().moveToPOI(cityPoi,null);

                                startActivity(new Intent(CitiesActivity.this, OneCityActivity.class));

                            });

                        }
                    }
                });
    }

    private void searchText(final CitiesCardsAdapter citiesCardsAdapter){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                citiesCardsAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CitiesActivity.this, MainActivityLG.class));

    }

}