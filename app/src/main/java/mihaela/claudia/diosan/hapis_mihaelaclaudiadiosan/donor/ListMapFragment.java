package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMapFragment extends Fragment  {

    View view;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    public ListMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_map, container, false);

        setHasOptionsMenu(true);

        mGridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        mLinearLayoutManager = new LinearLayoutManager(view.getContext());

        // Set up the RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_view_list_map);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(new MapAdapter(LIST_LOCATIONS));
        mRecyclerView.setRecyclerListener(mRecycleListener);

        return view;
    }

    /** Create a menu to switch between Linear and Grid LayoutManager. */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.layout_linear:
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                break;
            case R.id.layout_grid:
                mRecyclerView.setLayoutManager(mGridLayoutManager);
                break;
        }
        return true;
    }

    private class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {

        private NamedLocation[] namedLocations;

        private MapAdapter(NamedLocation[] locations) {
            super();
            namedLocations = locations;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_list_map_row, parent, false));
        }

        /**
         * This function is called when the user scrolls through the screen and a new item needs
         * to be shown. So we will need to bind the holder with the details of the next item.
         */

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder == null) {
                return;
            }
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return namedLocations.length;
        }


        class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

            MapView mapView;
            TextView title;
            GoogleMap map;
            View layout;

            private ViewHolder(View itemView) {
                super(itemView);
                layout = itemView;
                mapView = layout.findViewById(R.id.lite_list_row_map);
                title = layout.findViewById(R.id.lite_list_row_text);
                if (mapView != null) {
                    // Initialise the MapView
                    mapView.onCreate(null);
                    mapView.onResume();
                    // Set the map ready callback to receive the GoogleMap object
                    mapView.getMapAsync(this);
                    mapView.onPause();




                }
            }

            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapsInitializer.initialize(view.getContext());
                map = googleMap;
                map.animateCamera(CameraUpdateFactory.zoomTo(10),  2000, null);
                setMapLocation();

            }


            private void setMapLocation() {
                if (map == null) return;

                NamedLocation data = (NamedLocation) mapView.getTag();
                if (data == null) return;

                // Add a marker for this item and set the camera
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f));
                map.addMarker(new MarkerOptions().position(data.location));

                // Set the map type back to normal.
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }

            private void bindView(int pos) {
                NamedLocation item = namedLocations[pos];
                // Store a reference of the ViewHolder object in the layout.
                layout.setTag(this);
                // Store a reference to the item in the mapView's tag. We use it to get the
                // coordinate of a location, when setting the map location.
                mapView.setTag(item);
                setMapLocation();
                title.setText(item.name);
            }
        }
    }



    private RecyclerView.RecyclerListener mRecycleListener = new RecyclerView.RecyclerListener() {

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            MapAdapter.ViewHolder mapHolder = (MapAdapter.ViewHolder) holder;
            if (mapHolder != null && mapHolder.map != null) {
                // Clear the map and free up resources by changing the map type to none.
                // Also reset the map when it gets reattached to layout, so the previous map would
                // not be displayed.
                mapHolder.map.clear();
                mapHolder.map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        }
    };


    /**
     * Location represented by a position ({@link com.google.android.gms.maps.model.LatLng} and a
     * name ({@link java.lang.String}).
     */
    private static class NamedLocation {

        public final String name;
        public final LatLng location;

        NamedLocation(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }
    }

    /**
     * A list of locations to show in this ListView.
     */

    private static final NamedLocation[] LIST_LOCATIONS = new NamedLocation[]{
            new NamedLocation("ANDREW", new LatLng(41.609769, 0.620776)),
            new NamedLocation("MARIA", new LatLng(41.611742, 0.628077)),
            new NamedLocation("MAITE", new LatLng(41.611704, 0.631876)),
            new NamedLocation("LUIS", new LatLng(41.620809, 0.628363)),
            new NamedLocation("CRISTINA", new LatLng(41.617109, 0.613393))
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
