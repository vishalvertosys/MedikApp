package medik247.vs.com.medik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Indosurplus on 5/18/2017.
 */

public class HistoryDoctorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount);
        ImageView imageView= (ImageView) findViewById(R.id.backup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryDoctorActivity.this,DoctorScreen.class);
                startActivity(intent);
            }
        });
    }
}
