package medik247.vs.com.medik;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MajorUSerLOgin extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


     String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_user_login);

         final Handler handler2 = new Handler();
         Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Time Excute","Done");
                gpsApihit();


                handler2.postDelayed(this, 300000);
            }
        };
        LinearLayout layoutmyaccount= (LinearLayout) findViewById(R.id.userAccount);
        layoutmyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent=new Intent(MajorUSerLOgin.this,ActivityAcount.class);

            startActivity(intent);

            }
        });

//Start
        handler2.postDelayed(runnable, 300000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout layoutout= (LinearLayout) findViewById(R.id.logoutmine);
        layoutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("print","//"+"click");

                SessionManager    session = new SessionManager(getApplicationContext());
                        session.logoutUser();



            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LinearLayout layouuser= (LinearLayout) findViewById(R.id.loginuser);
        layouuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MajorUSerLOgin.this,Major.class);
                startActivity(intent);
            }
        });
        LinearLayout layoutcreate= (LinearLayout) findViewById(R.id.layoutcreate);
        layoutcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });
        LinearLayout layoutfetch= (LinearLayout) findViewById(R.id.loginmessagesend);
layoutfetch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MajorUSerLOgin.this,SendMessage.class);
        startActivity(intent);
    }
});
        pushAddFragments(AppMapFragmentDoctor.getFragment(null), true, true);

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
  public void  gpsApihit(){
      SessionManager session=new SessionManager(MajorUSerLOgin.this);
      HashMap<String, String> user = session.getUserDetails();
//http://vertosys.com/docpat/get_user_information.php?email=ramnish@gmail.com
      // name
      String EmailID = user.get(SessionManager.KEY_EMAIL);

      GPSTracker    gps = new GPSTracker(MajorUSerLOgin.this);
      RequestQueue queue2 = Volley.newRequestQueue(MajorUSerLOgin.this);
      String url2="http://vertosys.com/docpat/get_user_information.php?email="+EmailID;
      JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>()  {

          @Override
          public void onResponse(JSONObject response) {
              Log.e("Response", response.toString());
              String responsemessage= null;
              try {
                  responsemessage = response.getString("message");
                  JSONObject jsonObject=new JSONObject(responsemessage);
                   userid=jsonObject.getString("user_id");


              } catch (JSONException e) {

                  e.printStackTrace();
              }


          }
          // TODO Auto-generated method stub


      }, new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
              // TODO Auto-generated method stub
              Toast.makeText(MajorUSerLOgin.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

          }
      });

      queue2.add(jsObjRequest2);

      // check if GPS enabled
      if(gps.canGetLocation()){

       double   latitude = gps.getLatitude();
          double   longitude = gps.getLongitude();
          Log.e("latlang","/"+latitude+longitude);


          RequestQueue queue = Volley.newRequestQueue(MajorUSerLOgin.this);
          String url="http://vertosys.com/docpat/InsertLocation.php?user_id="+userid+"&latitude="+latitude+"&longitude="+longitude;
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
                  Toast.makeText(MajorUSerLOgin.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.major_user_login, menu);
//        return true;
//    }

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
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
            Intent intent=new Intent(MajorUSerLOgin.this,Sigin.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
