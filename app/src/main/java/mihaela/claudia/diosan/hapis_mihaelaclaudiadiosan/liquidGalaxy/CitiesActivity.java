package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;


import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters.CitiesCardsAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.logic.Cities;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.createPOI;

public class CitiesActivity extends NetworkInfo {

    /*Storage Permission*/
    private static  final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Integer SELECT_FILE = 0;



    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private StorageReference storageReference;

    /*SearchView*/
    private SearchView searchView;

    SharedPreferences preferences;

    private Uri selectedImagePath;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        mFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutCities);

        preferences = this.getSharedPreferences("cityInfo", MODE_PRIVATE);

        initViews();
        setRecyclerView();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerView();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.green)
        );
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

                            final CitiesCardsAdapter citiesCardsAdapter = new CitiesCardsAdapter(cities, this);
                            searchText(citiesCardsAdapter);
                            recyclerView.setAdapter(citiesCardsAdapter);

                            registerForContextMenu(recyclerView);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.long_click_cities, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        verifyStoragePermissions(this);
        CropImage.activity()
                .start(this);

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CitiesActivity.this, MainActivityLG.class));

    }

    private void uploadPhotoToFirebase(final Uri selectedImagePath, String city){

        if (selectedImagePath != null){

            final DocumentReference documentReference = mFirestore.collection("cities").document(city);
            final StorageReference ref = storageReference.child("cities/" + city);

            ref.putFile(selectedImagePath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    documentReference.update("image", uri.toString());
                                }
                            });
                        }
                    });

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                selectedImagePath = result.getUri();

                String city = preferences.getString("city", "");
                uploadPhotoToFirebase(selectedImagePath, city);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}