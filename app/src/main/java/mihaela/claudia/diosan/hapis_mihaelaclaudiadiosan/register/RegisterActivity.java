package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class RegisterActivity extends MainActivity {


    /*Buttons*/
    MaterialButton knowMoreDonorBtn;
    MaterialButton knowMoreVolunteerBtn;
    MaterialButton startRegisterDonorBtn;
    MaterialButton startRegisterVolunteerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        makeActivityFullScreen();
        initViews();
        onClickButtons();

    }



    private void makeActivityFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void initViews() {
        knowMoreDonorBtn =  findViewById(R.id.know_more_donor_button);
        startRegisterDonorBtn = findViewById(R.id.start_register_donor);
        knowMoreVolunteerBtn =  findViewById(R.id.know_more_volunteer_button);
        startRegisterVolunteerBtn = findViewById(R.id.start_register_volunteer);
    }

    private void onClickButtons() {
        knowMoreDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDonorPopUpDialog();
            }
        });

        knowMoreVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVolunteerPopUpDialog();
            }
        });
//

        startRegisterDonorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerDonorActivity = new Intent(RegisterActivity.this, RegisterDonorActivity.class );
                startActivity(registerDonorActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        startRegisterVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerVolunteerActivity = new Intent(RegisterActivity.this, RegisterVolunteerActivity.class );
                startActivity(registerVolunteerActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    private void showVolunteerPopUpDialog(){
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.volunteer_know_more_title))
                    .setMessage(getString(R.string.volunteer_know_more_text))
                    .setPositiveButton(getString(R.string.register_pop_up_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    })
                    .show();
    }

    private void showDonorPopUpDialog(){
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.donor_know_more_title))
                .setMessage(getString(R.string.donor_know_more_text))
                .setPositiveButton(getString(R.string.register_pop_up_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
