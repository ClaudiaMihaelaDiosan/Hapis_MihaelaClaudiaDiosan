package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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
        viewPagerAdapter.addFragment(needsFragment);
        viewPagerAdapter.addFragment(locationFragment);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.create_homeless_terms);
        tabLayout.getTabAt(1).setIcon(R.drawable.create_homeless_profile);
        tabLayout.getTabAt(2).setIcon(R.drawable.create_homeless_needs);
        tabLayout.getTabAt(3).setIcon(R.drawable.create_homeless_location);

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
}
