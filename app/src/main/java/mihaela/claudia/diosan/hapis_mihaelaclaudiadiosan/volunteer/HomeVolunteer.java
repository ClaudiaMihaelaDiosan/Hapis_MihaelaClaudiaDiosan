package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class HomeVolunteer extends AppCompatActivity {

    private DrawerLayout volunteerDrawer;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_volunteer);

        volunteerDrawer = findViewById(R.id.volunteer_drawer);
        mToggle = new ActionBarDrawerToggle(this, volunteerDrawer, R.string.open_navigation_drawer_volunteer, R.string.close_navigation_drawer_volunteer);
        volunteerDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (mToggle.onOptionsItemSelected(menuItem)){
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}



