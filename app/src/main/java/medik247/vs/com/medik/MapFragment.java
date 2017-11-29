package medik247.vs.com.medik;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.commons.utils.PolylineUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Indosurplus on 5/11/2017.
 */

public class MapFragment extends BaseFragment {
    private static final String KEY_PLACE = "KEY_PLACE";
    private static final String TAG = "SimplifyLineActivity";
    String link;

    private MapView mapView;
    TextView textView;
    private MapboxMap map;


    public static Fragment getFragment(AppPlace appPlace)
    {
        Bundle bundle = new Bundle();
        MapFragment receiversFragment = new MapFragment();
        if (appPlace != null)
        {
            bundle.putSerializable(KEY_PLACE, appPlace);
        }
        receiversFragment.setArguments(bundle);
        return receiversFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Mapbox.getInstance(getContext(), getString(R.string.APIKEYMAPBOX));

        return inflater.inflate(R.layout.fragment_mapbox, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (MapView) getActivity().findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                map = mapboxMap;
                GPSTracker   gps = new GPSTracker(getContext());

                // check if GPS enabled
                if(gps.canGetLocation()){

                    final double latitude = gps.getLatitude();
                    final double longitude = gps.getLongitude();
                     link="http://vertosys.com/docpat/GetDoctor.php?"+"latitude="+latitude+"&longitude="+longitude;
                    RequestQueue queue2 = Volley.newRequestQueue(getActivity());
                    JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>()  {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response", response.toString());
                            String responsemessage= null;
                            try {
                                responsemessage = response.getString("Data");
                                JSONArray jsonArray = new JSONArray(responsemessage);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String lat=jsonObject.getString("latitude");
                                  String longt= jsonObject.getString("longitude");
double a= Double.parseDouble(lat);
                                    double b= Double.parseDouble(longt);

                                    map.addMarker(new MarkerViewOptions()
                                            .position(new LatLng(a,b
                                            ))
                                           );
                                }
                            }
                                catch(Exception e){
                                }
                            }




                        // TODO Auto-generated method stub


                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getContext(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });

                    queue2.add(jsObjRequest2);

                    IconFactory iconFactory = IconFactory.getInstance(getContext());
                    Icon icon = iconFactory.fromResource(R.drawable.navigationicon);

// Add the marker to the map

                    map.addMarker(new MarkerViewOptions()
                            .position(new LatLng(latitude,longitude
                            )).icon(icon)
                    );


                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude)) // Sets the new camera position
                            .zoom(17) // Sets the zoom
                            .bearing(180) // Rotate the camera
                            .tilt(30) // Set the camera tilt
                            .build(); // Creates a CameraPosition from the builder

                    mapboxMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(position), 7000);
                    int padding = 0; // offset from edges of the map in pixels


                    // \n is for new line
//            Toast.makeText(getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

                new DrawGeoJson().execute();

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private class DrawGeoJson extends AsyncTask<Void, Void, List<Position>> {
        @Override
        protected List<Position> doInBackground(Void... voids) {

            List<Position> points = new ArrayList<>();

            try {
                // Load GeoJSON file
                InputStream inputStream = getActivity().getAssets().open("jsonformat.geojson");
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = rd.read()) != -1) {
                    sb.append((char) cp);
                }

                inputStream.close();

                // Parse JSON
                JSONObject json = new JSONObject(sb.toString());
                JSONArray features = json.getJSONArray("features");
                JSONObject feature = features.getJSONObject(0);
                JSONObject geometry = feature.getJSONObject("geometry");
                if (geometry != null) {
                    String type = geometry.getString("type");

                    // Our GeoJSON only has one feature: a line string
                    if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("LineString")) {

                        // Get the Coordinates
                        JSONArray coords = geometry.getJSONArray("coordinates");
                        for (int lc = 0; lc < coords.length(); lc++) {
                            JSONArray coord = coords.getJSONArray(lc);
                            Position position = Position.fromCoordinates(coord.getDouble(0), coord.getDouble(1));
                            points.add(position);
                        }
                    }
                }
            } catch (Exception exception) {
                Log.e(TAG, "Exception Loading GeoJSON: " + exception.toString());
            }

            return points;
        }

        @Override
        protected void onPostExecute(List<Position> points) {
            super.onPostExecute(points);

            drawBeforeSimplify(points);
            drawSimplify(points);

        }
    }

    private void drawBeforeSimplify(List<Position> points) {


        LatLng[] pointsArray = new LatLng[points.size()];
        for (int i = 0; i < points.size(); i++) {
            pointsArray[i] = new LatLng(points.get(i).getLatitude(), points.get(i).getLongitude());
        }


    }

    private void drawSimplify(List<Position> points) {

        Position[] before = new Position[points.size()];
        for (int i = 0; i < points.size(); i++) {
            before[i] = points.get(i);
        }

        Position[] after = PolylineUtils.simplify(before, 0.001);

        LatLng[] result = new LatLng[after.length];
        for (int i = 0; i < after.length; i++) {
            result[i] = new LatLng(after[i].getLatitude(), after[i].getLongitude());
        }

//        map.addPolyline(new PolylineOptions()
//                .add(result)
//                .color(Color.parseColor("#3bb2d0"))
//                .width(4));

    }

}
