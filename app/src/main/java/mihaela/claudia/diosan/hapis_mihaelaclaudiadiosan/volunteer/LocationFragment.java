package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.maps.OnMapAndViewReadyListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment  implements  OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener, OnMapReadyCallback {


    View view;
    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    AutocompleteSupportFragment autocompleteSupportFragment;
    TextView selectedLocationTV;
    TextView textLocation;

    GoogleMap mGoogleMap;
    SupportMapFragment mMapFragment;
    Double latitude;
    Double longitude;
    LatLng MARKER;

    SharedPreferences preferences;

    MaterialButton cancelBtn;
    MaterialButton saveBtn;



    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location, container, false);

        // Get the map and register for the ready callback
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        selectedLocationTV = view.findViewById(R.id.selected_location_tv);
        textLocation = view.findViewById(R.id.selected_location_text);
        cancelBtn = view.findViewById(R.id.cancelLocationBtn);
        saveBtn = view.findViewById(R.id.saveLocationButton);




        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initPlaces();
        setupPlaceAutoComplete();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(),HomeVolunteer.class );
                startActivity(homeIntent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedLocationTV.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), getString(R.string.toast_save_btn), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), getString(R.string.location_error_toast), Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    private void initPlaces    (){
        Places.initialize(view.getContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(view.getContext());
    }

    public void setupPlaceAutoComplete(){
        autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autocompleteSupportFragment != null;
        autocompleteSupportFragment.setPlaceFields(placeFields);
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull final Place place) {

                if (place.getLatLng() != null) {
                    textLocation.setVisibility(View.VISIBLE);
                    latitude = place.getLatLng().latitude;
                    longitude = place.getLatLng().longitude;
                    String name = place.getName();

                    String homelessAddress = place.getAddress();
                    String homelessLatitude = latitude.toString();
                    String homelessLongitude = longitude.toString();

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("homelessAddress", homelessAddress);
                    editor.putString("homelessLatitude", homelessLatitude);
                    editor.putString("homelessLongitude",homelessLongitude );
                    editor.apply();

                    selectedLocationTV.setText(place.getAddress());
                    // Creating a marker
                    final MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(place.getLatLng());

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(name);

                    // Clears the previously touched position
                    mGoogleMap.clear();


                    mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            // Animating to the touched position
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 13.3f));

                            // Placing a marker on the touched position
                            mGoogleMap.addMarker(markerOptions);
                        }
                    });

                }

            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(view.getContext(), ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }


}
