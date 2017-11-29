package medik247.vs.com.medik;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewNavigationActivitty extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;


    // GPSTracker class
    GPSTracker gps;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        Log.d("call","mazor");
     //   setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        final LinearLayout layoutcreate= (LinearLayout) findViewById(R.id.layoutcreate);
//        layoutcreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//               drawer.closeDrawer(GravityCompat.START);
//
//            }
//        });





        LinearLayout layouuser= (LinearLayout) findViewById(R.id.loginuser);
        layouuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewNavigationActivitty.this,MajorUSerLOgin.class);
                startActivity(intent);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        gps = new GPSTracker(NewNavigationActivitty.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.e("latlang","/"+latitude);

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        LinearLayout layoutout44= (LinearLayout) findViewById(R.id.logout123);


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
        getMenuInflater().inflate(R.menu.new_navigation_activitty, menu);
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

        if (id == R.id.logout123) {
            Intent intent=new Intent(this,Sigin.class);
            startActivity(intent);

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.e("latlang121212","/"+latitude);
        googleMap.addMarker(new MarkerOptions().position(new LatLng( latitude, longitude)).title("Marker"));

    }

    @Override
    public void onClick(View view) {
        view.findViewById(R.id.logout123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewNavigationActivitty.this,"You click",Toast.LENGTH_SHORT).show();


            }
        });
    }
    public  void insertIntoDb(){
        Toast.makeText(NewNavigationActivitty.this,"You click",Toast.LENGTH_SHORT).show();

    }
}

