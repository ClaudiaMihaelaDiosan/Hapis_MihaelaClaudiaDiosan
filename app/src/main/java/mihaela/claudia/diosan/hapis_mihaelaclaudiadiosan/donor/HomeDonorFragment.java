package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.adapters.HomelessAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.logic.Homeless;

import static android.content.Context.MODE_PRIVATE;


public class HomeDonorFragment extends Fragment {



    private View view;

    private SharedPreferences preferences;

    private FirebaseFirestore mFirestore;

    private SearchView searchView;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_donor, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutDonor);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews();
        firebaseInit();
        buildRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            buildRecyclerView();
            new Handler().postDelayed(() -> {
                // Stop animation (This will be after 3 seconds)
                swipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.green)
        );

        return view;
    }


    private void initViews(){
        searchView = view.findViewById(R.id.donor_search);
        searchView.onActionViewExpanded();
        searchView.clearFocus();
    }

    private void firebaseInit(){
        mFirestore = FirebaseFirestore.getInstance();
    }


    private void buildRecyclerView(){

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view_donor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<Homeless> homelesses = new ArrayList<>();

        mFirestore.collection("homeless")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Log.d(TAG, document.getId() + " => " + document.getData());
                            String image = document.getString("image");
                            final String username = document.getString("homelessUsername");
                            final String birthday = document.getString("homelessBirthday");
                            final String lifeHistory = document.getString("homelessLifeHistory");
                            final String schedule = document.getString("homelessSchedule");
                            final String address = document.getString("homelessAddress");
                            String need = document.getString("homelessNeed");

                            final Homeless homeless = new Homeless(image, username, birthday, lifeHistory, address, schedule, need);
                            homelesses.add(homeless);
                            final HomelessAdapter homelessAdapter = new HomelessAdapter(homelesses);
                            searchText(homelessAdapter);
                            recyclerView.setAdapter(homelessAdapter);


                            recyclerView.setAdapter(homelessAdapter);
                            homelessAdapter.setOnItemClicklistener(position -> {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("homelessUsername",  homelesses.get(position).getHomelessUsername()).apply();

                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new HelpFragment())
                                        .addToBackStack(null).commit();
                            });

                        }
                    }
                });


    }


    private void searchText(final HomelessAdapter homelessAdapter){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homelessAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }

}
