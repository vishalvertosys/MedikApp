package medik247.vs.com.medik;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.name;
import static medik247.vs.com.medik.R.string.view;

/**
 * Created by Android on 28-04-2017.
 */

public class SignUp extends Activity {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String link=" http://vertosys.com/docpat/register.php?";
    String paraFname="first_name";
    String paralname="last_name";
    String paraemail="email";
    String parapassword="password";
    String paraphonenumber="phone_number";
    String paraofficeaddreess="office_address";
    String parahomeaddress="home_address";
  String doccertipara=  "doc_certificate";
    String profilepicencodedString;
            String doclicensepara="doc_license";
    String profile_picpara="profile_pic";
    String id,Image1,Image2,Image3;
    Bitmap bm;

    EditText edFname,edLname,edemail,edphonenumber,epassword,edconfirmpassword,edofficeaddress,edhomeaddress;
     String url;
    ProgressDialog progressDialog;
    private static final int IMG_RESULT3 =25 ;
    private static final int IMG_RESULT2 =222 ;
    private static int IMG_RESULT = 3;
    String ImageDecode;
    ImageView imageViewLoad1,imageViewLoad2,imageViewLoad3;
    Button LoadImage1,LoadImage2,LoadImage3;
    Intent intent;
    String[] FILE;
     String fname2,lname,password,phonenumber,email,officeaddreess,homeaddress,confirmpassword;
    private int REQUESTFINELOCAION=111;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
//        requestPermission();
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pattern = Pattern.compile(EMAIL_PATTERN);
        textView= (TextView) findViewById(R.id.set);
        edFname = (EditText) findViewById(R.id.edfname);
        edLname = (EditText) findViewById(R.id.edlname);
        edemail = (EditText) findViewById(R.id.editTextemail);
        edconfirmpassword = (EditText) findViewById(R.id.edconfirmpassword);
        epassword = (EditText) findViewById(R.id.editTextpassword);
        edphonenumber = (EditText) findViewById(R.id.phonenumber);
        edofficeaddress = (EditText) findViewById(R.id.editTextaddressoffice);
        edhomeaddress = (EditText) findViewById(R.id.homeaddress);
//        http://vertosys.com/docpat/register.php?first_name=varinder&last_name=singh&email=varinder@gmail.com&password=123456&phone_number=0987654321&profile_pic=pic.jpeg&user_type=doctor&office_address=address%20home&home_address=office%20address&doc_certificate=certificate&doc_license=license

