package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class HelpBuildingStatistics {
    private static Map<String, String> cityInfo = new HashMap<>();
    private static  Map<String, String> globalInfo = new HashMap<>();

    public static String buildGlobalStatistics(String cities, String homeless, String donors, String volunteers, String food, String clothes, String work, String lodging, String hygiene_products, String personallyStatistics, String throughVolunteerStatistics) {
        return "<h2> <b> CITIES</b></h2>\n" +
                "<p> <b> Total cities: </b> " + cities + "</p>\n" +
                "<h2> <b> USERS</b></h2>\n" +
                "<p> <b> Total homeless: </b> " + homeless + "</p>\n" +
                "<p> <b> Total donors: </b> " + donors + "</p>\n" +
                "<p> <b> Total volunteers: </b> " + volunteers + "</p>\n" +
                "<h2> <b> NEEDS</b></h2>\n" +
                "<p> <b> Food: </b> " + food + "</p>\n" +
                "<p> <b> Clothes: </b> " + clothes + "</p>\n" +
                "<p> <b> Work: </b> " + work + "</p>\n" +
                "<p> <b> Lodging: </b> " + lodging + "</p>\n" +
                "<p> <b> Hygiene products: </b> " + hygiene_products + "</p>\n" +
                "<h2> <b> DONATIONS</b></h2>\n" +
                "<p> <b> Personally Donations: </b> " + personallyStatistics + "</p>\n" +
                "<p> <b> Through Volunteer Donations: </b> " + throughVolunteerStatistics + "</p>\n";
    }

    public static String buildCityStatistics(String city, String homeless, String donors, String volunteers, String food, String clothes, String work, String lodging, String hygiene_products) {

        return "<h2> <b> Local statistics from: </b> " + city + "</h2>\n" +
                "<h2> <b> USERS</b></h2>\n" +
                "<p> <b> Total homeless: </b> " + homeless + "</p>\n" +
                "<p> <b> Total donors: </b> " + donors + "</p>\n" +
                "<p> <b> Total volunteers: </b> " + volunteers + "</p>\n" +
                "<h2> <b> NEEDS</b></h2>\n" +
                "<p> <b> Food: </b> " + food + "</p>\n" +
                "<p> <b> Clothes: </b> " + clothes + "</p>\n" +
                "<p> <b> Work: </b> " + work + "</p>\n" +
                "<p> <b> Lodging: </b> " + lodging + "</p>\n" +
                "<p> <b> Hygiene products: </b> " + hygiene_products + "</p>\n";
    }

    public static void getTotalCities(FirebaseFirestore mFirestore) {

        mFirestore.collection("cities").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String cities = String.valueOf(task.getResult().size());
                            globalInfo.put("cities", cities);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getHomelessNumber(FirebaseFirestore mFirestore) {

        mFirestore.collection("homeless").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String homeless = String.valueOf(task.getResult().size());
                            globalInfo.put("homeless", homeless);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityHomelessNumber(String city, FirebaseFirestore mFirestore) {

        mFirestore.collection("homeless").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String homelessNumber = String.valueOf(task.getResult().size());
                            cityInfo.put("homelessNumber", homelessNumber);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getDonorsNumber(FirebaseFirestore mFirestore) {
        mFirestore.collection("donors").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String donors = String.valueOf(task.getResult().size());
                            globalInfo.put("donors", donors);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityDonorsNumber(String city, FirebaseFirestore mFirestore) {
        mFirestore.collection("donors").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String donorsNumber = String.valueOf(task.getResult().size());
                            cityInfo.put("donorsNumber", donorsNumber);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getVolunteersNumber(FirebaseFirestore mFirestore) {
        mFirestore.collection("volunteers").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String volunteers = String.valueOf(task.getResult().size());
                            globalInfo.put("volunteers", volunteers);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityVolunteersNumber(String city,FirebaseFirestore mFirestore) {
        mFirestore.collection("volunteers").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String volunteersNumber = String.valueOf(task.getResult().size());
                            cityInfo.put("volunteersNumber", volunteersNumber);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }


    public static void getFood(String food, FirebaseFirestore mFirestore) {

        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed",food)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String food = String.valueOf(task.getResult().size());
                            globalInfo.put("food", food);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityFood(String city, String food, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", food)
                .whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String foodSt = String.valueOf(task.getResult().size());
                            cityInfo.put("foodSt", foodSt);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }


    public static void getClothes(String clothes, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", clothes)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String clothes = String.valueOf(task.getResult().size());
                            globalInfo.put("clothes", clothes);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityClothes(String city, String clothes, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", clothes)
                .whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String clothesSt = String.valueOf(task.getResult().size());
                            cityInfo.put("clothesSt", clothesSt);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }


    public static void getWork(String work, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", work )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String work = String.valueOf(task.getResult().size());
                            globalInfo.put("work", work);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityWork(String city, String work, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed",work)
                .whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String workSt = String.valueOf(task.getResult().size());
                            cityInfo.put("workSt", workSt);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }


    public static void getLodging(String lodging, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", lodging)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String lodging = String.valueOf(task.getResult().size());
                            globalInfo.put("lodging", lodging);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityLodging(String city, String lodging, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", lodging)
                .whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String lodgingSt = String.valueOf(task.getResult().size());
                            cityInfo.put("lodgingSt", lodgingSt);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getHygiene(String hygiene, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", hygiene)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String hygieneProducts = String.valueOf(task.getResult().size());
                            globalInfo.put("hygieneProducts", hygieneProducts);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

    public static void getCityHygiene(String city, String hygiene, FirebaseFirestore mFirestore) {
        mFirestore.collection("homeless")
                .whereEqualTo("homelessNeed", hygiene)
                .whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String hygieneSt = String.valueOf(task.getResult().size());
                            cityInfo.put("hygieneSt", hygieneSt);
                            mFirestore.collection("cities").document(city).set(cityInfo, SetOptions.merge());
                        }
                    }
                });
    }


    public static void getPersonallyNumber(FirebaseFirestore mFirestore) {
        mFirestore.collection("personallyDonations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String personallyStatistics = String.valueOf(task.getResult().size());
                            globalInfo.put("personallyStatistics", personallyStatistics);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }


    public static void getThroughVolunteerNumber(FirebaseFirestore mFirestore) {
        mFirestore.collection("throughVolunteerDonations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String throughVolunteerStatistics = String.valueOf(task.getResult().size());
                            globalInfo.put("throughVolunteerStatistics", throughVolunteerStatistics);
                            mFirestore.collection("statistics").document("global").set(globalInfo, SetOptions.merge());
                        }
                    }
                });
    }

}
