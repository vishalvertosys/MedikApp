package medik247.vs.com.medik;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 29-04-2017.
 */

public class SendMessage extends Activity {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =101 ;
    String param1="email";
    private String link="http://mymedicalrecordsweb.com/iwin-11-2/Web%20services/message.php?";
     String number;
     String messagesend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage);
        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            Log.e("d",""+checkAndRequestPermissions());
        }
        else {
            Log.e("Permission",""+checkAndRequestPermissions());

        }
        final EditText editText= (EditText) findViewById(R.id.emailid);
        Button button= (Button) findViewById(R.id.buttonsend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaildata=editText.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(SendMessage.this);
                String url=link+param1+"="+emaildata;
                final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()  {

                    @Override
                    public void onResponse(JSONObject response) {

                        String message = null;
                        try {
                            message = response.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject newjsonob = null;
                        try {
                            newjsonob = new JSONObject(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String recorsds = null;
                        try {
                            recorsds = newjsonob.getString("records");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray jsoneArr = null;
                        try {
                            jsoneArr = new JSONArray(recorsds);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < jsoneArr.length(); i++) {
                            JSONObject job = null;
                            try {
                                job = jsoneArr.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                 number = job.getString("number");
                                Log.e("number Send", number);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                 messagesend = job.getString("message");
                                Log.e("Message Send", messagesend);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(number, null, messagesend, null, null);
                                Toast.makeText(getApplicationContext(), "Send SMS Success",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception ex) {
                                Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                            }

                            // TODO Auto-generated method stub

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(SendMessage.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

                queue.add(jsObjRequest);

            }
        });
    }
    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
