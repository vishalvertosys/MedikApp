package medik247.vs.com.medik;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScreenSignIn extends Activity {
    String link="http://vertosys.com/docpat/login.php?";
    String param1="email";
    String param2="password";
    private Pattern pattern;
    ProgressDialog progressDialog;



    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String emailid,password;
    SessionManager session;
     String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screensignin);
        pattern = Pattern.compile(EMAIL_PATTERN);
Intent intent2=getIntent();
        try {
            String uid = intent2.getStringExtra("KEY_ID");
            SharedPreferences pref2 = getApplicationContext().getSharedPreferences("MYID", 0); // 0 - for private mode
            SharedPreferences.Editor editor2 = pref2.edit();
            editor2.putString("UIDP",uid);
            editor2.commit();
        }catch (Exception e){}
        Intent intent = new Intent(this, MyFirebaseMessagingService.class);
        this.startService(intent);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyToken", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
      token=   pref.getString("key_token",null);
        Log.e("SaveToken",token);
        progressDialog = new ProgressDialog(ScreenSignIn.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        final EditText emailtext= (EditText) findViewById(R.id.emaildata);
        final EditText passwordText= (EditText) findViewById(R.id.passworddata);
        Button button= (Button) findViewById(R.id.loginbutton);
        TextView signuppage= (TextView) findViewById(R.id.signuppage);
        signuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScreenSignIn.this,MajorSignup.class);
                startActivity(intent);
            }
        });
        session = new SessionManager(getApplicationContext());
        session.checkLogin(ScreenSignIn.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                emailid=emailtext.getText().toString();
                password=passwordText.getText().toString();
//                http://vertosys.com/docpat/login.php?email=ramnish@gmail.com&password=123456&DeviceID=8961237ghjbhfbhsjdf
                String deviceID=generateID();
                if(validate(emailid)==true) {

                    RequestQueue queue = Volley.newRequestQueue(ScreenSignIn.this);
                    //DeviceID
                    String url=link+param1+"="+emailid+"&"+param2+"="+password+"&"+"DeviceID"+"="+token;
                    Log.e("LoginURL",url);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()  {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response",response.toString());
                            String status = null;
                            try {
                                status=response.getString("success");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                if(status.equals("false")){
                                    progressDialog.dismiss();
                                    String message=response.getString("message");
                                    Toast.makeText(ScreenSignIn.this,message,Toast.LENGTH_LONG).show();

                                }
                                String message=response.getString("message");
                                String usertype=response.getString("user_type");
                                Log.d("user","//"+usertype);
                                if(usertype.equals("doctor")) {
                                    {
                                        progressDialog.dismiss();

                                        session.createLoginSession(password, emailid,"doctor");
                                        Intent intent1=getIntent();
                                String uid=        intent1.getStringExtra("KEY_IDSet");
                                    Log.e("UID",""+uid);
                                        Intent intent = new Intent(ScreenSignIn.this, DoctorScreen.class);
                                        startActivity(intent);
                                    }
                                }
                                else  if(usertype.equals("patient")){
                                    progressDialog.dismiss();

                                    session.createLoginSession(password, emailid,"patient");
                                    Intent intent = new Intent(ScreenSignIn.this, Main2Activity.class);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // TODO Auto-generated method stub

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(ScreenSignIn.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });

                    queue.add(jsObjRequest);
                }
                else {
                    progressDialog.dismiss();

                    Toast.makeText(ScreenSignIn.this,"Invalid Email",Toast.LENGTH_SHORT).show();
                }


            }
        });
        ImageView imageView= (ImageView) findViewById(R.id.backsigin);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScreenSignIn.this,Sigin.class);
                startActivity(intent);
            }
        });


    }
    public String generateID() {
        String deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if ("9774d56d682e549c".equals(deviceId) || deviceId == null) {
            deviceId = ((TelephonyManager) this
                    .getSystemService(Context.TELEPHONY_SERVICE))
                    .getDeviceId();
            if (deviceId == null) {
                Random tmpRand = new Random();
                deviceId = String.valueOf(tmpRand.nextLong());
            }
        }
        return getHash(deviceId);
    }

    public String getHash(String stringToHash) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] result = null;
        try {
            result = digest.digest(stringToHash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : result) {
            sb.append(String.format("%02X", b));
        }

        String messageDigest = sb.toString();
        return messageDigest;
    }
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}
