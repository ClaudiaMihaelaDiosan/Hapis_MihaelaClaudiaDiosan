package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Currency;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class PaymentActivity extends AppCompatActivity {

    MaterialButton payBtn;
    CardForm cardForm;
    MaterialAlertDialogBuilder alertDialogBuilder;
    CurrencyEditText etInput;
    MaterialButton backBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        checkConection();


        etInput = (CurrencyEditText) findViewById(R.id.etInput);
        etInput.setCurrency("€");
        etInput.setDelimiter(false);
        etInput.setSpacing(true);
        etInput.setDecimals(true);
        //Make sure that Decimals is set as false if a custom Separator is used
        etInput.setSeparator(".");


        cardForm = findViewById(R.id.card_form);
        payBtn = findViewById(R.id.button_pay);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation(getString(R.string.paymant_phone_explanation))
                .setup(PaymentActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()){
                    alertDialogBuilder = new MaterialAlertDialogBuilder(PaymentActivity.this);
                    alertDialogBuilder.setTitle(getString(R.string.payment_confirm_title));
                    alertDialogBuilder.setMessage(getString(R.string.payment_card__number) + cardForm.getCardNumber() + "\n" +
                            getString(R.string.payment_card_expiration) + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            getString(R.string.payment_card_cvv) + cardForm.getCvv() + "\n" +
                            getString(R.string.payment_postal_code) + cardForm.getPostalCode() + "\n" +
                            getString(R.string.payment_phone_number) + cardForm.getMobileNumber());

                    alertDialogBuilder.setPositiveButton( getString(R.string.payment_confirm_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(PaymentActivity.this,  getString(R.string.payment_toast_success), Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialogBuilder.setNegativeButton( getString(R.string.payment_cancel_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else {
                    Toast.makeText(PaymentActivity.this,  getString(R.string.payment_toast_fail), Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    public void checkConection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            //Toast.makeText(getApplicationContext(), R.string.wifi_connected, Toast.LENGTH_SHORT).show();

        } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            //Toast.makeText(getApplicationContext(), R.string.mobile_connected, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), R.string.no_network_operating, Toast.LENGTH_SHORT).show();

        }
    }


}
