package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.auxiliary;

import android.widget.SearchView;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters.LgUserAdapter;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgNavigation.POI;

public class HelpUserListClass {

    public static String description(String birthday, String location, String schedule, String need) {
        return  "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Birthday: </b> " + birthday + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" +
                "<p> <b> Schedule: </b> " + schedule + "</p>\n" +
                "<p> <b> Most important need: </b> " + need + "</p>\n";
    }

    public static String descriptionDonorVolunteer(String email, String location){
        return  "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Email: </b> " + email + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" ;
    }


    public static String buildBio(String lifeHistory, String birthday, String location, String schedule, String need) {
        return "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Birthday: </b> " + birthday + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" +
                "<p> <b> Schedule: </b> " + schedule + "</p>\n" +
                "<p> <b> Most important need: </b> " + need + "</p>\n" +
                "<h2><b> Life history </b> </h2>\n" +
                "<p> " + lifeHistory + "</p>\n";
    }

    public static String buildBioDonorVolunteer(String firstName, String lastName, String phone, String email, String location){
        return   "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Email: </b> " + email + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" +
                "<h2> <b> Extra Info</b></h2>\n" +
                "<p> <b> First Name: </b> " + firstName + "</p>\n" +
                "<p> <b> Last Name: </b> " + lastName + "</p>\n" +
                "<p> <b> Phone Number: </b> " + phone + "</p>\n" ;
    }

    public static String buildTransactions(String lifeHistory, String birthday, String location, String schedule, String need, String personallyDonations, String throughVolunteerDonations) {

        return "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Birthday: </b> " + birthday + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" +
                "<p> <b> Schedule: </b> " + schedule + "</p>\n" +
                "<p> <b> Most important need: </b> " + need + "</p>\n" +
                "<h2><b> Life history </b> </h2>\n" +
                "<p> " + lifeHistory + "</p>\n" +
                "<h2><b> Transactions </b> </h2>\n" +
                "<p><b> Personally Donations: </b> " + personallyDonations + "</p>\n" +
                "<p><b> Through Volunteer Donations: </b> " + throughVolunteerDonations + "</p>\n";
    }

    public static String buildTransactionsDonor(String firstName, String lastName, String phone, String email, String location, String personallyDonations, String throughVolunteerDonations){

        return  "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Email: </b> " + email + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" +
                "<h2> <b> Extra Info</b></h2>\n" +
                "<p> <b> First Name: </b> " + firstName + "</p>\n" +
                "<p> <b> Last Name: </b> " + lastName + "</p>\n" +
                "<p> <b> Phone Number: </b> " + phone + "</p>\n" +
                "<h2><b> Transactions </b> </h2>\n" +
                "<p><b> Personally Donations: </b> " + personallyDonations + "</p>\n" +
                "<p><b> Through Volunteer Donations: </b> " + throughVolunteerDonations + "</p>\n";
    }

    public static String buildTransactionsVolunteer(String firstName, String lastName, String phone, String email, String location, String createdHomeless){

        return  "<h2> <b> Basic Info</b></h2>\n" +
                "<p> <b> Email: </b> " + email + "</p>\n" +
                "<p> <b> Location: </b> " + location + "</p>\n" +
                "<h2> <b> Extra Info</b></h2>\n" +
                "<p> <b> First Name: </b> " + firstName + "</p>\n" +
                "<p> <b> Last Name: </b> " + lastName + "</p>\n" +
                "<p> <b> Phone Number: </b> " + phone + "</p>\n" +
                "<h2><b> Transactions </b> </h2>\n" +
                "<p><b> Created homeless profiles: </b> " +  createdHomeless + "</p>\n";
    }

    public static String buildCommand(POI poi) {
        return "echo 'flytoview=<gx:duration>3</gx:duration><gx:flyToMode>smooth</gx:flyToMode><LookAt><longitude>" + poi.getLongitude() + "</longitude>" +
                "<latitude>" + poi.getLatitude() + "</latitude>" +
                "<altitude>" + poi.getAltitude() + "</altitude>" +
                "<heading>" + poi.getHeading() + "</heading>" +
                "<tilt>" + poi.getTilt() + "</tilt>" +
                "<range>" + poi.getRange() + "</range>" +
                "<gx:altitudeMode>" + poi.getAltitudeMode() + "</gx:altitudeMode>" +
                "</LookAt>' > /tmp/query.txt";
    }


    public static void searchText(final LgUserAdapter lgUserAdapter, SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lgUserAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    public static POI createPOI(String name, String latitude, String longitude) {

        POI poi = new POI()
                .setName(name)
                .setLongitude(Double.parseDouble(longitude))
                .setLatitude(Double.parseDouble(latitude))
                .setAltitude(0.0d)
                .setHeading(0.0d)
                .setTilt(70.0d)
                .setRange(200.0d)
                .setAltitudeMode("relativeToSeaFloor");

        return poi;
    }
}
