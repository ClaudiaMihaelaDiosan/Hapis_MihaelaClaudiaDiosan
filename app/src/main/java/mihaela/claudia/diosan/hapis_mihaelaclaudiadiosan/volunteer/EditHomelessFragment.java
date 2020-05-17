package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.ForgotPasswordActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditHomelessFragment extends Fragment implements View.OnClickListener {


    private TextView usernameTV;
    private TextInputEditText  phoneET;
    private TextInputEditText lifeHistoryET;

    private TextInputEditText scheduleET;

    private TextView location;
    private TextView need;

    private ImageView editProfileImage;


    private SharedPreferences preferences;
    private View view;


    private FirebaseFirestore mFirestore;
    private FirebaseUser user;
    private StorageReference storageReference;

    MaterialButton cancelBtn;
    MaterialButton saveBtn;
    MaterialButton deleteBtn;

    String username;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_homeless, container, false);

        initViews();
        firebaseInit();

        username = preferences.getString("homelessUsername", "");
        usernameTV.setText(username);

        getCurrentInfo(username);



        return view;
    }

    private void firebaseInit(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mFirestore = FirebaseFirestore.getInstance();

    }

    private void initViews(){
        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);
        usernameTV = view.findViewById(R.id.homeless_username_tv);
        phoneET = view.findViewById(R.id.homeless_edit_phone);
        lifeHistoryET = view.findViewById(R.id.homeless_edit_life_history);
        scheduleET = view.findViewById(R.id.homeless_edit_schedule);
        location = view.findViewById(R.id.selected_location_text);
        need = view.findViewById(R.id.most_important_need);
        editProfileImage = view.findViewById(R.id.edit_profile_image);
        cancelBtn = view.findViewById(R.id.cancelEditButton);
        saveBtn = view.findViewById(R.id.saveHomelessBtn);
        deleteBtn = view.findViewById(R.id.deleteProfileBtn);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cancelBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelEditButton:
                Intent homeIntent = new Intent(getActivity(),HomeVolunteer.class );
                startActivity(homeIntent);
                break;
            case R.id.saveHomelessBtn:
                break;
            case R.id.deleteProfileBtn:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog(){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getString(R.string.delete_dialog_title))
                .setMessage(getString(R.string.delete_message))
                .setIcon(R.drawable.delete_homeless_icon)
                .setPositiveButton(getString(R.string.delete_confirm_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHomeless(username);
                        deleteProfilePhoto(username);
                        startActivity(new Intent(getActivity(), HomeVolunteer.class));
                    }
                })
                .setNegativeButton(getString(R.string.delete_cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    private void deleteHomeless(String username){
        mFirestore.collection("homeless").document(username)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      //  Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        showSuccessfullDialog(getString(R.string.delete_correct_toast));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                     //   Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    private void deleteProfilePhoto(String username){
       storageReference.child("homelessProfilePhotos/" + user.getEmail() + "->" + username)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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

    public void showSuccessfullDialog(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.GREEN);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_black_24dp, 0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }


    private void getCurrentInfo(String username){

        DocumentReference documentReference = mFirestore.collection("homeless").document(username);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null){

                        String phone = documentSnapshot.getString("homelessPhoneNumber");
                        String lifeHistory = documentSnapshot.getString("homelessLifeHistory");
                        String schedule = documentSnapshot.getString("homelessSchedule");
                        String locationValue = documentSnapshot.getString("homelessAddress");
                        String needValue = documentSnapshot.getString("homelessNeed");
                        String image = documentSnapshot.getString("image");


                        phoneET.setHint(phone);
                        lifeHistoryET.setHint(lifeHistory);
                        scheduleET.setHint(schedule);
                        location.setText(locationValue);
                        need.setText(needValue);

                        Glide
                                .with(getActivity())
                                .load(image)
                                .into(editProfileImage);


                    }
                }
            }
        });

    }



}
