package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.intro;

import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary.HelpActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class IntroActivity extends NetworkInfo {

    private ViewPager screenPager;
    private TabLayout tabIndicator;


    private MaterialButton btnNext, btnGetStarted;
    private Animation btnAnim;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        HelpActivity.makeActivityFullScreen(getWindow(), getSupportActionBar());
        checkFirstTimeOpenedApp();

        setContentView(R.layout.activity_intro);

        initViews();
        setupViewPager();
    }


    private void checkFirstTimeOpenedApp() {
        if (restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }


    private void initViews() {
        tabIndicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
    }


    private void setupViewPager() {
        //fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(getString(R.string.logo_intro_title), getString(R.string.logo_intro_description), R.drawable.intro_logo ));
        mList.add(new ScreenItem(getString(R.string.donor_intro_title), getString(R.string.donor_intro_description), R.drawable.intro_donor ));
        mList.add(new ScreenItem(getString(R.string.volunteer_intro_tile), getString(R.string.volunteer_intro_description), R.drawable.intro_volunteer ));
        mList.add(new ScreenItem(getString(R.string.liquid_galaxy_intro_title), getString(R.string.liquid_galaxy_description), R.drawable.intro_liquid_galaxy ));


        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        IntroViewPagerAdapter introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup TabLayout wih ViewPager
        tabIndicator.setupWithViewPager(screenPager);

        //next button click Listener
        btnNext.setOnClickListener(v -> {

            position = screenPager.getCurrentItem();
            if (position < mList.size()){
                position++;
                screenPager.setCurrentItem(position);

            }
            if (position == mList.size()-1){  //when we reach to the last screen
                //show the GETSTARTED Button and hide the indicator and the next button

                loadLastScreen();

            }
        });


        //TabLayout and Change Listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1) {
                    loadLastScreen();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Get Started button click Listener
        btnGetStarted.setOnClickListener(v -> {

            //open login activity
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);

            //we need to save a boolean value to storage so next time when the user run the app
            //we could know that he is already check the intro screen activity
            //we use sharedpreference

            savePrefsData();
            finish();
        });
    }


    private boolean restorePrefData() {
        SharedPreferences pref =  getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        return pref.getBoolean("IsIntroOpened",false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("IsIntroOpened",true);
        editor.apply();
    }

    //show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen(){
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);

    }


}