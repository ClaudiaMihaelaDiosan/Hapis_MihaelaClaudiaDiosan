package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.button.MaterialButton;



import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class DonateVolunteerFragment extends Fragment {

    View view;
    VideoView vid;
    MediaController mediaController;
    MaterialButton donateBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_donate_volunteer, container, false);

        donateBtn = view.findViewById(R.id.volunteer_donate_button);


        final VideoView videoView = (VideoView) view.findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getContext().getPackageName() + "/" + R.raw.homeless_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);


        mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paymentIntent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(paymentIntent);
            }
        });

        return view;
    }


}
