package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.os.Bundle;

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


public class HomeDonorFragment extends Fragment {

    View view;

    private RecyclerView mrecyclerView;
    private AdapterHome mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Homeless> homelessList;

    public HomeDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_donor, container, false);

        //Recyclerview
        addItems();
        buildRecyclerView();

        return view;
    }

    public void buildRecyclerView(){
        mrecyclerView = view.findViewById(R.id.recycler_view_donor);
        mrecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new AdapterHome(homelessList);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterHome.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomelessFragment homelessFragment = new HomelessFragment();
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

}
