package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class HelpFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences preferences;

    private Map<String,String> donor = new HashMap<>();

    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private ChipGroup chipGroup;

    private MaterialButton personallyBtn;
    private MaterialButton throughVolunteerBtn;
    private TextView helpType;

    private View view;
    private String need;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);
        initViews();

        firebaseInit();

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes final int checkedId) {
                // Handle the checked chip change.
                final Chip chip = chipGroup.findViewById(checkedId);
                if(chip != null){
                    uploadDataToFirebase(chip.getText().toString());
                    need = chip.getText().toString();
                  //  helpType.setVisibility(View.VISIBLE);
                    helpType.setText(need);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        personallyBtn.setOnClickListener(this);
        throughVolunteerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personally_button:
                if (helpType.getText().toString().isEmpty()){
                    showErrorToast(getString(R.string.error_chip));
                }else{
                    PersonallyFragment helpFragment = new PersonallyFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, helpFragment)
                            .addToBackStack(null).commit();
                }

                break;
            case R.id.through_volunteer_button:
                if (helpType.getText().toString().isEmpty()){
                    showErrorToast(getString(R.string.error_chip));
                }else {
                    ThroughVolunteerFragment throughVolunteerFragment = new ThroughVolunteerFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, throughVolunteerFragment)
                            .addToBackStack(null).commit();
                }
                break;
        }

    }


    public void showErrorToast(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_drawable, 0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }

    private void initViews(){
        chipGroup = (ChipGroup) view.findViewById(R.id.chip_group_donation);
        personallyBtn = view.findViewById(R.id.personally_button);
        throughVolunteerBtn = view.findViewById(R.id.through_volunteer_button);
        helpType = view.findViewById(R.id.help_type_donor);
    }


   private void firebaseInit(){
       mFirestore = FirebaseFirestore.getInstance();
       user = FirebaseAuth.getInstance().getCurrentUser();
   }



    private void uploadDataToFirebase(String donationType){

        String homelessUsername = preferences.getString("homelessUsername","");

        donor.put("donatesTo", homelessUsername);
        donor.put("donationType", donationType);


        mFirestore.collection("donors").document(user.getEmail()).set(donor, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //  successfullyUploadedInfoToast();
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
               Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

            }
        });
    }



}
