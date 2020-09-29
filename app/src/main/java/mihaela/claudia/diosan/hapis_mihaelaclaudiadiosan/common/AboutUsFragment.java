package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;


public class AboutUsFragment extends Fragment {

TextView aboutGithub, aboutWebPage, aboutYoutTube, aboutTwitter, aboutInstagram;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        aboutGithub = view.findViewById(R.id.about_us_github);
        aboutWebPage = view.findViewById(R.id.about_us_webpage);
        aboutYoutTube = view.findViewById(R.id.about_us_youtube);
        aboutTwitter = view.findViewById(R.id.about_us_twitter);
        aboutInstagram = view.findViewById(R.id.about_us_instagram);

        aboutGithub.setMovementMethod(LinkMovementMethod.getInstance());
        aboutWebPage.setMovementMethod(LinkMovementMethod.getInstance());
        aboutYoutTube.setMovementMethod(LinkMovementMethod.getInstance());
        aboutTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        aboutInstagram.setMovementMethod(LinkMovementMethod.getInstance());


        return view;
    }
}