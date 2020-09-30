package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.adapters.DeliveryAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.logic.Delivery;

public class DeliveryFragment extends Fragment {

    private FirebaseFirestore mFirestore;
    FirebaseUser user;
    private CollectionReference collectionReference;

    private View view;
    private DeliveryAdapter deliveryAdapter;

    /*SearchView bar*/
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_delivery, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        String visualizationPrefs = preferences.getString("visualization", "all");

        searchView = view.findViewById(R.id.delivery_search);
        searchView.onActionViewExpanded();
        searchView.clearFocus();

        initFirebase();

        if (visualizationPrefs.equals("all")){
            Query query_all = collectionReference.whereEqualTo("delivered", false);
            setUpRecyclerView(query_all);
        }else {
            String email = user.getEmail();
            Query query_mine = collectionReference.whereEqualTo("delivered", false).whereEqualTo("volunteerEmail", email);
            setUpRecyclerView(query_mine);
        }

        return view;
    }

    private void initFirebase(){
        mFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        collectionReference = mFirestore.collection("throughVolunteerDonations");
    }


    private void setUpRecyclerView(Query query) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_delivery);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(deliveryAdapter);

        final List<Delivery> deliveryList = new ArrayList<>();
        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            final String donorUsername = document.getString("donorUsername");
                            final String donationType = document.getString("donationType");
                            final String donatesTo = document.getString("donatesTo");
                            final String donationLocation = document.getString("donationLocation");
                            final String donationDate = document.getString("donationDate");
                            final String donationHour = document.getString("donationHour");
                            final String donorEmail = document.getString("donorEmail");
                            final String donorPhone = document.getString("donorPhone");

                            final Delivery delivery = new Delivery(donatesTo, donorUsername, donorEmail, donorPhone, donationType, donationLocation, donationHour, donationDate);
                            deliveryList.add(delivery);

                            final DeliveryAdapter deliveryAdapter = new DeliveryAdapter(deliveryList);
                            searchText(deliveryAdapter);
                            recyclerView.setAdapter(deliveryAdapter);


                            deliveryAdapter.setOnItemClickListener(new DeliveryAdapter.OnItemClickListener() {
                                @Override
                                public void onContactedClick(int position) {
                                  contactedClick(deliveryList, position);
                                }

                                @Override
                                public void onDeliveredClick(int position) {
                                deliveredClick(deliveryList, position);
                                }

                                @Override
                                public void onCancelDeliverClick(int position) {
                                   canceledClick(deliveryList, position);
                                }
                            });
                        }
                    }
                });
    }

    private void searchText(final DeliveryAdapter deliveryAdapter){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                deliveryAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }


    private void contactedClick(List<Delivery> deliveryList, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        builder.setTitle(getString(R.string.contacted_done))
                .setMessage(getString(R.string.contacted_message))
                .setIcon(R.drawable.check_drawable)
                .setPositiveButton(getString(R.string.confirm_button_delivery), (dialog, which) -> {

                    String donorEmail = deliveryList.get(position).getDonorEmail();
                    String donatesTo = deliveryList.get(position).getDonatesTo();
                    String donationType = deliveryList.get(position).getDonationType();

                    DocumentReference documentReference = mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + donatesTo + ":" + donationType);
                    documentReference.update("contacted", true);

                    HelpActivity.showSuccessToast(getActivity(), getString(R.string.toast_delivery));
                    Intent homeIntent = new Intent(getActivity(), HomeVolunteer.class);
                    startActivity(homeIntent);
                }).setNegativeButton(getString(R.string.neutral_button_delivery), (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void deliveredClick(List<Delivery> deliveryList, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        builder.setTitle(getString(R.string.delivery_done))
                .setMessage(getString(R.string.delivery_message))
                .setIcon(R.drawable.check_drawable)
                .setPositiveButton(getString(R.string.confirm_button_delivery), (dialog, which) -> {
                    String donorEmail = deliveryList.get(position).getDonorEmail();
                    String donatesTo = deliveryList.get(position).getDonatesTo();
                    String donationType = deliveryList.get(position).getDonationType();

                    DocumentReference documentReference = mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + donatesTo + ":" + donationType);

                    documentReference.update("delivered", true);
                    HelpActivity.showSuccessToast(getActivity(), getString(R.string.toast_delivery));

                    Intent homeIntent = new Intent(getActivity(), HomeVolunteer.class);
                    startActivity(homeIntent);
                }).setNegativeButton(getString(R.string.negative_button_delivery), (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void canceledClick(List<Delivery> deliveryList, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        builder.setTitle(getString(R.string.cancel_deliver_title))
                .setMessage(getString(R.string.cancel_deliver_body))
                .setIcon(R.drawable.check_drawable)
                .setPositiveButton(getString(R.string.cancel_deliver_positive_btn), (dialog, which) -> {
                    String donorEmail = deliveryList.get(position).getDonorEmail();
                    String donatesTo = deliveryList.get(position).getDonatesTo();
                    String donationType = deliveryList.get(position).getDonationType();

                    DocumentReference documentReference = mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + donatesTo + ":" + donationType);

                    documentReference.update("contacted", false);
                    HelpActivity.showSuccessToast(getActivity(), getString(R.string.toast_delivery));

                    Intent homeIntent = new Intent(getActivity(), HomeVolunteer.class);
                    startActivity(homeIntent);
                }).setNegativeButton(getString(R.string.cancel_deliver_negative_btn), (dialog, which) -> {
                    String donorEmail = deliveryList.get(position).getDonorEmail();
                    String donatesTo = deliveryList.get(position).getDonatesTo();
                    String donationType = deliveryList.get(position).getDonationType();

                    mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + donatesTo + ":" + donationType)
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                HelpActivity.showSuccessToast(getActivity(), getString(R.string.toast_delivery));
                                Intent homeIntent = new Intent(getActivity(), HomeVolunteer.class);
                                startActivity(homeIntent);
                            })
                            .addOnFailureListener(e -> {
                                //   Log.w(TAG, "Error deleting document", e);
                            });
                }).setNeutralButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}
