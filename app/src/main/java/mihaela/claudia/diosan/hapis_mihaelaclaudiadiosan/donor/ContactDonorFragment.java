package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDonorFragment extends Fragment {

    View view;

    public ContactDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_donor, container, false);

        MaterialButton donorContactButton = view.findViewById(R.id.donor_contact_button);


        donorContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });

        return view;
    }

    public void showToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.contact_custom_toast,
                (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
        // get the reference of TextView and ImageVIew from inflated layout
        TextView toastTextView = (TextView) layout.findViewById(R.id.toastTextView);
        ImageView toastImageView = (ImageView) layout.findViewById(R.id.toastImageView);
        // set the text in the TextView
        toastTextView.setText(getString(R.string.contact_toast_message));
        // set the Image in the ImageView
        toastImageView.setImageResource(R.drawable.message_toast);
        // create a new Toast using context
        Toast toast = new Toast(getActivity());
        toast.setDuration(Toast.LENGTH_LONG); // set the duration for the Toast
        toast.setView(layout); // set the inflated layout
        toast.show(); // display the custom Toast
    }
}
