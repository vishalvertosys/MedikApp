package medik247.vs.com.medik;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * The App lat lng class.
 */
public class AppLatLng implements Serializable
{
    /**
     * The Latitude.
     */
    private double latitude;
    /**
     * The Longitude.
     */
    private double longitude;

    /**
     * Instantiates a new App lat lng.
     *
     * @param latLng the lat lng
     */
    public AppLatLng(LatLng latLng)
    {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    /**
     * Gets latitude instance.
     *
     * @return the latitude
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * Gets longitude instance.
     *
     * @return the longitude
     */
    public double getLongitude()
    {
        return longitude;
    }
}