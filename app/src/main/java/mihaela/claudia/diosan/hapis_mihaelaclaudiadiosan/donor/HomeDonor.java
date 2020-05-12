package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;


public class HomeDonor extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*Navigation Elements*/
    private DrawerLayout donorDrawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    View header;

    /*TextViews*/
    TextView donorUsername;
    TextView donorEmail;
    TextView donorPhone;

    /*Preferences*/
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_donor);
        initViews();
        setNavigationElements();
        setSharedPreferences();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new HomeDonorFragment()).commit();
            navigationView.setCheckedItem(R.id.user_menu_home);
        }
    }


    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mToolbar = findViewById(R.id.donor_toolbar);
        donorDrawer = findViewById(R.id.donor_drawer);
        navigationView = findViewById(R.id.nav_view_donor);
    }

    private void setNavigationElements() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        donorUsername = header.findViewById(R.id.user_username_text_view);
        donorEmail = header.findViewById(R.id.user_email_text_view);
        donorPhone = header.findViewById(R.id.user_phone_text_view);

        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, donorDrawer, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        donorDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void setSharedPreferences() {
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                key = sharedPreferences.getString("donorPhone", "");
                donorPhone.setText(key);
            }
        });
    }



    public void getDonorInfo(){
        String username = preferences.getString("donorUsername","");
        String email = preferences.getString("donorUsername","");
        String phone = preferences.getString("donorPhone","");

        donorUsername.setText(username);
        donorEmail.setText(email);
        donorPhone.setText(phone);

    }

    @Override
    protected void onResume() {
        super.onResume();
       getDonorInfo();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            

            switch (item.getItemId()){

                case R.id.home_navigation:
                fragment = new HomeDonorFragment();
                break;

            case R.id.map_navigation:
                fragment = new MapFragment();
                break;

            case R.id.list_map_navigaion:
                fragment = new ListMapFragment();
                break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, fragment).commit();
            return true;

        }
    };



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

              switch (item.getItemId()){
                case android.R.id.home:
                donorDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.user_menu_home:
                bottomNavigationView.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new HomeDonorFragment()).commit();
                break;
            case R.id.user_menu_donate:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new DonateDonorFragment()).commit();
                break;
            case R.id.user_menu_configuration:
                bottomNavigationView.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new ConfigurationDonorFragment()).commit();
                break;
            case R.id.user_menu_logout:
                Intent loginIntent = new Intent(HomeDonor.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.user_menu_contact:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new ContactDonorFragment()).commit();
                break;
        }
        donorDrawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        donorDrawer = findViewById(R.id.donor_drawer);

        if (donorDrawer.isDrawerOpen(GravityCompat.START)) {
            donorDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
