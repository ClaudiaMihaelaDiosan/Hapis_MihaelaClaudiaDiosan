package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary;

import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;


public class HelpActivity extends NetworkInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void makeActivityFullScreen(Window window, ActionBar actionBar) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(actionBar).hide();
    }

    /*
    * Toasts
    * */

    public static void showSuccessToast(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.BLUE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_drawable,0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showErrorToast(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        View view =toast.getView();
        view.setBackgroundColor(Color.WHITE);
        TextView toastMessage =  toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.RED);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setTextSize(15);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error_drawable, 0,0,0);
        toastMessage.setPadding(10,10,10,10);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /*
    * Pop Ups
    * */

    public static void showPositivePopup(Context context, String title, String message, String positiveBtn){
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtn, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /*
    * Check fields
    * */

    public static boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() == 0){
            return true;
        }else if (target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }


    public static boolean isPasswordValid(CharSequence password){
        return password.length() > 5;
    }

    public static boolean isUsernameValid(CharSequence username){
        return username.length() > 3 && username.length() <= 25;
    }

    public static boolean isLifeHistoryValid(CharSequence lifeHistory){

        return lifeHistory.length() > 19 && lifeHistory.length() <= 400;
    }

    public static boolean isScheduleValid(CharSequence schedule){

        return schedule.length() > 4 && schedule.length() <= 40;
    }

}