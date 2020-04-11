package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterDonorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThroughVolunteerFragment extends Fragment {


    View view;
    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    AutocompleteSupportFragment autocompleteSupportFragment;
    TextView locationDonor;

    MaterialButton datePickerBtn;
    MaterialButton timePickerBtn;

    TextView selectedDateDonor;
    TextView selectedTimeDonor;

    DatePickerDialog.OnDateSetListener setListener;

    MaterialButton confirmBtn;

    public ThroughVolunteerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_through_volunteer, container, false);


        locationDonor = view.findViewById(R.id.selected_location_donor);
        datePickerBtn = view.findViewById(R.id.date_picker_donor);
        timePickerBtn = view.findViewById(R.id.time_picker_donor);

        selectedDateDonor = view.findViewById(R.id.selected_date_donor);
        selectedTimeDonor = view.findViewById(R.id.selected_time_donor);

        confirmBtn = view.findViewById(R.id.donor_confirm_button);

        initPlaces();
        setupPlaceAutoComplete();

            datePickerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDate();

                }

            });

            setTextDateListener();

            timePickerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTime();
                }
            });

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectedDateDonor.getText().toString().equals(getString(R.string.fr_tv_date))){
                        Toast.makeText(view.getContext(), getString(R.string.date_error_toast), Toast.LENGTH_SHORT).show();
                    }else if (selectedTimeDonor.getText().toString().equals(getString(R.string.fr_tv_hour))){
                        Toast.makeText(view.getContext(), getString(R.string.time_error_toast), Toast.LENGTH_SHORT).show();
                    }else if (locationDonor.getText().toString().equals(getString(R.string.fr_tv_location))){
                        Toast.makeText(view.getContext(), getString(R.string.location_error_toast), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(), getString(R.string.fr_tv_confirm_toast), Toast.LENGTH_SHORT).show();
                    }
                }
            });




        return view;
    }


    private void initPlaces() {
        Places.initialize(view.getContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(view.getContext());
    }

    public void setupPlaceAutoComplete(){
        autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_donor);
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

    public void selectDate(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), setListener, year, month, day);
      //  Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }


    public void setTextDateListener(){
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                selectedDateDonor.setText(date);
            }
        };
    }

    public void selectTime(){
       final Calendar calendar = Calendar.getInstance();
       final int hour = calendar.get(Calendar.HOUR_OF_DAY);
       final int minute = calendar.get(Calendar.MINUTE);

       TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               String time = hourOfDay + ":" + minute + "h";
               selectedTimeDonor.setText(time);
           }
       }, hour, minute, false);
        timePickerDialog.show();
    }




}
