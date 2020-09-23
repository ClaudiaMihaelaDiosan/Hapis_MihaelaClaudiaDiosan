package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary;

import android.app.Activity;
import android.graphics.Color;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters.LgUserAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.logic.LgUser;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.tasks.VisitPoiTask;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildBioDonorVolunteer;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildCommand;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildTransactionsVolunteer;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.createPOI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.descriptionDonorVolunteer;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.searchText;

public class VolunteerRecyclerView {

    public static void setVolunteerRecyclerView(RecyclerView recyclerView, String city, FirebaseFirestore mFirestore, SearchView searchView, Activity activity) {

        final List<LgUser> users = new ArrayList<>();
        mFirestore.collection("volunteers").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                final String username = document.getString("username");
                                final String latitude = document.getString("latitude");
                                final String longitude = document.getString("longitude");
                                final String phone = document.getString("phone");
                                final String email = document.getString("email");
                                final String firstName = document.getString("firstName");
                                final String lastName = document.getString("lastName");
                                final String location = document.getString("address");
                                final String homelessCreated = document.getString("homelessCreated");


                                final LgUser user = new LgUser(username, latitude, longitude, location, email, phone, firstName, lastName, homelessCreated, Color.WHITE);
                                users.add(user);

                                final LgUserAdapter lgUserAdapter = new LgUserAdapter(users);
                                searchText(lgUserAdapter, searchView);
                                recyclerView.setAdapter(lgUserAdapter);

                                lgUserAdapter.setOnItemClickListener(new LgUserAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                        String description = descriptionDonorVolunteer(users.get(position).getEmail(), users.get(position).getLocation());
                                        POIController.cleanKmls();
                                        POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                        POIController.getInstance().moveToPOI(userPoi, null);

                                        POIController.getInstance().showPlacemark(userPoi,null, "https://i.ibb.co/xf1S6cn/volunteer-icon.png", "placemarks/volunteers");
                                        POIController.getInstance().showBalloon(userPoi, null, description,"volunteer", "balloons/basic/volunteers");
                                        POIController.getInstance().sendBalloon(userPoi, null, "balloons/basic/volunteers");
                                    }

                                    @Override
                                    public void onBioClick(int position) {

                                        POIController.cleanKmls();
                                        POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                        POIController.getInstance().moveToPOI(userPoi, null);

                                        POIController.getInstance().showPlacemark(userPoi,null, "https://i.ibb.co/xf1S6cn/volunteer-icon.png", "placemarks/volunteers");
                                        POIController.getInstance().showBalloon(userPoi, null, buildBioDonorVolunteer(users.get(position).getFirstName(), users.get(position).getLastName(), users.get(position).getPhone(), users.get(position).getEmail(), users.get(position).getLocation()), "volunteer", "balloons/bio/volunteers");
                                        POIController.getInstance().sendBalloon(userPoi, null, "balloons/bio/volunteers");
                                    }

                                    @Override
                                    public void onTransactionClick(int position) {

                                        POIController.cleanKmls();
                                        POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                        POIController.getInstance().moveToPOI(userPoi, null);

                                        POIController.getInstance().showPlacemark(userPoi,null, "https://i.ibb.co/xf1S6cn/volunteer-icon.png", "placemarks/volunteers");
                                        POIController.getInstance().showBalloon(userPoi, null, buildTransactionsVolunteer(users.get(position).getFirstName(), users.get(position).getLastName(), users.get(position).getPhone(), users.get(position).getEmail(), users.get(position).getLocation(), users.get(position).getHomelessCreated()),"volunteer", "balloons/transactions/volunteers");
                                        POIController.getInstance().sendBalloon(userPoi, null, "balloons/transactions/volunteers");
                                    }

                                    @Override
                                    public void onOrbitClick(int position) {
                                        POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                        String command = buildCommand(userPoi);
                                        VisitPoiTask visitPoiTask = new VisitPoiTask(command, userPoi, true,activity, activity);
                                        visitPoiTask.execute();

                                    }
                                });
                            }
                        }
                    }
                });
    }
}
