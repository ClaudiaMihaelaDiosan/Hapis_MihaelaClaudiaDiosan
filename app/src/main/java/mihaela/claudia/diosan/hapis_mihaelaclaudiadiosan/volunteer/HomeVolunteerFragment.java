package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.DonorAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

import static android.content.Context.MODE_PRIVATE;


public class HomeVolunteerFragment extends Fragment implements View.OnClickListener{


    private Dialog notificationDialog;
    private View view;


    private FloatingActionButton newHomelessProfile;
    private FloatingActionButton sendDeliveryNotification;

    private FirebaseFirestore mFirestore;

    private SharedPreferences preferences;

    private SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_volunteer, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews();
        firebaseInit();

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView(){

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<Homeless> homelesses = new ArrayList<>();

        mFirestore.collection("homeless")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String image = document.getString("image");
                                final String username = document.getString("homelessUsername");
                                final String phone = document.getString("homelessPhoneNumber");
                                final String birthday = document.getString("homelessBirthday");
                                final String lifeHistory = document.getString("homelessLifeHistory");
                                final String schedule = document.getString("homelessSchedule");
                                final String address = document.getString("homelessAddress");
                                String need = document.getString("homelessNeed");


                                final Homeless homeless = new Homeless(image, username, phone, birthday, lifeHistory, address, schedule, need);
                                homelesses.add(homeless);
                                final VolunteerAdapter volunteerAdapter = new VolunteerAdapter(homelesses);
                                searchText(volunteerAdapter);
                                recyclerView.setAdapter(volunteerAdapter);

                                recyclerView.setAdapter(volunteerAdapter);
                                volunteerAdapter.setOnItemClicklistener(new VolunteerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("homelessUsername",  homelesses.get(position).getHomelessUsername());
                                        editor.apply();

                                        EditHomelessFragment editHomelessFragment = new EditHomelessFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editHomelessFragment)
                                                .addToBackStack(null).commit();
                                    }
                                });
                            }
                        }
                    }
                });
    }

    private void searchText(final VolunteerAdapter volunteerAdapter){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                volunteerAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newHomelessProfile.setOnClickListener(this);
        sendDeliveryNotification.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_homeless_profile:
                Intent createAccountIntent = new Intent(getActivity(), CreateHomelessProfileActivity.class);
                startActivity(createAccountIntent);
                break;
            case R.id.send_delivery_notification:
                DeliveryFragment deliveryFragment = new DeliveryFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, deliveryFragment)
                        .addToBackStack(null).commit();
                break;
        }
    }

    private void initViews(){
        newHomelessProfile = view.findViewById(R.id.new_homeless_profile);
        sendDeliveryNotification = view.findViewById(R.id.send_delivery_notification);

        notificationDialog = new Dialog(getActivity());
        searchView = view.findViewById(R.id.volunteer_search);
        searchView.onActionViewExpanded();
        searchView.clearFocus();
    }

    private void firebaseInit(){
        mFirestore = FirebaseFirestore.getInstance();
    }


}
