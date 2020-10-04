package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGCommand;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGConnectionManager;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POIController;

import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpBuildingStatistics.buildCityStatistics;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary.HelpUserListClass.buildTransactions;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection.LGCommand.CRITICAL_MESSAGE;
import static mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI.createPOI;

public class HelpCityClass {

    private static Map<String,String> homelessInfo = new HashMap<>();
    private static Map<String,String> volunteerInfo = new HashMap<>();


    public static void showAllHomeless(String city, FirebaseFirestore mFirestore, String ip){

        mFirestore.collection("homeless").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            final String name = document.getString("homelessUsername");
                            final String longitude = document.getString("homelessLongitude");
                            final String latitude = document.getString("homelessLatitude");

                            POI Homeless = createPOI(name,latitude, longitude, "0.0d" );
                            POIController.getInstance().sendPlacemark(Homeless, null, ip, "placemarks/homeless");
                            POIController.getInstance().showPlacemark(Homeless,null, "https://i.ibb.co/1nsNbxr/homeless-icon.png", "placemarks/homeless");
                        }}
                });
    }

    public static void showAllDonors(String city, FirebaseFirestore mFirestore, String ip ){

        mFirestore.collection("donors").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            final String name = document.getString("username");
                            final String longitude = document.getString("longitude");
                            final String latitude = document.getString("latitude");
                            final String email = document.getString("email");

                            personallyTransactionsD(email, mFirestore);
                            throughVolunteerTransactionsD(email, mFirestore);

                            POI Homeless = new POI()
                                    .setName(name)
                                    .setLongitude(Double.parseDouble(longitude))
                                    .setLatitude(Double.parseDouble(latitude))
                                    .setAltitude(0.0d)
                                    .setHeading(0d)
                                    .setTilt(40.0d)
                                    .setRange(100.0d)
                                    .setAltitudeMode("relativeToSeaFloor");

                            POIController.getInstance().sendPlacemark(Homeless, null, ip, "placemarks/donors");
                            POIController.getInstance().showPlacemark(Homeless,null, "https://i.ibb.co/Bg4Lnvk/donor-icon.png", "placemarks/donors");
                        }}
                });
    }

    private static void personallyTransactionsD(String email, FirebaseFirestore mFirestore){

        mFirestore.collection("personallyDonations").whereEqualTo("donorEmail",email )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String personallyDonation = String.valueOf(task.getResult().size());
                        homelessInfo.put("personallyDonations", personallyDonation);
                        mFirestore.collection("donors").document(email).set(homelessInfo, SetOptions.merge());

                    }
                });
    }

    private static  void throughVolunteerTransactionsD(String email, FirebaseFirestore mFirestore){

        mFirestore.collection("throughVolunteerDonations").whereEqualTo("donorEmail",email )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String throughVolunteerDonations = String.valueOf(task.getResult().size());
                        homelessInfo.put("throughVolunteerDonations", throughVolunteerDonations);
                        mFirestore.collection("donors").document(email).set(homelessInfo, SetOptions.merge());
                    }
                });
    }


    public static void showAllVolunteers(String city, FirebaseFirestore mFirestore,String ip) {

        mFirestore.collection("volunteers").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            final String name = document.getString("username");
                            final String longitude = document.getString("longitude");
                            final String latitude = document.getString("latitude");
                            final String email = document.getString("email");

                            homelessCreated(email, mFirestore);


                            POI Homeless = new POI()
                                    .setName(name)
                                    .setLongitude(Double.parseDouble(longitude))
                                    .setLatitude(Double.parseDouble(latitude))
                                    .setAltitude(0.0d)
                                    .setHeading(0d)
                                    .setTilt(40.0d)
                                    .setRange(100.0d)
                                    .setAltitudeMode("relativeToSeaFloor");
                            POIController.getInstance().sendPlacemark(Homeless, null, ip, "placemarks/volunteers");
                            POIController.getInstance().showPlacemark(Homeless,null, "https://i.ibb.co/xf1S6cn/volunteer-icon.png", "placemarks/volunteers");
                        }}
                });
    }

    public static void homelessCreated(String email, FirebaseFirestore mFirestore){

        mFirestore.collection("homeless").whereEqualTo("volunteerEmail",email )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String homelessCreated = String.valueOf(task.getResult().size());
                        volunteerInfo.put("homelessCreated", homelessCreated);
                        mFirestore.collection("volunteers").document(email).set(volunteerInfo, SetOptions.merge());

                    }
                });
    }

    public static void cleanKmls(String homeless_slave, String local_statistics_slave, String global_statistics_slave){

        POIController.cleanKmls();
        POIController.cleanKmlSlave(homeless_slave);
        POIController.cleanKmlSlave(local_statistics_slave);
        POIController.cleanKmlSlave(global_statistics_slave);


    }

    public static void showLocalStatistics(String city, FirebaseFirestore mFirestore, String local_statistics_slave){
        mFirestore.collection("cities").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            final String city1 = document.getString("city");
                            final String cityWS = document.getString("cityWS");
                            final String latitude = document.getString("latitude");
                            final String longitude = document.getString("longitude");
                            final String altitude = document.getString("altitude");
                            final String homeless = document.getString("homelessNumber");
                            final String donors = document.getString("donorsNumber");
                            final String volunteers = document.getString("volunteersNumber");
                            final String foodSt = document.getString("foodSt");
                            final String clothesSt = document.getString("clothesSt");
                            final String workSt = document.getString("workSt");
                            final String lodgingSt = document.getString("lodgingSt");
                            final String hygieneSt = document.getString("hygieneSt");
                            final String image = document.getString("image");


                            POI cityPOI = createPOI(cityWS, latitude, longitude, altitude);
                            String sentence = "cd /var/www/html/hapis/balloons/statistics/cities/ ;curl -o " + cityPOI.getName() + " " + image;
                            LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, CRITICAL_MESSAGE, null));
                            POIController.getInstance().showBalloonOnSlave(cityPOI, null, buildCityStatistics(city1, homeless, donors, volunteers, foodSt, clothesSt, workSt, lodgingSt, hygieneSt), "http://lg1:81/hapis/balloons/statistics/cities/", cityPOI.getName(), local_statistics_slave);

                        }
                    }
                });
    }

    public static void showHomelessInfo(String city, FirebaseFirestore mFirestore, String homeless_slave ){
        mFirestore.collection("homeless").whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            final String username = document.getString("homelessUsername");

                            personallyTransactions(username, mFirestore);
                            throughVolunteerTransactions(username, mFirestore);

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

                            POI userPoi = createPOI(username, latitude, longitude,"0.0d");
                            POIController.downloadProfilePhoto(userPoi.getName(), image);
                            POIController.getInstance().showBalloonOnSlave(userPoi, null, buildTransactions(lifeHistory,birthday, location, schedule, need, personallyDonations, throughVolunteerDonations),"http://lg1:81/hapis/balloons/transactions/homeless/",username,homeless_slave);
                            String sentence = "sleep 20";
                            LGConnectionManager.getInstance().addCommandToLG(new LGCommand(sentence, CRITICAL_MESSAGE, null)); }

                    }
                });

    }

    public static void personallyTransactions(String homelessUsername, FirebaseFirestore mFirestore){

        mFirestore.collection("personallyDonations").whereEqualTo("donatesTo",homelessUsername )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String personallyDonations = String.valueOf(task.getResult().size());
                        homelessInfo.put("personallyDonations", personallyDonations);
                        mFirestore.collection("homeless").document(homelessUsername).set(homelessInfo, SetOptions.merge());

                    }
                });
    }


    public static void throughVolunteerTransactions(String homelessUsername,  FirebaseFirestore mFirestore){

        mFirestore.collection("throughVolunteerDonations").whereEqualTo("donatesTo",homelessUsername )
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String throughVolunteerDonations = String.valueOf(task.getResult().size());
                        homelessInfo.put("throughVolunteerDonations", throughVolunteerDonations);
                        mFirestore.collection("homeless").document(homelessUsername).set(homelessInfo, SetOptions.merge());

                    }
                });
    }


    public static String buildCommand(POI poi) {
        return "echo 'flytoview=<gx:duration>3</gx:duration><gx:flyToMode>smooth</gx:flyToMode><LookAt><longitude>" + poi.getLongitude() + "</longitude>" +
                "<latitude>" + poi.getLatitude() + "</latitude>" +
                "<altitude>" + poi.getAltitude() + "</altitude>" +
                "<heading>" + poi.getHeading() + "</heading>" +
                "<tilt>" + poi.getTilt() + "</tilt>" +
                "<range>" + poi.getRange() + "</range>" +
                "<gx:altitudeMode>" + poi.getAltitudeMode() + "</gx:altitudeMode>" +
                "</LookAt>' > /tmp/query.txt ; sleep 25";
    }

}
