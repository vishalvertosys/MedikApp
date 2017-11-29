package medik247.vs.com.medik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;


public class SessionManager {


    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "abc";

    // All Shared Preferences Keys

    public static final String LoginDetail = "LoginDetail";

    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "emailid";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String password, String emailid,String logindetail){
        Log.e("getdata",""+emailid);
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);


        // Storing name in pref.
        editor.putString(LoginDetail,logindetail);
        editor.putString(KEY_PASSWORD, password);

        // Storing email in pref
        editor.putString(KEY_EMAIL, emailid);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(Context context){
        // Check login status
        if(this.isLoggedIn()==true) {
            SharedPreferences settings = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            String loginDetails = settings.getString(LoginDetail, null);
            Log.e("LoginDetails", loginDetails);
            if (loginDetails.equals("doctor")) {
                Intent i = new Intent(_context, DoctorScreen.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                _context.startActivity(i);

            } else {
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(_context, Main2Activity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                _context.startActivity(i);
            }
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
user.put(LoginDetail,pref.getString(LoginDetail,null));
        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        SharedPreferences settings = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editorcheck=settings.edit();
        editorcheck.clear();
        editorcheck.commit();
        editor.clear();
        editor.commit();
        Log.d("clear","session logout code");

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Sigin.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}


