package me.doapps.tacher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;

/**
 * Created by jonathan on 26/10/2014.
 */
public class Splash extends FragmentActivity{
    private MainFragment mainFragment;

    private ProgressBar progress_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
        }
        else{
            mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(android.R.id.content);
        }

        setContentView(R.layout.activity_splash);

        progress_splash = (ProgressBar)findViewById(R.id.progress_splash);

    }
}
