package medik247.vs.com.medik;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import org.w3c.dom.Text;

import java.util.HashMap;

public class ActivityAcount extends Activity {
    TextView nameview,emailview,phoneview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);
        ImageView imageView= (ImageView) findViewById(R.id.backup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager session=new SessionManager(ActivityAcount.this);
                HashMap<String, String> user = session.getUserDetails();
//http://vertosys.com/docpat/get_user_information.php?email=ramnish@gmail.com
                // name
                String LoginDetails = user.get(SessionManager.LoginDetail);
               Intent intent=new Intent(ActivityAcount.this,ScreenSignIn.class);
                startActivity(intent);

            }
        });
        nameview= (TextView) findViewById(R.id.nametext);
        emailview= (TextView) findViewById(R.id.emailtext);
        phoneview= (TextView) findViewById(R.id.phonenumbertext);
        gpsApihit();


    }
    public void  gpsApihit(){
        SessionManager session=new SessionManager(ActivityAcount.this);
        HashMap<String, String> user = session.getUserDetails();
//http://vertosys.com/docpat/get_user_information.php?email=ramnish@gmail.com
        // name
        String EmailID = user.get(SessionManager.KEY_EMAIL);

        GPSTracker    gps = new GPSTracker(ActivityAcount.this);
        RequestQueue queue2 = Volley.newRequestQueue(ActivityAcount.this);
        String url2="http://vertosys.com/docpat/get_user_information.php?email="+EmailID;
        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>()  {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                String responsemessage= null;
                try {
                    responsemessage = response.getString("message");
                    JSONObject jsonObject=new JSONObject(responsemessage);

                    String userid=jsonObject.getString("user_id");
                    String fname=jsonObject.getString("first_name");
                    String lname=jsonObject.getString("last_name");
                    String emailid=jsonObject.getString("email");
                    String phonenumber=jsonObject.getString("phone_number");
                    nameview.setText(fname+" "+lname);
                    emailview.setText(emailid);
                    phoneview.setText(phonenumber);


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
            // TODO Auto-generated method stub


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(ActivityAcount.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        queue2.add(jsObjRequest2);

        // check if GPS enabled


            // \n is for new line
//
    }
}
