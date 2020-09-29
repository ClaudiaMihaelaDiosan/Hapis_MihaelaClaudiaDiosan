package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.menuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGCommand;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGConnectionManager;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.tasks.GetSessionTask;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController.cleanKmlSlave;

public class ToolsActivity extends NetworkInfo implements View.OnClickListener {

    MaterialButton cleanKmls, relaunchLG, rebootLG, shutdownLG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        initViews();

        cleanKmls.setOnClickListener(this);
        relaunchLG.setOnClickListener(this);
        rebootLG.setOnClickListener(this);
        shutdownLG.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cleanKmls:
                setCleanKMLButtonBehaviour();
                break;
            case R.id.relaunch:
                setRelaunchButtonBehaviour();
                break;
            case R.id.reboot:
                setRebootButtonBehaviour();
                break;
            case R.id.shutdown:
                setShutDownButtonBehaviour();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.install_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.first_time_install:
                install();
                return true;
            case R.id.complete_uninstall:
                uninstall();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initViews(){
        cleanKmls = findViewById(R.id.cleanKmls);
        relaunchLG = findViewById(R.id.relaunch);
        rebootLG = findViewById(R.id.reboot);
        shutdownLG = findViewById(R.id.shutdown);
    }

    private void setCleanKMLButtonBehaviour() {
            try {
                String sentence = "chmod 777 /var/www/html/kmls.txt; echo '' > /var/www/html/kmls.txt";
                showAlertAndExecution(sentence, getString(R.string.clean_kmls_action));
                cleanKmlSlave("slave_2");
                cleanKmlSlave("slave_3");
                cleanKmlSlave("slave_4");
                cleanKmlSlave("slave_5");
                cleanKmlSlave("slave_6");
                cleanKmlSlave("slave_7");
                cleanKmlSlave("slave_8");
            } catch (Exception e) {
                HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.error_galaxy));
            }
    }

    /*RELAUNCH*/
    //When relaunch Liquid Galaxy button is clicked, the sentence to achieve it is send to the LG.
    private void setRelaunchButtonBehaviour() {
            try {
                String sentence = "/home/lg/bin/lg-relaunch > /home/lg/log.txt";
                showAlertAndExecution(sentence, getString(R.string.relaunch_action));
            } catch (Exception e) {
                HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.error_galaxy));
            }

    }

    /*REBOOT*/
    //When reboot Liquid Galaxy button is clicked, the sentence to achieve it is send to the LG.
    private void setRebootButtonBehaviour() {
            try {
                String sentence = "/home/lg/bin/lg-reboot > /home/lg/log.txt";
                showAlertAndExecution(sentence, getString(R.string.reboot_action));

            } catch (Exception e) {
                HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.error_galaxy));
            }
    }

    /*SHUT DOWN*/
    //When shut down Liquid Galaxy button is clicked, the sentence to achieve it is send to the LG.
    private void setShutDownButtonBehaviour() {
            try {
                String sentence = "/home/lg/bin/lg-poweroff > /home/lg/log.txt";
                showAlertAndExecution(sentence, getString(R.string.shutdown_action));
            } catch (Exception e) {
                HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.error_galaxy));
            }
    }


    /*CLEAN KMLS, SHUT DOWN, RELAUNCH and REBOOT*/
    private void showAlertAndExecution(final String sentence, String action) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage(getString(R.string.sure) + " " + action + " " + getString(R.string.lg))
        .setPositiveButton(getResources().getString(R.string.lg_positive_btn), (arg0, arg1) -> LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, LGCommand.CRITICAL_MESSAGE, null)))
        .setNegativeButton(getResources().getString(R.string.lg_negative_btn), (arg0, arg1) -> {
        });

        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }


    private void install(){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);

            builder.setTitle(getString(R.string.install_title))
                    .setMessage(getString(R.string.install_description))
                    .setPositiveButton(getString(R.string.pop_up_button), (dialog, which) -> {
                        String sentence = "cd /var/www/html/ ; mkdir -p hapis/{balloons/{basic/{donors,volunteers,homeless},bio/{donors,volunteers,homeless},statistics/cities,transactions/{donors,volunteers,homeless}},placemarks/{donors,volunteers,homeless}} ; sudo apt install curl";
                        String donor = "cd /var/www/html/hapis/balloons/basic/donors/ ; curl -o donor https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Fdonor.png?alt=media&token=3555e7ca-6181-4704-968b-5dfac1637c53 ; cd /var/www/html/hapis/balloons/bio/donors/ ; curl -o donor https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Fdonor.png?alt=media&token=3555e7ca-6181-4704-968b-5dfac1637c53 ; cd /var/www/html/hapis/balloons/transactions/donors/ ; curl -o donor https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Fdonor.png?alt=media&token=3555e7ca-6181-4704-968b-5dfac1637c53";
                        String volunteer = "cd /var/www/html/hapis/balloons/basic/volunteers/ ; curl -o volunteer https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Fvolunteer.png?alt=media&token=8b0ee5f6-c15c-4f07-a967-1ace117a8c22 ; cd /var/www/html/hapis/balloons/bio/volunteers/ ; curl -o volunteer https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Fvolunteer.png?alt=media&token=8b0ee5f6-c15c-4f07-a967-1ace117a8c22 ; cd /var/www/html/hapis/balloons/transactions/volunteers/ ; curl -o volunteer https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Fvolunteer.png?alt=media&token=8b0ee5f6-c15c-4f07-a967-1ace117a8c22";
                        String logos = "cd /var/www/html/hapis/ ; curl -o logos.png https://firebasestorage.googleapis.com/v0/b/gsoc2020-hapis.appspot.com/o/lg_profile%2Flogos.png?alt=media&token=6a7658d6-86f8-4c6c-af2b-3f26172508c6";
                        LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, LGCommand.CRITICAL_MESSAGE, null));
                        LGConnectionManager.getInstance().addCommandToLG(new LGCommand(donor, LGCommand.CRITICAL_MESSAGE, null));
                        LGConnectionManager.getInstance().addCommandToLG(new LGCommand(volunteer, LGCommand.CRITICAL_MESSAGE, null));
                        LGConnectionManager.getInstance().addCommandToLG(new LGCommand(logos, LGCommand.CRITICAL_MESSAGE, null));
                        HelpActivity.showSuccessToast(getApplicationContext(), getString(R.string.install_toast));
                    })
                    .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = builder.show();
            alertDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.error_galaxy));
        }

    }


    private void uninstall(){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);

            builder.setTitle(getString(R.string.uninstall_title))
                    .setMessage(getString(R.string.uninstall_description))
                    .setPositiveButton(getString(R.string.pop_up_button), (dialog, which) -> {
                        String sentence = "cd /var/www/html/ ; rm -r hapis";
                        LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, LGCommand.CRITICAL_MESSAGE, null));
                        HelpActivity.showSuccessToast(getApplicationContext(), getString(R.string.uninstall_toast));
                    })
                    .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = builder.show();
            alertDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            HelpActivity.showErrorToast(getApplicationContext(), getString(R.string.error_galaxy));
        }

    }

}