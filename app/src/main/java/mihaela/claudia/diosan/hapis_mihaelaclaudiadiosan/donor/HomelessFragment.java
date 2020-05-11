package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomelessFragment extends Fragment {

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

    private ArrayList<Homeless> homelessList;



    public HomelessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homeless, container, false);
        AdapterHome mAdapter = new AdapterHome(homelessList);
        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        initViews(view);


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

    private void getInfo(){
        Integer position = preferences.getInt("position", 0);

        if (position.equals(0)){
            String andrewUsername = preferences.getString("andrewName", "");
            String andrewBirthday = preferences.getString("andrewBirthday","");
            String andrewStory = preferences.getString("andrewStory","");
            String andrewLocation = preferences.getString("andrewLocation","");
            String andrewSchedule = preferences.getString("andrewSchedule","");
            String andrewNeed = preferences.getString("andrewNeed","");

            profileImage.setImageResource(R.drawable.andrew_image);
            homelessUsername.setText(andrewUsername);
            homelessBirthday.setText(andrewBirthday);
            homelessLifeHistory.setText(andrewStory);
            homelessAddress.setText(andrewLocation);
            homelessSchedule.setText(andrewSchedule);
            homelessNeed.setText(andrewNeed);

        }else  if (position.equals(1)){
            String mariaUsername = preferences.getString("mariaName", "");
            String mariaBirthday = preferences.getString("mariaBirthday","");
            String mariaStory = preferences.getString("mariaStory","");
            String mariaLocation = preferences.getString("mariaLocation","");
            String mariaSchedule = preferences.getString("mariaSchedule","");
            String mariaNeed = preferences.getString("mariaNeed","");

            profileImage.setImageResource(R.drawable.maria_image);
            homelessUsername.setText(mariaUsername);
            homelessBirthday.setText(mariaBirthday);
            homelessLifeHistory.setText(mariaStory);
            homelessAddress.setText(mariaLocation);
            homelessSchedule.setText(mariaSchedule);
            homelessNeed.setText(mariaNeed);

        }else  if (position.equals(2)){
            String maiteUsername = preferences.getString("maiteName", "");
            String maiteBirthday = preferences.getString("maiteBirthday","");
            String maiteStory = preferences.getString("maiteStory","");
            String maiteLocation = preferences.getString("maiteLocation","");
            String maiteSchedule = preferences.getString("maiteSchedule","");
            String maiteNeed = preferences.getString("maiteNeed","");

            profileImage.setImageResource(R.drawable.maite_image);
            homelessUsername.setText(maiteUsername);
            homelessBirthday.setText(maiteBirthday);
            homelessLifeHistory.setText(maiteStory);
            homelessAddress.setText(maiteLocation);
            homelessSchedule.setText(maiteSchedule);
            homelessNeed.setText(maiteNeed);

        }else  if (position.equals(3)){
            String luisUsername = preferences.getString("luisName", "");
            String luisBirthday = preferences.getString("luisBirthday","");
            String luisStory = preferences.getString("luisStory","");
            String luisLocation = preferences.getString("luisLocation","");
            String luisSchedule = preferences.getString("luisSchedule","");
            String luisNeed = preferences.getString("luisNeed","");

            profileImage.setImageResource(R.drawable.luis_image);
            homelessUsername.setText(luisUsername);
            homelessBirthday.setText(luisBirthday);
            homelessLifeHistory.setText(luisStory);
            homelessAddress.setText(luisLocation);
            homelessSchedule.setText(luisSchedule);
            homelessNeed.setText(luisNeed);

        }else  if (position.equals(4)){
            String cristinaUsername = preferences.getString("cristinaName", "");
            String cristinaBirthday = preferences.getString("cristinaBirthday","");
            String cristinaStory = preferences.getString("cristinaStory","");
            String cristinaLocation = preferences.getString("cristinaLocation","");
            String cristinaSchedule = preferences.getString("cristinaSchedule","");
            String cristinaNeed = preferences.getString("cristinaNeed","");

            profileImage.setImageResource(R.drawable.cristina_image);
            homelessUsername.setText(cristinaUsername);
            homelessBirthday.setText(cristinaBirthday);
            homelessLifeHistory.setText(cristinaStory);
            homelessAddress.setText(cristinaLocation);
            homelessSchedule.setText(cristinaSchedule);
            homelessNeed.setText(cristinaNeed);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
    }

}
