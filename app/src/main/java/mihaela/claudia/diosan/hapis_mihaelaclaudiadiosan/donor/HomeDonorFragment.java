package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.HomelessAdapter;

import static android.content.Context.MODE_PRIVATE;


public class HomeDonorFragment extends Fragment {

    private View view;

    private ArrayList<Homeless> homelessList;

    private SharedPreferences preferences;

    public HomeDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_donor, container, false);

        preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);

        //Recyclerview
        addItems();
        buildRecyclerView();

        return view;
    }

    public void buildRecyclerView(){
        RecyclerView mrecyclerView = view.findViewById(R.id.recycler_view_donor);
        mrecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        AdapterHome mAdapter = new AdapterHome(homelessList);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterHome.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomelessFragment homelessFragment = new HomelessFragment();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("position", position);
                editor.apply();
                getItems();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, homelessFragment)
                        .addToBackStack(null).commit();
            }
        });

    }




    public void addItems(){
        homelessList = new ArrayList<>();

        homelessList.add(new Homeless(R.drawable.andrew_image,"Andrew",getString(R.string.work), getString(R.string.andrew_location)));
        homelessList.add(new Homeless(R.drawable.maria_image,"Maria",getString(R.string.food), getString(R.string.maria_location)));
        homelessList.add(new Homeless(R.drawable.maite_image,"Maite",getString(R.string.clothes), getString(R.string.maite_location)));
        homelessList.add(new Homeless(R.drawable.luis_image,"Luis",getString(R.string.work), getString(R.string.luis_location)));
        homelessList.add(new Homeless(R.drawable.cristina_image,"Cristina",getString(R.string.lodging), getString(R.string.cristina_location)));
    }

    public void getItems(){

        String andrewName = "Andrew";
        String andrewBirthday = "12/02/1980";
        String andrewStory = getString(R.string.andrew_story);
        String andrewLocation = getString(R.string.andrew_location);
        String andrewLatitude = "41.6119987";
        String andrewLongitude = "0.6242844";
        String andrewSchedule =  getString(R.string.andrew_schedule);
        String andrewNeed = getString(R.string.work);


        String mariaName = "Maria";
        String mariaBirthday = "06/03/1985";
        String mariaStory = getString(R.string.maria_story);
        String mariaLocation = getString(R.string.maria_location);
        String mariaLatitude = "41.611801";
        String mariaLongitude = "0.628162";
        String mariaSchedule =  getString(R.string.maria_schedule);
        String mariaNeed = getString(R.string.food);

        String maiteName = "Maite";
        String maiteBirthday = "03/05/1960";
        String maiteStory = getString(R.string.maite_story);
        String maiteLocation = getString(R.string.maite_location);
        String maiteLatitude = "41.6116452";
        String maiteLongitude = "0.6318808";
        String maiteSchedule =  getString(R.string.maite_schedule);
        String maiteNeed = getString(R.string.clothes);

        String luisName = "Luis";
        String luisBirthday = "07/01/1979";
        String luisStory = getString(R.string.luis_story);
        String luisLocation = getString(R.string.luis_location);
        String luisLatitude = "41.620809";
        String luisLongitude = "0.628363";
        String luisSchedule =  getString(R.string.luis_schedule);
        String luisNeed = getString(R.string.work);

        String cristinaName = "Cristina";
        String cristinaBirthday = "09/12/1975";
        String cristinaStory = getString(R.string.cristina_story);
        String cristinaLocation = getString(R.string.cristina_location);
        String cristinaLatitude = "41.6169655";
        String cristinaLongitude = "0.6132156";
        String cristinaSchedule =  getString(R.string.cristina_schedule);
        String cristinaNeed = getString(R.string.work);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("andrewName", andrewName);
        editor.putString("andrewBirthday", andrewBirthday);
        editor.putString("andrewStory", andrewStory);
        editor.putString("andrewLocation", andrewLocation);
        editor.putString("andrewLatitude", andrewLatitude);
        editor.putString("andrewLongitude", andrewLongitude);
        editor.putString("andrewSchedule", andrewSchedule);
        editor.putString("andrewNeed", andrewNeed);

        editor.putString("mariaName", mariaName);
        editor.putString("mariaBirthday", mariaBirthday);
        editor.putString("mariaStory", mariaStory);
        editor.putString("mariaLocation", mariaLocation);
        editor.putString("mariaLatitude", mariaLatitude);
        editor.putString("mariaLongitude", mariaLongitude);
        editor.putString("mariaSchedule", mariaSchedule);
        editor.putString("mariaNeed", mariaNeed);

        editor.putString("maiteName", maiteName);
        editor.putString("maiteBirthday", maiteBirthday);
        editor.putString("maiteStory", maiteStory);
        editor.putString("maiteLocation", maiteLocation);
        editor.putString("maiteLatitude", maiteLatitude);
        editor.putString("maiteLongitude", maiteLongitude);
        editor.putString("maiteSchedule", maiteSchedule);
        editor.putString("maiteNeed", maiteNeed);

        editor.putString("luisName", luisName);
        editor.putString("luisBirthday", luisBirthday);
        editor.putString("luisStory", luisStory);
        editor.putString("luisLocation", luisLocation);
        editor.putString("luisLatitude", luisLatitude);
        editor.putString("luisLongitude", luisLongitude);
        editor.putString("luisSchedule", luisSchedule);
        editor.putString("luisNeed", luisNeed);

        editor.putString("cristinaName", cristinaName);
        editor.putString("cristinaBirthday", cristinaBirthday);
        editor.putString("cristinaStory", cristinaStory);
        editor.putString("cristinaLocation", cristinaLocation);
        editor.putString("cristinaLatitude", cristinaLatitude);
        editor.putString("cristinaLongitude", cristinaLongitude);
        editor.putString("cristinaSchedule", cristinaSchedule);
        editor.putString("cristinaNeed", cristinaNeed);
        editor.apply();
    }

}