        imageViewLoad1 = (ImageView) findViewById(R.id.imageView_1);
        imageViewLoad2 = (ImageView) findViewById(R.id.imageView_2);
        imageViewLoad3 = (ImageView) findViewById(R.id.imageView_3);
        LoadImage1 = (Button) findViewById(R.id.buttonpick_1);
        LoadImage2 = (Button) findViewById(R.id.buttonpick_2);
        LoadImage3 = (Button) findViewById(R.id.buttonpick_3);
        Button button = (Button) findViewById(R.id.sumbit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname2 = edFname.getText().toString();
                lname = edLname.getText().toString();
                email = edemail.getText().toString();
                officeaddreess = edofficeaddress.getText().toString();
                homeaddress = edhomeaddress.getText().toString();
                phonenumber = edphonenumber.getText().toString();
                password = epassword.getText().toString();
                confirmpassword = edconfirmpassword.getText().toString();
                Log.e("Data", fname2 + lname + password + phonenumber + email + officeaddreess + homeaddress + confirmpassword);
                if (password.equals(confirmpassword)) {
                    if (validate(email) == true) {
//    apihit();
                    }
                    if (fname2.equals("")) {
                        edFname.setError("Field not entered");
                        edFname.requestFocus();
                    }
                    if (lname.equals("")) {
                        edLname.setError("Field not entered");
                        edLname.requestFocus();
                    }
                    if (email.equals("")) {
                        edemail.setError("Field not entered");
                        edemail.requestFocus();
                    }
                    if (phonenumber.equals("")) {
                        edphonenumber.setError("Field not entered");
                        edphonenumber.requestFocus();
                    }
                    if (password.equals("")) {

                        epassword.setError("Field  not entered");
                        epassword.requestFocus();

                    }
                    if (officeaddreess.equals("")) {
                        edofficeaddress.setError("Field  not entered");
                        edofficeaddress.requestFocus();
                    }

                    if (homeaddress.equals("")) {
                        edhomeaddress.setError("Field  not entered");
                        edhomeaddress.requestFocus();
                    }

                    if (password.equals(confirmpassword) == false) {
                        Toast.makeText(SignUp
                                .this, "Password Not Match", Toast.LENGTH_SHORT).show();

                    } else {
                        url = link + "first_name=" + fname2 + "&last_name=" + lname + "&email=" +
                                email + "&password=" + password + "&phone_number=" + phonenumber + "&profile_pic=pic.jpeg&user_type=patient" + "&DeviceID=";
                        //http://vertosys.com/docpat/register.php?first_name=varinder&last_name=singh&email=varinder2@gmail.com&password=123456&phone_number=0987654321&profile_pic=pic.jpeg&user_type=patient
                        Log.e("URL", url);
                        progressDialog.show();

                        apihit();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.backsignup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Sigin.class);
                startActivity(intent);
            }
        });

        LoadImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);

            }
        });

        LoadImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT2);

            }
        });

        LoadImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT3);

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            String[] Sepsname = {"Allergist", "Anesthesiologist", "Cardiologist", "Dermatologist", "Gastroenterologist",
                    "Hematologist","Neurologist","Neurosurgeon","Obstetrician","Gynecologist","Ophthalmologist","Orthopaedic Surgeon"
                    ,"Otolaryngologist","Pathologist","Plastic Surgeon"};

            @Override
            public void onClick(View v) {
                final
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                builder.setTitle("Choose an Speciality");
                builder.setItems(Sepsname, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = Sepsname[which].toString();
                        textView.setText(selected);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void apihit()
    {
        url = link+"first_name="+fname2+"&last_name="+lname+"&email="+
                email+"&password="+password+"&phone_number="+phonenumber+"&profile_pic="+"filename.png"+"&user_type=doctor&office_address="+
                officeaddreess+"&home_address="+homeaddress+"&doc_certificate=certificate&doc_license=license"+"&DeviceID=";
        Log.e("URL Doctor",url);
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()  {

            @Override
            public void onResponse(JSONObject response) {
                String status = null;
                Log.e("Response Base64",response.toString());
                try {
                    status=response.getString("success");
                    id=response.getString("user_id");

                    Log.e("Status",status);
                    if(status.equals("true")&&(id!=null)){
                        imageApiHit();
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();

                    Toast.makeText(SignUp.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                // TODO Auto-generated method stub

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                progressDialog.dismiss();


                Toast.makeText(SignUp.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        queue.add(jsObjRequest);
    }

    public void imageApiHit(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String REGISTER_URL="http://vertosys.com/docpat/upload.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Intent intent=new Intent(SignUp.this,Main2Activity.class);
                        startActivity(intent);
                        progressDialog.hide();
                        // Toast.makeText(ReportIssueActivity.this,"",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.getMessage());
                        Toast.makeText(SignUp.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userID",id);
                params.put("dis_image",Image1);
                params.put("cer_image",Image2);
                params.put("doc_license",Image3);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUESTFINELOCAION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {

                bm =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Image1= encodeToBase64(bm);
            }

            if (requestCode == IMG_RESULT2 && resultCode == RESULT_OK
                    && null != data) {

                bm =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Image2= encodeToBase64(bm);


            }
            if (requestCode == IMG_RESULT3 && resultCode == RESULT_OK
                    && null != data) {

                bm =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                Image3= encodeToBase64(bm);

            }
        } catch (Exception e) {
//            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
        }
    }






    public static String encodeToBase64(Bitmap image)
    {
        float maxImageSize = 400;
        String Image;
        Bitmap Imap =  scaleDown(image, maxImageSize,true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Imap.compress(Bitmap.CompressFormat.JPEG,25,baos);
        byte[] b = baos.toByteArray();
        Image = Base64.encodeToString(b, Base64.DEFAULT);

        return Image;

    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                 maxImageSize / realImage.getWidth(),
                 maxImageSize / realImage.getHeight());
        int width = Math.round( ratio * realImage.getWidth());
        int height = Math.round( ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

      Intent in=new Intent(this,Sigin.class);
        startActivity(in);

    }


}
