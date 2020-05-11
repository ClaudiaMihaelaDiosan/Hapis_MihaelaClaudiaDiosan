package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.MainActivity;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;

public class StatisticsActivity extends MainActivity {

    MaterialButton backLoginBtn;

    /*TextViews*/
    TextView homelessNumberTV;
    TextView donorNumberTV;
    TextView volunteerNumberTV;
    TextView foodStatisticsTV;
    TextView clothesStatisticsTV;
    TextView workStatisticsTV;
    TextView lodgingStatisticsTV;
    TextView hygieneProductsStatisticsTV;
    TextView personalHelpSTatisticsTV;
    TextView throughVolunteerStatisticsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();


        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(StatisticsActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void initViews(){
        backLoginBtn = findViewById(R.id.statistics_back_login);
        homelessNumberTV = findViewById(R.id.homeless_number_statistics);
        donorNumberTV = findViewById(R.id.donor_number_statistics);
        volunteerNumberTV = findViewById(R.id.volunteer_number_statistics);
        foodStatisticsTV = findViewById(R.id.food_statistics);
        clothesStatisticsTV = findViewById(R.id.clothes_statistics);
        workStatisticsTV = findViewById(R.id.work_statistics);
        lodgingStatisticsTV = findViewById(R.id.lodgind_statistics);
        hygieneProductsStatisticsTV = findViewById(R.id.hygiene_products_statistics);
        personalHelpSTatisticsTV = findViewById(R.id.statistics_personal_help);
        throughVolunteerStatisticsTV = findViewById(R.id.statistics_volunteer_help);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
