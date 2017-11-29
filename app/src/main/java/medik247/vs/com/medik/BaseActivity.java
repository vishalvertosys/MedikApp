package medik247.vs.com.medik;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity
{
    ProgressDialog progress;

    public void showProgressing(Context context, String message)
    {
        try
        {
            progress = new ProgressDialog(context);
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
            if (message != null)
            {
                progress.setMessage(message);
            }
            progress.show();
        }
        catch (Exception e)
        {
        }
    }

    public void hideProgressing()
    {
        try
        {
            if (progress != null)
            {
                progress.dismiss();
            }
        }
        catch (Exception e)
        {
        }
    }

    public DrawerLayout mDrawerLayout;
    public ArrayList<Fragment> fragments;

    public ArrayList<Fragment> getFragments()
    {
        if (fragments == null)
        {
            fragments = new ArrayList<>();
        }
        return fragments;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        fragments = new ArrayList<>();
    }

    /**
     * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to show, in tab identified by tag shouldAnimate ->
     * should animate transaction. false when we switch tabs, or adding first fragment to a tab true when when we are pushing more
     * fragment into navigation stack. shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)). false when we are
     * switching tabs (except for the first time) true in all other cases.
     */
    public void pushAddFragments(Fragment fragment, boolean shouldAnimate, boolean shouldAdd)
    {
        try
        {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (shouldAnimate)
            {
                ft.setCustomAnimations(R.anim.anim_push_in, R.anim.anim_push_out);
            }
            ft.add(R.id.content_frame, fragment);
            if (shouldAdd)
            {
                getFragments().add(fragment);
            }
            ft.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Select the second last fragment in current tab's stack.. which will be shown after the fragment transaction given below
     */
    public void popFragments(boolean shouldAnimate)
    {
        try
        {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (shouldAnimate)
            {
                ft.setCustomAnimations(R.anim.anim_pop_in, R.anim.anim_pop_out);
            }
            Fragment fragment = getFragments().get(getFragments().size() - 1);
            ft.remove(fragment);
            ft.commit();
            getFragments().remove(fragment);
            if (!getFragments().isEmpty())
            {
                getFragments().get(getFragments().size() - 1).onResume();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
