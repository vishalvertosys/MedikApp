package medik247.vs.com.medik;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;


/**
 * Base Fragment for all the Fragment used in the activity
 */
public abstract class BaseFragment extends Fragment
{
    public BaseActivity activity;
    public int height, width;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try {
            activity = (BaseActivity) getActivity();
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }
        catch (Exception  e){
        }
    }

}