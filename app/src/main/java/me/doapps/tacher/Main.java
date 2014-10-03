package me.doapps.tacher;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import me.doapps.utils.Fonts;


public class Main extends ActionBarActivity {
    private static final long SPLASH_SCREEN_DELAY = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActionBar().hide();

        /*sincronizamos*/
        TextView txt_titulo = (TextView) findViewById(R.id.txt_titulo);
        txt_titulo.setTypeface(Fonts.setAngryBird(Main.this));

        TextView txt_subtitulo = (TextView) findViewById(R.id.txt_subtitulo);
        txt_subtitulo.setTypeface(Fonts.setAngryBird(Main.this));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progress_splash).setVisibility(View.GONE);
                findViewById(R.id.btn_login_fb).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_login_fb).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Main.this,Search.class);
                        startActivity(i);
                        finish();

                    }
                });

            }
        }, 3000);
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
