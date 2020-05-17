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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

import static android.content.Context.MODE_PRIVATE;


public class HomeVolunteerFragment extends Fragment implements View.OnClickListener{


    private Dialog notificationDialog;
    private View view;


    private FloatingActionButton newHomelessProfile;
    private FloatingActionButton sendDeliveryNotification;

    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private CollectionReference homelessRef;
    private HomelesAdapter homelesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_volunteer, container, false);

        initViews();
       /* editAndDeleteProfile();*/
        firebaseInit();

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView(){

        homelessRef = mFirestore.collection("homeless");
        Query query = homelessRef.whereEqualTo("volunteerEmail",user.getEmail());


        FirestoreRecyclerOptions<Homeless> options = new FirestoreRecyclerOptions.Builder<Homeless>()
                .setQuery(query, Homeless.class)
                .build();

        homelesAdapter = new HomelesAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(homelesAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        homelesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        homelesAdapter.stopListening();
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
                sendNotificationDialog();
                break;
        }
    }

    private void initViews(){
        newHomelessProfile = view.findViewById(R.id.new_homeless_profile);
        sendDeliveryNotification = view.findViewById(R.id.send_delivery_notification);

        notificationDialog = new Dialog(getActivity());
    }

    private void firebaseInit(){
        mFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

/*    private void editAndDeleteProfile(){
        editHomelessListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(getActivity(), getString(R.string.edit_button_toast), Toast.LENGTH_LONG).show();
            }
        };

        deleteHomelessListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.delete_button_toast), Toast.LENGTH_LONG).show();

            }
        };
    }*/


    private void sendNotificationDialog(){
        notificationDialog.setContentView(R.layout.send_delivery_notification);
        MaterialButton cancelNotification = (MaterialButton) notificationDialog.findViewById(R.id.delivery_close_button);
        MaterialButton sendNotification = (MaterialButton) notificationDialog.findViewById(R.id.send_notification_button);

        cancelNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationDialog.dismiss();
            }
        });

        notificationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        notificationDialog.show();

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),  getString(R.string.delivery_send_toast), Toast.LENGTH_LONG).show();
            }
        });
    }


}
