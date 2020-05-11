package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        chipGroup(view);
        onClickButtons(view);

        return view;
    }



    private void chipGroup(final View view){
       final ChipGroup chipGroup = (ChipGroup) view.findViewById(R.id.chip_group_donation);
       chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {
               // Handle the checked chip change.
               Chip chip = chipGroup.findViewById(checkedId);
               if(chip != null){
                   Toast.makeText(view.getContext(), getString(R.string.chip_text1) + "  " + chip.getText().toString() + "  " + getString(R.string.chip_text2),Toast.LENGTH_SHORT).show();

               }

           }
       });
   }

    private void onClickButtons(View view) {
        /*Buttons*/
        MaterialButton personallyBtn = view.findViewById(R.id.personally_button);
        MaterialButton throughVolunteerBtn = view.findViewById(R.id.through_volunteer_button);

        personallyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonallyFragment helpFragment = new PersonallyFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, helpFragment)
                        .addToBackStack(null).commit();
            }
        });

        throughVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThroughVolunteerFragment helpFragment = new ThroughVolunteerFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, helpFragment)
                        .addToBackStack(null).commit();
            }
        });
    }
}
