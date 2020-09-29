package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class NetworkInfo extends AppCompatActivity {

    private NetworkReceiver networkReceiver = new NetworkReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(networkReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(networkReceiver);
    }


    public class NetworkReceiver extends BroadcastReceiver {

        Context context;

        public NetworkReceiver(Context context){
            this.context = context;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            new CheckNetworkTask(context).execute(networkInfo);
        }
    }


    public class CheckNetworkTask extends AsyncTask<android.net.NetworkInfo, Void, List<String>> {

        Context context;

        public CheckNetworkTask(Context context){
            this.context = context;
        }

        @Override
        protected List<String> doInBackground(android.net.NetworkInfo... networkInfos) {
            android.net.NetworkInfo networkInfo = networkInfos[0];
            List<String> stateList = new ArrayList<>();

            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                stateList.add( getString(R.string.wifi_connected));
            } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                stateList.add(getString(R.string.mobile_connected));
            } else {
                stateList.add(getString(R.string.no_network_operating));
            }

            return stateList;
        }


        @Override
        protected void onPostExecute(List<String> status) {
            super.onPostExecute(status);

            String statusConnection = status.get(0);

            if (statusConnection.equals(getString(R.string.mobile_connected))) {
                Toast.makeText(getApplicationContext(), statusConnection, Toast.LENGTH_SHORT).show();
            }else if (statusConnection.equals(getString(R.string.no_network_operating))) {
                createAlertDialog();
            }


        }

        public void createAlertDialog(){

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setCancelable(false);

            builder.setTitle(getString(R.string.no_network_operating))
                    .setMessage(getString(R.string.option_network))
                    .setIcon(R.drawable.no_internet_connexion_icon)
                    .setPositiveButton(getString(R.string.positive_button_network), (dialog, which) -> {
                        finish();
                        moveTaskToBack(true);
                    })
                    .setNegativeButton(getString(R.string.negative_button_network), (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = builder.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
    }

}