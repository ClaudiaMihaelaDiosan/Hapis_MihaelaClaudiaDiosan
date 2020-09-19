package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor.HomeDonor;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomeVolunteer;

/**
 * A simple {@link Fragment} subclass.
 */

public class ContactFragment extends Fragment implements View.OnClickListener{

    /*EdiText*/
    private TextInputEditText subjectET;
    private TextInputEditText messageET;

    /*Buttons*/
    private MaterialButton donorContactButton;

    /*Firebase*/
    private FirebaseFirestore mFirestore;
    FirebaseUser user;

    private Map<String,String> contactFormData = new HashMap<>();

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        initViews(view);
        initFirebase();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        donorContactButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.contact_send_button){
            if (validFields()){
                sendContactFormData();
                HelpActivity.showSuccessToast(getActivity(), getString(R.string.contact_toast_message));

                isVolunteer(user.getEmail());
                isDonor(user.getEmail());
            }
        }
    }


    private void initViews(View view){
        donorContactButton = view.findViewById(R.id.contact_send_button);
        subjectET = view.findViewById(R.id.contact_subject_hint);
        messageET = view.findViewById(R.id.form_contact_message_hint);

    }

    private void initFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void sendContactFormData(){
        String subject = subjectET.getText().toString();
        String message = messageET.getText().toString();

        contactFormData.put("contactSubject", subject);
        contactFormData.put("contactMessage",message);

        mFirestore.collection("contactForm").document(user.getEmail() + "\n" +  subject).set(contactFormData, SetOptions.merge());
    }

    private boolean validFields() {
        if (subjectET.getText().toString().isEmpty()) {
            subjectET.setError(getString(R.string.empty_field_error));
            return false;
        }

        if (messageET.getText().toString().isEmpty()) {
            messageET.setError(getString(R.string.empty_field_error));
            return false;
        }
        return true;
    }

    private void isVolunteer(String email){
        DocumentReference donorsDocument = mFirestore.collection("volunteers").document(email);

        donorsDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        startActivity(new Intent(getContext(), HomeVolunteer.class));
                    }
                }
            }
        });
    }

    private void isDonor(String email){
        DocumentReference donorsDocument = mFirestore.collection("donors").document(email);

        donorsDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()){
                        startActivity(new Intent(getContext(), HomeDonor.class));
                    }
                }
            }
        });
    }


}
