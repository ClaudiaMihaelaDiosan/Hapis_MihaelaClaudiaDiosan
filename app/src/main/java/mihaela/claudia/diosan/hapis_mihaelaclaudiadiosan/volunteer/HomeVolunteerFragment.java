package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class HomeVolunteerFragment extends Fragment {

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton newHomelessProfile;
    FloatingActionButton sendDeliveryNotification;
    Dialog notificationDialog;
    MaterialButton sendNotification;
    MaterialButton cancelNotification;
    View view;



    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Homeless> homeless;
    private View.OnClickListener editHomelessListener;
    private View.OnClickListener deleteHomelessListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_volunteer, container, false);


       floatingActionMenu = view.findViewById(R.id.floatingActionMenu);
       newHomelessProfile = view.findViewById(R.id.new_homeless_profile);
       sendDeliveryNotification = view.findViewById(R.id.send_delivery_notification);

       notificationDialog = new Dialog(getActivity());


        newHomelessProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(getActivity(), CreateHomelessPerfileActivity.class);
                startActivity(createAccountIntent);
            }
        });

        sendDeliveryNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationDialog();
            }
        });


        editHomelessListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.edit_button_toast), Toast.LENGTH_LONG).show();
            }
        };

        deleteHomelessListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(getActivity(), getString(R.string.delete_button_toast), Toast.LENGTH_LONG).show();

            }
        };



        //Recyclerview
        addItems();
        buildRecyclerView();

        return view;
    }




    public void sendNotificationDialog(){
        notificationDialog.setContentView(R.layout.send_delivery_notification);
        cancelNotification = (MaterialButton) notificationDialog.findViewById(R.id.delivery_close_button);
        sendNotification = (MaterialButton) notificationDialog.findViewById(R.id.send_notification_button);

        cancelNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationDialog.dismiss();
            }
        });

        notificationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        notificationDialog.show();

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),  getString(R.string.delivery_send_toast), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buildRecyclerView(){
        mrecyclerView = view.findViewById(R.id.recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new HomelessAdapter(homeless, editHomelessListener, deleteHomelessListener);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setAdapter(mAdapter);

    }


    public void addItems(){
         homeless = new ArrayList<>();
         homeless.add(new Homeless(R.drawable.andrew_image,"Andrew","654978369", "12/02/1980", getString(R.string.andrew_story), getString(R.string.andrew_location), getString(R.string.andrew_schedule), getString(R.string.work)));
         homeless.add(new Homeless(R.drawable.maria_image,"Maria","633258698", "03/05/1960",getString(R.string.maria_story) , getString(R.string.maria_location), getString(R.string.maria_schedule), getString(R.string.food)));
         homeless.add(new Homeless(R.drawable.maite_image,"Maite","640125478", "06/03/1985", getString(R.string.maite_story), getString(R.string.maite_location), getString(R.string.maite_schedule), getString(R.string.clothes)));
         homeless.add(new Homeless(R.drawable.luis_image,"Luis","650231478", "07/01/1979",getString(R.string.luis_story) , getString(R.string.luis_location), getString(R.string.luis_schedule), getString(R.string.work)));
         homeless.add(new Homeless(R.drawable.cristina_image,"Cristina","632023125", "09/12/1975",getString(R.string.cristina_story) , getString(R.string.cristina_location), getString(R.string.cristina_schedule), getString(R.string.lodging)));
    }


}
