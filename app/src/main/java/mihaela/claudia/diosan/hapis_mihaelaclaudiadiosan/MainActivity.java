package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.ConnectivityReceiver;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.intro.IntroActivity;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private BroadcastReceiver connectivityReceiver = null;
    Boolean connectivityOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityReceiver = new ConnectivityReceiver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityReceiver.connectivityReceiverListener = (ConnectivityReceiver.ConnectivityReceiverListener) this;
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onPause() {
        super.onPause();
        ConnectivityReceiver.connectivityReceiverListener = (ConnectivityReceiver.ConnectivityReceiverListener) this;
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(String status) {

        if (status.equals(getResources().getString(R.string.wifi_connected))){
         //   Toast.makeText(getApplicationContext(), R.string.wifi_connected, Toast.LENGTH_SHORT).show();
        }else if (status.equals(getResources().getString(R.string.mobile_connected))){
          //  Toast.makeText(getApplicationContext(), R.string.mobile_connected, Toast.LENGTH_SHORT).show();
        }else {
         //   Toast.makeText(getApplicationContext(), R.string.no_network_operating, Toast.LENGTH_SHORT).show();
            createAlertDialog();
        }
    }


    public void createAlertDialog(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.no_network_operating))
                .setMessage(getString(R.string.option_network))
                .setIcon(R.drawable.no_internet_connexion_icon)
                .setPositiveButton(getString(R.string.positive_button_network), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                       // System.exit(0);
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton(getString(R.string.negative_button_network), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                })
                .show();
    }
}
