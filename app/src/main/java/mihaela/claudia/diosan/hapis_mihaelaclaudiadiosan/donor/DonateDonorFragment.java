package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.button.MaterialButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.YouTubePlayerActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.PaymentActivity;

public class DonateDonorFragment extends Fragment implements View.OnClickListener{


    private MaterialButton donateBtn;
    private MaterialButton videoBtn;

    private boolean fullScreen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);


        donateBtn = view.findViewById(R.id.donate_button);
        videoBtn = view.findViewById(R.id.view_video_button);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        donateBtn.setOnClickListener(this);
        videoBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.donate_button:
                Intent paymentIntent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(paymentIntent);
                break;

            case R.id.view_video_button:
                Intent videoActivity = new Intent(getActivity(), YouTubePlayerActivity.class);
                startActivity(videoActivity);
                break;
        }

    }

}
