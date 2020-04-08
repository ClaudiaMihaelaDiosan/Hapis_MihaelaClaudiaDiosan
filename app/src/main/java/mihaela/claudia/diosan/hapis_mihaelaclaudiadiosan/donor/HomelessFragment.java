package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomelessFragment extends Fragment {

    MaterialButton wantHelpBtn;
    View view;

    public HomelessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homeless, container, false);

        wantHelpBtn = view.findViewById(R.id.want_help_button);

        wantHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 HelpFragment helpFragment = new HelpFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, helpFragment)
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }
}
