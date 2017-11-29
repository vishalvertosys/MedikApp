package medik247.vs.com.medik;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Indosurplus on 5/25/2017.
 */

public class RequestAcceptReject extends Activity {
    TextView name,phonenumbervs,addresssd,datedas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.requestaccept);
        name= (TextView) findViewById(R.id.nametext);
        phonenumbervs= (TextView) findViewById(R.id.phonetext);
        addresssd= (TextView) findViewById(R.id.addresstextwew);
        datedas= (TextView) findViewById(R.id.wewewe23232);

        Intent intent=getIntent();
        String data=intent.getStringExtra("KEY_ID");
        Log.e("DataID",data);
        //http://vertosys.com/docpat/GetUserDetails.php?user_id=2
        String link="http://vertosys.com/docpat/GetUserDetails.php?user_id="+data;
        RequestQueue queue2 = Volley.newRequestQueue(RequestAcceptReject.this);
        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Responsedoctor", response.toString());
                String data23= null;
                try {
                    data23 = response.getString("Data");

                    JSONArray jsonObject = new JSONArray(data23);
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONObject obj = jsonObject.getJSONObject(i);
                        String namev = obj.getString("first_name");
                        String namevv = obj.getString("last_name");
                        String phonenub = obj.getString("phone_number");
                        String aaddd = obj.getString("Address");
                        String datedaad = obj.getString("date_added");
                        Log.e("Phonenu",phonenub+aaddd+datedaad);
                        name.setText(namev + " " + namevv);
                        phonenumbervs.setText(phonenub);
                        addresssd.setText(aaddd);
                        datedas.setText(datedaad);



                    }

                }catch (Exception e){}


            }


            // TODO Auto-generated method stub


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(RequestAcceptReject.this, "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        queue2.add(jsObjRequest2);
        ImageView imageView= (ImageView) findViewById(R.id.back_request);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(RequestAcceptReject.this,DoctorScreen.class);
        startActivity(intent);
    }
});
    }

}
