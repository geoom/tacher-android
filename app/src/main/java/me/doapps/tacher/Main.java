package me.doapps.tacher;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import me.doapps.utils.Fonts;


public class Main extends ActionBarActivity {
    private static final long SPLASH_SCREEN_DELAY = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        /*sincronizamos*/
        TextView txt_titulo = (TextView)findViewById(R.id.txt_titulo);
        txt_titulo.setTypeface(Fonts.setAngryBird(Main.this));

        TextView txt_subtitulo = (TextView)findViewById(R.id.txt_subtitulo);
        txt_subtitulo.setTypeface(Fonts.setAngryBird(Main.this));

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Main.this, Search.class));
                finish();
            }

        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
