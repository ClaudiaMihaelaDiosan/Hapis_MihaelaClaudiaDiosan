package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private static  final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int CAMERA_REQUEST_CODE = 100;

    Integer REQUEST_CAMERA = 1;
    Integer SELECT_FILE = 0;


    ImageView homelessProfileImage;
    ImageButton addProfilePhotoBtn;
    View view;

    TextInputEditText homelessUsername;
    TextInputEditText homelessPhoneNumber;
    TextInputEditText homelessBirthday;
    TextInputEditText homelessLifeHistory;

    String homelessUsernameValue;
    String homelessPhoneNumberValue;
    String homelessBirthdayValue;
    String homelessLifeHistoryValue;

    MaterialButton cancelBtn;
    MaterialButton saveBtn;

    SharedPreferences preferences;

    DatePickerDialog.OnDateSetListener setListener;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

      homelessProfileImage = view.findViewById(R.id.homeless_profile_image);
      addProfilePhotoBtn = view.findViewById(R.id.add_homeless_profile_photo_button);

      homelessUsername = view.findViewById(R.id.homeless_username_editText);
      homelessPhoneNumber = view.findViewById(R.id.homeless_phone_number_editText);
      homelessBirthday = view.findViewById(R.id.homeless_birthday_editText);
      homelessLifeHistory = view.findViewById(R.id.homeless_life_history_editText);

      cancelBtn = view.findViewById(R.id.cancelProfileButton);
      saveBtn = view.findViewById(R.id.saveProfileButton);


      preferences = getActivity().getSharedPreferences("homelessInfo", MODE_PRIVATE);


      addProfilePhotoBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              verifyStoragePermissions(getActivity());
              selectImage();
          }
      });

      homelessBirthday.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              selectDate();
          }
      });

        setTextDateListener();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(),HomeVolunteer.class );
                startActivity(homeIntent);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (isValidPhoneNumber(homelessPhoneNumber.getText().toString())){
                    getInfo();
                    Toast.makeText(getActivity(), getString(R.string.toast_save_btn), Toast.LENGTH_SHORT).show();
                }else if (!isValidPhoneNumber(homelessPhoneNumber.getText().toString())) {
                    Toast.makeText(getActivity(), getString(R.string.phone_error_text), Toast.LENGTH_SHORT).show();
                }
                }
        });

      return view;
    }


    public void getInfo(){
        homelessUsernameValue = homelessUsername.getText().toString();
        homelessPhoneNumberValue = homelessPhoneNumber.getText().toString();
        homelessBirthdayValue = homelessBirthday.getText().toString();
        homelessLifeHistoryValue = homelessLifeHistory.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homelessUsername", homelessUsernameValue);
        editor.putString("homelessPhoneNumber", homelessPhoneNumberValue);
        editor.putString("homelessBirthday", homelessBirthdayValue);
        editor.putString("homelessLifeHistory", homelessLifeHistoryValue);
        editor.apply();

    }


    public void validate(){
         if (homelessUsername.getText().toString().isEmpty()){
             homelessUsername.setError(getString(R.string.username_error_text));

         }

         if (homelessLifeHistory.getText().toString().isEmpty()){
             homelessLifeHistory.setError(getString(R.string.complete_life_toast));

         }
    }

    public  boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() == 0){
            return true;
        }else if (target== null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public void selectDate(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), setListener, year, month, day);
        datePickerDialog.show();

    }


    public void setTextDateListener(){
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                homelessBirthday.setText(date);
            }
        };
    }



        private void selectImage(){
        final CharSequence[] items = {getString(R.string.chooser_gallery), getString(R.string.chooser_camera), getString(R.string.chooser_cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_add_image));

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (items[i].equals(getString(R.string.chooser_camera))){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,REQUEST_CAMERA);

                }else if (items[i].equals(getString(R.string.chooser_gallery))){

                    Intent selectFileIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    selectFileIntent.setType("image/*");
                    startActivityForResult(selectFileIntent.createChooser(selectFileIntent, getString(R.string.dialog_select_file)), SELECT_FILE);

                }else if (items[i].equals(getString(R.string.chooser_cancel))){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
      //  super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == Activity.RESULT_OK && null != data){
            if (requestCode == REQUEST_CAMERA){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                homelessProfileImage.setImageBitmap(bitmap);
            }else
                if (requestCode == SELECT_FILE){
                Uri selectedImageUri = data.getData();
                homelessProfileImage.setImageURI(selectedImageUri);
            }
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int camera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        if (ActivityCompat.checkSelfPermission(activity, String.valueOf(camera)) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }



}
