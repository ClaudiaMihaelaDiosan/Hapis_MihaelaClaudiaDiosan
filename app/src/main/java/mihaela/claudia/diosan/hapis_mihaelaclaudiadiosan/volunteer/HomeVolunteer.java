package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login.LoginActivity;

public class HomeVolunteer extends MainActivity implements NavigationView.OnNavigationItemSelectedListener  {


    /*Navigation Elements*/
    private DrawerLayout volunteerDrawer;
    NavigationView navigationView;
    Toolbar mToolbar;
    View header;

    /*TextViews*/
    TextView volunteerUsername;
    TextView volunteerEmail;
    TextView volunteerPhone;
    TextView volunteerFirstName;
    TextView volunteerLastName;

    FirebaseUser user;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_volunteer);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        initViews();
        setNavigationElements();
        setUserData();


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeVolunteerFragment()).commit();
            navigationView.setCheckedItem(R.id.user_menu_home);
        }
    }



 private void setUserData(){
  DocumentReference documentReference = mFirestore.collection("volunteers").document(user.getEmail());

     if (user != null){
        volunteerEmail.setText(user.getEmail());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null){
                        String username = documentSnapshot.getString("volunteerUsername");
                        String phone = documentSnapshot.getString("volunteerPhone");
                        String firstName = documentSnapshot.getString("volunteerFirstName");
                        String lastName = documentSnapshot.getString("volunteerLastName");
                        volunteerUsername.setText(username);
                        volunteerPhone.setText(phone);
                        volunteerFirstName.setText(firstName);
                        volunteerLastName.setText(lastName);
                    }
                }
            }
        });
     }
 }

    private void initViews() {
        mToolbar = findViewById(R.id.volunteer_toolbar);
        volunteerDrawer = findViewById(R.id.volunteer_drawer);
        navigationView = findViewById(R.id.nav_view_volunteer);
    }

    private void setNavigationElements() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        volunteerUsername = header.findViewById(R.id.user_username_text_view);
        volunteerEmail = header.findViewById(R.id.user_email_text_view);
        volunteerPhone = header.findViewById(R.id.user_phone_text_view);
        volunteerFirstName = header.findViewById(R.id.user_first_name);
        volunteerLastName = header.findViewById(R.id.user_last_name);


        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, volunteerDrawer, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        volunteerDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
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
            case R.id.user_menu_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeVolunteerFragment()).commit();
                break;
            case R.id.user_menu_donate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DonateVolunteerFragment()).commit();
                break;
            case R.id.user_menu_configuration:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfigurationVolunteerFragment()).commit();
                break;
            case R.id.user_menu_logout:
                Intent loginIntent = new Intent(HomeVolunteer.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.user_menu_contact:
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



