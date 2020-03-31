package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;

import java.io.IOException;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static  final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ImageView homelessProfileImage;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 0;
    private View view;
    private Uri imageUri;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        homelessProfileImage = view.findViewById(R.id.homeless_profile_image);

//        homelessProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                verifyStoragePermissions(getActivity());
//                selectImage();
//
//            }
//        });


                return view;
    }

//    private void selectImage(){
//        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Image");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                if (items[i].equals("Camera")){
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, REQUEST_CAMERA);
//
//                }else if (items[i].equals("Gallery")){
//                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent1.setType("image/*");
//                    startActivityForResult(intent1.createChooser(intent1, "Select File"), SELECT_FILE);
//
//
//                }else if (items[i].equals("Cancel")){
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        builder.show();
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data );
//
//        if (resultCode == getActivity().RESULT_OK){
//
//            if (requestCode == REQUEST_CAMERA){
//                Bundle bundle = data.getExtras();
//                final Bitmap bitmap = (Bitmap) bundle.get("data");
//                homelessProfileImage.setImageBitmap(bitmap);
//
//
//            }else if (requestCode == SELECT_FILE){
//                Uri selectedImageUri = data.getData();
//                homelessProfileImage.setImageURI(selectedImageUri);
//            }
//        }
//    }



//    /**
//     * Checks if the app has permission to write to device storage
//     * <p/>
//     * If the app does not has permission then the user will be prompted to grant permissions
//     *
//     * @param activity the activity from which permissions are checked
//     */
//    public static void verifyStoragePermissions(Activity activity) {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    activity,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }
}
