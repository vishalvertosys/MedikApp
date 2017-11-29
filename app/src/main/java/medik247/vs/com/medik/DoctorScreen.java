package medik247.vs.com.medik;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.R.attr.fragment;

/**
 * Created by Indosurplus on 5/18/2017.
 */

public class DoctorScreen extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String userid;
    TextView textViewUser;
    String APIID;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewUser= (TextView) findViewById(R.id.text_username);
        gpsApihit();
        SharedPreferences pref2 = getApplicationContext().getSharedPreferences("MYID", 0); // 0 - for private mode
       try {
           APIID = pref2.getString("UIDP", null);
       }
       catch (Exception e){

       }
        ImageView imageViewmenu= (ImageView) findViewById(R.id.menu_iconclik);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayout layoutlogout= (LinearLayout) findViewById(R.id.logoutmine);
        layoutlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManager(getApplicationContext());
                session.logoutUser();
            }
        });
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        session = new SessionManager(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LinearLayout layout= (LinearLayout) findViewById(R.id.logoutmine);
        LinearLayout layouthistory= (LinearLayout) findViewById(R.id.historylayout);
        pushAddFragments(AppMapFragmentDoctor.getFragment(null), true, true);

        layouthistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent=new Intent(DoctorScreen.this,HistoryDoctorActivity.class);
                startActivity(intent);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
drawer.closeDrawers();








            }
        });
        LinearLayout layout1= (LinearLayout) findViewById(R.id.userAccount);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Toast.makeText(Main2Activity.this, "on click this", Toast.LENGTH_SHORT).show();
                session.logoutUser();
//   Intent intent=new Intent(Main2Activity.this,Sigin.class);
//   startActivity(intent);}
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Main2Activity.this,"You Click This",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DoctorScreen.this,ActivityAcount.class);
                startActivity(intent);
            }
        });
        imageViewmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.START);
            }
        });

    }
    public void  gpsApihit(){
        SessionManager session=new SessionManager(DoctorScreen.this);
        HashMap<String, String> user = session.getUserDetails();
//http://vertosys.com/docpat/get_user_information.php?email=ramnish@gmail.com
        // name
        String EmailID = user.get(SessionManager.KEY_EMAIL);

        RequestQueue queue2 = Volley.newRequestQueue(DoctorScreen.this);
        String url2="http://vertosys.com/docpat/get_user_information.php?email="+EmailID;
        Log.e("Doctor 1",url2);
        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>()  {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                String responsemessage= null;
                try {
                    responsemessage = response.getString("message");
                    Log.e("Response1",responsemessage);
                    JSONObject jsonObject=new JSONObject(responsemessage);
                    userid=jsonObject.getString("user_id");
                    String fname=jsonObject.getString("first_name");
                    String lname=jsonObject.getString("last_name");
                    textViewUser.setText(fname+" "+lname);
                    Log.e("2",userid);
                    gpsprog(userid);

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
            // TODO Auto-generated method stub


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(DoctorScreen.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        queue2.add(jsObjRequest2);

        // check if GPS enabled

    }
    public void gpsprog(String uid){
        GPSTracker    gps = new GPSTracker(DoctorScreen.this);

        if(gps.canGetLocation()){

            double   latitude = gps.getLatitude();
            double   longitude = gps.getLongitude();
            Log.e("latlang","/"+latitude+longitude);


            RequestQueue queue = Volley.newRequestQueue(DoctorScreen.this);
            String url="http://vertosys.com/docpat/InsertLocation.php?user_id="+uid+"&latitude="+latitude+"&longitude="+longitude;
            Log.e("Doctor 2",url);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()  {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Response", response.toString());
                }
                // TODO Auto-generated method stub


            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Toast.makeText(DoctorScreen.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

                }
            });

            queue.add(jsObjRequest);

            // \n is for new line
//          Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
            Intent intent=new Intent(DoctorScreen.this,Sigin.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
