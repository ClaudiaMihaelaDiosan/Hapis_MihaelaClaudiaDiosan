package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class PersonallyFragment extends Fragment implements OnMapReadyCallback {

    private SharedPreferences preferences;

    private TextView homelessLocation, homelessSchedule, homelessUsername;

    private FirebaseFirestore mFirestore;
    private String address, schedule, longitude, latitude;

    private GoogleMap mGoogleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personally, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);
        String username = preferences.getString("homelessUsername", "");

        initViews(view);
        firebaseInit();
        getHomelessInfo(username);

        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.homeless_location_map);
        mMapFragment.getMapAsync(this);

        return view;
    }

    private void initViews(View view){
        homelessLocation = view.findViewById(R.id.homeless_location);
        homelessSchedule = view.findViewById(R.id.homeless_schedule_text);
        homelessUsername = view.findViewById(R.id.homeless_username);

    }

    private void firebaseInit(){
        mFirestore = FirebaseFirestore.getInstance();

    }


    private void getHomelessInfo(final String username){
        DocumentReference documentReference = mFirestore.collection("homeless").document(username);

        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null){

                    address = documentSnapshot.getString("homelessAddress");
                    schedule = documentSnapshot.getString("homelessSchedule");

                    homelessLocation.setText(address);
                    homelessSchedule.setText(schedule);
                    homelessUsername.setText(username);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        String username = preferences.getString("homelessUsername", "");
        DocumentReference documentReference = mFirestore.collection("homeless").document(username);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null){

                    latitude = documentSnapshot.getString("homelessLatitude");
                    longitude = documentSnapshot.getString("homelessLongitude");

                    final LatLng position = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    final MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(position);

                    mGoogleMap.setOnMapLoadedCallback(() -> {
                        // Animating to the touched position
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 13.3f));
                        mGoogleMap.addMarker(markerOptions);

                    });

                }
            }
        });

    }
}
