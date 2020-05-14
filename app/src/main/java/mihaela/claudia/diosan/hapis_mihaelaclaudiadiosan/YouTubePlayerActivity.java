package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);

        fullScreenActivity();

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_video);
        youTubePlayerView.initialize(getString(R.string.API_KEY), this);

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void fullScreenActivity() {
        //make the activity on full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

      if (!restored) {
          youTubePlayer.cueVideo("39qrOzxxqeY");
      }


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, 1).show();
            Toast.makeText(getApplicationContext(), youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1){
            getYoutTubePlayerProvider().initialize(getString(R.string.API_KEY), this);
        }
    }

    private YouTubePlayer.Provider getYoutTubePlayerProvider() {

        return youTubePlayerView;
    }

}
