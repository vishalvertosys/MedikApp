package medik247.vs.com.medik;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static java.security.AccessController.getContext;

/**
 * Created by Indosurplus on 5/22/2017.
 */

public class DetailsDoctor extends Activity {
    String link;
    String userid;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaildoctor);
        Intent intent=getIntent();
        ImageView imageView= (ImageView) findViewById(R.id.imageback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsDoctor.this,ScreenSignIn.class);
                startActivity(intent);
            }
        });
        id   =  intent.getStringExtra("doctorID");
        Log.e("move","2"+id);
        String apilink="http://vertosys.com/docpat/GetUserDetails.php?user_id="+id;
        Log.e("move","apilink");

        RequestQueue queue3 = Volley.newRequestQueue(DetailsDoctor.this);
        JsonObjectRequest jsObjRequest3 = new JsonObjectRequest(Request.Method.GET, apilink, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response23232", response.toString());
                String responsemessage = null;
                try {
                    String responmsedetail=response.getString("Data");
                    JSONArray jsonArray=new JSONArray(responmsedetail);
                    for(int j=0;j<jsonArray.length();j++){
                        JSONObject jsonObject=jsonArray.getJSONObject(j);
                        String fname=      jsonObject.getString("first_name");
                        String lname=        jsonObject.getString("last_name");
                        String addtress= jsonObject.getString("Address");
                        Log.e("Data23",fname+lname+addtress);
                        TextView textView= (TextView) findViewById(R.id.doctorname);
                        TextView textViewinf= (TextView) findViewById(R.id.doctor1);
                        TextView textViewadd= (TextView) findViewById(R.id.doctor2);
                        textView.setText(fname+" "+lname);

                        textViewadd.setText(addtress);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            // TODO Auto-generated method stub


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(DetailsDoctor.this, "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        queue3.add(jsObjRequest3);

//        /http://vertosys.com/docpat/SendRequest.php?doctor_id=2&patient_id=3
        Button button= (Button) findViewById(R.id.request_doctor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager=new SessionManager(DetailsDoctor.this);
                HashMap<String,String>map=sessionManager.getUserDetails();
                String emailid=  map.get(SessionManager.KEY_EMAIL);
                RequestQueue queue2 = Volley.newRequestQueue(DetailsDoctor.this);
                String url2="http://vertosys.com/docpat/get_user_information.php?email="+emailid;
                Log.e("usere",url2);
                JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>()  {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                        String responsemessage= null;
                        try {
                            responsemessage = response.getString("message");
                            JSONObject jsonObject=new JSONObject(responsemessage);
                            userid=jsonObject.getString("user_id");
                            Log.e("move","move");
                            api(userid);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }


                    }
                    // TODO Auto-generated method stub


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(DetailsDoctor.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

                queue2.add(jsObjRequest2);

            }
        });
        //http://vertosys.com/docpat/SendRequest.php?doctor_id=2
    }
    public void jkl(String abf){
        Log.e("move","2");
        String apilink="http://vertosys.com/docpat/GetUserDetails.php?user_id="+abf;
        Log.e("move","apilink");

        RequestQueue queue2 = Volley.newRequestQueue(DetailsDoctor.this);
        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, apilink, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                String responsemessage = null;
                try {
                    String responmsedetail=response.getString("Data");
                    JSONObject jsonObject=new JSONObject(responmsedetail);
              String fname=      jsonObject.getString("first_name");
                    String lname=        jsonObject.getString("last_name");
                    String addtress= jsonObject.getString("Address");
                    TextView textView= (TextView) findViewById(R.id.doctorname);
                    TextView textViewinf= (TextView) findViewById(R.id.doctor1);
                    TextView textViewadd= (TextView) findViewById(R.id.doctor2);
textView.setText(fname+" "+lname);
                    textViewadd.setText(addtress);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            // TODO Auto-generated method stub


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(DetailsDoctor.this, "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        queue2.add(jsObjRequest2);

    }
   public void  api(String uid){
       link="http://vertosys.com/docpat/SendRequest.php?doctor_id="+id+"&patient_id="+uid;

Log.e("....",link);
       RequestQueue queue2 = Volley.newRequestQueue(DetailsDoctor.this);
       JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {

           @Override
           public void onResponse(JSONObject response) {
               Log.e("Response", response.toString());
               String responsemessage = null;
               try {
                   String responmsedetail=response.getString("success");
                   if(responmsedetail.equals("true")){
                       Toast.makeText(DetailsDoctor.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent(DetailsDoctor.this,ScreenSignIn.class);
                       startActivity(intent);
                   }
                   else {
                       Toast.makeText(DetailsDoctor.this,"Something Went Wrong !",Toast.LENGTH_SHORT).show();

                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }


           // TODO Auto-generated method stub


       }, new Response.ErrorListener() {

           @Override
           public void onErrorResponse(VolleyError error) {
               // TODO Auto-generated method stub
               Toast.makeText(DetailsDoctor.this, "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

           }
       });

       queue2.add(jsObjRequest2);
    }
}
