package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.button.MaterialButton;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.PaymentActivity;

public class DonateDonorFragment extends Fragment {


    public DonateDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        donate(view);
        setVideo(view);

        return view;
    }

    private void donate(View view){
        MaterialButton donateBtn = view.findViewById(R.id.donate_button);

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paymentIntent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(paymentIntent);
            }
        });

    }

    private void setVideo(View view){
        VideoView vid = view.findViewById(R.id.videoView);
        String videoPath = "android.resource://" + view.getContext().getPackageName() + "/" + R.raw.homeless_video;
        Uri uri = Uri.parse(videoPath);
        vid.setVideoURI(uri);

        MediaController mediaController = new MediaController(view.getContext());
        vid.setMediaController(mediaController);
        mediaController.setAnchorView(vid);
    }
}
