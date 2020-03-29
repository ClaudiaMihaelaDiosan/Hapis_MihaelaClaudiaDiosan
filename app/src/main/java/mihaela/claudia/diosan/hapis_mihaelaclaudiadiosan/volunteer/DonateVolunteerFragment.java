package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.app.Activity;
import android.content.Intent;
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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.GooglePayConfig;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class DonateVolunteerFragment extends Fragment {

    View view;
    VideoView vid;
    MediaController mediaController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_donate_volunteer, container, false);

        VideoView videoView = (VideoView) view.findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getContext().getPackageName() + "/" + R.raw.homeless_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        return view;
    }

}
