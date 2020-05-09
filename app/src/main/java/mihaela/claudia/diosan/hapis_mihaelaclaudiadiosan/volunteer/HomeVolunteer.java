package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class HomeVolunteer extends MainActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private DrawerLayout volunteerDrawer;
    Toolbar mToolbar;

    TextView volunteerUsername;
    TextView volunteerEmail;
    TextView volunteerPhone;

    SharedPreferences preferences;
    View header;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_volunteer);


        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);



        mToolbar = findViewById(R.id.volunteer_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        volunteerDrawer = findViewById(R.id.volunteer_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view_volunteer);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

        volunteerUsername = header.findViewById(R.id.volunteer_username_text_view);
        volunteerEmail = header.findViewById(R.id.volunteer_email_text_view);
        volunteerPhone = header.findViewById(R.id.volunteer_phone_text_view);


        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, volunteerDrawer, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        volunteerDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeVolunteerFragment()).commit();
            navigationView.setCheckedItem(R.id.volunteer_menu_home);
        }

        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                key = sharedPreferences.getString("volunteerPhone", "");
                volunteerPhone.setText(key);
            }
        });

    }

    public void getVolunteerInfo(){
        String username = preferences.getString("volunteerUsername","");
        String email = preferences.getString("volunteerEmail","");
        String phone = preferences.getString("volunteerPhone","");


        volunteerUsername.setText(username);
        volunteerEmail.setText(email);
        volunteerPhone.setText(phone);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getVolunteerInfo();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                volunteerDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.volunteer_menu_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeVolunteerFragment()).commit();
                break;
            case R.id.volunteer_menu_donate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DonateVolunteerFragment()).commit();
                break;
            case R.id.volunteer_menu_configuration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfigurationVolunteerFragment()).commit();
                break;
            case R.id.volunteer_menu_logout:
                Intent loginIntent = new Intent(HomeVolunteer.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.volunteer_menu_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactVolunteerFragment()).commit();
                break;
        }
        volunteerDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        volunteerDrawer = findViewById(R.id.volunteer_drawer);

        if (volunteerDrawer.isDrawerOpen(GravityCompat.START)) {
            volunteerDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}



