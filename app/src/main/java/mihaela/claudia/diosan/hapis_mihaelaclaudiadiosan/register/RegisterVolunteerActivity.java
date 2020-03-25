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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class RegisterVolunteerActivity extends AppCompatActivity {

    TextInputEditText volunteerBirthday;
    DatePickerDialog.OnDateSetListener setListener;
    Dialog succesRegisterDialog;
    MaterialButton registerVolunteerBtn;
    ImageView rvClosePopUp;
    MaterialButton rvOKBtn;
    TextView rvPopupMessage;
    TextView rvPopupTitle;
    TextView rvAcceptTerms;
    CheckBox rvAcceptTermsCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_volunteer);
        volunteerBirthday =  findViewById(R.id.volunteer_birthday_edit_text);
        registerVolunteerBtn = findViewById(R.id.register_volunteer_button);
        succesRegisterDialog = new Dialog(this);
        rvAcceptTerms = findViewById(R.id.volunteer_accept_terms_text_view);
        rvAcceptTerms.setMovementMethod(LinkMovementMethod.getInstance());

        rvAcceptTermsCheckbox = findViewById(R.id.volunteer_terms_checkbox);



        //Select donor birthday
        volunteerBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalendarDate();
            }
        });

            setEditTextListener();

        rvAcceptTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvAcceptTermsCheckbox.isChecked()){
                    rvAcceptTermsCheckbox.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    rvAcceptTermsCheckbox.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });



        //Register volunteer button
        registerVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvAcceptTermsCheckbox.isChecked()){
                    showPopUpDialog();
                }else {
                    showToast();
                }


            }
        });

    }


    public void showToast(){
        Toast toast = Toast.makeText(RegisterVolunteerActivity.this, getString(R.string.register_volunteer_terms_error), Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.TRANSPARENT);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toast.show();
    }

    public void setCalendarDate(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterVolunteerActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
        Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void setEditTextListener(){
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                volunteerBirthday.setText(date);
            }
        };
    }


    public void showPopUpDialog(){
        succesRegisterDialog.setContentView(R.layout.register_volunteer_popup);
        rvClosePopUp =  succesRegisterDialog.findViewById(R.id.rv_close_pop_up);
        rvOKBtn =  succesRegisterDialog.findViewById(R.id.rv_ok_button);
        rvPopupTitle =  succesRegisterDialog.findViewById(R.id.rv_title_popup);
        rvPopupMessage =  succesRegisterDialog.findViewById(R.id.rv_message_pop_up);



        rvClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                succesRegisterDialog.dismiss();
            }
        });

        Objects.requireNonNull(succesRegisterDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        succesRegisterDialog.show();

        rvOKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegisterVolunteerActivity.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }
}
