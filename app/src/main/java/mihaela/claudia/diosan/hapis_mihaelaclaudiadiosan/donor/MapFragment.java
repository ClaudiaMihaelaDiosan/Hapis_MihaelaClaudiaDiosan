package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.maps.OnMapAndViewReadyListener;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterDonorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener,
        GoogleMap.OnMarkerClickListener, OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnInfoWindowCloseListener {

    private GoogleMap mMap;

    private static final LatLng ANDREW = new LatLng(41.609769, 0.620776);

    private static final LatLng MARIA = new LatLng(41.611742, 0.628077);

    private static final LatLng MAITE = new LatLng(41.611704, 0.631876);

    private static final LatLng LUIS = new LatLng(41.620809, 0.628363);

    private static final LatLng CRISTINA = new LatLng(41.617109, 0.613393);

    View view;
    SupportMapFragment mMapFragment;
    private Marker myMarker;

    @Override
    public void onInfoWindowClick(Marker marker) {

//        String title = marker.getTitle();
//        String snippet = marker.getSnippet();
//
//        String text = title + "\n\n" + getString(R.string.here) + " " + snippet;
//
//
//        Toast toast = Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG);
//        View view =toast.getView();
//        view.setBackgroundColor( getResources().getColor(R.color.colorPrimaryDark));
//        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
//        toastMessage.setTextColor(Color.WHITE);
//        toastMessage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        toastMessage.setPadding(15,15,15,15);
//        toast.show();

    }



    @Override
    public void onInfoWindowClose(Marker marker) {
     // Toast.makeText(view.getContext(), "Close Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
       // Toast.makeText(view.getContext(), "Info Window long click", Toast.LENGTH_SHORT).show();
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.map_custom_bubble, null);
            mContents = getLayoutInflater().inflate(R.layout.map_custom_bubble_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }



        private void render(Marker marker, View view) {
            int badge;
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(Andrew)) {
                badge = R.drawable.andrew_image;
            } else if (marker.equals(Maria)) {
                badge = R.drawable.maria_image;
            } else if (marker.equals(Maite)) {
                badge = R.drawable.maite_image;
            } else if (marker.equals(Luis)) {
                badge = R.drawable.luis_image;
            } else if (marker.equals(Cristina)) {
                badge = R.drawable.cristina_image;
            } else {
                // Passing 0 to setImageResource will clear the image view.
                badge = 0;
            }
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }



        }
    }


    public void MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);

        // Get the map and register for the ready callback
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapAsync(this);

        return view;
    }



    @Override
    public boolean onMarkerClick(Marker marker) {

        int position = (int) (marker.getTag());

        if (marker.equals(position)){
            Toast.makeText(view.getContext(), "El marker llevará al fragment de la persona", Toast.LENGTH_SHORT).show();
        }



        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        mMap.setContentDescription("Map with lots of markers.");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(ANDREW)
                .include(MARIA)
                .include(MAITE)
                .include(LUIS)
                .include(CRISTINA)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 80));
    }

    private Marker Andrew;
    private Marker Maria;
    private Marker Maite;
    private Marker Luis;
    private Marker Cristina;
    private int position;

    private void addMarkersToMap(){
        //Colored icon
        Andrew =mMap.addMarker(new MarkerOptions()
                .position(ANDREW)
                .title("ANDREW")
                .snippet(getString(R.string.andrew_schedule))
                .icon(vectorToBitmap(R.drawable.men_icon, Color.parseColor("#2979ff"))));
        Andrew.setTag(position);

        Maria = mMap.addMarker(new MarkerOptions()
                .position(MARIA)
                .title("MARIA")
                .snippet(getString(R.string.maria_schedule))
                .icon(vectorToBitmap(R.drawable.women_icon, Color.parseColor("#F10000"))));
        Maria.setTag(position);

        Maite = mMap.addMarker(new MarkerOptions()
                .position(MAITE)
                .title("MAITE")
                .snippet(getString(R.string.maite_schedule))
                .icon(vectorToBitmap(R.drawable.women_icon, Color.parseColor("#F10000"))));
        Maite.setTag(position);

        Luis = mMap.addMarker(new MarkerOptions()
                .position(LUIS)
                .title("LUIS")
                .snippet(getString(R.string.luis_schedule))
                .icon(vectorToBitmap(R.drawable.men_icon, Color.parseColor("#2979ff"))));
        Luis.setTag(position);

        Cristina = mMap.addMarker(new MarkerOptions()
                .position(CRISTINA)
                .title("CRISTINA")
                .snippet(getString(R.string.cristina_schedule))
                .icon(vectorToBitmap(R.drawable.women_icon, Color.parseColor("#F10000"))));
        Cristina.setTag(position);

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

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(view.getContext(), R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}
