package medik247.vs.com.medik;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static medik247.vs.com.medik.R.string.view;


public class AppMapFragment extends BaseFragment implements OnMapReadyCallback
{
    List<LatLng> pointsdecode=new ArrayList<LatLng>();
    Polyline polyline23 = null;
    GoogleMap googleMap;
LinearLayout requestDoctor;
    List<ModelMaper>listmoder=new ArrayList<ModelMaper>();
    String  doctorID=null;
    List<ModelMarker>listmodel=new ArrayList<ModelMarker>();

    private static final String KEY_PLACE = "KEY_PLACE";
    public double lat1,gpslatitude,gpslongitude;
     Intent intent;
     String data234;

    public static Fragment getFragment(AppPlace appPlace)
    {
        Bundle bundle = new Bundle();
        AppMapFragment receiversFragment = new AppMapFragment();
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
        Runnable helloRunnable2 = new Runnable() {
            public void run() {

                System.out.println("Hello world 33333");
                onMapReady(googleMap);


            }
        };

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable2, 0, 3, TimeUnit.SECONDS);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = new SupportMapFragment();
        intent=new Intent(getContext(),DetailsDoctor.class);

        getChildFragmentManager().beginTransaction().add(R.id.map_container, mapFragment, "MAPS").commit();
        mapFragment.getMapAsync(this);
        requestDoctor= (LinearLayout) view.findViewById(R.id.layout_request);
        requestDoctor.setVisibility(View.GONE);
       Button requestDoctorutton= (Button) view.findViewById(R.id.requestbutton);
        requestDoctorutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    Marker marker;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        loop();
       this. googleMap=googleMap;
        Runnable helloRunnable2 = new Runnable() {
            public void run() {

                System.out.println("Hello world 222");


            }
        };

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable2, 0, 3, TimeUnit.SECONDS);
        Runnable helloRunnable = new Runnable() {
            public void run() {
                System.out.println("Hello world");

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        GPSTracker gps = new GPSTracker(getContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

              gpslatitude = gps.getLatitude();
              gpslongitude = gps.getLongitude();
            Log.e("lattitude",""+gpslatitude+gpslongitude);
            IconFactory iconFactory = IconFactory.getInstance(getContext());
            Icon icon2 = iconFactory.fromResource(R.drawable.blueicon);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gpslatitude, gpslongitude))
                    .title("You")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(gpslatitude, gpslongitude)),15));
            // Zoom in, animating the camera.
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            String getDoctorAPI="http://vertosys.com/docpat/GetDoctor.php?latitude="+gpslatitude+"&longitude="+gpslongitude;
            Log.e("URl Print",getDoctorAPI);

            RequestQueue queue2 = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, getDoctorAPI, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Responsedoctor", response.toString());
                    String responsemessage = null;
                    try {
                        responsemessage = response.getString("Data");
                        JSONArray jsonArray = new JSONArray(responsemessage);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String lat = jsonObject.getString("latitude");
                            String longt = jsonObject.getString("longitude");
                            double a = Double.parseDouble(lat);
                            double b = Double.parseDouble(longt);
                            doctorID=jsonObject.getString("user_id");
                            Log.e("Loop",""+doctorID);

                            listmodel.add(new ModelMarker(a,b,"Doctor"));
                            listmoder.add(new ModelMaper(a,b,doctorID,"Doctor"));
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Doctor"));

                            Log.e("lat", lat + longt);
                        }
                    } catch (Exception e) {
                    }
                }


                // TODO Auto-generated method stub


            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            queue2.add(jsObjRequest2);
           googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
               @Override
               public boolean onMarkerClick(Marker marker) {

                   googleMap.clear();
                   requestDoctor.setVisibility(View.VISIBLE);
                   for(int i=0;i<listmoder.size();i++){
                       googleMap.addMarker(new MarkerOptions()
                               .position(new LatLng(listmoder.get(i).lat, listmoder.get(i).lng))
                               .title(listmoder.get(i).title));
                   }
                 double aa=  marker.getPosition().latitude;
                   double bb=  marker.getPosition().longitude;
                   for(int k=0;k<listmoder.size();k++){
                       if(listmoder.get(k).getLat()==aa){
                           if(listmoder.get(k).getLng()==bb){
                                data234=listmoder.get(k).getUid();
                               intent.putExtra("doctorID",data234);

                               Log.e("data23",data234);

                           }

                       }
                   }

                   googleMap.addMarker(new MarkerOptions()
                           .position(new LatLng(gpslatitude, gpslongitude))
                           .title("You")
                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
                   Log.e("long",""+marker.getPosition().longitude);
                   String link6="https://maps.googleapis.com/maps/api/directions/json?origin="+gpslatitude+","+gpslongitude+"&destination="+marker.getPosition().latitude+","+marker.getPosition().longitude+"&mode=car&key=AIzaSyCbkAKAJnt57am2mMVkHc5PrNZ1rzM_RfQ";

                   String getDoctorAPI="http://vertosys.com/docpat/GetDoctor.php?latitude="+gpslatitude+"&longitude="+gpslongitude;
                   Log.e("URl Print",getDoctorAPI);

                   RequestQueue queue2 = Volley.newRequestQueue(getActivity());
                   JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, getDoctorAPI, null, new Response.Listener<JSONObject>() {

                       @Override
                       public void onResponse(JSONObject response) {
                           Log.e("Response", response.toString());
                           String responsemessage = null;
                           try {
                               responsemessage = response.getString("Data");
                               JSONArray jsonArray = new JSONArray(responsemessage);
                               for (int i = 0; i < jsonArray.length(); i++) {
                                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                                   String lat = jsonObject.getString("latitude");
                                   String longt = jsonObject.getString("longitude");
                                   double a = Double.parseDouble(lat);
                                   double b = Double.parseDouble(longt);
                                   googleMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Doctor"));


                                   Log.e("lat", lat + longt);
                               }
                           } catch (Exception e) {
                           }
                       }


                       // TODO Auto-generated method stub


                   }, new Response.ErrorListener() {

                       @Override
                       public void onErrorResponse(VolleyError error) {
                           // TODO Auto-generated method stub
                           Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                       }
                   });

                   queue2.add(jsObjRequest2);

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
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        GPSTracker gps = new GPSTracker(getContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            gpslatitude = gps.getLatitude();
            gpslongitude = gps.getLongitude();
            Log.e("lattitude",""+gpslatitude+gpslongitude);
            IconFactory iconFactory = IconFactory.getInstance(getContext());
            Icon icon2 = iconFactory.fromResource(R.drawable.blueicon);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gpslatitude, gpslongitude))
                    .title("You")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(gpslatitude, gpslongitude)),15));
            // Zoom in, animating the camera.
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            String getDoctorAPI="http://vertosys.com/docpat/GetDoctor.php?latitude="+gpslatitude+"&longitude="+gpslongitude;
            Log.e("URl Print",getDoctorAPI);

            RequestQueue queue2 = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, getDoctorAPI, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Responsedoctor", response.toString());
                    String responsemessage = null;
                    try {
                        responsemessage = response.getString("Data");
                        JSONArray jsonArray = new JSONArray(responsemessage);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String lat = jsonObject.getString("latitude");
                            String longt = jsonObject.getString("longitude");
                            double a = Double.parseDouble(lat);
                            double b = Double.parseDouble(longt);
                            doctorID=jsonObject.getString("user_id");
                            Log.e("Loop",""+doctorID);

                            listmodel.add(new ModelMarker(a,b,"Doctor"));
                            listmoder.add(new ModelMaper(a,b,doctorID,"Doctor"));
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Doctor"));

                            Log.e("lat", lat + longt);
                        }
                    } catch (Exception e) {
                    }
                }


                // TODO Auto-generated method stub


            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            queue2.add(jsObjRequest2);
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    googleMap.clear();
                    requestDoctor.setVisibility(View.VISIBLE);
                    for(int i=0;i<listmoder.size();i++){
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(listmoder.get(i).lat, listmoder.get(i).lng))
                                .title(listmoder.get(i).title));
                    }
                    double aa=  marker.getPosition().latitude;
                    double bb=  marker.getPosition().longitude;
                    for(int k=0;k<listmoder.size();k++){
                        if(listmoder.get(k).getLat()==aa){
                            if(listmoder.get(k).getLng()==bb){
                                data234=listmoder.get(k).getUid();
                                intent.putExtra("doctorID",data234);

                                Log.e("data23",data234);

                            }

                        }
                    }

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(gpslatitude, gpslongitude))
                            .title("You")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
                    Log.e("long",""+marker.getPosition().longitude);
                    String link6="https://maps.googleapis.com/maps/api/directions/json?origin="+gpslatitude+","+gpslongitude+"&destination="+marker.getPosition().latitude+","+marker.getPosition().longitude+"&mode=car&key=AIzaSyCbkAKAJnt57am2mMVkHc5PrNZ1rzM_RfQ";

                    String getDoctorAPI="http://vertosys.com/docpat/GetDoctor.php?latitude="+gpslatitude+"&longitude="+gpslongitude;
                    Log.e("URl Print",getDoctorAPI);

                    RequestQueue queue2 = Volley.newRequestQueue(getActivity());
                    JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, getDoctorAPI, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response", response.toString());
                            String responsemessage = null;
                            try {
                                responsemessage = response.getString("Data");
                                JSONArray jsonArray = new JSONArray(responsemessage);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String lat = jsonObject.getString("latitude");
                                    String longt = jsonObject.getString("longitude");
                                    double a = Double.parseDouble(lat);
                                    double b = Double.parseDouble(longt);
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Doctor"));


                                    Log.e("lat", lat + longt);
                                }
                            } catch (Exception e) {
                            }
                        }


                        // TODO Auto-generated method stub


                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                    queue2.add(jsObjRequest2);

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
    public void loop(){
        Runnable helloRunnable2 = new Runnable() {
            public void run() {

                System.out.println("Hello world Loop");
                map();

            }
        };

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable2, 0, 3, TimeUnit.SECONDS);

    }
    public void map(){
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        GPSTracker gps = new GPSTracker(getContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            gpslatitude = gps.getLatitude();
            gpslongitude = gps.getLongitude();
            Log.e("lattitude",""+gpslatitude+gpslongitude);
            IconFactory iconFactory = IconFactory.getInstance(getContext());
            Icon icon2 = iconFactory.fromResource(R.drawable.blueicon);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gpslatitude, gpslongitude))
                    .title("You")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(gpslatitude, gpslongitude)),15));
            // Zoom in, animating the camera.
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            String getDoctorAPI="http://vertosys.com/docpat/GetDoctor.php?latitude="+gpslatitude+"&longitude="+gpslongitude;
            Log.e("URl Print",getDoctorAPI);

            RequestQueue queue2 = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, getDoctorAPI, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Responsedoctor", response.toString());
                    String responsemessage = null;
                    try {
                        responsemessage = response.getString("Data");
                        JSONArray jsonArray = new JSONArray(responsemessage);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String lat = jsonObject.getString("latitude");
                            String longt = jsonObject.getString("longitude");
                            double a = Double.parseDouble(lat);
                            double b = Double.parseDouble(longt);
                            doctorID=jsonObject.getString("user_id");
                            Log.e("Loop",""+doctorID);

                            listmodel.add(new ModelMarker(a,b,"Doctor"));
                            listmoder.add(new ModelMaper(a,b,doctorID,"Doctor"));
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Doctor"));

                            Log.e("lat", lat + longt);
                        }
                    } catch (Exception e) {
                    }
                }


                // TODO Auto-generated method stub


            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            queue2.add(jsObjRequest2);
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    googleMap.clear();
                    requestDoctor.setVisibility(View.VISIBLE);
                    for (int i = 0; i < listmoder.size(); i++) {
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(listmoder.get(i).lat, listmoder.get(i).lng))
                                .title(listmoder.get(i).title));
                    }
                    double aa = marker.getPosition().latitude;
                    double bb = marker.getPosition().longitude;
                    for (int k = 0; k < listmoder.size(); k++) {
                        if (listmoder.get(k).getLat() == aa) {
                            if (listmoder.get(k).getLng() == bb) {
                                data234 = listmoder.get(k).getUid();
                                intent.putExtra("doctorID", data234);

                                Log.e("data23", data234);

                            }

                        }
                    }

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(gpslatitude, gpslongitude))
                            .title("You")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.blueicon)));
                    Log.e("long", "" + marker.getPosition().longitude);
                    String link6 = "https://maps.googleapis.com/maps/api/directions/json?origin=" + gpslatitude + "," + gpslongitude + "&destination=" + marker.getPosition().latitude + "," + marker.getPosition().longitude + "&mode=car&key=AIzaSyCbkAKAJnt57am2mMVkHc5PrNZ1rzM_RfQ";

                    String getDoctorAPI = "http://vertosys.com/docpat/GetDoctor.php?latitude=" + gpslatitude + "&longitude=" + gpslongitude;
                    Log.e("URl Print", getDoctorAPI);

                    RequestQueue queue2 = Volley.newRequestQueue(getActivity());
                    JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, getDoctorAPI, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response", response.toString());
                            String responsemessage = null;
                            try {
                                responsemessage = response.getString("Data");
                                JSONArray jsonArray = new JSONArray(responsemessage);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String lat = jsonObject.getString("latitude");
                                    String longt = jsonObject.getString("longitude");
                                    double a = Double.parseDouble(lat);
                                    double b = Double.parseDouble(longt);
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(a, b)).title("Doctor"));


                                    Log.e("lat", lat + longt);
                                }
                            } catch (Exception e) {
                            }
                        }


                        // TODO Auto-generated method stub


                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                    queue2.add(jsObjRequest2);

                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, link6, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Json Object", "response:=" + response);
                            try {
                                String routes = response.getString("routes");
                                JSONArray jsonArray = new JSONArray(routes);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String bounds = jsonObject.getString("bounds");
                                    String overviewpoints = jsonObject.getString("overview_polyline");
                                    Log.e("Overviewpoints", overviewpoints);
                                    JSONObject jsonObjectpoints = new JSONObject(overviewpoints);
                                    String points = jsonObjectpoints.getString("points");
                                    Log.e("points", points);
                                    pointsdecode = decodePolyLine(points);


                                    JSONObject jsonObject1 = new JSONObject(bounds);
                                    String northeast = jsonObject1.getString("northeast");
                                    JSONObject jsonObject2 = new JSONObject(northeast);
                                    lat1 = jsonObject2.getDouble("lat");
                                    double long1 = jsonObject2.getDouble("lng");
                                    LatLng sydney2 = new LatLng(lat1, long1);


                                    String southwest = jsonObject1.getString("southwest");
                                    JSONObject jsonObject3 = new JSONObject(southwest);

                                }


                                Log.e("Pointdecode", pointsdecode.toString() + pointsdecode.size());
                                PolylineOptions polylineOptions = new PolylineOptions().
                                        geodesic(true).
                                        color(Color.BLUE).
                                        width(10);
                                for (int j = 0; j < pointsdecode.size(); j++) {
//
////                        mMap.addMarker(new MarkerOptions().position(pointsdecode.get(j)).title("Place B"));
//
//
                                    PolylineOptions polylineOptions2 = polylineOptions.add(pointsdecode.get(j));
                                    polyline23 = googleMap.addPolyline(polylineOptions);
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

                                Log.e("Pointdecode Clear", pointsdecode.toString() + pointsdecode.size());


                                googleMap.addPolyline(polylineOptions);

                                Log.e("routes", routes);
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
