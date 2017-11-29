package medik247.vs.com.medik;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AppMapFragmentDoctor extends BaseFragment implements OnMapReadyCallback
{

    List<LatLng> pointsdecode=new ArrayList<LatLng>();
    Polyline polyline23 = null;
String url="http://vertosys.com/docpat/GetLocation.php?user_id=";
    List<ModelMarker>listmodel=new ArrayList<ModelMarker>();

    private static final String KEY_PLACE = "KEY_PLACE";
    public double lat1,gpslatitude,gpslongitude;
     String APIID;

    public static Fragment getFragment(AppPlace appPlace)
    {
        Bundle bundle = new Bundle();
        AppMapFragmentDoctor receiversFragment = new AppMapFragmentDoctor();
        if (appPlace != null)
        {
            bundle.putSerializable(KEY_PLACE, appPlace);
        }
        receiversFragment.setArguments(bundle);
        return receiversFragment;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.screendoctor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().add(R.id.map_containers2, mapFragment, "MAPS").commit();
        mapFragment.getMapAsync(this);

    }

    Marker marker;
    GoogleMap googleMap;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        GPSTracker gps = new GPSTracker(getContext());
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.711741,76.727514))
                .title("Patient")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
        // check if GPS enabled
        if (gps.canGetLocation()) {
            SharedPreferences pref2 = getActivity().getSharedPreferences("MYID", 0); // 0 - for private mode
            try {
                APIID = pref2.getString("UIDP", null);
                String datap=url+APIID;
                RequestQueue queue2 = Volley.newRequestQueue(getContext());
                Log.e("Doctor 1",datap);
                JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, datap, null, new Response.Listener<JSONObject>()  {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                        String responsemessage= null;
                        try {
String message=response.getString("message");
                            JSONObject jsonobject=new JSONObject(message);
                            String data1=jsonobject.getString("latitude");
                            String data2=jsonobject.getString("longitude");
                            double a= Double.parseDouble(data1);
                            double b= Double.parseDouble(data2);
                            listmodel.add(new ModelMarker(a,b,"patient"));
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(a, b))
                                    .title("Patient")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));


                        } catch (JSONException e) {

                            e.printStackTrace();
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

            }
            catch (Exception e){

            }

              gpslatitude = gps.getLatitude();
              gpslongitude = gps.getLongitude();
            Log.e("lattitude",""+gpslatitude+gpslongitude);
            IconFactory iconFactory = IconFactory.getInstance(getContext());
            Icon icon2 = iconFactory.fromResource(R.drawable.blueicon);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gpslatitude, gpslongitude))
                    .title("You")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.doctoricon)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(gpslatitude, gpslongitude)),15));
            // Zoom in, animating the camera.
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(Marker marker) {

        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.711741,76.727514))
                .title("Patient")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
        Log.e("listmodel",""+listmodel.size());
        for(int i=0;i<listmodel.size();i++){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(listmodel.get(i).lat, listmodel.get(i).longt))
                    .title(listmodel.get(i).title).icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
        }
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(gpslatitude, gpslongitude))
                .title("You")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.doctoricon)));
        String link6="https://maps.googleapis.com/maps/api/directions/json?origin="+gpslatitude+","+gpslongitude+"&destination="+marker.getPosition().latitude+","+marker.getPosition().longitude+"&mode=car&key=AIzaSyCbkAKAJnt57am2mMVkHc5PrNZ1rzM_RfQ";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, link6, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Json Object","response:="+response);
                try {
                    String routes=response.getString("routes");
                    JSONArray jsonArray=new JSONArray(routes);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String bounds=jsonObject.getString("bounds");
                        String overviewpoints=jsonObject.getString("overview_polyline");
                        Log.e("Overviewpoints",overviewpoints);
                        JSONObject jsonObjectpoints=new JSONObject(overviewpoints);
                        String points=jsonObjectpoints.getString("points");
                        Log.e("points",points);
                        pointsdecode = decodePolyLine(points);


                        JSONObject jsonObject1=new JSONObject(bounds);
                        String northeast=      jsonObject1.getString("northeast");
                        JSONObject jsonObject2=new JSONObject(northeast);
                        lat1= jsonObject2.getDouble("lat");
                        double  long1=jsonObject2.getDouble("lng");
                        LatLng sydney2 = new LatLng(lat1,long1);


                        String southwest= jsonObject1.getString("southwest");
                        JSONObject jsonObject3=new JSONObject(southwest);





//


                    }









                    Log.e("Pointdecode",pointsdecode.toString()+pointsdecode.size());
                    PolylineOptions polylineOptions = new PolylineOptions().
                            geodesic(true).
                            color(Color.BLUE).
                            width(10);
                    for(int j=0;j<pointsdecode.size();j++){
//
////                        mMap.addMarker(new MarkerOptions().position(pointsdecode.get(j)).title("Place B"));
//
//
                        PolylineOptions polylineOptions2=   polylineOptions.add(pointsdecode.get(j));
                        polyline23=googleMap.addPolyline(polylineOptions);
//
                    }
//
//                    polylineOptions.visible(false);
//                    Polyline line = mMap.addPolyline(new PolylineOptions()
//                            .add(new LatLng(location.getLatitude(), location.getLongitude()),
//                                    new LatLng(this.destinationLatitude, this.destinationLongitude))
//                            .width(1)
//                            .color(Color.DKGRAY)
                    polyline23.remove();

                    Log.e("Pointdecode Clear",pointsdecode.toString()+pointsdecode.size());





                    googleMap.addPolyline(polylineOptions);

                    Log.e("routes",routes);
                } catch (Exception e) {
                    e.printStackTrace();
                }


// TODO Auto-generated method stub

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
// TODO Auto-generated method stub
            }
        });
        queue.add(jsObjRequest);
        return false;
    }
});




        }
    }
    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }

}
