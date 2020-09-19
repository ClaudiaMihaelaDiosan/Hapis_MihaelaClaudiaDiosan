package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.adapters.DeliveryAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.logic.Delivery;

public class DeliveryFragment extends Fragment {

    private FirebaseFirestore mFirestore;
    private CollectionReference collectionReference;

    private View view;
    private DeliveryAdapter deliveryAdapter;

    private Map<String,String> donor = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_delivery, container, false);

        initFirebase();
        setUpRecyclerView();

        return view;
    }

    private void initFirebase(){
        mFirestore = FirebaseFirestore.getInstance();
        collectionReference = mFirestore.collection("throughVolunteerDonations");
    }


    private void setUpRecyclerView() {
        Query query = collectionReference.whereEqualTo("delivered", false);

        FirestoreRecyclerOptions<Delivery> options = new FirestoreRecyclerOptions.Builder<Delivery>()
                .setQuery(query, Delivery.class)
                .build();

        deliveryAdapter = new DeliveryAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_delivery);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(deliveryAdapter);

        deliveryAdapter.setOnItemClickListener(new DeliveryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, int position) {

                if (documentSnapshot.exists()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);

                    builder.setTitle(getString(R.string.delivery_done))
                            .setMessage(getString(R.string.delivery_message))
                            .setIcon(R.drawable.check_drawable)
                            .setPositiveButton(getString(R.string.confirm_button_delivery), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String donorEmail = documentSnapshot.getString("donorEmail");
                                    String donatesTo = documentSnapshot.getString("donatesTo");
                                    String donationType = documentSnapshot.getString("donationType");

                                    DocumentReference documentReference = mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + donatesTo + ":" + donationType);

                                    documentReference.update("delivered", true);
                                    showToast(getString(R.string.toast_delivery));

                                    Intent homeIntent = new Intent(getActivity(),HomeVolunteer.class );
                                    startActivity(homeIntent);
                                }
                            }).setNegativeButton(getString(R.string.negative_button_delivery), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.show();
                    alertDialog.setCanceledOnTouchOutside(false);

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        deliveryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        deliveryAdapter.stopListening();
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.GREEN);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_drawable, 0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }

}
