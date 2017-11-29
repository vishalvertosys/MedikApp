package medik247.vs.com.medik;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Indosurplus on 5/17/2017.
 */

public class ModelMarker {
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ModelMarker(double lat, double longt, String title) {

        this.lat = lat;
        this.longt = longt;
        this.title = title;
    }

    double lat;
    double longt;
    String title;
}
