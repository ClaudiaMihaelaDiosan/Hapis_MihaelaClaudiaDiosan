package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.ForgotPasswordActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register.RegisterDonorActivity;


public class HomeVolunteerFragment extends Fragment {

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton newHomelessProfile;
    FloatingActionButton sendDeliveryNotification;
    Dialog notificationDialog;
    MaterialButton sendNotification;
    MaterialButton cancelNotification;
    View view;


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


}
