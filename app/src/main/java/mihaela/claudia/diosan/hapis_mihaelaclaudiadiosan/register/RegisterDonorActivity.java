package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class RegisterDonorActivity extends AppCompatActivity {

    TextInputEditText donorBirthday;
    DatePickerDialog.OnDateSetListener setListener;
    Dialog succesRegisterDialog;
    Button registerDonorBtn;
    ImageView rdClosePopUp;
    Button rdOKBtn;
    TextView rdPopupMessage;
    TextView rdPopupTitle;
    TextView rdAcceptTermsTV;
    CheckBox acceptTermsCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);

//        donorBirthday = (TextInputEditText) findViewById(R.id.donor_birthday_edit_text);
//        registerDonorBtn = (Button) findViewById(R.id.register_donor_button);
//        succesRegisterDialog = new Dialog(this);
//        rdAcceptTermsTV = findViewById(R.id.accept_terms_text_view);
//        rdAcceptTermsTV.setMovementMethod(LinkMovementMethod.getInstance());
//
//
//        acceptTermsCheckbox = (CheckBox) findViewById(R.id.donor_terms_checkbox);
//
//
//
//
//
//        //Select donor birthday
//        donorBirthday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setCalendarDate();
//            }
//        });
//
//        setEditTextDateListener();
//
//        acceptTermsCheckbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (acceptTermsCheckbox.isChecked()){
//                    acceptTermsCheckbox.setTextColor(getResources().getColor(R.color.colorAccent));
//                }else {
//                    acceptTermsCheckbox.setTextColor(getResources().getColor(R.color.grey));
//                }
//            }
//        });
//
//        //Register donor button
//        registerDonorBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (acceptTermsCheckbox.isChecked()) {
//                    showPopUpDialog();
//                }else {
//                    showToast();
//                }
//            }
//        });
//
//    }
//
//    public void showToast(){
//        Toast toast = Toast.makeText(RegisterDonorActivity.this, getString(R.string.register_donor_terms_error), Toast.LENGTH_LONG);
//        View view =toast.getView();
//        view.setBackgroundColor(Color.TRANSPARENT);
//        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
//        toastMessage.setTextColor(Color.RED);
//        toast.show();
//    }
//
//
//    public void setCalendarDate(){
//        Calendar calendar = Calendar.getInstance();
//        final int year = calendar.get(Calendar.YEAR);
//        final int month = calendar.get(Calendar.MONTH);
//        final int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterDonorActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
//        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        datePickerDialog.show();
//    }
//
//
//    public void setEditTextDateListener(){
//        setListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                String date = dayOfMonth + "/" + month + "/" + year;
//                donorBirthday.setText(date);
//            }
//        };
//    }
//
//    public void showPopUpDialog(){
//        succesRegisterDialog.setContentView(R.layout.register_donor_popup);
//        rdClosePopUp = (ImageView) succesRegisterDialog.findViewById(R.id.rp_close_pop_up);
//        rdOKBtn = (Button) succesRegisterDialog.findViewById(R.id.rp_ok_button);
//        rdPopupTitle = (TextView) succesRegisterDialog.findViewById(R.id.rp_title_popup);
//        rdPopupMessage = (TextView) succesRegisterDialog.findViewById(R.id.rp_message_pop_up);
//
//
//
//        rdClosePopUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                succesRegisterDialog.dismiss();
//            }
//        });
//
//        succesRegisterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        succesRegisterDialog.show();
//
//        rdOKBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goLogin = new Intent(RegisterDonorActivity.this, LoginActivity.class);
//                startActivity(goLogin);
//            }
//        });
    }



    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }
}
