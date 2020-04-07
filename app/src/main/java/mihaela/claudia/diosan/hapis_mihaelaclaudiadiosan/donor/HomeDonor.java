package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

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
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;


public class HomeDonor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout donorDrawer;
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home_donor);

        mToolbar = findViewById(R.id.donor_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        donorDrawer = findViewById(R.id.donor_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view_donor);
        navigationView.setNavigationItemSelectedListener(this);

//        bottomNavigationView.findViewById(R.id.bottom_navigation_view);


        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, donorDrawer, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        donorDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new HomeDonorFragment()).commit();
            navigationView.setCheckedItem(R.id.donor_menu_home);
        }




    }

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
            case R.id.donor_menu_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new HomeDonorFragment()).commit();
                break;
            case R.id.donor_menu_donate:

                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new DonateDonorFragment()).commit();
                break;
            case R.id.donor_menu_configuration:
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new ConfigurationDonorFragment()).commit();
                break;
            case R.id.donor_menu_logout:
                Intent loginIntent = new Intent(HomeDonor.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.donor_menu_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new ContactDonorFragment()).commit();
                break;

//            case R.id.home_navigation:
//                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new HomeDonorFragment()).commit();
//                break;
//
//            case R.id.map_navigation:
//                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new MapFragment()).commit();
//                break;
//
//            case R.id.list_map_navigaion:
//                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new ListMapFragment()).commit();
//                break;

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
