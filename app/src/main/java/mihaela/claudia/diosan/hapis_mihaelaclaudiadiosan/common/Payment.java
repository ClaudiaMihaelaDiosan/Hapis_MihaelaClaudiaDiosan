package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common;

import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import me.abhinay.input.CurrencyEditText;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;

public class Payment extends NetworkInfo {

    MaterialButton payBtn;
    CardForm cardForm;
    MaterialAlertDialogBuilder alertDialogBuilder;
    CurrencyEditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setupPaymentForm();
    }

    private void setupPaymentForm(){
        etInput = findViewById(R.id.etInput);
        etInput.setCurrency("€");
        etInput.setDelimiter(false);
        etInput.setSpacing(true);
        etInput.setDecimals(true);
        etInput.setSeparator(".");


        cardForm = findViewById(R.id.card_form);
        payBtn = findViewById(R.id.button_pay);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation(getString(R.string.paymant_phone_explanation))
                .setup(Payment.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        payBtn.setOnClickListener(v -> {
            if (cardForm.isValid()){
                showAlertDialog();
            }else {
                HelpActivity.showErrorToast(getApplicationContext(),getString(R.string.payment_toast_fail));
            }
        });
    }

    private void showAlertDialog(){
        alertDialogBuilder = new MaterialAlertDialogBuilder(Payment.this);
        alertDialogBuilder.setTitle(getString(R.string.payment_confirm_title));
        alertDialogBuilder.setMessage(getString(R.string.payment_card__number) + cardForm.getCardNumber() + "\n" +
                getString(R.string.payment_card_expiration) + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                getString(R.string.payment_card_cvv) + cardForm.getCvv() + "\n" +
                getString(R.string.payment_postal_code) + cardForm.getPostalCode() + "\n" +
                getString(R.string.payment_phone_number) + cardForm.getMobileNumber());

        alertDialogBuilder.setPositiveButton( getString(R.string.payment_confirm_button), (dialog, which) -> {
            dialog.dismiss();
            Toast.makeText(Payment.this,  getString(R.string.payment_toast_success), Toast.LENGTH_SHORT).show();
        });

        alertDialogBuilder.setNegativeButton( getString(R.string.payment_cancel_button), (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
