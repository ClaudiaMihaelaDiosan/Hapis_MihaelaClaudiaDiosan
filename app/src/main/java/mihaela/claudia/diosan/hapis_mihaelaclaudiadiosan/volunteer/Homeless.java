package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

public class Homeless {
    private int profileImageResource;
    private String username;
    private String phone;
    private String birthday;
    private String lifeHistory;
    private String locationAddress;
    private String schedule;
    private String need;

    public Homeless(int profileImageResource, String username, String phone, String birthday, String lifeHistory, String locationAddress, String schedule, String need){
        this.profileImageResource = profileImageResource;
        this.username = username;
        this.phone = phone;
        this.birthday =birthday;
        this.lifeHistory = lifeHistory;
        this.locationAddress = locationAddress;
        this.schedule = schedule;
        this.need = need;

    }

    public int getProfileImageResource(){
        return profileImageResource;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLifeHistory() {
        return lifeHistory;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getNeed() {
        return need;
    }

    public String getSchedule() {
        return schedule;
    }

}
