package medik247.vs.com.medik;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;


//import com.parkitalia.android.fragments.AppMapFragment;

/**
 * This activity is used to show forget password screen. <br/>
 */
public class LocationSearchActivity extends BaseFragment implements TextWatcher, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    /**
     * The constant KEY_INTENT_CODE.
     */
    public static final int KEY_INTENT_CODE = 1025;
    /**
     * The constant KEY_PREVIOUS_LOCATION.
     */
    private static final String KEY_PREVIOUS_LOCATION = "KEY_PREVIOUS_LOCATION";
    /**
     * The List adapter.
     */
    private LocationListAdapter listAdapter;
    /**
     * The Filter.
     */
    private Filter filter;
    /**
     * The Api client.
     */
    private GoogleApiClient apiClient;

    /**
     * Launch activity.
     *
     * @param previousLocation the previous location
     */
    public static Fragment getFragment(String previousLocation)
    {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PREVIOUS_LOCATION, previousLocation == null ? "" : previousLocation);

        LocationSearchActivity receiversFragment = new LocationSearchActivity();
        receiversFragment.setArguments(bundle);
        return receiversFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_location_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        listAdapter = new LocationListAdapter(activity);
        filter = listAdapter.getFilter();
        initialiseView(view);

        apiClient = new GoogleApiClient.Builder(activity).addApi(Places.GEO_DATA_API)
                .enableAutoManage((Main2Activity) activity, 0, this)
                .addConnectionCallbacks(this).build();

    }

    /**
     * This method is used to initialise views and set there actions
     */
    private void initialiseView(View view)
    {
//        setHeader(getString(R.string.location_search_screen_title));
//        ListView listView = (ListView) view.findViewById(R.id.activity_location_search_lv_locations);
//        listView.setOnItemClickListener(new NotificationItemClickListener());
//        listView.setAdapter(listAdapter);
//        EditText etLocationSearch = (EditText) view.findViewById(R.id.activity_location_search_et_location);
//        etLocationSearch.addTextChangedListener(this);
//        etLocationSearch.setText(getArguments().getString(KEY_PREVIOUS_LOCATION));
//        etLocationSearch.setSelection(etLocationSearch.getText().length());
//        filter.filter(etLocationSearch.getText().toString());
    }

    /**
     * Before text changed.
     *
     * @param charSequence the char sequence
     * @param i            the
     * @param i1           the 1
     * @param i2           the 2
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        //nothing to do.
    }

    /**
     * On text changed.
     *
     * @param charSequence the char sequence
     * @param i            the
     * @param i1           the 1
     * @param i2           the 2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        filter.filter(charSequence.toString());
    }

    /**
     * After text changed.
     *
     * @param editable the editable
     */
    @Override
    public void afterTextChanged(Editable editable)
    {
        //nothing to do.
    }

    /**
     * On connected.
     *
     * @param bundle the bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        listAdapter.setGoogleApiClient(apiClient);
        filter.filter(getArguments().getString(KEY_PREVIOUS_LOCATION));
    }

    /**
     * On connection suspended.
     *
     * @param i the
     */
    @Override
    public void onConnectionSuspended(int i)
    {
        listAdapter.setGoogleApiClient(null);
    }

    /**
     * On connection failed.
     *
     * @param connectionResult the connection result
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(activity, "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }



    /**
     * The Notification item click listener class.
     */
    class NotificationItemClickListener implements AdapterView.OnItemClickListener
    {
        /**
         * On item click.
         *
         * @param adapterView the adapter view
         * @param view        the view
         * @param position    the position
         * @param id          the id
         */
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
        {
            LocationListAdapter.LocationViewHolder holder = (LocationListAdapter.LocationViewHolder) view.getTag();

            Places.GeoDataApi.getPlaceById(apiClient, holder.location.getPlaceId()).setResultCallback(new LatLgnListener(holder.location));
        }

    }

    /**
     * The Lat lgn listener class.
     */
    class LatLgnListener implements ResultCallback<PlaceBuffer>
    {
        /**
         * The Place autocomplete.
         */
        LocationListAdapter.PlaceAutocomplete placeAutocomplete;

        /**
         * Instantiates a new Lat lgn listener.
         *
         * @param placeAutocomplete the place autocomplete
         */
        public LatLgnListener(LocationListAdapter.PlaceAutocomplete placeAutocomplete)
        {
            this.placeAutocomplete = placeAutocomplete;
        }

        /**
         * On result.
         *
         * @param places the places
         */
        @Override
        public void onResult(@NonNull PlaceBuffer places)
        {
            if (places.getStatus().isSuccess() && places.getCount() > 0)
            {
                Place myPlace = places.get(0);
                AppPlace appPlace = new AppPlace(myPlace, placeAutocomplete.getFirstText(), placeAutocomplete.getSecondaryText());
                ((Main2Activity) activity).pushAddFragments(AppMapFragment.getFragment(appPlace), true, true);
            }
            else
            {
            }
            places.release();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        apiClient.stopAutoManage(getActivity());
        apiClient.disconnect();
    }
}
