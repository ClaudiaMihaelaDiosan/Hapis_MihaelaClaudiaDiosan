package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class HomeVolunteer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout volunteerDrawer;
    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home_volunteer);

        mToolbar = findViewById(R.id.volunteer_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        volunteerDrawer = findViewById(R.id.volunteer_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view_volunteer);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, volunteerDrawer, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        volunteerDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeVolunteerFragment()).commit();
            navigationView.setCheckedItem(R.id.volunteer_menu_home);
        }

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



