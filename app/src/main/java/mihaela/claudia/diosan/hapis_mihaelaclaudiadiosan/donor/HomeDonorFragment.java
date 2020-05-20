package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomelessAdapter;

import static android.content.Context.MODE_PRIVATE;


public class HomeDonorFragment extends Fragment {

    private View view;


    private SharedPreferences preferences;

    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private CollectionReference homelessRef;
    private DonorAdapter donorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_donor, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        firebaseInit();
        buildRecyclerView();

        return view;
    }

    public void buildRecyclerView(){
        homelessRef = mFirestore.collection("homeless");

        FirestoreRecyclerOptions<Homeless> options = new FirestoreRecyclerOptions.Builder<Homeless>()
                .setQuery(homelessRef, Homeless.class)
                .build();

        donorAdapter = new DonorAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_donor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(donorAdapter);

       donorAdapter.setOnItemClickListener(new DonorAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
               String username =  documentSnapshot.getString("homelessUsername");
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("homelessUsername", username);
               editor.apply();

               HomelessFragment homelessFragment = new HomelessFragment();
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, homelessFragment)
                       .addToBackStack(null).commit();

           }
       });

    }



    @Override
    public void onStart() {
        super.onStart();
        donorAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        donorAdapter.stopListening();
    }


    private void firebaseInit(){
        mFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

}
