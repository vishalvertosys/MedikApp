package medik247.vs.com.medik;

import com.google.android.gms.location.places.Place;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * The App place class.
 */
public class AppPlace implements Serializable
{
    /**
     * The Id.
     */
    private String id;
    /**
     * The Place types.
     */
    private List<Integer> placeTypes;
    /**
     * The Address.
     */
    private String address;
    /**
     * The Locale.
     */
    private Locale locale;
    /**
     * The Name.
     */
    private String name;
    /**
     * The First name.
     */
    private String firstName;
    /**
     * The Secondary name.
     */
    private String secondaryName;
    /**
     * The Lat lng.
     */
    private AppLatLng latLng;
    /**
     * The Phone number.
     */
    private String phoneNumber;
    /**
     * The Rating.
     */
    private float rating;
    /**
     * The Price level.
     */
    private int priceLevel;
    /**
     * The Attributions.
     */
    private String attributions;

    /**
     * Instantiates a new App place.
     *
     * @param place         the place
     * @param firstName     the first name
     * @param secondaryName the secondary name
     */
    public AppPlace(Place place, CharSequence firstName, CharSequence secondaryName)
    {
        id = place.getId();
        placeTypes = place.getPlaceTypes();
        address = place.getAddress() == null ? "" : place.getAddress().toString();
        locale = place.getLocale();
        name = place.getName() == null ? "" : place.getName().toString();
        latLng = new AppLatLng(place.getLatLng());
        phoneNumber = place.getPhoneNumber() == null ? "" : place.getPhoneNumber().toString();
        rating = place.getRating();
        priceLevel = place.getPriceLevel();
        attributions = place.getAttributions() == null ? "" : place.getAttributions().toString();
        this.firstName = firstName.toString();
        this.secondaryName = secondaryName.toString();
    }

    /**
     * Gets id instance.
     *
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Gets place types instance.
     *
     * @return the place types
     */
    public List<Integer> getPlaceTypes()
    {
        return placeTypes;
    }

    /**
     * Gets address instance.
     *
     * @return the address
     */
    public CharSequence getAddress()
    {
        return address;
    }

    /**
     * Gets locale instance.
     *
     * @return the locale
     */
    public Locale getLocale()
    {
        return locale;
    }

    /**
     * Gets name instance.
     *
     * @return the name
     */
    public CharSequence getName()
    {
        return name;
    }

    /**
     * Gets lat lng instance.
     *
     * @return the lat lng
     */
    public AppLatLng getLatLng()
    {
        return latLng;
    }

    /**
     * Gets phone number instance.
     *
     * @return the phone number
     */
    public CharSequence getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Gets rating instance.
     *
     * @return the rating
     */
    public float getRating()
    {
        return rating;
    }

    /**
     * Gets price level instance.
     *
     * @return the price level
     */
    public int getPriceLevel()
    {
        return priceLevel;
    }

    /**
     * Gets attributions instance.
     *
     * @return the attributions
     */
    public CharSequence getAttributions()
    {
        return attributions;
    }

    /**
     * Gets first name instance.
     *
     * @return the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Gets secondary name instance.
     *
     * @return the secondary name
     */
    public String getSecondaryName()
    {
        return secondaryName;
    }
}
