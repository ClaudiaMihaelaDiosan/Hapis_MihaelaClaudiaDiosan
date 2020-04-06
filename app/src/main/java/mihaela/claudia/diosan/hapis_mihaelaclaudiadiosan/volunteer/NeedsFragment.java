package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedsFragment extends Fragment {

    View view;
    TextView needTV;
    TextView needText;

    public NeedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_needs, container, false);

        needTV = view.findViewById(R.id.most_important_neev_tv);
        needText = view.findViewById(R.id.most_important_need);

        final ChipGroup chipGroup = (ChipGroup) view.findViewById(R.id.chip_group);

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

        return view;
    }
}
