package medik247.vs.com.medik;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * Created by Indosurplus on 5/5/2017.
 */

public class MajorSignup extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.majorsignup);
        setDialog();
    }
    private void setDialog()
    {
        final Dialog notiDialog = new Dialog(MajorSignup.this);
        notiDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        notiDialog.setContentView(R.layout.dialog);
        notiDialog.setCanceledOnTouchOutside(false);

        LinearLayout view_tutorial, find_parking;
        view_tutorial = (LinearLayout) notiDialog.findViewById(R.id.view_t);
        find_parking = (LinearLayout) notiDialog.findViewById(R.id.fine_park);

        view_tutorial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                pushAddFragments(DashboardScreen.getFragment(), false, true);
                Intent i = new Intent(MajorSignup.this, SignUp.class);
                startActivity(i);
                notiDialog.dismiss();
            }
        });

        find_parking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MajorSignup.this, Singuppatient.class);
                startActivity(i);
                notiDialog.dismiss();
            }
        });
        notiDialog.setCancelable(false);
        notiDialog.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
            Intent intent=new Intent(MajorSignup.this,Sigin.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
