package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.intro.IntroViewPagerAdapter;

public class CreateHomelessPerfileActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    private TermsFragment termsFragment;
    private ProfileFragment profileFragment;
    private NeedsFragment needsFragment;
    private LocationFragment locationFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_homeless_perfile);
        checkConection();


        viewPager = findViewById(R.id.create_homeless_view_pager);
        tabLayout = findViewById(R.id.create_homeless_tab_layout);

        termsFragment = new TermsFragment();
        profileFragment = new ProfileFragment();
        needsFragment = new NeedsFragment();
        locationFragment = new LocationFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(termsFragment);
        viewPagerAdapter.addFragment(profileFragment);
        viewPagerAdapter.addFragment(locationFragment);
        viewPagerAdapter.addFragment(needsFragment);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.create_homeless_terms);
        tabLayout.getTabAt(1).setIcon(R.drawable.create_homeless_profile);
        tabLayout.getTabAt(2).setIcon(R.drawable.create_homeless_location);
        tabLayout.getTabAt(3).setIcon(R.drawable.create_homeless_needs);


    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment){
            fragments.add(fragment);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle oldInstanceState) {
        super.onSaveInstanceState(oldInstanceState);
        oldInstanceState.clear();
    }

    public void checkConection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            //Toast.makeText(getApplicationContext(), R.string.wifi_connected, Toast.LENGTH_SHORT).show();

        } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            //Toast.makeText(getApplicationContext(), R.string.mobile_connected, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), R.string.no_network_operating, Toast.LENGTH_SHORT).show();

        }
    }




}
