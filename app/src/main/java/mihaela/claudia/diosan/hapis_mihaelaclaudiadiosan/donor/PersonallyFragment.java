package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class PersonallyFragment extends Fragment  {

    private SharedPreferences preferences;

    private View view;

    /*TextViews*/
    private TextView homelessLocation;
    private TextView homelessSchedule;

    /*Maps*/
    private GoogleMap mGoogleMap;

    public PersonallyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personally, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews(view);

        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.homeless_location_map);
      //  mMapFragment.getMapAsync(this);

        return view;
    }

    private void initViews(View view){
        homelessLocation = view.findViewById(R.id.homeless_location);
        homelessSchedule = view.findViewById(R.id.homeless_schedule_text);

    }

  /*  public void getInfo(){
        Integer position = preferences.getInt("position", 0);
        if (position.equals(0)){
            String andrewLocation = preferences.getString("andrewLocation","");
            String andrewSchedule = preferences.getString("andrewSchedule","");


            homelessLocation.setText(andrewLocation);
            homelessSchedule.setText(andrewSchedule);




        }else  if (position.equals(1)){
            String mariaLocation = preferences.getString("mariaLocation","");
            String mariaSchedule = preferences.getString("mariaSchedule","");

            homelessLocation.setText(mariaLocation);
            homelessSchedule.setText(mariaSchedule);


        }else  if (position.equals(2)){

            String maiteLocation = preferences.getString("maiteLocation","");
            String maiteSchedule = preferences.getString("maiteSchedule","");

            homelessLocation.setText(maiteLocation);
            homelessSchedule.setText(maiteSchedule);


        }else  if (position.equals(3)){

            String luisLocation = preferences.getString("luisLocation","");
            String luisSchedule = preferences.getString("luisSchedule","");

            homelessLocation.setText(luisLocation);
            homelessSchedule.setText(luisSchedule);


        }else  if (position.equals(4)){

            String cristinaLocation = preferences.getString("cristinaLocation","");
            String cristinaSchedule = preferences.getString("cristinaSchedule","");

            homelessLocation.setText(cristinaLocation);
            homelessSchedule.setText(cristinaSchedule);


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Integer position = preferences.getInt("position", 0);
        if (position.equals(0)) {
            final MarkerOptions markerOptions = new MarkerOptions();
            String andrewLatitude = preferences.getString("andrewLatitude", "");
            String andrewLongitude = preferences.getString("andrewLongitude", "");
            final LatLng andrew = new LatLng(Double.parseDouble(andrewLatitude), Double.parseDouble(andrewLongitude));
            markerOptions.position(andrew);

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    // Animating to the touched position
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(andrew));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(andrew, 13.3f));
                    mGoogleMap.addMarker(markerOptions);

                }
            });

        }else if (position.equals(1)){
            final MarkerOptions markerOptions = new MarkerOptions();
            String mariaLatitude = preferences.getString("mariaLatitude", "");
            String mariaLongitude = preferences.getString("mariaLongitude", "");
            final LatLng maria = new LatLng(Double.parseDouble(mariaLatitude), Double.parseDouble(mariaLongitude));
            markerOptions.position(maria);

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    // Animating to the touched position
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(maria));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(maria, 13.3f));
                    mGoogleMap.addMarker(markerOptions);

                }
            });
        }else if (position.equals(2)){
            final MarkerOptions markerOptions = new MarkerOptions();
            String maiteLatitude = preferences.getString("maiteLatitude", "");
            String maiteLongitude = preferences.getString("maiteLongitude", "");
            final LatLng maite = new LatLng(Double.parseDouble(maiteLatitude), Double.parseDouble(maiteLongitude));
            markerOptions.position(maite);

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    // Animating to the touched position
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(maite));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(maite, 13.3f));
                    mGoogleMap.addMarker(markerOptions);

                }
            });
        }else if (position.equals(3)){
            final MarkerOptions markerOptions = new MarkerOptions();
            String luisLatitude = preferences.getString("luisLatitude", "");
            String luisLongitude = preferences.getString("luisLongitude", "");
            final LatLng luis = new LatLng(Double.parseDouble(luisLatitude), Double.parseDouble(luisLongitude));
            markerOptions.position(luis);

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    // Animating to the touched position
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(luis));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(luis, 13.3f));
                    mGoogleMap.addMarker(markerOptions);

                }
            });
        }else if (position.equals(4)){
            final MarkerOptions markerOptions = new MarkerOptions();
            String cristinaLatitude = preferences.getString("cristinaLatitude", "");
            String cristinaLongitude = preferences.getString("cristinaLongitude", "");
            final LatLng cristina = new LatLng(Double.parseDouble(cristinaLatitude), Double.parseDouble(cristinaLongitude));
            markerOptions.position(cristina);

            mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    // Animating to the touched position
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(cristina));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cristina, 13.3f));
                    mGoogleMap.addMarker(markerOptions);

                }
            });
        }
    }*/
}
