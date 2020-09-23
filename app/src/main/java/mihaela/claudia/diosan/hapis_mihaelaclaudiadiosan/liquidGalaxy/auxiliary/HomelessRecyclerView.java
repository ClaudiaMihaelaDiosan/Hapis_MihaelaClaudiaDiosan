package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.widget.SearchView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters.LgUserAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.logic.LgUser;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.tasks.VisitPoiTask;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildBio;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildCommand;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildTransactions;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.createPOI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.description;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.searchText;

public class HomelessRecyclerView {

    private static Map<String, String> homelessInfo = new HashMap<>();


    public static void setHomelessReyclerView(RecyclerView recyclerView, String city, FirebaseFirestore mFirestore, SearchView searchView, Activity activity) {

        final List<LgUser> users = new ArrayList<>();

        mFirestore.collection("homeless").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            final String username = document.getString("homelessUsername");
                            final String latitude = document.getString("homelessLatitude");
                            final String longitude = document.getString("homelessLongitude");
                            final String birthday = document.getString("homelessBirthday");
                            final String location = document.getString("homelessAddress");
                            final String schedule = document.getString("homelessSchedule");
                            final String need = document.getString("homelessNeed");
                            final String lifeHistory = document.getString("homelessLifeHistory");
                            final String personallyDonations = document.getString("personallyDonations");
                            final String throughVolunteerDonations = document.getString("throughVolunteerDonations");
                            final String image = document.getString("image");


                            final LgUser user = new LgUser(username, Color.WHITE, latitude, longitude, image, birthday, location, schedule, need, lifeHistory, personallyDonations, throughVolunteerDonations);
                            users.add(user);


                            final LgUserAdapter lgUserAdapter = new LgUserAdapter(users);
                            searchText(lgUserAdapter, searchView);
                            recyclerView.setAdapter(lgUserAdapter);

                            lgUserAdapter.setOnItemClickListener(new LgUserAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    personallyTransactions(users.get(position).getUsername(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getUsername(), mFirestore);

                                    String description = description(users.get(position).getBirthday(), users.get(position).getLocation(), users.get(position).getSchedule(), users.get(position).getNeed());
                                    POIController.cleanKmls();
                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    POIController.getInstance().moveToPOI(userPoi, null);

                                    POIController.downloadProfilePhoto(userPoi.getName(), users.get(position).getImage());
                                    POIController.getInstance().showPlacemark(userPoi, null, "https://i.ibb.co/1nsNbxr/homeless-icon.png", "placemarks/homeless");
                                    POIController.getInstance().showBalloon(userPoi, null, description, users.get(position).getUsername(), "balloons/basic/homeless");
                                    POIController.getInstance().sendBalloon(userPoi, null, "balloons/basic/homeless");
                                }

                                @Override
                                public void onBioClick(int position) {
                                    personallyTransactions(users.get(position).getUsername(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getUsername(), mFirestore);

                                    POIController.cleanKmls();
                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    POIController.getInstance().moveToPOI(userPoi, null);

                                    POIController.getInstance().showPlacemark(userPoi, null, "https://i.ibb.co/1nsNbxr/homeless-icon.png", "placemarks/homeless");
                                    POIController.getInstance().showBalloon(userPoi, null, buildBio(users.get(position).getLifeHistory(), users.get(position).getBirthday(), users.get(position).getLocation(), users.get(position).getSchedule(), users.get(position).getNeed()), users.get(position).getUsername(), "balloons/bio/homeless");
                                    POIController.getInstance().sendBalloon(userPoi, null, "balloons/bio/homeless");

                                }

                                @Override
                                public void onTransactionClick(int position) {
                                    personallyTransactions(users.get(position).getUsername(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getUsername(), mFirestore);


                                    POIController.cleanKmls();
                                    personallyTransactions(users.get(position).getUsername(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getUsername(), mFirestore);

                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    POIController.getInstance().moveToPOI(userPoi, null);

                                    POIController.getInstance().showPlacemark(userPoi, null, "https://i.ibb.co/1nsNbxr/homeless-icon.png", "placemarks/homeless");
                                    POIController.getInstance().showBalloon(userPoi, null, buildTransactions(users.get(position).getLifeHistory(), users.get(position).getBirthday(), users.get(position).getLocation(), users.get(position).getSchedule(), users.get(position).getNeed(), users.get(position).getPersonallyDonations(), users.get(position).getThroughVolunteerDonation()), users.get(position).getUsername(), "balloons/transactions/homeless");
                                    POIController.getInstance().sendBalloon(userPoi, null, "balloons/transactions/homeless");

                                }

                                @Override
                                public void onOrbitClick(int position) {
                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    String command = buildCommand(userPoi);
                                    VisitPoiTask visitPoiTask = new VisitPoiTask(command, userPoi, true, activity, activity);
                                    visitPoiTask.execute();
                                }
                            });

                        }
                    }
                });
    }








    public static void personallyTransactions(String homelessUsername, FirebaseFirestore mFirestore) {

        mFirestore.collection("personallyDonations").whereEqualTo("donatesTo", homelessUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String personallyDonations = String.valueOf(task.getResult().size());
                        homelessInfo.put("personallyDonations", personallyDonations);
                        mFirestore.collection("homeless").document(homelessUsername).set(homelessInfo, SetOptions.merge());

                    }
                });
    }

    public static void throughVolunteerTransactions(String homelessUsername, FirebaseFirestore mFirestore) {

        mFirestore.collection("throughVolunteerDonations").whereEqualTo("donatesTo", homelessUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String throughVolunteerDonations = String.valueOf(task.getResult().size());
                        homelessInfo.put("throughVolunteerDonations", throughVolunteerDonations);
                        mFirestore.collection("homeless").document(homelessUsername).set(homelessInfo, SetOptions.merge());

                    }
                });
    }


}