package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;

import static android.content.Context.MODE_PRIVATE;


public class ThroughVolunteerFragment extends Fragment implements View.OnClickListener {


    /*Views*/
    private View view;

    /*Autocomplete places*/
    private List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

    /*TextViews*/
    private TextView selectedDateDonor;
    private TextView selectedTimeDonor;
    private TextView locationDonor;

    /*Buttons*/
    private MaterialButton datePickerBtn;
    private MaterialButton timePickerBtn;
    private MaterialButton confirmBtn;

    private DatePickerDialog.OnDateSetListener setListener;

    /*Firebase*/
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;

    private Map<String,String> throughVolunteerDonations = new HashMap<>();
    private Map<String,Boolean> delivered = new HashMap<>();

    /*SharedPreferences*/
    private SharedPreferences preferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_through_volunteer, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews(view);
        firebaseInit();

        initPlaces();
        setupPlaceAutoComplete();
        setTextDateListener();

        if (savedInstanceState != null){
            locationDonor.setText(savedInstanceState.getString("location"));
            selectedDateDonor.setText(savedInstanceState.getString("date"));
            selectedTimeDonor.setText(savedInstanceState.getString("time"));
        }
        return view;
    }

    private void initViews(View view) {
        locationDonor = view.findViewById(R.id.selected_location_donor);
        datePickerBtn = view.findViewById(R.id.date_picker_donor);
        timePickerBtn = view.findViewById(R.id.time_picker_donor);

        selectedDateDonor = view.findViewById(R.id.selected_date_donor);
        selectedTimeDonor = view.findViewById(R.id.selected_time_donor);

        confirmBtn = view.findViewById(R.id.donor_confirm_button);
    }

    private void firebaseInit(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datePickerBtn.setOnClickListener(this);
        timePickerBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_picker_donor:
                selectDate();
                break;
            case R.id.time_picker_donor:
                selectTime();
                break;
            case R.id.donor_confirm_button:
                if (isValidForm()){
                    uploadDataToFirebase();
                    HelpActivity.showSuccessToast(getActivity(),getString(R.string.fr_tv_confirm_toast));
                    startActivity(new Intent(getContext(), HomeDonor.class));
                }
                break;
        }

    }

    private void uploadDataToFirebase(){

        String donorEmail = user.getEmail();
        String homelessUsername = preferences.getString("homelessUsername", "");
        String donationType = preferences.getString("donationType", "");
        String title = "DONACIÓN ENTREGADA";
        String content = "Tu donación de" + " " + donationType + " para " + homelessUsername + " ha sido entregada!\n Gracias por tu ayuda!";

        addDonorData(donorEmail, homelessUsername, donationType);
        addVolunteerData(donorEmail, homelessUsername, donationType);

        throughVolunteerDonations.put("donationLocation", locationDonor.getText().toString());
        throughVolunteerDonations.put("donationHour", selectedTimeDonor.getText().toString());
        throughVolunteerDonations.put("donationDate", selectedDateDonor.getText().toString());
        throughVolunteerDonations.put("donorEmail", donorEmail);
        throughVolunteerDonations.put("title", title);
        throughVolunteerDonations.put("content", content);
        delivered.put("delivered", false);

        mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + homelessUsername + ":" + donationType).set(throughVolunteerDonations, SetOptions.merge());
        mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + homelessUsername + ":" + donationType).set(delivered, SetOptions.merge());
    }

    private void addDonorData(final String donorEmail, final String homelessUsername,final String donationType){
        mFirestore.collection("donors").document(user.getEmail()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null){
                    String donorUsername = documentSnapshot.getString("username");
                    String donorPhone = documentSnapshot.getString("phone");
                    throughVolunteerDonations.put("donorUsername",donorUsername);
                    throughVolunteerDonations.put("donorPhone", donorPhone);
                    mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + homelessUsername + ":" + donationType).set(throughVolunteerDonations, SetOptions.merge());

                }
            }
        });
    }

    private void addVolunteerData(final String donorEmail, final String homelessUsername,final String donationType){
        mFirestore.collection("homeless").document(homelessUsername).get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        String volunteerEmail = documentSnapshot.getString("volunteerEmail");
                        throughVolunteerDonations.put("volunteerEmail", volunteerEmail);
                        mFirestore.collection("throughVolunteerDonations").document(donorEmail + "->" + homelessUsername + ":" + donationType).set(throughVolunteerDonations, SetOptions.merge());
                    }
                });
    }

    private boolean isValidForm(){
        if (selectedDateDonor.getText().toString().equals(getString(R.string.fr_tv_date))){
            HelpActivity.showErrorToast( getActivity(),getString(R.string.date_error_toast));
            return false;
        }else if (selectedTimeDonor.getText().toString().equals(getString(R.string.fr_tv_hour))){
            HelpActivity.showErrorToast(getActivity(),getString(R.string.time_error_toast));
            return false;
        }else if (locationDonor.getText().toString().equals(getString(R.string.fr_tv_location))){
            HelpActivity.showErrorToast( getActivity(),getString(R.string.location_error_toast));
            return false;
        }
        return true;
    }

    private void initPlaces() {
        Places.initialize(view.getContext(), getString(R.string.API_KEY));
    }

    private void setupPlaceAutoComplete(){
        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_donor);
        assert autocompleteSupportFragment != null;
        autocompleteSupportFragment.setPlaceFields(placeFields);
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull final Place place) {

                if (place.getLatLng() != null) {

                    locationDonor.setText(place.getAddress());
                }
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(view.getContext(), ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectDate(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), setListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    private void setTextDateListener(){
        setListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            selectedDateDonor.setText(date);
        };
    }

    private void selectTime(){
       final Calendar calendar = Calendar.getInstance();
       final int hour = calendar.get(Calendar.HOUR_OF_DAY);
       final int minute = calendar.get(Calendar.MINUTE);

       TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), (view, hourOfDay, minute1) -> {
           String time = hourOfDay + ":" + minute1 + "h";
           selectedTimeDonor.setText(time);
       }, hour, minute, false);
        timePickerDialog.show();
    }

}
