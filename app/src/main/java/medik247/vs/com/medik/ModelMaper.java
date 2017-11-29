package medik247.vs.com.medik;

/**
 * Created by Indosurplus on 5/23/2017.
 */

public class ModelMaper {
    double lat;
    double lng;
    String uid;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ModelMaper(double lat, double lng, String uid, String title) {

        this.lat = lat;
        this.lng = lng;
        this.uid = uid;
        this.title = title;
    }
}
