package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary;

import android.app.Activity;
import android.graphics.Color;
import android.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

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

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildBioDonorVolunteer;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildCommand;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildTransactionsDonor;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.createPOI;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.descriptionDonorVolunteer;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.searchText;

public class DonorRecyclerView {

    private static Map<String, String> donorInfo = new HashMap<>();

    public static void setDonorRecyclerView(RecyclerView recyclerView, String city, FirebaseFirestore mFirestore, SearchView searchView, Activity activity) {

        final List<LgUser> users = new ArrayList<>();

        mFirestore.collection("donors").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
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
                            final String personallyDonations = document.getString("personallyDonation");
                            final String throughVolunteerDonations = document.getString("throughVolunteerDonations");


                            final LgUser user = new LgUser(username, Color.WHITE, latitude, longitude, location, email, phone, firstName, lastName, personallyDonations, throughVolunteerDonations);
                            users.add(user);


                            final LgUserAdapter lgUserAdapter = new LgUserAdapter(users);
                            searchText(lgUserAdapter, searchView);
                            recyclerView.setAdapter(lgUserAdapter);

                            lgUserAdapter.setOnItemClickListener(new LgUserAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    personallyTransactions(users.get(position).getEmail(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getEmail(), mFirestore);
                                    String description = descriptionDonorVolunteer(users.get(position).getEmail(), users.get(position).getLocation());
                                    POIController.cleanKmls();
                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    POIController.getInstance().moveToPOI(userPoi, null);

                                    POIController.getInstance().showPlacemark(userPoi,null, "https://i.ibb.co/Bg4Lnvk/donor-icon.png", "placemarks/donors");
                                    POIController.getInstance().showBalloon(userPoi, null, description,"donor", "balloons/basic/donors");
                                    POIController.getInstance().sendBalloon(userPoi, null, "balloons/basic/donors");
                                }

                                @Override
                                public void onBioClick(int position) {
                                    personallyTransactions(users.get(position).getEmail(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getEmail(), mFirestore);

                                    POIController.cleanKmls();
                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    POIController.getInstance().moveToPOI(userPoi, null);

                                    POIController.getInstance().showPlacemark(userPoi,null, "https://i.ibb.co/Bg4Lnvk/donor-icon.png", "placemarks/donors");
                                    POIController.getInstance().showBalloon(userPoi, null, buildBioDonorVolunteer(users.get(position).getFirstName(), users.get(position).getLastName(), users.get(position).getPhone(), users.get(position).getEmail(), users.get(position).getLocation()), "donor", "balloons/bio/donors");
                                    POIController.getInstance().sendBalloon(userPoi, null, "balloons/bio/donors");
                                }

                                @Override
                                public void onTransactionClick(int position) {
                                    personallyTransactions(users.get(position).getEmail(), mFirestore);
                                    throughVolunteerTransactions(users.get(position).getEmail(), mFirestore);


                                    POIController.cleanKmls();
                                    POI userPoi = createPOI(users.get(position).getUsername(), users.get(position).getLatitude(), users.get(position).getLongitude());
                                    POIController.getInstance().moveToPOI(userPoi, null);

                                    POIController.getInstance().showPlacemark(userPoi,null, "https://i.ibb.co/Bg4Lnvk/donor-icon.png", "placemarks/donors");
                                    POIController.getInstance().showBalloon(userPoi, null, buildTransactionsDonor(users.get(position).getFirstName(), users.get(position).getLastName(), users.get(position).getPhone(), users.get(position).getEmail(), users.get(position).getLocation(), users.get(position).getPersonallyDonations(), users.get(position).getThroughVolunteerDonation()),"donor", "balloons/transactions/donors");
                                    POIController.getInstance().sendBalloon(userPoi, null, "balloons/transactions/donors");
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
                });

    }


    public static void personallyTransactions(String email, FirebaseFirestore mFirestore){

        mFirestore.collection("personallyDonations").whereEqualTo("donorEmail",email )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String personallyDonation = String.valueOf(task.getResult().size());
                        donorInfo.put("personallyDonation", personallyDonation);
                        mFirestore.collection("donors").document(email).set(donorInfo, SetOptions.merge());

                    }
                });
    }


    public static void throughVolunteerTransactions(String email, FirebaseFirestore mFirestore){

        mFirestore.collection("throughVolunteerDonations").whereEqualTo("donorEmail",email )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String throughVolunteerDonations = String.valueOf(task.getResult().size());
                        donorInfo.put("throughVolunteerDonations", throughVolunteerDonations);
                        mFirestore.collection("donors").document(email).set(donorInfo, SetOptions.merge());
                    }
                });
    }


    }
