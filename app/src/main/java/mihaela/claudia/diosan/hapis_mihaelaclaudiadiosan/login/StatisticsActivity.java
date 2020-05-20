package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mFirestore = FirebaseFirestore.getInstance();

        initViews();

        getHomelessNumber();
        getDonorsNumber();
        getVolunteersNumber();
        getFood();
        getClothes();
        getWork();
        getLodging();
        getHygiene();


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
    }

    private void getHomelessNumber(){


        mFirestore.collection("homeless").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            homelessNumberTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getDonorsNumber(){
        mFirestore.collection("donors").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            donorNumberTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getVolunteersNumber(){
        mFirestore.collection("volunteers").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            volunteerNumberTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getFood(){
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", getString(R.string.chip_food))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            foodStatisticsTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getClothes(){
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", getString(R.string.chip_clothes))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            clothesStatisticsTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getWork(){
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", getString(R.string.chip_work))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            workStatisticsTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getLodging(){
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", getString(R.string.chip_lodging))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            lodgingStatisticsTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    private void getHygiene(){
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", getString(R.string.chip_hygiene_products))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            hygieneProductsStatisticsTV.setText(String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }




    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
    }

}
