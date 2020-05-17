package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomelessFragment extends Fragment implements View.OnClickListener {

    private MaterialButton wantHelpBtn;
    private View view;

    private SharedPreferences preferences;

    private ImageView profileImage;
    private TextView homelessUsername;
    private TextView homelessBirthday;
    private TextView homelessLifeHistory;
    private TextView homelessAddress;
    private TextView homelessSchedule;
    private TextView homelessNeed;

    private String username;

    private FirebaseFirestore mFirestore;
    private FirebaseUser user;
    private StorageReference storageReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homeless, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews(view);
        firebaseInit();

        username = preferences.getString("homelessUsername", "");
        homelessUsername.setText(username);

        getHomelessInfo(username);

        return view;
    }


    private void firebaseInit(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mFirestore = FirebaseFirestore.getInstance();

    }

    private void initViews(View view){
        profileImage = view.findViewById(R.id.profile_image_donor_cv);
        homelessUsername = view.findViewById(R.id.homeless_username_tv_cv);
        homelessBirthday =  view.findViewById(R.id.homeless_birthday_tv);
        homelessLifeHistory =view.findViewById(R.id.homeless_lifeHistory_tv);
        homelessAddress = view.findViewById(R.id.homeless_locationAddress_tv);
        homelessSchedule = view.findViewById(R.id.homeless_schedule_tv);
        homelessNeed = view.findViewById(R.id.homeless_need_tv);
        wantHelpBtn = view.findViewById(R.id.want_help_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wantHelpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.want_help_button){
            HelpFragment helpFragment = new HelpFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, helpFragment)
                    .addToBackStack(null).commit();
        }
    }

    private void getHomelessInfo(String username){

        DocumentReference documentReference = mFirestore.collection("homeless").document(username);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null){

                        String birthday = documentSnapshot.getString("homelessBirthday");
                        String lifeHistory = documentSnapshot.getString("homelessLifeHistory");
                        String locationValue = documentSnapshot.getString("homelessAddress");
                        String schedule = documentSnapshot.getString("homelessSchedule");
                        String needValue = documentSnapshot.getString("homelessNeed");
                        String image = documentSnapshot.getString("image");


                        homelessBirthday.setText(birthday);
                        homelessLifeHistory.setText(lifeHistory);
                        homelessAddress.setText(locationValue);
                        homelessSchedule.setText(schedule);
                        homelessNeed.setText(needValue);

                        Glide
                                .with(getActivity())
                                .load(image)
                                .placeholder(R.drawable.no_profile_image)
                                .into(profileImage);
                    }
                }
            }
        });
    }
}
