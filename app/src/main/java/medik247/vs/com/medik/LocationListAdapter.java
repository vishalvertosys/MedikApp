package medik247.vs.com.medik;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Show list of notifications with sorting of dates. <br> Its sticky header list Adapter.
 */
public class LocationListAdapter extends BaseAdapter implements Filterable
{
    private List<PlaceAutocomplete> placeAutocompletes;
    private LayoutInflater layoutInflater;
    private GoogleApiClient mGoogleApiClient;

    /**
     * Instantiates a new Location list adapter.
     *
     * @param activity the activity
     */
    public LocationListAdapter(Activity activity)
    {
        if (placeAutocompletes == null)
        {
            placeAutocompletes = new ArrayList<>();
        }
        if (activity != null)
        {
            layoutInflater = activity.getLayoutInflater();
        }
    }

    /**
     * Gets item instance.
     *
     * @param notificationPosition the notification position
     * @return the item
     */
    @Override
    public PlaceAutocomplete getItem(int notificationPosition)
    {
        if (placeAutocompletes.size() > notificationPosition)
        {
            return placeAutocompletes.get(notificationPosition);
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets item id instance.
     *
     * @param i the
     * @return the item id
     */
    @Override
    public long getItemId(int i)
    {
        return i;
    }

    /**
     * Gets count instance.
     *
     * @return the count
     */
    @Override
    public int getCount()
    {
        return placeAutocompletes.size();
    }

    /**
     * Gets view instance.
     *
     * @param position    the position
     * @param convertView the convert view
     * @param parent      the parent
     * @return the view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LocationViewHolder holder;
        View view;
        if (convertView == null)
        {
            view = layoutInflater.inflate(R.layout.item_location_list, parent, false);
            holder = new LocationViewHolder();
            holder.tvFirstText = (TextView) view.findViewById(R.id.item_location_tv_first_text);
            holder.tvSecondaryText = (TextView) view.findViewById(R.id.item_location_tv_secondary_text);
            view.setTag(holder);
        }
        else
        {
            holder = (LocationViewHolder) convertView.getTag();
            view = convertView;
        }

        holder.location = getItem(position);
        if (holder.location != null)
        {
            holder.tvFirstText.setText(holder.location.getFirstText());
            holder.tvSecondaryText.setText(holder.location.getSecondaryText());
        }
        return view;
    }

    /**
     * Gets filter instance.
     *
     * @return the filter
     */
    @Override
    public Filter getFilter()
    {
        return new LocationFilter();
    }

    /**
     * Sets google api client instance.
     *
     * @param googleApiClient the google api client
     */
    public void setGoogleApiClient(GoogleApiClient googleApiClient)
    {
        if (googleApiClient == null || !googleApiClient.isConnected())
        {
            mGoogleApiClient = null;
        }
        else
        {
            mGoogleApiClient = googleApiClient;
        }
    }

    /**
     * Inner class contains list of reference for child view.
     */
    class LocationViewHolder
    {
        /**
         * The Tv first text.
         */
        TextView tvFirstText;
        /**
         * The Tv secondary text.
         */
        TextView tvSecondaryText;
        /**
         * The Location.
         */
        PlaceAutocomplete location;
    }

    /**
     * The Place autocomplete class.
     */
    public class PlaceAutocomplete
    {
        /**
         * The Place id.
         */
        private String placeId;
        /**
         * The First text.
         */
        private CharSequence firstText;
        /**
         * The Secondary text.
         */
        private CharSequence secondaryText;

        /**
         * Instantiates a new Place autocomplete.
         *
         * @param placeId       the place id
         * @param firstText     the first text
         * @param secondaryText the secondary text
         */
        public PlaceAutocomplete(String placeId, CharSequence firstText, CharSequence secondaryText)
        {
            this.placeId = placeId;
            this.firstText = firstText;
            this.secondaryText = secondaryText;
        }

        /**
         * To string string.
         *
         * @return the string
         */
        @Override
        public String toString()
        {
            return firstText.toString() + secondaryText.toString();
        }

        /**
         * Gets place id instance.
         *
         * @return the place id
         */
        public String getPlaceId()
        {
            return placeId;
        }

        /**
         * Gets first text instance.
         *
         * @return the first text
         */
        public CharSequence getFirstText()
        {
            return firstText;
        }

        /**
         * Gets secondary text instance.
         *
         * @return the secondary text
         */
        public CharSequence getSecondaryText()
        {
            return secondaryText;
        }
    }

    /**
     * The Location filter class.
     */
    class LocationFilter extends Filter
    {
        /**
         * Perform filtering filter results.
         *
         * @param constraint the constraint
         * @return the filter results
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            if (constraint != null)
            {
                // Query the autocomplete API for the entered constraint
                List<PlaceAutocomplete> mResultList = getPredictions(constraint);
                if (mResultList != null)
                {
                    // Results
                    results.values = mResultList;
                    results.count = mResultList.size();
                    placeAutocompletes.clear();
                    placeAutocompletes.addAll(mResultList);
                }
            }
            return results;
        }

        /**
         * Publish results.
         *
         * @param constraint the constraint
         * @param results    the results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results != null && results.count > 0)
            {
                // The API returned at least one result, update the data.
                notifyDataSetChanged();
            }
            else
            {
                // The API did not return any results, invalidate the data set.
                notifyDataSetInvalidated();
            }
        }

        /**
         * Gets predictions instance.
         *
         * @param constraint the constraint
         * @return the predictions
         */
        private List<PlaceAutocomplete> getPredictions(CharSequence constraint)
        {
            if (mGoogleApiClient != null)
            {
                PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(), null, null);
                // Wait for predictions, set the timeout.
                AutocompletePredictionBuffer autocompletePredictions = results.await(10, TimeUnit.SECONDS);
                Status status = autocompletePredictions.getStatus();
                if (!status.isSuccess())
                {
                    autocompletePredictions.release();
                    return new ArrayList<>();
                }

                Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
                List<PlaceAutocomplete> resultList = new ArrayList<>(autocompletePredictions.getCount());
                while (iterator.hasNext())
                {
                    AutocompletePrediction prediction = iterator.next();
                    resultList.add(new PlaceAutocomplete(prediction.getPlaceId(), prediction.getPrimaryText(new StyleSpan(Typeface.BOLD)),
                            prediction.getSecondaryText(new StyleSpan(Typeface.BOLD))));
                }
                // Buffer release
                autocompletePredictions.release();
                return resultList;
            }
            return new ArrayList<>();
        }
    }
}
