package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDonorFragment extends Fragment {

    View view;

    private TextInputEditText donorEmail;

    public ContactDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_donor, container, false);

        MaterialButton donorContactButton = view.findViewById(R.id.donor_contact_button);
        donorEmail = view.findViewById(R.id.dcf_email);


        donorContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid()){
                    showToast();
                }else{
                    Toast.makeText(view.getContext(), getString(R.string.email_error_text), Toast.LENGTH_SHORT).show();
                }
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

    boolean isEmailValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(donorEmail.getText().toString()).matches() && !donorEmail.getText().toString().isEmpty();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            try {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
