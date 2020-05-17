package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless;

import com.google.android.gms.maps.model.LatLng;

public class Homeless {
    private String image;
    private String homelessUsername;
    private String homelessPhoneNumber;
    private String homelessBirthday;
    private String homelessLifeHistory;
    private String homelessAddress;
    private String homelessSchedule;
    private String homelessNeed;

    public Homeless(){

    }

    public Homeless(String image, String homelessUsername, String phone, String birthday, String lifeHistory, String locationAddress, String schedule, String need){
        this.image = image;
        this.homelessUsername = homelessUsername;
        this.homelessPhoneNumber = phone;
        this.homelessBirthday =birthday;
        this.homelessLifeHistory = lifeHistory;
        this.homelessAddress = locationAddress;
        this.homelessSchedule = schedule;
        this.homelessNeed = need;

    }

    public Homeless(String profileImageResource, String homelessUsername, String need, String locationAddress){
        this.image = profileImageResource;
        this.homelessUsername = homelessUsername;
        this.homelessNeed = need;
        this.homelessAddress = locationAddress;

    }

    public Homeless(String homelessUsername, LatLng locationCoordinates){
        this.homelessUsername = homelessUsername;
    }

    public String getImage(){
        return image;
    }

    public String getHomelessUsername() {
        return homelessUsername;
    }

    public String getHomelessPhoneNumber() {
        return homelessPhoneNumber;
    }

    public String getHomelessBirthday() {
        return homelessBirthday;
    }

    public String getHomelessLifeHistory() {
        return homelessLifeHistory;
    }

    public String getHomelessAddress() {
        return homelessAddress;
    }

    public String getHomelessNeed() {
        return homelessNeed;
    }

    public String getHomelessSchedule() {
        return homelessSchedule;
    }

    public void setImage(String image){
        this.image = image;
    }

}
