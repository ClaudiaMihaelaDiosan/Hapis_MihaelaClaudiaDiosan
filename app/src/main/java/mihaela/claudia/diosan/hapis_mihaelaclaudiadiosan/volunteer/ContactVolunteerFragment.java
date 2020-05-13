package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class ContactVolunteerFragment extends Fragment implements View.OnClickListener{

    private TextInputEditText subjectEditText;
    private TextInputEditText messageEditText;

    private MaterialButton volunteerContactBtn;

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    FirebaseUser user;

    private Map<String,String> contactFromData = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        initViews(view);

         return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        volunteerContactBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.contact_send_button){
            sendContactFormData();
        }
    }

    private void sendContactFormData(){
        String subject = subjectEditText.getText().toString();
        String message = subjectEditText.getText().toString();

        contactFromData.put("contactSubject", subject);
        contactFromData.put("contactMessage",message);

        if (validFields()) {
            mFirestore.collection("volunteersContactForm").document(user.getEmail() + "\n" +  subject).set(contactFromData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            successSentToast();
                                  }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            showErrorToast("Error " + error);

                        }
                    });
        }
    }

    private boolean validFields() {
        if (subjectEditText.getText().toString().isEmpty()) {
            subjectEditText.setError(getString(R.string.empty_field_error));
            return false;
        }

        if (messageEditText.getText().toString().isEmpty()) {
            messageEditText.setError(getString(R.string.empty_field_error));
            return false;
        }
        return true;
    }

    private void initViews(View view) {
        volunteerContactBtn = view.findViewById(R.id.contact_send_button);
        subjectEditText = view.findViewById(R.id.contact_subject_hint);
        messageEditText = view.findViewById(R.id.form_contact_message_hint);
    }



    public void showErrorToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
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


    public void successSentToast(){
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
