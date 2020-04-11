package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedsFragment extends Fragment {

    View view;
    TextView needTV;
    TextView needText;

    SharedPreferences preferences;

    TextInputEditText scheduleEditText;
    MaterialButton cancelScheduleBtn;
    MaterialButton saveScheduleBtn;

    String homelessSchedule;
    String homelessNeed;


    public NeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_needs, container, false);

        scheduleEditText = view.findViewById(R.id.homeless_schedule_edit_text);

        needTV = view.findViewById(R.id.most_important_neev_tv);
        needText = view.findViewById(R.id.most_important_need);
        cancelScheduleBtn = view.findViewById(R.id.cancelScheduleButton);
        saveScheduleBtn = view.findViewById(R.id.saveScheduleButton);

        final ChipGroup chipGroup = (ChipGroup) view.findViewById(R.id.chip_group);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {
                // Handle the checked chip change.
                Chip chip = chipGroup.findViewById(checkedId);
                if(chip != null){
                    needTV.setVisibility(View.VISIBLE);
                    needText.setText(chip.getText().toString());
                }

            }
        });

        cancelScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(),HomeVolunteer.class );
                startActivity(homeIntent);
            }
        });

        saveScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (!scheduleEditText.getText().toString().isEmpty() && !needText.getText().toString().isEmpty()){
                    getInfo();
                    Toast.makeText(getActivity(),getString(R.string.toast_save_btn), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    public void validate(){
        if (scheduleEditText.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),getString(R.string.complete_schedule_toast), Toast.LENGTH_SHORT).show();
        }

        if (needText.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),getString(R.string.complete_need_toast), Toast.LENGTH_SHORT).show();
        }
    }

    public void getInfo(){
        homelessSchedule = scheduleEditText.getText().toString();
        homelessNeed = needText.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homelessSchedule", homelessSchedule);
        editor.putString("homelessNeed", homelessNeed);
        editor.apply();
    }



}
