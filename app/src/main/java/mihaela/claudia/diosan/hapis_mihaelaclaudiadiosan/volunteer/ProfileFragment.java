package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.IOException;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class ProfileFragment extends Fragment {

    private static  final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int CAMERA_REQUEST_CODE = 100;

    Integer REQUEST_CAMERA = 1;
    Integer SELECT_FILE = 0;


    ImageView homelessProfileImage;
    ImageButton addProfilePhotoBtn;
    View view;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setRetainInstance(true);

      homelessProfileImage = view.findViewById(R.id.homeless_profile_image);
      addProfilePhotoBtn = view.findViewById(R.id.add_homeless_profile_photo_button);
//
//      homelessProfileImage.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//          }
//      });

      addProfilePhotoBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
//              Toast.makeText(getActivity(),"ImageButton", Toast.LENGTH_SHORT).show();
//              verifyStoragePermissions(getActivity());
              selectImage();

          }
      });

      return view;
    }


        private void selectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (items[i].equals("Camera")){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,REQUEST_CAMERA);

                }else
                if (items[i].equals("Gallery")){

                    Intent selectFileIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    selectFileIntent.setType("image/*");
                    startActivityForResult(selectFileIntent.createChooser(selectFileIntent, "Select FIle"), SELECT_FILE);

                }else if (items[i].equals("Cancel")){
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
