package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common.AboutUsFragment;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common.ContactFragment;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common.DonateFragment;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.common.UseTerms;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.connectivity.NetworkInfo;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.maps.ListMapFragment;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.maps.MapFragment;


public class HomeDonor extends NetworkInfo implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout donorDrawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    View header;

    private  TextView donorUsername, donorEmail, donorPhone, donorFirstName, donorLastName;

    private  FirebaseUser user;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_donor);

        initViews();
        initFirebase();
        setNavigationElements();
        setDataUser();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new HomeDonorFragment()).commit();
            navigationView.setCheckedItem(R.id.user_menu_home);
        }
    }

    private void initFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mToolbar = findViewById(R.id.donor_toolbar);
        setSupportActionBar(mToolbar);

        donorDrawer = findViewById(R.id.donor_drawer);
        navigationView = findViewById(R.id.nav_view_donor);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, donorDrawer, mToolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        donorDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setDataUser(){
        DocumentReference documentReference = mFirestore.collection("donors").document(user.getEmail());

        if (user != null){
            donorEmail.setText(user.getEmail());

            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null){

                        donorUsername.setText( documentSnapshot.getString("username"));
                        donorPhone.setText(documentSnapshot.getString("phone"));
                        donorFirstName.setText(documentSnapshot.getString("firstName"));
                        donorLastName.setText(documentSnapshot.getString("lastName"));
                    }
                }
            });
        }
    }

    private void setNavigationElements() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        donorUsername = header.findViewById(R.id.user_username_text_view);
        donorEmail = header.findViewById(R.id.user_email_text_view);
        donorPhone = header.findViewById(R.id.user_phone_text_view);
        donorFirstName = header.findViewById(R.id.user_first_name);
        donorLastName = header.findViewById(R.id.user_last_name);

        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, donorDrawer, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        donorDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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

            };



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.home){
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
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new DonateFragment()).commit();
                break;
            case R.id.user_menu_configuration:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container, new DonorConfigurationFragment()).commit();
                break;
            case R.id.user_menu_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(HomeDonor.this, LoginActivity.class));
                break;
            case R.id.user_menu_contact:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new ContactFragment()).commit();
                break;
            case R.id.user_menu_help:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new HelpDonorFragment()).commit();
                break;
            case  R.id.user_terms_of_use:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new UseTerms()).commit();
                break;
            case  R.id.user_menu_about_us:
                bottomNavigationView.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.donor_fragment_container,new AboutUsFragment()).commit();
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
            startActivity(new Intent(HomeDonor.this, HomeDonor.class));
        }
    }

}
