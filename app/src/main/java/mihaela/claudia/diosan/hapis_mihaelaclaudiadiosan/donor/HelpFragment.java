package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;

import static android.content.Context.MODE_PRIVATE;

public class HelpFragment extends Fragment implements View.OnClickListener {


    private SharedPreferences preferences;

    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private Map<String,String> personallyDonations = new HashMap<>();
    private Map<String,String> throughVolunteerDonations = new HashMap<>();

    private ChipGroup chipGroup;

    private MaterialButton personallyBtn, throughVolunteerBtn;

    private TextView homelessToHelp, helpType;

    private String need, homelessUsername;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_help, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);
        homelessUsername = preferences.getString("homelessUsername","");

        initViews();
        firebaseInit();

        homelessToHelp.setText(homelessUsername);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        personallyBtn.setOnClickListener(this);
        throughVolunteerBtn.setOnClickListener(this);

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {

            final Chip chip = chipGroup.findViewById(checkedId);
            if(chip != null){
                need = chip.getText().toString();
                helpType.setText(need);
            }

        });

    }


    private void initViews(){
        chipGroup = view.findViewById(R.id.chip_group_donation);
        personallyBtn = view.findViewById(R.id.personally_button);
        throughVolunteerBtn = view.findViewById(R.id.through_volunteer_button);
        helpType = view.findViewById(R.id.help_type_donor);
        homelessToHelp = view.findViewById(R.id.username_to_help);
    }


   private void firebaseInit(){
       mFirestore = FirebaseFirestore.getInstance();
       user = FirebaseAuth.getInstance().getCurrentUser();
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personally_button:
                if (helpType.getText().toString().isEmpty()){
                    HelpActivity.showErrorToast(getActivity(), getString(R.string.error_chip));
                }else{
                    uploadPersonallyDonationToFirebase(helpType.getText().toString(), homelessUsername);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new PersonallyFragment())
                            .addToBackStack(null).commit();
                }

                break;
            case R.id.through_volunteer_button:
                if (helpType.getText().toString().isEmpty()){
                    HelpActivity.showErrorToast(getActivity(), getString(R.string.error_chip));
                }else {
                    uploadThroughVolunteerDonationToFirebase(helpType.getText().toString(), homelessUsername);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new ThroughVolunteerFragment())
                            .addToBackStack(null).commit();
                }
                break;
        }

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
