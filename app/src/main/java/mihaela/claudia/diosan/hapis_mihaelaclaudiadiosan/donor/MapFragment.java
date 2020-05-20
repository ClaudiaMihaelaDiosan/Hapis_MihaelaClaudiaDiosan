package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.maps.OnMapAndViewReadyListener;




public class MapFragment extends Fragment implements OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener, OnMapReadyCallback {

    private GoogleMap mMap;


    private FirebaseFirestore mFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        firebaseInit();

        // Get the map and register for the ready callback
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapAsync(this);

        return view;
    }


    private void firebaseInit() {
        mFirestore = FirebaseFirestore.getInstance();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(true);

        addMarkersToMap();

        mFirestore.collection("homeless")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                             if (task.isSuccessful()){
                                 String latitude = document.getString("homelessLatitude");
                                 String longitude = document.getString("homelessLongitude");
                                 final LatLng position = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                                 LatLngBounds bounds = new LatLngBounds.Builder()
                                         .include(position)
                                         .build();


                                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14));
                             }

                            }
                        }
                    }
                });
    }


    private void addMarkersToMap() {
        //Colored icon
        mFirestore.collection("homeless")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String latitude = document.getString("homelessLatitude");
                                String longitude = document.getString("homelessLongitude");
                                String username = document.getString("homelessUsername");
                                String schedule = document.getString("homelessSchedule");

                                final LatLng position = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                                mMap.addMarker(new MarkerOptions()
                                        .position(position)
                                        .title(username)
                                        .snippet(getString(R.string.here) + " " + schedule)
                                        .icon(vectorToBitmap(R.drawable.ic_person_pin_circle_black_24dp, Color.parseColor("#F10000"))));
                            }
                        }
                    }
                });


    }

    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



}
