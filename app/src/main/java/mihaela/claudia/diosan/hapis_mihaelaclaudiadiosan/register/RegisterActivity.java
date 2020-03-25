package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class RegisterActivity extends AppCompatActivity {

//    CardView donorRegister;
//    CardView volunteerRegister;
//    Configuration newConfig;
    Dialog knowMoreDonorDialog;
    Dialog knowMoreVolunteerDialog;

    ImageView closePopUpDonor;
    ImageView closePopUpVolunteer;

    MaterialButton knowMoreDonorBtn;
    MaterialButton knowMoreDonorOkBtn;
    MaterialButton knowMoreVolunteerBtn;
    MaterialButton knowMoreVolunteerOkBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();


//        donorRegister = (CardView) findViewById(R.id.donor_cardview);
//        volunteerRegister = (CardView) findViewById(R.id.volunteer_cardview);
          knowMoreDonorDialog =  new Dialog(this);
          knowMoreDonorBtn =  findViewById(R.id.know_more_donor_button);

          knowMoreVolunteerDialog =  new Dialog(this);
          knowMoreVolunteerBtn =  findViewById(R.id.know_more_volunteer_button);



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
//
//        donorRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent registerDonorActivity = new Intent(RegisterActivity.this, RegisterDonorActivity.class );
//                startActivity(registerDonorActivity);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
//
//        volunteerRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent registerVolunteerActivity = new Intent(RegisterActivity.this, RegisterVolunteerActivity.class );
//                startActivity(registerVolunteerActivity);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
//            }
//        });
    }

    private void showVolunteerPopUpDialog() {

        knowMoreVolunteerDialog.setContentView(R.layout.volunteer_know_more_popup);
        closePopUpVolunteer = knowMoreVolunteerDialog.findViewById(R.id.know_more_volunteer_popup_close);
        knowMoreVolunteerOkBtn =  knowMoreVolunteerDialog.findViewById(R.id.volunteer_know_more_ok_button);

        closePopUpVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knowMoreVolunteerDialog.dismiss();
            }
        });

        Objects.requireNonNull(knowMoreVolunteerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        knowMoreVolunteerDialog.show();

        knowMoreVolunteerOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knowMoreVolunteerDialog.dismiss();
            }
        });
    }


    public void showDonorPopUpDialog(){
        knowMoreDonorDialog.setContentView(R.layout.donor_know_more_popup);
        closePopUpDonor =  knowMoreDonorDialog.findViewById(R.id.know_more_donor_popup_close);
        knowMoreDonorOkBtn = knowMoreDonorDialog.findViewById(R.id.know_more_donor_ok_button);

        closePopUpDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knowMoreDonorDialog.dismiss();
            }
        });

        Objects.requireNonNull(knowMoreDonorDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        knowMoreDonorDialog.show();

        knowMoreDonorOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                knowMoreDonorDialog.dismiss();
            }
        });
    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
