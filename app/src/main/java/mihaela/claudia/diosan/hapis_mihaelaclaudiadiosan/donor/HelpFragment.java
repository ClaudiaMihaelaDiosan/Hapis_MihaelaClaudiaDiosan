package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class HelpFragment extends Fragment implements View.OnClickListener {

    /*SharedPreferences*/
    private SharedPreferences preferences;

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;
    private Map<String,String> personallyDonations = new HashMap<>();
    private Map<String,String> throughVolunteerDonations = new HashMap<>();

    /*Buttons*/

    private MaterialButton personallyBtn;
    private MaterialButton throughVolunteerBtn;

    private ChipGroup chipGroup;
    private TextView helpType;

    private String need;
    private String homelessUsername;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);
        homelessUsername = preferences.getString("homelessUsername","");

        initViews();
        firebaseInit();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        personallyBtn.setOnClickListener(this);
        throughVolunteerBtn.setOnClickListener(this);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes final int checkedId) {

                final Chip chip = chipGroup.findViewById(checkedId);
                if(chip != null){
                    need = chip.getText().toString();
                    helpType.setText(need);
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personally_button:
                if (helpType.getText().toString().isEmpty()){
                    showErrorToast(getString(R.string.error_chip));
                }else{
                    uploadPersonallyDonationToFirebase(helpType.getText().toString(), homelessUsername);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new PersonallyFragment())
                            .addToBackStack(null).commit();
                }

                break;
            case R.id.through_volunteer_button:
                if (helpType.getText().toString().isEmpty()){
                    showErrorToast(getString(R.string.error_chip));
                }else {
                    uploadThroughVolunteerDonationToFirebase(helpType.getText().toString(), homelessUsername);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new ThroughVolunteerFragment())
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



    private void uploadPersonallyDonationToFirebase(String donationType, String homelessUsername){

        personallyDonations.put("donatesTo", homelessUsername);
        personallyDonations.put("donationType", donationType);

        mFirestore.collection("personallyDonations").document(user.getEmail() + "->" + homelessUsername + ":" + donationType).set(personallyDonations, SetOptions.merge());
    }


    private void uploadThroughVolunteerDonationToFirebase(String donationType, String homelessUsername){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homelessUsername", homelessUsername);
        editor.putString("donationType", donationType);
        editor.apply();

        throughVolunteerDonations.put("donatesTo", homelessUsername);
        throughVolunteerDonations.put("donationType", donationType);

        mFirestore.collection("throughVolunteerDonations").document(user.getEmail() + "->" + homelessUsername + ":" + donationType).set(throughVolunteerDonations, SetOptions.merge());

    }

}
