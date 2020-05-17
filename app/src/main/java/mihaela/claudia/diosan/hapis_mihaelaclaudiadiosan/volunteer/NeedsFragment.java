package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView needTV;
    private TextView needText;

    private MaterialButton cancelScheduleBtn;
    private MaterialButton saveScheduleBtn;

    private SharedPreferences preferences;

    private TextInputEditText scheduleEditText;

    private ChipGroup chipGroup;

    private FirebaseFirestore mFirestore;

    private Map<String,String> homeless = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_needs, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews();
        firebaseInit();




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

    private void initViews(){
        scheduleEditText = view.findViewById(R.id.homeless_schedule_edit_text);

        needTV = view.findViewById(R.id.most_important_neev_tv);
        needText = view.findViewById(R.id.most_important_need);
        cancelScheduleBtn = view.findViewById(R.id.cancelScheduleButton);
        saveScheduleBtn = view.findViewById(R.id.saveScheduleButton);
        chipGroup = (ChipGroup) view.findViewById(R.id.chip_group);
    }

    private void firebaseInit(){
        mFirestore = FirebaseFirestore.getInstance();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cancelScheduleBtn.setOnClickListener(this);
        saveScheduleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelScheduleButton:
                Intent homeIntent = new Intent(getActivity(),HomeVolunteer.class );
                startActivity(homeIntent);
                break;
            case R.id.saveScheduleButton:
                if (isValidForm()){
                uploadDataToFirebase();
                successfullyUploadedInfoToast();
                startActivity(new Intent(getActivity(), HomeVolunteer.class));
            }

        }

    }

    private boolean isValidForm(){
        if (scheduleEditText.getText().toString().isEmpty()){
            showErrorToast(getString(R.string.complete_schedule_toast));
           return false;
        }

        if (needText.getText().toString().isEmpty()){
            showErrorToast(getString(R.string.complete_need_toast));
           return false;
        }

        return true;
    }


    private void uploadDataToFirebase(){
        String homelessUsername = preferences.getString("homelessUsername","");

        String homelessSchedule = scheduleEditText.getText().toString();
        String homelessNeed = needText.getText().toString();

        homeless.put("homelessSchedule", homelessSchedule);
        homeless.put("homelessNeed", homelessNeed);

        mFirestore.collection("homeless").document(homelessUsername).set(homeless, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //  successfullyUploadedInfoToast();
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                showErrorToast("Error " + error);

            }
        });
    }


    public void showErrorToast(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
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

    private void successfullyUploadedInfoToast(){
        Toast toast = Toast.makeText(getActivity(), getString(R.string.account_created), Toast.LENGTH_LONG);
        View view =toast.getView();
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        view.setBackgroundColor(Color.TRANSPARENT);
        toastMessage.setTextColor(Color.GREEN);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_black_24dp, 0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.show();
    }


}
